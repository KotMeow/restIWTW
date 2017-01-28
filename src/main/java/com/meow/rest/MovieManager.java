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
@Path("movies")
public class MovieManager {

    @EJB
    MovieService movieService;
    @EJB
    ActorService actorService;

    @GET
    @Path("/")
    @Produces("application/json")
    public List getAllMovies() {
        List movies = movieService.getAllMovies();
        if (movies == null) return null;
        return movies;
    }

    @POST
    @Path("search")
    @Produces("application/json")
    public Movie Search(@FormParam("name") String name) {
        Movie movie = movieService.getMoviesByName(name);
        if (movie == null) return null;
        return movie;
    }


    @GET
    @Path("/{id}")
    @Produces("application/json")
    public Movie getMovie(@PathParam("id") long id) {
        return movieService.getMovieById(id);
    }

    @GET
    @Path("/{idMovie}/actors/")
    @Produces("application/json")
    public List<Actor> getMovieActors(@PathParam("idMovie") long idMovie) {
        return movieService.getMovieById(idMovie).getActors();
    }



//    addition

    @POST
    @Path("/{idMovie}/actors/add")
    @Produces(MediaType.TEXT_HTML)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public void addActor(@FormParam("name") String name,
                         @FormParam("role") String role,
                         @PathParam("idMovie") long idMovie,
                         @Context HttpServletResponse servletResponse) throws IOException {
        Actor actor = new Actor(name, role);
        actorService.addActor(actor);
        movieService.addActorToMovie(idMovie, actor.getId());
        servletResponse.sendRedirect("/hibernate/api/movies/" + idMovie + "/displayActors");
    }

    @POST
    @Path("/add")
    @Produces(MediaType.TEXT_HTML)
    public void addMovie(@FormParam("title") String title,
                         @FormParam("genre") String genre,
                         @FormParam("releaseYear") int releaseYear,
                         @Context HttpServletResponse servletResponse) throws IOException {
        Movie movie = new Movie(title, releaseYear, genre);
        movieService.addMovie(movie);
    }


//    editing

    @POST
    @Path("{id}/edit")
    public void editMovie(
            @PathParam("id") Long id,
            @FormParam("title") String title,
            @FormParam("genre") String genre,
            @FormParam("releaseYear") int year, @Context HttpServletResponse servletResponse) throws IOException {


        Movie movie = movieService.getMovieById(id);
        if (movie != null) {
            movie.setTitle(title);
            movie.setGenre(genre);
            movie.setReleaseYear(year);
            movieService.updateMovie(movie);
        } else {
            movie = new Movie(title, year, genre);
            movieService.addMovie(movie);
        }

        servletResponse.sendRedirect("/hibernate/api/movies/displayAll");
    }

    @POST
    @Path("/{idMovie}/actors/{idActor}/edit")
    @Produces("application/json")
    public void editActor(@PathParam("idActor") long idActor,
                          @PathParam("idMovie") long idMovie,
                          @FormParam("name") String name,
                          @FormParam("role") String role,
                          @Context HttpServletResponse servletResponse) throws IOException {

        Actor actor = actorService.getActorById(idActor);
        if (actor != null) {
            actor.setName(name);
            actor.setRole(role);
            actorService.updateActor(actor);
        } else {
            actor = new Actor(name, role);
            actorService.updateActor(actor);
        }

        servletResponse.sendRedirect("/hibernate/api/movies/" + idMovie + "/displayActors");
    }

//    deleting

    @DELETE
    @Path("/{id}")
    public void delete(@PathParam("id") Long id) {
        movieService.removeMovie(id);
    }




}
