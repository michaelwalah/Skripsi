
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Michael
 */
public class SharpnessEnhancement {
    private Mat src;
    private Mat dst;
    
    public SharpnessEnhancement(){
        src = Imgcodecs.imread("Gambar_Mangga_Gedong_Gincu_3.jpg");
        dst = new Mat(src.rows(), src.cols(), src.type());
    }
    
    public void filtering(){
        Imgproc.GaussianBlur(src, dst, new Size(0, 0), 10);
        Core.addWeighted(src, 1.5, dst, -0.5, 0, dst);
        
        //Write Output Image
        Imgcodecs.imwrite("Hasil_Gambar_Mangga_Gedong_Gincu_6.jpg", dst);
    }
}
