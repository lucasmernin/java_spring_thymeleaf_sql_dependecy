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
public class HeroDaoTest {

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
    public void testAddAndGetHero() {
        Hero hero = new Hero();
        hero.setName("Test First");
        hero.setDescription("Test Last");
        hero.setSuperpower("Test Specialty");
        heroDao.addHero(hero);

        Hero fromDao = heroDao.getHeroById(hero.getId());

        assertEquals(hero, fromDao);
    }

    @Test
    public void testGetAllHeros() {

        Hero hero = new Hero();
        hero.setName("Test First");
        hero.setDescription("Test Last");
        hero.setSuperpower("Test Specialty");
        heroDao.addHero(hero);

        Hero hero2 = new Hero();
        hero.setName("Test First");
        hero.setDescription("Test Last");
        hero.setSuperpower("Test Specialty");
        heroDao.addHero(hero2);

        List<Hero> heros = heroDao.getAllHeros();

        assertEquals(2, heros.size());
        assertTrue(heros.contains(hero));
        assertTrue(heros.contains(hero2));

    }
    
     @Test
    public void testUpdateHero() {
        Hero hero = new Hero();
        hero.setName("Test First");
        hero.setDescription("Test Last");
        hero.setSuperpower("Test Specialty");
        heroDao.addHero(hero);

        Hero fromDao = heroDao.getHeroById(hero.getId());
        assertEquals(hero, fromDao);

        hero.setName("test");
        heroDao.updateHero(hero);
        assertNotEquals(hero, fromDao);

        fromDao = heroDao.getHeroById(hero.getId());

        assertEquals(hero, fromDao);

    }

    @Test
    public void testDeleteHero() {
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
        
       

        LocalDateTime time = LocalDateTime.now();

        Sighting sighting = new Sighting();
        sighting.setDate(time);
        sighting.setLocation(location);
        sighting.setHeros(heros);

        sightingDao.addSighting(sighting);
        
        Hero fromDao = heroDao.getHeroById(hero.getId());
        assertEquals(hero, fromDao);
        
        heroDao.deleteHeroById(hero.getId());
        
        fromDao = heroDao.getHeroById(hero.getId());
        assertNull(fromDao);

        

        
    }

   
}
