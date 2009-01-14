package org.kuali.student.enumeration.web.client.view;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.kuali.student.enumeration.dto.Context;
import org.kuali.student.enumeration.dto.Contexts;
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

        //final String key = enumerationKeyBox.getText(); 
        final String key = "key";
        //final EnumeratedValue enumeratedValue = enumeratedValueComposit.getEnumeratedValue();
        final EnumeratedValue enumeratedValue = new EnumeratedValue();
       
        enumeratedValue.setAbbrevValue("value");
        enumeratedValue.setCode("code");
        DateTimeFormat dateFormat = DateTimeFormat.getFormat("dd/MM/yyyy");
        enumeratedValue.setEffectiveDate(dateFormat.parse("11/11/1999"));
        enumeratedValue.setExpirationDate(dateFormat.parse("12/12/2000"));
        
        enumeratedValue.setSortKey(1);
        enumeratedValue.setValue("value");

        Context context  = new Context();
        context.setType("t");
        context.setValue("v");
        
        Contexts contexts = new Contexts();
        contexts.getContext().add(context);
        enumeratedValue.setContexts(contexts);
        
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
                long baseTime = System.currentTimeMillis();
                
                 EnumeratedValue dto = new EnumeratedValue();
                dto.setCode("c");
                dto.setEffectiveDate(new Date(baseTime-10000000L));
                dto.setExpirationDate(new Date(baseTime+10000000L));
                dto.setSortKey(1);
                dto.setValue("v");
                dto.setAbbrevValue("a");
                
                //dto context
                List<Context> dtoContext = new ArrayList<Context>();
                Context newContext = new Context();
                newContext.setType("ContextA");
                newContext.setValue("1");
                dtoContext.add(newContext);
                Contexts contexts = new Contexts();
                contexts.setContext(dtoContext);
                dto.setContexts(contexts);
                //add first
                
                EnumerationGWTService.Util.getInstance().addEnumeratedValue("Key3", dto, new AsyncCallback<EnumeratedValue>() {
                    public void onFailure(Throwable caught) {
    
                        throw new RuntimeException("error", caught);
                    }

                    public void onSuccess(EnumeratedValue value) {

                    }
                });                
                System.out.println("added ");
                EnumerationGWTService.Util.getInstance().fetchEnumeration("Key3", "ContextA", "1", new Date(baseTime), new AsyncCallback<EnumeratedValueList>() {
                    public void onFailure(Throwable caught) {
                        throw new RuntimeException("Exception", caught);
                    }

                    public void onSuccess(EnumeratedValueList valueList) {
                        System.out.println("Success:"+valueList.getEnumeratedValue().size());
                   
                        for(EnumeratedValue value: valueList.getEnumeratedValue()){
                        }
                        
                        
                    }
                });                
                //update
                //update currently fails on context updates
                dto.setCode("newCode");
                dto.setValue("newValue");
                dto.getContexts().getContext().get(0).setType("newType");
                dto.getContexts().getContext().get(0).setValue("newContextValue");

                EnumerationGWTService.Util.getInstance().updateEnumeratedValue("Key3", "c", dto, new AsyncCallback<EnumeratedValue>() {
                    public void onFailure(Throwable caught) {
                        messageHTML.setHTML("error");
                        throw new RuntimeException("error", caught);
                    }

                    public void onSuccess(EnumeratedValue value) {
                        System.out.println("updated ");
                        messageHTML.setHTML("Success: update");
                        enumerationKeyBox.setText(key);
                        enumeratedValueComposit.setEnumeratedValue(value);
                    }
                });

                                
/*                
                EnumerationGWTService.Util.getInstance().updateEnumeratedValue(key, enumeratedValue.getCode(), enumeratedValue, new AsyncCallback<EnumeratedValue>() {
                    public void onFailure(Throwable caught) {
                        messageHTML.setHTML("error");
                        throw new RuntimeException("error", caught);
                    }

                    public void onSuccess(EnumeratedValue value) {
                        messageHTML.setHTML("Error: update");
                        enumerationKeyBox.setText(key);
                        enumeratedValueComposit.setEnumeratedValue(value);
                    }
                });
*/
            }
        });
    }
}
