/*
 * Copyright 2009 The Kuali Foundation Licensed under the
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
package org.kuali.student.brms.statement.naturallanguage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.kuali.student.brms.statement.entity.ReqComponent;
import org.kuali.student.brms.statement.entity.ReqComponentField;

public abstract class AbstractContext<T> implements Context<T> {
	/**
	 * <p>These common shared tokens are needed since velocity doesn't 
	 * allow periods in tokens.</p>
	 * <p>E.g. reqCompFieldType.totalCredits must either be convert to 
	 * totalCredits or reqCompFieldType_totalCredits so a template would look 
	 * like:</p>
	 * <p>'Student must take $totalCredits of MATH 100'</p>
	 * or
	 * <p>'Student must take $reqCompFieldType_totalCredits of MATH 100'</p>
	 */
	protected final static String EXPECTED_VALUE_TOKEN = "expectedValue";
	protected final static String FIELDS_TOKEN = "fields";
	protected final static String OPERATOR_TOKEN = "relationalOperator";

    /**
     * Gets requirement component fields as a map.
     * 
     * @param reqComponent Requirement component
     * @return Map of requirement component fields
     */
    public Map<String, String> getReqCompField(ReqComponent reqComponent) {
        List<ReqComponentField> fields = reqComponent.getReqComponentFields();
        Map<String, String> map = new HashMap<String, String>();
        for (ReqComponentField field : fields) {
            String key = field.getKey();
            //String key = field.getId();
            String value = field.getValue();
            map.put(key, value);
        }
        return map;
    }

    /**
     * Gets the value of the ReqCompFieldInfo key. 
     * See {@link ReqCompFieldInfo#getKey()} 
     * 
     * @param reqComponent Requirement component
     * @param key <code>ReqCompFieldInfo</code> key
     * @return Value of <code>ReqCompFieldInfo</code>
     */
    public String getReqCompFieldValue(ReqComponent reqComponent, String key) {
        return getReqCompField(reqComponent).get(key);
    }

    /**
     * Creates the data context map (template data) for a specific context.
     * 
     * @param context Context to create the map from
     * @throws DoesNotExistException If CLU, CluSet or relation does not exist
     */
	//public abstract Map<String, Object> createContextMap(T context) throws OperationFailedException;
}
