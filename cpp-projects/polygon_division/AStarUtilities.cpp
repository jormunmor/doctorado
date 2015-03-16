#include "AStarUtilities.h"
#include "opencv2/imgproc/imgproc.hpp"
#include "opencv2/highgui/highgui.hpp"

using namespace cv;

namespace astar
{

    // To move one pixel from start to end, by applying the A* algorithm.
    int movePixel(cv::Point& startPosition, cv::Point& endPosition, cv::Mat& image, cv::Mat& grayImage, cv::Mat& costImage, std::vector<cv::Point>& blackPixelsVector, std::map<string, int>& blackPixelsMapIndex)
    {
        // Create an instance of the search class. We create it here and pass it as
        // a pointer to avoid passing/returning it by copy, for efficiency purposes.
        // Also, we need the AStarSearch object here to get the solution nodes
        // directly, without returning them from the function.
        AStarSearch<MapSearchNode> aStarSearch;
        cv::Point size(image.cols, image.rows);
        // Be careful when interpreting this map. For the original developer, the cost
        // of going from one position to another is given by the number of the former.
        // Going from (18,0) to (19,0) has a cost of 1 instead of 174. Thus, the number
        // in each cell of the map has to be viewed as the cost of navigating from that
        // cell to any adjacent.
        unsigned char *world_map = costImage.data;

        int success = executeAStar(startPosition, endPosition, size, world_map, &aStarSearch);
        if(success == 1)
        {
            cv::Point previous = startPosition;
            cv::Vec3b previousColor;
            unsigned char previousGrayColor;
            unsigned char previousCost;
            cv::Point next;
            MapSearchNode *node = aStarSearch.GetSolutionStart();
            int steps = 0;
            for( ;; )
            {
                node = aStarSearch.GetSolutionNext();

                if( !node )
                {
                    //image.at<Vec3b>(next) = destinationColor;
                    //costImage.at<uchar>(next) = endPositionCost;
                    break;
                }

                // Recover the next path node color, switch the color with the previous and color it with the color.
                next = cv::Point(node->x, node->y);
                unsigned char nextCost = costImage.at<uchar>(next);
                cv::Vec3b nextColor = image.at<cv::Vec3b>(next);
                unsigned char nextGrayColor = grayImage.at<uchar>(next);

                // Store the old previous values.
                previousColor = image.at<cv::Vec3b>(previous);
                previousGrayColor = grayImage.at<uchar>(previous);
                previousCost = costImage.at<uchar>(previous);

                // Update actual previous values.
                costImage.at<uchar>(previous) = nextCost;
                image.at<cv::Vec3b>(previous) = nextColor;
                grayImage.at<uchar>(previous) = nextGrayColor;

                // If nextColor is a black pixel, then we have to update its new coordinates on the blackPixelsVector because it
                // has been moved. This is a costly operation.
                // TODO: search another form to do this.

                // Searching method: uncomment to use this. This is very costly.

                /*
                if(nextGrayColor == 0)
                {
                    for(unsigned int i=0; i<blackPixelsVector.size(); i++)
                    {
                        cv::Point& p = blackPixelsVector.at(i); // The & character is needed to get a reference to the element of the vector. Otherwise, a copy is returned.
                        if(p.x == next.x && p.y == next.y)
                        {
                            p.x = previous.x;
                            p.y = previous.y;
                            break;

                        }
                    }
                }
                */

                // Map method and vector update. May give good performance. Un comment to use this.
                // TODO: debug why this generates noise (additional black pixels)

                if(nextGrayColor == 0)
                {
                    // Create the key for the moving black pixel.
                    std::stringstream sstm;
                    sstm << next.x << "," << next.y;
                    string keyString = sstm.str();
                    //cout << "Old key string: " << keyString << endl;

                    // Get the index in the vector for that black pixel and remove it.
                    int idx = blackPixelsMapIndex[keyString];
                    blackPixelsVector.erase(blackPixelsVector.begin() + idx);

                    // Remove the old key from the map.
                    blackPixelsMapIndex.erase(keyString);

                    // Create the new key for the map.
                    std::stringstream sstm2;
                    sstm2 << previous.x << "," << previous.y;
                    string newKeyString = sstm2.str();
                    //cout << "New key string: " << newKeyString << endl;

                    // Create the new point and add it to the vector.
                    cv::Point newPoint(previous.x, previous.y);
                    blackPixelsVector.push_back(newPoint);

                    // Update the map.
                    blackPixelsMapIndex[newKeyString] = ((int) blackPixelsVector.size()) - 1;

                }


                // comment this line after debugging
                costImage.at<uchar>(next) = previousCost;
                image.at<cv::Vec3b>(next) = previousColor;
                grayImage.at<uchar>(next) = previousGrayColor;
                /*
                namedWindow( "Temporal Image" , CV_WINDOW_AUTOSIZE );
                imshow("Temporal Image", image);
                waitKey(0);
                */
                previous = next;

                // now we color the path with a green pixel on the original image
                //initialStateImage.at<Vec3b>(Point(node->x, node->y)) = greenColor;
                steps++;
            };

            //cout << "Solution steps " << steps << endl;
            //cout << "Solution cost: " << aStarSearch.GetSolutionCost() << endl;
            // show the path over the original image
            //namedWindow( "Color World Map", cv::WINDOW_AUTOSIZE);
            //imshow("Color World Map", image);
            //waitKey(0);
            // Once we're done with the solution, free the nodes up
            aStarSearch.FreeSolutionNodes();
            //cout << "Moved pixel from: (" << startPosition.x << "," << startPosition.y << ") to (" << endPosition.x << "," << endPosition.y << ")" << endl;

        }
        else
        {
            cout << "Search terminated. Did not find goal state.\n";
        }

        aStarSearch.EnsureMemoryFreed();

        return success;

    }

