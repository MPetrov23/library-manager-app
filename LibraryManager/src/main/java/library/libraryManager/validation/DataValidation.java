package library.libraryManager.validation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DataValidation {
    public static boolean isValidPhoneNumber(String number) {
        String regex = "^[+]?(\\d{1,2})?[\\s.-]?\\(?\\d{3}\\)?[\\s.-]?\\d{3}[\\s.-]?\\d{4}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(number);

        return matcher.matches();
    }


}
