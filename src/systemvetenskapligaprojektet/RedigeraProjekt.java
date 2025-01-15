/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package systemvetenskapligaprojektet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import oru.inf.InfDB;
import oru.inf.InfException;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
/**
 *
 * @author iftinserar
 */
public class RedigeraProjekt extends javax.swing.JFrame {
    private static InfDB idb;
    private String inloggadAnvandare;
    private int valtProjektID;

    /**
     * Creates new form RedigeraProjekt
     */
    public RedigeraProjekt(InfDB idb, String inloggadAnvandare) {
        initComponents();
        this.idb = idb;
        this.inloggadAnvandare = inloggadAnvandare;

        // Dölj felmeddelanden vid start
        lblFelmeddelandeProjektID.setVisible(false);
        lblFelmeddelandeStartdatum.setVisible(false);
        lblFelmeddelandeSlutdatum.setVisible(false);
        lblFelmeddelandeKostnad.setVisible(false);
        lblFelmeddelandeProjektchef.setVisible(false);
        lblFelmeddelandeLand.setVisible(false);
        lblLyckat.setVisible(false);
        lblFelmeddelande.setVisible(false);

        fyllComboBoxProjekt();
    }

    // Fyll combobox med projekt
    private void fyllComboBoxProjekt() {
        try {
            String query = "SELECT pid, projektnamn FROM projekt";
            ArrayList<HashMap<String, String>> projektLista = idb.fetchRows(query);

            ComboValjProjektID.removeAllItems();
            ComboValjProjektID.addItem("Välj projekt");

            if (projektLista != null) {
                for (HashMap<String, String> projekt : projektLista) {
                    String projektInfo = projekt.get("pid") + " - " + projekt.get("projektnamn");
                    ComboValjProjektID.addItem(projektInfo);
                }
            }
        } catch (InfException e) {
            JOptionPane.showMessageDialog(this, "Kunde inte fylla projekt: " + e.getMessage());
        }
    }

    // När ett projekt väljs från comboboxen
    private void ComboProjektActionPerformed(java.awt.event.ActionEvent evt) {
        String valtProjekt = (String) ComboValjProjektID.getSelectedItem();
        System.out.println("Valt projekt: " + valtProjekt); // Kontrollera valt värde

    if (!valtProjekt.equals("Välj projekt")) {
        String[] delar = valtProjekt.split(" - ");
        valtProjektID = Integer.parseInt(delar[0]);
        System.out.println("Valt projekt-ID: " + valtProjektID); // Kontrollera projekt-ID
        fyllTextfields();
        }
    }

