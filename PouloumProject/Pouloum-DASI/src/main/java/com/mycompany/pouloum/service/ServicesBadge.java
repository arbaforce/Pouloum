/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.pouloum.service;

import com.mycompany.pouloum.dao.DAOBadge;
import com.mycompany.pouloum.dao.JpaUtil;
import com.mycompany.pouloum.model.Badge;

/**
 *
 * @author Martin
 */
public class ServicesBadge {
    
    public static Badge getBadgeById(long id) {
        JpaUtil.createEntityManager();
        Badge b;

        try {
            b = DAOBadge.findById(id);
        } catch (Exception ex) {
            b = null;
        }

        JpaUtil.closeEntityManager();
        return b;
    }
}
