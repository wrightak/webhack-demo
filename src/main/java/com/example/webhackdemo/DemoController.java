package com.example.webhackdemo;

import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
public class DemoController {

    private JdbcTemplate jdbcTemplate;

    public DemoController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @GetMapping("/")
    public String getIndex() {
        return "/index.html";
    }

    @PostMapping("/add-attendee")
    @ResponseStatus(HttpStatus.CREATED)
    public void addAttendee(@RequestBody AddAttendeeBody body) {
        jdbcTemplate.execute("INSERT INTO attendees (name) VALUES ('"+ body.getAttendeeName() +"')");
    }
}
