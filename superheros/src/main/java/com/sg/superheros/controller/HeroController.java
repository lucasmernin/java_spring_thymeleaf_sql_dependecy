package com.sg.superheros.controller;

import com.sg.superheros.dao.HeroDao;
import com.sg.superheros.dao.LocationDao;
import com.sg.superheros.dao.OrganizationDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import com.sg.superheros.dao.SightingDao;
import com.sg.superheros.entities.Hero;
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
public class HeroController {

    @Autowired
    HeroDao heroDao;

    @Autowired
    LocationDao locationDao;

    @Autowired
    SightingDao sightingDao;

    @Autowired
    OrganizationDao organizationDao;

    @PostMapping("addHero")
    public String addHero(String name, String description, String superpower) {
        Hero hero = new Hero();

        hero.setName(name);
        hero.setDescription(description);
        hero.setSuperpower(superpower);
        heroDao.addHero(hero);

        return "redirect:/hero";
    }

    @PostMapping("deleteHero")
    public String deleteHero(Integer id) {
        heroDao.deleteHeroById(id);
        return "redirect:/hero";
    }

    @GetMapping("editHero")
    public String editHero(HttpServletRequest request, Model model) {
        int id = Integer.parseInt(request.getParameter("id"));
        Hero hero = heroDao.getHeroById(id);
        
        model.addAttribute("hero", hero);
        
        return "editHero";
    }

    @PostMapping("editHero")
    public String performEditHero(HttpServletRequest request) {
       int id = Integer.parseInt(request.getParameter("id"));
       Hero hero = heroDao.getHeroById(id);
       
       hero.setName(request.getParameter("name"));
       hero.setDescription(request.getParameter("description"));
       hero.setSuperpower(request.getParameter("superpower"));
       
       heroDao.updateHero(hero);

        return "redirect:/hero";
    }

    @GetMapping("hero")
    public String displayHeros(Model model) {
        List<Hero> heros = heroDao.getAllHeros();
        model.addAttribute("heros", heros);
        return "hero";
    }

}
