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
        lblLyckat.setVisible(false);

        
        //Lägg till lyssnare för ComboValjProjekt
        ComboValjProjekt.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                ComboValjProjektItemStateChanged(evt);
            }
        });
    }
    
    //Fyll ComboBox med projekt där den inloggade användaren är projektchef
    private void fyllDropdownProjekt() {
        try {
            //Hämta den inloggade användarens aid (anställd ID)
            String aidQuery = "SELECT aid FROM anstalld WHERE epost = '" + inloggadAnvandare + "';";
            String aid = idb.fetchSingle(aidQuery);

            if (aid != null) {
                //Hämta alla projekt som den inloggade användaren är projektchef för
                String selectProjekt = "SELECT pid, projektnamn FROM projekt WHERE projektchef = '" + aid + "';";
                ArrayList<HashMap<String, String>> projektResult = idb.fetchRows(selectProjekt);

                if (projektResult != null && !projektResult.isEmpty()) {
                    // Rensa och fyll projekt ComboBox
                    ComboValjProjekt.removeAllItems();
                    ComboValjProjekt.addItem("Välj projekt");

                    //Lägg till varje projekt i ComboBoxen
                    for (HashMap<String, String> projekt : projektResult) {
                        String projektInfo = projekt.get("pid") + " - " + projekt.get("projektnamn");
                        ComboValjProjekt.addItem(projektInfo);
                    }

                    //Lämna det första neutrala alternativet valt
                    ComboValjProjekt.setSelectedIndex(0);
                } else {
                    JOptionPane.showMessageDialog(this, "Du har inga projekt som projektchef.");
                }
            }
        } catch (InfException ex) {
            JOptionPane.showMessageDialog(this, "Ett fel inträffade vid hämtning av projekt: " + ex.getMessage());
        }
    }
    
    //Fyll ComboBox med kopplade partners för det valda projektet
    private void fyllDropdownKoppladePartners(String projektID) {
        try {
            //SQL-fråga som hämtar partners kopplade till projektet
            String query = "SELECT partner.pid, partner.namn " +
                           "FROM projekt_partner " +
                           "JOIN partner ON projekt_partner.partner_pid = partner.pid " +
                           "WHERE projekt_partner.pid = " + projektID;

            ArrayList<HashMap<String, String>> koppladePartners = idb.fetchRows(query);

            //Rensa och fyll ComboBox
            ComboTaBortPartner.removeAllItems();
            ComboTaBortPartner.addItem("Välj partner");

            //Lägg till varje partner i comboboxen
            for (HashMap<String, String> partner : koppladePartners) {
                String partnerInfo = partner.get("pid") + " - " + partner.get("namn");
                ComboTaBortPartner.addItem(partnerInfo);
            }
        } catch (InfException e) {
            JOptionPane.showMessageDialog(this, "Kunde inte fylla kopplade partners: " + e.getMessage());
        }
    }

    //Metod för att ta bort en partner från det valda projektet
    private void taBortPartnerFrånProjekt(String projektID) {
        try {
            //Hämta den valda partnern från ComboBoxen
            String valdPartner = (String) ComboTaBortPartner.getSelectedItem();
            if (valdPartner == null || valdPartner.equals("Välj partner")) {
                JOptionPane.showMessageDialog(this, "Välj en partner att ta bort.");
                return;
            }

            //Extrahera partnerID från den valda partnern
            String partnerID = valdPartner.split(" - ")[0]; // Hämta partnerID

            //SQL-fråga för att ta bort den valda partnern från projektet
            String deleteQuery = "DELETE FROM projekt_partner " +
                                 "WHERE pid = " + projektID + " AND partner_pid = " + partnerID;

            //Exekvera delete-frågan
            idb.delete(deleteQuery);
            lblLyckat.setVisible(true);
            fyllDropdownKoppladePartners(projektID); // Uppdatera ComboBoxen efter borttagning

            }catch(InfException ex){
            System.out.println(ex);
            }      
    }

    //Hantera val av projekt i ComboValjProjekt
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

        btnSpara = new javax.swing.JButton();
        btnTillbaka = new javax.swing.JButton();
        ComboTaBortPartner = new javax.swing.JComboBox<>();
        lblValjPartner = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        ComboValjProjekt = new javax.swing.JComboBox<>();
        lblLyckat = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        lblLaggTillHandlaggare1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

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

        ComboTaBortPartner.setFont(new java.awt.Font("Microsoft JhengHei UI", 0, 12)); // NOI18N
        ComboTaBortPartner.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        lblValjPartner.setFont(new java.awt.Font("Microsoft JhengHei UI", 1, 12)); // NOI18N
        lblValjPartner.setText("Partner");

        jLabel2.setFont(new java.awt.Font("Microsoft JhengHei UI", 1, 12)); // NOI18N
        jLabel2.setText("Projekt");

        ComboValjProjekt.setFont(new java.awt.Font("Microsoft JhengHei UI", 0, 12)); // NOI18N
        ComboValjProjekt.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        lblLyckat.setFont(new java.awt.Font("Microsoft JhengHei UI Light", 2, 12)); // NOI18N
        lblLyckat.setForeground(new java.awt.Color(0, 153, 0));
        lblLyckat.setText("Partnern har tagits bort!");

        jLabel1.setFont(new java.awt.Font("Microsoft JhengHei UI", 1, 18)); // NOI18N
        jLabel1.setText("TA BORT EN PARTNER");

        lblLaggTillHandlaggare1.setFont(new java.awt.Font("Microsoft JhengHei UI", 1, 18)); // NOI18N
        lblLaggTillHandlaggare1.setText("FRÅN DITT PROJEKT");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblLaggTillHandlaggare1)
                    .addComponent(jLabel1)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addGap(35, 35, 35)
                                .addComponent(ComboValjProjekt, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblValjPartner)
                                .addGap(35, 35, 35)
                                .addComponent(ComboTaBortPartner, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblLyckat)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnSpara)
                                .addGap(18, 18, 18)
                                .addComponent(btnTillbaka)))))
                .addGap(35, 35, 35))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addComponent(jLabel1)
                .addGap(0, 0, 0)
                .addComponent(lblLaggTillHandlaggare1, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(ComboValjProjekt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblValjPartner)
                    .addComponent(ComboTaBortPartner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
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
    taBortPartnerFrånProjekt(projektID);
    }//GEN-LAST:event_btnSparaActionPerformed

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
    private javax.swing.JButton btnSpara;
    private javax.swing.JButton btnTillbaka;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel lblLaggTillHandlaggare1;
    private javax.swing.JLabel lblLyckat;
    private javax.swing.JLabel lblValjPartner;
    // End of variables declaration//GEN-END:variables
}
