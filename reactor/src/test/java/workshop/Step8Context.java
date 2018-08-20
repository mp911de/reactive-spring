/*
 * Copyright 2018 the original author or authors.
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

import static org.assertj.core.api.Assertions.*;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import reactor.util.context.Context;

import java.util.Map.Entry;
import java.util.function.Function;

import org.junit.Test;

/**
 * Testing Reactor {@link reactor.util.context.Context}.
 *
 * @author Mark Paluch
 */
public class Step8Context {

	@Test
	public void verifyContext() {

		Context context = Context.of("username", "HAL");

		assertThat((String) context.get("username")).isEqualTo("HAL");
	}

	@Test
	public void verifyContextUsage() {

		Context context = Context.of("username", "HAL");

		Mono.subscriberContext().map(it -> it.get("username")) //
				.subscriberContext(context) //
				.as(StepVerifier::create) //
				.expectNext("HAL") //
				.verifyComplete();
	}

	@Test
	public void verifyContextUsageDirection() {

		Flux<Object> contextKeys = Mono.subscriberContext()
				.flatMapMany(it -> Flux.fromStream(it.stream().map(Entry::getKey)));

		Context context1 = Context.of("username", "foo");
		Context context2 = Context.of("password", "bar");

		contextKeys.map(it -> "First: " + it) //
				.subscriberContext(context1) //
				.concatWith(contextKeys.map(it -> "Second: " + it)).subscriberContext(context2) //
				.as(StepVerifier::create) //
				.expectNext("First: password") //
				.expectNext("First: username") //
				.expectNext("Second: password") //
				.verifyComplete();
	}
}
