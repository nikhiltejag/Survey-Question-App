package com.in28minutes.survey_question_app.runners;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.in28minutes.survey_question_app.entities.Question;
import com.in28minutes.survey_question_app.entities.Survey;
import com.in28minutes.survey_question_app.repository.QuestionRepository;
import com.in28minutes.survey_question_app.repository.SurveyRepository;

@Component
public class QuestionCLRunner implements CommandLineRunner {

    @Autowired
    private QuestionRepository qRepository;

    @Autowired
    private SurveyRepository sRepository;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("\n------------Question-Survey Command Line Runner----------");

        Question question1 = new Question(1,
                "Largest Country in the World", "Russia",
                "India", "Russia", "United States", "China");
        Question question2 = new Question(2,
                "Most Populus Country in the World", "China",
                "India", "Russia", "United States", "China");
        Question question3 = new Question(3,
                "Highest GDP in the World", "United States",
                "India", "Russia", "United States", "China");
        Question question4 = new Question(4,
                "Second largest english speaking country", "India", "India", "Russia", "United States", "China");

        sRepository.save(new Survey(1, "New Survey", "New Set of questions",
                qRepository.saveAll(List.of(question1, question2, question3, question4))));
    }

}
