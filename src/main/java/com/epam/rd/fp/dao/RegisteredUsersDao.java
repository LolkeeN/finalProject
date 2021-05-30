package com.epam.rd.fp.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static java.sql.DriverManager.getConnection;

public class RegisteredUsersDao {
    private static final Logger log = LogManager.getLogger(RegisteredUsersDao.class);
    private static final String CONNECTION_URL = "jdbc:mysql://localhost:3306/meetings?createDatabaseIfNotExist=true&user=root&password=myrootpass";

    public void registerUserForAMeeting(int userId, int meetingId){
        int rowcount = 0;
        ResultSet rs;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            log.error("No suitable driver found", e);
        }
        try {
            Connection conn = getConnection(CONNECTION_URL);
            PreparedStatement preparedStatement = conn.prepareStatement("SELECT COUNT(*) AS rowcount FROM registered_users where meeting_id = ? AND user_id = ?");
            preparedStatement.setInt(1, meetingId);
            preparedStatement.setInt(2, userId);
            rs = preparedStatement.executeQuery();
            while(rs.next()){
                rowcount++;
            }
            if (rowcount != 0){
                log.info("User's already registered");
                throw new IllegalArgumentException("User's already registered");
            }
            PreparedStatement prepStat = conn.prepareStatement("INSERT INTO registered_users (meeting_id, user_id) values (?, ?)");
            prepStat.setInt(1, meetingId);
            prepStat.setInt(2, userId);
            prepStat.execute();
        } catch (SQLException e) {
            log.error("Cannot register user for a meeting", e);
        }
    }
}
