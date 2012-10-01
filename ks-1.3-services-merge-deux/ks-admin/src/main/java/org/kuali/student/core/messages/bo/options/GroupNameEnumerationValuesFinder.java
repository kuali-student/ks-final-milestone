package org.kuali.student.core.messages.bo.options;

import org.kuali.student.core.enumerationmanagement.bo.options.EnumerationCodeValuesFinderBase;
import org.kuali.student.core.messages.bo.MessageEntity;

public class GroupNameEnumerationValuesFinder extends EnumerationCodeValuesFinderBase {

    @Override
    public String getEnumerationKey() {
        return MessageEntity.GROUP_NAME_ENUMERATION;
    }

}
