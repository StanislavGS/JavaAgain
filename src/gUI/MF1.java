/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gUI;

import IO.Read;
import cadObjects.CadObject;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author stanislav
 */
public class MF1 extends JPanel {

    JFrame frame;
    JProgressBar pbar;
    static final int MY_MINIMUM = 0;
    static final int MY_MAXIMUM = 100;

    public MF1() {
        pbar = new JProgressBar();
        pbar.setMinimum(MY_MINIMUM);
        pbar.setMaximum(MY_MAXIMUM);
        add(pbar);

        this.frame = new JFrame("Progress Bar Example");
        this.frame.setContentPane(this);
        frame.setJMenuBar(initMenuComponents());
        frame.pack();

    }

    private JMenuBar initMenuComponents() {

        javax.swing.JMenuItem fileOpenMenuItem;
        javax.swing.JMenuItem fileSaveAsMenuItem;
        javax.swing.JMenuItem fileSaveMenuItem;
        javax.swing.JMenu jMenu1;
        javax.swing.JMenu jMenu2;
        javax.swing.JMenu jMenu3;
        javax.swing.JMenuBar jMenuBar1;

        fileOpenMenuItem = new javax.swing.JMenuItem();
        fileSaveMenuItem = new javax.swing.JMenuItem();
        fileSaveAsMenuItem = new javax.swing.JMenuItem();
        jMenu1 = new javax.swing.JMenu();
        jMenu2 = new javax.swing.JMenu();
        jMenu3 = new javax.swing.JMenu();
        jMenuBar1 = new javax.swing.JMenuBar();

        fileOpenMenuItem.setText("Open");
        fileSaveMenuItem.setText("Save");
        fileSaveAsMenuItem.setText("Save as...");

        fileOpenMenuItem.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fileOpenMenuItemActionPerformed(evt);
            }
        });
        fileSaveAsMenuItem.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fileSaveAsMenuItemActionPerformed(evt);
            }
        });

        jMenu1.add(fileOpenMenuItem);
        jMenu1.add(fileSaveMenuItem);
        jMenu1.add(fileSaveAsMenuItem);

        jMenu1.setText("File");
        jMenu2.setText("Edit");
        jMenu3.setText("jMenu3");

        jMenuBar1.add(jMenu1);
        jMenuBar1.add(jMenu2);
        jMenuBar1.add(jMenu3);

        return jMenuBar1;
    }

//    public void updateBar(int newValue) {
//        pbar.setValue(newValue);
//    }
    public static void main(String args[]) {

        final MF1 it = new MF1();

        it.frame.setVisible(true);

//        for (int i = MY_MINIMUM; i <= MY_MAXIMUM; i++) {
//            final int percent = i;
//            try {
//                SwingUtilities.invokeLater(new Runnable() {
//                    public void run() {
//                        it.updateBar(percent);
//                    }
//                });
//                java.lang.Thread.sleep(100);
//            } catch (InterruptedException e) {
//                ;
//            }
//        }
    }

    private void fileOpenMenuItemActionPerformed(ActionEvent evt) {
        final JFileChooser fc = new JFileChooser();
        fc.addChoosableFileFilter(new FileNameExtensionFilter("Cad files", "cad"));
        int returnVal = fc.showOpenDialog(this);
        if (fc != null && fc.getSelectedFile().getAbsolutePath() != null) {
            CadObject obj = new CadObject();

            Read rd = new Read();
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    try {
                        rd.readCad(fc.getSelectedFile().getAbsolutePath(), pbar);
                    } catch (IOException ex) {
                        Logger.getLogger(MF1.class.getName()).log(Level.SEVERE, null, ex);
//                            JOptionPane.showMessageDialog(null, "Can't read file!" + ex.getMessage(),
//                        "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            });

            obj = rd.getcObj();

        }
    }

    private void fileSaveAsMenuItemActionPerformed(ActionEvent evt) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
