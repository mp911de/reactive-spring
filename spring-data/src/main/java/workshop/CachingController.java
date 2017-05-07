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

import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Mark Paluch
 */
@RestController
public class CachingController {

	final ReactiveRedisTemplate<Object, Object> redis;

	public CachingController(ReactiveRedisTemplate<Object, Object> redis) {
		this.redis = redis;
	}

	@GetMapping("expensive/{item}")
	public Mono<Object> getOrCreate(@PathVariable String item) {

		Mono<Object> expensiveToCalculate = Mono.fromCallable(() -> {

			Thread.sleep(2000);
			return 42L;
		});

		return redis.hasKey(item).flatMap(exists -> {

			if (exists) {
				return redis.opsForValue().get(item);
			}
			return expensiveToCalculate.flatMap(it -> redis.opsForValue().set(item, it).map(ignored -> (Object) it));
		});
	}
}
