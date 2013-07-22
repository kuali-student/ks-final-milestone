/**
 * Copyright 2011 The Kuali Foundation Licensed under the
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
 */

package org.kuali.student.krms.termresolver;

import org.kuali.rice.krms.api.engine.TermResolutionException;
import org.kuali.rice.krms.api.engine.TermResolver;
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;
import org.kuali.student.enrollment.courseregistration.dto.RegistrationRequestInfo;
import org.kuali.student.enrollment.courseregistration.dto.RegistrationRequestItemInfo;
import org.kuali.student.enrollment.courseregistration.service.CourseRegistrationService;
import org.kuali.student.krms.util.KSKRMSExecutionUtil;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.core.constants.KSKRMSServiceConstants;
import org.kuali.student.r2.lum.clu.service.CluService;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Return true if student is enrolled for course.
 */
public class EnrolledCourseTermResolver implements TermResolver<Boolean> {

    private CourseRegistrationService courseRegistrationService;
    private CourseOfferingService courseOfferingService;
    private CluService cluService;

    @Override
    public Set<String> getPrerequisites() {
        Set<String> prereqs = new HashSet<String>(2);
        prereqs.add(KSKRMSServiceConstants.TERM_PREREQUISITE_PERSON_ID);
        prereqs.add(KSKRMSServiceConstants.TERM_PREREQUISITE_CONTEXTINFO);
        return Collections.unmodifiableSet(prereqs);
    }

    @Override
    public String getOutput() {
        return KSKRMSServiceConstants.TERM_RESOLVER_ENROLLEDCOURSE;
    }

    @Override
    public Set<String> getParameterNames() {
        return Collections.singleton(KSKRMSServiceConstants.TERM_PARAMETER_TYPE_CLU_KEY);
    }

    @Override
    public int getCost() {
        // TODO Analyze, though probably not much to check here
        return 1;
    }

    @Override
    public Boolean resolve(Map<String, Object> resolvedPrereqs, Map<String, String> parameters) throws TermResolutionException {
        ContextInfo context = (ContextInfo) resolvedPrereqs.get(KSKRMSServiceConstants.TERM_PREREQUISITE_CONTEXTINFO);
        String personId = (String) resolvedPrereqs.get(KSKRMSServiceConstants.TERM_PREREQUISITE_PERSON_ID);
        String termId = (String) resolvedPrereqs.get(KSKRMSServiceConstants.TERM_PREREQUISITE_TERM_ID);

        try {
            //Retrieve the version independent clu id.
            String cluId = parameters.get(KSKRMSServiceConstants.TERM_PARAMETER_TYPE_CLU_KEY);
            //First check in the students current registration requests
            List<RegistrationRequestInfo> regRequests = courseRegistrationService.getUnsubmittedRegistrationRequestsByRequestorAndTerm(personId, termId, context) ;
            for(RegistrationRequestInfo request : regRequests){
                for(RegistrationRequestItemInfo regItem : request.getRegistrationRequestItems()){
                    //How to get the COs for regGroupID?
                }
            }


            //If not found, retrieve the students academic record and check for active registrations
//            List<CourseRegistrationInfo> regInfoList = courseRegistrationService.getCourseRegistrationsByStudent(personId, context);
//            for(CourseRegistrationInfo registration : regInfoList){
//                //We need the course offering to retrieve the courseid in order to retrieve the original course
//                CourseOffering courseOffering = this.courseOfferingService.getCourseOffering(registration.getCourseOfferingId(), context);
//                CluInfo clu = this.cluService.getClu(courseOffering.getCourseId(), context);
//                //If the version independent id is in the list is the same as parm, return true
//                if (cluId.equals(clu.getVersion().getVersionIndId())){
//                    return true;
//                }
//            }
        } catch (Exception e) {
            KSKRMSExecutionUtil.convertExceptionsToTermResolutionException(parameters, e, this);
        }

        return false;
    }

    public CourseRegistrationService getCourseRegistrationService() {
        return courseRegistrationService;
    }

    public void setCourseRegistrationService(CourseRegistrationService courseRegistrationService) {
        this.courseRegistrationService = courseRegistrationService;
    }

    public CourseOfferingService getCourseOfferingService() {
        return courseOfferingService;
    }

    public void setCourseOfferingService(CourseOfferingService courseOfferingService) {
        this.courseOfferingService = courseOfferingService;
    }

    public CluService getCluService() {
        return cluService;
    }

    public void setCluService(CluService cluService) {
        this.cluService = cluService;
    }

}
