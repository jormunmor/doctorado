/****************************************************************************************************************************/
/*busca_waypoints calcula los puntos de paso, para que se pueda recorrer el interior de un polígono dado(el vertice (-1,-1) no es válido) ya que marca el
 * final de la lista de puntos que entran, teniendo en cuenta cierta resolución,se tendrá en cuenta zonas interiores por las que no se puede pasar. Esta 
 * función devuelve la distancia que se recorre al pasar por los waypoints, y por referencia los waypoints y el número de estos encontrados,  se la han de
 *  pasar los siguientes argumentos: los vertices del polígono, la componentes x e y del vector que indica la dirección de barrido, el ancho de dicho 
 * barrido, un puntero para guardar el número de waypointsy el número de poligonos internos a tener en cuenta*/
/*********************************************************************************************************************/
//no 100% seguro de dist bien calcu pero 99%

#include "surveillance_waypoints.h"

/*******************************************************************/
/*funciones que usa busca_waypoints para poder hallar dichos puntos*/
/*******************************************************************/

//función que haya el punto de corte entre dos rectas, si una de las rectas tiene pendiente infinita se ha de pasar como la recta dos y solo la a2 que 
//sería x=a2
void corte_2rectas(double a1, double b1, double a2, double b2, double aux, double* xc, double* yc);

//función que devuelve el índice del punto de corte con mayor o menor x, después de cierto punto de corte que se le pasa, si aux=1 buscaŕa la y mayor o 
//menor,es el caso pendiente infinita
int busca_pc(double *puntos_corte, double puntos_c, int criterio, int indice_anterior, int aux);

//función que calcula el vértice desde el que vamos a iniciar el barrido de la figura para hallar los waypoints
int busca_inicio(double* vertices, double dir_barrido_x, double dir_barrido_y);

//función que calcula los vértices interiores al poĺigono, que será donde se hallarán los puntos de corte con las rectas
void nuevo_vertice(double vertice_x, double vertice_y, double vertice1_x, double vertice1_y, double vertice2_x, double vertice2_y, double ancho_barrido, double vertice_ant_x, double vertice_ant_y, double* verticesn_x, double* verticesn_y, double valor_referencia, int tipo);

//calcula el valor absoluto
double absoluto(double x);

//función que calcula la distancia recorrida a traves de los puntos de la tabla vector desde el indice inicial al final, a esto se le suma la distancia de p1 al punto del indice inicial y la de p2 al indice final, a no ser que todas las coordenadas de estos dos puntos sean cero
double calcula_distancia_recorrida(double *vector, int indice_ini, int indice_fin, int tam, double p1_x, double p1_y, double p2_x, double p2_y);

//en esta función se ve si se produce un corte entre la recta formada por p1 y p2 y la formada por los dos vertices dados, y si dicho punto de corte se halla entre estos vertices. Se devuelve el punto de corte por referencia
int corte_intermedio(int vertice_1, int vertice_2, double* verticesn, double* vertices, double p1_x, double p1_y, double p2_x, double p2_y, double* xc, double* yc);

//función que nos vas facilitar el uso de los indices en las tablas, tendremos tablas circulares
int c(int indice, int modificacion, int n_vertices);

//incluirá puntos intermedios para no salirnos del área permitida, y se los incluye para recorrer el poligono exterior
void trazado_intermedio(int vertice_1a, int vertice_1b, int vertice_2a, int vertice_2b, double* verticesn, double* vertices, double p1_x, double p1_y, double p2_x, double p2_y, double* waypoints, int* j, int n_vertices);

//función que incluye a partir de waypoints de j los vertices entre v1 y v2 de la tabla verticesn, incluidos estos. Incluye los valores desde v1 a v2, incrementando en el indice o de crementando segun sentido
void incluye_vertices(double* waypoints, double* verticesn, int v1, int v2, int* j, int n_vertices, int sentido);

//funcion que busca entre los puntos de corte el más cercano a p1
int busca_cercano(double* puntos_corte, int puntos_c, double p1_x, double p1_y);

//funcion que busca entre los puntos de corte el más lejano a p1
int busca_lejano(double* puntos_corte, int puntos_c, double p1_x, double p1_y);

//función que se encarga de incluir, a los waypoints obtenidos con el poligono exterior, una seríe de waypoints para que se puede sortear una cierta forma interior por la que no está permitida el paso
int trazado_intermedio_poligono_interno(double* waypoints, double* waypoints1, double* vertices_int, double* verticesn_int, int n_vertices1, int j);

//funcion que a partir del poligono vertices obtiene uno interior o exterior a este según al valor de tipo. El poligono obtenido se almacena en verticesn y tendrá una separación con el original de ancho_barrido/2.
void calcula_verticesn(double* vertices, double* verticesn, int vertice_inicio, int n_vertices, double ancho_barrido, int tipo);

/*******************************************************************/
/***********************función busca_waypoints*********************/
/*******************************************************************/

