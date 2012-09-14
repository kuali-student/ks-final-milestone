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
 * Created by vgadiyak on 9/10/12
 */
package org.kuali.student.enrollment.class2.scheduleofclasses.service.impl;

import org.kuali.rice.core.api.criteria.PredicateFactory;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.krad.uif.service.impl.ViewHelperServiceImpl;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.student.enrollment.class2.scheduleofclasses.form.ScheduleOfClassesSearchForm;
import org.kuali.student.enrollment.class2.scheduleofclasses.service.ScheduleOfClassesViewHelperService;
import org.kuali.student.enrollment.class2.scheduleofclasses.util.ScheduleOfClassesConstants;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingDisplayInfo;
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.LocaleInfo;
import org.kuali.student.r2.common.util.constants.CourseOfferingServiceConstants;

import javax.xml.namespace.QName;
import java.util.List;
import java.util.Locale;

/**
 * This class //TODO ...
 *
 * @author Kuali Student Team
 */
public class ScheduleOfClassesViewHelperServiceImpl extends ViewHelperServiceImpl implements ScheduleOfClassesViewHelperService {

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(ScheduleOfClassesViewHelperServiceImpl.class);

    private CourseOfferingService coService = null;

    public void loadCourseOfferingsByTermAndCourseCode (String termId, String courseCode, ScheduleOfClassesSearchForm form) throws Exception{

        // Building a query
        QueryByCriteria.Builder qbcBuilder = QueryByCriteria.Builder.create();
        qbcBuilder.setPredicates(PredicateFactory.and(
                PredicateFactory.like("courseOfferingCode", courseCode + "%"),
                PredicateFactory.equalIgnoreCase("atpId", termId)));
        QueryByCriteria criteria = qbcBuilder.build();
        List<String> courseOfferingIds = getCourseOfferingService().searchForCourseOfferingIds(criteria, getContextInfo());

        if(courseOfferingIds.size() > 0){
            form.getCoDisplayInfoList().clear();
            List<CourseOfferingDisplayInfo> coDisplayInfoList = getCourseOfferingService().getCourseOfferingDisplaysByIds(courseOfferingIds, getContextInfo());
            form.setCoDisplayInfoList(coDisplayInfoList);
        } else {
            LOG.error("Error: Can't find any Course Offering for a Course Code: " + courseCode + " in term: " + termId);
            GlobalVariables.getMessageMap().putError("Term & courseCode", ScheduleOfClassesConstants.SOC_MSG_ERROR_NO_COURSE_OFFERING_IS_FOUND, "courseCode", courseCode, termId);
            form.getCoDisplayInfoList().clear();
        }
    }

    private CourseOfferingService getCourseOfferingService() {
        if (coService == null) {
            coService = (CourseOfferingService) GlobalResourceLoader.getService(new QName(CourseOfferingServiceConstants.NAMESPACE,
                    CourseOfferingServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return coService;
    }

    public ContextInfo getContextInfo() {
        ContextInfo contextInfo = new ContextInfo();
        contextInfo.setAuthenticatedPrincipalId(GlobalVariables.getUserSession().getPrincipalId());
        contextInfo.setPrincipalId(GlobalVariables.getUserSession().getPrincipalId());
        LocaleInfo localeInfo = new LocaleInfo();
        localeInfo.setLocaleLanguage(Locale.getDefault().getLanguage());
        localeInfo.setLocaleRegion(Locale.getDefault().getCountry());
        contextInfo.setLocale(localeInfo);
        return contextInfo;
    }
}