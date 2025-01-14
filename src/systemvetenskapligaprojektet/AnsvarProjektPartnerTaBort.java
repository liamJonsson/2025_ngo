/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package systemvetenskapligaprojektet;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.JOptionPane;
import oru.inf.InfDB; //importeras i alla klasser som vi ska använda
import oru.inf.InfException; //importeras i alla klasser som vi ska använda

/**
 *
 * @author iftinserar
 */
public class AnsvarProjektPartnerTaBort extends javax.swing.JFrame {

    private static InfDB idb;
    private String inloggadAnvandare;
    private String projektID;
    /**
     * Creates new form AnsvarProjektPartnerTaBort
     */
    public AnsvarProjektPartnerTaBort(InfDB idb, String inloggadAnvandare, String projektID) {
      this.idb = idb;
        this.inloggadAnvandare = inloggadAnvandare;
        this.projektID = projektID;
        initComponents();
        fyllDropdownProjekt();
        fyllDropdownKoppladePartners(projektID);
        
        // Lägg till lyssnare för ComboValjProjekt
        ComboValjProjekt.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                ComboValjProjektItemStateChanged(evt);
            }
        });
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
    
    // Fyll ComboBox med kopplade partners för det valda projektet
    private void fyllDropdownKoppladePartners(String projektID) {
        try {
            // SQL-fråga som hämtar partners kopplade till projektet
            String query = "SELECT partner.pid, partner.namn " +
                           "FROM projekt_partner " +
                           "JOIN partner ON projekt_partner.partner_pid = partner.pid " +
                           "WHERE projekt_partner.pid = " + projektID;

            ArrayList<HashMap<String, String>> koppladePartners = idb.fetchRows(query);

            // Rensa och fyll ComboBox
            ComboTaBortPartner.removeAllItems();
            ComboTaBortPartner.addItem("Välj partner");

            // Lägg till varje partner i comboboxen
            for (HashMap<String, String> partner : koppladePartners) {
                String partnerInfo = partner.get("pid") + " - " + partner.get("namn");
                ComboTaBortPartner.addItem(partnerInfo);
            }
        } catch (InfException e) {
            JOptionPane.showMessageDialog(this, "Kunde inte fylla kopplade partners: " + e.getMessage());
        }
    }

    // Metod för att ta bort en partner från det valda projektet
    private void taBortPartnerFrånProjekt(String projektID) {
        try {
            // Hämta den valda partnern från ComboBoxen
            String valdPartner = (String) ComboTaBortPartner.getSelectedItem();
            if (valdPartner == null || valdPartner.equals("Välj partner")) {
                JOptionPane.showMessageDialog(this, "Välj en partner att ta bort.");
                return;
            }

            // Extrahera partnerID från den valda partnern
            String partnerID = valdPartner.split(" - ")[0]; // Hämta partnerID

            // SQL-fråga för att ta bort den valda partnern från projektet
            String deleteQuery = "DELETE FROM projekt_partner " +
                                 "WHERE pid = " + projektID + " AND partner_pid = " + partnerID;

            // Exekvera delete-frågan
            idb.delete(deleteQuery);

            JOptionPane.showMessageDialog(this, "Partner har tagits bort från projektet.");
            fyllDropdownKoppladePartners(projektID); // Uppdatera ComboBoxen efter borttagning

        } catch (InfException e) {
            JOptionPane.showMessageDialog(this, "Kunde inte ta bort partner: " + e.getMessage());
        }
    }

    // Hantera val av projekt i ComboValjProjekt
    private void ComboValjProjektItemStateChanged(java.awt.event.ItemEvent evt) {
        if (evt.getStateChange() == java.awt.event.ItemEvent.SELECTED) {
            String selectedProjekt = (String) ComboValjProjekt.getSelectedItem();
            if (!selectedProjekt.equals("Välj projekt")) {
                // Extrahera projektID och spara i global variabel
                projektID = selectedProjekt.split(" - ")[0];
                // Anropa fyllDropdownKoppladePartners för att uppdatera partnerlistan
                fyllDropdownKoppladePartners(projektID);
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
        btnTaBort = new javax.swing.JButton();
        btnTillbaka = new javax.swing.JButton();
        ComboTaBortPartner = new javax.swing.JComboBox<>();
        lblValjPartner = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        ComboValjProjekt = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("Ta bort partner från projekt");

        btnTaBort.setText("Ta bort");
        btnTaBort.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTaBortActionPerformed(evt);
            }
        });

        btnTillbaka.setText("Tillbaka");
        btnTillbaka.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTillbakaActionPerformed(evt);
            }
        });

        ComboTaBortPartner.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        lblValjPartner.setText("Välj partner");

        jLabel2.setText("Välj projekt");

        ComboValjProjekt.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(99, 99, 99)
                        .addComponent(jLabel1))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addComponent(btnTaBort)
                        .addGap(204, 204, 204)
                        .addComponent(btnTillbaka))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addComponent(jLabel2)
                        .addGap(2, 2, 2)
                        .addComponent(ComboValjProjekt, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(16, 16, 16)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblValjPartner)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 9, Short.MAX_VALUE)
                .addComponent(ComboTaBortPartner, javax.swing.GroupLayout.PREFERRED_SIZE, 277, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(42, 42, 42))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addComponent(jLabel1)
                .addGap(22, 22, 22)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(ComboValjProjekt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 67, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ComboTaBortPartner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblValjPartner))
                .addGap(62, 62, 62)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnTaBort)
                    .addComponent(btnTillbaka))
                .addGap(25, 25, 25))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnTaBortActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTaBortActionPerformed
    taBortPartnerFrånProjekt(projektID);
    }//GEN-LAST:event_btnTaBortActionPerformed

    private void btnTillbakaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTillbakaActionPerformed
    new AnsvarProjektPartner(idb, inloggadAnvandare).setVisible(true);
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
            java.util.logging.Logger.getLogger(AnsvarProjektPartnerTaBort.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AnsvarProjektPartnerTaBort.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AnsvarProjektPartnerTaBort.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AnsvarProjektPartnerTaBort.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                //new AnsvarProjektPartnerTaBort().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> ComboTaBortPartner;
    private javax.swing.JComboBox<String> ComboValjProjekt;
    private javax.swing.JButton btnTaBort;
    private javax.swing.JButton btnTillbaka;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel lblValjPartner;
    // End of variables declaration//GEN-END:variables
}
