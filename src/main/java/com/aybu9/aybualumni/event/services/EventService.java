package com.aybu9.aybualumni.event.services;

import com.aybu9.aybualumni.core.result.DataResult;
import com.aybu9.aybualumni.event.models.Event;

public interface EventService {

    DataResult<Event> create(Event event);
}
