/*
 * Copyright 2007 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 1.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl1.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.student.ui.kitchensink.client.kscommons.formlayoutpanel;

import static org.kuali.student.ui.kitchensink.client.KitchenSinkStyleConstants.STYLE_EXAMPLE;

import java.util.Arrays;

import org.kuali.student.common.ui.client.application.Application;
import org.kuali.student.common.ui.client.application.ApplicationContext;
import org.kuali.student.common.ui.client.dto.HelpInfo;
import org.kuali.student.common.ui.client.messages.MessagesService;
import org.kuali.student.common.ui.client.widgets.KSButton;
import org.kuali.student.common.ui.client.widgets.KSDatePicker;
import org.kuali.student.common.ui.client.widgets.KSRichEditor;
import org.kuali.student.common.ui.client.widgets.KSTextBox;
import org.kuali.student.common.ui.client.widgets.forms.KSFormField;
import org.kuali.student.common.ui.client.widgets.forms.KSFormLayoutPanel;
import org.kuali.student.core.messages.dto.MessageGroupKeyList;
import org.kuali.student.core.messages.dto.MessageList;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.VerticalPanel;


/**
 * This is a description of what this class does - Will Gomes don't forget to fill this in. 
 * 
 * @author Kuali Student Team
 *
 */
public class FormLayoutPanelExample extends Composite{
    final VerticalPanel main = new VerticalPanel();
    KSFormLayoutPanel form;
    
    private final static String FORM_LABEL_NAME = "name";
    private final static String FORM_LABEL_DESC = "description";
    private final static String FORM_LABEL_DATE = "date";
    private final static String FORM_MSG = "formmsg";
    private final static String FORM_BUTTON = "submit";
    
    private String locale = "en";
    private ApplicationContext context;
    
    public FormLayoutPanelExample(){
        initApp(locale);

        super.initWidget(main);       
    }
       
    /*
     * Create new form and populate fields
     */
    private void initForm(){               
        main.addStyleName(STYLE_EXAMPLE);
        
        form = new KSFormLayoutPanel();
        
        KSFormField sf = new KSFormField();
        sf.setLabelText(context.getMessage(FORM_LABEL_NAME));
        sf.setWidget(new KSTextBox());
        sf.setHelpInfo(buildHelpInfo());
        sf.setName("name");
        form.addFormField(sf);
        
        sf = new KSFormField();
        sf.setLabelText(context.getMessage(FORM_LABEL_DESC));
        sf.setWidget(new KSRichEditor());
        sf.setHelpInfo(buildHelpInfo());
        sf.setName("desc");
        form.addFormField(sf);
        
        sf = new KSFormField();
        sf.setHelpInfo(buildHelpInfo());
        sf.setLabelText(context.getMessage(FORM_LABEL_DATE));
        sf.setWidget(new KSDatePicker());
        sf.setName("date");      
        form.addFormField(sf);
                               
        KSButton submitButton = new KSButton(context.getMessage(FORM_BUTTON));
        submitButton.addClickHandler(new ClickHandler(){                 
            public void onClick(ClickEvent event) {
                StringBuffer sb = new StringBuffer();
                sb.append(context.getMessage(FORM_MSG));
                sb.append(context.getMessage(FORM_LABEL_NAME) + ": <" + form.getFieldValue("name") + ">\n");
                sb.append(context.getMessage(FORM_LABEL_DESC) + ": <" + form.getFieldValue("desc") + ">\n");
                sb.append(context.getMessage(FORM_LABEL_DATE) + ": <" + ((KSDatePicker)form.getFieldWidget("date")).getValue() + ">");
                Window.alert(sb.toString());                               
            }
        });        
        
        KSButton localeButton = new KSButton("Switch Locale");
        localeButton.addClickHandler(new ClickHandler(){
            public void onClick(ClickEvent arg0) {
                locale = (locale.equals("en") ? "it":"en");
                initApp(locale);
            }            
        });

        main.add(form);
        main.add(submitButton);
        main.add(localeButton);
    }
    
    
    private HelpInfo buildHelpInfo(){
        HelpInfo testInfo = new HelpInfo();
        testInfo.setId("123456");
        testInfo.setTitle("Help Title");
        testInfo.setShortVersion("Help Short Version");
        testInfo.setUrl("http://en.wikipedia.org/wiki/Main_Page");
        return testInfo;
    }

    private void initApp(String locale){
        context = new ApplicationContext();
        Application.setApplicationContext(context);
        
        MessageGroupKeyList groupKeys = new MessageGroupKeyList();
        groupKeys.setMessageGroupKeys(Arrays.asList("common","formexample"));
        
        MessagesService.Util.getInstance("MessageService").getMessagesByGroups(locale, groupKeys, new AsyncCallback<MessageList>(){

            public void onFailure(Throwable caught) {
                throw new RuntimeException("Unable to load messages", caught);
            }

            public void onSuccess(MessageList result) {
                main.clear();
                context.addMessages(result.getMessages());
                initForm();                
            }           
        });                
    }
}
