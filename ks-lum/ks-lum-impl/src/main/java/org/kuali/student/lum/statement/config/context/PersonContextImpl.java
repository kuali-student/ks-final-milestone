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

import java.util.HashMap;
import java.util.Map;

import org.kuali.student.common.exceptions.OperationFailedException;
import org.kuali.student.core.statement.dto.ReqComponentInfo;
import org.kuali.student.lum.statement.typekey.ReqComponentFieldTypes;

/**
 * This class creates the template context for a person.
 */
public class PersonContextImpl extends BasicContextImpl {
 
	public final static String PERSON_TOKEN = "personid";

    /**
     * Creates the context map (template data) for the requirement component.
     * 
     * @param reqComponent Requirement component
     * @throws OperationFailedException Creating context map fails
     */
    public Map<String, Object> createContextMap(ReqComponentInfo reqComponent) throws OperationFailedException {
        Map<String, Object> contextMap = new HashMap<String, Object>();
        contextMap.put(PERSON_TOKEN, getReqComponentFieldValue(reqComponent, ReqComponentFieldTypes.PERSON_KEY.getId()));
        return contextMap;
    }
}
