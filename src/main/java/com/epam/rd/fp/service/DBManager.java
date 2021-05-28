package com.epam.rd.fp.service;

import com.epam.rd.fp.model.Location;
import com.epam.rd.fp.model.Meeting;
import com.epam.rd.fp.model.Topic;
import com.epam.rd.fp.model.User;
import com.epam.rd.fp.model.enums.Role;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class DBManager {
    private static final DBManager dbManager = new DBManager();
    //    private static final String APP_SETTINGS = "app.properties";
    private static final String CONNECTION_URL = "jdbc:mysql://localhost:3306/meetings?createDatabaseIfNotExist=true&user=root&password=myrootpass";

    private DBManager() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static DBManager getInstance() {
        return dbManager;
    }

    public Connection getConnection(String conn) throws SQLException {
        return DriverManager.getConnection(conn);
    }

    public void insertUser(User user) throws SQLException {
        ResultSet rs;
        try (Connection conn = getConnection(CONNECTION_URL);
             PreparedStatement prepStat = conn.prepareStatement("INSERT into users (first_name,last_name, email, role, password) values (?, ?, ?,?, ?)")) {

            prepStat.setString(1, user.getFirstName());
            prepStat.setString(2, user.getLastName());
            prepStat.setString(3, user.getEmail());
            prepStat.setInt(4, user.getRole().getValue());
            prepStat.setString(5, user.getPassword());
            prepStat.execute();

            try (PreparedStatement prSt = conn.prepareStatement("SELECT  id from users where email = ?")) {
                prSt.setString(1, user.getEmail());
                rs = prSt.executeQuery();
                while (rs.next()) {
                    user.setId(rs.getInt("id"));
                }
            }

        }
    }

    public void insertMeeting(Meeting meeting) throws SQLException {
        ResultSet rs;
        try (Connection conn = getConnection(CONNECTION_URL);
             PreparedStatement prepStat = conn.prepareStatement("INSERT into meetings (name, date, topic_id, language) values (?, ?, ?, ?)")) {

            prepStat.setString(1, meeting.getName());
            prepStat.setString(2, meeting.getDate());
            prepStat.setObject(3, meeting.getTopics());
            prepStat.setString(4, meeting.getLanguage().getValue());
            prepStat.execute();

            try (PreparedStatement prSt = conn.prepareStatement("SELECT  id from meetings where name = ?")) {
                prSt.setString(1, meeting.getName());
                rs = prSt.executeQuery();
                while (rs.next()) {
                    meeting.setId(rs.getInt("id"));
                }
            }
        }
    }

    public void insertTopic(Topic topic) throws SQLException {
        ResultSet rs;
        try (Connection conn = getConnection(CONNECTION_URL);
             PreparedStatement prepStat = conn.prepareStatement("INSERT into topic (name, date, description, language) values (?, ?, ?, ?)")) {

            prepStat.setString(1, topic.getName());
            prepStat.setString(2, topic.getDate());
            prepStat.setString(3, topic.getDescription());
            prepStat.setString(4, topic.getLanguage().getValue());
            prepStat.execute();

            try (PreparedStatement prSt = conn.prepareStatement("SELECT  id from topic where name = ?")) {
                prSt.setString(1, topic.getName());
                rs = prSt.executeQuery();
                while (rs.next()) {
                    topic.setId(rs.getInt("id"));
                }
            }
        }
    }

    public void insertLocation(Location location) throws SQLException {
        ResultSet rs;
        try (Connection conn = getConnection(CONNECTION_URL);
             PreparedStatement prepStat = conn.prepareStatement("INSERT into location (country, city, street, house, room, language) values (?, ?, ?, ?, ?,?)")) {

            prepStat.setString(1, location.getCountry());
            prepStat.setString(2, location.getCity());
            prepStat.setString(3, location.getStreet());
            prepStat.setString(4, location.getHouse());
            prepStat.setString(5, location.getRoom());
            prepStat.setString(6, location.getLanguage().getValue());
            prepStat.execute();

            try (PreparedStatement prSt = conn.prepareStatement("SELECT  id from location where country = ? AND city = ? AND street = ? AND house = ? AND room = ?")) {
                prSt.setString(1, location.getCountry());
                prSt.setString(2, location.getCity());
                prSt.setString(3, location.getStreet());
                prSt.setString(4, location.getHouse());
                prSt.setString(5, location.getRoom());
                rs = prSt.executeQuery();
                while (rs.next()) {
                    location.setId(rs.getInt("id"));
                }
            }
        }
    }


    //
    protected String findMeetingDateByName(String name) {
        String result = "";
        ResultSet rs;

        try (Connection conn = getConnection(CONNECTION_URL);
             PreparedStatement prepStat = conn.prepareStatement("SELECT date FROM meetings WHERE name = ?")) {
            ;
            prepStat.setString(1, name);
            rs = prepStat.executeQuery();
            while (rs.next()) {
                result = rs.getString("date");
            }
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    //
//    protected int getMeetingParticipantsCount(String name){
//        try {
//            int result = 0;
//            ResultSet rs;
//
//            Connection conn = getConnection(CONNECTION_URL);
//            PreparedStatement prepStat = conn.prepareStatement("SELECT participants_count FROM meetings WHERE name = ?");
//            prepStat.setString(1, name);
//            rs = prepStat.executeQuery();
//            while (rs.next()) {
//                result = rs.getInt("participants_count");
//            }
//            return result;
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return 0;
//    }
//
//    protected int getMeetingRegisteredCount(String name){
//        try {
//            Properties prop = new Properties();
//
//            try (InputStream inputStream = new FileInputStream(APP_SETTINGS)) {
//                prop.load(inputStream);
//            }
//            String url = prop.getProperty(CONNECTION_URL);
//
//            int result = 0;
//            ResultSet rs;
//
//            Connection conn = getConnection(url);
//            PreparedStatement prepStat = conn.prepareStatement("SELECT registered_count FROM meetings WHERE name = ?");
//            prepStat.setString(1, name);
//            rs = prepStat.executeQuery();
//
//            while (rs.next()) {
//                result = rs.getInt("registered_count");
//            }
//            return result;
//
//        } catch (SQLException | IOException e) {
//            e.printStackTrace();
//        }
//        return 0;
//    }
//
//    protected List<String> findAllLogins() {
//        try {
//            Properties prop = new Properties();
//
//            try (InputStream inputStream = new FileInputStream(APP_SETTINGS)) {
//                prop.load(inputStream);
//            }
//            String url = prop.getProperty(CONNECTION_URL);
//
//            List<String> userList = new ArrayList<>();
//
//            ResultSet rs = null;
//            try (Connection conn = getConnection(url);
//                 Statement stat = conn.createStatement()) {
//                rs = stat.executeQuery("SELECT * from users");
//                while (rs.next()) {
//                    userList.add(rs.getString("login"));
//                }
//
//            } catch (SQLException e) {
//                System.out.println(e.getMessage());
//            } finally {
//                try {
//                    if (rs != null) {
//                        rs.close();
//                    }
//                } catch (SQLException e) {
//                    System.out.println(e.getMessage());
//                }
//            }
//
//            return userList;
//        } catch (IOException e) {
//            System.out.println(e.getMessage());
//        }
//        return new ArrayList<>();
//    }
//
//    protected List<String> findAllEmails() {
//        try {
//            Properties prop;
//            prop = new Properties();
//
//            try (InputStream inputStream = new FileInputStream(APP_SETTINGS)) {
//                prop.load(inputStream);
//            }
//            String url = prop.getProperty(CONNECTION_URL);
//
//            List<String> userList = new ArrayList<>();
//
//            ResultSet rs = null;
//            try (Connection conn = getConnection(url);
//                 Statement stat = conn.createStatement()) {
//                rs = stat.executeQuery("SELECT * from users");
//                while (rs.next()) {
//                    userList.add(rs.getString("email"));
//                }
//
//            } catch (SQLException e) {
//                System.out.println(e.getMessage());
//            } finally {
//                try {
//                    if (rs != null) {
//                        rs.close();
//                    }
//                } catch (SQLException e) {
//                    System.out.println(e.getMessage());
//                }
//            }
//
//            return userList;
//        } catch (IOException e) {
//            System.out.println(e.getMessage());
//        }
//        return new ArrayList<>();
//    }
//
//    protected List<String> findUsersByRole(String role) {
//        try {
//            Properties prop = new Properties();
//
//            try (InputStream inputStream = new FileInputStream(APP_SETTINGS)) {
//                prop.load(inputStream);
//            }
//            String url = prop.getProperty(CONNECTION_URL);
//
//            List<String> userList = new ArrayList<>();
//
//            ResultSet rs = null;
//
//            Connection conn = getConnection(url);
//            PreparedStatement prepStat = conn.prepareStatement("SELECT * FROM users WHERE role = ?");
//            prepStat.setString(1, role);
//            rs = prepStat.executeQuery();
//            while (rs.next()) {
//                userList.add(rs.getString("login"));
//            }
//            return userList;
//
//        } catch (SQLException | IOException e) {
//            e.printStackTrace();
//        }
//        return new ArrayList<>();
//    }
//
//    protected String findUserLoginById(long id) {
//        try {
//            Properties prop = new Properties();
//
//            try (InputStream inputStream = new FileInputStream(APP_SETTINGS)) {
//                prop.load(inputStream);
//            }
//            String url = prop.getProperty(CONNECTION_URL);
//
//            String res = "";
//            ResultSet rs = null;
//
//            Connection conn = getConnection(url);
//            PreparedStatement prepStat = conn.prepareStatement("SELECT * FROM users WHERE id = ?");
//            prepStat.setLong(1, id);
//            rs = prepStat.executeQuery();
//            while (rs.next()) {
//                res = rs.getString("login");
//            }
//            return res;
//
//        } catch (SQLException | IOException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//    protected long getUserId(String login) {
//        try {
//            Properties prop = new Properties();
//
//            try (InputStream inputStream = new FileInputStream(APP_SETTINGS)) {
//                prop.load(inputStream);
//            }
//            String url = prop.getProperty(CONNECTION_URL);
//
//            long res = 0;
//            ResultSet rs;
//
//            Connection conn = getConnection(url);
//            PreparedStatement prepStat = conn.prepareStatement("SELECT * FROM users WHERE login = ?");
//            prepStat.setString(1, login);
//            rs = prepStat.executeQuery();
//            while (rs.next()) {
//                res = rs.getLong("id");
//            }
//            return res;
//
//        } catch (SQLException | IOException e) {
//            e.printStackTrace();
//        }
//        return 0;
//    }
//
//    protected long getMeetingId(String name) {
//        try {
//            Properties prop = new Properties();
//
//            try (InputStream inputStream = new FileInputStream(APP_SETTINGS)) {
//                prop.load(inputStream);
//            }
//            String url = prop.getProperty(CONNECTION_URL);
//
//            long res = 0;
//            ResultSet rs;
//
//            Connection conn = getConnection(url);
//            PreparedStatement prepStat = conn.prepareStatement("SELECT id FROM meetings WHERE name = ?");
//            prepStat.setString(1, name);
//            rs = prepStat.executeQuery();
//            while (rs.next()) {
//                res = rs.getLong("id");
//            }
//            return res;
//
//        } catch (SQLException | IOException e) {
//            e.printStackTrace();
//        }
//        return 0;
//    }
//
//    protected void deleteUser(String login) {
//        try {
//            Properties prop = new Properties();
//
//            try (InputStream inputStream = new FileInputStream(APP_SETTINGS)) {
//                prop.load(inputStream);
//            }
//            String url = prop.getProperty(CONNECTION_URL);
//
//
//            try (Connection conn = getConnection(url);
//                 PreparedStatement prepStat = conn.prepareStatement("DELETE from users where login = ? ")) {
//
//                prepStat.setString(1, login);
//                prepStat.execute();
//            }
//        } catch (IOException | SQLException e) {
//            System.out.println(e.getMessage());
//        }
//
//    }
//
//    protected void deleteMeeting(String name) {
//        try {
//            Properties prop = new Properties();
//
//            try (InputStream inputStream = new FileInputStream(APP_SETTINGS)) {
//                prop.load(inputStream);
//            }
//            String url = prop.getProperty(CONNECTION_URL);
//
//
//            try (Connection conn = getConnection(url);
//                 PreparedStatement prepStat = conn.prepareStatement("DELETE from meetings where name = ? ")) {
//
//                prepStat.setString(1, name);
//                prepStat.execute();
//            }
//        } catch (IOException | SQLException e) {
//            System.out.println(e.getMessage());
//        }
//
//    }
//
    public User getUser(String email, String password) throws SQLException {
        ResultSet rs;
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);

        Connection conn = getConnection(CONNECTION_URL);
        PreparedStatement prepStat = conn.prepareStatement("SELECT * FROM users WHERE email = ? AND password = ?");
        prepStat.setString(1, email);
        prepStat.setString(2, password);
        rs = prepStat.executeQuery();
        while (rs.next()) {
            user.setId(rs.getInt("id"));
            user.setEmail(rs.getString("email"));
            user.setRole(Role.USER);
            user.setPassword(rs.getString("password"));
            user.setFirstName(rs.getString("first_name"));
            user.setLastName(rs.getString("last_name"));
        }
        return user;
    }
}