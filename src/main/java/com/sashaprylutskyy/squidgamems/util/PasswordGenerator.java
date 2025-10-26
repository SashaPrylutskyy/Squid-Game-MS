package com.sashaprylutskyy.squidgamems.util;

import java.security.SecureRandom;

public class PasswordGenerator {

    private static final String UPPERCASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String LOWERCASE = "abcdefghijklmnopqrstuvwxyz";
    private static final String DIGITS = "0123456789";
    private static final String ALL_CHARS = UPPERCASE + LOWERCASE + DIGITS;
    private static final SecureRandom RANDOM = new SecureRandom();

    public static String generate(int length) {
        if (length <= 0) {
            throw new IllegalArgumentException("Password length must be greater than 0");
        }

        StringBuilder password = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            password.append(ALL_CHARS.charAt(RANDOM.nextInt(ALL_CHARS.length())));
        }
        return password.toString();
    }

}
