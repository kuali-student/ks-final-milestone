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
 * Created by cmuller on 9/10/12
 */
package org.kuali.student.enrollment.class2.scheduleofclasses.keyvalue;

import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.core.api.util.ConcreteKeyValue;
import org.kuali.rice.core.api.util.KeyValue;
import org.kuali.rice.krad.uif.control.UifKeyValuesFinderBase;
import org.kuali.rice.krad.uif.view.ViewModel;
import org.kuali.student.enrollment.class2.scheduleofclasses.util.ScheduleOfClassesUtil;
import org.kuali.student.enrollment.courseofferingset.service.CourseOfferingSetService;
import org.kuali.student.mock.utilities.TestHelper;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.util.constants.CourseOfferingSetServiceConstants;
import org.kuali.student.r2.core.atp.dto.AtpInfo;
import org.kuali.student.r2.core.atp.service.AtpService;
import org.kuali.student.r2.core.constants.AtpServiceConstants;

import javax.xml.namespace.QName;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * This class is used to populate the term dropdown list on the Schedule of Classes page.
 * The list should contain only Valid Soc Terms.
 *
 * @author Kuali Student Team
 */
public class ScheduleOfClassesTermKeyValues extends UifKeyValuesFinderBase implements Serializable {
    private static final long serialVersionUID = 1L;
    private transient CourseOfferingSetService courseOfferingSetService;
    private transient AtpService atpService;

    @Override
    public List<KeyValue> getKeyValues(ViewModel model) {

        List<KeyValue> keyValues = new ArrayList<KeyValue>();
        List<AtpInfo> atps;

        //Build a predicate to search for published Socs
        ContextInfo context = TestHelper.getContext1();
        try {
            //Use AtpService to get Term name by Id
            atps = ScheduleOfClassesUtil.getValidSocTerms(getCourseOfferingSetService(),getAtpService(),context);
            for(AtpInfo atp: atps){
                 keyValues.add(new ConcreteKeyValue(atp.getId(), atp.getName()));
            }
        } catch (Exception e) {
            throw new RuntimeException("Error Performing Search", e);
        }

        //Return published term names
        return keyValues;
    }

    //Methods to get necessary services
    protected CourseOfferingSetService getCourseOfferingSetService() {
        if(courseOfferingSetService == null) {
            courseOfferingSetService = (CourseOfferingSetService) GlobalResourceLoader.getService(new QName(CourseOfferingSetServiceConstants.NAMESPACE, CourseOfferingSetServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return this.courseOfferingSetService;
    }

    protected AtpService getAtpService() {
        if(atpService == null) {
            atpService = (AtpService) GlobalResourceLoader.getService(new QName(AtpServiceConstants.NAMESPACE, AtpServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return this.atpService;
    }
}