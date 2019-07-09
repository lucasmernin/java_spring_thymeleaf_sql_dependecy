/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superheros.dao;

import com.sg.superheros.entities.Hero;
import com.sg.superheros.entities.Location;
import com.sg.superheros.entities.Sighting;
import java.time.LocalDateTime;
import java.util.List;

/**
 *
 * @author lukem
 */
public interface SightingDao {
    
    List<Sighting> getAllSightings();
    
    Sighting addSighting(Sighting sightings);
    
    void deleteSightingById(int id);
    
    void updateSighting(Sighting sightings);
    
    Sighting getSightingById(int id);
    
    List<Sighting> getSightingsForLocation(Location location);
    
    List<Sighting> getSightingsForHero(Hero hero);
    
    List<Sighting> getSightingsByDate(LocalDateTime date);
    
    List<Sighting> getLastTenSightings();
}
