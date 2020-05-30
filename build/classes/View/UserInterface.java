/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Controller.Controller;
import java.awt.Color;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import org.opencv.core.Core;

/**
 *
 * @author Michael Walah
 * @NPM 2014730019
 */
public class UserInterface extends javax.swing.JFrame {

    private String folderTrainPath = new String();
    private String folderTestPath = new String();
    private String imageTestPath = new String();
    private String imageTrainingPath = new String();
    private ImageIcon icon = new ImageIcon();
    private ImageIcon scaledIcon = new ImageIcon();

    /**
     * Creates new form NewJFrame
     */
    public UserInterface() {
        initComponents();
        buttonGroup.add(radioButtonFolder);
        buttonGroup.add(radioButtonImage);
        selectRadioButton();
        setKeyListener();
        setKeyListenerImage();
        folderTestField.setEditable(false);
        folderTrainField.setEditable(false);
        imageTestField.setEditable(false);
        logText1.setEditable(false);
    }

    private void setKeyListener() {
        threshodField.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                checkInput();
            }
        });
        clusterField.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                checkInput();
            }
        });
        dominantField.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                checkInput();
            }
        });
        kField.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                checkInput();
            }
        });
        loopProgramField.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                checkInput();
            }
        });
    }
    
    private void setKeyListenerImage() {
        threshodField.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                checkInputImage();
            }
        });
        clusterField.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                checkInputImage();
            }
        });
        dominantField.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                checkInputImage();
            }
        });
        kField.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                checkInputImage();
            }
        });
    }

    private void selectRadioButton() {
        if (!(radioButtonFolder.isSelected() && radioButtonImage.isSelected())) {
            browseFolderTest.setEnabled(false);
            browseFolderTrain.setEnabled(false);
            browseImageTest.setEnabled(false);
            predictButton.setEnabled(false);
            loopProgramField.setEnabled(false);
            loopProgramField.setEditable(false);
            folderTrainField.setEnabled(false);
            folderTestField.setEnabled(false);
            imageTestField.setEnabled(false);
        }
        if (radioButtonFolder.isSelected()) {
            browseImageTest.setEnabled(false);
            browseFolderTest.setEnabled(true);
            browseFolderTrain.setEnabled(true);
            predictButton.setEnabled(true);
            loopProgramField.setEnabled(true);
            loopProgramField.setEditable(true);
            folderTrainField.setEnabled(true);
            folderTestField.setEnabled(true);
            imageTestField.setEnabled(false);
        } else if (radioButtonImage.isSelected()) {
            browseFolderTest.setEnabled(false);
            browseFolderTrain.setEnabled(true);
            browseImageTest.setEnabled(true);
            predictButton.setEnabled(true);
            folderTrainField.setEnabled(true);
            folderTestField.setEnabled(false);
            imageTestField.setEnabled(true);
        }
    }

    private boolean checkInput() {
        try {
            int threshold = Integer.parseInt(threshodField.getText());
            int cluster = Integer.parseInt(clusterField.getText());
            int dominant = Integer.parseInt(dominantField.getText());
            int k = Integer.parseInt(kField.getText());
            int loopProgram = Integer.parseInt(loopProgramField.getText());
            if (threshold < 1 || cluster < 1 || dominant < 1 || k < 1 || loopProgram < 1) {
                String result = "Wrong input. Please insert an Integer number and greater than 0.";
                currentStatusText.setForeground(Color.RED);
                currentStatusText.setText(result.toUpperCase());
                return false;
            } 
            String result = "Not found error detected in input.";
            currentStatusText.setForeground(Color.BLACK);
            currentStatusText.setText(result.toUpperCase());
            return true;
        } catch (Exception e) {
            String result = "Wrong input. Please insert an Integer number and greater than 0.";
            currentStatusText.setForeground(Color.RED);
            currentStatusText.setText(result.toUpperCase());
            return false;
        }
    }
    
    private boolean checkInputImage(){
         try {
            int threshold = Integer.parseInt(threshodField.getText());
            int cluster = Integer.parseInt(clusterField.getText());
            int dominant = Integer.parseInt(dominantField.getText());
            int k = Integer.parseInt(kField.getText());
            if (threshold < 1 || cluster < 1 || dominant < 1 || k < 1){
                String result = "Wrong input. Please insert an Integer number and greater than 0.";
                currentStatusText.setForeground(Color.RED);
                currentStatusText.setText(result.toUpperCase());
                return false;
            }
            String result = "Not found error detected in input.";
            currentStatusText.setForeground(Color.BLACK);
            currentStatusText.setText(result.toUpperCase());
            return true;
         } catch (Exception e) {
            String result = "Wrong input. Please insert an Integer number and greater than 0.";
            currentStatusText.setForeground(Color.RED);
            currentStatusText.setText(result.toUpperCase());
            return false;
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup = new javax.swing.ButtonGroup();
        currentStatusText = new javax.swing.JLabel();
        predictButton = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        logText1 = new javax.swing.JTextArea();
        jLabel1 = new javax.swing.JLabel();
        threshodField = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        clusterField = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        dominantField = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        kField = new javax.swing.JTextField();
        radioButtonImage = new javax.swing.JRadioButton();
        radioButtonFolder = new javax.swing.JRadioButton();
        jLabel9 = new javax.swing.JLabel();
        loopProgramField = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        folderTrainField = new javax.swing.JTextField();
        browseFolderTrain = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        folderTestField = new javax.swing.JTextField();
        browseFolderTest = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        imageTestField = new javax.swing.JTextField();
        browseImageTest = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Klasifikasi Kematangan Mangofera Indica Berbasis Warna");

        currentStatusText.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        currentStatusText.setForeground(new java.awt.Color(255, 51, 51));
        currentStatusText.setText("IDLE");

        predictButton.setText("PREDICT");
        predictButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                predictButtonActionPerformed(evt);
            }
        });

        jLabel6.setText("Log");

        logText1.setColumns(20);
        logText1.setRows(5);
        jScrollPane2.setViewportView(logText1);

        jLabel1.setText("Canny Edge Detection Threshold Value");

        threshodField.setText("0");
        threshodField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                threshodFieldActionPerformed(evt);
            }
        });

        jLabel2.setText("Total Cluster");

        clusterField.setText("0");
        clusterField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clusterFieldActionPerformed(evt);
            }
        });

        jLabel3.setText("Dominant Color");

        dominantField.setText("0");
        dominantField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dominantFieldActionPerformed(evt);
            }
        });

        jLabel7.setText("Nearest Neighbor Value");

        kField.setText("0");
        kField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                kFieldActionPerformed(evt);
            }
        });

        buttonGroup.add(radioButtonImage);
        radioButtonImage.setText("Image Test (Only One Image)");
        radioButtonImage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radioButtonImageActionPerformed(evt);
            }
        });

        buttonGroup.add(radioButtonFolder);
        radioButtonFolder.setText("Folder Test");
        radioButtonFolder.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        radioButtonFolder.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radioButtonFolderActionPerformed(evt);
            }
        });

        jLabel9.setText("Looping For 1 Image Test");

        loopProgramField.setText("0");
        loopProgramField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loopProgramFieldActionPerformed(evt);
            }
        });

        jLabel4.setText("Folder Train");

        folderTrainField.setText("Path");
        folderTrainField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                folderTrainFieldActionPerformed(evt);
            }
        });

        browseFolderTrain.setText("Browse");
        browseFolderTrain.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                browseFolderTrainActionPerformed(evt);
            }
        });

        jLabel5.setText("Folder Test");

        folderTestField.setText("Path");
        folderTestField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                folderTestFieldActionPerformed(evt);
            }
        });

        browseFolderTest.setText("Browse");
        browseFolderTest.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                browseFolderTestActionPerformed(evt);
            }
        });

        jLabel8.setText("Image Test");

        imageTestField.setText("Path");
        imageTestField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                imageTestFieldActionPerformed(evt);
            }
        });

        browseImageTest.setText("Browse");
        browseImageTest.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                browseImageTestActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(currentStatusText, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                    .addComponent(jLabel3)
                                                    .addGap(151, 151, 151))
                                                .addGroup(layout.createSequentialGroup()
                                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 229, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(jLabel7))
                                                    .addGap(10, 10, 10)))
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(radioButtonFolder)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(loopProgramField, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(radioButtonImage)
                                            .addComponent(threshodField, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(dominantField, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(clusterField, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(kField, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel4)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(folderTestField, javax.swing.GroupLayout.PREFERRED_SIZE, 333, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(browseFolderTest, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jLabel8)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(folderTrainField, javax.swing.GroupLayout.PREFERRED_SIZE, 333, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(browseFolderTrain, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(132, 132, 132)
                                        .addComponent(predictButton, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(imageTestField, javax.swing.GroupLayout.PREFERRED_SIZE, 333, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(browseImageTest, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 21, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel9)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addGap(422, 422, 422))
                            .addComponent(jScrollPane2))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(threshodField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addGap(19, 19, 19)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(clusterField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(dominantField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(kField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(radioButtonFolder)
                            .addComponent(radioButtonImage))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel9)
                            .addComponent(loopProgramField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 69, Short.MAX_VALUE)
                        .addComponent(jLabel4)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(folderTrainField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(browseFolderTrain))
                        .addGap(18, 18, 18)
                        .addComponent(jLabel5)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(folderTestField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(browseFolderTest))
                        .addGap(18, 18, 18)
                        .addComponent(jLabel8)
                        .addGap(28, 28, 28)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(imageTestField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(browseImageTest))
                        .addGap(18, 18, 18)
                        .addComponent(predictButton, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane2))
                .addGap(18, 18, 18)
                .addComponent(currentStatusText, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void clusterFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clusterFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_clusterFieldActionPerformed

    private void browseFolderTrainActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_browseFolderTrainActionPerformed
        JFileChooser chooser = new JFileChooser();
        chooser.setCurrentDirectory(new java.io.File("data-train"));
        chooser.setDialogTitle("Choose Folder For Data Train");
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        chooser.setAcceptAllFileFilterUsed(false);
        if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            this.folderTrainPath = chooser.getSelectedFile().getPath();
            this.folderTrainField.setText(folderTrainPath);
            currentStatusText.setText("Train folder import : SUCCESS");
        } else {
            currentStatusText.setText("Train folder import : FAIL");
        }
    }//GEN-LAST:event_browseFolderTrainActionPerformed

    private void predictButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_predictButtonActionPerformed

        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                if (radioButtonFolder.isSelected()) {
                    boolean status = checkInput();
                    if (status) {
                        Controller main = new Controller();

                        int threshold = Integer.parseInt(threshodField.getText());
                        int cluster = Integer.parseInt(clusterField.getText());
                        int dominant = Integer.parseInt(dominantField.getText());
                        int k = Integer.parseInt(kField.getText());
                        int loopProgram = Integer.parseInt(loopProgramField.getText());

                        main.runnerForGUIFolder(threshold, cluster, dominant, k, loopProgram, logText1, folderTestPath, currentStatusText, folderTrainPath);
                    } else {
                        currentStatusText.setText("Please check all input before 'PREDICT'");
                    }
                } else if (radioButtonImage.isSelected()) {
                    boolean status2 = checkInputImage();
                    if (status2) {
                        Controller main = new Controller();

                        int threshold = Integer.parseInt(threshodField.getText());
                        int cluster = Integer.parseInt(clusterField.getText());
                        int dominant = Integer.parseInt(dominantField.getText());
                        int k = Integer.parseInt(kField.getText());

                        main.runnerForGUIImage(threshold, cluster, dominant, k, logText1, imageTestPath, currentStatusText, folderTrainPath);
                    } else {
                        currentStatusText.setText("Please check all input before 'PREDICT'");
                    }
                }
            }
        });
    }//GEN-LAST:event_predictButtonActionPerformed

    private void threshodFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_threshodFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_threshodFieldActionPerformed

    private void kFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_kFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_kFieldActionPerformed

    private void browseFolderTestActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_browseFolderTestActionPerformed
        JFileChooser chooser = new JFileChooser();
        chooser.setCurrentDirectory(new java.io.File("data-test"));
        chooser.setDialogTitle("Choose Folder For Data Test");
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        chooser.setAcceptAllFileFilterUsed(false);
            if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                this.folderTestPath = chooser.getSelectedFile().getPath();
                this.folderTestField.setText(folderTestPath);
                currentStatusText.setText("Test folder import : SUCCESS");
            } else {
                currentStatusText.setText("Test folder import : FAIL");
            }
    }//GEN-LAST:event_browseFolderTestActionPerformed

    private void dominantFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dominantFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_dominantFieldActionPerformed

    private void folderTestFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_folderTestFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_folderTestFieldActionPerformed

    private void folderTrainFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_folderTrainFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_folderTrainFieldActionPerformed

    private void radioButtonImageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radioButtonImageActionPerformed
        // TODO add your handling code here:
        selectRadioButton();
        radioButtonImage.addActionListener(browseImageTest.getAction());
        radioButtonFolder.addActionListener(browseFolderTrain.getAction());
        loopProgramField.setEditable(false);
        loopProgramField.setEnabled(false);
    }//GEN-LAST:event_radioButtonImageActionPerformed

    private void radioButtonFolderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radioButtonFolderActionPerformed
        // TODO add your handling code here:
        selectRadioButton();
        radioButtonFolder.addActionListener(browseFolderTrain.getAction());
        radioButtonFolder.addActionListener(browseFolderTest.getAction());
    }//GEN-LAST:event_radioButtonFolderActionPerformed

    private void imageTestFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_imageTestFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_imageTestFieldActionPerformed

    private void browseImageTestActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_browseImageTestActionPerformed
        // TODO add your handling code here:
        JFileChooser chooser = new JFileChooser();
        chooser.setCurrentDirectory(new java.io.File("data-test"));
        chooser.addChoosableFileFilter(new ImageExtension());
        chooser.setAcceptAllFileFilterUsed(false);

        int option = chooser.showOpenDialog(null);
        if (option == JFileChooser.APPROVE_OPTION) {
            this.imageTestPath = chooser.getSelectedFile().getAbsolutePath();
            this.imageTestField.setText(imageTestPath);
            currentStatusText.setText("Image test import : SUCCESS");
        } else {
            currentStatusText.setText("Image test import : FAIL");
        }
    }//GEN-LAST:event_browseImageTestActionPerformed

    private void loopProgramFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loopProgramFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_loopProgramFieldActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;

                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(UserInterface.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(UserInterface.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(UserInterface.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(UserInterface.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new UserInterface().setVisible(true);
            }
        });

    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton browseFolderTest;
    private javax.swing.JButton browseFolderTrain;
    private javax.swing.JButton browseImageTest;
    private javax.swing.ButtonGroup buttonGroup;
    private javax.swing.JTextField clusterField;
    private javax.swing.JLabel currentStatusText;
    private javax.swing.JTextField dominantField;
    private javax.swing.JTextField folderTestField;
    private javax.swing.JTextField folderTrainField;
    private javax.swing.JTextField imageTestField;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField kField;
    private javax.swing.JTextArea logText1;
    private javax.swing.JTextField loopProgramField;
    private javax.swing.JButton predictButton;
    private javax.swing.JRadioButton radioButtonFolder;
    private javax.swing.JRadioButton radioButtonImage;
    private javax.swing.JTextField threshodField;
    // End of variables declaration//GEN-END:variables
}
