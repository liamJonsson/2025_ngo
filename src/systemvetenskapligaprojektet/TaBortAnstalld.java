/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package systemvetenskapligaprojektet;
import oru.inf.InfDB; 
import oru.inf.InfException;
/**
 *
 * @author lisas
 */
public class TaBortAnstalld extends javax.swing.JFrame {

    private static InfDB idb;
    private String inloggadAnvandare;
    
    public TaBortAnstalld(InfDB idb, String inloggadAnvandare) {
        initComponents();
        this.inloggadAnvandare = inloggadAnvandare;
        this.idb = idb;
        lblFelIDTaBort.setVisible(false);
        lblTaBortAnstalldLyckad.setVisible(false);
    }


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblTaBortAnstalld = new javax.swing.JLabel();
        tfTaBortID = new javax.swing.JTextField();
        btnTillbakaTaBort = new javax.swing.JButton();
        btnTaBort = new javax.swing.JButton();
        lblFelIDTaBort = new javax.swing.JLabel();
        lblTaBortAnstalldLyckad = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        lblTaBortAnstalld.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblTaBortAnstalld.setText("Ta bort en anställd");

        tfTaBortID.setEditable(false);
        tfTaBortID.setFont(new java.awt.Font("Segoe UI", 2, 12)); // NOI18N
        tfTaBortID.setForeground(new java.awt.Color(102, 102, 102));
        tfTaBortID.setText("Fyll i ID:t på den anställde du önskar ta bort");
        tfTaBortID.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tfTaBortIDMouseClicked(evt);
            }
        });
        tfTaBortID.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfTaBortIDActionPerformed(evt);
            }
        });

        btnTillbakaTaBort.setText("Tillbaka");
        btnTillbakaTaBort.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTillbakaTaBortActionPerformed(evt);
            }
        });

        btnTaBort.setText("Ta bort");
        btnTaBort.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTaBortActionPerformed(evt);
            }
        });

        lblFelIDTaBort.setFont(new java.awt.Font("Segoe UI", 2, 12)); // NOI18N
        lblFelIDTaBort.setForeground(new java.awt.Color(255, 0, 0));
        lblFelIDTaBort.setText("ID:t finns inte i systemet");

        lblTaBortAnstalldLyckad.setFont(new java.awt.Font("Segoe UI", 2, 12)); // NOI18N
        lblTaBortAnstalldLyckad.setForeground(new java.awt.Color(0, 153, 0));
        lblTaBortAnstalldLyckad.setText("Den anställde har tagits bort");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(35, 35, 35)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblTaBortAnstalldLyckad)
                            .addComponent(lblFelIDTaBort)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                    .addComponent(btnTaBort)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(btnTillbakaTaBort))
                                .addComponent(tfTaBortID, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 249, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(79, 79, 79)
                        .addComponent(lblTaBortAnstalld)))
                .addContainerGap(35, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addComponent(lblTaBortAnstalld)
                .addGap(18, 18, 18)
                .addComponent(tfTaBortID, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2)
                .addComponent(lblTaBortAnstalldLyckad)
                .addGap(2, 2, 2)
                .addComponent(lblFelIDTaBort)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 46, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnTaBort)
                    .addComponent(btnTillbakaTaBort))
                .addGap(35, 35, 35))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnTillbakaTaBortActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTillbakaTaBortActionPerformed
        new AllaAnstallda(idb,inloggadAnvandare).setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnTillbakaTaBortActionPerformed

    
    //Tar bort en anställd
    private void btnTaBortActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTaBortActionPerformed
        try{
            //Hämtar den ifyllda texten från textrutan
            String taBortAid = tfTaBortID.getText();

            //Gör om String till int
            int aid = Integer.parseInt(taBortAid);
            
            //Kollar ifall aid finns i databasen
            String kontrolleraAid = "select aid from anstalld where aid = " + aid + ";";
            String aidFinns = idb.fetchSingle(kontrolleraAid);
            
            //Om aid finns i databasen tar vi bort den 
            if(aidFinns != null){
               String taBort = "delete from anstalld where aid = " + aid + ";";
               idb.delete(taBort); 
               lblTaBortAnstalldLyckad.setVisible(true);
                
            }
            else{
                lblFelIDTaBort.setVisible(true);
            }
        }
            catch(InfException ex) {
            System.out.println(ex);
        }
        
        catch(NumberFormatException ex) {
            lblFelIDTaBort.setVisible(true);
        }
    }//GEN-LAST:event_btnTaBortActionPerformed

    private void tfTaBortIDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfTaBortIDActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tfTaBortIDActionPerformed

    private void tfTaBortIDMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tfTaBortIDMouseClicked
        tfTaBortID.setEditable(true);
        tfTaBortID.setText("");
    }//GEN-LAST:event_tfTaBortIDMouseClicked

        
        
        
        
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
            java.util.logging.Logger.getLogger(TaBortAnstalld.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TaBortAnstalld.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TaBortAnstalld.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TaBortAnstalld.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
           //     new TaBortAnstalld().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnTaBort;
    private javax.swing.JButton btnTillbakaTaBort;
    private javax.swing.JLabel lblFelIDTaBort;
    private javax.swing.JLabel lblTaBortAnstalld;
    private javax.swing.JLabel lblTaBortAnstalldLyckad;
    private javax.swing.JTextField tfTaBortID;
    // End of variables declaration//GEN-END:variables
}
