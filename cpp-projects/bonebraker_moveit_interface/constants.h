#ifndef CONSTANTS_H
#define CONSTANTS_H

// Count to set the UAV as stabilizated (1 seg if quad_state topic is at 100 Hz)
#define UAV_STABILIZATION_COUNT	100

#define PICK_PART_Z_OFFSET 0.75

#define UAV_MAX_PLANNING_ATTEMPTS 		10
#define ARM_MAX_PLANNING_ATTEMPTS 10
#define GRIPPER_MAX_PLANNING_ATTEMPTS 	10

#define ARM_APROX_POS_dX 0.0
#define ARM_APROX_POS_dY 0.0
#define ARM_APROX_POS_dZ 0.25
#define ARM_PICK_POS_dZ 0.060
#define UAV_APROX_POS_dYAW -M_PI/4.0

enum GroupType{
    VEHICLE_FRAME,
    ARM,
    GRIPPER
};

enum ActionType{
    LAND,
    TAKEOFF,
    MOVE,
    PICK
};

enum OperationType{
    STANDALONE,
    COOPERATIVE

};

#endif // CONSTANTS_H
