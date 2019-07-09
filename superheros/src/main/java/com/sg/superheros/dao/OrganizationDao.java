/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superheros.dao;

import com.sg.superheros.entities.Hero;
import com.sg.superheros.entities.Organization;
import java.util.List;

/**
 *
 * @author lukem
 */
public interface OrganizationDao {
    
    List<Organization> getAllOrganizations();
    
    Organization addOrganization(Organization organizations);
    
    void deleteOrganizationById(int id);
    
    void updateOrganization(Organization organizations);
    
    Organization getOrganizationById(int id);
    
    
}
