/*
 * Copyright 2007 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 1.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl1.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.student.common.ui.client.configurable.mvc.multiplicity;



import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

/**
 * This is a description of what this class does - Will Gomes don't forget to fill this in. 
 * 
 * @author Kuali Student Team
 *
 */
public abstract class UpdatableMultiplicityComposite extends MultiplicityComposite {
    protected String addItemLabel;
    protected String itemLabel;    
    
    public UpdatableMultiplicityComposite(){
    }
    
    /**
     * @see org.kuali.student.common.ui.client.configurable.mvc.multiplicity.MultiplicityComposite#getItemDecorator()
     */
    @Override
    public MultiplicityItem getItemDecorator() {
        RemovableItem item = new RemovableItem();
        item.setItemLabel(itemLabel);            

        return item;
    }

    /**
     * @see org.kuali.student.common.ui.client.configurable.mvc.multiplicity.MultiplicityComposite#generateAddWidget()
     */
    public Widget generateAddWidget() {
        Label addWidget =  new Label(addItemLabel);
        addWidget.addStyleName("KS-Multiplicity-Labels");
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
