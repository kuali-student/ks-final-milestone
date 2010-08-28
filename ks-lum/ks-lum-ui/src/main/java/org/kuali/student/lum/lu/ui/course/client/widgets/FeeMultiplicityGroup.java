package org.kuali.student.lum.lu.ui.course.client.widgets;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.kuali.student.common.ui.client.configurable.mvc.FieldDescriptor;
import org.kuali.student.common.ui.client.configurable.mvc.binding.ModelWidgetBinding;
import org.kuali.student.common.ui.client.configurable.mvc.multiplicity.MultiplicityConfiguration;
import org.kuali.student.common.ui.client.configurable.mvc.multiplicity.MultiplicityGroup;
import org.kuali.student.common.ui.client.configurable.mvc.multiplicity.MultiplicityGroupItem;
import org.kuali.student.common.ui.client.configurable.mvc.sections.GroupSection;
import org.kuali.student.common.ui.client.configurable.mvc.sections.MultiplicitySection;
import org.kuali.student.common.ui.client.configurable.mvc.sections.Section;
import org.kuali.student.common.ui.client.configurable.mvc.sections.SwapSection;
import org.kuali.student.common.ui.client.configurable.mvc.sections.VerticalSection;
import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.common.ui.client.mvc.DataModel;
import org.kuali.student.common.ui.client.widgets.field.layout.element.MessageKeyInfo;
import org.kuali.student.common.ui.client.widgets.list.KSCheckBoxList;
import org.kuali.student.common.ui.client.widgets.list.KSRadioButtonList;
import org.kuali.student.common.ui.client.widgets.list.KSSelectItemWidgetAbstract;
import org.kuali.student.common.ui.client.widgets.list.ListItems;
import org.kuali.student.common.ui.client.widgets.list.SelectionChangeEvent;
import org.kuali.student.common.ui.client.widgets.list.SelectionChangeHandler;
import org.kuali.student.common.ui.client.widgets.list.impl.KSCheckBoxListImpl;
import org.kuali.student.common.ui.client.widgets.list.impl.KSRadioButtonListImpl;
import org.kuali.student.common.ui.client.widgets.list.impl.SimpleListItems;
import org.kuali.student.common.ui.client.widgets.search.KSPicker;
import org.kuali.student.core.assembly.data.Data;
import org.kuali.student.core.assembly.data.QueryPath;
import org.kuali.student.lum.lu.ui.tools.client.configuration.ToolsConstants;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Widget;

public class FeeMultiplicityGroup extends MultiplicityGroup {

    private Map<SwapCompositeCondition, List<SwapCompositeConditionFieldConfig>> swappableFieldsDefinition;
    private List<String> deletionParentKeys;
    
    public FeeMultiplicityGroup(MultiplicityConfiguration config, 
            Map<SwapCompositeCondition, List<SwapCompositeConditionFieldConfig>> swappableFieldsDefinition,
            List<String> deletionParentKeys) {
        super(config);
        this.swappableFieldsDefinition = swappableFieldsDefinition;
        this.deletionParentKeys = deletionParentKeys;
    }

    @Override
    public MultiplicityGroupItem createItem() {
        int itemCount = getItemCount();
        MultiplicityConfiguration config = getConfig();
        itemCount++;
        setItemCount(itemCount);
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
        getItems().add(item);
        item.redraw();
        getItemsPanel().add(item);

        return item;
    }

    private Widget createWidget() {

        GroupSection section = new GroupSection();
        MultiplicityConfiguration config = getConfig();
        final SwappableFieldsHelper helper = new SwappableFieldsHelper();
        // helperFieldKeys maps the paths with asterisks into absolute paths
        final Map<String, String> helperFieldKeys = new HashMap<String, String>();
        SwapSection swapSection = null;

        //TODO might want fields and nested multiplicities at the same time. Remove if/else

        if (config.getNestedConfig() != null) {
            MultiplicitySection ms = new MultiplicitySection(config.getNestedConfig().copy());
            String p = config.getNestedConfig().getParentFd().getFieldKey().replace('*', Character.forDigit(getItemCount()-1, 10));
            ms.setParentPath(p);
            section.addSection(ms);
        }
        else {
            for (Integer row  : config.getFields().keySet()) {
                List<FieldDescriptor> fields = config.getFields().get(row);
                for (FieldDescriptor fd : fields) {
                    //TODO  Should copy widgets/bindings too?
                    final FieldDescriptor newfd = new FieldDescriptor(translatePath(fd.getFieldKey()), new MessageKeyInfo(fd.getFieldLabel()), fd.getMetadata());
                    Widget fieldWidget = newfd.getFieldWidget();
                    section.addField(newfd);
                    helperFieldKeys.put(fd.getFieldKey(), newfd.getFieldKey());

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
//                    List<FieldDescriptor> displayFieldsConfigs = fieldConfig.getFieldDescriptorConfig();
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
                                FieldDescriptor fieldDescriptorConfig = conditionFieldConfig.getFieldDescriptorConfig();
                                conditionSection.addField(new FieldDescriptor(
                                        translatePath(fieldDescriptorConfig.getFieldKey()),
                                        fieldDescriptorConfig.getMessageKey(),
                                        fieldDescriptorConfig.getMetadata()));
                                helperFieldKeys.put(fieldDescriptorConfig.getFieldKey(), translatePath(fieldDescriptorConfig.getFieldKey()));
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
        newSubMultiplicityConfig.setParent(subMultParentConfig);
        Map<Integer, List<FieldDescriptor>> fieldsMap = newSubMultiplicityConfig.getFields();
        Map<Integer, List<FieldDescriptor>> newFieldsMap = new HashMap<Integer, List<FieldDescriptor>>();
        int lineCounter = 0;
        if (fieldsMap != null) {
            for (List<FieldDescriptor> fieldsInLine : fieldsMap.values()) {
                List<FieldDescriptor> newFields = new ArrayList<FieldDescriptor>();
                for (FieldDescriptor field : fieldsInLine) {
                    String configParentKey = (parentFd.getFieldKey() == null)? "" : parentFd.getFieldKey();
                    String configFieldKey = (field.getFieldKey() == null)? "" : field.getFieldKey();
                    String trimmedFieldKey = null;
                    FieldDescriptor newField = null;
                    trimmedFieldKey = configFieldKey.substring(configParentKey.length());
                    
                    QueryPath fieldPath = QueryPath.concat(subMultParentConfig.getFieldKey(), 
                            trimmedFieldKey);;
                    newField = new FieldDescriptor(
                            fieldPath.toString(), field.getMessageKey(), field.getMetadata()
                            );
                    newFields.add(newField);
                    
                }
                newFieldsMap.put(Integer.valueOf(lineCounter), newFields);
                lineCounter++;
            }
        }
        newSubMultiplicityConfig.setFields(newFieldsMap);
        
        return newSubMultiplicityConfig;
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
            Set<Entry<SwapCompositeCondition, List<SwapCompositeConditionFieldConfig>>> entries = swappableFieldsDefinition.entrySet();
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

