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
import org.kuali.student.r2.core.process.dto.CheckInfo;
import org.kuali.student.r2.core.process.dto.InstructionInfo;

/**
 * Proposition that evaluates a milestone start date check
 *
 * @author nwright
 */
public class MilestoneStartDateCheckProposition extends AbstractMilestoneDateCheckProposition {

    public MilestoneStartDateCheckProposition(InstructionInfo instruction, CheckInfo check) {
        super(instruction, check);
    }

    @Override
    protected boolean matches(Date asOfDate, Date startDate, Date endDate) {
        if (asOfDate.before(startDate)) {
            return false;
        }
        return true;
    }
}
