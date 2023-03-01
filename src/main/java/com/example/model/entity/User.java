package com.example.model.entity;

import jakarta.persistence.*;

import java.util.Objects;


@Entity
@Table(name = "users")
public class User {

    public User(String email, String password,String firstName, String lastName, Role role) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public User() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public User(Long id, Role role, String firstName, String lastName, String email, String password, String numberOfTelephone, String region, String address) {
        this.id = id;
        this.role = role;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.numberOfTelephone = numberOfTelephone;
        this.region = region;
        this.address = address;
    }

    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(nullable = false,length = 30)
    private String firstName;

    @Column(nullable = false,length = 30)
    private String lastName;

    @Column(nullable = false,unique = true,length = 50)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false,length = 11)
    private String numberOfTelephone;

    @Column(nullable = false,length = 20)
    private String region;

    @Column(nullable = false,length = 50)
    private String address;

    public String getNumberOfTelephone() {
        return numberOfTelephone;
    }

    public void setNumberOfTelephone(String numberOfTelephone) {
        this.numberOfTelephone = numberOfTelephone;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) && Objects.equals(firstName, user.firstName) && Objects.equals(lastName, user.lastName) && Objects.equals(email, user.email) && Objects.equals(password, user.password) && Objects.equals(role, user.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, email, password, role);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}