    int executeAStar(cv::Point start, cv::Point end, cv::Point size, unsigned char *world_map, AStarSearch<MapSearchNode> *aStarSearch)
    {

        // Our sample problem defines the world as a 2d array representing a terrain
        // Each element contains an integer from 0 to 5 which indicates the cost
        // of travel across the terrain. Zero means the least possible difficulty
        // in travelling (think ice rink if you can skate) whilst 5 represents the
        // most difficult. 9 indicates that we cannot pass.
        MapProblem problem(size.x, size.y, world_map);

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

        if(SearchState == AStarSearch<MapSearchNode>::SEARCH_STATE_SUCCEEDED)
        {
            return 1;
        }

        return 0;

    }

    MapProblem::MapProblem(int w, int h, unsigned char *map)
    {
        width = w;
        height = h;
        world_map = map;
    }

    unsigned char MapProblem::GetMap(int x, int y)
    {
        if(x < 0 || x >= width || y < 0 || y >= height)
        {
            return 255;
        }

        return world_map[(y*width)+x];
    }

    MapSearchNode::MapSearchNode()
    {
        x = y = 0;
    }

    MapSearchNode::MapSearchNode( int px, int py, MapProblem *prob )
    {
        x=px;
        y=py;
        problem = prob;
    }

    bool MapSearchNode::IsSameState( MapSearchNode &rhs )
    {

        // same state in a maze search is simply when (x,y) are the same
        if( (x == rhs.x) &&
            (y == rhs.y) )
        {
            return true;
        }
        else
        {
            return false;
        }

    }

    void MapSearchNode::PrintNodeInfo()
    {
        printf("Node position : (%d,%d)\n", x, y);
    }



    // Here's the heuristic function that estimates the distance from a Node
    // to the Goal.

    float MapSearchNode::GoalDistanceEstimate( MapSearchNode &nodeGoal )
    {
        //return fabsf(x - nodeGoal.x) + fabsf(y - nodeGoal.y);
        return sqrt((x - nodeGoal.x)*(x - nodeGoal.x) + (y - nodeGoal.y)*(y - nodeGoal.y));
    }

    bool MapSearchNode::IsGoal( MapSearchNode &nodeGoal )
    {

        if( (x == nodeGoal.x) &&
            (y == nodeGoal.y) )
        {
            return true;
        }

        return false;
    }

    // This generates the successors to the given Node. It uses a helper function called
    // AddSuccessor to give the successors to the AStar class. The A* specific initialisation
    // is done for each node internally, so here you just set the state information that
    // is specific to the application
    bool MapSearchNode::GetSuccessors( AStarSearch<MapSearchNode> *astarsearch, MapSearchNode *parent_node )
    {

        int parent_x = -1;
        int parent_y = -1;

        if( parent_node )
        {
            parent_x = parent_node->x;
            parent_y = parent_node->y;
        }


        MapSearchNode NewNode;

        // push each possible move except allowing the search to go backwards

        if( (problem->GetMap( x-1, y-1 ) < problem->obstacle_value)
            && !((parent_x == x-1) && (parent_y == y-1))
          )
        {
            NewNode = MapSearchNode(x-1, y-1, problem);
            astarsearch->AddSuccessor( NewNode );
        }

        if( (problem->GetMap( x+1, y+1 ) < problem->obstacle_value)
            && !((parent_x == x+1) && (parent_y == y+1))
          )
        {
            NewNode = MapSearchNode(x+1, y+1, problem);
            astarsearch->AddSuccessor( NewNode );
        }

        if( (problem->GetMap( x-1, y+1 ) < problem->obstacle_value)
            && !((parent_x == x-1) && (parent_y == y+1))
          )
        {
            NewNode = MapSearchNode(x-1, y+1, problem);
            astarsearch->AddSuccessor( NewNode );
        }

        if( (problem->GetMap( x+1, y-1 ) < problem->obstacle_value)
            && !((parent_x == x+1) && (parent_y == y-1))
          )
        {
            NewNode = MapSearchNode(x+1, y-1, problem);
            astarsearch->AddSuccessor( NewNode );
        }

        if( (problem->GetMap( x-1, y ) < problem->obstacle_value)
            && !((parent_x == x-1) && (parent_y == y))
          )
        {
            NewNode = MapSearchNode(x-1, y, problem);
            astarsearch->AddSuccessor( NewNode );
        }

        if( (problem->GetMap( x, y-1 ) < problem->obstacle_value)
            && !((parent_x == x) && (parent_y == y-1))
          )
        {
            NewNode = MapSearchNode(x, y-1, problem);
            astarsearch->AddSuccessor( NewNode );
        }

        if( (problem->GetMap( x+1, y ) < problem->obstacle_value)
            && !((parent_x == x+1) && (parent_y == y))
          )
        {
            NewNode = MapSearchNode(x+1, y, problem);
            astarsearch->AddSuccessor( NewNode );
        }


        if( (problem->GetMap( x, y+1 ) < problem->obstacle_value)
            && !((parent_x == x) && (parent_y == y+1))
            )
        {
            NewNode = MapSearchNode(x, y+1, problem);
            astarsearch->AddSuccessor( NewNode );
        }

        return true;
    }

    // given this node, what does it cost to move to successor. In the case
    // of our map the answer is the map terrain value at this node since that is
    // conceptually where we're moving

    float MapSearchNode::GetCost(MapSearchNode &successor)
    {
        return (float) problem->GetMap( x, y );

    }


}
