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

package org.kuali.student.r2.lum.statement.config.context;

import java.util.Map;

import org.kuali.student.r2.lum.statement.config.context.util.NLHelper;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r1.core.statement.dto.ReqComponentInfo;
import org.kuali.student.r1.core.statement.naturallanguage.AbstractContext;
import org.kuali.student.r1.lum.statement.typekey.ReqComponentFieldTypes;
import org.kuali.student.r2.common.dto.ContextInfo;

public class BasicContextImpl extends AbstractContext<ReqComponentInfo> {
	/**
	 * <code>NLHelper</code> token (key) references a static natural language
	 * helper class for use in templates.
	 * e.g. '$NLHelper.getProperGrammer($intValue, "course", "courses")'
	 */
	public final static String NL_HELPER_TOKEN = "NLHelper";
	/**
	 * Relational operator token.
	 */
	public final static String OPERATOR_TOKEN = "relationalOperator";
	/**
	 * An integer value token.
	 */
	public final static String INTEGER_VALUE_TOKEN = "intValue";

	/**
	 * Constructor.
	 */
	public BasicContextImpl() {
	}
	
    /**
     * Creates the context map (template data) for the requirement component.
     * Also, adds the field token map to the context map.
     *
     *
     *
     * @param reqComponent Requirement component
     * @param contextInfo
     * @throws OperationFailedException Creating context map fails
     */
    public Map<String, Object> createContextMap(ReqComponentInfo reqComponent, ContextInfo contextInfo) throws OperationFailedException {
    	String value = getReqComponentFieldValue(reqComponent, ReqComponentFieldTypes.INTEGER_VALUE1_KEY.getId());
    	String operator = getReqComponentFieldValue(reqComponent, ReqComponentFieldTypes.OPERATOR_KEY.getId());
    	
    	Map<String, Object> contextMap = super.createContextMap(reqComponent,contextInfo);
        contextMap.put(NL_HELPER_TOKEN, NLHelper.class);
		if(operator != null) {
	        contextMap.put(OPERATOR_TOKEN, operator);
		}
		if(value != null) {
			contextMap.put(INTEGER_VALUE_TOKEN, Integer.valueOf(value));
		}

        return contextMap;
    }
}
