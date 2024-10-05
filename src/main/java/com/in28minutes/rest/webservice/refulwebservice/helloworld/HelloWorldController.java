package com.in28minutes.rest.webservice.refulwebservice.helloworld;

import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {
	
	private MessageSource messageSource;
	
	public HelloWorldController(MessageSource messageSourse) {
		this.messageSource = messageSourse;
	}
	
	@GetMapping("/Hello-World")
	public String helloWorld() {
		return "Hello World";
	}

	@GetMapping("/Hello-World-bean")
	public HelloWorldBean helloWorldBean() {
		return new HelloWorldBean("Hello-World");
	}
	// path Parameters
	// /users/{id}/todos/{id} => /users/1/todos/100
	@GetMapping("/Hello-World/path-veriable/{name}")
	public HelloWorldBean helloWorldPathVeriable(@PathVariable String name) {
		return new HelloWorldBean(String.format("Hello-World ,%s", name));
	}
	
	@GetMapping("/Hello-World-Internationalization")
	public String helloWorldInternationalization() {
		Locale locale = LocaleContextHolder.getLocale();
		return messageSource.getMessage("good.morning.message", null, "Default Message", locale);
		//`en` - English (Good Morning)
		//`nl` - Dutch (Doedemorgen)
		//`Bonjour` - French (Bonjour)
		//`de` - Deutsch (Guten Morgen)
	}
}
