package com.gym.app.service.audit.gymClass;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class EventConditionFilter {
    @Value("${audit.date.start}")
    private String startDate;

    @Value("${audit.date.end}")
    private String endDate;

    public boolean isEventInDateRange(LocalDateTime eventDate) {
        LocalDateTime start = LocalDateTime.parse(startDate);
        LocalDateTime end = LocalDateTime.parse(endDate);

        return eventDate.isAfter(start) && eventDate.isBefore(end);
    }
}
