#ifndef MAPSEARCHNODE_H
#define MAPSEARCHNODE_H

#include "stlastar.h"
#include "MapProblem.h"
#include <math.h>

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

#endif // MAPSEARCHNODE_H
