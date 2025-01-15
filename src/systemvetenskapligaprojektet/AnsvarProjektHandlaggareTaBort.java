/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package systemvetenskapligaprojektet;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.JOptionPane;
import oru.inf.InfDB;
import oru.inf.InfException;
/**
 *
 * @author mejaa
 */
public class AnsvarProjektHandlaggareTaBort extends javax.swing.JFrame {

    private static InfDB idb;
    private String inloggadAnvandare;
    private String projektID;

    public AnsvarProjektHandlaggareTaBort(InfDB idb, String inloggadAnvandare, String projektID) {
        this.idb = idb;
        this.inloggadAnvandare = inloggadAnvandare;
        this.projektID = projektID;
        initComponents();
        fyllDropdownProjekt();
        fyllDropdownKoppladeHandlaggare(projektID);
        lblLyckat.setVisible(false);
        
        // Lägg till lyssnare för ComboValjProjekt
        ComboValjProjekt.addItemListener(evt -> ComboValjProjektItemStateChanged(evt));
    }

    // Fyll ComboBox med projekt där den inloggade användaren är projektchef
    private void fyllDropdownProjekt() {
        try {
            // Hämta den inloggade användarens aid (anställd ID)
            String aidQuery = "SELECT aid FROM anstalld WHERE epost = '" + inloggadAnvandare + "';";
            String aid = idb.fetchSingle(aidQuery);

            if (aid != null) {
                // Hämta alla projekt som den inloggade användaren är projektchef för
                String selectProjekt = "SELECT pid, projektnamn FROM projekt WHERE projektchef = '" + aid + "';";
                ArrayList<HashMap<String, String>> projektResult = idb.fetchRows(selectProjekt);

                if (projektResult != null && !projektResult.isEmpty()) {
                    // Rensa och fyll projekt ComboBox
                    ComboValjProjekt.removeAllItems();
                    ComboValjProjekt.addItem("Välj projekt");

                    // Lägg till varje projekt i ComboBoxen
                    for (HashMap<String, String> projekt : projektResult) {
                        String projektInfo = projekt.get("pid") + " - " + projekt.get("projektnamn");
                        ComboValjProjekt.addItem(projektInfo);
                    }

                    // Lämna det första neutrala alternativet valt
                    ComboValjProjekt.setSelectedIndex(0);
                } else {
                    JOptionPane.showMessageDialog(this, "Du har inga projekt som projektchef.");
                }
            }
        } catch (InfException ex) {
            JOptionPane.showMessageDialog(this, "Ett fel inträffade vid hämtning av projekt: " + ex.getMessage());
        }
    }

    // Fyll ComboBox med kopplade handläggare för det valda projektet
    private void fyllDropdownKoppladeHandlaggare(String projektID) {
        try {
            // SQL-fråga som hämtar handläggare kopplade till projektet
            String query = "SELECT anstalld.aid, anstalld.fornamn, anstalld.efternamn " +
                           "FROM anstalld " +
                           "JOIN ans_proj ON anstalld.aid = ans_proj.aid " +
                           "WHERE ans_proj.pid = " + projektID;

            // Hämtar resultaten från databasen
            ArrayList<HashMap<String, String>> koppladeHandlaggare = idb.fetchRows(query);

            // Rensa och fyll ComboBox
            ComboValjHandlaggare.removeAllItems();
            ComboValjHandlaggare.addItem("Välj handläggare");

            // Lägg till varje handläggare i ComboBoxen
            for (HashMap<String, String> handlaggare : koppladeHandlaggare) {
                String handlaggareInfo = handlaggare.get("aid") + " - " + handlaggare.get("fornamn") + " " + handlaggare.get("efternamn");
                ComboValjHandlaggare.addItem(handlaggareInfo);
            }
        } catch (InfException e) {
            JOptionPane.showMessageDialog(this, "Kunde inte fylla kopplade handläggare: " + e.getMessage());
        }
    }

    
    //Metod för att ta bort en handläggare från det valda projektet
    private void taBortHandlaggareFrånProjekt(String projektID) {
        try {
            // Hämta den valda handläggaren från ComboBoxen
            String valdHandlaggare = (String) ComboValjHandlaggare.getSelectedItem();
            if (valdHandlaggare == null || valdHandlaggare.equals("Välj handläggare")) {
                JOptionPane.showMessageDialog(this, "Välj en handläggare att ta bort.");
                return;
            }

            // Extrahera handläggareID (aid) från den valda handläggaren
            String handlaggareID = valdHandlaggare.split(" - ")[0]; // Hämta aid

            // SQL-fråga för att ta bort den valda handläggaren från projektet
            String deleteQuery = "DELETE FROM ans_proj " +
                                 "WHERE pid = " + projektID + " AND aid = " + handlaggareID;

            // Exekvera delete-frågan
            idb.delete(deleteQuery);

            // Uppdatera ComboBoxen och visa meddelande
            JOptionPane.showMessageDialog(this, "Handläggaren har tagits bort från projektet.");
            fyllDropdownKoppladeHandlaggare(projektID); // Uppdatera listan av handläggare

        } catch (InfException e) {
            JOptionPane.showMessageDialog(this, "Kunde inte ta bort handläggare: " + e.getMessage());
        }
    }


