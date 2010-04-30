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

package org.kuali.student.common.ui.client.widgets;


import org.kuali.student.common.ui.client.dto.HelpInfo;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;

@Deprecated
public class KSHelp extends Composite{
    private SimplePanel helpPanel = new SimplePanel();
    private KSHelpDialog popup;
    private String helpId;
    private Label helpText = new Label();
    private HelpInfo helpInfo;

    @Deprecated
    public KSHelp() {
        initialize();
    }

    @Deprecated
    public KSHelp(String id){
        helpId = id;  
        initialize();
    }

    private void fetchHelpInfo(){
        //TODO implement this with a RPC call
        HelpInfo testData = new HelpInfo();
        testData.setId(helpId);
        testData.setTitle("Help Title");
        testData.setShortVersion("HELP TEXT HELP TEXT HELP TEXT HELP TEXT");
        testData.setUrl("http://www.kuali.org");
        helpInfo = testData;
    }

    @Deprecated
    public String getHelpId() {
        return helpId;
    }

    @Deprecated
    public void setHelpId(String helpId) {
        this.helpId = helpId;
    }

    private void setDefaultStyle(){
        helpText.addStyleName(KSStyles.KS_HELP_TEXT);
        helpPanel.addStyleName(KSStyles.KS_HELP_TEXT_PANEL);
    }
    
    private void initialize() {
        this.initWidget(helpPanel); 

        this.fetchHelpInfo();

        popup = new KSHelpDialog(helpInfo);

        helpText.setText(helpInfo.getShortVersion());
        helpText.setWordWrap(true);

        helpText.addClickHandler(new ClickHandler(){

            public void onClick(ClickEvent event) {
                popup.show();               
            }
        });

        helpPanel.add(helpText);
        setDefaultStyle();
    }
}
