package com.epam.rd.fp.factory.impl;

import com.epam.rd.fp.factory.DaoFactory;
import com.epam.rd.fp.factory.ServiceFactory;
import com.epam.rd.fp.service.LocationService;
import com.epam.rd.fp.service.MeetingService;
import com.epam.rd.fp.service.TopicService;
import com.epam.rd.fp.service.UserService;
import com.epam.rd.fp.service.impl.MeetingServiceImpl;

public class ServiceFactoryImpl implements ServiceFactory {
    DaoFactory daoFactory = new DaoFactoryImpl();
    MeetingService meetingService;

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
        return null;
    }

    @Override
    public TopicService getTopicService() {
        return null;
    }

    @Override
    public UserService getUserService() {
        return null;
    }

}
