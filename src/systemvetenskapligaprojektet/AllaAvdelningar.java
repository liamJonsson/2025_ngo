/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package systemvetenskapligaprojektet;
import oru.inf.InfDB; //importeras i alla klasser som vi ska använda
import oru.inf.InfException; //importeras i alla klasser som vi ska använda
import javax.swing.DefaultListModel;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.SpringLayout;
/**
 *
 * @author limme
 */
public class AllaAvdelningar extends javax.swing.JFrame {
    private static InfDB idb;
    private String inloggadAnvandare;
    //Skapa en DefaultModel
    private DefaultListModel<String> listModelID = new DefaultListModel<>(); //Skapar list modeller som kan visas i gränssnittet
    private DefaultListModel<String> listModelNamn = new DefaultListModel<>(); //Skapar list modeller som kan visas i gränssnittet
    private DefaultListModel<String> listModelBeskrivning = new DefaultListModel<>(); //Skapar list modeller som kan visas i gränssnittet
    private DefaultListModel<String> listModelAdress = new DefaultListModel<>(); //Skapar list modeller som kan visas i gränssnittet
    private DefaultListModel<String> listModelEpost = new DefaultListModel<>(); //Skapar list modeller som kan visas i gränssnittet
    private DefaultListModel<String> listModelTelefon = new DefaultListModel<>(); //Skapar list modeller som kan visas i gränssnittet
    private DefaultListModel<String> listModelStad = new DefaultListModel<>(); //Skapar list modeller som kan visas i gränssnittet
    private DefaultListModel<String> listModelAvdelningschefFNamn = new DefaultListModel<>(); //Skapar list modeller som kan visas i gränssnittet
    private DefaultListModel<String> listModelAvdelningschefENamn = new DefaultListModel<>(); //Skapar list modeller som kan visas i gränssnittet

    /**
     * Creates new form AllaAvdelningar
     */
    public AllaAvdelningar(InfDB idb, String inloggadAnvandare) {
        this.inloggadAnvandare = inloggadAnvandare;
        this.idb = idb;
        initComponents();
        avdelningar();
    }
    
    /*public int antalAvdelningar(){
        int antal = 0;
        try{
        String selectAntalAvdelningar = "select count(avdid) from avdelning;";
        antalAvdelningar = idb.fetchSingle(selectAntalAvdelningar);
        antal = Integer.parseInt(antalAvdelningar);
        }
        catch(InfException ex){
            System.out.println(ex);
        }
        return antal;
    }*/
    
