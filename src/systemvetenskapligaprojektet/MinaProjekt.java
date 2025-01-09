/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package systemvetenskapligaprojektet;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import oru.inf.InfDB;
import oru.inf.InfException;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.DefaultListModel;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class MinaProjekt extends javax.swing.JFrame {
    private static InfDB idb;
    private String inloggadAnvandare;
    private JButton btnLaggTillProjekt, btnTaBortProjekt, btnRedigeraProjekt;
    private JComboBox<String> comboStatusFilter;

    public MinaProjekt(InfDB idb, String inloggadAnvandare) {
    this.idb = idb;
    this.inloggadAnvandare = inloggadAnvandare;
    comboStatusFilter = ComboStatusFilter; // Anslut den definierade JComboBox
    initComponents();
    statusFilter();  // Lägg till detta anrop för att fylla comboboxen
    initStatusFilterListener();
    skapaOchFyllTabell(null); // Visa alla projekt från början
    kontrolleraRollOchHanteraKnappar();
    }

    private void kontrolleraRollOchHanteraKnappar() {
    try {
        String aid = hamtaAnvandareID();
        if (aid == null) return;

        boolean projektchef = isProjektchef(aid);
    } catch (InfException ex) {
        JOptionPane.showMessageDialog(this, "Ett fel uppstod vid hämtning av roll: " + ex.getMessage());
    }
    }

    private String hamtaAnvandareID() throws InfException {
    if (inloggadAnvandare == null || inloggadAnvandare.isEmpty()) {
        JOptionPane.showMessageDialog(this, "E-postadress saknas för den inloggade användaren.");
        return null;
    }
    String query = "SELECT aid FROM anstalld WHERE epost = '" + inloggadAnvandare.trim() + "'";
    String aid = idb.fetchSingle(query);
    if (aid == null) {
        JOptionPane.showMessageDialog(this, "Användaren kunde inte identifieras via e-post.");
    }
    return aid;
    }

    private boolean isProjektchef(String aid) throws InfException {
    String query = "SELECT COUNT(*) FROM projekt WHERE projektchef = '" + aid + "'";  
    String resultat = idb.fetchSingle(query);
    // Kontrollera om resultatet är större än 0, vilket innebär att användaren är projektchef
    return resultat != null && Integer.parseInt(resultat) > 0;
    }
    
    private void visaKostnadsKolumn(boolean visa) {
    int kolumnIndex = 5; // Kostnadskolumn

    if (tblMinaprojekt.getColumnModel().getColumnCount() > kolumnIndex) {
        int minWidth = visa ? 75 : 0;
        int maxWidth = visa ? 200 : 0;
        int preferredWidth = visa ? 100 : 0;

        tblMinaprojekt.getColumnModel().getColumn(kolumnIndex).setMinWidth(minWidth);
        tblMinaprojekt.getColumnModel().getColumn(kolumnIndex).setMaxWidth(maxWidth);
        tblMinaprojekt.getColumnModel().getColumn(kolumnIndex).setPreferredWidth(preferredWidth);
    }
}

    private void skapaKnappar() {
        // Skapa panel för knappar och placera längst ner
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        // Lägg till knappar i panelen
        btnLaggTillProjekt = new JButton("Lägg till projekt");
        btnLaggTillProjekt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                laggTillProjekt();
            }
        });
        buttonPanel.add(btnLaggTillProjekt);

        btnTaBortProjekt = new JButton("Ta bort projekt");
        btnTaBortProjekt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                taBortProjekt();
            }
        });
        buttonPanel.add(btnTaBortProjekt);

        btnRedigeraProjekt = new JButton("Ändra projekt");
        btnRedigeraProjekt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                andraProjekt();
            }
        });
        buttonPanel.add(btnRedigeraProjekt);

        // Placera panelen längst ner
        getContentPane().add(buttonPanel, BorderLayout.SOUTH);
    }
    
private void skapaOchFyllTabell(String valdStatus) {
    try {
        String baseQuery = 
            "SELECT p.pid, p.projektnamn, p.beskrivning, p.startdatum, p.slutdatum, p.kostnad, p.status, p.prioritet, p.projektchef, p.land, partner.namn " +
            "FROM projekt p " +
            "LEFT JOIN projekt_partner pt ON p.pid = pt.pid " +
            "LEFT JOIN partner ON pt.partner_pid = partner.pid";

        // Lägg till WHERE-klausul baserat på valt status
        if (valdStatus != null && !valdStatus.isEmpty()) {
            baseQuery += " WHERE p.status = '" + valdStatus + "'";
        }

        ArrayList<HashMap<String, String>> projektLista = idb.fetchRows(baseQuery);

        if (projektLista == null || projektLista.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Inga projekt hittades.");
            return;
        }

        uppdateraTabell(projektLista);
    } catch (InfException e) {
        JOptionPane.showMessageDialog(this, "Kunde inte fylla tabellen: " + e.getMessage());
    }
}

