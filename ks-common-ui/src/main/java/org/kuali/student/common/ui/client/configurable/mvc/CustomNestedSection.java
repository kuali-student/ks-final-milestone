package org.kuali.student.common.ui.client.configurable.mvc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.common.ui.client.mvc.Model;
import org.kuali.student.common.ui.client.mvc.dto.ModelDTO;
import org.kuali.student.common.ui.client.mvc.dto.ModelDTOValue;
import org.kuali.student.common.ui.client.mvc.dto.ModelDTOValueBinder;
import org.kuali.student.common.ui.client.widgets.KSButton;
import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.common.ui.client.widgets.KSStyles;
import org.kuali.student.common.ui.client.widgets.forms.KSFormField;
import org.kuali.student.common.ui.client.widgets.list.KSSelectItemWidgetAbstract;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.InlineHTML;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class CustomNestedSection extends NestedSection{
    
    
    public enum SectionTitleType{DECORATED_TITLE_BAR, TITLE_TOP, TITLE_LEFT, NONE}
    public enum FieldLabelType{LABEL_TOP, LABEL_LEFT}
    //private VerticalPanel panel = new VerticalPanel();
    private FlowPanel panel = new FlowPanel();
    private List<RowDescriptor> rows = new ArrayList<RowDescriptor>();
    private RowDescriptor currentRow = new RowDescriptor();
    
    private SectionTitleType titleType = SectionTitleType.DECORATED_TITLE_BAR;
    private FieldLabelType currentFieldLabelType = FieldLabelType.LABEL_LEFT;
    
    public CustomNestedSection(){
        super.initWidget(panel);
    }
    
    private class RowDescriptor extends Composite{
        private HorizontalPanel rowPanel = new HorizontalPanel();
        private List<FieldDescriptor> fields = new ArrayList<FieldDescriptor>();
        private List<NestedSection> sections = new ArrayList<NestedSection>();
        
        public RowDescriptor(){
            this.initWidget(rowPanel);
        }
        
        public void addSection(NestedSection section){
            sections.add(section);
            rowPanel.add(section);
        }
        
        public void addField(FieldDescriptor fieldDescriptor){
            fields.add(fieldDescriptor);
            KSLabel label = new KSLabel(fieldDescriptor.getFieldLabel());
            label.addStyleName(KSStyles.KS_FORMLAYOUT_LABEL);
            if(currentFieldLabelType == FieldLabelType.LABEL_LEFT){
                rowPanel.add(label);
                rowPanel.add(fieldDescriptor.getFieldWidget());
            }
            else if(currentFieldLabelType == FieldLabelType.LABEL_TOP){
                FlowPanel vp = new FlowPanel();
                vp.add(label);
                vp.add(fieldDescriptor.getFieldWidget());
                rowPanel.add(vp);
            }
        }

        public List<FieldDescriptor> getFields() {
            return fields;
        }

        public List<NestedSection> getSections() {
            return sections;
        }   
    }
    
    
    
/*    public class Field extends Composite{
        
        private FieldDescriptor descriptor;
        private FieldLabelType fieldLabelType;
        private SimplePanel panel = new SimplePanel();
        
        public Field(FieldDescriptor descriptor){
            this.descriptor = descriptor;
            this.fieldLabelType = currentFieldLabelType;
            super.initWidget(panel);
            redraw();
        }
        
        public void redraw(){
            panel.clear();
            if(fieldLabelType == FieldLabelType.LABEL_LEFT){
                HorizontalPanel hp = new HorizontalPanel();
                KSLabel label = new KSLabel(descriptor.getFieldLabel());
                hp.add(label);
                hp.add(descriptor.getFieldWidget());
                panel.setWidget(hp);
            }
            else if(fieldLabelType == FieldLabelType.LABEL_TOP){
                
            }
        }
    }*/
    
    public SectionTitleType getTitleType() {
        return titleType;
    }

    public void setTitleType(SectionTitleType titleType) {
        this.titleType = titleType;
    }

    public FieldLabelType getCurrentFieldLabelType() {
        return currentFieldLabelType;
    }

    public void setCurrentFieldLabelType(FieldLabelType currentFieldLabelType) {
        this.currentFieldLabelType = currentFieldLabelType;
    }

    public void nextRow(){
        rows.add(currentRow);
        currentRow = new RowDescriptor();
    }

    @Override
    public void clear() {
        // TODO bsmith - THIS METHOD NEEDS JAVADOCS
        
    }
    
    @Override
    public void addField(FieldDescriptor fieldDescriptor) {
        super.addField(fieldDescriptor);
        currentRow.addField(fieldDescriptor);
    }

    @Override
    public void addSection(NestedSection section) {
        super.addSection(section);
        currentRow.addSection(section);
    }

    @Override
    public void redraw() {
        
        //Add a "REAL" redraw/recreate capability here?  Not needed at the current time
        panel.clear();
        //TODO add title type check here
        panel.add(sectionTitleLabel);
        panel.add(instructionsLabel);
        for(NestedSection ns: sections){
            ns.redraw();
        }
        if(currentRow.getFields().size() != 0 || currentRow.getSections().size() != 0){
            rows.add(currentRow);
        }
        for(RowDescriptor r: rows){
            panel.add(r);
        }
        
        
    }
/*    
    @SuppressWarnings("unchecked")
    private Object getFieldValue(String fieldKey) {
        Object result = null;
        Widget fieldWidget = fieldWidgetMap.get(fieldKey);
        if (fieldWidget instanceof HasValue) {
            result = ((HasValue)fieldWidget).getValue();
        } else if (fieldWidget instanceof KSSelectItemWidgetAbstract){
            result = ((KSSelectItemWidgetAbstract)fieldWidget).getSelectedItem();
        } else if (fieldWidget instanceof HasText){
            result = ((HasText)fieldWidget).getText();
        }
        return result;
    }*/

    @Override
    public void updateModel(Model<ModelDTO> model) {
        ModelDTO modelDTO = model.get();
        for (int i=0; i < fields.size(); i++){
            FieldDescriptor field = (FieldDescriptor)fields.get(i);
            field.getPropertyBinding().setValue(modelDTO, field.getWidgetBinding().getValue(field.getFieldWidget()));
        }
/*        for (int i=0; i < fields.size(); i++){
            FieldDescriptor field = (FieldDescriptor)fields.get(i);
            String fieldKey = field.getFieldKey();
            ModelDTOValue modelDTOValue = modelDTO.get(fieldKey);
            if (modelDTOValue  != null){
                ModelDTOValueBinder.copyValueToModelDTO(this.getFieldValue(fieldKey), modelDTOValue);
            } else {
                modelDTOValue = ModelDTOValueBinder.createModelDTOInstance(this.getFieldValue(fieldKey), field.getFieldType());
                modelDTO.put(fieldKey, modelDTOValue);
            }
        }*/
        for(NestedSection s: sections){
            s.updateModel(model);
        }
        
    }

    @Override
    public void updateView(Model<ModelDTO> model) {
        ModelDTO modelDTO = model.get();
        for (int i=0; i < fields.size(); i++){
            FieldDescriptor field = (FieldDescriptor)fields.get(i);
            field.getWidgetBinding().setValue(field.getFieldWidget(), field.getPropertyBinding().getValue(modelDTO));
        }
        for(NestedSection s: sections){
            s.updateModel(model);
        }
    }

    @Override
    public void validate(Callback<org.kuali.student.core.validation.dto.ValidationResultInfo.ErrorLevel> callback) {
        // TODO bsmith - THIS METHOD NEEDS JAVADOCS
        
    }
    
    

}