    public void avdelningar() {
        // Skapa en ArrayList för att hålla data från databasen
        ArrayList<String> avdelningsID = new ArrayList<>();
        ArrayList<String> avdelningsnamn = new ArrayList<>();
        ArrayList<String> beskrivning = new ArrayList<>();
        ArrayList<String> adress = new ArrayList<>();
        ArrayList<String> epost = new ArrayList<>();
        ArrayList<String> telefon = new ArrayList<>();
        ArrayList<String> allaAvdelningar = new ArrayList<>();
        ArrayList<String> avdelningschefFNamn = new ArrayList<>();
        ArrayList<String> avdelningschefENamn = new ArrayList<>();
        try{
            // SQL-fråga för att hämta id
            String selectAvdid = "select avdid from avdelning order by(avdid) asc;";
            avdelningsID = idb.fetchColumn(selectAvdid);
            // SQL-fråga för att hämta namnet
            String selectAvdelningsnamn = "select namn from avdelning;";
            avdelningsnamn = idb.fetchColumn(selectAvdelningsnamn);
            
            String selectBeskrivning = "select beskrivning from avdelning;";
            beskrivning = idb.fetchColumn(selectBeskrivning);
            
            String selectAdress = "select adress from avdelning;";
            adress = idb.fetchColumn(selectAdress);
            
            String selectEpost = "select epost from avdelning;";
            epost = idb.fetchColumn(selectEpost);
            
            String selectTelefon = "select telefon from avdelning;";
            telefon = idb.fetchColumn(selectTelefon);
            
            String selectAllaAvdelningar = "select avdid from avdelning order by(avdid) asc;";
            allaAvdelningar = idb.fetchColumn(selectAllaAvdelningar);
            
            String selectAvdelningschefFNamn = "select fornamn from anstalld where aid in (select chef from avdelning);";
            avdelningschefFNamn = idb.fetchColumn(selectAvdelningschefFNamn);
            
            String selectAvdelningschefENamn = "select efternamn from anstalld where aid in (select chef from avdelning);";
            avdelningschefENamn = idb.fetchColumn(selectAvdelningschefENamn);
         
        
        // Lägg till data från ArrayList till modellen
        for(String ettID:avdelningsID){
            listModelID.addElement(ettID); //Loopar igenom listan avdelningsID
        }
        for(String ettNamn:avdelningsnamn){
            listModelNamn.addElement(ettNamn); //Loopar igenom listan avdelningsNamn
        }
        for(String enBeskrivning:beskrivning){
            listModelBeskrivning.addElement(enBeskrivning); //Loopar igenom listan avdelningsNamn
        }
        for(String enAdress:adress){
            listModelAdress.addElement(enAdress); //Loopar igenom listan avdelningsNamn
        }
        for(String enEpost:epost){
            listModelEpost.addElement(enEpost); //Loopar igenom listan avdelningsNamn
        }
        for(String enTelefon:telefon){
            listModelTelefon.addElement(enTelefon); //Loopar igenom listan avdelningsNamn
        }
        for(String enAvdelning:allaAvdelningar){
            int ettAvdelningsID = Integer.parseInt(enAvdelning);
            String selectStad = "select stad from avdelning where avdid = " + ettAvdelningsID + ";";
            String stad = idb.fetchSingle(selectStad);
            int stadsID = Integer.parseInt(stad);
            String selectStadsNamn = "select namn from stad where sid = " + stadsID + ";";
            String stadsNamn = idb.fetchSingle(selectStadsNamn);
            listModelStad.addElement(stadsNamn);
        }
        for(String enAvdelning:allaAvdelningar){
            int ettAvdelningsID = Integer.parseInt(enAvdelning);
            String selectChef = "select chef from avdelning where avdid = " + ettAvdelningsID + ";";
            String chef = idb.fetchSingle(selectChef);
            int chefsID = Integer.parseInt(chef);
            String selectChefsFornamn = "select fornamn from anstalld where aid = " + chefsID + ";";
            String chefsFornamn = idb.fetchSingle(selectChefsFornamn);
            listModelAvdelningschefFNamn.addElement(chefsFornamn); //Loopar igenom listan avdelningsNamn
        }
        for(String enAvdelning:allaAvdelningar){
            int ettAvdelningsID = Integer.parseInt(enAvdelning);
            String selectChef = "select chef from avdelning where avdid = " + ettAvdelningsID + ";";
            String chef = idb.fetchSingle(selectChef);
            int chefsID = Integer.parseInt(chef);
            String selectChefsEfternamn = "select efternamn from anstalld where aid = " + chefsID + ";";
            String chefsEfternamn = idb.fetchSingle(selectChefsEfternamn);
            listModelAvdelningschefENamn.addElement(chefsEfternamn); //Loopar igenom listan avdelningsNamn
        }
        listAvdelningsID.setModel(listModelID); //Uppdaterar modellerna som nu innehåller data
        listAvdelningsnamn.setModel(listModelNamn); //Uppdaterar modellerna som nu innehåller data      
        listBeskrivning.setModel(listModelBeskrivning); //Uppdaterar modellerna som nu innehåller data
        listAdress.setModel(listModelAdress); //Uppdaterar modellerna som nu innehåller data
        listEpost.setModel(listModelEpost); //Uppdaterar modellerna som nu innehåller data
        listTelefon.setModel(listModelTelefon); //Uppdaterar modellerna som nu innehåller data
        listStad.setModel(listModelStad); //Uppdaterar modellerna som nu innehåller data
        listChefFNamn.setModel(listModelAvdelningschefFNamn); //Uppdaterar modellerna som nu innehåller data
        listChefENamn.setModel(listModelAvdelningschefENamn); //Uppdaterar modellerna som nu innehåller data
        }
        catch (InfException ex) {
            ex.printStackTrace();
        }
    }
    /*
    public void fyllTabell(){
        try{
        String[] kolumnNamn = {"avdid", "namn", "beskrivning", "adress", "epost", "telefon", "stad", "chef"};
        DefaultTableModel allaAvdelningar = new DefaultTableModel(kolumnNamn, 0);
        
        String selectAvdid = "select avdid from avdelning order by(avdid);";
        ArrayList<String> avdid = idb.fetchColumn(selectAvdid);
            if(avdid != null){
                for(String ettID:avdid){
                    String selectInfo = "select * from avdelning where avdid = " + ettID + ";";
                    HashMap<String,String> info = idb.fetchRow(selectInfo);
            
                    Object[] enRad = new Object[kolumnNamn.length];
                    int index = 0;
            
                    for(String enKolumn:kolumnNamn){
                        if(enKolumn.equals("stad")){
                            String selectStad = 
                            "select namn from stad where sid = (select stad from avdelning where avdid = " + ettID + ");";
                            String stad = idb.fetchSingle(selectStad);
                            enRad[index++] = stad;
                        }
                        else if(enKolumn.equals("chef")){
                            String selectChefFornamn = 
                            "select fornamn from anstalld where aid = (select chef from avdelning where avdid = " + ettID + ");";
                            String chefFornamn = idb.fetchSingle(selectChefFornamn);
                            String selectChefEfternamn = 
                            "select efternamn from anstalld where aid = (select chef from avdelning where avdid = " + ettID + ");";
                            String chefEfternamn = idb.fetchSingle(selectChefEfternamn);
                            String chefFulltNamn = chefFornamn + " " + chefEfternamn;
                            enRad[index++] = chefFulltNamn;
                        }
                        else{
                            enRad[index++] = info.get(enKolumn);}
                    }
                    allaAvdelningar.addRow(enRad);
                }
                tblTest.setModel(allaAvdelningar);
            }
            tblTest.setAutoResizeMode(tblTest.AUTO_RESIZE_OFF);
        TableColumn col = tblTest.getColumnModel().getColumn(0);
        col.setPreferredWidth(100);
        col = tblTest.getColumnModel().getColumn(1);
        col.setPreferredWidth(400);
        col = tblTest.getColumnModel().getColumn(2);
        col.setPreferredWidth(900);
        col = tblTest.getColumnModel().getColumn(3);
        col.setPreferredWidth(150);
        col = tblTest.getColumnModel().getColumn(4);
        col.setPreferredWidth(150);
        col = tblTest.getColumnModel().getColumn(5);
        col.setPreferredWidth(150);
        col = tblTest.getColumnModel().getColumn(6);
        col.setPreferredWidth(150);
        col = tblTest.getColumnModel().getColumn(7);
        col.setPreferredWidth(150);
        }
        catch(InfException ex){
            System.out.println(ex);
        }       
    }*/
    
