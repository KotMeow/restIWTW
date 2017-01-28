package com.meow;


import com.meow.domain.Movie;
import com.meow.service.MovieService;
import io.restassured.RestAssured;
import org.hamcrest.Matchers;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.Assert;
import javax.ejb.EJB;

import static io.restassured.RestAssured.*;
import static org.hamcrest.core.IsEqual.equalTo;


public class MovieServiceTest {


    private Movie movie = new Movie("Title", 1993, "Fantasy");
    private int id;

    @BeforeClass
    public static void setUp() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 8080;
        RestAssured.basePath = "/restiwtw/api";
    }


    @Test
    public void addMovie() {
        delete("/movies/").then().assertThat().statusCode(200);
        get("/movies/").then().assertThat().statusCode(200);
        given().
                formParam("title", movie.getTitle()).
                formParam("releaseYear", movie.getReleaseYear()).
                formParam("genre", movie.getGenre()).
        when().
                post("/movies/").then().assertThat().statusCode(201);


    }

    @Test
    public void getMovie() {
        id = get("/movies/").path("id[0]");
        when().
                get("/movies/{id}", id).
                then().
                statusCode(200).
                body("id", equalTo(id),
                        "genre", equalTo(movie.getGenre()),
                        "title", equalTo(movie.getTitle()),
                        "releaseYear", equalTo(movie.getReleaseYear()));
    }


    @Test
    public void addActor() {
        id = get("/movies/").path("id[0]");
        int size = get("/movies/{0}/actors", id).body().path("list.size()");
        when().
                get("/movies/{0}/actors",id).
                then().
                statusCode(200).
                body("", Matchers.hasSize(size));


        given().
                formParam("name", "nameTest").
                formParam("role", "roleTest").
                formParam("movieTitle", movie.getTitle()).
                when().
                post("/actors/").then().assertThat().statusCode(201);

        when().
                get("/movies/{0}/actors",id).
                then().
                statusCode(200).
                body("", Matchers.hasSize(size+1));
    }

    @Test
    public void removeActor() {
        id = get("/movies/").path("id[0]");
        int size = get("/movies/{0}/actors", id).body().path("list.size()");
        int idActor = get("/movies/{0}/actors", id).path("id[0]");
        when().
                get("/movies/{0}/actors",id).
                then().
                statusCode(200).
                body("", Matchers.hasSize(size));
        when().
                delete("/actors/{id}", idActor).
                then().
                statusCode(200);
        when().
                get("/movies/{0}/actors",id).
                then().
                statusCode(200).
                body("", Matchers.hasSize(size-1));
    }

}
