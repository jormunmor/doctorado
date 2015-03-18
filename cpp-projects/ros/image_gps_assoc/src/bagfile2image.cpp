// CPP includes
#include <sstream>
#include <string>

// ROS includes
#include "rosbag/bag.h"
#include "rosbag/view.h"
#include "rosbag/message_instance.h"
#include <boost/foreach.hpp>
#include "ros/ros.h"
#include "sensor_msgs/CompressedImage.h"

// opencv includes
#include <opencv2/imgproc/imgproc.hpp>
#include <opencv2/highgui/highgui.hpp>

using namespace std;
using namespace ros;
using namespace cv;

int main(int argc, char **argv)
{
	ros::init(argc, argv, "bagfile2image");
	ros::NodeHandle n;
	
	if(argc != 4)
	{
		cout << "bagfile2image: save compressed images in a bagfile to disk." << endl;
		cout << "Usage: bagfile2image <path_to_bagfile> <name_of_image_topic> <path_to_output_dir>" << endl;
		return 0;
		
	}
	rosbag::Bag bag;
	bag.open(argv[1], rosbag::bagmode::Read);
	string image_topic_name(argv[2]);
	string output_dir(argv[3]);
	
	// create topic
	std::vector<std::string> image_topic;
	image_topic.push_back(image_topic_name);
			
	// create view
	rosbag::View image_view(bag, rosbag::TopicQuery(image_topic));
	int total_imgs = image_view.size();
	cout << "Number of CompressedImage messages: " << total_imgs << endl;
	
	// compression params
	vector<int> compression_params;
    compression_params.push_back(CV_IMWRITE_JPEG_QUALITY);
    compression_params.push_back(100); // max quality
					
	// iterate over images
	cout << "Generating images. Please wait..." << endl;
	int written = 0;
	BOOST_FOREACH(rosbag::MessageInstance const m, image_view)
    {
    	sensor_msgs::CompressedImage::ConstPtr compressed_image_message = m.instantiate<sensor_msgs::CompressedImage>();
    	if (compressed_image_message != NULL)
        {
        	// load the image in a cv Mat object
        	cv::Mat matrix = cv::imdecode(cv::Mat(compressed_image_message->data),1);
        	
        	// create a file name
	        std::stringstream ss;
			ss << output_dir << "/img" << compressed_image_message->header.seq << ".jpg";
			std::string file_name = ss.str();
	        if(imwrite(file_name, matrix, compression_params))
	        {
	        	written++;
	        	cout << "\r" << written << "/" << total_imgs << " generated." << flush;
	        	
	        }
	     	                	
        }

    }
    cout << endl;
    cout << written << " images generated succesfully." << endl;
    bag.close();
    
    return 0;
}

