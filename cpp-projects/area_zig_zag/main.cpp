#include <stdio.h>
#include "surveillance_waypoints.h"
#include <iostream>
#include <fstream>
#include <string>
#include <sstream>
#include <stdlib.h>
#include <math.h>
#include <string.h>
#include <vector>
#include <limits>
#include "ll-utm/ll-utm.h"
#include "ll-utm/ll-utm_constants.h"

#include "opencv2/imgproc/imgproc.hpp"
#include "opencv2/highgui/highgui.hpp"

#define tam_maximo 2000

using namespace std;

typedef struct point
{
    double x;
    double y;

} Point;

void update_output_image(string in_filename, double *wps, double number_of_wps);

double compute_width(double altitude);

int main(int argc, char* argv[]) {

    vector<Point> outter_polygon;
    double dir_barrido_x = 1;
    double dir_barrido_y = 1;
    double width = 4;
    //double fly_height = 15.0;
    //double width = compute_width(fly_height);
    printf("width: %lf\n", width);
    //width = 4;
    int number_of_wps;
    int optimal_number_of_wps = std::numeric_limits<int>::max();
    double vertices [tam_maximo];
    double distance = 0;
    double optimal_distance = std::numeric_limits<double>::max();
    double *optimal_waypoints;
    FILE* idfile=NULL;

    if(argc<2)
    {
        cout << "ERROR: polygon filename required. Aborting..." << endl;
        return 0;

    }


    string in_filename = string(argv[1]);
    cout << "Opening polygon file: " << in_filename << endl;

    string out_filename = in_filename.substr(0, in_filename.size()-4) + "_zigzag.txt";
    string distance_filename = in_filename.substr(0, in_filename.size()-4) + "_distance.txt";

    idfile = fopen(in_filename.c_str(), "r");
    int i=0;
    int forbidden_areas = -1;

    if(idfile != NULL)
    {
        while (!feof(idfile))
        {
            fscanf(idfile,"%lf\n",&vertices[i]);
            fscanf(idfile,"%lf\n",&vertices[i+1]);
            if(vertices[i] == -1 && vertices[i+1] == -1)
            {
                forbidden_areas++;

            }
            else
            {
                if(forbidden_areas < 0) // only store the points of the outter polygon
                {
                    Point point;
                    point.x = vertices[i];
                    point.y = vertices[i+1];
                    outter_polygon.push_back(point);

                }
            }

            i = i + 2;

        }

        fclose(idfile);
        idfile= NULL;

    }
    else
    {
        cout << "ERROR: unable to open file. Aborting..." << endl;
        return 0;

    }

    cout << "Number of forbidden areas: " << forbidden_areas << endl;
    cout << "Number of points of the outter polygon: " << outter_polygon.size() << endl;

    for(int i=0; i<outter_polygon.size(); i++)
    {
        cout << "Trying iteration " << i << "..." << endl;
        if(i == outter_polygon.size()-1) //last vertex to initial vertex
        {
            dir_barrido_x = outter_polygon[i].x - outter_polygon[0].x;
            dir_barrido_y = outter_polygon[i].y - outter_polygon[0].y;

        }
        else //vertex to following vertex
        {
            dir_barrido_x = outter_polygon[i].x - outter_polygon[i+1].x;
            dir_barrido_y = outter_polygon[i].y - outter_polygon[i+1].y;

        }

        double waypoints [tam_maximo];
        distance = surveillance_waypoints(vertices, dir_barrido_x, dir_barrido_y, width, waypoints, &number_of_wps, forbidden_areas);

        //cout << "Number of waypoints for iteration " << i << ": " << number_of_wps << endl;
        //cout << "Distance for iteration " << i << ": " << distance << endl;
        printf("Number of waypoints for iteration %d: %lf\n", i, number_of_wps);
        printf("Distance for iteration %d: %lf\n", i, distance);

        /*
        int offset = 0;
        for(int cnt=0; cnt<number_of_wps; cnt++)
        {
            printf("Waypoint %d: %lf %lf\n", cnt, waypoints[offset],waypoints[offset+1] );
            offset += 2;
        }
        */

        if(number_of_wps < optimal_number_of_wps)
        {

            // create the optimal route file
            int fails = rename ( "datos.txt", out_filename.c_str() );
            if(fails != 0)
            {
                cout << "ERROR: unable to generate route file. Aborting..." << endl;
                return 0;

            }
            // create the optimal distance file
            ofstream myfile (distance_filename.c_str());
            if (myfile.is_open())
            {
                myfile << distance;
                myfile.close();
            }
            else
            {
                cout << "ERROR: unable to generate distance file. Aborting..." << endl;
                return 0;

            }

            optimal_number_of_wps = number_of_wps;
            optimal_distance = distance;
            optimal_waypoints = waypoints;

        }

    }

    //cout << "Optimal number of waypoints: " << optimal_number_of_wps << endl;
    //cout << "Optimal traversed distance: " << optimal_distance << endl;
    printf("Optimal number of waypoints: %lf\n", optimal_number_of_wps);
    printf("Optimal traversed distance:: %lf\n", optimal_distance);

    // now, if we encounter a config.txt file, we have to update the image that comes from the area partition algorithm
    update_output_image(in_filename, optimal_waypoints, optimal_number_of_wps);

    printf("Return value: %d\n", (int) ceil(optimal_distance));

    return (int) ceil(optimal_distance);

}

