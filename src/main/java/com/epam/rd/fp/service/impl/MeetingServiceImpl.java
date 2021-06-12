package com.epam.rd.fp.service.impl;

import com.epam.rd.fp.dao.LocationDao;
import com.epam.rd.fp.dao.MeetingDao;
import com.epam.rd.fp.dao.TopicDao;
import com.epam.rd.fp.dao.UserDao;
import com.epam.rd.fp.model.Meeting;
import com.epam.rd.fp.model.Topic;
import com.epam.rd.fp.service.MeetingService;

import java.util.List;

public class MeetingServiceImpl implements MeetingService {
    private final TopicDao topicDao;
    private final MeetingDao meetingDao;

    public MeetingServiceImpl(TopicDao topicDao, MeetingDao meetingDao, LocationDao locationDao, UserDao userDao) {
        this.topicDao = topicDao;
        this.meetingDao = meetingDao;
    }


    @Override
    public void bindSuggestedTopicWithMeeting(int topicId, int meetingId) {
        Topic topic = topicDao.getTopicById(topicId);
        Meeting meeting = meetingDao.getMeetingById(meetingId);
        topicDao.updateTopicDate(topic, meeting.getDate());
        meetingDao.bindTopicIdWithMeetingId(topicId, meeting.getId());
        meetingDao.deleteMeetingAndTopicConnectivityById(topicId, 1);
    }

    @Override
    public void changeMeetingTimeAndPlace(int meetingId, String date, int locationId) {
        Meeting meeting = meetingDao.getMeetingById(meetingId);
        meetingDao.setMeetingDate(meetingId, date);
        meetingDao.setMeetingLocation(meeting.getId(), locationId);
    }

    @Override
    public void changeTopicBySpeaker(int oldTopicId, int newTopicId, int meetingId) {
        meetingDao.updateMeetingTopic(oldTopicId, newTopicId, meetingId);
    }

    @Override
    public void createMeeting(Meeting meeting) {
        meetingDao.insertMeeting(meeting);
        meetingDao.bindLocationIdWithMeetingId(meeting.getLocation().getId(), meeting.getId());
    }

    @Override
    public Meeting getMeetingByName(String meetingName) {
        return meetingDao.getMeetingByName(meetingName);
    }

    @Override
    public void bindTopicIdWithMeetingId(int topicId, int meetingId) {
        meetingDao.bindTopicIdWithMeetingId(topicId, meetingId);
    }

    @Override
    public Meeting getMeetingById(int meetingId) {
        return meetingDao.getMeetingById(meetingId);
    }

    @Override
    public List<Meeting> getAllMeetings() {
        return meetingDao.getAllMeetings();
    }

    @Override
    public int countMeetingParticipants(int meetingId) {
        return meetingDao.countMeetingParticipants(meetingId);
    }

    @Override
    public int countMeetingRegisteredUsers(int meetingId) {
        return meetingDao.countMeetingRegisteredUsers(meetingId);
    }

    @Override
    public List<Topic> getMeetingsTopics(int meetingId) {
        return meetingDao.getMeetingsTopics(meetingId);
    }

    @Override
    public void addMeetingParticipant(int userId, int meetingId) {
        meetingDao.addMeetingParticipant(userId, meetingId);
    }

    @Override
    public void setMeetingDate(int id, String newDate) {
        meetingDao.setMeetingDate(id, newDate);
    }

    @Override
    public void bindLocationIdWithMeetingId(int locationId, int meetingId) {
        meetingDao.bindLocationIdWithMeetingId(locationId, meetingId);
    }


}
