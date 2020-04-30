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
import java.awt.Container;
import java.awt.Image;
import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.MatOfInt;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.core.TermCriteria;
import org.opencv.highgui.HighGui;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

public class ImageProcessing {

    private static final int KERNEL_SIZE = 3;
    private static final Size BLUR_SIZE = new Size(3, 3);
    //Variable for integer value
    private int largestIndex;
    private int count_idx;
    //Variables for Matrix
    private Mat src; //Matrix for Image Source
    private Mat detectedEdges; //Matrix for get Edge Detection Value
    private Mat dst; //Matrix for Destination Image in Canny Edge Detection
    private Mat drawing; //Matrix for Drawing Contours
    private Mat image_lab; //Matrix for Conversion Color
    private Mat samples; //Matrix for Reshape Image to 2D Matrix
    private Mat labels; //Matrix for Clustering Labels
    private Mat centers; //Matrix for Clustering Centers
    private Mat out; //Matrix that being used for operation AND in Masking
    private Mat out_2; //Matrix that being used for operation XOR in Masking
    //Variable for Scalar
    private Scalar color; //Variable that being used for image pixel value
    //Variable for List Contains Matrix of Point
    private List<MatOfPoint> contours; //Using for find and draw the contours
    private List<MatOfPoint> hullList; //Using for find and draw the contours
    //Variable for Array
    private double[][] intraTotalValue; //Contains intra cluster distance total value
    private double[] intraClusterDistanceValue; //Contains intra cluster distance value per cluster
    //Variable for Frame and Label
    private JFrame frame;
    private JLabel imgLabel;
    private int threshold;
    private int cluster;
    private int dominant;
    private JTextArea logText;

    private double maxRes; //to contains euclidean result of classification
    private double[][] vectorTest, vectorTrain; //temporarily contains vector feature of test & train image

    public ImageProcessing(int threshold, int cluster, int dominant) {
        dst = new Mat();
        this.cluster = cluster;
        this.dominant = dominant;
        this.threshold = threshold;
        this.detectedEdges = new Mat();
    }

    public ImageProcessing(int threshold, int cluster, int dominant, JTextArea logText) {
        dst = new Mat();
        this.cluster = cluster;
        this.dominant = dominant;
        this.threshold = threshold;
        this.logText = logText;
        this.detectedEdges = new Mat();
    }

    public void addComponentsToPane(Container pane, Image img) {
        if (!(pane.getLayout() instanceof BorderLayout)) {
            pane.add(new JLabel("Container doesn't use BorderLayout!"));
            return;
        }

//        JPanel sliderPanel = new JPanel();
//        sliderPanel.setLayout(new BoxLayout(sliderPanel, BoxLayout.PAGE_AXIS));
        imgLabel = new JLabel(new ImageIcon(img));
        pane.add(imgLabel, BorderLayout.CENTER);
    }

    public void doCannyEdgeDetection() {
        dst = new Mat(); //reset object
        Mat srcBlur = new Mat();
        //Pre Processing
        Mat greyImage = new Mat();
        Imgproc.cvtColor(src, greyImage, Imgproc.COLOR_BGR2GRAY);//convert gambar ke grayscale
        Imgproc.GaussianBlur(greyImage, srcBlur, BLUR_SIZE, 100);//apply gaussian blur
//        System.out.println(this.threshold+","+KERNEL_SIZE);
//        System.out.println(srcBlur.size());
        Imgproc.Canny(srcBlur, detectedEdges, this.threshold, this.threshold, KERNEL_SIZE, false);//apply canny edge detection
    }

    public void doDilation() {
        Imgproc.dilate(detectedEdges, detectedEdges, new Mat(), new Point(-1, -1), 1); //apply dilate for filling gaps
    }

