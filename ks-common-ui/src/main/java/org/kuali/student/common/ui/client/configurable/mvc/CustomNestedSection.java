package org.kuali.student.common.ui.client.configurable.mvc;

import org.kuali.student.common.ui.client.configurable.mvc.Section.FieldLabelType;
import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.common.ui.client.mvc.Model;
import org.kuali.student.common.ui.client.mvc.dto.ModelDTO;

import com.google.gwt.user.client.ui.FlowPanel;

public class CustomNestedSection extends NestedSection{
    
    private FlowPanel panel = new FlowPanel();
    private RowDescriptor currentRow = new RowDescriptor();
 
    public CustomNestedSection(){
        super.initWidget(panel);
    }
    
    public void nextRow(){
        rows.add(currentRow);
        currentRow = new RowDescriptor();
    }

    @Override
    public void clear() {
        // TODO bsmith - THIS METHOD NEEDS JAVADOCS
        
    }
    
    public FieldLabelType getCurrentFieldLabelType() {
        return currentRow.getCurrentFieldLabelType();
    }

    public void setCurrentFieldLabelType(FieldLabelType currentFieldLabelType) {
        currentRow.setCurrentFieldLabelType(currentFieldLabelType);
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

    @Override
    public void updateModel(ModelDTO modelDTO) {
        for (int i=0; i < fields.size(); i++){
            FieldDescriptor field = (FieldDescriptor)fields.get(i);
            field.getPropertyBinding().setValue(modelDTO, field.getWidgetBinding().getValue(field.getFieldWidget()));
        }
        for(NestedSection s: sections){
            s.updateModel(modelDTO);
        }
        
    }

    @Override
    public void updateView(ModelDTO modelDTO) {
        for (int i=0; i < fields.size(); i++){
            FieldDescriptor field = (FieldDescriptor)fields.get(i);
            field.getWidgetBinding().setValue(field.getFieldWidget(), field.getPropertyBinding().getValue(modelDTO));
        }
        for(NestedSection s: sections){
            s.updateView(modelDTO);
        }
    }

    @Override
    public void validate(Callback<org.kuali.student.core.validation.dto.ValidationResultInfo.ErrorLevel> callback) {
        // TODO bsmith - THIS METHOD NEEDS JAVADOCS
        
    }
    
    

}
