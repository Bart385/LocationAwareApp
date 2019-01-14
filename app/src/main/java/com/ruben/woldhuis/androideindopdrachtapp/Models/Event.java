package com.ruben.woldhuis.androideindopdrachtapp.Models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.util.ArrayList;

@Entity(tableName = "EVENT_MODEL")
public class Event {
    @ColumnInfo(name = "event_location")
    private Location location;
    @ColumnInfo(name = "event_name")
    private String eventName;
    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "event_uid")
    private String eventUID;
    @ColumnInfo(name = "event_date")
    private String expirationDateAsString;
    @ColumnInfo(name = "event_creator")
    private User eventCreator;
    @ColumnInfo(name = "event_subscribers")
    private ArrayList<String> subscribedUserUIDs;

    public Event(Location location, String eventName, @NonNull String eventUID,
                 String expirationDateAsString, User eventCreator) {
        this.location = location;
        this.eventName = eventName;
        this.eventUID = eventUID;
        this.expirationDateAsString = expirationDateAsString;
        this.eventCreator = eventCreator;
        subscribedUserUIDs = new ArrayList<>();
    }

    public Location getLocation() {
        return location;
    }

    public String getEventName() {
        return eventName;
    }

    @NonNull
    public String getEventUID() {
        return eventUID;
    }

    public String getExpirationDateAsString() {
        return expirationDateAsString;
    }

    public User getEventCreator() {
        return eventCreator;
    }

    public ArrayList<String> getSubscribedUserUIDs() {
        return subscribedUserUIDs;
    }

    public void setSubscribedUserUIDs(ArrayList<String> subscribedUserUIDs) {
        this.subscribedUserUIDs = subscribedUserUIDs;
    }

    @Ignore
    @Override
    public String toString() {
        return "Event{" +
                "location=" + location +
                ", eventName='" + eventName + '\'' +
                ", eventUID='" + eventUID + '\'' +
                ", expirationDateAsString='" + expirationDateAsString + '\'' +
                ", eventCreator=" + eventCreator +
                '}';
    }
}
