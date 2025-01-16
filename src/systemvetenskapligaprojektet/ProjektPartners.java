/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package systemvetenskapligaprojektet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import oru.inf.InfDB;
import oru.inf.InfException;

/**
 *
 * @author mejaa
 */
public class ProjektPartners extends javax.swing.JFrame {
    private static InfDB idb;
    private String inloggadAnvandare;
    private int ettProjektID;
    /**
     * Creates new form ProjektPartners
     */
    public ProjektPartners(InfDB idb, String inloggadAnvandare) {
        this.idb = idb;
        this.inloggadAnvandare = inloggadAnvandare;
        initComponents();
        fyllTabell();
        fyllComboBox();
        uppdateraTabell();
        
        
    }
     private void fyllTabell(){

        try{
            //Skapar en array som lagrar kolumnnamnen

        String[] kolumnNamn = {"pid", "namn", "kontaktperson", "kontaktepost", "telefon", "adress", "branch","stad"};
        
        
            //Skapar en DefaultTableModel som håller kolumnnamnen samt sätter antalet rader till noll.
        DefaultTableModel projektetsPartners = new DefaultTableModel(kolumnNamn, 0);
            
        String selectId = "select distinct pa.pid from partner pa join projekt_partner pp on pa.pid = pp.partner_pid join projekt pr on pp.pid = pr.pid join ans_proj ap on pr.pid = ap.pid join anstalld an on ap.aid = an.aid or pr.projektchef = an.aid where an.epost ='" + inloggadAnvandare + "' order by pa.pid;";
        //Hämtar alla Partner id från projekt där den inloggade användaren är handläggare och eventuellt även projektchef.
        ArrayList<String> pid = idb.fetchColumn(selectId);
            if(pid != null){
            
                for(String ettID:pid){
                    //Hämtar all data om varje partner.
                    String selectInfo = "select pid,namn,kontaktperson,kontaktepost,telefon,adress,branch,stad from partner where pid = '" + ettID + "';";

                    //Skapar en ArrayList av HashMaps som håller allt om varje partner.
                    ArrayList<HashMap<String,String>> info = idb.fetchRows(selectInfo);
                    
                    //Skapar en array som håller data för en rad i tabellen.
                    Object[] enRad = new Object[kolumnNamn.length];
                    int index = 0;
                    
                    //Skapar en for-each loop som går igenom varje kolumn i kolumnNamn.
                    for(String enKolumn:kolumnNamn){   
                        //Om enKolumn är "stad" ska namnet på staden läggas in istället för stads id.
                        if(enKolumn.equals("stad")){
                            String selectStadNamn = "select namn from stad where sid = (select stad from partner where pid = '" + ettID + "');";
                            String stadNamn = idb.fetchSingle(selectStadNamn);
                            enRad[index++] = stadNamn;                          
                        }  
                        //Värdet för den kolumn man är på läggs annars till i "enRad".
                        else{
                            enRad[index++] = info.get(0).get(enKolumn);
                                    
                        }
                    }
                    //EnRad läggs till i DefaultTableModel.
                     projektetsPartners.addRow(enRad);
                }
                
                //Jtable sätts med data från DefaultTableModel.
                tblPartners.setModel( projektetsPartners);

            }
        
        tblPartners.setAutoResizeMode(tblPartners.AUTO_RESIZE_OFF);
        //Sätter storleken på tabellen.
        TableColumn col = tblPartners.getColumnModel().getColumn(0);

        col.setPreferredWidth(50);

        col = tblPartners.getColumnModel().getColumn(1);

        col.setPreferredWidth(150);

        col = tblPartners.getColumnModel().getColumn(2);

        col.setPreferredWidth(150);

        col = tblPartners.getColumnModel().getColumn(3);

        col.setPreferredWidth(175);

        col = tblPartners.getColumnModel().getColumn(4);

        col.setPreferredWidth(150);
        
        col = tblPartners.getColumnModel().getColumn(5);

        col.setPreferredWidth(150);
        
        col = tblPartners.getColumnModel().getColumn(6);

        col.setPreferredWidth(150);
        
        col = tblPartners.getColumnModel().getColumn(7);

        col.setPreferredWidth(125);
        
        }

        catch(InfException ex){
            System.out.println(ex.getMessage());
        }      

    }
     private void fyllComboBox(){
        try{
        
        //Hämtar projekt id och projektnamn där den inloggade användaren är handläggare och eventuellt projektchef.
        String selectPidProjektchef = "select pid, projektnamn from projekt where projektchef = (select aid from anstalld where epost = '" + inloggadAnvandare + "') order by (pid);";      
        ArrayList<HashMap<String, String>> allaPidProjektchef = idb.fetchRows(selectPidProjektchef);
        String selectPid= "select pid, projektnamn from projekt where pid in(select pid from ans_proj where aid =(select aid from anstalld where epost = '" + inloggadAnvandare + "')) order by (pid);";      
        ArrayList<HashMap<String, String>> allaPid = idb.fetchRows(selectPid);
        
        comboBoxProjekt.removeAllItems();
        //Lägger till "Välj projekt" som ett alternativ i comboBoxen.
        comboBoxProjekt.addItem("Välj projekt");
        
        //Lägger in de projektID som man är projektchef på i comboBoxen.
        for (HashMap<String, String> projekt : allaPidProjektchef) {
                String projektInfoProjektchef = projekt.get("pid") + " - " + projekt.get("projektnamn");
                comboBoxProjekt.addItem(projektInfoProjektchef);
            }
        //Lägger in de projektID som man är handläggare på i comboBoxen.
         for (HashMap<String, String> projekt : allaPid) {
                String projektInfo = projekt.get("pid") + " - " + projekt.get("projektnamn");
                comboBoxProjekt.addItem(projektInfo);
         }
        }
        catch (InfException ex) {
            JOptionPane.showMessageDialog(this, "Kunde inte fylla kopplade partners: " + ex.getMessage());
        }
    }
     
