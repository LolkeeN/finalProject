package com.epam.rd.fp.model;

import com.epam.rd.fp.model.enums.Language;

import java.util.List;

public class Meeting {
    private int id;
    private String name;
    private String date;
    private List<Topic> topics;
    private List<User> registeredUsers;
    private Location location;
    private Language language;

}
