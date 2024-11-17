package com.in28minutes.rest.webservice.refulwebservice.user;

import java.net.URI;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.in28minutes.rest.webservice.refulwebservice.jpa.PostReposetory;
import com.in28minutes.rest.webservice.refulwebservice.jpa.UserReposetory;
import com.in28minutes.rest.webservice.refulwebservice.post.Post;

@RestController
public class UserJpaResourceController {

	
	private UserReposetory userRepository;
	
	private PostReposetory postRepository;
	
//	@Autowired
//	private HttpSecurity httpsecurity;
	
	public UserJpaResourceController(UserReposetory userRepository, PostReposetory postRepository) {
		this.userRepository = userRepository;
		this.postRepository = postRepository;
	}
	
	@GetMapping("/jpa/users")
	public List<UserResource> retriveAllUsers() throws HttpRequestMethodNotSupportedException{

		List<UserResource> userResources = userRepository.findAll().stream()
	            .map(t -> {
					try {
						return createUserResource(t);
					} catch (HttpRequestMethodNotSupportedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					return null;
				})
	            .collect(Collectors.toList());

		return userResources;
	}

    private UserResource createUserResource(User user) throws HttpRequestMethodNotSupportedException {
        UserResource userResource = new UserResource(user);
        // Add a self link to getUserById
        userResource.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).retriveOneUser(user.getId())).withSelfRel());
        return userResource;
    }
	@GetMapping("/jpa/users/{id}")
	public EntityModel<User> retriveOneUser(@PathVariable int id) throws HttpRequestMethodNotSupportedException{
		Optional<User> user = userRepository.findById(id);
		if(user==null)
			throw new UserNotFoundException("User Not found::  id : "+id);
		
		EntityModel<User> entityModel = EntityModel.of(user.get());
		
		WebMvcLinkBuilder linkbuilder= WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).retriveAllUsers());
		entityModel.add(linkbuilder.withRel("user_rel"));
		return entityModel;
	}
	
	@PostMapping("/jpa/users")
	public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
	    User savedUSer = userRepository.save(user);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(savedUSer.getId())
				.toUri();
		return ResponseEntity.created(location).build();
	}

	@DeleteMapping("/jpa/users/{id}")
	public void deleteUser(@PathVariable int id) {
		userRepository.deleteById(id);
	}
	


	@GetMapping("/jpa/users/{id}/posts")
	public List<Post> retrivePostUser(@PathVariable int id) throws HttpRequestMethodNotSupportedException{
		Optional<User> user = userRepository.findById(id);
		if(user==null)
			throw new UserNotFoundException("User Not found::  id : "+id);
		return user.get().getPost();
	}
	


	@PostMapping("/jpa/users/{id}/posts")
	public ResponseEntity<Object> retrivePostUser(@PathVariable int id,@Valid @RequestBody Post post) throws HttpRequestMethodNotSupportedException{
		Optional<User> user = userRepository.findById(id);
		if(user==null)
			throw new UserNotFoundException("User Not found::  id : "+id);
		
		post.setUser(user.get());
		Post savedPost = postRepository.save(post);
		
		URI location = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(savedPost.getId())
				.toUri();
		return ResponseEntity.created(location).build();
	}

	@GetMapping("/jpa/users/{id}/posts/{postId}")
	public Post retriveSinglePostForUser(@PathVariable int id,@PathVariable int postId) throws HttpRequestMethodNotSupportedException{

		Post savedPost = postRepository.findByUserIdAndId(id, postId);
		return savedPost;
	}

	
	
}
