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

    //Cek hasil dari canny edge detection, kalau hasilnya adalah matriks dengan nilai dari buah yang telah diambil
    //gunakan Masking untuk misahin kemudian clustering dengan hasil dari masking tersebut.
    //!!! Ingat, masking dilakukan apabila warna dari putih pada objek gambar yang diambil atau warna hitam dari objek gambar yang diambil
    public static void main(String[] args) throws Exception{
        // load the OpenCV native library
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        
        SharpnessEnhancement contrast = new SharpnessEnhancement();
        contrast.filtering();
        Clustering cluster = new Clustering();
        cluster.doClustering();
        
//        //Keisengan yang hakiki
//        //Background Convert to Color Black
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
