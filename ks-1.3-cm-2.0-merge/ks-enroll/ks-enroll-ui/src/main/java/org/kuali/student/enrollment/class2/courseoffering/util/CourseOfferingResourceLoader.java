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
 * Created by Li Pan on 5/7/12
 */
package org.kuali.student.enrollment.class2.courseoffering.util;

import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.student.enrollment.acal.constants.AcademicCalendarServiceConstants;
import org.kuali.student.enrollment.acal.service.AcademicCalendarService;
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;
import org.kuali.student.enrollment.courseofferingset.service.CourseOfferingSetService;
import org.kuali.student.r2.common.constants.StateServiceConstants;
import org.kuali.student.r2.lum.course.service.CourseService;
import org.kuali.student.r2.lum.clu.service.CluService;
import org.kuali.student.r2.common.constants.CommonServiceConstants;
import org.kuali.student.r2.common.util.constants.*;
import org.kuali.student.r2.common.state.service.StateService;
import org.kuali.student.r2.common.type.service.TypeService;
import org.kuali.student.r2.lum.lrc.service.LRCService;
import org.kuali.student.r2.lum.util.constants.CluServiceConstants;
import org.kuali.student.r2.lum.util.constants.LrcServiceConstants;

import javax.xml.namespace.QName;

/**
 * This class //TODO ...
 *
 * @author Kuali Student Team
 */
public class CourseOfferingResourceLoader {

    public static CourseOfferingService loadCourseOfferingService(){
        CourseOfferingService courseOfferingService = (CourseOfferingService) GlobalResourceLoader.getService(new QName(CourseOfferingServiceConstants.NAMESPACE, CourseOfferingServiceConstants.SERVICE_NAME_LOCAL_PART));
        return  courseOfferingService;
    }

    public static CourseOfferingSetService loadCourseOfferingSetService(){
        CourseOfferingSetService courseOfferingSetService = (CourseOfferingSetService) GlobalResourceLoader.getService(new QName(CourseOfferingSetServiceConstants.NAMESPACE, CourseOfferingSetServiceConstants.SERVICE_NAME_LOCAL_PART));
        return  courseOfferingSetService;
    }

    public static TypeService loadTypeService() {
        TypeService typeService = (TypeService) GlobalResourceLoader.getService(new QName(TypeServiceConstants.NAMESPACE, TypeServiceConstants.SERVICE_NAME_LOCAL_PART));

        return typeService;
    }

    public static StateService loadStateService() {
        StateService stateService = (StateService) GlobalResourceLoader.getService(new QName(StateServiceConstants.NAMESPACE, StateServiceConstants.SERVICE_NAME_LOCAL_PART));
        return stateService;
    }

    public static CourseService loadCourseService() {
        CourseService courseService = GlobalResourceLoader.getService(new QName(CommonServiceConstants.REF_OBJECT_URI_GLOBAL_PREFIX + "course", "CourseService"));
        return courseService;
    }

    public static CluService loadCluService() {
        CluService luService = (CluService)GlobalResourceLoader.getService(new QName(CluServiceConstants.CLU_NAMESPACE,"LuService"));
        return luService;
    }

    public static AcademicCalendarService loadAcademicCalendarService() {
        AcademicCalendarService acalService = (AcademicCalendarService)GlobalResourceLoader.getService(new QName(AcademicCalendarServiceConstants.NAMESPACE, AcademicCalendarServiceConstants.SERVICE_NAME_LOCAL_PART));
        return acalService;
    }

    public static LRCService loadLrcService() {
        LRCService lrcService = (LRCService)GlobalResourceLoader.getService(new QName(LrcServiceConstants.NAMESPACE, LrcServiceConstants.SERVICE_NAME_LOCAL_PART));
        return lrcService;
    }
}
