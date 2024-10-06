package com.in28minutes.rest.webservice.refulwebservice.user;
import org.springframework.hateoas.RepresentationModel;

public class UserResource extends  RepresentationModel<UserResource> {
    private User user;

    public UserResource(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }
}
