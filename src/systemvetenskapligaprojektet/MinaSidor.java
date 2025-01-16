/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package systemvetenskapligaprojektet;
import oru.inf.InfDB; //importeras i alla klasser som vi ska använda
import oru.inf.InfException; //importeras i alla klasser som vi ska använda
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author meam
 */
public class MinaSidor extends javax.swing.JFrame {

    private InfDB idb;
    private String inloggadAnvandare;
    private String text;
    
    /**
     * Creates new form MinaSidor
     */
    public MinaSidor(InfDB idb,String inloggadAnvandare) {
        this.idb = idb;
        this.inloggadAnvandare = inloggadAnvandare;
        
        initComponents();
        taFramInfo();
        taFramArbetsroll();
    }

     private void taFramInfo(){
        try{
            
        //Hämtar aid, fornamn, efternamn,telefonnummer,epost,adress,avdelning och anstallningsdatum från anstalld där eposten är den inloggade användarens epost, som sedan hamnar i varsitt textfield.
        String selectAid = "select aid from anstalld where epost = '" + inloggadAnvandare + "';";
        String anstalldsId = idb.fetchSingle(selectAid);
        tfAid.setText(anstalldsId);
        
        String selectFornamn = "select fornamn from anstalld where epost = '" + inloggadAnvandare + "';";
        String anstalldsFornamn = idb.fetchSingle(selectFornamn);
        tfFornamn.setText(anstalldsFornamn);
        
        String selectEfternamn = "select efternamn from anstalld where epost = '" + inloggadAnvandare + "';";
        String anstalldsEfternamn = idb.fetchSingle(selectEfternamn);
        tfEfternamn.setText(anstalldsEfternamn);
        
        String selectTelefonnummer = "select telefon from anstalld where epost = '" + inloggadAnvandare + "';";
        String anstalldsTelefon = idb.fetchSingle(selectTelefonnummer);
        tfTelefonnummer.setText(anstalldsTelefon);
        
        String selectEpost = "select epost from anstalld where epost = '" + inloggadAnvandare + "';";
        String anstalldsEpost = idb.fetchSingle(selectEpost);
        tfEpost.setText(anstalldsEpost);
        
        String selectAdress = "select adress from anstalld where epost = '" + inloggadAnvandare + "';";
        String anstalldsAdress = idb.fetchSingle(selectAdress);
        tfAdress.setText(anstalldsAdress);
        
        String selectAvdelning = "select avdelning from anstalld where epost = '" + inloggadAnvandare + "';";
        String anstalldsAvdelning = idb.fetchSingle(selectAvdelning); //Ok även om avdelningsnummret egentligen är en int?.
        tfAvdelning.setText(anstalldsAvdelning);
        
        String selectAnstallningsdatum ="select anstallningsdatum from anstalld where epost = '" + inloggadAnvandare + "';";
        String anstalldsAnstallningsdatum = idb.fetchSingle(selectAnstallningsdatum);
        tfAnstallningsdatum.setText(anstalldsAnstallningsdatum);
        
    }
        catch(InfException ex){
            System.out.println(ex); 
            
        }
    }
    
    private void taFramArbetsroll(){
        ArrayList<String> projektchef = new ArrayList<>();
        ArrayList<String> handlaggare = new ArrayList<>();
        text = "Administratör";
        try{
            //Hämtar den inloggade användarens aid.
            String selectAid = "select aid from anstalld where epost = '" + inloggadAnvandare + "';";
            String anstalldsId = idb.fetchSingle(selectAid); 
            
            //Hämtar alla projektchefers ansällningsid och lägger dessa i en ArrayList "projektchef".
            String selectProjektchef = "select projektchef from projekt;";
            projektchef = idb.fetchColumn(selectProjektchef);
            
            //Hämtar alla handläggares anställningsid och lägger dessa i ArrayListan "handlaggare".
            String selectHandlaggare = "select aid from handlaggare;";
            handlaggare = idb.fetchColumn(selectHandlaggare);
          
            //Loopar igenom "handlaggare", om ett id från denna listan är samma som den inloggade användarens id sätts "text" till "Handläggare".
            for(String enHandlaggare : handlaggare){
                if(enHandlaggare.equals(anstalldsId)){
                    text = "Handläggare";
                }
            //Loopar igenom "projektchef", om ett id från denna listan är samma som den inloggade användarens id sätts "text" till "Handläggare/Projektchef".
            for(String ettProjektchefsId : projektchef){
                if(ettProjektchefsId.equals(anstalldsId)){
                    text = "Handläggare/Projektchef";
                }
            }
            
            }
            //Textfieldet "tfArbetsroll" sätts till det som "text" lagrar.
            tfArbetsRoll.setText(text); 
            }
            
        catch (InfException ex) {
            System.out.println(ex);
        }
    }
      private void enableOff(){
        //Sätter textfälten för förnamn, efternamn, adress till att det inte går att ändra i dessa.
        tfFornamn.setEditable(false);  
        tfEfternamn.setEditable(false);
        tfAdress.setEditable(false);
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTextField11 = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        tfAid = new javax.swing.JTextField();
        tfFornamn = new javax.swing.JTextField();
        tfEfternamn = new javax.swing.JTextField();
        tfTelefonnummer = new javax.swing.JTextField();
        tfEpost = new javax.swing.JTextField();
        tfAdress = new javax.swing.JTextField();
        tfAvdelning = new javax.swing.JTextField();
        tfArbetsRoll = new javax.swing.JTextField();
        lblAid = new javax.swing.JLabel();
        lblAvdelning = new javax.swing.JLabel();
        btnSpara = new javax.swing.JButton();
        btnTillbaka = new javax.swing.JButton();
        btnRedigeraUppgifter = new javax.swing.JButton();
        lblAnstalldSedan = new javax.swing.JLabel();
        tfAnstallningsdatum = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();

        jTextField11.setText("jTextField1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Microsoft JhengHei UI", 1, 18)); // NOI18N
        jLabel1.setText("MINA SIDOR");

