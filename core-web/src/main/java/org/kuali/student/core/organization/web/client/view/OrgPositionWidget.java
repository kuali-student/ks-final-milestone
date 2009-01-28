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
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;

/**
 * This is a description of what this class does - Will Gomes don't forget to fill this in. 
 * 
 * @author Kuali Student Team
 *
 */
public class OrgPositionWidget extends Composite {

    TextBox posKey;
    TextArea posDesc;
    TextBox posEffectiveDate;
    TextBox posExpirationDate;
    TextBox posMinRelation;
    TextBox posDuration;
    TextBox posMaxRelation;

    FlexTable fTable = null;
    
    SimplePanel root = new SimplePanel();
    
    public OrgPositionWidget(){
        super.initWidget(root);
    }
    
    protected void onLoad(){
        fTable = new FlexTable();
        posKey = new TextBox();
        posDesc = new TextArea();
        posEffectiveDate = new TextBox();
        posExpirationDate = new TextBox();
        posMinRelation = new TextBox();
        posMaxRelation = new TextBox();
        posDuration = new TextBox();
        
        fTable.setWidget(0,0, new Label("Position"));
        fTable.setWidget(0,1, posKey);
        
        fTable.setWidget(1,0, new Label("Description"));
        fTable.setWidget(1,1, posDesc);
        
        fTable.setWidget(2,0, new Label("Effective date"));
        fTable.setWidget(2,1,posEffectiveDate);
        
        fTable.setWidget(3,0, new Label("Expiration date"));
        fTable.setWidget(3,1, posExpirationDate);
        
        fTable.setWidget(4,0, new Label("Min people"));
        fTable.setWidget(4,1, posMinRelation);

        fTable.setWidget(5,0, new Label("Max people"));
        fTable.setWidget(5,1, posMaxRelation);
        
        root.add(fTable);
    }
  
    
}
