package com.in28minutes.survey_question_app.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.in28minutes.survey_question_app.entities.User;

@Repository
public interface UserRepository extends PagingAndSortingRepository<User, Long> {

    User[] findByRole(@Param("role") String string);

}
