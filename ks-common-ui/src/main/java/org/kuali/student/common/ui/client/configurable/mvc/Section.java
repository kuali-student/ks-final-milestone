/*
 * Copyright 2009 The Kuali Foundation Licensed under the
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
package org.kuali.student.common.ui.client.configurable.mvc;

import java.util.ArrayList;
import java.util.List;

import org.kuali.student.common.ui.client.configurable.mvc.binding.SectionBinding;
import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.common.ui.client.mvc.DataModel;
import org.kuali.student.common.ui.client.mvc.dto.ModelDTO;
import org.kuali.student.common.ui.client.mvc.dto.ModelDTOValue;
import org.kuali.student.common.ui.client.widgets.KSRequiredMarker;
import org.kuali.student.common.ui.client.widgets.layout.HorizontalBlockFlowPanel;
import org.kuali.student.core.validation.dto.ValidationResultContainer;
import org.kuali.student.core.validation.dto.ValidationResultInfo;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public abstract class Section extends Composite implements ConfigurableLayoutSection{

    protected SectionTitle sectionTitle = SectionTitle.generateEmptyTitle();
    protected final Label instructionsLabel = new Label();
    protected LayoutController layoutController = null;

    public enum FieldLabelType{LABEL_TOP, LABEL_LEFT}
    public enum SectionTitleType{DECORATED_TITLE_BAR, TITLE_TOP, TITLE_LEFT, NONE}

    protected ArrayList<FieldDescriptor> fields = new ArrayList<FieldDescriptor>();
    protected ArrayList<Section> sections = new ArrayList<Section>();

    protected List<RowDescriptor> rows = new ArrayList<RowDescriptor>();

    private RequiredEnum requiredState = RequiredEnum.NOT_MARKED;
    private FieldLabelType labelType = FieldLabelType.LABEL_TOP;

    protected HorizontalBlockFlowPanel generateTitlePanel() {
        HorizontalBlockFlowPanel titlePanel = new HorizontalBlockFlowPanel();
        titlePanel.add(sectionTitle);
        titlePanel.add(new KSRequiredMarker(requiredState));
        return titlePanel;
    }

    public RequiredEnum getRequiredState() {
        return requiredState;
    }

    public void setRequiredState(RequiredEnum requiredState) {
        this.requiredState = requiredState;
        for(FieldDescriptor f: fields){
            if(this.getRequiredState() == RequiredEnum.REQUIRED){
                //if top level is required remove required from sub fields, stop required * from being shown
                if(f.getRequiredState() == RequiredEnum.REQUIRED){
                    f.setRequiredState(RequiredEnum.NOT_MARKED);
                }
            }
            else if(this.getRequiredState() == RequiredEnum.OPTIONAL){
                if(f.getRequiredState() == RequiredEnum.OPTIONAL){
                    f.setRequiredState(RequiredEnum.NOT_MARKED);
                }
            }
        }

        for(Section s: sections){
            if(this.getRequiredState() == RequiredEnum.REQUIRED){
                //if top level is required remove required from sub fields, stop required * from being shown
                if(s.getRequiredState() == RequiredEnum.REQUIRED){
                    s.setRequiredState(RequiredEnum.NOT_MARKED);
                }
            }
            else if(this.getRequiredState() == RequiredEnum.OPTIONAL){
                if(s.getRequiredState() == RequiredEnum.OPTIONAL){
                    s.setRequiredState(RequiredEnum.NOT_MARKED);
                }
            }
        }
    }

    public void setFieldHasHadFocusFlags(boolean hadFocus) {
        for(FieldDescriptor f: fields){
            f.setHasHadFocus(hadFocus);
        }

        for(Section s: sections){
            s.setFieldHasHadFocusFlags(hadFocus);
        }

    }

    public void resetFieldInteractionFlags() {
        for(FieldDescriptor f: fields){
            f.setDirty(false);
            f.setHasHadFocus(false);
        }

        for(Section s: sections){
            s.resetFieldInteractionFlags();
        }

    }

    @Override
    public void addSection(Section section) {
        //Required logic
        if(this.getRequiredState() == RequiredEnum.REQUIRED){
            //if top level is required remove required from sub fields, stop required * from being shown
            if(section.getRequiredState() == RequiredEnum.REQUIRED){
                section.setRequiredState(RequiredEnum.NOT_MARKED);
            }
        }
        else if(this.getRequiredState() == RequiredEnum.OPTIONAL){
            if(section.getRequiredState() == RequiredEnum.OPTIONAL){
                section.setRequiredState(RequiredEnum.NOT_MARKED);
            }
        }

        sections.add(section);
        addSectionToLayout(section);
    }

    protected void addSectionToLayout(Section section){
        RowDescriptor row = new RowDescriptor();
        row.addSection(section);
        rows.add(row);
    }

    @Override
    public void addField(final FieldDescriptor fieldDescriptor) {
        //Required logic
        if(this.getRequiredState() == RequiredEnum.REQUIRED){
            //if top level is required remove required from sub fields, stop required * from being shown
            if(fieldDescriptor.getRequiredState() == RequiredEnum.REQUIRED){
                fieldDescriptor.setRequiredState(RequiredEnum.NOT_MARKED);
            }
        }
        else if(this.getRequiredState() == RequiredEnum.OPTIONAL){
            if(fieldDescriptor.getRequiredState() == RequiredEnum.OPTIONAL){
                fieldDescriptor.setRequiredState(RequiredEnum.NOT_MARKED);
            }
        }

        fields.add(fieldDescriptor);

        addFieldToLayout(fieldDescriptor);

        //If fieldDescriptor.getFieldWidget() is not implementing the
        //HasBlurHandlers. binding.bind does not do the bind.
        // how to deal with the special case
        // FIXME wilj: validation needs to be reworked to use new model before this bug can be fixed
        if (true) {
            // for now just skip out
            // I could just comment out the code, but then I wouldn't get the warning to fix it during refactor
            return;
        }
        ValidationEventBinding binding = new LostFocusValidationEventBinding();
        if(fieldDescriptor.getValidationRequestCallback()== null){
            fieldDescriptor.setValidationCallBack(new Callback<Boolean>() {
                @Override
                public void exec(Boolean result) {
                    PropertyBinding pBinding = fieldDescriptor.getPropertyBinding();
                    PropertyBinding wBinding = fieldDescriptor.getWidgetBinding();
                    ModelDTO model = LayoutController.findParentLayout(fieldDescriptor.getFieldWidget()).getModel();
                    if (wBinding != null){
                        Widget w = fieldDescriptor.getFieldWidget();
                        pBinding.setValue(model, wBinding.getValue(w));
                        GWT.log(model.toString(), null);
                    } else {
                        GWT.log(fieldDescriptor.getFieldKey() + " has no widget binding.", null);
                    }
                    LayoutController.findParentLayout(fieldDescriptor.getFieldWidget()).validate(Section.this);
                }
            });
        }
        binding.bind(fieldDescriptor);


    }

    protected void addFieldToLayout(FieldDescriptor fieldDescriptor){
        RowDescriptor row = new RowDescriptor();
        row.setCurrentFieldLabelType(this.labelType);
        row.addField(fieldDescriptor);
        rows.add(row);
    }

    @Override
    public void addWidget(Widget widget) {
        RowDescriptor row = new RowDescriptor();
        row.addWidget(widget);
        rows.add(row);
    }

    @Override
    public SectionTitle getSectionTitle() {
        return sectionTitle;
    }

    @Override
    public void setSectionTitle(SectionTitle sectionTitle) {
        this.sectionTitle = sectionTitle;
    }

    @Override
    public String getInstructions() {
        return instructionsLabel.getText();
    }

    @Override
    public void setInstructions(String instructions) {
        instructionsLabel.setText(instructions);
    }

    @Override
    public List<FieldDescriptor> getFields() {
        List<FieldDescriptor> allFields = new ArrayList<FieldDescriptor>();
        allFields.addAll(fields);
        for(Section ns: sections){
            allFields.addAll(ns.getFields());
        }
        return allFields;
    }

    public abstract void validate(Callback<ValidationResultInfo.ErrorLevel> callback);

    public void updateModel(ModelDTO modelDTO){
        for (int i=0; i < fields.size(); i++){
            FieldDescriptor field = (FieldDescriptor)fields.get(i);
            if (field.getFieldWidget() instanceof HasModelDTOValue){
                ((HasModelDTOValue) field.getFieldWidget()).updateModelDTOValue();
            }

            PropertyBinding pBinding = field.getPropertyBinding();
            PropertyBinding wBinding = field.getWidgetBinding();
            if (wBinding != null){
                Widget w = field.getFieldWidget();
                pBinding.setValue(modelDTO, wBinding.getValue(w));
            } else {
                GWT.log(field.getFieldKey() + " has no widget binding.", null);
            }

        }
        for(Section s: sections){
            s.updateModel(modelDTO);
        }
    }

    public void updateView(ModelDTO modelDTO) {
        for (int i=0; i < fields.size(); i++){
            FieldDescriptor field = (FieldDescriptor)fields.get(i);
            if (field.getFieldWidget() instanceof HasModelDTOValue){
                ModelDTOValue value = modelDTO.get(field.getFieldKey());
                ((HasModelDTOValue) field.getFieldWidget()).setValue(value);
            } else {
                // HACK - _might_ be a hack, particularly if we have nested Multiplicities,
                // but before this else the code below was executed for SimpleMultiplicityComposite's,
                // effectively setting their value twice and wiping out the blank LoInfo-related data
                // from the backing ModelDTOValue
                PropertyBinding pBinding = field.getPropertyBinding();
                PropertyBinding wBinding = field.getWidgetBinding();
                if (wBinding != null){
                    Widget w = field.getFieldWidget();
                    wBinding.setValue(w, pBinding.getValue(modelDTO));
                } else {
                    GWT.log(field.getFieldKey() + " has no widget binding.", null);
                }
            }
        }
        for(Section s: sections){
            s.updateView(modelDTO);
        }
    }

    public void updateModel(DataModel model){
        SectionBinding.INSTANCE.setModelValue(this, model, "");
    }

    public void updateView(DataModel model) {
        SectionBinding.INSTANCE.setWidgetValue(this, model, "");
    }

    public void clear(){
        for (Section s:sections){
            s.clear();
        }

        for (RowDescriptor rd:rows){
            rd.clear();
        }
    }


    public abstract void redraw();

    public void processValidationResults(List<ValidationResultContainer> results) {
        for(RowDescriptor r: rows){
            r.clearValidationMessages();
            for(FieldDescriptor f: r.getFields()){
                if(f.hasHadFocus()){
                    for(ValidationResultContainer vc: results){
                        if(vc.getElement().equals(f.getFieldKey())){
                            r.setValidationMessages(vc.getValidationResults());
                        }
                    }

                }
            }
        }

        for(Section s: sections){
            s.processValidationResults(results);
        }
    }

    public void clearValidationMessages(){
        for(RowDescriptor r: rows){
            r.clearValidationMessages();
        }

        for(Section s: sections){
            s.clearValidationMessages();
        }
    }

    public FieldLabelType getLabelType() {
        return labelType;
    }

    public void setLabelType(FieldLabelType labelType) {
        this.labelType = labelType;
    }

    @Override
    public LayoutController getLayoutController() {
        return this.layoutController;
    }

    @Override
    public void setLayoutController(LayoutController controller) {
        this.layoutController = controller;
    }

    public ArrayList<Section> getSections() {
        return sections;
    }



}
