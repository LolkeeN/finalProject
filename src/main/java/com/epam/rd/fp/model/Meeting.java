package com.epam.rd.fp.model;

import com.epam.rd.fp.model.enums.Language;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class Meeting implements Comparable<Meeting> {
    private int id;
    private String name;
    private String date;
    private List<Topic> topics = new ArrayList<>();
    private int registeredUsers;
    private int participantsCount;
    private Location location;
    private Language language;

    public int getRegisteredUsers() {
        return registeredUsers;
    }

    public void setRegisteredUsers(int registeredUsers) {
        this.registeredUsers = registeredUsers;
    }

    public int getParticipantsCount() {
        return participantsCount;
    }

    public void setParticipantsCount(int participantsCount) {
        this.participantsCount = participantsCount;
    }

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

    public void setTopics(List<Topic> topics) {
        this.topics = topics;
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
        this.location = location;
        this.language = language;
    }

    public Meeting() {
    }

    public int compareTo(Meeting o) {
        if (this.getName().equals(o.getName())) {
            return 0;
        } else if (this.getName().length() > o.getName().length()) {
            return 1;
        }
        return -1;
    }



}

