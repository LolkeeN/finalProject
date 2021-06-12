package com.epam.rd.fp.factory.impl;

import com.epam.rd.fp.dao.LocationDao;
import com.epam.rd.fp.dao.MeetingDao;
import com.epam.rd.fp.dao.TopicDao;
import com.epam.rd.fp.dao.UserDao;
import com.epam.rd.fp.factory.DaoFactory;
import com.epam.rd.fp.service.DBManager;

public class DaoFactoryImpl implements DaoFactory {
    DBManager dbManager = DBManager.getInstance();
    LocationDao locationDao;
    MeetingDao meetingDao;
    TopicDao topicDao;
    UserDao userDao;

    @Override
    public LocationDao getLocationDao() {
        synchronized (this) {
            if (locationDao == null) {
                locationDao = new LocationDao(dbManager);
            }
        }
        return locationDao;
    }

    @Override
    public MeetingDao getMeetingDao() {
        synchronized (this) {
            if (meetingDao == null) {
                meetingDao = new MeetingDao(dbManager);
            }
        }
        return meetingDao;
    }

    @Override
    public TopicDao getTopicDao() {
        synchronized (this) {
            if (topicDao == null) {
                topicDao = new TopicDao(dbManager);
            }
        }
        return topicDao;
    }

    @Override
    public UserDao getUserDao() {
        synchronized (this){
            if (userDao == null){
                userDao = new UserDao(dbManager);
            }
        }
        return userDao;
    }
}
