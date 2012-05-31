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

package org.kuali.student.r1.core.statement.naturallanguage;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r1.core.statement.dto.ReqCompFieldInfo;
import org.kuali.student.r1.core.statement.dto.ReqCompFieldTypeInfo;
import org.kuali.student.r1.core.statement.dto.ReqComponentInfo;
import org.kuali.student.r1.core.statement.dto.ReqComponentTypeInfo;
import org.kuali.student.r1.core.statement.service.StatementService;
import org.kuali.student.r2.common.dto.ContextInfo;

/**
 * This is an abstract class for creating a map (containing token/data) used
 * in template translations.
 * 
 * @param <T> Type of context to create
 */
@Deprecated
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

	/*private StatementService statementService;

	public void setStatementService(StatementService statementService) {
		this.statementService = statementService;
	}

	private void validateReqComponentFields(ReqComponentInfo reqComponent) throws OperationFailedException {
		try {
			ReqComponentTypeInfo reqComponentType = statementService.getReqComponentType(reqComponent.getType());
			Set<String> set = new HashSet<String>();
			for(ReqCompFieldTypeInfo fieldType : reqComponentType.getReqCompFieldTypeInfos()) {
				set.add(fieldType.getFieldDescriptor().getId());
			}
			
			for(ReqCompFieldInfo field : reqComponent.getReqCompFields()) {
				if(!set.contains(field.getType())) {
					throw new OperationFailedException("Invalid field type: " + field.getType());
				}
			}
		} catch (Exception e) {
			throw new OperationFailedException(e.getMessage(), e);
		}
	}*/
	
	/**
     * Gets requirement component fields as a map.
     * 
     * @param reqComponent Requirement component
     * @return Map of requirement component fields
     */
	protected Map<String, String> getReqComponentFieldMap(ReqComponentInfo reqComponent) throws OperationFailedException {
		//validateReqComponentFields(reqComponent);
		List<ReqCompFieldInfo> fields = reqComponent.getReqCompFields();
        Map<String, String> map = new HashMap<String, String>();
        for (ReqCompFieldInfo field : fields) {
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
	protected String getReqComponentFieldValue(ReqComponentInfo reqComponent, String key) throws OperationFailedException {
        return getReqComponentFieldMap(reqComponent).get(key);
    }

    /**
     * Creates the context map (template data) for the requirement component.
     * Also, adds the field token map to the context map.
     *
     *
     *
     * @param reqComponent Requirement component
     * @param contextInfo
     * @throws DoesNotExistException If CLU, CluSet or relation does not exist
     */
    public Map<String, Object> createContextMap(ReqComponentInfo reqComponent, ContextInfo contextInfo) throws OperationFailedException {
        Map<String, Object> contextMap = new HashMap<String, Object>();
        contextMap.put(FIELDS_TOKEN, getReqComponentFieldMap(reqComponent));
        return contextMap;
    }
}
