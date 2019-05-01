package service;

import com.google.maps.model.LatLng;
import java.text.SimpleDateFormat;
import model.*;
import util.GeoTest;


public class ServicesTools {
    
    public static void simulateEmail( String recipient, String message )
    {
        System.out.println("EMAIL SENT TO " + recipient + ":");
        System.out.println("Your account has successfully been registered.");
    }
    
    public static void simulateEmailRegisterSuccess( User user )
    {
        String message = "Your account " + user.getNickname() + "has successfully been registered.";
        simulateEmail(user.getEmail(), message);
    }
    
    public static void simulateEmailRegisterFailure( String nickname, String email )
    {
        String message = "Your account " + nickname + " couldn't be registered.";
        simulateEmail(email, message);
    }
    
}
