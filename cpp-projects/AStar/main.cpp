////////////////////////////////////////////////////////////////////////////////////////////////////////////////

// STL A* Search implementation
// (C)2001 Justin Heyes-Jones
//
// Finding a path on a simple grid maze
// This shows how to do shortest path finding using A*

////////////////////////////////////////////////////////////////////////////////////////////////////////////////

#include "stlastar.h"
#include "MapProblem.h"
#include "MapSearchNode.h"

// opencv includes
#include "opencv2/imgproc/imgproc.hpp"
#include "opencv2/highgui/highgui.hpp"

// c++ includes
#include <iostream>
#include <stdlib.h>
#include <time.h>
#include <map>

#define DEBUG_LISTS 0
#define DEBUG_LIST_LENGTHS_ONLY 0

using namespace std;
using namespace cv;

// function defs
int executeAStart(cv::Point start, cv::Point end, cv::Point size, unsigned char *world_map, AStarSearch<MapSearchNode> *aStarSearch);
void executeBasicExample();
void executeCvExample();
void executeCvExample2();
void executeCvExample3();
void executeCvExample4();
void showProblemImage(cv::Point size, unsigned char *world_map);
void fillIndexVector(Mat *matrix, vector<cv::Point> *vec);
void fillPositionMap(vector<cv::Point> *vec, std::map<string, cv::Point> *blackPixelsMap);
void movePixel(cv::Point startPosition, cv::Point endPosition, cv::Mat *image, cv::Mat *costImage, std::vector<cv::Point> *startVector);

int main( int argc, char *argv[] )
{
    // 1. To show how to navigate given a simple array of costs.
    //executeBasicExample();

    // 2. To navigate by converting a 3-channel maze image into a gray maze image whose data will be used as array of costs.
    // Darker pixels have a lower cost, while lighter a higher. White pixels (value=255) are obstacles. The path is showed
    // in green color.
    //executeCvExample();

    // 3. The same as the preceding, but adding a +1 to black pixels to avoid 0 costs.
    //executeCvExample2();

    // 4. The same as the preceding but the path is not shown. Instead, the start pixel is moved from start to end, and intermediate
    // pixels from the path are shifted, proviking a displacement on each one.
    //executeCvExample3();

    // 5. The same as the preceding but the path is not shown, but the process is done for every black pixel.
    executeCvExample4();

    return 0;
}

int executeAStart(cv::Point start, cv::Point end, cv::Point size, unsigned char *world_map, AStarSearch<MapSearchNode> *aStarSearch)
{

    // Our sample problem defines the world as a 2d array representing a terrain
    // Each element contains an integer from 0 to 5 which indicates the cost
    // of travel across the terrain. Zero means the least possible difficulty
    // in travelling (think ice rink if you can skate) whilst 5 represents the
    // most difficult. 9 indicates that we cannot pass.
    MapProblem problem(size.x, size.y, world_map);

    //problem.PrintMap();

    // Create a start state
    MapSearchNode nodeStart(start.x, start.y, &problem);

    // Define the goal state
    MapSearchNode nodeEnd(end.x, end.y, &problem);

    // Set Start and goal states
    aStarSearch->SetStartAndGoalStates(nodeStart, nodeEnd);

    unsigned int SearchState;
    unsigned int SearchSteps = 0;

    do
    {
        SearchState = aStarSearch->SearchStep();
        SearchSteps++;
    }
    while(SearchState == AStarSearch<MapSearchNode>::SEARCH_STATE_SEARCHING);

    // Display the number of loops the search went through
    //cout << "Number of search steps executed: " << SearchSteps << "\n";

    if(SearchState == AStarSearch<MapSearchNode>::SEARCH_STATE_SUCCEEDED)
    {
        return 1;
    }

    return 0;

}

