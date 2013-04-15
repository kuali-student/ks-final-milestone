package org.kuali.student.core.messages.bo.options;

import org.kuali.student.core.enumerationmanagement.bo.options.EnumerationCodeValuesFinderBase;
import org.kuali.student.core.messages.bo.MessageEntity;

public class LocaleEnumerationValuesFinder extends EnumerationCodeValuesFinderBase {

    @Override
    public String getEnumerationKey() {
        return MessageEntity.LOCALE_ENUMERATION;
    }

}
