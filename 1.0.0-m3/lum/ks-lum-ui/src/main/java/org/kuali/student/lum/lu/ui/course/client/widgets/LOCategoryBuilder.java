/*
 * Copyright 2008 The Kuali Foundation
 * <p/>
 * Licensed under the Educational Community License, Version 1.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.opensource.org/licenses/ecl1.php
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * <p/>
 * User: hjohnson
 * Date: Nov 26, 2009
 * Time: 10:34:49 AM
 */
package org.kuali.student.lum.lu.ui.course.client.widgets;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.kuali.student.common.ui.client.application.Application;
import org.kuali.student.common.ui.client.configurable.mvc.HasModelDTOValue;
import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.common.ui.client.mvc.dto.ModelDTO;
import org.kuali.student.common.ui.client.mvc.dto.ModelDTOValue;
import org.kuali.student.common.ui.client.widgets.KSButton;
import org.kuali.student.common.ui.client.widgets.KSDropDown;
import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.common.ui.client.widgets.KSLightBox;
import org.kuali.student.common.ui.client.widgets.KSThinTitleBar;
import org.kuali.student.common.ui.client.widgets.buttongroups.CreateCancelGroup;
import org.kuali.student.common.ui.client.widgets.buttongroups.ButtonEnumerations.CreateCancelEnum;
import org.kuali.student.common.ui.client.widgets.focus.FocusGroup;
import org.kuali.student.common.ui.client.widgets.list.ListItems;
import org.kuali.student.common.ui.client.widgets.suggestbox.KSSuggestBox;
import org.kuali.student.common.ui.client.widgets.suggestbox.SearchSuggestOracle;
import org.kuali.student.common.ui.client.widgets.suggestbox.SuggestPicker;
import org.kuali.student.lum.lo.dto.LoCategoryInfo;
import org.kuali.student.lum.lo.dto.LoCategoryTypeInfo;
import org.kuali.student.lum.lu.ui.course.client.configuration.LUConstants;
import org.kuali.student.lum.lu.ui.course.client.service.LoRpcService;
import org.kuali.student.lum.lu.ui.course.client.service.LoRpcServiceAsync;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.HTMLTable.Cell;

/**
 * 
 * This class allows a user to select and remove LO categories within the context of 
 * LO creation. New categories can be added 'on the fly' and are persisted in the database
 * independently of LO creation 
 *  
 * @author Kuali Rice Team (kuali-rice@googlegroups.com)
 *
 */
public class LOCategoryBuilder extends Composite implements HasModelDTOValue {

    private String type;
    private String state;
    private String messageGroup;

    private LoRpcServiceAsync loRpcServiceAsync ;
    private LOCategoryPicker picker ;

    LOCategoryList categoryList;

    VerticalPanel root = new VerticalPanel();

    private KSButton addButton = new KSButton("Add");

    private KSLightBox createCategoryWindow;

