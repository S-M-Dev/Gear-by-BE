package com.smdev.gearbybe.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Random;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CodeUtil {

    public static String generateCode(){
        return String.format(
          "%s-%s-%s",
          generateNumberSequence(3),
          generateStringSequence(4),
          generateNumberSequence(3)
        );
    }

    private static String generateNumberSequence(int length){
        return generateSequence(length, '0', '9');
    }

    private static String generateStringSequence(int length){
        return generateSequence(length, 'A', 'Z');
    }

    private static String generateSequence(int length, char a, char b){
        StringBuilder buffer = new StringBuilder();
        for( ; length > 0; length--){
            buffer.append(randomCharBetween(a, b));
        }
        return buffer.toString();
    }

    private static char randomCharBetween(char a, char b){
        int offset = new Random().nextInt(b - a);
        return (char) (a + offset);
    }

}
