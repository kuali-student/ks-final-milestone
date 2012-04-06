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
 * Created by Daniel on 3/30/12
 */
package org.kuali.student.enrollment.class2.appointment.service.impl;

import org.kuali.rice.krad.lookup.LookupableImpl;
import org.kuali.rice.krad.web.form.LookupForm;
import org.kuali.student.enrollment.class2.appointment.dto.StudentGroupWrapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * This class //TODO ...
 *
 * @author Kuali Student Team
 */
public class StudentGroupWrapperLookupableImpl extends LookupableImpl {

    @Override
    protected List<?> getSearchResults(LookupForm lookupForm, Map<String, String> fieldValues, boolean unbounded) {
        List<StudentGroupWrapper> results = new ArrayList<StudentGroupWrapper>();

        StudentGroupWrapper sg1 = new StudentGroupWrapper();
        sg1.setDescription("Desc1");
        sg1.setName("Athletes");
        sg1.setId("Id1");
        results.add(sg1);

        StudentGroupWrapper sg2 = new StudentGroupWrapper();
        sg2.setDescription("Desc2");
        sg2.setName("Seniors");
        sg2.setId("Id2");
        results.add(sg2);

        return results;
    }
}
