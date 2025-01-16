/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package systemvetenskapligaprojektet;
import oru.inf.InfDB; //importeras i alla klasser som vi ska använda
import oru.inf.InfException; //importeras i alla klasser som vi ska använda
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.util.HashMap;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.JOptionPane;
/**
 *
 * @author mejaa
 */
public class AvdelningensAnstallda extends javax.swing.JFrame {
    private static InfDB idb;
    private String inloggadAnvandare;
    /**
     * Creates new form AvdelningensAnstallda
     */
    public AvdelningensAnstallda(InfDB idb, String inloggadAnvandare) {
        this.idb = idb;
        this.inloggadAnvandare = inloggadAnvandare;
        initComponents();
        fyllTabell();
        hamtaAvdelning();
        hanteraSearchListener();
    }
    
    private void fyllTabell(){

        try{
            //Skapar en array som lagrar kolumnnamnen

        String[] kolumnNamn = {"aid", "fornamn", "efternamn", "epost", "telefon"};
        
        //Skapar en DefaultTableModel som håller kolumnnamnen samt sätter antalet rader till noll.
        DefaultTableModel allaAnstallda = new DefaultTableModel(kolumnNamn, 0);

       

        String selectAid = "select aid from anstalld where avdelning = (select avdelning from anstalld where epost = '" + inloggadAnvandare + "')order by(aid);";
        //Hämtar de aid som är anställda på samma avdelning som den inloggade användaren.
        ArrayList<String> aid = idb.fetchColumn(selectAid);

            if(aid != null){

                for(String ettID:aid){
                    //Hämtar data om varje anställd på avdelningen.
                    String selectInfo = "select aid,fornamn,efternamn,epost,telefon from anstalld where aid = " + ettID + ";";

                    HashMap<String,String> info = idb.fetchRow(selectInfo);

           
                    //Skapar en array som håller data för en rad i tabellen.
                    Object[] enRad = new Object[kolumnNamn.length];
                    int index = 0;

                    //Skapar en for-each loop som går igenom varje kolumn i kolumnNamn där värdet för den kolumn man är på läggs till i "enRad".
                   for(String enKolumn:kolumnNamn){
                        enRad[index++] = info.get(enKolumn);
                    }
                   //"enRad" läggs till i DefaultTableModel.
                    allaAnstallda.addRow(enRad);

                }
                //Jtable sätts med data från DefaultTableModel.
                tblAnstallda.setModel(allaAnstallda);

            }

            tblAnstallda.setAutoResizeMode(tblAnstallda.AUTO_RESIZE_OFF);
            
        //Sätter storleken på tabellen.
        TableColumn col = tblAnstallda.getColumnModel().getColumn(0);

        col.setPreferredWidth(50);

        col = tblAnstallda.getColumnModel().getColumn(1);

        col.setPreferredWidth(125);

        col = tblAnstallda.getColumnModel().getColumn(2);

        col.setPreferredWidth(125);

        col = tblAnstallda.getColumnModel().getColumn(3);

        col.setPreferredWidth(250);

        col = tblAnstallda.getColumnModel().getColumn(4);

        col.setPreferredWidth(150);
        }

        catch(InfException ex){

            System.out.println(ex);

        }      

    }
    
