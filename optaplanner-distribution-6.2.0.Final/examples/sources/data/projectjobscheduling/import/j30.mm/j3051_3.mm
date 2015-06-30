************************************************************************
file with basedata            : mf51_.bas
initial value random generator: 1337428092
************************************************************************
projects                      :  1
jobs (incl. supersource/sink ):  32
horizon                       :  250
RESOURCES
  - renewable                 :  2   R
  - nonrenewable              :  2   N
  - doubly constrained        :  0   D
************************************************************************
PROJECT INFORMATION:
pronr.  #jobs rel.date duedate tardcost  MPM-Time
    1     30      0       27       11       27
************************************************************************
PRECEDENCE RELATIONS:
jobnr.    #modes  #successors   successors
   1        1          3           2   3   4
   2        3          3           5  10  11
   3        3          3           6   7  14
   4        3          2           5  10
   5        3          3           8  12  13
   6        3          2          19  26
   7        3          3           9  13  27
   8        3          1          22
   9        3          3          17  18  20
  10        3          3          12  15  18
  11        3          3          22  27  31
  12        3          1          29
  13        3          3          16  25  31
  14        3          1          23
  15        3          2          23  26
  16        3          2          21  22
  17        3          1          30
  18        3          1          21
  19        3          2          24  25
  20        3          3          23  24  31
  21        3          1          28
  22        3          2          24  26
  23        3          1          25
  24        3          2          28  30
  25        3          1          30
  26        3          1          29
  27        3          1          28
  28        3          1          29
  29        3          1          32
  30        3          1          32
  31        3          1          32
  32        1          0        
************************************************************************
REQUESTS/DURATIONS:
jobnr. mode duration  R 1  R 2  N 1  N 2
------------------------------------------------------------------------
  1      1     0       0    0    0    0
  2      1     1       0    7    9    4
         2     5       0    4    9    4
         3    10       4    0    8    4
  3      1     4       0   10    4    9
         2     5       0    5    2    8
         3     9       1    0    2    5
  4      1     2       0    2    6    9
         2     5      10    0    6    6
         3    10       8    0    4    4
  5      1     2       0    5    6    3
         2     3       0    4    6    3
         3     4       0    2    2    3
  6      1     7       0    6   10    5
         2     9       0    3   10    4
         3     9       0    4   10    2
  7      1     1       0    6    5    9
         2     2       4    0    5    5
         3     9       2    0    3    4
  8      1     1       0    6    2   10
         2     6       2    0    1   10
         3    10       0    4    1    9
  9      1     8       5    0   10    7
         2     8       0    5   10    6
         3    10       0    3    8    6
 10      1     4       0    8    5    5
         2     5       4    0    5    5
         3     6       0    4    4    5
 11      1     4       0    7    6    4
         2     8       8    0    6    4
         3    10       5    0    5    2
 12      1     6       0    2    8    6
         2     6       0    6    6    6
         3    10       3    0    4    5
 13      1     2       5    0    7    9
         2    10       4    0    6    6
         3    10       0    5    3    6
 14      1     3       0    5   10    7
         2     4       0    2    9    7
         3     6       4    0    7    6
 15      1     1       5    0    4    6
         2     3       0   10    4    4
         3     5       0   10    3    4
 16      1     3       0    8    1    5
         2     4       3    0    1    4
         3     8       0    7    1    4
 17      1     5       5    0    9    4
         2     5       0    9    9    4
         3    10       5    0    9    2
 18      1     1       7    0   10    9
         2     5       0    1   10    7
         3     9       6    0    9    4
 19      1     3       0    8    8    4
         2     6       5    0    8    4
         3     8       0    8    7    4
 20      1     1       0    7    5    7
         2     5       0    4    5    6
         3     5       3    0    5    5
 21      1     3       0    6    3    7
         2     9       0    6    2    3
         3    10       8    0    2    3
 22      1     2       0    4    8    8
         2     4       8    0    8    7
         3    10       6    0    4    4
 23      1     2       9    0    2    5
         2     3       0    5    2    4
         3     5       0    2    1    1
 24      1     8       8    0   10   10
         2     9       0    2    4   10
         3    10       7    0    4   10
 25      1     2       0    8    9   10
         2     4       0    6    8    6
         3     9       9    0    7    4
 26      1     7       0    5    1    9
         2     9       9    0    1    7
         3    10       0    2    1    5
 27      1     4       0    6    2    6
         2     6      10    0    2    5
         3     7       7    0    1    4
 28      1     1       4    0    2    7
         2     2       0    2    2    6
         3     4       0    1    2    6
 29      1     4       0    7    4    8
         2     6       0    7    4    4
         3     9       0    6    2    2
 30      1     4       0    9    6    9
         2     6       0    8    6    5
         3    10       0    6    6    3
 31      1     4       0    4    8    5
         2     7      10    0    7    4
         3     8       0    4    5    1
 32      1     0       0    0    0    0
************************************************************************
RESOURCEAVAILABILITIES:
  R 1  R 2  N 1  N 2
   35   34  168  186
************************************************************************
