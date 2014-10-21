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

import java.util.Map;
import org.kuali.rice.core.api.exception.RiceIllegalStateException;

/**
 * This class creates the template context for course list types.
 */
public class CourseListContextImpl extends AbstractLuContext {
	/**
	 * <code>cluSet</code> token (key) references a CluSet object to be used in a template
	 * e.g. 'Student must have completed all of $cluSet.getCluSetAsCode()' 
	 */
	protected final static String CLU_SET_TOKEN = "cluSet";
	/**
     * Creates the context map (template data) for the requirement component.
     *
     * @param parameters
     * @param contextInfo
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException If CLU, CluSet or relation does not exist
     */
    @Override
    public Map<String, Object> createContextMap(Map<String, Object> parameters)  {
        Map<String, Object> contextMap = super.createContextMap(parameters);
            try {
                contextMap.put(CLU_SET_TOKEN, getCluSet(parameters));
            } catch (OperationFailedException ex) {
                throw new RiceIllegalStateException (ex);
            }
        return contextMap;
    }
}
