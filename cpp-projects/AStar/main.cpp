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

#define DEBUG_LISTS 0
#define DEBUG_LIST_LENGTHS_ONLY 0

using namespace std;
using namespace cv;

// function defs
int executeAStart(cv::Point start, cv::Point end, cv::Point size, unsigned char *world_map, AStarSearch<MapSearchNode> *aStarSearch);
void executeBasicExample();
void executeCvExample();
void showProblemImage(cv::Point size, unsigned char *world_map);

int main( int argc, char *argv[] )
{
    //executeBasicExample();
    executeCvExample();


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
    cout << "Number of search steps executed: " << SearchSteps << "\n";

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

void showProblemImage(cv::Point size, unsigned char *world_map)
{
    cv::Mat worldMapMat(size.x, size.y, CV_8UC1); // do not modify this MAT or the world map will change
    worldMapMat.data = world_map;
    namedWindow( "World Map Costs", cv::WINDOW_AUTOSIZE);// Create a window for display.
    imshow("World Map Costs", worldMapMat);                   // Show our image inside it.
    waitKey(0);
}

