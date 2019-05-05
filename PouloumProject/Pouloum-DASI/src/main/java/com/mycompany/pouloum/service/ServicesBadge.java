package com.mycompany.pouloum.service;

import com.mycompany.pouloum.dao.DAOBadge;
import com.mycompany.pouloum.dao.JpaUtil;
import com.mycompany.pouloum.model.BadgeEvolved;

public class ServicesBadge {
    
    public static BadgeEvolved getBadgeById(Long id)
            throws Exception
    {
        JpaUtil.createEntityManager();
        
        try {
            BadgeEvolved b = DAOBadge.findById(id);
            
            return b;
        } finally {
            JpaUtil.closeEntityManager();
        }
    }
    
}
