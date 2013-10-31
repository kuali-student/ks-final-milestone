package org.kuali.student.ap.framework.util;

/**
 * Provides miscellaneous string utilities for KSAP that are not commonly
 * available through other toolkits.
 * 
 * <p>
 * The utilities provided by this class should be considered for promotion to
 * other frameworks.
 * </p>
 * 
 * @author Mark Fyffe <mwfyffe@iu.edu>
 * @version 0.7.6
 */
public final class KsapStringUtil {

	/**
	 * Replace smart characters with normal.
	 * 
	 * @param str
	 *            The string to replace.
	 * @return The string, with "smart" characters replaced with normal ascii.
	 */
	public static String replaceSmartCharacters(String str) {
		if (str == null)
			return null;
		StringBuilder sb = new StringBuilder(str);
		for (int i = 0; i < sb.length(); i++) {
			switch (sb.charAt(i)) {

			case 0xa0:
				sb.setCharAt(i, ' ');
				break;

			case 0x91:
			case 0x92:
			case 0x2018:
			case 0x2019:
			case 0x201A:
			case 0x201B:
				sb.setCharAt(i, '\'');
				break;

			case 0x93:
			case 0x94:
			case 0x201C:
			case 0x201D:
			case 0x201E:
			case 0x201F:
				sb.setCharAt(i, '\"');
				break;

			case 0x96:
			case 0x2013:
				sb.setCharAt(i, '-');
				break;

			case 0x2DC:
				sb.setCharAt(i, '~');

			}
		}
		return sb.toString();
	}

	private KsapStringUtil() {
	}

}
