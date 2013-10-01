/**
 * Copyright 2013 The Kuali Foundation Licensed under the
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
 */
package org.kuali.student.enrollment.class1.timeslot.keyvalue;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.core.api.util.KeyValue;
import org.kuali.rice.krad.uif.control.UifKeyValuesFinderBase;
import org.kuali.rice.krad.uif.view.ViewModel;
import org.kuali.student.enrollment.class1.timeslot.form.TimeSlotForm;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Options finder to which creates a list of time slot types based on the items that the user selected
 * in the time slot type multi-select.
 */
public class SelectedTimeSlotTypesOptionsFinder extends UifKeyValuesFinderBase implements Serializable {

    @Override
    public List<KeyValue> getKeyValues(ViewModel model) {
        TimeSlotForm form = (TimeSlotForm)model;
        /*
         * Get all of the times slot type options from the form and create a list of
         * KeyValues based on the options that the user selected in the time slot type multi-select.
         */
        List<KeyValue> allTimeSlotTypeKeyValues = form.getTimeSlotTypeKeyValues();

        List<KeyValue> keyValuePairs = new ArrayList<KeyValue>();

        for (String type : form.getTermTypeSelections()){
            KeyValue matchingKeyValue = getTimeSlotKeyValue(allTimeSlotTypeKeyValues,type);
            if (matchingKeyValue != null){
                keyValuePairs.add(matchingKeyValue);
            }
        }
        return keyValuePairs;
    }

    private KeyValue getTimeSlotKeyValue(List<KeyValue> timeslotKeyValues,String selectedType){
        for(KeyValue keyValue : timeslotKeyValues){
            if (StringUtils.equals(selectedType,keyValue.getKey())){
                return keyValue;
            }
        }
        return null;
    }
}
