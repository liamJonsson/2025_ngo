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
    private JTable tblMinaProjekt; 
    private JButton btnLaggTillProjekt, btnTaBortProjekt, btnRedigeraProjekt;
    private JComboBox<String> comboStatusFilter;

    public MinaProjekt(InfDB idb, String inloggadAnvandare) {
        this.idb = idb;
        this.inloggadAnvandare = inloggadAnvandare;
        comboStatusFilter = new JComboBox<>();
        initComponents();
        skapaOchFyllTabell();
        skapaFilter();
        kontrolleraRollOchHanteraKnappar();
    }

    private void kontrolleraRollOchHanteraKnappar() {
        try {
            if (inloggadAnvandare == null || inloggadAnvandare.isEmpty()) {
                JOptionPane.showMessageDialog(this, "E-postadress saknas för den inloggade användaren.");
                return;
            }

            String query = "SELECT aid FROM anstalld WHERE epost = '" + inloggadAnvandare + "'";
            String aid = idb.fetchSingle(query);

            if (aid == null) {
                JOptionPane.showMessageDialog(this, "Användaren kunde inte identifieras via epost.");
                return;
            }

            if (isProjektchef(aid)) {
                skapaKnappar();  // Gör knappar synliga för projektchef
                visaKostnadsKolumn(true);  // Visa kostnadskolumn för projektchef
            } else {
                visaKostnadsKolumn(false);  // Dölj kostnadskolumnen för handläggare
            }
        } catch (InfException ex) {
            JOptionPane.showMessageDialog(this, "Ett fel uppstod vid hämtning av roll: " + ex.getMessage());
        }
    }

    private boolean isProjektchef(String aid) {
        try {
            String query = "SELECT COUNT(*) FROM projekt WHERE projektchef = '" + aid + "'";
            String resultat = idb.fetchSingle(query);
            return resultat != null && Integer.parseInt(resultat) > 0;
        } catch (InfException e) {
            JOptionPane.showMessageDialog(this, "Ett fel uppstod vid kontroll av projektchef: " + e.getMessage());
            return false;
        }
    }

    private void visaKostnadsKolumn(boolean visa) {
        if (tblMinaprojekt == null) {
            System.out.println("tblMinaprojekt är null");
            return;
        }
        if (tblMinaprojekt.getColumnModel().getColumnCount() > 0) {
            int kolumnIndex = 5;  // Kostnad är den 6:e kolumnen (index 5)
            if (visa) {
                tblMinaprojekt.getColumnModel().getColumn(kolumnIndex).setMinWidth(75);
                tblMinaprojekt.getColumnModel().getColumn(kolumnIndex).setMaxWidth(200);
                tblMinaprojekt.getColumnModel().getColumn(kolumnIndex).setPreferredWidth(100);
            } else {
                tblMinaprojekt.getColumnModel().getColumn(kolumnIndex).setMinWidth(0);
                tblMinaprojekt.getColumnModel().getColumn(kolumnIndex).setMaxWidth(0);
                tblMinaprojekt.getColumnModel().getColumn(kolumnIndex).setPreferredWidth(0);
            }
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

private void skapaOchFyllTabell() {
    try {
        // Kolumnnamn för tabellen
        String[] kolumnNamn = {"pid", "projektnamn", "beskrivning", "startdatum", "slutdatum", "kostnad", "status", "prioritet", "projektchef", "land", "partner_namn"};

        // Skapa en DefaultTableModel för tabellen
        DefaultTableModel modell = new DefaultTableModel(kolumnNamn, 0);

        // SQL-fråga som hämtar projektdat och partner_namn
        String query = 
            "SELECT p.pid, p.projektnamn, p.beskrivning, p.startdatum, p.slutdatum, p.kostnad, p.status, p.prioritet, p.projektchef, p.land, partner.namn " +
            "FROM projekt p " +
            "LEFT JOIN projekt_partner pt ON p.pid = pt.pid " + 
            "LEFT JOIN partner ON pt.partner_pid = partner.pid";

        // Hämta data från databasen
        ArrayList<HashMap<String, String>> projektLista = idb.fetchRows(query);

        if (projektLista == null || projektLista.isEmpty()) {
            System.out.println("Ingen data hämtades från databasen.");
            return;
        }

        // Fyll tabellen med data
        for (HashMap<String, String> projekt : projektLista) {
            Object[] rad = new Object[kolumnNamn.length];
            for (int i = 0; i < kolumnNamn.length; i++) {
                // Hämta kolumnvärde med korrekt nyckel
                String kolumnVarde = projekt.get(kolumnNamn[i]);

                // Om partnernamnet inte finns, använd rätt nyckel
                if ("partner_namn".equals(kolumnNamn[i])) {
                    kolumnVarde = projekt.get("namn"); // Hämta partnernamn direkt från "namn"
                }

                // Hantera null-värden
                if (kolumnVarde != null) {
                    rad[i] = kolumnVarde;
                } else {
                    rad[i] = "Ingen data";  // Hantera tomma värden
                }
            }
            modell.addRow(rad);
        }

        // Uppdatera tabellen med den nya modellen
        tblMinaprojekt.setModel(modell);  // Uppdatera den existerande tabellen

    } catch (InfException e) {
        JOptionPane.showMessageDialog(this, "Kunde inte fylla tabellen: " + e.getMessage());
    }
}

    private void fyllTabellMedData(ArrayList<HashMap<String, String>> projektLista) {
        try {
            String[] kolumnNamn = {"pid", "projektnamn", "beskrivning", "startdatum", "slutdatum", "kostnad", "status", "prioritet", "projektchef", "land", "partner_namn"};
            DefaultTableModel modell = new DefaultTableModel(kolumnNamn, 0);

            if (projektLista == null || projektLista.isEmpty()) {
                System.out.println("Ingen data hämtades från databasen.");
                return;
            }

            for (HashMap<String, String> projekt : projektLista) {
                Object[] rad = new Object[kolumnNamn.length];
                for (int i = 0; i < kolumnNamn.length; i++) {
                    String kolumnVarde = projekt.get(kolumnNamn[i]);

                    if ("partner_namn".equals(kolumnNamn[i])) {
                        kolumnVarde = projekt.get("namn");
                    }

                    if (kolumnVarde != null) {
                        rad[i] = kolumnVarde;
                    } else {
                        rad[i] = "Ingen data";
                    }
                }
                modell.addRow(rad);
            }

            tblMinaprojekt.setModel(modell);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Kunde inte fylla tabellen: " + e.getMessage());
        }
    }
    
    private void skapaFilter() {
     try {
         // Hämta distinkta statusvärden från projekt-tabellen
         String query = "SELECT DISTINCT status FROM projekt";
         ArrayList<String> statusLista = idb.fetchColumn(query);

         if (statusLista != null) {
             // Lägg till "Alla" som ett alternativ
             comboStatusFilter.addItem("Alla");

             // Lägg till varje unikt status i ComboBox
             for (String status : statusLista) {
                 comboStatusFilter.addItem(status);  // Lägg till statusen som text
             }
         } else {
             System.out.println("Ingen statusinformation hämtades.");
         }
     } catch (InfException e) {
         JOptionPane.showMessageDialog(this, "Kunde inte hämta status: " + e.getMessage());
     }

     // Lägg till en ActionListener för att filtrera tabellen när ett nytt statusval görs
     comboStatusFilter.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
             filtreraStatus();  // Uppdatera tabellen med vald status
         }
     });
 }
    
    private void filtreraStatus() {
    // Hämta den valda statusen från comboboxen
    String valdStatus = (String) comboStatusFilter.getSelectedItem();
    
    // Om "Alla" är valt, visa alla projekt (utan filter)
    if (valdStatus == null || valdStatus.equals("Alla")) {
        skapaOchFyllTabell();  // Skapa och fyll tabellen med alla projekt
        return;
    }

    // Skapa SQL-fråga för att hämta projekt med den valda statusen
    String query = "SELECT p.pid, p.projektnamn, p.beskrivning, p.startdatum, p.slutdatum, p.kostnad, p.status, p.prioritet, p.projektchef, p.land, partner.namn " +
                   "FROM projekt p " +
                   "LEFT JOIN projekt_partner pt ON p.pid = pt.pid " + 
                   "LEFT JOIN partner ON pt.partner_pid = partner.pid " +
                   "WHERE p.status = '" + valdStatus + "'";  // Filtrera projekten efter status

    // Hämta filtrerad data från databasen
    try {
        ArrayList<HashMap<String, String>> projektLista = idb.fetchRows(query);
        fyllTabellMedData(projektLista);  // Fyll tabellen med den filtrerade datan
    } catch (InfException e) {
        JOptionPane.showMessageDialog(this, "Kunde inte filtrera data: " + e.getMessage());
    }
}

    private void laggTillProjekt() {
        JOptionPane.showMessageDialog(this, "Funktion för att lägga till projekt kommer här.");
    }

    private void andraProjekt() {
        JOptionPane.showMessageDialog(this, "Funktion för att ändra projekt kommer här.");
    }

    private void taBortProjekt() {
        JOptionPane.showMessageDialog(this, "Funktion för att ta bort projekt kommer här.");
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
        ComboBoxFiltrera = new javax.swing.JComboBox<>();

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

        ComboBoxFiltrera.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 764, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(ComboBoxFiltrera, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
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
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(ComboBoxFiltrera, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 227, Short.MAX_VALUE)))
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
    private javax.swing.JComboBox<String> ComboBoxFiltrera;
    private javax.swing.JButton btnLaggTill;
    private javax.swing.JButton btnRedigera;
    private javax.swing.JButton btnTaBort;
    private javax.swing.JButton btnTillbaka;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblMinaprojekt;
    // End of variables declaration//GEN-END:variables
}
