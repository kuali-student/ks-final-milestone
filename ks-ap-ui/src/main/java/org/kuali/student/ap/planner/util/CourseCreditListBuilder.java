/*
 * Copyright 2014 The Kuali Foundation Licensed under the
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
 */

package org.kuali.student.ap.planner.util;

import org.kuali.rice.core.api.util.ConcreteKeyValue;
import org.kuali.rice.core.api.util.KeyValue;
import org.kuali.rice.krad.uif.control.UifKeyValuesFinderBase;
import org.kuali.rice.krad.uif.view.ViewModel;
import org.kuali.student.ap.coursesearch.CreditsFormatter;
import org.kuali.student.ap.planner.form.PlannerFormImpl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class CourseCreditListBuilder extends UifKeyValuesFinderBase {

    /**
     * Creates a list of values to display in a drop down control for the planner dialog credit values.
     *
     * @see UifKeyValuesFinderBase#getKeyValues(org.kuali.rice.krad.uif.view.ViewModel)
     * @param model - Page Form
     * @return A list of key value pairs representing the credit options to display in the drop down menu
     */
    @Override
    public List<KeyValue> getKeyValues(ViewModel model){
        PlannerFormImpl form = (PlannerFormImpl)model;

        // Compile list of possible credit values
        CreditsFormatter.Range range = CreditsFormatter.getRange(form.getCourse());
        List<String> variableCreditValues = new ArrayList<String>();
        if(range.getMultiple()!=null && !range.getMultiple().isEmpty()){
            for(BigDecimal value : range.getMultiple()){
                variableCreditValues.add(value.toString());
            }
        } else{
            if(range.getMin()==range.getMax()){
                variableCreditValues.add(range.getMax().toString());
            }else{
                for(int i = range.getMin().intValue();i<=range.getMax().intValue();i++){
                    variableCreditValues.add(i+"");
                }
            }
        }

        // Compile List of credit values into key value pairs
        List<KeyValue> keyValues = new ArrayList<KeyValue>();
        keyValues.add(new ConcreteKeyValue("-1","Select"));
        for(String value : variableCreditValues){
            KeyValue keyValue = new ConcreteKeyValue(value,value);
            keyValues.add(keyValue);
        }

        return keyValues;
    }
}
