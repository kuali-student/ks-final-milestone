package org.kuali.student.common.ui.client.configurable.mvc;

import org.kuali.student.common.ui.client.configurable.mvc.Section.FieldLabelType;
import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.common.ui.client.mvc.Model;
import org.kuali.student.common.ui.client.mvc.dto.ModelDTO;

import com.google.gwt.user.client.ui.FlowPanel;

public class HorizontalNestedSection extends NestedSection{
    
    protected final FlowPanel panel = new FlowPanel();
    protected RowDescriptor row = new RowDescriptor();
    
    public HorizontalNestedSection(){
        super.initWidget(row);
        row.setCurrentFieldLabelType(FieldLabelType.LABEL_TOP);
        rows.add(row);
    }
    
    
    @Override
    public void updateModel(Model<ModelDTO> model) {
        ModelDTO modelDTO = model.get();
        for (int i=0; i < fields.size(); i++){
            FieldDescriptor field = (FieldDescriptor)fields.get(i);
            field.getPropertyBinding().setValue(modelDTO, field.getWidgetBinding().getValue(field.getFieldWidget()));
        }
        for(NestedSection s: sections){
            s.updateModel(model);
        }
    }

    @Override
    public void clear() {
        // TODO bsmith - THIS METHOD NEEDS JAVADOCS
        
    }
    
    @Override
    public void addField(FieldDescriptor fieldDescriptor) {
        super.addField(fieldDescriptor);
        row.addField(fieldDescriptor);
        
    }

    @Override
    public void addSection(NestedSection section) {
        super.addSection(section);
        row.addSection(section);

    }

    @Override
    public void redraw() {
        panel.clear();
        panel.add(sectionTitleLabel);
        panel.add(instructionsLabel);
        for(NestedSection ns: sections){
            ns.redraw();
        }
        panel.add(row);

    }


    @Override
    public void updateView(Model<ModelDTO> model) {
        ModelDTO modelDTO = model.get();
        for (int i=0; i < fields.size(); i++){
            FieldDescriptor field = (FieldDescriptor)fields.get(i);
            field.getWidgetBinding().setValue(field.getFieldWidget(), field.getPropertyBinding().getValue(modelDTO));
        }
        for(NestedSection s: sections){
            s.updateView(model);
        }
    }


    @Override
    public void validate(Callback<org.kuali.student.core.validation.dto.ValidationResultInfo.ErrorLevel> callback) {
        // TODO bsmith - THIS METHOD NEEDS JAVADOCS
        
    }
}
