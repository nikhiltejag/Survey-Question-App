package com.in28minutes.survey_question_app.controllers;

import org.json.JSONException;
import org.junit.jupiter.api.BeforeEach;
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
import org.springframework.security.crypto.codec.Base64;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;

import com.in28minutes.survey_question_app.SurveyQuestionAppApplication;
import com.in28minutes.survey_question_app.entities.Question;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = SurveyQuestionAppApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SurveyControllerIT {

    @LocalServerPort
    private int port;

    TestRestTemplate restTemplate = new TestRestTemplate();
    HttpHeaders headers = createdAuthenticationHeader("user1", "secret1");// new HttpHeaders();

    @BeforeEach
    public void beforeAllMethod() {
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
    }

    private HttpHeaders createdAuthenticationHeader(String username, String password) {
        HttpHeaders headers = new HttpHeaders();

        String auth = username + ":" + password;

		byte[] encodedAuth = Base64.encode(auth.getBytes(Charset.forName("US-ASCII")));

		String headerValue = "Basic " + new String(encodedAuth);
        headers.add("Authorization", headerValue);

        return headers;
    }

    @Test
    public void testRetrieveQuestion() throws JSONException {

        String retrieveSpecificQuestion = "/surveys/Survey1/questions/Question1";
        String url = getLocalhostUrl(retrieveSpecificQuestion);

        HttpEntity<String> entity = new HttpEntity<String>(null, headers);

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

        String expected = "{id:Question1}";

        JSONAssert.assertEquals(expected, response.getBody(), false);
    }

    private String getLocalhostUrl(String retrieveSpecificQuestion) {
        return "http://localhost:" + port + retrieveSpecificQuestion;
    }

    @Test
    public void testAddQuestion() throws JSONException {

        String retrieveAllQuestions = "/surveys/Survey1/questions";
        String url = getLocalhostUrl(retrieveAllQuestions);

        Question question = new Question(1, "dummy description", "correctAnswer",
                "option1", "option2", "correctAnswer", "option4");

        HttpEntity<Question> entity = new HttpEntity<Question>(question, headers);

        ResponseEntity<List<Question>> response = restTemplate.exchange(url,
                HttpMethod.POST, entity, new ParameterizedTypeReference<List<Question>>() {
                });

        String actual = response.getHeaders().get(HttpHeaders.LOCATION).get(0);

        assertTrue(actual.contains("surveys/Survey1/questions"));
    }
}
