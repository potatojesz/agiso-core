/* org.agiso.core.lang.util.logger.log4j.SessionLoggerAppender (2012-09-19)
 * 
 * SessionLoggerAppender.java
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
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.agiso.core.lang.util.DateUtils;
import org.agiso.core.logging.LoggerEvent;
import org.agiso.core.logging.LoggerEventListener;
import org.agiso.core.logging.log4j.appender.BaseLoggerAppender;
import org.apache.log4j.helpers.LogLog;
import org.apache.log4j.spi.ErrorCode;
import org.apache.log4j.spi.LoggingEvent;

/**
 * 
 * 
 * @author <a href="mailto:kkopacz@agiso.org">Karol Kopacz</a>
 * @since 1.0
 */
public class SessionLoggerAppender extends BaseLoggerAppender {
	public static final String SESSION_ID = "session_id";

//	--------------------------------------------------------------------------
	private static SessionLoggerAppender instance;

	private String sessionKey = SESSION_ID;

//	--------------------------------------------------------------------------
	public SessionLoggerAppender() {
		if(instance != null) {
			IllegalStateException e = new IllegalStateException(
					"Olny one instance of class " +
					this.getClass().getSimpleName() + " allowed."
			);
			errorHandler.error("Multiple appenders [" + this.name + "]", e, ErrorCode.GENERIC_FAILURE);
			throw e;
		}
		instance = this;
	}

//	--------------------------------------------------------------------------
	public void setSessionKey(String sessionKey) {
		this.sessionKey = sessionKey;
	}

//	--------------------------------------------------------------------------
	// @Override
	// public boolean requiresLayout() {
	// 	return true;
	// }

	@Override
	protected LoggingEvent setupEvent(LoggingEvent event) {
		// if(this.layout == null) {
		// 	errorHandler.error("No layout for appender " + name, null, ErrorCode.MISSING_LAYOUT);
		// 	return null;
		// }

		Object sessionId = event.getMDC(sessionKey);
		if(null == sessionId) {
			return null;
		}

		List<LoggerEventListener> loggers = SessionLoggerAppender.LOGGERS.get(sessionId);
		if(loggers == null || loggers.isEmpty()) {
			return null;
		}

		event.getNDC();					// zapamiętanie NDC
		event.getThreadName();			// zapamiętanie nazwy wątku
		event.getMDCCopy();				// skopiowanie MDC
		event.getLocationInformation();	// zapamiętanie lokalizacji
		event.getRenderedMessage();		// zapamiętanie wiadomości
		event.getThrowableStrRep();		// zapamiętanie zrzutu błędu

		return event;
	}

	@Override
	protected void doLog(LoggingEvent event) {
		Object sessionId = event.getMDC(sessionKey);
		for(LoggerEventListener logger : SessionLoggerAppender.LOGGERS.get(sessionId)) {
			logger.log(new Log4JSessionEvent(event));
		}
	}

	@Override
	public void close() {
		super.close();

		SessionLoggerAppender.LOGGERS.clear();
	}

//	--------------------------------------------------------------------------
	private static final Map<Object, List<LoggerEventListener>> LOGGERS = new HashMap<Object, List<LoggerEventListener>>();

	/**
	 * @param id
	 * @param loggerEventListener
	 */
	public static synchronized void registerSessionLogger(Object sessionId, LoggerEventListener loggerEventListener) {
		LogLog.debug("Registering listener " + loggerEventListener + " for session " + sessionId + " in appender [" + instance.name + "]");

		List<LoggerEventListener> loggers = SessionLoggerAppender.LOGGERS.get(sessionId);
		if(loggers == null) {
			loggers = new ArrayList<LoggerEventListener>();
			SessionLoggerAppender.LOGGERS.put(sessionId, loggers);
		}

		if(loggers.contains(loggerEventListener)) {
			LogLog.warn("Listener " + loggerEventListener + " for session " + sessionId + " already registered in appender [" + instance.name + "]");
		} else {
			loggers.add(loggerEventListener);
		}
	}

	/**
	 * @param sessionId
	 * @param logger
	 * @return
	 */
	public static synchronized boolean unregisterSessionLogger(Object sessionId, LoggerEventListener loggerEventListener) {
		LogLog.debug("Unegistering listener " + loggerEventListener + " for session " + sessionId + " from appender [" + instance.name + "]");

		List<LoggerEventListener> loggers = SessionLoggerAppender.LOGGERS.get(sessionId);
		return loggers == null? false : loggers.remove(loggerEventListener);
	}

	/**
	 * @param sessionId
	 * @return
	 */
	public static synchronized boolean unregisterSessionLoggers(Object sessionId) {
		LogLog.debug("Unegistering all listeners for session " + sessionId + " from appender [" + instance.name + "]");

		List<LoggerEventListener> loggers = SessionLoggerAppender.LOGGERS.get(sessionId);
		if(loggers == null || loggers.isEmpty()) {
			return false;
		}
		loggers.clear();
		return true;
	}

	public static synchronized Integer countSessionLoggers(Object sessionId) {
		List<LoggerEventListener> loggers = SessionLoggerAppender.LOGGERS.get(sessionId);
		if(loggers == null) {
			return null;
		}
		return loggers.size();
	}

//	--------------------------------------------------------------------------
	/**
	 * Implementacja zdarzenia loggera zdarzeń sesji opakowująca zdarzenie log4j.
	 * 
	 * @author <a href="mailto:kkopacz@agiso.org">Karol Kopacz</a>
	 * @since 1.0
	 */
	private class Log4JSessionEvent implements LoggerEvent {
		private static final long serialVersionUID = 1L;

		private LoggingEvent event;

	//	----------------------------------------------------------------------
		public Log4JSessionEvent(LoggingEvent event) {
			this.event = event;
		}

	//	----------------------------------------------------------------------
		@Override
		public String getLogLevel() {
			return event.getLevel().toString();
		}

		@Override
		public Date getTimeStamp() {
			Calendar calendar;
			try {
				calendar = DateUtils.getCalendar();
			} catch(NoClassDefFoundError ncdfe) {
				calendar = Calendar.getInstance();
			}
			calendar.setTimeInMillis(event.getTimeStamp());
			return calendar.getTime();
		}

		@Override
		public String getMessage() {
			return event.getMessage() == null? "" : event.getMessage().toString();
		}
	}
}
