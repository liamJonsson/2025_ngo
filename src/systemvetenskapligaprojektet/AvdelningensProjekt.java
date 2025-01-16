/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package systemvetenskapligaprojektet;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import oru.inf.InfDB;
import oru.inf.InfException;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.table.TableColumnModel;


/**
 *
 * @author mejaa
 */
public class AvdelningensProjekt extends javax.swing.JFrame {
    private InfDB idb;
    private String inloggadAnvandare;
    private JComboBox<String> comboStatusFilter;

    /**
     * Creates new form AvdelningensProjekt
     */
    public AvdelningensProjekt(InfDB idb, String inloggadAnvandare) {
        this.idb = idb;
        this.inloggadAnvandare = inloggadAnvandare;
        comboStatusFilter = comboBoxAvdelning;
        initComponents();
        fyllTabell();
        hamtaAvdelning();
        statusFilter(); 
        initStatusFilterListener();
        hanteraSearchListener();              
    }
    
private void fyllTabell() {
    try {
        // Skapar en array som lagrar kolumnnamnen
        String[] kolumnNamn = {"pid", "projektnamn", "beskrivning", "startdatum", "slutdatum", "status", "prioritet", "projektchef", "land"};
        DefaultTableModel avdelningensProjekt = new DefaultTableModel(kolumnNamn, 0);

        // Hämta projekt-IDs för alla projekt som är kopplade till den inloggade användarens avdelning
        String selectId = "SELECT pid FROM projekt WHERE pid IN (" +
                          "SELECT pid FROM ans_proj WHERE aid IN (" +
                          "SELECT aid FROM anstalld WHERE avdelning = (" +
                          "SELECT avdelning FROM anstalld WHERE epost = '" + inloggadAnvandare + "' ))" +
                          ") ORDER BY pid";

        ArrayList<String> pid = idb.fetchColumn(selectId);
        
        if (pid != null) {
            for (String ettID : pid) {
                // Hämtar information om projektet baserat på dess pid
                String selectInfo = "SELECT pid, projektnamn, beskrivning, startdatum, slutdatum, status, prioritet, projektchef, land " +
                                    "FROM projekt WHERE pid = '" + ettID + "';";

                //fetchRow här eftersom varje projekt är en enskild rad.
                HashMap<String, String> info = idb.fetchRow(selectInfo);

                // Skapa en ny rad i tabellen
                Object[] enRad = new Object[kolumnNamn.length];
                int index = 0;

                // För varje kolumnnamn, hämta motsvarande data
                for (String enKolumn : kolumnNamn) {
                    if(enKolumn.equals("beskrivning")){
                        enRad[index++] = "Klicka här för att se beskrivning!";
                    }
                    else if (enKolumn.equals("projektchef")) {
                        // Hämta projektchefens förnamn och efternamn
                        String selectFornamn = "SELECT fornamn FROM anstalld WHERE aid = (SELECT projektchef FROM projekt WHERE pid = '" + ettID + "');";
                        String fornamn = idb.fetchSingle(selectFornamn);
                        String selectEfternamn = "SELECT efternamn FROM anstalld WHERE aid = (SELECT projektchef FROM projekt WHERE pid = '" + ettID + "');";
                        String efternamn = idb.fetchSingle(selectEfternamn);
                        String namn = fornamn + " " + efternamn;
                        enRad[index++] = namn;
                    } 
                    else if (enKolumn.equals("land")) {
                        // Hämta landets namn
                        String selectLand = "SELECT namn FROM land WHERE lid = (SELECT land FROM projekt WHERE pid = '" + ettID + "');";
                        String land = idb.fetchSingle(selectLand);
                        enRad[index++] = land;
                    } 
                    else {
                        // För övriga kolumner, hämta information från info
                        enRad[index++] = info.get(enKolumn);
                    }
                }

                // Lägg till raden i tabellen för AvdelningensProjekt
                avdelningensProjekt.addRow(enRad);
            }

            // Sätt den uppdaterade modellen till tabellen
            tblProjekt.setModel(avdelningensProjekt);
        }
            // Anpassa kolumnbredder
            TableColumn col = tblProjekt.getColumnModel().getColumn(0); //ID
                col.setPreferredWidth(75);
                col = tblProjekt.getColumnModel().getColumn(1); //Namn
                col.setPreferredWidth(275);
                col = tblProjekt.getColumnModel().getColumn(2); //Beskrivning
                col.setPreferredWidth(750);
                col = tblProjekt.getColumnModel().getColumn(3); //Startdatum
                col.setPreferredWidth(300);
                col = tblProjekt.getColumnModel().getColumn(4); //Slutdatum
                col.setPreferredWidth(300);
                col = tblProjekt.getColumnModel().getColumn(5); //status
                col.setPreferredWidth(200);
                col = tblProjekt.getColumnModel().getColumn(6); //Prioritet
                col.setPreferredWidth(150);
                col = tblProjekt.getColumnModel().getColumn(7); //Projektchef
                col.setPreferredWidth(350);
                col = tblProjekt.getColumnModel().getColumn(8); //Land
                col.setPreferredWidth(225);      
    } 
    catch (InfException ex) {
        System.out.println(ex);
    }
}

    
    private void hamtaAvdelning(){
        try{
        String selectAvdelning = "select namn from avdelning where avdid =(select avdelning from anstalld where epost ='" + inloggadAnvandare + "');";
        String Avdelning = idb.fetchSingle(selectAvdelning);
        lblAvdelning.setText(Avdelning);
        }
        
        catch(InfException ex){

            System.out.println(ex);
        }
    }
    
private void skapaOchFyllTabell(String valdStatus) {
    try {
        // Initial SQL-fråga för att hämta projektdata
        String selectInfoProjekt = "SELECT DISTINCT p.pid, p.projektnamn, p.beskrivning, p.startdatum, p.slutdatum, p.status, p.prioritet, p.projektchef, p.land " +
                                   "FROM projekt p " +
                                   "INNER JOIN ans_proj ap ON p.pid = ap.pid " +
                                   "INNER JOIN anstalld a ON ap.aid = a.aid " +
                                   "WHERE a.avdelning = (SELECT avdelning FROM anstalld WHERE epost = '" + inloggadAnvandare + "');";
        
        // Om valdStatus inte är null eller tom, justera SQL-frågan för att inkludera status
        if (valdStatus != null && !valdStatus.isEmpty() && !"Välj status".equals(valdStatus)) {
            selectInfoProjekt = "SELECT DISTINCT p.pid, p.projektnamn, p.beskrivning, p.startdatum, p.slutdatum, p.status, p.prioritet, p.projektchef, p.land " +
                                "FROM projekt p " +
                                "INNER JOIN ans_proj ap ON p.pid = ap.pid " +
                                "INNER JOIN anstalld a ON ap.aid = a.aid " +
                                "WHERE p.status = '" + valdStatus + "' AND a.avdelning = (SELECT avdelning FROM anstalld WHERE epost = '" + inloggadAnvandare + "');";
        }

        // Kör SQL-frågan
        ArrayList<HashMap<String, String>> projektLista = idb.fetchRows(selectInfoProjekt);

        if (projektLista == null || projektLista.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Inga projekt hittades.");
            return;
        }

        // Uppdatera tabellen med projektdata
        uppdateraTabell(projektLista);
    } catch (InfException ex) {
        // Hantera exception och visa ett meddelande till användaren
        JOptionPane.showMessageDialog(this, "Kunde inte fylla tabellen: " + ex.getMessage());
    } catch (Exception ex) {
        // Hantera eventuella andra undantag
        JOptionPane.showMessageDialog(this, "Ett oväntat fel uppstod: " + ex.getMessage());
    }
}

private void uppdateraTabell(ArrayList<HashMap<String, String>> projektLista) {
    try {
        String[] kolumnNamn = {"pid", "projektnamn", "beskrivning", "startdatum", "slutdatum", "status", "prioritet", "projektchef", "land"};
        DefaultTableModel modell = new DefaultTableModel(kolumnNamn, 0);

        // Loopa igenom varje projekt i listan och skapa en rad för varje projekt
        for (HashMap<String, String> projekt : projektLista) {
            Object[] rad = new Object[kolumnNamn.length];

            // Lägg till data för varje kolumn
            for (int i = 0; i < kolumnNamn.length; i++) {
                String kolumnVarde = projekt.getOrDefault(kolumnNamn[i], "Ingen data");

                // Om vi är på "projektchef"-kolumnen, hämta projektchefens namn
                if (kolumnNamn[i].equals("projektchef")) {
                    String projektchefAid = projekt.get("projektchef");
                    
                    // Hämta projektchefens förnamn och efternamn
                    String selectFornamn = "SELECT fornamn FROM anstalld WHERE aid = '" + projektchefAid + "';";
                    String fornamn = idb.fetchSingle(selectFornamn);  // Hämta förnamn
                   
                    String selectEfternamn = "SELECT efternamn FROM anstalld WHERE aid = '" + projektchefAid + "';";
                    String efternamn = idb.fetchSingle(selectEfternamn);  // Hämta efternamn
                    
                    rad[i] = fornamn + " " + efternamn;
                } 
                // Om vi är på "land"-kolumnen, hämta landets namn
                else if (kolumnNamn[i].equals("land")) {
                    String landId = projekt.get("land");
                    String selectLand = "SELECT namn FROM land WHERE lid = '" + landId + "';";
                    String land = idb.fetchSingle(selectLand);  // Hämta landets namn
                    
                    rad[i] = land;
                } 
                // Om vi är på "beskrivning"-kolumnen, sätt en klickbar text
                else if (kolumnNamn[i].equals("beskrivning")) {
                    rad[i] = "Klicka här för att se beskrivning!";
                } 
                // För alla andra kolumner, sätt data från projektet
                else {
                    rad[i] = kolumnVarde;
                }
            }

            // Lägg till raden i tabellen
            modell.addRow(rad);
        }

        // Sätt den uppdaterade modellen som tabellens modell
        tblProjekt.setModel(modell);
              
        // Anpassa kolumnbredder
        TableColumn col = tblProjekt.getColumnModel().getColumn(0); //ID
        col.setPreferredWidth(75);
        col = tblProjekt.getColumnModel().getColumn(1); //Namn
        col.setPreferredWidth(275);
        col = tblProjekt.getColumnModel().getColumn(2); //Beskrivning
        col.setPreferredWidth(750);
        col = tblProjekt.getColumnModel().getColumn(3); //Startdatum
        col.setPreferredWidth(250);
        col = tblProjekt.getColumnModel().getColumn(4); //Slutdatum
        col.setPreferredWidth(250);
        col = tblProjekt.getColumnModel().getColumn(5); //status
        col.setPreferredWidth(200);
        col = tblProjekt.getColumnModel().getColumn(6); //Prioritet
        col.setPreferredWidth(150);
        col = tblProjekt.getColumnModel().getColumn(7); //Projektchef
        col.setPreferredWidth(350);
        col = tblProjekt.getColumnModel().getColumn(8); //Land
        col.setPreferredWidth(225); 

    } catch (InfException ex) {
        // Hantera exception och visa ett meddelande till användaren
        JOptionPane.showMessageDialog(this, "Kunde inte uppdatera tabellen: " + ex.getMessage());
    } catch (Exception ex) {
        // Hantera eventuella andra undantag
        JOptionPane.showMessageDialog(this, "Ett oväntat fel uppstod vid uppdatering av tabellen: " + ex.getMessage());
    }
}

    
    private void statusFilter(){
        try{
            String selectStatus = "select distinct status from projekt;";
            ArrayList<String> statusLista = idb.fetchColumn(selectStatus);
            
            if(statusLista != null && !statusLista.isEmpty()){
                comboBoxAvdelning.removeAllItems(); //Rensar comoboxen.
                comboBoxAvdelning.addItem("Välj status"); //Lägger till "Välj status som första objektet i comboboxen.
                
                for(String status : statusLista){
                    comboBoxAvdelning.addItem(status);  //Lägger till varje status som finns i databsen.
                }
                comboBoxAvdelning.setSelectedIndex(0);
            }
            else{ 
                JOptionPane.showMessageDialog(this, "Inga statusvärden hittades i databasen.");
            }
            }
        catch (InfException ex) {
            JOptionPane.showMessageDialog(this, "Ett fel uppstod vid hämtning av statusvärden: " + ex.getMessage());
        }            
    }
    
private void initStatusFilterListener() {
    comboBoxAvdelning.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            String valdStatus = (String) comboBoxAvdelning.getSelectedItem();
            skapaOchFyllTabell(valdStatus);  // Uppdatera tabellen baserat på det valda statusvärdet
        }
    });
}

