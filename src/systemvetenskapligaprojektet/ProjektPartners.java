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

        DefaultTableModel projektetsPartners = new DefaultTableModel(kolumnNamn, 0);

        String selectId = "select distinct pa.pid from partner pa join projekt_partner pp on pa.pid = pp.partner_pid join projekt pr on pp.pid = pr.pid join ans_proj ap on pr.pid = ap.pid join anstalld an on ap.aid = an.aid or pr.projektchef = an.aid where an.epost ='" + inloggadAnvandare + "' order by pa.pid;";

        ArrayList<String> pid = idb.fetchColumn(selectId);
            if(pid != null){
            
                for(String ettID:pid){
                    //int ettPid = Integer.parseInt(ettID);
                    String selectInfo = "select pid,namn,kontaktperson,kontaktepost,telefon,adress,branch,stad from partner where pid = '" + ettID + "';";



                    ArrayList<HashMap<String,String>> info = idb.fetchRows(selectInfo);

                    Object[] enRad = new Object[kolumnNamn.length];
                    int index = 0;
                    

                    for(String enKolumn:kolumnNamn){      
                        if(enKolumn.equals("stad")){
                            String selectStadNamn = "select namn from stad where sid = (select stad from partner where pid = '" + ettID + "');";
                            String stadNamn = idb.fetchSingle(selectStadNamn);
                            enRad[index++] = stadNamn;                          
                        }                        
                        else{
                            enRad[index++] = info.get(0).get(enKolumn);
                                    
                        }
                    }
                     projektetsPartners.addRow(enRad);
                }

                tblPartners.setModel( projektetsPartners);

            }

            tblPartners.setAutoResizeMode(tblPartners.AUTO_RESIZE_OFF);

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
            System.out.println(ex);
        }      

    }
     private void fyllComboBox(){
        try{
        
        //Hämta projektID från projektchefen som är inloggad + pid från de projekt den inloggade användaren är med på.
        String selectPidProjektchef = "select pid, projektnamn from projekt where projektchef = (select aid from anstalld where epost = '" + inloggadAnvandare + "') order by (pid);";      
        ArrayList<HashMap<String, String>> allaPidProjektchef = idb.fetchRows(selectPidProjektchef);
        String selectPid= "select pid, projektnamn from projekt where pid in(select pid from ans_proj where aid =(select aid from anstalld where epost = '" + inloggadAnvandare + "')) order by (pid);";      
        ArrayList<HashMap<String, String>> allaPid = idb.fetchRows(selectPid);
        
        comboBoxProjekt.removeAllItems();
        comboBoxProjekt.addItem("Välj projekt");
        
        for (HashMap<String, String> projekt : allaPidProjektchef) {
                String projektInfoProjektchef = projekt.get("pid") + " - " + projekt.get("projektnamn");
                comboBoxProjekt.addItem(projektInfoProjektchef);
            }
         for (HashMap<String, String> projekt : allaPid) {
                String projektInfo = projekt.get("pid") + " - " + projekt.get("projektnamn");
                comboBoxProjekt.addItem(projektInfo);
            }
        
        }
        catch (InfException e) {
            JOptionPane.showMessageDialog(this, "Kunde inte fylla kopplade partners: " + e.getMessage());
        }
    }
     
     private void uppdateraTabell(){
        comboBoxProjekt.addActionListener(evt -> {
            //Hämtar datan i comboboxen som valdes
            String projekt = comboBoxProjekt.getSelectedItem().toString();
            System.out.println(projekt);
            //Om comboboxen inte är vald på "Välj projekt"
            
            if(projekt.equals("Välj projekt")) {
                fyllTabell();
            }
            
            if (!projekt.equals("Välj projekt")) {
                // Regex för att matcha pid som står i starten av projekt
                Pattern pattern = Pattern.compile("(\\d+)");
                Matcher matcher = pattern.matcher(projekt);
                // Kontrollera om mönstret hittades
                if(matcher.find()){
                    String ettProjekt = matcher.group(1);
                    ettProjektID = Integer.parseInt(ettProjekt);
                }        
                    try{
            //Skapar en array som lagrar kolumnnamnen

        String[] kolumnNamn = {"pid", "namn", "kontaktperson", "kontaktepost", "telefon", "adress", "branch","stad"};

        DefaultTableModel projektetsPartners = new DefaultTableModel(kolumnNamn, 0);

        String selectId = "select pid from partner where pid in(select partner_pid from projekt_partner where pid = " + ettProjektID + ");";
        ArrayList<String> pid = idb.fetchColumn(selectId);
            if(pid != null){
            
                for(String ettID:pid){
                    //int ettPid = Integer.parseInt(ettID);
                    String selectInfo = "select pid,namn,kontaktperson,kontaktepost,telefon,adress,branch,stad from partner where pid = '" + ettID + "';";



                    ArrayList<HashMap<String,String>> info = idb.fetchRows(selectInfo);

                    Object[] enRad = new Object[kolumnNamn.length];
                    int index = 0;
                    
                    //ev ta bort for loop info + ändra else satsen
                    for(HashMap<String,String> enRadInfo:info){
                            for(String enKolumn:kolumnNamn){      
                                if(enKolumn.equals("stad")){
                                    String selectStadNamn = "select namn from stad where sid = (select stad from partner where pid = '" + ettID + "');";
                                    String stadNamn = idb.fetchSingle(selectStadNamn);
                                    enRad[index++] = stadNamn;                          
                                }                        
                                else{
                                    enRad[index++] = enRadInfo.get(enKolumn);

                                }
                            }
                            projektetsPartners.addRow(enRad);
                    }
                }

                tblPartners.setModel( projektetsPartners);

            }

            tblPartners.setAutoResizeMode(tblPartners.AUTO_RESIZE_OFF);

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

        comboBoxProjekt.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jButton1.setText("Tillbaka");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel1.setText("Partners för projekt");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 16, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jButton1)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 430, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(comboBoxProjekt, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(24, 24, 24))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(comboBoxProjekt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 40, Short.MAX_VALUE)
                .addComponent(jButton1)
                .addContainerGap())
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
