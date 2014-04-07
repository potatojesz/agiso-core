/* org.agiso.core.lang.util.logger.log4j.DatabaseLoggerAppender (02-05-2012)
 * 
 * DatabaseLoggerAppender.java
 * 
 * Copyright 2012 agiso.org.
 */
package org.agiso.core.logging.log4j.appender;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Properties;

import javax.sql.DataSource;

import org.agiso.core.beans.util.BeanUtils;
import org.agiso.core.lang.util.StringUtils;
import org.agiso.core.logging.log4j.appender.BaseLoggerAppender;
import org.apache.log4j.helpers.LogLog;
import org.apache.log4j.spi.ErrorCode;
import org.apache.log4j.spi.LoggingEvent;

/**
 * Appender log4j loggera zapisujący logi do bazy danych.
 * 
 * @author Karol Kopacz
 * @since 1.0
 */
public class DatabaseLoggerAppender extends BaseLoggerAppender {
	public static final String XML_FILE_EXTENSION = ".xml";
	public static final long DEFAULT_DATA_SOURCE_BEAN_WAIT_TIME = 0;

//	--------------------------------------------------------------------------
	private Connection conn;

	private String url;
	private String driver;
	private String username;
	private String password;
	private String sqlQuery;

	private String dataSourceBeanName;
	private Long dataSourceBeanWaitTime;

	private String propertiesLocation;
	private String propertiesEncoding;
	private String propertyUrl;
	private String propertyDriver;
	private String propertyUsername;
	private String propertyPassword;
	private String propertySqlQuery;

	private volatile PreparedStatement stmt;

//	--------------------------------------------------------------------------
	public DatabaseLoggerAppender() {
		dataSourceBeanWaitTime = DEFAULT_DATA_SOURCE_BEAN_WAIT_TIME;
	}

//	--------------------------------------------------------------------------
	public void setUrl(String url) {
		this.url = url;
	}

