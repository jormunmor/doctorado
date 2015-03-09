#include "opencv2/imgproc/imgproc.hpp"
#include "opencv2/highgui/highgui.hpp"

#include <cstdio>
#include <iostream>
#include <fstream>
#include <sstream>
#include <string>
#include <list>
#include <cmath>
#include <map>

typedef struct CvWSNode
{
    struct CvWSNode* next;
    int mask_ofs;
    int img_ofs;
}
CvWSNode;

typedef struct CvWSQueue
{
    CvWSNode* first;
    CvWSNode* last;
}
CvWSQueue;

using namespace cv;
using namespace std;

// GLOBAL VARS

// images
Mat markerMask; // image that contains the seeds
Mat grayPolygonImage; // this image is used to mark in the markerMask image the exterior and forbidden areas as a segmented area
Mat expandedPolygonImage; // extended image with white borders
Mat wshed; // image with the final segmentation

// polygon vectors
vector< vector<cv::Point2d> > utm_polygon; // The polygon that comes from the UTM coordinates.
vector< vector<cv::Point> > cpp_polygon; // The resultant polygon that comes from translating UTM to euclidean coordinates
vector< vector<cv::Point> > scaled_polygon; // The cpp_polygon scaled to be draw in a bigger image
vector< vector<cv::Point> > viewport_polygon; // The cpp_polygon scaled to be draw in a bigger image

// max/min values for distance to centroid calculation
double max_x = std::numeric_limits<double>::min();
double max_y = std::numeric_limits<double>::min();
double min_x = std::numeric_limits<double>::max();
double min_y = std::numeric_limits<double>::max();

// constants
const int BARRIER_R = 50;
const int BARRIER_G = 50;
const int BARRIER_B = 50;

string polygonFilePath = "/home/jorge/Desktop/polygon.txt";
string input_img_filename;
string output_img_filename;

// output image info
int width, height, final_w, final_h, desired_width = 500, added_lines = 20;
double scale_ratio = 1;

static CvWSNode* icvAllocWSNodes( CvMemStorage* storage );
void watershed2(Mat& src, Mat& markers, int* percentages, int* conqueredCells, int numberOfCells );
void cvWatershed2( const CvArr* srcarr, CvArr* dstarr, int* percentages, int* conqueredCells, int numberOfCells );
Mat createMatImg(int numberOfSeeds);
std::list<cv::Point> generate_seed_points(int numberOfSeeds);
void save_contours_to_disk(vector<vector<Point> > *color_contours, int size, int* percentages);
void save_images_to_disk();
void save_config_to_disk();

