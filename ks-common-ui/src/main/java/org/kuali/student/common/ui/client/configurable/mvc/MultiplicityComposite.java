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
 * This is a description of what this class does - Will Gomes don't forget to fill this in. 
 * 
 * @author Kuali Student Team
 *
 */
public abstract class MultiplicityComposite extends Composite implements HasModelDTOValue{
        
    private DockPanel dockPanel;
    private VerticalPanel itemsPanel;
    private ModelDTOValue.ListType modelDTOList;     

    /**
     * This simply decorates a list item widget to add styling and 
     *   
     * @author Kuali Student Team
     *
     */
    private class ListItemWidgetDecorator extends Composite{
        VerticalPanel vPanel = new VerticalPanel();
        Widget listItem;
        
        KSButton removeButton = new KSButton("-", new ClickHandler(){
            public void onClick(ClickEvent event) {
                ModelDTOValue listItemValue = ((HasModelDTOValue)listItem).getModelDTOValue(); 
                modelDTOList.get().remove(listItemValue);
                dockPanel.remove(ListItemWidgetDecorator.this);
            }            
        });        
        
        private ListItemWidgetDecorator(Widget listItem){
            this.listItem = listItem;
            initWidget(vPanel);
            if (listItem instanceof MultiplicityComposite){
                ((MultiplicityComposite)listItem).init();
            }
            vPanel.add(listItem);
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
        
        Widget newItemWidget = createListItemWidget();
        ListItemWidgetDecorator listItem = new ListItemWidgetDecorator(newItemWidget);
        modelDTOList.get().add(((HasModelDTOValue)newItemWidget).getModelDTOValue());
        itemsPanel.add(listItem);
    }    
    
    private void addItem(ModelDTOValue modelDTOValue){
        Widget newItemWidget = createListItemWidget();
        ListItemWidgetDecorator listItem = new ListItemWidgetDecorator(newItemWidget);
        ((HasModelDTOValue)newItemWidget).setModelDTOValue(modelDTOValue);
        itemsPanel.add(listItem);        
    }
    
    public void init(){
        dockPanel = new DockPanel();
        itemsPanel = new VerticalPanel();
        initWidget(dockPanel);
        
        KSButton addItemButton = new KSButton("Add Item", new ClickHandler(){
            public void onClick(ClickEvent event) {
                addItem();
            }            
        });                

        dockPanel.add(addItemButton, DockPanel.NORTH);
        dockPanel.add(itemsPanel, DockPanel.SOUTH);
        
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
    public abstract Widget createListItemWidget();
    
    
}
