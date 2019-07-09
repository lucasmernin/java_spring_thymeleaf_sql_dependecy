/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superheros.dao;

import com.sg.superheros.entities.Hero;
import com.sg.superheros.entities.Location;
import com.sg.superheros.entities.Sighting;
import java.util.List;


/**
 *
 * @author lukem
 */
public interface LocationDao {
    
    Location addLocation(Location location);
    
    void deleteLocationById(int id);
    
    void updateLocation(Location location);
    
    List<Location> getAllLocations();
    
    Location getLocationById(int id);
    
    
    
}
