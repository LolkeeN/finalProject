package com.epam.rd.fp.factory;

import com.epam.rd.fp.service.LocationService;
import com.epam.rd.fp.service.MeetingService;
import com.epam.rd.fp.service.TopicService;
import com.epam.rd.fp.service.UserService;

public interface ServiceFactory {
    MeetingService getMeetingService();
    LocationService getLocationService();
    TopicService getTopicService();
    UserService getUserService();
}
