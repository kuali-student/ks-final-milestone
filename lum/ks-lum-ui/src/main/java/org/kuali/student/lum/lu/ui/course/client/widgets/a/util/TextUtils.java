/*
 * Copyright 2009 Johnson Consulting Services
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
/**
 * 
 */
package org.kuali.student.lum.lu.ui.course.client.widgets.a.util;

/**
 * @author wilj
 *
 */
public class TextUtils {

	public static String getDefaultHyphen() {
		// TODO use deferred binding for this
		if (BrowserUtils.isIE()) {
			// IE "shy hyphen"
			return "&shy;";
		} else {
			// "zero-width space"
			return "&#8203;";
		}
	}

	private static boolean isBreakable(final char ch) {
		// TODO determine what other characters result in wordwrap on major browsers
		// and yes, this is ugly, no real character class matching under JRE emulation
		return ch == '\t' || ch == ' ' || ch == '\n' || ch == '-';
	}

	public static void main(final String[] args) {
		try {
			mtest("this is a normal text string to test");
			mtest("this starts normalbutthengetsreallylong");
			mtest("thisisreallylongrightfromthestart but then has spaces");
			mtest("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa1234567890");
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}

	private static void mtest(final String text) {
		System.out.println(softWrap(text, 10, "-"));
	}

	public static String softWrap(final String text, final int lineLength) {
		return softWrap(text, lineLength, getDefaultHyphen());
	}

	public static String softWrap(final String text, final int lineLength, final String hyphen) {
		final int len = text.length();
		if (len <= lineLength) {
			return text;
		}
		final StringBuilder result = new StringBuilder(len + (((len / lineLength)) * hyphen.length()));

		int rangeStart = 0;
		int rangeEnd = lineLength;
		do {

			// we've found a "break" already if the remainder is short enough
			boolean breakFound = (rangeEnd - rangeStart < lineLength);
			while (rangeEnd > rangeStart && !breakFound) {
				final char c = text.charAt(rangeEnd);
				breakFound = isBreakable(c);
				if (!breakFound) {
					rangeEnd--;
				}
			}
			if (breakFound) {
				result.append(text.substring(rangeStart, rangeEnd));
			} else {
				// a word longer than the allowed line-length was found
				rangeEnd = Math.min(len, (rangeStart + lineLength));
				result.append(text.substring(rangeStart, rangeEnd));
				result.append(hyphen);
			}
			rangeStart = rangeEnd;
			rangeEnd = rangeStart + lineLength;
			rangeEnd = Math.min(len, rangeEnd);
		} while (rangeStart < len);

		return result.toString();
	}
}
