/*
 * Copyright 2008 The Kuali Foundation
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
package org.kuali.student.ui.personidentity.client.view;

import com.google.gwt.user.client.ui.SourcesTabEvents;
import com.google.gwt.user.client.ui.TabListener;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * The tab panel by default should select tab 0, and if this tab panel has children
 * that are also tab panels they should default to tab 
 * 
 * @author Kuali Student Team
 *
 */
public class SelectedTabPanel extends TabPanel {

    protected int selectedIndex = -1;      
    
    public TabListener DEFAULT_TAB_LISTENER = new TabListener() {
        public boolean onBeforeTabSelected(SourcesTabEvents sender, int tabIndex) {
            return true;
        }

        public void onTabSelected(SourcesTabEvents sender, int tabIndex) {
            if(getWidget(tabIndex) instanceof SelectedTabPanel){
                SelectedTabPanel subTab = (SelectedTabPanel)getWidget(tabIndex);
                if(subTab.getSelectedIndex() == -1){
                    subTab.selectTab(0);
                }
            }
        }
    };
    
    protected TabListener setIndexTabListener = new TabListener(){
        public boolean onBeforeTabSelected(SourcesTabEvents sender, int tabIndex) {
            return true;
        }
        public void onTabSelected(SourcesTabEvents sender, int tabIndex) {                                        
            selectedIndex = tabIndex;                        
        }
    };
    
    /**
     * This constructs a ...
     * 
     */
    public SelectedTabPanel() {
        super();
        this.addTabListener(this.setIndexTabListener);
    }

    
    public int getSelectedIndex(){
        return this.selectedIndex;
    }
    
    public Widget getSelectedWidget(){
        if(selectedIndex == -1)
            return null;
        else return getWidget(this.selectedIndex);
    }
}
