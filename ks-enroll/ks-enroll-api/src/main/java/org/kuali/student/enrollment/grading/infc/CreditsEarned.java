/**
 * Copyright 2010 The Kuali Foundation Licensed under the Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.osedu.org/licenses/ECL-2.0 Unless required by applicable law or agreed to in writing, software distributed
 * under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied. See the License for the specific language governing permissions and limitations under the License.
 */

package org.kuali.student.enrollment.grading.infc;

import org.kuali.student.r2.common.infc.HasAttributesAndMeta;
import org.kuali.student.r2.common.infc.HasId;

/**
 * Information about an assigned grade in a roster entry
 * 
 * @author Kuali Student Team (Kamal)
 */

public interface CreditsEarned extends HasId, HasAttributesAndMeta {

    /**
     * Credit group to which the credit belongs
     * 
     * @name Credit Group Id
     * @impl maps to LearningResultRecord.resultValueGroupId in LRR
     */
    public String getCreditGroupId();

    /**
     * Credit Earned
     * 
     * @name Credit
     * @impl maps to ResultValue.value in LRC where resultValue.Id = LearningResultRecord.resultValueId in LRR. The
     *       resultSourceId in the LRR should be sourceId for source used for credit calculation. In case the credits are
     *       manually entered, the source should be of manually set credits 
     */
    public String getCredit();

}
