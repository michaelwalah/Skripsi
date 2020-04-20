package Algorithm;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Michael Walah
 * @NPM 2014730019
 */
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
//import java.io.File;
//import java.io.IOException;
import java.util.Random;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
//import javax.imageio.ImageIO;
//import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
//import javax.swing.JLabel;
import org.opencv.core.*;
import org.opencv.highgui.HighGui;
//import org.opencv.highgui.HighGui;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

public class Main {

    //Cek hasil dari canny edge detection, kalau hasilnya adalah matriks dengan nilai dari buah yang telah diambil
    //gunakan Masking untuk misahin kemudian clustering dengan hasil dari masking tersebut.
    //!!! Ingat, masking dilakukan apabila warna dari putih pada objek gambar yang diambil atau warna hitam dari objek gambar yang diambil
    public static void main(String[] args) throws Exception {
        // load the OpenCV native library
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

        //Create Object from Class ImageProcessing
        ImageProcessing imgProc = new ImageProcessing();

        System.out.println("Data Train:");
         //Load all train data with its classification (real)
        File[] files_train = new File("data-train").listFiles();
        ArrayList<String> allDataTrain = new ArrayList<>();
        allDataTrain = imgProc.loadDataTraining(files_train);
        String[][] trainData = new String[allDataTrain.size()][2];
        for (int i = 0; i < trainData.length; i++) {
            trainData[i][0] = allDataTrain.get(i);
            if (allDataTrain.get(i).contains("ManggaMatang")) {
                trainData[i][1] = "Matang";
            } else {
                trainData[i][1] = "Mentah";
            }
        }
        
        System.out.println("Data Test:");
        //Load all test data with its classification (real)
        File[] files_test = new File("data-test").listFiles();
        ArrayList<String> allDataTest = new ArrayList<>();
        allDataTest = imgProc.loadDataTest(files_test);
        String[][] testData = new String[allDataTest.size()][2];
        for (int i = 0; i < testData.length; i++) {
            testData[i][0] = allDataTest.get(i);
            if (allDataTest.get(i).contains("Matang")) {
                testData[i][1] = "Matang";
            } else {
                testData[i][1] = "Mentah";
            }
        }
        
        //create array list to store dominant color of all train data and test data
        ArrayList<double[][]> dominantColorTrain = new ArrayList<>();
        ArrayList<double[][]> dominantColorTest = new ArrayList<>();
        
        
        
        //extract dominant color from train data
        for (int i = 0; i < trainData.length; i++) {
            dominantColorTrain.add(imgProc.extractFeature(trainData[i][0],0));
        }
        
        
        for (int i = 0; i < testData.length; i++) {
            dominantColorTest.add(imgProc.extractFeature(testData[i][0],1));
        }

        //classification
        
    }
}//end class
