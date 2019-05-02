package vue;

import dao.JpaUtil;
import model.*;
import service.ServicesApp;
import util.Input;

public class testOrganize {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        JpaUtil.init();
        
        try{
            User uSignUp = null;
            User cSignIn = null;
            
            // Inscription client
            System.out.println("MENU INSCRIPTION :");
            String pseudo = Input.readString("Veuillez entrer votre pseudo : ");
            String nom = Input.readString("Veuillez entrer votre nom : ");
            String prenom = Input.readString("Veuillez entrer votre prenom : ");
            char civilite = (Input.readString("Veuillez entrer votre civilite : ") + "?").charAt(0);
            String dateDeNaissance = Input.readString("Veuillez entrer votre date de naissance (jj/mm/aaaa) : ");
            String tel = Input.readString("Veuillez entrer votre telephone : ");
            String adressePostale = Input.readString("Veuillez entrer votre adresse postale : ");
            String mail = Input.readString("Veuillez entrer votre email : ");
            String mdp = Input.readString("Veuillez entrer votre mot de passe : ");
            
            Address adresse = new Address("", adressePostale, "", "", "");
            uSignUp = new User(pseudo,prenom,nom,mail,mdp,false,false,civilite,dateDeNaissance,tel,adresse);
            ServicesApp.UserRegister(uSignUp);
            uSignUp = null;
            
            // Authentification client
            System.out.println("MENU AUTHENTIFICATION CLIENT :");
            mail = Input.readString("Veuillez entrer votre email : ");
            mdp = Input.readString("Veuillez entrer votre mdp : ");
            cSignIn = ServicesApp.UserAuthenticate(mail, mdp);
            
            while (cSignIn == null) {
                mail = Input.readString("Veuillez entrer votre email : ");
                mdp = Input.readString("Veuillez entrer votre mdp : ");
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
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        JpaUtil.destroy();
    }
}
