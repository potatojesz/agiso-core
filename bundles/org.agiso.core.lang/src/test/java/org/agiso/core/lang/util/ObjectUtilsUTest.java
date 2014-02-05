/* org.agiso.core.lang.util.ObjectUtilsUTest (2009-01-07)
 * 
 * ObjectUtilsUTest.java
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

import org.agiso.core.lang.annotation.InEquals;
import org.agiso.core.lang.annotation.InHashCode;
import org.agiso.core.lang.annotation.InToString;
import org.agiso.core.lang.util.ObjectUtils;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Testuje poprawność działania metod klasy narzędziowej {@link ObjectUtils}.
 * 
 * @author Karol Kopacz
 * @since 1.0
 */
public class ObjectUtilsUTest {
	private TestObject testObject1;

//	--------------------------------------------------------------------------
	/**
	 * Tworzy obiekt testowy klasy {@link TestObject}.
	 */
	@BeforeMethod void setUpBeforeMethod() throws Exception {
		testObject1 = new TestObject();
		testObject1.setCharField('a');
		testObject1.setBooleanField(true);
		testObject1.setByteField(Byte.MIN_VALUE);
		testObject1.setDoubleField(Double.MIN_VALUE);
		testObject1.setFloatField(Float.MIN_VALUE);
		testObject1.setIntField(Integer.MIN_VALUE);
		testObject1.setLongField(Long.MIN_VALUE);
		testObject1.setShortField(Short.MIN_VALUE);
		testObject1.setStringFieldA("string A");
		testObject1.setStringFieldB("string B");
	}
	/**
	 * Usuwa referencję do obiektu testowego.
	 */
	@AfterMethod void tearDownAfterMethod() throws Exception {
		testObject1 = null;
	}

//	--------------------------------------------------------------------------
	/**
	 * Testuje generowanie reprezentacji łańcuchowej obiektu i wpływ adnotacji
	 * {@link InToString}.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testToStringBuilder() throws Exception {
		System.out.println("testToStringBuilder");
		String hexHash = Integer.toHexString(System.identityHashCode(testObject1));
		String string = 
			"TestObject@" + hexHash + "[character: " + true +
				", byteField: " + Byte.MIN_VALUE +
				", doubleField: " + Double.MIN_VALUE +
				", floatField: " + Float.MIN_VALUE +
				", intField: " + Integer.MIN_VALUE +
				", longField: " + Long.MIN_VALUE +
				", shortField: " + Short.MIN_VALUE +
				", stringFieldA: string A" +
				", stringFieldB: string B" +
			"]";
		assert string.equals(ObjectUtils.toStringBuilder(testObject1));

		// Powtarzamy referencję w dwóch polach klasy i sprawdzamy działanie:
		testObject1.setStringFieldB(testObject1.getStringFieldA());
		hexHash = Integer.toHexString(System.identityHashCode(testObject1));
		string = 
			"TestObject@" + hexHash + "[character: " + true +
				", byteField: " + Byte.MIN_VALUE +
				", doubleField: " + Double.MIN_VALUE +
				", floatField: " + Float.MIN_VALUE +
				", intField: " + Integer.MIN_VALUE +
				", longField: " + Long.MIN_VALUE +
				", shortField: " + Short.MIN_VALUE +
				", stringFieldA: string A" +
				", stringFieldB: string A" +
			"]";
		assert string.equals(ObjectUtils.toStringBuilder(testObject1));
	}

	/**
	 * Testuje wyznaczanie sumy haszującej obiektu i wpływ adnotacji {@link
	 * InHashCode}. Wyznacza referencyjną sumę haszującą obiektu, po czym
	 * dokonuje zmian w obiekcie sprawdza wpływ tych zmian na wyznaczanie sumy
	 * haszującej.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testHashCodeBuilder() throws Exception {
		System.out.println("testHashCodeBuilder");
		int objectAHash = ObjectUtils.hashCodeBuilder(testObject1);

		// Zmieniamy wartość pola, suma haszująca ma się zmienić:
		testObject1.setBooleanField(false);
		assert objectAHash != ObjectUtils.hashCodeBuilder(testObject1);

		// Przywracamy wartość pola, suma powinna wrócić do wartości wyjściowej:
		testObject1.setBooleanField(true);
		assert objectAHash == ObjectUtils.hashCodeBuilder(testObject1);

		// Zmieniamy pole wyłączone adnotacją InHashCode, suma nie powinna się zmienić:
		testObject1.setCharField('*');
		assert objectAHash == ObjectUtils.hashCodeBuilder(testObject1);
	}

	/**
	 * Testuje porównywanie dwóch obiektów i wpływ adnotacji {@link InEquals}.
	 * Tworzy tymczasowy obiekt identyczny z obiektem testowym {@link
	 * ObjectUtilsUTest#testObjectB} i sprawdza ich równość. Następnie dokonuje
	 * zmian w utworzonym obiekcie i sprawdza ich wpływ na wynik porówania.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testEqualsBuilder() throws Exception {
		System.out.println("testEqualsBuilder");
		TestObject testObject2 = new TestObject();
		testObject2.setCharField('a');
		testObject2.setBooleanField(true);
		testObject2.setByteField(Byte.MIN_VALUE);
		testObject2.setDoubleField(Double.MIN_VALUE);
		testObject2.setFloatField(Float.MIN_VALUE);
		testObject2.setIntField(Integer.MIN_VALUE);
		testObject2.setLongField(Long.MIN_VALUE);
		testObject2.setShortField(Short.MIN_VALUE);
		testObject2.setStringFieldA("string A");
		testObject2.setStringFieldB("string B");

		// Utworzony obiekt ma być identyczny z testowym:
		assert ObjectUtils.equalsBuilder(testObject1, testObject2);

		// Zmieniamy pole utworzonego obiektu, ma być różny od testowego:
		testObject1.setBooleanField(false);
		assert !ObjectUtils.equalsBuilder(testObject1, testObject2);

		// Przywracamy wartość zmieniaonego pola, obiekty mają być równe:
		testObject1.setBooleanField(true);
		assert ObjectUtils.equalsBuilder(testObject1, testObject2);

		// Zmieniamy pole wyłączone adnotacją InEquals, obiety mają być równe:
		testObject1.setCharField('*');
		assert ObjectUtils.equalsBuilder(testObject1, testObject2);
	}

//	--------------------------------------------------------------------------
	/**
	 * Klasa testowa. Deklaruje kompletny zestaw pól typów prostych.
	 */
	public class TestObject {
		@InEquals(ignore = true)
		@InHashCode(ignore = true)
		@InToString(ignore = true)
		private char charField;

