package com.ruben.woldhuis.androideindopdrachtapp.Models;

import java.io.Serializable;
import java.time.LocalTime;

public class Meetup implements Serializable {
    private String eventName;
    private LocalTime time;
    private Location location;

    public Meetup(String eventName, LocalTime time, Location location) {
        this.eventName = eventName;
        this.time = time;
        this.location = location;
    }

    public String getEventName() {
        return eventName;
    }

    public LocalTime getTime() {
        return time;
    }

    public Location getLocation() {
        return location;
    }
}
