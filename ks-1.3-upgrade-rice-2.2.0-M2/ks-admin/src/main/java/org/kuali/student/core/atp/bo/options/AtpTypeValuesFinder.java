package org.kuali.student.core.atp.bo.options;

import org.kuali.student.core.atp.bo.AtpType;
import org.kuali.student.core.bo.KsTypeBusinessObject;
import org.kuali.student.core.bo.options.TypeValuesFinder;

public class AtpTypeValuesFinder extends TypeValuesFinder {

    @Override
    public Class<? extends KsTypeBusinessObject> getBusinessObjectClass() {
        return AtpType.class;
    }

}
