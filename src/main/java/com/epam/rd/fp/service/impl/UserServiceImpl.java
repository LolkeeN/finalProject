package com.epam.rd.fp.service.impl;

import com.epam.rd.fp.dao.UserDao;
import com.epam.rd.fp.model.User;
import com.epam.rd.fp.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class UserServiceImpl implements UserService {
    private static final Logger log = LogManager.getLogger(TopicServiceImpl.class);
    private final UserDao userDao;

    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public int getSpeakerIdByTopicId(int topicId) {
        return userDao.getSpeakerIdByTopicId(topicId);
    }

    @Override
    public User getUser(String email, String password) {
        return userDao.getUser(email, password);
    }

    @Override
    public boolean isRegistered(int userId, int meetingId) {
        return userDao.isRegistered(userId, meetingId);
    }

    @Override
    public void registerUserForAMeeting(int userId, int meetingId) {
        userDao.registerUserForAMeeting(userId, meetingId);
    }

    @Override
    public boolean isAlreadyRegistered(String email) {
        return userDao.isAlreadyRegistered(email);
    }

    @Override
    public void insertUser(User user) {
        userDao.insertUser(user);
    }
}