void executeBasicExample()
{
    int width = 20;
    int height = 20;
    // Be careful when interpreting this map. For the original developer, the cost
    // of going from one position to another is given by the number of the former.
    // Going from (18,0) to (19,0) has a cost of 1 instead of 174. Thus, the number
    // in each cell of the map has to be viewed as the cost of navigating from that
    // cell to any adjacent.
    unsigned char world_map[20*20] =
    {

        1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,174,                                       // 00
        1,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,255,1,   // 01
        1,255,255,1,1,255,255,255,1,255,1,255,1,255,1,255,255,255,1,1,                 // 02
        1,255,255,1,1,255,255,255,1,255,1,255,1,255,1,255,255,255,1,1,                 // 03
        1,255,1,1,1,1,255,255,1,255,1,255,1,1,1,1,255,255,1,1,                         // 04
        1,255,1,1,255,1,1,1,1,255,1,1,1,1,255,1,1,1,1,1,                               // 05
        1,255,255,255,255,1,1,1,1,1,1,255,255,255,255,1,1,1,1,1,                       // 06
        1,255,255,255,255,255,255,255,255,1,1,1,255,255,255,255,255,255,255,1,         // 07
        1,255,1,1,1,1,1,1,1,1,1,255,1,1,1,1,1,1,1,1,                                   // 08
        1,255,1,255,255,255,255,255,255,255,1,1,255,255,255,255,255,255,255,1,         // 09
        1,255,1,1,1,1,255,1,1,255,1,1,1,1,1,1,1,1,1,1,                                 // 10
        1,255,255,255,255,255,1,255,1,255,1,255,255,255,255,255,1,1,1,1,               // 11
        1,255,1,255,1,255,255,255,1,255,1,255,1,255,1,255,255,255,1,1,                 // 12
        1,255,1,255,1,255,255,255,1,255,1,255,1,255,1,255,255,255,1,1,                 // 13
        1,255,1,1,1,1,255,255,1,255,1,255,1,1,1,1,255,255,1,1,                         // 14
        1,255,1,1,255,1,1,1,1,255,1,1,1,1,255,1,1,1,1,1,                               // 15
        1,255,255,255,255,1,1,1,1,1,1,255,255,255,255,1,1,1,1,1,                       // 16
        1,1,255,255,255,255,255,255,255,1,1,1,255,255,255,1,255,255,255,255,           // 17
        1,255,1,1,1,1,1,1,1,1,1,255,1,1,1,1,1,1,1,1,                                   // 18
        2,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,                                       // 19

    };

    // Create an instance of the search class. We create it here and pass it as
    // a pointer to avoid passing/returning it by copy, for efficiency purposes.
    // Also, we need the AStarSearch object here to get the solution nodes
    // directly, without returning them from the function.
    AStarSearch<MapSearchNode> aStarSearch;
    cv::Point start(0, 0);
    cv::Point end(width-1, height-1);
    cv::Point size(width, height);
    showProblemImage(size, world_map);
    int success = executeAStart(start, end, size, world_map, &aStarSearch);
    if(success == 1)
    {
        cout << "Search found goal state.\n";

        MapSearchNode *node = aStarSearch.GetSolutionStart();
        int steps = 0;

        node->PrintNodeInfo();
        for( ;; )
        {
            node = aStarSearch.GetSolutionNext();

            if( !node )
            {
                break;
            }

            node->PrintNodeInfo();
            steps++;
        };

        cout << "Solution steps " << steps << endl;
        cout << "Solution cost: " << aStarSearch.GetSolutionCost() << endl;

        // Once we're done with the solution, free the nodes up
        aStarSearch.FreeSolutionNodes();

    }
    else
    {
        cout << "Search terminated. Did not find goal state.\n";
    }

    aStarSearch.EnsureMemoryFreed();

    //printf("Posicion 0: %d\n", world_map[0]);
}