    public void drawContours() {
        contours = new ArrayList<>();
        Mat hierarchy = new Mat();
        Imgproc.findContours(detectedEdges, contours, hierarchy, Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);

        //Find Largest Hull
        double maxArea = 0.0;
        MatOfPoint biggestHull = new MatOfPoint();
        largestIndex = 0;
        int count = 0;
        hullList = new ArrayList<>();
        for (MatOfPoint contour : contours) {
            MatOfInt hull = new MatOfInt();
            Imgproc.convexHull(contour, hull);
            if (maxArea < hull.size().area()) {
                maxArea = hull.size().area();
                largestIndex = count;
                Point[] contourArray = contour.toArray();
                Point[] hullPoints = new Point[hull.rows()];
                List<Integer> hullContourIdxList = hull.toList();
                for (int i = 0; i < hullContourIdxList.size(); i++) {
                    hullPoints[i] = contourArray[hullContourIdxList.get(i)];
                }
                biggestHull = new MatOfPoint(hullPoints);
            }
            count = count + 1;
        }
        hullList.add(biggestHull);

        //Draw Contour
        Random rng = new Random();
        drawing = Mat.zeros(detectedEdges.size(), CvType.CV_8UC3);
        color = new Scalar(rng.nextInt(256), rng.nextInt(256), rng.nextInt(256));
        Imgproc.drawContours(drawing, contours, largestIndex, color);
        Imgproc.drawContours(drawing, hullList, 0, color);
    }

    public Mat doMasking() {
        //Create Mask
        double h = drawing.size().height;
        double w = drawing.size().width;
        src.copyTo(dst, drawing);
        Mat mask = Mat.zeros((int) h + 2, (int) w + 2, CvType.CV_8U);
        Imgproc.floodFill(drawing, mask, new Point(0, 0), new Scalar(255, 255, 255));
        color = new Scalar(0, 0, 0);
        Imgproc.drawContours(drawing, contours, largestIndex, color);
        Imgproc.drawContours(drawing, hullList, 0, color);

        //Apply Masking
        out = new Mat();
        Core.bitwise_and(src, drawing, out);
        out_2 = new Mat();
        Core.bitwise_xor(src, out, out_2);
        return out_2;
    }

    public void doClustering() {
        //Create New Matrix to Convert BGR to CIE LAB
        image_lab = new Mat();

        Imgproc.cvtColor(out_2, image_lab, Imgproc.COLOR_BGR2Lab);
        //ambil pixel bukan hitam

        List<double[]> temp = new ArrayList<>();
        for (int i = 0; i < image_lab.rows(); i++) {
            for (int j = 0; j < image_lab.cols(); j++) {
                double[] value = image_lab.get(i, j);
                if (value[0] != 0.0 && value[1] != 128.0 && value[2] != 128.0) {
                    temp.add(value);
                }
            }
        }

        //Convert from ArrayList to Matrix
        Mat filtered_pixel = new Mat(1, temp.size(), CvType.CV_8UC3);
        for (int i = 0; i < temp.size(); i++) {
            filtered_pixel.put(0, i, temp.get(i));
        }

        //Reshape to 2D matrix
        samples = filtered_pixel.reshape(1, filtered_pixel.cols() * filtered_pixel.rows());

        Mat samples32f = new Mat();
        samples.convertTo(samples32f, CvType.CV_32F);
        TermCriteria term = new TermCriteria(TermCriteria.COUNT, 100, 1);

        //Create Matrix to Put Labels
        labels = new Mat();
        //Create Matrix to Put Centers (Centroids)
        centers = new Mat();
        //Running K-Means Clustering Algorithm
        Core.kmeans(samples32f, this.cluster, labels, term, 1, Core.KMEANS_PP_CENTERS, centers);

        //Print All Centers
        if (logText != null) {
            logText.setText(logText.getText() + "\n" + "Centers: " + "\n" + centers.dump());
        }
        System.out.println("Centers: " + "\n" + centers.dump());

        //Calculate Intra Cluster Distance
        if (logText != null) {
            logText.setText(logText.getText() + "\n" + "==============================================");
        }
        System.out.println("==============================================");

        //Get Each L, a, and b Value On One Cluster
        intraTotalValue = new double[centers.rows()][2]; //[x][0] simppan total 'jarak euclidean' dari seluruh anggota cluster dengan centroid, x[0][1] simpan total anggota
        for (int i = 0; i < samples.rows(); i++) {
            double[] labCenterValueL = centers.get((int) (labels.get(i, 0))[0], 0);
            double[] labCenterValueA = centers.get((int) (labels.get(i, 0))[0], 1);
            double[] labCenterValueB = centers.get((int) (labels.get(i, 0))[0], 2);
            double distEuclid = Math.sqrt(Math.pow((samples.get(i, 0))[0] - labCenterValueL[0], 2)
                    + Math.pow((samples.get(i, 1))[0] - labCenterValueA[0], 2)
                    + Math.pow((samples.get(i, 2))[0] - labCenterValueB[0], 2));
            intraTotalValue[(int) (labels.get(i, 0))[0]][0] = intraTotalValue[(int) (labels.get(i, 0))[0]][0] + distEuclid;
            intraTotalValue[(int) (labels.get(i, 0))[0]][1] = intraTotalValue[(int) (labels.get(i, 0))[0]][1] + 1;
        }
//        System.out.println(intraTotalValue[0][0]);
//        System.out.println(intraTotalValue[0][1]);

        System.out.println("Intra Cluster Distance Value");
        if (logText != null) {
            logText.setText(logText.getText() + "\n" + "Intra Cluster Distance Value");
        }
        intraClusterDistanceValue = new double[centers.rows()];
        count_idx = 0;
        for (double[] tes : intraTotalValue) {
            intraClusterDistanceValue[count_idx] = tes[0] / tes[1];
            System.out.println(tes[0] / tes[1]);
            if (logText != null) {
                logText.setText(logText.getText() + "\n" + (tes[0] / tes[1]));
            }
            count_idx++;
        }
    }

