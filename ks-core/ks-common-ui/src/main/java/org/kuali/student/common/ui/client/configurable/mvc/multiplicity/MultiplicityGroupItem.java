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
 *
 * Date: 14-Jul-2010
 * Time: 10:52:16 AM
 */

package org.kuali.student.common.ui.client.configurable.mvc.multiplicity;

import org.kuali.student.common.ui.client.configurable.mvc.multiplicity.MultiplicityConfiguration.StyleType;
import org.kuali.student.common.ui.client.configurable.mvc.sections.BaseSection;
import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.common.ui.client.widgets.field.layout.layouts.BorderedHeadedLayout;
import org.kuali.student.common.ui.client.widgets.field.layout.layouts.HeadedLayout;
import org.kuali.student.common.ui.client.widgets.field.layout.layouts.UnborderedHeadedLayout;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Widget;

public class MultiplicityGroupItem extends BaseSection {
	
    private boolean updateable=false;
    private Integer itemKey;      
    private Widget itemWidget;
    private Callback<MultiplicityGroupItem> removeCallback;
    private boolean created = true;
    private boolean deleted = false;
	private boolean loaded = false;

    private MultiplicityConfiguration.StyleType style;

	String itemLabel;

    /**
     *  Represents a single item within a MultiplicityGroup
     *
     * //TODO Should this be an inner class in MultiplicityGroup?
     *  
     * @param itemLabel
     * @param style
     * @param updateable
     */
    public MultiplicityGroupItem(String itemLabel, StyleType style, boolean updateable){

		this.itemLabel = itemLabel;
		this.style = style;
		this.updateable = updateable;
	}

    private void buildLayout() {

        switch (style) {
            case TOP_LEVEL_GROUP:
                layout = new BorderedHeadedLayout(itemLabel, updateable);
                break;
            case SUB_LEVEL_GROUP:
                layout = new UnborderedHeadedLayout(itemLabel, updateable);
                break;
        }
        ((HeadedLayout)layout).setUpdateable(updateable);
    }

	public void redraw() {
		if (!loaded){
			buildLayout(); 

			if(updateable){
	    	    ((HeadedLayout)layout).addDeleteHandler(new ClickHandler() {
	                public void onClick(ClickEvent event) {
	                    getRemoveCallback().exec(MultiplicityGroupItem.this);
	                }
	            });
	    	}
	    	
	    	layout.addWidget(this.getItemWidget());
	    	this.add(layout);
		}		
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
        return created;
    }

    public void setCreated(boolean created) {
        this.created = created;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean isDeleted) {
        this.deleted = isDeleted;
        if(isDeleted)
            this.created = false;
    }
}
