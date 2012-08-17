package org.kuali.student.core.subjectcode.bo.options;

import org.kuali.student.core.bo.KsTypeBusinessObject;
import org.kuali.student.core.bo.options.TypeValuesFinder;
import org.kuali.student.core.subjectcode.bo.SubjectCodeType;

public class SubjectCodeTypeValuesFinder extends TypeValuesFinder {

    @Override
    public Class<? extends KsTypeBusinessObject> getBusinessObjectClass() {
        return SubjectCodeType.class;
    }

}
