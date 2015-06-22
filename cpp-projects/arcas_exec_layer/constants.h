#ifndef CONSTANTS_H
#define CONSTANTS_H

enum GroupType{
    VEHICLE_FRAME,
    ARM,
    GRIPPER
};

enum ActionType{
    LAND,
    TAKEOFF,
    MOVE,
    PICK,
    PLACE
};

enum OperationType{
    STANDALONE,
    COOPERATIVE

};

#endif // CONSTANTS_H
