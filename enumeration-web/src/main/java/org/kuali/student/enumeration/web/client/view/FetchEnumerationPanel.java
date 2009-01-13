package org.kuali.student.enumeration.web.client.view;

import java.util.Date;

import org.kuali.student.enumeration.dto.Context;
import org.kuali.student.enumeration.dto.EnumeratedValue;
import org.kuali.student.enumeration.dto.EnumeratedValueList;
import org.kuali.student.enumeration.web.client.service.EnumerationGWTService;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class FetchEnumerationPanel extends FlowPanel {
    HTML messageHTML = new HTML();

    public FetchEnumerationPanel() {
        HTML enumerationKey = new HTML("enumeration Key");
        final TextBox enumerationKeyBox = new TextBox();

        HTML enumContextKey = new HTML("enumContext Key");
        final TextBox enumContextKeyBox = new TextBox();

        HTML contextValue = new HTML("context Value");
        final TextBox contextValueBox = new TextBox();

        HTML contextDate = new HTML("context Date (dd/MM/yyyy)");
        final TextBox contextDateBox = new TextBox();

        add(enumerationKey);
        add(enumerationKeyBox);
        add(enumContextKey);
        add(enumContextKeyBox);

        add(contextValue);
        add(contextValueBox);
        add(contextDate);
        add(contextDateBox);

        Button button = new Button("fetch");
        add(button);
        button.addClickListener(new ClickListener() {
            public void onClick(Widget arg0) {
                DateTimeFormat fmt = DateTimeFormat.getFormat("dd/MM/yyyy");
                Date dateInput = null;
                try {
                    dateInput = fmt.parse(contextDateBox.getText());
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                }

                EnumerationGWTService.Util.getInstance().fetchEnumeration(enumerationKeyBox.getText(), enumContextKeyBox.getText(), contextValueBox.getText(), dateInput, new AsyncCallback<EnumeratedValueList>() {
                    public void onFailure(Throwable caught) {
                        messageHTML.setHTML("Exception");
                        throw new RuntimeException("Exception", caught);
                    }

                    public void onSuccess(EnumeratedValueList valueList) {
                        System.out.println("success");
                        for(EnumeratedValue value: valueList.getEnumeratedValue()){
                            value.getAbbrevValue();
                            value.getCode();
                            value.getEffectiveDate();
                            value.getExpirationDate();
                            value.getSortKey();
                            value.getValue();
                            for(Context context:value.getContexts().getContext()){
                                context.getType();
                                context.getValue();
                            }
                        }
                    }
                });
            }
        });
    }
}
