/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Algorithm.ImageProcessing;
import Algorithm.Main;
import com.sun.javafx.property.adapter.PropertyDescriptor;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.LayoutManager;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import javafx.stage.FileChooser;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.plaf.basic.BasicButtonListener;
import org.opencv.core.Core;

/**
 *
 * @author Michael Walah
 * @NPM 2014730019
 */
public class UserInterface extends javax.swing.JFrame {
    
    String folderTrainPath = new String();
    String folderTestPath = new String();
    String imageTestPath = new String();

    /**
     * Creates new form NewJFrame
     */
    public UserInterface() {
        initComponents();
        setKeyListener();
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
    }

    private boolean checkInput() {
        try {
            int threshold = Integer.parseInt(threshodField.getText());
            int cluster = Integer.parseInt(clusterField.getText());
            int dominant = Integer.parseInt(dominantField.getText());
            int k = Integer.parseInt(kField.getText());
            if (threshold < 1 || cluster < 1 || dominant < 1 || k < 1) {
                String result = "Wrong input. Please inser a number and greater than 0.";
                currentStatusText.setForeground(Color.RED);
                currentStatusText.setText(result.toUpperCase());
                return false;
            }
            String result = "Not found error detected in input.";
            currentStatusText.setForeground(Color.BLACK);
            currentStatusText.setText(result.toUpperCase());
            return true;
        } catch (Exception e) {
            String result = "Wrong input. Please inser a number and greater than 0.";
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

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        threshodField = new javax.swing.JTextField();
        clusterField = new javax.swing.JTextField();
        dominantField = new javax.swing.JTextField();
        folderTrainField = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        currentStatusText = new javax.swing.JLabel();
        predictButton = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        logText1 = new javax.swing.JTextArea();
        jLabel7 = new javax.swing.JLabel();
        kField = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        folderTestField = new javax.swing.JTextField();
        jButton2 = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        imageTestField = new javax.swing.JTextField();
        jButton3 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("Threshold");

        jLabel2.setText("Cluster");

        jLabel3.setText("Dominant Color");

        jLabel4.setText("Folder Train");

        threshodField.setText("0");
        threshodField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                threshodFieldActionPerformed(evt);
            }
        });

        clusterField.setText("0");
        clusterField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clusterFieldActionPerformed(evt);
            }
        });

        dominantField.setText("0");
        dominantField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dominantFieldActionPerformed(evt);
            }
        });

        folderTrainField.setText("Path");
        folderTrainField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                folderTrainFieldActionPerformed(evt);
            }
        });

        jButton1.setText("Browse");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

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

        jLabel7.setText("Nearest Neighbor");

        kField.setText("0");
        kField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                kFieldActionPerformed(evt);
            }
        });

        jLabel5.setText("Folder Test");

        folderTestField.setText("Path");
        folderTestField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                folderTestFieldActionPerformed(evt);
            }
        });

        jButton2.setText("Browse");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel8.setText("Image Test");

        imageTestField.setText("Path");
        imageTestField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                imageTestFieldActionPerformed(evt);
            }
        });

        jButton3.setText("Browse");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(137, 137, 137)
                                .addComponent(predictButton, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel5)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(imageTestField)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(currentStatusText, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(folderTrainField, javax.swing.GroupLayout.DEFAULT_SIZE, 327, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(folderTestField, javax.swing.GroupLayout.DEFAULT_SIZE, 327, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel4)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addGroup(layout.createSequentialGroup()
                                            .addComponent(jLabel3)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 107, Short.MAX_VALUE)
                                            .addComponent(dominantField, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(layout.createSequentialGroup()
                                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(jLabel2)
                                                .addComponent(jLabel1))
                                            .addGap(138, 138, 138)
                                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                .addComponent(threshodField, javax.swing.GroupLayout.Alignment.TRAILING)
                                                .addComponent(clusterField, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 203, Short.MAX_VALUE)))
                                        .addGroup(layout.createSequentialGroup()
                                            .addComponent(jLabel7)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(kField, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addComponent(jLabel8))
                                .addGap(0, 0, Short.MAX_VALUE)))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 375, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(threshodField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(clusterField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(dominantField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3))
                        .addGap(28, 28, 28)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(kField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(folderTrainField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(folderTestField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(imageTestField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(predictButton, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(currentStatusText, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 444, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(16, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void clusterFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clusterFieldActionPerformed
        // TODO add your handling code here:
        clusterField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent de) {
                warning();
            }

            @Override
            public void removeUpdate(DocumentEvent de) {
                warning();
            }

            @Override
            public void changedUpdate(DocumentEvent de) {
                warning();
            }

            public void warning() {
                if (Integer.parseInt(clusterField.getText()) <= 0) {
                    JOptionPane.showMessageDialog(null, "Wrong Input Cluster Value", "Error Message", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }//GEN-LAST:event_clusterFieldActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        JFileChooser chooser = new JFileChooser();
        chooser.setCurrentDirectory(new java.io.File("."));
        chooser.setDialogTitle("Choose Folder For Data Test");
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        chooser.setAcceptAllFileFilterUsed(false);
        if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            this.folderTrainPath = chooser.getSelectedFile().getPath();
            this.folderTrainField.setText(folderTrainPath);
            currentStatusText.setText("Train folder import : SUCCESS");
        } else {
            currentStatusText.setText("Train folder import : FAIL");
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void predictButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_predictButtonActionPerformed

        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                boolean status = checkInput();
                if (status) {
                    Main main = new Main();

                    int threshold = Integer.parseInt(threshodField.getText());
                    int cluster = Integer.parseInt(clusterField.getText());
                    int dominant = Integer.parseInt(dominantField.getText());
                    int k = Integer.parseInt(kField.getText());

                    main.runnerForGUI(threshold, cluster, dominant, k, logText1, folderTestPath, currentStatusText, folderTrainPath);
                } else {
                    currentStatusText.setText("Please check all input before 'PREDICT'.");
                }
            }
        });
    }//GEN-LAST:event_predictButtonActionPerformed

    private void threshodFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_threshodFieldActionPerformed
        // TODO add your handling code here:
        threshodField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent de) {
                warning();
            }

            @Override
            public void removeUpdate(DocumentEvent de) {
                warning();
            }

            @Override
            public void changedUpdate(DocumentEvent de) {
                warning();
            }

            public void warning() {
                if (Integer.parseInt(threshodField.getText()) <= 0) {
                    JOptionPane.showMessageDialog(null, "Wrong Input Canny Edge Detection Threshold Value", "Error Message", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }//GEN-LAST:event_threshodFieldActionPerformed

    private void folderTrainFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_folderTrainFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_folderTrainFieldActionPerformed

    private void kFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_kFieldActionPerformed
        // TODO add your handling code here:
        kField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent de) {
                warning();
            }

            @Override
            public void removeUpdate(DocumentEvent de) {
                warning();
            }

            @Override
            public void changedUpdate(DocumentEvent de) {
                warning();
            }

            public void warning() {
                if (Integer.parseInt(kField.getText()) <= 0) {
                    JOptionPane.showMessageDialog(null, "Wrong input K-Nearest Neighbor Value", "Error Message", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }//GEN-LAST:event_kFieldActionPerformed

    private void folderTestFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_folderTestFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_folderTestFieldActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        JFileChooser chooser = new JFileChooser();
        chooser.setCurrentDirectory(new java.io.File("."));
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
    }//GEN-LAST:event_jButton2ActionPerformed

    private void dominantFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dominantFieldActionPerformed
        // TODO add your handling code here:
        dominantField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent de) {
                warning();
            }

            @Override
            public void removeUpdate(DocumentEvent de) {
                warning();
            }

            @Override
            public void changedUpdate(DocumentEvent de) {
                warning();
            }

            public void warning() {
                if (Integer.parseInt(dominantField.getText()) <= 0) {
                    JOptionPane.showMessageDialog(null, "Wrong Input Total Dominant Color", "Error Message", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }//GEN-LAST:event_dominantFieldActionPerformed

    private void imageTestFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_imageTestFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_imageTestFieldActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        JFileChooser chooser = new JFileChooser();
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
    }//GEN-LAST:event_jButton3ActionPerformed

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
            java.util.logging.Logger.getLogger(UserInterface.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(UserInterface.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(UserInterface.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(UserInterface.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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
    private javax.swing.JTextField clusterField;
    private javax.swing.JLabel currentStatusText;
    private javax.swing.JTextField dominantField;
    private javax.swing.JTextField folderTestField;
    private javax.swing.JTextField folderTrainField;
    private javax.swing.JTextField imageTestField;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField kField;
    private javax.swing.JTextArea logText1;
    private javax.swing.JButton predictButton;
    private javax.swing.JTextField threshodField;
    // End of variables declaration//GEN-END:variables
}