    public double[][] findDominantColor() {
        Map<Integer, Double> dominantColor = new HashMap<Integer, Double>();
        //Find Dominant Color
        if (logText != null) {
            logText.setText(logText.getText() + "\n" + "==============================================");
            logText.setText(logText.getText() + "\n" + "Dominant Colors:");
        }
        System.out.println("==============================================");
        System.out.println("Dominant Colors:");
        double intraClusterDistance2 = 0;
        double labClusterValue2 = 0;
        double score = 0;
        for (int i = 0; i < centers.rows(); i++) {
            intraClusterDistance2 = Math.pow((1 / intraClusterDistanceValue[i]), 0.8);
            labClusterValue2 = Math.pow((intraTotalValue[i][1] / samples.rows()), 0.2);
            score = intraClusterDistance2 + labClusterValue2;
            dominantColor.put(i, score);
        }
        Map<Integer, Double> sorted = dominantColor.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .collect(Collectors.toMap(Map.Entry::getKey,
                        Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
        //Print sortedmap
        for (Map.Entry<Integer, Double> en : sorted.entrySet()) {
            if (logText != null) {
                logText.setText(logText.getText() + "\n" + "Key (Cluster) = " + en.getKey() + ", Value = " + en.getValue());
            }
            System.out.println("Key (Cluster) = " + en.getKey() + ", Value = " + en.getValue());
        }
        //Get 5 Dominant Color
        double[][] vector_image = new double[this.dominant][3];
        count_idx = 0;
        for (Map.Entry<Integer, Double> en : sorted.entrySet()) {
            if (count_idx == this.dominant) {
                break;
            }
            vector_image[count_idx][0] = (centers.get(en.getKey(), 0))[0];
            vector_image[count_idx][1] = (centers.get(en.getKey(), 1))[0];
            vector_image[count_idx][2] = (centers.get(en.getKey(), 2))[0];
            count_idx++;
        }

        //Debug and Print the 5 Vector Image
        count_idx = 0;
        for (double[] tes : vector_image) {
            if (logText != null) {
                logText.setText(logText.getText() + "\n" + "vektor: " + (count_idx + 1) + "\n" + tes[0] + "\n" + tes[1] + "\n" + tes[2] + "\n============================");
            }
            System.out.println("vektor: " + (count_idx + 1));
            System.out.println(tes[0]);
            System.out.println(tes[1]);
            System.out.println(tes[2]);
            System.out.println("============================");
            count_idx++;
        }
        return vector_image;
    }

    public void doPermutation(int a[], int size, int n) {
        if (size == 1) {
            //do calculate euclidean
            double totalRes = 0.0;
            for (int i = 0; i < a.length; i++) {
                totalRes += Math.sqrt(Math.pow(vectorTest[i][0] - vectorTrain[a[i]][0], 2)
                        + Math.pow(vectorTest[i][1] - vectorTrain[a[i]][1], 2)
                        + Math.pow(vectorTest[i][2] - vectorTrain[a[i]][2], 2));
            }
            if (totalRes < maxRes) {
                maxRes = totalRes;
            }
            //Uncommand to see the Permutation for each vector in every image
            //System.out.printf("Permutation-%d result: %.3f --- bestRes: %.3f\n",count_idx,totalRes,maxRes);
            count_idx++;
        }

        for (int i = 0; i < size; i++) {
            doPermutation(a, size - 1, n);

            if (size % 2 == 1) {
                int temp = a[0];
                a[0] = a[size - 1];
                a[size - 1] = temp;
            } else {
                int temp = a[i];
                a[i] = a[size - 1];
                a[size - 1] = temp;
            }
        }
    }

    public double doClassification(double[][] vectorTest, double[][] vectorTrain) {
        maxRes = Double.MAX_VALUE;
        this.vectorTest = vectorTest;
        this.vectorTrain = vectorTrain;
        count_idx = 0;
        int[] index = new int[this.dominant];
        for (int i = 0; i < index.length; i++) {
            index[i] = i;
        }
        doPermutation(index, index.length, index.length);
        return maxRes;
    }

    public ArrayList<String> loadDataTraining(File[] list) {
        ArrayList<String> alDataTrain = new ArrayList<>();
        for (File x : list) {
            if (x.isDirectory()) {
                if (x.getAbsolutePath().contains("ManggaMatang") || x.getAbsolutePath().contains("ManggaMentah")) {
                    alDataTrain.addAll(loadDataTraining(x.listFiles()));
                }
            } else {
                String test = x.getAbsolutePath();
                String str = "";
                for (int i = 0; i < test.length(); i++) {
                    if (test.charAt(i) == '\\') {
                        str += "/";
                    } else {
                        str += test.charAt(i) + "";
                    }
                }
                alDataTrain.add(str);
            }
        }
        return alDataTrain;
    }

    public ArrayList<String> loadDataTest(File[] list) {
        ArrayList<String> alDataTest = new ArrayList<>();
        for (File x : list) {
            if (x.isDirectory()) {
                if (x.getAbsolutePath().contains("Matang") || x.getAbsolutePath().contains("Mentah")) {
                    alDataTest.addAll(loadDataTraining(x.listFiles()));
                }
            } else {
                String test = x.getAbsolutePath();
                String str = "";
                for (int i = 0; i < test.length(); i++) {
                    if (test.charAt(i) == '\\') {
                        str += "/";
                    } else {
                        str += test.charAt(i) + "";
                    }
                }
                alDataTest.add(str);
            }
        }
        return alDataTest;
    }

    public double[][] extractFeature(String pathTest, int typeOfFolder, int typeToShowImage) {
        String imagePath = pathTest;
        src = Imgcodecs.imread(imagePath);
        if (src.empty()) {
            System.out.println("Empty image: " + imagePath);
            System.exit(0);
        }
        // Create and set up the window.
        if (typeToShowImage > 0) {
            if (typeOfFolder > 0) {
                System.out.println("Data Test Result:");
                frame = new JFrame("Processing Image");

                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                // Set up the content pane.
                Image img = HighGui.toBufferedImage(src);

                addComponentsToPane(frame.getContentPane(), img);
                // Use the content pane's default BorderLayout. No need for
                // setLayout(new BorderLayout());
                // Display the window.
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        }

        doCannyEdgeDetection();
        doDilation();
        drawContours();
        doMasking();
        doClustering();
        double[][] res = findDominantColor();

        //done
        Image img = HighGui.toBufferedImage(out_2);
        if (imgLabel != null && frame != null) {
            imgLabel.setIcon(new ImageIcon(img));
            frame.repaint();
        }

        //Debug
        //Display Square Color of Centroid (OPTIONAL, Uncommand to See the Result)
//        for (int i = 0; i < featureVector.length; i++) {
//            Scalar lab = new Scalar(featureVector[i]);
//            Mat square = new Mat(50,50,16,lab);
//            Mat converted = new Mat();
//            Imgproc.cvtColor(square, converted, Imgproc.COLOR_Lab2BGR);
//            
//            String[] temp = pathTest.split("/");
//            
//            if(type==0){
//                originalFrame = new JFrame("Square Image - Train Data:"+temp[temp.length-1]);
//            } else originalFrame = new JFrame("Square Image - Test Data:"+temp[temp.length-1]);
//            
//            originalFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//            
//            // Set up the content pane.
//            Image img = HighGui.toBufferedImage(converted);
//
//            addComponentsToPane(originalFrame.getContentPane(), img);
//            // Use the content pane's default BorderLayout. No need for
//            // setLayout(new BorderLayout());
//            // Display the window.
//            originalFrame.pack();
//            originalFrame.setLocationRelativeTo(null);
//            originalFrame.setVisible(true);
//            
//        }
        return res;
    }
}
