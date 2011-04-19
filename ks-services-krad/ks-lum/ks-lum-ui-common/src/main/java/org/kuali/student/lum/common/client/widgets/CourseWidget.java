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
import org.kuali.student.common.ui.client.widgets.rules.AccessWidgetValue;
import org.kuali.student.common.ui.client.widgets.search.KSPicker;
import org.kuali.student.core.assembly.data.Data;
import org.kuali.student.core.assembly.data.Metadata;

import com.google.gwt.user.client.ui.Composite;

public class CourseWidget extends Composite implements AccessWidgetValue, HasDataValue {

    private CluSetRetriever courseMetadataRetriever  = new CluSetRetrieverImpl();
    private Callback getCluNameCallback;

    //widgets
    private VerticalSection layout = new VerticalSection();
    private KSDropDown courseTypeWidget;
    private KSPicker courseWidget = null;
    private KSLabel previousCourseCode;
    private String previousCourseId;

    //data
    private Metadata searchCourseMetadata = null;
    private BlockingTask initializeTask = new BlockingTask("Initializing");

    public CourseWidget() {
        this.initWidget(layout);
    }

    @Override
    public void initWidget(List<Metadata> fieldsMetadata) {

        previousCourseCode = null;
        previousCourseId = null;
        
        layout.clear();

        final VerticalSection choosingSection = new VerticalSection();
        choosingSection.addWidget(new KSLabel("<b>Add a course</b>"));

        //first metadata is a drop down to select type of course
        createAndAddCourseTypesDropdown();

        retrieveMetadata();
    }

    private void createAndAddCourseTypesDropdown() {
        courseTypeWidget = new KSDropDown();
        SimpleListItems courseTypes = new SimpleListItems();
        courseTypes.addItem(CommonWidgetConstants.CLU_SET_APPROVED_CLUS_FIELD, "Approved Courses");
        courseTypes.addItem(CommonWidgetConstants.CLU_SET_PROPOSED_CLUS_FIELD, "Proposed Courses");
        courseTypeWidget.setListItems(courseTypes);
        courseTypeWidget.addSelectionChangeHandler(new SelectionChangeHandler() {

            @Override
            public void onSelectionChange(SelectionChangeEvent event) {
                String courseTypeSelected = ((KSDropDownImpl)event.getWidget()).getSelectedItem();
                if (courseTypeSelected == null) {
                    if (courseWidget != null) {
                        layout.remove(courseWidget);
                    }                    
                    return;
                }
                addCourseListWidget(true, courseTypeSelected);
            }
        });        
        layout.add(courseTypeWidget);
    }

    private void retrieveMetadata() {
        if (searchCourseMetadata == null) {
            KSBlockingProgressIndicator.addTask(initializeTask);
            courseMetadataRetriever.getMetadata("courseSet", new Callback<Metadata>(){
                @Override
                public void exec(Metadata result) {
                    searchCourseMetadata = result;
                    searchCourseMetadata.getProperties().get(CommonWidgetConstants.CLU_SET_APPROVED_CLUS_FIELD).getConstraints().get(0).setMaxOccurs(1);
                    searchCourseMetadata.getProperties().get(CommonWidgetConstants.CLU_SET_PROPOSED_CLUS_FIELD).getConstraints().get(0).setMaxOccurs(1);
                    searchCourseMetadata.getProperties().get(CommonWidgetConstants.CLU_SET_APPROVED_CLUS_FIELD).getConstraints().get(0).setId("single");
                    searchCourseMetadata.getProperties().get(CommonWidgetConstants.CLU_SET_PROPOSED_CLUS_FIELD).getConstraints().get(0).setId("single");
                    KSBlockingProgressIndicator.removeTask(initializeTask);

                    if (previousCourseCode == null) {
                        courseTypeWidget.selectItem(CommonWidgetConstants.CLU_SET_APPROVED_CLUS_FIELD);
                        addCourseListWidget(true, courseTypeWidget.getSelectedItem());
                    }
                }
            });
        } else {
            if (previousCourseCode == null) {
                courseTypeWidget.selectItem(CommonWidgetConstants.CLU_SET_APPROVED_CLUS_FIELD);
                addCourseListWidget(true, courseTypeWidget.getSelectedItem());
            }
        }
    }

    private void addCourseListWidget(boolean enabled, String courseType) {
        if (courseWidget != null) {
            layout.remove(courseWidget);
        }
        if (previousCourseCode != null) {
            layout.remove(previousCourseCode);
            previousCourseCode = null;
            previousCourseId = null;
        }
        courseWidget = (KSPicker) DefaultWidgetFactory.getInstance().getWidget(searchCourseMetadata.getProperties().get(courseType));
        courseWidget.getSearchPanel().setMutipleSelect(false);
    //    ((KSSuggestBox) courseWidget.getInputWidget()).setEnabled(enabled);
        layout.add(courseWidget);
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
        previousCourseId = id;
        previousCourseCode = new KSLabel(code);
        layout.add(previousCourseCode);
        createAndAddCourseTypesDropdown();
    }

    public void addGetCluNameCallback(Callback callback) {
        this.getCluNameCallback = callback;    
    }

    @Override
    public Data.Value getValue() {
        Data.Value pickerValue = courseWidget.getValue();
        if ((pickerValue.toString().isEmpty()) && (previousCourseCode != null) && (!previousCourseCode.getText().isEmpty())) {
            return new Data.StringValue(previousCourseId);
        }

        return pickerValue;
    }
}
