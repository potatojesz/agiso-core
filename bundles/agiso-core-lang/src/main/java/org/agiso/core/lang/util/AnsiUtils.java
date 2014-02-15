/* org.agiso.core.lang.util.AnsiUtils (12-02-2014)
 * 
 * AnsiUtils.java
 * 
 * Copyright 2014 agiso.org
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

import java.util.Arrays;

/**
 * 
 * 
 * @author Karol Kopacz
 * @since 1.0
 */
public abstract class AnsiUtils {
	private static IAnsiProcessor processor = new DefaultAnsiProcessor();
	private static IAnsiElementWrapper wrapper = new AnsiElementWrapper();

	private AnsiUtils() {
	}

	public static void setAnsiProcessor(IAnsiProcessor processor) {
		AnsiUtils.processor = processor;
	}
	public static IAnsiElementWrapper setAnsiProcessor(IWrappingAnsiProcessor processor) {
		AnsiUtils.processor = processor;
		return wrapper;
	}


	public static String ansiString(Object... elements) {
		if(processor instanceof IWrappingAnsiProcessor) {
			elements = Arrays.copyOf(elements, elements.length);
			for(int index = 0; index < elements.length; index++) {
				if(elements[index] instanceof WrappingAnsiElement) {
					elements[index] = ((WrappingAnsiElement)elements[index]).getValue();
				}
			}
		}
		return processor.ansiString(elements);
	}

	public interface IAnsiProcessor {
		public String ansiString(Object... elements);
	}
	public interface IWrappingAnsiProcessor extends IAnsiProcessor {
	}
	private static class DefaultAnsiProcessor implements IAnsiProcessor {
		@Override
		public String ansiString(Object... elements) {
			StringBuilder sb = new StringBuilder();
			for(Object element : elements) {
				if(element instanceof IAnsiElement) {
					sb.append(((IAnsiElement)element).getValue());
				} else {
					sb.append(element);
				}
			}
			return sb.toString();
		}
	}

	public static class AnsiElement {
		public static final IAnsiElement NORMAL = new WrappingAnsiElement("");
		public static final IAnsiElement BOLD = new WrappingAnsiElement("");
		public static final IAnsiElement FAINT = new WrappingAnsiElement("");
		public static final IAnsiElement ITALIC = new WrappingAnsiElement("");
		public static final IAnsiElement UNDERLINE = new WrappingAnsiElement("");

		public static final IAnsiElement BLACK = new WrappingAnsiElement("");
		public static final IAnsiElement RED = new WrappingAnsiElement("");
		public static final IAnsiElement GREEN = new WrappingAnsiElement("");
		public static final IAnsiElement YELLOW = new WrappingAnsiElement("");
		public static final IAnsiElement BLUE = new WrappingAnsiElement("");
		public static final IAnsiElement MAGENTA = new WrappingAnsiElement("");
		public static final IAnsiElement CYAN = new WrappingAnsiElement("");
		public static final IAnsiElement WHITE = new WrappingAnsiElement("");
		public static final IAnsiElement DEFAULT = new WrappingAnsiElement("");

	}
	private interface IAnsiElement {
		public Object getValue();
	}
	private static class WrappingAnsiElement implements IAnsiElement {
		private Object value;

		public WrappingAnsiElement(Object value) {
			this.value = value;
		}

		public Object getValue() {
			return value;
		}
		public void setValue(Object value) {
			this.value = value;
		}
	}

	public interface IAnsiElementWrapper {
		public IAnsiElementWrapper withAnsiNormal(Object value);
		public IAnsiElementWrapper withAnsiBold(Object value);
		public IAnsiElementWrapper withAnsiFaint(Object value);
		public IAnsiElementWrapper withAnsiItalic(Object value);
		public IAnsiElementWrapper withAnsiUnderline(Object value);
		public IAnsiElementWrapper withAnsiBlack(Object value);
		public IAnsiElementWrapper withAnsiRed(Object value);
		public IAnsiElementWrapper withAnsiGreen(Object value);
		public IAnsiElementWrapper withAnsiYellow(Object value);
		public IAnsiElementWrapper withAnsiBlue(Object value);
		public IAnsiElementWrapper withAnsiMagenta(Object value);
		public IAnsiElementWrapper withAnsiCyan(Object value);
		public IAnsiElementWrapper withAnsiWhite(Object value);
		public IAnsiElementWrapper withAnsiDefault(Object value);
	}
	private static class AnsiElementWrapper implements IAnsiElementWrapper {
		@Override
		public IAnsiElementWrapper withAnsiNormal(Object value) {
			((WrappingAnsiElement)AnsiElement.NORMAL).setValue(value);
			return this;
		}
		@Override
		public IAnsiElementWrapper withAnsiBold(Object value) {
			((WrappingAnsiElement)AnsiElement.BOLD).setValue(value);
			return this;
		}
		@Override
		public IAnsiElementWrapper withAnsiFaint(Object value) {
			((WrappingAnsiElement)AnsiElement.FAINT).setValue(value);
			return this;
		}
		@Override
		public IAnsiElementWrapper withAnsiItalic(Object value) {
			((WrappingAnsiElement)AnsiElement.ITALIC).setValue(value);
			return this;
		}
		@Override
		public IAnsiElementWrapper withAnsiUnderline(Object value) {
			((WrappingAnsiElement)AnsiElement.UNDERLINE).setValue(value);
			return this;
		}
		@Override
		public IAnsiElementWrapper withAnsiBlack(Object value) {
			((WrappingAnsiElement)AnsiElement.BLACK).setValue(value);
			return this;
		}
		@Override
		public IAnsiElementWrapper withAnsiRed(Object value) {
			((WrappingAnsiElement)AnsiElement.RED).setValue(value);
			return this;
		}
		@Override
		public IAnsiElementWrapper withAnsiGreen(Object value) {
			((WrappingAnsiElement)AnsiElement.GREEN).setValue(value);
			return this;
		}
		@Override
		public IAnsiElementWrapper withAnsiYellow(Object value) {
			((WrappingAnsiElement)AnsiElement.YELLOW).setValue(value);
			return this;
		}
		@Override
		public IAnsiElementWrapper withAnsiBlue(Object value) {
			((WrappingAnsiElement)AnsiElement.BLUE).setValue(value);
			return this;
		}
		@Override
		public IAnsiElementWrapper withAnsiMagenta(Object value) {
			((WrappingAnsiElement)AnsiElement.MAGENTA).setValue(value);
			return this;
		}
		@Override
		public IAnsiElementWrapper withAnsiCyan(Object value) {
			((WrappingAnsiElement)AnsiElement.CYAN).setValue(value);
			return this;
		}
		@Override
		public IAnsiElementWrapper withAnsiWhite(Object value) {
			((WrappingAnsiElement)AnsiElement.WHITE).setValue(value);
			return this;
		}
		@Override
		public IAnsiElementWrapper withAnsiDefault(Object value) {
			((WrappingAnsiElement)AnsiElement.DEFAULT).setValue(value);
			return this;
		}
	}
}
