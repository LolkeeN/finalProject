package com.epam.rd.fp.service.impl;

import com.epam.rd.fp.dao.LocationDao;
import com.epam.rd.fp.model.Location;
import com.epam.rd.fp.service.LocationService;

public class LocationServiceImpl implements LocationService {
    private final LocationDao locationDao;

    public LocationServiceImpl(LocationDao locationDao) {
        this.locationDao = locationDao;
    }

    @Override
    public void createLocation(Location location) {
        locationDao.insertLocation(location);
    }

    @Override
    public Location getLocation(int locationId) {
        return locationDao.getLocation(locationId);
    }
}
