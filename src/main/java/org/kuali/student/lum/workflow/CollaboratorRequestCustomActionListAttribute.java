package org.kuali.student.lum.workflow;

import org.kuali.rice.kew.actionitem.ActionItem;
import org.kuali.rice.kew.actionlist.CustomActionListAttribute;
import org.kuali.rice.kew.actionlist.DisplayParameters;
import org.kuali.rice.kew.actions.ActionSet;
import org.kuali.rice.kew.web.session.UserSession;

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
    public ActionSet getLegalActions(UserSession userSession, ActionItem actionItem) throws Exception {
    	return DEFAULT_LEGAL_ACTIONS;
	}
	
	@Override
    public DisplayParameters getDocHandlerDisplayParameters(UserSession userSession, ActionItem actionItem) throws Exception {
		return null;
	}

}
