// CPP includes
#include <time.h>
#include <iostream>
#include <fstream>
#include <sstream>
#include <vector>
#include <cmath>
#include <string>
// ROS includes
#include "rosbag/bag.h"
#include "rosbag/view.h"
#include "rosbag/message_instance.h"
#include <boost/foreach.hpp>
#include "ros/ros.h"
#include "gps_common/GPSFix.h"
#include "mav_msgs/Height.h"
#include "sensor_msgs/Imu.h"
#include "sensor_msgs/CompressedImage.h"
#include "geometry_msgs/Quaternion.h"
#include "LinearMath/btScalar.h"
#include "LinearMath/btVector3.h"
#include "LinearMath/btMatrix3x3.h"
#include "LinearMath/btQuaternion.h"
#include "LinearMath/btTransform.h"
// opencv includes
#include <opencv2/imgproc/imgproc.hpp>
#include <opencv2/highgui/highgui.hpp>
// boost includes
#include <boost/filesystem.hpp>


using namespace std;
using namespace ros;

#define PI 3.14159265359

mav_msgs::Height::ConstPtr get_bar_height_msg(rosbag::View::iterator &height_iterator, rosbag::View &height_view, ros::Time &gps_stamp);
sensor_msgs::Imu::ConstPtr get_imu_msg(rosbag::View::iterator &imu_iterator, rosbag::View &imu_view, ros::Time &gps_stamp);
sensor_msgs::CompressedImage::ConstPtr get_image_msg(rosbag::View::iterator &image_iterator, rosbag::View &image_view, ros::Time &gps_stamp);
double distance_between_GPS_points(double lat1, double long1, double lat2, double long2);
void select_by_meters(
	std::vector<gps_common::GPSFix::ConstPtr> &gps_vector, 
	std::vector<mav_msgs::Height::ConstPtr> &height_vector, 
	std::vector<sensor_msgs::Imu::ConstPtr> &imu_vector, 
	std::vector<sensor_msgs::CompressedImage::ConstPtr> &image_vector,
	std::vector<gps_common::GPSFix::ConstPtr> &new_gps_vector, 
	std::vector<mav_msgs::Height::ConstPtr> &new_height_vector, 
	std::vector<sensor_msgs::Imu::ConstPtr> &new_imu_vector, 
	std::vector<sensor_msgs::CompressedImage::ConstPtr> &new_image_vector,
	int distance_value);

