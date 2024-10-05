package com.in28minutes.rest.webservice.refulwebservice.person.versioningController;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VersioningPersonController {
	
	@GetMapping("/v1/person")
	private PersonV1 getFirstVersionOfPerson() {
		return new PersonV1("Bob Charlie");
	}

	@GetMapping("/v2/person")
	private PersonV2 getSecondVersionOfPerson() {
		return new PersonV2(new Name("Bob", "Charlie"));
	}

	@GetMapping(path = "/person",params = "version=1")
	private PersonV1 getFirstVersionWithRequestParam() {
		return new PersonV1("Bob Charlie");
	}

	@GetMapping(path = "/person" ,params = "version=2")
	private PersonV2 getSecondVersionWithRequestParam() {
		return new PersonV2(new Name("Bob", "Charlie"));
	}

	@GetMapping(path = "/person/header",headers = "X-API-VERSION=1")
	private PersonV1 getFirstVersionWithHeader() {
		return new PersonV1("Bob Charlie");
	}

	@GetMapping(path = "/person/header",headers = "X-API-VERSION=2")
	private PersonV2 getSecondVersionWithHeader() {
		return new PersonV2(new Name("Bob", "Charlie"));
	}

	@GetMapping(path = "/person/header",produces ="application/vnd.company.app-v1+json")
	private PersonV1 getFirstVersionAcceptHeader() {
		return new PersonV1("Bob Charlie");
	}

	@GetMapping(path = "/person/header",produces ="application/vnd.company.app-v2+json")
	private PersonV2 getSecondVersionAcceptHeader() {
		return new PersonV2(new Name("Bob", "Charlie"));
	}
}
