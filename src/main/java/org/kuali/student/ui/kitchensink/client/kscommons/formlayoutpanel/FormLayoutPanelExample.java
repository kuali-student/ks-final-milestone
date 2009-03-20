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

import org.kuali.student.common.ui.client.dto.HelpInfo;
import org.kuali.student.common.ui.client.widgets.KSButton;
import org.kuali.student.common.ui.client.widgets.KSDatePicker;
import org.kuali.student.common.ui.client.widgets.KSRichEditor;
import org.kuali.student.common.ui.client.widgets.KSTextBox;
import org.kuali.student.common.ui.client.widgets.forms.KSFormField;
import org.kuali.student.common.ui.client.widgets.forms.KSFormLayoutPanel;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * This is a description of what this class does - Will Gomes don't forget to fill this in. 
 * 
 * @author Kuali Student Team
 *
 */
public class FormLayoutPanelExample extends Composite{
    final VerticalPanel main = new VerticalPanel();
    KSFormLayoutPanel form = new KSFormLayoutPanel();    
    
    public FormLayoutPanelExample(){
        main.addStyleName(STYLE_EXAMPLE);

        KSFormField sf = new KSFormField();
        sf.setLabelText("Name");
        sf.setWidget(new KSTextBox());
        sf.setHelpInfo(new HelpInfo());
        sf.setName("name");
        form.addFormField(sf);
        
        sf = new KSFormField();
        sf.setLabelText("Description");
        sf.setWidget(new KSRichEditor());
        sf.setHelpInfo(new HelpInfo());
        sf.setName("desc");
        form.addFormField(sf);
        
        sf = new KSFormField();
        sf.setHelpInfo(new HelpInfo());
        sf.setLabelText("Date");
        sf.setWidget(new KSDatePicker());
        sf.setName("date");      
        form.addFormField(sf);
        
                
        KSButton b1 = new KSButton("Submit"); 
        b1.addClickHandler(new ClickHandler(){                 
            public void onClick(ClickEvent event) {
                StringBuffer sb = new StringBuffer();
                sb.append("Form values: ");
                sb.append("Name: <" + form.getFieldValue("name") + ">\n");
                sb.append("Description: <" + form.getFieldValue("desc") + ">\n");
                sb.append("Date: <" + ((KSDatePicker)form.getFieldWidget("date")).getDate() + ">");
                Window.alert(sb.toString());                               
            }
        });        
        
        main.add(form);
        main.add(b1);    

        super.initWidget(main);
        
    }
}
