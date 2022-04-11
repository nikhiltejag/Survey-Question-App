package com.in28minutes.firstspringbootapplication.jpa;

import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {

    User[] findByRole(String string);

}
