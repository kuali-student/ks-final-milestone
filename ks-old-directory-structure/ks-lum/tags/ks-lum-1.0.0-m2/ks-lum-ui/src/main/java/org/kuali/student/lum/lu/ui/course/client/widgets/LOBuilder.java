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
package org.kuali.student.lum.lu.ui.course.client.widgets;

import java.util.List;

import org.kuali.student.common.ui.client.application.Application;
import org.kuali.student.common.ui.client.configurable.mvc.CustomNestedSection;
import org.kuali.student.common.ui.client.configurable.mvc.FieldDescriptor;
import org.kuali.student.common.ui.client.configurable.mvc.HasModelDTOValue;
import org.kuali.student.common.ui.client.configurable.mvc.MultiplicitySection;
import org.kuali.student.common.ui.client.configurable.mvc.SimpleMultiplicityComposite;
import org.kuali.student.common.ui.client.configurable.mvc.Section.FieldLabelType;
import org.kuali.student.common.ui.client.mvc.dto.ModelDTO;
import org.kuali.student.common.ui.client.mvc.dto.ModelDTOValue;
import org.kuali.student.common.ui.client.mvc.dto.ModelDTOValue.StringType;
import org.kuali.student.common.ui.client.mvc.dto.ModelDTOValue.Type;
import org.kuali.student.common.ui.client.theme.Theme;
import org.kuali.student.common.ui.client.widgets.KSButton;
import org.kuali.student.common.ui.client.widgets.KSCheckBox;
import org.kuali.student.common.ui.client.widgets.KSImage;
import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.common.ui.client.widgets.KSTextBox;
import org.kuali.student.common.ui.client.widgets.suggestbox.KSAdvancedSearchWindow;
import org.kuali.student.common.ui.client.widgets.tabs.KSTabPanel;
import org.kuali.student.lum.lo.dto.LoInfo;
import org.kuali.student.lum.lu.ui.course.client.configuration.LUConstants;
import org.kuali.student.lum.lu.ui.course.client.configuration.mvc.CluDictionaryClassNameHelper;
import org.kuali.student.lum.lu.ui.course.client.service.LoRpcService;
import org.kuali.student.lum.lu.ui.course.client.service.LoRpcServiceAsync;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * This class manages the users interactions when building/updating Learning Objectives within the
 * context of managing CLUs.  It allows the user to type in LO text directly or execute a search and
 * select one or more of the returned LOs.
 * 
 * Users can then re-organize LOs on the screen including altering the sequence and creating sub LOs
 * 
 * @author Kuali Rice Team (kuali-rice@googlegroups.com)
 *
 *
 */
public class LOBuilder extends Composite  implements HasModelDTOValue {


    //FIXME:  Initialize type and state
    private static String type;
    private static String state;
    private static String messageGroup;
    
    private LoRpcServiceAsync loRpcServiceAsync = GWT.create(LoRpcService.class);

    KSAdvancedSearchWindow loSearchWindow;   

    VerticalPanel main = new VerticalPanel();

    HorizontalPanel searchMainPanel = new HorizontalPanel();
    SimplePanel searchSpacerPanel = new SimplePanel();
    HorizontalPanel searchLinkPanel = new HorizontalPanel();
    
    VerticalPanel loPanel = new VerticalPanel();

    KSLabel searchLink;
    LearningObjectiveList loList;
    KSLabel instructions ;

    private static final int NUM_INITIAL_LOS = 1;
    
    private KSImage searchImage = Theme.INSTANCE.getCommonImages().getSearchIcon();


    protected LOBuilder() {
        //TODO: should this be an error?  Can we set realistic defaults?
        
    }


