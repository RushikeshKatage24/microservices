package com.in28minutes.rest.webservice.refulwebservice.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.in28minutes.rest.webservice.refulwebservice.post.Post;

public interface PostReposetory extends JpaRepository<Post, Integer>{
	
	Post findByUserIdAndId(int userId,int id);
}
