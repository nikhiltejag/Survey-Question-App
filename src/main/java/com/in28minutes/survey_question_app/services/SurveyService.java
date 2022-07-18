package com.in28minutes.survey_question_app.services;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.in28minutes.survey_question_app.entities.Question;
import com.in28minutes.survey_question_app.entities.Survey;
import com.in28minutes.survey_question_app.repository.SurveyRepository;

@Component
public class SurveyService {

    @Autowired
    private SurveyRepository surveyRepository;

    private static List<Survey> surveys = new ArrayList<>();

    /*
     * static {
     * Question question1 = new Question(1,
     * "Largest Country in the World", "Russia",
     * "India", "Russia", "United States", "China");
     * Question question2 = new Question(2,
     * "Most Populus Country in the World", "China",
     * "India", "Russia", "United States", "China");
     * Question question3 = new Question(3,
     * "Highest GDP in the World", "United States",
     * "India", "Russia", "United States", "China");
     * Question question4 = new Question(4,
     * "Second largest english speaking country", "India", "India", "Russia",
     * "United States", "China");
     * 
     * List<Question> questions = new ArrayList<>(Arrays.asList(question1,
     * question2, question3, question4));
     * 
     * Survey survey = new Survey("Survey1", "My Favorite Survey",
     * "Description of the Survey", questions);
     * 
     * surveys.add(survey);
     * }
     */

    public List<Survey> retrieveAllSurveys() {
        return surveyRepository.findAll();
    }

    public Optional<Survey> retrieveSurvey(int surveyId) {
        return surveyRepository.findById(surveyId);
    }

    public List<Question> retrieveQuestions(int surveyId) {
        Optional<Survey> survey = retrieveSurvey(surveyId);

        if (survey.isEmpty())
            return null;

        return survey.get().getQuestions();
    }

    public Question retrieveQuestion(int surveyId, int questionId) {

        Optional<Survey> survey = retrieveSurvey(surveyId);

        if (survey.isEmpty())
            return null;

        for (Question question : survey.get().getQuestions()) {
            if (question.getId() == questionId)
                return question;
        }

        return null;
    }

    private SecureRandom random = new SecureRandom();

    public Question addQuestion(int surveyId, Question question) {

        Optional<Survey> survey = retrieveSurvey(surveyId);

        if (survey.isPresent())
            return null;

        int randomId = new BigInteger(130, random).intValue();
        question.setId(randomId);

        survey.get().getQuestions().add(question);
        surveyRepository.save(survey.get());
        return question;
    }
}