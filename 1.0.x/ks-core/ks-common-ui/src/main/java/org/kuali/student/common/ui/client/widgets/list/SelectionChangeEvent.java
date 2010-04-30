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

package org.kuali.student.common.ui.client.widgets.list;

import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.user.client.ui.Widget;

/**
 * This is a description of what this class does - Will Gomes don't forget to fill this in. 
 * 
 * @author Kuali Student Team
 *
 */
public class SelectionChangeEvent extends GwtEvent<SelectionChangeHandler> {

    private static Type<SelectionChangeHandler> TYPE;
    private Widget widget;

    /**
     * Used to fire a SelectionChangeEvent
     * 
     */
    public static void fire(Widget source) {
        if (TYPE != null) {
          SelectionChangeEvent event = new SelectionChangeEvent(source);
          source.fireEvent(event);
          
        }
      }
    
    public SelectionChangeEvent(Widget widget){
    	this.widget = widget;
    }
    
    public Widget getWidget() {
		return widget;
	}

	public void setWidget(Widget widget) {
		this.widget = widget;
	}

	/**
     * Gets the type associated with this event.
     * 
     * @return returns the handler type
     */
    public static Type<SelectionChangeHandler> getType() {
      if (TYPE == null) {
        TYPE = new Type<SelectionChangeHandler>();
      }
      return TYPE;
    }

    /**
     * @see com.google.gwt.event.shared.GwtEvent#dispatch(com.google.gwt.event.shared.EventHandler)
     */
    @Override
    protected void dispatch(SelectionChangeHandler handler) {
        handler.onSelectionChange(this);
    }

    /**
     * @see com.google.gwt.event.shared.GwtEvent#getAssociatedType()
     */
    @Override
    public Type<SelectionChangeHandler> getAssociatedType() {
        return TYPE;
    }

}
