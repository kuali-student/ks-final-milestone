/**
 * Copyright 2010 The Kuali Foundation Licensed under the
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

package org.kuali.student.lum.lu.ui.course.client.widgets;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.kuali.student.common.ui.client.application.Application;
import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.common.ui.client.widgets.focus.FocusGroup;
import org.kuali.student.common.ui.client.widgets.list.ListItems;
import org.kuali.student.common.ui.client.widgets.list.SelectionChangeHandler;
import org.kuali.student.common.ui.client.widgets.suggestbox.KSSuggestBox;
import org.kuali.student.common.ui.client.widgets.suggestbox.SearchSuggestOracle;
import org.kuali.student.common.ui.client.widgets.suggestbox.SuggestPicker;
import org.kuali.student.core.search.dto.SearchParam;
import org.kuali.student.lum.common.client.lo.LUConstants;

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
public class CluCodePicker extends Composite implements SuggestPicker {

    //FIXME:  [KSCOR-225]  Class needs to be rewritten to use KSPicker instead of SuggestPicker and use lookup config through metadata rt
	

    private String type;
    private String state;
    private String messageGroup;

    final SearchSuggestOracle luSearchOracle = new SearchSuggestOracle(
            "lu.search.cluByCodeAndState",
            "lu.queryParam.startsWith.cluCode",
            "lu.queryParam.cluId",
            "lu.resultColumn.cluId", 
    "lu.resultColumn.cluOfficialIdentifier.cluCode");
    final KSSuggestBox suggestBox = new KSSuggestBox(luSearchOracle);

//    final KSDropDown stateDropDown = new KSDropDown();

    VerticalPanel root = new VerticalPanel();

    private final FocusGroup focus = new FocusGroup(this);

    //FIXME: [KSCOR-225] These should come from an enumeration somehow
    private static final String STATE_EXPLORE = "Explore";
    private static final String STATE_TEMPLATE = "Template";
    private static final String STATE_DRAFT = "Draft";
    private static final String STATE_SUBMITTED = "Submitted";
    private static final String STATE_WITHDRAWN = "Withdrawn";
    private static final String STATE_APPROVED = "Approved";
    private static final String STATE_REJECTED = "Rejected";
    private static final String STATE_ACTIVE = "Active";
    private static final String STATE_RETIRED = "Retired";



    public CluCodePicker(String messageGroup, String type, String state) {
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
//        luStateParam.setValue(STATE_ACTIVE);
//        params.add(luStateParam);
		List<SearchParam> additionalParams = new ArrayList<SearchParam>();
		SearchParam luStateParam = new SearchParam();
		luStateParam.setKey("lu.queryParam.cluState");
		luStateParam.setValue(STATE_ACTIVE);
		additionalParams.add(luStateParam);

        luSearchOracle.setAdditionalSearchParams(additionalParams);


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

    private ListItems buildCluStateListItems() {

        return new ListItems(){
            List<String> states = Arrays.asList(STATE_ACTIVE, STATE_APPROVED, STATE_DRAFT, STATE_EXPLORE, STATE_REJECTED,
                    STATE_RETIRED, STATE_SUBMITTED, STATE_TEMPLATE, STATE_WITHDRAWN);

            @Override
            public List<String> getAttrKeys() {
                List<String> attributes = new ArrayList<String>();
                attributes.add("State");
                return attributes;
            }

            @Override
            public String getItemAttribute(String id, String attrkey) {
                String value = null;
                Integer index;
                try{
                    index = Integer.valueOf(id);
                    value = states.get(index);
                } catch (Exception e) {
                }

                return value;
            }

            @Override
            public int getItemCount() {
                return states.size();
            }

            @Override
            public List<String> getItemIds() {
                List<String> ids = new ArrayList<String>();
                for(int i=0; i < states.size(); i++){
                    ids.add(String.valueOf(i));
                }
                return ids;
            }

            @Override
            public String getItemText(String id) {
                return getItemAttribute(id, "State");
            }
        };

    }

	@Override
	public HandlerRegistration addSelectionChangeHandler(
			SelectionChangeHandler handler) {
		return suggestBox.addSelectionChangeHandler(handler);
	}
}
