/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superheros.dao;

import com.sg.superheros.entities.Hero;
import com.sg.superheros.entities.Organization;
import com.sg.superheros.entities.Sighting;
import java.util.List;

/**
 *
 * @author lukem
 */
public interface HeroDao {

    Hero addHero(Hero hero);

    void deleteHeroById(int id);

    void updateHero(Hero hero);

    Hero getHeroById (int id);
 
    List<Hero> getAllHeros();
}
