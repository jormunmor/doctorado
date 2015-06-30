************************************************************************
file with basedata            : mm36_.bas
initial value random generator: 1823086640
************************************************************************
projects                      :  1
jobs (incl. supersource/sink ):  12
horizon                       :  83
RESOURCES
  - renewable                 :  2   R
  - nonrenewable              :  2   N
  - doubly constrained        :  0   D
************************************************************************
PROJECT INFORMATION:
pronr.  #jobs rel.date duedate tardcost  MPM-Time
    1     10      0       20        0       20
************************************************************************
PRECEDENCE RELATIONS:
jobnr.    #modes  #successors   successors
   1        1          3           2   3   4
   2        3          3           5   6   7
   3        3          1           8
   4        3          3           5   7   8
   5        3          1           9
   6        3          1           8
   7        3          1          10
   8        3          2           9  11
   9        3          1          12
  10        3          1          12
  11        3          1          12
  12        1          0        
************************************************************************
REQUESTS/DURATIONS:
jobnr. mode duration  R 1  R 2  N 1  N 2
------------------------------------------------------------------------
  1      1     0       0    0    0    0
  2      1     1       6   10    0    8
         2     1       6   10    4    0
         3     7       5   10    0    8
  3      1     6       7    9    0    6
         2     9       4    6    5    0
         3     9       5    4    0    4
  4      1     5       7    9    0    7
         2     6       4    6    0    3
         3     7       1    4    8    0
  5      1     3       8    4    5    0
         2     5       6    3    5    0
         3     7       3    1    3    0
  6      1     3       8    2    7    0
         2     7       7    1    7    0
         3    10       7    1    0    4
  7      1     8       7    8   10    0
         2     9       6    6    9    0
         3     9       7    6    8    0
  8      1     2      10    4    6    0
         2     4       8    4    0    7
         3     9       7    2    0    4
  9      1     1       6   10    7    0
         2     3       5    9    7    0
         3     5       3    9    0    3
 10      1     7       5    4    4    0
         2     9       3    3    0    2
         3    10       1    2    4    0
 11      1     2       6   10    0    7
         2     4       6    8    0    7
         3    10       5    6    0    6
 12      1     0       0    0    0    0
************************************************************************
RESOURCEAVAILABILITIES:
  R 1  R 2  N 1  N 2
   11   14   56   44
************************************************************************
