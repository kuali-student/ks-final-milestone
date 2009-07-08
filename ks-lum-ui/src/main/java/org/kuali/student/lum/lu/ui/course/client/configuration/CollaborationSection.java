package org.kuali.student.lum.lu.ui.course.client.configuration;

import org.kuali.student.common.ui.client.configurable.ConfigurableField;
import org.kuali.student.common.ui.client.configurable.ConfigurableLayoutSection;
import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.core.validation.dto.ValidationResultInfo;
import org.kuali.student.lum.lu.ui.course.client.service.CluProposal;
import org.kuali.student.lum.lu.ui.course.client.widgets.Collaborators;

public class CollaborationSection extends SimpleConfigurableSection<CluProposal> {
	
	private Collaborators collab = new Collaborators(); 
	
	
	public CollaborationSection() {
		super();
		panel.add(collab);
	}
	
	@Override
	public ConfigurableLayoutSection<CluProposal> addField(
			ConfigurableField<CluProposal> field) {
		// Can't add fields for now
		return this;
	}

	@Override
	public void populate() {
		if(getParentLayout()!=null){
			CluProposal proposal = getParentLayout().getObject();
			if(proposal!=null&&proposal.getWorkflowId()!=null){
				collab.setWorkflowId(proposal.getWorkflowId());
				collab.refreshCollaboratorList();
			}		
		}
	}

	@Override
	public void updateObject() {
		//Object is read only for this widget
	}

	@Override
	public void validate(Callback<ValidationResultInfo.ErrorLevel> callback) {
		// TODO Auto-generated method stub

	}

}
