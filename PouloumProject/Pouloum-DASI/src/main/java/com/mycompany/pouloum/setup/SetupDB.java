package com.mycompany.pouloum.setup;

import com.mycompany.pouloum.dao.*;
import com.mycompany.pouloum.model.*;
import com.mycompany.pouloum.service.ServicesActivity;
import com.mycompany.pouloum.service.ServicesAddress;
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
            setupAddress();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        try {
            setupPouloumers();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        try {
            setupPouloumersEvents();
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
                
                String text = line + " ";
                
                int level = 0;
                for (String padding = text; line.charAt(level) == '\t'; level++);
                while (tree.size() > level) {
                    tree.pop();
                }
                
                String data = "";
                
                int pipe;
                for (pipe = 0; pipe < text.length(); pipe++) if (text.charAt(pipe) == '|') break;
                if (pipe < text.length()) {
                    data = text.substring(pipe+1);
                    text = text.substring(0, pipe);
                }
                
                Activity parent = tree.isEmpty() ? null : tree.peek();
                String name = text.trim();
                String description = "";
                List<Badge> badges = new ArrayList<>();
                
                // APPROPRIATE BADGES
                if (!data.isEmpty()) {
                    if (data.contains("E")) badges.add(Badge.EARTH);
                    if (data.contains("F")) badges.add(Badge.FIRE);
                    if (data.contains("W")) badges.add(Badge.WATER);
                    if (data.contains("A")) badges.add(Badge.WIND);
                }
                
                if (badges.isEmpty()) {
                    if (parent != null) {
                        badges.addAll(parent.getBadges());
                    } else {
                
                /*
                // RANDOM BADGES
                Random rand = new Random(); // random badges
                int badgeEarth = rand.nextInt(4);
                if (badgeEarth == 1) badges.add(Badge.EARTH);
                int badgeFire = rand.nextInt(4);
                if (badgeFire == 1) badges.add(Badge.FIRE);
                int badgeWater = rand.nextInt(4);
                if (badgeWater == 1) badges.add(Badge.WATER);
                int badgeWind = rand.nextInt(4);
                if (badgeWind == 1) badges.add(Badge.WIND);
                
                if (badges.isEmpty()) {
                    int badgeDef = rand.nextInt(Badge.values().length);
                    badges.add(Badge.values()[badgeDef]);
                }
                */
                
                    }
                }
                
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
    
    protected static void setupAddress() throws IOException{
        BufferedReader reader = getResourceReader("setup/Address.txt");

        ArrayList<Address> addresses = new ArrayList<>();

        try {
            for (String line = reader.readLine(); line != null; line = reader.readLine()) {
                String[] parts = line.split(",");
                if (parts.length == 5) {
                    Address a = new Address(parts[0], parts[1], parts[2], parts[3], parts[4]);
                    addresses.add(a);
                }
            }
        } finally {
            reader.close();
        }
        
        JpaUtil.createEntityManager();

        try {
            for (Address a : addresses) {
                try {
                    JpaUtil.openTransaction();

                    try {
                        DAOAddress.persist(a);

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

    protected static void setupPouloumers()
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
    
    protected static void setupPouloumersEvents()
            throws Exception {
        List<Pouloumer> users = new ArrayList<>();
        
        Date d1 = DateUtil.toDate("16/08/1984");
        Address a1 = new Address("6", "Rue Camille Koechlin", "Villeurbanne", "69100", "France");
        Pouloumer p_Momo = new Pouloumer("Momo", "Moez", "WOAGNER", "moez.woagner@hotmail.fr", "mdpmw", false, false, 'M', d1,"0832205629", a1);
        users.add(p_Momo);
        
        Date d2 = DateUtil.toDate("17/02/1996");
        Address a2 = new Address("9", "Impasse Guillet", "Villeurbanne", "69100", "France");
        Pouloumer p_Matty = new Pouloumer("Matty", "Matteo", "HONRY", "matteo.honry@hotmail.fr", "mdpmh", false, false, 'M', d2,"0482381862", a2);
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
        
        
        List<Activity> activities = ServicesActivity.findAllActivities();
        List<Event> events = new ArrayList<Event>();
        
        Pouloumer p1 = ServicesPouloumer.getPouloumerByNickname("Momo");
        Date d01 = new Date();
        Event e1 = new Event("1er evenement", "such description", d01, false, 30, a1, activities.get(0), p1, 1, 10);
        Event e2 = new Event("2er evenement", "wow description", d01, false, 60, a1, activities.get(1), p1, 3000, 9000);
        Event e3 = new Event("3eme evenement", "damn description", d01, false, 80, a1, activities.get(2), p1, 2, 4);

        Pouloumer p2 = ServicesPouloumer.getPouloumerByNickname("Keke");
        Date d02 = new Date();
        Event e4 = new Event("my keke", "such kek", d02, false, 70, a2, activities.get(4), p2, 1, 10);
        Event e5 = new Event("your keke", "dat kek", d02, false, 40, a2, activities.get(5), p2, 0, 15);

        Pouloumer p3 = ServicesPouloumer.getPouloumerByNickname("Matty");
        Date d03 = new Date();
        Event e6 = new Event("hehehe", "useless information is useless", d03, false, 150, a3, activities.get(6), p3, 10, 12);

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
    
    protected static void setupEvents()
            throws Exception {
        JpaUtil.createEntityManager();
        
        List<Event> events = new ArrayList<>();
        
        List<Long> idsPouloumers = DAOPouloumer.findAllIDs();
        int nbPouloumer = idsPouloumers.size();

        List<Long> idsActivities = DAOActivity.findAllIDs();
        int nbActivity = idsActivities.size();

        List<Long> idsAddress = DAOAddress.findAllIDs();
        int nbAddress = idsAddress.size();
            
        Random rand = new Random();

        DataFactory df = new DataFactory();
        Date minDate = df.getDate(2019, 5, 6);
        Date maxDate = df.getDate(2019, 5, 19);
        
        JpaUtil.closeEntityManager();
        
        for (int i = 0; i<200; i++){
            Date date = df.getDateBetween(minDate, maxDate);
            Pouloumer organizer = ServicesPouloumer.getPouloumerById(idsPouloumers.get(rand.nextInt(nbPouloumer))); 
            Activity activity = ServicesActivity.getActivityById(idsActivities.get(rand.nextInt(nbActivity)));
            Address address = ServicesAddress.getAddressById(idsAddress.get(rand.nextInt(nbAddress)));
            Event e = new Event("evenement"+i, df.getRandomText(50, 200), date, false, 60, address, activity, organizer, rand.nextInt(5), rand.nextInt(5)+5);
            events.add(e);
        }
        
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
    }
    
}
