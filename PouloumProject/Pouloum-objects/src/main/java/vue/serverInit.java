package vue;

import dao.JpaUtil;
import service.ServicesApp;


public class serverInit {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        JpaUtil.init();
        
        try {
            ServicesApp.CreateSampleUsers();
        } catch(Exception e) {
            e.printStackTrace();
        }
        
        JpaUtil.destroy();
        
    }
}
