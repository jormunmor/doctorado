In this folder is all the software related to the V-REP platform. For the 
moment, there is available the following:

1. In the scenes folder there is a planner.ttt file that contains one 
environment model used in the Surveillance JSHOP2 domain. That scene contains
a V-REP server implementation in a LUA script that listen to request from 
clients. There are two servers implemented, but only one is enabled. The other
remains to clarify programming concepts of V-REP.

2. In the VRepClient folder there is a C++ implementation of a V-REP client to
communicate with the server and request path planning calculations. The client
request the feasibility of move between two locations given the name of a UAV
and the server responds with the length in meters of the path, or -1 if not
possible. The client was developed in Qt Creator.

The following is needed to execute the software:

1. Download and install the latest version of V-REP from http://www.coppeliarobotics.com/

2. Open the scene with V-REP.

3. Start the simulation. Now the V-REP server is listening to incoming request.

4. Compile the V-REP client with Qt Creator. Some libraries from the V-REP 
installation are needed and may be need to be configured in the .pro.

5. Execute the client binary by using the following format (change the arguments
as you wish):

./VRepClient pathplanning uav#0 loc0 loc1
