package com.in28minutes.survey_question_app.jpa;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends CrudRepository<User, Long> {

    User[] findByRole(@Param("role") String role);

}
