package org.kuali.student.lum.ui.requirements.client;

import org.kuali.student.commons.ui.mvc.client.ApplicationContext;
import org.kuali.student.commons.ui.mvc.client.ViewMetadataLoadCallback;
import org.kuali.student.commons.ui.viewmetadata.client.ViewMetaData;
import org.kuali.student.commons.ui.widgets.ErrorDialog;
import org.kuali.student.lum.ui.requirements.client.controller.LumApplication;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class RequirementsEntryPoint implements EntryPoint {

    public void onModuleLoad() {
        System.out.println("HERE....0"); 
        
        RootPanel.get().add(new LumApplication());        
        
//        ErrorDialog.bindUncaughtExceptionHandler();
//        ApplicationContext.loadViewMetadata(RequirementsController.VIEW_NAME, new ViewMetadataLoadCallback() {
//            public void onBulkLoadComplete() {
//                // now that the metadata is loaded, show the view
//                RootPanel.get().add(new LumApplication());
//            }
//
//            public void onLoad(String viewName, ViewMetaData view) {
//            // do nothing, no incremental progress required
//            }
//        });
    }
}
