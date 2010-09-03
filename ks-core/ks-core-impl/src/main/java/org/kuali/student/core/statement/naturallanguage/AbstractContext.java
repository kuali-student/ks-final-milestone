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

package org.kuali.student.core.statement.naturallanguage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.kuali.student.core.exceptions.DoesNotExistException;
import org.kuali.student.core.exceptions.OperationFailedException;
import org.kuali.student.core.statement.entity.ReqComponent;
import org.kuali.student.core.statement.entity.ReqComponentField;

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
     * Gets requirement component fields as a map.
     * 
     * @param reqComponent Requirement component
     * @return Map of requirement component fields
     */
	protected Map<String, String> getReqComponentFieldMap(ReqComponent reqComponent) {
        List<ReqComponentField> fields = reqComponent.getReqComponentFields();
        Map<String, String> map = new HashMap<String, String>();
        for (ReqComponentField field : fields) {
            String type = field.getType();
            String value = field.getValue();
            map.put(type, value);
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
	protected String getReqComponentFieldValue(ReqComponent reqComponent, String key) {
        return getReqComponentFieldMap(reqComponent).get(key);
    }

    /**
     * Creates the context map (template data) for the requirement component.
     * Also, adds the field token map to the context map.
     *
     * @param reqComponent Requirement component
     * @throws DoesNotExistException If CLU, CluSet or relation does not exist
     */
    public Map<String, Object> createContextMap(ReqComponent reqComponent) throws OperationFailedException {
        Map<String, Object> contextMap = new HashMap<String, Object>();
        contextMap.put(FIELDS_TOKEN, getReqComponentFieldMap(reqComponent));
        return contextMap;
    }
}
