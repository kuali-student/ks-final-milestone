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
package org.kuali.student.lum.ui.home.client.view;

import java.util.ArrayList;
import java.util.List;

import org.kuali.student.common.ui.client.mvc.Controller;
import org.kuali.student.common.ui.client.mvc.ViewComposite;
import org.kuali.student.common.ui.client.widgets.KSButton;
import org.kuali.student.common.ui.client.widgets.KSDropDown;
import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.common.ui.client.widgets.KSTextBox;
import org.kuali.student.common.ui.client.widgets.list.ListItems;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class FindPanel extends ViewComposite {
   
    private VerticalPanel mainPanel = new VerticalPanel();
    
    private FlexTable findLayout = new FlexTable();
    private KSLabel findLabel = new KSLabel("Find");
    private KSDropDown searchFor = new KSDropDown();
    private KSTextBox searchText = new KSTextBox();
    private KSLabel searchDescription = new KSLabel("Searching Title and Description");
    private KSButton findButton = new KSButton("Find");
    
    public class SearchType{
        private String id;
        private String type;
        
        public SearchType(String id, String type) {
            this.id = id;
            this.type = type;
        }
        public String getId() {
            return id;
        }
        public void setId(String id) {
            this.id = id;
        }
        public String getType() {
            return type;
        }
        public void setType(String type) {
            this.type = type;
        }
        
        
    }
    
    private List<SearchType> searchTypes = new ArrayList<SearchType>();
    
    private ListItems searchItems = new ListItems(){
        @Override
        public List<String> getAttrKeys() {
            List<String> attributes = new ArrayList<String>();
            attributes.add("Type");
            return attributes;
        }

        @Override
        public String getItemAttribute(String id, String attrkey) {
            String value = null;
            for(SearchType t: searchTypes){
                if(t.getId().equals(id)){
                    if(attrkey.equals("Type")){
                        value = t.getType();
                    }
                    break;
                }
            }
            return value;
        }

        @Override
        public int getItemCount() {    
            return searchTypes.size();
        }

        @Override
        public List<String> getItemIds() {
            List<String> ids = new ArrayList<String>();
            for(SearchType t: searchTypes){
                ids.add(t.getId());
            }
            return ids;
        }

        @Override
        public String getItemText(String id) {
            String value = null;
            for(SearchType t: searchTypes){
                if(t.getId().equals(id)){
                    value = t.getType();
                    break;
                }
            }
            return value;
        }
    };
    
    private class WidgetRow extends Composite{
        public WidgetRow(Widget theWidget){
            theWidget.addStyleName("Content-Left-Margin");
        }
    }
    
    public FindPanel(Controller controller) {
        // TODO Bsmith - THIS CONSTRUCTOR NEEDS A JAVADOC
        super(controller, "Find Course or Proposal");
        searchTypes.add(new SearchType("1", "Proposals"));
        searchTypes.add(new SearchType("2", "Courses"));
        searchTypes.add(new SearchType("3", "Courses + Proposals"));
        searchFor.setListItems(searchItems);
        findLayout.setCellSpacing(25);
        
        findLabel.addStyleName("Home-Category-Label");
        findButton.addStyleName("Home-Blue-Button");
        findLayout.setWidget(0, 0, findLabel);
        findLayout.setWidget(0, 1, searchFor);
        
        VerticalPanel searchPanel = new VerticalPanel();
        searchPanel.add(searchText);
        searchText.addStyleName("Home-Search-Textbox");
        searchPanel.add(searchDescription);
        searchDescription.addStyleName("Home-Description-Label");
        findLayout.setWidget(1, 1, searchPanel);
        
        findLayout.setWidget(2, 1, findButton);
        
        findLayout.setStyleName("Content-Left-Margin");
        mainPanel.add(findLayout);
        this.initWidget(mainPanel);
    }
}
