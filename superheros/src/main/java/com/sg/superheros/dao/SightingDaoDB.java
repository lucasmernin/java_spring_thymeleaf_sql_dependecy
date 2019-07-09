package com.sg.superheros.dao;

import com.sg.superheros.dao.HeroDaoDB.HeroMapper;
import com.sg.superheros.dao.LocationDaoDB.LocationMapper;
import com.sg.superheros.entities.Hero;
import com.sg.superheros.entities.Location;
import com.sg.superheros.entities.Sighting;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author lukem
 */
@Repository
public class SightingDaoDB implements SightingDao {

    @Autowired
    JdbcTemplate jdbc;
    
       @Override
    public Sighting getSightingById(int id) {
        try {
            final String SELECT_SIGHTING_BY_ID = "SELECT * FROM sighting WHERE id = ?";
            Sighting sighting = jdbc.queryForObject(SELECT_SIGHTING_BY_ID, new SightingMapper(), id);
            sighting.setLocation(getLocationForSighting(id));
            sighting.setHeros(getHerosForSighting(id));
            return sighting;
        } catch (DataAccessException ex) {
            return null;
        }
    }

    private Location getLocationForSighting(int id) {
        final String SELECT_LOCATION_FOR_SIGHTING = "SELECT l.* FROM location l "
                + "JOIN sighting s ON s.locationid = l.id WHERE s.id = ?";
        return jdbc.queryForObject(SELECT_LOCATION_FOR_SIGHTING, new LocationMapper(), id);
    }

    private List<Hero> getHerosForSighting(int id) {
        final String SELECT_HEROS_FOR_SIGHTING = "SELECT h.* FROM hero h "
                + "JOIN sighting_hero sh ON sh.heroid = h.id WHERE sh.sightingid = ?";
        return jdbc.query(SELECT_HEROS_FOR_SIGHTING, new HeroMapper(), id);
    }

    
    
    
    
    
    @Override
    public List<Sighting> getAllSightings() {
        final String SELECT_ALL_SIGHTINGS = "SELECT * FROM sighting";
        List<Sighting> sightings = jdbc.query(SELECT_ALL_SIGHTINGS, new SightingMapper());
        associateLocationAndHeros(sightings);
        return sightings;
    }
    
     private void associateLocationAndHeros(List<Sighting> sightings) {
        for (Sighting sighting : sightings) {
            sighting.setLocation(getLocationForSighting(sighting.getId()));
            sighting.setHeros(getHerosForSighting(sighting.getId()));
        }
    }
     
     @Override
    public List<Sighting> getLastTenSightings() {
        final String SELECT_LAST_TEN_SIGHTINGS = "SELECT * FROM sighting ORDER BY id DESC LIMIT 10";
        List<Sighting> sightings = jdbc.query(SELECT_LAST_TEN_SIGHTINGS, new SightingMapper());
        associateLocationAndHeros(sightings);
        return sightings;
    }

     
       @Override
    public List<Sighting> getSightingsForLocation(Location location) {
        final String SELECT_SIGHTINGS_FOR_LOCATION = "SELECT * FROM sighting WHERE locationid = ?";
        List<Sighting> sightings = jdbc.query(SELECT_SIGHTINGS_FOR_LOCATION, new SightingMapper(), location.getId());
        associateLocationAndHeros(sightings);
        return sightings;
    }

    @Override
    public List<Sighting> getSightingsForHero(Hero hero) {
        final String SELECT_SIGHTINGS_FOR_HERO = "SELECT s.* FROM sighting s JOIN "
                + "sighting_hero sh ON sh.sightingid = s.id WHERE sh.heroid = ?";
        List<Sighting> sightings = jdbc.query(SELECT_SIGHTINGS_FOR_HERO, new SightingMapper(), hero.getId());
        associateLocationAndHeros(sightings);
        return sightings;
    }
     

    @Override
    @Transactional
    public Sighting addSighting(Sighting sighting) {
        final String INSERT_SIGHTING = "INSERT INTO sighting(date, locationid)" + "VALUES(?,?)";
        jdbc.update(INSERT_SIGHTING,
                sighting.getDate(),
                sighting.getLocation().getId());

        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        sighting.setId(newId);
        insertSightingHero(sighting);

        return sighting;
    }

    private void insertSightingHero(Sighting sighting) {
        final String INSERT_SIGHTING_HERO = "INSERT INTO "
                + "sighting_hero(sightingid, heroid) VALUES(?,?)";
        for (Hero hero : sighting.getHeros()) {
            jdbc.update(INSERT_SIGHTING_HERO,
                    sighting.getId(),
                    hero.getId());
        }
    }

    @Override
    @Transactional
    public void deleteSightingById(int id) {
        final String DELETE_SIGHTING_HERO = "DELETE FROM sighting_hero WHERE sightingid = ?";
        jdbc.update(DELETE_SIGHTING_HERO, id);
        
        final String DELETE_SIGHTING = "DELETE FROM sighting WHERE id = ?";
        jdbc.update(DELETE_SIGHTING, id);

        
    }

    @Override
    @Transactional
    public void updateSighting(Sighting sighting) {
        final String UPDATE_SIGHTING = "UPDATE sighting SET date = ?," + "locationid = ? WHERE id = ?";
        jdbc.update(UPDATE_SIGHTING,
                sighting.getDate(),
                sighting.getLocation().getId(),
                sighting.getId());
                

        final String DELETE_SIGHTING_HERO = "DELETE FROM sighting_hero WHERE sightingid = ?";
        jdbc.update(DELETE_SIGHTING_HERO, sighting.getId());
        insertSightingHero(sighting);

    }

    @Override
    public List<Sighting> getSightingsByDate(LocalDateTime date) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

   
    
  

    public static final class SightingMapper implements RowMapper<Sighting> {

        @Override
        public Sighting mapRow(ResultSet rs, int index) throws SQLException {
            Sighting sighting = new Sighting();
            sighting.setId(rs.getInt("id"));
            sighting.setDate(rs.getTimestamp("date").toLocalDateTime());

            return sighting;
        }
    }

}
