package com.kwetter.dto;

import java.util.List;
import java.util.Set;

public class UserDTO {
    private String id;
    private String username;
    private String firstName;
    private String lastName;
    private String biography;
    private String website;
    private String location;
    private Set<String> following;
    private Set<String> followers;
    private List<String> roles;
    private int kweets;

    public UserDTO(){
        //EMPTY CONSTRUCTOR FOR JSON CALLS
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public int getKweets() {
        return kweets;
    }

    public void setKweets(int kweets) {
        this.kweets = kweets;
    }

    public Set<String> getFollowing() {
        return following;
    }

    public void setFollowing(Set<String> following) {
        this.following = following;
    }

    public Set<String> getFollowers() {
        return followers;
    }

    public void setFollowers(Set<String> followers) {
        this.followers = followers;
    }
}
