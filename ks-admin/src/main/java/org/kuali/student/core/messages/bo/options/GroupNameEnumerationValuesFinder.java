package org.kuali.student.core.messages.bo.options;

import org.kuali.student.core.enumerationmanagement.bo.options.EnumerationValuesFinderBase;

public class GroupNameEnumerationValuesFinder extends EnumerationValuesFinderBase {

    @Override
    public String getEnumerationKey() {
        return "ks.message.groupNames";
    }

}
