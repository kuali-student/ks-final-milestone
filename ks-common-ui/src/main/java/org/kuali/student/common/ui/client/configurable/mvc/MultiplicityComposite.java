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
package org.kuali.student.common.ui.client.configurable.mvc;

import java.util.ArrayList;
import java.util.List;

import org.kuali.student.common.ui.client.mvc.dto.ModelDTOValue;
import org.kuali.student.common.ui.client.widgets.KSButton;
import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.common.ui.client.widgets.layout.VerticalFlowPanel;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
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

    protected FlowPanel mainPanel = new FlowPanel();
    protected FlowPanel itemsPanel = new FlowPanel();
    protected ModelDTOValue.ListType modelDTOList =  new ModelDTOValue.ListType();
    protected List<HasModelDTOValue> modelDTOValueWidgets;
    protected boolean loaded = false;
    protected String addItemLabel;
    private String itemLabel = "Item ";
    private int itemCount = 0;
    protected boolean useDeleteLabel = false;

    private boolean updateable = true;


    //Initialization block
    {
        modelDTOList.set(new ArrayList<ModelDTOValue>());
    };
    
    public MultiplicityComposite() {
    	this(false);
    }

    public MultiplicityComposite(boolean useDeleteLabel) {
    	this.useDeleteLabel = useDeleteLabel;
	}

	/**
     * This simply decorates a list item widget to add styling and a remove button 
     *   
     * @author Kuali Student Team
     *
     */
    private class MultiplicityItemWidgetDecorator extends Composite{
        VerticalFlowPanel itemPanel;
        Widget listItem;

        private MultiplicityItemWidgetDecorator(Widget listItem){
            itemPanel = new VerticalFlowPanel();
            this.listItem = listItem;
            initWidget(itemPanel);
            if (listItem instanceof MultiplicityComposite){
                ((MultiplicityComposite)listItem).redraw();
            }
            itemCount++;
            itemPanel.addStyleName("KS-Multiplicity-Item");

            HorizontalPanel headerPanel = new HorizontalPanel();
            headerPanel.addStyleName("KS-Multiplicity-Item-Header");
            KSLabel headerLabel = new KSLabel(itemLabel + " " + itemCount);
            headerPanel.add(headerLabel);
            if (updateable) {
                headerPanel.add(generateRemoveWidget());
            }

            itemPanel.add(headerPanel);
            itemPanel.add(listItem);
        }

        private Widget generateRemoveWidget() {
        	ClickHandler ch = new ClickHandler(){
                public void onClick(ClickEvent event) {
                    ModelDTOValue listItemValue = ((HasModelDTOValue)listItem).getValue(); 
                    modelDTOList.get().remove(listItemValue);
                    itemsPanel.remove(MultiplicityItemWidgetDecorator.this);
                    modelDTOValueWidgets.remove(listItem);
                }
        	};

        	Widget returnWidget;
        	if (useDeleteLabel) {
        		Label deleteLabel = new Label("Delete");
        		deleteLabel.addStyleName("KS-Multiplicity-Labels");
        		deleteLabel.addClickHandler(ch);
        		returnWidget = deleteLabel;
        	}
        	else {
				returnWidget = new KSButton("-", ch); 
        	}
        	return returnWidget;
        }        
    }



    /**
     * @see com.google.gwt.user.client.ui.HasValue#setValue(java.lang.Object)
     */
    @Override
    public void setValue(ModelDTOValue modelDTOValue) {
        if(modelDTOValue != null){
            assert(modelDTOValue instanceof ModelDTOValue.ListType);
            this.modelDTOList = (ModelDTOValue.ListType)modelDTOValue;
            redraw();
        }
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
    protected void addItem(){            
        Widget newItemWidget = createItem();
        MultiplicityItemWidgetDecorator listItem = new MultiplicityItemWidgetDecorator(newItemWidget);

        itemsPanel.add(listItem);
        if (newItemWidget instanceof MultiplicityComposite){
            ((MultiplicityComposite)newItemWidget).redraw();
        }

        modelDTOList.get().add(((HasModelDTOValue)newItemWidget).getValue());
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
        GWT.log("ModelDTOValueActual = " + modelDTOValue.toString(), null);
        GWT.log("ModelDTOValue in Section = " + ((HasModelDTOValue)newItemWidget).getValue().toString(), null);
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
            mainPanel.add(itemsPanel);
            if (updateable) {
                mainPanel.add(generateAddWidget());

            }
            loaded = true;
        } else {
            clear();
        }

        //Update the composite with values from ModelDTO
        if (modelDTOList != null){
            List<ModelDTOValue> modelDTOValueList = modelDTOList.get();
            GWT.log("ModelDTOList = " + modelDTOList.toString(), null);
            GWT.log("ModelDTOValueList length = " + modelDTOValueList.size(), null);
            for (int i=0; i < modelDTOValueList.size(); i++){
                addItem(modelDTOValueList.get(i));
            }                                               
        }
    }

    protected Widget generateAddWidget() {
        return new KSButton(addItemLabel, new ClickHandler(){
            public void onClick(ClickEvent event) {
                addItem();
            }            
        }); 
    }

    /**
     *
     *  This method will remove all data associated with this mode
     *
     */
    public void clear(){
        itemCount = 0;
        if (itemsPanel != null){itemsPanel.clear();}
        //TODO: Should this do iterate over each widget and call their clear()
        if (modelDTOValueWidgets != null){modelDTOValueWidgets.clear();}        
        //if (modelDTOList != null){modelDTOList.get().clear();}
    }


    /**
     * @see com.google.gwt.event.logical.shared.HasValueChangeHandlers#addValueChangeHandler(com.google.gwt.event.logical.shared.ValueChangeHandler)
     */
    @Override
    public HandlerRegistration addValueChangeHandler(ValueChangeHandler<ModelDTOValue> handler) {        
        // TODO: Add value change handlers
        return null;
    }

    public void setAddItemLabel(String addItemLabel) {
        this.addItemLabel = addItemLabel;
    }


    public void setItemLabel(String itemLabel) {
        this.itemLabel = itemLabel;
    }

    /**
     * This must return a widget that implements the HasModelDTOValue interface. This method
     * will be used when user clicks the "Add" button to add a new item to the list of items.
     * 
     * @return
     */
    public abstract Widget createItem();


    public boolean isUpdateable() {
        return updateable;
    }


    public void setUpdateable(boolean forUpdate) {
        this.updateable = forUpdate;
    }

}
