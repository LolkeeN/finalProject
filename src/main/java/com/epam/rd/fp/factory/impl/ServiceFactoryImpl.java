package com.epam.rd.fp.factory.impl;

import com.epam.rd.fp.factory.DaoFactory;
import com.epam.rd.fp.factory.ServiceFactory;
import com.epam.rd.fp.service.LocationService;
import com.epam.rd.fp.service.MeetingService;
import com.epam.rd.fp.service.TopicService;
import com.epam.rd.fp.service.UserService;
import com.epam.rd.fp.service.impl.LocationServiceImpl;
import com.epam.rd.fp.service.impl.MeetingServiceImpl;
import com.epam.rd.fp.service.impl.TopicServiceImpl;
import com.epam.rd.fp.service.impl.UserServiceImpl;

public class ServiceFactoryImpl implements ServiceFactory {
    DaoFactory daoFactory = new DaoFactoryImpl();
    MeetingService meetingService;
    LocationService locationService;
    UserService userService;
    TopicService topicService;

    @Override
    public MeetingService getMeetingService() {
        synchronized (this) {
            if (meetingService == null) {
                meetingService = new MeetingServiceImpl(daoFactory.getTopicDao(), daoFactory.getMeetingDao(), daoFactory.getLocationDao(), daoFactory.getUserDao());
            }
        }
        return meetingService;
    }

    @Override
    public LocationService getLocationService() {
        synchronized (this) {
            if ( locationService == null) {
                locationService = new LocationServiceImpl(daoFactory.getLocationDao());
            }
        }
        return locationService;
    }

    @Override
    public TopicService getTopicService() {
        synchronized (this) {
            if (topicService == null) {
                topicService = new TopicServiceImpl(daoFactory.getUserDao(), daoFactory.getTopicDao());
            }
        }
        return topicService;
    }

    @Override
    public UserService getUserService() {
        synchronized (this) {
            if (userService == null) {
                userService = new UserServiceImpl(daoFactory.getUserDao());
            }
        }
        return userService;
    }

}
