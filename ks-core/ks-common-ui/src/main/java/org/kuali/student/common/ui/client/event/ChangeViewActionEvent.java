/*
 * Copyright 2009 The Kuali Foundation Licensed under the
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
package org.kuali.student.common.ui.client.event;

import org.kuali.student.common.ui.client.application.ViewContext;


/**
 * The ChangeViewActionEvent can be used to request the controller to change the current view to the desired view.
*
 * @param <V>  This should be the enumeration class that defines the view enum known by the controller on which this
 * action event is being fired upon.
 */
public class ChangeViewActionEvent<V extends Enum<?>> extends ActionEvent<ChangeViewActionHandler>{
    public static final Type<ChangeViewActionHandler> TYPE = new Type<ChangeViewActionHandler>();
    
    private V viewType;
    
    private ViewContext viewContext;
    
    /** 
     * @param viewType The name of the view to switch to.
     */
    public ChangeViewActionEvent(V viewType){
        this.viewType = viewType;
    }
    
    /** 
     * @param viewType The name of the view to switch
     * @param viewContext Context information (eg. ids, actions) required by view.
     */
    public ChangeViewActionEvent(V viewType, ViewContext viewContext){
        this.viewType = viewType;
        this.viewContext = viewContext;
    }

    @Override
    protected void dispatch(ChangeViewActionHandler handler) {
        handler.onViewStateChange(this);
    }

    @Override
    public com.google.gwt.event.shared.GwtEvent.Type<ChangeViewActionHandler> getAssociatedType() {
        return TYPE;
    }
    
    public V getViewType(){
        return viewType;
    }

	public ViewContext getViewContext() {
		return viewContext;
	}

	public void setViewContext(ViewContext viewContext) {
		this.viewContext = viewContext;
	}       
}
