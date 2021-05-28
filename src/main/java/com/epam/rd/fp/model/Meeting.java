package com.epam.rd.fp.model;

import com.epam.rd.fp.model.enums.Language;

import java.util.ArrayList;
import java.util.List;

public class Meeting {
    private int id;
    private String name;
    private String date;
    private List<Topic> topics = new ArrayList<>();
    private List<User> registeredUsers;
    private Location location;
    private Language language;



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<Topic> getTopics() {
        return topics;
    }

    public void addTopic(Topic topic) {
        topics.add(topic);
    }

    public void setTopics(List<Topic> topics) {
        this.topics = topics;
    }

    public List<User> getRegisteredUsers() {
        return registeredUsers;
    }

    public void setRegisteredUsers(List<User> registeredUsers) {
        this.registeredUsers = registeredUsers;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public Meeting(String name, String date, List<Topic> topics, Location location, Language language) {
        this.name = name;
        this.date = date;
        this.topics = topics;
        this.registeredUsers = registeredUsers;
        this.location = location;
        this.language = language;
    }

    public Meeting() {
    }
}
