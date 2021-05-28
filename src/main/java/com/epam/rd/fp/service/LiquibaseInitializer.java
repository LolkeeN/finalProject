//package com.epam.rd.fp.service;
//
//import com.epam.rd.fp.service.DBManager;
//import liquibase.Contexts;
//import liquibase.LabelExpression;
//import liquibase.Liquibase;
//import liquibase.database.Database;
//import liquibase.database.DatabaseFactory;
//import liquibase.database.jvm.JdbcConnection;
//import liquibase.exception.DatabaseException;
//import liquibase.exception.LiquibaseException;
//import liquibase.resource.ClassLoaderResourceAccessor;
//
//import javax.servlet.ServletContextEvent;
//import javax.servlet.ServletContextListener;
//import javax.servlet.annotation.WebListener;
//import java.sql.Connection;
//import java.sql.SQLException;
//
//
//@WebListener
//public class LiquibaseInitializer implements ServletContextListener {
//
//    @Override
//    public void contextInitialized(ServletContextEvent sce) {
//        DBManager dbManager = DBManager.getInstance();
//        try (Connection connection = dbManager.getConnection("jdbc:mysql://localhost:3306/meetings?createDatabaseIfNotExist=true&user=root&password=myrootpass")) {
//            Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(connection));
//            Liquibase liquibase = new Liquibase("changelogs/db.changelog-1.0.xml", new ClassLoaderResourceAccessor(), database);
//            liquibase.update("public");
//        } catch (SQLException | LiquibaseException e) {
//            e.printStackTrace();
//        }
//    }
//}
