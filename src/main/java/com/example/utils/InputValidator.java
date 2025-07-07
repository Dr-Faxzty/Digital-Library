package com.example.utils;

import java.util.regex.Pattern;

public class InputValidator {

    private static final Pattern EMAIL_REGEX = Pattern.compile("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$");
    private static final Pattern TC_REGEX = Pattern.compile("^[A-Z]{6}[0-9]{2}[A-Z][0-9]{2}[A-Z][0-9]{3}[A-Z]$");

    public static boolean isEmailValid(String email) {
        return email != null && EMAIL_REGEX.matcher(email).matches();
    }

    public static boolean isTaxCodeValid(String cf) {
        return cf != null && TC_REGEX.matcher(cf.toUpperCase()).matches();
    }

    public static boolean isPasswordValid(String password) {
        return password != null && password.length() >= 6;
    }

    public static boolean areFieldsFilled(String... fields) {
        for (String field : fields) {
            if (field == null || field.trim().isEmpty()) {
                return false;
            }
        }
        return true;
    }
}
