package org.kuali.student.core.atp.bo.options;

import org.kuali.student.core.atp.bo.DateRangeType;
import org.kuali.student.core.bo.TypeBusinessObject;
import org.kuali.student.core.bo.options.TypeValuesFinder;

public class DateRangeTypeValuesFinder extends TypeValuesFinder {

    @Override
    public Class<? extends TypeBusinessObject> getBusinessObjectClass() {
        return DateRangeType.class;
    }

}
