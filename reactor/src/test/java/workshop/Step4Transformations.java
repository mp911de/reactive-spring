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
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import org.junit.Test;

/**
 * Testing reactive transformations.
 *
 * @author Mark Paluch
 */
public class Step4Transformations {

	@Test
	public void monoShouldMapValueToUpperCaseHelloWorld() {

		Mono<String> mono = Mono.just("Heisenberg");

		mono = mono.map(String::toUpperCase);

		StepVerifier.create(mono).expectNext("HEISENBERG").verifyComplete();
	}

	@Test
	public void fluxShouldMapValueToUpperCaseHelloWorld() {

		Flux<String> flux = Flux.just("Mike", "Gustavo");

		flux = flux.map(String::toUpperCase);

		StepVerifier.create(flux).expectNext("MIKE", "GUSTAVO").verifyComplete();
	}

	@Test
	public void monoShouldEmitIndividualCharactersAsString() {

		Mono<String> mono = Mono.just("Schraderbräu");

		Flux<String> flux = mono.map(s -> s.split("")).flatMapMany(Flux::fromArray);

		StepVerifier.create(flux).expectNext("S", "c", "h", "r", "a", "d", "e", "r", "b", "r", "ä", "u").verifyComplete();
	}

	@Test
	public void fluxShouldConcatTwoMonos() {

		Mono<String> hello = Mono.just("Breaking");
		Mono<String> world = Mono.just("Bad");

		Flux<String> flux = Flux.concat(hello, world);

		StepVerifier.create(flux).expectNext("Breaking", "Bad").verifyComplete();
	}
}