    private void hamtaAvdelning(){
        try{
            //Försöker hämta namnet på avdelningen som den inloggade användaren jobbar på och sedan lägga in det i labeln "lblAvdelning".
        String selectAvdelning = "select namn from avdelning where avdid =(select avdelning from anstalld where epost ='" + inloggadAnvandare + "');";
        String Avdelning = idb.fetchSingle(selectAvdelning);
        lblAvdelning.setText(Avdelning);
        }
        catch(InfException ex){

            System.out.println(ex.getMessage());
        }
    }
    
    
    private void hanteraSearchListener() {
        btnSok.addActionListener((ActionEvent e) -> {
            // Be användaren om sökterm
            String sokTerm = JOptionPane.showInputDialog("Ange både för- och efternamn, eller e-postadress, för att söka efter en anställd:");

            if (sokTerm == null || sokTerm.trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Du måste ange en sökterm!");
                return;
            }

            try {
                String query = "";
                if (sokTerm.contains(" ")) {
                    // Om sökterm innehåller ett mellanslag så kommer den anta att det är för- och efternamn
                    String[] namnDelar = sokTerm.split(" ", 2);
                    String fornamn = namnDelar[0].trim();
                    String efternamn = namnDelar[1].trim();

                    query = "SELECT aid, fornamn, efternamn, epost, telefon " +
                            "FROM anstalld " +
                            "WHERE LOWER(fornamn) = '" + fornamn.toLowerCase() + "' " +
                            "  AND LOWER(efternamn) = '" + efternamn.toLowerCase() + "';";
                } else {
                    // Annars så kommer den anta att söktermen är en e-postadress
                    query = "SELECT aid, fornamn, efternamn, epost, telefon " +
                            "FROM anstalld " +
                            "WHERE LOWER(epost) = '" + sokTerm.toLowerCase() + "';";
                }

                // Hämta resultat från databasen
                ArrayList<HashMap<String, String>> resultat = idb.fetchRows(query);

                if (resultat == null || resultat.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Inga anställda matchar din sökning.");
                    return;
                }

                // Skapa en ny tabellmodell för att visa resultatet
                String[] kolumnNamn = {"Anställnings-ID", "Förnamn", "Efternamn", "E-post", "Telefon"};
                DefaultTableModel filtreradModell = new DefaultTableModel(kolumnNamn, 0);

                // Lägg till rader i modellen baserat på resultatet
                for (HashMap<String, String> rad : resultat) {
                    filtreradModell.addRow(new Object[]{
                            rad.get("aid"),
                            rad.get("fornamn"),
                            rad.get("efternamn"),
                            rad.get("epost"),
                            rad.get("telefon")
                    });
                }

                // Uppdatera tabellen med den filtrerade modellen
                tblAnstallda.setModel(filtreradModell);

            } catch (InfException ex) {
                JOptionPane.showMessageDialog(null, "Ett fel inträffade vid sökningen: " + ex.getMessage());
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

        jScrollPane1 = new javax.swing.JScrollPane();
        tblAnstallda = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        btnTillbaka = new javax.swing.JButton();
        lblAvdelning = new javax.swing.JLabel();
        btnSok = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jScrollPane1.setEnabled(false);

        tblAnstallda.setFont(new java.awt.Font("Microsoft JhengHei UI", 0, 12)); // NOI18N
        tblAnstallda.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "ID", "Förnamn", "Efternamn", "Epost", "Telefonnummer"
            }
        ));
        tblAnstallda.setEnabled(false);
        jScrollPane1.setViewportView(tblAnstallda);

        jLabel1.setFont(new java.awt.Font("Microsoft JhengHei UI", 1, 18)); // NOI18N
        jLabel1.setText("Anställda på");

        btnTillbaka.setFont(new java.awt.Font("Microsoft JhengHei UI", 0, 12)); // NOI18N
        btnTillbaka.setText("Tillbaka");
        btnTillbaka.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTillbakaActionPerformed(evt);
            }
        });

        lblAvdelning.setFont(new java.awt.Font("Microsoft JhengHei UI", 1, 18)); // NOI18N
        lblAvdelning.setText("jLabel2");

        btnSok.setFont(new java.awt.Font("Microsoft JhengHei UI", 0, 12)); // NOI18N
        btnSok.setText("Sök anställd");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(btnSok)
                        .addGap(18, 18, 18)
                        .addComponent(btnTillbaka))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblAvdelning, javax.swing.GroupLayout.PREFERRED_SIZE, 550, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 720, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(35, 35, 35))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(lblAvdelning))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 235, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnTillbaka)
                    .addComponent(btnSok))
                .addGap(35, 35, 35))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnTillbakaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTillbakaActionPerformed
        new MinAvdelning(idb, inloggadAnvandare).setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_btnTillbakaActionPerformed

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
            java.util.logging.Logger.getLogger(AvdelningensAnstallda.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AvdelningensAnstallda.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AvdelningensAnstallda.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AvdelningensAnstallda.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                //new AvdelningensAnstallda().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnSok;
    private javax.swing.JButton btnTillbaka;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblAvdelning;
    private javax.swing.JTable tblAnstallda;
    // End of variables declaration//GEN-END:variables
}
