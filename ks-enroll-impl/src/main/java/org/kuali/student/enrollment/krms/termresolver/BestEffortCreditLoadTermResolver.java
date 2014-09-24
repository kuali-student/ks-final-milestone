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
 * Created by Charles on 5/29/14
 * Ported to a Term Resolver by Paul on 9/11/2014
 */
package org.kuali.student.enrollment.krms.termresolver;

import org.joda.time.DateTime;
import org.kuali.rice.krms.api.engine.TermResolutionException;
import org.kuali.student.common.util.krms.RulesExecutionConstants;
import org.kuali.student.enrollment.class2.courseoffering.krms.termresolver.util.CourseOfferingTermResolverSupport;
import org.kuali.student.enrollment.class2.courseoffering.krms.termresolver.util.TermResolverPerformanceUtil;
import org.kuali.student.enrollment.courseregistration.dto.CourseRegistrationInfo;
import org.kuali.student.enrollment.courseregistration.dto.RegistrationRequestItemInfo;
import org.kuali.student.enrollment.rules.credit.limit.CourseRegistrationServiceTypeStateConstants;
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
 * This class is a term resolver that does a credit load check for registration requests.
 *
 * @author Kuali Student Team
 */
public class BestEffortCreditLoadTermResolver extends CourseOfferingTermResolverSupport<Boolean> {

    private static final Logger LOGGER = LoggerFactory.getLogger(BestEffortCreditLoadTermResolver.class);

    public static final Float NO_CREDIT_LIMIT = -1f;
    private boolean countWaitlistedCoursesTowardsCreditLimit = true;

    @Override
    public Set<String> getPrerequisites() {
        Set<String> prereqs = new HashSet<>();
        prereqs.add(RulesExecutionConstants.CONTEXT_INFO_TERM.getName());
        prereqs.add(RulesExecutionConstants.REGISTRATION_REQUEST_ITEM_TERM.getName());
        prereqs.add(RulesExecutionConstants.EXISTING_REGISTRATIONS_TERM.getName());
        prereqs.add(RulesExecutionConstants.EXISTING_WAITLISTED_REGISTRATIONS_TERM.getName());
        prereqs.add(RulesExecutionConstants.SIMULATED_REGISTRATIONS_TERM.getName());
        prereqs.add(RulesExecutionConstants.MAX_CREDITS_TERM.getName());
        return Collections.unmodifiableSet(prereqs);
    }

    @Override
    public String getOutput() {
        return KSKRMSServiceConstants.TERM_RESOLVER_BEST_EFFORT_CREDIT_LOAD;
    }

    @Override
    public Set<String> getParameterNames() {
        return Collections.emptySet();
    }

    @Override
    public int getCost() {
        return 2;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Boolean resolve(Map<String, Object> resolvedPrereqs, Map<String, String> parameters) throws TermResolutionException {

        DateTime startTime = new DateTime();

        // Resolve pre-requisite terms
        ContextInfo contextInfo = (ContextInfo) resolvedPrereqs.get(RulesExecutionConstants.CONTEXT_INFO_TERM.getName());
        RegistrationRequestItemInfo requestItemInfo = (RegistrationRequestItemInfo) resolvedPrereqs.get(RulesExecutionConstants.REGISTRATION_REQUEST_ITEM_TERM.getName());
        List<CourseRegistrationInfo> existingRegistrations = (List<CourseRegistrationInfo>) resolvedPrereqs.get(RulesExecutionConstants.EXISTING_REGISTRATIONS_TERM.getName());
        List<CourseRegistrationInfo> existingWaitlist = (List<CourseRegistrationInfo>) resolvedPrereqs.get(RulesExecutionConstants.EXISTING_WAITLISTED_REGISTRATIONS_TERM.getName());
        List<CourseRegistrationInfo> simulatedRegistrations = (List<CourseRegistrationInfo>) resolvedPrereqs.get(RulesExecutionConstants.SIMULATED_REGISTRATIONS_TERM.getName());
        Float maxCredits = (Float) resolvedPrereqs.get(RulesExecutionConstants.MAX_CREDITS_TERM.getName());

        Boolean loadVerified = null;
        List<CourseRegistrationInfo> existingCrs = new ArrayList<>();

        try {
            // add existing registrations to list
            existingCrs.addAll(existingRegistrations);

            // add waitlist (if applicable)
            if (countWaitlistedCoursesTowardsCreditLimit) {
                existingCrs.addAll(existingWaitlist);
            }

            // add simulated registrations
            existingCrs.addAll(simulatedRegistrations);

            boolean addRequest = requestItemInfo.getTypeKey().equals(CourseRegistrationServiceTypeStateConstants.REQ_ITEM_ADD_TYPE_KEY);

            // If this is an add request, add it to the list of courses calculated
            if (addRequest) {
                CourseRegistrationInfo regItem = createNewCourseRegistration(requestItemInfo, contextInfo);
                existingCrs.add(regItem);
            } else {
                // see if we are editing an existing item
                if (requestItemInfo.getExistingCourseRegistrationId() != null) {
                    // update the existing cr with the new credit options
                    for (CourseRegistrationInfo existingCr: existingCrs) {
                        if (existingCr.getId().equals(requestItemInfo.getExistingCourseRegistrationId())) {
                            existingCr.setCredits(requestItemInfo.getCredits());
                            break;
                        }
                    }
                }
            }

            // Verify the credit load
            loadVerified = verifyLoadIsOK(existingCrs, maxCredits);
        } catch (Exception ex) {
            LOGGER.error("Exception trying to evaluate credit load", ex);
            KSKRMSExecutionUtil.convertExceptionsToTermResolutionException(parameters, ex, this);
        }

        if (loadVerified != null && !loadVerified) {
            LOGGER.warn("Credit Load check failed for {}. Total load: {}. Max credits allowed: {}",
                    contextInfo.getPrincipalId(), getTotalLoad(existingCrs), maxCredits);
        }

        DateTime endTime = new DateTime();
        TermResolverPerformanceUtil.putStatistics(getOutput(), startTime, endTime);

        // return the result
        return loadVerified;
    }

    /**
     * Verify that the sum of the course load (including waitlisted courses) is less than the limit.
     * @param existingCrs Course registrations taken by students including waitlisted courses
     * @param maxCredits The credit limit
     * @return true, if the courses do not exceed the credit limit
     */
    private boolean verifyLoadIsOK(List<CourseRegistrationInfo> existingCrs,
                                   Float maxCredits) {
        boolean loadIsOK;
        if (maxCredits.equals(NO_CREDIT_LIMIT)) {
            // If no limit is found, then NO_CREDIT_LIMIT is the value of the credit limit, which
            // indicates there's no credit limit.
            loadIsOK = true;
        } else {
            float totalLoad = getTotalLoad(existingCrs);
            loadIsOK = (totalLoad <= maxCredits);
        }
        return loadIsOK;
    }

    private float getTotalLoad(List<CourseRegistrationInfo> existingCrs) {
        Float totalLoad = 0f;
        for (CourseRegistrationInfo info: existingCrs) {
            totalLoad += info.getCredits().floatValue();
        }
        return totalLoad;
    }

    public void setCountWaitlistedCoursesTowardsCreditLimit(boolean countWaitlistedCoursesTowardsCreditLimit) {
        this.countWaitlistedCoursesTowardsCreditLimit = countWaitlistedCoursesTowardsCreditLimit;
    }

}
