/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package systemvetenskapligaprojektet;
import oru.inf.InfDB; //Ska importeras till alla klasser
import oru.inf.InfException; //Samma sak här som ovan
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.HashMap;
/**
 *
 * @author iftinserar
 */
public class AllaProjekt extends javax.swing.JFrame {
    private static InfDB idb;
    private String inloggadAnvandare;
    
    /**
     * Creates new form Projekt
     */
    public AllaProjekt(InfDB idb, String inloggadAnvandare) {
        this.idb = idb;
        this.inloggadAnvandare = inloggadAnvandare;
        initComponents();
        hanteraSearchListener(); //separat metod för sök
        fyllTabell();
    }        
/**
     */
public void fyllTabell() {
    try {
        String[] kolumnNamn = {"pid", "projektnamn", "beskrivning", "startdatum", "slutdatum", "status", "prioritet", "projektchef", "land"};
        DefaultTableModel projektTabellModel = new DefaultTableModel(kolumnNamn, 0);

        // Hämta alla projekt-ID
        String selectPid = "SELECT pid FROM projekt ORDER BY pid;";
        ArrayList<String> pidLista = idb.fetchColumn(selectPid);

        if (pidLista != null) {
            for (String ettPID : pidLista) {
                String selectInfo = "SELECT * FROM projekt WHERE pid = " + ettPID + ";";
                HashMap<String, String> info = idb.fetchRow(selectInfo);

                Object[] enRad = new Object[kolumnNamn.length];
                int index = 0;

                for (String enKolumn : kolumnNamn) {
                    if (enKolumn.equals("land")) {
                        // Hämta landets namn
                        String selectLand = 
                            "SELECT namn FROM land WHERE lid = (SELECT land FROM projekt WHERE pid = " + ettPID + ");";
                        String land = idb.fetchSingle(selectLand);
                        enRad[index++] = land;
                    } else if (enKolumn.equals("projektchef")) {
                        // Hämta projektchefens för- och efternamn
                        String selectChefFornamn = 
                            "SELECT fornamn FROM anstalld WHERE aid = (SELECT projektchef FROM projekt WHERE pid = " + ettPID + ");";
                        String chefFornamn = idb.fetchSingle(selectChefFornamn);

                        String selectChefEfternamn = 
                            "SELECT efternamn FROM anstalld WHERE aid = (SELECT projektchef FROM projekt WHERE pid = " + ettPID + ");";
                        String chefEfternamn = idb.fetchSingle(selectChefEfternamn);

                        String chefFulltNamn = chefFornamn + " " + chefEfternamn;
                        enRad[index++] = chefFulltNamn;
                    } else {
                        // Annars hämta data direkt från `info` HashMap
                        enRad[index++] = info.get(enKolumn);
                    }
                }
                projektTabellModel.addRow(enRad);
            }
                tblProjekt.setModel(projektTabellModel);
            }

            // Anpassa kolumnbredd
            tblProjekt.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
            int[] kolumnBredd = {50, 150, 200, 100, 100, 100, 100, 150, 100};
            for (int i = 0; i < kolumnBredd.length; i++) {
                TableColumn col = tblProjekt.getColumnModel().getColumn(i);
                col.setPreferredWidth(kolumnBredd[i]);
            }

        } catch (InfException ex) {
            System.out.println("Ett fel uppstod: " + ex.getMessage());
        }
    }
    
