/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package lowerbound;

import com.mxgraph.view.mxGraph;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.*;
import javax.swing.JFileChooser;

/**
 *
 * @author luca
 */
public class form extends javax.swing.JFrame {


    
    int NUM_VERTICI, PARTENZA = 0;
    int SCREEN_WIDTH;
    int SCREEN_HEIGHT;
    final static int NULL_EDGE = -1;
    
    //double upperBound = 0;

    //costante moltiplcativa per ottenere l'upper bound dal lowerbound
    //double ro = 1.2;  //default value
    
    Settings parameters;
    Log l;
    LBGraph lbg;
    UBGraph ubg;
    
    /**
     * Creates new form form
     */
    public form() {
        super("TSP Programmazione Dinamica");
        initComponents();        
        
        SCREEN_WIDTH = PnlTsp.getWidth() - 50;
        SCREEN_HEIGHT = PnlTsp.getHeight() - 50;
        
        mxGraph g = new mxGraph();
        
        parameters = new Settings(SCREEN_WIDTH, SCREEN_HEIGHT);
        parameters.setAlwaysOnTop(true);
        parameters.setVisible(true);
        
        lbg = new LBGraph();
        ubg = new UBGraph();
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        PnlTsp = new javax.swing.JPanel();
        LblTSP = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        MenuProgram = new javax.swing.JMenu();
        MenuRun = new javax.swing.JMenuItem();
        menuSettings = new javax.swing.JMenuItem();
        jMenu1 = new javax.swing.JMenu();
        MenuLBGraph = new javax.swing.JMenuItem();
        MenuUBGraph = new javax.swing.JMenuItem();
        MenuLog = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        PnlTsp.setPreferredSize(new java.awt.Dimension(709, 563));

        javax.swing.GroupLayout PnlTspLayout = new javax.swing.GroupLayout(PnlTsp);
        PnlTsp.setLayout(PnlTspLayout);
        PnlTspLayout.setHorizontalGroup(
            PnlTspLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 708, Short.MAX_VALUE)
        );
        PnlTspLayout.setVerticalGroup(
            PnlTspLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 562, Short.MAX_VALUE)
        );

        LblTSP.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        LblTSP.setText("TSP");

        MenuProgram.setText("Program");
        MenuProgram.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        MenuRun.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        MenuRun.setText("Run");
        MenuRun.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MenuRunActionPerformed(evt);
            }
        });
        MenuProgram.add(MenuRun);

        menuSettings.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        menuSettings.setText("Settings");
        menuSettings.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuSettingsActionPerformed(evt);
            }
        });
        MenuProgram.add(menuSettings);

        jMenuBar1.add(MenuProgram);

        jMenu1.setText("View");
        jMenu1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        MenuLBGraph.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        MenuLBGraph.setText("LowerBound Graph");
        MenuLBGraph.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MenuLBGraphActionPerformed(evt);
            }
        });
        jMenu1.add(MenuLBGraph);

        MenuUBGraph.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        MenuUBGraph.setText("UpperBound Graph");
        MenuUBGraph.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MenuUBGraphActionPerformed(evt);
            }
        });
        jMenu1.add(MenuUBGraph);

        MenuLog.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        MenuLog.setText("Log");
        MenuLog.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MenuLogActionPerformed(evt);
            }
        });
        jMenu1.add(MenuLog);

        jMenuBar1.add(jMenu1);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(LblTSP, javax.swing.GroupLayout.PREFERRED_SIZE, 708, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(PnlTsp, javax.swing.GroupLayout.PREFERRED_SIZE, 708, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(LblTSP)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(PnlTsp, javax.swing.GroupLayout.PREFERRED_SIZE, 562, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void MenuRunActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MenuRunActionPerformed
                        
        //parte la schermata di log
        l = new Log();
        l.setVisible(true);
        TspSolver solver = new TspSolver(parameters.userUpperBound, parameters.NUM_VERTICI, PARTENZA, parameters.vertici, parameters.cost, l.getLog(), lbg.getPnlLB(), PnlTsp, parameters.num_iterazioni_LB, parameters.num_iterazioni_Alfa, parameters.alfaStart, parameters.alfaMin, lbg.getLblLB(), ubg.getLblUB(), LblTSP, ubg.getPnlUB(), parameters.isDebug(), parameters.num_iter_lbDebug, parameters.init_dimension_pool, parameters.tetto);
        solver.start();
        
    }//GEN-LAST:event_MenuRunActionPerformed

    private void menuSettingsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuSettingsActionPerformed
        parameters.setVisible(true);
    }//GEN-LAST:event_menuSettingsActionPerformed

    private void MenuUBGraphActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MenuUBGraphActionPerformed
        ubg.setVisible(true);
    }//GEN-LAST:event_MenuUBGraphActionPerformed

    private void MenuLBGraphActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MenuLBGraphActionPerformed
        lbg.setVisible(true);
    }//GEN-LAST:event_MenuLBGraphActionPerformed

    private void MenuLogActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MenuLogActionPerformed
        // TODO add your handling code here:
        l.setVisible(true);        
    }//GEN-LAST:event_MenuLogActionPerformed

            
    
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
            java.util.logging.Logger.getLogger(form.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(form.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(form.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(form.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new form().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel LblTSP;
    private javax.swing.JMenuItem MenuLBGraph;
    private javax.swing.JMenuItem MenuLog;
    private javax.swing.JMenu MenuProgram;
    private javax.swing.JMenuItem MenuRun;
    private javax.swing.JMenuItem MenuUBGraph;
    private javax.swing.JPanel PnlTsp;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem menuSettings;
    // End of variables declaration//GEN-END:variables
}
