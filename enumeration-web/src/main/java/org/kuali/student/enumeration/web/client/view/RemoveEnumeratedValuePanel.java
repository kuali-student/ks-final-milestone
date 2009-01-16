package org.kuali.student.enumeration.web.client.view;

import org.kuali.student.enumeration.web.client.service.EnumerationGWTService;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class RemoveEnumeratedValuePanel extends FlowPanel {
    Button removeEnumeratedValueButton = new Button("Remove Enumerated Value");

    FlowPanel removeEnumeratedValuePanel = new FlowPanel();

    final TextBox keyField = new TextBox();

    final TextBox codeField = new TextBox();
    HTML messageHTML = new HTML();
    public RemoveEnumeratedValuePanel() {
        add(messageHTML);
        
        FlexTable layoutTable = new FlexTable();
        layoutTable.setWidget(0, 0, new HTML("Enum Key"));
        layoutTable.setWidget(0, 1, keyField);
        layoutTable.setWidget(0, 2, new HTML("Enum Code"));
        layoutTable.setWidget(0, 3, codeField);
        layoutTable.setWidget(0, 4, removeEnumeratedValueButton);
        
        add(layoutTable);
        
        
        removeEnumeratedValueButton.addClickListener(new ClickListener(){

            public void onClick(Widget arg0) {
                EnumerationGWTService.Util.getInstance().removeEnumeratedValue(keyField.getText(),codeField.getText(), new AsyncCallback<Boolean>() {
                    public void onFailure(Throwable caught) {
                        throw new RuntimeException("Unable to remove enumerated value", caught);
                    }
                    public void onSuccess(Boolean removed) {
                        if(removed){
                            messageHTML.setText("removed");
                        }else{
                            messageHTML.setText("not removed");
                            
                        }

                    }
                });
                
            }});

    }
}
