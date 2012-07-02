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
 * Created by David Yin on 6/27/12
 */
package org.kuali.student.enrollment.class2.courseoffering.service.impl;

import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.krad.inquiry.InquirableImpl;
import org.kuali.student.enrollment.class2.courseoffering.dto.CourseOfferingEditWrapper;
import org.kuali.student.enrollment.class2.courseoffering.util.CourseOfferingResourceLoader;
import org.kuali.student.enrollment.common.util.ContextBuilder;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.FormatOfferingInfo;
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;
import org.kuali.student.lum.course.service.CourseService;
import org.kuali.student.lum.course.service.CourseServiceConstants;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.lum.lrc.infc.ResultValuesGroup;
import org.kuali.student.r2.lum.lrc.service.LRCService;

import javax.xml.namespace.QName;
import java.util.List;
import java.util.Map;

/**
 * This class //TODO ...
 *
 * @author Kuali Student Team
 */
public class CourseOfferingEditInquirableImpl extends InquirableImpl {
    private transient CourseOfferingService courseOfferingService;
    private CourseService courseService;
    private LRCService lrcService;
    private ContextInfo contextInfo = null;

    @Override
    public Object retrieveDataObject(Map<String, String> parameters) {
        String coInfoId = parameters.get("coInfo.id");
        if(coInfoId == null || "".equals(coInfoId)){
            coInfoId = parameters.get("id");
        }
        if(coInfoId == null || "".equals(coInfoId)){
            coInfoId = parameters.get("theCourseOffering.id");
        }
        ResultValuesGroup rvGroup = null;

        try {
            CourseOfferingInfo coInfo = getCourseOfferingService().getCourseOffering(coInfoId, getContextInfo());
            CourseOfferingEditWrapper formObject = new CourseOfferingEditWrapper(coInfo);
            List<FormatOfferingInfo> formats = getCourseOfferingService().getFormatOfferingsByCourseOffering(coInfoId, getContextInfo());
//            formObject.setFormatOfferingList(formats);
            formObject.setCoInfo(coInfo);

            //Display grading options
            String gradingOptId = coInfo.getGradingOptionId();
            if (gradingOptId != null && !gradingOptId.isEmpty()) {
                rvGroup = getLRCService().getResultValuesGroup(coInfo.getGradingOptionId(), getContextInfo());
                formObject.setSelectedGradingOptionName(rvGroup.getName());
            }

            //Display student registration options
            List<String> studentRegOptIds = coInfo.getStudentRegistrationOptionIds();
            String selectedstudentRegOpts = new String();

            if (studentRegOptIds != null && !studentRegOptIds.isEmpty()) {
                for (String studentRegOptId: coInfo.getStudentRegistrationOptionIds()) {
                    rvGroup = getLRCService().getResultValuesGroup(studentRegOptId, getContextInfo());
                    selectedstudentRegOpts = selectedstudentRegOpts + rvGroup.getName() + "|";

                }
                selectedstudentRegOpts = selectedstudentRegOpts.substring(0, selectedstudentRegOpts.length()-1);
                formObject.setSelectedstudentRegOpts(selectedstudentRegOpts);
            }

            return formObject;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public CourseOfferingService getCourseOfferingService() {
        if(courseOfferingService == null)
            courseOfferingService= CourseOfferingResourceLoader.loadCourseOfferingService();
        return courseOfferingService;
    }

    protected CourseService getCourseService() {
        if(courseService == null) {
            courseService = (CourseService) GlobalResourceLoader.getService(new QName(CourseServiceConstants.COURSE_NAMESPACE, "CourseService"));
        }
        return this.courseService;
    }

    protected LRCService getLRCService() {
        if(lrcService == null) {
            lrcService = (LRCService) GlobalResourceLoader.getService(new QName("http://student.kuali.org/wsdl/lrc", "LrcService"));
        }
        return this.lrcService;
    }

    public ContextInfo getContextInfo() {
        if (contextInfo == null){
            contextInfo =  ContextBuilder.loadContextInfo();
        }
        return contextInfo;
    }

}
