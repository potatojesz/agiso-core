/* org.agiso.core.lang.util.ConvertUtils (2007-07-16)
 *
 * ConvertUtils.java
 *
 * Copyright 2007 agiso.org.
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

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Scanner;

/**
 * 
 * 
 * @author <a href="mailto:kkopacz@araj.pl">Karol Kopacz</a>
 * @since 1.0
 */
public abstract class ConvertUtils {
	/**
	 * @deprecated Use {@link #toMap(ResourceBundle)} instead
	 */
	@Deprecated
	public static Map<String, String> convertBundleToMap(ResourceBundle bundle) {
		return toMap(bundle);
	}
	/**
	 * Konwertuje zbiór zasobów na mapę.
	 * 
	 * @param bundle Zbiór zasobów do konwersji.
	 * @return Mapa zasobów.
	 */
	public static Map<String, String> toMap(ResourceBundle bundle) {
		Map<String, String> map = new HashMap<String, String>();
		Enumeration<String> keys = bundle.getKeys();
		while(keys.hasMoreElements()) {
			String key = keys.nextElement();
			map.put(key, bundle.getString(key));
		}
		return map;
	}

	/**
	 * @deprecated Use {@link #toByteArray(File)} instead
	 */
	@Deprecated
	public static byte[] convertFileToByteArray(File file) throws IOException {
		return toByteArray(file);
	}
	/**
	 * Konwertuje plik na tablicę bajtów.
	 * 
	 * @param file Plik do konwersji.
	 * @return Tablica bajtów odczytanych z pliku.
	 * 
	 * @throws IOException W przypadku błędu odczytu pliku.
	 */
	public static byte[] toByteArray(File file) throws IOException {
		return toByteArray(new FileInputStream(file));
	}

	/**
	 * @deprecated Use {@link #toByteArray(InputStream)} instead
	 */
	public static byte[] convertStreamToByteArray(InputStream stream) throws IOException {
		return toByteArray(stream);
	}
	/**
	 * Konwertuje struień wejściowy na tablicę bajtów.
	 * 
	 * @param stream Strumień wejściowy do konwersji.
	 * @return Tablica bajtów odczytanych ze strumienia.
	 * 
	 * @throws IOException W przypadku błędu odczytu strumienia.
	 */
	public static byte[] toByteArray(InputStream stream) throws IOException {
		// Wyznaczanie rozmiaru strumienia:
		long length = stream.available();

		// Nie można utworzyć tablicy używając typu long. Wymagany jest do tego
		// typ int. Przed konwersją sprawdzamy, czy strumień nie jest zbyt duży:
		if(length > Integer.MAX_VALUE) {
			throw new RuntimeException("Stream is to large.");
		}

		// Tworzymy tablicę do przechowywania danych ze strumienia i odczytujemy:
		byte[] bytes = new byte[(int)length];

		int offset = 0;
		int numRead = 0;
		while(offset < bytes.length	&& (numRead = stream.read(
				bytes, offset, bytes.length - offset)) >= 0) {
			offset += numRead;
		}

		// Sprawdzamy, czy wszystkie dane zostały odczytane:
		if(offset < bytes.length) {
			throw new IOException("Could not completely read stream.");
		}

		// Zamykamy strumień i zwracamy tablicę z jego zawartością:
		stream.close();

		return bytes;
	}

	/**
	 * @deprecated Use {@link #toString(InputStream)} instead
	 */
	public static String convertStreamToString(InputStream stream) {
		return toString(stream);
	}
	/**
	 * Konwertuje strumień wejściowy na łańcuch znaków
	 * 
	 * @param stream Strumień wejściowy do konwersji.
	 * @return Łańcuch zaków odczytanych ze strumienia.
	 */
	public static String toString(InputStream stream) {
		Scanner s = new Scanner(stream).useDelimiter("\\A");
		return s.hasNext() ? s.next() : "";
	}
	/**
	 * Konwertuje strumień wejściowy na łańcuch znaków
	 * 
	 * @param stream Strumień wejściowy do konwersji.
	 * @param encoding Kodowanie użyte do konwersji odczytu łańcucha.
	 * @return Łańcuch zaków odczytanych ze strumienia.
	 */
	public static String toString(InputStream stream, String encoding) {
		Scanner s = new Scanner(stream, encoding).useDelimiter("\\A");
		return s.hasNext() ? s.next() : "";
	}

	/**
	 * @deprecated Use {@link #toHexString(byte[])} instead
	 */
	public static String convertByteArrayToHexString(byte in[]) {
		return toHexString(in);
	}
	/**
	 * Konwertuje tablicę bajtów na łańcuch wartości szesnastkowych.
	 * 
	 * @param stream Tablica bajtów do konwersji.
	 * @return Łańcuch danych szesnastkowych.
	 */
	public static String toHexString(byte in[]) {
		if(in == null || in.length <= 0) {
			return null;
		}

		int i = 0;
		byte ch = 0x00;
		String pseudo[] = {
				"0", "1", "2", "3",
				"4", "5", "6", "7",
				"8", "9", "A", "B",
				"C", "D", "E", "F"
		};

		StringBuffer out = new StringBuffer(in.length * 2);
		while(i < in.length) {
			ch = (byte)(in[i] & 0xF0);		// Strip off high nibble
			ch = (byte)(ch >>> 4);			// shift the bits down
			ch = (byte)(ch & 0x0F);			// must do this is high order bit is on!
			out.append(pseudo[(int)ch]);	// convert the nibble to a String Character
			ch = (byte)(in[i] & 0x0F);		// Strip off low nibble 
			out.append(pseudo[(int)ch]);	// convert the nibble to a String Character
			out.append(' ');
			i++;
		}

		return new String(out);
	}

	/**
	 * @deprecated Use {@link #toDigest(byte[],String)} instead
	 */
	public static String convertBytesToDigest(byte[] bytes, String algorithm) throws NoSuchAlgorithmException {
		return toDigest(bytes, algorithm);
	}
	/**
	 * Konwertuje tablicę bajtów na skrót zgodnie z algorytmem określonym przez
	 * parametr wywołania <code>algorithm</code>.
	 * 
	 * @param bytes Tablica bajtów do konwersji.
	 * @param algorithm Algorytm wyznaczenia skrótu.
	 * 
	 * @return Łańcuch reprezentujący skrót.
	 * @throws NoSuchAlgorithmException 
	 */
	public static String toDigest(byte[] bytes, String algorithm) throws NoSuchAlgorithmException {
		MessageDigest digest = MessageDigest.getInstance("MD5");
		digest.update(bytes);
		byte[] hash = digest.digest();

		StringBuffer result = new StringBuffer();
		for(int i = 0; i < hash.length; i++) {
			String s = Integer.toHexString(hash[i]);
			int length = s.length();
			if(length >= 2) {
				result.append(s.substring(length - 2, length));
			} else {
				result.append("0");
				result.append(s);
			}
		}
		return result.toString();
	}
}