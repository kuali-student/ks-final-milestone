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

package org.kuali.student.r2.core.krms.naturallanguage;

import org.kuali.rice.krms.api.repository.term.TermDefinitionContract;
import org.kuali.rice.krms.api.repository.term.TermParameterDefinitionContract;
import org.kuali.student.r2.core.krms.naturallanguage.Context;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.OperationFailedException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This is an abstract class for creating a map (containing token/data) used
 * in template translations.
 * 
 * @param <T> Type of context to create
 */
public abstract class AbstractContext<T> implements Context<T> {
	/**
	 * <p>These common shared tokens are needed since velocity doesn't 
	 * allow periods in tokens.</p>
	 * <p>E.g. kuali.reqComponent.field.type.totalCredits must either be convert to 
	 * totalCredits or reqCompFieldType_totalCredits so a template would look 
	 * like:</p>
	 * <p>'Student must take $totalCredits of MATH 100'</p>
	 * or
	 * <p>'Student must take $reqCompFieldType_totalCredits of MATH 100'</p>
	 */
	protected final static String FIELDS_TOKEN = "fields";

	/**
     * Gets term parameters values as a map.
     * 
     * @param term
     * @return Map of requirement component fields
     */
	protected Map<String, String> getTermParameterMap(TermDefinitionContract term) throws OperationFailedException {
		List<? extends TermParameterDefinitionContract> parameters = term.getParameters();
        Map<String, String> map = new HashMap<String, String>();
        for (TermParameterDefinitionContract parameter : parameters) {
            String name = parameter.getName();
            String value = parameter.getValue();
            map.put(name, value);
        }
        return map;
    }

    /**
     * Gets the value of the ReqCompFieldInfo key.
     *
     * @param term
     * @param key <code>ReqCompFieldInfo</code> key
     * @return Value of <code>ReqCompFieldInfo</code>
     */
	protected String getTermParameterValue(TermDefinitionContract term, String key) throws OperationFailedException {
        return getTermParameterMap(term).get(key);
    }

    /**
     * Creates the context map (template data) for the requirement component.
     * Also, adds the field token map to the context map.
     *
     * @param term Requirement component
     * @param contextInfo
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException If CLU, CluSet or relation does not exist
     */
    public Map<String, Object> createContextMap(TermDefinitionContract term, ContextInfo contextInfo) throws OperationFailedException {
        Map<String, Object> contextMap = new HashMap<String, Object>();
        contextMap.put(FIELDS_TOKEN, getTermParameterMap(term));
        return contextMap;
    }
}
