package com.in28minutes.survey_question_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.in28minutes.survey_question_app.entities.Survey;

@Repository
public interface SurveyRepository extends JpaRepository<Survey, Integer> {
    
}
