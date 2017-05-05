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


import java.io.IOException;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.core.MongoOperations;

/**
 * @author Mark Paluch
 */
@SpringBootApplication
public class WorkshopApplication implements CommandLineRunner {

	final MongoOperations mongoOperations;

	public WorkshopApplication(MongoOperations mongoOperations) {
		this.mongoOperations = mongoOperations;
	}

	public static void main(String[] args) throws IOException {
		SpringApplication.run(WorkshopApplication.class, args);
	}

	@Override
	public void run(String... strings) throws Exception {

		mongoOperations.save(new Person("Hank"));
		mongoOperations.save(new Person("Marie"));
		mongoOperations.save(new Person("Jesse"));
		mongoOperations.save(new Person("Walter"));
		mongoOperations.save(new Person("Skyler"));
		mongoOperations.save(new Person("Flynn"));
	}
}
