package org.kuali.student.core.atp.bo.options;

import org.kuali.student.core.atp.bo.DateRangeType;
import org.kuali.student.core.bo.KsTypeBusinessObject;
import org.kuali.student.core.bo.options.TypeValuesFinder;

public class DateRangeTypeValuesFinder extends TypeValuesFinder {

    @Override
    public Class<? extends KsTypeBusinessObject> getBusinessObjectClass() {
        return DateRangeType.class;
    }

}
