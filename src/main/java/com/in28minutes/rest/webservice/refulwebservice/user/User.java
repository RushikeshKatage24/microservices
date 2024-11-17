package com.in28minutes.rest.webservice.refulwebservice.user;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.in28minutes.rest.webservice.refulwebservice.post.Post;
//@JsonIgnoreProperties(value = "user_id")
@Entity(name = "user_details")
public class User {
	
	@Id
	@JsonProperty("user_id")
	private Integer id;
	@Size(min = 2 , message = "Name should have atlist 2 charactors")
	@JsonProperty("user_name")
	private String name;
	@Past(message = "Birth Date should be in the past")
	@JsonProperty("birth_date")
	private LocalDate birthDate;
//	@OneToMany(mappedBy = "user",fetch = FetchType.EAGER)
	@OneToMany(mappedBy = "user",fetch = FetchType.LAZY)
//	@JsonIgnore
	private List<Post> post;
	public User(Integer id, String name, LocalDate birthDate) {
		super();
		this.id = id;
		this.name = name;
		this.birthDate = birthDate;
	}
	public User() {
		super();
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public LocalDate getBirthDate() {
		return birthDate;
	}
	public void setBirthDate(LocalDate birthDate) {
		this.birthDate = birthDate;
	}
	public List<Post> getPost() {
		return post;
	}
	public void setPost(List<Post> post) {
		this.post = post;
	}
	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", birthDate=" + birthDate + ", post=" + post + "]";
	}
	
}