double surveillance_waypoints(double* vertices, double dir_barrido_x, double dir_barrido_y, double ancho_barrido, double *waypoints, int* num_waypoints, int n_pol_interno) {

    double verticesn[NUM_VERTEX]; //almacena el poligono interior por el que nos podemos mover
    double vertices_int[NUM_VERTEX]; //vertices de un poligono interior
    double verticesn_int[NUM_VERTEX]; //vertices del poligono interior por los que podemos pasar
    double p1_x; //punto de la recta con la que vamos hallando los waypoints	
    double p1_y;
    double desplazamiento_x; //nos permitirán mover la recta con la que vamos cortando el poligono
    double desplazamiento_y;
    double a; //a y b son los parámetros de la recta con la que cortamos el polígono
    double b;
    double a1; //otros dos parámetros para calcular distintas rectas
    double b1;
    double xc; //puntos de corte entre rectas
    double yc;
    double distancia; //distancia recorrida al pasar por todos los waypoints
    //	double v;				//variable auxiliar
    //	double d1;				//variable auxiliar de distancia
    //	double d2;				//variable auxiliar de distancia
    double waypoints1[1000 * NUM_VERTEX]; //nos permitirá modificar los waypoints debido a una zona interior prohibida
    double puntos_corte[NUM_VERTEX]; //aqui almacenaremos los puntos de corte de cierta recta con nuestro poligono

    int vert_estado[NUM_VERTEX]; //para ir recorriendo la figura y encontrando los waypoints valdrá -1 inicialmente, 2, 1, dependiendo del número de rectas que se hayasn de tener en cuenta en los cortes desde ese vértice, y 0 al finalizar el algoritmo

    int vertices_puntos_c[NUM_VERTEX]; //tabla donde se guardarán los dos vertices del segmento donde está el punto de corte
    int vertice_ult_pc[2]; //guardamos los dos vertices colindates del ultimo pto de corte

    int vertice_inicio; //número del vértice por el que empezamos a buscar los waypoints
    int n_vertices = 0; //guardará el número de vértices del polígono externo
    int n_vertices1 = 0; //guardará el número de vértices de algún polígono interno
    int i = 0; //variable auxiliar
    int n = 0; //variable auxiliar
    int puntos_c = 2; //con el valor 2 no tendremos problemas si inicialmente hay 4 puntos de corte
    int puntos_c1 = 0; //guardamos el numero de puntos de corte
    int puntos_c_anterior; //guarda el número de puntos de corte de la anterior iteración

    int aux = 0; //valdrá 1 con pendiente infinita de la dirección de barrido, sino 0
    int aux1 = 0; //variable auxiliar
    //	int aux2=0;				//variable auxiliar
    //	int aux3=0;				//variable auxiliar
    int fuera = 0; //variable auxiliar
    int cambio = 0; //si vale cero se mete primero los puntos de más a la derecha, y se 1 los de la izquierda, si la pendiente del barrido es infinita será arriba si es cero, y abajo si es 1
    int j = 2; //indice con el que iremos guardando los waypoints
    //	int t=0;				//variable auxiliar

    FILE *datos;
    FILE *poligono;
    FILE *interno;

    //primero de todo vamos a calcular los waypoints necesarios para recorrer el polígono exterior y posteriormente veremos si tenemos algún polígono interno por el que no se pueda pasar y modificaremos los waypoints anteriormente calculados
    //calculamos el número de vértices del polígono exterior

    while (i == 0 && n_vertices <= 2 * NUM_VERTEX) {

        if (vertices[n_vertices] == -1 && vertices[n_vertices + 1] == -1)
            i = 1;
        else
            n_vertices = n_vertices + 2;
    }
    n_vertices = n_vertices / 2;

    //calculamos el vértice desde el que comenzaremos a recorrer el polígono
    vertice_inicio = busca_inicio(vertices, dir_barrido_x, dir_barrido_y);

    //guardamos dos vertices colindantes a este de inicio, 
    vertice_ult_pc[0] = vertice_inicio - 1;
    vertice_ult_pc[1] = c(vertice_inicio - 1, 1, n_vertices);

    //pasamos a rellenar la tabla verticesn, nos indicará un polígono por el que se podrá mover el vehículo, será interno dado y con una separación de ancho_barrido/2
    calcula_verticesn(vertices, verticesn, vertice_inicio, n_vertices, ancho_barrido, INTERNO);

    //ahora que tenemos la nueva figura hayamos los waypoints como corte con rectas de pendiente dir_barrido

    //inicializamos la tabla vert_estado
    for (i = 0; i < n_vertices; i++) {
        vert_estado[i] = -1;
    }
    vert_estado[vertice_inicio - 1] = 2; //segurá que habrá que considerar dos rectas ahí, en el primer vértice
    //inicializamos el primer waypoint
    waypoints[0] = verticesn[2 * (vertice_inicio - 1)];
    waypoints[1] = verticesn[2 * (vertice_inicio - 1) + 1];

    //vemos si la pendiente de barrido es infinita

    if (dir_barrido_x != 0)
        a = dir_barrido_y / dir_barrido_x;
    else
        aux = 1; //eso significa que la pendiente de la recta es infinito
    //calculamos lo que hay que ir sumando al punto de la recta con la que vamos a ir cortando el polígono, para así poder obtener facilmente una serie de rectas separadas una distancia de ancho_barrido
    if (aux == 0) //pendiente no infinita
    {
        //calculamos un vector perpendicular a dir_barrido y con módulo unidad
        desplazamiento_x = -1 * (dir_barrido_y * ancho_barrido) / sqrt(pow(dir_barrido_x, 2) + pow(dir_barrido_y, 2));
        desplazamiento_y = (dir_barrido_x * ancho_barrido) / sqrt(pow(dir_barrido_x, 2) + pow(dir_barrido_y, 2));
        //tenemos que comprobar si el sentido de ese vector es el oportuno
        p1_x = waypoints[0] + desplazamiento_x;
        p1_y = waypoints[1] + desplazamiento_y;

        b = p1_y - a*p1_x;
        //vemos si la recta obtenida usando ese vector se desplaza hacia el interior de la figura o hacia fuera
        //vemos si estan todos los vertices al mismo lado de la recta
        for (i = 0; i < n_vertices; i++) {
            if (verticesn[2 * i + 1] - a * verticesn[2 * i] - b < 0)
                fuera++;
        }
        if (fuera == 0) {
            desplazamiento_x = -desplazamiento_x;
            desplazamiento_y = -desplazamiento_y;
        }
    } else {
        //en este caso el desplazamiento es siempre hacia las x negativas, debido a como se calcula vertice inicio
        desplazamiento_x = -ancho_barrido;
        desplazamiento_y = 0;
    }
    //inicializamos el punto por el que tiene que pasar la primera de las rectas de corte
    p1_x = waypoints[0];
    p1_y = waypoints[1];
    //mientras las rectas con las que cortamos la figura estén dentro de esta
    while (fuera < n_vertices) //hasta que esten fuera todos los vertices 
    {
        puntos_c_anterior = puntos_c; //actualizamos
        //calculamos la nueva recta
        p1_x = p1_x + desplazamiento_x; //la pendiente evidentemente será siempre la misma
        p1_y = p1_y + desplazamiento_y;

        if (aux == 0)
            b = p1_y - a * p1_x;
        /*comprobamos los vertices que están al lado de la recta en el que han de estar todos al final, inicialemte tendrán el valor -1, luego pasará a 
         * tener el valor 2 o 1 dependiendo si hay que considerar 2 o 1 recta desde ese vertice, se pondrá un 2 si al encontrarlo vemos que los vértices 
         * colindantes tienen un -1, y 1 si alguno de los vertices es != de -1. Si al ver un nuevo vertice que era -1, ves que algun colindante tiene 1 
         * o 2 hay que restarle 1,...*/
        i = 0;
        fuera = 0;
        //actualizamos la tabla
        while (i < n_vertices) //recorremos toda la tabla vert_estado
        {
            //vemos si se sale fuera el vértice y si todavía no lo hemos considerado
            if ((aux == 0 && (verticesn[2 * i + 1] - a * verticesn[2 * i] - b < 0 && vert_estado[i] == -1)) || (aux == 1 && (p1_x < verticesn[2 * i] && vert_estado[i] == -1))) {
               //miramos si a ambos lados hay un -1, y ponemos un 2. Consideramos 2 rectas
                if (i != (n_vertices - 1) && i != 0) {
                    if (vert_estado[i - 1] == -1 && vert_estado[i + 1] == -1)
                        vert_estado[i] = 2;
                } else {
                    if (i == n_vertices - 1) {
                        if (vert_estado[i - 1] == -1 && vert_estado[0] == -1)
                            vert_estado[i] = 2;
                    } else {
                        if (vert_estado[n_vertices - 1] == -1 && vert_estado[i + 1] == -1)
                            vert_estado[i] = 2;
                    }
                }
                //si uno de los colindantes es -1, al que nos es -1 le restamos, y al nuevo le ponemos 1
                if (vert_estado[i] == -1) //si ya se ha modificado antes no entramos
                {
                    if (i != (n_vertices - 1) && i != 0) {
                        if (vert_estado[i - 1] != -1 && vert_estado[i + 1] == -1) {
                            vert_estado[i] = 1;
                            vert_estado[i - 1]--;
                        }
                        if (vert_estado[i - 1] == -1 && vert_estado[i + 1] != -1) {
                            vert_estado[i] = 1;
                            vert_estado[i + 1]--;
                        }
                    } else {
                        if (i == n_vertices - 1) {
                            if (vert_estado[i - 1] != -1 && vert_estado[0] == -1) {
                                vert_estado[i] = 1;
                                vert_estado[i - 1]--;
                            }
                            if (vert_estado[i - 1] == -1 && vert_estado[0] != -1) {
                                vert_estado[i] = 1;
                                vert_estado[0]--;
                            }
                        } 
                        else {
                            if (vert_estado[n_vertices - 1] != -1 && vert_estado[i + 1] == -1) {
                                vert_estado[i] = 1;
                                vert_estado[n_vertices - 1]--;
                            }
                            if (vert_estado[n_vertices - 1] == -1 && vert_estado[i + 1] != -1) {
                                vert_estado[i] = 1;
                                vert_estado[i + 1]--;
                            }
                        }
                    }

                    //si los dos son != -1, se le resta 1 a los dos y le pones a este un 0, aqui se unen dos rectas
                    if (vert_estado[i] == -1) //si todavía es -1
                    {
                        if (i != (n_vertices - 1) && i != 0) {
                            if (vert_estado[i - 1] != -1 && vert_estado[i + 1] != -1) {
                                vert_estado[i] = 0;
                                vert_estado[i - 1]--;
                                vert_estado[i + 1]--;
                            }
                        } else {
                            if (i == n_vertices - 1) {
                                if (vert_estado[i - 1] != -1 && vert_estado[0] != -1) {
                                    vert_estado[i] = 0;
                                    vert_estado[i - 1]--;
                                    vert_estado[0]--;
                                }

                            } else {
                                if (vert_estado[n_vertices - 1] != -1 && vert_estado[i + 1] != -1) {
                                    vert_estado[i] = 0;
                                    vert_estado[n_vertices - 1]--;
                                    vert_estado[i + 1]--;
                                }
                            }
                        }
                    }
                }
            }
            i++;
        }
        //contamos el número de vertices que actualmente se quedan fuera
        for (i = 0; i < n_vertices; i++) {
            if (vert_estado[i] == 0)
                fuera++;
        }
        if (fuera != n_vertices) {
            //aqui tenemos que hallar los puntos de corte entre la recta de barrido y el poligono usando la tabla actualizada antes
            ;
            puntos_c = 0;
            for (i = 0; i < n_vertices; i++) //miramemos toda la tabla vert_estado
            {
                //metemos en puntos_corte, todos los puntos de corte que encontremos para esa recta.
                //en puntos_c metemos el número de puntos de corte existente, para luego tratarlos
                switch (vert_estado[i]) {
                        //si desde ese vertice solo se considera una recta
                    case 1:

                        if (i != n_vertices - 1) {
                            if (vert_estado[i + 1] == -1) {
                                if (verticesn[2 * i] - verticesn[2 * i + 2] != 0) {
                                    a1 = (verticesn[2 * i + 1] - verticesn[2 * i + 3]) / (verticesn[2 * i] - verticesn[2 * i + 2]);
                                    b1 = verticesn[2 * i + 1] - a1 * verticesn[2 * i];

                                    if (aux == 0)
                                        corte_2rectas(a, b, a1, b1, 0, &xc, &yc);
                                    else
                                        corte_2rectas(a1, b1, p1_x, 0, 1, &xc, &yc);
                                    //guardamos el vertice anterior y posterior al punto de corte
                                    vertices_puntos_c[2 * puntos_c] = i;
                                    vertices_puntos_c[2 * puntos_c + 1] = i + 1;
                                } else{ //en este caso la pendiente de la recta es infinita
                                
                                    if (aux == 1) {
                                        //no punto de corte
                                        puntos_c--;
                                    }
                                    else {
                                        corte_2rectas(a, b, verticesn[2 * i], 0, 1, &xc, &yc);
                                        //guardamos el vertice anterior y posterior al punto de corte
                                        vertices_puntos_c[2 * puntos_c] = i;
                                        vertices_puntos_c[2 * puntos_c + 1] = i + 1;
                                    }
                                }
                            } else {
                                if (i == 0) {
                                    if (verticesn[2 * (n_vertices - 1)] - verticesn[0] != 0) {
                                        a1 = (verticesn[2 * i + 1] - verticesn[2 * (n_vertices - 1) + 1]) / (verticesn[2 * i] - verticesn[2 * (n_vertices - 1)]);
                                        b1 = verticesn[2 * i + 1] - a1 * verticesn[2 * i];

                                        if (aux == 0)
                                            corte_2rectas(a, b, a1, b1, 0, &xc, &yc);
                                        else
                                            corte_2rectas(a1, b1, p1_x, 0, 1, &xc, &yc);
                                        //guardamos el vertice anterior y posterior al punto de corte
                                        vertices_puntos_c[2 * puntos_c] = i;
                                        vertices_puntos_c[2 * puntos_c + 1] = n_vertices - 1;
                                    } else //en este caso la pendiente de la recta es infinita
                                    {
                                        if (aux == 1) {
                                            //no punto de corte
                                            puntos_c--;
                                        }
                                        else {
                                            corte_2rectas(a, b, verticesn[2 * i], 0, 1, &xc, &yc);
                                            //guardamos el vertice anterior y posterior al punto de corte
                                            vertices_puntos_c[2 * puntos_c] = i;
                                            vertices_puntos_c[2 * puntos_c + 1] = n_vertices - 1;
                                        }
                                    }
                                } 
                                else {
                                    if (verticesn[2 * i] - verticesn[2 * i - 2] != 0) {
                                        a1 = (verticesn[2 * i + 1] - verticesn[2 * i - 1]) / (verticesn[2 * i] - verticesn[2 * i - 2]);
                                        b1 = verticesn[2 * i + 1] - a1 * verticesn[2 * i];

                                        if (aux == 0)
                                            corte_2rectas(a, b, a1, b1, 0, &xc, &yc);
                                        else
                                            corte_2rectas(a1, b1, p1_x, 0, 1, &xc, &yc);
                                        //guardamos el vertice anterior y posterior al punto de corte
                                        vertices_puntos_c[2 * puntos_c] = i - 1;
                                        vertices_puntos_c[2 * puntos_c + 1] = i;
                                    } else{ //en este caso la pendiente de la recta es infinita
                                        if (aux == 1) {
                                            //no corte
                                            puntos_c--;
                                        }
                                        else {
                                            corte_2rectas(a, b, verticesn[2 * i], 0, 1, &xc, &yc);
                                            //guardamos el vertice anterior y posterior al punto de corte
                                            vertices_puntos_c[2 * puntos_c] = i - 1;
                                            vertices_puntos_c[2 * puntos_c + 1] = i;
                                        }
                                    }
                                }
                            }
                        } else {
                            if (vert_estado[0] == -1) {
                                if (verticesn[2 * i] - verticesn[0] != 0) {
                                    a1 = (verticesn[2 * i + 1] - verticesn[1]) / (verticesn[2 * i] - verticesn[0]);
                                    b1 = verticesn[2 * i + 1] - a1 * verticesn[2 * i];

                                    if (aux == 0)
                                        corte_2rectas(a, b, a1, b1, 0, &xc, &yc);
                                    else
                                        corte_2rectas(a1, b1, p1_x, 0, 1, &xc, &yc);
                                    //guardamos el vertice anterior y posterior al punto de corte

                                    vertices_puntos_c[2 * puntos_c] = 0;
                                    vertices_puntos_c[2 * puntos_c + 1] = i;
                                } else{ //en este caso la pendiente de la recta es infinita
                                
                                    if (aux == 1) {
                                        //no corte dos de 90º
                                        puntos_c--;
                                    }
                                    else {
                                        corte_2rectas(a, b, verticesn[2 * i], 0, 1, &xc, &yc);
                                        //guardamos el vertice anterior y posterior al punto de corte
                                        vertices_puntos_c[2 * puntos_c] = 0;
                                        vertices_puntos_c[2 * puntos_c + 1] = i;
                                    }
                                }

                            } else {
                                if (verticesn[2 * i] - verticesn[2 * i - 2] != 0) {
                                    a1 = (verticesn[2 * i + 1] - verticesn[2 * i - 1]) / (verticesn[2 * i] - verticesn[2 * i - 2]);
                                    b1 = verticesn[2 * i + 1] - a1 * verticesn[2 * i];

                                    if (aux == 0)
                                        corte_2rectas(a, b, a1, b1, 0, &xc, &yc);
                                    else
                                        corte_2rectas(a1, b1, p1_x, 0, 1, &xc, &yc);
                                    //guardamos el vertice anterior y posterior al punto de corte
                                    vertices_puntos_c[2 * puntos_c] = i - 1;
                                    vertices_puntos_c[2 * puntos_c + 1] = i;

                                } else //en este caso la pendiente de la recta es infinita
                                {
                                    if (aux == 1) {
                                        puntos_c--;
                                    }
                                    else {
                                        corte_2rectas(a, b, verticesn[2 * i], 0, 1, &xc, &yc);
                                        //guardamos el vertice anterior y posterior al punto de corte
                                        vertices_puntos_c[2 * puntos_c] = i - 1;
                                        vertices_puntos_c[2 * puntos_c + 1] = i;
                                    }
                                }
                            }
                        }
                        puntos_corte[2 * puntos_c] = xc;
                        puntos_corte[2 * puntos_c + 1] = yc;
                        puntos_c++;
                        break;
                    case 2:
                        //si desde ese vertice tenemos que considerar dos rectas de corte
                        if (i != 0 && i != n_vertices - 1) {
                            if (verticesn[2 * i] - verticesn[2 * i - 2] != 0) {
                                a1 = (verticesn[2 * i + 1] - verticesn[2 * i - 1]) / (verticesn[2 * i] - verticesn[2 * i - 2]);
                                b1 = verticesn[2 * i + 1] - a1 * verticesn[2 * i];
                                if (aux == 0)
                                    corte_2rectas(a, b, a1, b1, 0, &xc, &yc);
                                else
                                    corte_2rectas(a1, b1, p1_x, 0, 1, &xc, &yc);
                                //guardamos el vertice anterior y posterior al punto de corte
                                vertices_puntos_c[2 * puntos_c] = i - 1;
                                vertices_puntos_c[2 * puntos_c + 1] = i;
                            } else //en este caso la pendiente de la recta es infinita
                            {
                                if (aux == 1) {
                                    //no corte dos de 90º
                                    puntos_c--;
                                }
                                else {
                                    corte_2rectas(a, b, verticesn[2 * i], 0, 1, &xc, &yc);
                                    //guardamos el vertice anterior y posterior al punto de corte
                                    vertices_puntos_c[2 * puntos_c] = i - 1;
                                    vertices_puntos_c[2 * puntos_c + 1] = i;
                                }
                            }
                            puntos_corte[2 * puntos_c] = xc;
                            puntos_corte[2 * puntos_c + 1] = yc;
                            puntos_c++;
                            if (verticesn[2 * i] - verticesn[2 * i + 2] != 0) {
                                a1 = (verticesn[2 * i + 1] - verticesn[2 * i + 3]) / (verticesn[2 * i] - verticesn[2 * i + 2]);
                                b1 = verticesn[2 * i + 1] - a1 * verticesn[2 * i];
                                if (aux == 0)
                                    corte_2rectas(a, b, a1, b1, 0, &xc, &yc);
                                else
                                    corte_2rectas(a1, b1, p1_x, 0, 1, &xc, &yc);
                                //guardamos el vertice anterior y posterior al punto de corte
                                vertices_puntos_c[2 * puntos_c] = i;
                                vertices_puntos_c[2 * puntos_c + 1] = i + 1;
                            } else //en este caso la pendiente de la recta es infinita
                            {
                                if (aux == 1) {
                                    //no corte dos de 90º
                                    puntos_c--;
                                }
                                else {
                                    corte_2rectas(a, b, verticesn[2 * i], 0, 1, &xc, &yc);
                                    //guardamos el vertice anterior y posterior al punto de corte
                                    vertices_puntos_c[2 * puntos_c] = i;
                                    vertices_puntos_c[2 * puntos_c + 1] = i + 1;
                                }
                            }
                            puntos_corte[2 * puntos_c] = xc;
                            puntos_corte[2 * puntos_c + 1] = yc;
                            puntos_c++;
                        } else {
                            if (i == 0) {
                                if (verticesn[2 * (n_vertices - 1)] - verticesn[0] != 0) {
                                    a1 = (verticesn[2 * i + 1] - verticesn[2 * (n_vertices - 1) + 1]) / (verticesn[2 * i] - verticesn[2 * (n_vertices - 1)]);
                                    b1 = verticesn[2 * i + 1] - a1 * verticesn[2 * i];
                                    if (aux == 0)
                                        corte_2rectas(a, b, a1, b1, 0, &xc, &yc);
                                    else
                                        corte_2rectas(a1, b1, p1_x, 0, 1, &xc, &yc);
                                    //guardamos el vertice anterior y posterior al punto de corte

                                    vertices_puntos_c[2 * puntos_c] = i;
                                    vertices_puntos_c[2 * puntos_c + 1] = n_vertices - 1;
                                } else //en este caso la pendiente de la recta es infinita
                                {
                                    if (aux == 1) {
                                        xc = verticesn[2 * i];
                                        yc = p1_x;
                                    }
                                    else {
                                        corte_2rectas(a, b, verticesn[2 * i], 0, 1, &xc, &yc);
                                        //guardamos el vertice anterior y posterior al punto de corte
                                        vertices_puntos_c[2 * puntos_c] = i;
                                        vertices_puntos_c[2 * puntos_c + 1] = n_vertices - 1;
                                    }
                                }
                                puntos_corte[2 * puntos_c] = xc;
                                puntos_corte[2 * puntos_c + 1] = yc;
                                puntos_c++;
                                
                                if (verticesn[2 * i] - verticesn[2 * i + 2] != 0) {
                                    a1 = (verticesn[2 * i + 1] - verticesn[2 * i + 3]) / (verticesn[2 * i] - verticesn[2 * i + 2]);
                                    b1 = verticesn[2 * i + 1] - a1 * verticesn[2 * i];

                                    if (aux == 0)
                                        corte_2rectas(a, b, a1, b1, 0, &xc, &yc);
                                    else
                                        corte_2rectas(a1, b1, p1_x, 0, 1, &xc, &yc);
                                    //guardamos el vertice anterior y posterior al punto de corte
                                    vertices_puntos_c[2 * puntos_c] = i;
                                    vertices_puntos_c[2 * puntos_c + 1] = i + 1;
                                } else //en este caso la pendiente de la recta es infinita
                                {
                                    if (aux == 1) {
                                        //no corte dos de 90º
                                        puntos_c--;
                                    }
                                    else {
                                        corte_2rectas(a, b, verticesn[2 * i], 0, 1, &xc, &yc);
                                        //guardamos el vertice anterior y posterior al punto de corte
                                        vertices_puntos_c[2 * puntos_c] = i;
                                        vertices_puntos_c[2 * puntos_c + 1] = i + 1;
                                    }
                                }
                                puntos_corte[2 * puntos_c] = xc;
                                puntos_corte[2 * puntos_c + 1] = yc;
                                puntos_c++;
                            } else {
                                if (verticesn[2 * i] - verticesn[2 * i - 2] != 0) {
                                    a1 = (verticesn[2 * i + 1] - verticesn[2 * i - 1]) / (verticesn[2 * i] - verticesn[2 * i - 2]);
                                    b1 = verticesn[2 * i + 1] - a1 * verticesn[2 * i];
                                    if (aux == 0)
                                        corte_2rectas(a, b, a1, b1, 0, &xc, &yc);
                                    else
                                        corte_2rectas(a1, b1, p1_x, 0, 1, &xc, &yc);
                                    //guardamos el vertice anterior y posterior al punto de corte
                                    vertices_puntos_c[2 * puntos_c] = i - 1;
                                    vertices_puntos_c[2 * puntos_c + 1] = i;
                                } else //en este caso la pendiente de la recta es infinita
                                {
                                    if (aux == 1) {
                                        //no corte dos de 90º
                                        puntos_c--;
                                    }
                                    else {
                                        corte_2rectas(a, b, verticesn[2 * i], 0, 1, &xc, &yc);
                                        //guardamos el vertice anterior y posterior al punto de corte
                                        vertices_puntos_c[2 * puntos_c] = i - 1;
                                        vertices_puntos_c[2 * puntos_c + 1] = i;
                                    }
                                }
                                puntos_corte[2 * puntos_c] = xc;
                                puntos_corte[2 * puntos_c + 1] = yc;
                                puntos_c++;
                                if (verticesn[2 * i] - verticesn[0] != 0) {
                                    a1 = (verticesn[2 * i + 1] - verticesn[1]) / (verticesn[2 * i] - verticesn[0]);
                                    b1 = verticesn[2 * i + 1] - a1 * verticesn[2 * i];
                                    if (aux == 0)
                                        corte_2rectas(a, b, a1, b1, 0, &xc, &yc);
                                    else
                                        corte_2rectas(a1, b1, p1_x, 0, 1, &xc, &yc);

                                    //guardamos el vertice anterior y posterior al punto de corte
                                    vertices_puntos_c[2 * puntos_c] = 0;
                                    vertices_puntos_c[2 * puntos_c + 1] = i;
                                } else //en este caso la pendiente de la recta es infinita
                                {
                                    if (aux == 1) {
                                        //no corte dos de 90º
                                        puntos_c--;
                                    }
                                    else {
                                        corte_2rectas(a, b, verticesn[2 * i], 0, 1, &xc, &yc);
                                        //guardamos el vertice anterior y posterior al punto de corte
                                        vertices_puntos_c[2 * puntos_c] = 0;
                                        vertices_puntos_c[2 * puntos_c + 1] = i;
                                    }
                                }

                                puntos_corte[2 * puntos_c] = xc;
                                puntos_corte[2 * puntos_c + 1] = yc;
                                puntos_c++;
                            }
                        }
                        break;
                }
           }
            //ahora que tenemos la información de los puntos de corte, tenemos que pasarlo a waypoints
            //tenemos la variable cambio que nos va permitir empezar por los waypoints situados a un lado u otro de la recta que usamos antes

            //para no perder el número de puntos de corte
            puntos_c1 = puntos_c;
            //iremos alternando entre 0 y 1
            if (cambio == 0) {
                //buscamos el waypoint más a la izquierda
                i = busca_pc(puntos_corte, puntos_c, IZQ, -1, aux);
                //incluimos puntos intermedios para que no nos salgamos del polígono
                trazado_intermedio(vertice_ult_pc[0], vertice_ult_pc[1], vertices_puntos_c[2 * i], vertices_puntos_c[2 * i + 1], verticesn, vertices, waypoints[j - 2], waypoints[j - 1], puntos_corte[2 * i], puntos_corte[2 * i + 1], waypoints, &j, n_vertices);
                //nuevo waypoint
                waypoints[j] = puntos_corte[2 * i];
                waypoints[j + 1] = puntos_corte[2 * i + 1];
                j = j + 2;
                puntos_c1--;
                //guardamos los vertices colindantes del ultimo punto de corte
                vertice_ult_pc[0] = vertices_puntos_c[2 * i];
                vertice_ult_pc[1] = vertices_puntos_c[2 * i + 1];
                //si todavía quedan puntos de corte por incluir
                while (puntos_c1 > 0) {
                    //buscamos el siguiente índice más a la izquierda
                    i = busca_pc(puntos_corte, puntos_c, IZQ, i, aux);
                    //incluimos puntos intermedios para que no nos salgamos del polígono
                    trazado_intermedio(vertice_ult_pc[0], vertice_ult_pc[1], vertices_puntos_c[2 * i], vertices_puntos_c[2 * i + 1], verticesn, vertices, waypoints[j - 2], waypoints[j - 1], puntos_corte[2 * i], puntos_corte[2 * i + 1], waypoints, &j, n_vertices);
                    //nuevo waypoint
                    waypoints[j] = puntos_corte[2 * i];
                    waypoints[j + 1] = puntos_corte[2 * i + 1];
                    j = j + 2;
                    puntos_c1--;
                    //guardamos los vertices colindantes del ultimo punto de corte
                    vertice_ult_pc[0] = vertices_puntos_c[2 * i];
                    vertice_ult_pc[1] = vertices_puntos_c[2 * i + 1];
                }
            } else //en el caso de que cambio sea 1
            { //buscamos el siguiente índice más a la derecha
                i = busca_pc(puntos_corte, puntos_c, DER, -1, aux);
                //incluimos puntos intermedios para que no nos salgamos del polígono
                trazado_intermedio(vertice_ult_pc[0], vertice_ult_pc[1], vertices_puntos_c[2 * i], vertices_puntos_c[2 * i + 1], verticesn, vertices, waypoints[j - 2], waypoints[j - 1], puntos_corte[2 * i], puntos_corte[2 * i + 1], waypoints, &j, n_vertices);

                //nuevo waypoint
                waypoints[j] = puntos_corte[2 * i];
                waypoints[j + 1] = puntos_corte[2 * i + 1];
                j = j + 2;
                puntos_c1--;
                //guardamos los vertices colindantes del ultimo punto de corte
                vertice_ult_pc[0] = vertices_puntos_c[2 * i];
                vertice_ult_pc[1] = vertices_puntos_c[2 * i + 1];

                //si todavía quedan puntos de corte por incluir
                while (puntos_c1 > 0) { //buscamos el siguiente índice más a la derecha
                    i = busca_pc(puntos_corte, puntos_c, DER, i, aux);

                    //incluimos puntos intermedios para que no nos salgamos del polígono
                    trazado_intermedio(vertice_ult_pc[0], vertice_ult_pc[1], vertices_puntos_c[2 * i], vertices_puntos_c[2 * i + 1], verticesn, vertices, waypoints[j - 2], waypoints[j - 1], puntos_corte[2 * i], puntos_corte[2 * i + 1], waypoints, &j, n_vertices);
                    //guardamos los vertices colindantes del ultimo punto de corte
                    waypoints[j] = puntos_corte[2 * i];
                    waypoints[j + 1] = puntos_corte[2 * i + 1];
                    j = j + 2;
                    puntos_c1--;
                    //guardamos los vertices colindantes del ultimo punto de corte
                    vertice_ult_pc[0] = vertices_puntos_c[2 * i];
                    vertice_ult_pc[1] = vertices_puntos_c[2 * i + 1];
                }
            }
            //cambiamos el valor de cambio
            cambio = !cambio;
        }
    }
    //ahora vamos a tener en cuenta si hay polígonos interiores por los cuales no se nos permite el paso

    //variable auxiliar
    aux1 = 0;

    //mientras tengamos algún poligono interno no considerado
    while (n_pol_interno > 0) {
        //inicializamos
        i = 2 * n_vertices + aux1 + 2;
        n = 0;

        //copiamos el polígono interno en su tabla vertices_int
        while (!(vertices[i] == -1 && vertices[i + 1] == -1)) {
            vertices_int[n] = vertices[i];
            vertices_int[n + 1] = vertices[i + 1];

            i = i + 2;
            n = n + 2;
        }
        vertices_int[n] = -1;
        vertices_int[n + 1] = -1;
        
        //calculamos en número de vértices de lo de dentro
        i = 0;
        n_vertices1 = 0;

        while (i == 0 && n_vertices1 <= 2 * NUM_VERTEX) {

            if (vertices_int[n_vertices1] == -1 && vertices_int[n_vertices1 + 1] == -1)
                i = 1;
            else
                n_vertices1 = n_vertices1 + 2;
        }
        n_vertices1 = n_vertices1 / 2;

        //calculando esto estaremos en un vertice convexo

        vertice_inicio = busca_inicio(vertices_int, dir_barrido_x, dir_barrido_y);
        //calculamos verticesn_int,que serán los vertices del polígono exterior al dado a una diatancia ancho_barrido/2	
        calcula_verticesn(vertices_int, verticesn_int, vertice_inicio, n_vertices1, ancho_barrido, EXTERNO);

        //devuelve el numero de elementos de waypoints1
        //incluimos todos los waypoints necesarios para no meternos en el poligono interior
        j = trazado_intermedio_poligono_interno(waypoints, waypoints1, vertices_int, verticesn_int, n_vertices1, j);

        //actualizamos variables
        n_pol_interno--;
        aux1 = aux1 + 2 * n_vertices1 + 2;
    }

    /*Calculamos la distancia recorrida*/
    distancia = calcula_distancia_recorrida(waypoints, 0, j / 2 - 1, 100 * NUM_VERTEX, 0, 0, 0, 0);
    *num_waypoints = j / 2;
    //volcamos el poligono y los waypoints en dos archivos de texto
    datos = fopen("datos.txt", "w");
    i = 0;
    while (i < j) {
        fprintf(datos, "%f ", waypoints[i]);
        fprintf(datos, "%f\n", waypoints[i + 1]);
        i = i + 2;
    }
    fclose(datos);
    poligono = fopen("poligono.txt", "w");
    i = 0;
    while (i < 2 * n_vertices) {
        fprintf(poligono, "%f ", vertices[i]);
        fprintf(poligono, "%f\n", vertices[i + 1]);
        i = i + 2;
    }
    fprintf(poligono, "%f ", vertices[0]); //para que lo una
    fprintf(poligono, "%f\n", vertices[1]);
    fclose(poligono);
    interno = fopen("interno.txt", "w");
    i = 0;
    while (i < 2 * n_vertices1) {
        fprintf(interno, "%f ", vertices_int[i]);
        fprintf(interno, "%f\n", vertices_int[i + 1]);
        i = i + 2;
    }
    fprintf(interno, "%f ", vertices_int[0]); //para que lo una
    fprintf(interno, "%f\n", vertices_int[1]);
    fclose(interno);
    
    return (distancia);
}

