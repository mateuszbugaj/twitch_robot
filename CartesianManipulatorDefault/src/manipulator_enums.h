enum MICROSTEPPING{
    ONE = 1,
    HALF = 2,
    QUATER = 4,
    EIGHTH = 8,
    SIXTEENTH = 16
};

enum DRIVE_FACTOR{
    BELT = 26, // There should be 26.6 maybe but THERE IS NO CONCEPT OF FLOATING POINT ENUMS IN CPP
    SCREW = 438
};