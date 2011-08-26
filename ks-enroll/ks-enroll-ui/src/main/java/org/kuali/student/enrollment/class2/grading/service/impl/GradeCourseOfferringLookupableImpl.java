package org.kuali.student.enrollment.class2.grading.service.impl;

/*
 * Copyright 2007 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 1.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl1.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.krad.lookup.LookupableImpl;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.web.form.LookupForm;
import org.kuali.student.enrollment.class2.grading.util.GradingConstants;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;
import org.kuali.student.enrollment.grading.dto.GradeRosterInfo;
import org.kuali.student.enrollment.grading.service.GradingService;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.*;

import javax.xml.namespace.QName;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GradeCourseOfferringLookupableImpl extends LookupableImpl {

    @Override
    protected List<?> getSearchResults(LookupForm lookupForm, Map<String, String> fieldValues, boolean unbounded) {
        ContextInfo context = ContextInfo.newInstance();

        String termKey = fieldValues.get("termKey");

        String currentUser = GlobalVariables.getUserSession().getPrincipalId();
        List<CourseOfferingInfo> courseOfferingInfoList = new ArrayList();

        GradingService gradingService = (GradingService) GlobalResourceLoader.getService(new QName(GradingConstants.GRADING_SERVICE_URL, GradingConstants.GRADING_SERVICE_NAME));
        CourseOfferingService courseOfferingService = (CourseOfferingService)GlobalResourceLoader.getService(new QName("http://student.kuali.org/wsdl/courseOffering", "CourseOfferingMockService"));
        try {
            List<GradeRosterInfo> gradeRosterInfoList = gradingService.getGradeRostersByGraderAndTerm(currentUser, termKey, context);
            if (gradeRosterInfoList != null){
                List<String> courseOfferingIds = new ArrayList();
                for (GradeRosterInfo gradeInfo : gradeRosterInfoList){
                     courseOfferingIds.add(gradeInfo.getCourseOfferingId());
                }
                courseOfferingInfoList = courseOfferingService.getCourseOfferingsByIdList(courseOfferingIds, context);
            }

        } catch (DoesNotExistException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (InvalidParameterException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (MissingParameterException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (OperationFailedException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (PermissionDeniedException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        return courseOfferingInfoList;
    }
}
