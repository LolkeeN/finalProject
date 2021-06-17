package com.epam.rd.fp.factory;

import com.epam.rd.fp.dao.LocationDao;
import com.epam.rd.fp.dao.MeetingDao;
import com.epam.rd.fp.dao.TopicDao;
import com.epam.rd.fp.dao.UserDao;

public interface DaoFactory {
    LocationDao getLocationDao();

    MeetingDao getMeetingDao();

    TopicDao getTopicDao();

    UserDao getUserDao();

}
