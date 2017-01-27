package com.meow.service;

import com.meow.domain.Actor;
import com.meow.domain.Movie;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Created by Meow on 27.01.2017.
 */
@Stateless
public class MovieService {

    @PersistenceContext
    private EntityManager manager;

    public List<Movie> getAllMovies() {
        List<Movie> movies = manager.createQuery("Select a From Movie a", Movie.class).getResultList();
        return movies;
    }

    public Movie getMovieById(Long id) {
        Movie movie = manager.find(Movie.class, id);
        return movie;
    }

    public void addMovie(Movie movie) {

            manager.persist(movie);

    }

    public void removeMovie(long id) {
            Movie movie = manager.find(Movie.class, id);
            for (Actor actor : movie.getActors()) {
                manager.remove(actor);
            }
            manager.remove(movie);


    }

    public void updateMovie(Movie movie) {

            manager.merge(movie);

    }

    public void addActorToMovie(long idMovie, long idActor) {

            Actor actor = manager.find(Actor.class, idActor);
            Movie movie = manager.find(Movie.class, idMovie);
            movie.getActors().add(actor);
            manager.persist(movie);

    }

    public void removeActorFromMovie(long movieId, long actorId) {

            Actor actor = manager.find(Actor.class, actorId);
            Movie movie = manager.find(Movie.class, movieId);
            movie.getActors().remove(actor);
            manager.remove(actor);

    }

    public Movie getMoviesByName(String name) {
        return manager.createQuery("Select a From Movie a where a.title like :custName", Movie.class).setParameter("custName", name).getSingleResult();
    }
}