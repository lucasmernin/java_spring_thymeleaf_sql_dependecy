/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superheros.dao;

import com.sg.superheros.entities.Hero;
import com.sg.superheros.entities.Location;
import com.sg.superheros.entities.Organization;
import com.sg.superheros.entities.Sighting;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 *
 * @author lukem
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class LocationDaoTest {
    
     @Autowired
    HeroDao heroDao;

    @Autowired
    LocationDao locationDao;

    @Autowired
    OrganizationDao organizationDao;
    
    @Autowired
    SightingDao sightingDao;

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        List<Hero> heros = heroDao.getAllHeros();
        for (Hero hero : heros) {
            heroDao.deleteHeroById(hero.getId());
        }

        List<Location> locations = locationDao.getAllLocations();
        for (Location location : locations) {
            locationDao.deleteLocationById(location.getId());
        }

        List<Organization> organizations = organizationDao.getAllOrganizations();
        for (Organization organization : organizations) {
            organizationDao.deleteOrganizationById(organization.getId());
        }
        
         List<Sighting> sightings = sightingDao.getAllSightings();
        for (Sighting sighting : sightings) {
            sightingDao.deleteSightingById(sighting.getId());
        }
    }

    @After
    public void tearDown() {
    }
    
       @Test
    public void testAddAndGetLocation() {
        
        Location location = new Location();
        location.setName("Test First");
        location.setAddress("22");
        location.setLongitude(new BigDecimal("2"));
        location.setLatitude(new BigDecimal("1"));
        
        locationDao.addLocation(location);
        
        Location fromDao = locationDao.getLocationById(location.getId());
        
        assertEquals(location, fromDao);
    }
    
     @Test
    public void testGetAllLocations() {
     
         Location location = new Location();
        location.setName("Test First");
        location.setAddress("22");
        location.setLongitude(new BigDecimal("2"));
        location.setLatitude(new BigDecimal("1"));
        
        locationDao.addLocation(location);
        
         Location location2 = new Location();
        location.setName("Test First");
        location.setAddress("22");
        location.setLongitude(new BigDecimal("2"));
        location.setLatitude(new BigDecimal("1"));
        
        locationDao.addLocation(location2);
        
        List<Location> locations = locationDao.getAllLocations();
        
        assertEquals(2, locations.size());
        assertTrue(locations.contains(location));
        assertTrue(locations.contains(location2));

    }
    
    @Test
    public void testDeleteLocationById() {
        
          Location location = new Location();
        location.setName("Test First");
        location.setAddress("22");
        location.setLongitude(new BigDecimal("2"));
        location.setLatitude(new BigDecimal("1"));
        
        locationDao.addLocation(location);
        
        
        
        Location fromDao = locationDao.getLocationById(location.getId());
        assertEquals(location, fromDao);
        
        locationDao.deleteLocationById(fromDao.getId());
        
        fromDao = locationDao.getLocationById(location.getId());
        assertNull(fromDao);
    }
    
    @Test
    public void testUpdateLocation() {
       Location location = new Location();
        location.setName("Test First");
        location.setAddress("22");
        location.setLongitude(new BigDecimal("2"));
        location.setLatitude(new BigDecimal("1"));
        
        locationDao.addLocation(location);
        
        Location fromDao = locationDao.getLocationById(location.getId());
        assertEquals(location, fromDao);
        
        location.setName("test");
        locationDao.updateLocation(location);
        assertNotEquals(location, fromDao);
        
        fromDao = locationDao.getLocationById(location.getId());
        
        assertEquals(location, fromDao);
        
        
    }
}
