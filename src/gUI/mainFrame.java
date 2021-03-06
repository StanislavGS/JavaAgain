/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gUI;

import IO.Read;
import cadObjects.CadObject;
import java.awt.Component;
import java.io.IOException;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author stanislav
 */
public class mainFrame extends javax.swing.JFrame {

    /**
     * Creates new form mainFrame
     */
    private JPanel contentPane;

    public mainFrame() {
        initComponents();
//        jProgressBar1.setMinimum(0);
//        jProgressBar1.setMaximum(100);
//        jProgressBar1.setBorderPainted(rootPaneCheckingEnabled);
//        add(jProgressBar1);
        //Graphics g=new Line2D(new Point(5, 10),new Point(30, 20));
        //this.print(g);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        fileOpenMenuItem = new javax.swing.JMenuItem();
        fileSaveMenuItem = new javax.swing.JMenuItem();
        fileSaveAsMenuItem = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenu3 = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jMenu1.setText("File");

        fileOpenMenuItem.setText("Open");
        fileOpenMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fileOpenMenuItemActionPerformed(evt);
            }
        });
        jMenu1.add(fileOpenMenuItem);

        fileSaveMenuItem.setText("Save");
        jMenu1.add(fileSaveMenuItem);

        fileSaveAsMenuItem.setText("Save as...");
        fileSaveAsMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fileSaveAsMenuItemActionPerformed(evt);
            }
        });
        jMenu1.add(fileSaveAsMenuItem);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Edit");
        jMenuBar1.add(jMenu2);

        jMenu3.setText("jMenu3");
        jMenuBar1.add(jMenu3);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1080, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 534, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void fileOpenMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fileOpenMenuItemActionPerformed
        final JFileChooser fc = new JFileChooser();
        fc.addChoosableFileFilter(new FileNameExtensionFilter("Cad files", "cad"));
        int returnVal = fc.showOpenDialog(this);
        if (fc != null && fc.getSelectedFile().getAbsolutePath() != null) {

            CadObject obj = new CadObject();
//            JProgressBar jPrB = new JProgressBar();
//            jPrB.setMinimum(0);
//            jPrB.setMaximum(100);
//            jPrB.setUI(this);
//            this.add(jPrB);
//            jPrB.setVisible(true);
//            Component add = this.add(jPrB);
//this.remove(add);

//            this.contentPane = new JPanel();
//            contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
//            setContentPane(contentPane);
//            contentPane.setLayout(null);
//
//            JProgressBar pbar = new JProgressBar(0, 100);
//            pbar.setBounds(22, 77, 386, 27);
//            contentPane.add(pbar);
//            pbar.setValue(50);
////            this.add(pbar);
//            pbar.setVisible(true);
//            contentPane.setVisible(true);

            //Component[] cmpo=this.getContentPane().getComponents();//getLayout().addLayoutComponent("pbar", pbar);
            //this.contentPane.add(pbar);    Null pointer exception
            JProgressBar pbar =new JProgressBar(0,100);//JProgressBar) cmpo[0];
            pbar.setValue(50);
            setContentPane(pbar);
            pbar.setVisible(true);
            
            //pbar.repaint();
            
            this.repaint();
            this.getContentPane().repaint();
            try {
                Read rd = new Read();
                rd.readCad(fc.getSelectedFile().getAbsolutePath(), pbar);
                obj = rd.getcObj();
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "Can't read file!" + ex.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
            pbar.setVisible(false);
//            this.remove(jPrB);
        }
    }//GEN-LAST:event_fileOpenMenuItemActionPerformed

    private void fileSaveAsMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fileSaveAsMenuItemActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_fileSaveAsMenuItemActionPerformed

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
            java.util.logging.Logger.getLogger(mainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(mainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(mainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(mainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new mainFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem fileOpenMenuItem;
    private javax.swing.JMenuItem fileSaveAsMenuItem;
    private javax.swing.JMenuItem fileSaveMenuItem;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenuBar jMenuBar1;
    // End of variables declaration//GEN-END:variables
}
