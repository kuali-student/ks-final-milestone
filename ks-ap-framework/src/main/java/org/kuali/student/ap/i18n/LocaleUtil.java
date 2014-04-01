package org.kuali.student.ap.i18n;

import org.apache.commons.lang.StringUtils;
import org.kuali.student.r2.common.dto.LocaleInfo;

import java.util.Locale;

/**
 * This class allows for easy conversion between {@link Locale} and {@link LocaleInfo}
 * @author Chris Maurer <chmaurer@iu.edu>
 */
public class LocaleUtil {

    public static Locale localeInfo2Locale(LocaleInfo li) {
        if (li == null) {
            return Locale.getDefault();
        }
        return new Locale(nullSafeGet(nullSafeGet(li.getLocaleLanguage())), nullSafeGet(li.getLocaleRegion()), nullSafeGet(li.getLocaleVariant()));
    }

    public static LocaleInfo locale2LocaleInfo(Locale locale) {
        LocaleInfo localeInfo = new LocaleInfo();
        localeInfo.setLocaleLanguage(locale.getLanguage());
        localeInfo.setLocaleRegion(locale.getCountry());
        localeInfo.setLocaleVariant(locale.getVariant());
        return localeInfo;
    }

    /**
     * Return either the string that was passed, or an empty string if null
     * @param value
     * @return Either the value that was passed in or "", if null
     */
    public static String nullSafeGet(String value) {
        return StringUtils.trimToEmpty(value);
    }
}
