/**
 * Copyright 2011 The Kuali Foundation Licensed under the Educational Community License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package org.kuali.student.enrollment.krms.proposition;

import org.kuali.rice.krms.api.engine.ExecutionEnvironment;
import org.kuali.rice.krms.api.engine.ResultEvent;
import org.kuali.rice.krms.framework.engine.PropositionResult;
import org.kuali.rice.krms.framework.engine.Rule;
import org.kuali.rice.krms.framework.engine.result.BasicResult;
import org.kuali.student.common.util.krms.RulesExecutionConstants;
import org.kuali.student.common.util.krms.proposition.AbstractLeafProposition;
import org.kuali.student.core.process.evaluator.AbstractCheckProposition;
import org.kuali.student.core.process.evaluator.KRMSEvaluator;
import org.kuali.student.enrollment.class2.courseoffering.krms.service.RequisitesService;
import org.kuali.student.enrollment.courseoffering.infc.RegistrationGroup;
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;
import org.kuali.student.enrollment.courseregistration.dto.RegistrationRequestInfo;
import org.kuali.student.enrollment.courseregistration.dto.RegistrationRequestItemInfo;
import org.kuali.student.enrollment.registration.client.service.impl.util.RegistrationValidationResultsUtil;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.infc.ValidationResult;
import org.kuali.student.r2.common.util.constants.LprServiceConstants;
import org.kuali.student.r2.core.process.dto.CheckInfo;
import org.kuali.student.r2.core.process.dto.InstructionInfo;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * This proposition evaluates all the instructions associated with a process
 *
 * @author nwright
 */
public class RequisitesProposition extends AbstractLeafProposition {

    private String agendaType;
    private String ruleType;

    public RequisitesProposition() {
    }

    public RequisitesProposition(String agendaType, String ruleType) {
        this.agendaType = agendaType;
        this.ruleType = ruleType;
    }

    @Override
    public PropositionResult evaluate(ExecutionEnvironment environment) {
        ContextInfo contextInfo = environment.resolveTerm(RulesExecutionConstants.CONTEXT_INFO_TERM, this);
        RegistrationRequestItemInfo requestItem = environment.resolveTerm(RulesExecutionConstants.REGISTRATION_REQUEST_ITEM_TERM, this);

        // get all the needed services from the execution context
        // we do this so we can locally cache some services and the cache lives just for the length of krms execution
        CourseOfferingService courseOfferingService = environment.resolveTerm(RulesExecutionConstants.COURSE_OFFERING_SERVICE_TERM, this);
        try {
            RegistrationGroup regGroup = courseOfferingService.getRegistrationGroup(requestItem.getRegistrationGroupId(), contextInfo);

            if (!executeRuleForRegGroupAndType(environment, regGroup)) {
                ValidationResultInfo vr = new ValidationResultInfo();
                vr.setLevel(ValidationResult.ErrorLevel.ERROR);
                vr.setElement("registrationRequestItems['" + requestItem.getId() + "']");
                vr.setMessage(RegistrationValidationResultsUtil.marshallSimpleMessage(LprServiceConstants.LPRTRANS_ITEM_FAILED_ANTI_REQUISITES_MESSAGE_KEY));
                return KRMSEvaluator.buildPropositionResult(environment, vr, false, this);
            }

        } catch (Exception ex) {
            return KRMSEvaluator.constructExceptionPropositionResult(environment, ex, this);
        }

        return KRMSEvaluator.buildPropositionResult(environment, true, this);
    }

    private boolean executeRuleForRegGroupAndType(ExecutionEnvironment environment, RegistrationGroup regGroup) {

        RequisitesService requisitesService = environment.resolveTerm(RulesExecutionConstants.REQUISITES_SERVICE_TERM, this);
        boolean isCoRuleEvaluated = false;
        for (String activityOfferingId : regGroup.getActivityOfferingIds()) {
            // Get the rule for the ao.
            Rule rule = requisitesService.getRuleForActivityOfferingIdAndType(activityOfferingId, agendaType, ruleType);

            if ((rule == null) && (!isCoRuleEvaluated)) {
                rule = requisitesService.getRuleForCourseOfferingIdAndType(regGroup.getCourseOfferingId(), agendaType, ruleType);
            }

            if ((rule != null) && (!rule.evaluate(environment))) {
                return false;
            }

        }

        return true;

    }

    public String getAgendaType() {
        return agendaType;
    }

    public void setAgendaType(String agendaType) {
        this.agendaType = agendaType;
    }

    public String getRuleType() {
        return ruleType;
    }

    public void setRuleType(String ruleType) {
        this.ruleType = ruleType;
    }

}
