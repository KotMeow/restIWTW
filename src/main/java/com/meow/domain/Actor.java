package com.meow.domain;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by Meow on 27.01.2017.
 */
@XmlRootElement
@Entity
@NamedQueries({
        @NamedQuery(name="actor.deleteAll", query="Delete from Actor")
})
public class Actor {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    long id;
    private String name;
    private String role;

    public Actor(String name, String role) {
        this.name = name;
        this.role = role;
    }

    public Actor(){

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