int main( int argc, char** argv )
{

    if(argc < 3)
    {
        cout << "Usage: area_partition polygon_file.txt percentage1 [percentage2] [percentage3] ... [percentageN]" << endl;
        return -1;

    }

    int numberOfSeeds = argc - 2;
    int percentages[numberOfSeeds], conqueredCells[numberOfSeeds], total_percentage = 0;
    polygonFilePath = string(argv[1]);
    for(int i=0; i<numberOfSeeds; i++)
    {
        conqueredCells[i] = 0;
        int perct = atoi(argv[i+2]);
        percentages[i] = perct;
        total_percentage += perct;

    }

    if(total_percentage < 100)
    {
        cout << "Warning: the sum of percentages is lower than 100. Some parts of the polygon won't be segmented." << endl;
        return -1;

    }
    else if(total_percentage > 100)
    {
        cout << "Error: the sum of percentages is greater than 100. Aborting..." << endl;
        return -1;

    }

    // create the img path names
    string filename1 = polygonFilePath.substr(0, polygonFilePath.size()-4); // avoid the .txt chars
    string filename2 = polygonFilePath.substr(0, polygonFilePath.size()-4); // avoid the .txt chars
    input_img_filename = filename1.append("_input_image.png");
    output_img_filename = filename2.append("_output_image.png");

    // The following Mat contains the polygon painted inside. Some of his vertex will touch the image border.
    // Due to the fact that some OpenCV functions such as findContours don't take into account the 1 pixel border of
    // the image, that polygon image must be repainted inside a bigger one to avoid loosing some vertex.
    // The polygon is painted in black while the background (exterior and forbidden areas) is white.
    Mat polygonImage = createMatImg(numberOfSeeds);
    if( polygonImage.empty() )
    {
        cout << "Unable to create polygon image from path '" << string(polygonFilePath) <<  "'. Aborting..." << endl;
        cout << "Check that the number of seeds is equal or less than the number of vertex." << endl;
        return -1;

    }

    // The following image is the bigger one that contains the polygon image inside. The background is white.
    // Also the additional rows and cols will be white.
    expandedPolygonImage = Mat(polygonImage.rows + added_lines, polygonImage.cols + added_lines, CV_8UC3);
    expandedPolygonImage = Scalar::all(255);

    // To paint the polygon image in the bigger one, we create a ROI with appropriate height and width.
    // An offset of added_lines/2 is applied to center the ROI.
    cv::Rect roi( cv::Point(added_lines/2, added_lines/2), cv::Size(polygonImage.cols, polygonImage.rows));
    cv::Mat destinationROI = expandedPolygonImage(roi);
    polygonImage.copyTo( destinationROI );

    // In previous versions, the polygon was re-painted with a thickness of 2 pixels to avoid having
    // the white background from the outter polygon and forbidden areas touching the seeds. Now
    // the watersheed has been modified to represent the white areas as negative values that
    // can not grow, so the seeds can touch it. Thus, the thickness can be of one pixel. Indeed this is no
    // longer needed, so even the drawContours call may be commented.
    //drawContours(expandedPolygonImage, viewport_polygon, 0, Scalar(0, 0, 0), 1);
    cvtColor(expandedPolygonImage, markerMask, COLOR_BGR2GRAY); // markerMask is a 1-channel image
    cvtColor(expandedPolygonImage, grayPolygonImage, COLOR_BGR2GRAY);
    markerMask = Scalar::all(0);

    // Generate and place the seed points
    std::list<cv::Point> seed_points = generate_seed_points(numberOfSeeds);
    std::list<cv::Point>::iterator it;
    for (it=seed_points.begin(); it!=seed_points.end(); ++it)
    {
        cv::Point seed = (*it);
        markerMask.at<uchar>(seed) = 255;

    }

    //Mat blackPixels = (grayPolygonImage == 0);
    //namedWindow( "Original Image" , CV_WINDOW_AUTOSIZE );
    //imshow("Original Image", markerMask);
    //waitKey(0);

    // Start of the watershed algorithm
    int i, j, compCount = 0;
    vector<vector<cv::Point> > contours;
    vector<Vec4i> hierarchy;

    // Find the seeds, pixels with value = 255. Each contour is a vector of points
    findContours(markerMask, contours, hierarchy, CV_RETR_CCOMP, CV_CHAIN_APPROX_SIMPLE);
    if( contours.empty() )
    {
        cout << "Error: no seeds were placed. Aborting..." << endl;
        return -1;
    }

    Mat markers(markerMask.size(), CV_32S); // size of markers must be equal to original 3-channel image
    markers = Scalar::all(0); // original set to all zeros
    int idx = 0;
    for( ; idx >= 0; idx = hierarchy[idx][0], compCount++ ){
        drawContours(markers, contours, idx, Scalar::all(compCount+1), -1, 8, hierarchy, INT_MAX);

    }

    // here IDX takes as values (0, 1, 2...) as seeds placed in the image starting from 0
    // compCount takes as values (1, 2, 3, ...) as seeds placed starting from 1
    if( compCount == 0 )
    {
        cout << "Error: no components found. Aborting..." << endl;
        return -1;

    }

    // The outter polygon and the forbidden areas must be marked in any form
    // to exclude them from the growing. Assigning a high positive value is not a
    // good idea because the growing will still occur (seeds are positive values in range [0,255]).
    // For this reason, the white parts of the color image are marked in the markers matrix as negative
    // values (-255). The watershed algorithm has been modified to accept this kind
    // of values, as well as the -1 for the barriers.
    Mat add = grayPolygonImage==255;
    add.convertTo(add,CV_32S);
    markers -= add;

    // Create a color for each component
    vector<Vec3b> colorTab;
    for( i = 0; i < compCount; i++ )
    {
        int b, g, r;
        do
        {
            b = theRNG().uniform(0, 255);
            g = theRNG().uniform(0, 255);
            r = theRNG().uniform(0, 255);
        } while(b==BARRIER_B && g==BARRIER_G && r==BARRIER_R); // we avoid the barrier color to represent the seeds

        colorTab.push_back(Vec3b((uchar)b, (uchar)g, (uchar)r));
    }

    // Set the number of conquered cells by each seed. Initially only one conquered cell (the proper seed placement).
    for( i = 1; i <= numberOfSeeds; i++){
        conqueredCells[i-1] = 1;
        // uncomment this if the seeds are placed in more than one pixel
        //int conqueredPixels = countNonZero(markers==i);
        //conqueredCells[i-1] = conqueredPixels;

    }

    // Count the number of cells that must be conquered.
    int numberOfCells = countNonZero(grayPolygonImage==0) - numberOfSeeds;
    printf("Number of cells to conquer before execution: %d\n", numberOfCells);

    // The algorithm is applied to the bigger image. The results are stored in the markers Mat
    watershed2(expandedPolygonImage, markers, percentages, conqueredCells, numberOfCells);
    //t = (double)getTickCount() - t;

    // Count and print the number of cells that were not conquered after executing the algorithm.
    int sum = 0;
    for( i = 1; i <= numberOfSeeds; i++){
        sum += conqueredCells[i-1];
        int remaining = (percentages[i-1]*numberOfCells/100.0)-conqueredCells[i-1];
        cout << "Number of conquered cells for color " << colorTab[i-1] << ": " << conqueredCells[i-1] << endl;
        cout << "Number of remaining cells for color " << colorTab[i-1] << ": " << remaining << endl;
        // uncomment this if the seeds are placed in more than one pixel
        //int conqueredPixels = countNonZero(markers==i);
        //conqueredCells[i-1] = conqueredPixels;

    }
    printf("Number of cells to conquer after execution: %d\n", numberOfCells - sum);
    printf("Number of blacks after execution: %d\n", countNonZero(markers==0));

    /*
    int value = 4;
    if(value == 4)
        return 0;
    */

    //int remainingCells = countNonZero(markers==0);
    //printf("Number of cells to conquer after execution: %d\n", remainingCells);

    // Create a Matrix to paint the results with one color per segmented area.
    wshed = Mat(markers.size(), CV_8UC3);

    for( i = 0; i < markers.rows; i++ )
    {
        for( j = 0; j < markers.cols; j++ )
        {
            int index = markers.at<int>(i,j);
            if(index < -1) // negatives below -1 are considered obstacles and thus painted in white
            {
                wshed.at<Vec3b>(i,j) = Vec3b(255, 255, 255);
            }
            else if(index == -1) // barriers are painted in a color never used for the seeds.
            {
                wshed.at<Vec3b>(i,j) = Vec3b(BARRIER_B, BARRIER_G, BARRIER_R);

            }
            else if(index == 0) // un-painted are colored in black
            {
                    wshed.at<Vec3b>(i,j) = Vec3b(0, 0, 0);

            }
            else if(index > compCount) // positives above the seeds are considered obstacles and thus painted in white
            {
                wshed.at<Vec3b>(i,j) = Vec3b(255, 255, 255);
            }
            else // use the color of the seeds
            {
                wshed.at<Vec3b>(i,j) = colorTab[index - 1];

            }

        }

    }

    // Vectorize the results. The idea is to create a Matrix for each of the subareas of the final segmentation
    // and calculate the contours of that subarea for each Matrix. Then, approx each with a polygon.
    // We will do all this only if we have more than one seed. If we have only one seed with 100% perctg,
    // we create the images but left intact the initial polygon because it doesn't needed any aproximation.

    if(percentages[0] != 100)
    {

        // For each segmentation area (color), will create a gray Mat wich will contains 255 in the positions of the subarea
        // and 0 in the rest. The final output image of the watershed will be traversed only one time to create each of
        // the gray Mats for performance purposes, so a map of Mats will be created.
        // When using a c++ map with class objects, those classes need to define operator < if used as keys,
        // so for avoiding this using the Vec3b colors as the keys, will use string representations of them.
        std::map<string,Mat> subareasMap;
        string colors[colorTab.size()];
        for(unsigned int i=0; i<colorTab.size(); i++)
        {
            // Convert the Vec3b to a string that serves as key to the map
            ostringstream color;
            color << colorTab[i];
            string color_string = color.str();
            colors[i] = color_string;

            // Create the gray Mat and put it to the map
            Mat subarea(wshed.size(), CV_8UC1);
            subarea = Scalar::all(0);
            subareasMap[color_string] = subarea;

        }

        // Traverse the output matrix and create the subareas matrix
        for(int i=0; i<wshed.rows; i++)
        {
            for(int j=0; j<wshed.cols; j++)
            {
                ostringstream color;
                color << wshed.at<Vec3b>(i, j);
                string color_string = color.str();

                // Only colors assigned to subareas must be taken into account
                int appereances = subareasMap.count(color_string);
                if(appereances == 1) // only one Mat per segmentation color
                {
                    Mat certain_subarea = subareasMap[color_string];
                    certain_subarea.at<unsigned char>(i, j) = 255;

                }

            }
        }

        // For each color, find the contours of the subarea and aprox a polygon
        cout << "\nInfo before applying polygon approximation: " << endl;
        vector<vector<Point> > color_contours[colorTab.size()];
        for(unsigned int i=0; i<colorTab.size(); i++)
        {
            Mat subarea = subareasMap[colors[i]];
            Mat subarea_copy;
            subarea.copyTo(subarea_copy);
            vector<Vec4i> hierarchy;
            vector<vector<Point> > contours;
            findContours(subarea_copy, contours, hierarchy, CV_RETR_CCOMP, CHAIN_APPROX_SIMPLE);
            cout << "- Number of contours for color " << colorTab[i] << ": " << contours.size() << endl;
            for(unsigned int k=0; k<contours.size(); k++)
            {
                cout << "---- Number of points for contour " << k << ": " << contours[k].size() << endl;

            }
            color_contours[i].resize(contours.size());
            for(unsigned int j = 0; j < contours.size(); j++ )
            {
                approxPolyDP(contours[j], color_contours[i][j], 3, true);

            }

        }

        cout << "\nInfo after applying polygon approximation: " << endl;
        for(unsigned int i=0; i<colorTab.size(); i++)
        {
            cout << "- Number of contours for color " << colorTab[i] << ": " << color_contours[i].size() << endl;
            for(unsigned int j=0; j<color_contours[i].size(); j++)
            {
                cout << "---- Number of points for contour " << j << ": " << color_contours[i][j].size() << endl;

            }

        }

        // generate utm/gps subareas
        save_contours_to_disk(color_contours, colorTab.size(), percentages);

    }

    // save the images in disk, will flip them so the output Mats are modified. Any
    save_images_to_disk();

    //cout << "\nNumber of remaining pixels without segmentation: " << remainingCells << endl;
    cout << "\nProgram finished. Press any key to continue." << endl;

    // uncomment to show windows with the results //
    /*
    namedWindow( "image", 1 );
    namedWindow( "watershed transform", 1 );
    imshow( "image", viewportImg );
    imshow( "watershed transform", wshed );
    waitKey(0); //waits until a key is pressed;
    */

    // return remainingCells;
    return 0;

}

