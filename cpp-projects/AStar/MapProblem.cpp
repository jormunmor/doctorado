#include "MapProblem.h"

MapProblem::MapProblem(int w, int h, int *map)
{
    width = w;
    height = h;
    world_map = map;
}

int MapProblem::GetMap(int x, int y)
{
    if(x < 0 || x >= width || y < 0 || y >= height)
    {
        return 9;
    }

    return world_map[(y*width)+x];
}

void MapProblem::PrintMap()
{
    // print a opening horizontal line composed of _____________
    for(int i=0; i<width; i++)
    {
        std::cout << "_" ;
    }

    std::cout << std::endl;

    for(int i=0; i<height; i++)
    {

        for(int j=0; j<width; j++)
        {
            std::cout << world_map[(i*width)+j];
        }

        std::cout << std::endl;

    }

    // print a closing horizontal line composed of _____________
    for(int i=0; i<width; i++)
    {
        std::cout << "‾" ;
    }

    std::cout << std::endl;
}