/*
	In this implementation we always suppose that gps transmit at frequencies 
	lower than the camera and barometer (for the moment). 
*/
int main(int argc, char **argv)
{
	ros::init(argc, argv, "image_gps");
	ros::NodeHandle n;
	
	if(argc != 5)
	{
		cout << "Usage: image_gps <path_to_bagfile> <meters_between_imgs> <lower img limit> <upper img limit>" << endl;
		return 0;
	}
	
	rosbag::Bag bag;
	bag.open(argv[1], rosbag::bagmode::Read);
	boost::filesystem::path path_name(argv[1]);
	string bagfile_path_dir = path_name.parent_path().string();
	string meters = string(argv[2]);
	int lower_limit, upper_limit;
	lower_limit = atoi(argv[3]);
	upper_limit = atoi(argv[4]);
		
	// create topics
	std::vector<std::string> gps_topic;
	gps_topic.push_back(std::string("/gnss_nv08c/data"));
	std::vector<std::string> image_topic;
	image_topic.push_back(std::string("/logitechnode/image_raw/compressed"));
	std::vector<std::string> imu_topic;
	imu_topic.push_back(std::string("/lse_xsens_mti/xsens/imu/data"));
	std::vector<std::string> height_topic;
	height_topic.push_back(std::string("/mav/pressure_height_filtered"));
		
	// create views
	rosbag::View gps_view(bag, rosbag::TopicQuery(gps_topic));
	cout << "Number of GPSFix messages in bagfile: " << gps_view.size()  << endl;
	rosbag::View image_view(bag, rosbag::TopicQuery(image_topic));
	cout << "Number of CompressedImage messages in bagfile: " << image_view.size()  << endl;
	rosbag::View imu_view(bag, rosbag::TopicQuery(imu_topic));
	cout << "Number of Imu messages in bagfile: " << imu_view.size()  << endl;
	rosbag::View height_view(bag, rosbag::TopicQuery(height_topic));
	cout << "Number of Height messages in bagfile: " << height_view.size()  << endl;
	cout << endl;
	
	// create iterators
	rosbag::View::iterator height_iterator = height_view.begin();
	rosbag::View::iterator imu_iterator = imu_view.begin();
	rosbag::View::iterator image_iterator = image_view.begin();
				
	// create vectors to store the objects
	std::vector<gps_common::GPSFix::ConstPtr> gps_vector;
	std::vector<mav_msgs::Height::ConstPtr> height_vector;
	std::vector<sensor_msgs::Imu::ConstPtr> imu_vector;
	std::vector<sensor_msgs::CompressedImage::ConstPtr> image_vector;
    
    // iterate over gps (lower publish frequency)
    BOOST_FOREACH(rosbag::MessageInstance const m, gps_view)
    {
    	gps_common::GPSFix::ConstPtr gps_message = m.instantiate<gps_common::GPSFix>();
    	bool height_encountered = false;
    	bool imu_encountered = false;
    	bool image_encountered = false;
        if (gps_message != NULL)
        {
        	// get the gps timestamp
        	ros::Time gps_stamp = gps_message->header.stamp;
        	
        	// get associated height message
        	mav_msgs::Height::ConstPtr height_message = get_bar_height_msg(height_iterator, height_view, gps_stamp);
           	if(height_message != NULL)
	        {
	        	height_encountered = true;
        	}
        	else
        	{
        		// discard this gps because didn't encounter associated height
        		// before the gps timestamp
        		continue;
        	}
        	
        	// get associated imu message
        	sensor_msgs::Imu::ConstPtr imu_message = get_imu_msg(imu_iterator, imu_view, gps_stamp);
           	if(imu_message != NULL)
	        {
	        	imu_encountered = true;
        	}
        	else
        	{
        		// discard this gps because didn't encounter associated imu
        		// before the gps timestamp
        		continue;
        	}
        	
        	// get associated image message
        	sensor_msgs::CompressedImage::ConstPtr image_message = get_image_msg(image_iterator, image_view, gps_stamp);
           	if(image_message != NULL)
	        {
	        	image_encountered = true;
        	}
        	else
        	{
        		// discard this gps because didn't encounter associated image
        		// before the gps timestamp
        		continue;
        	}
        	
        	// Only store messages if encountered an image, an imu and an height
        	// before the timestamp of the gps.
        	if(height_encountered && imu_encountered && image_encountered) 
        	{
	        	height_vector.push_back(height_message);
	        	imu_vector.push_back(imu_message);
	        	image_vector.push_back(image_message);
        	  	gps_vector.push_back(gps_message);
        	}
	                	
        }
        
    }
    
    // at this point we don't need anymore the bag file, so close it
    bag.close();
    
    // show all retrieved messages count
    cout << "Number of retrieved Gps Coords: " << gps_vector.size() << endl;
    cout << "Number of retrieved Barometric Height values: " << height_vector.size() << endl;
    cout << "Number of retrieved Imu values: " << imu_vector.size() << endl;
    cout << "Number of retrieved Images: " << image_vector.size() << endl;
    cout << endl;
    
    if( atoi(meters.c_str()) > 0) // trim number of messages based on GPS distance
    {
    	cout << "Dropping messages based on GPS points distance {" << meters << "}..." << endl;
    	    	    	
    	// create the new empty vectors
    	// create vectors to store the objects
		std::vector<gps_common::GPSFix::ConstPtr> new_gps_vector;
		std::vector<mav_msgs::Height::ConstPtr> new_height_vector;
		std::vector<sensor_msgs::Imu::ConstPtr> new_imu_vector;
		std::vector<sensor_msgs::CompressedImage::ConstPtr> new_image_vector;
		
		
		// fill the new vectors
    	select_by_meters(
    		gps_vector, height_vector, imu_vector, image_vector,
    		new_gps_vector, new_height_vector, new_imu_vector, new_image_vector,
    		atoi(meters.c_str()));
    		
    	// show final messages count
	    cout << "Resulting number of Gps Coords: " << new_gps_vector.size() << endl;
    	cout << "Resulting number of Barometric Height values: " << new_height_vector.size() << endl;
	    cout << "Resulting number of Imu values: " << new_imu_vector.size() << endl;
    	cout << "Resulting number of Images: " << new_image_vector.size() << endl << endl;
    	
    	// trim the results based on lower and upper limits and finish
    	if(lower_limit > 0 && upper_limit > 0)
    	{
    		cout << "Trimming vectors based on lower and upper limits..." << endl;
    		cout << "Starting by image: " << lower_limit << endl;
			cout << "Finishing in image: " << new_image_vector.size() - upper_limit << endl << endl;
			
			std::vector<gps_common::GPSFix::ConstPtr>::const_iterator first_gps = new_gps_vector.begin() + lower_limit;
			std::vector<gps_common::GPSFix::ConstPtr>::const_iterator last_gps = new_gps_vector.end() - upper_limit;
			std::vector<gps_common::GPSFix::ConstPtr> final_gps_vector(first_gps, last_gps);
			
			std::vector<mav_msgs::Height::ConstPtr>::const_iterator first_height = new_height_vector.begin() + lower_limit;
			std::vector<mav_msgs::Height::ConstPtr>::const_iterator last_height = new_height_vector.end() - upper_limit;
			std::vector<mav_msgs::Height::ConstPtr> final_height_vector(first_height, last_height);
			
			std::vector<sensor_msgs::Imu::ConstPtr>::const_iterator first_imu = new_imu_vector.begin() + lower_limit;
			std::vector<sensor_msgs::Imu::ConstPtr>::const_iterator last_imu = new_imu_vector.end() - upper_limit;
			std::vector<sensor_msgs::Imu::ConstPtr> final_imu_vector(first_imu, last_imu);
			
			std::vector<sensor_msgs::CompressedImage::ConstPtr>::const_iterator first_image = new_image_vector.begin() + lower_limit;
			std::vector<sensor_msgs::CompressedImage::ConstPtr>::const_iterator last_image = new_image_vector.end() - upper_limit;
			std::vector<sensor_msgs::CompressedImage::ConstPtr> final_image_vector(first_image, last_image);
			
			// substitute the old vectors with the final ones
			gps_vector = final_gps_vector;
			height_vector = final_height_vector;
			imu_vector = final_imu_vector;
			image_vector = final_image_vector;
			
		}
		else // finish
		{
			// substitute the old vectors with the new ones
			gps_vector = new_gps_vector;
			height_vector = new_height_vector;
			imu_vector = new_imu_vector;
			image_vector = new_image_vector;
			
    	}
    
    }
    
    // show final messages count
    cout << "Final number of Gps Coords: " << gps_vector.size() << endl;
    cout << "Final number of Barometric Height values: " << height_vector.size() << endl;
    cout << "Final number of Imu values: " << imu_vector.size() << endl;
    cout << "Final number of Images: " << image_vector.size() << endl;
    cout << endl;
    
    // uncomment this code only for information purposes
    /*
    cout << "\t\t\t\tBarHeight\tImu\t\tImage" << endl;
    for(unsigned int i=0; i < gps_vector.size(); i++)
    {
 		gps_common::GPSFix::ConstPtr gps_m = gps_vector[i];  
 		mav_msgs::Height::ConstPtr height_m = height_vector[i];
 		sensor_msgs::Imu::ConstPtr imu_m = imu_vector[i];
 		sensor_msgs::CompressedImage::ConstPtr image_m = image_vector[i];
 		ros::Duration d1 = gps_m->header.stamp - height_m->header.stamp;
 		ros::Duration d2 = gps_m->header.stamp - imu_m->header.stamp;
 		ros::Duration d3 = gps_m->header.stamp - image_m->header.stamp;
 		cout << "Time differences: " << d1.toNSec() << "\t" << d2.toNSec() << "\t" << d3.toNSec() << endl;
 		
    }
    */
    
    
    
    
    
    // this code generates the micmac text file needed to generate the dense cloud
    ofstream outfile;
    string text_file_path = bagfile_path_dir + string("/GPS_WPK_GRVC_") + meters + string(".csv");
  	outfile.open (text_file_path.c_str());
  	outfile << "#F=N Y X Z K W P" << endl;
  	outfile << "#" << endl;
  	outfile << "#image\tlatitude\tlongitude\taltitude\tyaw\tpitch\troll" << endl;
  	
  	// this code generates a text file with camera points (1/8 from total images)
    ofstream outfile_camera_points;
    string camera_points_file_path = bagfile_path_dir + string("/camera_points_") + meters + string(".txt");
  	outfile_camera_points.open (camera_points_file_path.c_str());
  	
  	// create rotation matrix Rx and Rz for camera
  	double z_angle = 135*PI/180;
  	btMatrix3x3 Rz(cos(z_angle), -sin(z_angle), 0, 
  					sin(z_angle), cos(z_angle), 0,
  					0, 0, 1); // 135º Z axis rotation
  	
  	double x_angle = PI;
  	btMatrix3x3 Rx(1, 0, 0, 
  					0, cos(x_angle), -sin(x_angle),
  					0, sin(x_angle), cos(x_angle)); // 180º X axis rotation
  	
  	// create translation vector for camera
        btVector3 Tr(0, 0, -0.2);
  	
  	// multiply rotations (Rx=Rx*Rz)
  	Rx*=Rz;
  	
  	// create the respective Camera Transform
  	btTransform Tcamera(Rx, Tr);
  	
  	// compression params for storing images
	vector<int> compression_params;
    compression_params.push_back(CV_IMWRITE_JPEG_QUALITY);
    compression_params.push_back(100); // max quality
    int written_images = 0;
    int total_imgs = gps_vector.size();
    // create the image directory
    string image_directory_path = bagfile_path_dir + "/images_" + meters;
    boost::filesystem::create_directory(image_directory_path);
    
    
  	
  	for(unsigned int i=0; i < gps_vector.size(); i++)
    {
 		gps_common::GPSFix::ConstPtr gps_m = gps_vector[i];  
 		mav_msgs::Height::ConstPtr height_m = height_vector[i];
 		sensor_msgs::Imu::ConstPtr imu_m = imu_vector[i];
 		sensor_msgs::CompressedImage::ConstPtr image_m = image_vector[i];
 		
 		// calculate roll, pitch, yaw for the camera orientation
 		geometry_msgs::Quaternion orientation = imu_m->orientation;
 		btQuaternion uav_q(orientation.x, orientation.y, orientation.z, orientation.w); // orientation of the UAV
 		btTransform Tuav(uav_q); // UAV Transform with zero translation vector
 		Tuav *= Tcamera; // now the camera system is passed to the uav system reference
 		btQuaternion final_orientation = Tuav.getRotation(); 		
 		btScalar roll, pitch, yaw;
 		btMatrix3x3(final_orientation).getRPY(roll, pitch, yaw);
 		
 		// convert the quaternion to roll, pitch, yaw
 		/*
 		geometry_msgs::Quaternion orientation = imu_m->orientation;
 		btQuaternion q(orientation.x, orientation.y, orientation.z, orientation.w);
 		btScalar roll, pitch, yaw;
 		btMatrix3x3(q).getRPY(roll, pitch, yaw);
 		*/
 		
 		// print to file
 		outfile << "img" << image_m->header.seq << ".jpg\t";
 		outfile << gps_m->latitude << "\t" << gps_m->longitude << "\t" << height_m->height << "\t";
 		outfile << yaw << "\t" << pitch << "\t" << roll << endl;
 		
 		if(i%8 == 0) // every 8 images we store a camera point
 		{
 			outfile_camera_points << "img" << image_m->header.seq << ".jpg|";
 		}
 				
 		// save image in bagfile directory
 		// load the image in a cv Mat object
        cv::Mat matrix = cv::imdecode(cv::Mat(image_m->data),1);
        // create a file name
	    std::stringstream ss;
		ss << image_directory_path.c_str() << "/img" << image_m->header.seq << ".jpg";
		std::string file_name = ss.str();
	    if(imwrite(file_name, matrix, compression_params))
	    {
	       	written_images++;
	       	cout << "\r" << written_images << "/" << total_imgs << " generated." << flush;
	        	
	    }
 		
    }
    cout << endl;
    cout << written_images << " images generated succesfully." << endl;
  	outfile.close();
  	outfile_camera_points.close();
	
  return 0;
}

