package org.kuali.student.core.atp.bo.options;

import org.kuali.rice.kns.bo.KualiType;
import org.kuali.rice.kns.bo.options.KualiTypeValuesFinder;
import org.kuali.student.core.atp.bo.AtpDurationType;

public class AtpDurationTypeValuesFinder extends KualiTypeValuesFinder {

    @Override
    public Class<? extends KualiType> getBusinessObjectClass() {
        return AtpDurationType.class;
    }

}
