package service;

import com.google.maps.model.LatLng;
import dao.DAOEvent;
import dao.DAOUser;
import dao.JpaUtil;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import model.*;
import util.GeoTest;

public class ServicesApp {
    
    public static void UserRegister(User user) {
        try {
            JpaUtil.creerEntityManager();
            
            User check = DAOUser.findUserByEmail(user.getEmail());
            if(check != null) {
                // email already used
                return;
            }
            // email available
            
            JpaUtil.ouvrirTransaction();
            
            try {
                DAOUser.persist(user);
                JpaUtil.validerTransaction();
                ServicesTools.simulateEmailRegisterSuccess(user);
                
            } catch (Exception e) {
                JpaUtil.annulerTransaction();
                ServicesTools.simulateEmailRegisterFailure(user.getFirst_name(), user.getEmail());
                throw e;
            }
            
            JpaUtil.fermerEntityManager();
        
        } catch(Exception e) {
            e.printStackTrace();
        }
       
        //return signUpUser;
    }
    
    public static User UserAuthenticate( String email, String password ) {
        User authenticated = null;
        
        try {
            JpaUtil.creerEntityManager();
            
            authenticated = DAOUser.findUserByEmailAndPassword(email, password);
            
            JpaUtil.fermerEntityManager();
        } catch (Exception e) {
            // e.printStackTrace();
        }
        
        return authenticated;
    }
    
    /*
    public static User DemandeEvent(Event i){
        
        User employeChoisi = null;
        
        try{
            
            JpaUtil.creerEntityManager();
            
            SimpleDateFormat h = new SimpleDateFormat("HH:mm");
            String heureMinuteEvent=h.format(i.getDateDeDebut());
            String [] infosdate=(heureMinuteEvent.split(":"));
            
            int heureDebut=Integer.parseInt(infosdate[0]);
            
            //System.out.println(heuredebutGMT);
            List<User> employesDispos=DAOUser.findUsersDispos(heureDebut);
            double meilleurTemps=9999999999999999.999;
            for(User employeIter : employesDispos){
                double tempsTempo=GeoTest.getTripDurationByBicycleInMinute(employeIter.getCoordGPSDomicile(),i.getUser().getCoordGPSDomicile());
                if(tempsTempo<=meilleurTemps){
                    meilleurTemps=tempsTempo;
                    employeChoisi=employeIter;
                }
            }
            if(employeChoisi!=null){
                                
                JpaUtil.ouvrirTransaction();
                
                try{
                    
                    //modifier la disponibilte à false de l'employe via update dans la BD
                    employeChoisi.setDisponibilite(false);
                    //affecter l'intervention en cours à l'employe
                    employeChoisi.setEventEnCours(i);
                    employeChoisi=DAOUser.updateUser(employeChoisi);
                    
                    
                    //affecter l'intervention à la liste du user
                    i.getUser().getEventsDemandees().add(i);
                    DAOUser.updateUser(i.getUser());
                    
                    //on fait persister l'intervention
                    DAOEvent.persist(i);
                    
                    JpaUtil.validerTransaction();
                }catch(Exception e){
                    JpaUtil.annulerTransaction();
                    employeChoisi = null; // CHANGE : AJOUTE !!
                    throw e;
                }
                               
                
                //envoyer notification SMS
                System.out.print("SMS envoye a l'employe :");
                ServicesTools.simulationSmsUser(employeChoisi);
                
                System.out.println("L'intervention ("+i.getClass().getSimpleName()+") a ete attribuee à " + employeChoisi.getPrenom() + ".\n"
                    + "Elle sera realisee sous peu. Vous recevrez un sms bilan.");
            }
            else{
                System.out.println("Aucun employe disponible actuellement.\n"
                        + "Veuillez reesayer dans quelque minutes.");
            }

            
            JpaUtil.fermerEntityManager();
            
        }catch(Exception e){
            e.printStackTrace();
        }
        return employeChoisi;
    }
    */
    
