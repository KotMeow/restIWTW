package com.meow;


import com.meow.domain.Movie;
import com.meow.service.MovieService;
import io.restassured.RestAssured;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.ejb.EJB;

import static io.restassured.RestAssured.*;


public class MovieServiceTest {

    @EJB
    MovieService movieService;

    @BeforeClass
    public static void setUp() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 8080;
        RestAssured.basePath = "/restiwtw/api";
    }


    @Test
    public void addMovie() {
        delete("/movies/").then().assertThat().statusCode(200);
        get("/actors/").then().assertThat().statusCode(200);
        get("/movies/").then().assertThat().statusCode(200);
        Movie movie = new Movie("Title", 1993, "Fantasy");
//		Person person = new Person(PERSON_FIRST_NAME, PERSON_LAST_NAME, 1976);
//
        given().
                formParam("title", "testtitle").
                formParam("releaseYear", 1950).
                formParam("genre", "fantasytest").
        when().
                post("/movies/").then().assertThat().statusCode(201);
    }

}
