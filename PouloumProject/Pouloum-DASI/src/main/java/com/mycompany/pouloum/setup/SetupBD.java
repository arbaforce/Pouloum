package com.mycompany.pouloum.setup;

import com.mycompany.pouloum.dao.*;
import com.mycompany.pouloum.model.*;
import com.mycompany.pouloum.service.ServicesActivity;
import com.mycompany.pouloum.service.ServicesEvent;
import com.mycompany.pouloum.service.ServicesPouloumer;
import com.mycompany.pouloum.util.DateUtil;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import static java.lang.Thread.sleep;
import java.net.URL;
import java.nio.file.Files;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 *
 * @author nmesnard
 */
public class SetupBD {
    
    protected static BufferedReader getResourceReader(String resourceName) {
        InputStream resourceStream = ClassLoader.getSystemClassLoader().getResourceAsStream(resourceName);
        BufferedReader resReader = new BufferedReader(new InputStreamReader(resourceStream));
        return resReader;
    }
    
    public static void main(String[] args) {
        JpaUtil.init();
        
        try {
            setupActivities();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        try {
            setupPouloumer();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        try {
            setupEvents();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        JpaUtil.destroy();
    }
    
    protected static void setupActivities()
        throws IOException
    {
        BufferedReader reader = getResourceReader("setup/Activities.txt");
        
        Stack<Activity> tree = new Stack<>();
        
        try {
            for (String line = reader.readLine(); line != null; line = reader.readLine()) {
                if ((line + "/").charAt(0) == '/') continue;
                
                int level = 0;
                for (String padding = line + " "; line.charAt(level) == '\t'; level++);
                while (tree.size() > level) tree.pop();
                
                Activity parent = tree.isEmpty() ? null : tree.peek();
                List<Activity> emptylist = new ArrayList<>();
                String name = line.trim();
                String description = "";
                List<Badge> badges = new ArrayList<>();
                String rules = "";
                List<String> resources = new ArrayList<>();
                int default_min = 0;
                int default_max = 0;
                
                try {
                    Activity cur = ServicesActivity.createActivity(parent, emptylist, name, description, badges, rules, resources, default_min, default_max);
                    
                    if (parent != null) {
                        try {
                            ServicesActivity.addChildActivity(parent, cur);
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                    
                    tree.push(cur);
                } catch (Exception ex) {
                    ex.printStackTrace();
                    tree.push(null);
                }
                
            }
        } finally {
            reader.close();
        }
    }
    
    protected static void setupPouloumer()
        throws ParseException
    {
        List<Pouloumer> users = new ArrayList<>();
        
        Date d1 = DateUtil.toDate("16/08/1984");
        Address a1 = new Address("6", "Rue Camille Koechlin", "Villeurbanne", "69100", "France");
        Pouloumer u1 = new Pouloumer("Momo", "Moez", "WOAGNER", "moez.woagner@hotmail.fr", "mdpmw", false, false, 'M', d1,"0832205629", a1);
        users.add(u1);
        
        Date d2 = DateUtil.toDate("17/02/1996");
        Address a2 = new Address("9", "Impasse Guillet", "Villeurbanne", "69100", "France");
        Pouloumer u2 = new Pouloumer("Matty", "Matteo", "HONRY", "matteo.honry@hotmail.fr", "mdpmh", false, false, 'M', d2,"0482381862", a1);
        users.add(u2);
        
        Date d3 = DateUtil.toDate("16/02/1982");
        Address a3 = new Address("20", "Rue Decomberousse", "Villeurbanne", "69100", "France");
        Pouloumer u3 = new Pouloumer("Keke", "Kevin", "CECCANI", "kevin.ceccani@hotmail.fr", "mdpkc", false, false, 'M', d3,"0664426037", a3);
        users.add(u3);
        
        Date d4 = DateUtil.toDate("13/08/1988");
        Address a4 = new Address("1", "Rue d'Alsace", "Villeurbanne", "69100", "France");
        Pouloumer u4 = new Pouloumer("Valice", "Alice", "VOYRET", "alice.voyret@hotmail.fr", "mdpav", false, false, 'F', d4,"0486856520", a4);
        users.add(u4);
        
        Date d5 = DateUtil.toDate("16/05/1989");
        Address a5 = new Address("4", "Rue de la Jeunesse", "Villeurbanne", "69100", "France");
        Pouloumer u5 = new Pouloumer("Juju", "Julien", "RINERD", "jrinerd5241@hotmail.fr", "mdpjr", false, false, 'M', d5,"0727252485", a5);
        users.add(u5);
        
        Date d6 = DateUtil.toDate("24/05/1983");
        Address a6 = new Address("7", "Rue de la Cloche", "Villeurbanne", "69100", "France");
        Pouloumer u6 = new Pouloumer("Olive", "Olivier", "WOSTPHOL", "owostphol@hotmail.fr", "mdpow", false, false, 'M', d6,"0860680312", a6);
        users.add(u6);
        
        JpaUtil.createEntityManager();
        
        try {
            for (Pouloumer u : users) {
                try {
                    Pouloumer check;
                    check = DAOPouloumer.findPouloumerByEmail(u.getEmail());
                    if (check != null) continue;
                    check = DAOPouloumer.findPouloumerByNickname(u.getNickname());
                    if (check != null) continue;
                    
                    JpaUtil.openTransaction();
                    
                    try {
                        DAOPouloumer.persist(u);
                        
                        JpaUtil.commitTransaction();
                    } catch (Exception ex) {
                        JpaUtil.cancelTransaction();
                        throw ex;
                    }
                    
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            
        } finally {
            JpaUtil.closeEntityManager();
        }
    }
    
    protected static void setupEvents()
        throws Exception
    {
        List<Activity> activities = ServicesActivity.findAllActivities();
        List<Event> events = new ArrayList<Event>();
        
        Pouloumer p1 = ServicesPouloumer.getPouloumerByNickname("Momo");
        Address a1 = p1.getAddress();
        Date d1 = new Date();
        Event e1 = new Event("1er evenement", "such description", d1, false, 30, a1, activities.get(0), p1, 1, 10);
        Event e2 = new Event("2er evenement", "wow description", d1, false, 60, a1, activities.get(1), p1, 3000, 9000);
        Event e3 = new Event("3eme evenement", "damn description", d1, false, 80, a1, activities.get(2), p1, 2, 4);
        
        Pouloumer p2 = ServicesPouloumer.getPouloumerByNickname("Keke");
        Address a2 = p1.getAddress();
        Date d2 = new Date();
        Event e4 = new Event("my keke", "such kek", d2, false, 70, a2, activities.get(4), p2, 1, 10);
        Event e5 = new Event("your keke", "dat kek", d2, false, 40, a2, activities.get(5), p2, 0, 15);
        
        Pouloumer p3 = ServicesPouloumer.getPouloumerByNickname("Matty");
        Address a3 = p1.getAddress();
        Date d3 = new Date();
        Event e6 = new Event("hehehe", "useless information is useless", d3, false, 150, a3, activities.get(6), p3, 10, 12);

        events.add(e1);
        events.add(e2);
        events.add(e3);
        events.add(e4);
        events.add(e5);
        events.add(e6);
        
        Pouloumer p4 = ServicesPouloumer.getPouloumerByNickname("Valice");
        Pouloumer p5 = ServicesPouloumer.getPouloumerByNickname("Juju");
        Pouloumer p6 = ServicesPouloumer.getPouloumerByNickname("Olive");        
                
        JpaUtil.createEntityManager();
        
        try {
            for (Event e : events) {
                try {
                    
                    JpaUtil.openTransaction();
                    
                    try {
                        DAOEvent.persist(e);
                        
                        JpaUtil.commitTransaction();
                    } catch (Exception ex) {
                        JpaUtil.cancelTransaction();
                        throw ex;
                    }
                    
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            
        } finally {
            JpaUtil.closeEntityManager();
        }
        
        ServicesEvent.addParticipant(e1, p2);
        ServicesEvent.addParticipant(e1, p3);
        ServicesEvent.addParticipant(e1, p4);
        ServicesEvent.addParticipant(e1, p5);
        
        ServicesEvent.addParticipant(e2, p1);
        ServicesEvent.addParticipant(e2, p2);
        ServicesEvent.addParticipant(e2, p6);
        
        ServicesEvent.addParticipant(e3, p1);
        ServicesEvent.addParticipant(e3, p2);
        ServicesEvent.addParticipant(e3, p3);
        ServicesEvent.addParticipant(e3, p4);
        ServicesEvent.addParticipant(e3, p5);
        ServicesEvent.addParticipant(e3, p6);
        
        ServicesEvent.addParticipant(e6, p4);
        ServicesEvent.addParticipant(e6, p5);
        ServicesEvent.addParticipant(e6, p6);
    }
    
}
