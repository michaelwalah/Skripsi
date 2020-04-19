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
    public static void main(String[] args) throws Exception{
        // load the OpenCV native library
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        
        File[] files = new File("data-train").listFiles();
        ImageProcessing imgProc = new ImageProcessing();
        
        ArrayList<String> temp = new ArrayList<>();
        temp = imgProc.loadDataTraining(files);
        String[][] trainData = new String[temp.size()][2];
        for (int i = 0; i < trainData.length; i++) {
            trainData[i][0] = temp.get(i);
            if(temp.get(i).contains("ManggaMatang")) trainData[i][1] = "Matang";
            else trainData[i][1] = "Mentah";
        }
//        for (int i = 0; i < temp.size(); i++) {
//            System.out.println(temp.get(i));
//        }
    }
}//end class
