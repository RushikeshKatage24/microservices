package com.in28minutes.rest.webservice.refulwebservice.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import com.in28minutes.rest.webservice.refulwebservice.user.User;

public interface UserReposetory extends JpaRepository<User, Integer>{
		
}
