/* org.agiso.core.lang.util.TextUtils (2009-10-16)
 * 
 * TextUtils.java
 * 
 * Copyright 2009 agiso.org
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

/**
 * 
 * 
 * @author <a href="mailto:kkopacz@agiso.org">Karol Kopacz</a>
 */
public abstract class TextUtils {
	public static String wrap(String text, int screenWidth,
			int firstIndent, int indent, String lineBreak, boolean traceMode) {
		return wrap(new StringBuffer(text), screenWidth, firstIndent, indent,
						lineBreak, traceMode
		).toString();
	}

	/**
	 * Hard-wraps flow-text. Uses StringBuffer-s instead of String-s.
	 * This is the method that is internally used by all other <code>wrap</code>
	 * variations, so if you are working with StringBuffers anyway, it gives
	 * better performance.
	 *
	 * @see #wrap(String, int, int, int, String, boolean)
	 */
	public static StringBuffer wrap(StringBuffer text, int screenWidth,
			int firstIndent, int indent, String lineBreak, boolean traceMode) {
		if(firstIndent < 0 || indent < 0 || screenWidth < 0) {
			throw new IllegalArgumentException("Negative dimension");
		}

		int allowedCols = screenWidth - 1;
		if((allowedCols - indent) < 2 || (allowedCols - firstIndent) < 2) {
			throw new IllegalArgumentException("Usable columns < 2");
		}

		int e = 0, b = 0, ln = text.length();
		int defaultNextLeft = allowedCols - indent;

		StringBuffer res = new StringBuffer((int)(ln * 1.2));
		int left = allowedCols - firstIndent;
		for(int i = 0; i < firstIndent; i++) {
			res.append(' ');
		}
		StringBuffer tempb = new StringBuffer(indent + 2);
		tempb.append(lineBreak);
		for(int i = 0; i < indent; i++) {
			tempb.append(' ');
		}
		String defaultBreakAndIndent = tempb.toString();

		boolean firstSectOfSrcLine = true;
		boolean firstWordOfSrcLine = true;
		int traceLineState = 0;
		int nextLeft = defaultNextLeft;
		String breakAndIndent = defaultBreakAndIndent;
		int wln = 0, x;
		char c, c2;
		do {
			word: while (e <= ln) {
				if (e != ln) {
					c = text.charAt(e);
				} else {
					c = ' ';
				}
				if (traceLineState > 0 && e > b) {
					if (c == '.' && traceLineState == 1) {
						c = ' ';
					} else {
						c2 = text.charAt(e - 1);
						if (c2 == ':') {
							c = ' ';
						} else if (c2 == '(') {
							traceLineState = 2;
							c = ' ';
						}
					}
				}
				if (c != ' ' && c != '\n' && c != '\r' && c != '\t') {
					e++;
				} else {
					wln = e - b;
					if (left >= wln) {
						res.append(text.substring(b, e));
						left -= wln;
						b = e;
					} else {
						wln = e - b;
						if (wln > nextLeft || firstWordOfSrcLine) {
							int ob = b;
							while (wln > left) {
								if (left > 2 || (left == 2
										&& (firstWordOfSrcLine
										|| !(b == ob && nextLeft > 2))
										)) {
									res.append(text.substring(b, b + left - 1));
									res.append("-");
									res.append(breakAndIndent);
									wln -= left - 1;
									b += left - 1;
									left = nextLeft;
								} else {
									x = res.length() - 1;
									if (x >= 0 && res.charAt(x) == ' ') {
										res.delete(x, x + 1);
									}
									res.append(breakAndIndent);
									left = nextLeft;
								}
							}
							res.append(text.substring(b, b + wln));
							b += wln;
							left -= wln;
						} else {
							x = res.length() - 1;
							if (x >= 0 && res.charAt(x) == ' ') {
								res.delete(x, x + 1);
							}
							res.append(breakAndIndent);
							res.append(text.substring(b, e));
							left = nextLeft - wln;
							b = e;
						}
					}
					firstSectOfSrcLine = false;
					firstWordOfSrcLine = false;
					break word;
				}
			}
			int extra = 0;
			space: while (e < ln) {
				c = text.charAt(e);
				if (c == ' ') {
					e++;
				} else if (c == '\t') {
					e++;
					extra += 7;
				} else if (c == '\n' || c == '\r') {
					nextLeft = defaultNextLeft;
					breakAndIndent = defaultBreakAndIndent;
					res.append(breakAndIndent);
					e++;
					if (e < ln) {
						c2  = text.charAt(e);
						if ((c2 == '\n' || c2 == '\r') && c != c2) {
							e++;
						}
					}
					left = nextLeft;
					b = e;
					firstSectOfSrcLine = true;
					firstWordOfSrcLine = true;
					traceLineState = 0;
				} else {
					wln = e - b + extra;
					if (firstSectOfSrcLine) {
						int y = allowedCols - indent - wln;
						if (traceMode && ln > e + 2
								&& text.charAt(e) == 'a'
								&& text.charAt(e + 1) == 't'
								&& text.charAt(e + 2) == ' ') {
							if (y > 5 + 3) {
								y -= 3;
							}
							traceLineState = 1;
						}
						if (y > 5) {
							y = allowedCols - y;
							nextLeft = allowedCols - y;
							tempb = new StringBuffer(indent + 2);
							tempb.append(lineBreak);
							for (int i = 0; i < y; i++) {
								tempb.append(' ');
							}
							breakAndIndent = tempb.toString();
						}
					}
					if (wln <= left) {
						res.append(text.substring(b, e));
						left -= wln;
						b = e;
					} else {
						res.append(breakAndIndent);
						left = nextLeft;
						b = e;
					}
					firstSectOfSrcLine = false;
					break space;
				}
			}
		} while (e < ln);

		return res;
	}
}
