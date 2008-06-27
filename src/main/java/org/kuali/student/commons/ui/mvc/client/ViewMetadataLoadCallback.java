package org.kuali.student.commons.ui.mvc.client;

import org.kuali.student.commons.ui.viewmetadata.client.ViewMetaData;

public interface ViewMetadataLoadCallback {
	public void onLoad(String viewName, ViewMetaData view);
	public void onBulkLoadComplete();
}