    /*
    public static boolean ValiderEvent(User emp, int hour, int minutes, String rapport)
    {            

        
        boolean res=false;
        
        Date dateFin = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(dateFin);
        cal.set(Calendar.HOUR_OF_DAY, hour);
        cal.set(Calendar.MINUTE, minutes);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        dateFin = cal.getTime();
        
        Event itempo = emp.getEventEnCours(); 
        
        try{
            
            JpaUtil.creerEntityManager();
            
            
            JpaUtil.ouvrirTransaction();
            
            try{
                
                // update employe
                emp.getEventsRealisees().add(itempo);
                emp.setEventEnCours(null);
                emp.setDisponibilite(true);
               
                emp = DAOUser.updateUser(emp);
                 System.out.println(emp);
                // update objet intervention
                itempo.setRapport(rapport); // CHANGE !!
                itempo.setTerminee(true);
                itempo.setDateDeFin(dateFin);
                
                DAOEvent.updateEvent(itempo);
                
                res=true;
                JpaUtil.validerTransaction();
                
            }catch(Exception e){
                System.out.println("test");
                JpaUtil.annulerTransaction();
                throw e;
            }
            
            JpaUtil.fermerEntityManager();
            
            System.out.print("SMS envoye au user :");
            ServicesTools.simulationSmsUser(itempo);
                
            System.out.println("L'intervention ("+itempo.getClass().getSimpleName()+") est cloturee.\n");
            
            
            
        }catch(Exception e){
            e.printStackTrace();
        }
        
        return res;
    }
    */
    
    public static List<Event> GetEventsToday(){
        List<Event> result = new ArrayList<>();
        
        try {
            JpaUtil.creerEntityManager();
            
            Date todaydate = new Date();
            SimpleDateFormat sf = new SimpleDateFormat("dd/MM/yyyy");
            String today= sf.format(todaydate);
            
            List<Event> allInter = DAOEvent.findAll();
            if (allInter!=null && !allInter.isEmpty()) {
                for (Event intIter : allInter) {
                    String dateTempo= sf.format(intIter.getStart());  
                    if (dateTempo.equals(today)) {
                        result.add(intIter);
                    }
                }
            }
            
            JpaUtil.fermerEntityManager();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return result;
    }
    
    
    
    public static void CreateSampleUsers(){
        try {
            JpaUtil.creerEntityManager();
            
            List<User> users = new ArrayList<>();
            
            Address a1 = new Address("6", "Camille Koechlin", "Villeurbanne", "69100", "France");
            User u1 = new User("Momo", "Moez", "WOAGNER", "moez.woagner@proactif.com", "mdpmw", false, false, 'M', "16/08/1984","0832205629", a1);
            users.add(u1);
            
            Address a2 = new Address("9", "Impasse Guillet", "Villeurbanne", "69100", "France");
            User u2 = new User("Matty", "Matteo", "HONRY", "matteo.honry@proactif.com", "mdpmh", false, false, 'M', "17/02/1996","0482381862", a1);
            users.add(u2);
            
            Address a3 = new Address("20", "Rue Decomberousse", "Villeurbanne", "69100", "France");
            User u3 = new User("Keke", "Kevin", "CECCANI", "kevin.ceccani@proactif.com", "mdpkc", false, false, 'M', "16/02/1982","0664426037", a3);
            users.add(u3);
            
            Address a4 = new Address("1", "Rue d'Alsace", "Villeurbanne", "69100", "France");
            User u4 = new User("Valice", "Alice", "VOYRET", "alice.voyret@proactif.com", "mdpav", false, false, 'F', "13/08/1988","0486856520", a4);
            users.add(u4);
            
            Address a5 = new Address("4", "Rue de la Jeunesse", "Villeurbanne", "69100", "France");
            User u5 = new User("Juju", "Julien", "RINERD", "jrinerd5241@proactif.com", "mdpjr", false, false, 'M', "16/05/1989","0727252485", a5);
            users.add(u5);
            
            Address a6 = new Address("7", "Rue de la Cloche", "Villeurbanne", "69100", "France");
            User u6 = new User("Olive", "Olivier", "WOSTPHOL", "owostphol@proactif.com", "mdpow", false, false, 'M', "24/05/1983","0860680312", a6);
            users.add(u6);            
            
            for (User u : users) {
                try {
                    UserRegister(u);
                } catch (Exception e) {
                    // e.printStackTrace();
                }
            }
            
            JpaUtil.fermerEntityManager();
        
        } catch (Exception e) {
            e.printStackTrace();
        }     
    }
    
}