/// TODO: permitir que el algoritmo tome un polígono con degradado y realice el flooding dando prioridad a los pixeles que estén más cerca de los bordes

void watershed2(Mat& src, Mat& markers, int* percentages, int* conqueredCells, int numberOfCells )
{
    InputArray i_arr = src;
    InputOutputArray io_arr = markers;
    CvMat c_src = i_arr.getMat(), c_markers = io_arr.getMat();
    cvWatershed2(&c_src, &c_markers, percentages, conqueredCells, numberOfCells );
}

void cvWatershed2( const CvArr* srcarr, CvArr* dstarr, int* percentages, int* conqueredCells, int numberOfCells )
{
    const int IN_QUEUE = -2;
    const int WSHED = -1;
    const int NQ = 256;
    cv::Ptr<CvMemStorage> storage;

    CvMat sstub, *src;
    CvMat dstub, *dst;
    CvSize size;
    CvWSNode* free_node = 0, *node;
    CvWSQueue q[NQ];
    int active_queue;
    int i, j;
    int db, dg, dr;
    int* mask;
    uchar* img;
    int mstep, istep;
    int subs_tab[513];

    // MAX(a,b) = b + MAX(a-b,0)
    #define ws_max(a,b) ((b) + subs_tab[(a)-(b)+NQ])
    // MIN(a,b) = a - MAX(a-b,0)
    #define ws_min(a,b) ((a) - subs_tab[(a)-(b)+NQ])

    #define ws_push(idx,mofs,iofs)  \
    {                               \
        if( !free_node )            \
            free_node = icvAllocWSNodes( storage );\
        node = free_node;           \
        free_node = free_node->next;\
        node->next = 0;             \
        node->mask_ofs = mofs;      \
        node->img_ofs = iofs;       \
        if( q[idx].last )           \
            q[idx].last->next=node; \
        else                        \
            q[idx].first = node;    \
        q[idx].last = node;         \
    }

    #define ws_pop(idx,mofs,iofs)   \
    {                               \
        node = q[idx].first;        \
        q[idx].first = node->next;  \
        if( !node->next )           \
            q[idx].last = 0;        \
        node->next = free_node;     \
        free_node = node;           \
        mofs = node->mask_ofs;      \
        iofs = node->img_ofs;       \
    }

    #define c_diff(ptr1,ptr2,diff)      \
    {                                   \
        db = abs((ptr1)[0] - (ptr2)[0]);\
        dg = abs((ptr1)[1] - (ptr2)[1]);\
        dr = abs((ptr1)[2] - (ptr2)[2]);\
        diff = ws_max(db,dg);           \
        diff = ws_max(diff,dr);         \
        assert( 0 <= diff && diff <= 255 ); \
    }

    src = cvGetMat( srcarr, &sstub );
    dst = cvGetMat( dstarr, &dstub );

    if( CV_MAT_TYPE(src->type) != CV_8UC3 )
        CV_Error( CV_StsUnsupportedFormat, "Only 8-bit, 3-channel input images are supported" );

    if( CV_MAT_TYPE(dst->type) != CV_32SC1 )
        CV_Error( CV_StsUnsupportedFormat,
            "Only 32-bit, 1-channel output images are supported" );

    if( !CV_ARE_SIZES_EQ( src, dst ))
        CV_Error( CV_StsUnmatchedSizes, "The input and output images must have the same size" );

    //size = cvGetMatSize(src);
    size.width = src->width;
    size.height = src->height;
    storage = cvCreateMemStorage();

    istep = src->step;
    img = src->data.ptr;
    mstep = dst->step / sizeof(mask[0]);
    mask = dst->data.i;

    memset( q, 0, NQ*sizeof(q[0]) );

    for( i = 0; i < 256; i++ )
        subs_tab[i] = 0;
    for( i = 256; i <= 512; i++ )
        subs_tab[i] = i - 256;

    // This code is to draw two pixel rows for the image,
    // the upper and lower border with WSHED values.
    for( j = 0; j < size.width; j++ )
        mask[j] = mask[j + mstep*(size.height-1)] = WSHED;

    // Initial phase: put all the neighbor pixels of each marker to the ordered queue and
    // determine the initial boundaries of the basins. The borders of the image are not
    // iterated by the loop index i and j.
    for( i = 1; i < size.height-1; i++ )
    {
        // The istep and mstep values contain the byte offset
        // that represent the width of the color image
        // and markers image, respectively.
        img += istep; mask += mstep;

        // This line is to draw two pixel columns for the image,
        // the left and right borders with WSHED values.
        mask[0] = mask[size.width-1] = WSHED;

        // For each row, we will see the value of each cell in the marker matrix.
        for( j = 1; j < size.width-1; j++ )
        {
            // Recover the marker[i][j] element.
            int* m = mask + j;

            // In the original watershed, the negative values were set to 0 initially.
            // Now we allow having negative values to mark these zones as zones that
            // must not grow. If the pixel is such a zone, then we do not add any of its
            // surrounding pixels. We must distinguish among the -1 value for barriers that
            // are generated for the watershed, and values lower than -1 (-255 i.e.) that
            // represent the forbidden areas and outter polygon.
            if( m[0] < 0 )
            {
                //m[0] = -1;
                continue;

            }

            // If m[i][j] is zero and is surrounded (4-neighbours) by any seed
            // then we add it to the queue.
            if( m[0] == 0 && (m[-1] > 0 || m[1] > 0 || m[-mstep] > 0 || m[mstep] > 0) )
            {
                // This code is to compute the priority of the pixel.
                // This priority is based on the pixel color and the
                // pixel color of the surrounding seeds. It is computed
                // as the minimum color difference.
                uchar* ptr = img + j*3;
                int idx = 256, t;
                if( m[-1] > 0 )
                    c_diff( ptr, ptr - 3, idx );
                if( m[1] > 0 )
                {
                    c_diff( ptr, ptr + 3, t );
                    idx = ws_min( idx, t );
                }
                if( m[-mstep] > 0 )
                {
                    c_diff( ptr, ptr - istep, t );
                    idx = ws_min( idx, t );
                }
                if( m[mstep] > 0 )
                {
                    c_diff( ptr, ptr + istep, t );
                    idx = ws_min( idx, t );
                }
                assert( 0 <= idx && idx <= 255 );
                ws_push( idx, i*mstep + j, i*istep + j*3 );
                // After being added to the apropriate queue,
                // the cell is marked as IN_QUEUE.
                m[0] = IN_QUEUE;
            }
        }
    }

    // Find the first non-empty queue. The range
    // is [0, 255].
    for( i = 0; i < NQ; i++ )
        if( q[i].first )
            break;

    // If there is no markers, exit immediately.
    if( i == NQ )
    {
        return;
    }

    active_queue = i;
    img = src->data.ptr;
    mask = dst->data.i;

    // recursively fill the basins
    for(;;)
    {
        int mofs, iofs;
        int lab = 0, t;
        int* m;
        uchar* ptr;

        // Check if the active queue has elements.
        // If not, then search for the following queue.
        if( q[active_queue].first == 0 )
        {
            for( i = active_queue+1; i < NQ; i++ )
            {
                if( q[i].first )
                {
                    break;
                }
            }

            // If no queue was found, then exit inmediately.
            if( i == NQ )
            {
                break;
            }

            active_queue = i;
        }

        // Extract the element from the given queue.
        ws_pop( active_queue, mofs, iofs );

        // Compute the value to 'paint' the element.
        m = mask + mofs;
        ptr = img + iofs;
        t = m[-1];
        if( t > 0 ) lab = t;
        t = m[1];
        if( t > 0 )
        {
            if( lab == 0 ) lab = t;
            else if( t != lab ) lab = WSHED;
        }
        t = m[-mstep];
        if( t > 0 )
        {
            if( lab == 0 ) lab = t;
            else if( t != lab ) lab = WSHED;
        }
        t = m[mstep];
        if( t > 0 )
        {
            if( lab == 0 ) lab = t;
            else if( t != lab ) lab = WSHED;
        }
        assert( lab != 0 );

        // check if the assigned marker (lab) has conquered the percentage of cells
        if(lab != WSHED){
            int actualPercentage = (conqueredCells[lab-1]/((double) numberOfCells))*100;
            if(actualPercentage >= percentages[lab-1]){
                lab = WSHED;
                /*
                if(active_queue == 0 || active_queue == 255){
                    m[0] = WSHED;

                }
                continue;
                */

            } else{
                conqueredCells[lab-1]++;

            }
        }

        m[0] = lab;
        //cout << "Active Queue: " << active_queue << endl;

        if( lab == WSHED )
            continue;

        if( m[-1] == 0 )
        {
            c_diff( ptr, ptr - 3, t );
            ws_push( t, mofs - 1, iofs - 3 );
            active_queue = ws_min( active_queue, t );
            m[-1] = IN_QUEUE;
        }
        if( m[1] == 0 )
        {
            c_diff( ptr, ptr + 3, t );
            ws_push( t, mofs + 1, iofs + 3 );
            active_queue = ws_min( active_queue, t );
            m[1] = IN_QUEUE;
        }
        if( m[-mstep] == 0 )
        {
            c_diff( ptr, ptr - istep, t );
            ws_push( t, mofs - mstep, iofs - istep );
            active_queue = ws_min( active_queue, t );
            m[-mstep] = IN_QUEUE;
        }
        if( m[mstep] == 0 )
        {
            c_diff( ptr, ptr + istep, t );
            ws_push( t, mofs + mstep, iofs + istep );
            active_queue = ws_min( active_queue, t );
            m[mstep] = IN_QUEUE;
        }
    }

    //cout << "Computing missing borders..." << endl;

    // After executing the watershed algorithm, some areas may be un-painted. If these
    // areas are near a polygon (or forbidden area) border, then that border will not be
    // painted and the polygon shape will appear incomplete. To avoid that, we iterate
    // over the markers matrix checking for each element with 0 value (not painted) if some
    // of his 4-neighbours have a -255 value, and if so we paint a barrier (-1). Positions
    // out of the polygon and inside forbidden areas are supposed to have a -255 value
    // configured in the main function.

    mask = dst->data.i;
    for( i = 1; i < size.height-1; i++ )
    {
        mask += mstep;

        for( j = 1; j < size.width-1; j++ )
        {
            int* m = mask + j;
            if( m[0] == 0 )
            {
                if(m[-1] == -255 || m[1] == -255 || m[-mstep] == -255 || m[mstep] == -255)
                {
                    m[0] = WSHED;
                }
            }
        }
    }


}

