package com.epam.rd.fp.model;

import com.epam.rd.fp.model.enums.Language;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

    @Override
    public String toString() {
        return "Meeting{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", date='" + date + '\'' +
                ", topics=" + topics +
                ", registeredUsers=" + registeredUsers +
                ", participantsCount=" + participantsCount +
                ", location=" + location +
                ", language=" + language +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Meeting meeting = (Meeting) o;
        return id == meeting.id && registeredUsers == meeting.registeredUsers && participantsCount == meeting.participantsCount && Objects.equals(name, meeting.name) && Objects.equals(date, meeting.date) && Objects.equals(topics, meeting.topics) && Objects.equals(location, meeting.location) && language == meeting.language;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, date, topics, registeredUsers, participantsCount, location, language);
    }
}

