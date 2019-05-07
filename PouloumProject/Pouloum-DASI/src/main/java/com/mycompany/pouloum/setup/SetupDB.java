package com.mycompany.pouloum.setup;

import com.mycompany.pouloum.dao.*;
import com.mycompany.pouloum.model.*;
import com.mycompany.pouloum.service.ServicesActivity;
import com.mycompany.pouloum.service.ServicesAddress;
import com.mycompany.pouloum.service.ServicesEvent;
import com.mycompany.pouloum.service.ServicesPouloumer;
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
            setupAddress();
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
    
    protected static void setupAddress() throws IOException{
        BufferedReader reader = getResourceReader("setup/Address.txt");

        ArrayList<Address> addresses = new ArrayList<Address>();

        try {
            for (String line = reader.readLine(); line != null; line = reader.readLine()) {
                System.out.println(line);
                String[] parts = line.split(",");
                System.out.println("    "+parts[0]);
                System.out.println("    "+parts[1]);
                System.out.println("    "+parts[2]);
                Address a = new Address(parts[0], parts[1], null, parts[2], "France" );
                addresses.add(a);
                
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

    protected static void setupPouloumer()
            throws ParseException {
        List<Pouloumer> users = new ArrayList<>();
        DataFactory df = new DataFactory();
        Random rand = new Random();
        
        for(int i = 0; i<100; i++){
            Pouloumer p = new Pouloumer(Integer.toString(i), df.getFirstName(), df.getLastName(), Integer.toString(i)+df.getEmailAddress(), df.getRandomWord(8, 14), false, false, 'F', df.getBirthDate(), "06"+df.getNumberText(6),null);
            int nbInteret = rand.nextInt(6)+3;
            List<Integer> idUsed = new ArrayList<Integer>();
            for (int j = 0; j<nbInteret; j++){
                try {
                    int id = rand.nextInt(100)+1;
                    if (idUsed.contains(id)){
                        j--;
                    }
                    else{
                        idUsed.add(id);
                        Activity a = ServicesActivity.getActivityById((long) id);
                        p.addInterest(a);
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
        List<Event> events = new ArrayList<Event>();
        DataFactory df = new DataFactory();
        Random rand = new Random();
        Date minDate = df.getDate(2019, 5, 6);
        Date maxDate = df.getDate(2019, 5, 19);
        
        for (int i = 0; i<50; i++){
            Date date = df.getDateBetween(minDate, maxDate);
            Pouloumer organizer = ServicesPouloumer.getPouloumerById((long) (rand.nextInt(3)+1)); 
            Activity activity = ServicesActivity.getActivityById((long)(rand.nextInt(200)+1));
            Address address = ServicesAddress.getAddressById((long)(rand.nextInt(20)+1));
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
