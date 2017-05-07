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
package workshop.client;

import reactor.core.Disposable;
import reactor.core.publisher.Flux;

import java.io.IOException;

import org.springframework.web.reactive.function.client.WebClient;

/**
 * Reactive client for a SSE endpoint.
 *
 * @author Mark Paluch
 */
public class WorkshopSseClient {

	static WebClient client = WebClient.create("http://127.0.0.1:8080");

	public static void main(String[] args) throws IOException {

		Flux<String> flux = client.get() //
				.uri("/sse/messages") //
				.retrieve() //
				.bodyToFlux(String.class) //
				.doOnNext(System.out::println);

		System.out.println("Subscribing to SSE");
		Disposable subscription = flux.subscribe();

		System.out.println("Press any key to terminate subscription...");
		System.in.read();

		subscription.dispose();
	}
}
