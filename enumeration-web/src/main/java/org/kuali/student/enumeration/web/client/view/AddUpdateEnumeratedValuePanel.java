package org.kuali.student.enumeration.web.client.view;

import org.kuali.student.enumeration.dto.EnumeratedValue;
import org.kuali.student.enumeration.dto.EnumerationMetaList;
import org.kuali.student.enumeration.web.client.service.EnumerationGWTService;

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
        add(new HTML("enumeration Key"));
        add(enumerationKeyBox);

        add(enumeratedValueComposit);
        Button addButton = new Button("Add Enumerated Value");
        add(addButton);

        Button updateButton = new Button("Update Enumerated Value");
        add(updateButton);

        addButton.addClickListener(new ClickListener() {
            public void onClick(Widget arg0) {
                EnumerationGWTService.Util.getInstance().addEnumeratedValue(enumerationKeyBox.getText(), enumeratedValueComposit.getEnumeratedValue(), new AsyncCallback<EnumeratedValue>() {
                    public void onFailure(Throwable caught) {
                        System.out.println("error");
                        messageHTML.setHTML("error");
                        throw new RuntimeException("error", caught);
                    }

                    public void onSuccess(EnumeratedValue value) {
                        System.out.println("success");
                        enumeratedValueComposit.setEnumeratedValue(value);
                    }
                });
            }
        });
        updateButton.addClickListener(new ClickListener() {
            public void onClick(Widget arg0) {
                EnumerationGWTService.Util.getInstance().updateEnumeratedValue(enumerationKeyBox.getText(), enumeratedValueComposit.getEnumeratedValue().getCode(), enumeratedValueComposit.getEnumeratedValue(), new AsyncCallback<EnumeratedValue>() {
                    public void onFailure(Throwable caught) {
                        System.out.println("error");
                        messageHTML.setHTML("error");
                        throw new RuntimeException("error", caught);
                    }

                    public void onSuccess(EnumeratedValue value) {
                        System.out.println("success");
                        enumeratedValueComposit.setEnumeratedValue(value);
                    }
                });

            }
        });
    }
}
