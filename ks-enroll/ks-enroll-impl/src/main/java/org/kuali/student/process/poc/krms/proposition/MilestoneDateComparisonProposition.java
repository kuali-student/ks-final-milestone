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

package org.kuali.student.process.poc.krms.proposition;

import org.kuali.rice.krms.api.engine.ExecutionEnvironment;
import org.kuali.rice.krms.api.engine.ResultEvent;
import org.kuali.rice.krms.api.engine.Term;
import org.kuali.rice.krms.framework.engine.PropositionResult;
import org.kuali.rice.krms.framework.engine.result.BasicResult;
import org.kuali.student.common.util.krms.RulesExecutionConstants;
import org.kuali.student.common.util.krms.proposition.AbstractLeafProposition;
import org.kuali.student.r2.core.atp.dto.MilestoneInfo;

import java.util.Collections;
import java.util.Date;

/**
 * This proposition evaluates a check of a particular date against the dates of a given Milestone.
 * Caveat: the comparisons are all exclusive by default.  If the inclusive field is set to true, then an inclusive comparison is performed
 *
 * @author alubbers
 */
public class MilestoneDateComparisonProposition extends AbstractLeafProposition {

    private final Term comparisonDateTerm;

    public enum DateComparisonType {
        BETWEEN, BEFORE, AFTER
    }

    private final DateComparisonType comparisonType;

    private final String milestoneId;

    private final Boolean inclusive;

    public MilestoneDateComparisonProposition(String comparisonDateTermKey, DateComparisonType comparisonType, String milestoneId) {
        this.comparisonDateTerm = new Term(comparisonDateTermKey);
        this.comparisonType = comparisonType;
        this.milestoneId = milestoneId;
        this.inclusive = false;
    }

    public MilestoneDateComparisonProposition(String comparisonDateTermKey, DateComparisonType comparisonType, String milestoneId, Boolean inclusive) {
        this.comparisonDateTerm = new Term(comparisonDateTermKey);
        this.comparisonType = comparisonType;
        this.milestoneId = milestoneId;
        this.inclusive = inclusive;
    }

    public Term getComparisonDateTerm() {
        return comparisonDateTerm;
    }

    public DateComparisonType getComparisonType() {
        return comparisonType;
    }

    public String getMilestoneId() {
        return milestoneId;
    }

    public Boolean getInclusive() {
        return inclusive;
    }

    @Override
    public PropositionResult evaluate(ExecutionEnvironment environment) {

        Term milestoneTerm = new Term(RulesExecutionConstants.MILESTONE_TERM_NAME, Collections.singletonMap(RulesExecutionConstants.MILESTONE_ID_TERM_PROPERTY_NAME, milestoneId));

        MilestoneInfo milestone = environment.resolveTerm(milestoneTerm, this);

        Date comparisonDate = environment.resolveTerm(comparisonDateTerm, this);

        boolean evaluation = false;
        switch (comparisonType) {
            case BEFORE: {
                evaluation = comparisonDate.before(milestone.getStartDate());
                if(inclusive && !evaluation) {
                    evaluation = comparisonDate.equals(milestone.getStartDate());
                }
                break;
            }
            case AFTER: {
                evaluation = comparisonDate.after(milestone.getEndDate());
                if(inclusive && !evaluation) {
                    evaluation = comparisonDate.equals(milestone.getEndDate());
                }
                break;
            }
            case BETWEEN: {
                evaluation = comparisonDate.after(milestone.getStartDate()) && comparisonDate.before(milestone.getEndDate());
                if(inclusive && !evaluation) {
                    evaluation = comparisonDate.equals(milestone.getStartDate()) || comparisonDate.equals(milestone.getEndDate());
                }
                break;
            }
            default: {
                throw new RuntimeException("Invalid comparisonType when evaluating a MilestoneDateComparisonProposition: " + comparisonType);
            }
        }

        PropositionResult result = new PropositionResult(evaluation);

        environment.getEngineResults().addResult(new BasicResult(ResultEvent.PropositionEvaluated, this, environment, result.getResult()));

        return result;
    }
}
