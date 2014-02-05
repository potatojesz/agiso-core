/* org.agiso.core.lang.util.DateUtilsMTest (2010-01-16)
 * 
 * DateUtilsMTest.java
 * 
 * Copyright 2010 agiso.org.
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

import static org.mockito.Mockito.*;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.TimeZone;

import org.agiso.core.lang.util.DateUtils.ICalendarFactory;
import org.testng.annotations.Test;

/**
 * Testuje poprawność działania klasy narzędziowej {@link DateUtils}.
 * 
 * @author Karol Kopacz
 * @since 1.0
 */
@Test(singleThreaded = false)
public class DateUtilsMTest {
	private static final TimeZone CET = TimeZone.getTimeZone("CET");

	@Test
	public void testGetTimestamp() throws Exception {
		Date timestamp;
		Calendar calendar = Calendar.getInstance();

		DateUtils.setCalendarFactory(null);
		calendar.setTime(timestamp = DateUtils.getTimestamp());
		assert Timestamp.class.equals(timestamp.getClass());
		assert 0 == calendar.get(Calendar.MILLISECOND);

		ICalendarFactory calendarFactory = mock(ICalendarFactory.class);
		Calendar srcCalendar = Calendar.getInstance();
		when(calendarFactory.getCalendar()).thenReturn(srcCalendar);
		DateUtils.setCalendarFactory(calendarFactory);

		srcCalendar.set(Calendar.YEAR, 2010);
		srcCalendar.set(Calendar.MONTH, 0);
		srcCalendar.set(Calendar.DAY_OF_MONTH, 16);
		srcCalendar.set(Calendar.HOUR_OF_DAY, 1);
		srcCalendar.set(Calendar.MINUTE, 2);
		srcCalendar.set(Calendar.SECOND, 3);
		srcCalendar.set(Calendar.MILLISECOND, 4);

		calendar.setTime(timestamp = DateUtils.getTimestamp());
		assert Timestamp.class.equals(timestamp.getClass());
		assert 2010 == calendar.get(Calendar.YEAR);
		assert    0 == calendar.get(Calendar.MONTH);
		assert   16 == calendar.get(Calendar.DAY_OF_MONTH);
		assert    1 == calendar.get(Calendar.HOUR_OF_DAY);
		assert    2 == calendar.get(Calendar.MINUTE);
		assert    3 == calendar.get(Calendar.SECOND);
		assert    0 == calendar.get(Calendar.MILLISECOND);
	}

