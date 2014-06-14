package org.kuali.student.enrollment.class2.courseoffering.krms.termresolver.util;

import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.RegistrationGroupInfo;
import org.kuali.student.enrollment.courseoffering.infc.CourseOffering;
import org.kuali.student.enrollment.courseregistration.dto.CourseRegistrationInfo;
import org.kuali.student.enrollment.courseregistration.dto.RegistrationRequestInfo;
import org.kuali.student.enrollment.courseregistration.dto.RegistrationRequestItemInfo;
import org.kuali.student.enrollment.courseregistration.service.CourseRegistrationService;
import org.kuali.student.r2.common.dto.ContextInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SW Genis on 2014/06/14.
 */
public abstract class CourseRegistrationTermResolverSupport<T> extends CourseOfferingTermResolverSupport<T> {

    private CourseRegistrationService courseRegistrationService;

    public boolean checkCourseEnrolled(String personId, String versionIndId, String termId, ContextInfo context) throws Exception {
        List<String> courseIds = this.getCluIdsFromVersionIndId(versionIndId, context);

        //First check in the students current registration requests
        List<String> regGroupIds = new ArrayList<String>();
        List<RegistrationRequestInfo> regRequests = this.getCourseRegistrationService().getUnsubmittedRegistrationRequestsByRequestorAndTerm(personId, termId, context) ;
        for(RegistrationRequestInfo request : regRequests){
            for(RegistrationRequestItemInfo regItem : request.getRegistrationRequestItems()){
                if(regItem.getExistingCourseRegistrationId()!=null){
                    // FIXME KSENROLL-11465
                    // the existing registration is an lpr  so this can't work this way.
                    regGroupIds.remove(regItem.getExistingCourseRegistrationId());
                }
                regGroupIds.add(regItem.getRegistrationGroupId());
            }
        }

        //Check to see if one of the course version is in the registration request.
        for(String regGroupId : regGroupIds){
            RegistrationGroupInfo regGroup = this.getCourseOfferingService().getRegistrationGroup(regGroupId, context);
            CourseOfferingInfo courseOffering = this.getCourseOfferingService().getCourseOffering(regGroup.getCourseOfferingId(), context);
            if(courseIds.contains(courseOffering.getCourseId())){
                return true;
            }
        }

        //Also check for already enrolled but not yet completed courses.
        List<CourseRegistrationInfo> recordInfoList = this.getCourseRegistrationService().getCourseRegistrationsByStudent(personId, context);
        for(CourseRegistrationInfo studentRecord : recordInfoList){
            CourseOffering courseOffering = this.getCourseOfferingService().getCourseOffering(studentRecord.getCourseOfferingId(), context);
            if(courseIds.contains(courseOffering.getCourseId())){
                return true;
            }
        }

        return false;
    }

    public CourseRegistrationService getCourseRegistrationService() {
        return courseRegistrationService;
    }

    public void setCourseRegistrationService(CourseRegistrationService courseRegistrationService) {
        this.courseRegistrationService = courseRegistrationService;
    }
}
