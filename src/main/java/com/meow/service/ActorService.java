package com.meow.service;

import com.meow.domain.Actor;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Created by Meow on 27.01.2017.
 */
@Stateless
public class ActorService {

    @PersistenceContext
    private EntityManager manager;


    public List<Actor> getAllActors() {
        return manager.createQuery("Select a From Actor a", Actor.class).getResultList();
    }

    public Actor getActorById(Long id) {
        return manager.find(Actor.class, id);
    }


    public void addActor(Actor actor) {
        manager.persist(actor);
    }

    public void removeActor(long id) {
        Actor actor = manager.find(Actor.class, id);
        manager.remove(actor);
    }

    public void updateActor(Actor actor) {
            manager.merge(actor);
    }
}
