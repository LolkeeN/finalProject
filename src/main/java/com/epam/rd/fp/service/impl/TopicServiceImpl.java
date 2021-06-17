package com.epam.rd.fp.service.impl;

import com.epam.rd.fp.dao.TopicDao;
import com.epam.rd.fp.dao.UserDao;
import com.epam.rd.fp.model.Topic;
import com.epam.rd.fp.model.User;
import com.epam.rd.fp.model.enums.Role;
import com.epam.rd.fp.service.TopicService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class TopicServiceImpl implements TopicService {
    private final UserDao userDao;
    private final TopicDao topicDao;
    private static final Logger log = LogManager.getLogger(TopicServiceImpl.class);


    public TopicServiceImpl(UserDao userDao, TopicDao topicDao) {
        this.userDao = userDao;
        this.topicDao = topicDao;
    }

    @Override
    public void bindSpeakerWithFreeTopic(int userId, int topicId) {
        Topic topic = topicDao.getTopicById(topicId);
        if (topic.isAvailability()) {
            User user = userDao.getUser(userId);
            if (user.getRole() != Role.SPEAKER) {
                log.warn("Cannot set a topic to non-speaker, user role is not a speaker");
                throw new IllegalArgumentException("Cannot set a topic to non-speaker");
            }
            topicDao.bindTopicWithSpeakerId(topic.getId(), user.getId());
            topicDao.updateTopicAvailability(topic, false);
        } else {
            throw new IllegalArgumentException("Topic with id " + topic.getId() + " is not available");
        }
    }

    @Override
    public void bindSpeakerToTopic(int userId, int topicId) {
        Topic topic = topicDao.getTopicById(topicId);
        User user = userDao.getUser(userId);
        if (user.getRole().getValue() != 3) {
            log.info("Cannot set a topic to non-speaker, user role is not a speaker");
            throw new IllegalArgumentException("Cannot set a topic to non-speaker");
        }
        topicDao.bindTopicWithSpeakerId(topic.getId(), user.getId());
        topicDao.updateTopicAvailability(topic, false);
    }

    @Override
    public void insertTopic(Topic topic) {
        topicDao.insertTopic(topic);
    }

    @Override
    public void updateTopicAvailability(Topic topic, boolean b) {
        topicDao.updateTopicAvailability(topic, b);
    }

    @Override
    public List<Topic> getTopicIdBySpeakerId(int speakerId) {
        return topicDao.getTopicIdBySpeakerId(speakerId);
    }

    @Override
    public List<Topic> getFreeTopics() {
        return topicDao.getFreeTopics();
    }

    @Override
    public List<Topic> getSuggestedTopics() {
        return topicDao.getSuggestedTopics();
    }

}