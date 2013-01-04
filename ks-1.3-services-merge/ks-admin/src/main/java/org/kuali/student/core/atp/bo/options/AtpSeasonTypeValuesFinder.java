package org.kuali.student.core.atp.bo.options;

import org.kuali.student.core.atp.bo.AtpSeasonalType;
import org.kuali.student.core.bo.KsTypeBusinessObject;
import org.kuali.student.core.bo.options.TypeValuesFinder;

public class AtpSeasonTypeValuesFinder extends TypeValuesFinder {

    @Override
    public Class<? extends KsTypeBusinessObject> getBusinessObjectClass() {
        return AtpSeasonalType.class;
    }

}
