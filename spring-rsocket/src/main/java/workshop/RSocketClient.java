/*
 * Copyright 2019 the original author or authors.
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

import io.rsocket.transport.netty.client.TcpClientTransport;

import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.http.codec.json.Jackson2JsonEncoder;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.messaging.rsocket.RSocketStrategies;
import org.springframework.util.MimeTypeUtils;

/**
 * @author Mark Paluch
 */
public class RSocketClient {

	public static void main(String[] args) {

		rSocketRequester().route("randomNumbers").data(new RandomNumberRequest(10)).retrieveFlux(RandomNumber.class)
				.doOnNext(System.out::println).blockLast();
	}

	static RSocketRequester rSocketRequester() {

		return RSocketRequester.builder().dataMimeType(MimeTypeUtils.APPLICATION_JSON).rsocketStrategies(strategies())
				.connect(TcpClientTransport.create(7000)).block();
	}

	static RSocketStrategies strategies() {
		return RSocketStrategies.builder().decoder(new Jackson2JsonDecoder()).encoder(new Jackson2JsonEncoder()).build();
	}
}
