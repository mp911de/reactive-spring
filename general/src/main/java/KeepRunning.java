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

/**
 * @author Mark Paluch
 */
public class KeepRunning {

	public static class Test extends Thread {

		boolean keepRunning = true;

		public void run() {
			long count = 0;
			while (keepRunning) {
				count++;
			}
			System.out.println("Thread terminated after cycles: " + count);
		}
	}

	public static void main(String[] args) throws InterruptedException {
		Test t = new Test();
		t.start();
		System.out.println("Started");
		Thread.sleep(1000);
		t.keepRunning = false;
		System.out.println("keepRunning set to false.");
	}
}
