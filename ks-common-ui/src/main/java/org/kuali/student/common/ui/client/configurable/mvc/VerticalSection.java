package org.kuali.student.common.ui.client.configurable.mvc;

import org.kuali.student.common.ui.client.mvc.Callback;

import com.google.gwt.user.client.ui.FlowPanel;

public class VerticalSection extends Section{
    
    protected final FlowPanel panel = new FlowPanel();
    
    public VerticalSection(){
        super.initWidget(panel);
        setLabelType(FieldLabelType.LABEL_LEFT);
    }
        
    @Override
    public void clear() {
        super.clear();
    }
    
    @Override
    public void redraw() {
        panel.clear();
        panel.add(generateTitlePanel());
        panel.add(instructionsLabel);
        for(Section ns: sections){
            ns.redraw();
        }
        for(RowDescriptor r: rows){
            panel.add(r);
        }
    }


    @Override
    public void validate(Callback<org.kuali.student.core.validation.dto.ValidationResultInfo.ErrorLevel> callback) {
        // TODO bsmith - THIS METHOD NEEDS JAVADOCS        
    }

}
