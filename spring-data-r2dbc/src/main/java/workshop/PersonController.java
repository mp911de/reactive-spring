/*
 * Copyright 2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package workshop;

import static org.springframework.data.r2dbc.query.Criteria.*;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import org.springframework.data.r2dbc.core.DatabaseClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Mark Paluch
 */
@RestController
public class PersonController {

	private final DatabaseClient databaseClient;

	private final PersonRepository people;

	private final PersonEventRepository events;

	private final TransactionalService transactionalService;

	public PersonController(DatabaseClient databaseClient, PersonRepository people, PersonEventRepository events,
			TransactionalService transactionalService) {
		this.databaseClient = databaseClient;
		this.people = people;
		this.events = events;
		this.transactionalService = transactionalService;
	}

	@GetMapping("/")
	public Flux<Person> findAll() {
		return people.findAll();
	}

	@GetMapping("/events")
	public Flux<PersonEvent> findAllEvents() {
		return events.findAll();
	}

	@GetMapping("/by-name/{name}")
	public Mono<Person> findOne(@PathVariable String name) {
		return databaseClient.select().from(Person.class).matching(where("lastname").is(name)).fetch().first();
	}

	@PostMapping("/create/{firstname}/{lastname}")
	public Mono<Void> create(@PathVariable String firstname, @PathVariable String lastname) {
		return transactionalService.addPerson(firstname, lastname);
	}
}
