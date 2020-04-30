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
import java.awt.Image;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.opencv.core.*;
import org.opencv.highgui.HighGui;
import org.opencv.imgcodecs.Imgcodecs;

public class Main {

    JTextArea logText;

    public void runnerForGUIFolder(int threshold, int cluster, int dominant, int k, int loopProgram, JTextArea logText, String path_test, JLabel statusText, String path_train) {
        //Create blank workbook
        XSSFWorkbook workbook = new XSSFWorkbook();

        //Create a blank sheet
        XSSFSheet sheet = workbook.createSheet("Evaluation");

        //Data needs to be written (Object[])
        Map<String, Object[]> data = new TreeMap<>();
        data.put("Checker", new Object[]{"File Name", "Folder", "Prediction", "Waktu"});

//        //Map data structure to save the value of image name, image folder, and prediction
//        Map<String, String[]> toExcell = new HashMap<>();
        //Looping for how many process need to every image test 
        for (int a = 0; a < loopProgram; a++) {
            //Record starting time program process
            long timeStart = System.currentTimeMillis();
            //Program start running
            logText.setText("Program Running");
            System.out.println("Program Running");
            this.logText = logText;
            System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

            //Create Object from Class ImageProcessing
            ImageProcessing imgProc = new ImageProcessing(threshold, cluster, dominant, logText);

            logText.setText(logText.getText() + "\n" + "Load Data Train");
            System.out.println("Load Data Train");
            //Load all train data with its classification (real)
            File[] files_train = new File(path_train).listFiles();
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
            statusText.setText("Load Data Training Success");
            logText.setText(logText.getText() + "\n" + "Load Data Train Done");
            System.out.println("Load Data Train Done");

            logText.setText(logText.getText() + "\n" + "");
            System.out.println("");

            logText.setText(logText.getText() + "\n" + "Load Data Test");
            System.out.println("Load Data Test");
            //Load all test data with its classification (real)
            File[] files_test = new File(path_test).listFiles();
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
            statusText.setText("Load Data Test Success");
            logText.setText(logText.getText() + "\n" + "Load Data Test Done");
            System.out.println("Load Data Test Done");

            //create array list to store dominant color of all train data and test data
            ArrayList<double[][]> dominantColorTrain = new ArrayList<>();
            ArrayList<double[][]> dominantColorTest = new ArrayList<>();

            //Code to show all image in Data Test Folder
//        JFrame testDataFrame;
//        Mat[] imageTest = new Mat[allDataTest.size()];
//        for (int i = 0; i < testData.length; i++) {
//            String[] imageTestName = testData[i][0].split("/");
//            imageTest[i] = Imgcodecs.imread(allDataTest.get(i));
//            testDataFrame = new JFrame("Original Image: " + imageTestName[imageTestName.length - 1]);
//            testDataFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//            Image originalImage = HighGui.toBufferedImage(imageTest[i]);
//            imgProc.addComponentsToPane(testDataFrame.getContentPane(), originalImage);
//            testDataFrame.pack();
//            testDataFrame.setLocationRelativeTo(null);
//            testDataFrame.setVisible(true);
//        }
//        logText.setText(logText.getText() + "\n" + "");
//        System.out.println("");
            logText.setText(logText.getText() + "\n" + "Do Processing On Data Train");
            System.out.println("Do Processing On Data Train");
            logText.setText(logText.getText() + "\n" + "Data Train Result:");
            System.out.println("Data Train Result:");
            //extract dominant color from train data
            for (int i = 0; i < trainData.length; i++) {
                dominantColorTrain.add(imgProc.extractFeature(trainData[i][0], 0, 0));
            }
            logText.setText(logText.getText() + "\n" + "Processing Data Train Success");
            statusText.setText("Processing Data Train Success");
            System.out.println("Processing Data Train Succcess");

            logText.setText(logText.getText() + "\n" + "");
            System.out.println("");

            logText.setText(logText.getText() + "\n" + "Do Processing On Data Test");
            System.out.println("Do Processing On Data Test");
            logText.setText(logText.getText() + "\n" + "Data Test Result:");
            System.out.println("Data Test Result:");
            for (int i = 0; i < testData.length; i++) {
                dominantColorTest.add(imgProc.extractFeature(testData[i][0], 1, 0));
            }
            logText.setText(logText.getText() + "\n" + "Processing Data Test Success");
            statusText.setText("Processing Data Test Success");
            System.out.println("Processing Data Test Succcess");

            logText.setText(logText.getText() + "\n" + "");
            System.out.println("");

            //classification
            logText.setText(logText.getText() + "\n" + "Do Classification");
            System.out.println("Do Classification");
            logText.setText(logText.getText() + "\n" + "Result:");
            System.out.println("Result:");
            statusText.setText("Classification");

            //Record end time program process
            long timeEnd = 0;
            //Process running time program
            long timeProgram = 0;
            long second = 0;

            for (int i = 0; i < dominantColorTest.size(); i++) {
                Map<Integer, Double> classificationRes = new HashMap<>();
                logText.setText(logText.getText() + "\n" + "Classification computation result for test - " + i);
                System.out.println("Classification computation result for test - " + i);
                for (int j = 0; j < dominantColorTrain.size(); j++) {
                    double res = imgProc.doClassification(dominantColorTest.get(i), dominantColorTrain.get(j));
//                double res3f = Math.round(res * 1000.0) / 1000.0;
//                logText.setText(logText.getText() + "\n" + "Pair of test-" + i + " with train-" + j + ": " + res3f + " --- train status: " + trainData[j][1]);
//                System.out.printf("Pair of test-%d with train-%d: %.3f --- train status: %s\n", i, j, res, trainData[j][1]);
                    classificationRes.put(j, res);
                }
                Map<Integer, Double> sortedRes = classificationRes.entrySet().stream()
                        .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                        .collect(Collectors.toMap(Map.Entry::getKey,
                                Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));

                int limit = 0;
                int matang = 0;
                int mentah = 0;
                double totalNilaiMatang = 0.0;
                double totalNilaiMentah = 0.0;
                Mat[] imgCandidateNearestNeighbor = new Mat[k];
                int index = 0;
                for (Map.Entry<Integer, Double> entry : sortedRes.entrySet()) {
                    if (limit >= sortedRes.size() - k) {
                        imgCandidateNearestNeighbor[index] = Imgcodecs.imread(trainData[entry.getKey()][0]);//Variable use for take path of the nearest neighbor image
                        if (trainData[entry.getKey()][1].equalsIgnoreCase("matang")) {
                            matang++;
                            totalNilaiMatang += entry.getValue();
                        } else {
                            mentah++;
                            totalNilaiMentah += entry.getValue();
                        }
                        index++;
                    }
                    limit++;
                }

                timeEnd = System.currentTimeMillis();
                timeProgram = timeEnd - timeStart;
                second = (timeProgram / 1000) % 60;

                String[] imageName = testData[i][0].split("/");
                if (matang > mentah) {
                    logText.setText(logText.getText() + "\n" + "Image test for " + imageName[imageName.length - 1] + ", " + imageName[imageName.length - 2] + " : matang");
                    System.out.println("Image test for " + imageName[imageName.length - 1] + ", " + imageName[imageName.length - 2] + " : matang");
                    if (!data.containsKey(imageName[imageName.length - 1])) {
                        data.put(imageName[imageName.length - 1], new Object[]{imageName[imageName.length - 1], imageName[imageName.length - 2], "Matang", Long.toString(second)});
                    } else {
                        data.replace(imageName[imageName.length - 1], new Object[]{imageName[imageName.length - 1], imageName[imageName.length - 2], "Matang", Long.toString(second)});
                    }
                } else if (matang < mentah) {
                    logText.setText(logText.getText() + "\n" + "Image test for " + imageName[imageName.length - 1] + ", " + imageName[imageName.length - 2] + " : mentah");
                    System.out.println("Image test for " + imageName[imageName.length - 1] + ", " + imageName[imageName.length - 2] + " : mentah");
                    if (!data.containsKey(imageName[imageName.length - 1])) {
                        data.put(imageName[imageName.length - 1], new Object[]{imageName[imageName.length - 1], imageName[imageName.length - 2], "Mentah", Long.toString(second)});
                    } else {
                        data.replace(imageName[imageName.length - 1], new Object[]{imageName[imageName.length - 1], imageName[imageName.length - 2], "Matang", Long.toString(second)});
                    }
                } else {
                    if (totalNilaiMatang > totalNilaiMentah) {
                        logText.setText(logText.getText() + "\n" + "Image test for " + imageName[imageName.length - 1] + ", " + imageName[imageName.length - 2] + " : matang");
                        System.out.println("Image test for " + imageName[imageName.length - 1] + ", " + imageName[imageName.length - 2] + " : matang");
                        if (!data.containsKey(imageName[imageName.length - 1])) {
                            data.put(imageName[imageName.length - 1], new Object[]{imageName[imageName.length - 1], imageName[imageName.length - 2], "Matang", Long.toString(second)});
                        } else {
                            data.replace(imageName[imageName.length - 1], new Object[]{imageName[imageName.length - 1], imageName[imageName.length - 2], "Matang", Long.toString(second)});
                        }
                    } else if (totalNilaiMatang < totalNilaiMentah) {
                        logText.setText(logText.getText() + "\n" + "Image test for " + imageName[imageName.length - 1] + ", " + imageName[imageName.length - 2] + " : mentah");
                        System.out.println("Image test for " + imageName[imageName.length - 1] + ", " + imageName[imageName.length - 2] + " : mentah");
                        if (!data.containsKey(imageName[imageName.length - 1])) {
                            data.put(imageName[imageName.length - 1], new Object[]{imageName[imageName.length - 1], imageName[imageName.length - 2], "Mentah", Long.toString(second)});
                        } else {
                            data.replace(imageName[imageName.length - 1], new Object[]{imageName[imageName.length - 1], imageName[imageName.length - 2], "Matang", Long.toString(second)});
                        }
                    } else {
                        logText.setText(logText.getText() + "\n" + "Can't Classified");
                        System.out.println("Can't Classified"); //not possible
                    }
                }
                //Code to show 3 Nearest Neighbor Image for Every Image
//            JFrame nearestNeighborFrame;
//            for (int j = 0; j < 3; j++) {
//                // buat buffered image, load ke javafx
//                nearestNeighborFrame = new JFrame("Nearest Neighbor Image - " + j + " for: " + imageName[imageName.length - 1]);
//                nearestNeighborFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//                Image imgNearestNeighbor = HighGui.toBufferedImage(imgCandidateNearestNeighbor[j]);
//                imgProc.addComponentsToPane(nearestNeighborFrame.getContentPane(), imgNearestNeighbor);
//                nearestNeighborFrame.pack();
//                nearestNeighborFrame.setLocationRelativeTo(null);
//                nearestNeighborFrame.setVisible(true);
//            }
                logText.setText(logText.getText() + "\n" + "Classification Success");
                System.out.println("Classification Success");
                logText.setText(logText.getText() + "\n" + "");
                System.out.println("");
                //Print the running time program on log UI and terminal
                logText.setText(logText.getText() + "\n" + "Running Time of Program " + second + " second");
                System.out.println("Running Time of Program " + second + " second");
                logText.setText(logText.getText() + "\n" + "");
                System.out.println("");
            }
        }

        //Iterate over data and write to sheet
        Set<String> keyset = data.keySet();
        int rownum = 0;
        for (String key : keyset) {
            Row row = sheet.createRow(rownum++);
            Object[] objArr = data.get(key);
            int cellnum = 0;
            for (Object obj : objArr) {
                Cell cell = row.createCell(cellnum++);
                if (obj instanceof String) {
                    cell.setCellValue((String) obj);
                } else if (obj instanceof Integer) {
                    cell.setCellValue((Integer) obj);
                }
            }
        }
        try {
            //Write the workbook in file system
            FileOutputStream out = new FileOutputStream(new File("Classification Result.xlsx"));
            workbook.write(out);
            out.close();
            logText.setText(logText.getText() + "\n" + "Classification Result.xlsx written successfully on disk");
            System.out.println("Classification Result.xlsx written successfully on disk");
        } catch (Exception e) {
            e.printStackTrace();
        }
        statusText.setText("Done");
        logText.setText(logText.getText() + "\n" + "");
        System.out.println("");
        logText.setText(logText.getText() + "\n" + "Program Done");
        System.out.println("Program Done");
    }