     private void uppdateraTabell(){
        comboBoxProjekt.addActionListener(evt -> {
            //Hämtar datan i comboboxen som valdes.
            String projekt = comboBoxProjekt.getSelectedItem().toString();
            
            //Om man valt "Välj projekt" i comboBoxen ska tabellen fyllas som den gjorde i början.
            if(projekt.equals("Välj projekt")) {
                fyllTabell();
            }
            
            //Om comboboxen inte är vald på "Välj projekt".
            if (!projekt.equals("Välj projekt")) {
                //Regex för att matcha pid som står i starten av projektet.
                Pattern pattern = Pattern.compile("(\\d+)");
                Matcher matcher = pattern.matcher(projekt);
                //Kontrollera om mönstret hittades och om det är hittat sätts det som "ettProjektID".
                if(matcher.find()){
                    try{String ettProjekt = matcher.group(1);
                    ettProjektID = Integer.parseInt(ettProjekt);
                    }
                    catch(NumberFormatException ex){
                        System.out.println(ex);
                    }
                }        
                    try{
            //Skapar en array som lagrar kolumnnamnen

        String[] kolumnNamn = {"pid", "namn", "kontaktperson", "kontaktepost", "telefon", "adress", "branch","stad"};
        //Skapar en DefaultTableModel som håller kolumnnamnen samt sätter antalet rader till till noll.
        DefaultTableModel projektetsPartners = new DefaultTableModel(kolumnNamn, 0);

        String selectId = "select pid from partner where pid in(select partner_pid from projekt_partner where pid = " + ettProjektID + ");";
        //Hämtar Partner id från ettProjektID, vilket är det projekt som valdes i comboBoxen.
        ArrayList<String> pid = idb.fetchColumn(selectId);
            if(pid != null){
                //För varje Partner id i "pid" tas data fram om partnern samt datan hämtas.
                for(String ettID:pid){
                    
                    String selectInfo = "select pid,namn,kontaktperson,kontaktepost,telefon,adress,branch,stad from partner where pid = '" + ettID + "';";



                    ArrayList<HashMap<String,String>> info = idb.fetchRows(selectInfo);

                    //Skapar en array som håller data för en rad i tabellen.
                    Object[] enRad = new Object[kolumnNamn.length];
                    int index = 0;
                   
                    for(HashMap<String,String> enRadInfo:info){
                        //Skapar en for-each loop som går igenom varje kolumn i kolumnNamn.
                            for(String enKolumn:kolumnNamn){  
                               //Om enKolumn är "stad" ska namnet på staden läggas in istället för sid.
                                if(enKolumn.equals("stad")){
                                    String selectStadNamn = "select namn from stad where sid = (select stad from partner where pid = '" + ettID + "');";
                                    String stadNamn = idb.fetchSingle(selectStadNamn);
                                    enRad[index++] = stadNamn;                          
                                }   
                                //Värdet för den kolumn man är på läggs annars till i enRad.
                                else{
                                    enRad[index++] = enRadInfo.get(enKolumn);

                                }
                            }
                            //EnRad läggs till i DefaultTableModel.
                            projektetsPartners.addRow(enRad);
                    }
                }
                //Jtable sätts med data från DefaultTableModel.
                tblPartners.setModel( projektetsPartners);

            }
            
            tblPartners.setAutoResizeMode(tblPartners.AUTO_RESIZE_OFF);
        //Sätter storleken på tabellen.
        TableColumn col = tblPartners.getColumnModel().getColumn(0);

        col.setPreferredWidth(50);

        col = tblPartners.getColumnModel().getColumn(1);

        col.setPreferredWidth(150);

        col = tblPartners.getColumnModel().getColumn(2);

        col.setPreferredWidth(150);

        col = tblPartners.getColumnModel().getColumn(3);

        col.setPreferredWidth(175);

        col = tblPartners.getColumnModel().getColumn(4);

        col.setPreferredWidth(150);
        
        col = tblPartners.getColumnModel().getColumn(5);

        col.setPreferredWidth(150);
        
        col = tblPartners.getColumnModel().getColumn(6);

        col.setPreferredWidth(150);
        
        col = tblPartners.getColumnModel().getColumn(7);

        col.setPreferredWidth(125);
        
        }

        catch(InfException ex){
            System.out.println(ex.getMessage());
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

        jScrollPane1 = new javax.swing.JScrollPane();
        tblPartners = new javax.swing.JTable();
        comboBoxProjekt = new javax.swing.JComboBox<>();
        jButton1 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        tblPartners.setFont(new java.awt.Font("Microsoft JhengHei UI", 0, 12)); // NOI18N
        tblPartners.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(tblPartners);

        comboBoxProjekt.setFont(new java.awt.Font("Microsoft JhengHei UI", 0, 12)); // NOI18N
        comboBoxProjekt.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jButton1.setFont(new java.awt.Font("Microsoft JhengHei UI", 0, 12)); // NOI18N
        jButton1.setText("Tillbaka");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Microsoft JhengHei UI", 1, 18)); // NOI18N
        jLabel1.setText("SAMARBETSPARTNERS");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(jButton1))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 636, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addComponent(comboBoxProjekt, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(35, 35, 35))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(comboBoxProjekt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(45, 45, 45)
                .addComponent(jButton1)
                .addGap(35, 35, 35))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        new MinaProjekt(idb, inloggadAnvandare).setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_jButton1ActionPerformed

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
            java.util.logging.Logger.getLogger(ProjektPartners.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ProjektPartners.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ProjektPartners.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ProjektPartners.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                //new ProjektPartners().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> comboBoxProjekt;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblPartners;
    // End of variables declaration//GEN-END:variables
}
