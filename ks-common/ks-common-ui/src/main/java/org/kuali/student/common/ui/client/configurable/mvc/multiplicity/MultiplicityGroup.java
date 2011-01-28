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
package org.kuali.student.common.ui.client.configurable.mvc.multiplicity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.kuali.student.common.ui.client.configurable.mvc.FieldDescriptor;
import org.kuali.student.common.ui.client.configurable.mvc.binding.ModelWidgetBinding;
import org.kuali.student.common.ui.client.configurable.mvc.sections.BaseSection;
import org.kuali.student.common.ui.client.configurable.mvc.sections.GroupSection;
import org.kuali.student.common.ui.client.configurable.mvc.sections.MultiplicitySection;
import org.kuali.student.common.ui.client.configurable.mvc.sections.Section;
import org.kuali.student.common.ui.client.configurable.mvc.sections.SwapSection;
import org.kuali.student.common.ui.client.configurable.mvc.sections.VerticalSection;
import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.common.ui.client.mvc.DataModel;
import org.kuali.student.common.ui.client.widgets.KSButton;
import org.kuali.student.common.ui.client.widgets.KSButtonAbstract.ButtonStyle;
import org.kuali.student.common.ui.client.widgets.ListOfStringWidget;
import org.kuali.student.common.ui.client.widgets.list.KSRadioButtonList;
import org.kuali.student.common.ui.client.widgets.list.KSSelectItemWidgetAbstract;
import org.kuali.student.common.ui.client.widgets.list.ListItems;
import org.kuali.student.common.ui.client.widgets.list.SelectionChangeEvent;
import org.kuali.student.common.ui.client.widgets.list.SelectionChangeHandler;
import org.kuali.student.common.ui.client.widgets.list.impl.KSRadioButtonListImpl;
import org.kuali.student.common.ui.client.widgets.list.impl.SimpleListItems;
import org.kuali.student.common.ui.client.widgets.search.KSPicker;
import org.kuali.student.core.assembly.data.Data;
import org.kuali.student.core.assembly.data.MetadataInterrogator;
import org.kuali.student.core.assembly.data.QueryPath;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;


public class MultiplicityGroup extends Composite {

    private MultiplicityConfiguration config;

    private List<MultiplicityGroupItem> items = new ArrayList<MultiplicityGroupItem>();
    private List<MultiplicityGroupItem> removed = new ArrayList<MultiplicityGroupItem>();

    private FlowPanel mainPanel = new FlowPanel();
    private FlowPanel itemsPanel = new FlowPanel();
    private boolean loaded = false;
    private int itemCount = 0;
    private String parentPath;
    private boolean isDirty = false;

    private Map<SwapCompositeCondition, List<SwapCompositeConditionFieldConfig>> swappableFieldsDefinition;
    private List<String> deletionParentKeys;

    public MultiplicityGroup() {
    }

    public MultiplicityGroup(MultiplicityConfiguration config,
            Map<SwapCompositeCondition, List<SwapCompositeConditionFieldConfig>> swappableFieldsDefinition,
            List<String> deletionParentKeys) {
        this.config = config;
        initWidget(mainPanel);
        this.swappableFieldsDefinition = swappableFieldsDefinition;
        this.deletionParentKeys = deletionParentKeys;
    }


    /**
     * Creates an instance of a MultiplicityGroup based on the options in the MultiplicityConfiguration
     *
     * A MultiplicityGroup uses GroupSection to display data in a variable grid.  May be multiple rows and multiple fields per row based on
     * the defs in the MultiplicityConfiguration.
     *
     * May contain one or more MultiplicityItemSections based on user action, dictionary defs or data found in the model.
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
        	Integer minOccurs = MetadataInterrogator.getLargestMinOccurs(config.getMetaData());
        	
        	if (minOccurs != null) {
	            for (int i=0; i < minOccurs; i++){
	            	createItem();
	            }
        	}
        	else if(config.getDefaultItemsCreated() != 0){
        		for (int i=0; i < config.getDefaultItemsCreated(); i++){
	            	createItem();
	            }
        	}

        }
    }

    private Widget generateAddWidget() {
    	KSButton addWidget;
    	if(config.getStyleType() == MultiplicityConfiguration.StyleType.TOP_LEVEL_GROUP){
    		addWidget = new KSButton(config.getAddItemLabel(), ButtonStyle.FORM_LARGE);
    	}
    	else{
    		addWidget = new KSButton(config.getAddItemLabel(), ButtonStyle.FORM_SMALL);
    	}
        addWidget.addClickHandler(new ClickHandler(){
            public void onClick(ClickEvent event) {
                createItem();
            }
        });
        return addWidget;
    }

    /**
     * This adds an empty item to the multiplicity group
     *
     * @return
     */
	public MultiplicityGroupItem createItem(){

		itemCount++;

		MultiplicityGroupItem item = new MultiplicityGroupItem(config.getItemLabel() + " " + itemCount, config.getStyleType(), config.isUpdateable());

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
        isDirty = true;

        return item;
	}

