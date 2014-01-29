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

import org.kuali.rice.core.api.util.type.KualiDecimal;
import org.kuali.student.r2.core.constants.ExemptionServiceConstants;
import org.kuali.student.r2.core.process.dto.CheckInfo;
import org.kuali.student.r2.core.process.dto.InstructionInfo;

/**
 * Proposition that evaluates a milestone deadline (end date) check
 *
 * @author nwright
 */
public class MinValueCheckProposition extends AbstractValueCheckProposition {

    public MinValueCheckProposition(InstructionInfo instruction, CheckInfo check) {
        super(instruction, check);
    }

    @Override
    protected String getExemptionTypeToLookFor() {
        return ExemptionServiceConstants.CHECK_VALUE_MIN_EXEMPTION_TYPE_KEY;
    }

    @Override
    protected boolean matches(KualiDecimal leftComparisonValue, KualiDecimal rightComparisonValue) {
        if (leftComparisonValue.isLessThan(rightComparisonValue)) {
            return false;
        }
        return true;
    }
}
