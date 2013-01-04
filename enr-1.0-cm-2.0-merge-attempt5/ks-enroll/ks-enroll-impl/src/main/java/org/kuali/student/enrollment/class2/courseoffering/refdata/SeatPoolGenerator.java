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
 * Created by Charles on 11/13/12
 */
package org.kuali.student.enrollment.class2.courseoffering.refdata;

import org.kuali.rice.core.api.criteria.PredicateFactory;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.student.common.util.UUIDHelper;
import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.SeatPoolDefinitionInfo;
import org.kuali.student.enrollment.courseoffering.infc.SeatPoolDefinition;
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;
import org.kuali.student.enrollment.lui.service.LuiService;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.util.constants.LuiServiceConstants;

import java.util.ArrayList;
import java.util.List;

/**
 * This class //TODO ...
 *
 * @author Kuali Student Team
 */
public class SeatPoolGenerator {
    CourseOfferingService coService;
    LuiService luiService;

    List<String> atpIds = new ArrayList<String>();

    public SeatPoolGenerator() {
        atpIds.add("kuali.atp.2011Fall");
        atpIds.add("kuali.atp.2012Spring");
        atpIds.add("kuali.atp.2011Summer1");
        atpIds.add("kuali.atp.2011Spring");
        atpIds.add("kuali.atp.2012Winter");
        atpIds.add("kuali.atp.2011Winter");
        atpIds.add("kuali.atp.2012Summer1");
        atpIds.add("kuali.atp.2012Fall");
    }
    public void setCoService(CourseOfferingService coService) {
        this.coService = coService;
    }

    public void generateSeatpool() throws Exception {
        ContextInfo contextInfo = new ContextInfo();
        // CHEM484 796ec3cd-cf3e-4013-b9b1-6b20616fe109
        // AO 95642dc0-31c4-422d-aa45-067de9d8b28e
//        List<SeatPoolDefinitionInfo> infos =
//                coService.getSeatPoolDefinitionsForActivityOffering("796ec3cd-cf3e-4013-b9b1-6b20616fe109", contextInfo);
//        ActivityOfferingInfo aoInfo = coService.getActivityOffering("796ec3cd-cf3e-4013-b9b1-6b20616fe109", contextInfo);
//        System.err.println("Hello");
        for (int i = 0; i < 500; i++) {
            System.err.println(UUIDHelper.genStringUUID());
        }
        assert (Boolean.TRUE);
    }

}
