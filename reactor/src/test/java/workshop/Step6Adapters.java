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

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import rx.Observable;
import rx.RxReactiveStreams;
import rx.Single;

import java.util.NoSuchElementException;

import org.junit.Test;

/**
 * Using adapters to interop between reactive composition libraries.
 *
 * @author Mark Paluch
 */
public class Step6Adapters {

	@Test
	public void adoptRxJava1ObservableToFlux() {

		Observable<String> people = Observable.just("Jesse", "Hank");

		Flux<String> flux = Flux.from(RxReactiveStreams.toPublisher(people));

		flux.as(StepVerifier::create).expectNext("Jesse", "Hank").verifyComplete();
	}

	@Test
	public void adoptRxJava1SingleToMono() {

		Single<String> jesse = Single.just("Jesse");

		Mono<String> mono = Mono.from(RxReactiveStreams.toPublisher(jesse));

		mono.as(StepVerifier::create).expectNext("Jesse").verifyComplete();
	}

	@Test
	public void adoptRxJava1EmptySingleToMono() {

		Single<String> empty = Observable.<String> empty().toSingle();

		Mono<String> mono = Mono.from(RxReactiveStreams.toPublisher(empty));

		// Expect a surprise here
		mono.as(StepVerifier::create).verifyError(NoSuchElementException.class);
	}

	@Test
	public void adoptRxJava2ObservableToFlux() {

		io.reactivex.Observable<String> jesse = io.reactivex.Observable.just("Jesse");

		Flux<String> flux = Flux.from(jesse.toFlowable(BackpressureStrategy.BUFFER));

		flux.as(StepVerifier::create).expectNext("Jesse").verifyComplete();
	}

	@Test
	public void adoptFluxToRxJavaObservable() throws Exception {

		Flux<String> people = Flux.just("Jesse", "Hank");

		Observable<String> observable = RxReactiveStreams.toObservable(people);

		observable.test().awaitTerminalEvent().assertResult("Jesse", "Hank");
	}

	@Test
	public void adoptFluxToRxJava2Flowable() throws Exception {

		Flux<String> people = Flux.just("Jesse", "Hank");

		Flowable<String> flowable = Flowable.fromPublisher(people);

		flowable.test().await().assertResult("Jesse", "Hank").awaitTerminalEvent();
	}
}
