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

package org.kuali.student.lum.workflow;

import org.kuali.rice.kew.actionitem.ActionItem;
import org.kuali.rice.kew.actionlist.CustomActionListAttribute;
import org.kuali.rice.kew.actionlist.DisplayParameters;
import org.kuali.rice.kew.actions.ActionSet;
import org.kuali.rice.kns.UserSession;

public class CollaboratorRequestCustomActionListAttribute implements
		CustomActionListAttribute {

	private static final long serialVersionUID = 6776164670024486696L;

	/**
	 * Sets up the default ActionSet which includes only FYIs.
	 */
	private static ActionSet DEFAULT_LEGAL_ACTIONS = new ActionSet();
	static {
		DEFAULT_LEGAL_ACTIONS.addApprove();
		DEFAULT_LEGAL_ACTIONS.addDisapprove();
	}
	
	public CollaboratorRequestCustomActionListAttribute() {}
    
	@Override
	public ActionSet getLegalActions(String principalId, ActionItem actionItem) throws Exception{
	    return DEFAULT_LEGAL_ACTIONS;
	}

    @Override
    public DisplayParameters getDocHandlerDisplayParameters(String principalId, ActionItem actionItem) throws Exception{
        return null;
    }

}
