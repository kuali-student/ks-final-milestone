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

package org.kuali.student.lum.lu.ui.tools.client.configuration;

import java.util.List;

import org.kuali.student.common.ui.client.configurable.mvc.FieldDescriptor;
import org.kuali.student.common.ui.client.configurable.mvc.LayoutController;
import org.kuali.student.common.ui.client.configurable.mvc.SectionTitle;
import org.kuali.student.common.ui.client.configurable.mvc.binding.ModelWidgetBinding;
import org.kuali.student.common.ui.client.configurable.mvc.sections.BaseSection;
import org.kuali.student.common.ui.client.configurable.mvc.sections.Section;
import org.kuali.student.common.ui.client.configurable.mvc.sections.ValidationMessagePanel;
import org.kuali.student.common.ui.client.configurable.mvc.sections.VerticalSection;
import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.common.ui.client.mvc.DataModel;
import org.kuali.student.common.ui.client.mvc.ModelRequestCallback;
import org.kuali.student.common.ui.client.widgets.field.layout.FieldElement;
import org.kuali.student.common.ui.client.widgets.list.KSCheckBoxList;
import org.kuali.student.common.ui.client.widgets.list.SelectionChangeEvent;
import org.kuali.student.common.ui.client.widgets.list.SelectionChangeHandler;
import org.kuali.student.common.ui.client.widgets.list.impl.SimpleListItems;
import org.kuali.student.core.assembly.data.Data;
import org.kuali.student.core.assembly.data.QueryPath;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.orch.CluSetHelper;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.orch.CluSetRangeHelper;
import org.kuali.student.lum.lu.ui.tools.client.widgets.WarningDialog;
import org.kuali.student.lum.lu.ui.tools.client.widgets.itemlist.ItemListFieldBinding;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class CluSetContentEditorSection extends VerticalSection {

    private CluSetEditOptionList cluSetEditOptions;
//    private AddCluLightBox addApprovedCourseLightBox;
    private String modelId;


    public CluSetContentEditorSection(String modelId) {
    	super();
        init(modelId);
    }

    public CluSetContentEditorSection(SectionTitle title, String modelId) {
        super(title);
        init(modelId);
    }


    private void init(String modelId) {
        this.modelId = modelId;
        cluSetEditOptions = new CluSetEditOptionList();
        layout.add(cluSetEditOptions);
        cluSetEditOptions.addSelectionChangeHandler(new SelectionChangeHandler() {
            @Override
            public void onSelectionChange(SelectionChangeEvent event) {
                getModel(new Callback<DataModel>() {
                    @Override
                    public void exec(final DataModel dataModel) {
                        for(final FieldDescriptor f: fields){
                            // TODO distinguish between Approved CLUs and Proposed CLUs
                            // TODO implement course ranges
                            if (f.getFieldKey().equals(ToolsConstants.CLU_SET_CLU_SETS_FIELD)) {
                            } else if (f.getFieldKey().equals(ToolsConstants.CLU_SET_CLUS_FIELD)
//                                  f.getFieldKey().equals(ToolsConstants.CLU_SET_CLUS_FIELD) ||
                                    ) {
                                final String path = f.getFieldKey();
                                final QueryPath qPath = QueryPath.parse(path);
                                Data clusData = dataModel.get(qPath);
                                // Warn the user if check box is being unchecked but there are items associated with
                                // the edit option
                                if (!cluSetEditOptions.getSelectedItems().contains(f.getFieldKey()) &&
                                        clusNotEmpty(clusData)) {
                                    if (f.getFieldKey().equals(ToolsConstants.CLU_SET_CLUS_FIELD)) {
                                        final WarningDialog warningDialog = new WarningDialog(
                                                "Delete Courses",
                                                "You are about to delete the courses in this CLU Set.",
                                                "Do you want to continue", "Delete", "Cancel");
                                        warningDialog.addConfirmationCallback(new Callback<Boolean>() {
                                            @Override
                                            public void exec(Boolean result) {
                                                ModelWidgetBinding binding = f.getModelWidgetBinding();
                                                if (result.booleanValue()) {
                                                    Data valueData = dataModel.get(qPath);
                                                    CluSetHelper cluSetHelper = CluSetHelper.wrap(valueData.getParent());
                                                    cluSetHelper.setClus(null);
                                                }
                                                warningDialog.hide();
                                                binding.setWidgetValue(
                                                        f.getFieldWidget(), dataModel, path);
                                                cluSetEditOptions.deSelectItem(ToolsConstants.CLU_SET_CLUS_FIELD);
                                                updateWidgetData(dataModel);
                                            }
                                        });
                                        warningDialog.show();
                                    }
                                }
                            } else if (f.getFieldKey().equals(ToolsConstants.CLU_SET_CLU_SET_RANGE_FIELD)) {
                                final String path = f.getFieldKey();
                                final QueryPath qPath = QueryPath.parse(path);
                                Data valueData = dataModel.get(qPath);
                                CluSetRangeHelper cluSetRangeHelper = CluSetRangeHelper.wrap(valueData);
                                if (!cluSetEditOptions.getSelectedItems().contains(f.getFieldKey())) {
                                    
                                }
                            }
                        }
                        updateWidgetData(dataModel);
                    }
                });
            }
        });
    }

    private void getModel(final Callback<DataModel> callback) {
        LayoutController controller = LayoutController.findParentLayout(this);
        if (controller != null) {
            controller.requestModel(modelId, new ModelRequestCallback<DataModel>() {

                @Override
                public void onModelReady(DataModel model) {
                    callback.exec(model);
                }

                @Override
                public void onRequestFail(Throwable cause) {
                    GWT.log("CluSetContentEditorSection: Unable to retrieve model ", cause);
                }

            });
        }
    }

    @Override
    public void updateWidgetData(DataModel dataModel) {
           	super.updateWidgetData(dataModel);
                
                for(final FieldDescriptor f: fields){
                	layout.removeLayoutElement(f.getFieldElement());
                    // TODO distinguish between Approved CLUs and Proposed CLUs
                    // TODO implement course ranges
                    if (f.getFieldKey().equals(ToolsConstants.CLU_SET_CLU_SETS_FIELD)) {
                        String path = f.getFieldKey();
                        final QueryPath qPath = QueryPath.parse(path);
                        Data valueData = dataModel.get(qPath);
                        final List<?> items =
                            ((ItemListFieldBinding<?>)
                                    f.getModelWidgetBinding()).getItemList(valueData);
                        // field is being edited if there are data in this field
                        if (items != null && !items.isEmpty()) {
                            cluSetEditOptions.selectItem(path);
                        }
                    } else if (f.getFieldKey().equals(ToolsConstants.CLU_SET_CLUS_FIELD)) {
                        String path = f.getFieldKey();
                        final QueryPath qPath = QueryPath.parse(path);
                        Data clusData = dataModel.get(qPath);
                        if (clusNotEmpty(clusData)) {
                            cluSetEditOptions.selectItem(path);
                        }
                        if (isFieldBeingEdited(f, dataModel)) {
                            // if the field being edited is add courses
                            if (f.getFieldKey().equals(ToolsConstants.CLU_SET_CLUS_FIELD)) {
                                layout.addField(f.getFieldElement());
                            }
                            if (f.getFieldKey().equals(ToolsConstants.CLU_SET_CLU_SETS_FIELD)) {
                                Hyperlink addLink = new Hyperlink("Add CLU Set", "addCluSet");
                                layout.addField(f.getFieldElement());
                                layout.addWidget(addLink);
                            }
                        }
                    } else if (f.getFieldKey().equals(ToolsConstants.CLU_SET_CLU_SET_RANGE_FIELD) ||
                            f.getFieldKey().equals(ToolsConstants.CLU_SET_CLU_SET_RANGE_EDIT_FIELD)) {
                        List<FieldDescriptor> fields = getFields();
                        FieldDescriptor cluSetRangeField = null;
                        if (fields != null) {
                            for (FieldDescriptor field : fields) {
                                if (field.getFieldKey().equals(ToolsConstants.CLU_SET_CLU_SET_RANGE_FIELD)) {
                                    cluSetRangeField = field;
                                }
                            }
                        }
                        if (isFieldBeingEdited(cluSetRangeField, dataModel)) {
                        	layout.addField(f.getFieldElement());
                        }
                    } else {
                    	layout.addField(f.getFieldElement());
                    }
                }

    }
    
    private boolean clusNotEmpty(Data clusData) {
        boolean clusExist = false;
        if (clusData != null) {
            for (Data.Property p : clusData) {
                if(!"_runtimeData".equals(p.getKey())){
                    String cluId = p.getValue();
                    if (cluId != null && !cluId.trim().isEmpty()) {
                        clusExist = true;
                        break;
                    }
                }
            }
        }
        return clusExist;
    }

    private boolean nullSafeEquals(String str1, String str2) {
        String tempStr1 = (str1 == null)? "" : str1;
        String tempStr2 = (str2 == null)? "" : str2;
        return (tempStr1.equals(tempStr2));
    }

    private boolean isFieldBeingEdited(FieldDescriptor f, DataModel dataModel) {
        boolean result = false;
        List<String> selectedEditOptionIds = cluSetEditOptions.getSelectedItems();
        if (selectedEditOptionIds != null) {
            for (String selectedEditOptionId : selectedEditOptionIds) {
                // field is being edited if the option is chosen by the user
                if (nullSafeEquals(f.getFieldKey(), selectedEditOptionId)) {
                    result = true;
                }
            }
        }
        return result;
    }

    public class CluSetEditOptionList extends KSCheckBoxList{
        public CluSetEditOptionList(){
            SimpleListItems editOptions = new SimpleListItems();

            editOptions.addItem(ToolsConstants.CLU_SET_CLUS_FIELD, "Courses");
            // TODO distinguish between Approved CLUs and Proposed CLUs
            // TODO implement course ranges
//            editOptions.addItem("ProposedCourses", "Proposed Courses");
            editOptions.addItem(ToolsConstants.CLU_SET_CLU_SETS_FIELD, "CLU Sets");
            editOptions.addItem(ToolsConstants.CLU_SET_CLU_SET_RANGE_FIELD, "Course Ranges (Course numbers, common learning objectives, etc)");

            super.setListItems(editOptions);
        }
    }

}
