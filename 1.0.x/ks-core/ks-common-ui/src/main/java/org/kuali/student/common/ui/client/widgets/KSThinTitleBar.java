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
package org.kuali.student.common.ui.client.widgets;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;

public class KSThinTitleBar extends Composite{
    private final HorizontalPanel titlePanel = new HorizontalPanel();
    private Label titleLabel = new Label();
    
    public KSThinTitleBar(String text){
        titlePanel.setHorizontalAlignment(HorizontalPanel.ALIGN_CENTER);
        titleLabel = new Label(text, false);
        titlePanel.add(titleLabel);
        titlePanel.addStyleName(KSStyles.KS_POPUP_HEADER);
        this.initWidget(titlePanel);
    }
    
    public void setTitle(String text){
        titleLabel.setText(text);
    }
    
    public String getTitle(){
        return titleLabel.getText();
    }
}