	public FlowPanel getItemsPanel() {
	    return itemsPanel;
	}

    private Widget createWidget() {

		GroupSection section = new GroupSection();
        final SwappableFieldsHelper helper = new SwappableFieldsHelper();
        // helperFieldKeys maps the paths with asterisks into absolute paths
        final Map<String, String> helperFieldKeys = new HashMap<String, String>();
        SwapSection swapSection = null;

		//TODO might want fields and nested multiplicities at the same time. Remove if/else

		if (config.getNestedConfig() != null) {
            MultiplicitySection ms = new MultiplicitySection(config.getNestedConfig().copy());
			String p = config.getNestedConfig().getParentFd().getFieldKey().replace('*', Character.forDigit(itemCount-1, 10));
    		ms.setParentPath(p);
            section.addSection(ms);
		}
		else {
			for (Integer row  : config.getFields().keySet()) {
				List<MultiplicityFieldConfiguration> fieldConfigs = config.getFields().get(row);
				for (MultiplicityFieldConfiguration fieldConfig : fieldConfigs) {
                    //TODO  Should copy widgets/bindings too?
                    FieldDescriptor newfd = null;
                    Widget fieldWidget = null;
                    if (fieldConfig.getFieldWidgetInitializer() != null) {
                        fieldWidget = fieldConfig.getFieldWidgetInitializer().getNewWidget();
                        ModelWidgetBinding mwb = fieldConfig.getFieldWidgetInitializer()
                            .getModelWidgetBindingInstance();
                        newfd = new FieldDescriptor(
                                translatePath(fieldConfig.getFieldPath()),
                                fieldConfig.getMessageKeyInfo(),
                                fieldConfig.getMetadata(),
                                fieldWidget);
                        newfd.setWidgetBinding(mwb);
                    } else {
                        newfd = new FieldDescriptor(
                                translatePath(fieldConfig.getFieldPath()),
                                fieldConfig.getMessageKeyInfo(),
                                fieldConfig.getMetadata());
                        fieldWidget = newfd.getFieldWidget();
                    }
                    newfd.getFieldElement().setRequired(fieldConfig.isRequired());
                    section.addField(newfd);
                    helperFieldKeys.put(fieldConfig.getFieldPath(), newfd.getFieldKey());

                    // add handlers to all select field that can be selected and triggers
                    // selection change event to notify the swap section to switch section.
                    if (fieldWidget instanceof KSSelectItemWidgetAbstract) {
                        ((KSSelectItemWidgetAbstract)fieldWidget).addSelectionChangeHandler(new SelectionChangeHandler() {
                            @Override
                            public void onSelectionChange(SelectionChangeEvent event) {
                                helper.updateFieldDisplay();
                            }
                        });
                    } else if (fieldWidget instanceof KSPicker) {
                        Widget inputWidget = ((KSPicker)fieldWidget).getInputWidget();
                        if (inputWidget instanceof KSSelectItemWidgetAbstract) {
                            ((KSSelectItemWidgetAbstract)inputWidget).addSelectionChangeHandler(new SelectionChangeHandler() {
                                @Override
                                public void onSelectionChange(SelectionChangeEvent event) {
                                    helper.updateFieldDisplay();
                                }
                            });
                        }
                    }
				}
				section.nextLine();
			}
            if (swappableFieldsDefinition != null) {
                ConditionChoices conditionChoices = new ConditionChoices(
                        new ArrayList<SwapCompositeCondition>(
                                swappableFieldsDefinition.keySet()));
                List<String> newDeletionParentKeys = null;
                swapSection = new SwapSection(conditionChoices);
                if (deletionParentKeys != null) {
                    for (String deletionParentKey : deletionParentKeys) {
                        newDeletionParentKeys = (newDeletionParentKeys == null)?
                                new ArrayList<String>() : newDeletionParentKeys;
                        newDeletionParentKeys.add(translatePath(deletionParentKey));
                    }
                }
                swapSection.setDeletionParentKey(newDeletionParentKeys);
                helper.setConditionChoices(conditionChoices);
                // go through swappableFieldsDefinition and create and add the fields
                // into conditionSection
                for (Entry<SwapCompositeCondition, List<SwapCompositeConditionFieldConfig>> entry : swappableFieldsDefinition.entrySet()) {
                    SwapCompositeCondition condition = entry.getKey();
                    List<SwapCompositeConditionFieldConfig> fieldConfigs = entry.getValue();
                    Section conditionSection = new VerticalSection();
                    if (fieldConfigs != null) {
                        for (SwapCompositeConditionFieldConfig conditionFieldConfig : fieldConfigs) {
                            if (conditionFieldConfig.useMultiplicity()) {
                                MultiplicityConfiguration newSubMultiplicityConfig =
                                    configSwappableMultiplicitySection(
                                            conditionFieldConfig.getMultiplicityConfiguration());
                                MultiplicitySection subMultiplicitySection =
                                    new MultiplicitySection(newSubMultiplicityConfig);
                                conditionSection.addSection(subMultiplicitySection);
                            } else {
                                MultiplicityFieldConfiguration fieldConfig =
                                    conditionFieldConfig.getMultiplicityFieldConfiguration();
                                FieldDescriptor concreteFieldDescriptor = new FieldDescriptor(
                                        translatePath(fieldConfig.getFieldPath()),
                                        fieldConfig.getMessageKeyInfo(),
                                        fieldConfig.getMetadata());
                                concreteFieldDescriptor.getFieldElement().setRequired(fieldConfig.isRequired());
                                if (fieldConfig.getFieldWidgetInitializer() != null) {
                                    Widget fieldWidget = fieldConfig.getFieldWidgetInitializer().getNewWidget();
                                    ModelWidgetBinding mwb = fieldConfig.getFieldWidgetInitializer()
                                        .getModelWidgetBindingInstance();
                                    concreteFieldDescriptor.setFieldWidget(fieldWidget);
                                    if(fieldWidget instanceof ListOfStringWidget){
                                    	((ListOfStringWidget)fieldWidget).setFd(concreteFieldDescriptor);
                                    }

                                    concreteFieldDescriptor.setWidgetBinding(mwb);
                                }
                                conditionSection.addField(concreteFieldDescriptor);
                                helperFieldKeys.put(fieldConfig.getFieldPath(), translatePath(fieldConfig.getFieldPath()));
                            }
                        }
                    }
                    swapSection.addSection(conditionSection, condition.getConditionId());
                }
                section.addWidget(conditionChoices);
                section.addSection(swapSection);
                helper.setSwapSection(swapSection);
            }

            helper.setParentSection(section);
            helper.setSwappableFieldsDefinition(swappableFieldsDefinition);
            helper.setHelperFieldKeys(helperFieldKeys);
        }
		return section;
	}

