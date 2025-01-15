/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package systemvetenskapligaprojektet;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;
import oru.inf.InfDB;
import oru.inf.InfException;
/**
 *
 * @author lisas
 */
public class RedigeraPartner extends javax.swing.JFrame {
    private static InfDB idb;
    private String inloggadAnvandare;
    private int pid;
    private int stadsID;
    private Validering validera;
    private String valtPartnerID;
    /**
     * Creates new form RedigeraPartner
     */
    public RedigeraPartner(InfDB idb, String inloggadAnvandare) {
        initComponents();
        this.inloggadAnvandare = inloggadAnvandare;
        this.idb = idb;
        lblFelEpost.setVisible(false);
        lblFelTelefon.setVisible(false);
        lblFelStad.setVisible(false);
        lblLyckat.setVisible(false);
        lblFelmeddelande.setVisible(false);

        fyllComboBoxPartner();
    }
    
    // Fyll ComboBox med alla partners
    private void fyllComboBoxPartner() {
        try {
            String query = "SELECT partner.pid, partner.namn FROM partner";
            ArrayList<HashMap<String, String>> partnerLista = idb.fetchRows(query);

            ComboValjPartner.removeAllItems();
            ComboValjPartner.addItem("Välj partner");

            if (partnerLista != null) {
                for (HashMap<String, String> partner : partnerLista) {
                    String partnerInfo = partner.get("pid") + " - " + partner.get("namn");
                    ComboValjPartner.addItem(partnerInfo);
                }
            }
        } catch (InfException e) {
            JOptionPane.showMessageDialog(this, "Kunde inte fylla partner: " + e.getMessage());
        }
    }


    // När en partner väljs från ComboBoxen
    private void ComboPartnerActionPerformed(java.awt.event.ActionEvent evt) {
        String valtPartner = (String) ComboValjPartner.getSelectedItem();

        // Kontrollera om valtPartner är null eller "Välj partner"
        if (valtPartner == null || valtPartner.equals("Välj partner")) {
            return;
        }

        String[] delar = valtPartner.split(" - ");
        pid = Integer.parseInt(delar[0]);

        fyllTextfields();
    }

