package org.kuali.student.enumeration.web.client.view;

import org.kuali.student.enumeration.dto.EnumeratedValueField;
import org.kuali.student.enumeration.dto.EnumeratedValueFields;
import org.kuali.student.enumeration.dto.EnumerationMeta;
import org.kuali.student.enumeration.dto.FieldDescriptor;
import org.kuali.student.enumeration.web.client.service.EnumerationGWTService;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;

public class AddEnumerationMetaPanel extends FlowPanel{
    HTML messageHTML = new HTML();
    EnumerationMetaComposite enumerationMetaComposite = new EnumerationMetaComposite();
    public AddEnumerationMetaPanel(){
        Button addButton = new Button("Add");
        add(enumerationMetaComposite);
        add(addButton);
        
        addButton.addClickListener(new ClickListener(){
            public void onClick(Widget arg0) {
                EnumerationGWTService.Util.getInstance().addEnumerationMeta(enumerationMetaComposite.getEnumerationMeta(),new AsyncCallback<EnumerationMeta>() {
                    public void onFailure(Throwable caught) {
                        messageHTML.setHTML("Exception");
                        throw new RuntimeException("Exception", caught);
                    }
                    public void onSuccess(EnumerationMeta meta) {
                        messageHTML.setHTML("Success:");
                    }
                });
            }
        });
    }
}
