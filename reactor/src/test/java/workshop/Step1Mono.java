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

		Mono<String> mono = Mono.just(jesse);

		verify(mono, jesse);
	}

	@Test
	public void monoShouldDeferCallable() {

		String marie = "Marie";

		Callable<String> myValue = () -> marie;

		Mono<String> mono = Mono.fromCallable(myValue);

		verify(mono, marie);
	}

	@Test
	public void monoShouldExecuteOnADifferentThread() {

		String walter = "Walter";

		Scheduler elastic = Schedulers.elastic();

		Mono<String> mono = Mono.just(walter).subscribeOn(elastic);

		verifyDifferentThread(mono, walter);
	}

	@Test
	public void monoShouldEmitAnError() {

		IllegalStateException exception = new IllegalStateException("Skyler found out about weed");

		Mono<String> mono = Mono.error(exception);

		StepVerifier.create(mono).expectError(IllegalStateException.class).verify();
	}
}