    /*

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        listAvdelningsID = new javax.swing.JList<>();
        btnTillbaka = new javax.swing.JButton();
        btnLaggTill = new javax.swing.JButton();
        btnSok = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        listAvdelningsnamn = new javax.swing.JList<>();
        jScrollPane4 = new javax.swing.JScrollPane();
        listBeskrivning = new javax.swing.JList<>();
        jScrollPane2 = new javax.swing.JScrollPane();
        listAdress = new javax.swing.JList<>();
        jScrollPane5 = new javax.swing.JScrollPane();
        listEpost = new javax.swing.JList<>();
        jScrollPane6 = new javax.swing.JScrollPane();
        listTelefon = new javax.swing.JList<>();
        jScrollPane7 = new javax.swing.JScrollPane();
        listStad = new javax.swing.JList<>();
        jScrollPane8 = new javax.swing.JScrollPane();
        listChefFNamn = new javax.swing.JList<>();
        lblID = new javax.swing.JLabel();
        lblNamn = new javax.swing.JLabel();
        lblBeskrivning = new javax.swing.JLabel();
        lblAdress = new javax.swing.JLabel();
        lblEpost = new javax.swing.JLabel();
        lblTelefon = new javax.swing.JLabel();
        lblStad = new javax.swing.JLabel();
        lblChef = new javax.swing.JLabel();
        lblAllaAvdelningar = new javax.swing.JLabel();
        jScrollPane9 = new javax.swing.JScrollPane();
        listChefENamn = new javax.swing.JList<>();
        btnRedigera = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(1550, 300));

        listAvdelningsID.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane1.setViewportView(listAvdelningsID);

        btnTillbaka.setText("Tillbaka");
        btnTillbaka.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTillbakaActionPerformed(evt);
            }
        });

        btnLaggTill.setText("Lägg Till");
        btnLaggTill.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLaggTillActionPerformed(evt);
            }
        });

        btnSok.setText("OBS SOK!!!");

        listAvdelningsnamn.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        listAvdelningsnamn.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        listAvdelningsnamn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                listAvdelningsnamnMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(listAvdelningsnamn);

        listBeskrivning.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane4.setViewportView(listBeskrivning);

        listAdress.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane2.setViewportView(listAdress);

        listEpost.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane5.setViewportView(listEpost);

        listTelefon.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane6.setViewportView(listTelefon);

        listStad.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane7.setViewportView(listStad);

        listChefFNamn.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane8.setViewportView(listChefFNamn);

        lblID.setText("   ID");

        lblNamn.setText("   Namn");

        lblBeskrivning.setText("   Beskrivning");

        lblAdress.setText("   Adress");

        lblEpost.setText("   Epost");

        lblTelefon.setText("   Telefon");

        lblStad.setText("   Stad");

        lblChef.setText("   Avdelningschef");

        lblAllaAvdelningar.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblAllaAvdelningar.setText("Alla avdelningar");

        listChefENamn.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane9.setViewportView(listChefENamn);

        btnRedigera.setText("Redigera");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblID, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(lblNamn, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(21, 21, 21)
                                        .addComponent(lblBeskrivning, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnLaggTill)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnRedigera)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnTillbaka)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnSok, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblAdress, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblEpost, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblTelefon, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblStad, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(lblChef, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(404, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblAllaAvdelningar)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(lblAllaAvdelningar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblNamn)
                            .addComponent(lblID)
                            .addComponent(lblBeskrivning)
                            .addComponent(lblAdress)
                            .addComponent(lblEpost)
                            .addComponent(lblTelefon)
                            .addComponent(lblStad)
                            .addComponent(lblChef))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane9, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(17, 17, 17)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnLaggTill)
                    .addComponent(btnSok)
                    .addComponent(btnTillbaka, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnRedigera))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnLaggTillActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLaggTillActionPerformed
        new LaggTillAvdelning(idb,inloggadAnvandare).setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_btnLaggTillActionPerformed

    private void btnTillbakaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTillbakaActionPerformed
        new MenyAdmin(idb,inloggadAnvandare).setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnTillbakaActionPerformed

    private void listAvdelningsnamnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_listAvdelningsnamnMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_listAvdelningsnamnMouseClicked

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
            java.util.logging.Logger.getLogger(AllaAvdelningar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AllaAvdelningar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AllaAvdelningar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AllaAvdelningar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                //new AllaAvdelningar().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnLaggTill;
    private javax.swing.JButton btnRedigera;
    private javax.swing.JButton btnSok;
    private javax.swing.JButton btnTillbaka;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JLabel lblAdress;
    private javax.swing.JLabel lblAllaAvdelningar;
    private javax.swing.JLabel lblBeskrivning;
    private javax.swing.JLabel lblChef;
    private javax.swing.JLabel lblEpost;
    private javax.swing.JLabel lblID;
    private javax.swing.JLabel lblNamn;
    private javax.swing.JLabel lblStad;
    private javax.swing.JLabel lblTelefon;
    private javax.swing.JList<String> listAdress;
    private javax.swing.JList<String> listAvdelningsID;
    private javax.swing.JList<String> listAvdelningsnamn;
    private javax.swing.JList<String> listBeskrivning;
    private javax.swing.JList<String> listChefENamn;
    private javax.swing.JList<String> listChefFNamn;
    private javax.swing.JList<String> listEpost;
    private javax.swing.JList<String> listStad;
    private javax.swing.JList<String> listTelefon;
    // End of variables declaration//GEN-END:variables
}
