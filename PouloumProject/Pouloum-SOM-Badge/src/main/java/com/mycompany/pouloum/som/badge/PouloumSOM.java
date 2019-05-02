/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.pouloum.som.badge;

import com.google.gson.JsonObject;
import com.mycompany.pouloum.util.DBConnection;
import com.mycompany.pouloum.dao.JpaUtil;
import com.mycompany.pouloum.dao.DAOBadge;
import com.mycompany.pouloum.model.Badge;

/**
 *
 * @author Martin
 */
public class PouloumSOM {

    protected JsonObject container;

    public PouloumSOM(JsonObject container) {
        this.container = container;
    }

    public void release() {
    }

    public Badge getBadgeById(long id) {
        JpaUtil.createEntityManager();
        Badge b;

        try {
            b = DAOBadge.findById(id);
        } catch (Exception e) {
            b = null;
        }

        JpaUtil.closeEntityManager();
        return b;
    }

}