//función que devuelve el índice del punto de corte con mayor o menor x, después de cierto punto de corte que se le pasa, si aux=1 buscaŕa la y mayor o menor,es el caso pendiente infinita

int busca_pc(double *puntos_corte, double puntos_c, int criterio, int indice_anterior, int aux) {
    double umbral; //para poder buscar el siguiente punto más a la izquierda derecha
    int i = 0; //variable auxiliar
    int j = -2; //variable auxiliar, vale -2 para que no afecte

    //si tenemos que devolver el índice del de más a la izquierda
    if (criterio == IZQ) {
        if (aux == 0) //si los puntos no estan sobre una recta de pendiente infinita
        {
            if (indice_anterior == -1) //si estamos buscando el primer elemento 
            {
                j = 0;
                while (i < puntos_c) {

                    if (puntos_corte[2 * i] > puntos_corte[2 * j])
                        j = i;
                    i++;
                }
            } else //para el resto de casos tendremos que usar el umbral
            {
                umbral = puntos_corte[2 * indice_anterior];
                while (i != (j + 1)) //tomamos un primer valor inferior al umbral
                {
                    if (puntos_corte[2 * i] < umbral)
                        j = i;
                    i++;
                }
                i = 0;
                while (i < puntos_c) {
                    //buscamos el valor inferior a unbral y más cercano			
                    if (puntos_corte[2 * i] < umbral && puntos_corte[2 * i] > puntos_corte[2 * j])
                        j = i;
                    i++;
                }
            }
        } else //si la pendiente es infinita
        {
            if (indice_anterior == -1) //si estamos buscando el primer elemento 
            {
                j = 0;
                while (i < puntos_c) {
                    //es simplemente el de menor y			
                    if (puntos_corte[2 * i + 1] > puntos_corte[2 * j + 1])
                        j = i;
                    i++;
                }
            } else {
                umbral = puntos_corte[2 * indice_anterior + 1];
                while (i != (j + 1)) //tomamos un primer valor inferior
                {
                    if (puntos_corte[2 * i + 1] < umbral)
                        j = i;
                    i++;
                }
                i = 0;
                while (i < puntos_c) {
                    //buscamos el valor inferior a unbral y más cercano				
                    if (puntos_corte[2 * i + 1] < umbral && puntos_corte[2 * i + 1] > puntos_corte[2 * j + 1])
                        j = i;
                    i++;
                }
            }
        }
    } else { //estamos en el caso de que criterio es derecha
        if (aux == 0) {
            if (indice_anterior == -1) //si estamos buscando el primer elemento 
            {
                j = 0;
                while (i < puntos_c) {
                    //será el de mayor x			
                    if (puntos_corte[2 * i] < puntos_corte[2 * j])
                        j = i;
                    i++;
                }
            } else { //calculamos el umbral
                umbral = puntos_corte[2 * indice_anterior];
                while (i != (j + 1)) //tomamos un primer valor superior a umbral
                {
                    if (puntos_corte[2 * i] > umbral)
                        j = i;
                    i++;
                }
                i = 0;
                while (i < puntos_c) {
                    //buscamos el valor superior a unbral y más cercano			
                    if (puntos_corte[2 * i] > umbral && puntos_corte[2 * i] < puntos_corte[2 * j])
                        j = i;
                    i++;
                }
            }
        } else //si la pendiente es infinita
        {
            if (indice_anterior == -1) //si estamos buscando el primer elemento 
            {
                j = 0;
                while (i < puntos_c) {
                    //será el de mayor y		
                    if (puntos_corte[2 * i + 1] < puntos_corte[2 * j + 1])
                        j = i;
                    i++;
                }
            } else { //calculamos el valor de umbral
                umbral = puntos_corte[2 * indice_anterior + 1];
                while (i != (j + 1)) //tomamos un primer valor superior a umbral
                {
                    if (puntos_corte[2 * i + 1] > umbral)
                        j = i;
                    i++;
                }
                i = 0;
                while (i < puntos_c) {
                    //buscamos el valor superior a unbral y más cercano			
                    if (puntos_corte[2 * i + 1] > umbral && puntos_corte[2 * i + 1] < puntos_corte[2 * j + 1])
                        j = i;
                    i++;
                }
            }
        }
    }
    //devolvemos el índice que hemos guardado en j
    return (j);
}

