package vue;

import dao.*;
import dao.JpaUtil;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import model.*;
import service.ServicesApp;


public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        JpaUtil.init();
        
        try {
            ServicesApp.CreateSampleUsers();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        JpaUtil.destroy();
    }
    
}
