package org.kuali.student.enrollment.class2.courseoffering.keyvalue;

import org.kuali.rice.core.api.util.ConcreteKeyValue;
import org.kuali.rice.core.api.util.KeyValue;
import org.kuali.rice.krad.keyvalues.KeyValuesBase;
import org.kuali.student.enrollment.class2.courseoffering.util.CourseOfferingConstants;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ActivityOfferingActionsKeyValues extends KeyValuesBase implements Serializable {

    @Override
    public List<KeyValue> getKeyValues() {

        List<KeyValue> keyValues = new ArrayList<KeyValue>();
        keyValues.add(new ConcreteKeyValue(CourseOfferingConstants.ACTIVITY_OFFERING_SCHEDULING_ACTION,"Approve for Scheduling"));
        keyValues.add(new ConcreteKeyValue(CourseOfferingConstants.ACTIVITY_OFFERING_DRAFT_ACTION,"Set as Draft"));
        keyValues.add(new ConcreteKeyValue(CourseOfferingConstants.ACTIVITY_OFFERING_DELETE_ACTION,"Delete"));

        return keyValues;
    }
}
