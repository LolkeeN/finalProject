package com.epam.rd.fp.service;

import com.epam.rd.fp.model.Location;

public interface LocationService {
   void createLocation(Location location);


   Location getLocation(int locationId);
}
