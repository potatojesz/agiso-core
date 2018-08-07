/* org.agiso.core.i18n.util.I18nUtilsUTest (30-05-2013)
 * 
 * I18nUtilsUTest.java
 * 
 * Copyright 2013 agiso.org
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
package org.agiso.core.i18n.util;

import static org.agiso.core.i18n.util.I18nUtils.*;

import java.util.function.Consumer;
import java.util.function.Function;

import org.agiso.core.i18n.beans.BeanImplementation1;
import org.agiso.core.i18n.beans.BeanImplementation2;
import org.agiso.core.i18n.beans.IBeanInterface;
import org.agiso.core.i18n.support.reflections.AnnotationMessageProvider;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import net.jodah.typetools.TypeResolver;

/**
 * 
 * 
 * @author Karol Kopacz
 * @since 1.0
 */
public class I18nUtilsUTest {
	@BeforeClass
	public void beforeClass() throws Exception {
		setMessageProvider(new AnnotationMessageProvider("org.agiso.core.i18n"));
	}

//	--------------------------------------------------------------------------
	@Test
	public void testField1() throws Exception {
		assert "org.agiso.core.i18n.beans.IBeanInterface.field1"
				.equals(getCode(IBeanInterface::getField1));
		assert "org.agiso.core.i18n.beans.IBeanInterface.field1"
				.equals(getCode(IBeanInterface.class, IBeanInterface::getField1));
		assert "org.agiso.core.i18n.beans.IBeanInterface.field1"
				.equals(getCode(IBeanInterface.class, "field1"));
		assert "org.agiso.core.i18n.beans.IBeanInterface.field1"
				.equals(findCode(IBeanInterface.class, "field1"));
		assert "Interface field1 label"
				.equals(getMessage(IBeanInterface.class, "field1"));

		assert "org.agiso.core.i18n.beans.BeanImplementation1.field1"
				.equals(getCode(BeanImplementation1::getField1));
		assert "org.agiso.core.i18n.beans.BeanImplementation1.field1"
				.equals(getCode(BeanImplementation1.class, BeanImplementation1::getField1));
		assert "org.agiso.core.i18n.beans.BeanImplementation1.field1"
				.equals(getCode(BeanImplementation1.class, "field1"));
		assert "org.agiso.core.i18n.beans.BeanImplementation1.field1"
				.equals(findCode(BeanImplementation1.class, "field1"));
		assert "BeanImplementation1 field1 label"
				.equals(getMessage(BeanImplementation1.class, "field1"));

		assert "org.agiso.core.i18n.beans.BeanImplementation1.field1"
				.equals(getCode(BeanImplementation2::getField1));
		assert "org.agiso.core.i18n.beans.BeanImplementation1.field1"
				.equals(getCode(BeanImplementation2.class, BeanImplementation2::getField1));
		assert "org.agiso.core.i18n.beans.BeanImplementation1.field1"
				.equals(getCode(BeanImplementation2.class, "field1"));
		assert "org.agiso.core.i18n.beans.BeanImplementation1.field1"
				.equals(findCode(BeanImplementation2.class, "field1"));
		assert "BeanImplementation1 field1 label"
				.equals(getMessage(BeanImplementation2.class, "field1"));
	}

	@Test
	public void testField2() throws Exception {
		assert "org.agiso.core.i18n.beans.IBeanInterface.field2"
				.equals(getCode(IBeanInterface::getField2));
		assert "org.agiso.core.i18n.beans.IBeanInterface.field2"
				.equals(getCode(IBeanInterface.class, IBeanInterface::getField2));
		assert "org.agiso.core.i18n.beans.IBeanInterface.field2"
				.equals(getCode(IBeanInterface.class, "field2"));
		assert "org.agiso.core.i18n.beans.IBeanInterface.field2"
				.equals(findCode(IBeanInterface.class, "field2"));
		assert "Interface field2 label"
				.equals(getMessage(IBeanInterface.class, "field2"));

		assert "org.agiso.core.i18n.beans.BeanImplementation1.field2"
				.equals(getCode(BeanImplementation1::getField2));
		assert "org.agiso.core.i18n.beans.BeanImplementation1.field2"
				.equals(getCode(BeanImplementation1.class, BeanImplementation1::getField2));
		assert "org.agiso.core.i18n.beans.BeanImplementation1.field2"
				.equals(getCode(BeanImplementation1.class, "field2"));
		assert "org.agiso.core.i18n.beans.IBeanInterface.field2"
				.equals(findCode(BeanImplementation1.class, "field2"));
		assert "org.agiso.core.i18n.beans.BeanImplementation1.field2"
				.equals(getMessage(BeanImplementation1.class, "field2"));

		assert "org.agiso.core.i18n.beans.BeanImplementation2.field2"
				.equals(getCode(BeanImplementation2::getField2));
		assert "org.agiso.core.i18n.beans.BeanImplementation2.field2"
				.equals(getCode(BeanImplementation2.class, BeanImplementation2::getField2));
		assert "org.agiso.core.i18n.beans.BeanImplementation2.field2"
				.equals(getCode(BeanImplementation2.class, "field2"));
		assert "org.agiso.core.i18n.beans.IBeanInterface.field2"
				.equals(findCode(BeanImplementation2.class, "field2"));
		assert "org.agiso.core.i18n.beans.BeanImplementation2.field2"
				.equals(getMessage(BeanImplementation2.class, "field2"));
	}

