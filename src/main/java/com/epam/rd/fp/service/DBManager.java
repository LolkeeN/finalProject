package com.epam.rd.fp.service;

import com.epam.rd.fp.dao.TopicDao;
import com.epam.rd.fp.model.DatabaseProperties;
import com.epam.rd.fp.util.PropertyProvider;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBManager {
    private static final Logger log = LogManager.getLogger(TopicDao.class);
    private static final DBManager dbManager = new DBManager();

    private DBManager() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
        } catch (Exception e) {
            log.error("No suitable driver found", e);
            throw new RuntimeException(e);
        }
    }

    public static DBManager getInstance() {
        if (dbManager == null) {
            return new DBManager();
        }
        return dbManager;
    }

    public Connection getConnection() throws SQLException {
        DatabaseProperties databaseProperties = PropertyProvider.getDatabaseProperties();
        return DriverManager.getConnection(databaseProperties.getUrl(), databaseProperties.getUser(), databaseProperties.getPassword());
    }
}