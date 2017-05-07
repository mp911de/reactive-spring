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
import reactor.core.publisher.Mono;

import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.core.io.buffer.DefaultDataBufferFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;

/**
 * Simple controller with a return body.
 *
 * @author Mark Paluch
 */
@RestController
@RequestMapping("api")
public class WorkshopController {

	private final DataBufferFactory factory = new DefaultDataBufferFactory();

	@GetMapping("resolved")
	public String resolvedReturn() {
		return "Jesse";
	}

	@GetMapping("deferred")
	public Mono<String> deferred() {
		return Mono.just("Jesse");
	}

	@GetMapping("exchange")
	public Mono<Void> exchange(ServerWebExchange exchange) {

		exchange.getResponse().setStatusCode(HttpStatus.BAD_GATEWAY);

		DataBuffer dataBuffer = factory.allocateBuffer();

		dataBuffer.write("foo".getBytes());

		return exchange.getResponse().writeWith(Mono.just(dataBuffer));
	}

	@GetMapping("people")
	public Flux<Person> getPeople(@RequestParam int count) {
		return Flux.range(0, count).map(it -> new Person("" + it));
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
