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
 * Created by venkat on 3/12/13
 */
package org.kuali.student.enrollment.class2.courseoffering.keyvalue;

import org.kuali.rice.core.api.util.ConcreteKeyValue;
import org.kuali.rice.core.api.util.KeyValue;
import org.kuali.rice.krad.uif.control.UifKeyValuesFinderBase;
import org.kuali.rice.krad.uif.view.ViewModel;
import org.kuali.rice.krad.web.form.MaintenanceDocumentForm;
import org.kuali.student.enrollment.class2.courseoffering.dto.ActivityOfferingWrapper;
import org.kuali.student.enrollment.class2.courseoffering.dto.ColocatedActivity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * This class //TODO ...
 *
 * @author Kuali Student Team
 */
class ColocatedAOsKeyValues extends UifKeyValuesFinderBase implements Serializable {

    private static final long serialVersionUID = 1L;

    @Override
    public List<KeyValue> getKeyValues(ViewModel model) {

        MaintenanceDocumentForm form = (MaintenanceDocumentForm)model;
        ActivityOfferingWrapper wrapper = (ActivityOfferingWrapper) form.getDocument().getNewMaintainableObject().getDataObject();
        List<KeyValue> keyValues = new ArrayList<KeyValue>();
        for(ColocatedActivity colo : wrapper.getColocatedActivities()){
            keyValues.add(new ConcreteKeyValue(colo.getEditRenderHelper().getCode(),colo.getEditRenderHelper().getCode()));
        }

        return keyValues;

    }

}
