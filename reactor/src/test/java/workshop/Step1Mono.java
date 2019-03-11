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

import static workshop.Verifications.*;

import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;
import reactor.test.StepVerifier;

import java.util.concurrent.Callable;

import org.junit.Test;

/**
 * Tests to get comfortable with {@link Mono}.
 *
 * @author Mark Paluch
 */
public class Step1Mono {

	@Test
	public void monoShouldCreateScalarValue() {

		String jesse = "Jesse";

		// TODO: Replace this line with an implementation creates a Mono from the value above
		Mono<String> mono = Mono.empty();

		verify(mono, jesse);
	}

	@Test
	public void monoShouldDeferCallable() {

		String marie = "Marie";

		Callable<String> myValue = () -> marie;

		// TODO: Replace this line by creating a Mono from the Callable above
		Mono<String> mono = Mono.empty();

		verify(mono, marie);
	}

	@Test
	public void monoShouldExecuteOnADifferentThread() {

		String walter = "Walter";

		Scheduler elastic = Schedulers.elastic();

		// TODO: Replace this line by creating a Mono that publishes its value on a
		// different thread applying the Scheduler from above
		Mono<String> mono = Mono.just(walter);

		verifyDifferentThread(mono, walter);
	}

	@Test
	public void monoShouldEmitAnError() {

		IllegalStateException exception = new IllegalStateException("Skyler found out about weed");

		// TODO: Replace this line by creating a Mono that publishes an error
		Mono<String> mono = Mono.empty();

		mono.as(StepVerifier::create).expectError(IllegalStateException.class).verify();
	}
}