    //Hantera val av projekt i ComboValjProjekt
    private void ComboValjProjektItemStateChanged(java.awt.event.ItemEvent evt) {
        if (evt.getStateChange() == java.awt.event.ItemEvent.SELECTED) {
            String selectedProjekt = (String) ComboValjProjekt.getSelectedItem();
            if (!selectedProjekt.equals("Välj projekt")) {
                // Extrahera projektID och spara i global variabel
                projektID = selectedProjekt.split(" - ")[0];
                // Anropa fyllDropdownKoppladeHandlaggare för att uppdatera handläggarlistan
                fyllDropdownKoppladeHandlaggare(projektID);
            }
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
        btnSpara = new javax.swing.JButton();
        btnTillbaka = new javax.swing.JButton();
        lblLyckat = new javax.swing.JLabel();
        ComboValjProjekt = new javax.swing.JComboBox<>();
        ComboValjHandlaggare = new javax.swing.JComboBox<>();
        lblLaggTillHandlaggare1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Microsoft JhengHei UI", 1, 18)); // NOI18N
        jLabel1.setText("TA BORT EN HANDLÄGGARE");

        jLabel2.setFont(new java.awt.Font("Microsoft JhengHei UI", 1, 12)); // NOI18N
        jLabel2.setText("Projekt");

        jLabel3.setFont(new java.awt.Font("Microsoft JhengHei UI", 1, 12)); // NOI18N
        jLabel3.setText("Handläggare");

        btnSpara.setFont(new java.awt.Font("Microsoft JhengHei UI", 0, 12)); // NOI18N
        btnSpara.setText("Spara");
        btnSpara.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSparaActionPerformed(evt);
            }
        });

        btnTillbaka.setFont(new java.awt.Font("Microsoft JhengHei UI", 0, 12)); // NOI18N
        btnTillbaka.setText("Tillbaka");
        btnTillbaka.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTillbakaActionPerformed(evt);
            }
        });

        lblLyckat.setFont(new java.awt.Font("Microsoft JhengHei UI Light", 2, 12)); // NOI18N
        lblLyckat.setForeground(new java.awt.Color(0, 153, 0));
        lblLyckat.setText("Handläggaren har tagits bort! ");

        ComboValjProjekt.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        ComboValjHandlaggare.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        lblLaggTillHandlaggare1.setFont(new java.awt.Font("Microsoft JhengHei UI", 1, 18)); // NOI18N
        lblLaggTillHandlaggare1.setText("FRÅN DITT PROJEKT");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(lblLyckat, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGap(35, 35, 35)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblLaggTillHandlaggare1)
                            .addComponent(jLabel1)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(btnSpara)
                                    .addGap(18, 18, 18)
                                    .addComponent(btnTillbaka))
                                .addGroup(layout.createSequentialGroup()
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel3)
                                        .addComponent(jLabel2))
                                    .addGap(35, 35, 35)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(ComboValjHandlaggare, 0, 178, Short.MAX_VALUE)
                                        .addComponent(ComboValjProjekt, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))))
                .addGap(35, 35, 35))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addComponent(jLabel1)
                .addGap(0, 0, 0)
                .addComponent(lblLaggTillHandlaggare1, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ComboValjProjekt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(ComboValjHandlaggare, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(45, 45, 45)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnTillbaka)
                    .addComponent(btnSpara))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblLyckat)
                .addGap(35, 35, 35))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSparaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSparaActionPerformed
    taBortHandlaggareFrånProjekt(projektID);
    }//GEN-LAST:event_btnSparaActionPerformed

    private void btnTillbakaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTillbakaActionPerformed
        new AnsvarProjektHandlaggare(idb, inloggadAnvandare).setVisible(true);
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
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                //new TaBortHandlaggare().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> ComboValjHandlaggare;
    private javax.swing.JComboBox<String> ComboValjProjekt;
    private javax.swing.JButton btnSpara;
    private javax.swing.JButton btnTillbaka;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel lblLaggTillHandlaggare1;
    private javax.swing.JLabel lblLyckat;
    // End of variables declaration//GEN-END:variables
}
