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

package org.kuali.student.krms.naturallanguage.config.context;

import org.kuali.student.r2.core.krms.naturallanguage.TermParameterTypes;
import org.kuali.student.r2.common.exceptions.OperationFailedException;

import java.util.Map;


/**
 * This class creates the template context for grade check type.
 */
public class GpaContextImpl extends BasicContextImpl {
	/** GPA template token */ 
	public final static String GPA_TOKEN = "gpa";
	
    /**
     * Creates the context map (template data) for the requirement component.
     *
     * @param parameters
     * @param contextInfo
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException Creating context map fails
     */
        @Override
    public Map<String, Object> createContextMap(Map<String, Object> parameters)  {
    	Map<String, Object> contextMap = super.createContextMap(parameters);

    	String gpa = (String) parameters.get(TermParameterTypes.GPA_KEY.getId());
    	if(gpa != null) {
    		contextMap.put(GPA_TOKEN, gpa);
    	}

        return contextMap;
    }
}