void update_output_image(string in_filename, double *wps, double number_of_wps)
{

    string filename = in_filename.substr(0, in_filename.find_last_of("p")) + string("config.txt"); // get the dir of the poly file
    FILE* idfile = fopen(filename.c_str(), "r");
    double min_x, min_y, scale_ratio;
    int added_lines;
    char image_path[1000];
    string img_path;

    if(idfile != NULL)
    {
        // we read without checking the feof flag, not a good practice
        fscanf(idfile,"%lf",&min_x);
        fscanf(idfile,"%lf",&min_y);
        fscanf(idfile,"%lf",&scale_ratio);
        fscanf(idfile,"%d",&added_lines);
        fscanf(idfile,"%s", image_path);
        img_path = string(image_path);

        fclose(idfile);
        idfile= NULL;

        // print to check correctness
        /*
        printf("Latitude: %lf\n", latitude);
        printf("Longitude: %lf\n", longitude);
        printf("Scale ratio: %lf\n", scale_ratio);
        printf("Added lines: %d\n", added_lines);
        printf("Image: %s\n", image_path);
        */

        // transform the wps to image coordinates
        vector<cv::Point> line;
        int offset = 0;
        for(int i=0; i<number_of_wps; i++)
        {
            int x_point = (int) (wps[offset] - min_x); // latitude
            int y_point = (int) (wps[offset+1] - min_y); // longitude
            int x_scaled = x_point*scale_ratio;
            int y_scaled = y_point*scale_ratio;
            cv::Point viewportPoint(x_scaled + added_lines/2, y_scaled + added_lines/2);
            cout << "Punto: " << viewportPoint.x << ", " << viewportPoint.y << endl;
            line.push_back(viewportPoint);

            offset += 2;

        }

        // read the image
        cv::Mat image;
        image = cv::imread(img_path, CV_LOAD_IMAGE_COLOR);

        // create a pointer to the data as an array of points (via a conversion to a Mat() object)
        cv::Mat m = cv::Mat(line);
        const cv::Point *pts = (const cv::Point*) m.data;
        int npts = m.rows;
        // draw the polygon
        cv::polylines(image, &pts, &npts, 1, false, cv::Scalar(255, 255, 255));

        // save the image
        cv::imwrite(img_path, image);



    }
    else
    {
        cout << "Unable to include zig zag polyline in output image." << endl;

    }

}

double compute_width(double altitude)
{
    double delta_xL1,delta_xL2;
    float aperture,gamma,delta,camera_angle,heading;
    double L1,L2;
    double delta_z;
    double width;

    delta_z = altitude;

    camera_angle = 90.0; aperture = 30.0;  heading = 0.0;
    gamma = (PI/180.0)*(heading - aperture); delta = (PI/180.0)*(heading + aperture);

    L1 = delta_z / tan((PI/180.0)*(camera_angle+aperture));
    L2 = delta_z / tan((PI/180.0)*(camera_angle-aperture));
    delta_xL1 = (delta_z*tan((PI/180.0)*aperture)) / sin((PI/180.0)*(camera_angle+aperture)) ;
    delta_xL2 = (delta_z*tan((PI/180.0)*aperture)) / sin((PI/180.0)*(camera_angle-aperture)) ;
    width = 2*delta_xL1;

    return(width);
}
