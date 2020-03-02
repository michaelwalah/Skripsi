/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Michael
 */
import java.awt.Color;
import java.awt.image.BufferedImage;
//import java.io.File;
//import java.io.IOException;
import java.util.Random;
//import javax.imageio.ImageIO;
//import javax.swing.ImageIcon;
import javax.swing.JFrame;
//import javax.swing.JLabel;
import org.opencv.core.*;
//import org.opencv.highgui.HighGui;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

public class Main {

    public static void main(String[] args) throws Exception{
        // load the OpenCV native library
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        
        //Find the Image Location
        String locationImage = "D:/Campus/Semester 12/Skripsi/Skripsi Sekarang/Program Skripsi/Klasifikasi Kematangan Buah Mangga Berdasarkan Warna/Gambar_Mangga_Gedong_Gincu.jpg";
        
        //Read the Image
        Mat image = Imgcodecs.imread(locationImage);
        
        //Keisengan yang hakiki
        //Background Convert to Color Black
//        for(int i = 0; i < image.width(); i++){
//            for(int j=0; j < image.height(); j++){
//                double[] data = image.get(j, i); //Stores element in an array
//                int gray = (int) (Math.round(0.22*data[2])+(0.7*data[1])+(0.08*data[0]));
//                double t = 0.0;
//                if(gray < 127){
//                    t = 1.3;
//                }
//                else{
//                    t = (1-((double)(gray-127)/128))*1.3;
//                }
//                data[0] = data[0]*t;
//                data[1] = data[1]*t;
//                data[2] = data[2]*t;
//                image.put(j,i, data);
//            }
//        }
        
        //Create Java Frame for the Image
        JFrame frame = new JFrame("Original Image");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        ImagePanel saltedImagePanel = new ImagePanel();
        frame.setContentPane(saltedImagePanel);
        
        //Convert Mat to Java's BufferedImage
        MatToBufImg converter = new MatToBufImg();
        
        //Create New Matrix to Convert BGR to CIE LAB
        Mat image_lab = new Mat();
        //Convert BGR Image to LAB Image
        Imgproc.cvtColor(image, image_lab, Imgproc.COLOR_BGR2Lab);
        
        //CLUSTERING
        //Reshape to 2D Matrix
        Mat samples = image_lab.reshape(1, image_lab.cols() * image_lab.rows());
        
        Mat samples32f = new Mat();
        samples.convertTo(samples32f, CvType.CV_32F, 1.0 / 128.0);
        TermCriteria term = new TermCriteria(TermCriteria.COUNT,100,1);
        
        //Create Matrix to Put Labels
        Mat labels = new Mat();
        //Create Matrix to Put Centers (Centroids)
        Mat centers = new Mat();
        //Running K-Means Clustering Algorithm
        Core.kmeans(samples32f, 10, labels, term, 1, Core.KMEANS_PP_CENTERS, centers);
        //Print All Labels
//        System.out.print("labels: "+labels.dump());
        //Print All Centers
        System.out.println("centers: "+centers.dump());
        
        System.out.println("image " + image.channels() + " channels " + image.cols() + " columns and " + image.rows() + " rows");
        //Output : Image contain 3 channels 601 columns and 488 rows
        
        //salt(image, 10001); //Put 10001 'salt' dots on the image (Try Salting Effect)
        
        converter.setMatrix(image, ".jpg");
        BufferedImage img = converter.getBufferedImage();
        //add the JPG image to JFrame
        saltedImagePanel.setImage(img);
        //saltedImagePanel.repaint(); //ask the system to repaint the (updated GUI)
        //saltedImagePanel.setBackground(Color.black);
        
        frame.setSize(img.getWidth(), img.getHeight());
        
        //frame.add(new JLabel(icn_img));
        frame.setLocationRelativeTo(null); //center GUI
        //frame.revalidate();
        frame.setVisible(true);
        
        // Schedule a job for the event dispatch thread:
        // creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new CannyEdgeDetection();
            }
        });
        
    }
    
//    public static int randInt(int min, int max){
//        Random rand  = new Random();
//        
//        int randomNum = rand.nextInt((max - min) +1) + min;
//        
//        return randomNum;
//    }
//    
//    private static void salt(Mat image, int n){
//        if (image.channels()==1) {
//            System.out.println("Image has a single (grayscale) channels");
//        }
//        if (image.channels()==3) {
//            System.out.println("Image has 3 channels");
//            for (int i = 0; i < n; i++) {
//                //create a salt value randomly
//                double val = randInt(0,250);
//                //the following double array hold the three new values for each channel of the pixel we're modifying
//                double[] speckColor = new double[]{ val, val, val};
//                
//                double x = randInt(1, image.width());
//                double y = randInt(1, image.height());
//                
//                image.put((int)x, (int)y, speckColor); //modify the pixel values at the point (i,i)
//            }//end for
//        }//end if
//    }//end method
}//end class
