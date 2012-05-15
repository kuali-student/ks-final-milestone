package org.kuali.student.enrollment.class2.courseoffering.service.impl;

import org.kuali.rice.krad.inquiry.InquirableImpl;
import org.kuali.student.enrollment.class2.courseoffering.util.CourseOfferingResourceLoader;
import org.kuali.student.enrollment.common.util.ContextBuilder;
import org.kuali.student.enrollment.courseofferingset.dto.SocRolloverResultInfo;
import org.kuali.student.enrollment.courseofferingset.service.CourseOfferingSetService;
import org.kuali.student.enrollment.class2.courseoffering.util.CourseOfferingConstants;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.*;

import java.util.Map;

public class SocRolloverResultInfoInquirableImpl extends InquirableImpl {
    private transient CourseOfferingSetService courseOfferingSetService = null;
    private ContextInfo contextInfo = null;
    @Override
    public SocRolloverResultInfo retrieveDataObject(Map<String, String> parameters) {
        try {
            SocRolloverResultInfo socRolloverResultInfo = getCourseOfferingSetService().getSocRolloverResult(
                                    parameters.get(CourseOfferingConstants.SOCROLLOVERRESULTINFO_ID), getContextInfo());
            return socRolloverResultInfo;
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

        return null;
    }


    public CourseOfferingSetService getCourseOfferingSetService() {
        if(courseOfferingSetService == null)
            courseOfferingSetService= CourseOfferingResourceLoader.loadCourseOfferingSetService();
        return courseOfferingSetService;
    }

    public ContextInfo getContextInfo() {
        if (contextInfo == null){
            contextInfo =  ContextBuilder.loadContextInfo();
        }
        return contextInfo;
    }
}
