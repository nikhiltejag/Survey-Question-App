package com.in28minutes.firstspringbootapplication.controllers;

import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;

import com.in28minutes.firstspringbootapplication.*;
import com.in28minutes.firstspringbootapplication.model.Question;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = FirstspringbootapplicationApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SurveyControllerIT {

    @LocalServerPort
    private int port;

    @Test
    public void testRetrieveQuestion() throws JSONException {

        String url = "http://localhost:" + port + "/surveys/Survey1/questions/Question1";

        TestRestTemplate restTemplate = new TestRestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

        HttpEntity<String> entity = new HttpEntity<String>(null, headers);

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

        String expected = "{id:Question1}";

        JSONAssert.assertEquals(expected, response.getBody(), false);
    }

    @Test
    public void testAddQuestion() throws JSONException {

        String url = "http://localhost:" + port + "/surveys/Survey1/questions";

        TestRestTemplate restTemplate = new TestRestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

        Question question = new Question("doesn't matter", "dummy description", "correctAnswer",
                Arrays.asList("option1", "option2", "correctAnswer", "option4"));

        HttpEntity<Question> entity = new HttpEntity<Question>(question, headers);

        ResponseEntity<List<Question>> response = restTemplate.exchange(url,
                HttpMethod.POST, entity, new ParameterizedTypeReference<List<Question>>() {
                });

        String actual = response.getHeaders().get(HttpHeaders.LOCATION).get(0);

        assertTrue(actual.contains("surveys/Survey1/questions"));
    }
}
