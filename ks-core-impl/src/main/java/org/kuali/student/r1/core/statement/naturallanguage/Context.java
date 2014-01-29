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

import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.OperationFailedException;

import java.util.Map;

@Deprecated
public interface Context<T> {
    /**
     * Creates the template context map (template token and data) for 
     * a specific context.
     * 
     *
     * @param context Context to create the map from
     * @param contextInfo
     * @throws OperationFailedException If creating context data map fails
     */
	public Map<String, Object> createContextMap(T context, ContextInfo contextInfo) throws OperationFailedException;
}
