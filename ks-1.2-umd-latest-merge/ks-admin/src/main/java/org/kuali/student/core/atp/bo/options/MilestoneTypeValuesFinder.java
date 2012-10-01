package org.kuali.student.core.atp.bo.options;

import org.kuali.student.core.atp.bo.MilestoneType;
import org.kuali.student.core.bo.KsTypeBusinessObject;
import org.kuali.student.core.bo.options.TypeValuesFinder;

public class MilestoneTypeValuesFinder extends TypeValuesFinder {

    @Override
    public Class<? extends KsTypeBusinessObject> getBusinessObjectClass() {
        return MilestoneType.class;
    }

}
