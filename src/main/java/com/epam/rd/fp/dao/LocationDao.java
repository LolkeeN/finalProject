package com.epam.rd.fp.dao;

import com.epam.rd.fp.model.Location;
import com.epam.rd.fp.model.User;
import com.epam.rd.fp.model.enums.Language;
import com.epam.rd.fp.model.enums.Role;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static java.sql.DriverManager.getConnection;

public class LocationDao {
    private static final Logger log = LogManager.getLogger(LocationDao.class);
    private static final String INSERT_LOCATION_INTO_LOCATION_TABLE = "INSERT into location (country, city, street, house, room, language) values (?, ?, ?, ?, ?,?)";
    private static final String GET_LOCATION_ID_BY_ITS_DATA = "SELECT  id from location where country = ? AND city = ? AND street = ? AND house = ? AND room = ?";
    private static final String GET_LOCATION_DATA_BY_ID = "SELECT * FROM location WHERE id = ?";

    public void insertLocation(String connection, Location location) {
        ResultSet rs;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            log.error("No suitable driver found", e);
        }
        try (Connection conn = getConnection(connection);
             PreparedStatement prepStat = conn.prepareStatement(INSERT_LOCATION_INTO_LOCATION_TABLE)) {

            prepStat.setString(1, location.getCountry());
            prepStat.setString(2, location.getCity());
            prepStat.setString(3, location.getStreet());
            prepStat.setString(4, location.getHouse());
            prepStat.setString(5, location.getRoom());
            prepStat.setString(6, location.getLanguage().getValue());
            prepStat.execute();

            try (PreparedStatement prSt = conn.prepareStatement(GET_LOCATION_ID_BY_ITS_DATA)) {
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
        } catch (SQLException e) {
            log.error("Cannot insert location into location table", e);
            throw new IllegalArgumentException("Cannot insert location");
        }
    }

    public Location getLocation(String connection, int id) {
        ResultSet rs;
        Location location = new Location();
        location.setId(id);

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            log.error("No suitable driver found", e);
        }
        try {
            Connection conn = getConnection(connection);
            PreparedStatement prepStat = conn.prepareStatement(GET_LOCATION_DATA_BY_ID);
            prepStat.setInt(1, id);
            rs = prepStat.executeQuery();
            while (rs.next()) {
                location.setId(rs.getInt("id"));
                location.setCountry(rs.getString("country"));
                location.setStreet(rs.getString("street"));
                location.setRoom(rs.getString("room"));
                location.setHouse(rs.getString("house"));
                if (rs.getString("language").equals("EN")) {
                    location.setLanguage(Language.EN);
                } else {
                    location.setLanguage(Language.RU);
                }
            }
        }catch (SQLException e){
            log.error("Cannot get location from location table", e);
            throw new IllegalArgumentException("Cannot get location");
        }
        return location;
    }


}
