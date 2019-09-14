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

import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.reactive.TransactionalOperator;

/**
 * @author Mark Paluch
 */
@Component
public class TransactionalService {

	private final PersonRepository personRepository;
	private final PersonEventRepository eventRepository;
	private final TransactionalOperator operator;

	public TransactionalService(PersonRepository personRepository, PersonEventRepository eventRepository,
			TransactionalOperator operator) {
		this.personRepository = personRepository;
		this.eventRepository = eventRepository;
		this.operator = operator;
	}

	@Transactional
	public Mono<Void> addPerson(String firstname, String lastname) {
		return doInsert(firstname, lastname);
	}

	public Mono<Void> addPersonTxOperator(String firstname, String lastname) {
		return doInsert(firstname, lastname).as(operator::transactional);
	}

	private Mono<Void> doInsert(String firstname, String lastname) {

		Person person = new Person(firstname, lastname);

		return personRepository.save(person).flatMap(it -> {

			return eventRepository.save(new PersonEvent(it.getId(), PersonEvent.EventType.Insert, LocalDateTime.now()));
		}).then();
	}
}
