package com.mycompany.pouloum.setup;

import com.mycompany.pouloum.dao.*;
import com.mycompany.pouloum.dao.JpaUtil;
import com.mycompany.pouloum.model.*;
import com.mycompany.pouloum.service.ServicesActivity;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.file.Files;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
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
                            ServicesActivity.addActivityChild(parent, cur);
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
        
        Address a1 = new Address("6", "Rue Camille Koechlin", "Villeurbanne", "69100", "France");
        Pouloumer u1 = new Pouloumer("Momo", "Moez", "WOAGNER", "moez.woagner@proactif.com", "mdpmw", false, false, 'M', "16/08/1984","0832205629", a1);
        users.add(u1);
        
        Address a2 = new Address("9", "Impasse Guillet", "Villeurbanne", "69100", "France");
        Pouloumer u2 = new Pouloumer("Matty", "Matteo", "HONRY", "matteo.honry@proactif.com", "mdpmh", false, false, 'M', "17/02/1996","0482381862", a1);
        users.add(u2);
        
        Address a3 = new Address("20", "Rue Decomberousse", "Villeurbanne", "69100", "France");
        Pouloumer u3 = new Pouloumer("Keke", "Kevin", "CECCANI", "kevin.ceccani@proactif.com", "mdpkc", false, false, 'M', "16/02/1982","0664426037", a3);
        users.add(u3);
        
        Address a4 = new Address("1", "Rue d'Alsace", "Villeurbanne", "69100", "France");
        Pouloumer u4 = new Pouloumer("Valice", "Alice", "VOYRET", "alice.voyret@proactif.com", "mdpav", false, false, 'F', "13/08/1988","0486856520", a4);
        users.add(u4);
        
        Address a5 = new Address("4", "Rue de la Jeunesse", "Villeurbanne", "69100", "France");
        Pouloumer u5 = new Pouloumer("Juju", "Julien", "RINERD", "jrinerd5241@proactif.com", "mdpjr", false, false, 'M', "16/05/1989","0727252485", a5);
        users.add(u5);
        
        Address a6 = new Address("7", "Rue de la Cloche", "Villeurbanne", "69100", "France");
        Pouloumer u6 = new Pouloumer("Olive", "Olivier", "WOSTPHOL", "owostphol@proactif.com", "mdpow", false, false, 'M', "24/05/1983","0860680312", a6);
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
    
}