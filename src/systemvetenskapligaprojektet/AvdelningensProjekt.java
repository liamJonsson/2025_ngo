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
        skapaOchFyllTabell(null);
        hanteraSearchListener();
        
        
    }
    
    private void fyllTabell(){

        try{
            //Skapar en array som lagrar kolumnnamnen

        String[] kolumnNamn = {"pid", "projektnamn", "beskrivning", "startdatum", "slutdatum", "status", "prioritet","projektchef", "land"};

        DefaultTableModel allaProjekt = new DefaultTableModel(kolumnNamn, 0);

       

        String selectId = "select pid from projekt where pid in(select pid from ans_proj where aid in(select aid from anstalld where avdelning = (select avdelning from anstalld where epost = '" + inloggadAnvandare + "'))) order by (pid)";



        ArrayList<String> pid = idb.fetchColumn(selectId);
            if(pid != null){
            
                for(String ettID:pid){
                    //int ettPid = Integer.parseInt(ettID);
                    String selectInfo = "select pid,projektnamn,beskrivning,startdatum,slutdatum,status, prioritet,projektchef,land from projekt where pid = '" + ettID + "';";



                    ArrayList<HashMap<String,String>> info = idb.fetchRows(selectInfo);

                    Object[] enRad = new Object[kolumnNamn.length];
                    int index = 0;
                    

                    for(String enKolumn:kolumnNamn){      
                        if(enKolumn.equals("projektchef")){
                            String selectFornamn = "select fornamn from anstalld where aid = (select projektchef from projekt where pid = '" + ettID + "');";
                            String fornamn = idb.fetchSingle(selectFornamn);
                            String selectEfternamn = "select efternamn from anstalld where aid = (select projektchef from projekt where pid = '" + ettID + "');";
                            String efternamn = idb.fetchSingle(selectEfternamn);
                            String namn = fornamn + " " + efternamn;
                            enRad[index++] = namn;                          
                        }                        
                        else{
                            enRad[index++] = info.get(0).get(enKolumn);
                                    
                        }
                    }
                    allaProjekt.addRow(enRad);
                }

                tblProjekt.setModel(allaProjekt);

            }

            tblProjekt.setAutoResizeMode(tblProjekt.AUTO_RESIZE_OFF);

        TableColumn col = tblProjekt.getColumnModel().getColumn(0);

        col.setPreferredWidth(50);

        col = tblProjekt.getColumnModel().getColumn(1);

        col.setPreferredWidth(100);

        col = tblProjekt.getColumnModel().getColumn(2);

        col.setPreferredWidth(250);

        col = tblProjekt.getColumnModel().getColumn(3);

        col.setPreferredWidth(100);

        col = tblProjekt.getColumnModel().getColumn(4);

        col.setPreferredWidth(100);
        
        
        col = tblProjekt.getColumnModel().getColumn(5);

        col.setPreferredWidth(100);
        
        col = tblProjekt.getColumnModel().getColumn(6);

        col.setPreferredWidth(100);
        
        col = tblProjekt.getColumnModel().getColumn(7);

        col.setPreferredWidth(150);
        
        col = tblProjekt.getColumnModel().getColumn(8);

        col.setPreferredWidth(50);
        }

        catch(InfException ex){
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
        String selectInfoProjekt = "select distinct p.pid, p.projektnamn, p.beskrivning,p.startdatum,p.slutdatum,p.status,p.prioritet,p.projektchef,p.land from projekt p inner join ans_proj ap on p.pid = ap.pid inner join anstalld a on ap.aid = a.aid where a.avdelning = (select avdelning from anstalld where epost = '" + inloggadAnvandare + "');";

                
                //"select pid,projektnamn,beskrivning,startdatum,slutdatum,status,prioritet,projektchef,land from projekt where pid in(select pid from ans_proj where aid in(select aid from anstalld where avdelning =(select avdelning from anstalld where epost = '" 
        //+ inloggadAnvandare + "')));";
        
      
        //WHERE-klausul om valdStatus inte är null eller tomt
        if (valdStatus != null && !valdStatus.isEmpty() && !"Välj status".equals(valdStatus)) {
            selectInfoProjekt = "select distinct p.pid, p.projektnamn, p.beskrivning,p.startdatum,p.slutdatum,p.status,p.prioritet,p.projektchef,p.land from projekt p inner join ans_proj ap on p.pid = ap.pid inner join anstalld a on ap.aid = a.aid where p.status = '" + valdStatus + "' and a.avdelning = (select avdelning from anstalld where epost = '" + inloggadAnvandare + "');";
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
        JOptionPane.showMessageDialog(this, "Kunde inte fylla tabellen: " + ex.getMessage());
    }
}
    private void uppdateraTabell(ArrayList<HashMap<String, String>> projektLista) {
    String[] kolumnNamn = {"pid", "projektnamn", "beskrivning", "startdatum", "slutdatum", "status", "prioritet", "projektchef", "land"};
    DefaultTableModel modell = new DefaultTableModel(kolumnNamn, 0);

    for (HashMap<String, String> projekt : projektLista) {
        Object[] rad = new Object[kolumnNamn.length];
        for (int i = 0; i < kolumnNamn.length; i++) {
            String kolumnVarde = projekt.getOrDefault(kolumnNamn[i], "Ingen data");  
             
            rad[i] = kolumnVarde;
        }
        modell.addRow(rad);
    }

    tblProjekt.setModel(modell);
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


    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        scrollPane = new javax.swing.JScrollPane();
        tblProjekt = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        lblAvdelning = new javax.swing.JLabel();
        btnTillbaka = new javax.swing.JButton();
        comboBoxAvdelning = new javax.swing.JComboBox<>();
        btnSok = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

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
        scrollPane.setViewportView(tblProjekt);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel1.setText("Projekt på");

        lblAvdelning.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblAvdelning.setText("jLabel2");

        btnTillbaka.setText("Tillbaka");
        btnTillbaka.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTillbakaActionPerformed(evt);
            }
        });

        comboBoxAvdelning.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        btnSok.setText("Sök");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(scrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 692, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnSok)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 574, Short.MAX_VALUE)
                                .addComponent(btnTillbaka)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(comboBoxAvdelning, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblAvdelning, javax.swing.GroupLayout.PREFERRED_SIZE, 406, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(23, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(lblAvdelning))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(scrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(comboBoxAvdelning, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnTillbaka)
                    .addComponent(btnSok))
                .addContainerGap(28, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnTillbakaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTillbakaActionPerformed
        new MinAvdelning(idb, inloggadAnvandare).setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_btnTillbakaActionPerformed

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
    private javax.swing.JLabel lblAvdelning;
    private javax.swing.JScrollPane scrollPane;
    private javax.swing.JTable tblProjekt;
    // End of variables declaration//GEN-END:variables
}
