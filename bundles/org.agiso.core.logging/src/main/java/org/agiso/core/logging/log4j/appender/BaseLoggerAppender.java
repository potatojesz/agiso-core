/* org.agiso.core.lang.util.logger.log4j.BaseLoggerAppender (2012-05-02)
 * 
 * BaseLoggerAppender.java
 * 
 * Copyright 2012 agiso.org.
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
package org.agiso.core.logging.log4j.appender;

import java.util.ArrayList;
import java.util.Iterator;

import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.spi.ErrorCode;
import org.apache.log4j.spi.LoggingEvent;

/**
 * 
 * 
 * @author <a href="mailto:kkopacz@agiso.org">Karol Kopacz</a>
 * @since 1.0
 */
public abstract class BaseLoggerAppender extends AppenderSkeleton {
	/**
	 * size of LoggingEvent buffer before writting to the database.
	 * Default is 1.
	 */
	private int bufferSize = 1;

	/**
	 * ArrayList holding the buffer of Logging Events.
	 */
	private ArrayList<LoggingEvent> buffer;

	/**
	 * Helper object for clearing out the buffer
	 */
	private ArrayList<LoggingEvent> removes;

//	--------------------------------------------------------------------------
	public BaseLoggerAppender() {
		buffer = new ArrayList<LoggingEvent>(bufferSize);
		removes = new ArrayList<LoggingEvent>(bufferSize);
	}

//	--------------------------------------------------------------------------
	@Override
	public boolean requiresLayout() {
		return false;
	}

	@Override
	protected final void append(LoggingEvent event) {
		event = setupEvent(event);
		if(event == null) {
			return;
		}

		buffer.add(event);
		if(buffer.size() >= bufferSize) {
			flushBuffer();
		}
	}

	@Override
	public void close() {
		closed = true;
	}

//	--------------------------------------------------------------------------
	protected LoggingEvent setupEvent(LoggingEvent event) {
		return event;
	}

	protected abstract void doLog(LoggingEvent event);

//	--------------------------------------------------------------------------
	private void flushBuffer() {
		// Do the actual logging:
		removes.ensureCapacity(buffer.size());
		for(Iterator<LoggingEvent> i = buffer.iterator(); i.hasNext();) {
			try {
				LoggingEvent event = (LoggingEvent)i.next();
				doLog(event);
				removes.add(event);
			} catch(Exception e) {
				errorHandler.error("Failed to flush log", e, ErrorCode.FLUSH_FAILURE);
			}
		}

		// Remove from the buffer any events that were reported:
		buffer.removeAll(removes);

		// Clear the buffer of reported events:
		removes.clear();
	}
}
