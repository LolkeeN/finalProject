package com.epam.rd.fp.util;

public class Constants {
    public static final String CONNECTION_URL = "jdbc:mysql://localhost:3306/meetings?createDatabaseIfNotExist=true&user=root&password=myrootpass";

    private Constants() {
        throw new IllegalArgumentException("YOU SHOULD NOT CREATE CONSTANTS!");
    }
}
