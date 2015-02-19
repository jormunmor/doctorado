#include <iostream>
#include <string>
#include <string.h>
#include <cstdlib>
#include <stdio.h>
#include <math.h>
#include <unistd.h>

extern "C" {
    #include "extApi.h"
/*	#include "extApiCustom.h" if you wanna use custom remote API functions! */
}

using namespace std;

int clientId;
simxChar* fromClient;
simxChar* fromServer;

simxChar* string_to_simxCharArray(string str);
simxUChar* string_to_simxUCharArray(string str);
bool IsLive();
string get_path(string quadName, string startPosition, string endPosition);
void Finish();

int main(int argc, char *args[])
{
    // configure the vrep client
    string fromClientString = "fromClient";
    string fromServerString = "fromServer";
    string meters_string_("");
    fromClient = string_to_simxCharArray(fromClientString);
    fromServer = string_to_simxCharArray(fromServerString);

    clientId = simxStart("127.0.0.1", 19999, true, true, 5000, 5); // Starts a communication thread with the server
    if (clientId == -1)
    {
        cout << "Failed connecting to remote API server" << endl;
        return -1;
    }

    cout << "Connection to remote server OK" << endl;

    string res("");
    if (strcmp(args[1], "pathplanning") == 0)
    {

        if (argc == 5)
        {
            res = get_path(string(args[2]), string(args[3]), string(args[4])); // response format: "pathLength [pathPoints]"
            //int index = res.find(" ");
            //if(index != string::npos) // will always find a " ", but doing this check doesn't hurt
            //{
            //    meters_string_ = res.substr(0, index);
            //}

        }

    }
    else
    {
        cout << "Unknown command: " << args[1] << endl;
    }

    simxFinish(-1); // terminates all running communication threads
    delete fromClient;
    delete fromServer;

    //string meters_string = meters_string_;
    int comma_index = res.find(","); // the meters_string_ may contains a double value with comma, but c++ only understands double values with point
    if(comma_index != string::npos) // there is a comma
    {

        res = res.replace(comma_index, 1, ".");

   }

    double meters_ = atof(res.c_str());
    double meters = ceil(meters_);
    //printf("%lf\n", meters_);
    //printf("%lf\n", meters);
    //return meters;
    cout << "The path length is: " << meters << endl;

    return meters;

}

// given a string, return a vector of simxChar
simxChar* string_to_simxCharArray(string str){
    simxChar* arr = new simxChar[str.length()];

    for (unsigned int i = 0; i < str.length(); i++)
    {
        arr[i] = str.at(i);
    }

    return arr;
}

// given a string, return a vector of simxUChar
simxUChar* string_to_simxUCharArray(string str){
    simxUChar* arr = new simxUChar[str.length()];

    for (unsigned int i = 0; i < str.length(); i++)
    {
        arr[i] = str.at(i);
    }

    return arr;
}

bool IsLive()
{
        return (simxGetConnectionId(clientId) != -1);
}

string get_path(string uav_name, string start_position, string end_position)
{
        if (!IsLive())
        {
            cout << "Connection with the server was closed" << endl;
            exit(-1);
        }

        simxUChar* reply_data;
        simxInt reply_size;
        string request_string = string("pathplanning ") + uav_name + string(" ") + start_position + string(" ") + end_position;
        if (simxQuery(clientId, string_to_simxCharArray(string("request")), string_to_simxUCharArray(request_string), request_string.length(), string_to_simxCharArray(string("reply")), &reply_data, &reply_size, 12000)==0)
        {
            //do nothing
            //printf("The reply is: %s\n", reply_data);
        }

        string path_str((char*) reply_data); ///TODO this conversion may induce problems

        return path_str;
}

void Finish()
{
    if (simxGetConnectionId(clientId) != -1) {
        simxFinish(clientId);
    }
}
