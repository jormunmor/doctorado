#include <iostream>

// opencv includes
#include "opencv2/imgproc/imgproc.hpp"
#include "opencv2/highgui/highgui.hpp"

using namespace std;
using namespace cv;

void applyHomogeneousSmoothing();
void applyGaussianSmoothing();
void applyMedianSmoothing();
void applyBilateralSmoothing();

int main()
{
    //applyHomogeneousSmoothing();
    //applyGaussianSmoothing();
    //applyMedianSmoothing();
    applyBilateralSmoothing();

    return 0;
}

void applyHomogeneousSmoothing()
{
    //create 2 empty windows
     namedWindow( "Original Image" , CV_WINDOW_AUTOSIZE );
     namedWindow( "Smoothed Image" , CV_WINDOW_AUTOSIZE );

      // Load an image from file
     Mat src = imread("low_cost.png", 1);

      //show the loaded image
     imshow( "Original Image", src );

     Mat dst;

     //smooth the image in the "src" and save it to "dst"
     blur(src, dst, Size(3, 3));

     //show the blurred image with the text
     imshow( "Smoothed Image", dst );
     waitKey(0);

     // write to disk
     imwrite("homogeneous.png", dst);

}

void applyGaussianSmoothing()
{
    //create 2 empty windows
     namedWindow( "Original Image" , CV_WINDOW_AUTOSIZE );
     namedWindow( "Smoothed Image" , CV_WINDOW_AUTOSIZE );

      // Load an image from file
     Mat src = imread("low_cost.png", 1);

      //show the loaded image
     imshow( "Original Image", src );

     Mat dst;

     //smooth the image in the "src" and save it to "dst"
     GaussianBlur(src, dst, Size(3, 3), 0, 0 );

     //show the blurred image with the text
     imshow( "Smoothed Image", dst );
     waitKey(0);

     // write to disk
     imwrite("gaussian.png", dst);

}

void applyMedianSmoothing()
{
    //create 2 empty windows
     namedWindow( "Original Image" , CV_WINDOW_AUTOSIZE );
     namedWindow( "Smoothed Image" , CV_WINDOW_AUTOSIZE );

      // Load an image from file
     Mat src = imread("low_cost.png", 1);

      //show the loaded image
     imshow( "Original Image", src );

     Mat dst;

     //smooth the image in the "src" and save it to "dst"
     medianBlur(src, dst, 3);

     //show the blurred image with the text
     imshow( "Smoothed Image", dst );
     waitKey(0);

     // write to disk
     imwrite("median.png", dst);

}

void applyBilateralSmoothing()
{
    //create 2 empty windows
     namedWindow( "Original Image" , CV_WINDOW_AUTOSIZE );
     namedWindow( "Smoothed Image" , CV_WINDOW_AUTOSIZE );

      // Load an image from file
     Mat src = imread("low_cost.png", 1);

      //show the loaded image
     imshow( "Original Image", src );

     Mat dst;

     //smooth the image in the "src" and save it to "dst"
     bilateralFilter(src, dst, 10, 200, 200);

     //show the blurred image with the text
     imshow( "Smoothed Image", dst );
     waitKey(0);

     // write to disk
     imwrite("bilateral.png", dst);

}
