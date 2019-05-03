package com.mycompany.pouloum.service;

import com.mycompany.pouloum.dao.DAOBadge;
import com.mycompany.pouloum.dao.JpaUtil;
import com.mycompany.pouloum.model.Badge;

public class ServicesBadge {
    
    public static Badge getBadgeById(Long id)
            throws Exception
    {
        JpaUtil.createEntityManager();
        
        try {
            Badge b = DAOBadge.findById(id);
            
            return b;
        } finally {
            JpaUtil.closeEntityManager();
        }
    }
    
}