//función que calcula el vértice desde el que vamos a iniciar el barrido de la figura para hallar los waypoints

int busca_inicio(double* vertices, double dir_barrido_x, double dir_barrido_y) {

    int encontrado = 0; //indicara que ya tenemos el vértice que estamos buscando
    int i = 0; //índice
    int j = 0; //índice
    double a; //para el cálculo de la ecuación de la recta
    double b;
    double valor; //variable auxiliar

    if (dir_barrido_x == 0) // para evitar la división por cero tratamos este caso aparte
    {
        valor = vertices[0]; //inicializamos la variable valor e i
        i = 1;
        while (!(vertices[2 * j] == -1 && vertices[2 * j + 1] == -1)) {
            //buscamos el vertice de mayor x
            if (vertices[2 * j] > valor) {
                i = j + 1;
                valor = vertices[2 * j];
            }
            j++;
        }
    }
    else {
        //mientras no encontremos el vertice que estamos buscando
        while (encontrado == 0) {
            if (!(vertices[2 * i] == -1 && vertices[2 * i + 1] == -1)) //comprobamos cada vértice, eso indica que es el ultimo
            {
                //calculamos la ecuacion de la recta en vertice en el que estamos, con la pte dire_barrido
                a = dir_barrido_y / dir_barrido_x;
                b = vertices[2 * i + 1] - a * vertices[2 * i];
                j = 0; //iniciamos el índice
                //vemos si todos los otros vertices están al mismo lado de este vértice
                encontrado = 1;
                while (!(vertices[2 * j] == -1 && vertices[2 * j + 1] == -1)) {

                    //solo mantendremos el 1, es decir lo habremos hallado si ninguno de los vertices cumple la siguiente condición, lo que significa que todo los vértices están al mismo lado de la recta
                    if (vertices[2 * j + 1] - a * vertices[2 * j] - b<-0.01) //evitamos problemas con los decimales al evaluer el propio vertice
                    {
                        encontrado = 0;
                    }
                    j++;
                }
            } else
                encontrado = -1; //se ha producido un error
            i++;
        }
    }
    return (i); //devolvemos un numero que indica la posición del vertice de inicio en la lista de vértices(empieza en 1)
}

//función que calcula los vértices interiores al poĺigono, que será donde se hallarán los puntos de corte con las rectas

