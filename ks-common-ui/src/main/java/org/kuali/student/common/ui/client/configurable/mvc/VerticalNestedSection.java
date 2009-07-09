package org.kuali.student.common.ui.client.configurable.mvc;

import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.common.ui.client.mvc.Model;
import org.kuali.student.common.ui.client.mvc.dto.ModelDTO;

import com.google.gwt.user.client.ui.FlowPanel;

public class VerticalNestedSection extends NestedSection{
    
    protected final FlowPanel panel = new FlowPanel();
    
    public VerticalNestedSection(){
        super.initWidget(panel);
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