void executeCvExample()
{
    // Create and show the initial state image
    Mat initialStateImage = imread("maze.png", CV_LOAD_IMAGE_COLOR);
    namedWindow("Color World Map", cv::WINDOW_AUTOSIZE);// Create a window for display.
    imshow("Color World Map", initialStateImage);
    waitKey(0);

    // Create the cost image by converting the image to grayscale
    Mat costImage(initialStateImage.rows, initialStateImage.cols, CV_8UC1);
    cvtColor(initialStateImage, costImage, COLOR_BGR2GRAY);
    int width = costImage.cols;
    int height = costImage.rows;
    // Be careful when interpreting this map. For the original developer, the cost
    // of going from one position to another is given by the number of the former.
    // Going from (18,0) to (19,0) has a cost of 1 instead of 174. Thus, the number
    // in each cell of the map has to be viewed as the cost of navigating from that
    // cell to any adjacent.
    unsigned char *world_map = costImage.data;

    // Create an instance of the search class. We create it here and pass it as
    // a pointer to avoid passing/returning it by copy, for efficiency purposes.
    // Also, we need the AStarSearch object here to get the solution nodes
    // directly, without returning them from the function.
    AStarSearch<MapSearchNode> aStarSearch;
    cv::Point start(0, 0);
    cv::Point end(99, 99);
    cv::Point size(width, height);
    //showProblemImage(size, world_map);
    int success = executeAStart(start, end, size, world_map, &aStarSearch);
    if(success == 1)
    {
        //cout << "Search found goal state.\n";
        // create the color of the solution path
        Vec3b greenColor(0, 255, 0);

        MapSearchNode *node = aStarSearch.GetSolutionStart();
        int steps = 0;

        //node->PrintNodeInfo();
        for( ;; )
        {
            node = aStarSearch.GetSolutionNext();

            if( !node )
            {
                break;
            }

            //node->PrintNodeInfo();
            // now we color the path with a green pixel on the original image
            initialStateImage.at<Vec3b>(Point(node->x, node->y)) = greenColor;
            steps++;
        };

        cout << "Solution steps " << steps << endl;
        cout << "Solution cost: " << aStarSearch.GetSolutionCost() << endl;
        // show the path over the original image
        imshow("Color World Map", initialStateImage);
        waitKey(0);
        // Once we're done with the solution, free the nodes up
        aStarSearch.FreeSolutionNodes();

    }
    else
    {
        cout << "Search terminated. Did not find goal state.\n";
    }

    aStarSearch.EnsureMemoryFreed();

    //printf("Posicion 0: %d\n", world_map[0]);

}

void executeCvExample2()
{
    // Create and show the initial state image
    Mat initialStateImage = imread("maze4.png", CV_LOAD_IMAGE_COLOR);
    namedWindow("Color World Map", cv::WINDOW_AUTOSIZE);// Create a window for display.
    imshow("Color World Map", initialStateImage);
    waitKey(0);

    // Create the cost image by converting the image to grayscale
    Mat costImage(initialStateImage.rows, initialStateImage.cols, CV_8UC1);
    cvtColor(initialStateImage, costImage, COLOR_BGR2GRAY);
    int width = costImage.cols;
    int height = costImage.rows;

    // Add a +1 to each black pixel to avoid cost of 0. This is a costly operation.

    Mat add = costImage==0; // now each pixel with a 0 contains a 255. But we need a one.
    //Binary image
    cv::Mat binaryAdd(add.size(), add.type());
    //Apply thresholding
    cv::threshold(add, binaryAdd, 0, 1, cv::THRESH_BINARY);
    costImage += binaryAdd;

    // Be careful when interpreting this map. For the original developer, the cost
    // of going from one position to another is given by the number of the former.
    // Going from (18,0) to (19,0) has a cost of 1 instead of 174. Thus, the number
    // in each cell of the map has to be viewed as the cost of navigating from that
    // cell to any adjacent.
    unsigned char *world_map = costImage.data;

    // Create an instance of the search class. We create it here and pass it as
    // a pointer to avoid passing/returning it by copy, for efficiency purposes.
    // Also, we need the AStarSearch object here to get the solution nodes
    // directly, without returning them from the function.
    AStarSearch<MapSearchNode> aStarSearch;
    cv::Point start(0, 0);
    cv::Point end(costImage.cols - 1, costImage.rows - 1);
    cv::Point size(width, height);
    //showProblemImage(size, world_map);
    int success = executeAStart(start, end, size, world_map, &aStarSearch);
    if(success == 1)
    {
        //cout << "Search found goal state.\n";
        // create the color of the solution path
        Vec3b greenColor(0, 255, 0);

        MapSearchNode *node = aStarSearch.GetSolutionStart();
        int steps = 0;

        //node->PrintNodeInfo();
        for( ;; )
        {
            node = aStarSearch.GetSolutionNext();

            if( !node )
            {
                break;
            }

            //node->PrintNodeInfo();
            // now we color the path with a green pixel on the original image
            initialStateImage.at<Vec3b>(Point(node->x, node->y)) = greenColor;
            steps++;
        };

        cout << "Solution steps " << steps << endl;
        cout << "Solution cost: " << aStarSearch.GetSolutionCost() << endl;
        // show the path over the original image
        imshow("Color World Map", initialStateImage);
        waitKey(0);
        // Once we're done with the solution, free the nodes up
        aStarSearch.FreeSolutionNodes();

    }
    else
    {
        cout << "Search terminated. Did not find goal state.\n";
    }

    aStarSearch.EnsureMemoryFreed();

    //printf("Posicion 0: %d\n", world_map[0]);

}