    public LOBuilder(String luType, String luState, String luGroup) {
        super();
        initWidget(main);
        
        type = luType;
        state = luState;
        messageGroup = luGroup;        

        ClickHandler searchClickHandler = new ClickHandler() {
              @Override
            public void onClick(ClickEvent event) {
                if (loSearchWindow == null) {
                    initSearchWindow();
                }
                loSearchWindow.show();
            }          
        };

        instructions = new KSLabel(getLabel(LUConstants.LO_INSTRUCTIONS));
        searchLink = new KSLabel(getLabel(LUConstants.LO_SEARCH_LINK));
        searchLink.addClickHandler(searchClickHandler);          
        searchImage.addClickHandler(searchClickHandler);          

        searchLinkPanel.add(searchImage);
        searchLinkPanel.add(searchLink);        
        searchSpacerPanel.add(new KSLabel(""));
        
        searchMainPanel.add(searchSpacerPanel);
        searchMainPanel.add(searchLinkPanel);
        
        loList = new LearningObjectiveList();
        loPanel.add(loList);
   
        searchImage.addStyleName("KS-LOBuilder-Search-Image");
        searchLink.addStyleName("KS-LOBuilder-Search-Link");
        searchSpacerPanel.addStyleName("KS-LOBuilder-Spacer-Panel");
        searchMainPanel.addStyleName("KS-LOBuilder-Search-Panel");
        loPanel.addStyleName("KS-LOBuilder-LO-Panel");
        instructions.addStyleName("KS-LOBuilder-Instructions");

        main.add(searchMainPanel);
        main.add(instructions);
        main.add(loPanel);
        
      //  main.add(new CategoryManagementComposite());
        

    }
    class CategoryManagementComposite extends Composite{
        VerticalPanel mainPanel = new VerticalPanel();
        CategoryManagementComposite(){
            KSLabel titleLabel = new KSLabel("Categories Management");
            
            

            
            KSTabPanel tabPanel = new KSTabPanel();
            tabPanel.addTab("C", "Create/Modify", createModifyPanel());
            tabPanel.addTab("B", "Batch Create", batchCreatePanel());
            mainPanel.add(titleLabel);
            mainPanel.add(tabPanel);
            super.initWidget(mainPanel);
        }
        private Widget batchCreatePanel(){
            VerticalPanel batchCreatePanel = new VerticalPanel();
            FlexTable categoryTable = new FlexTable();
            categoryTable.setWidget(0,0, new KSLabel("Category"));
            categoryTable.setWidget(0,1, new KSLabel("Type"));

            categoryTable.setWidget(1,0, new KSTextBox());
            categoryTable.setWidget(1,1, new KSTextBox());

            batchCreatePanel.add(categoryTable);
            return batchCreatePanel;
        }
        private Widget createModifyPanel(){
            VerticalPanel createModifyPanel = new VerticalPanel();
            HorizontalPanel createPanel = new HorizontalPanel();
            KSLabel createLabel = new KSLabel("Create");
            createPanel.add(createLabel);
            createPanel.add(new KSLabel("New Category"));
            KSTextBox newCategoryTextBox = new KSTextBox();
            createPanel.add(newCategoryTextBox);
            createPanel.add(new KSLabel("Type"));
            KSTextBox typeTextBox = new KSTextBox();
            createPanel.add(typeTextBox);
            KSButton createButton = new KSButton("Create");
            createPanel.add(createButton);

            
            createModifyPanel.add(createPanel);
            createModifyPanel.add(new KSLabel("Modify"));

            HorizontalPanel modifyPanel = new HorizontalPanel();
            VerticalPanel filterPanel = new VerticalPanel();
            filterPanel.add(new KSLabel("Filter"));
            filterPanel.add(new KSLabel("Category"));
            filterPanel.add(new KSTextBox());
            filterPanel.add(new KSLabel("Type"));
            filterPanel.add(new KSCheckBox("Select All"));
            filterPanel.add(new KSCheckBox("Accreditation"));
            filterPanel.add(new KSCheckBox("Skill"));
            filterPanel.add(new KSCheckBox("Subject"));
            filterPanel.add(new KSCheckBox("Unclassified"));
            filterPanel.add(new KSButton("Filter"));
            
            FlexTable modifyTable = new FlexTable();
            modifyTable.setWidget(0, 0, new KSLabel("Category"));
            modifyTable.setWidget(0, 1, new KSLabel("Type"));
            modifyTable.setWidget(1, 0, new KSTextBox());
            modifyTable.setWidget(1, 1, new KSTextBox());
            modifyTable.setWidget(2, 0, new KSTextBox());
            modifyTable.setWidget(2, 1, new KSTextBox());
            modifyTable.setWidget(3, 0, new KSTextBox());
            modifyTable.setWidget(3, 1, new KSTextBox());
            modifyTable.setWidget(4, 0, new KSTextBox());
            modifyTable.setWidget(4, 1, new KSTextBox());
            modifyTable.setWidget(5, 0, new KSTextBox());
            modifyTable.setWidget(5, 1, new KSTextBox());
            
            modifyPanel.add(filterPanel);
            modifyPanel.add(modifyTable);
            
            
            createModifyPanel.add(modifyPanel);
            return createModifyPanel;
        }
    }


    public static class LearningObjectiveList extends SimpleMultiplicityComposite {        
        protected Widget generateRemoveWidget(final Widget listItem, final Composite parent){
            useDeleteLabel = false;

            return super.generateRemoveWidget(listItem, parent);
        }
        private static final String STYLE_HIGHLIGHTED_ITEM = "KS-LOBuilder-Highlighted-Item";
        {
            setAddItemLabel(getLabel(LUConstants.LEARNING_OBJECTIVE_ADD_LABEL_KEY));
        }

        @Override
        public void redraw() {
            super.redraw();
            // populate with at least NUM_INITIAL_LOS items,
            // even if there aren't that many defined yet
            int startIdx = null == modelDTOList ? 0 : modelDTOList.get().size();
            for (int i = startIdx; i < NUM_INITIAL_LOS; i++) {
                addItem();
            }
        }

