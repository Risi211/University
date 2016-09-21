/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ppp;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;

/**
 *
 * @author luca
 */
public class form extends javax.swing.JFrame {

    int MIN_TEMP = 50;
    int MAX_TEMP = 75;
    int FREQ = 200; //periodo in ms in cui viene generata la temperatura    
    Subscription s;
    Observable<Double> AverageTempStream;
    
    //default values
    double soglia = 70; //gradi
    double tempo_allarme = 100; //ms
    double max_variation = 2.0; //gradi
    
    int cont = 0;
    double media_prec;
    
    long time = 0;
    ExeMonitor flag;
    /**
     * Creates new form form
     */
    public form() {
        
        super("Controllo Temperatura");
        
        initComponents();                
        
        flag = new ExeMonitor();
        
                //creo sensori
                Observable<Double> TempStream1 = Observable.create((Subscriber<? super Double> subscriber) -> {
                        new AsyncGenerator(subscriber, FREQ, new TempSensor(MIN_TEMP,MAX_TEMP,0.01)).start();
                });	

                Observable<Double> TempStream2 = Observable.create((Subscriber<? super Double> subscriber) -> {
                        new AsyncGenerator(subscriber, FREQ, new TempSensor(MIN_TEMP,MAX_TEMP,0.01)).start();
                });

                Observable<Double> TempStream3 = Observable.create((Subscriber<? super Double> subscriber) -> {
                        new AsyncGenerator(subscriber, FREQ, new TempSensor(MIN_TEMP,MAX_TEMP,0.01)).start();
                });                

              final Helper h = new Helper();   
              h.cont_element = 0;

                AverageTempStream =
                        Observable.zip(TempStream1,TempStream2, TempStream3, 
                            (t1, t2, t3) -> 
                            {                               
                                return (t1+t2+t3)/3;
                            })
                             .filter((a) ->
                                    {
                                        //solo il primo valore generato va in questo controllo
                                        //tutti gli altri vanno nell'else
                                        if(h.cont_element == 0 && a < MAX_TEMP && a > MIN_TEMP)
                                        {
                                            h.prev = a;
                                            h.cont_element++;
                                            return true;
                                        }
                                        else
                                        {
                                            if(Math.abs(a - h.prev) > max_variation)
                                            {
                                                return false;
                                            }
                                            else
                                            {
                                                h.prev = a;
                                                return true;
                                            }
                                        }
                                    });
                
                s = AverageTempStream.subscribe((Double d) -> 
                {  
                    
                    if(flag.isSet())
                    {
                        double media = d;      

                        if(cont == 0)
                        {
                            //è la prima media letta
                            cont++;
                            media_prec = media;
                        }
                        //controllo se è da scartare la media, cioè se ha letto solo spike
                        if(media - media_prec > max_variation)
                        {
                            return;
                        }
                        media_prec = media; //altrimenti aggiorno la media precedente

                        SwingUtilities.invokeLater(() -> LblTemperatura.setText("Temperatura: " + media));                                
                        //controllo se bisogna attivare l'allarme
                        if(media > soglia)
                        {
                            if(System.currentTimeMillis() - time > tempo_allarme)
                            {
                               SwingUtilities.invokeLater(() ->  PanelAllarme.setBackground(java.awt.Color.RED));                                
                            }
                        }
                        else
                        {
                            time = System.currentTimeMillis();
                            SwingUtilities.invokeLater(() ->  PanelAllarme.setBackground(java.awt.Color.GREEN));
                        }                        
                    }

                    
                });                 
               
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        BTNStart = new javax.swing.JButton();
        BTNStop = new javax.swing.JButton();
        TXTSoglia = new javax.swing.JTextField();
        TXTTempoAllarme = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        BTNSetta = new javax.swing.JButton();
        PanelAllarme = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        LblTemperatura = new javax.swing.JLabel();
        TxtMaxVariation = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        BTNStart.setText("Start");
        BTNStart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BTNStartActionPerformed(evt);
            }
        });

        BTNStop.setText("Stop");
        BTNStop.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BTNStopActionPerformed(evt);
            }
        });

        TXTSoglia.setText("70");

        TXTTempoAllarme.setText("100");

        jLabel1.setText("Gradi Soglia Allarme");

        jLabel2.setText("Tempo Allare (millisecondi)");

        BTNSetta.setText("Setta");
        BTNSetta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BTNSettaActionPerformed(evt);
            }
        });

        PanelAllarme.setBackground(java.awt.Color.green);

        javax.swing.GroupLayout PanelAllarmeLayout = new javax.swing.GroupLayout(PanelAllarme);
        PanelAllarme.setLayout(PanelAllarmeLayout);
        PanelAllarmeLayout.setHorizontalGroup(
            PanelAllarmeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 25, Short.MAX_VALUE)
        );
        PanelAllarmeLayout.setVerticalGroup(
            PanelAllarmeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 27, Short.MAX_VALUE)
        );

        jLabel3.setText("Allarme:");

        LblTemperatura.setText("Temperatura: ");

        TxtMaxVariation.setText("2.0");

        jLabel4.setText("MaxVariation");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(74, 74, 74)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(TXTSoglia)
                            .addComponent(TXTTempoAllarme, javax.swing.GroupLayout.DEFAULT_SIZE, 39, Short.MAX_VALUE))
                        .addGap(28, 28, 28)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addGap(0, 112, Short.MAX_VALUE))))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(BTNStart)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(BTNStop)
                        .addGap(66, 66, 66))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(LblTemperatura)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(PanelAllarme, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(TxtMaxVariation, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(28, 28, 28)
                                .addComponent(jLabel4))
                            .addComponent(BTNSetta))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(BTNStart)
                    .addComponent(BTNStop))
                .addGap(32, 32, 32)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(TxtMaxVariation, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addGap(26, 26, 26)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(TXTSoglia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addGap(32, 32, 32)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(TXTTempoAllarme, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addGap(30, 30, 30)
                .addComponent(BTNSetta)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addComponent(jLabel3))
                    .addComponent(PanelAllarme, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(LblTemperatura)
                .addContainerGap(31, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BTNStartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BTNStartActionPerformed
                       
        flag.set(true);
        time = System.currentTimeMillis();
        cont = 0;
        
 
    }//GEN-LAST:event_BTNStartActionPerformed

    private void BTNStopActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BTNStopActionPerformed
        
        flag.set(false);
        //s.unsubscribe();
        
    }//GEN-LAST:event_BTNStopActionPerformed

    private void BTNSettaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BTNSettaActionPerformed
        
        try
        {
            soglia = (Double.parseDouble(TXTSoglia.getText()));
            tempo_allarme = (Double.parseDouble(TXTTempoAllarme.getText()));            
            max_variation = (Double.parseDouble(TxtMaxVariation.getText()));
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(null, "Inserisci campi numerici positivi");
        }
        
    }//GEN-LAST:event_BTNSettaActionPerformed

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
    private javax.swing.JButton BTNSetta;
    private javax.swing.JButton BTNStart;
    private javax.swing.JButton BTNStop;
    private javax.swing.JLabel LblTemperatura;
    private javax.swing.JPanel PanelAllarme;
    private javax.swing.JTextField TXTSoglia;
    private javax.swing.JTextField TXTTempoAllarme;
    private javax.swing.JTextField TxtMaxVariation;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    // End of variables declaration//GEN-END:variables
}
