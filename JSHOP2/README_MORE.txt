The source code for this JSHOP2 distribution has been modified. To use this do the following (inside the JSHOP2 folder):

- source classpath.sh
- make clean /// this is to clean the whole JSHOP2 distribution
- make /// to compile the JSHOP2 SOURCE CODE
- make 1 /// to compile and run the Arcas mission

Actually, after running an example and finding a plan, a .txt file is created inside the HOME folder
with the name planJshop2.txt. The GUI from the original distribution have a bug that do not show some of
the leaf nodes for the plan, so to get the complete plan this file must be readed. The graphical tool
is not reliable.

The path on which the file is created can by modified in the source JSHOP2GUI.java. It can be generated
on the folder of the example, but this implies that external apps that want that plan file will need
to know the name of the specific example folder, so for simplicity it is created on HOME. As disadvantage, 
it is overwritten any time JSHOP2 finds a new plan.

For the moment, two domains are present in the planning engine:

1. The Arcas domain. It can be run with the problem present in the example.

2. The Surveillance domain. It requires to have a VREP server running in background to accept the incoming requests done
	in the preconditions of the domain, so the VREP server must be started.
