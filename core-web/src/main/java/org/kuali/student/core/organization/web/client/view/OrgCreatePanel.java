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
package org.kuali.student.core.organization.web.client.view;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.DockPanel.DockLayoutConstant;

/**
 * This is a description of what this class does - Will Gomes don't forget to fill this in. 
 * 
 * @author Kuali Student Team
 *
 */
public class OrgCreatePanel extends Composite{

    VerticalPanel vPanel = new VerticalPanel();
    
    public static final String CREATE_ORG_ALL = "All";
    public static final String CREATE_ORG_POSITIONS = "Positions";
    public static final String CREATE_ORG_RELATIONS = "Organizations";
    
    String type;
    
    ListBox orgType;
    TextBox orgName;
    TextBox orgAbbrev;
    TextArea orgDesc;
    
    //These should be date boxes
    TextBox orgEffectiveDate;
    TextBox orgExpirationDate;
    
    VerticalPanel vPositions;
    VerticalPanel vRelations;
    
    boolean loaded = false;
    
    
    public OrgCreatePanel(String type){
        this.type=type;
        super.initWidget(vPanel);
    }
    

    protected void onLoad(){
        //vPanel.setWidth("100%");
        //vPanel.setHorizontalAlignment(VerticalPanel.ALIGN_CENTER);
        if (!loaded){
            loaded = true;
            if (type.equals(CREATE_ORG_ALL)){
                createOrganization();
                createOrgPositions();
                createOrgRelations();
            } else if (type.equals(CREATE_ORG_POSITIONS)){
                createOrgPositions();            
            }
        }
    }
    
    protected void createOrganization(){
        FlexTable fTable = new FlexTable();
        
        orgType = new ListBox();
        orgName = new TextBox();
        orgAbbrev = new TextBox();
        orgDesc = new TextArea();
        
        fTable.setWidget(0,0, new Label("Type"));
        fTable.setWidget(0,1, orgType);
        
        fTable.setWidget(1,0, new Label("Name"));
        fTable.setWidget(1,1, orgName);
        
        fTable.setWidget(2,0, new Label("Abbreviation"));
        fTable.setWidget(2,1, orgAbbrev);
        
        fTable.setWidget(3,0, new Label("Description"));
        fTable.setWidget(3,1, orgDesc);
        
        fTable.setWidget(4,0, new Label("Effective Date"));
        fTable.setWidget(4,1, orgEffectiveDate);

        fTable.setWidget(5,0, new Label("Expiration Date"));
        fTable.setWidget(5,1, orgExpirationDate);    

        vPanel.add(new SectionLabel("Organization"));
        vPanel.add(fTable);
    }
    
    
    protected void createOrgPositions(){
        vPanel.add(new SectionLabel("Positions"));
        vPanel.add( new OrgPositionWidget());
    }

    protected void createOrgRelations(){
        vPanel.add(new SectionLabel("Relationships"));
        vPanel.add( new OrgRelationWidget());
    }
}
