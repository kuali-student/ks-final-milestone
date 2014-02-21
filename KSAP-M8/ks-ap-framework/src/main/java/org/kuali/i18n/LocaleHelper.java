package org.kuali.i18n;

import org.apache.commons.lang.StringUtils;
import org.kuali.student.r2.common.dto.LocaleInfo;

import java.util.Locale;

/**
 * Created with IntelliJ IDEA.
 * User: chmaurer
 * Date: 1/17/14
 * Time: 9:30 AM
 * To change this template use File | Settings | File Templates.
 */
public class LocaleHelper {

    public static Locale localeInfo2Locale(LocaleInfo li) {
        Locale locale = new Locale(nullSafeGet(nullSafeGet(li.getLocaleLanguage())), nullSafeGet(li.getLocaleRegion()), nullSafeGet(li.getLocaleVariant()));
        return locale;
    }

    public static LocaleInfo locale2LocaleInfo(Locale locale) {
        LocaleInfo localeInfo = new LocaleInfo();
        localeInfo.setLocaleLanguage(locale.getLanguage());
        localeInfo.setLocaleRegion(locale.getCountry());
        localeInfo.setLocaleScript(locale.getScript());
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
