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

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Mark Paluch
 */
public class RandomNumber {

	private final int sequence;
	private final double number;

	public RandomNumber(@JsonProperty("sequence") int sequence, @JsonProperty("number") double number) {
		this.sequence = sequence;
		this.number = number;
	}

	public int getSequence() {
		return sequence;
	}

	public double getNumber() {
		return number;
	}

	@Override
	public String toString() {
		final StringBuffer sb = new StringBuffer();
		sb.append(getClass().getSimpleName());
		sb.append(" [sequence=").append(sequence);
		sb.append(", number=").append(number);
		sb.append(']');
		return sb.toString();
	}
}
