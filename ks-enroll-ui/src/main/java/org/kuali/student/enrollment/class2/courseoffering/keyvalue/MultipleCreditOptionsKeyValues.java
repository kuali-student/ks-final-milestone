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
 * Created by Daniel on 7/6/12
 */
package org.kuali.student.enrollment.class2.courseoffering.keyvalue;

import org.kuali.rice.core.api.util.ConcreteKeyValue;
import org.kuali.rice.core.api.util.KeyValue;
import org.kuali.rice.krad.uif.control.UifKeyValuesFinderBase;
import org.kuali.rice.krad.uif.view.ViewModel;
import org.kuali.rice.krad.web.form.MaintenanceDocumentForm;
import org.kuali.student.enrollment.class2.courseoffering.dto.CourseOfferingEditWrapper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * This class makes key values based on allowed credit options from the course offering
 *
 * @author Kuali Student Team
 */
public class MultipleCreditOptionsKeyValues extends UifKeyValuesFinderBase implements Serializable {

    private static final long serialVersionUID = 1L;

    @Override
    public List<KeyValue> getKeyValues(ViewModel model) {
        MaintenanceDocumentForm form1 = (MaintenanceDocumentForm)model;
        CourseOfferingEditWrapper form = (CourseOfferingEditWrapper)form1.getDocument().getDocumentDataObject();

        List<KeyValue> keyValues = new ArrayList<KeyValue>();

        List<Float> creditOptionsF = new ArrayList();
        for(String creditOption : form.getCreditOption().getAllowedCredits()){
            creditOptionsF.add(Float.valueOf(creditOption));
        }
        Collections.sort(creditOptionsF);
        for(Float creditOption : creditOptionsF){
            keyValues.add(new ConcreteKeyValue(String.valueOf(creditOption), String.valueOf(creditOption)));
        }

        return keyValues;
    }

}
