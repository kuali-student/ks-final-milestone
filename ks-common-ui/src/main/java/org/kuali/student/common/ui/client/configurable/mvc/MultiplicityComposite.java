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
package org.kuali.student.common.ui.client.configurable.mvc;

import java.util.ArrayList;
import java.util.List;

import org.kuali.student.common.ui.client.mvc.dto.ModelDTOValue;
import org.kuali.student.common.ui.client.widgets.KSButton;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * A multiplicity composite allows a users to add/remove/display a list of 
 * item widgets. The item widget to be used must be provided by the createItem()
 * method, which can be a MultiplicitySection or any widget that supports the
 * HasModelDTOValue interface.  
 * 
 * @author Kuali Student Team
 *
 */
public abstract class MultiplicityComposite extends Composite implements HasModelDTOValue{
        
    private DockPanel mainPanel;
    private VerticalPanel itemsPanel;
    private ModelDTOValue.ListType modelDTOList;     

    /**
     * This simply decorates a list item widget to add styling and a remove button 
     *   
     * @author Kuali Student Team
     *
     */
    private class MultiplicityItemWidgetDecorator extends Composite{
        DockPanel dockPanel;
        Widget listItem;
        
        KSButton removeButton = new KSButton("-", new ClickHandler(){
            public void onClick(ClickEvent event) {
                ModelDTOValue listItemValue = ((HasModelDTOValue)listItem).getModelDTOValue(); 
                modelDTOList.get().remove(listItemValue);
                itemsPanel.remove(MultiplicityItemWidgetDecorator.this);
            }            
        });        
        
        private MultiplicityItemWidgetDecorator(Widget listItem){
            dockPanel = new DockPanel();
            this.listItem = listItem;
            initWidget(dockPanel);
            if (listItem instanceof MultiplicityComposite){
                ((MultiplicityComposite)listItem).init();
            }
            dockPanel.add(removeButton, DockPanel.EAST);            
            dockPanel.add(listItem, DockPanel.EAST);
        }
    }
    
    /**
     * Set the model dto list this list field maps to.
     * 
     * @see org.kuali.student.common.ui.client.configurable.mvc.HasModelDTOValue#setModelDTO()
     */
    @Override
    public void setModelDTOValue(ModelDTOValue modelDTOValue) {
        assert(modelDTOValue instanceof ModelDTOValue.ListType);
        this.modelDTOList = (ModelDTOValue.ListType)modelDTOValue;
        
        refresh();
    }


    /** 
     * 
     * @see org.kuali.student.common.ui.client.configurable.mvc.HasModelDTOValue#updateModelDTO(org.kuali.student.common.ui.client.mvc.dto.ModelDTOValue)
     */
    @Override
    public void updateModelDTOValue() {
        // TODO Will Gomes - THIS METHOD NEEDS JAVADOCS
        
    }
    
    /**
     * @see org.kuali.student.common.ui.client.configurable.mvc.HasModelDTOValue#getModelDTOValue()
     */
    public ModelDTOValue getModelDTOValue(){
        return modelDTOList;
    }
    
    private void addItem(){
        if (modelDTOList == null){
            modelDTOList = new ModelDTOValue.ListType();
            modelDTOList.set(new ArrayList<ModelDTOValue>());
        }
        
        Widget newItemWidget = createItem();
        MultiplicityItemWidgetDecorator listItem = new MultiplicityItemWidgetDecorator(newItemWidget);
        modelDTOList.get().add(((HasModelDTOValue)newItemWidget).getModelDTOValue());
        itemsPanel.add(listItem);
    }    
    
    private void addItem(ModelDTOValue modelDTOValue){
        Widget newItemWidget = createItem();
        MultiplicityItemWidgetDecorator listItem = new MultiplicityItemWidgetDecorator(newItemWidget);
        ((HasModelDTOValue)newItemWidget).setModelDTOValue(modelDTOValue);
        itemsPanel.add(listItem);        
    }
    
    /** 
     * Init method to support lazy initialization of multiplicity composite
     *
     */
    public void init(){
        mainPanel = new DockPanel();
        mainPanel.addStyleName("KS-Multiplicity-Composite");
        itemsPanel = new VerticalPanel();
        initWidget(mainPanel);
        
        KSButton addItemButton = new KSButton("Add Item", new ClickHandler(){
            public void onClick(ClickEvent event) {
                addItem();
            }            
        });                

        mainPanel.add(addItemButton, DockPanel.NORTH);
        mainPanel.add(itemsPanel, DockPanel.SOUTH);
        
    }

    private void refresh(){

        List<ModelDTOValue> modelDTOValueList = modelDTOList.get();
        
        for (int i=0; i < modelDTOValueList.size(); i++){
            addItem(modelDTOValueList.get(i));
        }                                   
    }
    
    /**
     * The model returned must return a widget that implements the HasModelDTOValue interface.
     * 
     * @return
     */
    public abstract Widget createItem();
    
    
}
