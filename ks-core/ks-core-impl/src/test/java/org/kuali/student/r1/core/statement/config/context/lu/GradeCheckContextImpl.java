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

package org.kuali.student.r1.core.statement.config.context.lu;

import java.util.HashMap;
import java.util.Map;

import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r1.core.statement.dto.ReqComponentInfo;
import org.kuali.student.r1.core.statement.naturallanguage.ReqComponentFieldTypes;

/**
 * This class creates the template context for grade check type.
 */
public class GradeCheckContextImpl extends AbstractLuContext<ReqComponentInfo> {
	/** GPA template token */ 
	private final static String GPA_TOKEN = "gpa";
	
    /**
     * Creates the context map (template data) for the requirement component.
     * 
     *
     *
     *
     *
     *
     * @param reqComponent Requirement component
     * @param contextInfo
     * @throws DoesNotExistException If CLU, CluSet or relation does not exist
     */
    public Map<String, Object> createContextMap(ReqComponentInfo reqComponent, ContextInfo contextInfo) throws OperationFailedException {
    	Map<String, Object> contextMap = new HashMap<String, Object>();
    	contextMap.put(GPA_TOKEN, getReqComponentFieldValue(reqComponent, ReqComponentFieldTypes.GPA_KEY.getType()));

        return contextMap;
    }
}
