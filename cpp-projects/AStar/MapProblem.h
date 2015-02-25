#ifndef MAPPROBLEM_H
#define MAPPROBLEM_H

#include <iostream>

class MapProblem
{
public:
    int width;
    int height;
    int *world_map;

    MapProblem(int w, int h, int *map);
    int GetMap(int x, int y);
    void PrintMap();

};

#endif // MAPPROBLEM_H
