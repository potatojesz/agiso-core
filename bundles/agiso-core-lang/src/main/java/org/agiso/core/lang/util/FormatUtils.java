/* org.agiso.core.lang.util.FormatUtils (2007-07-16)
 *
 * FormatUtils.java
 * 
 * Copyright 2007 agiso.org
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

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * 
 * 
 * @author Karol Kopacz
 * @since 1.0
 */
public class FormatUtils {

//	Deklaracje pól statycznych -----------------------------------------------
	// TODO: Zweryfikować bezpieczeństwo wątkowe klasy NumberFormat
	private static NumberFormat numberFormatter;
	private static NumberFormat currencyFormatter;

//	Inicjalizacja pól statycznych --------------------------------------------
	static {
		numberFormatter = NumberFormat.getNumberInstance(Locale.getDefault());
		numberFormatter.setMaximumFractionDigits(4);

		currencyFormatter = NumberFormat.getNumberInstance(Locale.getDefault());
		currencyFormatter.setMinimumFractionDigits(2);
		currencyFormatter.setMaximumFractionDigits(2);
	}

//	Definicje metod dostępowych ----------------------------------------------
	public static NumberFormat getCurrencyFormatter() {
		return currencyFormatter;
	}

	public static NumberFormat getNumberFormatter() {
		return numberFormatter;
	}

//	Definicje metod narzędziowych --------------------------------------------
	public static final synchronized String formatCurrency(Object obj) {
		return currencyFormatter.format(obj).replace('\u00A0', '\u0020');
	}

	public static final synchronized String formatNumber(Object obj) {
		return numberFormatter.format(obj).replace('\u00A0', '\u0020');
	}

	public static final String formatDate(Date date) {
		return DateUtils.formatDate(date);
	}
	public static final Date parseDate(String string) throws ParseException {
		return DateUtils.parseDate(string);
	}
}
