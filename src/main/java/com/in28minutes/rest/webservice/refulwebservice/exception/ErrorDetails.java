package com.in28minutes.rest.webservice.refulwebservice.exception;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class ErrorDetails {
	
	private LocalDateTime localDateTime;
	private String message;
	private String details;
	public ErrorDetails(LocalDateTime localDateTime, String message, String details) {
		super();
		this.localDateTime = localDateTime;
		this.message = message;
		this.details = details;
	}
	public LocalDateTime getTimeStamp() {
		return localDateTime;
	}
	public void setTimeStamp(LocalDateTime localDateTime) {
		this.localDateTime = localDateTime;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getDetails() {
		return details;
	}
	public void setDetails(String details) {
		this.details = details;
	}
}
