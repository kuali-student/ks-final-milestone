package org.kuali.student.lum.lu.ui.course.client.widgets;

import org.kuali.student.common.ui.client.application.Application;
import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.common.ui.client.widgets.focus.FocusGroup;
import org.kuali.student.common.ui.client.widgets.suggestbox.KSSuggestBox;
import org.kuali.student.common.ui.client.widgets.suggestbox.SearchSuggestOracle;
import org.kuali.student.common.ui.client.widgets.suggestbox.SuggestPicker;
import org.kuali.student.lum.lu.ui.course.client.configuration.LUConstants;
import org.kuali.student.lum.lu.ui.course.client.service.LoRpcService;
import org.kuali.student.lum.lu.ui.course.client.service.LoRpcServiceAsync;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.VerticalPanel;

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
            "lo.resultColumn.categoryName", 
            "lo.resultColumn.categoryName");
    final KSSuggestBox suggestBox = new KSSuggestBox(luSearchOracle);

//    final KSDropDown stateDropDown = new KSDropDown();

    VerticalPanel root = new VerticalPanel();

    private final FocusGroup focus = new FocusGroup(this);

    //FIXME: These should come from an enumeration somehow
    private static final String STATE_EXPLORE = "Explore";
    private static final String STATE_TEMPLATE = "Template";
    private static final String STATE_DRAFT = "Draft";
    private static final String STATE_SUBMITTED = "Submitted";
    private static final String STATE_WITHDRAWN = "Withdrawn";
    private static final String STATE_APPROVED = "Approved";
    private static final String STATE_REJECTED = "Rejected";
    private static final String STATE_ACTIVATED = "Activated";
    private static final String STATE_RETIRED = "Retired";



    public LOCategoryCodePicker(String messageGroup, String type, String state) {
        super();

        this.type = type;
        this.state = state;
        this.messageGroup = messageGroup;

        focus.addWidget(suggestBox);

        initWidget(root);

        VerticalPanel main = new VerticalPanel();

        luSearchOracle.setTextWidget(suggestBox.getTextBox());


//        final ListItems cluStateListItems = buildCluStateListItems();
//        stateDropDown.setListItems(cluStateListItems);
//        stateDropDown.setMultipleSelect(false);
//        stateDropDown.selectItem("0");

        main.add(getLabel(LUConstants.CODE_LABEL_KEY));
        main.add(suggestBox);
//        main.add(stateDropDown);
//        final ArrayList<QueryParamValue> params = new ArrayList<QueryParamValue>();
//        QueryParamValue luStateParam = new QueryParamValue();
//        luStateParam.setKey("lu.queryParam.cluState");     
//        luStateParam.setValue(STATE_ACTIVATED);
//        params.add(luStateParam);
//        luSearchOracle.setAdditionalQueryParams(params);


//        stateDropDown.addSelectionChangeHandler(new SelectionChangeHandler() {
//
//            @Override
//            public void onSelectionChange(KSSelectItemWidgetAbstract w) {
//                
//                ArrayList<QueryParamValue> newParams = new ArrayList<QueryParamValue>();
//                List<String> selectedItems = stateDropDown.getSelectedItems();
//                for(String s: selectedItems){
//                    QueryParamValue luStateParam = new QueryParamValue();
//                    luStateParam.setKey("lu.queryParam.cluState");     
//                    luStateParam.setValue(cluStateListItems.getItemText(s));
//                    params.add(luStateParam);
//               }
//                luSearchOracle.setAdditionalQueryParams(newParams);
//
//            }
//
//        });


        root.add(main);

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
}
