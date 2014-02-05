/* org.agiso.core.lang.util.DateUtils (2009-02-06)
 * 
 * DateUtils.java
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

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Random;
import java.util.TimeZone;

/**
 * Klasa narzędziowa dostarczająca funkcjonalności związanych z datą i czasem.
 * 
 * @author Karol Kopacz
 * @since 1.0
 */
public abstract class DateUtils {
	/**
	 * Interfejs fabryki dostarczającej kalendarz wykorzystywany do pobierania
	 * daty i czasu.
	 */
	public interface ICalendarFactory {
		public Calendar getCalendar();
		public Calendar getCalendar(Locale locale);
		public Calendar getCalendar(TimeZone zone);
		public Calendar getCalendar(TimeZone zone, Locale locale);
	}

//	--------------------------------------------------------------------------
	private static ICalendarFactory calendarFactory;

	private static final DateFormat dateFormat;
	private static final DateFormat timeFormat;
	private static final DateFormat dateTimeFormat;


	private static final Random r = new Random();
	private static final ICalendarFactory internalCalendarFactory = new ICalendarFactory() {
		public Calendar getCalendar() {
			return Calendar.getInstance();
		}
		public Calendar getCalendar(Locale locale) {
			return Calendar.getInstance(locale);
		}
		public Calendar getCalendar(TimeZone zone) {
			return Calendar.getInstance(zone);
		}
		public Calendar getCalendar(TimeZone zone, Locale locale) {
			return Calendar.getInstance(zone, locale);
		}
	};

	static {
		calendarFactory = internalCalendarFactory;

		dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		timeFormat = new SimpleDateFormat("HH:mm:ss");
		dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	}

//	--------------------------------------------------------------------------
	/**
	 * Zwraca wykorzystywaną przez klasę narzędziową fabrykę kalendarzy, lub
	 * <code>null</code> jeśli nie była ona ustawiona i wykorzystywana jest
	 * fabryka wbudowana.
	 * 
	 * @return the calendarFactory
	 */
	public static ICalendarFactory getCalendarFactory() {
		if(calendarFactory == internalCalendarFactory) {
			return null;
		} else {
			return calendarFactory;
		}
	}

	/**
	 * Ustawia fabrykę obiektów {@link Calendar} wykorzystywanych przez klasę
	 * narzędziową. Jeśli metoda nie zostanie wywołana wykorzystywany będzie
	 * kalenarz dostarczaney przez metodę {@link Calendar#getInstance()}.<br/>
	 * Wywołanie metody z wartością <code>null</code> przywraca mechanizm wbudowany.
	 * 
	 * @param factory Fabryka kalendarzy.
	 */
	public static void setCalendarFactory(ICalendarFactory factory) {
		if(factory != null) {
			calendarFactory = factory;
		} else {
			calendarFactory = internalCalendarFactory;
		}
	}

//	--------------------------------------------------------------------------
	/**
	 * Pobiera aktualną datę.
	 * 
	 * @return Obiekt {@link Timestamp} reprezentujący aktualną datę.
	 */
	public static Date getDate() {
		return getDate(getCalendar());
	}
	public static Date getDate(Calendar calendar) {
		return new Timestamp(calendar.getTimeInMillis());
	}

	/**
	 * Pobiera aktualną datę zaokrągloną do pełnego dnia.
	 * 
	 * @return Obiekt {@link Timestamp} reprezentujący aktualną datę dzienną.
	 */
	public static Date getDayDate() {
		return getDayDate(getCalendar());
	}
	/**
	 * Zaokrągla przekazaną datę do pełnego dnia.
	 * 
	 * @param date Data do zaokrąglenia.
	 * @return Obiekt {@link Timestamp} reprezentujący zaokrągloną datę.
	 */
	public static Date getDayDate(Date date) {
		Calendar calendar = getCalendar();
		calendar.setTime(date);
		return getDayDate(calendar);
	}
	/**
	 * Zaokrągla datę przekazanego kalendarza do pełnego dnia uwzględniając
	 * przesunięcie strefy czasowej (ZONE_OFFSET) i czasu letniego (DST_OFFSET).
	 * 
	 * @param calendar Kalendarz z datą do zaokrąglenia.
	 * @return Obiekt {@link Timestamp} reprezentujący zaokrągloną datę.
	 */
	public static Date getDayDate(Calendar calendar) {
		int offset = calendar.get(Calendar.ZONE_OFFSET) + calendar.get(Calendar.DST_OFFSET);
		return new Timestamp(((calendar.getTimeInMillis() + offset) / 86400000 * 86400000) - offset);
	}

	/**
	 * Pobiera aktualną datę zaokrągloną do pełnych sekund.
	 * 
	 * @return Obiekt {@link Timestamp} reprezentujący aktualną datę zaokrągloną
	 *     do pełnych sekund.
	 */
	public static Date getTimestamp() {
		return getTimeStamp(getCalendar());
	}
	public static Date getTimeStamp(Calendar calendar) {
		return new Timestamp(calendar.getTimeInMillis() / 1000 * 1000);
	}

