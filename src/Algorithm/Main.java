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
import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;
import javax.swing.JTextArea;
//import javax.swing.JLabel;
import org.opencv.core.*;

public class Main {

    JTextArea logText;

    public void runnerForGUI(int threshold, int cluster, int dominant, JTextArea logText) {
        logText.setText("Menjalankan Program");
        this.logText = logText;
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

        //Create Object from Class ImageProcessing
        ImageProcessing imgProc = new ImageProcessing(threshold, cluster, dominant, logText);

        logText.setText(logText.getText() + "\n" + "Melakukan Data Train");
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

        logText.setText(logText.getText() + "\n" + "Data Train Selesai");
        logText.setText(logText.getText() + "\n" + "Melakukan Data Test");

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
            dominantColorTrain.add(imgProc.extractFeature(trainData[i][0], 0));
        }

        for (int i = 0; i < testData.length; i++) {
            dominantColorTest.add(imgProc.extractFeature(testData[i][0], 1));
        }

        //classification
        int k = 5;

        for (int i = 0; i < dominantColorTest.size(); i++) {
            Map<Integer, Double> classificationRes = new HashMap<>();
            logText.setText(logText.getText() + "\n" + "Classification computation result for test-" + i);
            System.out.println("Classification computation result for test-" + i);
            for (int j = 0; j < dominantColorTrain.size(); j++) {
                double res = imgProc.doClassification(dominantColorTest.get(i), dominantColorTrain.get(j));
                double res3f = Math.round(res * 1000.0) / 1000.0;
                logText.setText(logText.getText() + "\n" + "Pair of test-" + i + " with train-" + j + ": " + res3f + " --- train status: " + trainData[j][1]);
                System.out.printf("Pair of test-%d with train-%d: %.3f --- train status: %s\n", i, j, res, trainData[j][1]);
                classificationRes.put(j, res);
            }
            Map<Integer, Double> sortedRes = classificationRes.entrySet().stream()
                    .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                    .collect(Collectors.toMap(Map.Entry::getKey,
                            Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));

            int limit = 0;
            System.out.println(sortedRes.size());
            int matang = 0;
            int mentah = 0;
            double totalNilaiMatang = 0.0;
            double totalNilaiMentah = 0.0;
            for (Map.Entry<Integer, Double> entry : sortedRes.entrySet()) {
                if (limit >= sortedRes.size() - k) {
                    if (trainData[entry.getKey()][1].equalsIgnoreCase("matang")) {
                        matang++;
                        totalNilaiMatang += entry.getValue();
                    } else {
                        mentah++;
                        totalNilaiMentah += entry.getValue();
                    }
                }
                limit++;
            }
            String[] temp = testData[i][0].split("/");
            if (matang > mentah) {
                logText.setText(logText.getText() + "\n" + "Gambar test-" + temp[temp.length - 1] + ": matang");
                System.out.println("Gambar test-" + temp[temp.length - 1] + ": matang");
            } else if (matang < mentah) {
                logText.setText(logText.getText() + "\n" + "Gambar test-" + temp[temp.length - 1] + ": mentah");
                System.out.println("Gambar test-" + temp[temp.length - 1] + ": mentah");
            } else {
                if (totalNilaiMatang > totalNilaiMentah) {
                    logText.setText(logText.getText() + "\n" + "Gambar test-" + temp[temp.length - 1] + ": matang");
                    System.out.println("Gambar test-" + temp[temp.length - 1] + ": matang");
                } else if (totalNilaiMatang < totalNilaiMentah) {
                    logText.setText(logText.getText() + "\n" + "Gambar test-" + temp[temp.length - 1] + ": mentah");
                    System.out.println("Gambar test-" + temp[temp.length - 1] + ": mentah");
                } else {
                    logText.setText(logText.getText() + "\n" + "Keajaiban terjadi, tidak dapat diputuskan");
                    System.out.println("Keajaiban terjadi, tidak dapat diputuskan"); //not possible
                }
            }
        }
    }

    public static void main(String[] args) throws Exception {
        // load the OpenCV native library
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        //Create Object from Class ImageProcessing
        Scanner sc = new Scanner(System.in);
        System.out.print("Threshold : ");
        int threshold = sc.nextInt();
        System.out.print("Cluster : ");
        int cluster = sc.nextInt();
        System.out.print("Dominant : ");
        int dominant = sc.nextInt();
        ImageProcessing imgProc = new ImageProcessing(threshold, cluster, dominant);

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
            dominantColorTrain.add(imgProc.extractFeature(trainData[i][0], 0));
        }

        for (int i = 0; i < testData.length; i++) {
            dominantColorTest.add(imgProc.extractFeature(testData[i][0], 1));
        }

        //classification
        int k = 5;

        for (int i = 0; i < dominantColorTest.size(); i++) {
            Map<Integer, Double> classificationRes = new HashMap<>();
            System.out.println("Classification computation result for test-" + i);
            for (int j = 0; j < dominantColorTrain.size(); j++) {
                double res = imgProc.doClassification(dominantColorTest.get(i), dominantColorTrain.get(j));
                System.out.printf("Pair of test-%d with train-%d: %.3f --- train status: %s\n", i, j, res, trainData[j][1]);
                classificationRes.put(j, res);
            }
            Map<Integer, Double> sortedRes = classificationRes.entrySet().stream()
                    .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                    .collect(Collectors.toMap(Map.Entry::getKey,
                            Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));

            int limit = 0;
            System.out.println(sortedRes.size());
            int matang = 0;
            int mentah = 0;
            double totalNilaiMatang = 0.0;
            double totalNilaiMentah = 0.0;
            for (Map.Entry<Integer, Double> entry : sortedRes.entrySet()) {
                if (limit >= sortedRes.size() - k) {
                    if (trainData[entry.getKey()][1].equalsIgnoreCase("matang")) {
                        matang++;
                        totalNilaiMatang += entry.getValue();
                    } else {
                        mentah++;
                        totalNilaiMentah += entry.getValue();
                    }
                }
                limit++;
            }
            String[] temp = testData[i][0].split("/");
            if (matang > mentah) {
                System.out.println("Gambar test-" + temp[temp.length - 1] + ": matang");
            } else if (matang < mentah) {
                System.out.println("Gambar test-" + temp[temp.length - 1] + ": mentah");
            } else {
                if (totalNilaiMatang > totalNilaiMentah) {
                    System.out.println("Gambar test-" + temp[temp.length - 1] + ": matang");
                } else if (totalNilaiMatang < totalNilaiMentah) {
                    System.out.println("Gambar test-" + temp[temp.length - 1] + ": mentah");
                } else {
                    System.out.println("Keajaiban terjadi, tidak dapat diputuskan"); //not possible
                }
            }
        }
    }
}//end class
