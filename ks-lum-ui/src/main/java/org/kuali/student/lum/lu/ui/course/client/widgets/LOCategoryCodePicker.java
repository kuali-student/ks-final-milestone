package org.kuali.student.lum.lu.ui.course.client.widgets;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.kuali.student.common.ui.client.application.Application;
import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.common.ui.client.widgets.KSButton;
import org.kuali.student.common.ui.client.widgets.KSDropDown;
import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.common.ui.client.widgets.KSLightBox;
import org.kuali.student.common.ui.client.widgets.KSThinTitleBar;
import org.kuali.student.common.ui.client.widgets.buttongroups.ButtonEnumerations.CreateCancelEnum;
import org.kuali.student.common.ui.client.widgets.buttongroups.CreateCancelGroup;
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
 * /*
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
public class LOCategoryCodePicker extends Composite implements SuggestPicker {

    private String type;
    private String state;
    private String messageGroup;

    private LoRpcServiceAsync loRpcServiceAsync = GWT.create(LoRpcService.class);
    final SearchSuggestOracle luSearchOracle = new SearchSuggestOracle(loRpcServiceAsync,
            "lo.search.categories",
            "lo.queryParam.loCategoryName",
            "lo.queryParam.loCategoryId",
            "lo.resultColumn.categoryId", 
    "lo.resultColumn.categoryName");
    final KSSuggestBox suggestBox = new KSSuggestBox(luSearchOracle);

    FlexTable selections = new FlexTable();

    VerticalPanel root = new VerticalPanel();
    int row = 0;
    int col = 0;

    private final FocusGroup focus = new FocusGroup(this);
    private KSButton addButton = new KSButton("Add");
    
    private static final String LO_DESCRIPTION_ATTR_KEY = "Description";
    private static final String LO_CLU_CODE_ATTR_KEY = "From";
    
    final ClickHandler deleteHandler = new ClickHandler() {
        @Override
        public void onClick(ClickEvent event) {
            Cell cell = selections.getCellForEvent(event);
            selections.removeRow(cell.getRowIndex());
        }                   
    };

    private KSLightBox createCategoryWindow;

    public LOCategoryCodePicker(String messageGroup, String type, String state) {
        super();

        this.type = type;
        this.state = state;
        this.messageGroup = messageGroup;

        focus.addWidget(suggestBox);

        initWidget(root);

        luSearchOracle.setTextWidget(suggestBox.getTextBox());
//      final ArrayList<QueryParamValue> params = new ArrayList<QueryParamValue>();
//      QueryParamValue luStateParam = new QueryParamValue();
//      luStateParam.setKey("lu.queryParam.cluState");     
//      luStateParam.setValue(STATE_ACTIVATED);
//      params.add(luStateParam);
//      luSearchOracle.setAdditionalQueryParams(params);

        final VerticalPanel selectedPanel = new VerticalPanel();


        addButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                addSelectedCategory(deleteHandler);
            }
        });

        VerticalPanel main = new VerticalPanel();
        HorizontalPanel suggestPanel = new HorizontalPanel();
        suggestPanel.add(suggestBox);
        suggestPanel.add(addButton);

        selectedPanel.add(selections);
        main.add(getLabel(LUConstants.LO_CATEGORY_CODE_KEY));
        main.add(suggestPanel);
        main.add(selectedPanel);
        root.add(main);

        main.addStyleName("KS-LOCategoryPicker");
        addButton.addStyleName("KS-LOCategoryPicker-Button");

    }

    private void addSelectedCategory(final ClickHandler deleteHandler) {

        String categoryId = suggestBox.getSelectedId();

        if (categoryId.trim().equals("")) {
            showNewCategoryWindow();
        }
        else {
            addCategory();           
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
                final LoCategoryTypeInfoList list = new LoCategoryTypeInfoList(result);
                typesDropDown.setListItems(list);
                KSThinTitleBar titleBar = new KSThinTitleBar("New Category " + suggestBox.getText());//+ enteredWord);
                main.add(titleBar);
                main.add(new KSLabel("Select a Type"));
                main.add(typesDropDown);
                CreateCancelGroup buttonPanel = new CreateCancelGroup(new Callback<CreateCancelEnum>(){

                    @Override
                    public void exec(CreateCancelEnum result) {
                        switch(result){
                            case CREATE:
                                LoCategoryInfo loCategoryInfo = new LoCategoryInfo();
                                loCategoryInfo.setName(suggestBox.getText());
                                loCategoryInfo.setState("active");
                                
                                loRpcServiceAsync.createLoCategory("kuali.loRepository.key.singleUse", typesDropDown.getSelectedItem(),
                                        loCategoryInfo, new AsyncCallback<LoCategoryInfo>() {

                                            @Override
                                            public void onFailure(Throwable caught) {
                                                Window.alert("createCategory failed " + caught.getMessage());                                                
                                            }

                                            @Override
                                            public void onSuccess(LoCategoryInfo result) {
                                                addCategory();            
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

        // show window
        // call lo to create new category        
    }
    
    private void addCategory() {
        selections.setWidget(row, col++, new KSLabel(suggestBox.getText().trim()));
        KSLabel deleteLabel = new KSLabel("[x]");
        deleteLabel.addStyleName("KS-LOBuilder-Search-Link");
        deleteLabel.addClickHandler(deleteHandler);
        selections.setWidget(row, col++, deleteLabel);
        row++;
        col = 0;          

        suggestBox.reset();
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

    public void clear(){
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

    private KSLabel getLabel(String labelKey) {
        return new KSLabel(Application.getApplicationContext().getUILabel(messageGroup, type, state, labelKey));
    }
    
    private class LoCategoryTypeInfoList implements ListItems{
        Map<String, LoCategoryTypeInfo> loTypeMap = new HashMap<String, LoCategoryTypeInfo>();

        public LoCategoryTypeInfoList(List<LoCategoryTypeInfo> loTypes){
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


}
