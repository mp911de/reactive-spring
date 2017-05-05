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

import static org.assertj.core.api.Assertions.*;

import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.Duration;
import java.util.function.Supplier;

import org.reactivestreams.Publisher;

/**
 * @author Mark Paluch
 */
class Verifications {

	static void verifyDifferentThread(Mono<String> mono, String expectation) {

		StepVerifier.create(mono).consumeNextWith(s -> {

			assertThat(s).isEqualTo(expectation);
			assertThat(Thread.currentThread().getName()).isNotEqualTo("main");

		}).verifyComplete();
	}

	static void verify(Mono<String> mono, String expectedValue) {
		StepVerifier.create(mono).expectNext(expectedValue).verifyComplete();
	}

	static <T> void verifyDelayedEmission(Supplier<? extends Publisher<? extends T>> supplier) {

		StepVerifier.withVirtualTime(supplier).thenAwait(Duration.ofSeconds(50)) //
				.expectNextCount(10) //
				.verifyComplete();
	}
}
