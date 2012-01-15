package org.kuali.student.core.document.ui.client.widgets.documenttool;

import org.kuali.student.common.ui.client.configurable.mvc.binding.ModelWidgetBindingSupport;
import org.kuali.student.common.ui.client.mvc.DataModel;

public class DocumentListBinding extends ModelWidgetBindingSupport<DocumentList>{

	private String objectIdPath;
	public DocumentListBinding(String objectIdPath){
		this.objectIdPath = objectIdPath;
	}
	
	@Override
	public void setModelValue(DocumentList widget, DataModel model, String path) {
		// Not needed, this is a read only binding
		
	}

	@Override
	public void setWidgetValue(DocumentList widget, DataModel model, String path) {
		//ignore path, not needed
        if (model.get(objectIdPath) != null) {
            String id = model.get(objectIdPath);
            widget.getAndSetDocInfos(id);
        }
	}

}