void nuevo_vertice(double vertice_x, double vertice_y, double vertice1_x, double vertice1_y, double vertice2_x, double vertice2_y, double ancho_barrido, double vertice_ant_x, double vertice_ant_y, double* verticesn_x, double* verticesn_y, double valor_referencia, int tipo) {

    double d; //variable para el calculo de distancias
    double vx; //vector director de la recta vertice a vertice1
    double vy;
    double p1_x; //coordenada x del punto P1
    double p1_y; //coordenada x del punto P1
    double p2_x; //coordenada y del punto P2
    double p2_y; //coordenada y del punto P2
    double l1; //variable auxiliar
    double l2; //variable auxiliar
    double a; //parametro de una ecuación de una recta
    double b; //parámetro de una ecuación de una recta
    double c;
    double aux_x; //para guardar un punto del espacio
    double aux_y;

    //calculamos la distancia (al cuadrado)del vertice2 a vertice
    d = pow(vertice_x - vertice2_x, 2) + pow(vertice_y - vertice2_y, 2);
    //calculamos el vector director de la recta que va de vertice a vertice1
    vx = vertice1_x - vertice_x;
    vy = vertice1_y - vertice_y;
    //calculamos los puntos de la recta vertice vertice1, que están a la distancia d de vertice
    a = pow(vx, 2) + pow(vy, 2);
    b = 2 * vertice1_x * vx + 2 * vertice1_y * vy - 2 * vx * vertice_x - 2 * vy*vertice_y;
    c = pow(vertice_x, 2) + pow(vertice_y, 2) - 2 * vertice1_x * vertice_x - 2 * vertice1_y * vertice_y + pow(vertice1_x, 2) + pow(vertice1_y, 2) - d;

    l1 = (-b + sqrt(pow(b, 2) - 4 * a * c)) / (2 * a);
    l2 = (-b - sqrt(pow(b, 2) - 4 * a * c)) / (2 * a);

    p1_x = vertice1_x + vx*l1;
    p1_y = vertice1_y + vy*l1;

    p2_x = vertice1_x + vx*l2;
    p2_y = vertice1_y + vy*l2;
    //nos quedamos con el punto que está más cerca del vertice1 y lo guardamos en p1
    if ((pow(vertice1_x - p1_x, 2) + pow(vertice1_y - p1_y, 2))>(pow(vertice1_x - p2_x, 2) + pow(vertice1_y - p2_y, 2))) {
        p1_x = p2_x;
        p1_y = p2_y;
    }
    //calculamos el punto medio entre p1 y el vertice2 y lo metemos en p1
    p1_x = (p1_x + vertice2_x) / 2;
    p1_y = (p1_y + vertice2_y) / 2;
    //ahora hallamos los dos puntos que distan un distancia determinada por barrido del vertice y estan en la recta bisectriz
    d = pow((ancho_barrido / 2) / ((sqrt(pow(vertice2_x - p1_x, 2) + pow(vertice2_y - p1_y, 2))) / sqrt(d)), 2); //es la distancia al cuadrado
    //calculamos el vector director de la recta que va de vertice a vertice1
    vx = p1_x - vertice_x;
    vy = p1_y - vertice_y;
    //calculamos los puntos de la recta vertice vertice1, que están a la distancia d de vertice
    a = pow(vx, 2) + pow(vy, 2);
    b = 2 * p1_x * vx + 2 * p1_y * vy - 2 * vx * vertice_x - 2 * vy*vertice_y;
    c = pow(vertice_x, 2) + pow(vertice_y, 2) - 2 * p1_x * vertice_x - 2 * p1_y * vertice_y + pow(p1_x, 2) + pow(p1_y, 2) - d;

    l1 = (-b + sqrt(pow(b, 2) - 4 * a * c)) / (2 * a);
    l2 = (-b - sqrt(pow(b, 2) - 4 * a * c)) / (2 * a);
    //guardamos el valor del punto medio
    aux_x = p1_x;
    aux_y = p1_y;

    p2_x = p1_x + vx*l2;
    p2_y = p1_y + vy*l2;

    p1_x = p1_x + vx*l1;
    p1_y = p1_y + vy*l1;
    //ahora hemos de decidir con cual de los dos valores nos quedamos
    if (valor_referencia == -1) //en este caso estamos en el primer vertice
    {
        //nos quedamos con el valor que este más cercano al punto medio que calculamos anteriormente
        if (tipo == INTERNO) {
            if ((pow(aux_x - p1_x, 2) + pow(aux_y - p1_y, 2))>(pow(aux_x - p2_x, 2) + pow(aux_y - p2_y, 2))) {
                //metemos en p1 el valor definitivo con el que nos quedamos
                p1_x = p2_x;
                p1_y = p2_y;
            }
        } else {
            if ((pow(aux_x - p1_x, 2) + pow(aux_y - p1_y, 2))<(pow(aux_x - p2_x, 2) + pow(aux_y - p2_y, 2))) {
                //metemos en p1 el valor definitivo con el que nos quedamos
                p1_x = p2_x;
                p1_y = p2_y;
            }
        }
    } else {
        //nos vamos a quedar con el valor que que haga que el segmento que forma con el vertice anterior sea paralelo al polígono
        //calculamos la pendiente buscada (es el segmento entre vertice1 y vertice)
        d = (vertice1_y - vertice_y) / (vertice1_x - vertice_x);
        aux_x = (vertice_ant_y - p1_y) / (vertice_ant_x - p1_x); //pte con p1
        aux_y = (vertice_ant_y - p2_y) / (vertice_ant_x - p2_x); //pte con p2
        //el valor bueno lo metemos en p1
        //vemos  cual se parece más a d1
        if ((absoluto(d - aux_x) > absoluto(d - aux_y))&&(vertice1_x - vertice_x != 0)&&(vertice_ant_x - p1_x != 0)&&(vertice_ant_x - p2_x) != 0) {
            p1_x = p2_x;
            p1_y = p2_y;
        } else {
            if ((vertice1_x - vertice_x == 0) || (vertice_ant_x - p1_x == 0) || (vertice_ant_x - p2_x) == 0) {
                if (vertice1_x - vertice_x == 0) {
                    if (absoluto(vertice_ant_x - p2_x) < absoluto(vertice_ant_x - p1_x)) {
                        p1_x = p2_x;
                        p1_y = p2_y;
                    }
                } else {
                    if (absoluto(vertice_ant_x - p2_x) > absoluto(vertice_ant_x - p1_x)) {
                        p1_x = p2_x;
                        p1_y = p2_y;
                    }
                }
            }
        }
    }
    //ahora tenemos en p1 el valor adecuado
    *verticesn_x = p1_x;
    *verticesn_y = p1_y;
}
//función que calcula el valor absoluto de un número

double absoluto(double x) {
    if (x < 0) return -x;
    return x;
}
//función que haya el punto de corte entre dos rectas, si una de las rectas tiene pendiente infinita se ha de pasar como la recta dos y solo la a2 que sería x=a2
void corte_2rectas(double a1, double b1, double a2, double b2, double aux, double* xc, double* yc) {
    if (aux == 0) //si las restas no tienen pendiente infinita
    {
        *xc = (b2 - b1) / (a1 - a2);
        *yc = a1 * (*xc) + b1;
    } else //si la pendiente es infinita
    {
        *xc = a2;
        *yc = a1 * (*xc) + b1;
    }
}
//función que calcula la distancia recorrida a traves de los puntos de la tabla vector desde el indice inicial al final, a esto se le suma la distancia de p1 al punto del indice inicial y la de p2 al indice final, a no ser que todas las coordenadas de estos dos puntos sean cero

double calcula_distancia_recorrida(double *vector, int indice_ini, int indice_fin, int tam, double p1_x, double p1_y, double p2_x, double p2_y) {

    double distancia = 0; //guardamos la distancia
    int i = indice_ini; //índice inicial

    if (!(p1_x == 0 && p1_y == 0 && p2_x == 0 && p2_y == 0)) //si no son todos ceros hemos de calcular dos distancia más
    {
        distancia = distancia + sqrt(pow(p1_x - vector[2 * i], 2) + pow(p1_y - vector[2 * i + 1], 2));
    }
    while (i != indice_fin) //vamos calculando la distancia entre punto y punto
    {
        distancia = distancia + sqrt(pow(vector[2 * c(i, 1, tam)] - vector[2 * i], 2) + pow(vector[2 * (c(i, 1, tam)) + 1] - vector[2 * i + 1], 2));
        i = c(i, 1, tam);
    }
    if (!(p1_x == 0 && p1_y == 0 && p2_x == 0 && p2_y == 0)) //si no son todos ceros hemos de calcular dos distancia más
    {
        distancia = distancia + sqrt(pow(p2_x - vector[2 * i], 2) + pow(p2_y - vector[2 * i + 1], 2));
    }
    return distancia;
}

//en esta función se ve si se produce un corte entre la recta formada por p1 y p2 y la formada por los dos vertices dados, y si dicho punto de corte se halla entre estos vertices. Se devuelve el punto de corte por referencia
int corte_intermedio(int vertice_1, int vertice_2, double* verticesn, double* vertices, double p1_x, double p1_y, double p2_x, double p2_y, double* xc, double* yc) {
    double a1; //para la recta de los vertices
    double b1;
    double a2; //para la racta de los p_corte
    double b2;
    int i = FALSO; //si no se encuentra nada será FALSO, es lo que devolvemos

    if (absoluto(p1_x - p2_x) < 0.001) //si es pendiente infinita
    {
        if (absoluto(vertices[2 * vertice_1] - vertices[2 * vertice_2]) > 0.01) //si los dos rectas tienen pte inf no se hace nada
        {
            a1 = (vertices[2 * vertice_1 + 1] - vertices[2 * vertice_2 + 1]) / (vertices[2 * vertice_1] - vertices[2 * vertice_2]);
            b1 = vertices[2 * vertice_1 + 1] - a1 * vertices[2 * vertice_1];
            //hallamos el punto de corte
            corte_2rectas(a1, b1, p1_x, 0, 1, xc, yc);
            // comprobamos si el corte esta en el segmento vertice1 vertice2, y si lo está i=VERDADERO
            if (a1 != 0) {
                if (((*yc <= p1_y&&*yc >= p2_y) || (*yc >= p1_y&&*yc <= p2_y))&&(((*xc <= vertices[2 * vertice_1]&&*xc >= vertices[2 * vertice_2]) || (*xc >= vertices[2 * vertice_1]&&*xc <= vertices[2 * vertice_2]))&&((*yc <= vertices[2 * vertice_1 + 1]&&*yc >= vertices[2 * vertice_2 + 1]) || (*yc >= vertices[2 * vertice_1 + 1]&&*yc <= vertices[2 * vertice_2 + 1]))))
                    i = VERDADERO;
            } else {
                if (((*yc <= p1_y&&*yc >= p2_y) || (*yc >= p1_y&&*yc <= p2_y))&&(((*xc <= vertices[2 * vertice_1]&&*xc >= vertices[2 * vertice_2]) || (*xc >= vertices[2 * vertice_1]&&*xc <= vertices[2 * vertice_2]))))
                    i = VERDADERO;
            }
        }
    } else {
        if (absoluto(vertices[2 * vertice_1] - vertices[2 * vertice_2]) > 0.01) //si los dos rectas tienen pte inf no se hace nada
        {
            //calculamos los parámetros de la recta
            a1 = (vertices[2 * vertice_1 + 1] - vertices[2 * vertice_2 + 1]) / (vertices[2 * vertice_1] - vertices[2 * vertice_2]);
            b1 = vertices[2 * vertice_1 + 1] - a1 * vertices[2 * vertice_1];
            a2 = (p1_y - p2_y) / (p1_x - p2_x);
            b2 = p1_y - a2*p1_x;

            if (a1 != a2) //sino no habría corte
            {
                corte_2rectas(a1, b1, a2, b2, 0, xc, yc);
                // comprobamos si el corte esta en el segmento vertice1 vertice2, y si lo está i=VERDADERO
                //con esta seríe de if evitamos ciertas comprobaciones que no tienen sentido para ciertas pendientes
                if (a1 != 0 && a2 != 0) {
                    if (((*xc <= p1_x&&*xc >= p2_x) || (*xc >= p1_x&&*xc <= p2_x))&&((*yc <= p1_y&&*yc >= p2_y) || (*yc >= p1_y&&*yc <= p2_y))&&(((*xc <= vertices[2 * vertice_1]&&*xc >= vertices[2 * vertice_2]) || (*xc >= vertices[2 * vertice_1]&&*xc <= vertices[2 * vertice_2]))&&((*yc <= vertices[2 * vertice_1 + 1]&&*yc >= vertices[2 * vertice_2 + 1]) || (*yc >= vertices[2 * vertice_1 + 1]&&*yc <= vertices[2 * vertice_2 + 1]))))
                        i = VERDADERO;
                } else {
                    if (a1 == 0 && a2 != 0) {
                        if (((*xc <= p1_x&&*xc >= p2_x) || (*xc >= p1_x&&*xc <= p2_x))&&((*yc <= p1_y&&*yc >= p2_y) || (*yc >= p1_y&&*yc <= p2_y))&&(((*xc <= vertices[2 * vertice_1]&&*xc >= vertices[2 * vertice_2]) || (*xc >= vertices[2 * vertice_1]&&*xc <= vertices[2 * vertice_2]))))
                            i = VERDADERO;
                    }
                    if (a1 != 0 && a2 == 0) {
                        if (((*xc <= p1_x&&*xc >= p2_x) || (*xc >= p1_x&&*xc <= p2_x))&&(((*xc <= vertices[2 * vertice_1]&&*xc >= vertices[2 * vertice_2]) || (*xc >= vertices[2 * vertice_1]&&*xc <= vertices[2 * vertice_2]))&&((*yc <= vertices[2 * vertice_1 + 1]&&*yc >= vertices[2 * vertice_2 + 1]) || (*yc >= vertices[2 * vertice_1 + 1]&&*yc <= vertices[2 * vertice_2 + 1]))))
                            i = VERDADERO;
                    }
                }
            }
        } else {
            //solo calculamos los parámetros de una de las rectas	
            a2 = (p1_y - p2_y) / (p1_x - p2_x);
            b2 = p1_y - a2*p1_x;

            corte_2rectas(a2, b2, vertices[2 * vertice_1], 0, 1, xc, yc);
            // comprobamos si el corte esta en el segmento vertice1 vertice2
            if (a2 != 0) {
                if (((*xc <= p1_x&&*xc >= p2_x) || (*xc >= p1_x&&*xc <= p2_x))&&((*yc <= p1_y&&*yc >= p2_y) || (*yc >= p1_y&&*yc <= p2_y))&&((*yc <= vertices[2 * vertice_1 + 1]&&*yc >= vertices[2 * vertice_2 + 1]) || (*yc >= vertices[2 * vertice_1 + 1]&&*yc <= vertices[2 * vertice_2 + 1])))
                    i = VERDADERO;
            } else {
                if (((*xc <= p1_x&&*xc >= p2_x) || (*xc >= p1_x&&*xc <= p2_x))&&((*yc <= vertices[2 * vertice_1 + 1]&&*yc >= vertices[2 * vertice_2 + 1]) || (*yc >= vertices[2 * vertice_1 + 1]&&*yc <= vertices[2 * vertice_2 + 1])))
                    i = VERDADERO;
            }
        }
    }

    return (i);
}


