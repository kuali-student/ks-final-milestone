package org.kuali.student.common.ui.client.configurable.mvc;

import java.util.List;

import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.core.validation.dto.ValidationResultContainer;

import com.google.gwt.user.client.ui.FlowPanel;

public class HorizontalSection extends Section{
    
    protected final FlowPanel panel = new FlowPanel();
    protected RowDescriptor row = new RowDescriptor();
    
    public HorizontalSection(){
        super.initWidget(row);
        row.setCurrentFieldLabelType(FieldLabelType.LABEL_TOP);
        rows.add(row);
    }
    
    
    @Override
    public void clear() {
        // TODO bsmith - THIS METHOD NEEDS JAVADOCS
        
    }
    
    @Override
    public void redraw() {
        panel.clear();
        panel.add(generateTitlePanel());
        panel.add(instructionsLabel);
        for(Section ns: sections){
            ns.redraw();
        }
        panel.add(row);

    }


    @Override
    public void validate(Callback<org.kuali.student.core.validation.dto.ValidationResultInfo.ErrorLevel> callback) {
        // TODO bsmith - THIS METHOD NEEDS JAVADOCS
        
    }
}
