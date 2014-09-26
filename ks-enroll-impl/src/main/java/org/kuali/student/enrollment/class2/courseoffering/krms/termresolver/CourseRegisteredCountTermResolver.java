/**
 * Copyright 2014 The Kuali Foundation Licensed under the
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
 * This class is a term resolver for retrieving the number of current
 * registrations for a student/course.
 *
 * Created by Paul Richardson on 8/19/14
 */
package org.kuali.student.enrollment.class2.courseoffering.krms.termresolver;

import org.joda.time.DateTime;
import org.kuali.rice.krms.api.engine.TermResolutionException;
import org.kuali.student.common.util.krms.RulesExecutionConstants;
import org.kuali.student.enrollment.class2.courseoffering.krms.termresolver.util.CourseOfferingTermResolverSupport;
import org.kuali.student.enrollment.courseregistration.dto.CourseRegistrationInfo;
import org.kuali.student.enrollment.courseregistration.dto.RegistrationRequestItemInfo;
import org.kuali.student.enrollment.registration.engine.util.RegEnginePerformanceUtil;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.krms.util.KSKRMSExecutionUtil;
import org.kuali.student.r2.core.constants.KSKRMSServiceConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Returns the number of current registrations for a student and course
 *
 * @author Kuali Student Team
 */
public class CourseRegisteredCountTermResolver extends CourseOfferingTermResolverSupport<Integer> {

    private static final Logger LOGGER = LoggerFactory.getLogger(CourseRegisteredCountTermResolver.class);

    @Override
    public String getOutput() {
        return KSKRMSServiceConstants.TERM_RESOLVER_COURSE_REGISTERED_COUNT;
    }

    @Override
    public Set<String> getParameterNames() {
        return Collections.emptySet();
    }

    @Override
    public Set<String> getPrerequisites() {
        Set<String> prereqs = new HashSet<>();
        prereqs.add(RulesExecutionConstants.CONTEXT_INFO_TERM.getName());
        prereqs.add(RulesExecutionConstants.REGISTRATION_REQUEST_ITEM_TERM.getName());
        prereqs.add(RulesExecutionConstants.EXISTING_REGISTRATIONS_TERM.getName());
        prereqs.add(RulesExecutionConstants.EXISTING_WAITLISTED_REGISTRATIONS_TERM.getName());
        prereqs.add(RulesExecutionConstants.SIMULATED_REGISTRATIONS_TERM.getName());

        return Collections.unmodifiableSet(prereqs);
    }

    @Override
    public int getCost() {
        return 1;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Integer resolve(Map<String, Object> resolvedPrereqs, Map<String, String> parameters) throws TermResolutionException {

        DateTime startTime = new DateTime();

        int registeredCount = 0;

        // Resolve pre-requisite terms
        ContextInfo contextInfo = (ContextInfo) resolvedPrereqs.get(RulesExecutionConstants.CONTEXT_INFO_TERM.getName());
        RegistrationRequestItemInfo requestItemInfo = (RegistrationRequestItemInfo) resolvedPrereqs.get(RulesExecutionConstants.REGISTRATION_REQUEST_ITEM_TERM.getName());
        List<CourseRegistrationInfo> existingRegistrations = (List<CourseRegistrationInfo>) resolvedPrereqs.get(RulesExecutionConstants.EXISTING_REGISTRATIONS_TERM.getName());
        List<CourseRegistrationInfo> existingWaitlist = (List<CourseRegistrationInfo>) resolvedPrereqs.get(RulesExecutionConstants.EXISTING_WAITLISTED_REGISTRATIONS_TERM.getName());
        List<CourseRegistrationInfo> simulatedRegistrations = (List<CourseRegistrationInfo>) resolvedPrereqs.get(RulesExecutionConstants.SIMULATED_REGISTRATIONS_TERM.getName());
        String regCourseOfferingId = null;
        try {
            CourseRegistrationInfo regItem = createNewCourseRegistration(requestItemInfo, contextInfo);
            regCourseOfferingId = regItem.getCourseOfferingId();

            List<CourseRegistrationInfo> crList = new ArrayList<>();
            crList.addAll(existingRegistrations);
            crList.addAll(existingWaitlist);
            crList.addAll(simulatedRegistrations);

            for (CourseRegistrationInfo cr:crList) {
                if (regCourseOfferingId.equals(cr.getCourseOfferingId())) {
                    registeredCount++;
                }
            }
        } catch (Exception ex) {
            LOGGER.error("Exception trying to resolve course registered count", ex);
            KSKRMSExecutionUtil.convertExceptionsToTermResolutionException(parameters, ex, this);
        }

        if (registeredCount > 0) {
            LOGGER.warn("Term Repeatability failed for {}, course offering id {}", contextInfo.getPrincipalId(), regCourseOfferingId);
        }

        DateTime endTime = new DateTime();
        RegEnginePerformanceUtil.putStatistics(RegEnginePerformanceUtil.TERMS, getOutput(), startTime, endTime);

        return registeredCount;
    }
}
