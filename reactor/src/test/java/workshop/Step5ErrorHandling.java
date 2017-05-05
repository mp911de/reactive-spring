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

import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import org.junit.Test;

/**
 * Tests to handle error cases.
 *
 * @author Mark Paluch
 */
public class Step5ErrorHandling {

	@Test
	public void monoShouldEmitAFallbackValueOnError() {

		Mono<String> failedMono = Mono.error(new IllegalStateException("Something bad happened!"));

		String recoveryValue = "Recovered!";

		// TODO: Replace this line with a fallback value in an error happens
		Mono<String> withErrorHandling = failedMono;

		StepVerifier.create(withErrorHandling).expectNext("Recovered!").verifyComplete();
	}

	@Test
	public void monoShouldEmitADeferredFallbackValueOnError() {

		Mono<String> failedMono = Mono.error(new IllegalStateException("Something bad happened!"));

		Mono<String> recoveryValue = Mono.just("Recovered!");

		// TODO: Replace this line with a fallback that is computed in a deferred way
		Mono<String> withErrorHandling = failedMono;

		StepVerifier.create(withErrorHandling).expectNext("Recovered!").verifyComplete();
	}

	@Test
	public void monoShouldEmitAFallbackValueIfEmpty() {

		Mono<String> emptyMono = Mono.empty();

		Mono<String> recoveryValue = Mono.just("Recovered!");

		// TODO: Replace this line with a fallback that is computed in a deferred way
		Mono<String> withErrorHandling = emptyMono;

		StepVerifier.create(withErrorHandling).expectNext("Recovered!").verifyComplete();
	}

	@Test
	public void monoShouldTranslateException() {

		Mono<String> failedMono = Mono.error(new IllegalStateException("Something bad happened!"));

		// TODO: Replace this line with a Mono translating IllegalStateException to MyBusinessException
		Mono<String> withErrorHandling = failedMono;

		StepVerifier.create(withErrorHandling).expectError(MyBusinessException.class).verify();
	}

	static class MyBusinessException extends Exception {

		public MyBusinessException(Throwable cause) {
			super(cause);
		}
	}
}
