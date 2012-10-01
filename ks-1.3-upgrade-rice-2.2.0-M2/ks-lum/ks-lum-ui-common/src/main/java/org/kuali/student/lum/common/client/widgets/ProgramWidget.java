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

import org.kuali.student.common.assembly.data.Data;
import org.kuali.student.common.assembly.data.Metadata;
import org.kuali.student.common.ui.client.configurable.mvc.DefaultWidgetFactory;
import org.kuali.student.common.ui.client.configurable.mvc.sections.VerticalSection;
import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.common.ui.client.mvc.HasDataValue;
import org.kuali.student.common.ui.client.widgets.KSDropDown;
import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.common.ui.client.widgets.field.layout.element.MessageKeyInfo;
import org.kuali.student.common.ui.client.widgets.impl.KSDropDownImpl;
import org.kuali.student.common.ui.client.widgets.list.SelectionChangeEvent;
import org.kuali.student.common.ui.client.widgets.list.SelectionChangeHandler;
import org.kuali.student.common.ui.client.widgets.list.impl.SimpleListItems;
import org.kuali.student.common.ui.client.widgets.progress.BlockingTask;
import org.kuali.student.common.ui.client.widgets.progress.KSBlockingProgressIndicator;
import org.kuali.student.common.ui.client.widgets.search.KSPicker;
import org.kuali.student.core.statement.ui.client.widgets.rules.AccessWidgetValue;

import com.google.gwt.user.client.ui.Composite;

public class ProgramWidget extends Composite implements AccessWidgetValue, HasDataValue {

    private CluSetRetriever courseMetadataRetriever  = new CluSetRetrieverImpl();
    private Callback getCluNameCallback;

    //widgets
    private VerticalSection layout = new VerticalSection();
    private KSDropDown programTypeWidget;
    private KSPicker programWidget = null;
    private KSLabel previousProgramCode;
    private String previousProgramId;

    //data
    private Metadata searchProgramMetadata = null;
    private BlockingTask initializeTask = new BlockingTask("Initializing");

    public ProgramWidget() {
        this.initWidget(layout);
    }

    @Override
    public void initWidget(List<Metadata> fieldsMetadata) {

        previousProgramCode = null;
        previousProgramId = null;
        
        layout.clear();

        final VerticalSection choosingSection = new VerticalSection();
        choosingSection.addWidget(new KSLabel("<b>Add a program</b>"));

        //first metadata is a drop down to select type of program
        createAndAddProgramTypesDropdown();

        retrieveMetadata();
    }

    private void createAndAddProgramTypesDropdown() {
        programTypeWidget = new KSDropDown();
        SimpleListItems programTypes = new SimpleListItems();
        programTypes.addItem(CommonWidgetConstants.CLU_SET_APPROVED_CLUS_FIELD, "Approved Program");
        programTypes.addItem(CommonWidgetConstants.CLU_SET_PROPOSED_CLUS_FIELD, "Proposed Program");
        programTypeWidget.setListItems(programTypes);
        programTypeWidget.addSelectionChangeHandler(new SelectionChangeHandler() {

            @Override
            public void onSelectionChange(SelectionChangeEvent event) {
                String programTypeSelected = ((KSDropDownImpl)event.getWidget()).getSelectedItem();
                if (programTypeSelected == null) {
                    if (programWidget != null) {
                        layout.remove(programWidget);
                    }                    
                    return;
                }
                addProgramListWidget(true, programTypeSelected);
            }
        });        
        layout.add(programTypeWidget);
    }

    private void retrieveMetadata() {
        if (searchProgramMetadata == null) {
            KSBlockingProgressIndicator.addTask(initializeTask);
            courseMetadataRetriever.getMetadata("programSet", new Callback<Metadata>(){
                @Override
                public void exec(Metadata result) {
                    searchProgramMetadata = result;
                    searchProgramMetadata.getProperties().get(CommonWidgetConstants.CLU_SET_APPROVED_CLUS_FIELD).getConstraints().get(0).setMaxOccurs(1);
                    searchProgramMetadata.getProperties().get(CommonWidgetConstants.CLU_SET_PROPOSED_CLUS_FIELD).getConstraints().get(0).setMaxOccurs(1);
                    searchProgramMetadata.getProperties().get(CommonWidgetConstants.CLU_SET_APPROVED_CLUS_FIELD).getConstraints().get(0).setId("single");
                    searchProgramMetadata.getProperties().get(CommonWidgetConstants.CLU_SET_PROPOSED_CLUS_FIELD).getConstraints().get(0).setId("single");
                    KSBlockingProgressIndicator.removeTask(initializeTask);

                    if (previousProgramCode == null) {
                        programTypeWidget.selectItem(CommonWidgetConstants.CLU_SET_APPROVED_CLUS_FIELD);
                        addProgramListWidget(true, programTypeWidget.getSelectedItem());
                    }
                }
            });
        } else {
            if (previousProgramCode == null) {
                programTypeWidget.selectItem(CommonWidgetConstants.CLU_SET_APPROVED_CLUS_FIELD);
                addProgramListWidget(true, programTypeWidget.getSelectedItem());
            }
        }
    }

    private void addProgramListWidget(boolean enabled, String programType) {
        if (programWidget != null) {
            layout.remove(programWidget);
        }
        if (previousProgramCode != null) {
            layout.remove(previousProgramCode);
            previousProgramCode = null;
            previousProgramId = null;
        }
        programWidget = (KSPicker) DefaultWidgetFactory.getInstance().getWidget(searchProgramMetadata.getProperties().get(programType));
     //   programWidget.getSearchPanel().setMutipleSelect(false);
    //    ((KSSuggestBox) programWidget.getInputWidget()).setEnabled(enabled);
        layout.add(programWidget);
    }

    protected MessageKeyInfo generateMessageInfo(String labelKey) {
        return new MessageKeyInfo("clusetmanagement", "clusetmanagement", "draft", labelKey);
    }

	@Override
	public void addValueChangeCallback(Callback<Data.Value> callback) {
	}

	@Override
	public void setValue(Data.Value value) {    
	}

    @Override
    public void getValue(Callback<String> doneSaveCallback) { 
    }

    @Override
    public void setValue(final String id) {
        if (id != null) {
            getCluNameCallback.exec(id);
        }        
    }

    public void setLabelContent(String id, final String code) {
        layout.clear();
        previousProgramId = id;
        previousProgramCode = new KSLabel(code);
        layout.add(previousProgramCode);
        createAndAddProgramTypesDropdown();
    }

    public void addGetCluNameCallback(Callback callback) {
        this.getCluNameCallback = callback;    
    }

    @Override
    public Data.Value getValue() {
        Data.Value pickerValue = programWidget.getValue();
        if ((pickerValue.toString().isEmpty()) && (previousProgramCode != null) && (!previousProgramCode.getText().isEmpty())) {
            return new Data.StringValue(previousProgramId);
        }

        return pickerValue;
    }
}
