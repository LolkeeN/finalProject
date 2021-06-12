package com.epam.rd.fp.service;

import com.epam.rd.fp.model.Topic;
import com.epam.rd.fp.model.User;

public interface TopicService {
    void bindSpeakerWithFreeTopic(int userId, int topicId);

    void bindSpeakerToTopic(int userId, int topicId);

    void insertTopic(Topic topic);

    void updateTopicAvailability(Topic topic, boolean b);

}
