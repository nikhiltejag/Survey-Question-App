package com.in28minutes.survey_question_app.controllers;

import org.apache.tomcat.util.codec.binary.Base64;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.charset.Charset;
import java.util.Arrays;

import com.in28minutes.survey_question_app.model.Question;
import com.in28minutes.survey_question_app.services.SurveyService;

@ExtendWith(SpringExtension.class)
// @WebMvcTest(value = SurveyController.class)
@SpringBootTest
@AutoConfigureMockMvc
public class SurveyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    // @MockBean
    @Mock
    private SurveyService surveyService;

    @Test
    public void testRetrieveQuestion() throws Exception {
        Question mockQuestion = new Question("Question1",
                "Largest Country in the World", "Russia", Arrays.asList(
                        "India", "Russia", "United States", "China"));

        Mockito.when(surveyService.retrieveQuestion(Mockito.anyString(), Mockito.anyString())).thenReturn(mockQuestion);
        
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("http://localhost:8081/surveys/Survey1/questions/Question1")
                .header("Authorization", getHeaderValue("user1", "secret1"))
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        // String expected = "{\"id\":\"Question1\",\"description\":\"Largest Country in
        // the World\",\"correctAnswer\":\"Russia\"}";

        String expected = "{id:Question1,description:\"Largest Country in the World\",correctAnswer:Russia}";
        System.out.println(result.getResponse().getContentAsString());
        JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
    }

    private String getHeaderValue(String username, String password) {
        String headerValue = "Basic " + new String(Base64.
        encodeBase64((username+":"+password).getBytes(Charset.forName("US-ASCII"))));
        return headerValue;
}

@Test
    public void createSurveyQuestion() throws Exception {
        Question mockQuestion = new Question("1", "Just a question", "1", Arrays.asList("1", "2", "3", "4"));

        String questionJSON = "{\"description\":\"Smallest Number\",\"correctAnswer\":\"1\",\"options\":[\"1\",\"2\",\"3\",\"4\"]}";

        Mockito.when(surveyService.addQuestion(Mockito.anyString(), Mockito.any(Question.class)))
                .thenReturn(mockQuestion);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.post(
                "/surveys/Survey1/questions")
                .header("Authorization", getHeaderValue("user1", "secret1"))
                .accept(MediaType.APPLICATION_JSON).content(questionJSON)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = mvcResult.getResponse();

        assertEquals(HttpStatus.CREATED.value(), response.getStatus());

        assertTrue(response
                .getHeader(HttpHeaders.LOCATION).contains("http://localhost/surveys/Survey1/questions/"));
    }
}