    public void runnerForGUIImage(int threshold, int cluster, int dominant, int k, int loopProgram, JTextArea logText, String path_test, JLabel statusText, String path_train) {
        for (int a = 0; a < loopProgram; a++) {
            //Program starting time
            long timeStart = System.currentTimeMillis();
            logText.setText("Program Running");
            System.out.println("Program Running");
            this.logText = logText;
            System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

            //Create Object from Class ImageProcessing
            ImageProcessing imgProc = new ImageProcessing(threshold, cluster, dominant, logText);

            logText.setText(logText.getText() + "\n" + "Load Data Train");
            System.out.println("Load Data Train");
            //Load all train data with its classification (real)
            File[] files_train = new File(path_train).listFiles();
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
            statusText.setText("Load Data Training Success");
            logText.setText(logText.getText() + "\n" + "Load Data Train Done");
            System.out.println("Load Data Train Done");

            logText.setText(logText.getText() + "\n" + "");
            System.out.println("");

            logText.setText(logText.getText() + "\n" + "Load Data Test");
            System.out.println("Load Data Test");
            File files_test = new File(path_test);
            String imageTest = files_test.getAbsolutePath();
            System.out.println("Load Data Test Success" + "\n");
            logText.setText(logText.getText() + "\n" + "Load Data Test Success" + "\n");

            //create array list to store dominant color of all train data and test data
            ArrayList<double[][]> dominantColorTrain = new ArrayList<>();
            ArrayList<double[][]> dominantColorTest = new ArrayList<>();

            //Code to show all image in Data Test Folder
            Mat imgTestData = Imgcodecs.imread(imageTest);
            if (imgTestData.empty()) {
                System.out.println("Empty image: " + files_test);
                System.out.println("Can't find path");
                System.exit(0);
            }
            JFrame testDataFrame = new JFrame("Original Image");
            testDataFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            Image originalImage = HighGui.toBufferedImage(imgTestData);
            imgProc.addComponentsToPane(testDataFrame.getContentPane(), originalImage);
            testDataFrame.pack();
            testDataFrame.setLocationRelativeTo(null);
            testDataFrame.setVisible(true);
            logText.setText(logText.getText() + "\n" + "");
            System.out.println("");

            //Image Processing for data train
            logText.setText(logText.getText() + "\n" + "Do Processing On Data Train");
            System.out.println("Do Processing On Data Train");
            logText.setText(logText.getText() + "\n" + "Data Train Result:");
            System.out.println("Data Train Result:");
            //extract dominant color from train data
            for (int i = 0; i < trainData.length; i++) {
                dominantColorTrain.add(imgProc.extractFeature(trainData[i][0], 0, 0));
            }
            logText.setText(logText.getText() + "\n" + "Processing Data Train Success");
            statusText.setText("Processing Data Train Success");
            System.out.println("Processing Data Train Succcess");

            logText.setText(logText.getText() + "\n" + "");
            System.out.println("");

            //Image Processing for data test
            logText.setText(logText.getText() + "\n" + "Do Processing On Data Test");
            System.out.println("Do Processing On Data Test");
            logText.setText(logText.getText() + "\n" + "Data Test Result:");
            System.out.println("Data Test Result:");
            dominantColorTest.add(imgProc.extractFeature(imageTest, 1, 1));
            logText.setText(logText.getText() + "\n" + "Processing Data Test Success");
            statusText.setText("Processing Data Test Success");
            System.out.println("Processing Data Test Succcess");

            logText.setText(logText.getText() + "\n" + "");
            System.out.println("");

            //classification
            logText.setText(logText.getText() + "\n" + "Do Classification");
            System.out.println("Do Classification");
            logText.setText(logText.getText() + "\n" + "Result:");
            System.out.println("Result:");
            statusText.setText("Classification");
            for (int i = 0; i < dominantColorTest.size(); i++) {
                Map<Integer, Double> classificationRes = new HashMap<>();
                logText.setText(logText.getText() + "\n" + "Classification computation result for test - " + i);
                System.out.println("Classification computation result for test - " + i);
                for (int j = 0; j < dominantColorTrain.size(); j++) {
                    double res = imgProc.doClassification(dominantColorTest.get(i), dominantColorTrain.get(j));
//                double res3f = Math.round(res * 1000.0) / 1000.0;
//                logText.setText(logText.getText() + "\n" + "Pair of test-" + i + " with train-" + j + ": " + res3f + " --- train status: " + trainData[j][1]);
//                System.out.printf("Pair of test-%d with train-%d: %.3f --- train status: %s\n", i, j, res, trainData[j][1]);
                    classificationRes.put(j, res);
                }
                Map<Integer, Double> sortedRes = classificationRes.entrySet().stream()
                        .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                        .collect(Collectors.toMap(Map.Entry::getKey,
                                Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
                int limit = 0;
                int matang = 0;
                int mentah = 0;
                double totalNilaiMatang = 0.0;
                double totalNilaiMentah = 0.0;
                Mat[] imgCandidateNearestNeighbor = new Mat[k];
                int index = 0;
                for (Map.Entry<Integer, Double> entry : sortedRes.entrySet()) {
                    if (limit >= sortedRes.size() - k) {
                        imgCandidateNearestNeighbor[index] = Imgcodecs.imread(trainData[entry.getKey()][0]);//Variable use for take path of the nearest neighbor image
                        if (trainData[entry.getKey()][1].equalsIgnoreCase("matang")) {
                            matang++;
                            totalNilaiMatang += entry.getValue();
                        } else {
                            mentah++;
                            totalNilaiMentah += entry.getValue();
                        }
                        index++;
                    }
                    limit++;
                }

                //Show classification result
                String[] imageName = imageTest.substring(133).split("/");
                if (matang > mentah) {
                    logText.setText(logText.getText() + "\n" + "Image test for " + imageName[imageName.length - 1] + ", " + imageTest.substring(126, 132) + " : matang");
                    System.out.println("Image test for " + imageName[imageName.length - 1] + ", " + imageTest.substring(126, 132) + " : matang");
                } else if (matang < mentah) {
                    logText.setText(logText.getText() + "\n" + "Image test for " + imageName[imageName.length - 1] + ", " + imageTest.substring(126, 132) + " : mentah");
                    System.out.println("Image test for " + imageName[imageName.length - 1] + ", " + imageTest.substring(126, 132) + " : mentah");
                } else {
                    if (totalNilaiMatang > totalNilaiMentah) {
                        logText.setText(logText.getText() + "\n" + "Image test for " + imageName[imageName.length - 1] + ", " + imageTest.substring(126, 132) + " : matang");
                        System.out.println("Image test for " + imageName[imageName.length - 1] + ", " + imageTest.substring(126, 132) + " : matang");
                    } else if (totalNilaiMatang < totalNilaiMentah) {
                        logText.setText(logText.getText() + "\n" + "Image test for " + imageName[imageName.length - 1] + ", " + imageTest.substring(126, 132) + " : mentah");
                        System.out.println("Image test for " + imageName[imageName.length - 1] + ", " + imageTest.substring(126, 132) + " : mentah");
                    } else {
                        logText.setText(logText.getText() + "\n" + "Can't Classified");
                        System.out.println("Can't Classified"); //not possible
                    }
                }

                //Code to show 3 Nearest Neighbor Image for 1 Image
                JFrame nearestNeighborFrame;
                for (int j = 0; j < 3; j++) {
                    // buat buffered image, load ke javafx
                    nearestNeighborFrame = new JFrame("Nearest Neighbor Image - " + (j + 1));
                    nearestNeighborFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    Image imgNearestNeighbor = HighGui.toBufferedImage(imgCandidateNearestNeighbor[j]);
                    imgProc.addComponentsToPane(nearestNeighborFrame.getContentPane(), imgNearestNeighbor);
                    nearestNeighborFrame.pack();
                    nearestNeighborFrame.setLocationRelativeTo(null);
                    nearestNeighborFrame.setVisible(true);
                }
                
                logText.setText(logText.getText() + "\n" + "Classification Success");
                System.out.println("Classification Success");
                logText.setText(logText.getText() + "\n" + "");
                System.out.println("");
                //Program end time
                long timeEnd = System.currentTimeMillis();
                long timeProgram = timeEnd - timeStart;
                //Calculate the program running time
                long second = (timeProgram / 1000) % 60;
                //Print the program running time on UI and terminal
                logText.setText(logText.getText() + "\n" + "Running Time of Program " + second + " second");
                System.out.println("Running Time of Program " + second + " second");
            }
            statusText.setText("Done");
            logText.setText(logText.getText() + "\n" + "Program Done");
            System.out.println("Program Done");
            logText.setText(logText.getText() + "\n" + "");
            System.out.println("");
        }
    }

    //Code To Run On Terminal
    //----Uncommand To See----
//    public static void main(String[] args) throws Exception {
//        // Load the OpenCV Native Library
//        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
//
//        System.out.println("Program Running");
//        //User Input for Image Processing
//        Scanner sc = new Scanner(System.in);
//        System.out.print("Canny Edge Detection Threshold Value: ");
//        int threshold = sc.nextInt();
//        System.out.print("Total Cluster: ");
//        int cluster = sc.nextInt();
//        System.out.print("Total Dominant Color: ");
//        int dominant = sc.nextInt();
//        System.out.print("Nearest Neighbor Value: ");
//        int nearestNeighbor = sc.nextInt();
//        System.out.println("Choose Type for Folder or File Test");
//        System.out.println("0 for File Test");
//        System.out.println("1 for Folder Test");
//        int mode = sc.nextInt();
//        System.out.println("");
//
//        while (!(mode == 0 || mode == 1)) {
//            System.out.println("Wrong Mode Input, Input Must 0 or 1");
//            System.out.print("Input Another Value to Choose: ");
//            mode = sc.nextInt();
//        }
//
//        System.out.println("Loop For 1 Image Test: ");
//        int loop = sc.nextInt();
//        System.out.println("");
//
//        for (int a = 0; a < loop; a++) {
//            //Program running time
//            long timeStart = System.currentTimeMillis();
//            
//            //Create Object from Class ImageProcessing
//            ImageProcessing imgProc = new ImageProcessing(threshold, cluster, dominant);
//
//            System.out.println("Load Data Train");
//            File[] files_train = new File("data-train").listFiles();
//            ArrayList<String> allDataTrain = new ArrayList<>();
//            allDataTrain = imgProc.loadDataTraining(files_train);
//            String[][] trainData = new String[allDataTrain.size()][2];
//            for (int i = 0; i < trainData.length; i++) {
//                trainData[i][0] = allDataTrain.get(i);
//                if (allDataTrain.get(i).contains("ManggaMatang")) {
//                    trainData[i][1] = "Matang";
//                } else {
//                    trainData[i][1] = "Mentah";
//                }
//            }
//            System.out.println("Load Data Train Success" + "\n");
//            if (mode == 0) {
//
//                //Load Test Data with Its Main (real)
//                System.out.print("Write the Directory of the Image: ");
//                String path = sc.next();
//                System.out.println("");
//
//                System.out.println("Load Data Test");
//                File files_test = new File(path);
//                String imageTest = files_test.getAbsolutePath();
//                System.out.println("Load Data Test Success" + "\n");
//
//                //Create Array List to Store Dominant Color of All Train Data and Test Data
//                ArrayList<double[][]> dominantColorTrain = new ArrayList<>();
//                ArrayList<double[][]> dominantColorTest = new ArrayList<>();
//
//                //Code to show all image in Data Test Folder
//                Mat imgTestData = Imgcodecs.imread(imageTest);
//                if (imgTestData.empty()) {
//                    System.out.println("Empty image: " + files_test);
//                    System.out.println("Can't find path");
//                    System.exit(0);
//                }
//                JFrame testDataFrame = new JFrame("Original Image");
//                testDataFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//                Image originalImage = HighGui.toBufferedImage(imgTestData);
//                imgProc.addComponentsToPane(testDataFrame.getContentPane(), originalImage);
//                testDataFrame.pack();
//                testDataFrame.setLocationRelativeTo(null);
//                testDataFrame.setVisible(true);
//
//                System.out.println("Do Processing On Data Train");
//                System.out.println("Data Train Result:");
//                //Extract Dominant Color from Train Data
//                for (int i = 0; i < trainData.length; i++) {
//                    dominantColorTrain.add(imgProc.extractFeature(trainData[i][0], 0, 0));
//                }
//                System.out.println("Processing Data Train Success" + "\n");
//
//                System.out.println("Do Processing On Data Test");
//                dominantColorTest.add(imgProc.extractFeature(imageTest, 1, 1));
//                System.out.println("Processing Data Test Success" + "\n");
//
//                //Classification
//                System.out.println("Do Main");
//                System.out.println("Result:");
//                for (int i = 0; i < dominantColorTest.size(); i++) {
//                    Map<Integer, Double> classificationRes = new HashMap<>();
//                    System.out.println("Main computation result for test-" + i);
//                    for (int j = 0; j < dominantColorTrain.size(); j++) {
//                        double res = imgProc.doClassification(dominantColorTest.get(i), dominantColorTrain.get(j));
////                System.out.printf("Pair of test-%d with train-%d: %.3f --- train status: %s\n", i, j, res, trainData[j][1]);
//                        classificationRes.put(j, res);
//                    }
//                    Map<Integer, Double> sortedRes = classificationRes.entrySet().stream()
//                            .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
//                            .collect(Collectors.toMap(Map.Entry::getKey,
//                                    Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
//
//                    int limit = 0;
//                    int matang = 0;
//                    int mentah = 0;
//                    double totalNilaiMatang = 0.0;
//                    double totalNilaiMentah = 0.0;
//                    Mat[] imgCandidateNearestNeighbor = new Mat[nearestNeighbor];
//                    int index = 0;
//                    for (Map.Entry<Integer, Double> entry : sortedRes.entrySet()) {
//                        if (limit >= sortedRes.size() - nearestNeighbor) {
//                            //Variable Use to Take Path of the Nearest Neighbor Picture
//                            imgCandidateNearestNeighbor[index] = Imgcodecs.imread(trainData[entry.getKey()][0]);
//                            if (trainData[entry.getKey()][1].equalsIgnoreCase("Matang")) {
//                                matang++;
//                                totalNilaiMatang += entry.getValue();
//                            } else {
//                                mentah++;
//                                totalNilaiMentah += entry.getValue();
//                            }
//                            index++;
//                        }
//                        limit++;
//                    }
//
//                    String[] imageName = imageTest.substring(133).split("/");
//                    if (matang > mentah) {
//                        System.out.println("Image test for " + imageName[imageName.length - 1] + ", " + imageTest.substring(126, 132) + " : Matang");
//                    } else if (matang < mentah) {
//                        System.out.println("Image test for " + imageName[imageName.length - 1] + ", " + imageTest.substring(126, 132) + " : Mentah");
//                    } else {
//                        if (totalNilaiMatang > totalNilaiMentah) {
//                            System.out.println("Image test for " + imageName[imageName.length - 1] + ", " + imageTest.substring(126, 132) + " : Matang");
//                        } else if (totalNilaiMatang < totalNilaiMentah) {
//                            System.out.println("Image test for " + imageName[imageName.length - 1] + ", " + imageTest.substring(126, 132) + " : Mentah");
//                        } else {
//                            System.out.println("Can't Classified"); //not possible
//                        }
//                    }
//
//                    //Code to show 3 Nearest Neighbor Image for Every Image
//                    JFrame nearestNeighborFrame;
//                    for (int j = 0; j < 3; j++) {
//                        // buat buffered image, load ke javafx
//                        nearestNeighborFrame = new JFrame("Nearest Neighbor Image - " + (j + 1));
//                        nearestNeighborFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//                        Image imgNearestNeighbor = HighGui.toBufferedImage(imgCandidateNearestNeighbor[j]);
//                        imgProc.addComponentsToPane(nearestNeighborFrame.getContentPane(), imgNearestNeighbor);
//                        nearestNeighborFrame.pack();
//                        nearestNeighborFrame.setLocationRelativeTo(null);
//                        nearestNeighborFrame.setVisible(true);
//                    }
//                }
//                System.out.println("Main Success");
//                long timeEnd = System.currentTimeMillis();
//                long timeProgram = timeEnd - timeStart;
//                long second = (timeProgram / 1000) % 60;
//                System.out.println("Running Time For Program: " + second + " second");
//                System.out.println("Program Done");
//            } else if (mode == 1) {
//                //Load All Test Data with Its Main (real)
//                System.out.println("Load Data Test");
//                File[] files_test = new File("data-test").listFiles();
//                ArrayList<String> allDataTest = new ArrayList<>();
//                allDataTest = imgProc.loadDataTest(files_test);
//                String[][] testData = new String[allDataTest.size()][2];
//                for (int i = 0; i < testData.length; i++) {
//                    testData[i][0] = allDataTest.get(i);
//                    if (allDataTest.get(i).contains("Matang")) {
//                        testData[i][1] = "Matang";
//                    } else {
//                        testData[i][1] = "Mentah";
//                    }
//                }
//                System.out.println("Load Data Test Success" + "\n");
//
//                //Create Array List to Store Dominant Color of All Train Data and Test Data
//                ArrayList<double[][]> dominantColorTrain = new ArrayList<>();
//                ArrayList<double[][]> dominantColorTest = new ArrayList<>();
//
//                //Code to show all image in Data Test Folder
//                //----Uncommand To See----
////            Mat[] imgTestData = new Mat[allDataTest.size()];
////            for (int i = 0; i < testData.length; i++) {
////                String[] imageTestName = testData[i][0].split("/");
////                imgTestData[i] = Imgcodecs.imread(allDataTest.get(i));
////                JFrame testDataFrame = new JFrame("Original Image: " + imageTestName[imageTestName.length - 1]);
////                testDataFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
////                Image originalImage = HighGui.toBufferedImage(imgTestData[i]);
////                imgProc.addComponentsToPane(testDataFrame.getContentPane(), originalImage);
////                testDataFrame.pack();
////                testDataFrame.setLocationRelativeTo(null);
////                testDataFrame.setVisible(true);
////            }
//                System.out.println("Do Processing On Data Train");
//                System.out.println("Data Train Result:");
//                //Extract Dominant Color from Train Data
//                for (int i = 0; i < trainData.length; i++) {
//                    dominantColorTrain.add(imgProc.extractFeature(trainData[i][0], 0, 0));
//                }
//                System.out.println("Processing Data Train Success" + "\n");
//
//                System.out.println("Do Processing On Data Test");
//                for (int i = 0; i < testData.length; i++) {
//                    dominantColorTest.add(imgProc.extractFeature(testData[i][0], 1, 0));
//                }
//                System.out.println("Processing Data Test Success" + "\n");
//
//                //Classification
//                System.out.println("Do Main");
//                System.out.println("Result:");
//                for (int i = 0; i < dominantColorTest.size(); i++) {
//                    Map<Integer, Double> classificationRes = new HashMap<>();
//                    System.out.println("Main computation result for test-" + i);
//                    for (int j = 0; j < dominantColorTrain.size(); j++) {
//                        double res = imgProc.doClassification(dominantColorTest.get(i), dominantColorTrain.get(j));
////                System.out.printf("Pair of test-%d with train-%d: %.3f --- train status: %s\n", i, j, res, trainData[j][1]);
//                        classificationRes.put(j, res);
//                    }
//                    Map<Integer, Double> sortedRes = classificationRes.entrySet().stream()
//                            .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
//                            .collect(Collectors.toMap(Map.Entry::getKey,
//                                    Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
//
//                    int limit = 0;
//                    int matang = 0;
//                    int mentah = 0;
//                    double totalNilaiMatang = 0.0;
//                    double totalNilaiMentah = 0.0;
//                    Mat[] imgCandidateNearestNeighbor = new Mat[nearestNeighbor];
//                    int index = 0;
//                    for (Map.Entry<Integer, Double> entry : sortedRes.entrySet()) {
//                        if (limit >= sortedRes.size() - nearestNeighbor) {
//                            //Variable Use to Take Path of the Nearest Neighbor Picture
//                            imgCandidateNearestNeighbor[index] = Imgcodecs.imread(trainData[entry.getKey()][0]);
//                            if (trainData[entry.getKey()][1].equalsIgnoreCase("Matang")) {
//                                matang++;
//                                totalNilaiMatang += entry.getValue();
//                            } else {
//                                mentah++;
//                                totalNilaiMentah += entry.getValue();
//                            }
//                            index++;
//                        }
//                        limit++;
//                    }
//
//                    String[] imageName = testData[i][0].split("/");
//                    if (matang > mentah) {
//                        System.out.println("Image test for " + imageName[imageName.length - 1] + ", " + imageName[imageName.length - 2] + " : Matang");
//                    } else if (matang < mentah) {
//                        System.out.println("Image test for " + imageName[imageName.length - 1] + ", " + imageName[imageName.length - 2] + " : Mentah");
//                    } else {
//                        if (totalNilaiMatang > totalNilaiMentah) {
//                            System.out.println("Image test for " + imageName[imageName.length - 1] + ", " + imageName[imageName.length - 2] + " : Matang");
//                        } else if (totalNilaiMatang < totalNilaiMentah) {
//                            System.out.println("Image test for " + imageName[imageName.length - 1] + ", " + imageName[imageName.length - 2] + " : Mentah");
//                        } else {
//                            System.out.println("Can't Classified"); //not possible
//                        }
//                    }
//
//                    //Code to show 3 Nearest Neighbor Image for Every Image
//                    //----Uncommand To See----
////                JFrame nearestNeighborFrame;
////                for (int j = 0; j < 3; j++) {
////                    // buat buffered image, load ke javafx
////                    nearestNeighborFrame = new JFrame("Nearest Neighbor Image " + j + " for: " + imageName[imageName.length - 1]);
////                    nearestNeighborFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
////                    Image imgNearestNeighbor = HighGui.toBufferedImage(imgCandidateNearestNeighbor[j]);
////                    imgProc.addComponentsToPane(nearestNeighborFrame.getContentPane(), imgNearestNeighbor);
////                    nearestNeighborFrame.pack();
////                    nearestNeighborFrame.setLocationRelativeTo(null);
////                    nearestNeighborFrame.setVisible(true);
////                }
//                }
//                System.out.println("Main Success");
//                long timeEnd = System.currentTimeMillis();
//                long timeProgram = timeEnd - timeStart;
//                long second = (timeProgram / 1000) % 60;
//                System.out.println("Running Time For Program: " + second + " second");
//                System.out.println("Program Done");
//            }
//        }
//    }
}//end class