//incluirá puntos intermedios para no salirnos del área permitida, y se los incluye para recorrer el poligono exterior

void trazado_intermedio(int vertice_1a, int vertice_1b, int vertice_2a, int vertice_2b, double* verticesn, double* vertices, double p1_x, double p1_y, double p2_x, double p2_y, double* waypoints, int* j, int n_vertices) {

    int v1_mayor; //variable para guardar uno de los vértices
    int v1_menor; //variable para guardar uno de los vértices
    int v2_mayor; //variable para guardar uno de los vértices
    int v2_menor; //variable para guardar uno de los vértices
    int v; //variable auxiliar
    //	int i=0;	//índice
    int aux = FALSO; //variable auxiliar
    double d1; //guardará la distacia que se recorre yendo por uno de los lados del polígono
    double d2; //guardará la distacia que se recorre yendo por uno de los lados del polígono
    double xc; //contendrá un punto de corte
    double yc; //contendrá un punto de corte
    //inicializamos las variables con la información de los vértices
    if (c(vertice_1a, 1, n_vertices) == vertice_1b) {
        v1_mayor = vertice_1b;
        v1_menor = vertice_1a;
    } else {
        v1_menor = vertice_1b;
        v1_mayor = vertice_1a;
    }
    if (c(vertice_2a, 1, n_vertices) == vertice_2b) {
        v2_mayor = vertice_2b;
        v2_menor = vertice_2a;
    } else {
        v2_menor = vertice_2b;
        v2_mayor = vertice_2a;
    }
    //si vamos incrementando el número de vértice, al recorrer el polígono
    d1 = calcula_distancia_recorrida(vertices, v1_mayor, v2_menor, n_vertices, p1_x, p1_y, p2_x, p2_y);
    //si vamos decrementando el número de vértice, al recorrer el polígono
    d2 = calcula_distancia_recorrida(vertices, v2_mayor, v1_menor, n_vertices, p2_x, p2_y, p1_x, p1_y);
    //miramos si corta con el propio segmento del punto de corte primero, si corta tendremos que añadir puntos intermedios para que no nos salgamos del polígono
    if (corte_intermedio(vertice_1a, vertice_1b, verticesn, vertices, p1_x, p1_y, p2_x, p2_y, &xc, &yc) == VERDADERO) {
        //tenemos que mirar por que lado vamos
        //decidimos con la distancia
        if (d1 < d2) {
            //inclumos vertices entre los dos puntos de corte
            incluye_vertices(waypoints, verticesn, v1_mayor, v2_menor, j, n_vertices, INCREMENTO);
        } else { //inclumos vertices entre los dos puntos de corte
            incluye_vertices(waypoints, verticesn, v1_menor, v2_mayor, j, n_vertices, DECREMENTO);
        }
    } else {
        //miramos si corta con algunos de los segmentos incrementando el vértice, ahora no es el vertice del punto de corte
        v = v1_mayor;
        //hasta que los miremos todos
        while (v != v1_menor && aux == FALSO) {
            if (corte_intermedio(v, c(v, 1, n_vertices), verticesn, vertices, p1_x, p1_y, p2_x, p2_y, &xc, &yc) == VERDADERO) {
                //tenemos que mirar por que lado vamos
                //decidimos con la distancia
                if (d1 < d2) {
                    //inclumos vertices entre los dos puntos de corte
                    incluye_vertices(waypoints, verticesn, c(v1_mayor, 1, n_vertices), v2_menor, j, n_vertices, INCREMENTO);
                } else { //inclumos vertices entre los dos puntos de corte
                    incluye_vertices(waypoints, verticesn, c(v1_menor, -1, n_vertices), v2_mayor, j, n_vertices, DECREMENTO);
                }
                aux = VERDADERO;
            }
            v = c(v, 1, n_vertices);
        }
    }
}
//función que nos vas facilitar el uso de los indices en las tablas, tendremos tablas circulares
int c(int indice, int modificacion, int n_vertices) {
    int i;
    if (modificacion > 0) //indica si estamo incrementando el índice o por el contrario disminuyendo
    {
        if (indice == (n_vertices - 1)) {
            i = 0;
        } else
            i = indice + 1;
    } else {
        if (indice == 0) {
            i = n_vertices - 1;
        } else
            i = indice - 1;
    }
    return (i);
}

//función que incluye a partir de waypoints de j los vertices entre v1 y v2 de la tabla verticesn, incluidos estos. Incluye los valores desde v1 a v2, incrementando en el indice o de crementando segun sentido
void incluye_vertices(double* waypoints, double* verticesn, int v1, int v2, int* j, int n_vertices, int sentido) {
    //	int i=0;	//indice
    int vertice = v1; //variable axiliar
    if (sentido == INCREMENTO) { //metemos en waypoints los vertices solicitados
        while (vertice != c(v2, 1, n_vertices)) {
            waypoints[*j] = verticesn[2 * vertice];
            waypoints[*j + 1] = verticesn[2 * vertice + 1];
            *j = *j + 2;
            vertice = c(vertice, 1, n_vertices);
        }
    } else { //metemos en waypoints los vertices solicitados
        while (vertice != c(v2, -1, n_vertices)) {
            waypoints[*j] = verticesn[2 * vertice];
            waypoints[*j + 1] = verticesn[2 * vertice + 1];
            *j = *j + 2;
            vertice = c(vertice, -1, n_vertices);
        }
    }
}

//función que busca el punto más cercano al dado, de entre los de la tabla
int busca_cercano(double* puntos_corte, int puntos_c, double p1_x, double p1_y) {
    int i = 0;
    int indice = 0;
    double distancia = sqrt(pow(p1_x - puntos_corte[2 * i], 2) + pow(p1_y - puntos_corte[2 * i + 1], 2));

    for (i = 0; i < puntos_c; i++) { //vamos calculando la distancia de cada punto
        if (sqrt(pow(p1_x - puntos_corte[2 * i], 2) + pow(p1_y - puntos_corte[2 * i + 1], 2)) < distancia) {
            indice = i;
            distancia = sqrt(pow(p1_x - puntos_corte[2 * i], 2) + pow(p1_y - puntos_corte[2 * i + 1], 2));
        }
    }
    return (indice);
}

//función que busca el punto más lejano al dado, de entre los de la tabla
int busca_lejano(double* puntos_corte, int puntos_c, double p1_x, double p1_y) {
    int i = 0;
    int indice = 0;
    double distancia = 0;
    for (i = 0; i < puntos_c; i++) { //vamos calculando la distancia de cada punto
        if (sqrt(pow(p1_x - puntos_corte[2 * i], 2) + pow(p1_y - puntos_corte[2 * i + 1], 2)) > distancia) {
            indice = i;
            distancia = sqrt(pow(p1_x - puntos_corte[2 * i], 2) + pow(p1_y - puntos_corte[2 * i + 1], 2));
        }
    }
    return (indice);
}

