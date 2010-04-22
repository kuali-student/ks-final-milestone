/**
 * Copyright 2010 The Kuali Foundation Licensed under the
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

package org.kuali.student.common.ui.client.widgets.buttonlayout;

import org.kuali.student.common.ui.client.widgets.KSButton;
import org.kuali.student.common.ui.client.widgets.KSStyles;

import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.HasAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

public class ButtonRow extends ButtonLayoutTwoGroups{
    private HorizontalPanel leftPanel = new HorizontalPanel();
    private HorizontalPanel rightPanel = new HorizontalPanel();
    private SimplePanel contentPanel = new SimplePanel();
    private DockPanel mainPanel = new DockPanel();
    private Widget content = null;
    
    public ButtonRow(){
        setupDefaultStyles();
        mainPanel.add(contentPanel, DockPanel.NORTH);
        mainPanel.add(leftPanel, DockPanel.WEST);
        mainPanel.setHorizontalAlignment(HasAlignment.ALIGN_RIGHT);
        mainPanel.add(rightPanel, DockPanel.EAST);
        this.initWidget(mainPanel);
    }
    
    public ButtonRow(boolean contentBottom){
        setupDefaultStyles();
        if(contentBottom){
            mainPanel.add(contentPanel, DockPanel.SOUTH);
        }
        else{
            mainPanel.add(contentPanel, DockPanel.NORTH);
        }
        mainPanel.add(leftPanel, DockPanel.WEST);
        mainPanel.setHorizontalAlignment(HasAlignment.ALIGN_RIGHT);
        mainPanel.add(rightPanel, DockPanel.EAST);
        this.initWidget(mainPanel);
    }
    
    @Override
    protected void onLoad() {
        super.onLoad();
        if(content != null){
            mainPanel.setWidth(content.getOffsetWidth() + "px");
        }
    }
    
    private void setupDefaultStyles(){
        mainPanel.addStyleName(KSStyles.KS_BUTTON_ROW_MAIN_PANEL);
        contentPanel.addStyleName(KSStyles.KS_BUTTON_ROW_CONTENT_PANEL);
    }

    @Override
    public void setContent(Widget w) {
        contentPanel.setWidget(w);
        content = w;
        
    }

    @Override
    public void addButtonToPrimaryGroup(KSButton button) {
        leftPanel.add(button);
        buttons.add(button);
    }

    @Override
    public void addButtonToSecondaryGroup(KSButton button) {
        rightPanel.add(button);
        buttons.add(button);
    }

    @Override
    public void addButton(KSButton button) {
        this.addButtonToPrimaryGroup(button);  
    }
    
}
