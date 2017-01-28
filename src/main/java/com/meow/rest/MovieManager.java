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
import javax.ws.rs.core.Response;
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


    @POST
    @Path("/")
    @Produces(MediaType.TEXT_HTML)
    public Response addMovie(@FormParam("title") String title,
                             @FormParam("genre") String genre,
                             @FormParam("releaseYear") int releaseYear,
                             @Context HttpServletResponse servletResponse) throws IOException {
        Movie movie = new Movie(title, releaseYear, genre);
        movieService.addMovie(movie);
        return Response.status(Response.Status.CREATED).build();
    }

    @DELETE
    public Response clearPersons() {
        movieService.clearMovies();
        return Response.status(200).build();
    }

//    editing

    @POST
    @Path("{id}/edit")
    public Response editMovie(
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

        return Response.ok().build();
    }


//    deleting

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        movieService.removeMovie(id);
        return Response.ok().build();
    }

}
