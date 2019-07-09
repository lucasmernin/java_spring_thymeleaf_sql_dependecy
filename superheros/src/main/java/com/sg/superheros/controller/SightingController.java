package com.sg.superheros.controller;

import com.sg.superheros.dao.HeroDao;
import com.sg.superheros.dao.LocationDao;
import com.sg.superheros.dao.OrganizationDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import com.sg.superheros.dao.SightingDao;
import com.sg.superheros.entities.Hero;
import com.sg.superheros.entities.Location;
import com.sg.superheros.entities.Sighting;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

/**
 *
 * @author lukem
 */
@Controller
public class SightingController {

    @Autowired
    HeroDao heroDao;

    @Autowired
    LocationDao locationDao;

    @Autowired
    SightingDao sightingDao;

    @Autowired
    OrganizationDao organizationDao;

    @PostMapping("addSighting")
    public String addSighting(String date, Integer locationid, int[] heroid) {

        Sighting sighting = new Sighting();

        List<Hero> heros = new ArrayList<>();
        int i;

        for (i = 0; i < heroid.length; i++) {
            heros.add(heroDao.getHeroById(heroid[i]));
        }

        sighting.setDate(LocalDateTime.parse(date));
        sighting.setLocation(locationDao.getLocationById(locationid));
        sighting.setHeros(heros);
        sightingDao.addSighting(sighting);

        return "redirect:/sighting";
    }

    @PostMapping("deleteSighting")
    public String deleteSighting(Integer id) {
        sightingDao.deleteSightingById(id);
        return "redirect:/sighting";
    }

    @GetMapping("editSighting")
    public String editSighting(HttpServletRequest request, Model model) {
        int id = Integer.parseInt(request.getParameter("id"));
        Sighting sighting = sightingDao.getSightingById(id);
        List<Location> locations = locationDao.getAllLocations();
        List<Hero> heros = heroDao.getAllHeros();


        model.addAttribute("sighting", sighting);
        model.addAttribute("locations", locations);
        model.addAttribute("heros", heros);
        

        return "editSighting";
    }

    @PostMapping("editSighting")
    public String performEditSighting(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("id"));
        Sighting sighting = sightingDao.getSightingById(id);
        
        String[] heroIds = request.getParameterValues("heroid");
        String locationId = request.getParameter("locationid");
        
        
        
        List<Hero> heros = new ArrayList<>();
        for(String heroid : heroIds) {
            heros.add(heroDao.getHeroById(Integer.parseInt(heroid)));
        }
        
        
        
        
        
        String date = request.getParameter("date");
        LocalDateTime time = LocalDateTime.parse(date);
        
        sighting.setLocation(locationDao.getLocationById(Integer.parseInt(locationId)));
        sighting.setHeros(heros);
        sighting.setDate(time);

        sightingDao.updateSighting(sighting);

        return "redirect:/sighting";
    }

    @GetMapping("sighting")
    public String displaySightings(Model model) {
        List<Sighting> sightings = sightingDao.getAllSightings();
        List<Hero> heros = heroDao.getAllHeros();
        List<Location> locations = locationDao.getAllLocations();
        
        model.addAttribute("sightings", sightings);
        model.addAttribute("heros", heros);
        model.addAttribute("locations", locations);

        return "sighting";
    }

}
