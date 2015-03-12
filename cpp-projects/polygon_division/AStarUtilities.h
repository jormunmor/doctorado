#include <iostream>
#include "opencv2/imgproc/imgproc.hpp"
#include "opencv2/highgui/highgui.hpp"
#include "stlastar.h"

namespace astar
{

   class MapProblem
   {
       public:
           int width;
           int height;
           unsigned char *world_map;
           const unsigned char obstacle_value = (unsigned char) 255;

           MapProblem(int w, int h, unsigned char *map);
           unsigned char GetMap(int x, int y);

   };

   class MapSearchNode
   {
       public:
           int x;
           int y;
           MapProblem *problem;

           MapSearchNode();
           MapSearchNode(int px, int py, MapProblem *prob);

           float GoalDistanceEstimate(MapSearchNode &nodeGoal);
           bool IsGoal(MapSearchNode &nodeGoal);
           bool GetSuccessors(AStarSearch<MapSearchNode> *astarsearch, MapSearchNode *parent_node);
           float GetCost(MapSearchNode &successor);
           bool IsSameState(MapSearchNode &rhs);
           void PrintNodeInfo();
   };

   void movePixel(cv::Point& startPosition, cv::Point& endPosition, cv::Mat& image, cv::Mat& grayImage, cv::Mat& costImage, std::vector<cv::Point>& blackPixelsVector);

   int executeAStar(cv::Point start, cv::Point end, cv::Point size, unsigned char *world_map, AStarSearch<MapSearchNode> *aStarSearch);

}