		@InToString(name = "character")
		private boolean booleanField;

		private byte byteField;
		private double doubleField;
		private float floatField;
		private int intField;
		private long longField;
		private short shortField;

		private String stringFieldA;
		private String stringFieldB;

		public char getCharField() {
			return charField;
		}
		public void setCharField(char charField) {
			this.charField = charField;
		}

		public boolean isBooleanField() {
			return booleanField;
		}
		public void setBooleanField(boolean booleanField) {
			this.booleanField = booleanField;
		}

		public byte getByteField() {
			return byteField;
		}
		public void setByteField(byte byteField) {
			this.byteField = byteField;
		}

		public double getDoubleField() {
			return doubleField;
		}
		public void setDoubleField(double doubleField) {
			this.doubleField = doubleField;
		}

		public float getFloatField() {
			return floatField;
		}
		public void setFloatField(float floatField) {
			this.floatField = floatField;
		}

		public int getIntField() {
			return intField;
		}
		public void setIntField(int intField) {
			this.intField = intField;
		}

		public long getLongField() {
			return longField;
		}
		public void setLongField(long longField) {
			this.longField = longField;
		}

		public short getShortField() {
			return shortField;
		}
		public void setShortField(short shortField) {
			this.shortField = shortField;
		}

		public String getStringFieldA() {
			return stringFieldA;
		}
		public void setStringFieldA(String stringFieldA) {
			this.stringFieldA = stringFieldA;
		}

		public String getStringFieldB() {
			return stringFieldB;
		}
		public void setStringFieldB(String stringFieldB) {
			this.stringFieldB = stringFieldB;
		}
	}
}
