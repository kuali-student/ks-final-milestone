package org.kuali.student.lum.lu.ui.course.client.widgets;

import org.kuali.student.common.ui.client.widgets.focus.FocusGroup;
import org.kuali.student.common.ui.client.widgets.suggestbox.KSSuggestBox;
import org.kuali.student.common.ui.client.widgets.suggestbox.SearchSuggestOracle;
import org.kuali.student.common.ui.client.widgets.suggestbox.SuggestPicker;
import org.kuali.student.lum.lu.ui.course.client.service.LuRpcService;
import org.kuali.student.lum.lu.ui.course.client.service.LuRpcServiceAsync;

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
public class CluCodePicker extends Composite implements SuggestPicker {

	private LuRpcServiceAsync luRpcServiceAsync = GWT.create(LuRpcService.class);
	final SearchSuggestOracle luSearchOracle = new SearchSuggestOracle(luRpcServiceAsync,
	        "lu.search.cluByCodeAndState",
	        "lu.queryParam.cluCode",
	        "lu.queryParam.cluId",
	        "lu.resultColumn.cluId", 
	        "lu.resultColumn.cluOfficialIdentifier.cluCode");
    final KSSuggestBox suggestBox = new KSSuggestBox(luSearchOracle);

    VerticalPanel root = new VerticalPanel();

    private final FocusGroup focus = new FocusGroup(this);

	public CluCodePicker() {
		super();

		// FIXME when org search window is displayed, call focus.setSuppressed(true), and set it to false afterwards
		focus.addWidget(suggestBox);

		initWidget(root);
		luSearchOracle.setTextWidget(suggestBox.getTextBox());

//		//Restrict searches to Department Types
//		ArrayList<QueryParamValue> params = new ArrayList<QueryParamValue>();
//		QueryParamValue orgTypeParam = new QueryParamValue();
//		orgTypeParam.setKey("org.queryParam.orgType");
//		orgTypeParam.setValue("kuali.org.Department");
//		params.add(orgTypeParam);
//		luSearchOracle.setAdditionalQueryParams(params);

		root.add(suggestBox);

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
}
