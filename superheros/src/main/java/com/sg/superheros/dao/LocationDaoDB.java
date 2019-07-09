package com.sg.superheros.dao;

import com.sg.superheros.dao.HeroDaoDB.HeroMapper;
import com.sg.superheros.entities.Hero;
import com.sg.superheros.entities.Location;
import com.sg.superheros.entities.Sighting;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import static jdk.nashorn.internal.runtime.Debug.id;
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
public class LocationDaoDB implements LocationDao {
    
    @Autowired
    JdbcTemplate jdbc;

    @Override
    @Transactional
    public Location addLocation(Location location) {
        final String INSERT_LOCATION = "INSERT INTO location(longitude, latitude, name, address)" + "VALUES(?,?,?,?)";
        jdbc.update(INSERT_LOCATION,
                location.getLongitude(),
                location.getLatitude(),
                location.getName(),
                location.getAddress());
        
        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        location.setId(newId);
        
        return location;
    }

    @Override
    @Transactional
    public void deleteLocationById(int id) {
        final String DELETE_LOCATION_SIGHTING ="DELETE FROM sighting WHERE locationid = ?";
        jdbc.update(DELETE_LOCATION_SIGHTING, id);
        
        final String DELETE_LOCATION = "DELETE FROM location WHERE id = ?";
        jdbc.update(DELETE_LOCATION, id);
    }

    @Override
    public void updateLocation(Location location) {
        final String UPDATE_LOCATION = "UPDATE location SET longitude = ?, latitude = ?, name = ?, address = ?" + "WHERE id = ?";
        jdbc.update(UPDATE_LOCATION,
                location.getLongitude(),
                location.getLatitude(),
                location.getName(),
                location.getAddress(),
                location.getId());
    }

    @Override
    public List<Location> getAllLocations() {
        final String SELECT_ALL_LOCATIONS = "SELECT * FROM location";
        return jdbc.query(SELECT_ALL_LOCATIONS, new LocationMapper());
    }

    @Override
    public Location getLocationById(int id) {
        try {
            final String SELECT_LOCATION_BY_ID = "SELECT * FROM location WHERE id = ?";
            return jdbc.queryForObject(SELECT_LOCATION_BY_ID, new LocationMapper(), id);
        } catch(DataAccessException ex) {
            return null;
        }
    }

    public static final class LocationMapper implements RowMapper<Location> {
        
        @Override
        public Location mapRow(ResultSet rs, int index) throws SQLException {
            Location loc = new Location();
            
            loc.setId(rs.getInt("id"));
            loc.setLongitude(rs.getBigDecimal("longitude"));
            loc.setLatitude(rs.getBigDecimal("latitude"));
            loc.setName(rs.getString("name"));
            loc.setAddress(rs.getString("address"));
            
            return loc;
        }
    }

}
