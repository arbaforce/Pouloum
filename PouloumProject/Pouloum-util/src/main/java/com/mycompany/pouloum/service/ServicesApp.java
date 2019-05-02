package com.mycompany.pouloum.service;

import com.google.maps.model.LatLng;
import com.mycompany.pouloum.dao.DAOEvent;
import com.mycompany.pouloum.dao.DAOUser;
import com.mycompany.pouloum.dao.JpaUtil;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import com.mycompany.pouloum.model.*;
import com.mycompany.pouloum.util.GeoTest;

public class ServicesApp {
    
    public static boolean UserRegister(User user) {
        boolean result = false;
        
        try {
            JpaUtil.createEntityManager();
            
            {
            User check = DAOUser.findUserByEmail(user.getEmail());
            if (check != null)
                // email already used
                return false;
            }
            // email available
            
            {
            User check = DAOUser.findUserByNickname(user.getNickname());
            if (check != null)
                // nickname already used
                return false;
            }
            // nickname available
            
            JpaUtil.openTransaction();
            
            try {
                DAOUser.persist(user);
                JpaUtil.commitTransaction();
                ServicesTools.simulateEmailRegisterSuccess(user);
                
                result = true;
            } catch (Exception ex) {
                JpaUtil.cancelTransaction();
                ServicesTools.simulateEmailRegisterFailure(user.getFirst_name(), user.getEmail());
                throw ex;
            }
            
            JpaUtil.closeEntityManager();
        
        } catch(Exception e) {
            e.printStackTrace();
        }
       
        return result;
    }
    
    public static User UserAuthenticate( String email, String password ) {
        User authenticated = null;
        
        try {
            JpaUtil.createEntityManager();
            
            authenticated = DAOUser.findUserByEmailAndPassword(email, password);
            
            JpaUtil.closeEntityManager();
        } catch (Exception ex) {
            // e.printStackTrace();
        }
        
        return authenticated;
    }
    
    public static void CreateEvent( User u, Event i ) {
        try {
            JpaUtil.createEntityManager();
            
            JpaUtil.openTransaction();
            
            
            List<Event> is = u.getEvents();
            is.add(i);
            u.setEvents(is);
            u = DAOUser.updateUser(u);
            
            i.setOrganizer(u);
            DAOEvent.persist(i);
            
            try {
                JpaUtil.commitTransaction();
            } catch (Exception ex) {
                JpaUtil.cancelTransaction();
                throw ex;
            }
            
            ServicesTools.simulateSMSCreationSuccess(u, i);
            
            JpaUtil.closeEntityManager();
        } catch (Exception ex) {
            ServicesTools.simulateSMSCreationFailure(u, i.getLabel());
            
            ex.printStackTrace();
        }
    }
    
    public static boolean JoinEvent( User u, Event i )
    {
        List<User> participants = i.getParticipants();
        
        if (participants.contains(u)) {
            return false;
        }
        
        if (i.getParticipants_num() >= i.getParticipants_max()) {
            return false;
        }
        
        boolean success = false;
        
        try {
            JpaUtil.createEntityManager();
            
            JpaUtil.openTransaction();
            
            try {
                List<Event> events = u.getEvents();
                events.add(i);
                u.setEvents(events);
                u = DAOUser.updateUser(u);
                
                participants.add(u);
                i.setParticipants(participants);
                DAOEvent.updateEvent(i);
                
                JpaUtil.commitTransaction();
                
                success = true;
            } catch (Exception ex) {
                JpaUtil.cancelTransaction();
                throw ex;
            }
            
            JpaUtil.closeEntityManager();
            
            if (success) {
                ServicesTools.simulateSMSJoinSuccess(u, i);
            } else {
                ServicesTools.simulateSMSJoinFailure(u, i);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        return success;
    }
    
    public static List<Event> GetEventsToday(){
        List<Event> result = new ArrayList<>();
        
        try {
            JpaUtil.createEntityManager();
            
            Date todaydate = new Date();
            SimpleDateFormat sf = new SimpleDateFormat("dd/MM/yyyy");
            String today = sf.format(todaydate);
            
            List<Event> allInter = DAOEvent.findAll();
            if (allInter!=null && !allInter.isEmpty()) {
                for (Event intIter : allInter) {
                    String dateTempo= sf.format(intIter.getStart());
                    if (dateTempo.equals(today)) {
                        result.add(intIter);
                    }
                }
            }
            
            JpaUtil.closeEntityManager();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        return result;
    }
    
    
    
    public static void CreateSampleUsers(){
        try {
            JpaUtil.createEntityManager();
            
            List<User> users = new ArrayList<>();
            
            Address a1 = new Address("6", "Rue Camille Koechlin", "Villeurbanne", "69100", "France");
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
                } catch (Exception ex) {
                    // e.printStackTrace();
                }
            }
            
            JpaUtil.closeEntityManager();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
}
