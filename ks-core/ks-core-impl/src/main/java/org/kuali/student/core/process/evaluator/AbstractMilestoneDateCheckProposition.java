/**
 * Copyright 2011 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package org.kuali.student.core.process.evaluator;

import java.util.Date;
import org.kuali.rice.krms.api.engine.ExecutionEnvironment;
import org.kuali.rice.krms.api.engine.ResultEvent;
import org.kuali.rice.krms.framework.engine.PropositionResult;
import org.kuali.rice.krms.framework.engine.result.BasicResult;
import org.kuali.student.common.util.krms.RulesExecutionConstants;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.infc.ValidationResult;
import org.kuali.student.r2.core.atp.dto.MilestoneInfo;
import org.kuali.student.r2.core.atp.service.AtpService;
import org.kuali.student.r2.core.constants.ExemptionServiceConstants;
import org.kuali.student.r2.core.exemption.dto.DateOverrideInfo;
import org.kuali.student.r2.core.exemption.dto.ExemptionInfo;
import org.kuali.student.r2.core.exemption.service.ExemptionService;
import org.kuali.student.r2.core.process.dto.CheckInfo;
import org.kuali.student.r2.core.process.dto.InstructionInfo;

/**
 * This class represents a proposition to evaluate if a student has a Hold
 *
 * @author nwright
 */
public abstract class AbstractMilestoneDateCheckProposition extends AbstractCheckProposition {

    public AbstractMilestoneDateCheckProposition(InstructionInfo instruction, CheckInfo check) {
        super(instruction, check);
    }


    @Override
    public PropositionResult evaluate(ExecutionEnvironment environment) {
        ContextInfo contextInfo = environment.resolveTerm(RulesExecutionConstants.CONTEXT_INFO_TERM, this);
        String atpId = environment.resolveTerm(RulesExecutionConstants.ATP_ID_TERM, this);
        Boolean recordSuccesses =  environment.resolveTerm(RulesExecutionConstants.RECORD_INSTRUCTION_SUCCESSES_TERM, this);
        AtpService atpService = environment.resolveTerm(RulesExecutionConstants.ATP_SERVICE_TERM, this);
        Date asOfDate = environment.resolveTerm(RulesExecutionConstants.AS_OF_DATE_TERM, this);

        MilestoneInfo milestone;
        try {
            milestone = this.getMilestone(atpService, atpId, contextInfo);
        } catch (Exception ex) {
            return KRMSEvaluator.constructExceptionPropositionResult(environment, ex, this);
        }

        if (matches(asOfDate, milestone.getStartDate(), milestone.getEndDate())) {
            return this.recordSuccessResult(environment);
        }
        // check if student has an override supplying a new start date 
        // Note: I wonder if the business wants to be able to override a start date setting it later instead of earlier?
        // in other words the exemption actually makes it harder/more restrictive for the student
        // if so we need to do this before we do the ABOVE check
        DateOverrideInfo dateOverride;
        try {
            dateOverride = this.checkIfHasExeptionOverridingDate(environment, asOfDate, milestone, contextInfo);
        } catch (Exception ex) {
            return KRMSEvaluator.constructExceptionPropositionResult(environment, ex, this);
        }
        if (dateOverride != null) {
            if (matches(asOfDate, dateOverride.getEffectiveEndDate(), dateOverride.getEffectiveEndDate())) {
                if (recordSuccesses) {
                    ValidationResultInfo vr = new ValidationResultInfo();
                    vr.setElement(instruction.getId());
                    vr.setLevel(ValidationResult.ErrorLevel.OK);
                    vr.setMessage("Initially failed milestone date check but passed because an exemption overrode the dates");
                    Map<String, Object> executionDetails = new LinkedHashMap<String, Object>();
                    executionDetails.put(RulesExecutionConstants.PROCESS_EVALUATION_RESULTS, vr);
                    PropositionResult result = new PropositionResult(true, executionDetails);
                    BasicResult br = new BasicResult(executionDetails, ResultEvent.PROPOSITION_EVALUATED, this, environment,
                            result.getResult());
                    environment.getEngineResults().addResult(br);
                    return result;
                }
                PropositionResult result = new PropositionResult(true);
                BasicResult br = new BasicResult(ResultEvent.PROPOSITION_EVALUATED, this, environment, result.getResult());
                environment.getEngineResults().addResult(br);
                return result;
            }
        }
        return this.recordFailureResultOrExemption(environment);
    }

