/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package systemvetenskapligaprojektet;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import oru.inf.InfDB; //importeras i alla klasser som vi ska använda
import oru.inf.InfException; //importeras i alla klasser som vi ska använda
/**
 *
 * @author iftinserar
 */
public class AnsvarProjektPartnerLaggTill extends javax.swing.JFrame {

    private static InfDB idb;
    private String inloggadAnvandare;
    private String valProjektID; // Deklarera global variabel för valprojekt ID

    /**
     * Creates new form AnsvarProjektPartnerLaggTill
     */
    public AnsvarProjektPartnerLaggTill(InfDB idb, String inloggadAnvandare) {
        this.idb = idb;
        this.inloggadAnvandare = inloggadAnvandare;  
        initComponents();
        fyllDropdownPartners();
        fyllDropdownProjekt();

        // Lägg till lyssnare för ComboBoxen
        setupComboBoxListeners();
    }

    // Metod för att lägga till lyssnare till ComboBox-komponenterna
    private void setupComboBoxListeners() {
        ComboValjProjekt.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                ComboValjProjektItemStateChanged(evt);
            }
        });
    }

    // Existerande metod för att hantera ItemStateChange på ComboValjProjekt
    private void ComboValjProjektItemStateChanged(java.awt.event.ItemEvent evt) {
        if (evt.getStateChange() == java.awt.event.ItemEvent.SELECTED) {
            String selectedProjekt = (String) ComboValjProjekt.getSelectedItem();
            if (!selectedProjekt.equals("Välj projekt")) {
                // Extrahera projektID och spara i global variabel
                valProjektID = selectedProjekt.split(" - ")[0];
                // Anropa fyllDropdownPartners för att uppdatera partnerlistan
                fyllDropdownPartners();
            }
        }
    }    
    
    //Fyll projekt dropdown (ComboValjProjekt) metod
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

    // Fyll partner dropdown (ComboValjPartner) metod
    private void fyllDropdownPartners() {
        try {
            // SQL-fråga för att hämta partners som inte är kopplade till det valda projektet
            String selectPartners = "SELECT partner.pid, partner.namn " +
                                     "FROM partner " +
                                     "WHERE partner.pid NOT IN (SELECT partner_pid " +
                                     "FROM projekt_partner WHERE pid = '" + valProjektID + "');";

            // Hämta partners som inte är kopplade till det valda projektet
            ArrayList<HashMap<String, String>> partners = idb.fetchRows(selectPartners);

            if (partners != null && !partners.isEmpty()) {
                // Rensa och fyll ComboBox med partners
                ComboValjPartner.removeAllItems();
                ComboValjPartner.addItem("Välj partner");

                // Lägg till varje partner i ComboBoxen
                for (HashMap<String, String> partner : partners) {
                    String partnerInfo = partner.get("pid") + " - " + partner.get("namn");
                    ComboValjPartner.addItem(partnerInfo);
                }

                // Lämna det första neutrala alternativet valt
                ComboValjPartner.setSelectedIndex(0);
            } else {
                JOptionPane.showMessageDialog(this, "Inga partners hittades för detta projekt.");
            }
        } catch (InfException ex) {
            JOptionPane.showMessageDialog(this, "Ett fel inträffade vid hämtning av partners: " + ex.getMessage());
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
        lblValjPartner = new javax.swing.JLabel();
        btnSpara = new javax.swing.JButton();
        btnTillbaka = new javax.swing.JButton();
        ComboValjPartner = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        ComboValjProjekt = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("Lägg till ny partner för projekt");

        lblValjPartner.setText("Välj Partner");

        btnSpara.setText("Spara");
        btnSpara.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSparaActionPerformed(evt);
            }
        });

        btnTillbaka.setText("Tillbaka");
        btnTillbaka.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTillbakaActionPerformed(evt);
            }
        });

        ComboValjPartner.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel2.setText("Välj projekt");

        ComboValjProjekt.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(btnSpara)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnTillbaka)
                .addGap(23, 23, 23))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addComponent(jLabel1))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(lblValjPartner)
                        .addGap(18, 18, 18)
                        .addComponent(ComboValjPartner, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel2)
                        .addGap(18, 18, 18)
                        .addComponent(ComboValjProjekt, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap(170, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel2)
                    .addComponent(ComboValjProjekt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(24, 24, 24)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblValjPartner)
                    .addComponent(ComboValjPartner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnTillbaka)
                    .addComponent(btnSpara))
                .addContainerGap(47, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnTillbakaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTillbakaActionPerformed
    new AnsvarProjektPartner(idb, inloggadAnvandare).setVisible(true);
    this.setVisible(false);
    }//GEN-LAST:event_btnTillbakaActionPerformed

    private void btnSparaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSparaActionPerformed
        try {
            String selectedPartner = (String) ComboValjPartner.getSelectedItem();
            if (selectedPartner == null || selectedPartner.equals("Välj partner")) {
                JOptionPane.showMessageDialog(this, "Välj en partner.");
                return;
            }

            // Extrahera partnerID från den valda dropdown-texten (format: "pid - namn")
            String partnerID = selectedPartner.split(" - ")[0];

            // Hämta det valda projektet från ComboValjProjekt
            String selectedProjekt = (String) ComboValjProjekt.getSelectedItem();
            if (selectedProjekt == null || selectedProjekt.equals("Välj projekt")) {
                JOptionPane.showMessageDialog(this, "Välj ett projekt.");
                return;
            }

            // Extrahera projektID från den valda texten i ComboBox (t.ex., "1 - Projekt A")
            String projektID = selectedProjekt.split(" - ")[0];

            // Lägg till partner i projektet
            String insertPartnerInProject = "INSERT INTO projekt_partner (pid, partner_pid) VALUES (" + projektID + ", " + partnerID + ");";
            System.out.println("SQL-query för att lägga till partner i projekt: " + insertPartnerInProject);  // Felsökningsutskrift
            idb.insert(insertPartnerInProject);

            JOptionPane.showMessageDialog(this, "Partner har lagts till i projektet.");

            // Återgå till föregående vy
            new AnsvarProjektPartner(idb, inloggadAnvandare).setVisible(true);
            this.setVisible(false);
        } catch (InfException ex) {
            JOptionPane.showMessageDialog(this, "Ett fel inträffade vid sparande av partner.");
        }
    }//GEN-LAST:event_btnSparaActionPerformed

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
            java.util.logging.Logger.getLogger(AnsvarProjektPartnerLaggTill.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AnsvarProjektPartnerLaggTill.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AnsvarProjektPartnerLaggTill.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AnsvarProjektPartnerLaggTill.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                //new AnsvarProjektPartnerLaggTill().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> ComboValjPartner;
    private javax.swing.JComboBox<String> ComboValjProjekt;
    private javax.swing.JButton btnSpara;
    private javax.swing.JButton btnTillbaka;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel lblValjPartner;
    // End of variables declaration//GEN-END:variables
}
