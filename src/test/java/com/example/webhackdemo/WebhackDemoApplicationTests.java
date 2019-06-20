package com.example.webhackdemo;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.jdbc.JdbcTestUtils.countRowsInTable;
import static org.springframework.test.jdbc.JdbcTestUtils.countRowsInTableWhere;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class WebhackDemoApplicationTests {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Before
    public void setUp() throws Exception {
        jdbcTemplate.execute("TRUNCATE attendees");
    }

    @Test
    public void contextLoads() {
    }

    @Test
    public void index_returnsHelloWorldPage() {
        RequestEntity<Void> requestEntity = RequestEntity
                .get(URI.create("http://localhost:" + port + "/"))
                .build();

        ResponseEntity<String> response = testRestTemplate.exchange(requestEntity, String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).contains("Hello World!");
    }

    @Test
    public void addAttendee_savesToDatabase() {
        assertThat(countRowsInTableWhere(jdbcTemplate, "attendees", "name = 'andrew'")).isEqualTo(0);

        String body = "{\n" +
                "  \"attendeeName\": \"andrew\"\n" +
                "}";

        RequestEntity<String> addAttendeeRequest = RequestEntity
                .post(URI.create("http://localhost:" + port + "/add-attendee"))
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(body);

        ResponseEntity<Void> addAttendeeResponse = testRestTemplate.exchange(addAttendeeRequest, Void.class);

        assertThat(addAttendeeResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        assertThat(countRowsInTableWhere(jdbcTemplate, "attendees", "name = 'andrew'")).isEqualTo(1);

    }
}
