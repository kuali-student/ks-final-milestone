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
 * method, which can be a MultiplicitySection, another MultiplicityComposite, 
 * or any widget that supports the HasModelDTOValue interface.  
 * 
 * @author Kuali Student Team
 *
 */
public abstract class MultiplicityComposite extends Composite implements HasModelDTOValue{
        
    private DockPanel mainPanel;
    private VerticalPanel itemsPanel;
    private ModelDTOValue.ListType modelDTOList;
    private List<HasModelDTOValue> modelDTOValueWidgets;
    private boolean loaded = false;

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
                ((MultiplicityComposite)listItem).redraw();
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
        
        redraw();
    }


    /** 
     * @see org.kuali.student.common.ui.client.configurable.mvc.HasModelDTOValue#updateModelDTO(org.kuali.student.common.ui.client.mvc.dto.ModelDTOValue)
     */
    @Override
    public void updateModelDTOValue() {
        for (int i=0; i < itemsPanel.getWidgetCount(); i++){
            Widget w = itemsPanel.getWidget(i); 
            if (w instanceof HasModelDTOValue){
                ((HasModelDTOValue)w).updateModelDTOValue();
            }
        }
    }
    
    /**
     * @see org.kuali.student.common.ui.client.configurable.mvc.HasModelDTOValue#getModelDTOValue()
     */
    public ModelDTOValue getModelDTOValue(){
        return modelDTOList;
    }
    
    /**
     * This method creates a new empty item.
     *
     */
    private void addItem(){
        if (modelDTOList == null){
            modelDTOList = new ModelDTOValue.ListType();
            modelDTOList.set(new ArrayList<ModelDTOValue>());
        }
        
        Widget newItemWidget = createItem();
        MultiplicityItemWidgetDecorator listItem = new MultiplicityItemWidgetDecorator(newItemWidget);
        modelDTOList.get().add(((HasModelDTOValue)newItemWidget).getModelDTOValue());
        itemsPanel.add(listItem);
        modelDTOValueWidgets.add((HasModelDTOValue)newItemWidget);
    }    
    
    /**
     * 
     * This method creates a new item, populated using values from the modelDTOValue object
     * 
     * @param modelDTOValue
     */
    private void addItem(ModelDTOValue modelDTOValue){
        Widget newItemWidget = createItem();
        MultiplicityItemWidgetDecorator listItem = new MultiplicityItemWidgetDecorator(newItemWidget);
        ((HasModelDTOValue)newItemWidget).setModelDTOValue(modelDTOValue);
        itemsPanel.add(listItem);
        modelDTOValueWidgets.add((HasModelDTOValue)newItemWidget);
    }
    
    /** 
     * Init method to support lazy initialization of multiplicity composite
     *
     */
    public void redraw(){        
        //Setup if not already loaded
        if (!loaded){
            modelDTOValueWidgets = new ArrayList<HasModelDTOValue>();
            
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
            loaded = true;
        } else {        
            clear();
        }
        
        //Update the composite with values from ModelDTO
        if (modelDTOList != null){
            List<ModelDTOValue> modelDTOValueList = modelDTOList.get();
            
            for (int i=0; i < modelDTOValueList.size(); i++){
                addItem(modelDTOValueList.get(i));
            }                                               
        }
    }
    
    /**
     *
     *  This method will remove all data associated with this mode
     *
     */
    public void clear(){
        if (itemsPanel != null){itemsPanel.clear();}
        //TODO: Should this do iterate over each widget and call their clear()
        if (modelDTOValueWidgets != null){modelDTOValueWidgets.clear();}        
        if (modelDTOList != null){modelDTOList.get().clear();}
    }
    
    /**
     * This must return a widget that implements the HasModelDTOValue interface. This method
     * will be used when user clicks the "Add" button to add a new item to the list of items.
     * 
     * @return
     */
    public abstract Widget createItem();
    
    
}