	public void setDriver(String driver) {
		this.driver = driver;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setDataSourceBeanName(String dataSourceBeanName) {
		this.dataSourceBeanName = dataSourceBeanName;
	}

	public void setDataSourceBeanWaitTime(String dataSourceBeanWaitTime) {
		this.dataSourceBeanWaitTime = new Long(dataSourceBeanWaitTime);
	}

	public void setSqlQuery(String sqlQuery) {
		this.sqlQuery = sqlQuery;
	}

	public void setPropertiesEncoding(String propertiesEncoding) {
		this.propertiesEncoding = propertiesEncoding;
	}

	public void setPropertiesLocation(String propertiesLocation) throws IOException {
		this.propertiesLocation = propertiesLocation;
	}

	public void setPropertyUrl(String propertyUrl) {
		this.propertyUrl = propertyUrl;
	}

	public void setPropertyDriver(String propertyDriver) {
		this.propertyDriver = propertyDriver;
	}

	public void setPropertyUsername(String propertyUsername) {
		this.propertyUsername = propertyUsername;
	}

	public void setPropertyPassword(String propertyPassword) {
		this.propertyPassword = propertyPassword;
	}

	public void setPropertySqlQuery(String propertySqlQuery) {
		this.propertySqlQuery = propertySqlQuery;
	}

	protected PreparedStatement getStmt(LoggingEvent event) {
		return stmt;
	}

//	--------------------------------------------------------------------------
	@Override
	public void activateOptions() {
		// Jeśli określono lokalizację pliku z ustawieniami, parametry połączenia
		// określamy na podstawie wartości przypisanych do odpowiednich kluczy:
		if(StringUtils.isNotEmpty(propertiesLocation)) {
			LogLog.debug("Loading appender [" + this.name + "] properties from " + propertiesLocation);

			Properties properties = loadProperties(propertiesLocation);

			if(StringUtils.isEmpty(url) && StringUtils.isNotEmpty(propertyUrl)) {
				url = properties.getProperty(propertyUrl);
			}
			if(StringUtils.isEmpty(driver) && StringUtils.isNotEmpty(propertyDriver)) {
				driver = properties.getProperty(propertyDriver);
			}
			if(StringUtils.isEmpty(username) && StringUtils.isNotEmpty(propertyUsername)) {
				username = properties.getProperty(propertyUsername);
			}
			if(StringUtils.isEmpty(password) && StringUtils.isNotEmpty(propertyPassword)) {
				password = properties.getProperty(propertyPassword);
			}
			if(StringUtils.isEmpty(sqlQuery) && StringUtils.isNotEmpty(propertySqlQuery)) {
				sqlQuery = properties.getProperty(propertySqlQuery);
			}
		}

		// Jeśli nie określono nazwy bean'a (w kontekście aplikacji) źródła danych, tworzymy
		// własne połączenie do bazy danych korzystając z dostarczonych parametrów połączeniowych.
		// W przypadku, gdy nazwa bean'a źródła danych jest określona, wykorzystujemy go
		// do uzyskania połączenia (dopuszczając czas oczekiwania na jego uzyskanie):
		if(StringUtils.isBlank(dataSourceBeanName)) {
			try {
				LogLog.debug("Connecting appender [" + this.name + "] to database url: " + url);

				Class.forName(driver).newInstance();
				conn = DriverManager.getConnection(url, username, password);
				stmt = prepareStatement(conn);

				LogLog.debug("Appender [" + this.name + "] successfully connected to database.");
			} catch(Exception e) {
				errorHandler.error("Error while connecting appender [" + this.name + "] to " + url, e, ErrorCode.GENERIC_FAILURE);
				throw new RuntimeException(e);
			}
		} else {
			LogLog.debug("Connecting appender [" + this.name + "] to spring data source: " + dataSourceBeanName);

			// W trakcie inicjalizacji logger'a podczas startu aplikacji kontekst aplikacji może
			// nie być w pełni przygotowany. Dlatego podczas pobierania bean'a źródła danych
			// pozwalamy na oczekiwanie na dokończenie inicjalizacji, a samo pobranie wykonujemy
			// w osobnym wątku tak, aby nie blokować wątku inicjalizującego:
			Thread thread = new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						DataSource dataSource = BeanUtils.getBean(dataSourceBeanName, DataSource.class, dataSourceBeanWaitTime);
						conn = dataSource.getConnection();
						stmt = prepareStatement(conn);

						LogLog.debug("Appender [" + name + "] successfully connected to spring data source: " + dataSourceBeanName);
					} catch(Exception e) {
						errorHandler.error("Error while connecting appender [" + name + "] to spring data source: "
								+ dataSourceBeanName, e, ErrorCode.GENERIC_FAILURE);
						throw new RuntimeException(e);
					}
				}
			}, this.getClass().getSimpleName() + " connection initializer");

			// Jeśli czas oczekiwania nie jest określony, pobieranie źródła danych uruchamiamy
			// jak zwykłą metodę. Brak źródła danych spowoduje zatrzymanie startowania aplikacji.
			// Jeśli czas oczekiwania jest określony, pobieranie uruchamiamy jako wątek. Brak
			// źródła spowoduje wyświetlenie komunikatu o błędzie, aplikacja się uruchomi, ale
			// nie będzie działał mechanizm logowania do bazy danych.
			if(dataSourceBeanWaitTime == 0) {
				thread.run();
			} else {
				thread.start();
			}
		}
	}

	@Override
	public void close() {
		if(this.closed) {
			return;
		}

		LogLog.debug("Closing appender [" + this.name + "].");
		super.close();

		try {
			if(stmt != null && !stmt.isClosed()) {
				stmt.close();
			}

			if(conn != null && !conn.isClosed()) {
				conn.close();
			}

			LogLog.debug("Appender [" + this.name + "] disconnected from database.");
		} catch(Exception e) {
			LogLog.debug("Error while closing appender [" + this.name + "].", e);
		}
	}

	@Override
	protected LoggingEvent setupEvent(LoggingEvent event) {
		event.getNDC();					// zapamiętanie NDC
		event.getThreadName();			// zapamiętanie nazwy wątku
		event.getMDCCopy();				// skopiowanie MDC
		event.getLocationInformation();	// zapamiętanie lokalizacji
		event.getRenderedMessage();		// zapamiętanie wiadomości
		event.getThrowableStrRep();		// zapamiętanie zrzutu błędu

		return event;
	}

	@Override
	protected synchronized void doLog(LoggingEvent event) {
		PreparedStatement stmt = getStmt(event);
		if(stmt != null) try {
			fillStatement(stmt, event);

			stmt.executeUpdate();
		} catch(SQLException e) {
			errorHandler.error("Failed to flush log", e, ErrorCode.FLUSH_FAILURE);
		}
	}

	protected final PreparedStatement prepareStatement(Connection conn) throws SQLException {
		return conn.prepareStatement(sqlQuery);
	}

	/**
	 * CREATE TABLE `logs` (
	 *   `id` bigint(20) NOT NULL AUTO_INCREMENT,
	 *   `level` varchar(7) NOT NULL,
	 *   `logTime` datetime NOT NULL,
	 *   `message` varchar(511) DEFAULT NULL,
	 *   `thread` varchar(63) DEFAULT NULL,
	 *   `logger` varchar(127) DEFAULT NULL,
	 *   `location` varchar(255) DEFAULT NULL,
	 *   `exception` longtext,
	 *   PRIMARY KEY (`id`)
	 * ) ENGINE=InnoDB CHARSET=utf8;
	 * 
	 * INSERT INTO logs (level, logTime, message, thread, logger, location, exception)
	 *     VALUES(?, ?, ?,, ?, ?, ?, ?);
	 * 
	 * @param stmt
	 * @param event
	 * 
	 * @throws SQLException 
	 */
	protected void fillStatement(PreparedStatement stmt, LoggingEvent event) throws SQLException {
		Object value;
		stmt.setString(1, event.getLevel().toString());					// level
		stmt.setTimestamp(2, new Timestamp(event.getTimeStamp()));		// timestamp
		value = event.getMessage();										// message
		stmt.setString(3, value == null? null : value.toString());
		stmt.setString(4, event.getThreadName());						// thread
		stmt.setString(5, event.getLoggerName());						// logger
		stmt.setString(6, event.getLocationInformation().fullInfo);		// location
		if(null == event.getThrowableStrRep()) {						// exception
			stmt.setString(7, null);
		} else {
			value = new StringBuffer();
			for(String string : event.getThrowableStrRep()) {
				((StringBuffer)value).append(string).append("\n");
			}
			stmt.setString(7, value.toString());
		}
	}

