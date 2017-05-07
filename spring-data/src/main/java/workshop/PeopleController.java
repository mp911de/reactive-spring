/*
 * Copyright 2017 the original author or authors.
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

import io.reactivex.Maybe;
import reactor.core.publisher.Flux;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Mark Paluch
 */
@RestController
public class PeopleController {

	final PersonRepository personRepository;

	public PeopleController(PersonRepository personRepository) {
		this.personRepository = personRepository;
	}

	@GetMapping("/")
	Flux<Person> people() {
		return personRepository.findAll();
	}

	@GetMapping("/people/{name}")
	Maybe<Person> person(@PathVariable String name) {
		return personRepository.findByName(name);
	}

	@GetMapping("/by-name")
	Flux<Person> peopleByNameStartsWith(@RequestParam String name) {
		return personRepository.findPeopleByNameStartsWith(name);
	}
}
