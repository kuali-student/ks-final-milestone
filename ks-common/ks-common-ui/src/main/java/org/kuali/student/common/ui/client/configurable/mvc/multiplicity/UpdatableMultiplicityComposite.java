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

package org.kuali.student.common.ui.client.configurable.mvc.multiplicity;

import org.kuali.student.common.ui.client.widgets.KSButton;
import org.kuali.student.common.ui.client.widgets.KSButtonAbstract.ButtonStyle;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Widget;

/**
 * This extends multiplicity composite to allow adding and removing of items. 
 * 
 * @author Kuali Student Team
 *
 */
public abstract class UpdatableMultiplicityComposite extends MultiplicityComposite {
    protected String addItemLabel;
    protected String itemLabel;
    protected boolean readOnly = false;
    
    public UpdatableMultiplicityComposite(StyleType style){
    	super(style);
    }
    
    public UpdatableMultiplicityComposite(StyleType style, boolean readOnly){
    	super(style);
    	this.readOnly=readOnly;
    }
    
    /**
     * @see org.kuali.student.common.ui.client.configurable.mvc.multiplicity.MultiplicityComposite#getItemDecorator()
     */
    @Override
    public MultiplicityItem getItemDecorator(StyleType style) {
        org.kuali.student.common.ui.client.configurable.mvc.sections.RemovableItemWithHeader item = new org.kuali.student.common.ui.client.configurable.mvc.sections.RemovableItemWithHeader(style, readOnly);
        item.setItemLabel(itemLabel + " " + itemCount);            
        return item;
    }

    /**
     * @see org.kuali.student.common.ui.client.configurable.mvc.multiplicity.MultiplicityComposite#generateAddWidget()
     */
    public Widget generateAddWidget() {
    	//If this is read only don't display add widgets
    	if(readOnly){
    		return null;
    	}
    	
    	KSButton addWidget;
    	if(style == StyleType.TOP_LEVEL){
    		addWidget = new KSButton(addItemLabel, ButtonStyle.FORM_LARGE);
    	}
    	else{
    		addWidget = new KSButton(addItemLabel, ButtonStyle.FORM_SMALL);
    	}
        addWidget.addClickHandler(new ClickHandler(){
            public void onClick(ClickEvent event) {
                addItem();
            }            
        });
        return addWidget;
    }


    public String getAddItemLabel() {
        return addItemLabel;
    }


    public void setAddItemLabel(String addItemLabel) {
        this.addItemLabel = addItemLabel;
    }

    public String getItemLabel() {
        return itemLabel;
    }

    public void setItemLabel(String itemLabel) {
        this.itemLabel = itemLabel;
    }
    

}
