package org.kuali.student.core.workflow.ui.client.widgets;

import org.kuali.student.common.ui.client.configurable.mvc.layouts.ContentNavLayoutController;
import org.kuali.student.common.ui.client.application.KSAsyncCallback;
import org.kuali.student.common.assembly.data.Metadata;

public interface WorkflowEnhancedNavController extends ContentNavLayoutController{
	public WorkflowUtilities getWfUtilities();
	public void getMetadataForFinalState(KSAsyncCallback<Metadata> metadata);
}
