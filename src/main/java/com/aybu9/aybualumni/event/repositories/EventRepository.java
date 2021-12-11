package com.aybu9.aybualumni.event.repositories;

import com.aybu9.aybualumni.event.models.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface 
EventRepository extends JpaRepository<Event, Long> {
}
