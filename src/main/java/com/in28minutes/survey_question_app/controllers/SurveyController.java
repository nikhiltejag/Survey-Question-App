package com.in28minutes.survey_question_app.controllers;

import java.net.URI;
import java.util.List;

import com.in28minutes.survey_question_app.entities.Question;
import com.in28minutes.survey_question_app.services.SurveyService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
public class SurveyController {

    @Autowired
    public SurveyService surveyService;

    @GetMapping("/surveys/{surveyId}/questions")
    public List<Question> retrieveAllQuestions(@PathVariable String surveyId) {
        return surveyService.retrieveQuestions(surveyId);
    }

    @PostMapping("/surveys/{surveyId}/questions")
    public ResponseEntity<?> addQuestion(@PathVariable String surveyId, @RequestBody Question newQuestion) {

        Question question = surveyService.addQuestion(surveyId, newQuestion);

        if (question == null)
            return ResponseEntity.noContent().build();

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(question.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @GetMapping("/surveys/{surveyId}/questions/{questionId}")
    public Question retrieveQuestion(@PathVariable String surveyId, @PathVariable int questionId) {
        return surveyService.retrieveQuestion(surveyId, questionId);
    }
}