	@Test
	public void testField3() throws Exception {
		assert "org.agiso.core.i18n.beans.IBeanInterface.field3"
				.equals(getCode(IBeanInterface::getField3));
		assert "org.agiso.core.i18n.beans.IBeanInterface.field3"
				.equals(getCode(IBeanInterface.class, IBeanInterface::getField3));
		assert "org.agiso.core.i18n.beans.IBeanInterface.field3"
				.equals(getCode(IBeanInterface.class, "field3"));
		assert "org.agiso.core.i18n.beans.IBeanInterface.field3"
				.equals(findCode(IBeanInterface.class, "field3"));
		assert "Interface field3 label"
				.equals(getMessage(IBeanInterface.class, "field3"));

		assert "org.agiso.core.i18n.beans.BeanImplementation1.field3"
				.equals(getCode(BeanImplementation1::getField3));
		assert "org.agiso.core.i18n.beans.BeanImplementation1.field3"
				.equals(getCode(BeanImplementation1.class, BeanImplementation1::getField3));
		assert "org.agiso.core.i18n.beans.BeanImplementation1.field3"
				.equals(getCode(BeanImplementation1.class, "field3"));
		assert "org.agiso.core.i18n.beans.IBeanInterface.field3"
				.equals(findCode(BeanImplementation1.class, "field3"));
		assert "org.agiso.core.i18n.beans.BeanImplementation1.field3"
				.equals(getMessage(BeanImplementation1.class, "field3"));

		assert "org.agiso.core.i18n.beans.BeanImplementation2.field3"
				.equals(getCode(BeanImplementation2::getField3));
		assert "org.agiso.core.i18n.beans.BeanImplementation2.field3"
				.equals(getCode(BeanImplementation2.class, BeanImplementation2::getField3));
		assert "org.agiso.core.i18n.beans.BeanImplementation2.field3"
				.equals(getCode(BeanImplementation2.class, "field3"));
		assert "org.agiso.core.i18n.beans.BeanImplementation2.field3"
				.equals(findCode(BeanImplementation2.class, "field3"));
		assert "BeanImplementation2 field3 label"
				.equals(getMessage(BeanImplementation2.class, "field3"));
	}

	@Test
	public void testField4() throws Exception {
		assert "org.agiso.core.i18n.beans.IBeanInterface.field4"
				.equals(getCode(IBeanInterface::getField4));
		assert "org.agiso.core.i18n.beans.IBeanInterface.field4"
				.equals(getCode(IBeanInterface.class, IBeanInterface::getField4));
		assert "org.agiso.core.i18n.beans.IBeanInterface.field4"
				.equals(getCode(IBeanInterface.class, "field4"));
		assert "org.agiso.core.i18n.beans.IBeanInterface.field4"
				.equals(findCode(IBeanInterface.class, "field4"));
		assert "Interface field4 label"
				.equals(getMessage(IBeanInterface.class, "field4"));

		assert "org.agiso.core.i18n.beans.BeanImplementation1.field4"
				.equals(getCode(BeanImplementation1::getField4));
		assert "org.agiso.core.i18n.beans.BeanImplementation1.field4"
				.equals(getCode(BeanImplementation1.class, BeanImplementation1::getField4));
		assert "org.agiso.core.i18n.beans.BeanImplementation1.field4"
				.equals(getCode(BeanImplementation1.class, "field4"));
		assert "org.agiso.core.i18n.beans.IBeanInterface.field4"
				.equals(findCode(BeanImplementation1.class, "field4"));
		assert "org.agiso.core.i18n.beans.BeanImplementation1.field4"
				.equals(getMessage(BeanImplementation1.class, "field4"));

		assert "org.agiso.core.i18n.beans.BeanImplementation2.field4"
				.equals(getCode(BeanImplementation2::getField4));
		assert "org.agiso.core.i18n.beans.BeanImplementation2.field4"
				.equals(getCode(BeanImplementation2.class, BeanImplementation2::getField4));
		assert "org.agiso.core.i18n.beans.BeanImplementation2.field4"
				.equals(getCode(BeanImplementation2.class, "field4"));
		assert "org.agiso.core.i18n.beans.BeanImplementation2.field4"
				.equals(findCode(BeanImplementation2.class, "field4"));
		assert "org.agiso.core.i18n.beans.BeanImplementation2.field4"
				.equals(getMessage(BeanImplementation2.class, "field4"));
	}

//	--------------------------------------------------------------------------
//	@Test
	//	https://github.com/jhalterman/typetools/issues/14
	public void testTypeResolverResolveRawArgument1() throws Exception {
		Consumer<String> smr = System.out::println;
		Class<?> smt = TypeResolver.resolveRawArgument(Consumer.class, smr.getClass());
		assert String.class.equals(smt);

		Consumer<Integer> imr = System.out::println;
		Class<?> imt = TypeResolver.resolveRawArgument(Consumer.class, imr.getClass());
		assert Integer.class.equals(imt);

		Consumer<Integer> ilr = (x) -> {
			System.out.println(x);
		};
		Class<?> ilt = TypeResolver.resolveRawArgument(Consumer.class, ilr.getClass());
		assert Integer.class.equals(ilt);
	}

//	@Test
	public void testTypeResolverResolveRawArgument2() throws Exception {
		Function<BeanImplementation2, String> ir = new Function<BeanImplementation2, String>() {
			@Override
			public String apply(BeanImplementation2 t) {
				return null;
			}
		};
		Class<?>[] it = TypeResolver.resolveRawArguments(Function.class, ir.getClass());
		assert BeanImplementation2.class.equals(it[0]);

		Function<BeanImplementation2, String> lr = (s) -> {
			return null;
		};
		Class<?>[] lt = TypeResolver.resolveRawArguments(Function.class, lr.getClass());
		assert BeanImplementation2.class.equals(lt[0]);

		Function<BeanImplementation2, String> rr = BeanImplementation2::getField1;
		Class<?>[] rt = TypeResolver.resolveRawArguments(Function.class, rr.getClass());
		assert BeanImplementation2.class.equals(rt[0]);
	}
}