//función que incluye una serie de waypoints para no evitar una serie de polígonos interiores los cuales indican zonas por las que no se pueden pasar
int trazado_intermedio_poligono_interno(double* waypoints, double* waypoints1, double* vertices_int, double* verticesn_int, int n_vertices1, int j) {
    int t = 0; //índice para los nuevos waypoints
    int v = 0; //recorreremos los distintos vertices con esta variable
    int i = 0; //índice
    int n = 0; //variable auxiliar
    double xc; //puntos de corte
    double yc; //puntos de corte
    int puntos_c; //número de puntos de corte
    int puntos_c1; //variable relacionada con el número de puntos de corte
    int vertices_puntos_c[NUM_VERTEX]; //vertices colindantes a cada punto de corte
    double puntos_corte[NUM_VERTEX]; //contendrá las coordenadas de los puntos de corte
    int v1_mayor; //variable para guardar uno de los vértices		
    int v1_menor; //variable para guardar uno de los vértices
    int v2_mayor; //variable para guardar uno de los vértices
    int v2_menor; //variable para guardar uno de los vértices
    double d1; //distancia
    double d2; //distancia
    int aux1 = 0; //variable auxiliar
    int aux2 = 0; //variable auxiliar
    //para cada uno se los segmentos formado por los waypoints buscamos puntos de corte
    t = 0;
    for (i = 0; i < (j / 2) - 1; i++) {
        //para cada segmento miramos si corta con alguno de los vertices del poligono interior
        v = 0;
        puntos_c = 0;
        //miramos todos los vértices
        while (v < n_vertices1) { //comprobamos si hay corte
            if (corte_intermedio(v, c(v, 1, n_vertices1), verticesn_int, verticesn_int, waypoints[2 * i], waypoints[2 * i + 1], waypoints[2 * i + 2], waypoints[2 * i + 3], &xc, &yc) == VERDADERO) {
                //guardamos el punto de cprte y los vértices colindantes
                puntos_corte[2 * puntos_c] = xc;
                puntos_corte[2 * puntos_c + 1] = yc;
                vertices_puntos_c[2 * puntos_c] = v;
                vertices_puntos_c[2 * puntos_c + 1] = c(v, 1, n_vertices1);

                puntos_c++;
            }
            v++;
            
        }
        //guardamos un waypoint en la nueva tabla
        waypoints1[t] = waypoints[2 * i];
        waypoints1[t + 1] = waypoints[2 * i + 1];
        t = t + 2;
        if (puntos_c > 0) {
            //si se ha encontrado cortes buscamos el más cercano, al primer vertice de los waypoints,
            aux1 = busca_cercano(puntos_corte, puntos_c, waypoints[2 * i], waypoints[2 * i + 1]);
            aux2 = busca_lejano(puntos_corte, puntos_c, waypoints[2 * i], waypoints[2 * i + 1]);

            //inclumos el 1er punto de corte
            waypoints1[t] = puntos_corte[2 * aux1];
            waypoints1[t + 1] = puntos_corte[2 * aux1 + 1];
            t = t + 2;
            
            //vemos por donde se va por el camino más corto para llegar al más lejano
            if (c(vertices_puntos_c[2 * aux1], 1, n_vertices1) == vertices_puntos_c[2 * aux1 + 1]) {
                v1_mayor = vertices_puntos_c[2 * aux1 + 1];
                v1_menor = vertices_puntos_c[2 * aux1];
            } else {
                v1_menor = vertices_puntos_c[2 * aux1 + 1];
                v1_mayor = vertices_puntos_c[2 * aux1];
            }
            if (c(vertices_puntos_c[2 * aux2], 1, n_vertices1) == vertices_puntos_c[2 * aux2 + 1]) {
                v2_mayor = vertices_puntos_c[2 * aux2 + 1];
                v2_menor = vertices_puntos_c[2 * aux2];
            } else {
                v2_menor = vertices_puntos_c[2 * aux2 + 1];
                v2_mayor = vertices_puntos_c[2 * aux2];
            }
            //calculamos la distancia incrementando el índice
            d1 = calcula_distancia_recorrida(verticesn_int, v1_mayor, v2_menor, n_vertices1, puntos_corte[2 * aux1], puntos_corte[2 * aux1 + 1], puntos_corte[2 * aux2], puntos_corte[2 * aux2 + 1]);
            //calculamos la distancia decrementando el índice
            d2 = calcula_distancia_recorrida(verticesn_int, v2_mayor, v1_menor, n_vertices1, puntos_corte[2 * aux2], puntos_corte[2 * aux2 + 1], puntos_corte[2 * aux1], puntos_corte[2 * aux1 + 1]);
            //añadimos puntos intermedios
            
            //n nos valdrá para saber si para ir de aux1 aux2 hay que sumar o restar
            if (c(aux1, -1, puntos_c) == aux2)
                n = 1;
            else
                n = -1;
            puntos_c1 = puntos_c;

            //según la distancia seguimos un camino u otro
            if (d1 < d2) {
                while (puntos_c1 > 1) {
                    incluye_vertices(waypoints1, verticesn_int, vertices_puntos_c[2 * aux1 + 1], vertices_puntos_c[2 * (c(aux1, n, puntos_c))], &t, n_vertices1, INCREMENTO);
                    puntos_c1--;
                    if (puntos_c1 != 1) {
                        aux1 = c(aux1, n, puntos_c);
                        waypoints1[t] = puntos_corte[2 * aux1];
                        waypoints1[t + 1] = puntos_corte[2 * aux1 + 1];
                        t = t + 2;

                        aux1 = c(aux1, n, puntos_c);
                        waypoints1[t] = puntos_corte[2 * aux1];
                        waypoints1[t + 1] = puntos_corte[2 * aux1 + 1];
                        t = t + 2;

                        puntos_c1--;
                    }
                }
            } else {
                while (puntos_c1 > 1) {

                    incluye_vertices(waypoints1, verticesn_int, vertices_puntos_c[2 * aux1], vertices_puntos_c[2 * c(aux1, n, puntos_c) + 1], &t, n_vertices1, DECREMENTO);

                    puntos_c1--;

                    if (puntos_c1 != 1) {
                        aux1 = c(aux1, n, puntos_c);
                        waypoints1[t] = puntos_corte[2 * aux1];
                        waypoints1[t + 1] = puntos_corte[2 * aux1 + 1];
                        t = t + 2;

                        aux1 = c(aux1, n, puntos_c);
                        waypoints1[t] = puntos_corte[2 * aux1];
                        waypoints1[t + 1] = puntos_corte[2 * aux1 + 1];
                        t = t + 2;

                        puntos_c1--;
                    }
                }
            }

            //incluimos el ult pto de corte

            //inclumos el 1er punto de corte
            waypoints1[t] = puntos_corte[2 * aux2];
            waypoints1[t + 1] = puntos_corte[2 * aux2 + 1];
            t = t + 2;
        }
    }
    waypoints1[t] = waypoints[2 * i];
    waypoints1[t + 1] = waypoints[2 * i + 1];
    t = t + 2;
    //ahora copiamos  en waypoints waypoints1
    i = 0;

    while (i < t) {
        waypoints[i] = waypoints1[i];
        waypoints[i + 1] = waypoints1[i + 1];

        i = i + 2;
    }
    return (t); //devolvemos el índice
}

//funcion que a partir del poligono vertices obtiene uno interior o exterior a este según al valor de tipo. El poligono obtenido se almacena en verticesn y tendrá una separación con el original de ancho_barrido/2.
void calcula_verticesn(double* vertices, double* verticesn, int vertice_inicio, int n_vertices, double ancho_barrido, int tipo) {
    int vertice_actual; //nos valdrá para ver cuando hemos obtenido todos los nuevo vertices

    //El calculo del primer vertice se realiza aparte

    if (vertice_inicio != 1 && vertice_inicio != n_vertices) {
        nuevo_vertice(vertices[2 * (vertice_inicio - 1)], vertices[2 * (vertice_inicio - 1) + 1], vertices[2 * (vertice_inicio - 1) - 2], vertices[2 * (vertice_inicio - 1) - 1], vertices[2 * (vertice_inicio - 1) + 2], vertices[2 * (vertice_inicio - 1) + 3], ancho_barrido, 0, 0, &verticesn[2 * (vertice_inicio - 1)], &verticesn[2 * (vertice_inicio - 1) + 1], -1, tipo);
    } else { //aqui estemos en un vértice del extremo de la tabla y tenemos que tratarlo de forma distinta
        if (vertice_inicio != n_vertices) {
            nuevo_vertice(vertices[2 * (vertice_inicio - 1)], vertices[2 * (vertice_inicio - 1) + 1], vertices[2 * (n_vertices - 1)], vertices[2 * (n_vertices - 1) + 1], vertices[2 * (vertice_inicio - 1) + 2], vertices[2 * (vertice_inicio - 1) + 3], ancho_barrido, 0, 0, &verticesn[2 * (vertice_inicio - 1)], &verticesn[2 * (vertice_inicio - 1) + 1], -1, tipo);
        } else {
            nuevo_vertice(vertices[2 * (vertice_inicio - 1)], vertices[2 * (vertice_inicio - 1) + 1], vertices[2 * (vertice_inicio - 1) - 2], vertices[2 * (vertice_inicio - 1) - 1], vertices[0], vertices[1], ancho_barrido, 0, 0, &verticesn[2 * (vertice_inicio - 1)], &verticesn[2 * (vertice_inicio - 1) + 1], -1, tipo);
        }
    }
    //para el resto de vértices
    
    //incrementamos el numero de vertice
    if (vertice_inicio < n_vertices)
        vertice_actual = vertice_inicio + 1;
    else
        vertice_actual = 1;

    //ahora calculamos el resto de vertices, hasta que llegurmos de nuevo al inicial
    while (vertice_actual != vertice_inicio) {
        if (vertice_actual != 1 && vertice_actual != n_vertices) {
            nuevo_vertice(vertices[2 * (vertice_actual - 1)], vertices[2 * (vertice_actual - 1) + 1], vertices[2 * (vertice_actual - 1) - 2], vertices[2 * (vertice_actual - 1) - 1], vertices[2 * (vertice_actual - 1) + 2], vertices[2 * (vertice_actual - 1) + 3], ancho_barrido, verticesn[2 * (vertice_actual - 1) - 2], verticesn[2 * (vertice_actual - 1) - 1], &verticesn[2 * (vertice_actual - 1)], &verticesn[2 * (vertice_actual - 1) + 1], 0, tipo);
        } else { //aqui estemos en un vértice del extremo de la tabla y tenemos que tratarlo de forma distinta
            if (vertice_actual != n_vertices) {
                nuevo_vertice(vertices[2 * (vertice_actual - 1)], vertices[2 * (vertice_actual - 1) + 1], vertices[2 * (n_vertices - 1)], vertices[2 * (n_vertices - 1) + 1], vertices[2 * (vertice_actual - 1) + 2], vertices[2 * (vertice_actual - 1) + 3], ancho_barrido, verticesn[2 * (n_vertices - 1)], verticesn[2 * (n_vertices - 1) + 1], &verticesn[2 * (vertice_actual - 1)], &verticesn[2 * (vertice_actual - 1) + 1], 0, tipo);
            } else {
                nuevo_vertice(vertices[2 * (vertice_actual - 1)], vertices[2 * (vertice_actual - 1) + 1], vertices[2 * (vertice_actual - 1) - 2], vertices[2 * (vertice_actual - 1) - 1], vertices[0], vertices[1], ancho_barrido, verticesn[2 * (vertice_actual - 1) - 2], verticesn[2 * (vertice_actual - 1) - 1], &verticesn[2 * (vertice_actual - 1)], &verticesn[2 * (vertice_actual - 1) + 1], 0, tipo);
            }
        }
        if (vertice_actual < n_vertices)
            vertice_actual = vertice_actual + 1;
        else
            vertice_actual = 1;
    }
}

