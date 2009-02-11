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
package org.kuali.student.core.organization.ui.client.view;

import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;


/**
 * This is a description of what this class does - Will Gomes don't forget to fill this in. 
 * 
 * @author Kuali Student Team
 *
 */
public class OrgMenu extends VerticalPanel{
    FlexTable fTable = new FlexTable();
    
    boolean loaded = false;
    SimplePanel workPanel;
    
   
    Hyperlink locateOrg = new Hyperlink("Organization", "locateOrg");
    Hyperlink createOrg = new Hyperlink("Organization", "createOrg");
    Hyperlink createPos = new Hyperlink("Positions", "createPos");
    Hyperlink modifyOrg = new Hyperlink("Organization", "modifyPos");
    
    public OrgMenu(SimplePanel workPanel){
        this.add(fTable);
        this.setStyleName("ks-section");
        this.workPanel = workPanel;
    }

    protected void onLoad(){
        locateOrg.addClickListener(new ClickListener(){           
            public void onClick(Widget sender) {
                workPanel.setWidget(new OrgLocatePanel());
        }});

        createOrg.addClickListener(new ClickListener(){           
            public void onClick(Widget sender) {
                workPanel.setWidget(new OrgCreatePanel(OrgCreatePanel.CREATE_ORG_ALL));
        }});
        
        createPos.addClickListener(new ClickListener(){
            public void onClick(Widget sender) {
                workPanel.setWidget(new OrgCreatePanel(OrgCreatePanel.CREATE_ORG_POSITIONS));
        }});
    
        modifyOrg.addClickListener(new ClickListener(){
            public void onClick(Widget sender) {
                workPanel.setWidget(new OrgCreatePanel(OrgCreatePanel.CREATE_ORG_ALL));
        }});
        
                    
        fTable.setWidget(0,0, new SectionLabel("Create"));       
        fTable.setWidget(1,0, createPos);
        fTable.setWidget(2,0, createOrg);
        
        fTable.setWidget(0,1, new SectionLabel("Modify Existing"));
        fTable.setWidget(1,1, new Hyperlink("Position", "modifyPos"));
        fTable.setWidget(2,1, modifyOrg);
               
        fTable.setWidget(0,2, new SectionLabel("Administer Existing"));
        
        fTable.setWidget(0,3,new SectionLabel("Locate"));
        fTable.setWidget(1,3, new Hyperlink("Position", "locatePos"));
        fTable.setWidget(2,3, locateOrg);

    }
        
}