//	--------------------------------------------------------------------------
	/**
	 * @param propertiesLocation
	 * @return
	 */
	private Properties loadProperties(String propertiesLocation) {
		Properties properties = new Properties();

		InputStream is = null;
		try {
			URL url = new URL(null, propertiesLocation, new ClasspathURLStreamHandler());
			is = url.openConnection().getInputStream();

			if(propertiesLocation.endsWith(XML_FILE_EXTENSION)) {
				properties.loadFromXML(is);
			} else {
				if(this.propertiesEncoding != null) {
					properties.load(new InputStreamReader(is, this.propertiesEncoding));
				}
				else {
					properties.load(is);
				}
			}
		} catch(IOException e) {
			errorHandler.error("Could not load properties from " + propertiesLocation + ".", e, ErrorCode.GENERIC_FAILURE);
		} finally {
			if(is != null) {
				try {
					is.close();
				} catch(IOException e) {
				}
			}
		}

		return properties;
	}

//	--------------------------------------------------------------------------
//	http://stackoverflow.com/questions/861500/url-to-load-resources-from-the-classpath-in-java
//	--------------------------------------------------------------------------
	/**
	 * A {@link URLStreamHandler} that handles resources on the classpath.
	 */
	class ClasspathURLStreamHandler extends URLStreamHandler {
		/** The classloader to find resources from. */
		private final ClassLoader classLoader;

		public ClasspathURLStreamHandler() {
			this.classLoader = getClass().getClassLoader();
		}

		public ClasspathURLStreamHandler(ClassLoader classLoader) {
			this.classLoader = classLoader;
		}

		@Override
		protected URLConnection openConnection(URL u) throws IOException {
			final URL resourceUrl = classLoader.getResource(u.getPath());
			return resourceUrl.openConnection();
		}
	}
}
