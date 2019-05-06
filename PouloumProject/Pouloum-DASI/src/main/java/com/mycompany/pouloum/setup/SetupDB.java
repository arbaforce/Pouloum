package com.mycompany.pouloum.setup;

import com.mycompany.pouloum.dao.*;
import com.mycompany.pouloum.model.*;
import com.mycompany.pouloum.service.ServicesActivity;
import com.mycompany.pouloum.service.ServicesEvent;
import com.mycompany.pouloum.service.ServicesPouloumer;
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
        
        for(int i = 0; i<200; i++){
            Pouloumer p = new Pouloumer(df.getRandomWord(), df.getFirstName(), df.getLastName(), df.getEmailAddress(), df.getRandomWord(8, 14), false, false, 'F', df.getBirthDate(), "06"+df.getNumberText(6),null);
            int nbInteret = rand.nextInt(6)+3;
            List<Integer> idUsed = new ArrayList<Integer>();
            for (int j = 0; j<=nbInteret; j++){
                try {
                    int id = rand.nextInt(100)+1;
                    if (idUsed.contains(id)){
                        j--;
                    }
                    else{
                        idUsed.add(id);
                        p.addInterest(ServicesActivity.getActivityById((long) id));
                    }
                } catch (Exception ex) {
                    Logger.getLogger(SetupDB.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            users.add(p);
        }

        JpaUtil.createEntityManager();

        try {
            for (Pouloumer u : users) {
                try {
                    Pouloumer check;
                    check = DAOPouloumer.findPouloumerByEmail(u.getEmail());
                    if (check != null) {
                        continue;
                    }
                    check = DAOPouloumer.findPouloumerByNickname(u.getNickname());
                    if (check != null) {
                        continue;
                    }

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
            throws Exception {
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
