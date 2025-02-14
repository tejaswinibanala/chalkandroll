package com.school.chalkandroll.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.school.chalkandroll.annotation.FieldsValueMatch;
import com.school.chalkandroll.annotation.PasswordValidator;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.HashSet;
import java.util.Set;


@Entity
@FieldsValueMatch.List({
        @FieldsValueMatch(
                field = "pwd",
                fieldMatch = "confirmPwd",
                message = "Passwords do not match!"
        ),
        @FieldsValueMatch(
                field = "email",
                fieldMatch = "confirmEmail",
                message = "Email addresses do not match!"
        )
})
public class Person extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int personId;
    @NotBlank(message = "Name Shouldn't be blank")
    @Size(min = 3)
    private String name;
    @NotBlank(message="Mobile number must not be blank")
    @Pattern(regexp="(^$|[0-9]{10})",message = "Mobile number must be 10 digits")
    private String mobileNumber;
    @PasswordValidator
    @Size(min = 5,message = "Password length should be atleast 5 characters")
    @NotBlank(message = "Password must not be blank")
    @JsonIgnore
    private String pwd;
    @Transient
    @NotBlank(message = "Confirm Password must not be blank")
    @JsonIgnore
    private String confirmPwd;
    @NotBlank(message="Email must not be blank")
    @Email(message = "Please provide a valid email address" )
    private String email;
    @Transient
    @NotBlank
    @NotBlank(message="Confirm Email must not be blank")
    @Email(message = "Please provide a valid email address" )
    @JsonIgnore
    private String confirmEmail;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id",referencedColumnName = "addressId")
    private Address address;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "role_id",referencedColumnName = "roleId",nullable = false)
    private Roles roles;


    @ManyToOne(fetch = FetchType.LAZY,optional = true)
    @JoinColumn(name  = "class_id",referencedColumnName = "classId",nullable = true)
    private EazyClass eazyClass;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinTable(name = "person_courses",
            joinColumns = {
                    @JoinColumn(name = "person_id", referencedColumnName = "personId")},
            inverseJoinColumns = {
                    @JoinColumn(name = "course_id", referencedColumnName = "courseId")})
    private Set<Courses> courses = new HashSet<>();



    public Set<Courses> getCourses() {
        return courses;
    }

    public void setCourses(Set<Courses> courses) {
        this.courses = courses;
    }

    public EazyClass getEazyClass() {
        return eazyClass;
    }

    public void setEazyClass(EazyClass eazyClass) {
        this.eazyClass = eazyClass;
    }


    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Roles getRoles() {
        return roles;
    }

    public void setRoles(Roles roles) {
        this.roles = roles;
    }

    public int getPersonId() {
        return personId;
    }

    public void setPersonId(int personId) {
        this.personId = personId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getConfirmPwd() {
        return confirmPwd;
    }

    public void setConfirmPwd(String confirmPwd) {
        this.confirmPwd = confirmPwd;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getConfirmEmail() {
        return confirmEmail;
    }

    public void setConfirmEmail(String confirmEmail) {
        this.confirmEmail = confirmEmail;
    }
}
