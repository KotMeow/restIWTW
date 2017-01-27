package com.meow.rest;

import com.meow.domain.Actor;
import com.meow.domain.Movie;
import com.meow.service.ActorService;
import com.meow.service.MovieService;

import javax.ejb.EJB;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.util.List;

/**
 * Created by Meow on 27.01.2017.
 */
@Path("actors")
public class ActorManager {

    @EJB
    ActorService actorService;
    @EJB
    MovieService movieService;

    @GET
    @Path("/actors/{idActor}")
    @Produces("application/json")
    public Actor getActor(@PathParam("idActor") long idActor) {
        return actorService.getActorById(idActor);
    }

    @GET
    @Path("/")
    @Produces("application/json")
    public List<Actor> getAllActor() {
        return actorService.getAllActors();
    }

    @POST
    @Path("/add")
    @Produces(MediaType.TEXT_HTML)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public void addActor(@FormParam("name") String name,
                         @FormParam("role") String role,
                         @FormParam("movieTitle") String movieTitle,
                         @Context HttpServletResponse servletResponse) throws IOException {
        Actor actor = new Actor(name, role);

        if (movieService.getMoviesByName(movieTitle) != null ) {
        actorService.addActor(actor);
        long idMovie = movieService.getMoviesByName(movieTitle).getId();
        movieService.addActorToMovie(idMovie, actor.getId());
        }

    }
}
