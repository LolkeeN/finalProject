package com.epam.rd.fp.util;

import com.epam.rd.fp.model.DatabaseProperties;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.util.Properties;

public class PropertyProvider {
    public static DatabaseProperties getDatabaseProperties() {
        try (InputStream input = PropertyProvider.class.getClassLoader().getResourceAsStream("application.properties")) {

            Properties properties = new Properties();

            if (input == null) {
                throw new IllegalArgumentException("No such file!");
            }

            properties.load(input);
            DatabaseProperties databaseProperties = new DatabaseProperties();
            databaseProperties.setUrl(properties.getProperty("db.url"));
            databaseProperties.setUser(properties.getProperty("db.user"));
            databaseProperties.setPassword(properties.getProperty("db.password"));
            return databaseProperties;

        } catch (IOException ex) {
            throw new UncheckedIOException(ex);
        }
    }
}
