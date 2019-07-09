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
public class OrganizationDaoTest {
    
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
    public void testAddAndGetOrganization() {
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
        
        Organization fromDao = organizationDao.getOrganizationById(org.getId());
        
        assertEquals(org, fromDao);
    }
    
     @Test
    public void testGetAllOrganizations() {
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
        
         Organization org2 = new Organization();
        org2.setName("n");
        org2.setDescription("x");
        org2.setContact("s");
        org2.setHeros(heros);
        organizationDao.addOrganization(org2);
        
        List<Organization> orgs = organizationDao.getAllOrganizations();
        
        assertEquals(2, orgs.size());
        assertTrue(orgs.contains(org));
        assertTrue(orgs.contains(org2));

    }
    
    @Test
    public void testDeleteOrganization() {
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
        
         Organization org2 = new Organization();
        org2.setName("n");
        org2.setDescription("x");
        org2.setContact("s");
        org2.setHeros(heros);
        organizationDao.addOrganization(org2);
        
        
        organizationDao.deleteOrganizationById(org.getId());
        
        List<Organization> orgs = organizationDao.getAllOrganizations();
        
        
        
        assertEquals(1, orgs.size());
    }
    
    @Test
    public void testUpdateOrganization() {
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
        
        Organization fromDao = organizationDao.getOrganizationById(org.getId());
        assertEquals(org, fromDao);
        
        org.setName("test");
        organizationDao.updateOrganization(org);
        assertNotEquals(org, fromDao);
        
        fromDao = organizationDao.getOrganizationById(org.getId());
        
        assertEquals(org, fromDao);
        
        
    }
}