        tfAid.setEditable(false);
        tfAid.setFont(new java.awt.Font("Microsoft JhengHei UI", 0, 12)); // NOI18N

        tfFornamn.setEditable(false);
        tfFornamn.setFont(new java.awt.Font("Microsoft JhengHei UI", 0, 12)); // NOI18N

        tfEfternamn.setEditable(false);
        tfEfternamn.setFont(new java.awt.Font("Microsoft JhengHei UI", 0, 12)); // NOI18N

        tfTelefonnummer.setEditable(false);
        tfTelefonnummer.setFont(new java.awt.Font("Microsoft JhengHei UI", 0, 12)); // NOI18N

        tfEpost.setEditable(false);
        tfEpost.setFont(new java.awt.Font("Microsoft JhengHei UI", 0, 12)); // NOI18N

        tfAdress.setEditable(false);
        tfAdress.setFont(new java.awt.Font("Microsoft JhengHei UI", 0, 12)); // NOI18N

        tfAvdelning.setEditable(false);
        tfAvdelning.setFont(new java.awt.Font("Microsoft JhengHei UI", 0, 12)); // NOI18N

        tfArbetsRoll.setEditable(false);
        tfArbetsRoll.setFont(new java.awt.Font("Microsoft JhengHei UI", 0, 12)); // NOI18N

        lblAid.setFont(new java.awt.Font("Microsoft JhengHei UI", 1, 12)); // NOI18N
        lblAid.setText("AnställningsID");

        lblAvdelning.setFont(new java.awt.Font("Microsoft JhengHei UI", 1, 12)); // NOI18N
        lblAvdelning.setText("Avdelning");

