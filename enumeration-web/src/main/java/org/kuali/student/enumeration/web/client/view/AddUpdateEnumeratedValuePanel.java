package org.kuali.student.enumeration.web.client.view;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.kuali.student.enumeration.dto.Context;
import org.kuali.student.enumeration.dto.Contexts;
import org.kuali.student.enumeration.dto.EnumeratedValue;
import org.kuali.student.enumeration.web.client.service.EnumerationGWTService;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class AddUpdateEnumeratedValuePanel extends FlowPanel {
    HTML messageHTML = new HTML();
    TextBox enumerationKeyBox = new TextBox();

    EnumeratedValueComposit enumeratedValueComposit = new EnumeratedValueComposit();

    public AddUpdateEnumeratedValuePanel() {
        add(messageHTML);

        add(new HTML("enumeration Key"));
        add(enumerationKeyBox);

        add(enumeratedValueComposit);
        Button addButton = new Button("Add Enumerated Value");
        add(addButton);

        Button updateButton = new Button("Update Enumerated Value");
        add(updateButton);

        final String key = enumerationKeyBox.getText(); 
        
        final EnumeratedValue enumeratedValue = enumeratedValueComposit.getEnumeratedValue();
        
        addButton.addClickListener(new ClickListener() {
            public void onClick(Widget arg0) {
                EnumerationGWTService.Util.getInstance().addEnumeratedValue(key, enumeratedValue, new AsyncCallback<EnumeratedValue>() {
                    public void onFailure(Throwable caught) {
                        System.out.println("error");
                        messageHTML.setHTML("error");
                        throw new RuntimeException("error", caught);
                    }

                    public void onSuccess(EnumeratedValue value) {
                        messageHTML.setHTML("Success: Add");
                        enumerationKeyBox.setText(key);
                        enumeratedValueComposit.setEnumeratedValue(value);
                    }
                });
            }
        });
        updateButton.addClickListener(new ClickListener() {
            public void onClick(Widget arg0) {
          EnumerationGWTService.Util.getInstance().updateEnumeratedValue(key, enumeratedValue.getCode(), enumeratedValue, new AsyncCallback<EnumeratedValue>() {
                    public void onFailure(Throwable caught) {
                        messageHTML.setHTML("Update error:"+caught.getMessage());
                        System.out.println("Update error:"+caught.getMessage());
                        throw new RuntimeException("error", caught);
                    }

                    public void onSuccess(EnumeratedValue value) {
                        messageHTML.setHTML("Success: update");
                        enumerationKeyBox.setText(key);
                        enumeratedValueComposit.setEnumeratedValue(value);
                    }
                });

            }
        });
    }
}
