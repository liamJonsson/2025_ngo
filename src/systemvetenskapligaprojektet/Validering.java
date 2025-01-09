/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package systemvetenskapligaprojektet;
import java.util.regex.Pattern;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
/**
 *
 * @author limme
 */
public class Validering {
    //Email
    private static final String EMAIL_REGEX = "^[\\w._%+-]+@[\\w.-]+\\.[a-zA-Z]{2,}$";
    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);
    /*private static final String NGO_EMAIL_REGEX = "^[\\w._%+-]+@[\\w.-]+\\.ngo\\.org$";
    private static final Pattern NGO_EMAIL_PATTERN = Pattern.compile(NGO_EMAIL_REGEX);*/
    //Telefon
    private static final String PHONE_REGEX = "^[0-9]{3}-[0-9]{3}-[0-9]{4}$";
    private static final Pattern PHONE_PATTERN = Pattern.compile(PHONE_REGEX);
    private static final String PHONE_REGEXAvdelning = "^[0-9]{9}$";
    private static final Pattern PHONE_PATTERNAvdelning = Pattern.compile(PHONE_REGEXAvdelning);
    
    //Email
    public static boolean valideringEmail(String email) {
        if (email == null || email.isEmpty()) {
            return false;
        }
        return EMAIL_PATTERN.matcher(email).matches();
    }
    //Telefon
    public static boolean valideringTelefon(String telefon) {
        if (telefon == null || telefon.isEmpty()) {
            return false;
        }
        return PHONE_PATTERN.matcher(telefon).matches();
    }
    public static boolean valideringTelefonAvdelning(String telefon) {
        if (telefon == null || telefon.isEmpty()) {
            return false;
        }
        return PHONE_PATTERNAvdelning.matcher(telefon).matches();
    }
    public static boolean valideringTelefonPartner(String telefon) {
        if (telefon == null || telefon.isEmpty()) {
            return false;
        }
        // Regex för att matcha ett telefonnummer med formatet +xxxxxxxxxx (minst 10 siffror)
        String PHONE_REGEX_INTERNATIONAL = "^\\+\\d{10}$";
        Pattern PHONE_PATTERN_INTERNATIONAL = Pattern.compile(PHONE_REGEX_INTERNATIONAL);

        return PHONE_PATTERN_INTERNATIONAL.matcher(telefon).matches();
    }
    //Datum
    public static boolean valideringDatum(String date) {
        if (date == null || date.isEmpty()) {
            return false;
        }

        // Ange det förväntade datumformatet
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        try {
            // Försök att parsa datumet
            LocalDate.parse(date, formatter);
            return true;
        } catch (DateTimeParseException e) {
            // Ogiltigt datumformat eller ogiltigt datum
            return false;
        }
    }
}

    
