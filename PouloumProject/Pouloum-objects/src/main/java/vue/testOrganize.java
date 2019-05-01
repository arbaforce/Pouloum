package vue;

import dao.JpaUtil;
import model.*;
import service.ServicesApp;
import util.Saisie;

public class testOrganize {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        JpaUtil.init();
        
        try{
            User uSignUp = null ,cSignIn = null;
            
            // Inscription client
            System.out.println("MENU INSCRIPTION :");
            String pseudo = Saisie.lireChaine("Veuillez entrer votre pseudo : ");
            String nom = Saisie.lireChaine("Veuillez entrer votre nom : ");
            String prenom = Saisie.lireChaine("Veuillez entrer votre prenom : ");
            char civilite = (Saisie.lireChaine("Veuillez entrer votre civilite : ") + "?").charAt(0);
            String dateDeNaissance = Saisie.lireChaine("Veuillez entrer votre date de naissance (jj/mm/aaaa) : ");
            String tel = Saisie.lireChaine("Veuillez entrer votre telephone : ");
            String adressePostale = Saisie.lireChaine("Veuillez entrer votre adresse postale : ");
            String mail = Saisie.lireChaine("Veuillez entrer votre email : ");
            String mdp = Saisie.lireChaine("Veuillez entrer votre mot de passe : ");
            
            Address adresse = new Address("", adressePostale, "", "", "");
            uSignUp = new User(pseudo,prenom,nom,mail,mdp,false,false,civilite,dateDeNaissance,tel,adresse);
            ServicesApp.UserRegister(uSignUp);
            uSignUp = null;
            
            // Authentification client
            System.out.println("MENU AUTHENTIFICATION CLIENT :");
            mail = Saisie.lireChaine("Veuillez entrer votre email : ");
            mdp = Saisie.lireChaine("Veuillez entrer votre mdp : ");
            cSignIn = ServicesApp.UserAuthenticate(mail, mdp);
            
            while (cSignIn == null) {
                mail = Saisie.lireChaine("Veuillez entrer votre email : ");
                mdp = Saisie.lireChaine("Veuillez entrer votre mdp : ");
                cSignIn = ServicesApp.UserAuthenticate(mail, mdp);
            }
            
            if (cSignIn != null) {
                /*
                System.out.println("Connecte en tant que : " +cSignIn.getNom() + " " + cSignIn.getPrenom());
                
                String desc = Saisie.lireChaine("description : ");
                Event i = new Event(cSignIn,desc);
                ServicesApp.EventOrganize(i);
                */
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        JpaUtil.destroy();
    }
}