	/**
	 * Pobiera pseudolosową datę.
	 * 
	 * @return Losowo wygenerowana data.
	 */
	public static Date getRandomDate() {
		return getRandomDate(r);
	}
	/**
	 * Pobiera pseudolosową datę do generacji której wykorzystany zostanie
	 * przekazany generator.
	 * 
	 * @param random
	 * @return
	 */
	public static Date getRandomDate(Random random) {
		return new Date(Math.abs(random.nextLong()));
	}
	/**
	 * Pobiera pseudolosową datę z określonego przedziału czasowego.
	 * 
	 * @param begin Data początkowa.
	 * @param end Data końcowa.
	 * @return Losowo wygenerowana data.
	 */
	public static Date getRandomDate(Date begin, Date end) {
		return getRandomDate(begin, end, r);
	}
	/**
	 * Pobiera pseudolosową datę z okreslonego przedziału czasowego do generacji
	 * której wykorzystany zostanie przekazany generator. Data generowana jest
	 * z dokładnością (ziarnem) wynoszącym 1ms (tj. 1 w reprezentacji daty w formie
	 * liczyby typu <code>long</code>).
	 * 
	 * @param begin Data początkowa przedziału (włączona do zbioru wynikowego).
	 * @param end Data końcowa przedziału (wyłączona ze zbioru wynikowego).
	 * @param random Generator pseudolosowy wykorzystywany do pozyskania daty.
	 * @return Losowo wygenerowana data z przedziału [begin; end).
	 */
	public static Date getRandomDate(Date begin, Date end, Random random) {
		long delay = end.getTime() - begin.getTime();
		return new Date(begin.getTime() + (Math.abs(random.nextLong() % delay)));
	}

//	--------------------------------------------------------------------------
	/**
	 * @return Obiekt kalendarza przygotowany przez ustawioną fabrykę.
	 */
	public static Calendar getCalendar() {
		if(calendarFactory == null) {
			return internalCalendarFactory.getCalendar();
		} else {
			return calendarFactory.getCalendar();
		}
	}
	/**
	 * @return Obiekt kalendarza przygotowany przez ustawioną fabrykę.
	 */
	public static Calendar getCalendar(Locale locale) {
		if(calendarFactory == null) {
			return internalCalendarFactory.getCalendar(locale);
		} else {
			return calendarFactory.getCalendar(locale);
		}
	}
	/**
	 * @return Obiekt kalendarza przygotowany przez ustawioną fabrykę.
	 */
	public static Calendar getCalendar(TimeZone zone) {
		if(calendarFactory == null) {
			return internalCalendarFactory.getCalendar(zone);
		} else {
			return calendarFactory.getCalendar(zone);
		}
	}
	/**
	 * @return Obiekt kalendarza przygotowany przez ustawioną fabrykę.
	 */
	public static Calendar getCalendar(TimeZone zone, Locale locale) {
		if(calendarFactory == null) {
			return internalCalendarFactory.getCalendar(zone, locale);
		} else {
			return calendarFactory.getCalendar(zone, locale);
		}
	}

//	--------------------------------------------------------------------------
	/**
	 * Formatuje datę przekazaną przez parametr wywołania <code>date</code> do
	 * łańcucha zgodnego z formatem <b>yyyy-MM-dd</b>.
	 */
	public static final synchronized String formatDate(Date date) {
		return dateFormat.format(date);
	}
	/**
	 * Formatuje datę zgodną z formatem <b>yyyy-MM-dd</b> przekazaną przez
	 * parametr wywołania <code>date</code> do obiektu {@link Date}.
	 */
	public static final synchronized Date parseDate(String date) {
		try {
			return dateFormat.parse(date);
		} catch(ParseException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Formatuje czas przekazany przez parametr wywołania <code>time</code> do
	 * łańcucha zgodnego z formatem <b>HH:mm:ss</b>.
	 */
	public static final synchronized String formatTime(Date time) {
		return timeFormat.format(time);
	}
	/**
	 * Formatuje czas zgodny z formatem <b>HH:mm:ss</b> przekazany przez parametr
	 * wywołania <code>time</code> do obiektu {@link Date}.
	 */
	public static final synchronized Date parseTime(String time) {
		try {
			return timeFormat.parse(time);
		} catch(ParseException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Formatuje datę przekazaną przez parametr wywołania <code>dateTime</code>
	 * do łańcucha zgodnego z formatem <b>yyyy-MM-dd HH:mm:ss</b>.
	 */
	public static final synchronized String formatDateTime(Date dateTime) {
		return dateTimeFormat.format(dateTime);
	}
	/**
	 * Formatuje datę zgodną z formatem <b>yyyy-MM-dd HH:mm:ss</b> przekazaną
	 * przez parametr wywołania <code>dateTime</code> do obiektu {@link Date}.
	 */
	public static final synchronized Date parseDateTime(String dateTime) {
		try {
			return dateTimeFormat.parse(dateTime);
		} catch(ParseException e) {
			throw new RuntimeException(e);
		}
	}
}
