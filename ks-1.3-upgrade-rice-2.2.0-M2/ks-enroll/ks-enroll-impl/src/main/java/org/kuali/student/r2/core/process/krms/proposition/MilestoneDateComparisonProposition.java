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

package org.kuali.student.r2.core.process.krms.proposition;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.kuali.rice.krms.api.engine.ExecutionEnvironment;
import org.kuali.rice.krms.api.engine.ResultEvent;
import org.kuali.rice.krms.api.engine.Term;
import org.kuali.rice.krms.framework.engine.PropositionResult;
import org.kuali.rice.krms.framework.engine.result.BasicResult;
import org.kuali.student.common.util.krms.RulesExecutionConstants;
import org.kuali.student.common.util.krms.proposition.AbstractLeafProposition;
import org.kuali.student.r2.core.atp.dto.MilestoneInfo;
import org.kuali.student.r2.core.exemption.infc.DateOverride;

/**
 * This proposition evaluates a check of a particular date against the dates of a given Milestone.
 * Caveat: the comparisons are all exclusive by default.  If the inclusive field is set to true, then an inclusive comparison is performed
 *
 * @author alubbers
 */
public class MilestoneDateComparisonProposition extends AbstractLeafProposition implements ExemptionAwareProposition {

    private boolean exemptionUsed = false;
    private final Term milestonesTerm;

    public enum DateComparisonType {
        BETWEEN, BEFORE, AFTER
    }

    private final Term comparisonDateTerm;

    private final DateOverride dateOverride;

    private final DateComparisonType comparisonType;

    private final String milestoneType;

    private final String atpKey;

    private final Boolean inclusive;

    public MilestoneDateComparisonProposition(String comparisonDateTermKey, DateComparisonType comparisonType, String milestoneType, String atpKey, Boolean inclusive, DateOverride dateOverrideInfo) {
        this.comparisonDateTerm = new Term(comparisonDateTermKey);
        this.comparisonType = comparisonType;
        this.milestoneType = milestoneType;
        this.inclusive = inclusive;
        this.dateOverride = dateOverrideInfo;
        this.atpKey = atpKey;

        Map<String, String> termParametersMap = new HashMap<String, String>(2);
        termParametersMap.put(RulesExecutionConstants.MILESTONE_TYPE_TERM_PROPERTY, milestoneType);
        termParametersMap.put(RulesExecutionConstants.MILESTONE_ATP_KEY_TERM_PROPERTY, atpKey);
        milestonesTerm = new Term(RulesExecutionConstants.MILESTONES_BY_TYPE_TERM_NAME, termParametersMap);
    }

    public Term getComparisonDateTerm() {
        return comparisonDateTerm;
    }

    public DateComparisonType getComparisonType() {
        return comparisonType;
    }

    public String getMilestoneType() {
        return milestoneType;
    }

    public Boolean getInclusive() {
        return inclusive;
    }

    public DateOverride getDateOverride() {
        return dateOverride;
    }

    @Override
    public PropositionResult evaluate(ExecutionEnvironment environment) {

        // resolve the list of Milestones from the given Milestone type key
        List<MilestoneInfo> milestones = environment.resolveTerm(milestonesTerm, this);

        Date comparisonDate = environment.resolveTerm(comparisonDateTerm, this);

        boolean dateCompareResult = true;
        for(MilestoneInfo milestone : milestones) {
            dateCompareResult = compareDateToMilestone(comparisonDate, milestone.getStartDate(), milestone.getEndDate());

            if(dateCompareResult) {
                break;
            }
        }

        if(dateOverride != null) {
           if(dateCompareResult) {
               exemptionUsed = false;
           }
           else {

               // set the proposition result equal to the comparison of the date and the dates from the override
               dateCompareResult = compareDateToMilestone(comparisonDate, dateOverride.getEffectiveEndDate(), dateOverride.getEffectiveEndDate());

               // it should be up to the caller what happens when the comparison fails even when the exemption information was checked
               // so we set the exemptionUsed to true no matter what the actual comparison result was
               exemptionUsed = true;
           }
        }

        PropositionResult result = new PropositionResult(dateCompareResult);

        environment.getEngineResults().addResult(new BasicResult(ResultEvent.PROPOSITION_EVALUATED, this, environment, result.getResult()));

        return result;
    }

    private boolean compareDateToMilestone(Date comparisonDate, Date startDate, Date endDate) {
        boolean dateCompareResult;
        switch (comparisonType) {
            case BEFORE: {
                dateCompareResult = comparisonDate.before(endDate);
                if(inclusive && !dateCompareResult) {
                    dateCompareResult = comparisonDate.equals(endDate);
                }
                break;
            }
            case AFTER: {
                dateCompareResult = comparisonDate.after(startDate);
                if(inclusive && !dateCompareResult) {
                    dateCompareResult = comparisonDate.equals(startDate);
                }
                break;
            }
            case BETWEEN: {
                dateCompareResult = comparisonDate.after(startDate) && comparisonDate.before(endDate);
                if(inclusive && !dateCompareResult) {
                    dateCompareResult = comparisonDate.equals(startDate) || comparisonDate.equals(endDate);
                }
                break;
            }
            default: {
                throw new RuntimeException("Invalid comparisonType when evaluating a MilestoneDateComparisonProposition: " + comparisonType);
            }
        }
        return dateCompareResult;
    }

    public boolean isExemptionUsed() {
        return exemptionUsed;
    }
}