static CvWSNode* icvAllocWSNodes( CvMemStorage* storage )
{
    CvWSNode* n = 0;

    int i, count = (storage->block_size - sizeof(CvMemBlock))/sizeof(*n) - 1;

    n = (CvWSNode*)cvMemStorageAlloc( storage, count*sizeof(*n) );
    for( i = 0; i < count-1; i++ )
        n[i].next = n + i + 1;
    n[count-1].next = 0;

    return n;
}

Mat createMatImg(int numberOfSeeds)
{
    Mat outputMat(0, 0, CV_8UC3);
    string line;
    ifstream polygon_file(polygonFilePath.c_str());
    double latitude, longitude;

    if (polygon_file.is_open())
    {
        // read the outter polygon
        utm_polygon.push_back(vector<cv::Point2d>());
        int index = 0;
        while ( getline(polygon_file,line) )
        {
            // avoid empty lines
            if(line.compare(string("")) == 0)
            {
                continue;
            }
            string number; // Have a buffer string
            stringstream ss(line); // Insert the string into a stream
            vector<string> tokens; // Create vector to hold our words
            ss >> number;
            latitude = atof(number.c_str());
            ss >> number;
            longitude = atof(number.c_str());

            if(latitude == -1 || longitude == -1 ){
                // check if there are more points->forbidden areas
                index++;
                utm_polygon.push_back(vector<cv::Point2d>());

            }
            else{
                // insert the point and calculate min/max values
                cv::Point2d vertex(latitude, longitude);
                utm_polygon[index].push_back(vertex);

                if(latitude < min_x){
                    min_x = latitude;

                }

                if(longitude < min_y){
                    min_y = longitude;

                }

                if(latitude > max_x){
                    max_x = latitude;

                }

                if(longitude > max_y){
                    max_y = longitude;

                }

            }

        }

    }
    else
    {
        cout << "Unable to open file" << endl;
        return outputMat;

    }

    polygon_file.close();
    utm_polygon.pop_back();

    // check if more seeds than vertex. If that's the case, return a size 0 matrix
    if(utm_polygon[0].size() < numberOfSeeds)
    {
        return outputMat;
    }

    width = max_x - min_x;
    height = max_y - min_y;

    printf("max_x: %lf\n", max_x);
    printf("max_y: %lf\n", max_y);
    printf("min_x: %lf\n", min_x);
    printf("min_y: %lf\n", min_y);

    scale_ratio = ((double) desired_width)/width;
    final_w = width*scale_ratio; // set 5 additional pixels per side
    final_h = height*scale_ratio; // set 5 additional pixels per side
    /*
    printf("Image width: %d\n", width);
    printf("Image height: %d\n", height);
    printf("Final image width: %d\n", final_w);
    printf("Final image height: %d\n", final_h);
    printf("Scale ratio: %lf\n", scale_ratio);
    */
    // save to disk config parameters such as min_x, min_y, etc, to let other apps work with utm points over output image
    save_config_to_disk();

    for(unsigned int i=0;i<utm_polygon.size(); i++){
        cpp_polygon.push_back(vector<cv::Point>());
        scaled_polygon.push_back(vector<cv::Point>());
        viewport_polygon.push_back(vector<cv::Point>());
        for(unsigned int j=0; j<utm_polygon[i].size(); j++){
            // translate the floating points to integer points near (0,0) origin by substracting minimal values
            int x_point = (int) (utm_polygon[i][j].x - min_x);
            int y_point = (int) (utm_polygon[i][j].y - min_y);
            int x_scaled = x_point*scale_ratio;
            int y_scaled = y_point*scale_ratio;

            if(x_scaled >= final_w)
            {
                x_scaled = final_w-1;

            }

            if(y_scaled >= final_h)
            {
                y_scaled = final_h-1;

            }

            Point point(x_point, y_point); // the point resultant of translating from utm
            Point scaledPoint(x_scaled, y_scaled); // the point resultant of translating to a fixed image width
            Point viewportPoint(x_scaled + added_lines/2, y_scaled + added_lines/2); // apply a offset to each point to center the polygon
            cpp_polygon[i].push_back(point);
            scaled_polygon[i].push_back(scaledPoint);
            viewport_polygon[i].push_back(viewportPoint);

        }

    }

    outputMat = Mat(final_h, final_w, CV_8UC3);
    outputMat = Scalar::all(255);

    // paint the outer scaled polygon
    cv::Point poly[1][scaled_polygon[0].size()];
    for(unsigned int i=0; i<scaled_polygon[0].size();i++)
    {
        poly[0][i] = scaled_polygon[0][i];

    }
    const cv::Point* ppt[1] = {poly[0]};
    int npt[] = {scaled_polygon[0].size()};
    fillPoly(outputMat, ppt, npt, 1, Scalar( 0, 0, 0 ), 0 );

    // paint the forbidden areas
    for(unsigned int k=1; k<scaled_polygon.size(); k++)
    {
        // paint the outer polygon
        cv::Point poly[1][scaled_polygon[k].size()];
        for(unsigned int i=0; i<scaled_polygon[k].size();i++)
        {
            poly[0][i] = scaled_polygon[k][i];

        }
        const cv::Point* ppt2[1] = {poly[0]};
        int npt2[] = {scaled_polygon[k].size()};
        fillPoly(outputMat, ppt2, npt2, 1, Scalar( 255, 255, 255 ), 0 );

    }

    return outputMat;

}

