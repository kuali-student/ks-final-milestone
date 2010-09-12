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

package org.kuali.student.common.ui.client.configurable.mvc.multiplicity.wip;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;
import org.kuali.student.common.ui.client.configurable.mvc.SectionTitle;

import static org.kuali.student.common.ui.client.configurable.mvc.multiplicity.wip.MultiplicityConfiguration.*;

import org.kuali.student.common.ui.client.mvc.Callback;

/**
 *
 * @author Kuali Student Team
 *
 */
public class MultiplicityGroupItem extends Composite{
	
	private MultiplicityHeader header;
	private FlowPanel layout = new FlowPanel();
	private FlowPanel body = new FlowPanel();
	private String itemLabel;
    private boolean updateable=false;
	private boolean loaded = false;
	
    private Integer itemKey;      
    private Widget itemWidget;
    private Callback<MultiplicityGroupItem> removeCallback;

    private MultiplicityConfiguration.StyleType style;
    
    private boolean isCreated = true;
    private boolean isDeleted = false;

    /**
     *      * 
     *      !!!!!! WORK IN PROGRESS  !!!!!!
     *     
     * @param style
     */
    public MultiplicityGroupItem(StyleType style) {
		super();
        this.style = style;
		initWidget(layout);
	}
   
    public void setItemLabel(String itemLabel) {
        this.itemLabel = itemLabel;
    }

	public void redraw() {
		if (!loaded){
			layout.clear();
	    	if(style == StyleType.TOP_LEVEL){
	    		SectionTitle title = SectionTitle.generateH4Title(itemLabel);
	    		title.addStyleName("ks-form-bordered-header-title");
	    		header = new MultiplicityHeader(title, updateable);
	    		header.setStyleName("ks-form-bordered-header");
	    		layout.setStyleName("ks-form-bordered");
	    		body.setStyleName("ks-form-bordered-body");
	    	}
	    	else if(style == StyleType.SUB_LEVEL){
	    		SectionTitle title = SectionTitle.generateH5Title(itemLabel);
	    		title.addStyleName("ks-form-course-format-activity-header-title");
	    		header = new MultiplicityHeader(title, updateable);
	    		header.setStyleName("ks-form-course-format-activity-header");
	    		layout.setStyleName("ks-form-course-format-activity");
	    	}
	    	
	    	//This check is to disable delete of items in case of readonly mode.
	    	if(updateable){
	    	    header.addDeleteHandler(new ClickHandler() {
	                public void onClick(ClickEvent event) {
	                    getRemoveCallback().exec(MultiplicityGroupItem.this);
	                }
	            });
	    	}
	    	
	    	layout.add(header);
	    	body.add(this.getItemWidget());
	    	layout.add(body);
		}		
	}
    
    
    public void clear() {
        loaded = false;
    }
    
	public Integer getItemKey() {
        return itemKey;
    }

    public void setItemKey(Integer itemKey) {
        this.itemKey = itemKey;
    }

    public Widget getItemWidget() {
        return itemWidget;
    }

    public void setItemWidget(Widget itemWidget) {
        this.itemWidget = itemWidget;
    }

    public void setRemoveCallback(Callback<MultiplicityGroupItem> callback){
        removeCallback = callback;
    }

    public Callback<MultiplicityGroupItem> getRemoveCallback(){
        return removeCallback;
    }

    public boolean isCreated() {
        return isCreated;
    }

    public void setCreated(boolean created) {
        this.isCreated = created;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
        if(isDeleted)
            this.isCreated = false;
    }

	public boolean isUpdateable() {
		return updateable;
	}

	public void setUpdateable(boolean updateable) {
		this.updateable = updateable;
	}
    
    
}