/* org.agiso.core.lang.util.ThreadUtilsUTest (2009-01-07)
 * 
 * ThreadUtilsUTest.java
 * 
 * Copyright 2009 agiso.org.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.agiso.core.lang.util;

import org.agiso.core.lang.util.ThreadUtils;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Testuje poprawność działania metod klasy narzędziowej {@link ThreadUtils}.
 * 
 * http://www.javaworld.com/javaworld/jw-04-2005/jw-0404-testng.html?page=1
 * TODO: Zaimplementować test klasy ThreadUtils
 * 
 * @author <a href="mailto:kkopacz@agiso.org">Karol Kopacz</a>
 * @since 1.0
 */
@Test(singleThreaded = true)
public class ThreadUtilsUTest {
	private int counter = 0;
	private int threadId = 0;

//	--------------------------------------------------------------------------
	/**
	 * 
	 */
	@BeforeMethod void setUpBeforeMethod() throws Exception {
		System.out.println("@BeforeMethod");
	}
	/**
	 * 
	 */
	@AfterMethod void tearDownAfterMethod() throws Exception {
		System.out.println("@AfterMethod");
	}

//	--------------------------------------------------------------------------
	@Test(threadPoolSize = 5, invocationCount = 5)
	public void testGetUniqueThreadId() throws Exception {
		System.out.println("testGetUniqueThreadId");
		synchronized(this) {
			if((threadId++ % 2) == 0) {
				assert counter == ThreadUtils.getUniqueThreadId();
				counter++;
			}
		}

//		JoinedThread threadA = new JoinedThread() {
//			@Override protected void execute() {
//				inThreadAssertEquals(1, ThreadUtils.getUniqueThreadId());
//			}
//		};
//		JoinedThread threadB = new JoinedThread(threadA) {
//			@Override protected void execute() {
//				inThreadAssertEquals(2, ThreadUtils.getUniqueThreadId());
//			}
//		};
//		JoinedThread threadC = new JoinedThread(threadB) {
//			@Override protected void execute() {
//				inThreadAssertEquals(4, ThreadUtils.getUniqueThreadId());
//			}
//		};
//
//		threadA.start();
//		threadC.join();
//
//		assert 0 == ThreadUtils.getUniqueThreadId();
//
//		assert threadA.endSuccessfully();
//		assert threadB.endSuccessfully();
//		assert threadC.endSuccessfully();
	}

	@Test
	public void testXXX() throws Exception {
		System.out.println();
	}

	/**
	 * @throws Exception
	 */
//	public void testAttributes() throws Exception {
////		ThreadUtils.putAttribute("parentId", null, true);
////		ThreadUtils.putAttribute("threadId", ThreadUtils.getUniqueThreadId(), true);
//
//		System.out.println("id:     " + ThreadUtils.getUniqueThreadId() + " " + ThreadUtils.hasInheritedAttributes());
//		System.out.println("parent: " + ThreadUtils.getAttribute("parentId"));
//		System.out.println("thread: " + ThreadUtils.getAttribute("threadId"));
//		System.out.println();
//
//		new TestThread().start();
//		
//		Thread.sleep(10000L);
//
//		System.out.println("id:     " + ThreadUtils.getUniqueThreadId() + " " + ThreadUtils.hasInheritedAttributes());
//		System.out.println("parent: " + ThreadUtils.getAttribute("parentId"));
//		System.out.println("thread: " + ThreadUtils.getAttribute("threadId"));
//		System.out.println("child:  " + ThreadUtils.getAttribute("childId"));
//	}

//	--------------------------------------------------------------------------
	public abstract class JoinedThread extends Thread {
		private Thread parent;
		private boolean success;

		public JoinedThread() {
		}
		public JoinedThread(Thread parent) throws InterruptedException {
			this.parent = parent;
			start();
		}

		public void run() {
			try {
				if(parent != null) {
					parent.join();
				}
				execute();
			} catch(Exception e) {
				throw new RuntimeException(e);
			}
			success = true;
		}

		protected abstract void execute();

		public synchronized boolean endSuccessfully() {
			return success;
		}
	}
}
