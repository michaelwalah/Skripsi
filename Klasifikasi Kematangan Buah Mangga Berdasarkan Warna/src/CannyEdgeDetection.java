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
    private JFrame frame;
    private JLabel imgLabel;
    
    public CannyEdgeDetection() {
        String imagePath = "D:/Campus/Semester 12/Skripsi/Skripsi Sekarang/Program Skripsi/Klasifikasi Kematangan Buah Mangga Berdasarkan Warna/mangga-foto-rev2-dv/ManggaMATANG/kondisi-ideal/mangga-matang-ideal2.jpg";
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
    }
    
    private void addComponentsToPane(Container pane, Image img) {
        if (!(pane.getLayout() instanceof BorderLayout)) {
            pane.add(new JLabel("Container doesn't use BorderLayout!"));
            return;
        }

        JPanel sliderPanel = new JPanel();
        sliderPanel.setLayout(new BoxLayout(sliderPanel, BoxLayout.PAGE_AXIS));

        sliderPanel.add(new JLabel("Min Threshold:"));
        JSlider slider = new JSlider(0, MAX_LOW_THRESHOLD, 0);
        slider.setMajorTickSpacing(10);
        slider.setMinorTickSpacing(5);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        slider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                JSlider source = (JSlider) e.getSource();
                lowThresh = source.getValue();
                update();
            }
        });
        sliderPanel.add(slider);

        pane.add(sliderPanel, BorderLayout.PAGE_START);
        imgLabel = new JLabel(new ImageIcon(img));
        pane.add(imgLabel, BorderLayout.CENTER);
    }
    
    private void update() {
        Mat greyImage = new Mat();
        Imgproc.cvtColor(src, greyImage, Imgproc.COLOR_BGR2GRAY);
        Imgproc.GaussianBlur(greyImage, srcBlur, BLUR_SIZE, 10);
        Imgproc.Canny(srcBlur, detectedEdges, lowThresh, lowThresh * RATIO, KERNEL_SIZE, false);
        //dst = new Mat(src.size(), CvType.CV_8UC3, Scalar.all(0));
        //dst = dst.setTo(new Scalar(0));
        dst = new Mat();
        double h = src.size().height;
        double w = src.size().width;
        System.out.println(h+","+w);
        src.copyTo(dst, detectedEdges);
        Mat mask = Mat.zeros((int)h+2, (int)w+2, CvType.CV_8U);
        // dilate to fill gaps
        Imgproc.dilate(dst, dst, new Mat(), new Point(-1, -1), 1);
        Imgproc.floodFill(dst,mask,new Point(0,0),new Scalar(128));
        //Mat invert_ff_img = dst.clone();
        Mat out = dst.clone();
        //Core.bitwise_not(dst, invert_ff_img);
        //Core.bitwise_or(invert_ff_img, src, out);
        //Imgproc.erode(dst, dst, new Mat(), new Point(-1, -1), 1);
        Image img = HighGui.toBufferedImage(out);
        imgLabel.setIcon(new ImageIcon(img));
        frame.repaint();
    }
}
