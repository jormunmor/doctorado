#ifndef SURVEILLANCE_WAYPOINTS_H
#define SURVEILLANCE_WAYPOINTS_H

#include <stdio.h>
#include <math.h>

#define PI 3.14159265

#define NUM_VERTEX 500
#define IZQ -1
#define DER 1
#define VERDADERO 1
#define FALSO -1
#define INCREMENTO 1
#define DECREMENTO -1
#define EXTERNO 1
#define INTERNO -1

/****************************************************************************************************************************/
/*busca_waypoints calcula los puntos de paso, para que se pueda recorrer el interior de un polígono dado(el vertice (-1,-1) 
no es válido), teniendo en cuenta cierta resolución, se tendrá en cuenta zonas interiores por las que no 
se puede pasar. Esta función devuelve la distancia que se recorre al pasar por los waypoints, y por referencia 
los waypoints y el número de estos encontrados,  se la han de pasar los siguientes argumentos: los vertices del 
polígono, la componentes x e y del vector que indica la dirección de barrido, el ancho de dicho barrido, un puntero 
para guardar el número de waypoints y el número de poligonos internos a tener en cuenta*/
/*********************************************************************************************************************/

double surveillance_waypoints(double* vertices,double dir_barrido_x,double dir_barrido_y,double ancho_barrido,double *busca_waypoints,int* num_waypoints,int n_pol_interno);

double compute_camera_fov_width(int uav_id, double uav_altitude, double ground_altitude, double camera_angle);
double compute_width(int uav_id, double altitude, double height);

#endif