	@Test
	public void testGetDayDate() throws Exception {
		Date dayDate;
		Calendar calendar = Calendar.getInstance();

		// Sprawdzanie popawności działania dla daty bieżącej:
		DateUtils.setCalendarFactory(null);
		calendar.setTime(dayDate = DateUtils.getDayDate());
		assert Timestamp.class.equals(dayDate.getClass());
		assert 0 == calendar.get(Calendar.HOUR);
		assert 0 == calendar.get(Calendar.MINUTE);
		assert 0 == calendar.get(Calendar.SECOND);
		assert 0 == calendar.get(Calendar.MILLISECOND);

		// Sprawdzanie poprawności działania dla daty konkretnej:
		ICalendarFactory calendarFactory = mock(ICalendarFactory.class);
		Calendar srcCalendar = Calendar.getInstance();
		when(calendarFactory.getCalendar()).thenReturn(srcCalendar);
		DateUtils.setCalendarFactory(calendarFactory);

		// CET 2010-01-16 01:02:03.04 (czas zimowy)
		srcCalendar.setTimeZone(CET);
		srcCalendar.set(Calendar.YEAR, 2010);
		srcCalendar.set(Calendar.MONTH, 0);
		srcCalendar.set(Calendar.DAY_OF_MONTH, 16);
		srcCalendar.set(Calendar.HOUR_OF_DAY, 1);
		srcCalendar.set(Calendar.MINUTE, 2);
		srcCalendar.set(Calendar.SECOND, 3);
		srcCalendar.set(Calendar.MILLISECOND, 4);
		assert 0 == srcCalendar.get(Calendar.DST_OFFSET);

		calendar = DateUtils.getCalendar();
		calendar.setTime(dayDate = DateUtils.getDayDate());
		assert Timestamp.class.equals(dayDate.getClass());
		assert CET.equals(calendar.getTimeZone());
		assert 2010 == calendar.get(Calendar.YEAR);
		assert    0 == calendar.get(Calendar.MONTH);
		assert   16 == calendar.get(Calendar.DAY_OF_MONTH);
		assert    0 == calendar.get(Calendar.HOUR_OF_DAY);
		assert    0 == calendar.get(Calendar.MINUTE);
		assert    0 == calendar.get(Calendar.SECOND);
		assert    0 == calendar.get(Calendar.MILLISECOND);

		// CET 2010-07-16 01:02:03.04 (czas letni)
		srcCalendar.setTimeZone(CET);
		srcCalendar.set(Calendar.YEAR, 2010);
		srcCalendar.set(Calendar.MONTH, 6);
		srcCalendar.set(Calendar.DAY_OF_MONTH, 16);
		srcCalendar.set(Calendar.HOUR_OF_DAY, 1);
		srcCalendar.set(Calendar.MINUTE, 2);
		srcCalendar.set(Calendar.SECOND, 3);
		srcCalendar.set(Calendar.MILLISECOND, 4);
		assert 3600000 == srcCalendar.get(Calendar.DST_OFFSET);

		calendar = DateUtils.getCalendar();
		calendar.setTime(dayDate = DateUtils.getDayDate());
		assert Timestamp.class.equals(dayDate.getClass());
		assert CET.equals(calendar.getTimeZone());
		assert 2010 == calendar.get(Calendar.YEAR);
		assert    6 == calendar.get(Calendar.MONTH);
		assert   16 == calendar.get(Calendar.DAY_OF_MONTH);
		assert    0 == calendar.get(Calendar.HOUR_OF_DAY);
		assert    0 == calendar.get(Calendar.MINUTE);
		assert    0 == calendar.get(Calendar.SECOND);
		assert    0 == calendar.get(Calendar.MILLISECOND);
	}

	@Test
	public void testGetRandomDate() throws Exception {
		ICalendarFactory calendarFactory = mock(ICalendarFactory.class);
		Calendar srcCalendar = Calendar.getInstance();
		when(calendarFactory.getCalendar()).thenReturn(srcCalendar);
		DateUtils.setCalendarFactory(calendarFactory);

		srcCalendar.set(Calendar.YEAR, 2010);
		srcCalendar.set(Calendar.MONTH, 0);
		srcCalendar.set(Calendar.DAY_OF_MONTH, 16);
		srcCalendar.set(Calendar.HOUR_OF_DAY, 1);
		srcCalendar.set(Calendar.MINUTE, 2);
		srcCalendar.set(Calendar.SECOND, 3);
		srcCalendar.set(Calendar.MILLISECOND, 4);

		Date date;
		Date begin = new Date(0);
		Date end = new Date(10);
		Random random = mock(Random.class);

		when(random.nextLong()).thenReturn(Long.MIN_VALUE);
		date = DateUtils.getRandomDate(begin, end, random);
		assert begin.compareTo(date) <= 0;
		assert end.compareTo(date) > 0;

		for(long rndValue = -11; rndValue <= 11; rndValue++) {
			when(random.nextLong()).thenReturn(rndValue);
			date = DateUtils.getRandomDate(begin, end, random);
			assert begin.compareTo(date) <= 0;
			assert end.compareTo(date) > 0;
		}

		when(random.nextLong()).thenReturn(Long.MAX_VALUE);
		date = DateUtils.getRandomDate(begin, end, random);
		assert begin.compareTo(date) <= 0;
		assert end.compareTo(date) > 0;
	}
}
