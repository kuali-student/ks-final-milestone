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

package org.kuali.student.brms.devgui.client;

import org.kuali.student.commons.ui.mvc.client.ApplicationContext;
import org.kuali.student.commons.ui.mvc.client.ViewMetadataLoadCallback;
import org.kuali.student.commons.ui.viewmetadata.client.ViewMetaData;
import org.kuali.student.commons.ui.widgets.ErrorDialog;
import org.kuali.student.brms.devgui.client.controller.DevelopersGuiController;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class DevelopersGuiEntryPoint implements EntryPoint {

    public void onModuleLoad() {
        ErrorDialog.bindUncaughtExceptionHandler();
        ApplicationContext.loadViewMetadata(DevelopersGuiController.VIEW_NAME, new ViewMetadataLoadCallback() {
            public void onBulkLoadComplete() {
                // now that the metadata is loaded, show the view
                RootPanel.get().add(new DevelopersGuiController());
            }

            public void onLoad(String viewName, ViewMetaData view) {
            // do nothing, no incremental progress required
            }
        });
    }
}
