package com.sg.superheros.dao;

import com.sg.superheros.dao.HeroDaoDB.HeroMapper;
import com.sg.superheros.entities.Hero;
import com.sg.superheros.entities.Organization;
import java.sql.ResultSet;
import java.sql.SQLException;
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
public class OrganizationDaoDB implements OrganizationDao {

    @Autowired
    JdbcTemplate jdbc;

    @Override
    public Organization getOrganizationById(int id) {
        try {
            final String SELECT_ORGANIZATION_BY_ID = "SELECT * FROM organization WHERE id =?";
            Organization organization = jdbc.queryForObject(SELECT_ORGANIZATION_BY_ID, new OrganizationMapper(), id);

            organization.setHeros(getHerosForOrganization(id));
            return organization;
        } catch (DataAccessException ex) {
            return null;
        }
    }

    private List<Hero> getHerosForOrganization(int id) {
        final String SELECT_HEROS_FOR_ORGANIZATION = "SELECT h.* FROM hero h "
                + "JOIN organization_hero oh ON oh.heroid = h.id WHERE oh.organizationid = ?";
        return jdbc.query(SELECT_HEROS_FOR_ORGANIZATION, new HeroMapper(), id);
    }

    @Override
    public List<Organization> getAllOrganizations() {
        final String SELECT_ALL_ORGANIZATIONS = "SELECT * FROM organization";
        List<Organization> organizations = jdbc.query(SELECT_ALL_ORGANIZATIONS, new OrganizationMapper());
        associateHeros(organizations);
        return organizations;
    }

    private void associateHeros(List<Organization> organizations) {
        for (Organization organization : organizations) {
            organization.setHeros(getHerosForOrganization(organization.getId()));
        }
    }

    @Override
    @Transactional
    public Organization addOrganization(Organization organization) {
        final String INSERT_ORGANIZATION = "INSERT INTO organization(name, description, contact)" + "VALUES(?,?,?)";
        jdbc.update(INSERT_ORGANIZATION,
                organization.getName(),
                organization.getDescription(),
                organization.getContact());

        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        organization.setId(newId);
        insertOrganizationHero(organization);

        return organization;
    }

    private void insertOrganizationHero(Organization organization) {
        final String INSERT_ORGANIZATION_HERO = "INSERT INTO "
                + "organization_hero(organizationid, heroid) VALUES(?,?)";
        for (Hero hero : organization.getHeros()) {
            jdbc.update(INSERT_ORGANIZATION_HERO,
                    organization.getId(),
                    hero.getId());

        }
    }

    @Override
    @Transactional
    public void updateOrganization(Organization organization) {
        final String UPDATE_ORGANIZATION = "UPDATE organization SET name = ?, description = ?, contact = ?" + "WHERE id = ?";
        jdbc.update(UPDATE_ORGANIZATION,
                organization.getName(),
                organization.getDescription(),
                organization.getContact(),
                organization.getId());

        final String DELETE_ORGANIZATION_HERO = "DELETE FROM organization_hero WHERE organizationid = ?";
        jdbc.update(DELETE_ORGANIZATION_HERO, organization.getId());
        
        
        insertOrganizationHero(organization);
    }

    @Override
    @Transactional
    public void deleteOrganizationById(int id) {
        final String DELETE_HERO_ORGANIZATION = "DELETE FROM organization_hero WHERE organizationid=?";
        jdbc.update(DELETE_HERO_ORGANIZATION, id);

        final String DELETE_ORGANIZATION = "DELETE FROM organization WHERE id = ?";
        jdbc.update(DELETE_ORGANIZATION, id);

    }

    public static final class OrganizationMapper implements RowMapper<Organization> {

        @Override
        public Organization mapRow(ResultSet rs, int index) throws SQLException {
            Organization org = new Organization();
            org.setId(rs.getInt("id"));
            org.setName(rs.getString("name"));
            org.setDescription(rs.getString("description"));
            org.setContact(rs.getString("contact"));

            return org;
        }
    }

}
