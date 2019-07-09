package com.sg.superheros.controller;

import com.sg.superheros.dao.HeroDao;
import com.sg.superheros.dao.LocationDao;
import com.sg.superheros.dao.OrganizationDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import com.sg.superheros.dao.SightingDao;
import com.sg.superheros.entities.Location;
import java.math.BigDecimal;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import static jdk.nashorn.internal.runtime.Debug.id;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

/**
 *
 * @author lukem
 */
@Controller
public class LocationController {

    @Autowired
    HeroDao heroDao;

    @Autowired
    LocationDao locationDao;

    @Autowired
    SightingDao sightingDao;

    @Autowired
    OrganizationDao organizationDao;

    @PostMapping("addLocation")
    public String addLocation(String longitude, String latitude, String name, String address) {
        Location location = new Location();

        location.setLongitude(new BigDecimal(longitude));
        location.setLatitude(new BigDecimal(latitude));
        location.setName(name);
        location.setAddress(address);

        locationDao.addLocation(location);

        return "redirect:/location";
    }

    @PostMapping("deleteLocation")
    public String deleteLocation(Integer id) {
        locationDao.deleteLocationById(id);
        return "redirect:/location";
    }

    @GetMapping("editLocation")
    public String editLocation(HttpServletRequest request, Model model) {
        int id = Integer.parseInt(request.getParameter("id"));
        Location location = locationDao.getLocationById(id);

        model.addAttribute("location", location);

        return "editLocation";
    }

    @PostMapping("editLocation")
    public String performEditLocation(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("id"));
        Location location = locationDao.getLocationById(id);
        
        
        
        location.setLongitude(new BigDecimal(request.getParameter("longitude")));
        location.setLatitude(new BigDecimal(request.getParameter("latitude")));
        location.setName(request.getParameter("name"));
        location.setAddress(request.getParameter("address"));
        
        locationDao.updateLocation(location);
        

        return "redirect:/location";
    }

    @GetMapping("location")
    public String displayLocations(Model model) {
        List<Location> locations = locationDao.getAllLocations();
        model.addAttribute("locations", locations);

        return "location";
    }

}
