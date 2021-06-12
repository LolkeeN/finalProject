package com.epam.rd.fp.service;

import com.epam.rd.fp.dao.TopicDao;
import com.epam.rd.fp.model.*;
import com.epam.rd.fp.util.PropertyProvider;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;

public class DBManager {
    private static final Logger log = LogManager.getLogger(TopicDao.class);
    private static final DBManager dbManager = new DBManager();

    private DBManager() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            log.error("No suitable driver found", e);
        }
    }

    public static DBManager getInstance() {
        return dbManager;
    }

    public Connection getConnection() throws SQLException {
       DatabaseProperties databaseProperties = PropertyProvider.getDatabaseProperties();
        return DriverManager.getConnection(databaseProperties.getUrl(), databaseProperties.getUser(), databaseProperties.getPassword());
    }








    //
//    protected String findMeetingDateByName(String name) {
//        String result = "";
//        ResultSet rs;
//
//        try (Connection conn = getConnection(CONNECTION_URL);
//             PreparedStatement prepStat = conn.prepareStatement("SELECT date FROM meetings WHERE name = ?")) {
//            ;
//            prepStat.setString(1, name);
//            rs = prepStat.executeQuery();
//            while (rs.next()) {
//                result = rs.getString("date");
//            }
//            return result;
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return result;
//    }

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

}