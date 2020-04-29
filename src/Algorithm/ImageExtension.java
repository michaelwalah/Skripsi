/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Algorithm;

import java.io.File;
import javax.swing.filechooser.FileFilter;

/**
 *
 * @author Michael
 */
public class ImageExtension extends FileFilter {

    public final static String JPEG = "jpeg";
    public final static String JPG = "jpg";
    public final static String PNG = "png";

    @Override
    public boolean accept(File file) {
        if (file.isDirectory()) {
         return true;
        }
        
        String extension = getExtension(file);
        if (extension != null) {
            if (extension.equals(JPEG) ||
            extension.equals(JPG) ||
            extension.equals(PNG)) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    @Override
    public String getDescription() {
        return "Image Only";
    }
    
    public String getExtension(File file){
        String ext = null;
        String symbol = file.getName();
        int i = symbol.lastIndexOf(".");
        
        if (i > 0 && i < symbol.length() - 1) {
            ext = symbol.substring(i+1).toLowerCase();
        }
        return ext;
    }
}
