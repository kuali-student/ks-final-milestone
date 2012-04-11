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
 * Created by Daniel on 3/29/12
 */
package org.kuali.student.enrollment.class2.appointment.keyvalue;

import org.kuali.rice.core.api.util.ConcreteKeyValue;
import org.kuali.rice.core.api.util.KeyValue;
import org.kuali.rice.krad.uif.control.UifKeyValuesFinderBase;
import org.kuali.rice.krad.uif.view.ViewModel;
import org.kuali.student.r2.core.population.service.PopulationService;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * This class //TODO ...
 *
 * @author Kuali Student Team
 */
public class StudentGroupsFinder extends UifKeyValuesFinderBase implements Serializable {

    private PopulationService populationService;

    private String[][] mockData = new String[][]{{"POP1","Athletes"},
            {"POP2","Seniors"}};
    
    @Override
    public List<KeyValue> getKeyValues(ViewModel model) {
        List<KeyValue> keyValues = new ArrayList<KeyValue>();
        
        for(String[] data:mockData){
            ConcreteKeyValue keyValue = new ConcreteKeyValue();
            keyValue.setKey(data[0]);
            keyValue.setValue(data[1]);
            keyValues.add(keyValue);
        }

        return keyValues;
    }
}