        @Override
        public Widget createItem() {
            final MultiplicitySection multi = new MultiplicitySection(CluDictionaryClassNameHelper.LO_INFO_CLASS,
                    "kuali.lo.type.singleUse", "draft");
            CustomNestedSection ns = new CustomNestedSection();
            ns.setCurrentFieldLabelType(FieldLabelType.LABEL_TOP);

            final LOPicker picker = new LOPicker();
            picker.addValueChangeHandler(new ValueChangeHandler<String>() {
                @Override
                public void onValueChange(ValueChangeEvent<String> event) {


                }
            });
            picker.addMoveUpAction(new ClickHandler(){
                @Override
                public void onClick(ClickEvent event) {
                    moveUp(multi);
                }
            });
            picker.addMoveDownAction(new ClickHandler(){
                @Override
                public void onClick(ClickEvent event) {
                    moveDown(multi);
                }
            });
            picker.addDeleteAction(new ClickHandler(){
                @Override
                public void onClick(ClickEvent event) {
                    delete(multi);
                }
            });
            FieldDescriptor fd = new FieldDescriptor("desc", null/* getLabel(LUConstants.LEARNING_OBJECTIVE_LO_NAME_KEY)*/, Type.STRING, picker);
            ns.addField(fd);
            ns.addStyleName("KS-LOBuilder-Section");
            multi.addSection(ns);

            return multi;
        }

        private void removeOldHighlights() {
            List<HasModelDTOValue> widgets = this.getModelDTOValueWidgets();
            for (HasModelDTOValue w: widgets) {
                ((Widget)w).removeStyleName(STYLE_HIGHLIGHTED_ITEM);
            }
        }

        private void addSelectedLO(String loDescription) {

            //TODO: If there are empty LO boxes should we put the selected LOs in them
            //  and not always create new ones?

            ModelDTO loModel = new ModelDTO(LoInfo.class.getName());
            StringType type = new StringType("kuali.lo.type.singleUse");
            StringType state = new StringType("draft");
            loModel.put("desc", loDescription);
            loModel.put("type", type);
            loModel.put("state", state);

            ModelDTOValue value = new ModelDTOValue.ModelDTOType();
            ((ModelDTOValue.ModelDTOType)value).set(loModel);

            Widget w = super.addNewItem(value);
            w.addStyleName(STYLE_HIGHLIGHTED_ITEM);
        }

        private void moveUp(Widget item){
            Widget decrator = item.getParent().getParent();
            int index = LearningObjectiveList.this.itemsPanel.getWidgetIndex(decrator);
            if(index ==0){
                return;
            }
            LearningObjectiveList.this.itemsPanel.remove(decrator);
            LearningObjectiveList.this.itemsPanel.insert(decrator,index -1 );

        }
        private void moveDown(Widget item){
            Widget decrator = item.getParent().getParent();
            int index = LearningObjectiveList.this.itemsPanel.getWidgetIndex(decrator);
            if(index == LearningObjectiveList.this.itemsPanel.getWidgetCount()-1){
                return;
            }
            LearningObjectiveList.this.itemsPanel.remove(decrator);
            LearningObjectiveList.this.itemsPanel.insert(decrator,index+1 );
            
        }
        private void delete(Widget item){
            Widget decrator = item.getParent().getParent();
            int index = LearningObjectiveList.this.itemsPanel.getWidgetIndex(decrator);
            if(index == -1){
                return;
            }
            super.decorateItemWidget(item);
            LearningObjectiveList.this.itemsPanel.remove(decrator);
        }
    }  

    private void initSearchWindow() {

        loSearchWindow = new KSAdvancedSearchWindow(loRpcServiceAsync, "lo.search.loByDesc","lo.resultColumn.loDescId", "Find Learning Objectives");   
        //FIXME: This text should be from message service
        loSearchWindow.setInstructions("Search for these words in Learning Objectives");
        loSearchWindow.setIgnoreCase(true);
        loSearchWindow.setPartialMatch(true);
        loSearchWindow.addSelectionHandler(new SelectionHandler<List<String>>(){
            public void onSelection(SelectionEvent<List<String>> event) {
                loList.removeOldHighlights();
                final List<String> selected = event.getSelectedItem();
                if (selected.size() > 0){
                    for (String loDesc : selected ) {
                        loList.addSelectedLO(loDesc);                        
                    }

                    loSearchWindow.hide();
                }                
            }            
        });
    }

    @Override
    public void setValue(ModelDTOValue modelDTOValue) {
        loList.setValue(modelDTOValue);

    }

    @Override
    public void setValue(ModelDTOValue value, boolean fireEvents) {
        setValue(value, fireEvents);
    }

    /**
     * @see com.google.gwt.user.client.ui.HasValue#getValue()
     */
    @Override
    public ModelDTOValue getValue() {
        return loList.getValue();
    }

    /** 
     * @see org.kuali.student.common.ui.client.configurable.mvc.HasModelDTOValue#updateModelDTO(org.kuali.student.common.ui.client.mvc.dto.ModelDTOValue)
     */
    @Override
    public void updateModelDTOValue() {
        loList.updateModelDTOValue();
    }

    /**
     * @see com.google.gwt.event.logical.shared.HasValueChangeHandlers#addValueChangeHandler(com.google.gwt.event.logical.shared.ValueChangeHandler)
     */
    @Override
    public HandlerRegistration addValueChangeHandler(ValueChangeHandler<ModelDTOValue> handler) {        
        return loList.addValueChangeHandler(handler);
    }

    
    private static String getLabel(String labelKey) {
        return Application.getApplicationContext().getUILabel(messageGroup, type, state, labelKey);
    }
}
