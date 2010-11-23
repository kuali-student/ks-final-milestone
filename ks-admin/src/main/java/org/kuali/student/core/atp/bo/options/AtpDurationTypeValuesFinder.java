package org.kuali.student.core.atp.bo.options;

import org.kuali.student.core.atp.bo.AtpDurationType;
import org.kuali.student.core.bo.KsTypeBusinessObject;
import org.kuali.student.core.bo.options.TypeValuesFinder;

public class AtpDurationTypeValuesFinder extends TypeValuesFinder {

    @Override
    public Class<? extends KsTypeBusinessObject> getBusinessObjectClass() {
        return AtpDurationType.class;
    }

}
