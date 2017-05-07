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

import reactor.core.publisher.Flux;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.ThreadLocalRandom;

import javax.annotation.PostConstruct;

import org.springframework.data.mongodb.core.CollectionOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Mark Paluch
 */
@RestController
public class LoginEventController {

	final MongoTemplate blockingMongo;
	final ReactiveMongoTemplate mongo;
	final LoginEventRepository eventRepository;

	public LoginEventController(MongoTemplate blockingMongo, ReactiveMongoTemplate mongo,
			LoginEventRepository eventRepository) {
		this.blockingMongo = blockingMongo;
		this.mongo = mongo;
		this.eventRepository = eventRepository;
	}

	@PostConstruct
	public void postConstruct() {

		blockingMongo.dropCollection(LoginEvent.class);
		blockingMongo.createCollection(LoginEvent.class, CollectionOptions.empty().capped().size(2048).maxDocuments(1000));

		Flux.interval(Duration.ofSeconds(2)).flatMap(counter -> {

			return mongo.findAll(Person.class).collectList().map(people -> {

				ThreadLocalRandom random = ThreadLocalRandom.current();

				return people.get(random.nextInt(people.size()));
			});
		}).map(p -> new LoginEvent(p, Instant.now())).flatMap(mongo::save).subscribe();
	}

	@GetMapping(value = "/logins", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
	Flux<LoginEvent> streamEvents() {
		return eventRepository.findPeopleBy();
	}
}
