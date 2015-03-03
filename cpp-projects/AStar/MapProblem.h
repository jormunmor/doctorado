#ifndef MAPPROBLEM_H
#define MAPPROBLEM_H

#include <iostream>

/*
 * This class represents a navigation problem. It contains a map
 * with the navigation costs as well as the width and height of
 * the map.
*/

class MapProblem
{
public:
    int width;
    int height;
    unsigned char *world_map;
    const unsigned char obstacle_value = (unsigned char) 255;

    MapProblem(int w, int h, unsigned char *map);
    unsigned char GetMap(int x, int y);
    void PrintMap();

};

#endif // MAPPROBLEM_H