/* Compute the width of the FOV projection*/
double compute_camera_fov_width(int uav_id, double uav_altitude, double ground_altitude, double camera_angle) {
    /*
            typedef struct
            {
                    double x;
                    double y;
            }UTM_POINTS;

            UTM_POINTS p[4];
            double width = 0.0;
            double vector[3];
            double vectorUAV[3];
            double vectorWorld[3];
            double x_c,y_c,z_c;
            double u,v;
            double norm;
            double lambda;
	
            ImageParameters imageParam;
            InternalCameraCalibration camCalib;
            CameraPosHead posHed;

            memset(&imageParam, 0, (sizeof(ImageParameters)));
            memset(&camCalib, 0, (sizeof(InternalCameraCalibration)));
            memset(&posHed, 0, (sizeof(CameraPosHead)));

            switch(uav_id)
            {
                    case TUB2_ID:
                            imageParam.width = 384;
                            imageParam.height = 288;
                            camCalib.u0 = 179.9591;
                            camCalib.v0 = 112.6779;
                            camCalib.alpha_u = 494.4553;
                            camCalib.alpha_v = 492.6934;
                            camCalib.skew = 0.0017;
                            camCalib.radialDistortion[0] = -0.3844;
                            camCalib.radialDistortion[1] = 0.3066;
                            camCalib.radialDistortion[2] = 0.0059;
                            break;
                    case TUB1_ID:
                    case TUB3_ID:
                            imageParam.width = 384;
                            imageParam.height = 288;
                            camCalib.u0 = 199.9948;
                            camCalib.v0 = 116.6379;
                            camCalib.alpha_u = 551.3304;
                            camCalib.alpha_v = 549.3181;
                            camCalib.skew = 0.0026;
                            camCalib.radialDistortion[0] = -0.3845;
                            camCalib.radialDistortion[1] = 0.3674;
                            camCalib.radialDistortion[2] = 0.0014;
                            break;
                    case FC_ID:
                            imageParam.width = 320;
                            imageParam.height = 240;
                            camCalib.u0 = 148.6924;
                            camCalib.v0 = 86.4043;
                            camCalib.alpha_u = 462.0314;
                            camCalib.alpha_v = 489.0906;
                            camCalib.skew = -0.0117;
                            camCalib.radialDistortion[0] = -0.3668;
                            camCalib.radialDistortion[1] = 0.3435;
                            camCalib.radialDistortion[2] = 0.0020;
                            break;
                    case GCN1_ID:
                            imageParam.width = 640;
                            imageParam.height = 480;
                            camCalib.u0 = 347.1712;
                            camCalib.v0 = 281.7029;
                            camCalib.alpha_u = 1140.4620;
                            camCalib.alpha_v = 1072.6803;
                            camCalib.skew = 0.0064;
                            camCalib.radialDistortion[0] = -0.3823;
                            camCalib.radialDistortion[1] = 0.3914;
                            camCalib.radialDistortion[2] = -0.0011;
                            break;
                    case GCN2_ID:
                            imageParam.width = 640;
                            imageParam.height = 480;
                            camCalib.u0 = 325.0515;
                            camCalib.v0 = 226.5261;
                            camCalib.alpha_u = 996.2291;
                            camCalib.alpha_v = 939.8955;
                            camCalib.skew = 0.0061;
                            camCalib.radialDistortion[0] = -0.4102;
                            camCalib.radialDistortion[1] = 0.6072;
                            camCalib.radialDistortion[2] = -0.0022;
                            break;
                    default:
                            imageParam.width = 384;
                            imageParam.height = 288;
                            camCalib.u0 = 179.9591;
                            camCalib.v0 = 112.6779;
                            camCalib.alpha_u = 494.4553;
                            camCalib.alpha_v = 492.6934;
                            camCalib.skew = 0.0017;
                            camCalib.radialDistortion[0] = -0.3844;
                            camCalib.radialDistortion[1] = 0.3066;
                            camCalib.radialDistortion[2] = 0.0059;
                            break;
            }
            posHed.rotation1[0] = (float)0.0;
            posHed.rotation1[1] = (float)(-sin(camera_angle));
            posHed.rotation1[2] = (float)(cos(camera_angle));
            posHed.rotation1[3] = (float)(1.0);
            posHed.rotation1[4] = (float)(0.0);
            posHed.rotation1[5] = (float)(0.0);
            posHed.rotation1[6] = (float)(0.0);
            posHed.rotation1[7] = (float)(cos(camera_angle));
            posHed.rotation1[8] = (float)(sin(camera_angle));
	
            posHed.rotation2[0] = 1.0;
            posHed.rotation2[1] = 0.0;
            posHed.rotation2[2] = 0.0;
            posHed.rotation2[3] = 0.0;
            posHed.rotation2[4] = 1.0;
            posHed.rotation2[5] = 0.0;
            posHed.rotation2[6] = 0.0;
            posHed.rotation2[7] = 0.0;
            posHed.rotation2[8] = 1.0;

            char dummy[20];
            LLtoUTM(23, posHed.latitude, posHed.longitude, y_c, x_c, dummy);
            z_c = uav_altitude-ground_altitude;

            //Compute vectors 
            vector[2]=1.0;
            // First ray : //
            u=imageParam.width;
            v=0.0;
            vector[0] = u/camCalib.alpha_u - camCalib.u0/camCalib.alpha_v - (v*camCalib.skew)/(camCalib.alpha_u*camCalib.alpha_v) + (camCalib.skew*camCalib.v0)/(camCalib.alpha_u*camCalib.alpha_v);
            vector[1] = (v - camCalib.v0)/camCalib.alpha_v;
            norm=sqrt(vector[0]*vector[0]+vector[1]*vector[1]+vector[2]*vector[2]);
            vector[0]=vector[0]/norm;
            vector[1]=vector[1]/norm;
            vector[2]=vector[2]/norm;
            //Rotate to UAV frame
            vectorUAV[0]=posHed.rotation1[0]*vector[0]+posHed.rotation1[1]*vector[1]+posHed.rotation1[2]*vector[2];
            vectorUAV[1]=posHed.rotation1[3]*vector[0]+posHed.rotation1[4]*vector[1]+posHed.rotation1[5]*vector[2];
            vectorUAV[2]=posHed.rotation1[6]*vector[0]+posHed.rotation1[7]*vector[1]+posHed.rotation1[8]*vector[2];
            //Rotate to World Frame
            vectorWorld[0]=posHed.rotation2[0]*vectorUAV[0]+posHed.rotation2[1]*vectorUAV[1]+posHed.rotation2[2]*vectorUAV[2];
            vectorWorld[1]=posHed.rotation2[3]*vectorUAV[0]+posHed.rotation2[4]*vectorUAV[1]+posHed.rotation2[5]*vectorUAV[2];
            vectorWorld[2]=posHed.rotation2[6]*vectorUAV[0]+posHed.rotation2[7]*vectorUAV[1]+posHed.rotation2[8]*vectorUAV[2];
            //Project
            if(vectorWorld[2]>=0.0)
            {
                    vectorWorld[2]=-0.087;//??
            }
            lambda = -z_c/vectorWorld[2];
            p[0].x = x_c+lambda*vectorWorld[0];
            p[0].y = y_c+lambda*vectorWorld[1];

            //Compute vectors 
            vector[2]=1.0;
            // Second ray : //
            u=imageParam.width;
            v=imageParam.height;
            vector[0] = u/camCalib.alpha_u - camCalib.u0/camCalib.alpha_v - (v*camCalib.skew)/(camCalib.alpha_u*camCalib.alpha_v) + (camCalib.skew*camCalib.v0)/(camCalib.alpha_u*camCalib.alpha_v);
            vector[1] = (v - camCalib.v0)/camCalib.alpha_v;
            norm=sqrt(vector[0]*vector[0]+vector[1]*vector[1]+vector[2]*vector[2]);
            vector[0]=vector[0]/norm;
            vector[1]=vector[1]/norm;
            vector[2]=vector[2]/norm;
            //Rotate to UAV frame
            vectorUAV[0]=posHed.rotation1[0]*vector[0]+posHed.rotation1[1]*vector[1]+posHed.rotation1[2]*vector[2];
            vectorUAV[1]=posHed.rotation1[3]*vector[0]+posHed.rotation1[4]*vector[1]+posHed.rotation1[5]*vector[2];
            vectorUAV[2]=posHed.rotation1[6]*vector[0]+posHed.rotation1[7]*vector[1]+posHed.rotation1[8]*vector[2];
            //Rotate to World Frame
            vectorWorld[0]=posHed.rotation2[0]*vectorUAV[0]+posHed.rotation2[1]*vectorUAV[1]+posHed.rotation2[2]*vectorUAV[2];
            vectorWorld[1]=posHed.rotation2[3]*vectorUAV[0]+posHed.rotation2[4]*vectorUAV[1]+posHed.rotation2[5]*vectorUAV[2];
            vectorWorld[2]=posHed.rotation2[6]*vectorUAV[0]+posHed.rotation2[7]*vectorUAV[1]+posHed.rotation2[8]*vectorUAV[2];
            //Project
            if(vectorWorld[2]>=0.0)
            {
                    vectorWorld[2]=-0.087;//??
            }
            lambda = -z_c/vectorWorld[2];
            p[1].x = x_c+lambda*vectorWorld[0];
            p[1].y = y_c+lambda*vectorWorld[1];

            //Compute vectors 
            vector[2]=1.0;
            // Third ray : //
            u=0.0;
            v=imageParam.height;
            vector[0] = u/camCalib.alpha_u - camCalib.u0/camCalib.alpha_v - (v*camCalib.skew)/(camCalib.alpha_u*camCalib.alpha_v) + (camCalib.skew*camCalib.v0)/(camCalib.alpha_u*camCalib.alpha_v);
            vector[1] = (v - camCalib.v0)/camCalib.alpha_v;
            norm=sqrt(vector[0]*vector[0]+vector[1]*vector[1]+vector[2]*vector[2]);
            vector[0]=vector[0]/norm;
            vector[1]=vector[1]/norm;
            vector[2]=vector[2]/norm;
            //Rotate to UAV frame
            vectorUAV[0]=posHed.rotation1[0]*vector[0]+posHed.rotation1[1]*vector[1]+posHed.rotation1[2]*vector[2];
            vectorUAV[1]=posHed.rotation1[3]*vector[0]+posHed.rotation1[4]*vector[1]+posHed.rotation1[5]*vector[2];
            vectorUAV[2]=posHed.rotation1[6]*vector[0]+posHed.rotation1[7]*vector[1]+posHed.rotation1[8]*vector[2];
            //Rotate to World Frame
            vectorWorld[0]=posHed.rotation2[0]*vectorUAV[0]+posHed.rotation2[1]*vectorUAV[1]+posHed.rotation2[2]*vectorUAV[2];
            vectorWorld[1]=posHed.rotation2[3]*vectorUAV[0]+posHed.rotation2[4]*vectorUAV[1]+posHed.rotation2[5]*vectorUAV[2];
            vectorWorld[2]=posHed.rotation2[6]*vectorUAV[0]+posHed.rotation2[7]*vectorUAV[1]+posHed.rotation2[8]*vectorUAV[2];
            //Project
            if(vectorWorld[2]>=0.0)
            {
                    vectorWorld[2]=-0.087;//??
            }
            lambda = -z_c/vectorWorld[2];
            p[2].x = x_c+lambda*vectorWorld[0];
            p[2].y = y_c+lambda*vectorWorld[1];

            //Compute vectors 
            vector[2]=1.0;
            // Fourth ray : //
            u=0.0;
            v=0.0;
            vector[0] = u/camCalib.alpha_u - camCalib.u0/camCalib.alpha_v - (v*camCalib.skew)/(camCalib.alpha_u*camCalib.alpha_v) + (camCalib.skew*camCalib.v0)/(camCalib.alpha_u*camCalib.alpha_v);
            vector[1] = (v - camCalib.v0)/camCalib.alpha_v;
            norm=sqrt(vector[0]*vector[0]+vector[1]*vector[1]+vector[2]*vector[2]);
            vector[0]=vector[0]/norm;
            vector[1]=vector[1]/norm;
            vector[2]=vector[2]/norm;
            //Rotate to UAV frame
            vectorUAV[0]=posHed.rotation1[0]*vector[0]+posHed.rotation1[1]*vector[1]+posHed.rotation1[2]*vector[2];
            vectorUAV[1]=posHed.rotation1[3]*vector[0]+posHed.rotation1[4]*vector[1]+posHed.rotation1[5]*vector[2];
            vectorUAV[2]=posHed.rotation1[6]*vector[0]+posHed.rotation1[7]*vector[1]+posHed.rotation1[8]*vector[2];
            //Rotate to World Frame
            vectorWorld[0]=posHed.rotation2[0]*vectorUAV[0]+posHed.rotation2[1]*vectorUAV[1]+posHed.rotation2[2]*vectorUAV[2];
            vectorWorld[1]=posHed.rotation2[3]*vectorUAV[0]+posHed.rotation2[4]*vectorUAV[1]+posHed.rotation2[5]*vectorUAV[2];
            vectorWorld[2]=posHed.rotation2[6]*vectorUAV[0]+posHed.rotation2[7]*vectorUAV[1]+posHed.rotation2[8]*vectorUAV[2];
            //Project
            if(vectorWorld[2]>=0.0)
            {
                    vectorWorld[2]=-0.087;//??
            }
            lambda = -z_c/vectorWorld[2];
            p[3].x = x_c+lambda*vectorWorld[0];
            p[3].y = y_c+lambda*vectorWorld[1];

            width = sqrt ( (p[2].y-p[1].y)*(p[2].y-p[1].y) + (p[2].x-p[1].x)*(p[2].x-p[1].x)  );

     */
    //	return(120.0);
    return (1.57);
}

/* Compute the coordinates of the corners of the projection */
double compute_width(int uav_id, double altitude, double ground) {
    double delta_xL1, delta_xL2;
    float aperture, gamma, delta, camera_angle, heading;
    double L1, L2;
    double delta_z;
    double width;

    delta_z = altitude - ground;

    camera_angle = 90.0;
    aperture = 30.0;
    heading = 0.0;
    gamma = (PI / 180.0)*(heading - aperture);
    delta = (PI / 180.0)*(heading + aperture);

    L1 = delta_z / tan((PI / 180.0)*(camera_angle + aperture));
    L2 = delta_z / tan((PI / 180.0)*(camera_angle - aperture));
    delta_xL1 = (delta_z * tan((PI / 180.0) * aperture)) / sin((PI / 180.0)*(camera_angle + aperture));
    delta_xL2 = (delta_z * tan((PI / 180.0) * aperture)) / sin((PI / 180.0)*(camera_angle - aperture));
    width = 2 * delta_xL1;

    return (width);
}
