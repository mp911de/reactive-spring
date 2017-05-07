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

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.test.StepVerifier;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicLong;

import org.junit.Test;

/**
 * Tests to explore async to blocking synchronisation and blocking through reactive APIs.
 *
 * @author Mark Paluch
 */
public class Step7BlockingAndAsync {

	@Test
	public void synchronizeMono() {

		Mono<String> tuco = Mono.just("Tuco").subscribeOn(Schedulers.elastic());

		// TODO: Obtain this value from the Mono above using hard synchronization
		String result = "";

		assertThat(result).isEqualTo("Tuco");
	}

	@Test
	public void synchronizeFlux() {

		Flux<String> salamancas = Flux.just("Hector", "Tuco");

		// TODO: Obtain this value from the Flux above using hard synchronization
		List<String> result = Collections.emptyList();

		assertThat(result).contains("Hector", "Tuco");
	}

	@Test
	public void adoptToCompletableFuture() {

		Mono<String> tuco = Mono.just("Tuco").subscribeOn(Schedulers.elastic());

		// TODO: Replace this line to create a CompletableFuture from Mono
		CompletableFuture<String> future = CompletableFuture.completedFuture("foo");

		future.thenAccept(s -> assertThat(s).isEqualTo("Tuco")).join();
	}

	@Test
	public void blockingToReactive() {

		Callable<String> takesAWhile = () -> {
			Thread.sleep(2000);
			return "Mike";
		};

		// TODO: Observe the output sequence. Use subscribeOn and publishOn to adjust context switching behavior
		Mono<String> naive = Mono.fromCallable(takesAWhile)
				.doOnSubscribe(it -> System.out.println(Thread.currentThread().getName() + ": Subscribe"))
				.doOnSuccess(it -> System.out.println(Thread.currentThread().getName() + ": Success"));

		System.out.println("Before subscribe");
		naive.subscribe();
		System.out.println("After subscribe");
	}

	@Test
	public void reactivePushFutureToMono() {

		CompletableFuture<String> saul = new CompletableFuture<>();

		// TODO: Create a Mono from the CompletableFuture.
		Mono<String> naive = Mono.empty();

		System.out.println("Before subscribe");
		naive.doOnSuccess(System.out::println).subscribe();
		System.out.println("After subscribe");

		// TODO: Move the completion before Mono.subscribe to explore the behavior
		saul.complete("Saul");
	}

	@Test
	public void reactivePullGenerator() {

		Flux<Double> doubleStream = Flux.generate(AtomicLong::new, (o, synchronousSink) -> {

			return o;
		});

		StepVerifier.create(doubleStream).expectNextCount(10).verifyComplete();
	}
}
