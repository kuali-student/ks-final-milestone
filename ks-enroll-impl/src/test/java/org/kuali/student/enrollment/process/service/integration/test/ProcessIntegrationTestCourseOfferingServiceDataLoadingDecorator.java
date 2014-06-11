/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kuali.student.enrollment.process.service.integration.test;

import java.util.Arrays;
import java.util.Collections;
import org.kuali.student.enrollment.class2.courseoffering.service.decorators.CourseOfferingServiceDecorator;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.FormatOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.RegistrationGroupInfo;
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;
import org.kuali.student.r2.common.util.constants.LuiServiceConstants;
import org.kuali.student.r2.lum.lrc.dto.ResultValuesGroupInfo;

/**
 *
 * @author nwright
 */
public class ProcessIntegrationTestCourseOfferingServiceDataLoadingDecorator extends CourseOfferingServiceDecorator {

    public ProcessIntegrationTestCourseOfferingServiceDataLoadingDecorator() {
    }

    public ProcessIntegrationTestCourseOfferingServiceDataLoadingDecorator(CourseOfferingService nextDecorator) {
        super();
        setNextDecorator(nextDecorator);
        init();
    }

    public void init() {
        ContextInfo contextInfo = new ContextInfo();
        contextInfo.setPrincipalId("Test-Initializer");

        CourseOfferingInfo co = this._createCO("ENGL101-FA2011", "ENGL101", "kuali.atp.FA2011", "ENGL", "101", "English 101", "3",
                contextInfo);

    }

    private CourseOfferingInfo _createCO(String id, String courseId, String termId, String subjArea, String courseNumb,
            String courseTitle, String credits, ContextInfo contextInfo) {
        CourseOfferingInfo co = new CourseOfferingInfo();

        co.setId(id);
        co.setCourseId(courseId);
        co.setTermId(termId);
        co.setCourseOfferingCode(courseId);
        co.setTypeKey(LuiServiceConstants.COURSE_OFFERING_TYPE_KEY);
        co.setStateKey(LuiServiceConstants.LUI_CO_STATE_OFFERED_KEY);
        co.setCourseOfferingTitle(courseTitle);
        co.setSubjectArea(subjArea);
        co.setCourseNumberSuffix(courseNumb);
        ResultValuesGroupInfo creditOption = this._createCreditOptions(credits, contextInfo);
        co.setCreditOptionId(creditOption.getKey());
        co.setCreditCnt(credits);
        try {
            co = this.createCourseOffering(co.getCourseId(), co.getTermId(), co.getTypeKey(), co, Collections.EMPTY_LIST,
                    contextInfo);
        } catch (Exception ex) {
            throw new RuntimeException("error creating course", ex);
        }
        FormatOfferingInfo fo = new FormatOfferingInfo();
        fo.setId(co.getId());
        fo.setFormatId(co.getId() + "FO");
        fo.setCourseOfferingId(co.getId());
        fo.setTypeKey(LuiServiceConstants.FORMAT_OFFERING_TYPE_KEY);
        fo.setStateKey(LuiServiceConstants.LUI_FO_STATE_OFFERED_KEY);
        try {
            fo = this.createFormatOffering(fo.getCourseOfferingId(), fo.getFormatId(), fo.getTypeKey(), fo, contextInfo);
        } catch (Exception ex) {
            throw new RuntimeException("error creating course offering", ex);
        }

        ActivityOfferingInfo ao = new ActivityOfferingInfo();
        ao.setCourseOfferingId(co.getId());
        ao.setFormatOfferingId(fo.getId());
        ao.setActivityId(courseId + "Lecture");
        ao.setTypeKey(LuiServiceConstants.LECTURE_ACTIVITY_OFFERING_TYPE_KEY);
        ao.setStateKey(LuiServiceConstants.LUI_AO_STATE_OFFERED_KEY);
        try {
            ao = this.createActivityOffering(ao.getFormatOfferingId(), ao.getActivityId(), ao.getTypeKey(), ao, contextInfo);
        } catch (Exception ex) {
            throw new RuntimeException("error creating course", ex);
        }

        RegistrationGroupInfo rg = new RegistrationGroupInfo();
        rg.setId(id);
        rg.setActivityOfferingIds(Arrays.asList(ao.getId()));
        rg.setFormatOfferingId(fo.getId());
        rg.setCourseOfferingId(co.getId());
        rg.setActivityOfferingClusterId(id); // ???
        rg.setIsGenerated(false);
        rg.setTermId(termId);
        rg.setTypeKey(LuiServiceConstants.REGISTRATION_GROUP_TYPE_KEY);
        rg.setStateKey(LuiServiceConstants.REGISTRATION_GROUP_OFFERED_STATE_KEY);
        try {
            rg = this.createRegistrationGroup(rg.getFormatOfferingId(), rg.getActivityOfferingClusterId(), rg.getTypeKey(), rg,
                    contextInfo);
        } catch (Exception ex) {
            throw new RuntimeException("error creating course", ex);
        }

        return co;
    }

    private ResultValuesGroupInfo _createCreditOptions(String credits, ContextInfo context) {
        ResultValuesGroupInfo info = new ResultValuesGroupInfo();
        info.setKey("kuali.creditType.credit.degree." + credits);
        info.setName(credits);
        info.setResultValueKeys(Arrays.asList(credits));
        return info;
    }
}
