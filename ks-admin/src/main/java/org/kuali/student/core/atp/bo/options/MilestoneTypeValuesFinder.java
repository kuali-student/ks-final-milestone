package org.kuali.student.core.atp.bo.options;

import org.kuali.student.core.atp.bo.MilestoneType;
import org.kuali.student.core.bo.TypeBusinessObject;
import org.kuali.student.core.bo.options.TypeValuesFinder;

public class MilestoneTypeValuesFinder extends TypeValuesFinder {

    @Override
    public Class<? extends TypeBusinessObject> getBusinessObjectClass() {
        return MilestoneType.class;
    }

}