void executeCvExample3()
{
    // Create and show the initial state image
    Mat initialStateImage = imread("maze6.png", CV_LOAD_IMAGE_COLOR);
    namedWindow("Color World Map", cv::WINDOW_AUTOSIZE);// Create a window for display.
    imshow("Color World Map", initialStateImage);
    waitKey(0);

    // Create the cost image by converting the image to grayscale
    Mat costImage(initialStateImage.rows, initialStateImage.cols, CV_8UC1);
    cvtColor(initialStateImage, costImage, COLOR_BGR2GRAY);
    int width = costImage.cols;
    int height = costImage.rows;

    // Add a +1 to each black pixel to avoid cost of 0. This is a costly operation.

    Mat add = costImage==0; // now each pixel with a 0 contains a 255. But we need a one.
    //Binary image
    cv::Mat binaryAdd(add.size(), add.type());
    //Apply thresholding
    cv::threshold(add, binaryAdd, 0, 1, cv::THRESH_BINARY);
    costImage += binaryAdd;

    // Be careful when interpreting this map. For the original developer, the cost
    // of going from one position to another is given by the number of the former.
    // Going from (18,0) to (19,0) has a cost of 1 instead of 174. Thus, the number
    // in each cell of the map has to be viewed as the cost of navigating from that
    // cell to any adjacent.
    unsigned char *world_map = costImage.data;

    // Create an instance of the search class. We create it here and pass it as
    // a pointer to avoid passing/returning it by copy, for efficiency purposes.
    // Also, we need the AStarSearch object here to get the solution nodes
    // directly, without returning them from the function.
    AStarSearch<MapSearchNode> aStarSearch;
    cv::Point start(0, 0);
    cv::Point end(costImage.cols - 1, costImage.rows - 1);
    cv::Point size(width, height);
    //showProblemImage(size, world_map);
    int success = executeAStart(start, end, size, world_map, &aStarSearch);
    if(success == 1)
    {
        // Create the color on which we have to colour the black pixel (suppose green).
        Vec3b color(0, 255, 0);
        cv::Point previous = start;
        cv::Point next;
        // Set the start position to that color.
        //initialStateImage.at<Vec3b>(start) = color;
        MapSearchNode *node = aStarSearch.GetSolutionStart();
        int steps = 0;

        for( ;; )
        {
            node = aStarSearch.GetSolutionNext();

            if( !node )
            {
                initialStateImage.at<Vec3b>(next) = color;
                break;
            }

            // Recover the next path node color, switch the color with the previous and color it with the color.
            next = cv::Point(node->x, node->y);
            cv::Vec3b nextColor = initialStateImage.at<Vec3b>(next);
            initialStateImage.at<Vec3b>(previous) = nextColor;
            previous = next;
            // now we color the path with a green pixel on the original image
            //initialStateImage.at<Vec3b>(Point(node->x, node->y)) = greenColor;
            steps++;
        };

        cout << "Solution steps " << steps << endl;
        cout << "Solution cost: " << aStarSearch.GetSolutionCost() << endl;
        // show the path over the original image
        imshow("Color World Map", initialStateImage);
        waitKey(0);
        // Once we're done with the solution, free the nodes up
        aStarSearch.FreeSolutionNodes();

    }
    else
    {
        cout << "Search terminated. Did not find goal state.\n";
    }

    aStarSearch.EnsureMemoryFreed();

    //printf("Posicion 0: %d\n", world_map[0]);

}

