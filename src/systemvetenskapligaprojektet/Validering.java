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
    private static final String EMAIL_REGEX = "^[\\w._%+-]+@[\\w.-]+\\.[a-zA-Z]{2,}$";
    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);
    private static final String PHONE_REGEX = "^[0-9]{3}-[0-9]{3}-[0-9]{4}$";
    private static final Pattern PHONE_PATTERN = Pattern.compile(PHONE_REGEX);
    
    public static boolean valideringEmail(String email) {
        if (email == null || email.isEmpty()) {
            return false;
        }
        return EMAIL_PATTERN.matcher(email).matches();
    }
    public static boolean valideringTelefon(String telefon) {
        if (telefon == null || telefon.isEmpty()) {
            return false;
        }
        return PHONE_PATTERN.matcher(telefon).matches();
    }
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

    