    public LOCategoryBuilder(String messageGroup, String type, String state) {
        super();

        this.type = type;
        this.state = state;
        this.messageGroup = messageGroup;

        loRpcServiceAsync = GWT.create(LoRpcService.class);
        picker = new LOCategoryPicker();
        categoryList = new LOCategoryList();

        initWidget(root);

        final VerticalPanel selectedPanel = new VerticalPanel();

        addButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                addEnteredCategory();
            }
        });

        VerticalPanel main = new VerticalPanel();
        HorizontalPanel suggestPanel = new HorizontalPanel();
        suggestPanel.add(picker);
        suggestPanel.add(addButton);

        selectedPanel.add(categoryList);
        main.add(getLabel(LUConstants.LO_CATEGORY_CODE_KEY));
        main.add(suggestPanel);
        main.add(selectedPanel);
        root.add(main);

        main.addStyleName("KS-LOCategoryPicker");
        addButton.addStyleName("KS-LOCategoryPicker-Button");

    }

    private void addEnteredCategory() {

        String categoryId = picker.getSelectedId();

        if (categoryList == null)
            categoryList = new LOCategoryList();

        if (categoryId.trim().equals("")) {
            showNewCategoryWindow();
        }
        else {
            LoCategoryInfo category = new LoCategoryInfo();
            category.setId(picker.getSelectedId());
            category.setName(picker.getText());
            category.setState("active");
            addCategory(category);   
         }
    }

    private void showNewCategoryWindow() {

        final VerticalPanel main = new VerticalPanel();
        final KSDropDown typesDropDown = new KSDropDown();
        createCategoryWindow = new KSLightBox();

        loRpcServiceAsync.getLoCategoryTypes(new AsyncCallback<List<LoCategoryTypeInfo>>() {

            @Override
            public void onFailure(Throwable caught) {
                Window.alert("getLoCategoryTypes failed " + caught.getMessage());
            }

            @Override
            public void onSuccess(List<LoCategoryTypeInfo> result) {
                final LOCategoryTypeInfoList list = new LOCategoryTypeInfoList(result);
                typesDropDown.setListItems(list);
                String a = picker.getText();
                KSThinTitleBar titleBar = new KSThinTitleBar("Create New Category " + picker.getText());//+ enteredWord);
                main.add(titleBar);
                main.add(new KSLabel("Select a Type"));
                main.add(typesDropDown);
                CreateCancelGroup buttonPanel = new CreateCancelGroup(new Callback<CreateCancelEnum>(){

                    @Override
                    public void exec(CreateCancelEnum result) {
                        switch(result){
                            case CREATE:
                                final LoCategoryInfo loCategoryInfo = new LoCategoryInfo();
                                loCategoryInfo.setName(picker.getText());
                                loCategoryInfo.setState("active");

                                loRpcServiceAsync.createLoCategory("kuali.loRepository.key.singleUse", typesDropDown.getSelectedItem(),
                                        loCategoryInfo, new AsyncCallback<LoCategoryInfo>() {

                                    @Override
                                    public void onFailure(Throwable caught) {
                                        Window.alert("createCategory failed " + caught.getMessage());                                                
                                    }

                                    @Override
                                    public void onSuccess(LoCategoryInfo result) {
                                        addCategory(result);       
                                        createCategoryWindow.hide();
                                    }


                                });


                                break;
                            case CANCEL:
                                createCategoryWindow.hide();
                                break;
                        }
                    }
                });

                main.add(buttonPanel);
                main.addStyleName("KS-LOWindow");
                createCategoryWindow.setWidget(main);
                createCategoryWindow.show();
            }

        });
    }

    private void addCategory(LoCategoryInfo category) {
        categoryList.addItem(category);
        picker.reset();
    }

    private KSLabel getLabel(String labelKey) {
        return new KSLabel(Application.getApplicationContext().getUILabel(messageGroup, type, state, labelKey));
    }

    @Override
    public void setValue(ModelDTOValue modelDTOValue) {
        categoryList.setValue(modelDTOValue);

    }

    @Override
    public void setValue(ModelDTOValue value, boolean fireEvents) {
        setValue(value);
    }

    /**
     * @see com.google.gwt.user.client.ui.HasValue#getValue()
     */
    @Override
    public ModelDTOValue getValue() {
        return categoryList.getValue();
    }

    /**
     * @see org.kuali.student.common.ui.client.configurable.mvc.HasModelDTOValue#updateModelDTO(org.kuali.student.common.ui.client.mvc.dto.ModelDTOValue)
     */
    @Override
    public void updateModelDTOValue() {
        categoryList.updateModelDTOValue();
    }

    /**
     * @see com.google.gwt.event.logical.shared.HasValueChangeHandlers#addValueChangeHandler(com.google.gwt.event.logical.shared.ValueChangeHandler)
     */
    @Override
    public HandlerRegistration addValueChangeHandler(ValueChangeHandler<ModelDTOValue> handler) {
        return categoryList.addValueChangeHandler(handler);
    }

    /**
     * 
     * This class provides a suggest box for existing LO categories backed by a search on category name
     *  
     * @author Kuali Rice Team (kuali-rice@googlegroups.com)
     *
     */private class LOCategoryPicker extends Composite implements SuggestPicker {

        final SearchSuggestOracle luSearchOracle = new SearchSuggestOracle(loRpcServiceAsync,
                "lo.search.categories",
                "lo.queryParam.loCategoryName",
                "lo.queryParam.loCategoryId",
                "lo.resultColumn.categoryId", 
        "lo.resultColumn.categoryName");

        final KSSuggestBox suggestBox = new KSSuggestBox(luSearchOracle);

        private final FocusGroup focus = new FocusGroup(this);

        private VerticalPanel main = new VerticalPanel();

        protected LOCategoryPicker() {
            super();
            init();
        }

        public String getSelectedId() {
            return suggestBox.getSelectedId();
        }

        private void init () {

            focus.addWidget(suggestBox);

            luSearchOracle.setTextWidget(suggestBox.getTextBox());
//          final ArrayList<QueryParamValue> params = new ArrayList<QueryParamValue>();
//          QueryParamValue luStateParam = new QueryParamValue();
//          luStateParam.setKey("lu.queryParam.cluState");     
//          luStateParam.setValue(STATE_ACTIVATED);
//          params.add(luStateParam);
//          luSearchOracle.setAdditionalQueryParams(params);

            main.add(suggestBox);
            initWidget(main);
        }



        @Override
        public String getValue() {
            return suggestBox.getSelectedId();
        }

        @Override
        public void setValue(String value) {
            setValue(value, true);
        }

        @Override
        public void setValue(String value, boolean fireEvents) {
            suggestBox.reset();
            suggestBox.setValue(value, fireEvents);
        }


        @Override
        public HandlerRegistration addValueChangeHandler(ValueChangeHandler<String> handler) {
            return suggestBox.addValueChangeHandler(handler);
        }

        @Override
        public void fireEvent(GwtEvent<?> event) {
            super.fireEvent(event);
        }

        public void reset(){
            suggestBox.reset();
        }

        @Override
        public HandlerRegistration addFocusHandler(FocusHandler handler) {
            return focus.addFocusHandler(handler);
        }

        @Override
        public HandlerRegistration addBlurHandler(BlurHandler handler) {
            return focus.addBlurHandler(handler);
        }

        public String getText() {
            return suggestBox.getText();
        }

    }

    private class LOCategoryTypeInfoList implements ListItems{
        Map<String, LoCategoryTypeInfo> loTypeMap = new HashMap<String, LoCategoryTypeInfo>();

        public LOCategoryTypeInfoList(List<LoCategoryTypeInfo> loTypes){
            for (LoCategoryTypeInfo type: loTypes){
                loTypeMap.put(type.getId(), type);
            }
        }

        public List<String> getAttrKeys() {
            return Arrays.asList("Name");
        }

        public String getItemAttribute(String id, String attrkey) {
            LoCategoryTypeInfo lo = loTypeMap.get(id);

            if (attrkey.equals("Name")){
                return lo.getName(); 
            }

            return null;
        }

        public int getItemCount() {
            return loTypeMap.size();
        }

        public List<String> getItemIds() {
            List<String> keys = new ArrayList<String>();

            for (String s:loTypeMap.keySet()){
                keys.add(s);
            }

            return keys;
        }

        public String getItemText(String id) {
            return ((LoCategoryTypeInfo)loTypeMap.get(id)).getName();
        }
    }

