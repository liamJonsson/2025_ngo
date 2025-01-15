/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package systemvetenskapligaprojektet;
import java.math.BigDecimal;
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
    private double enKostnad;

    /**
     * Creates new form RedigeraProjekt
     */
    public RedigeraProjekt(InfDB idb, String inloggadAnvandare) {
        initComponents();
        this.idb = idb;
        this.inloggadAnvandare = inloggadAnvandare;

        // Dölj felmeddelanden vid start
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

        lblProjektID.setFont(new java.awt.Font("Microsoft JhengHei UI", 1, 12)); // NOI18N
        lblProjektID.setText("Välj projekt att redigera");

        lblProjektNamn.setFont(new java.awt.Font("Microsoft JhengHei UI", 1, 12)); // NOI18N
        lblProjektNamn.setText("Namn");

        lblBeskrivning.setFont(new java.awt.Font("Microsoft JhengHei UI", 1, 12)); // NOI18N
        lblBeskrivning.setText("Beskrivning");

        lblStartdatum.setFont(new java.awt.Font("Microsoft JhengHei UI", 1, 12)); // NOI18N
        lblStartdatum.setText("Startdatum");

        lblSlutdatum.setFont(new java.awt.Font("Microsoft JhengHei UI", 1, 12)); // NOI18N
        lblSlutdatum.setText("Slutdatum");

        lblKostnad.setFont(new java.awt.Font("Microsoft JhengHei UI", 1, 12)); // NOI18N
        lblKostnad.setText("Kostnad");

        lblStatus.setFont(new java.awt.Font("Microsoft JhengHei UI", 1, 12)); // NOI18N
        lblStatus.setText("Status");

        lblPrioritet.setFont(new java.awt.Font("Microsoft JhengHei UI", 1, 12)); // NOI18N
        lblPrioritet.setText("Prioritet");

        lblProjektchef.setFont(new java.awt.Font("Microsoft JhengHei UI", 1, 12)); // NOI18N
        lblProjektchef.setText("Projektchef");

        lblLand.setFont(new java.awt.Font("Microsoft JhengHei UI", 1, 12)); // NOI18N
        lblLand.setText("Land");

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

        lblFelmeddelandeStartdatum.setFont(new java.awt.Font("Microsoft JhengHei UI Light", 2, 12)); // NOI18N
        lblFelmeddelandeStartdatum.setForeground(new java.awt.Color(255, 0, 0));
        lblFelmeddelandeStartdatum.setText("Använd korrekt datumformat: YYYY-MM-DD");

        lblFelmeddelandeSlutdatum.setFont(new java.awt.Font("Microsoft JhengHei UI Light", 2, 12)); // NOI18N
        lblFelmeddelandeSlutdatum.setForeground(new java.awt.Color(255, 0, 0));
        lblFelmeddelandeSlutdatum.setText("Använd korrekt datumformat: YYYY-MM-DD");
        lblFelmeddelandeSlutdatum.setToolTipText("");

        lblFelmeddelandeKostnad.setFont(new java.awt.Font("Microsoft JhengHei UI Light", 2, 12)); // NOI18N
        lblFelmeddelandeKostnad.setForeground(new java.awt.Color(255, 0, 0));
        lblFelmeddelandeKostnad.setText("Använd korrekt format: XXXX.XX");

        lblFelmeddelandeProjektchef.setFont(new java.awt.Font("Microsoft JhengHei UI Light", 2, 12)); // NOI18N
        lblFelmeddelandeProjektchef.setForeground(new java.awt.Color(255, 0, 0));
        lblFelmeddelandeProjektchef.setText("Välj ett existerande anställningsID");

        lblFelmeddelandeLand.setFont(new java.awt.Font("Microsoft JhengHei UI Light", 2, 12)); // NOI18N
        lblFelmeddelandeLand.setForeground(new java.awt.Color(255, 0, 0));
        lblFelmeddelandeLand.setText("Välj ett existerande landsID");

        lblAllaAnstallda.setFont(new java.awt.Font("Microsoft JhengHei UI", 1, 18)); // NOI18N
        lblAllaAnstallda.setText("REDIGERA PROJEKT");

        ComboValjProjektID.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        ComboValjProjektID.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ComboValjProjektIDActionPerformed(evt);
            }
        });

        lblLyckat.setFont(new java.awt.Font("Microsoft JhengHei UI Light", 2, 12)); // NOI18N
        lblLyckat.setForeground(new java.awt.Color(0, 153, 0));
        lblLyckat.setText("Projektet har ändrats! ");

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Hög", "Medel", "Låg" }));

        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Pågående", "Planerat", "Avslutad" }));

        lblFelmeddelande.setFont(new java.awt.Font("Microsoft JhengHei UI Light", 2, 12)); // NOI18N
        lblFelmeddelande.setForeground(new java.awt.Color(255, 0, 0));
        lblFelmeddelande.setText("Alla fält måste fyllas i");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblAllaAnstallda)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lblProjektNamn, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblBeskrivning, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblStartdatum, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(106, 106, 106)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(tfRedigeraBeskrivning, javax.swing.GroupLayout.PREFERRED_SIZE, 258, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(tfRedigeraStartdatum, javax.swing.GroupLayout.PREFERRED_SIZE, 258, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(tfRedigeraProjektNamn, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 258, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(lblKostnad, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblSlutdatum, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblProjektID, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(35, 35, 35)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblFelmeddelandeStartdatum, javax.swing.GroupLayout.PREFERRED_SIZE, 258, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(tfRedigeraSlutdatum)
                                .addComponent(tfRedigeraKostnad)
                                .addComponent(ComboValjProjektID, 0, 258, Short.MAX_VALUE))
                            .addComponent(lblFelmeddelandeSlutdatum, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblFelmeddelandeKostnad, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(lblLand, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(lblProjektchef, javax.swing.GroupLayout.DEFAULT_SIZE, 138, Short.MAX_VALUE)
                                .addComponent(lblPrioritet, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(lblStatus, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(lblFelmeddelande, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(35, 35, 35)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblFelmeddelandeProjektchef, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblFelmeddelandeLand, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(tfRedigeraProjektchef)
                                    .addComponent(tfRedigeraLand)
                                    .addComponent(jComboBox1, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jComboBox2, 0, 258, Short.MAX_VALUE))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblLyckat)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(btnSpara)
                                        .addGap(18, 18, 18)
                                        .addComponent(btnTillbaka)))))))
                .addGap(35, 35, 35))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addComponent(lblAllaAnstallda)
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblProjektID)
                    .addComponent(ComboValjProjektID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblProjektNamn)
                    .addComponent(tfRedigeraProjektNamn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblBeskrivning)
                    .addComponent(tfRedigeraBeskrivning, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblStartdatum)
                    .addComponent(tfRedigeraStartdatum, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(2, 2, 2)
                .addComponent(lblFelmeddelandeStartdatum)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblSlutdatum)
                    .addComponent(tfRedigeraSlutdatum, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(2, 2, 2)
                .addComponent(lblFelmeddelandeSlutdatum)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblKostnad)
                    .addComponent(tfRedigeraKostnad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(2, 2, 2)
                .addComponent(lblFelmeddelandeKostnad)
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
                    .addComponent(lblProjektchef)
                    .addComponent(tfRedigeraProjektchef, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(2, 2, 2)
                .addComponent(lblFelmeddelandeProjektchef)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblLand)
                    .addComponent(tfRedigeraLand, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(2, 2, 2)
                .addComponent(lblFelmeddelandeLand)
                .addGap(43, 43, 43)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnTillbaka)
                    .addComponent(btnSpara)
                    .addComponent(lblFelmeddelande))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblLyckat)
                .addGap(35, 35, 35))
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
        String textKostnad = tfRedigeraKostnad.getText();
        String projektchef = tfRedigeraProjektchef.getText();
        String land = tfRedigeraLand.getText();
        
        BigDecimal kostnad;
                        try{
                            kostnad = new BigDecimal(textKostnad);
                
                            //Kontrollera (12, 2)
                            if(kostnad.precision() > 12 || kostnad.scale() > 2){
                                throw new NumberFormatException();
                            }
                        }
                        catch(NumberFormatException ex){
                            lblFelmeddelandeKostnad.setVisible(true);
                            return;
                        }

        if (projektnamn.isEmpty() || startdatum.isEmpty() || slutdatum.isEmpty() || textKostnad.isEmpty() || projektchef.isEmpty() || land.isEmpty()) {
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