mav_msgs::Height::ConstPtr get_bar_height_msg(rosbag::View::iterator &height_iterator, rosbag::View &height_view, ros::Time &gps_stamp)
{
	// Initialize Height view iterator variables
	mav_msgs::Height::ConstPtr height_message;
	mav_msgs::Height::ConstPtr height_message_copy;
	height_message_copy.reset(); // set to null
	ros::Duration gps_height_difference;
	ros::Time height_stamp;
	        	        	
	while(height_iterator != height_view.end())
	{
    	height_message = (*height_iterator).instantiate<mav_msgs::Height>();
    	height_stamp = height_message->header.stamp;
    	gps_height_difference = gps_stamp - height_stamp;
    	height_iterator++;
       	if(gps_height_difference < ros::Duration(0)) 
    	{
    		break;
    		
    	}
    	else
    	{
    		height_message_copy = height_message;
    	}
			
    }
    
    return height_message_copy;
}

sensor_msgs::Imu::ConstPtr get_imu_msg(rosbag::View::iterator &imu_iterator, rosbag::View &imu_view, ros::Time &gps_stamp)
{
	// Initialize Imu view iterator variables
	sensor_msgs::Imu::ConstPtr imu_message;
	sensor_msgs::Imu::ConstPtr imu_message_copy;
	imu_message_copy.reset(); // set to null
	ros::Duration gps_imu_difference;
	ros::Time imu_stamp;
	        	        	
	while(imu_iterator != imu_view.end())
	{
    	imu_message = (*imu_iterator).instantiate<sensor_msgs::Imu>();
    	imu_stamp = imu_message->header.stamp;
    	gps_imu_difference = gps_stamp - imu_stamp;
    	imu_iterator++;
       	if(gps_imu_difference < ros::Duration(0)) 
    	{
    		break;
    		
    	}
    	else
    	{
    		imu_message_copy = imu_message;
    	}
			
    }
    
    return imu_message_copy;
}

