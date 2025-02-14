package com.school.chalkandroll.model;

import jakarta.persistence.Entity;
import jakarta.persistence.*;

import java.util.*;


@Entity
public class Courses extends BaseEntity{
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)

    private int courseId;

    private String name;

    private String fees;

    @ManyToMany(mappedBy = "courses", fetch = FetchType.EAGER,cascade = CascadeType.PERSIST)
    private Set<Person> persons = new HashSet<>();


    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFees() {
        return fees;
    }

    public void setFees(String fees) {
        this.fees = fees;
    }

    public Set<Person> getPersons() {
        return persons;
    }

    public void setPersons(Set<Person> persons) {
        this.persons = persons;
    }
}
