/**
 * Copyright 2010 The Kuali Foundation Licensed under the
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

package org.kuali.student.r2.core.krms.config.context.lu;

import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.core.krms.naturallanguage.TermParameterTypes;

import java.util.HashMap;
import java.util.Map;

/**
 * This class creates the template context for grade condition type.
 */
public class CreditContextImpl extends AbstractLuContext {
	/** Total credits template token */ 
	private final static String TOTAL_CREDITS_TOKEN = "totalCredits";

    /**
     * Creates the context map (template data) for the requirement component.
     *
     * @param parameters
     * @param contextInfo
     * @throws OperationFailedException
     */
    @Override
    public Map<String, Object> createContextMap(Map<String, Object> parameters) {
        Map<String, Object> contextMap = new HashMap<String, Object>();
        contextMap.put(TOTAL_CREDITS_TOKEN, parameters.get(TermParameterTypes.TOTAL_CREDIT_KEY.getId()));
        return contextMap;
    }
}
