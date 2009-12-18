package org.kuali.student.lum.lu.ui.course.client.widgets;

import java.util.HashMap;
import java.util.Map;

import org.kuali.student.common.ui.client.application.Application;
import org.kuali.student.common.ui.client.widgets.KSButton;
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
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerRegistration;
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
            "lo.resultColumn.categoryName", 
    "lo.resultColumn.categoryName");
    final KSSuggestBox suggestBox = new KSSuggestBox(luSearchOracle);

    FlexTable selections = new FlexTable();

    VerticalPanel root = new VerticalPanel();
    int row = 0;
    int col = 0;

    private final FocusGroup focus = new FocusGroup(this);
    private KSButton addButton = new KSButton("Add");

    public LOCategoryCodePicker(String messageGroup, String type, String state) {
        super();

        this.type = type;
        this.state = state;
        this.messageGroup = messageGroup;

        focus.addWidget(suggestBox);

        initWidget(root);

        VerticalPanel main = new VerticalPanel();
        HorizontalPanel suggestPanel = new HorizontalPanel();
        suggestPanel.add(suggestBox);
        suggestPanel.add(addButton);

        luSearchOracle.setTextWidget(suggestBox.getTextBox());
//      final ArrayList<QueryParamValue> params = new ArrayList<QueryParamValue>();
//      QueryParamValue luStateParam = new QueryParamValue();
//      luStateParam.setKey("lu.queryParam.cluState");     
//      luStateParam.setValue(STATE_ACTIVATED);
//      params.add(luStateParam);
//      luSearchOracle.setAdditionalQueryParams(params);
        
        final VerticalPanel selectedPanel = new VerticalPanel();
        selectedPanel.add(selections);

        addButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                selections.setWidget(row, col++, new KSLabel(suggestBox.getText().trim()));
                KSLabel deleteLabel = new KSLabel("[x]");
                deleteLabel.addStyleName("KS-LOBuilder-Search-Link");
                deleteLabel.addClickHandler(new ClickHandler() {
                    @Override
                    public void onClick(ClickEvent event) {
                        Cell cell = selections.getCellForEvent(event);
                        selections.removeRow(cell.getRowIndex());
                    }                   
                });
                selections.setWidget(row, col++, deleteLabel);
                row++;
                col = 0;                
                suggestBox.reset();
            }});
        main.add(getLabel(LUConstants.LO_CATEGORY_CODE_KEY));
        main.addStyleName("KS-LOCategoryPicker");
        addButton.addStyleName("KS-LOCategoryPicker-Button");
        main.add(suggestPanel);
        main.add(selectedPanel);

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
