package com.epam.rd.fp.service;

import com.epam.rd.fp.model.Meeting;
import com.epam.rd.fp.model.Topic;

import java.util.List;

public interface MeetingService {
    void bindSuggestedTopicWithMeeting(int topicId, int meetingId);

    void changeMeetingTimeAndPlace(int meetingId, String date, int locationId);

    void changeTopicBySpeaker(int oldTopicId, int newTopicId, int meetingId);

    void createMeeting(Meeting meeting);

    Meeting getMeetingByName(String meetingName);

    void bindTopicIdWithMeetingId(int topicId, int meetingId);

    Meeting getMeetingById(int meetingName);

    List<Meeting> getAllMeetings();

    int countMeetingParticipants(int meetingId);

    int countMeetingRegisteredUsers(int meetingId);

    List<Topic> getMeetingsTopics(int meetingId);

    void addMeetingParticipant(int userId, int meetingId);

    void setMeetingDate(int id, String newDate);

    void bindLocationIdWithMeetingId(int locationId, int meetingId);
}
