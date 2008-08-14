/**
 * 
 */
package org.kuali.student.ui.personidentity.client.view;

import org.kuali.student.commons.ui.mvc.client.ApplicationContext;
import org.kuali.student.commons.ui.mvc.client.Controller;
import org.kuali.student.commons.ui.mvc.client.MVC;
import org.kuali.student.commons.ui.mvc.client.MVCEvent;
import org.kuali.student.commons.ui.mvc.client.MVCEventListener;
import org.kuali.student.ui.personidentity.client.ModelState;

import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SourcesTabEvents;
import com.google.gwt.user.client.ui.TabListener;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author Garey
 */
public class PersonTab extends TabPanel {

    PersonDetailsWidget pDetails = null;
    CreatePersonWidget pCreate = null;
    PersonSearchPanel pSearch = null;

    boolean loaded = false;
    
    final PersonTab me = this;
    
    // final AbsolutePanel blankPanel = new AbsolutePanel();

    /**
     * 
     */
    public PersonTab() {

        pDetails = new PersonDetailsWidget();
        pCreate = new CreatePersonWidget();
        pSearch = new PersonSearchPanel();

        this.addTabListener(new TabListener() {
            public boolean onBeforeTabSelected(SourcesTabEvents sender, int tabIndex) {
                return true;
            }

            public void onTabSelected(SourcesTabEvents sender, int tabIndex) {

                if (tabIndex == getWidgetIndex(pCreate)) {

                    // ModelState.getInstance().getPropertyChangeSuppor().firePropertyChange("CLICK_CREATE_PERSON_TAB",
                    // false, true);
                    ModelState.getInstance().getPropertyChangeSupport().firePropertyChange(ActionEvents.CLICK_CREATE_PERSON_TAB);
                }
            }
        });

        // blankPanel.setWidth("100%");
        // blankPanel.add(new Label("TODO: Widget Not defined"));

        // add(pSearch, "Person Search");
        addTab(pSearch, ApplicationContext.getViews().get(AdminEditPanel.VIEW_NAME).getMessages().get("personSearch"));
        // add(pDetails, "Person Details");
        addTab(pDetails, ApplicationContext.getViews().get(AdminEditPanel.VIEW_NAME).getMessages().get("personDetails"));
        // add(pCreate, "Create Person");
        addTab(pCreate, ApplicationContext.getViews().get(AdminEditPanel.VIEW_NAME).getMessages().get("createPerson"));

        super.addStyleName("gwt-TabBar-Nested");
        
    }

    protected void onLoad() {
        if (!loaded) {
            loaded = true;
            Controller c = MVC.findParentController(this);
            c.getEventDispatcher().addListener(SearchWidget.PersonSearchEvent.class, new MVCEventListener() {
                public void onEvent(Class<? extends MVCEvent> event, Object data) {
                    // select the search results tab
                    me.selectTab(0);
                }
            });
            
            c.getEventDispatcher().addListener(PersonSearchResultPanel.SelectPersonEvent.class, new MVCEventListener() {
                public void onEvent(Class<? extends MVCEvent> event, Object data) {
                    if(data == null) {
                        me.selectTab(0);
                    }
                }
            });
        }
    }
    
    public void displaySearchResultsTab() {
        this.selectTab(this.getWidgetIndex(pSearch));
    }

    public void displayDetailsResultsTab() {
        this.selectTab(this.getWidgetIndex(pDetails));
    }

    public void displayCreateResultsTab() {
        this.selectTab(this.getWidgetIndex(pCreate));
    }

    public PersonSearchPanel getPersonSearchWidget() {
        return pSearch;
    }

    public PersonDetailsWidget getPersonDetailsWidget() {
        return pDetails;
    }

    public CreatePersonWidget getPersonCreateWidget() {
        return pCreate;
    }

    private void addTab(Widget tabWidget, String text) {
        Label label = new Label(text);
        label.addStyleName("gwt-TabBarItem");
        add(tabWidget, label);
    }

}
