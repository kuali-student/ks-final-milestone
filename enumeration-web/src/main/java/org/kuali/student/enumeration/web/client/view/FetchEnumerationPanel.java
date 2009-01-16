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
import com.google.gwt.user.client.ui.DisclosurePanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class FetchEnumerationPanel extends FlowPanel {
    HTML messageHTML = new HTML();

    FlowPanel resultPanel = new FlowPanel();
    final TextBox enumerationKeyBox = new TextBox();
    final TextBox enumContextKeyBox = new TextBox();
    final TextBox contextValueBox = new TextBox();
    final TextBox contextDateBox = new TextBox();
    Button button = new Button("fetch Enumeration");
    public FetchEnumerationPanel() {
        add(messageHTML);
        FlexTable layoutTable = new FlexTable();
        layoutTable.setWidget(0, 0, new HTML("Enum Key"));
        layoutTable.setWidget(0, 1, enumerationKeyBox);
        
        layoutTable.setWidget(0, 2, new HTML("EnumContext Key (Type)"));
        layoutTable.setWidget(0, 3, enumContextKeyBox);

        layoutTable.setWidget(0, 4, new HTML("Context Value"));
        layoutTable.setWidget(0, 5, contextValueBox);

        layoutTable.setWidget(0, 6, new HTML("Context Date (dd/MM/yyyy)"));
        layoutTable.setWidget(0, 7, contextDateBox);

        layoutTable.setWidget(0, 8, button);
        add(layoutTable);
        
        add(resultPanel);
        
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
                        listEnumeratedValue(valueList);
                    }
                });
            }
        });
    }
    
    private void listEnumeratedValue(EnumeratedValueList valueList){
        resultPanel.clear();
        messageHTML.setHTML("Success:"+valueList.getEnumeratedValue().size());
        for(EnumeratedValue value: valueList.getEnumeratedValue()){
            DisclosurePanel d = new DisclosurePanel();
            d.setHeader(new HTML("Abbrev:" + value.getAbbrevValue() + " Code:" + value.getCode()));
            //effectiveDateBox.setText(dateFormat.format(value.getEffectiveDate()));
            //expirationDateBox.setText(dateFormat.format(value.getExpirationDate()));
           // sortKeyBox.setText(Integer.toString(value.getSortKey()));
           // valueBox.setText(value.getValue());
            resultPanel.add(d);
            
            FlexTable fieldTable = new FlexTable();
            fieldTable.setHTML(0, 0, "Type");
            fieldTable.setHTML(0, 1, "Value");
            int rowIndex = 1;
            
            for (Context context : value.getContexts().getContext()) {
                fieldTable.setWidget(rowIndex, 0, new HTML(context.getType()));
                fieldTable.setWidget(rowIndex, 1, new HTML(context.getValue()));
                rowIndex = rowIndex + 1;
            }
            d.setContent(fieldTable);
        }
    }
}