sensor_msgs::CompressedImage::ConstPtr get_image_msg(rosbag::View::iterator &image_iterator, rosbag::View &image_view, ros::Time &gps_stamp)
{
	// Initialize Image view iterator variables
	sensor_msgs::CompressedImage::ConstPtr image_message;
	sensor_msgs::CompressedImage::ConstPtr image_message_copy;
	image_message_copy.reset(); // set to null
	ros::Duration gps_image_difference;
	ros::Time image_stamp;
	        	        	
	while(image_iterator != image_view.end())
	{
    	image_message = (*image_iterator).instantiate<sensor_msgs::CompressedImage>();
    	image_stamp = image_message->header.stamp;
    	gps_image_difference = gps_stamp - image_stamp;
    	image_iterator++;
       	if(gps_image_difference < ros::Duration(0)) 
    	{
    		break;
    		
    	}
    	else
    	{
    		image_message_copy = image_message;
    	}
			
    }
    
    return image_message_copy;
}

void select_by_meters(
	std::vector<gps_common::GPSFix::ConstPtr> &gps_vector, 
	std::vector<mav_msgs::Height::ConstPtr> &height_vector, 
	std::vector<sensor_msgs::Imu::ConstPtr> &imu_vector, 
	std::vector<sensor_msgs::CompressedImage::ConstPtr> &image_vector,
	std::vector<gps_common::GPSFix::ConstPtr> &new_gps_vector, 
	std::vector<mav_msgs::Height::ConstPtr> &new_height_vector, 
	std::vector<sensor_msgs::Imu::ConstPtr> &new_imu_vector, 
	std::vector<sensor_msgs::CompressedImage::ConstPtr> &new_image_vector,
	int distance_value)
{
	// store the first element of each vector as reference
	new_gps_vector.push_back(gps_vector[0]);
	new_height_vector.push_back(height_vector[0]);
	new_imu_vector.push_back(imu_vector[0]);
	new_image_vector.push_back(image_vector[0]);
	
	double lat1, long1, lat2, long2;
	lat2 = new_gps_vector[new_gps_vector.size()-1]->latitude; // last point stored
	long2 = new_gps_vector[new_gps_vector.size()-1]->longitude; // last point stored
	// iterate over the resting elements, and store them if distance is < distance_value
	for(unsigned int i=1; i < gps_vector.size(); i++)
	{

		lat1 = gps_vector[i]->latitude;
		long1 = gps_vector[i]->longitude;
		double point_distance = distance_between_GPS_points(lat1, long1, lat2, long2);
		if( point_distance > distance_value ) // must add the point
		{
			new_gps_vector.push_back(gps_vector[i]);
			new_height_vector.push_back(height_vector[i]);
			new_imu_vector.push_back(imu_vector[i]);
			new_image_vector.push_back(image_vector[i]);
		
			lat2 = lat1; // last point stored
			long2 = long1; // last point stored
		
		}
		
	}

}

double distance_between_GPS_points(double lat1, double long1, double lat2, double long2)
{

	int R = 6371000; // earth radious in meters
	double dLat = (lat2-lat1) * PI/180;
	double dLon = (long2-long1) * PI/180;
	lat1 = lat1 * PI/180;
	lat2 = lat2 * PI/180;

	double a = sin(dLat/2) * sin(dLat/2) + sin(dLon/2) * sin(dLon/2) * cos(lat1) * cos(lat2); 
	double c = 2 * atan2(sqrt(a), sqrt(1-a)); 
	return R * c;

}