// Lyssnare för sökknappen
private void hanteraSearchListener() {
    btnSok.addActionListener((ActionEvent e) -> {
        String sokTerm = JOptionPane.showInputDialog("Ange datumspann (yyyy-MM-dd till yyyy-MM-dd):");
        if (sokTerm == null || sokTerm.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Du måste ange ett datumspann.");
            return;
        }

        // Försök att hitta om termen är ett giltigt datumspann
        if (sokTerm.contains("till") && sokTerm.matches("^\\d{4}-\\d{2}-\\d{2} till \\d{4}-\\d{2}-\\d{2}$")) {
            // Om datumformatet är korrekt, hantera datumspannet
            hanteraDatumSpannSok(sokTerm);
        } else {
            // Om termen inte är ett giltigt datumspann
            JOptionPane.showMessageDialog(null, "Ogiltig sökterm. Ange ett datumspann i formatet 'yyyy-MM-dd till yyyy-MM-dd'.");
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
                String projektSlut = modell.getValueAt(i, 4).toString();  // Förutsätter att slutdatum är i kolumn 4

                // Kontrollera om något av projektdatumen är inom det angivna datumspannet
                boolean inomDatumspann = false;

                // Om projektets startdatum är inom spannet
                if (projektStart.compareTo(slutDatumFilter) <= 0 && projektStart.compareTo(startDatumFilter) >= 0) {
                    inomDatumspann = true;
                }

                // Om projektets slutdatum är inom spannet
                if (projektSlut.compareTo(startDatumFilter) >= 0 && projektSlut.compareTo(slutDatumFilter) <= 0) {
                    inomDatumspann = true;
                }

                // Om projektets start och slutdatum är innanför spannet, inkludera det
                if (projektStart.compareTo(startDatumFilter) <= 0 && projektSlut.compareTo(slutDatumFilter) >= 0) {
                    inomDatumspann = true;
                }

                // Om något av datumen är inom spannet, lägg till raden i den filtrerade tabellen
                if (inomDatumspann) {
                    filtreradModell.addRow(new Object[]{
                            modell.getValueAt(i, 0), //Pid
                            modell.getValueAt(i, 1), //Projektnamn
                            modell.getValueAt(i, 2), //Beskrivning
                            projektStart, //Startdatum
                            projektSlut,  //Slutdatum
                            modell.getValueAt(i, 5), //Kostnad
                            modell.getValueAt(i, 6), //Status
                            modell.getValueAt(i, 7), //Prioritet
                            modell.getValueAt(i, 8)  //Projektchef
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



    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        lblAvdelning = new javax.swing.JLabel();
        btnTillbaka = new javax.swing.JButton();
        comboBoxAvdelning = new javax.swing.JComboBox<>();
        btnSok = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblProjekt = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Microsoft JhengHei UI", 1, 18)); // NOI18N
        jLabel1.setText("Projekt på");

        lblAvdelning.setFont(new java.awt.Font("Microsoft JhengHei UI", 1, 18)); // NOI18N
        lblAvdelning.setText("jLabel2");

        btnTillbaka.setFont(new java.awt.Font("Microsoft JhengHei UI", 0, 12)); // NOI18N
        btnTillbaka.setText("Tillbaka");
        btnTillbaka.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTillbakaActionPerformed(evt);
            }
        });

        comboBoxAvdelning.setFont(new java.awt.Font("Microsoft JhengHei UI", 0, 12)); // NOI18N
        comboBoxAvdelning.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        btnSok.setFont(new java.awt.Font("Microsoft JhengHei UI", 0, 12)); // NOI18N
        btnSok.setText("Sök projekt");

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
        tblProjekt.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblProjektMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblProjekt);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblAvdelning, javax.swing.GroupLayout.PREFERRED_SIZE, 548, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(125, 125, 125))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(503, 503, 503)
                                .addComponent(btnSok)
                                .addGap(18, 18, 18)
                                .addComponent(btnTillbaka)
                                .addGap(18, 67, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jScrollPane1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                        .addComponent(comboBoxAvdelning, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(35, 35, 35))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(lblAvdelning))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(comboBoxAvdelning, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(40, 40, 40)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSok)
                    .addComponent(btnTillbaka))
                .addGap(35, 35, 35))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnTillbakaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTillbakaActionPerformed
        new MinAvdelning(idb, inloggadAnvandare).setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_btnTillbakaActionPerformed

    private void tblProjektMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblProjektMouseClicked
        int column = tblProjekt.columnAtPoint(evt.getPoint());
        if(column == 2){
            new BeskrivningAvdelningensProjekt(idb,inloggadAnvandare).setVisible(true);
            this.setVisible(false);
        }
    }//GEN-LAST:event_tblProjektMouseClicked

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
            java.util.logging.Logger.getLogger(AvdelningensProjekt.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AvdelningensProjekt.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AvdelningensProjekt.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AvdelningensProjekt.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                //new AvdelningensProjekt().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnSok;
    private javax.swing.JButton btnTillbaka;
    private javax.swing.JComboBox<String> comboBoxAvdelning;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblAvdelning;
    private javax.swing.JTable tblProjekt;
    // End of variables declaration//GEN-END:variables
}
