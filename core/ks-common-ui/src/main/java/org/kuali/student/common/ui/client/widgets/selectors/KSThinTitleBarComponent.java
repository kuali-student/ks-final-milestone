/*
 * Copyright 2009 The Kuali Foundation Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 * 
 * http://www.osedu.org/licenses/ECL-2.0
 * 
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */
package org.kuali.student.common.ui.client.widgets.selectors;

import org.kuali.student.common.ui.client.widgets.KSButton;
import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.common.ui.client.widgets.KSStyles;

import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;

public class KSThinTitleBarComponent extends Composite{
    private final HorizontalPanel titlePanel = new HorizontalPanel();
    private KSLabel titleLabel = new KSLabel();
    private KSButton cancelButton = new KSButton("Cancel");
    
    public KSThinTitleBarComponent(String text){
        titlePanel.setHorizontalAlignment(HorizontalPanel.ALIGN_LEFT);
        titleLabel = new KSLabel(text, false);
        titlePanel.add(titleLabel);
        titlePanel.setHorizontalAlignment(HorizontalPanel.ALIGN_RIGHT);
        titlePanel.add(cancelButton);
        titlePanel.addStyleName(KSStyles.KS_POPUP_HEADER);  //TODO header should be grey
        this.initWidget(titlePanel);
    }
    
    public void setTitle(String text){
        titleLabel.setText(text);
    }
    
    public String getTitle(){
        return titleLabel.getText();
    }
    
    public void addCancelButtonHandler(ClickHandler clickHandler) {
        cancelButton.addClickHandler(clickHandler);
    }
}
