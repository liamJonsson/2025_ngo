/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package systemvetenskapligaprojektet;
import oru.inf.InfDB; //importeras i alla klasser som vi ska använda
import oru.inf.InfException; //importeras i alla klasser som vi ska använda
import javax.swing.DefaultListModel;
import java.util.ArrayList;
/**
 *
 * @author lisas
 */
public class AllaAnstallda extends javax.swing.JFrame {
    private static InfDB idb;
    private String inloggadAnvandare;
    
    private DefaultListModel<String> listModelAllData = new DefaultListModel<>();

    public AllaAnstallda(InfDB idb, String inloggadAnvandare) {
        this.inloggadAnvandare = inloggadAnvandare;
        this.idb = idb;
        initComponents();
        populateListFromDatabase();
    }
    
    public void populateListFromDatabase() {
        // Skapa en ArrayList för att hålla data från databasen
        ArrayList<String> anstallningsID = new ArrayList<>();
        ArrayList<String> anstallningsFornamn = new ArrayList<>();
        ArrayList<String> anstallningsEfternamn = new ArrayList<>();
        ArrayList<String> anstallningsAdress = new ArrayList<>();
        ArrayList<String> anstallningsEpost = new ArrayList<>();
        ArrayList<String> anstallningsTelefon = new ArrayList<>();
        ArrayList<String> anstallningsDatum = new ArrayList<>();
        ArrayList<String> anstallningsLosenord = new ArrayList<>();
        ArrayList<String> anstallningsAvdelning = new ArrayList<>();
        

    try{
        
        anstallningsID = idb.fetchColumn("select aid from anstalld order by (aid) asc;");
        anstallningsFornamn = idb.fetchColumn("select fornamn from anstalld;");
        anstallningsEfternamn = idb.fetchColumn("select efternamn from anstalld;");
        anstallningsAdress = idb.fetchColumn("select adress from anstalld;");
        anstallningsEpost = idb.fetchColumn("select epost from anstalld;");
        anstallningsTelefon = idb.fetchColumn("select telefon from anstalld;");
        anstallningsDatum = idb.fetchColumn("select anstallningsdatum from anstalld;");
        anstallningsLosenord = idb.fetchColumn("select losenord from anstalld;");
        anstallningsAvdelning = idb.fetchColumn("select avdelning from anstalld;");
        
            } 
        
        catch (InfException ex) {
            ex.printStackTrace();
            
        }
     
    int antalAnstallda = anstallningsID.size();
    for (int i = 0; i < antalAnstallda; i++){
        String rad = String.format("ID: %s,  %s %s,  %s,  %s,  %s,  %s,  %s,  Avdelning: %s",
            anstallningsID.get(i),
            anstallningsFornamn.get(i),
            anstallningsEfternamn.get(i),
            anstallningsAdress.get(i),
            anstallningsEpost.get(i),
            anstallningsTelefon.get(i),
            anstallningsDatum.get(i),
            anstallningsLosenord.get(i),
            anstallningsAvdelning.get(i));
        
        listModelAllData.addElement(rad);
    }
    
    listAllt.setModel(listModelAllData);
    
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
        jScrollPane10 = new javax.swing.JScrollPane();
        listAllt = new javax.swing.JList<>();
        jLabel1 = new javax.swing.JLabel();
        btnTaBortAnstalld = new javax.swing.JButton();

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

        listAllt.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        listAllt.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5", "" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane10.setViewportView(listAllt);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 2, 12)); // NOI18N
        jLabel1.setText("ID / Namn / Adress / Epost / Telefon / Anställningsdatum / Lösenord / Avdelning");

        btnTaBortAnstalld.setText("Ta bort anställd");
        btnTaBortAnstalld.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTaBortAnstalldActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblAllaAnstallda)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(btnSok)
                            .addGap(18, 18, 18)
                            .addComponent(btnLaggTill, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(btnTaBortAnstalld, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnTillbaka))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane10, javax.swing.GroupLayout.PREFERRED_SIZE, 951, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 486, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(24, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(45, Short.MAX_VALUE)
                .addComponent(lblAllaAnstallda)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane10, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1)
                .addGap(52, 52, 52)
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
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JLabel lblAllaAnstallda;
    private javax.swing.JList<String> listAllt;
    // End of variables declaration//GEN-END:variables
}
