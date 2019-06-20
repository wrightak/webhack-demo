package com.example.webhackdemo;

public class AddAttendeeBody {
    private String attendeeName;

    public AddAttendeeBody() {
    }

    public AddAttendeeBody(String attendeeName) {
        this.attendeeName = attendeeName;
    }

    public String getAttendeeName() {
        return attendeeName;
    }
}
