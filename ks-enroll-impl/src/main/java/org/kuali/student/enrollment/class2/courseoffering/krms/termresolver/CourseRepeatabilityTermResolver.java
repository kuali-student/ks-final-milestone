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
 * This class is a term resolver for checking the total course attempts against
 * the max repeatability rules for that course.
 *
 * Returns a string indicating error, warning, or success
 *
 * Created by Paul Richardson on 8/19/14
 */
package org.kuali.student.enrollment.class2.courseoffering.krms.termresolver;

import org.joda.time.DateTime;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.krms.api.KrmsConstants;
import org.kuali.rice.krms.api.engine.TermResolutionException;
import org.kuali.rice.krms.api.engine.TermResolver;
import org.kuali.rice.krms.api.repository.RuleManagementService;
import org.kuali.rice.krms.api.repository.agenda.AgendaDefinition;
import org.kuali.rice.krms.api.repository.reference.ReferenceObjectBinding;
import org.kuali.student.common.util.krms.RulesExecutionConstants;
import org.kuali.student.enrollment.courseoffering.dto.RegistrationGroupInfo;
import org.kuali.student.enrollment.registration.engine.util.RegEnginePerformanceUtil;
import org.kuali.student.r2.common.util.constants.CourseOfferingServiceConstants;
import org.kuali.student.r2.core.constants.KSKRMSServiceConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.namespace.QName;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Returns String based on:
 * -- total times the course has been attempted (prereq)
 * -- max repeatability for this course (prereq)
 * <p/>
 * If total attempts >= max repeats, return an error string
 * If total attempts < max repeats and total attempts >= (max repeats - 1), return a warning string
 * Otherwise return a success string
 *
 * In addition, if there is a repeatability rule on the course offering, the result will always be a
 * success string
 *
 * @author Kuali Student Team
 */
public class CourseRepeatabilityTermResolver implements TermResolver<String> {

    private static final Logger LOGGER = LoggerFactory.getLogger(CourseRepeatabilityTermResolver.class);

    public final static String MAX_REPEATABILITY_ERROR = "kuali.max.repeatability.error";
    public final static String MAX_REPEATABILITY_WARNING = "kuali.max.repeatability.warning";
    public final static String MAX_REPEATABILITY_SUCCESS = "kuali.max.repeatability.success";

    public static final String KRMS_COURSE_CONTEXT = "10000";
    public static final String KRMS_REPEATABILITY_TYPE = "10003";
    public static final String KRMS_AGENDA = "Agenda";

    private RuleManagementService ruleManagementService;

    @Override
    public String getOutput() {
        return KSKRMSServiceConstants.TERM_RESOLVER_COURSE_REPEATABILITY;
    }

    @Override
    public Set<String> getParameterNames() {
        return Collections.emptySet();
    }

    @Override
    public Set<String> getPrerequisites() {
        Set<String> prereqs = new HashSet<>(2);
        prereqs.add(RulesExecutionConstants.TOTAL_COURSE_ATTEMPTS_TERM.getName());
        prereqs.add(RulesExecutionConstants.MAX_REPEATABILITY_TERM.getName());
        prereqs.add(RulesExecutionConstants.REGISTRATION_GROUP_TERM.getName());
        return Collections.unmodifiableSet(prereqs);
    }

    @Override
    public int getCost() {
        return 3;
    }

    @Override
    public String resolve(Map<String, Object> resolvedPrereqs, Map<String, String> parameters) throws TermResolutionException {

        DateTime startTime = new DateTime();

        Integer maxRepeats = (Integer) resolvedPrereqs.get(RulesExecutionConstants.MAX_REPEATABILITY_TERM.getName());
        Integer totalAttempts = (Integer) resolvedPrereqs.get(RulesExecutionConstants.TOTAL_COURSE_ATTEMPTS_TERM.getName());

        String errorLevel = MAX_REPEATABILITY_SUCCESS;

        if (totalAttempts != null && maxRepeats != null) {
            if (totalAttempts >= maxRepeats) {
                errorLevel = MAX_REPEATABILITY_ERROR;
            } else if (maxRepeats > 1 && totalAttempts >= maxRepeats - 1) {
                errorLevel = MAX_REPEATABILITY_WARNING;
            }
        }

        //Check if the course has a custom repeatability rule on it.

        //Grab prereqs
        RegistrationGroupInfo rg = (RegistrationGroupInfo) resolvedPrereqs.get(RulesExecutionConstants.REGISTRATION_GROUP_TERM.getName());

        //See if the course has any custom rules on it
        List<ReferenceObjectBinding> bindings = getRuleManagementService().findReferenceObjectBindingsByReferenceObject(CourseOfferingServiceConstants.REF_OBJECT_URI_COURSE_OFFERING, rg.getCourseOfferingId());
        for (ReferenceObjectBinding binding : bindings) {
            //Grab agenda definitions of a specific type
            if (KRMS_AGENDA.equals(binding.getKrmsDiscriminatorType())) {
                AgendaDefinition agendaDefinition = getRuleManagementService().getAgenda(binding.getKrmsObjectId());
                //Check that the agenda is of type "kuali.krms.agenda.type.course.creditConstraints"
                if (KRMS_COURSE_CONTEXT.equals(agendaDefinition.getContextId()) && KRMS_REPEATABILITY_TYPE.equals(agendaDefinition.getTypeId())) {
                    //If there is a repeatability rule, exclude this course from repeatability by always returning success
                    errorLevel = MAX_REPEATABILITY_SUCCESS;
                }
            }
        }

        if (!errorLevel.equals(MAX_REPEATABILITY_SUCCESS)) {
            LOGGER.warn("Max Repeatability check for was not successful -- Total Attempts: {}, Max Repeats: {}, Error Level: {}", totalAttempts, maxRepeats, errorLevel);
        }

        DateTime endTime = new DateTime();
        RegEnginePerformanceUtil.putStatistics(RegEnginePerformanceUtil.TERMS, getOutput(), startTime, endTime);

        return errorLevel;
    }

    public RuleManagementService getRuleManagementService() {
        if (ruleManagementService == null) {
            ruleManagementService = GlobalResourceLoader.getService(new QName(KrmsConstants.Namespaces.KRMS_NAMESPACE_2_0, "ruleManagementService"));
        }
        return ruleManagementService;
    }

    public void setRuleManagementService(RuleManagementService ruleManagementService) {
        this.ruleManagementService = ruleManagementService;
    }

}