std::list<cv::Point> generate_seed_points(int numberOfSeeds)
{
    std::list<cv::Point> seedPointsList; // initially empty
    std::list<cv::Point> polygonPointsList(viewport_polygon[0].begin(), viewport_polygon[0].end());
    std::list<cv::Point>::iterator polyIterator = polygonPointsList.begin();
    cv::Point firstPoint(*polyIterator); // pick the first element as the first seed
    cv::Point centroid(firstPoint); // the sum of the assigned points
    seedPointsList.push_back(firstPoint);
    polygonPointsList.erase(polygonPointsList.begin()); // delete the first element
    numberOfSeeds--;
    double x_difference, y_difference, biggestDistanceToCentroid, distanceToCentroid;
    //cout << "Remaining seeds: " << numberOfSeeds << endl;
    while(numberOfSeeds > 0)
    {
        // take the first initially as the most far
        std::list<cv::Point>::iterator it = polygonPointsList.begin();
        std::list<cv::Point>::iterator mostFar = it;
        x_difference = ((double)centroid.x)/seedPointsList.size() - (*it).x;
        y_difference = ((double)centroid.y)/seedPointsList.size() - (*it).y;
        biggestDistanceToCentroid = sqrt( pow(x_difference, 2) + pow(y_difference, 2) );
        it++;

        while(it != polygonPointsList.end())
        {
            x_difference = ((double)centroid.x)/seedPointsList.size() - (*it).x;
            y_difference = ((double)centroid.y)/seedPointsList.size() - (*it).y;
            distanceToCentroid = sqrt( pow(x_difference, 2) + pow(y_difference, 2) );
            if(distanceToCentroid > biggestDistanceToCentroid)
            {
                biggestDistanceToCentroid = distanceToCentroid;
                mostFar = it;

            }

            it++;

        }

        seedPointsList.push_back(*mostFar);
        centroid += *mostFar;
        polygonPointsList.erase(mostFar);
        numberOfSeeds--;

    }

    return seedPointsList;

}