    // Fyll textfälten med projektets data
    private void fyllTextfields() {
    ComboValjProjektID.addActionListener(evt -> {
        String valtProjekt = ComboValjProjektID.getSelectedItem().toString();

        if (!valtProjekt.equals("Välj projekt")) {
            // Regex för att extrahera projekt-ID
            Pattern pattern = Pattern.compile("(\\d+) - ");
            Matcher matcher = pattern.matcher(valtProjekt);

            if (matcher.find()) {
                // Extrahera projekt-ID
                valtProjektID = Integer.parseInt(matcher.group(1));

                try {
                    // SQL-fråga för att hämta projektdata
                    String query = "SELECT * FROM projekt WHERE pid = " + valtProjektID + ";";

                    ArrayList<HashMap<String, String>> projektInfo = idb.fetchRows(query);

                    if (projektInfo != null) {
                        for (HashMap<String, String> rad : projektInfo) {
                            for (String attribut : rad.keySet()) {
                                switch (attribut) {
                                    case "projektnamn":
                                        tfRedigeraProjektNamn.setText(rad.get(attribut));
                                        break;
                                    case "beskrivning":
                                        tfRedigeraBeskrivning.setText(rad.get(attribut));
                                        break;
                                    case "startdatum":
                                        tfRedigeraStartdatum.setText(rad.get(attribut));
                                        break;
                                    case "slutdatum":
                                        tfRedigeraSlutdatum.setText(rad.get(attribut));
                                        break;
                                    case "kostnad":
                                        tfRedigeraKostnad.setText(rad.get(attribut));
                                        break;
                                    case "projektchef":
                                        tfRedigeraProjektchef.setText(rad.get(attribut));
                                        break;
                                    case "land":
                                        tfRedigeraLand.setText(rad.get(attribut));
                                        break;
                                }
                            }
                        }
                    } else {
                        System.out.println("Inga data hittades för valt projekt-ID.");
                    }
                } catch (InfException ex) {
                    JOptionPane.showMessageDialog(this, "Fel vid hämtning av projektdata: " + ex.getMessage());
                    ex.printStackTrace();
                }
            } else {
                System.out.println("Kunde inte extrahera projekt-ID från valet.");
            }
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

        lblProjektID = new javax.swing.JLabel();
        lblProjektNamn = new javax.swing.JLabel();
        lblBeskrivning = new javax.swing.JLabel();
        lblStartdatum = new javax.swing.JLabel();
        lblSlutdatum = new javax.swing.JLabel();
        lblKostnad = new javax.swing.JLabel();
        lblStatus = new javax.swing.JLabel();
        lblPrioritet = new javax.swing.JLabel();
        lblProjektchef = new javax.swing.JLabel();
        lblLand = new javax.swing.JLabel();
        tfRedigeraProjektNamn = new javax.swing.JTextField();
        tfRedigeraStartdatum = new javax.swing.JTextField();
        tfRedigeraSlutdatum = new javax.swing.JTextField();
        tfRedigeraKostnad = new javax.swing.JTextField();
        tfRedigeraBeskrivning = new javax.swing.JTextField();
        tfRedigeraProjektchef = new javax.swing.JTextField();
        tfRedigeraLand = new javax.swing.JTextField();
        btnSpara = new javax.swing.JButton();
        btnTillbaka = new javax.swing.JButton();
        lblFelmeddelandeProjektID = new javax.swing.JLabel();
        lblFelmeddelandeStartdatum = new javax.swing.JLabel();
        lblFelmeddelandeSlutdatum = new javax.swing.JLabel();
        lblFelmeddelandeKostnad = new javax.swing.JLabel();
        lblFelmeddelandeProjektchef = new javax.swing.JLabel();
        lblFelmeddelandeLand = new javax.swing.JLabel();
        lblAllaAnstallda = new javax.swing.JLabel();
        ComboValjProjektID = new javax.swing.JComboBox<>();
        lblLyckat = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jComboBox2 = new javax.swing.JComboBox<>();
        lblFelmeddelande = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        lblProjektID.setText("Välj projekt att redigera");

        lblProjektNamn.setText("Namn");

        lblBeskrivning.setText("Beskrivning");

        lblStartdatum.setText("Startdatum");

        lblSlutdatum.setText("Slutdatum");

        lblKostnad.setText("Kostnad");

        lblStatus.setText("Status");

        lblPrioritet.setText("Prioritet");

        lblProjektchef.setText("Projektchef");

        lblLand.setText("Land");

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

        lblFelmeddelandeProjektID.setText("Ogiltigt ID");

        lblFelmeddelandeStartdatum.setText("Felaktigt format på startdatum");

        lblFelmeddelandeSlutdatum.setText("Felaktigt format på slutdatum");

        lblFelmeddelandeKostnad.setText("Felaktigt format på kostnad");

        lblFelmeddelandeProjektchef.setText("Vänligen fyll i giltigt ID för projektchef");

        lblFelmeddelandeLand.setText("Vänligen fyll i giltigt ID för land");

        lblAllaAnstallda.setFont(new java.awt.Font("Microsoft JhengHei UI", 1, 18)); // NOI18N
        lblAllaAnstallda.setText("REDIGERA PROJEKTET");

        ComboValjProjektID.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        ComboValjProjektID.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ComboValjProjektIDActionPerformed(evt);
            }
        });

        lblLyckat.setText("Projektet har ändrats! ");

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Hög", "Medel", "Låg" }));

        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Pågående", "Planerat", "Avslutad" }));

        lblFelmeddelande.setText("Alla fält måste fyllas i");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addComponent(lblAllaAnstallda))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblLand)
                            .addComponent(lblProjektchef)
                            .addComponent(lblPrioritet)
                            .addComponent(lblStatus)
                            .addComponent(lblKostnad)
                            .addComponent(lblSlutdatum)
                            .addComponent(lblStartdatum)
                            .addComponent(lblBeskrivning)
                            .addComponent(lblProjektNamn)
                            .addComponent(lblProjektID))
                        .addGap(52, 52, 52)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(tfRedigeraStartdatum, javax.swing.GroupLayout.DEFAULT_SIZE, 175, Short.MAX_VALUE)
                                    .addComponent(tfRedigeraSlutdatum)
                                    .addComponent(tfRedigeraKostnad)
                                    .addComponent(tfRedigeraLand)
                                    .addComponent(tfRedigeraProjektchef)
                                    .addComponent(tfRedigeraBeskrivning)
                                    .addComponent(tfRedigeraProjektNamn)
                                    .addComponent(ComboValjProjektID, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 99, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblFelmeddelandeProjektID)
                                    .addComponent(lblFelmeddelandeLand)
                                    .addComponent(lblFelmeddelandeProjektchef)
                                    .addComponent(lblFelmeddelandeKostnad)
                                    .addComponent(lblFelmeddelandeSlutdatum)
                                    .addComponent(lblFelmeddelandeStartdatum)))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jComboBox1, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jComboBox2, 0, 175, Short.MAX_VALUE))
                                .addGap(0, 0, Short.MAX_VALUE)))))
                .addGap(61, 61, 61))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(btnSpara)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblFelmeddelande, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblLyckat, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(68, 68, 68)
                .addComponent(btnTillbaka)
                .addGap(26, 26, 26))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(lblAllaAnstallda)
                .addGap(90, 90, 90)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblProjektID)
                    .addComponent(lblFelmeddelandeProjektID)
                    .addComponent(ComboValjProjektID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 42, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblProjektNamn)
                    .addComponent(tfRedigeraProjektNamn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblBeskrivning)
                    .addComponent(tfRedigeraBeskrivning, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblFelmeddelandeStartdatum)
                    .addComponent(tfRedigeraStartdatum, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblStartdatum))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tfRedigeraSlutdatum, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblSlutdatum)
                    .addComponent(lblFelmeddelandeSlutdatum))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tfRedigeraKostnad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblKostnad)
                    .addComponent(lblFelmeddelandeKostnad))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblStatus)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblPrioritet)
                    .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tfRedigeraProjektchef, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblFelmeddelandeProjektchef)
                    .addComponent(lblProjektchef))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tfRedigeraLand, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblFelmeddelandeLand)
                    .addComponent(lblLand))
                .addGap(37, 37, 37)
                .addComponent(lblFelmeddelande)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnTillbaka)
                    .addComponent(btnSpara)
                    .addComponent(lblLyckat))
                .addGap(19, 19, 19))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSparaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSparaActionPerformed
    boolean hasError = false;

    try {
        String projektnamn = tfRedigeraProjektNamn.getText();
        String beskrivning = tfRedigeraBeskrivning.getText();
        String startdatum = tfRedigeraStartdatum.getText();
        String slutdatum = tfRedigeraSlutdatum.getText();
        String kostnad = tfRedigeraKostnad.getText();
        String projektchef = tfRedigeraProjektchef.getText();
        String land = tfRedigeraLand.getText();

        if (projektnamn.isEmpty() || startdatum.isEmpty() || slutdatum.isEmpty() || kostnad.isEmpty() || projektchef.isEmpty() || land.isEmpty()) {
            lblFelmeddelande.setText("Alla fält måste fyllas i.");
            lblFelmeddelande.setVisible(true);
            hasError = true;
        }

        if (!hasError) {
            String updateQuery = String.format(
                    "UPDATE projekt SET projektnamn = '%s', beskrivning = '%s', startdatum = '%s', slutdatum = '%s', kostnad = %s, projektchef = %s, land = '%s' WHERE pid = %d",
                    projektnamn, beskrivning, startdatum, slutdatum, kostnad, projektchef, land, valtProjektID);

            idb.update(updateQuery);
            lblLyckat.setVisible(true);
        }
    }
         catch(InfException ex){
            System.out.println(ex);
        }
    }//GEN-LAST:event_btnSparaActionPerformed

    private void btnTillbakaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTillbakaActionPerformed
    new AllaProjekt(idb, inloggadAnvandare).setVisible(true);
        this.setVisible(false);        
    }//GEN-LAST:event_btnTillbakaActionPerformed

    private void ComboValjProjektIDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ComboValjProjektIDActionPerformed
         fyllTextfields();
    }//GEN-LAST:event_ComboValjProjektIDActionPerformed

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
            java.util.logging.Logger.getLogger(RedigeraProjekt.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(RedigeraProjekt.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(RedigeraProjekt.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(RedigeraProjekt.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new RedigeraProjekt(idb, "projektchef").setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> ComboValjProjektID;
    private javax.swing.JButton btnSpara;
    private javax.swing.JButton btnTillbaka;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JLabel lblAllaAnstallda;
    private javax.swing.JLabel lblBeskrivning;
    private javax.swing.JLabel lblFelmeddelande;
    private javax.swing.JLabel lblFelmeddelandeKostnad;
    private javax.swing.JLabel lblFelmeddelandeLand;
    private javax.swing.JLabel lblFelmeddelandeProjektID;
    private javax.swing.JLabel lblFelmeddelandeProjektchef;
    private javax.swing.JLabel lblFelmeddelandeSlutdatum;
    private javax.swing.JLabel lblFelmeddelandeStartdatum;
    private javax.swing.JLabel lblKostnad;
    private javax.swing.JLabel lblLand;
    private javax.swing.JLabel lblLyckat;
    private javax.swing.JLabel lblPrioritet;
    private javax.swing.JLabel lblProjektID;
    private javax.swing.JLabel lblProjektNamn;
    private javax.swing.JLabel lblProjektchef;
    private javax.swing.JLabel lblSlutdatum;
    private javax.swing.JLabel lblStartdatum;
    private javax.swing.JLabel lblStatus;
    private javax.swing.JTextField tfRedigeraBeskrivning;
    private javax.swing.JTextField tfRedigeraKostnad;
    private javax.swing.JTextField tfRedigeraLand;
    private javax.swing.JTextField tfRedigeraProjektNamn;
    private javax.swing.JTextField tfRedigeraProjektchef;
    private javax.swing.JTextField tfRedigeraSlutdatum;
    private javax.swing.JTextField tfRedigeraStartdatum;
    // End of variables declaration//GEN-END:variables
}
