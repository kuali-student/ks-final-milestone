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
import org.kuali.student.common.ui.client.widgets.layout.HorizontalBlockFlowPanel;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
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
/**
 * This is a description of what this class does - Will Gomes don't forget to fill this in. 
 * 
 * @author Kuali Student Team
 *
 */
public abstract class MultiplicityComposite extends Composite implements HasModelDTOValue{
        
    private FlowPanel mainPanel = new FlowPanel();
    private FlowPanel itemsPanel = new FlowPanel();
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
        HorizontalBlockFlowPanel itemPanel;
        Widget listItem;
        
        KSButton removeButton = new KSButton("-", new ClickHandler(){
            public void onClick(ClickEvent event) {
                ModelDTOValue listItemValue = ((HasModelDTOValue)listItem).getValue(); 
                modelDTOList.get().remove(listItemValue);
                itemsPanel.remove(MultiplicityItemWidgetDecorator.this);
                modelDTOValueWidgets.remove(listItem);
            }            
        });        
        
        private MultiplicityItemWidgetDecorator(Widget listItem){
            itemPanel = new HorizontalBlockFlowPanel();
            this.listItem = listItem;
            initWidget(itemPanel);
            if (listItem instanceof MultiplicityComposite){
                ((MultiplicityComposite)listItem).redraw();
            }
            itemPanel.add(listItem);
            itemPanel.add(removeButton);
        }
        
    }
    
    /**
     * @see com.google.gwt.user.client.ui.HasValue#setValue(java.lang.Object)
     */
    @Override
    public void setValue(ModelDTOValue modelDTOValue) {
        assert(modelDTOValue instanceof ModelDTOValue.ListType);
        this.modelDTOList = (ModelDTOValue.ListType)modelDTOValue;        
        
        redraw();
    }


    /**
     * @see com.google.gwt.user.client.ui.HasValue#setValue(java.lang.Object, boolean)
     */
    @Override
    public void setValue(ModelDTOValue value, boolean fireEvents) {
        setValue(value);
    }
    

    /**
     * @see com.google.gwt.user.client.ui.HasValue#getValue()
     */
    @Override
    public ModelDTOValue getValue(){
        return modelDTOList;
    }

    /** 
     * @see org.kuali.student.common.ui.client.configurable.mvc.HasModelDTOValue#updateModelDTO(org.kuali.student.common.ui.client.mvc.dto.ModelDTOValue)
     */
    @Override
    public void updateModelDTOValue() {
        for (int i=0; i < modelDTOValueWidgets.size(); i++){
            modelDTOValueWidgets.get(i).updateModelDTOValue();
        }
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
        modelDTOList.get().add(((HasModelDTOValue)newItemWidget).getValue());        
        itemsPanel.add(listItem);
        if (newItemWidget instanceof MultiplicityComposite){
            ((MultiplicityComposite)newItemWidget).redraw();
        }

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
        ((HasModelDTOValue)newItemWidget).setValue(modelDTOValue);
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
            
            initWidget(mainPanel);
            
            mainPanel.addStyleName("KS-Multiplicity-Composite");
            //itemsPanel = new VerticalPanel();

            
            KSButton addItemButton = new KSButton("Add Item", new ClickHandler(){
                public void onClick(ClickEvent event) {
                    addItem();
                }            
            });                
    
            itemsPanel.add(addItemButton);
            mainPanel.add(itemsPanel);
            mainPanel.add(addItemButton);
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
     * @see com.google.gwt.event.logical.shared.HasValueChangeHandlers#addValueChangeHandler(com.google.gwt.event.logical.shared.ValueChangeHandler)
     */
    @Override
    public HandlerRegistration addValueChangeHandler(ValueChangeHandler<ModelDTOValue> handler) {        
        // TODO: Add value change handlers
        return null;
    }
 
    /**
     * This must return a widget that implements the HasModelDTOValue interface. This method
     * will be used when user clicks the "Add" button to add a new item to the list of items.
     * 
     * @return
     */
    public abstract Widget createItem();
    
    
}