  /** 
     *Hanterar sökknappen
     */
    private void hanteraSearchListener() {
    btnSok.addActionListener((ActionEvent e) -> {
        String sokTerm = JOptionPane.showInputDialog("Ange datumspann (yyyy-MM-dd till yyyy-MM-dd) eller status:");
        if (sokTerm == null || sokTerm.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Du måste ange en sökterm.");
            return;
        }

        // Försök att hitta om termen är ett giltigt datumspann
        if (sokTerm.contains("till") && sokTerm.matches("^\\d{4}-\\d{2}-\\d{2} till \\d{4}-\\d{2}-\\d{2}$")) {
            // Om datumformatet är korrekt, hantera datumspannet
            hanteraDatumSpannSok(sokTerm);
        } else {
            // Lista över giltiga statusar
            String[] giltigaStatusar = {"Pågående", "Avslutat", "Pausat"};
            boolean giltigStatus = false;

            // Kontrollera om sökterm är en giltig status
            for (String status : giltigaStatusar) {
                if (status.equalsIgnoreCase(sokTerm)) {
                    giltigStatus = true;
                    break;
                }
            }

            // Om termen är en giltig status, utför statusökningen
            if (giltigStatus) {
                hanteraStatusSok(sokTerm);
            } else {
                // Om termen inte är ett datumspann eller en giltig status
                JOptionPane.showMessageDialog(null, "Ogiltig sökterm. Ange ett giltigt datumspann (yyyy-MM-dd till yyyy-MM-dd) eller en giltig status.");
            }
        }
      });
    }

// Hantera filtrering av projekt baserat på datumspann
private void hanteraDatumSpannSok(String sokTerm) {
    if (sokTerm.contains("till")) {
        String[] datum = sokTerm.split("till");
        if (datum.length == 2) {
            String startDatumFilter = datum[0].trim();
            String slutDatumFilter = datum[1].trim();

            // Hämta den nuvarande tabellens modell
            DefaultTableModel modell = (DefaultTableModel) tblProjekt.getModel();

            // Skapa en ny modell baserat på kolumnnamnen från den nuvarande modellen
            int columnCount = modell.getColumnCount();
            String[] kolumnNamn = new String[columnCount];
            for (int i = 0; i < columnCount; i++) {
                kolumnNamn[i] = modell.getColumnName(i);
            }

            // Skapa en ny tabellmodell med samma kolumnnamn
            DefaultTableModel filtreradModell = new DefaultTableModel(kolumnNamn, 0);

            // Loopa genom alla rader och filtrera baserat på datumspannet
            for (int i = 0; i < modell.getRowCount(); i++) {
                String projektStart = modell.getValueAt(i, 3).toString(); // Förutsätter att startdatum är i kolumn 3

                // Kontrollera om projektets startdatum är inom det angivna datumspannet
                if (projektStart.compareTo(startDatumFilter) >= 0 && projektStart.compareTo(slutDatumFilter) <= 0) {
                    filtreradModell.addRow(new Object[]{
                            modell.getValueAt(i, 0),
                            modell.getValueAt(i, 1),
                            modell.getValueAt(i, 2),
                            projektStart,
                            modell.getValueAt(i, 4), // Slutdatum
                            modell.getValueAt(i, 5), // Kostnad
                            modell.getValueAt(i, 6), // Status
                            modell.getValueAt(i, 7), // Prioritet
                            modell.getValueAt(i, 8)  // Projektchef
                    });
                }
            }

            // Sätt den filtrerade modellen som ny modell för tabellen
            tblProjekt.setModel(filtreradModell);
        } else {
            JOptionPane.showMessageDialog(null, "Felaktigt format för datumspann. Använd 'yyyy-MM-dd till yyyy-MM-dd'.");
        }
    }
}
            

private void hanteraStatusSok(String sokTerm) {
    DefaultTableModel modell = (DefaultTableModel) tblProjekt.getModel();
    DefaultTableModel filtreradModell = new DefaultTableModel();

    // Kopiera kolumnnamnen manuellt
    for (int i = 0; i < modell.getColumnCount(); i++) {
        filtreradModell.addColumn(modell.getColumnName(i));
    }

    // Filtrera rader baserat på status
    for (int i = 0; i < modell.getRowCount(); i++) {
        String status = modell.getValueAt(i, 5).toString();
        if (status.equalsIgnoreCase(sokTerm)) {
            filtreradModell.addRow(new Object[]{
                    modell.getValueAt(i, 0),
                    modell.getValueAt(i, 1),
                    modell.getValueAt(i, 2),
                    modell.getValueAt(i, 3),
                    modell.getValueAt(i, 4),
                    status,
                    modell.getValueAt(i, 6),
                    modell.getValueAt(i, 7),
                    modell.getValueAt(i, 8)
            });
        }
    }

    tblProjekt.setModel(filtreradModell);
}
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnTillbaka = new javax.swing.JButton();
        btnSok = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblProjekt = new javax.swing.JTable();
        btnLaggTill = new javax.swing.JButton();
        btnTaBort = new javax.swing.JButton();
        btnRedigera = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        btnTillbaka.setText("Tillbaka");
        btnTillbaka.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTillbakaActionPerformed(evt);
            }
        });

        btnSok.setText("Sök");

        tblProjekt.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane2.setViewportView(tblProjekt);

        btnLaggTill.setText("Lägg till");
        btnLaggTill.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLaggTillActionPerformed(evt);
            }
        });

        btnTaBort.setText("Ta bort");
        btnTaBort.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTaBortActionPerformed(evt);
            }
        });

        btnRedigera.setText("Redigera");
        btnRedigera.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRedigeraActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 1003, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnTillbaka)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnRedigera)
                        .addGap(36, 36, 36)
                        .addComponent(btnTaBort)
                        .addGap(33, 33, 33)
                        .addComponent(btnLaggTill)
                        .addGap(71, 71, 71)
                        .addComponent(btnSok)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 263, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(90, 90, 90)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnTillbaka)
                    .addComponent(btnSok)
                    .addComponent(btnLaggTill)
                    .addComponent(btnTaBort)
                    .addComponent(btnRedigera))
                .addContainerGap(25, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnTillbakaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTillbakaActionPerformed
    new MenyAdmin(idb, inloggadAnvandare).setVisible(true);
    this.dispose();
    }//GEN-LAST:event_btnTillbakaActionPerformed

    private void btnLaggTillActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLaggTillActionPerformed
    new LaggTillProjekt(idb, inloggadAnvandare).setVisible(true);
    this.setVisible(false);
    }//GEN-LAST:event_btnLaggTillActionPerformed

    private void btnTaBortActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTaBortActionPerformed
    new TaBortProjekt(idb, inloggadAnvandare).setVisible(true);
    this.setVisible(false);
    }//GEN-LAST:event_btnTaBortActionPerformed

    private void btnRedigeraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRedigeraActionPerformed
    new RedigeraProjekt(idb, inloggadAnvandare).setVisible(true);
    this.setVisible(false);
    }//GEN-LAST:event_btnRedigeraActionPerformed

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
            java.util.logging.Logger.getLogger(AllaProjekt.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AllaProjekt.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AllaProjekt.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AllaProjekt.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                //new Projekt().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnLaggTill;
    private javax.swing.JButton btnRedigera;
    private javax.swing.JButton btnSok;
    private javax.swing.JButton btnTaBort;
    private javax.swing.JButton btnTillbaka;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable tblProjekt;
    // End of variables declaration//GEN-END:variables
}