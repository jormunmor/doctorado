Generate the assignment for the problem. Later, concat it with the content of
the text files in the order:

1_start_part.txt + 
2_uav_defs_part.txt +
3_object_state_defs_part.txt + 
4_location_defs_part.txt + 
5_assembly_location_defs_part.txt + 
6_object_defs_part.txt + 
7_assembly_planner_part.txt +
8_assignment_part.txt +
9_end_part.txt

and rename it as a file called 'problem' (without the .txt extension).

IMPORTANT NOTE: the 8_assignment_part.txt file is never generated as a file, it is the assignment
for the problem that has to be generated after each score computation. It is created as a string
inside de OptaPlanner engine and concatenated with the contents of the other files. It appears
here to show how the JSHOP2 problem file is divided into logical parts. The rest of the files
are generated once after OptaPlanner starts and remain unaltered.
