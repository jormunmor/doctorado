//LatLong- UTM conversion..h
//definitions for lat/long to UTM and UTM to lat/lng conversions
#include <string.h>

#ifndef LATLONGCONV
#define LATLONGCONV

void LLtoUTM(int ReferenceEllipsoid, const double Lat, const double Long, 
			 double &UTMNorthing, double &UTMEasting, char* UTMZone);
void UTMtoLL(int ReferenceEllipsoid, const double UTMNorthing, const double UTMEasting, const char* UTMZone,
			  double& Lat,  double& Long );
char UTMLetterDesignator(double Lat);


class Ellipsoid
{
public:
	Ellipsoid(){};
	Ellipsoid(int Id, const char* name, double radius, double ecc)
	{
		id = Id; 
		EquatorialRadius = radius; eccentricitySquared = ecc;
        if(strlen(name) < 29)
            strcpy(ellipsoidName, name);
        else
            strcpy(ellipsoidName, "NULL");
	}

	int id;
	char ellipsoidName[30];
	double EquatorialRadius; 
	double eccentricitySquared;  

};



#endif
