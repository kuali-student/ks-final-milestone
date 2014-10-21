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

/**
 * 
 */
package org.kuali.student.lum.kim.role.type;

import java.util.Collections;
import java.util.List;

/**
 * Action Request derived role type to look at non-adhoc action requests with a
 * status of DONE where an action taken has already satisfied the request
 * 
 */
public class KSNonAdhocDoneActionRequestDerivedRoleTypeServiceImpl extends KSNonAdhocActionRequestDerivedRoleTypeServiceImpl {

	/* (non-Javadoc)
     * @see org.kuali.student.lum.kim.role.type.KSActionRequestDerivedRoleTypeServiceImpl#getRequestStatusesToCheck()
     */
    @Override
    protected List<REQUESTS_STATUS_TO_CHECK> getRequestStatusesToCheck() {
		return Collections.singletonList(REQUESTS_STATUS_TO_CHECK.DONE);
    }

}
