package org.kuali.student.lum.lu.ui.home.client.view;

import java.util.ArrayList;
import java.util.List;

import org.kuali.student.common.ui.client.mvc.Controller;
import org.kuali.student.common.ui.client.mvc.ViewComposite;
import org.kuali.student.common.ui.client.widgets.KSButton;
import org.kuali.student.common.ui.client.widgets.KSDropDown;
import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.common.ui.client.widgets.KSTextBox;
import org.kuali.student.common.ui.client.widgets.list.KSSelectItemWidgetAbstract;
import org.kuali.student.common.ui.client.widgets.list.ListItems;
import org.kuali.student.common.ui.client.widgets.list.SelectionChangeHandler;
import org.kuali.student.common.ui.client.widgets.suggestbox.KSAdvancedSearchWindow;
import org.kuali.student.lum.lu.ui.course.client.service.LuRpcService;
import org.kuali.student.lum.lu.ui.course.client.service.LuRpcServiceAsync;
import org.kuali.student.lum.lu.ui.main.client.controller.LUMApplicationManager.LUMViews;
import org.kuali.student.lum.lu.ui.main.client.events.ChangeViewStateEvent;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class FindPanel extends ViewComposite{
    LuRpcServiceAsync luServiceAsync = GWT.create(LuRpcService.class);
    KSAdvancedSearchWindow searchWindow = new KSAdvancedSearchWindow(luServiceAsync, "lu.search.clus","lu.resultColumn.cluId");
    
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

        searchWindow.addSelectionHandler(new SelectionHandler<List<String>>(){

            public void onSelection(SelectionEvent<List<String>> event) {
                final List<String> selected = event.getSelectedItem();
                if (selected.size() > 0){
                    searchWindow.hide();
                    FindPanel.this.getController().fireApplicationEvent(new ChangeViewStateEvent<LUMViews>(LUMViews.EDIT_COURSE_PROPOSAL, event));
                }                
            }
            
        });
        searchFor.setListItems(searchItems);
        searchFor.addSelectionChangeHandler(new SelectionChangeHandler(){
            public void onSelectionChange(KSSelectItemWidgetAbstract w) {
                searchWindow.show();
            }            
        });
        
        findLayout.setCellSpacing(25);        
        findLabel.addStyleName("Home-Category-Label");
        findButton.addStyleName("Home-Blue-Button");
        findLayout.setWidget(0, 0, findLabel);
        findLayout.setWidget(0, 1, searchFor);
        
        /* FIXME: Is this how search should be displayed
        VerticalPanel searchPanel = new VerticalPanel();
        searchPanel.add(searchText);
        searchText.addStyleName("Home-Search-Textbox");
        searchPanel.add(searchDescription);
        searchDescription.addStyleName("Home-Description-Label");
        findLayout.setWidget(1, 1, searchPanel);
        
        findLayout.setWidget(2, 1, findButton);
        */
        
        findLayout.setStyleName("Content-Left-Margin");
        mainPanel.add(findLayout);

        this.initWidget(mainPanel);
    }
    
}
