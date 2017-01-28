package com.meow.domain;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Meow on 27.01.2017.
 */
@XmlRootElement
@Entity
@NamedQueries({
        @NamedQuery(name="movies.deleteAll", query="Delete from Movie")
})
public class Movie {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;

    @Column(unique = true)
    private String title;
    private int releaseYear;
    private String genre;

    public Movie(String title, int releaseYear, String genre) {
        this.title = title;
        this.releaseYear = releaseYear;
        this.genre = genre;
    }

    public Movie(){

    }
    public List<Actor> getActors() {
        return actors;
    }

    public void setActors(List<Actor> actors) {
        this.actors = actors;
    }

    @OneToMany(targetEntity = Actor.class, fetch=FetchType.EAGER)
    private List<Actor> actors = new ArrayList<>();


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

}

