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
package org.kuali.student.lum.common.client.widgets;

import java.util.List;

import org.kuali.student.common.ui.client.configurable.mvc.DefaultWidgetFactory;
import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.common.ui.client.mvc.HasDataValue;
import org.kuali.student.common.ui.client.widgets.KSDropDown;
import org.kuali.student.common.ui.client.widgets.list.KSRadioButtonList;
import org.kuali.student.common.ui.client.widgets.list.SelectionChangeEvent;
import org.kuali.student.common.ui.client.widgets.list.SelectionChangeHandler;
import org.kuali.student.common.ui.client.widgets.list.impl.KSRadioButtonListImpl;
import org.kuali.student.common.ui.client.widgets.rules.AccessWidgetValue;
import org.kuali.student.common.ui.client.widgets.search.KSPicker;
import org.kuali.student.core.assembly.data.Data;
import org.kuali.student.core.assembly.data.Metadata;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;

public class GradeWidget extends Composite implements AccessWidgetValue, HasDataValue {

    private FlowPanel layout = new FlowPanel();
    KSPicker gradeTypeWidget;
    KSPicker gradeWidget = null;
    Metadata gradeMetadata;

    public GradeWidget() {
        this.initWidget(layout);
    }

    @Override
    public void initWidget(List<Metadata> fieldsMetadata) {
        layout.clear();
        gradeTypeWidget = (KSPicker) DefaultWidgetFactory.getInstance().getWidget(fieldsMetadata.get(0));
        layout.add(gradeTypeWidget);
        gradeMetadata = fieldsMetadata.get(1);
        setupHandlers();
    }

    private void setupHandlers() {

        ((KSRadioButtonList)gradeTypeWidget.getInputWidget()).addSelectionChangeHandler(new SelectionChangeHandler() {

            @Override
            public void onSelectionChange(SelectionChangeEvent event) {
                String gradeTypeSelected = ((KSRadioButtonListImpl)event.getWidget()).getSelectedItem();
                gradeMetadata.getInitialLookup().getParams().get(0).setDefaultValueList(null);
                gradeMetadata.getInitialLookup().getParams().get(0).setDefaultValueString(gradeTypeSelected);
                addGradeListWidget(true);
            }
        });
    }

    private void addGradeListWidget(boolean enabled) {
        if (gradeWidget != null) {
            layout.remove(gradeWidget);
        }
        gradeWidget = (KSPicker) DefaultWidgetFactory.getInstance().getWidget(gradeMetadata);
        ((KSDropDown)gradeWidget.getInputWidget()).setEnabled(enabled);
        layout.add(gradeWidget);
    }

	@Override
	public void addValueChangeCallback(Callback<Data.Value> callback) {
	}

	@Override
	public void setValue(Data.Value value) {
        addGradeListWidget(false);
		gradeWidget.setValue(value);        
	}

    @Override
    public void getValue(Callback<String> doneSaveCallback) { 
    }

    @Override
    public void setValue(String id) {
    }

    @Override
    public Data.Value getValue() {
        return (gradeWidget != null ? gradeWidget.getValue() : null);
    }
}
