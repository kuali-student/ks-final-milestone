package org.kuali.student.common.conversion.util.converter;

import org.dozer.DozerConverter;
import org.kuali.student.r2.common.dto.LocaleInfo;

public class LocaleInfoConverter extends DozerConverter<String, LocaleInfo> {

    public LocaleInfoConverter() {
        super(String.class, LocaleInfo.class);
    }

    @Override
    public LocaleInfo convertTo(String source, LocaleInfo destination) {
        LocaleInfo localeInfo = new LocaleInfo();
        localeInfo.setLocaleLanguage(source);
        return localeInfo;
    }

    @Override
    public String convertFrom(LocaleInfo source, String destination) {
        return source.getLocaleLanguage();
    }

}
