package com.ssafy.backend.util;

import java.util.Random;

public class PasswordUtil {
    public static String getRandomPassword(int len) {
        char[] characters = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
        StringBuilder sb = new StringBuilder();
        Random r = new Random();
        for (int i = 0; i < len; i++) {
            sb.append(characters[r.nextInt(characters.length)]);
        }
        return sb.toString();
    }
}
