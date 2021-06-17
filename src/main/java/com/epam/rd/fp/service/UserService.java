package com.epam.rd.fp.service;

import com.epam.rd.fp.model.User;

public interface UserService {
    int getSpeakerIdByTopicId(int topicId);

    User getUser(String email, String password);

    boolean isRegistered(int userId, int meetingId);

    void registerUserForAMeeting(int userId, int meetingId);

    boolean isAlreadyRegistered(String email);

    void insertUser(User user);
}
