#include "MapProblem.h"

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
            unsigned char character = world_map[(i*width)+j];

            if(character >= 0 && character <= 42)
            {
                std::cout << "0";
            }
            else if(character >= 43 && character <= 86)
            {
                std::cout << "1";
            }
            else if(character >= 87 && character <= 130)
            {
                std::cout << "2";
            }
            else if(character >= 131 && character <= 174)
            {
                std::cout << "3";
            }
            else if(character >= 175 && character <= 218)
            {
                std::cout << "4";
            }
            else if(character >= 219 && character <= 254)
            {
                std::cout << "5";
            }
            else
            {
                std::cout << "\u25AE";
            }

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