    // Fyll textfälten med partnerdata
    private void fyllTextfields() {
        ComboValjPartner.addActionListener(evt -> {
            String valtPartner = ComboValjPartner.getSelectedItem().toString();

            if (!valtPartner.equals("Välj partner")) {
                // Regex för att extrahera partner-ID
                Pattern pattern = Pattern.compile("(\\d+) - ");
                Matcher matcher = pattern.matcher(valtPartner);

                if (matcher.find()) {
                    // Extrahera partner-ID
                    int valtPartnerID = Integer.parseInt(matcher.group(1));

                    try {
                        // SQL-fråga för att hämta partnerdata
                        String query = "SELECT * FROM partner WHERE pid = " + valtPartnerID + ";";

                        ArrayList<HashMap<String, String>> partnerInfo = idb.fetchRows(query);

                        if (partnerInfo != null && !partnerInfo.isEmpty()) {
                            // Hämta första raden (det är en rad per partner)
                            HashMap<String, String> rad = partnerInfo.get(0);

                            // Använd switch-case för att fylla textfälten
                            for (String attribut : rad.keySet()) {
                                switch (attribut) {
                                    case "namn":
                                        tfRedigeraPartnerNamn.setText(rad.get(attribut));
                                        break;
                                    case "kontaktperson":
                                        tfRedigeraKontaktP.setText(rad.get(attribut));
                                        break;
                                    case "kontaktepost":
                                        tfRedigeraKontaktEpost.setText(rad.get(attribut));
                                        break;
                                    case "telefon":
                                        tfRedigeraTelefon.setText(rad.get(attribut));
                                        break;
                                    case "adress":
                                        tfRedigeraAdress.setText(rad.get(attribut));
                                        break;
                                    case "branch":
                                        tfRedigeraBranch.setText(rad.get(attribut));
                                        break;
                                    case "stad":
                                        tfRedigeraStad.setText(rad.get(attribut));
                                        break;
                                }
                            }
                        } else {
                            System.out.println("Inga data hittades för vald partner-ID.");
                        }
                    } catch (InfException ex) {
                        JOptionPane.showMessageDialog(this, "Fel vid hämtning av partnerdata: " + ex.getMessage());
                        ex.printStackTrace();
                    }
                } else {
                    System.out.println("Kunde inte extrahera partner-ID från valet.");
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

        lblValutaFel = new javax.swing.JLabel();
        lblRedigeraPartner = new javax.swing.JLabel();
        tfRedigeraPartnerNamn = new javax.swing.JTextField();
        tfRedigeraKontaktP = new javax.swing.JTextField();
        tfRedigeraKontaktEpost = new javax.swing.JTextField();
        tfRedigeraTelefon = new javax.swing.JTextField();
        tfRedigeraAdress = new javax.swing.JTextField();
        tfRedigeraBranch = new javax.swing.JTextField();
        tfRedigeraStad = new javax.swing.JTextField();
        lblNamn = new javax.swing.JLabel();
        lblID = new javax.swing.JLabel();
        lblTelefon = new javax.swing.JLabel();
        lblKontaktP = new javax.swing.JLabel();
        lblKontaktEpost = new javax.swing.JLabel();
        lblAdress = new javax.swing.JLabel();
        lblStad = new javax.swing.JLabel();
        lblBranch = new javax.swing.JLabel();
        btnTillbaka = new javax.swing.JButton();
        btnSpara = new javax.swing.JButton();
        lblFelTelefon = new javax.swing.JLabel();
        lblFelStad = new javax.swing.JLabel();
        lblFelEpost = new javax.swing.JLabel();
        ComboValjPartner = new javax.swing.JComboBox<>();
        lblFelmeddelande = new javax.swing.JLabel();
        lblLyckat = new javax.swing.JLabel();

        lblValutaFel.setText("FEL VALUTA");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        lblRedigeraPartner.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblRedigeraPartner.setText("Redigera partners uppgifter");

        lblNamn.setText("Namn");

        lblID.setText("Välj partner att redigera");

        lblTelefon.setText("Telefon");

        lblKontaktP.setText("Kontaktperson");

        lblKontaktEpost.setText("Kontaktepost");

        lblAdress.setText("Adress");

        lblStad.setText("Stad");

        lblBranch.setText("Branch");

        btnTillbaka.setText("Tillbaka");
        btnTillbaka.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTillbakaActionPerformed(evt);
            }
        });

        btnSpara.setText("Spara");
        btnSpara.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSparaActionPerformed(evt);
            }
        });

        lblFelTelefon.setText("Fel tel");

        lblFelStad.setText("Fel stad");

        lblFelEpost.setText("FelEpost");

        ComboValjPartner.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        ComboValjPartner.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ComboValjPartnerActionPerformed(evt);
            }
        });

        lblFelmeddelande.setText("Alla fält måste fyllas i");

        lblLyckat.setText("Partnern har ändrats!");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lblAdress, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnSpara, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblBranch, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnTillbaka)
                                .addGap(31, 31, 31))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(ComboValjPartner, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(193, 193, 193)
                                        .addComponent(lblFelTelefon, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(lblRedigeraPartner)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(lblNamn, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(lblStad, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(207, 207, 207))
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(layout.createSequentialGroup()
                                                .addGap(8, 8, 8)
                                                .addComponent(lblKontaktEpost))
                                            .addComponent(lblTelefon, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(18, 18, 18)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(tfRedigeraKontaktEpost, javax.swing.GroupLayout.DEFAULT_SIZE, 157, Short.MAX_VALUE)
                                            .addComponent(tfRedigeraTelefon)))
                                    .addComponent(tfRedigeraAdress, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(tfRedigeraBranch, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(tfRedigeraPartnerNamn, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(tfRedigeraStad, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(lblKontaktP)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(tfRedigeraKontaktP, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblFelEpost, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addComponent(lblFelStad, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 176, Short.MAX_VALUE)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(lblLyckat)
                                            .addComponent(lblFelmeddelande, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                            .addComponent(lblID, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(31, 31, 31))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(lblRedigeraPartner)
                .addGap(77, 77, 77)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblID)
                    .addComponent(ComboValjPartner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tfRedigeraPartnerNamn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblNamn))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblKontaktP)
                    .addComponent(tfRedigeraKontaktP, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblKontaktEpost)
                    .addComponent(tfRedigeraKontaktEpost, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblFelEpost))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTelefon)
                    .addComponent(tfRedigeraTelefon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblFelTelefon))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tfRedigeraAdress, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblAdress))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tfRedigeraBranch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblBranch))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(tfRedigeraStad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblStad)
                            .addComponent(lblFelStad))
                        .addGap(18, 18, 18))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(lblFelmeddelande)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblLyckat)
                        .addGap(8, 8, 8)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnTillbaka)
                    .addComponent(btnSpara))
                .addContainerGap(29, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnTillbakaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTillbakaActionPerformed
        new AllaPartners(idb, inloggadAnvandare).setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_btnTillbakaActionPerformed

    private void btnSparaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSparaActionPerformed
    boolean hasError = false; // För att kontrollera om det finns några fel
    boolean hittad = false;   // För att kolla om det valda partner-id:t finns

    try {
        // Dölj felmeddelanden från början
        lblFelmeddelande.setVisible(false);
        lblFelEpost.setVisible(false);
        lblFelTelefon.setVisible(false);
        lblFelStad.setVisible(false);
        lblLyckat.setVisible(false);
        lblFelmeddelande.setVisible(false);

        // Kontrollera om partner-ID finns i databasen
        try {
            // Hämta PID från ComboBox istället för textfält
            String selectedPid = (String) ComboValjPartner.getSelectedItem(); // Antag att ComboValjPartner är din ComboBox
            if (selectedPid != null && !selectedPid.equals("Välj partner")) {
                pid = Integer.parseInt(selectedPid.split(" - ")[0]); // Extrahera PID från ComboBox-värdet
            } else {
                lblFelmeddelande.setVisible(true); // Fel: inget val gjort i ComboBox
                hasError = true;
            }

            // Om partner-ID är tomt eller ogiltigt
            if (pid <= 0) {
                lblFelmeddelande.setVisible(true); // Fel: ogiltigt partner-ID
                hasError = true;
            }
        } catch (NumberFormatException ex) {
            lblFelmeddelande.setVisible(true); // Fel i input (ej nummer)
            hasError = true;
        }

        // Kontrollera om stad-ID finns i databasen
        try {
            hittad = false;
            String textStad = tfRedigeraStad.getText();
            String selectStad = "SELECT sid FROM stad;";
            ArrayList<String> allaStader = idb.fetchColumn(selectStad);
            stadsID = Integer.parseInt(textStad);

            for (String enStad : allaStader) {
                int ettID = Integer.parseInt(enStad);
                if (ettID == stadsID) {
                    hittad = true;
                    lblFelStad.setVisible(false); // Inget fel
                    break;
                }
            }

            if (!hittad) {
                lblFelStad.setVisible(true); // Fel: stad-ID inte funnet
                hasError = true;
            }
        } catch (NumberFormatException ex) {
            lblFelStad.setVisible(true); // Fel i stad-ID input (ej nummer)
            hasError = true;
        }

        // Kontrollera om några textfält är tomma
        String namn = tfRedigeraPartnerNamn.getText();
        String kontaktPerson = tfRedigeraKontaktP.getText();
        String kontaktEpost = tfRedigeraKontaktEpost.getText();
        String telefon = tfRedigeraTelefon.getText();
        String adress = tfRedigeraAdress.getText();
        String branch = tfRedigeraBranch.getText();

        if (namn.isEmpty()) {
            lblFelmeddelande.setVisible(true); // Fel: namn kan inte vara tomt
            hasError = true;
        }
        if (kontaktPerson.isEmpty()) {
            lblFelmeddelande.setVisible(true); // Fel: kontaktperson kan inte vara tomt
            hasError = true;
        }
        if (kontaktEpost.isEmpty()) {
            lblFelmeddelande.setVisible(true); // Fel: e-post kan inte vara tomt
            hasError = true;
        }
        if (telefon.isEmpty()) {
            lblFelmeddelande.setVisible(true); // Fel: telefon kan inte vara tomt
            hasError = true;
        }
        if (adress.isEmpty()) {
            lblFelmeddelande.setVisible(true); // Fel: adress kan inte vara tomt
            hasError = true;
        }
        if (branch.isEmpty()) {
            lblFelmeddelande.setVisible(true); // Fel: branch kan inte vara tomt
            hasError = true;
        }

        // Om inga fel har inträffat, fortsätt att spara
        if (!hasError) {
            try {
                // Validera email och telefon
                if (validera.valideringEmail(kontaktEpost) && validera.valideringTelefonPartner(telefon)) {

                    // Hämta aktuell partnerdata från databasen om något fält är tomt
                    String selectPartner = "SELECT * FROM partner WHERE pid = " + pid + ";";
                    HashMap<String, String> enPartner = idb.fetchRow(selectPartner);
                    String[] enRad = new String[enPartner.size()];

                    for (String ettAttribut : enPartner.keySet()) {
                        switch (ettAttribut) {
                            case "pid":
                                enRad[0] = enPartner.get("pid");
                                break;
                            case "namn":
                                enRad[1] = enPartner.get("namn");
                                break;
                            case "kontaktperson":
                                enRad[2] = enPartner.get("kontaktPerson");
                                break;
                            case "kontaktepost":
                                enRad[3] = enPartner.get("kontaktEpost");
                                break;
                            case "telefon":
                                enRad[4] = enPartner.get("telefon");
                                break;
                            case "adress":
                                enRad[5] = enPartner.get("adress");
                                break;
                            case "branch":
                                enRad[6] = enPartner.get("branch");
                                break;
                            case "stad":
                                enRad[7] = enPartner.get("stad");
                                break;
                        }
                    }

                    // Om vissa fält är tomma, använd de befintliga värdena från databasen
                    if (namn.isEmpty()) {
                        namn = enRad[1];
                    }
                    if (kontaktPerson.isEmpty()) {
                        kontaktPerson = enRad[2];
                    }
                    if (kontaktEpost.isEmpty()) {
                        kontaktEpost = enRad[3];
                    }
                    if (telefon.isEmpty()) {
                        telefon = enRad[4];
                    }
                    if (adress.isEmpty()) {
                        adress = enRad[5];
                    }
                    if (branch.isEmpty()) {
                        branch = enRad[6];
                    }

                    // Uppdatera partnerinformationen i databasen
                    String updatePartner = String.format(
                            "UPDATE partner SET namn = '%s', kontaktperson = '%s', kontaktepost = '%s', telefon = '%s', adress = '%s', branch = '%s', stad = '%s' WHERE pid = %d",
                            namn, kontaktPerson, kontaktEpost, telefon, adress, branch, stadsID, pid);
                    idb.update(updatePartner);

                    lblLyckat.setVisible(true);

                } else {
                    // Om validering misslyckas, visa felmeddelanden
                    if (!validera.valideringEmail(kontaktEpost)) {
                        lblFelEpost.setVisible(true);
                    }
                    if (!validera.valideringTelefonPartner(telefon)) {
                        lblFelTelefon.setVisible(true);
                    }
                }
            } catch (InfException ex) {
                System.out.println(ex);
            }
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    }//GEN-LAST:event_btnSparaActionPerformed

    private void ComboValjPartnerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ComboValjPartnerActionPerformed
    fyllTextfields();
    }//GEN-LAST:event_ComboValjPartnerActionPerformed
  
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
            java.util.logging.Logger.getLogger(RedigeraPartner.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(RedigeraPartner.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(RedigeraPartner.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(RedigeraPartner.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
             //   new RedigeraPartner().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> ComboValjPartner;
    private javax.swing.JButton btnSpara;
    private javax.swing.JButton btnTillbaka;
    private javax.swing.JLabel lblAdress;
    private javax.swing.JLabel lblBranch;
    private javax.swing.JLabel lblFelEpost;
    private javax.swing.JLabel lblFelStad;
    private javax.swing.JLabel lblFelTelefon;
    private javax.swing.JLabel lblFelmeddelande;
    private javax.swing.JLabel lblID;
    private javax.swing.JLabel lblKontaktEpost;
    private javax.swing.JLabel lblKontaktP;
    private javax.swing.JLabel lblLyckat;
    private javax.swing.JLabel lblNamn;
    private javax.swing.JLabel lblRedigeraPartner;
    private javax.swing.JLabel lblStad;
    private javax.swing.JLabel lblTelefon;
    private javax.swing.JLabel lblValutaFel;
    private javax.swing.JTextField tfRedigeraAdress;
    private javax.swing.JTextField tfRedigeraBranch;
    private javax.swing.JTextField tfRedigeraKontaktEpost;
    private javax.swing.JTextField tfRedigeraKontaktP;
    private javax.swing.JTextField tfRedigeraPartnerNamn;
    private javax.swing.JTextField tfRedigeraStad;
    private javax.swing.JTextField tfRedigeraTelefon;
    // End of variables declaration//GEN-END:variables
}