    protected abstract boolean matches(Date asOfDate, Date startDate, Date endDate);

    private MilestoneInfo getMilestone(AtpService atpService, String atpId, ContextInfo contextInfo) throws Exception {
        MilestoneInfo milestone;
        List<MilestoneInfo> milestones;
        milestones = atpService.getMilestonesByTypeForAtp(atpId, check.getMilestoneTypeKey(), contextInfo);
        if (milestones.isEmpty()) {
            throw new DoesNotExistException(atpId + " has no milestones for type " + check.getMilestoneTypeKey());
        }
        if (milestones.size() > 1) {
            throw new OperationFailedException(
                    atpId + " has more than one, " + milestones.size() + ", milestones for type "
                    + check.getMilestoneTypeKey());
        }
        milestone = milestones.get(0);
        return milestone;
    }

    private DateOverrideInfo checkIfHasExeptionOverridingDate(ExecutionEnvironment environment,
            Date asOfDate,
            MilestoneInfo milestone,
            ContextInfo contextInfo)
            throws Exception {

        ExemptionService exemptionService = environment.resolveTerm(RulesExecutionConstants.EXEMPTION_SERVICE_TERM, this);
        String personId = environment.resolveTerm(RulesExecutionConstants.PERSON_ID_TERM, this);
        List<ExemptionInfo> exemptions;
        exemptions = exemptionService.getActiveExemptionsByTypeProcessAndCheckForPerson(
                ExemptionServiceConstants.MILESTONE_DATE_EXEMPTION_TYPE_KEY,
                instruction.getProcessKey(),
                check.getId(),
                personId,
                asOfDate,
                contextInfo);
        if (exemptions.isEmpty()) {
            return null;
        }
        DateOverrideInfo minMax = new DateOverrideInfo();
        boolean overrode = false;
        for (ExemptionInfo exemption : exemptions) {
            DateOverrideInfo override = exemption.getDateOverride();
            if (override == null) {
                continue;
            }
            if (!milestone.getTypeKey().equals(override.getMilestoneId())) {
                continue;
            }

            if (override.getEffectiveStartDate() != null) {
                if (minMax.getEffectiveStartDate() == null) {
                    minMax.setEffectiveStartDate(override.getEffectiveStartDate());
                    overrode = true;
                    continue;
                }
                if (minMax.getEffectiveStartDate().after(override.getEffectiveStartDate())) {
                    minMax.setEffectiveStartDate(override.getEffectiveStartDate());
                    overrode = true;
                    continue;
                }
            }
            if (override.getEffectiveEndDate() != null) {
                if (minMax.getEffectiveEndDate() == null) {
                    minMax.setEffectiveEndDate(override.getEffectiveEndDate());
                    overrode = true;
                    continue;
                }
                if (minMax.getEffectiveEndDate().before(override.getEffectiveEndDate())) {
                    minMax.setEffectiveEndDate(override.getEffectiveEndDate());
                    overrode = true;
                    continue;
                }
            }
        }
        if (!overrode) {
            return null;
        }
        if (minMax.getEffectiveStartDate() == null) {
            minMax.setEffectiveStartDate(milestone.getStartDate());
        }
        if (minMax.getEffectiveEndDate() == null) {
            minMax.setEffectiveEndDate(milestone.getEndDate());
        }
        return minMax;
    }
}
