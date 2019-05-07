package com.mycompany.pouloum.service;

import com.mycompany.pouloum.model.*;

public class ServicesTools {

    public static void simulateMessage(String type, String recipient, String message) {
        System.out.println(type + " SENT TO " + recipient + ":");
        System.out.println(message);
    }

    public static void simulateEmail(String recipient, String message) {
        simulateMessage("EMAIL", recipient, message);
    }

    public static void simulateSMS(String recipient, String message) {
        simulateMessage("SMS", recipient, message);
    }

    public static void simulateEmailRegisterSuccess(Pouloumer user) {
        String message = "Your account " + user.getNickname() + " has successfully been registered.";
        simulateEmail(user.getEmail(), message);
    }

    public static void simulateEmailRegisterFailure(String nickname, String email) {
        String message = "Your account " + nickname + " couldn't be registered.";
        simulateEmail(email, message);
    }

    public static void simulateSMSCreationSuccess(Pouloumer user, Event event) {
        String message = "Your event " + event.getLabel() + " has successfully been created.";
        simulateSMS(user.getEmail(), message);
    }

    public static void simulateSMSCreationFailure(Pouloumer user, String label) {
        String message = "Your event " + label + " couldn't be created.";
        simulateSMS(user.getEmail(), message);
    }

    public static void simulateSMSJoinSuccess(Pouloumer user, Event event) {
        String message = "You have successfully joined the event " + event.getLabel() + ".";
        simulateSMS(user.getEmail(), message);
    }

    public static void simulateSMSJoinFailure(Pouloumer user, Event event) {
        String message = "You couldn't have been added to the event " + event.getLabel() + ".";
        simulateSMS(user.getEmail(), message);
    }

}
