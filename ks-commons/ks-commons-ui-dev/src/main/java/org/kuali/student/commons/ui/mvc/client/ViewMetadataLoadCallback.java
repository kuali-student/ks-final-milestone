package org.kuali.student.commons.ui.mvc.client;

import org.kuali.student.commons.ui.viewmetadata.client.ViewMetaData;

/**
 * Callback used when loading view metadata via the ApplicationContext.
 */
public interface ViewMetadataLoadCallback {
    /**
     * Called when metadata for the specified view name is loaded
     * 
     * @param viewName
     *            the name of the view for which metadata was loaded
     * @param view
     *            the metadata that was loaded
     */
    public void onLoad(String viewName, ViewMetaData view);

    /**
     * Called when all metadata requested has been loaded
     */
    public void onBulkLoadComplete();
}
