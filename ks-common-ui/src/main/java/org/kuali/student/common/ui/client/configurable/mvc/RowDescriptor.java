package org.kuali.student.common.ui.client.configurable.mvc;

import java.util.ArrayList;
import java.util.List;

import org.kuali.student.common.ui.client.configurable.mvc.Section.FieldLabelType;
import org.kuali.student.common.ui.client.widgets.KSLabel;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;

public class RowDescriptor extends Composite{
    private HorizontalPanel rowPanel = new HorizontalPanel();
    private List<FieldDescriptor> fields = new ArrayList<FieldDescriptor>();
    private List<NestedSection> sections = new ArrayList<NestedSection>();
    
    protected FieldLabelType currentFieldLabelType = FieldLabelType.LABEL_LEFT;
    
    public RowDescriptor(){
        this.initWidget(rowPanel);
    }
    
    public void addSection(NestedSection section){
        sections.add(section);
        rowPanel.add(section);
    }
    
    public void addField(FieldDescriptor fieldDescriptor){
        fields.add(fieldDescriptor);
        if (fieldDescriptor.getFieldWidget() instanceof MultiplicityComposite){
            MultiplicityComposite listField = (MultiplicityComposite)fieldDescriptor.getFieldWidget(); 
            listField.redraw();
            rowPanel.add(listField);
        }
        else{   
            KSLabel label = new KSLabel(fieldDescriptor.getFieldLabel());
            //label.addStyleName(KSStyles.KS_FORMLAYOUT_LABEL);
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
    }

    public List<FieldDescriptor> getFields() {
        return fields;
    }

    public List<NestedSection> getSections() {
        return sections;
    }

    public FieldLabelType getCurrentFieldLabelType() {
        return currentFieldLabelType;
    }

    public void setCurrentFieldLabelType(FieldLabelType currentFieldLabelType) {
        this.currentFieldLabelType = currentFieldLabelType;
    }   
    
    
}