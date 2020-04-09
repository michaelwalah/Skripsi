/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Michael
 */
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Image;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.MatOfInt;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.highgui.HighGui;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

public class CannyEdgeDetection {
    private static final int MAX_LOW_THRESHOLD = 100;
    private static final int RATIO = 3;
    private static final int KERNEL_SIZE = 3;
    private static final Size BLUR_SIZE = new Size(3,3);
    private int lowThresh = 0;
    private Mat src;
    private Mat srcBlur = new Mat();
    private Mat detectedEdges = new Mat();
    private Mat dst = new Mat();
    private Mat image_lab;
    private JFrame frame;
    private JLabel imgLabel;
    private Random rng = new Random(12345);

    
    public CannyEdgeDetection() {
        String imagePath = "mangga-foto-rev2-dv/ManggaMentah/kondisi-kurang/mangga-mentah-kurang10.jpg";
        src = Imgcodecs.imread(imagePath);
        if (src.empty()) {
            System.out.println("Empty image: " + imagePath);
            System.exit(0);
        }

        // Create and set up the window.
        frame = new JFrame("Image Using Canny Edge Detection");
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
        update();
    }
    
    private void addComponentsToPane(Container pane, Image img) {
        if (!(pane.getLayout() instanceof BorderLayout)) {
            pane.add(new JLabel("Container doesn't use BorderLayout!"));
            return;
        }

        JPanel sliderPanel = new JPanel();
        sliderPanel.setLayout(new BoxLayout(sliderPanel, BoxLayout.PAGE_AXIS));

//        sliderPanel.add(new JLabel("Min Threshold:"));
//        JSlider slider = new JSlider(0, MAX_LOW_THRESHOLD, 0);
//        slider.setMajorTickSpacing(10);
//        slider.setMinorTickSpacing(5);
//        slider.setPaintTicks(true);
//        slider.setPaintLabels(true);
//        slider.addChangeListener(new ChangeListener() {
//            @Override
//            public void stateChanged(ChangeEvent e) {
//                JSlider source = (JSlider) e.getSource();
//                lowThresh = source.getValue();
//                update();
//            }
//        });
//        sliderPanel.add(slider);

//        pane.add(sliderPanel, BorderLayout.PAGE_START);
        imgLabel = new JLabel(new ImageIcon(img));
        pane.add(imgLabel, BorderLayout.CENTER);
    }
    
    private void update() {
        dst = new Mat(); //reset objek dst
        //pre processing
        Mat greyImage = new Mat();
        Imgproc.cvtColor(src, greyImage, Imgproc.COLOR_BGR2GRAY);//convert gambar ke grayscale
        Imgproc.GaussianBlur(greyImage, srcBlur, BLUR_SIZE, 100);//apply gaussian blur

        lowThresh = 50;
        int lowThreshXRatio = 50;
        //Imgproc.Canny(srcBlur, detectedEdges, lowThresh, lowThresh * RATIO, KERNEL_SIZE, false); //apply canny edge detection
        Imgproc.Canny(srcBlur, detectedEdges, lowThresh, lowThreshXRatio, KERNEL_SIZE, false);
        Imgproc.dilate(detectedEdges, detectedEdges, new Mat(), new Point(-1, -1), 1); //apply dilate for filling gaps
        
        List<MatOfPoint> contours = new ArrayList<>();
        Mat hierarchy = new Mat();
        Imgproc.findContours(detectedEdges, contours, hierarchy, Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);
        
        //find largest hull
        double maxArea = 0.0;
        MatOfPoint biggestHull = new MatOfPoint();
        int largestIndex = 0;
        int count = 0;
        List<MatOfPoint> hullList = new ArrayList<>();
        for (MatOfPoint contour : contours) {
            MatOfInt hull = new MatOfInt();
            Imgproc.convexHull(contour, hull);
            if(maxArea < hull.size().area()){
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
        
        //draw contour
        Mat drawing = Mat.zeros(detectedEdges.size(), CvType.CV_8UC3);
        Scalar color = new Scalar(rng.nextInt(256), rng.nextInt(256), rng.nextInt(256));
        Imgproc.drawContours(drawing, contours, largestIndex, color);
        Imgproc.drawContours(drawing, hullList, 0, color );
        
        //create mask
        double h = drawing.size().height;
        double w = drawing.size().width;
        src.copyTo(dst, drawing);
        Mat mask = Mat.zeros((int)h+2, (int)w+2, CvType.CV_8U);
        Imgproc.floodFill(drawing,mask,new Point(0,0),new Scalar(255,255,255));
        color = new Scalar(0, 0, 0);
        Imgproc.drawContours(drawing, contours, largestIndex, color);
        Imgproc.drawContours(drawing, hullList, 0, color );
        
        //apply masking
        Mat out = new Mat();
        Core.bitwise_and(src,drawing,out);
        Mat out_2 = new Mat();
        Core.bitwise_xor(src, out, out_2);
        
        //Create New Matrix to Convert BGR to CIE LAB
        image_lab = new Mat();
        
        Imgproc.cvtColor(out_2, image_lab, Imgproc.COLOR_BGR2Lab);
        
        //ambil pixel bukan hitam
        List<double[]> temp = new ArrayList<>();
        for(int i=0; i < image_lab.rows(); i++){
            for(int j=0; j< image_lab.cols(); j++){
                double[] tes = image_lab.get(i, j);
                if(tes[0]!=0.0 && tes[1]!=128.0 && tes[2]!=128.0){
                    temp.add(tes);
                }
            }
        }
        
        double[][] result = new double[temp.size()][];
        result = temp.toArray(result);
        //done
        Image img = HighGui.toBufferedImage(out_2);
        imgLabel.setIcon(new ImageIcon(img));
        frame.repaint();
    }
}