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

package org.kuali.student.common.ui.client.mvc;

import org.kuali.student.common.ui.client.configurable.mvc.views.SectionView;
import org.kuali.student.common.ui.client.mvc.breadcrumb.BreadcrumbSupport;
import org.kuali.student.common.ui.client.mvc.history.HistorySupport;

import com.google.gwt.user.client.ui.Widget;

/**
 * Interface defining the operations necessary to implement a view.
 * 
 * @author Kuali Student Team
 */
public interface View extends HistorySupport, BreadcrumbSupport {
    /**
     * Called by controller before the view is displayed to allow lazy initialization or any other preparatory work to be
     * done.  Executes the callback with true if when the view is ready to be shown.
     */
    public void beforeShow(Callback<Boolean> onReadyCallback);

    /**
     * Called by the controller before the view is hidden to allow the view to perform cleanup or request confirmation from
     * the user, etc. Can cancel the action by returning false.
     * 
     * @return true if the view can be hidden, or false to cancel the action.
     */
    public boolean beforeHide();

    /**
     * Returns the controller associated with the view
     * 
     * @return
     */
    public Controller getController();

    /**
     * Returns the view's name
     * 
     * @return
     */
    public String getName();
    
    public Enum<?> getViewEnum();

    /** 
     * Can be called to reset a view to a cleared state.
     *
     */
    public void clear();
    
    /**
     * Updates the model with information from this view. If this view does not need to update the model
     * leave this method empty.
     */
    public void updateModel();
    
    /**
     * Get the attachable widget which represents this view
     * @see SectionView
     * @return
     */
    public Widget asWidget();
    
    /**
     * 
     * This method needs to be implemented only on views that want the export button to display.
     * The default implementation is not to display the export button
     * 
     * @return
     */
    public boolean isExportButtonActive();
    
    public void showExport(boolean show);
    
    
}
