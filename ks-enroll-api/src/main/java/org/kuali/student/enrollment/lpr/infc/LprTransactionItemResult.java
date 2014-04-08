/*
 * Copyright 2011 The Kuali Foundation 
 *
 * Licensed under the Educational Community License, Version 2.0 (the
 * "License"); you may not use this file except in compliance with the
 * License. You may obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package org.kuali.student.enrollment.lpr.infc;

import org.kuali.student.r2.common.dto.ValidationResultInfo;

import java.util.List;

public interface LprTransactionItemResult {

    /**
     * The resulting LPR for this LPR transaction item if its
     * successful.
     *
     * Returns null if unsuccessful, a valid LPR Id in case of
     * success.
     *
     * @return The resulting LPR Id stored in the object
     */
    public String getResultingLprId();

    /**
     * Validation messages, errors and messages once submitted
     * 
     * @name Validation Results
     */
    public List<ValidationResultInfo> getValidationResults();
}

