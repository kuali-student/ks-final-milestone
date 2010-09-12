package org.kuali.student.core.workflow.ui.client.widgets;

import org.kuali.student.common.ui.client.mvc.DataModelDefinition;

public interface ContentConfigurer {
	public void configure(WorkflowEnhancedController layout);
	public void setModelDefinition(DataModelDefinition modelDefinition);
}
