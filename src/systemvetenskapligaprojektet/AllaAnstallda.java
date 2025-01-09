/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package systemvetenskapligaprojektet;
import oru.inf.InfDB; //importeras i alla klasser som vi ska använda
import oru.inf.InfException; //importeras i alla klasser som vi ska använda
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.table.TableColumn;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author lisas
 */
public class AllaAnstallda extends javax.swing.JFrame {
    private static InfDB idb;
    private String inloggadAnvandare;
    
    public AllaAnstallda(InfDB idb, String inloggadAnvandare) {
        this.inloggadAnvandare = inloggadAnvandare;
        this.idb = idb;
        initComponents();
        fyllTabell();
    }
   
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane8 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        btnLaggTill = new javax.swing.JButton();
        btnSok = new javax.swing.JButton();
        btnTillbaka = new javax.swing.JButton();
        lblAllaAnstallda = new javax.swing.JLabel();
        btnTaBortAnstalld = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblAllaAnstallda = new javax.swing.JTable();

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane8.setViewportView(jTextArea1);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        btnLaggTill.setText("Lägg till ny anställd");
        btnLaggTill.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLaggTillActionPerformed(evt);
            }
        });

        btnSok.setText("Sök");

        btnTillbaka.setText("Tillbaka");
        btnTillbaka.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTillbakaActionPerformed(evt);
            }
        });

        lblAllaAnstallda.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblAllaAnstallda.setText("Alla anställda");

        btnTaBortAnstalld.setText("Ta bort anställd");
        btnTaBortAnstalld.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTaBortAnstalldActionPerformed(evt);
            }
        });

        tblAllaAnstallda.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4", "Title 5", "Title 6", "Title 7", "Title 8", "Title 9"
            }
        ));
        jScrollPane1.setViewportView(tblAllaAnstallda);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblAllaAnstallda)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(btnSok)
                        .addGap(18, 18, 18)
                        .addComponent(btnLaggTill, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnTaBortAnstalld, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 478, Short.MAX_VALUE)
                        .addComponent(btnTillbaka))
                    .addComponent(jScrollPane1))
                .addContainerGap(24, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(42, 42, 42)
                .addComponent(lblAllaAnstallda)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 231, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 56, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnLaggTill)
                    .addComponent(btnSok)
                    .addComponent(btnTillbaka)
                    .addComponent(btnTaBortAnstalld))
                .addGap(35, 35, 35))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    //Tillbaka till förgående sida
    private void btnTillbakaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTillbakaActionPerformed
        new MenyAdmin(idb,inloggadAnvandare).setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnTillbakaActionPerformed

    //Lägg till en Anställd
    private void btnLaggTillActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLaggTillActionPerformed
       new LaggTillAnstalld(idb,inloggadAnvandare).setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_btnLaggTillActionPerformed

    private void btnTaBortAnstalldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTaBortAnstalldActionPerformed
        new TaBortAnstalld(idb,inloggadAnvandare).setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_btnTaBortAnstalldActionPerformed

    public void fyllTabell(){
        try{
            String kolumnNamn[] = {"aid", "fornamn", "efternamn", "adress", "epost", "telefon", "anstallningsdatum", "losenord", "avdelning"};
            DefaultTableModel allaAnstallda = new DefaultTableModel(kolumnNamn, 0);
            
            String selectAID = "select aid from anstalld order by(aid);";
            ArrayList<String> aid = idb.fetchColumn(selectAID);
            if(aid != null){
                for(String ettID:aid){
                    String selectInfo = "select * from anstalld where aid = " + ettID + ";";
                    HashMap<String, String> info = idb.fetchRow(selectInfo);
                    
                    Object[] enRad = new Object[kolumnNamn.length];
                    int index = 0;
                    
                    for(String enKolumn:kolumnNamn){
                        enRad[index++] = info.get(enKolumn);
                        
                    }
                    allaAnstallda.addRow(enRad);
                }
                tblAllaAnstallda.setModel(allaAnstallda);
            }
            tblAllaAnstallda.setAutoResizeMode(tblAllaAnstallda.AUTO_RESIZE_OFF);
            TableColumn col = tblAllaAnstallda.getColumnModel().getColumn(0); //ID
            col.setPreferredWidth(50);
            col = tblAllaAnstallda.getColumnModel().getColumn(1); //Fornamn
            col.setPreferredWidth(100);
            col = tblAllaAnstallda.getColumnModel().getColumn(2); //Efternamn
            col.setPreferredWidth(100);
            col = tblAllaAnstallda.getColumnModel().getColumn(3); //Adress
            col.setPreferredWidth(250);
            col = tblAllaAnstallda.getColumnModel().getColumn(4); //Epost
            col.setPreferredWidth(200);
            col = tblAllaAnstallda.getColumnModel().getColumn(5); //Telefon
            col.setPreferredWidth(100);
            col = tblAllaAnstallda.getColumnModel().getColumn(6); //Anstallningsdatum
            col.setPreferredWidth(100);
            col = tblAllaAnstallda.getColumnModel().getColumn(7); //Losenord
            col.setPreferredWidth(100);
            col = tblAllaAnstallda.getColumnModel().getColumn(8); //Avdelning
            col.setPreferredWidth(75);   
        }
        catch(InfException ex){
            System.out.println(ex);
    }
}    

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
            java.util.logging.Logger.getLogger(AllaAnstallda.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AllaAnstallda.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AllaAnstallda.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AllaAnstallda.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
              //  new AllaAnstallda().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnLaggTill;
    private javax.swing.JButton btnSok;
    private javax.swing.JButton btnTaBortAnstalld;
    private javax.swing.JButton btnTillbaka;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JLabel lblAllaAnstallda;
    private javax.swing.JTable tblAllaAnstallda;
    // End of variables declaration//GEN-END:variables
}