private void uppdateraTabell(ArrayList<HashMap<String, String>> projektLista) {
    String[] kolumnNamn = {"pid", "projektnamn", "beskrivning", "startdatum", "slutdatum", "kostnad", "status", "prioritet", "projektchef", "land", "partner_namn"};
    DefaultTableModel modell = new DefaultTableModel(kolumnNamn, 0);

    for (HashMap<String, String> projekt : projektLista) {
        Object[] rad = new Object[kolumnNamn.length];
        for (int i = 0; i < kolumnNamn.length; i++) {
            String kolumnVarde = projekt.getOrDefault(kolumnNamn[i], "Ingen data");
            if ("partner_namn".equals(kolumnNamn[i])) {
                kolumnVarde = projekt.getOrDefault("namn", "Ingen data");
            }
            rad[i] = kolumnVarde;
        }
        modell.addRow(rad);
    }

    tblMinaprojekt.setModel(modell);
}
    
private void statusFilter() {
    try {
        // Hämta statusar från databasen
        String query = "SELECT DISTINCT status FROM projekt";
        ArrayList<String> resultatLista = idb.fetchColumn(query);

        // Kontrollera att resultatet inte är tomt
        if (resultatLista != null && !resultatLista.isEmpty()) {
            ComboStatusFilter.removeAllItems(); // Rensa comboboxen

            // Lägg till "Välj status" som första objekt i comboboxen
            ComboStatusFilter.addItem("Välj status");

            // Lägg till varje status som finns i databasen
            for (String status : resultatLista) {
                ComboStatusFilter.addItem(status);
            }

            // Lämna det första neutrala alternativet valt
            ComboStatusFilter.setSelectedIndex(0); // Sätt "Välj status" som valt
        } else {
            JOptionPane.showMessageDialog(this, "Inga statusvärden hittades i databasen.");
        }
    } catch (InfException e) {
        JOptionPane.showMessageDialog(this, "Ett fel uppstod vid hämtning av statusvärden: " + e.getMessage());
    }
}

    private void filtreraStatus() {
    String valdStatus = (String) comboStatusFilter.getSelectedItem();

    if (valdStatus == null || valdStatus.isEmpty()) {
        skapaOchFyllTabell(null); // Visa alla projekt om inget filter är valt
    } else {
        skapaOchFyllTabell(valdStatus); // Filtrera efter vald status
    }
    }
    
private void initStatusFilterListener() {
    ComboStatusFilter.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            String valdStatus = (String) ComboStatusFilter.getSelectedItem();
            skapaOchFyllTabell(valdStatus);  // Uppdatera tabellen baserat på det valda statusvärdet
        }
    });
}
    private void laggTillProjekt() {
    //new LaggTillProjekt(idb,inloggadAnvandare).setVisible(true);
       //this.setVisible(false);
    }

    private void andraProjekt() {
    //new andraProjekt(idb,inloggadAnvandare).setVisible(true);
       //this.setVisible(false);
    }

    private void taBortProjekt() {
        //new taBortProjekt(idb,inloggadAnvandare).setVisible(true);
       //this.setVisible(false);
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
        tblMinaprojekt = new javax.swing.JTable();
        btnTillbaka = new javax.swing.JButton();
        btnRedigera = new javax.swing.JButton();
        btnTaBort = new javax.swing.JButton();
        btnLaggTill = new javax.swing.JButton();
        ComboStatusFilter = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jScrollPane1.setEnabled(false);

        tblMinaprojekt.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4", "Title 5", "Title 6", "Title 7", "Title 8", "Title 9", "Title 10", "Title 11"
            }
        ));
        tblMinaprojekt.setEnabled(false);
        jScrollPane1.setViewportView(tblMinaprojekt);

        btnTillbaka.setText("Tillbaka");
        btnTillbaka.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTillbakaActionPerformed(evt);
            }
        });

        btnRedigera.setText("Redigera");

        btnTaBort.setText("Ta bort");

        btnLaggTill.setText("Lägg till");
        btnLaggTill.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLaggTillActionPerformed(evt);
            }
        });

        ComboStatusFilter.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        ComboStatusFilter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ComboStatusFilterActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 764, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(ComboStatusFilter, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(31, 31, 31))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnTillbaka)
                        .addGap(56, 56, 56)
                        .addComponent(btnRedigera)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnTaBort)
                        .addGap(28, 28, 28)
                        .addComponent(btnLaggTill)
                        .addGap(143, 143, 143))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 250, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(ComboStatusFilter, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnTillbaka)
                    .addComponent(btnRedigera)
                    .addComponent(btnTaBort)
                    .addComponent(btnLaggTill))
                .addGap(15, 15, 15))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnTillbakaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTillbakaActionPerformed
    new MenyHandlaggare(idb, inloggadAnvandare).setVisible(true);
    this.dispose();                                            
    }//GEN-LAST:event_btnTillbakaActionPerformed

    private void ComboStatusFilterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ComboStatusFilterActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ComboStatusFilterActionPerformed

    private void btnLaggTillActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLaggTillActionPerformed
    new LaggTillProjekt(idb,inloggadAnvandare).setVisible(true);
    this.setVisible(false);
    }//GEN-LAST:event_btnLaggTillActionPerformed
                    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                //new MinaProjektTest().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> ComboStatusFilter;
    private javax.swing.JButton btnLaggTill;
    private javax.swing.JButton btnRedigera;
    private javax.swing.JButton btnTaBort;
    private javax.swing.JButton btnTillbaka;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblMinaprojekt;
    // End of variables declaration//GEN-END:variables
}
