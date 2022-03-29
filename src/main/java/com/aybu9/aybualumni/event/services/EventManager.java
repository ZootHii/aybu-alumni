package com.aybu9.aybualumni.event.services;

import com.aybu9.aybualumni.core.result.DataResult;
import com.aybu9.aybualumni.core.result.SuccessDataResult;
import com.aybu9.aybualumni.event.models.Event;
import com.aybu9.aybualumni.event.repositories.EventRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EventManager implements EventService {

    private final EventRepository eventRepository;

    public EventManager(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @Override
    @Transactional
    public DataResult<Event> create(Event event) {
        return new SuccessDataResult<>(eventRepository.save(event), "event create success");
    }
}