void executeCvExample4()
{
    // Create and show the initial state image
    Mat initialStateImage = imread("maze7.png", CV_LOAD_IMAGE_COLOR);
    namedWindow("Color Image", cv::WINDOW_AUTOSIZE);// Create a window for display.
    imshow("Color Image", initialStateImage);
    waitKey(0);

    // Create the cost image by converting the image to grayscale
    Mat costImage(initialStateImage.rows, initialStateImage.cols, CV_8UC1);
    cvtColor(initialStateImage, costImage, COLOR_BGR2GRAY);

    // Add a +1 to each black pixel to avoid cost of 0. This is a costly operation.
    Mat blackPixels = costImage==0; // now each pixel with a 0 contains a 255, and 0 the rest. But we need a one instead of 255.
    // Binary image.
    cv::Mat binaryAdd(blackPixels.size(), blackPixels.type());
    //Apply thresholding
    cv::threshold(blackPixels, binaryAdd, 0, 1, cv::THRESH_BINARY);
    costImage += binaryAdd;
    namedWindow("Cost Image", cv::WINDOW_AUTOSIZE);// Create a window for display.
    imshow("Cost Image", costImage);
    waitKey(0);
    namedWindow("Start Image", cv::WINDOW_AUTOSIZE);// Create a window for display.
    imshow("Start Image", blackPixels);
    waitKey(0);

    Mat greenPix(1, 1, CV_8UC3); // Mat that holds a single color pixel.
    greenPix.at<Vec3b>(cv::Point(0, 0)) = cv::Vec3b(0, 255, 0);
    Mat greenPixGrayCorrespondence(1, 1, CV_8UC1); // Mat that holds the gray correspondence of the color pixel
    cvtColor(greenPix, greenPixGrayCorrespondence, COLOR_BGR2GRAY);

    //now we must recover the indexes where the end pixels are (the color pixels)
    Mat colorPixels = costImage==greenPixGrayCorrespondence.at<uchar>(cv::Point(0,0));
    std::vector<cv::Point> colorPixelsVector;  // vector with the destiny index
    fillIndexVector(&colorPixels, &colorPixelsVector);
    namedWindow("End Image", cv::WINDOW_AUTOSIZE);// Create a window for display.
    imshow("End Image", colorPixels);
    waitKey(0);

    // Now we must recover the indexes where the start pixels are (the black pixels).
    // These index will be used as a key to recover the position of the black pixels.
    // This is due to the fact that black pixels can be moved if they are in the path
    // calculated for any other black pixel.
    std::vector<cv::Point> blackPixelsVector;  // vector with the initial index position used as key for the map
    fillIndexVector(&blackPixels, &blackPixelsVector);

    //std::map<string,cv::Point> blackPixelsMap; // key=black pixel initial index, value=actual position
    //fillPositionMap(&blackPixelsVector, &blackPixelsMap);

    // Initialize random seed
    srand (time(NULL));
    // Iterate over the black pixels and move them to a random color pixel position
    cout << "Number of black pixels to move: " << blackPixelsVector.size() << endl;
    while(blackPixelsVector.size() > 0)
    {
        cv::Point startPixel = blackPixelsVector.back();
        blackPixelsVector.pop_back();
        int pos = rand() % colorPixelsVector.size();
        cv::Point endPixel = colorPixelsVector.at(pos);
        movePixel(startPixel, endPixel, &initialStateImage, &costImage, &blackPixelsVector);
        //imshow("Color World Map", initialStateImage);
        //waitKey(0);

    }

    imshow("Color World Map", initialStateImage);
    waitKey(0);
    imwrite("output.png", initialStateImage);

}