/**
 * 
 * This inner class handles adding and removing selected categories to/from 
 * a list in the CategoryPicker.  Uses ModelDTOList
 *  
 * TODO: Still valid in DOL? 
 * 
 * @author Kuali Rice Team (kuali-rice@googlegroups.com)
 *
 */    public class LOCategoryList extends Composite {
        protected List<ModelDTO> modelDTOList = new ArrayList<ModelDTO>();
        VerticalPanel main = new VerticalPanel();

        FlexTable categoryTable = new FlexTable();

        final ClickHandler deleteHandler = new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                Cell cell = categoryTable.getCellForEvent(event);
                int r = cell.getRowIndex();
                KSLabel label = (KSLabel)categoryTable.getWidget(r, 0);
                categoryList.removeItem(label.getText());
                categoryList.redraw();
            }                   
        };
        
        public LOCategoryList(){

            main.add(categoryTable);
            super.initWidget(main);

        }

        public HandlerRegistration addValueChangeHandler(ValueChangeHandler<ModelDTOValue> handler) {
            return null;
        }

        public void updateModelDTOValue() {
            categoryList.updateModelDTOValue();
        }

        public void redraw() {

            int row = 0;
            int col = 0;

            categoryTable.clear();

            for (int i = 0; i < modelDTOList.size(); i++) {
                String name = ((ModelDTOValue.StringType)modelDTOList.get(i).get("name")).get();
                categoryTable.setWidget(row, col++, new KSLabel(name));
                KSLabel deleteLabel = new KSLabel("[x]");
                deleteLabel.addStyleName("KS-LOBuilder-Search-Link");
                deleteLabel.addClickHandler(deleteHandler);
                categoryTable.setWidget(row, col++, deleteLabel);
                row++;
                col = 0;                                
            }
        }
        
        public ModelDTOValue getValue() {
            ModelDTOValue.ListType value = new ModelDTOValue.ListType();

            // fill the list of ModelDTO to ModelDTOValue.ModelDTOType 
            List<ModelDTOValue> valueList = new ArrayList<ModelDTOValue>();
            for(ModelDTO dto:modelDTOList){
                ModelDTOValue.ModelDTOType dtoValue = new ModelDTOValue.ModelDTOType();
                dtoValue.set(dto);
                valueList.add(dtoValue);
            }
            value.set(valueList);

            return value;
        }

        public void setValue(ModelDTOValue value) {
            ModelDTOValue.ListType list = (ModelDTOValue.ListType) value;
            modelDTOList = new ArrayList<ModelDTO>();
            // fill the ModelDTOValue.ModelDTOType to List<ModelDTO>
            // when the server hasn't been called yet, there's not list in LoModelDTO
            if (null != list) {
                for(ModelDTOValue dto : list.get()){
                    ModelDTOValue.ModelDTOType dtoType = (ModelDTOValue.ModelDTOType)dto;
                    modelDTOList.add(dtoType.get());
                }
            }
            redraw();            
        }

        public void removeItem(String text) {
           
            int i = 0;
            for (ModelDTO dto : modelDTOList) {
                String name = ((ModelDTOValue.StringType)dto.get("name")).get();
                
                if (name.equals(text)) {
                    modelDTOList.remove(i);
                    break;
                }
                i++;                              
            }
            redraw();
        }

        public void addItem(LoCategoryInfo category) {
            ModelDTO modelDTO = new ModelDTO();
            ModelDTOValue.StringType name = new ModelDTOValue.StringType();
            name.set(category.getName());            
            modelDTO.put("name", name);
            
            ModelDTOValue.StringType id = new ModelDTOValue.StringType();
            id.set(category.getId());            
            modelDTO.put("id", id);

            ModelDTOValue.StringType state = new ModelDTOValue.StringType();
            state.set(category.getState());            
            modelDTO.put("state", state);

            modelDTOList.add(modelDTO);
            
            redraw();
        }
    }        
}