    public MultiplicityConfiguration configSwappableMultiplicitySection(MultiplicityConfiguration origConfig) {
        MultiplicityConfiguration newSubMultiplicityConfig =
            origConfig.copy();

        FieldDescriptor parentFd = origConfig.getParentFd();
        FieldDescriptor subMultParentConfig =
            new FieldDescriptor(
                    translatePath(parentFd.getFieldKey()),
                    parentFd.getMessageKey(),
                    parentFd.getMetadata());
        subMultParentConfig.getFieldElement().setRequired(parentFd.getFieldElement().isRequired());
        newSubMultiplicityConfig.setParent(subMultParentConfig);
        Map<Integer, List<MultiplicityFieldConfiguration>> fieldConfigsMap = newSubMultiplicityConfig.getFields();
        Map<Integer, List<MultiplicityFieldConfiguration>> newFieldsMap = new HashMap<Integer, List<MultiplicityFieldConfiguration>>();
        int lineCounter = 0;
        if (fieldConfigsMap != null) {
            for (List<MultiplicityFieldConfiguration> fieldConfigsInLine : fieldConfigsMap.values()) {
                List<MultiplicityFieldConfiguration> newFieldConfigs = new ArrayList<MultiplicityFieldConfiguration>();
                for (MultiplicityFieldConfiguration fieldConfig : fieldConfigsInLine) {
                    String configParentKey = (parentFd.getFieldKey() == null)? "" : parentFd.getFieldKey();
                    String configFieldKey = (fieldConfig.getFieldPath() == null)? "" : fieldConfig.getFieldPath();
                    String trimmedFieldKey = null;
                    MultiplicityFieldConfiguration newFieldConfig = null;
                    trimmedFieldKey = configFieldKey.substring(configParentKey.length());

                    QueryPath fieldPath = QueryPath.concat(subMultParentConfig.getFieldKey(),
                            trimmedFieldKey);;
                    newFieldConfig = new MultiplicityFieldConfiguration(
                            fieldPath.toString(), fieldConfig.getMessageKeyInfo(), fieldConfig.getMetadata(),
                            fieldConfig.getFieldWidgetInitializer());
                    newFieldConfig.setRequired(fieldConfig.isRequired());
                    newFieldConfigs.add(newFieldConfig);

                }
                newFieldsMap.put(Integer.valueOf(lineCounter), newFieldConfigs);
                lineCounter++;
            }
        }
        newSubMultiplicityConfig.setFields(newFieldsMap);

        return newSubMultiplicityConfig;
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

    public void incrementItemKey(){
		itemCount++;
	}

    /**
	 * This returns the index key for the model for the item currently being added by addItem
	 * This is useful, if you need to refer to the index in the createItem method
	 * @return
	 */
	public int getAddItemKey(){
		return itemCount-1;
	}


    public List<MultiplicityGroupItem> getItems() {
        return items;
    }

    public List<MultiplicityGroupItem> getRemovedItems() {
        return removed;
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

    public void resetDirtyFlags() {
    	isDirty = false;
		for (MultiplicityGroupItem item:items){
			item.resetDirtyFlags();
			Widget itemWidget = item.getItemWidget();
			if (itemWidget instanceof BaseSection){
				((BaseSection)itemWidget).resetDirtyFlags();
			}
		}
    }

	public boolean isDirty(){
		for (MultiplicityGroupItem item:items){
			if (item.isDirty()){
				isDirty = true;
				break;
			} else {
				Widget itemWidget = item.getItemWidget();
				if (itemWidget instanceof BaseSection && ((BaseSection)itemWidget).isDirty()){
					isDirty = true;
					break;
				}
			}
		}
		return isDirty;
	}

	public void setIsDirty(boolean dirty){
		isDirty = dirty;
	}

    public class ConditionChoices extends KSRadioButtonList{
        private KSRadioButtonListImpl selectItemWidget = GWT.create(KSRadioButtonListImpl.class);
        public ConditionChoices(List<SwapCompositeCondition> conditions){
            SimpleListItems choices = new SimpleListItems();
            if (conditions != null) {
                for (SwapCompositeCondition condition : conditions) {
                    choices.addItem(condition.getConditionId(), condition.getConditionId());
                }
            }
            selectItemWidget.setListItems(choices);
        }

        /**
         * @see org.kuali.student.common.ui.client.widgets.list.KSSelectItemWidgetAbstract#deSelectItem(java.lang.String)
         */
        public void deSelectItem(String id) {
            selectItemWidget.deSelectItem(id);
        }

        /**
         * @see org.kuali.student.common.ui.client.widgets.list.KSSelectItemWidgetAbstract#getSelectedItems()
         */
        public List<String> getSelectedItems() {
            return selectItemWidget.getSelectedItems();
        }

        /**
         * @see org.kuali.student.common.ui.client.widgets.list.KSSelectItemWidgetAbstract#selectItem(java.lang.String)
         */
        public void selectItem(String id) {
            selectItemWidget.selectItem(id);
        }

        public void setListItems(ListItems listItems) {
            selectItemWidget.setListItems(listItems);
        }

        /**
         * Use to set number of columns to use when displaying list
         *
         */
        public void setColumnSize(int cols){
            selectItemWidget.setColumnSize(cols);
        }

        public void setMultipleSelect(boolean isMultipleSelect) {}

        /**
         * This overridden method is not used
         *
         * @see org.kuali.student.common.ui.client.widgets.list.KSSelectItemWidgetAbstract#onLoad()
         */
        @Override
        public void onLoad() {}

        public HandlerRegistration addSelectionChangeHandler(SelectionChangeHandler handler) {
            return selectItemWidget.addSelectionChangeHandler(handler);
        }

        public ListItems getListItems() {
            return selectItemWidget.getListItems();
        }

        public String getName() {
            return selectItemWidget.getName();
        }

        public void setName(String name) {
            selectItemWidget.setName(name);
        }

        @Override
        public void setEnabled(boolean b) {
            selectItemWidget.setEnabled(b);
        }

        @Override
        public boolean isEnabled() {
            return selectItemWidget.isEnabled();
        }

        @Override
        public boolean isMultipleSelect() {
            return selectItemWidget.isMultipleSelect();
        }

        @Override
        public void redraw() {
            selectItemWidget.redraw();
        }

        @Override
        public void clear() {
            selectItemWidget.clear();
        }

        @Override
        public HandlerRegistration addBlurHandler(BlurHandler handler) {
            return selectItemWidget.addBlurHandler(handler);
        }

        @Override
        public HandlerRegistration addFocusHandler(FocusHandler handler) {
            return selectItemWidget.addFocusHandler(handler);
        }

        public void addWidgetReadyCallback(Callback<Widget> callback) {
            selectItemWidget.addWidgetReadyCallback(callback);
        }

        public boolean isInitialized() {
            return selectItemWidget.isInitialized();
        }

        public void setInitialized(boolean initialized) {
            selectItemWidget.setInitialized(initialized);
        }

        /**
         * By default if the list items used by the checkbox has multiple attributes, the checkbox
         * generated will display all attributes as columns. Set this property to true if this
         * behavior is not desired.
         *
         * @param ignoreMultiple
         */
        public void setIgnoreMultipleAttributes(boolean ignoreMultiple){
            selectItemWidget.setIgnoreMultipleAttributes(ignoreMultiple);
        }
    }

    public class SwappableFieldsHelper {

        private GroupSection parentSection;
        private Map<SwapCompositeCondition, List<SwapCompositeConditionFieldConfig>> swappableFieldsDefinition =
            new HashMap<SwapCompositeCondition, List<SwapCompositeConditionFieldConfig>>();
        private Map<String, String> helperFieldKeys;
        private ConditionChoices conditionChoices;
        private SwapSection swapSection;
        public void updateFieldDisplay() {
            Set<Entry<SwapCompositeCondition, List<SwapCompositeConditionFieldConfig>>> entries =
                (swappableFieldsDefinition == null)? null : swappableFieldsDefinition.entrySet();
            if (parentSection == null) {
                throw new RuntimeException("parentSection cannot be null");
            }
            if (entries != null) {
                for (Entry<SwapCompositeCondition, List<SwapCompositeConditionFieldConfig>> entry : entries) {
                    SwapCompositeCondition condition = entry.getKey();
                    if (condition.evaluate(parentSection, helperFieldKeys)) {
                        // clear values in the widgets
                        List<Section> deletedSections = swapSection.getDeletedSections();
                        List<FieldDescriptor> fields = null;
                        if (deletedSections != null) {
                            for (Section deletedSection : deletedSections) {
                                fields = (fields == null)? new ArrayList() : fields;
                                fields.addAll(deletedSection.getFields());
                            }
                        }
                        if (fields != null) {
                            for (FieldDescriptor field : fields) {
                                Widget fieldWidget = field.getFieldWidget();
                                DataModel dataModel = new DataModel();
                                dataModel.setRoot(new Data());
                                ((ModelWidgetBinding)field.getModelWidgetBinding())
                                .setWidgetValue(fieldWidget,
                                        dataModel, field.getFieldKey());
                            }
                        }
                        // trigger SectionChangeEvent to notify the selection widget
                        // that the selected condition has changed.
                        SelectionChangeEvent selectionChangeEvent =
                            new SelectionChangeEvent(conditionChoices);
                        selectionChangeEvent.setUserInitiated(true);
                        conditionChoices.selectItem(condition.getConditionId());
                        conditionChoices.fireEvent(selectionChangeEvent);
                    }
                }
            }
        }
        public GroupSection getParentSection() {
            return parentSection;
        }
        public void setParentSection(GroupSection parentSection) {
            this.parentSection = parentSection;
        }
        public Map<SwapCompositeCondition, List<SwapCompositeConditionFieldConfig>> getSwappableFieldsDefinition() {
            return swappableFieldsDefinition;
        }
        public void setSwappableFieldsDefinition(Map<SwapCompositeCondition, List<SwapCompositeConditionFieldConfig>> swappableFieldsDefinition) {
            this.swappableFieldsDefinition = swappableFieldsDefinition;
        }
        public Map<String, String> getHelperFieldKeys() {
            return helperFieldKeys;
        }
        public void setHelperFieldKeys(Map<String, String> helperFieldKeys) {
            this.helperFieldKeys = helperFieldKeys;
        }
        public ConditionChoices getConditionChoices() {
            return conditionChoices;
        }
        public void setConditionChoices(ConditionChoices conditionChoices) {
            this.conditionChoices = conditionChoices;
        }
        public SwapSection getSwapSection() {
            return swapSection;
        }
        public void setSwapSection(SwapSection swapSection) {
            this.swapSection = swapSection;
        }
    }

}
