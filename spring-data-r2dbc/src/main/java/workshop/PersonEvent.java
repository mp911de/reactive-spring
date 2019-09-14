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

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;

/**
 * @author Mark Paluch
 */
public class PersonEvent {

	@Id private Long id;

	private long personId;

	private EventType eventType;

	private LocalDateTime eventTime;

	public PersonEvent(long personId, EventType eventType, LocalDateTime eventTime) {
		this.personId = personId;
		this.eventType = eventType;
		this.eventTime = eventTime;
	}

	public enum EventType {
		Insert, Update;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public long getPersonId() {
		return personId;
	}

	public void setPersonId(long personId) {
		this.personId = personId;
	}

	public EventType getEventType() {
		return eventType;
	}

	public void setEventType(EventType eventType) {
		this.eventType = eventType;
	}

	public LocalDateTime getEventTime() {
		return eventTime;
	}

	public void setEventTime(LocalDateTime eventTime) {
		this.eventTime = eventTime;
	}
}
