/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kuali.student.enrollment.process.service.integration.test;

import java.util.Arrays;
import org.kuali.student.r2.common.dto.ContextInfo;
import java.util.List;
import org.kuali.student.r2.lum.course.dto.CourseInfo;
import org.kuali.student.r2.lum.course.service.CourseService;
import org.kuali.student.r2.lum.course.service.CourseServiceDecorator;
import org.kuali.student.r2.lum.lrc.dto.ResultValuesGroupInfo;
import org.kuali.student.r2.lum.util.constants.CluServiceConstants;

/**
 *
 * @author nwright
 */
public class ProcessIntegrationTestCourseServiceDataLoadingDecorator extends CourseServiceDecorator {

    public ProcessIntegrationTestCourseServiceDataLoadingDecorator() {
    }

    public ProcessIntegrationTestCourseServiceDataLoadingDecorator(CourseService nextDecorator) {
        super();
        setNextDecorator(nextDecorator);
        init();
    }

    public void init() {
        ContextInfo contextInfo = new ContextInfo();
        contextInfo.setPrincipalId("Test-Initializer");

        CourseInfo course1 = this._createCourse("ENGL101", "ENGL", "101", "English 101", "3", contextInfo);

    }

    private CourseInfo _createCourse(String code, String subjArea, String courseNumb, String courseTitle, String credits, ContextInfo contextInfo) {
        CourseInfo info = new CourseInfo();
        info.setId(code);
        info.setCode(code);
        info.setSubjectArea(subjArea);
        info.setCourseNumberSuffix(courseNumb);
        info.setTypeKey(CluServiceConstants.CREDIT_COURSE_LU_TYPE_KEY);
        info.setStateKey("Active");
        info.setCourseTitle(courseTitle);
        info.setName(courseTitle);
        List<ResultValuesGroupInfo> creditOptions = Arrays.asList(this._createCreditOptions(credits, contextInfo));        
        info.setCreditOptions(creditOptions);
        try {
            info = this.createCourse(info, contextInfo);
        } catch (Exception ex) {
            throw new RuntimeException("error creating course", ex);
        }
        return info;
    }

    private ResultValuesGroupInfo _createCreditOptions(String credits, ContextInfo context) {
        ResultValuesGroupInfo info = new ResultValuesGroupInfo();
        info.setKey("kuali.creditType.credit.degree." + credits);
        info.setName(credits);
        info.setResultValueKeys(Arrays.asList(credits));
        return info;
    }
}
