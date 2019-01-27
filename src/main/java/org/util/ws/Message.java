package org.util.ws;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * 
 * @author cike
 *
 */
public abstract class Message {

	public abstract boolean response(String message);

	/**
	 * 
	 * @param message
	 * @throws IOException
	 */
	public void handle(String message) {
		this.result.add(message);
		if (response(message)) {
			latch.countDown();
		}
	}

	/**
	 * 
	 * @return
	 * @throws InterruptedException
	 */
	public String result() throws InterruptedException {
		result = new ArrayList<String>();
		latch = new CountDownLatch(1);
		latch.await(1, TimeUnit.SECONDS);
		return result.toString();
	}

	protected List<String> result;
	private CountDownLatch latch;

}
