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
package org.kuali.student.ui.kitchensink.client.kscommons.tabpanel;

import static org.kuali.student.ui.kitchensink.client.KitchenSinkStyleConstants.STYLE_EXAMPLE;

import org.kuali.student.common.ui.client.widgets.KSButton;
import org.kuali.student.common.ui.client.widgets.KSImage;
import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.common.ui.client.widgets.KSTabPanel;
import org.kuali.student.ui.kitchensink.client.kscommons.datepicker.DatePickerExample;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * This is a description of what this class does - Gary Struthers don't forget to fill this in. 
 * 
 * @author Kuali Student Team (gstruthers@berkeley.edu)
 *
 */
public class TabPanelExample extends Composite {
    final VerticalPanel main = new VerticalPanel();
    
    final KSImage image1 = new KSImage("images/flower1.jpg");
    final KSImage image2 = new KSImage("images/flower2.jpg");
    final KSImage image3 = new KSImage("images/flower3.jpg"); 
    final KSImage imageTab = new KSImage("images/1day.png"); 

    final KSButton imageButton = new KSButton("Image Button",new MyClickHandler()); 
    final SimplePanel simplePanel1 = new SimplePanel();
    final VerticalPanel verticalPanel2 = new VerticalPanel();
    final FlowPanel flowPanel3 = new FlowPanel();
    final KSTabPanel panel = new KSTabPanel();
    final KSLabel labelTab = new KSLabel("Label tab",false);
    final KSLabel label = new KSLabel("Click on a tab below to show its contents: ", false);

    /**
     * This constructs KSTabPanel which delegates to a GWT DecoratedTabPanel
     * 
     */
    public TabPanelExample() {
        main.addStyleName(STYLE_EXAMPLE);
        simplePanel1.add(image1);
        panel.addTab(simplePanel1, labelTab);
        verticalPanel2.add(image2);
        verticalPanel2.add(imageButton);
        panel.addTab(verticalPanel2, "Text tab");
        DatePickerExample datePacker = new DatePickerExample();
        panel.addTab(datePacker, imageTab);
        panel.selectTab(0);
        main.add(label);

        main.add(panel);
        super.initWidget(main);
    }
    
    class MyClickHandler implements ClickHandler  {   
        
        public void onClick(ClickEvent event) {              
            Button b = (Button)(event.getSource());              
            Window.alert("You clicked: " + b.getText());               
        }        
    }

}
