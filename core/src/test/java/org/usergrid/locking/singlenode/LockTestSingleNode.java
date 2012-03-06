/*******************************************************************************
 * Copyright 2012 Apigee Corporation
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package org.usergrid.locking.singlenode;

import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.usergrid.locking.LockManager;
import org.usergrid.locking.exception.UGLockException;



public class LockTestSingleNode {

	private static final Logger logger = LoggerFactory.getLogger(LockTestSingleNode.class);
	
	private LockManager manager;
	
	private ExecutorService pool;
	
	@Before
	public void setUp() throws Exception {
		
		manager = new SingleNodeLockManagerImpl();
		
		// Create a different thread to lock the same node, that is held by the main thread.
		pool = Executors.newFixedThreadPool(1);
	}
	
	@After
	public void tearDown() throws Exception {
		pool.shutdownNow();
	}

	/**
	 * Locks a path and launches a thread which also locks the same path.
	 * @throws UGLockException 
	 *
	 */
    @Test
	public void testLock() throws InterruptedException, ExecutionException, UGLockException {

		final UUID application = UUID.randomUUID();
		final UUID entity = UUID.randomUUID();

		logger.info("Locking:" + application.toString() + "/" + entity.toString());

		// Lock a node twice to test reentrancy and validate.
		manager.lock(application, entity);
		manager.lock(application, entity);

		boolean wasLocked = lockInDifferentThread(application, entity);
		Assert.assertEquals(false, wasLocked);
		
		// Unlock once
		manager.unlock(application, entity);
		
		// Try from the thread expecting to fail since we still hold one reentrant lock.
		wasLocked = lockInDifferentThread(application, entity);
		Assert.assertEquals(false, wasLocked);
		
		// Unlock completely
		logger.info("Releasing lock:" + application.toString() + "/" + entity.toString());
		manager.unlock(application, entity);
		
		// Try to effectively get the lock from the thread since the current one has already released it.
		wasLocked = lockInDifferentThread(application, entity);
		Assert.assertEquals(true, wasLocked);
	}
    
    /**
     * Locks a couple of times and try to clean up. Later oin another thread successfully acquire the lock
     * 
     */
    @Test
	public void testLock2() throws InterruptedException, ExecutionException, UGLockException {

		final UUID application = UUID.randomUUID();
		final UUID entity = UUID.randomUUID();
		final UUID entity2 = UUID.randomUUID();

		logger.info("Locking:" + application.toString() + "/" + entity.toString());

		// Acquire to locks. One of them twice.
		manager.lock(application, entity);
		manager.lock(application, entity);
		manager.lock(application, entity2);

		// Cleanup the locks for main thread
		logger.info("Cleaning up locks for current thread...");
		manager.cleanupLocksForThread();
		
		boolean wasLocked = lockInDifferentThread(application, entity);
		Assert.assertEquals(true, wasLocked);
		
		wasLocked = lockInDifferentThread(application, entity2);
		Assert.assertEquals(true, wasLocked);
	}

    /**
     * Acquires a lock in a different thread.
     * @param application
     * @param entity
     * @return
     */
	private boolean lockInDifferentThread(final UUID application, final UUID entity) {
		Future<Boolean> status = pool.submit(new Callable<Boolean>() {

			@Override
			public Boolean call() throws Exception {
				
				try {
					manager.lock(application, entity);
					System.out.println("Lock2 acquired");
				} catch (Exception e) {
					return false;
				}

				// False here means that the lock WAS NOT ACQUIRED. And that is
				// what we expect.
				return true;
			}
		});

		boolean wasLocked = true;
		try {
			wasLocked = status.get(2, TimeUnit.SECONDS);
		} catch (Exception e) {
			wasLocked = false;
		} 
		
		return wasLocked;
	}

}
