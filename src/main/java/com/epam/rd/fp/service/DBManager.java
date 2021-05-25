package com.epam.rd.fp.service;

import com.epam.rd.fp.model.User;

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
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static DBManager getInstance() {
        return dbManager;
    }

    Connection getConnection() throws SQLException {
        return DriverManager.getConnection(CONNECTION_URL);
    }
//
//    protected void insertUser(User user) throws SQLException {
//        try {
//            Properties prop = new Properties();
//
//            try (InputStream inputStream = new FileInputStream(APP_SETTINGS)) {
//                prop.load(inputStream);
//            }
//            String url = prop.getProperty(CONNECTION_URL);
//
//            ResultSet rs;
//            try (Connection conn = getConnection(url);
//                 PreparedStatement prepStat = conn.prepareStatement("INSERT into users (login, email, role, password) values (?, ?, ?, ?)")) {
//
//                prepStat.setString(1, user.getLogin());
//                prepStat.setString(2, user.getEmail());
//                prepStat.setString(3, user.getRole());
//                prepStat.setString(4, user.getPassword());
//                prepStat.execute();
//
//                try(PreparedStatement prSt = conn.prepareStatement("SELECT  id from users where login = ?")){
//                    prSt.setString(1, user.getLogin());
//                    rs = prSt.executeQuery();
//                    while(rs.next()){
//                        user.setId(rs.getLong("id"));
//                    }
//                }
//
//            }
//        } catch (IOException e) {
//            System.out.println(e.getMessage());
//        }
//    }
//
//    protected void insertMeeting(Meeting meeting) throws SQLException {
//        try {
//            Properties prop = new Properties();
//
//            try (InputStream inputStream = new FileInputStream(APP_SETTINGS)) {
//                prop.load(inputStream);
//            }
//            String url = prop.getProperty(CONNECTION_URL);
//
//            ResultSet rs;
//            try (Connection conn = getConnection(url);
//                 PreparedStatement prepStat = conn.prepareStatement("INSERT into meetings (name, participants_count, registered_count, date) values (?, ?, ?, ?)")) {
//
//                prepStat.setString(1, meeting.getName());
//                prepStat.setInt(2, meeting.getParticipantsCount());
//                prepStat.setInt(3, meeting.getRegisteredCount());
//                prepStat.setString(4, meeting.getDate());
//                prepStat.execute();
//
//                try(PreparedStatement prSt = conn.prepareStatement("SELECT  id from meetings where name = ?")){
//                    prSt.setString(1, meeting.getName());
//                    rs = prSt.executeQuery();
//                    while(rs.next()){
//                        meeting.setId(rs.getLong("id"));
//                    }
//                }
//            }
//        } catch (IOException e) {
//            System.out.println(e.getMessage());
//        }
//    }
//
//    protected String findMeetingDateByName(String name){
//        try {
//            Properties prop = new Properties();
//
//            try (InputStream inputStream = new FileInputStream(APP_SETTINGS)) {
//                prop.load(inputStream);
//            }
//            String url = prop.getProperty(CONNECTION_URL);
//
//            String result = "";
//            ResultSet rs;
//
//            Connection conn = getConnection(url);
//            PreparedStatement prepStat = conn.prepareStatement("SELECT date FROM meetings WHERE name = ?");
//            prepStat.setString(1, name);
//            rs = prepStat.executeQuery();
//            while (rs.next()) {
//                result = rs.getString("date");
//            }
//            return result;
//
//        } catch (SQLException | IOException e) {
//            e.printStackTrace();
//        }
//        return "";
//    }
//
//    protected int getMeetingParticipantsCount(String name){
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
//            PreparedStatement prepStat = conn.prepareStatement("SELECT participants_count FROM meetings WHERE name = ?");
//            prepStat.setString(1, name);
//            rs = prepStat.executeQuery();
//            while (rs.next()) {
//                result = rs.getInt("participants_count");
//            }
//            return result;
//
//        } catch (SQLException | IOException e) {
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
//    public User getUser(String login) {
//        try {
//            Properties prop = new Properties();
//
//            try (InputStream inputStream = new FileInputStream(APP_SETTINGS)) {
//                prop.load(inputStream);
//            }
//            String url = prop.getProperty(CONNECTION_URL);
//
//            ResultSet rs;
//            User user = new User();
//            user.setLogin(login);
//
//            Connection conn = getConnection(url);
//            PreparedStatement prepStat = conn.prepareStatement("SELECT * FROM users WHERE login = ?");
//            prepStat.setString(1, login);
//            rs = prepStat.executeQuery();
//            while (rs.next()) {
//                user.setId(rs.getLong("id"));
//                user.setEmail(rs.getString("email"));
//                user.setRole(rs.getString("role"));
//                user.setPassword(rs.getString("password"));
//            }
//            return user;
//
//        } catch (SQLException | IOException e) {
//            e.printStackTrace();
//        }
//        return new User();
//    }
}