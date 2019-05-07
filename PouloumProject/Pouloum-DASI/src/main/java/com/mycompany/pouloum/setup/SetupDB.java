package com.mycompany.pouloum.setup;

import com.mycompany.pouloum.dao.*;
import com.mycompany.pouloum.model.*;
import com.mycompany.pouloum.service.ServicesActivity;
import com.mycompany.pouloum.service.ServicesEvent;
import com.mycompany.pouloum.service.ServicesPouloumer;
import com.mycompany.pouloum.util.CRE;
import static com.mycompany.pouloum.util.CRE.*;
import com.mycompany.pouloum.util.DateUtil;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.fluttercode.datafactory.impl.DataFactory;

/**
 *
 * @author nmesnard
 */
public class SetupDB {

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
            throws IOException {
        BufferedReader reader = getResourceReader("setup/Activities.txt");

        Stack<Activity> tree = new Stack<>();

        try {
            for (String line = reader.readLine(); line != null; line = reader.readLine()) {
                if ((line + "/").charAt(0) == '/') {
                    continue;
                }

                int level = 0;
                for (String padding = line + " "; line.charAt(level) == '\t'; level++);
                while (tree.size() > level) {
                    tree.pop();
                }

                Activity parent = tree.isEmpty() ? null : tree.peek();
                String name = line.trim();
                String description = "";
                List<Badge> badges = new ArrayList<>();
                String rules = "";
                List<String> resources = new ArrayList<>();
                int default_min = 0;
                int default_max = 0;

                try {
                    Activity cur = ServicesActivity.createActivity(parent, name, description, badges, rules, resources, default_min, default_max);

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
            throws ParseException {
        List<Pouloumer> users = new ArrayList<>();
        DataFactory df = new DataFactory();
        Random rand = new Random();
        
        for (int i = 0; i<200; i++) {
            Address address = null;

            String p_nickname = df.getRandomWord();
            String p_password = df.getRandomWord(8, 14);
            try {
                CRE created = ServicesPouloumer.signUp(df.getLastName(), df.getFirstName(), p_nickname, 
                    df.getRandomWord() + "@" + df.getRandomWord() + "." + df.getRandomWord(2,3),
                    p_password, false, false, '?', df.getBirthDate(),
                    "06"+df.getNumberText(6),address);
                
                if (created == CRE_OK) {
                    Pouloumer p = ServicesPouloumer.getPouloumerByNickname(p_nickname);
                    
                    int nbInterets = rand.nextInt(6)+3;
                    List<Long> idInterets = new ArrayList<>();
                    while (idInterets.size() < nbInterets) {
                        Long id = new Long(rand.nextInt(100) + 1);
                        try {
                            Activity a = ServicesActivity.getActivityById(id);
                            if (!idInterets.contains(id)) idInterets.add(a.getId());
                        } catch (Exception ex) {
                        }
                    }
                }
            } catch (Exception ex) {
            }
        }
    }
    
    protected static void setupEvents()
            throws Exception {
        List<Activity> activities = ServicesActivity.findAllActivities();
        List<Event> events = new ArrayList<Event>();
        
        
        List<Pouloumer> users = new ArrayList<>();
        
        Date d1 = DateUtil.toDate("16/08/1984");
        Address a1 = new Address("6", "Rue Camille Koechlin", "Villeurbanne", "69100", "France");
        Pouloumer p_Momo = new Pouloumer("Momo", "Moez", "WOAGNER", "moez.woagner@hotmail.fr", "mdpmw", false, false, 'M', d1,"0832205629", a1);
        users.add(p_Momo);
        
        Date d2 = DateUtil.toDate("17/02/1996");
        Address a2 = new Address("9", "Impasse Guillet", "Villeurbanne", "69100", "France");
        Pouloumer p_Matty = new Pouloumer("Matty", "Matteo", "HONRY", "matteo.honry@hotmail.fr", "mdpmh", false, false, 'M', d2,"0482381862", a1);
        users.add(p_Matty);
        
        Date d3 = DateUtil.toDate("16/02/1982");
        Address a3 = new Address("20", "Rue Decomberousse", "Villeurbanne", "69100", "France");
        Pouloumer p_Keke = new Pouloumer("Keke", "Kevin", "CECCANI", "kevin.ceccani@hotmail.fr", "mdpkc", false, false, 'M', d3,"0664426037", a3);
        users.add(p_Keke);
        
        Date d4 = DateUtil.toDate("13/08/1988");
        Address a4 = new Address("1", "Rue d'Alsace", "Villeurbanne", "69100", "France");
        Pouloumer p_Valice = new Pouloumer("Valice", "Alice", "VOYRET", "alice.voyret@hotmail.fr", "mdpav", false, false, 'F', d4,"0486856520", a4);
        users.add(p_Valice);
        
        Date d5 = DateUtil.toDate("16/05/1989");
        Address a5 = new Address("4", "Rue de la Jeunesse", "Villeurbanne", "69100", "France");
        Pouloumer p_Juju = new Pouloumer("Juju", "Julien", "RINERD", "jrinerd5241@hotmail.fr", "mdpjr", false, false, 'M', d5,"0727252485", a5);
        users.add(p_Juju);
        
        Date d6 = DateUtil.toDate("24/05/1983");
        Address a6 = new Address("7", "Rue de la Cloche", "Villeurbanne", "69100", "France");
        Pouloumer p_Olive = new Pouloumer("Olive", "Olivier", "WOSTPHOL", "owostphol@hotmail.fr", "mdpow", false, false, 'M', d6,"0860680312", a6);
        users.add(p_Olive);
        
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
        
        Pouloumer p1 = p_Momo;
        Date d01 = new Date();
        Event e1 = new Event("1er evenement", "such description", d01, false, 30, a1, activities.get(0), p1, 1, 10);
        Event e2 = new Event("2er evenement", "wow description", d01, false, 60, a1, activities.get(1), p1, 3000, 9000);
        Event e3 = new Event("3eme evenement", "damn description", d01, false, 80, a1, activities.get(2), p1, 2, 4);

        Pouloumer p2 = p_Keke;
        Date d02 = new Date();
        Event e4 = new Event("my keke", "such kek", d02, false, 70, a2, activities.get(4), p2, 1, 10);
        Event e5 = new Event("your keke", "dat kek", d02, false, 40, a2, activities.get(5), p2, 0, 15);

        Pouloumer p3 = p_Matty;
        Date d03 = new Date();
        Event e6 = new Event("hehehe", "useless information is useless", d03, false, 150, a3, activities.get(6), p3, 10, 12);

        events.add(e1);
        events.add(e2);
        events.add(e3);
        events.add(e4);
        events.add(e5);
        events.add(e6);

        Pouloumer p4 = p_Valice;
        Pouloumer p5 = p_Juju;
        Pouloumer p6 = p_Olive;
        
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
