/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package systemvetenskapligaprojektet;
import oru.inf.InfDB;
import oru.inf.InfException;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.JOptionPane;

/**
 *
 * @author mejaa
 */
public class AnsvarProjektHandlaggareLaggTill extends javax.swing.JFrame {

    private static InfDB idb;
    private String inloggadAnvandare;
    private String valProjektID; // Deklarera global variabel för valprojekt ID

    // Skapande av komponenter och initialisering
public AnsvarProjektHandlaggareLaggTill(InfDB idb, String inloggadAnvandare) {
    this.idb = idb;
    this.inloggadAnvandare = inloggadAnvandare;  
    initComponents(); // Initialisera GUI-komponenter
    fyllDropdownProjekt(); // Fyll dropdown med projekt
    fyllDropdownEjKoppladeHandlaggare(); // Fyll dropdown med handläggare
    lblLyckat.setVisible(false);
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
            // Anropa fyllDropdownEjKoppladeHandlaggare för att uppdatera handläggarlistan
            fyllDropdownEjKoppladeHandlaggare();
        }
    }
}
    // Fyll projekt dropdown (ComboValjProjekt) metod
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

    //Fyll handläggare dropdown (ComboValjHandlaggare) metod
    private void fyllDropdownEjKoppladeHandlaggare() {
        try {
            // Hämta projektchefens aid (användarens aid)
            String projektChefAidQuery = "SELECT aid FROM anstalld WHERE epost = '" + inloggadAnvandare + "';";
            String projektChefAid = idb.fetchSingle(projektChefAidQuery);

            // SQL-fråga för att hämta handläggare som inte är kopplade till det valda projektet och inte är projektchef
            String selectHandlaggare = "SELECT anstalld.aid, anstalld.fornamn, anstalld.efternamn " +
                                       "FROM anstalld " +
                                       "WHERE anstalld.aid NOT IN (SELECT aid FROM ans_proj WHERE pid = '" + valProjektID + "') " +
                                       "AND anstalld.aid != '" + projektChefAid + "' " +  // Uteslut projektchefen här
                                       "AND anstalld.aid IN (SELECT aid FROM handlaggare) " +
                                       "ORDER BY anstalld.aid ASC;";  // Sortera handläggare efter aid i stigande ordning

            // Hämta handläggare som inte är kopplade till det valda projektet och inte är projektchef
            ArrayList<HashMap<String, String>> handlaggare = idb.fetchRows(selectHandlaggare);

            if (handlaggare != null && !handlaggare.isEmpty()) {
                // Rensa och fyll ComboBox med handläggare
                ComboValjHandlaggare.removeAllItems();
                ComboValjHandlaggare.addItem("Välj handläggare");

                // Lägg till varje handläggare i ComboBoxen
                for (HashMap<String, String> handlaggareInfo : handlaggare) {
                    String handlaggareText = handlaggareInfo.get("aid") + " - " + handlaggareInfo.get("fornamn") + " " + handlaggareInfo.get("efternamn");
                    ComboValjHandlaggare.addItem(handlaggareText);
                }

                // Lämna det första neutrala alternativet valt
                ComboValjHandlaggare.setSelectedIndex(0);
            } else {
                JOptionPane.showMessageDialog(this, "Inga handläggare hittades för detta projekt.");
            }
        } catch (InfException ex) {
            JOptionPane.showMessageDialog(this, "Ett fel inträffade vid hämtning av handläggare: " + ex.getMessage());
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

        lblLaggTillHandlaggare = new javax.swing.JLabel();
        lblHandlaggareInfo = new javax.swing.JLabel();
        lblProjektInfo = new javax.swing.JLabel();
        btnTillbaka = new javax.swing.JButton();
        lblLyckat = new javax.swing.JLabel();
        ComboValjProjekt = new javax.swing.JComboBox<>();
        ComboValjHandlaggare = new javax.swing.JComboBox<>();
        btnSpara = new javax.swing.JButton();
        lblLaggTillHandlaggare1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        lblLaggTillHandlaggare.setFont(new java.awt.Font("Microsoft JhengHei UI", 1, 18)); // NOI18N
        lblLaggTillHandlaggare.setText("LÄGG TILL EN HANDLÄGGARE");

        lblHandlaggareInfo.setFont(new java.awt.Font("Microsoft JhengHei UI", 1, 12)); // NOI18N
        lblHandlaggareInfo.setText("Handläggare");

        lblProjektInfo.setFont(new java.awt.Font("Microsoft JhengHei UI", 1, 12)); // NOI18N
        lblProjektInfo.setText("Projekt");

        btnTillbaka.setText("Tillbaka");
        btnTillbaka.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTillbakaActionPerformed(evt);
            }
        });

        lblLyckat.setFont(new java.awt.Font("Microsoft JhengHei UI Light", 2, 12)); // NOI18N
        lblLyckat.setForeground(new java.awt.Color(0, 153, 0));
        lblLyckat.setText("Handläggaren har lagts till!");

        ComboValjProjekt.setFont(new java.awt.Font("Microsoft JhengHei UI", 0, 12)); // NOI18N
        ComboValjProjekt.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        ComboValjHandlaggare.setFont(new java.awt.Font("Microsoft JhengHei UI", 0, 12)); // NOI18N
        ComboValjHandlaggare.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        btnSpara.setText("Spara");
        btnSpara.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSparaActionPerformed(evt);
            }
        });

        lblLaggTillHandlaggare1.setFont(new java.awt.Font("Microsoft JhengHei UI", 1, 18)); // NOI18N
        lblLaggTillHandlaggare1.setText("TILL DITT PROJEKT");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblLaggTillHandlaggare1)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblHandlaggareInfo)
                                    .addComponent(lblProjektInfo))
                                .addGap(35, 35, 35)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(ComboValjHandlaggare, 0, 178, Short.MAX_VALUE)
                                    .addComponent(ComboValjProjekt, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addComponent(lblLaggTillHandlaggare))
                        .addContainerGap(34, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblLyckat, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnSpara)
                                .addGap(18, 18, 18)
                                .addComponent(btnTillbaka)))
                        .addGap(35, 35, 35))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addComponent(lblLaggTillHandlaggare, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(lblLaggTillHandlaggare1, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblProjektInfo, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ComboValjProjekt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblHandlaggareInfo)
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

    private void btnTillbakaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTillbakaActionPerformed
        new AnsvarProjektHandlaggare(idb, inloggadAnvandare).setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_btnTillbakaActionPerformed

    private void btnSparaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSparaActionPerformed
        try {
            String selectedHandlaggare = (String) ComboValjHandlaggare.getSelectedItem();
            if (selectedHandlaggare == null || selectedHandlaggare.equals("Välj handläggare")) {
                JOptionPane.showMessageDialog(this, "Välj en handläggare.");
                return;
            }

            // Extrahera handläggarID från den valda dropdown-texten (format: "aid - fornamn efternamn")
            String handlaggareID = selectedHandlaggare.split(" - ")[0];

            // Hämta det valda projektet från ComboValjProjekt
            String selectedProjekt = (String) ComboValjProjekt.getSelectedItem();
            if (selectedProjekt == null || selectedProjekt.equals("Välj projekt")) {
                JOptionPane.showMessageDialog(this, "Välj ett projekt.");
                return;
            }

            // Extrahera projektID från den valda texten i ComboBox (t.ex., "1 - Projekt A")
            String projektID = selectedProjekt.split(" - ")[0];

            // Lägg till handläggare i projektet
            String insertHandlaggareInProject = "INSERT INTO ans_proj (pid, aid) VALUES (" + projektID + ", " + handlaggareID + ");";
            idb.insert(insertHandlaggareInProject);
            lblLyckat.setVisible(true);
            
        } catch (InfException e){
            JOptionPane.showMessageDialog(this, "Kunde inte lägga till handläggare: " + e.getMessage());
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

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                //new laggTillHandlaggare().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> ComboValjHandlaggare;
    private javax.swing.JComboBox<String> ComboValjProjekt;
    private javax.swing.JButton btnSpara;
    private javax.swing.JButton btnTillbaka;
    private javax.swing.JLabel lblHandlaggareInfo;
    private javax.swing.JLabel lblLaggTillHandlaggare;
    private javax.swing.JLabel lblLaggTillHandlaggare1;
    private javax.swing.JLabel lblLyckat;
    private javax.swing.JLabel lblProjektInfo;
    // End of variables declaration//GEN-END:variables
}
