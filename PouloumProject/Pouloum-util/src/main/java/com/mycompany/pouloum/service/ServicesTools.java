package com.mycompany.pouloum.service;

import com.google.maps.model.LatLng;
import java.text.SimpleDateFormat;
import com.mycompany.pouloum.model.*;
import com.mycompany.pouloum.util.GeoTest;


public class ServicesTools {
    
    public static void simulateEmail( String recipient, String message )
    {
        System.out.println("EMAIL SENT TO " + recipient + ":");
        System.out.println(message);
    }
    public static void simulateSMS( String recipient, String message )
    {
        System.out.println("SMS SENT TO " + recipient + ":");
        System.out.println(message);
    }
    
    public static void simulateEmailRegisterSuccess( User user )
    {
        String message = "Your account " + user.getNickname() + " has successfully been registered.";
        simulateEmail(user.getEmail(), message);
    }
    public static void simulateEmailRegisterFailure( String nickname, String email )
    {
        String message = "Your account " + nickname + " couldn't be registered.";
        simulateEmail(email, message);
    }
    
    public static void simulateSMSCreationSuccess( User user, Event event )
    {
        String message = "Your event " + event.getLabel() + " has successfully been created.";
        simulateSMS(user.getEmail(), message);
    }
    public static void simulateSMSCreationFailure( User user, String label )
    {
        String message = "Your event " + label + " couldn't be created.";
        simulateSMS(user.getEmail(), message);
    }
    
    public static void simulateSMSJoinSuccess( User user, Event event )
    {
        String message = "You have successfully joined the event " + event.getLabel() + ".";
        simulateSMS(user.getEmail(), message);
    }
    public static void simulateSMSJoinFailure( User user, Event event )
    {
        String message = "You couldn't have been added to the event " + event.getLabel() + ".";
        simulateSMS(user.getEmail(), message);
    }
    
}
