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
/*                long baseTime = System.currentTimeMillis();
                
                EnumeratedValue entity1 = new EnumeratedValue();
                //entity1.set.setEnumerationKey("Key1");
                entity1.setAbbrevValue("Abbrev1");
                entity1.setCode("Code1");
                entity1.setEffectiveDate(new Date(baseTime-10000000L));
                entity1.setExpirationDate(new Date(baseTime+10000000L));
                entity1.setSortKey(1);
                entity1.setValue("Value1");
                
                EnumeratedValue entity2 = new EnumeratedValue();
                //entity2.setEnumerationKey("Key1");
                entity2.setAbbrevValue("Abbrev2");
                entity2.setCode("Code2");
                entity2.setEffectiveDate(new Date(baseTime-10000000L));
                entity2.setExpirationDate(new Date(baseTime+50000000L));
                entity2.setSortKey(1);
                entity2.setValue("Value2");
                
                EnumeratedValue entity3 = new EnumeratedValue ();
                //entity3.setEnumerationKey("Key1");
                entity3.setAbbrevValue("Abbrev3");
                entity3.setCode("Code3");
                entity3.setEffectiveDate(new Date(baseTime-10000000L));
                entity3.setExpirationDate(new Date(baseTime+10000000L));
                entity3.setSortKey(1);
                entity3.setValue("Value3");
                
                EnumeratedValue entity4 = new EnumeratedValue();
                //entity4.setEnumerationKey("Key1");
                entity4.setAbbrevValue("Abbrev4");
                entity4.setCode("Code4");
                entity4.setEffectiveDate(new Date(baseTime-10000000L));
                entity4.setExpirationDate(new Date(baseTime+50000000L));
                entity4.setSortKey(1);
                entity4.setValue("Value4");

                Context contextEntity1 = new Context();
                contextEntity1.setType("country");
                contextEntity1.setValue("US");
                
                Context contextEntity3 = new Context();
                contextEntity3.setType("country");
                contextEntity3.setValue("US");
                
                Context contextEntity2 = new Context();
                contextEntity2.setType("country");
                contextEntity2.setValue("CA");
                
                Context contextEntity4 = new Context();
                contextEntity4.setType("country");
                contextEntity4.setValue("CA");
                
                List<Context> list = new ArrayList<Context>();
                list.add(contextEntity1);
                list.add(contextEntity2);
                list.add(contextEntity3);
                list.add(contextEntity4);
                
                Contexts contexts = new Contexts();
                contexts.setContext(list);
                
                entity1.setContexts(contexts);
                EnumerationGWTService.Util.getInstance().addEnumeratedValue("Key1", entity1, new AsyncCallback<EnumeratedValue>() {
                    public void onFailure(Throwable caught) {
                        System.out.println("error");
                        throw new RuntimeException("error", caught);
                    }

                    public void onSuccess(EnumeratedValue value) {
                        System.out.println("Success");
                    }
                });                
                //enumerationManagementDAO.addEnumeratedValue("Key1", entity2);
                //enumerationManagementDAO.addEnumeratedValue("Key1", entity3);
                //enumerationManagementDAO.addEnumeratedValue("Key1", entity4);
                //enumService.setEnumDAO(enumerationManagementDAO);

                EnumerationGWTService.Util.getInstance().fetchEnumeration("Key1", "country", "US", new Date(baseTime), new AsyncCallback<EnumeratedValueList>() {
                    public void onFailure(Throwable caught) {
                        messageHTML.setHTML("Exception");
                        throw new RuntimeException("Exception", caught);
                    }

                    public void onSuccess(EnumeratedValueList valueList) {
                        System.out.println(valueList.getEnumeratedValue().size());
                        listEnumeratedValue(valueList);
                    }
                });
                

                //enumService.fetchEnumeration("Key1", null, null, null);
                //result =enumService.fetchEnumeration("Key1" , "country", "CA", null);
                
                */