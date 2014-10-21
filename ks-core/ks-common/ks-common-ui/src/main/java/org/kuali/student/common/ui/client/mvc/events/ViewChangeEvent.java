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

package org.kuali.student.common.ui.client.mvc.events;

import org.kuali.student.common.ui.client.mvc.ApplicationEvent;
import org.kuali.student.common.ui.client.mvc.View;

/**
 * "Checked" event that is fired by the controller when the view is changed
 * 
 * @author Kuali Student Team
 */
public class ViewChangeEvent extends ApplicationEvent<ViewChangeHandler> {
    public static final Type<ViewChangeHandler> TYPE = new Type<ViewChangeHandler>();

    private final View previousView;
    private final View newView;

    /**
     * This constructs a new ViewChangeEvent
     * 
     * @param previousView
     *            the previously displayed View, or null if this is the first view change
     * @param newView
     *            the new View to be displayed
     */
    public ViewChangeEvent(View previousView, View newView) {
        super();
        this.previousView = previousView;
        this.newView = newView;
    }

    /**
     * Returns the previously displayed view, or null if this is the first view change
     * 
     * @return
     */
    public View getPreviousView() {
        return previousView;
    }

    /**
     * Returns the new View to be displayed
     * 
     * @return
     */
    public View getNewView() {
        return newView;
    }

    @Override
    protected void dispatch(ViewChangeHandler handler) {
        handler.onViewChange(this);
    }

    @Override
    public Type<ViewChangeHandler> getAssociatedType() {
        return TYPE;
    }

}
