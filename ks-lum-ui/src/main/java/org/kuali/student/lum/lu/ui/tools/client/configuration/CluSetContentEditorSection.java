package org.kuali.student.lum.lu.ui.tools.client.configuration;

import java.util.List;

import org.kuali.student.common.ui.client.configurable.mvc.FieldDescriptor;
import org.kuali.student.common.ui.client.configurable.mvc.LayoutController;
import org.kuali.student.common.ui.client.configurable.mvc.SectionTitle;
import org.kuali.student.common.ui.client.configurable.mvc.binding.ModelWidgetBinding;
import org.kuali.student.common.ui.client.configurable.mvc.sections.BaseSection;
import org.kuali.student.common.ui.client.configurable.mvc.sections.Section;
import org.kuali.student.common.ui.client.configurable.mvc.sections.ValidationMessagePanel;
import org.kuali.student.common.ui.client.widgets.field.layout.FieldElement;
import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.common.ui.client.mvc.DataModel;
import org.kuali.student.common.ui.client.mvc.ModelRequestCallback;
import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.common.ui.client.widgets.list.KSCheckBoxList;
import org.kuali.student.common.ui.client.widgets.list.SelectionChangeEvent;
import org.kuali.student.common.ui.client.widgets.list.SelectionChangeHandler;
import org.kuali.student.common.ui.client.widgets.list.impl.SimpleListItems;
import org.kuali.student.core.assembly.data.Data;
import org.kuali.student.core.assembly.data.QueryPath;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.orch.CluSetHelper;
import org.kuali.student.lum.lu.ui.tools.client.widgets.AddCluLightBox;
import org.kuali.student.lum.lu.ui.tools.client.widgets.WarningDialog;
import org.kuali.student.lum.lu.ui.tools.client.widgets.itemlist.ItemListFieldBinding;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class CluSetContentEditorSection extends BaseSection {
    
    private VerticalPanel layout = new VerticalPanel();
    private CluSetEditOptionList cluSetEditOptions;
    private AddCluLightBox addApprovedCourseLightBox;
    
    
    
    public CluSetContentEditorSection() {
        init();
    }
    
    public CluSetContentEditorSection(SectionTitle title) {
        this.sectionTitle = title;
        this.instructions.setVisible(false);
        layout.add(this.instructions);
        layout.add(this.message);
        init();
    }
    
    public CluSetContentEditorSection(SectionTitle title, String instructions){
        this.sectionTitle = title;
        this.setInstructions(instructions);
        layout.add(this.instructions);
        layout.add(this.message);
        init();
    }
    
    private void init() {
        layout.add(this.sectionTitle);
        this.initWidget(layout);
        layout.setStyleName("ks-form-module");
        sectionTitle.addStyleName("ks-heading-page-section");
        cluSetEditOptions = new CluSetEditOptionList();
        cluSetEditOptions.addSelectionChangeHandler(new SelectionChangeHandler() {
            @Override
            public void onSelectionChange(SelectionChangeEvent event) {
                getModel(new Callback<DataModel>() {
                    @Override
                    public void exec(final DataModel dataModel) {
                        for(final FieldDescriptor f: fields){
                            // TODO distinguish between Approved CLUs and Proposed CLUs
                            // TODO implement course ranges
                            if (f.getFieldKey().equals(ToolsConstants.CLU_SET_CLUS_FIELD) ||
//                                  f.getFieldKey().equals(ToolsConstants.CLU_SET_CLUS_FIELD) ||
                                    f.getFieldKey().equals(ToolsConstants.CLU_SET_CLU_SETS_FIELD) ||
                                    f.getFieldKey().equals("CourseRanges")) {
                                final String path = f.getFieldKey();
                                final QueryPath qPath = QueryPath.parse(path);
                                Data valueData = dataModel.get(qPath);
                                final List<?> items = 
                                    ((ItemListFieldBinding<?>) 
                                            f.getModelWidgetBinding()).getItemList(valueData);
                                // Warn the user if check box is being unchecked but there are items associated with
                                // the edit option 
                                if (!cluSetEditOptions.getSelectedItems().contains(f.getFieldKey()) &&
                                        items != null && !items.isEmpty()) {
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
                                                redraw();
                                            }
                                        });
                                        warningDialog.show();
                                    }
                                }
                            }
                        }
                        redraw();
                    }
                });
            }
        });
    }
    
    @Override
    protected void addFieldToLayout(FieldDescriptor fieldDescriptor) {
        FieldElement field = new FieldElement(fieldDescriptor);
        FlowPanel fieldContainer = new FlowPanel();
        FlowPanel fieldLayout = new FlowPanel();
        fieldContainer.add(field);
        ValidationMessagePanel validationPanel = new ValidationMessagePanel();
        fieldLayout.add(fieldContainer);
        fieldLayout.add(validationPanel);
        FieldInfo info = new FieldInfo(fieldDescriptor.getFieldLabel(), validationPanel, field, fieldLayout);
        pathFieldInfoMap.put(fieldDescriptor.getFieldKey(), info);
        layout.add(fieldLayout);
        fieldLayout.setStyleName("ks-form-module-group");
        fieldLayout.addStyleName("clearfix");
        fieldContainer.setStyleName("ks-form-module-fields");
    }
    
    @Override
    protected void removeSectionFromLayout(BaseSection section) {
        layout.remove(section);
    }

    @Override
    protected void addSectionToLayout(BaseSection section) {
        layout.add(section);
    }
    
    private void getModel(final Callback<DataModel> callback) {
        LayoutController controller = LayoutController.findParentLayout(this);
        if (controller != null) {
            controller.requestModel(new ModelRequestCallback<DataModel>() {

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
    public void redraw() {
        getModel(new Callback<DataModel>() {
            @Override
            public void exec(final DataModel dataModel) {
                layout.clear();
                layout.add(sectionTitle);
                layout.add(instructions);
                layout.add(message);
                for(Section ns: sections){
                    ns.redraw();
                    addSectionToLayout((BaseSection)ns);
                }
                layout.add(cluSetEditOptions);
                
                for(final FieldDescriptor f: fields){
                    // TODO distinguish between Approved CLUs and Proposed CLUs
                    // TODO implement course ranges
                    if (f.getFieldKey().equals(ToolsConstants.CLU_SET_CLUS_FIELD) ||
//                            f.getFieldKey().equals(ToolsConstants.CLU_SET_CLUS_FIELD) ||
                            f.getFieldKey().equals(ToolsConstants.CLU_SET_CLU_SETS_FIELD) ||
                            f.getFieldKey().equals("CourseRanges")) {
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

                        if (isFieldBeingEdited(f, dataModel)) {
                            // if the field being edited is add courses
                            if (f.getFieldKey().equals(ToolsConstants.CLU_SET_CLUS_FIELD)) {
                                if (addApprovedCourseLightBox != null) {
                                    KSLabel addCluLink = new KSLabel("Add Course");
                                    addCluLink.setStyleName("KS-Rules-Search-Link");
                                    addFieldToLayout(f);
                                    layout.add(addCluLink);
                                    addCluLink.addClickHandler(new ClickHandler() {
                                        @Override
                                        public void onClick(ClickEvent event) {
                                            addApprovedCourseLightBox.clearSelections();
                                            addApprovedCourseLightBox.show();
                                        }
                                    });
                                }
                            }
                            if (f.getFieldKey().equals(ToolsConstants.CLU_SET_CLU_SETS_FIELD)) {
                                Hyperlink addLink = new Hyperlink("Add CLU Set", "addCluSet");
                                addFieldToLayout(f);
                                layout.add(addLink);
                            }
                        }
                    } else {
                        addFieldToLayout(f);
                    }
                }
                //Fire validation request here?
            }
        });
        
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
    
    @Override
    public void clear() {
        
    }

    @Override
    protected void addWidgetToLayout(Widget w) {
        layout.add(w);
    }
    
    public class CluSetEditOptionList extends KSCheckBoxList{
        public CluSetEditOptionList(){
            SimpleListItems editOptions = new SimpleListItems();

            editOptions.addItem(ToolsConstants.CLU_SET_CLUS_FIELD, "Approved Courses");
            // TODO distinguish between Approved CLUs and Proposed CLUs
            // TODO implement course ranges
//            editOptions.addItem("ProposedCourses", "Proposed Courses");
            editOptions.addItem(ToolsConstants.CLU_SET_CLU_SETS_FIELD, "CLU Sets");
            editOptions.addItem("CourseRanges", "Course Ranges (Course numbers, common learning objectives, etc)");

            super.setListItems(editOptions);
        }
    }
    
    public void setAddApprovedCourseWidget(AddCluLightBox addApprovedCourseLightBox) {
        this.addApprovedCourseLightBox = addApprovedCourseLightBox;
    }
    
}
