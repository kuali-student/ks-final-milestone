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
 * Created by vgadiyak on 6/13/12
 */
package org.kuali.student.enrollment.class2.courseoffering.keyvalue;

import org.kuali.rice.core.api.util.ConcreteKeyValue;
import org.kuali.rice.core.api.util.KeyValue;
import org.kuali.rice.krad.uif.control.UifKeyValuesFinderBase;
import org.kuali.rice.krad.uif.view.ViewModel;
import org.kuali.student.r2.common.util.constants.LuiServiceConstants;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * This class provides a key value finder for Waitlist types
 *
 * @author Kuali Student Team
 */
public class WaitlistTypeOptionsKeyValues extends UifKeyValuesFinderBase implements Serializable {

    private static final long serialVersionUID = 1L;

    @Override
    public List<KeyValue> getKeyValues(ViewModel model) {

        List<KeyValue> keyValues = new ArrayList<KeyValue>();

        List<String> waitlistTypes  = new ArrayList<String>(Arrays.asList(LuiServiceConstants.ALL_WAITLIST_TYPES));

        for(String waitlistType: waitlistTypes) {
            if (waitlistType.equals(LuiServiceConstants.AUTOMATIC_WAITLIST_TYPE_KEY)) {
                keyValues.add(new ConcreteKeyValue(waitlistType, "Automatic"));
            } else if (waitlistType.equals(LuiServiceConstants.CONFIRMATION_WAITLIST_TYPE_KEY)) {
                keyValues.add(new ConcreteKeyValue(waitlistType, "Confirmation"));
            } else if (waitlistType.equals(LuiServiceConstants.MANUAL_WAITLIST_TYPE_KEY)) {
                keyValues.add(new ConcreteKeyValue(waitlistType, "Manual"));
            }
        }

        return keyValues;
    }

}