        btnSpara.setFont(new java.awt.Font("Microsoft JhengHei UI", 0, 12)); // NOI18N
        btnSpara.setText("Spara");
        btnSpara.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSparaActionPerformed(evt);
            }
        });

        btnTillbaka.setFont(new java.awt.Font("Microsoft JhengHei UI", 0, 12)); // NOI18N
        btnTillbaka.setText("Tillbaka");
        btnTillbaka.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTillbakaActionPerformed(evt);
            }
        });

        btnRedigeraUppgifter.setFont(new java.awt.Font("Microsoft JhengHei UI", 0, 12)); // NOI18N
        btnRedigeraUppgifter.setText("Redigera uppgifter");
        btnRedigeraUppgifter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRedigeraUppgifterActionPerformed(evt);
            }
        });

        lblAnstalldSedan.setFont(new java.awt.Font("Microsoft JhengHei UI", 1, 12)); // NOI18N
        lblAnstalldSedan.setText("Anställningsdatum");

        tfAnstallningsdatum.setEditable(false);
        tfAnstallningsdatum.setFont(new java.awt.Font("Microsoft JhengHei UI", 0, 12)); // NOI18N

        jLabel2.setFont(new java.awt.Font("Microsoft JhengHei UI", 1, 12)); // NOI18N
        jLabel2.setText("Förnamn");

        jLabel3.setFont(new java.awt.Font("Microsoft JhengHei UI", 1, 12)); // NOI18N
        jLabel3.setText("Efternamn");

        jLabel4.setFont(new java.awt.Font("Microsoft JhengHei UI", 1, 12)); // NOI18N
        jLabel4.setText("Adress");

        jLabel5.setFont(new java.awt.Font("Microsoft JhengHei UI", 1, 12)); // NOI18N
        jLabel5.setText("Epost");

        jLabel6.setFont(new java.awt.Font("Microsoft JhengHei UI", 1, 12)); // NOI18N
        jLabel6.setText("Telefon");

        jLabel7.setFont(new java.awt.Font("Microsoft JhengHei UI", 1, 12)); // NOI18N
        jLabel7.setText("Arbetsroll");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(btnRedigeraUppgifter)
                            .addGap(45, 45, 45)
                            .addComponent(btnSpara)
                            .addGap(18, 18, 18)
                            .addComponent(btnTillbaka))
                        .addGroup(layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(lblAid)
                                .addComponent(jLabel3)
                                .addComponent(jLabel4)
                                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel6)
                                .addComponent(lblAvdelning)
                                .addComponent(jLabel7)
                                .addComponent(lblAnstalldSedan)
                                .addComponent(jLabel2))
                            .addGap(35, 35, 35)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(tfEpost, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                                .addComponent(tfAid)
                                .addComponent(tfFornamn)
                                .addComponent(tfEfternamn)
                                .addComponent(tfTelefonnummer)
                                .addComponent(tfAdress)
                                .addComponent(tfAvdelning)
                                .addComponent(tfArbetsRoll)
                                .addComponent(tfAnstallningsdatum)))))
                .addGap(35, 35, 35))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblAid)
                    .addComponent(tfAid, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tfFornamn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tfEfternamn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(tfAdress, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tfEpost, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(tfTelefonnummer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tfAvdelning, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblAvdelning))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tfArbetsRoll, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tfAnstallningsdatum, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblAnstalldSedan))
                .addGap(43, 43, 43)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnTillbaka)
                    .addComponent(btnSpara)
                    .addComponent(btnRedigeraUppgifter))
                .addGap(35, 35, 35))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnTillbakaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTillbakaActionPerformed
    //Om text är lika med "Handläggare" eller om text är lika med "Handläggare/Projektchef" kommer tillbakaknappen att föra en till MenyHandlaggare.
        if(text.equals("Handläggare") || text.equals("Handläggare/Projektchef")){
            new MenyHandlaggare(idb, inloggadAnvandare).setVisible(true);
        }
        //Annars kommer man att skickas till MenyAdmin.
        else{
            new MenyAdmin(idb, inloggadAnvandare).setVisible(true);   
        }
        //MinaSidor sätts till osynlig.
        this.setVisible(false);           
    }//GEN-LAST:event_btnTillbakaActionPerformed

    private void btnRedigeraUppgifterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRedigeraUppgifterActionPerformed
        //När man trycker på knappen "Redigera uppgifter" kommer textfältet för förnamn, efternamn samt adress att bli redigerbara.
        tfFornamn.setEditable(true);  
        tfEfternamn.setEditable(true);
        tfAdress.setEditable(true);
    }//GEN-LAST:event_btnRedigeraUppgifterActionPerformed

    private void btnSparaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSparaActionPerformed

        try{
            //Hämtar den sträng av text som står i textfältet för förnamn, efternamn samt adress och skickar sedan in dessa strängar till databasen så att datan uppdateras. 
            String fornamn = tfFornamn.getText();
            String updateFornamn = "UPDATE anstalld SET fornamn ='"+ fornamn + "' WHERE epost ='"+ inloggadAnvandare + "';";
            idb.update(updateFornamn);
        
            String efternamn = tfEfternamn.getText();
            String updateEfternamn = "UPDATE anstalld SET efternamn ='"+ efternamn + "' WHERE epost ='"+ inloggadAnvandare + "';";
            idb.update(updateEfternamn);
        
            String adress = tfAdress.getText();
            String updateAdress = "UPDATE anstalld SET adress ='"+ adress + "' WHERE epost ='"+ inloggadAnvandare + "';";
            idb.update(updateAdress);
        }
        catch(InfException ex){
            System.out.println(ex);
        }
        
        enableOff();
        
    }//GEN-LAST:event_btnSparaActionPerformed

  
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
            java.util.logging.Logger.getLogger(MinaSidor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MinaSidor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MinaSidor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MinaSidor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                //new MinaSidor().setVisible(true);
            }
        });
    }
    
    
   
            

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnRedigeraUppgifter;
    private javax.swing.JButton btnSpara;
    private javax.swing.JButton btnTillbaka;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JTextField jTextField11;
    private javax.swing.JLabel lblAid;
    private javax.swing.JLabel lblAnstalldSedan;
    private javax.swing.JLabel lblAvdelning;
    private javax.swing.JTextField tfAdress;
    private javax.swing.JTextField tfAid;
    private javax.swing.JTextField tfAnstallningsdatum;
    private javax.swing.JTextField tfArbetsRoll;
    private javax.swing.JTextField tfAvdelning;
    private javax.swing.JTextField tfEfternamn;
    private javax.swing.JTextField tfEpost;
    private javax.swing.JTextField tfFornamn;
    private javax.swing.JTextField tfTelefonnummer;
    // End of variables declaration//GEN-END:variables
}