void showProblemImage(cv::Point size, unsigned char *world_map)
{
    cv::Mat worldMapMat(size.x, size.y, CV_8UC1); // do not modify this MAT or the world map will change
    worldMapMat.data = world_map;
    namedWindow( "World Map Costs", cv::WINDOW_AUTOSIZE);// Create a window for display.
    imshow("World Map Costs", worldMapMat);                   // Show our image inside it.
    waitKey(0);
}

// Fill-in the vector with all matrix index that have a value of 255.
void fillIndexVector(Mat *matrix, vector<cv::Point> *vec)
{
    for(int j=0; j<matrix->rows; j++)
    {
        for(int i=0; i<matrix->cols; i++)
        {
            cv::Point index(i, j);
            if(matrix->at<uchar>(index) == 255)
            {
                vec->push_back(index);
            }
        }
    }
}

void fillPositionMap(vector<cv::Point> *vec, std::map<string, cv::Point> *blackPixelsMap)
{

}

// To move one pixel from start to end, by applying the A* algorithm.
void movePixel(cv::Point startPosition, cv::Point endPosition, cv::Mat *image, cv::Mat *costImage, std::vector<cv::Point> *blackPixelsVector)
{
    // Create an instance of the search class. We create it here and pass it as
    // a pointer to avoid passing/returning it by copy, for efficiency purposes.
    // Also, we need the AStarSearch object here to get the solution nodes
    // directly, without returning them from the function.
    AStarSearch<MapSearchNode> aStarSearch;
    cv::Point size(image->cols, image->rows);
    // Be careful when interpreting this map. For the original developer, the cost
    // of going from one position to another is given by the number of the former.
    // Going from (18,0) to (19,0) has a cost of 1 instead of 174. Thus, the number
    // in each cell of the map has to be viewed as the cost of navigating from that
    // cell to any adjacent.
    unsigned char *world_map = costImage->data;

    int success = executeAStart(startPosition, endPosition, size, world_map, &aStarSearch);
    if(success == 1)
    {
        // Create the color on which we have to colour the black pixel (suppose green).
        Vec3b color = image->at<Vec3b>(endPosition);
        cv::Point previous = startPosition;
        cv::Point next;
        // Set the start position to that color.
        //initialStateImage.at<Vec3b>(start) = color;
        MapSearchNode *node = aStarSearch.GetSolutionStart();
        int steps = 0;
        for( ;; )
        {
            node = aStarSearch.GetSolutionNext();

            if( !node )
            {
                image->at<Vec3b>(next) = color;
                break;
            }

            // Recover the next path node color, switch the color with the previous and color it with the color.
            next = cv::Point(node->x, node->y);
            cv::Vec3b nextColor = image->at<Vec3b>(next);
            image->at<Vec3b>(previous) = nextColor;
            // If nextColor is a black pixel, then we have to update its new coordinates on the blackPixelsVector because it
            // has been moved.

            if(nextColor.val[0]==0 && nextColor.val[1]==0 && nextColor.val[2]==0)
            {
                for(int i=0; i<blackPixelsVector->size(); i++)
                {
                    cv::Point p = blackPixelsVector->at(i);
                    if(p.x == next.x && p.y == next.y)
                    {
                        // erase the element
                        blackPixelsVector->erase(blackPixelsVector->begin() + i);
                        blackPixelsVector->push_back(cv::Point(previous.x, previous.y));
                        //p.x = previous.x;
                        //p.y = previous.y;
                        break;
                    }
                }
            }

            previous = next;
            // now we color the path with a green pixel on the original image
            //initialStateImage.at<Vec3b>(Point(node->x, node->y)) = greenColor;
            steps++;
        };

        //cout << "Solution steps " << steps << endl;
        //cout << "Solution cost: " << aStarSearch.GetSolutionCost() << endl;
        // show the path over the original image
        //imshow("Color World Map", initialStateImage);
        //waitKey(0);
        // Once we're done with the solution, free the nodes up
        aStarSearch.FreeSolutionNodes();
        cout << "Moved pixel from: (" << startPosition.x << "," << startPosition.y << ") to (" << endPosition.x << "," << endPosition.y << ")" << endl;

    }
    else
    {
        cout << "Search terminated. Did not find goal state.\n";
    }

    aStarSearch.EnsureMemoryFreed();

}


