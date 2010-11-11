package org.kuali.student.lum.lu.bo.options;

import org.kuali.student.core.bo.KsTypeBusinessObject;
import org.kuali.student.core.bo.options.TypeValuesFinder;
import org.kuali.student.lum.lu.bo.LuLuRelationType;

public class LuLuRelationTypeValuesFinder extends TypeValuesFinder {

    @Override
    public Class<? extends KsTypeBusinessObject> getBusinessObjectClass() {
        return LuLuRelationType.class;
    }

}
