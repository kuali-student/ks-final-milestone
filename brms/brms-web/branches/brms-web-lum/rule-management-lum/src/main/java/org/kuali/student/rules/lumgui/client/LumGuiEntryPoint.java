package org.kuali.student.rules.lumgui.client;

import org.kuali.student.commons.ui.mvc.client.ApplicationContext;
import org.kuali.student.commons.ui.mvc.client.ViewMetadataLoadCallback;
import org.kuali.student.commons.ui.viewmetadata.client.ViewMetaData;
import org.kuali.student.commons.ui.widgets.ErrorDialog;
import org.kuali.student.rules.lumgui.client.controller.LumGuiController;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class LumGuiEntryPoint implements EntryPoint {

    public void onModuleLoad() {
        ErrorDialog.bindUncaughtExceptionHandler();
        ApplicationContext.loadViewMetadata(LumGuiController.VIEW_NAME, new ViewMetadataLoadCallback() {
            public void onBulkLoadComplete() {
                // now that the metadata is loaded, show the view
                RootPanel.get().add(new LumGuiController());
            }

            public void onLoad(String viewName, ViewMetaData view) {
            // do nothing, no incremental progress required
            }
        });
    }
}
