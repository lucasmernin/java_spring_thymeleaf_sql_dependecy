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
public class SightingDaoTest {
    
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
    public void testAddAndGetSighting() {
       
          Location location = new Location();
        location.setName("Test First");
        location.setAddress("22");
        location.setLongitude(new BigDecimal("2"));
        location.setLatitude(new BigDecimal("1"));
        
        location = locationDao.addLocation(location);
        
        Hero hero = new Hero();
        hero.setName("Test First");
        hero.setDescription("Test Last");
        hero.setSuperpower("Test Specialty");
        heroDao.addHero(hero);
        List<Hero> heros = new ArrayList<>();
        heros.add(hero);

        

        Sighting sighting = new Sighting();
        
        sighting.setLocation(location);
        sighting.setHeros(heros);
        sighting.setDate(LocalDateTime.now().withNano(0));

       sighting = sightingDao.addSighting(sighting); 
        
        Sighting fromDao = sightingDao.getSightingById(sighting.getId());
        assertEquals(sighting, fromDao);
       
    }
    
     @Test
    public void testGetAllSightings() {
        Hero hero = new Hero();
        hero.setName("Test First");
        hero.setDescription("Test Last");
        hero.setSuperpower("Test Specialty");
        heroDao.addHero(hero);
        List<Hero> heros = new ArrayList<>();
        heros.add(hero);
        
        Organization org = new Organization();
        org.setName("n");
        org.setDescription("x");
        org.setContact("s");
        org.setHeros(heros);
        org = organizationDao.addOrganization(org);
        
          Location location = new Location();
        location.setName("Test First");
        location.setAddress("22");
        location.setLongitude(new BigDecimal("2"));
        location.setLatitude(new BigDecimal("1"));
        
        location = locationDao.addLocation(location);
        
        

        

        Sighting sighting = new Sighting();
        
        sighting.setLocation(location);
        sighting.setHeros(heros);
        sighting.setDate(LocalDateTime.now().withNano(0));

        sighting = sightingDao.addSighting(sighting);
        
        Sighting sightings = new Sighting();
        
        sightings.setLocation(location);
        sightings.setHeros(heros);
        sightings.setDate(LocalDateTime.now().withNano(0));

        sightings = sightingDao.addSighting(sightings);

        List<Sighting> sightingss = sightingDao.getAllSightings();
        assertEquals(2, sightingss.size());
        
        
    }
    
    @Test
    public void testDeleteSightingById() {
        Hero hero = new Hero();
        hero.setName("Test First");
        hero.setDescription("Test Last");
        hero.setSuperpower("Test Specialty");
        heroDao.addHero(hero);
        List<Hero> heros = new ArrayList<>();
        heros.add(hero);
        
          Organization org = new Organization();
        org.setName("n");
        org.setDescription("x");
        org.setContact("s");
        org.setHeros(heros);
        organizationDao.addOrganization(org);
        
          Location location = new Location();
        location.setName("Test First");
        location.setAddress("22");
        location.setLongitude(new BigDecimal("2"));
        location.setLatitude(new BigDecimal("1"));
        
        locationDao.addLocation(location);
        
        

        

        Sighting sighting = new Sighting();
        
        sighting.setLocation(location);
        sighting.setHeros(heros);
        sighting.setDate(LocalDateTime.now().withNano(0));

        sightingDao.addSighting(sighting);
        
        Sighting fromDao = sightingDao.getSightingById(sighting.getId());
        assertEquals(sighting, fromDao);
        
        sightingDao.deleteSightingById(sighting.getId());
        
        fromDao = sightingDao.getSightingById(sighting.getId());
        assertNull(fromDao);
    }
    
    @Test
    public void testUpdateSighting() {
        Hero hero = new Hero();
        hero.setName("Test First");
        hero.setDescription("Test Last");
        hero.setSuperpower("Test Specialty");
        heroDao.addHero(hero);
        List<Hero> heros = new ArrayList<>();
        heros.add(hero);
        
           Organization org = new Organization();
        org.setName("n");
        org.setDescription("x");
        org.setContact("s");
        org.setHeros(heros);
        organizationDao.addOrganization(org);
        
          Location location = new Location();
        location.setName("Test First");
        location.setAddress("22");
        location.setLongitude(new BigDecimal("2"));
        location.setLatitude(new BigDecimal("1"));
        
        locationDao.addLocation(location);
        
        

        

        Sighting sighting = new Sighting();
        
        sighting.setLocation(location);
        sighting.setHeros(heros);
        sighting.setDate(LocalDateTime.now().withNano(0));

        sightingDao.addSighting(sighting);
        
        Sighting fromDao = sightingDao.getSightingById(sighting.getId());
        assertEquals(sighting, fromDao);
        
        fromDao.setId(99);
        
        
        
        assertNotEquals(sighting, fromDao);
        
        
        
    }
    
}
