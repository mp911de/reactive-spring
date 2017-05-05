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
import reactor.test.StepVerifier;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import org.junit.Test;

/**
 * Tests to get comfortable with {@link Flux}.
 *
 * @author Mark Paluch
 */
public class Step2Flux {

	@Test
	public void verifyFluxEmission() {

		Flux<String> flux = Flux.just("Skyler", "Walter");

		StepVerifier.create(flux).expectNext("Skyler", "Walter").verifyComplete();
	}

	@Test
	public void verifyInfiniteStreamEmission() {

		Stream<Double> stream = Stream.generate(Math::random);

		Flux<Double> flux = Flux.fromStream(stream);

		StepVerifier.create(flux).expectNextCount(5).thenCancel().verify();
	}

	@Test
	public void fluxShouldCreateHelloWorldFromIterable() {

		List<String> strings = Arrays.asList("Saul", "Mike");

		Flux<String> flux = Flux.fromIterable(strings);

		StepVerifier.create(flux).expectNext("Saul", "Mike").verifyComplete();
	}

	@Test
	public void fluxShouldEmitItemsOverTime() {

		Duration duration = Duration.ofSeconds(2);

		Flux<Long> flux = Flux.interval(duration);

		StepVerifier.create(flux.doOnNext(System.out::println).take(4)).expectNext(0L, 1L, 2L, 3L).verifyComplete();
	}
}
