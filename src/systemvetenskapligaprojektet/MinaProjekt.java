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
import javax.swing.table.TableColumn;

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
        btnAnsvarProjekt.setVisible(false);
        kontrollIfProjektchef();
    }
    
    private void kontrollIfProjektchef() {
        try {
            String selectAid = "SELECT aid FROM anstalld WHERE epost = '" + inloggadAnvandare + "'";
            String anstalldsID = idb.fetchSingle(selectAid);

            String checkProjektchef = "SELECT COUNT(*) FROM projekt WHERE projektchef = '" + anstalldsID + "'";
            String resultat = idb.fetchSingle(checkProjektchef);

            if (resultat != null && Integer.parseInt(resultat) > 0) {
                btnAnsvarProjekt.setVisible(true);
            }
        } catch (InfException ex) {
            System.out.println("Fel vid kontroll av projektchef: " + ex.getMessage());
        }
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
      
    private void skapaOchFyllTabell(String valdStatus) {
        try {
            String aid = hamtaAnvandareID();
            if (aid == null) return;

            // Kontrollera om användaren är projektchef
            boolean arProjektchef = isProjektchef(aid);

            // Göm kostnadskolumnen om användaren inte är projektchef
            visaKostnadsKolumn(arProjektchef);

            // Query för att ta fram projekt som en användare är kopplad till via tabellen ans_proj
            String handlaggareQuery = 
                "SELECT p.pid, p.projektnamn, p.beskrivning, p.startdatum, p.slutdatum, p.kostnad, p.status, p.prioritet, " +
                "(SELECT CONCAT(fornamn, ' ', efternamn) FROM anstalld WHERE aid = p.projektchef) AS projektchef, " +
                "(SELECT namn FROM land WHERE lid = p.land) AS land, " +
                "GROUP_CONCAT(partner.namn SEPARATOR ',') AS partners " +
                "FROM projekt p " +
                "LEFT JOIN projekt_partner pt ON p.pid = pt.pid " +
                "LEFT JOIN partner ON pt.partner_pid = partner.pid " +
                "JOIN ans_proj ON p.pid = ans_proj.pid " +
                "WHERE ans_proj.aid = '" + aid + "' " +
                "GROUP BY p.pid";

            // Query för att ta fram projekt där användaren är projektchef
            String projektchefQuery = 
                "SELECT p.pid, p.projektnamn, p.beskrivning, p.startdatum, p.slutdatum, p.kostnad, p.status, p.prioritet, " +
                "(SELECT CONCAT(fornamn, ' ', efternamn) FROM anstalld WHERE aid = p.projektchef) AS projektchef, " +
                "(SELECT namn FROM land WHERE lid = p.land) AS land, " +
                "GROUP_CONCAT(partner.namn SEPARATOR ',') AS partners " +
                "FROM projekt p " +
                "LEFT JOIN projekt_partner pt ON p.pid = pt.pid " +
                "LEFT JOIN partner ON pt.partner_pid = partner.pid " +
                "WHERE p.projektchef = '" + aid + "' " +
                "GROUP BY p.pid";

            // Slå samman båda resultaten till en enda tabell och ta bort dubbletter
            String baseQuery = handlaggareQuery + " UNION " + projektchefQuery;

            // Om en status är vald, filtrera resultaten
            if (valdStatus == null || valdStatus.isEmpty() || "Välj status".equals(valdStatus)) {
                valdStatus = null; // Visa alla projekt
            }

            // Hämta data
            ArrayList<HashMap<String, String>> projektLista = idb.fetchRows(baseQuery);

            if (projektLista == null || projektLista.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Inga projekt hittades.");
                return;
            }

            // Uppdatera tabellen med hämtad data
            uppdateraTabell(projektLista);
            
            // Kontrollera roll och visa/göm kostnadskolumnen
           visaKostnadsKolumn(arProjektchef);
           } catch (InfException e) {
            JOptionPane.showMessageDialog(this, "Kunde inte fylla tabellen: " + e.getMessage());
        }
    }

    private void uppdateraTabell(ArrayList<HashMap<String, String>> projektLista) {
        String[] kolumnNamn = {"pid", "projektnamn", "beskrivning", "startdatum", "slutdatum", "kostnad", "status", "prioritet", "projektchef", "land", "partners"};
        DefaultTableModel modell = new DefaultTableModel(kolumnNamn, 0);

        for (HashMap<String, String> projekt : projektLista) {
            Object[] rad = new Object[kolumnNamn.length];
            for (int i = 0; i < kolumnNamn.length; i++) {
                String kolumnVarde = projekt.getOrDefault(kolumnNamn[i], "Ingen data");

                // Om kolumnen är "partners", lägg till klickbar text
                if ("partners".equals(kolumnNamn[i])) {
                    kolumnVarde = "Klicka här för att se info om partner/partners!";
                }

                rad[i] = kolumnVarde;
            }
            modell.addRow(rad);
        }

        tblMinaprojekt.setModel(modell);

        // Sätt storleken på kolumnerna
        tblMinaprojekt.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        int[] kolumnBredd = {50, 100, 150, 100, 100, 100, 100, 75, 150, 100, 250};
        for (int i = 0; i < kolumnBredd.length; i++) {
        if (i == 5) continue; // Hoppa över kostnadskolumnen
        TableColumn col = tblMinaprojekt.getColumnModel().getColumn(i);
        col.setPreferredWidth(kolumnBredd[i]);
        }
    }

        private void visaKostnadsKolumn(boolean visa) {
            int kolumnIndex = 5; // Kostnadskolumnens index

            if (tblMinaprojekt.getColumnModel().getColumnCount() > kolumnIndex) {
                TableColumn kolumn = tblMinaprojekt.getColumnModel().getColumn(kolumnIndex);
                kolumn.setMinWidth(visa ? 75 : 0);
                kolumn.setMaxWidth(visa ? 200 : 0);
                kolumn.setPreferredWidth(visa ? 100 : 0);
            }
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

            // Kontrollera om användaren valt "Välj status"
            String valdStatus = (String) ComboStatusFilter.getSelectedItem();
            if ("Välj status".equals(valdStatus)) {
                valdStatus = null; // Visa alla projekt om "Välj status" är valt
            }

        } catch (InfException e) {
            JOptionPane.showMessageDialog(this, "Ett fel uppstod vid hämtning av statusvärden: " + e.getMessage());
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
        btnAnsvarProjekt = new javax.swing.JButton();
        ComboStatusFilter = new javax.swing.JComboBox<>();
        lblMinaProjekt = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jScrollPane1.setEnabled(false);

        tblMinaprojekt.setFont(new java.awt.Font("Microsoft JhengHei UI", 0, 12)); // NOI18N
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
        tblMinaprojekt.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblMinaprojektMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblMinaprojekt);

        btnTillbaka.setFont(new java.awt.Font("Microsoft JhengHei UI", 0, 12)); // NOI18N
        btnTillbaka.setText("Tillbaka");
        btnTillbaka.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTillbakaActionPerformed(evt);
            }
        });

        btnAnsvarProjekt.setFont(new java.awt.Font("Microsoft JhengHei UI", 0, 12)); // NOI18N
        btnAnsvarProjekt.setText("Projektchef");
        btnAnsvarProjekt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAnsvarProjektActionPerformed(evt);
            }
        });

        ComboStatusFilter.setFont(new java.awt.Font("Microsoft JhengHei UI", 0, 12)); // NOI18N
        ComboStatusFilter.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        ComboStatusFilter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ComboStatusFilterActionPerformed(evt);
            }
        });

        lblMinaProjekt.setFont(new java.awt.Font("Microsoft JhengHei UI", 1, 18)); // NOI18N
        lblMinaProjekt.setText("MINA PROJEKT");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 665, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnAnsvarProjekt)
                        .addGap(18, 18, 18)
                        .addComponent(btnTillbaka))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblMinaProjekt)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(18, 18, 18)
                .addComponent(ComboStatusFilter, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addComponent(lblMinaProjekt)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ComboStatusFilter, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(45, 45, 45)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnTillbaka)
                    .addComponent(btnAnsvarProjekt))
                .addGap(35, 35, 35))
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

    private void btnAnsvarProjektActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAnsvarProjektActionPerformed
        new AnsvarProjekt(idb,inloggadAnvandare).setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_btnAnsvarProjektActionPerformed

    private void tblMinaprojektMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblMinaprojektMouseClicked
        //ActionListener för att hantera klick på "partners"-kolumnen
        int column = tblMinaprojekt.columnAtPoint(evt.getPoint());
        if(column == 10){
            new ProjektPartners(idb,inloggadAnvandare).setVisible(true);
            this.setVisible(false);
        }
    }//GEN-LAST:event_tblMinaprojektMouseClicked
                    
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
    private javax.swing.JButton btnAnsvarProjekt;
    private javax.swing.JButton btnTillbaka;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblMinaProjekt;
    private javax.swing.JTable tblMinaprojekt;
    // End of variables declaration//GEN-END:variables
}
