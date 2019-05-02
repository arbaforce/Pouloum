/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vue;

import dao.JpaUtil;
import java.util.List;
import model.*;
import service.ServicesApp;
import util.Input;

public class testJoin {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        JpaUtil.init();
        
        try{
            User uSignIn;
            
            // Authentification employe
            System.out.println("MENU AUTHENTIFICATION CLIENT :");
            
            String mail = Input.readString("Veuillez entrer votre email : ");
            String mdp = Input.readString("Veuillez entrer votre mdp : ");
            uSignIn = ServicesApp.UserAuthenticate(mail, mdp);
            
            while (uSignIn == null) {
                mail = Input.readString("Veuillez entrer votre email : ");
                mdp = Input.readString("Veuillez entrer votre mdp : ");
                uSignIn = ServicesApp.UserAuthenticate(mail, mdp);
            }
            
            if (uSignIn != null) {
                System.out.println("Connecte en tant que : " + uSignIn.getLast_name() + " " + uSignIn.getFirst_name());
                
                List<Event> interJour = ServicesApp.GetEventsToday();
                if (interJour != null && !interJour.isEmpty()) {
                    System.out.println("Events du jour :");
                    for(Event inter : interJour){
                        System.out.println(inter.getId() + " " +inter.getClass().getSimpleName() +" "+ inter.getDescription());
                    }
                } else {
                    System.out.println("Pas encore d'interventions realisees aujourd'hui !");
                }
                
                /*
                if (uSignIn.getEventEnCours() != null) {
                    System.out.println("Event en cours :");
                    System.out.println("Type :" + uSignIn.getEventEnCours().getClass().getSimpleName());
                    System.out.println("Adresse :" + uSignIn.getEventEnCours().getClient().getAdressePostale());
                    System.out.println("Description :" + uSignIn.getEventEnCours().getDescription());
                    String typeInter = uSignIn.getEventEnCours().getClass().getSimpleName();
                    
                    Livraison intervLivr = (Livraison) uSignIn.getEventEnCours();
                    System.out.println("Objet :" + intervLivr.getObjet());
                    System.out.println("Prestataire :" + intervLivr.getPrestataire());
                    
                    int heure = Saisie.lireInteger("heure : ");
                    int minutes = Saisie.lireInteger("minutes : ");
                    String rapport = Saisie.lireChaine("rapport : ");
                    ServicesApp.ValiderEvent(uSignIn, heure, minutes, rapport);
                } else {
                    System.out.println("Pas d'interventions attribuee pour l'instant !");
                }
                */
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        JpaUtil.destroy();
    }
}
