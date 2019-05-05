package com.mycompany.pouloum.service;

import com.mycompany.pouloum.dao.DAOBadge;
import com.mycompany.pouloum.dao.JpaUtil;
import com.mycompany.pouloum.model.BadgeEvolved;

public class ServicesBadge {
    
    /**
     * Get a badge, given its id.
     *
     * @param id is the badge id.
     * @throws Exception if there's an error trying to access the database,
     * or if there is no badge with the given id.
     * @return Badge, the badge matching to the id.
     */
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
