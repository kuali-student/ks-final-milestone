/**
 * Copyright 2012 The Kuali Foundation Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 *
 * Created by vgadiyak on 6/12/12
 */
package org.kuali.student.enrollment.class2.courseoffering.keyvalue;

import org.kuali.rice.core.api.util.ConcreteKeyValue;
import org.kuali.rice.core.api.util.KeyValue;
import org.kuali.rice.krad.keyvalues.KeyValuesBase;
import org.kuali.student.enrollment.courseoffering.dto.WaitlistLevel;

import java.io.Serializable;
import java.util.*;

/**
 * This class //TODO ...
 *
 * @author Kuali Student Team
 */
public class WaitlistLevelOptionsKeyValues extends KeyValuesBase implements Serializable {

    private static final long serialVersionUID = 1L;

    @Override
    public List<KeyValue> getKeyValues() {

        List<KeyValue> keyValues = new ArrayList<KeyValue>();

        for (WaitlistLevel waitlistLevel : WaitlistLevel.values()) {
            if (waitlistLevel.equals(WaitlistLevel.COURSE_OFFERING)) {
                keyValues.add(new ConcreteKeyValue(waitlistLevel.toString(), "Course Offering"));
            } else if (waitlistLevel.equals(WaitlistLevel.ACTIVITY_OFFERING)) {
                keyValues.add(new ConcreteKeyValue(waitlistLevel.toString(), "Activity Offering"));
            }
        }

        return keyValues;
    }

}