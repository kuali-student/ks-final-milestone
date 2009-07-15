package org.kuali.student.common.ui.client.configurable.mvc;

import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.common.ui.client.mvc.dto.ModelDTO;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;

public class VerticalSection extends NestedSection{
    
    protected final FlowPanel panel = new FlowPanel();
    
    public VerticalSection(){
        super.initWidget(panel);
    }
    
    
    @Override
    public void updateModel(ModelDTO modelDTO) {
        for (int i=0; i < fields.size(); i++){
            FieldDescriptor field = (FieldDescriptor)fields.get(i);
            if (field.getFieldWidget() instanceof MultiplicityComposite){
                ((MultiplicityComposite)field.getFieldWidget()).updateModelDTOValue();
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
        for(NestedSection s: sections){
            s.updateModel(modelDTO);
        }
    }

    @Override
    public void clear() {
        // TODO bsmith - THIS METHOD NEEDS JAVADOCS
        
    }
    
    @Override
    public void addField(FieldDescriptor fieldDescriptor) {
        super.addField(fieldDescriptor);
        RowDescriptor row = new RowDescriptor();
        row.addField(fieldDescriptor);
        rows.add(row);
        
    }

    @Override
    public void addSection(NestedSection section) {
        super.addSection(section);
        RowDescriptor row = new RowDescriptor();
        row.addSection(section);
        rows.add(row);
    }

    @Override
    public void redraw() {
        panel.clear();
        panel.add(sectionTitleLabel);
        panel.add(instructionsLabel);
        for(NestedSection ns: sections){
            ns.redraw();
        }
        for(RowDescriptor r: rows){
            panel.add(r);
        }
        /*form = new KSFormLayoutPanel();
        panel.add(form);
        for(Object o: orderedLayoutList){
            if(o instanceof NestedSection){
                NestedSection ns = ((NestedSection) o);
                ns.redraw();
                panel.add(ns);
            }
            else if(o instanceof FieldDescriptor){
                FieldDescriptor field = (FieldDescriptor)o;
                if (field.getFieldWidget() instanceof MultiplicityComposite){
                    MultiplicityComposite listField = (MultiplicityComposite)field.getFieldWidget(); 
                    listField.redraw();
                    panel.add(listField);                   
                } else {
                    KSFormField formField = new KSFormField(field.getFieldKey(), field.getFieldLabel());
                    formField.setWidget(field.getFieldWidget());
                    form.addFormField(formField);
                }
            }
        }*/
    }


    @Override
    public void updateView(ModelDTO modelDTO) {
        for (int i=0; i < fields.size(); i++){
            FieldDescriptor field = (FieldDescriptor)fields.get(i);
            PropertyBinding pBinding = field.getPropertyBinding();
            PropertyBinding wBinding = field.getWidgetBinding();
            if (wBinding != null){
                Widget w = field.getFieldWidget();            
                wBinding.setValue(w, pBinding.getValue(modelDTO));
            } else {
                GWT.log(field.getFieldKey() + " has no widget binding.", null);
            }
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
