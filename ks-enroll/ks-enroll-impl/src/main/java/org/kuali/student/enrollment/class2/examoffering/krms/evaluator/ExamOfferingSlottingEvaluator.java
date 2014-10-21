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

package org.kuali.student.enrollment.class2.examoffering.krms.evaluator;

import org.kuali.student.enrollment.class2.examoffering.service.facade.ExamOfferingResult;
import org.kuali.student.enrollment.courseoffering.infc.ActivityOffering;
import org.kuali.student.enrollment.courseoffering.infc.CourseOffering;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.OperationFailedException;

import java.util.List;

/**
 * This is an interface for the execution of the final exam matrix in order to create the requested delivery
 * logistic.
 *
 * @author Kuali Student Team
 */
public interface ExamOfferingSlottingEvaluator {

    public static final String USE_AO_LOCATION_OPTION_KEY = "kuali.option.key.eo.slotting.use.ao.location";

    /**
     * This method will evaluate the appropriate final exam matrix and create the requested delivery logistic.
     *
     * @param activityOffering
     * @param examOfferingId
     * @param termType
     * @param optionKeys
     * @param context
     * @throws OperationFailedException
     */
    public ExamOfferingResult executeRuleForAOSlotting(ActivityOffering activityOffering, String examOfferingId, String termType,
                                         List<String> optionKeys, ContextInfo context) throws OperationFailedException;

    /**
     * This method will evaluate the appropriate final exam matrix and create the requested delivery logistic.
     *
     * @param courseOffering
     * @param examOfferingId
     * @param termType
     * @param optionKeys
     * @param context
     * @throws OperationFailedException
     */
    public ExamOfferingResult executeRuleForCOSlotting(CourseOffering courseOffering,String examOfferingId, String termType,
                                         List<String> optionKeys, ContextInfo context) throws OperationFailedException;
}
