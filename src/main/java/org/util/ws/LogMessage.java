package org.util.ws;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author cike
 *
 */
public class LogMessage extends AbstractMessage {

	@Override
	public boolean response(String message) {
		log.info(message);
		return false;
	}

	private final Logger log = LoggerFactory.getLogger(this.getClass());

}
