/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package systemvetenskapligaprojektet;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import oru.inf.InfDB;
import oru.inf.InfException;

/**
 *
 * @author lisas
 */
public class AnsvarProjektPartner extends javax.swing.JFrame {
    private static InfDB idb;
    private String inloggadAnvandare;
    

    /**
     * Creates new form AnsvarProjektPartner
     */
    public AnsvarProjektPartner(InfDB idb, String inloggadAnvandare) {
        initComponents();
        this.inloggadAnvandare = inloggadAnvandare;
        this.idb = idb;
            fyllTabell();
    }
    
public void fyllTabell() {
    try {
        // Hämta den inloggade användarens aid (anställd ID)
        String aidQuery = "SELECT aid FROM anstalld WHERE epost = '" + inloggadAnvandare + "';";
        String aid = idb.fetchSingle(aidQuery);

        // Kontrollera om aid hittades
        if (aid != null) {

            // Kolumnnamn för tabellen
            String[] kolumnNamn = {"ProjektID", "PartnerID", "namn", "kontaktperson", "kontaktepost", "telefon", "adress", "branch", "stad"};
            DefaultTableModel allaPartners = new DefaultTableModel(kolumnNamn, 0);

            // Hämta alla projekt där den inloggade användaren är projektchef
            String selectProjekt = "SELECT pid FROM projekt WHERE projektchef = '" + aid + "';";
            ArrayList<String> projektIDs = idb.fetchColumn(selectProjekt);

            // Kontrollera om några projekt hittades
            if (projektIDs != null && !projektIDs.isEmpty()) {

                // Hämta partners för varje projekt som den inloggade användaren är projektchef för
                for (String projektID : projektIDs) {

                    // Korrigerad SQL-fråga för att hämta partners för det aktuella projektet
                    String selectPartners = "SELECT " +
                                             "pp.pid AS ProjektID, " +
                                             "partner.pid AS PartnerID, " +
                                             "partner.namn, " +
                                             "partner.kontaktperson, " +
                                             "partner.kontaktepost, " +
                                             "partner.telefon, " +
                                             "partner.adress, " +
                                             "partner.branch, " +
                                             "partner.stad " +
                                             "FROM projekt_partner pp " +
                                             "JOIN partner ON pp.partner_pid = partner.pid " +
                                             "WHERE pp.pid = '" + projektID + "';"; // Använd projektID för varje projekt

                    // Hämta partners kopplade till det aktuella projektet
                    ArrayList<HashMap<String, String>> partners = idb.fetchRows(selectPartners);

                    // Kontrollera om partners hittades
                    if (partners != null && !partners.isEmpty()) {
                        // Lägg till partnerinformation i tabellen
                        for (HashMap<String, String> partner : partners) {
        String[] enRad = new String[kolumnNamn.length];

        for (int i = 0; i < kolumnNamn.length; i++) {
            if (kolumnNamn[i].equals("ProjektID")) {
                // ProjektID från loopen
                enRad[i] = projektID;
            } else if (kolumnNamn[i].equals("PartnerID")) {
                // PartnerID från SQL-resultat mappat till "pid"
                enRad[i] = partner.getOrDefault("PartnerID", partner.getOrDefault("pid", "Ingen data"));
            } else {
                // Dynamisk mappning av övriga kolumner
                enRad[i] = partner.getOrDefault(kolumnNamn[i], "Ingen data");
            }
        }

        allaPartners.addRow(enRad);

                        }
                    }
                }

                // Uppdatera tabellen med de filtrerade partners
                tblAnsvarProjektPartner.setModel(allaPartners);
                
                tblAnsvarProjektPartner.setAutoResizeMode(tblAnsvarProjektPartner.AUTO_RESIZE_OFF);
            TableColumn col = tblAnsvarProjektPartner.getColumnModel().getColumn(0); //ProjektID
            col.setPreferredWidth(75);
            col = tblAnsvarProjektPartner.getColumnModel().getColumn(1); //PartnerID
            col.setPreferredWidth(75);
            col = tblAnsvarProjektPartner.getColumnModel().getColumn(2); //Namn
            col.setPreferredWidth(200);
            col = tblAnsvarProjektPartner.getColumnModel().getColumn(3); //Kontaktperson
            col.setPreferredWidth(125);
            col = tblAnsvarProjektPartner.getColumnModel().getColumn(4); //Kontaktepost
            col.setPreferredWidth(175);
            col = tblAnsvarProjektPartner.getColumnModel().getColumn(5); //Telefon
            col.setPreferredWidth(100);
            col = tblAnsvarProjektPartner.getColumnModel().getColumn(6); //Adres
            col.setPreferredWidth(150);
            col = tblAnsvarProjektPartner.getColumnModel().getColumn(7); //Bransch
            col.setPreferredWidth(150);
            col = tblAnsvarProjektPartner.getColumnModel().getColumn(8); //Stad
            col.setPreferredWidth(75);

            } 
            

            else{
                System.out.println("Inga projekt hittades för projektchef med aid: " + aid);
            }

        } 
        else{
            System.out.println("Ingen användare hittades för e-post: " + inloggadAnvandare);
        }
    } 
    catch (InfException ex) {
        System.out.println("Fel: " + ex);
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

        jScrollPane1 = new javax.swing.JScrollPane();
        tblAnsvarProjektPartner = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        btnLaggTill = new javax.swing.JButton();
        btnTaBort = new javax.swing.JButton();
        btnTillbaka = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        tblAnsvarProjektPartner.setFont(new java.awt.Font("Microsoft JhengHei UI", 0, 12)); // NOI18N
        tblAnsvarProjektPartner.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(tblAnsvarProjektPartner);

        jLabel1.setFont(new java.awt.Font("Microsoft JhengHei UI", 1, 18)); // NOI18N
        jLabel1.setText("PARTNERS");

        btnLaggTill.setFont(new java.awt.Font("Microsoft JhengHei UI", 0, 12)); // NOI18N
        btnLaggTill.setText("Lägg till en partner");
        btnLaggTill.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLaggTillActionPerformed(evt);
            }
        });

        btnTaBort.setFont(new java.awt.Font("Microsoft JhengHei UI", 0, 12)); // NOI18N
        btnTaBort.setText("Ta bort en partner");
        btnTaBort.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTaBortActionPerformed(evt);
            }
        });

        btnTillbaka.setFont(new java.awt.Font("Microsoft JhengHei UI", 0, 12)); // NOI18N
        btnTillbaka.setText("Tillbaka");
        btnTillbaka.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTillbakaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnLaggTill)
                        .addGap(18, 18, 18)
                        .addComponent(btnTaBort)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnTillbaka))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel1)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 684, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(35, 35, 35))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(45, 45, 45)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnLaggTill)
                    .addComponent(btnTaBort)
                    .addComponent(btnTillbaka))
                .addGap(35, 35, 35))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnLaggTillActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLaggTillActionPerformed
    new AnsvarProjektPartnerLaggTill(idb, inloggadAnvandare).setVisible(true);
    this.setVisible(false);
    }//GEN-LAST:event_btnLaggTillActionPerformed

    private void btnTaBortActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTaBortActionPerformed
    try {
        // Hämta det aktuella projektet där användaren är projektchef
        String selectProjekt = "SELECT pid FROM projekt WHERE projektchef = (SELECT aid FROM anstalld WHERE epost = '" + inloggadAnvandare + "');";
        ArrayList<String> projektIDs = idb.fetchColumn(selectProjekt);

        if (!projektIDs.isEmpty()) {
            // Använd första projektet från listan
            String projektID = projektIDs.get(0);

            // Skapa och visa den nya vyn med det hämtade projektID
            new AnsvarProjektPartnerTaBort(idb, inloggadAnvandare, projektID).setVisible(true);
            this.setVisible(false);
        } else {
            JOptionPane.showMessageDialog(this, "Det finns inga projekt där du är projektchef.");
        }
    } catch (InfException ex) {
        JOptionPane.showMessageDialog(this, "Ett fel inträffade vid hämtning av projekt: " + ex.getMessage());
    }
    }//GEN-LAST:event_btnTaBortActionPerformed

    private void btnTillbakaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTillbakaActionPerformed
    new AnsvarProjekt(idb, inloggadAnvandare).setVisible(true);
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
            java.util.logging.Logger.getLogger(AnsvarProjektPartner.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AnsvarProjektPartner.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AnsvarProjektPartner.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AnsvarProjektPartner.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
              //  new AnsvarProjektPartner().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnLaggTill;
    private javax.swing.JButton btnTaBort;
    private javax.swing.JButton btnTillbaka;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblAnsvarProjektPartner;
    // End of variables declaration//GEN-END:variables
}
