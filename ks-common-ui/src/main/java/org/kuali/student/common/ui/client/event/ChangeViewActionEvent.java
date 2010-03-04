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


/**
 * The ChangeViewActionEvent can be used to request the controller to change the current view to the desired view.
*
 * @param <V>  This should be the enumeration class that defines the view enum known by the controller on which this
 * action event is being fired upon.
 */
public class ChangeViewActionEvent<V extends Enum<?>> extends ActionEvent<ChangeViewActionHandler>{
    public static final Type<ChangeViewActionHandler> TYPE = new Type<ChangeViewActionHandler>();
    
    private V viewType;
    
    private ViewDetail viewDetail;
    
    /** 
     * @param viewType The name of the view to switch to.
     */
    public ChangeViewActionEvent(V viewType){
        this.viewType = viewType;
    }
    
    /** 
     * @param viewType The name of the view to switch
     * @param viewDetail Any data required to switch to this view.
     */
    public ChangeViewActionEvent(V viewType, ViewDetail viewDetail){
        this.viewType = viewType;
        this.viewDetail = viewDetail;
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

    public ViewDetail getViewDetail() {
        return viewDetail;
    }

    /** 
     * Use to pass along any data that may be required by the controller to initialize the view in question.
     * 
     * @param viewDetail 
     */
    public void setViewDetails(ViewDetail viewDetail) {
        this.viewDetail = viewDetail;
    }
    
    //TODO: A more defined structure of details that may be required to switch the view
    public static class ViewDetail{
    	String dataId;
    	public ViewDetail(String dataId){
    		this.dataId = dataId;
    	}
    	
    	public String getDataId(){
    		return dataId;
    	}
    }
}
