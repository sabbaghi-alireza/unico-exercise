package com.unico.exercise.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Created by Alireza on 3/16/2017.
 */
@Entity
public class Element implements Serializable {

    private static final long serialVersionUID = 1L;

    public Element(Integer first, Integer second) {
        this.first = first;
        this.second = second;
    }

    public Element(){
    }

    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    private Integer first;

    @NotNull
    private Integer second;

    private Integer gcd;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getFirst() {
        return first;
    }

    public void setFirst(Integer first) {
        this.first = first;
    }

    public Integer getSecond() {
        return second;
    }

    public void setSecond(Integer second) {
        this.second = second;
    }

    public Integer getGcd() {
        return gcd;
    }

    public void setGcd(Integer gcd) {
        this.gcd = gcd;
    }

    @Override
    public String toString() {
        return "Element{" +
                "first=" + first +
                ", second=" + second +
                '}';
    }
}
