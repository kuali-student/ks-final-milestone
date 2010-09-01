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

package org.kuali.student.lum.statement.config.context;

import java.util.Map;

import org.kuali.student.core.statement.entity.ReqComponent;
import org.kuali.student.core.statement.entity.ReqComponentField;
import org.kuali.student.core.statement.naturallanguage.AbstractContext;
import org.kuali.student.core.exceptions.OperationFailedException;
import org.kuali.student.lum.statement.config.context.util.NLHelper;
import org.kuali.student.lum.statement.typekey.ReqComponentFieldTypes;

public class BasicContextImpl extends AbstractContext<ReqComponent> {
	/**
	 * <code>NLHelper</code> token (key) references a static natural language
	 * helper class for use in templates.
	 * e.g. '$NLHelper.getProperGrammer($value, "course", "courses")'
	 */
	private final static String NL_HELPER_TOKEN = "NLHelper";
	/**
	 * Value token.
	 */
	private final static String VALUE_TOKEN = "value";
	/**
	 * Value data type token.
	 */
	private final static String VALUE_DATA_TYPE_TOKEN = "valueDataType";
	/**
	 * Relational operator token.
	 */
	private final static String OPERATOR_TOKEN = "relationalOperator";

	/**
	 * Constructor.
	 */
	public BasicContextImpl() {
	}
	
	private void addValueToken(Map<String, Object> contextMap, String valueDataType, String value) {
		if(ReqComponentFieldTypes.ValueDataType.STRING.toString().equalsIgnoreCase(valueDataType)) {
			contextMap.put(VALUE_TOKEN, value);
		} else if(ReqComponentFieldTypes.ValueDataType.INTEGER.toString().equalsIgnoreCase(valueDataType)) {
			contextMap.put(VALUE_TOKEN, Integer.valueOf(value));
		} else if(ReqComponentFieldTypes.ValueDataType.DOUBLE.toString().equalsIgnoreCase(valueDataType)) {
			contextMap.put(VALUE_TOKEN, Double.valueOf(value));
		} else {
			contextMap.put(VALUE_TOKEN, value);
		}
	}

    /**
     * Creates the context map (template data) for the requirement component.
     * Also, adds the field token map to the context map.
     *
     * @param reqComponent Requirement component
     * @throws OperationFailedException Creating context map fails
     */
    public Map<String, Object> createContextMap(ReqComponent reqComponent) throws OperationFailedException {
    	String value = getReqComponentFieldValue(reqComponent, ReqComponentFieldTypes.VALUE_KEY.getId());
    	String valueDataType = getReqComponentFieldValue(reqComponent, ReqComponentFieldTypes.VALUE_DATA_TYPE_KEY.getId());
    	String operator = getReqComponentFieldValue(reqComponent, ReqComponentFieldTypes.OPERATOR_KEY.getId());
    	
    	Map<String, Object> contextMap = super.createContextMap(reqComponent);
        contextMap.put(NL_HELPER_TOKEN, NLHelper.class);
        contextMap.put(VALUE_DATA_TYPE_TOKEN, valueDataType);
        addValueToken(contextMap, valueDataType, value);
        contextMap.put(OPERATOR_TOKEN, operator);

        return contextMap;
    }
}