void save_contours_to_disk(vector<vector<Point> > *color_contours, int size, int* percentages)
{
    for(int i=0; i<size; i++)
    {
        string filename = polygonFilePath.substr(0, polygonFilePath.size()-4); // avoid the .txt chars
        ostringstream convert;   // stream used for the conversion
        convert << i;      // insert the index of the subarea
        convert << "_" << percentages[i];
        filename += string("_subarea") + convert.str() + string(".txt");
        ofstream text_file;
        text_file.open (filename.c_str());
        for(unsigned int j=0; j<color_contours[i].size(); j++)
        {
            for(unsigned int k=0; k<color_contours[i][j].size(); k++)
            {
                Point point = color_contours[i][j][k];
                point.x -= added_lines/2;
                point.y -= added_lines/2;
                point *= 1/scale_ratio;
                point.x += min_x;
                point.y += min_y;
                text_file << point.x << " " << point.y << endl;
            }

            text_file << "-1.0 -1.0" << endl;

        }
        text_file.close();

    }

}

void save_images_to_disk()
{
    // flip the images
    //flip(viewportImg, viewportImg, 0);
    //flip(wshed, wshed, 0);

    // write to disk
    imwrite(input_img_filename, expandedPolygonImage);
    imwrite(output_img_filename, wshed);

}

void save_config_to_disk()
{

    string filename = polygonFilePath.substr(0, polygonFilePath.find_last_of("p")); // get the dir of the poly file

    filename += string("config.txt");
    /*
    ofstream text_file;
    text_file.open (filename.c_str());
    text_file << min_x << endl;
    text_file << min_y << endl;
    text_file << scale_ratio << endl;
    text_file << output_img_filename << endl;
    text_file.close();
    */

    FILE * pFile;
    pFile = fopen (filename.c_str(), "w");
    fprintf(pFile, "%lf\n", min_x);
    fprintf(pFile, "%lf\n", min_y);
    fprintf(pFile, "%lf\n", scale_ratio);
    fprintf(pFile, "%d\n", added_lines);
    fprintf(pFile, "%s\n", output_img_filename.c_str());
    fprintf(pFile, "%s\n", "");
    fclose(pFile);

}
