/**
 * Copyright 2010 The Kuali Foundation Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 * <p/>
 * http://www.osedu.org/licenses/ECL-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 * <p/>
 */
package org.kuali.student.common.ui.client.configurable.mvc.multiplicity.wip;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;
import org.kuali.student.common.ui.client.configurable.mvc.FieldDescriptor;
import org.kuali.student.common.ui.client.configurable.mvc.sections.GroupSection;
import org.kuali.student.common.ui.client.configurable.mvc.sections.wip.MultiplicitySection;
import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.common.ui.client.widgets.KSButton;
import org.kuali.student.common.ui.client.widgets.KSButtonAbstract.ButtonStyle;
import org.kuali.student.common.ui.client.widgets.field.layout.element.MessageKeyInfo;
import org.kuali.student.core.assembly.data.QueryPath;

import java.util.ArrayList;
import java.util.List;


public class MultiplicityGroup extends Composite {

    private MultiplicityConfiguration config;

    private List<MultiplicityGroupItem> items = new ArrayList<MultiplicityGroupItem>();
    private List<MultiplicityGroupItem> removed = new ArrayList<MultiplicityGroupItem>();

    private FlowPanel mainPanel = new FlowPanel();
    private FlowPanel itemsPanel = new FlowPanel();
    private boolean loaded = false;
    private int itemCount = 0;
    private String parentPath;

    public MultiplicityGroup() {
    }

    /**
     *   !!!! WORK IN PROGRESS !!!!
     *   
     * Creates an instance of a MultiplicityGroup based on the options in the MultiplicityConfiguration
     *
     * A MultiplicityGroup uses GroupSection to display data in a variable grid.  May be multiple rows and multiple fields per row based on
     * the defs in the MultiplicityConfiguration
     *
     * @param config
     */
    public MultiplicityGroup(MultiplicityConfiguration config){
        this.config = config;
        initWidget(mainPanel);
    }

    protected Callback<MultiplicityGroupItem> removeCallback = new Callback<MultiplicityGroupItem>(){

        public void exec(MultiplicityGroupItem itemToRemove) {
            itemToRemove.setDeleted(true);
            removed.add(itemToRemove);
            itemsPanel.remove(itemToRemove);
        }
    };
    
    /**
     * This adds an empty item to the multiplicity group 
     *
     * @return
     */
	public MultiplicityGroupItem addGroupItem(){
		itemCount++;

		MultiplicityGroupItem item = new MultiplicityGroupItem(config.getStyleType());
        item.setItemLabel(config.getItemLabel() + " " + itemCount);
        item.setUpdateable(config.isUpdateable());
        
        Widget itemWidget = createWidget();

	    if (item != null){
		    item.setItemKey(new Integer(itemCount -1));
		    item.setItemWidget(itemWidget);
		    item.setRemoveCallback(removeCallback);
	    } else if (itemWidget instanceof MultiplicityGroupItem){
	    	item = (MultiplicityGroupItem)itemWidget;
	    	item.setItemKey(new Integer(itemCount -1));
	    }
	    items.add(item);
	    item.redraw();
	    itemsPanel.add(item);

	    return item;
	}

    public String translatePath(String path) {
        String fieldPath;
        if (parentPath != null) {
            QueryPath parent = QueryPath.concat(parentPath);
            int i = parent.size();

            QueryPath subPath = QueryPath.concat(path);
            String itemPath =  subPath.subPath(i, subPath.size()).toString();

            QueryPath qp = QueryPath.concat(parentPath, itemPath);
            fieldPath = qp.toString();
        }
        else {
            fieldPath = path;
        }

        fieldPath = fieldPath.replace('*', Character.forDigit(itemCount-1, 10));
        return fieldPath;
    }

    /**
	 * This returns the index key for the model for the item currently being added by addItem
	 * This is useful, if you need to refer to the index in the createItem method
	 * @return
	 */
	public int getAddItemKey(){
		return itemCount-1;
	}

	public void incrementItemKey(){
		itemCount++;
	}

    public void onLoad() {
        if (!loaded) {
            mainPanel.add(itemsPanel);

            if(config.isUpdateable()){
                Widget addWidget = generateAddWidget();
                mainPanel.add(addWidget);
            }

            loaded = true;
        }

        if (!loaded || itemCount == 0){
            for (int i=0; i < config.getMinEmptyItems(); i++){
            	addGroupItem();
            }
        }
    }

    public List<MultiplicityGroupItem> getItems() {
        return items;
    }

    public List<MultiplicityGroupItem> getRemovedItems() {
        return removed;
    }

    public void clear(){
        itemsPanel.clear();
        items.clear();
        removed.clear();
        itemCount = 0;
    }

    public void redraw(){
        for (MultiplicityGroupItem item:items){
            item.redraw();
        }
    }

    private Widget generateAddWidget() {
    	KSButton addWidget;
    	if(config.getStyleType() == MultiplicityConfiguration.StyleType.TOP_LEVEL){
    		addWidget = new KSButton(config.getAddItemLabel(), ButtonStyle.FORM_LARGE);
    	}
    	else{
    		addWidget = new KSButton(config.getAddItemLabel(), ButtonStyle.FORM_SMALL);
    	}
        addWidget.addClickHandler(new ClickHandler(){
            public void onClick(ClickEvent event) {
                addGroupItem();
            }            
        });
        return addWidget;
    }

	private Widget createWidget() {

		GroupSection section = new GroupSection();

		if (config.getNestedConfig() != null) {
            MultiplicitySection ms = new MultiplicitySection(config.getNestedConfig().copy());
			String p = config.getNestedConfig().getParentFd().getFieldKey().replace('*', Character.forDigit(itemCount-1, 10));
    		ms.setParentPath(p);
            section.addSection(ms);
		}
		else {
			for (Integer row  : config.getFields().keySet()) {
				List<FieldDescriptor> fields = config.getFields().get(row);
				for (FieldDescriptor fd : fields) {
                    //TODO  Should copy widgets/bindings too?
					FieldDescriptor newfd = new FieldDescriptor(translatePath(fd.getFieldKey()), new MessageKeyInfo(fd.getFieldLabel()), fd.getMetadata());
					section.addField(newfd);
				}
				section.nextLine();
			}
		}
		return section;
	}

	public int getItemCount() {
		return itemCount;
	}
	
	public void setItemCount(int itemCount) {
		this.itemCount = itemCount;
	}

	public String getParentPath() {
		return parentPath;
	}

    /**
     * Allows the parentpath for this instance to be set, e.g. course/formats/0/activities
     * 
     * @param parentPath
     */
    public void setParentPath(String parentPath) {
		this.parentPath = parentPath;
	}

	public MultiplicityConfiguration getConfig() {
		return config;
	}

	public void setConfig(MultiplicityConfiguration config) {
		this.config = config;
	}   
	
	
}
