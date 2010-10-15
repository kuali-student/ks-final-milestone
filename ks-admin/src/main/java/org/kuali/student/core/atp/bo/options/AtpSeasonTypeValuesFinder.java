package org.kuali.student.core.atp.bo.options;

import org.kuali.student.core.atp.bo.AtpSeasonalType;
import org.kuali.student.core.bo.TypeBusinessObject;
import org.kuali.student.core.bo.options.TypeValuesFinder;

public class AtpSeasonTypeValuesFinder extends TypeValuesFinder {

    @Override
    public Class<? extends TypeBusinessObject> getBusinessObjectClass() {
        return AtpSeasonalType.class;
    }

}
