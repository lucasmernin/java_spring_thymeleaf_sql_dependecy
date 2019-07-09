package com.sg.superheros.controller;

import com.sg.superheros.dao.HeroDao;
import com.sg.superheros.dao.LocationDao;
import com.sg.superheros.dao.OrganizationDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import com.sg.superheros.dao.SightingDao;
import com.sg.superheros.entities.Hero;
import com.sg.superheros.entities.Organization;
import java.util.ArrayList;
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
public class OrganizationController {

    @Autowired
    HeroDao heroDao;

    @Autowired
    LocationDao locationDao;

    @Autowired
    SightingDao sightingDao;

    @Autowired
    OrganizationDao organizationDao;
    
      @PostMapping("addOrganization")
    public String addOrganization(String name, String description, String contact, int[] heroid) {
        Organization org = new Organization();
        
         List<Hero> heros = new ArrayList<>();
        int i;

        for (i = 0; i < heroid.length; i++) {
            heros.add(heroDao.getHeroById(heroid[i]));
        }

        org.setHeros(heros);
        org.setName(name);
        org.setDescription(description);
        org.setContact(contact);
        organizationDao.addOrganization(org);

        return "redirect:/organization";
    }

        @PostMapping("deleteOrganization")
    public String deleteOrganizationById(Integer id) {
        organizationDao.deleteOrganizationById(id);
        return "redirect:/organization";
    }

    @GetMapping("editOrganization")
    public String editOrganization(HttpServletRequest request, Model model) {
        int id = Integer.parseInt(request.getParameter("id"));
        Organization organization = organizationDao.getOrganizationById(id);
        
        List<Hero> heros = heroDao.getAllHeros();
        
        model.addAttribute("heros", heros);
        model.addAttribute("organization", organization);
        
        return "editOrganization";
    }

    @PostMapping("editOrganization")
    public String performEditOrganization(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("id"));
        Organization organization = organizationDao.getOrganizationById(id);
        
        String[] heroIds = request.getParameterValues("heroid");
        
        List<Hero> heros = new ArrayList<>();
        for(String heroid : heroIds) {
            heros.add(heroDao.getHeroById(Integer.parseInt(heroid)));
        }
        
        organization.setHeros(heros);
        organization.setName(request.getParameter("name"));
        organization.setDescription(request.getParameter("description"));
        organization.setContact(request.getParameter("contact"));
        
        organizationDao.updateOrganization(organization);
        
        
       
        return "redirect:/organization";
    }

    @GetMapping("organization")
    public String displayOrganizations(Model model) {
        List<Organization> organizations = organizationDao.getAllOrganizations();
        List<Hero> heros = heroDao.getAllHeros();
        
        model.addAttribute("organizations", organizations);
        model.addAttribute("heros", heros);
        
        return "organization";
    }
}
