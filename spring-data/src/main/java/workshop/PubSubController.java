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

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;

import java.time.Duration;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.connection.ReactiveSubscription.ChannelMessage;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.ReactiveRedisMessageListenerContainer;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Mark Paluch
 */
@RestController
@RequiredArgsConstructor
public class PubSubController {

	final ReactiveRedisMessageListenerContainer listenerContainer;
	final ReactiveRedisOperations<String, String> operations;

	@PostConstruct
	public void postConstruct() {

		Flux.interval(Duration.ofSeconds(2)) //
				.map(Math::toIntExact) //
				.map(it -> String.format("He%s!", StringUtils.repeat('y', it))) //
				.flatMap(message -> operations.convertAndSend("messages", message)) //
				.subscribe();
	}

	@GetMapping(value = "/events/{channel}", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
	Flux<ChannelMessage<String, String>> streamEvents(@PathVariable String channel) {
		return listenerContainer.receive(ChannelTopic.of(channel));
	}
}
