package com.epam.rd.fp.model;

import com.epam.rd.fp.model.enums.Language;

import java.util.Objects;


public class Topic {
    private int id;
    private String name;
    private String description;
    private User speaker;
    private String date;
    private Language language;
    private boolean availability;

    public Topic(String name) {
        this.name = name;
    }

    public Topic() {
    }

    @Override
    public String toString() {
        return "Topic{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", speaker=" + speaker +
                ", date='" + date + '\'' +
                ", language=" + language +
                ", availability=" + availability +
                '}';
    }

    public boolean isAvailability() {
        return availability;
    }

    public void setAvailability(boolean availability) {
        this.availability = availability;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getSpeaker() {
        return speaker;
    }

    public void setSpeaker(User speaker) {
        this.speaker = speaker;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Topic topic = (Topic) o;
        return id == topic.id && availability == topic.availability && Objects.equals(name, topic.name) && Objects.equals(description, topic.description) && Objects.equals(speaker, topic.speaker) && Objects.equals(date, topic.date) && language == topic.language;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, speaker, date, language, availability);
    }
}
