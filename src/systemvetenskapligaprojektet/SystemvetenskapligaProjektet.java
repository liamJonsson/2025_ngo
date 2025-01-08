/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package systemvetenskapligaprojektet;

import oru.inf.InfDB; //importeras i alla klasser som vi ska använda
import oru.inf.InfException; //importeras i alla klasser som vi ska använda

/**
 *
 * @author limme
 */
public class SystemvetenskapligaProjektet {
    private static InfDB idb; //skapar ett fält av klassen InfDB. Fältet som man vill lagra databasuppkopplingen i
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try{
            idb = new InfDB("ngo_2024","3306","dbAdmin2024","dbAdmin2024PW"); //Namnet på databasen, vilken port,användarnamnet till databasen, lösenordet för användaren
            new Inloggning(idb).setVisible(true);
        }
        catch(InfException ex){ //om någotning går fel i uppkopplingen av databasen så kommer variabeln ex fånga upp de specifika felmeddelandena som finns i infdb-klassen
            System.out.println(ex.getMessage());   
        }
    }
    
}
