package com.mycompany.pouloum.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author DASI Team
 */
public class Input {
    
    public static String readString(String invite) {
        String chaineLue = null;
        System.out.print(invite);
        try {
            InputStreamReader isr = new InputStreamReader(System.in);
            BufferedReader br = new BufferedReader(isr);
            chaineLue = br.readLine();
        } catch (IOException e) {
            ex.printStackTrace(System.err);
        }
        return chaineLue;
    }
    
    public static Integer readInteger(String invite) {
        Integer valeurLue = null;
        while (valeurLue == null) {
            try {
                valeurLue = Integer.parseInt(readString(invite));
            } catch (NumberFormatException e) {
                System.out.println("/!\\ Input error - Integer expected /!\\");
            }
        }
        return valeurLue;
    }
    
    public static Integer readInteger(String invite, List<Integer> valeursPossibles) {
        Integer valeurLue = null;
        while (valeurLue == null) {
            try {
                valeurLue = Integer.parseInt(readString(invite));
            } catch (NumberFormatException e) {
                System.out.println("/!\\ Input error - Integer expected /!\\");
            }
            if (!(valeursPossibles.contains(valeurLue))) {
                System.out.println("/!\\ Input error - Unexpected value /!\\");
                valeurLue = null;
            }
        }
        return valeurLue;
    }
    
    public static void pause() {
        readString("-- PAUSE --");
    }
    
    public static void main(String[] args) {
        System.out.println("Hello!");
        
        String name = Input.readString("Type your name: ");
        System.out.println("Hi, " + name + "!");
        
        Integer age = Input.readInteger("Type your age: ");
        System.out.println("You are " + age + " years old.");
        
        Integer year = Input.readInteger("Type your IF department year number (3,4,5): ", Arrays.asList(3,4,5));
        System.out.println("You are in " + year + "IF.");
        
        Input.pause();
        
        System.out.println("Goodbye!");
    }
}
