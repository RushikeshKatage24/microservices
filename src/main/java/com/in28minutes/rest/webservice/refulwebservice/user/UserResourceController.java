package com.in28minutes.rest.webservice.refulwebservice.user;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.hibernate.EntityMode;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
public class UserResourceController {
	
	private UserDaoService userDaoService;
	
	public UserResourceController(UserDaoService userDaoService) {
		this.userDaoService =userDaoService;
	}
	
	@GetMapping("/users")
	public CollectionModel<UserResource> retriveAllUsers(UserDaoService userDaoService) throws HttpRequestMethodNotSupportedException{

		List<UserResource> userResources = userDaoService.findAll().stream()
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

		
		CollectionModel<UserResource> collectionModel = CollectionModel.of(userResources);
        collectionModel.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).retriveAllUsers(userDaoService)).withSelfRel());

		return collectionModel;
	}


    private UserResource createUserResource(User user) throws HttpRequestMethodNotSupportedException {
        UserResource userResource = new UserResource(user);
        // Add a self link to getUserById
        userResource.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UserResourceController.class).retriveOneUser(user.getId())).withSelfRel());
        return userResource;
    }
	@GetMapping("/users/{id}")
	public EntityModel<User> retriveOneUser(@PathVariable int id) throws HttpRequestMethodNotSupportedException{
		User user = userDaoService.findOne(id);
		if(user == null)
			throw new UserNotFoundException("User Not found::  id : "+id);
		
		EntityModel<User> entityModel = EntityModel.of(user);
		
		WebMvcLinkBuilder linkbuilder= WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).retriveAllUsers(userDaoService));
		entityModel.add(linkbuilder.withRel("user_rel"));
		return entityModel;
	}
	
	@PostMapping("/users")
	public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
	    User savedUSer = userDaoService.addUser(user);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(savedUSer.getId())
				.toUri();
		return ResponseEntity.created(location).build();
	}

	@DeleteMapping("/users/{id}")
	public void deleteUser(@PathVariable int id) {
		userDaoService.deleteById(id);
	}
	
}
