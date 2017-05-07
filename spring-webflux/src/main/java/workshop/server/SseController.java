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
package workshop.server;

import reactor.core.publisher.Flux;

import java.time.Duration;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller exposing a SSE endpoint.
 *
 * @author Mark Paluch
 */
@RestController
@RequestMapping("sse")
public class SseController {

	@GetMapping(value = "messages", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
	public Flux<Person> getMessages() {
		return Flux.interval(Duration.ofSeconds(1)).map(it -> new Person("" + it));
	}

	static class Person {

		final String name;

		public Person(String name) {
			this.name = name;
		}

		public String getName() {
			return name;
		}
	}
}
