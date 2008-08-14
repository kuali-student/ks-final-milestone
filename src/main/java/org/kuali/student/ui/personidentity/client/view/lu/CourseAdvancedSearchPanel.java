package org.kuali.student.ui.personidentity.client.view.lu;

import java.util.List;

import org.kuali.student.commons.ui.mvc.client.ApplicationContext;
import org.kuali.student.commons.ui.mvc.client.Controller;
import org.kuali.student.commons.ui.mvc.client.MVC;
import org.kuali.student.commons.ui.mvc.client.MVCEvent;
import org.kuali.student.commons.ui.mvc.client.MVCEventListener;
import org.kuali.student.commons.ui.widgets.BusyWidgetShade;
import org.kuali.student.ui.personidentity.client.controller.LearningUnitController;
import org.kuali.student.ui.personidentity.client.model.lu.GwtLuTypeInfo;
import org.kuali.student.ui.personidentity.client.model.lu.GwtLuiCriteria;
import org.kuali.student.ui.personidentity.client.model.lu.GwtLuiInfo;
import org.kuali.student.ui.personidentity.client.view.AdminEditPanel;
import org.kuali.student.ui.personidentity.client.view.HidablePanel;
import org.kuali.student.ui.personidentity.client.view.SearchWidget;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class CourseAdvancedSearchPanel extends HidablePanel {

    ListBox luTypes = new ListBox();

    TextBox courseTitleTextBox = new TextBox();
    Label courseTitleLabel = new Label(ApplicationContext.getViews().get(AdminEditPanel.VIEW_NAME).getMessages().get("luType"));

    TextBox description = new TextBox();
    Label courseNumberLabel = new Label(ApplicationContext.getViews().get(AdminEditPanel.VIEW_NAME).getMessages().get("description"));

    TextBox courseInstructorTextBox = new TextBox();
    Label courseInstructorLabel = new Label(ApplicationContext.getViews().get(AdminEditPanel.VIEW_NAME).getMessages().get("instructor"));

    Button btnSearch = new Button(ApplicationContext.getViews().get(AdminEditPanel.VIEW_NAME).getMessages().get("search"));

    boolean loaded = false;

    public CourseAdvancedSearchPanel() {
        setup();
        initStyles();
    }

    private void initStyles() {
        courseTitleTextBox.addStyleName("KS-TextBox");
        courseTitleLabel.addStyleName("KS-Label");
        description.addStyleName("KS-TextBox");
        courseNumberLabel.addStyleName("KS-Label");
        courseInstructorLabel.addStyleName("KS-Label");
        courseInstructorTextBox.addStyleName("KS-TextBox");
        luTypes.addStyleName("KS-ListBox");
        btnSearch.addStyleName("KS-Button");
    }
    protected void onLoad() {
        super.onLoad();
        if (!loaded) {
            loaded = true;
            this.loadLuTypes();
			final Controller c = MVC.findParentController(this);
			if (c != null) {
				c.getEventDispatcher().addListener(SearchWidget.CourseSearchEvent.class, new MVCEventListener() {
					public void onEvent(Class<? extends MVCEvent> event, Object data) {
						description.setText("");
					}
				});
			}
        }
    }

    protected void setup() {

        btnSearch.addClickListener(new ClickListener() {
            public void onClick(Widget sender) {
                BusyWidgetShade.shade(btnSearch); 
                GwtLuiCriteria criteria = new GwtLuiCriteria();
                criteria.setDescription(description.getText());
                // if(!"".equals(description.getText()))
                criteria.setDescription("%" + description.getText() + "%");
                if (!"".equals(luTypes.getValue(luTypes.getSelectedIndex()))) {
                    criteria.setLuTypeKey(luTypes.getValue(luTypes.getSelectedIndex()));
                }
                LearningUnitController.searchForLuis(criteria, new AsyncCallback(){               
                    public void onFailure(Throwable caught) {
                        BusyWidgetShade.unshade(btnSearch); 
                        Window.alert(caught.getMessage());                            
                    }

                    public void onSuccess(Object result) {
                        LearningUnitController.updateSearchResults(description.getText(), (List<GwtLuiInfo>)result);
                        BusyWidgetShade.unshade(btnSearch);                       
                    }});
            }
        });

        FlexTable advanced = new FlexTable();
        advanced.setWidget(0, 0, courseTitleLabel);
        advanced.setWidget(0, 1, luTypes);

        advanced.setWidget(1, 0, courseNumberLabel);
        advanced.setWidget(1, 1, description);

        advanced.setWidget(2, 1, btnSearch);

        courseTitleLabel.addStyleName("KS-CourseTitle-Label");
        courseTitleTextBox.addStyleName("KS-CourseTitle-TextBox");

        description.addStyleName("KS-CourseNumber-Label");
        courseNumberLabel.addStyleName("KS-CourseNumber-Label");

        courseInstructorTextBox.addStyleName("KS-CourseInstructor-Label");
        courseInstructorLabel.addStyleName("KS-CourseInstructor-Label");

        this.setCenterWidget(advanced);
        this.setTitle(ApplicationContext.getViews().get(AdminEditPanel.VIEW_NAME).getMessages().get("advancedCourseSearch"));
        this.addStyleName("KS-PersonAdvancedSearch-Panel");

        
    }

    protected void loadLuTypes() {
        LearningUnitController.findLuTypes(new AsyncCallback() {
            public void onFailure(Throwable caught) {
                Window.alert(caught.getMessage());
            }

            public void onSuccess(Object result) {
                List<GwtLuTypeInfo> lTypes = (List<GwtLuTypeInfo>) result;
                if (lTypes != null) {
                    luTypes.addItem("", "");
                    for (GwtLuTypeInfo lType : lTypes) {
                        luTypes.addItem(lType.getDescription(), lType.getLuTypeKey());
                    }
                }
            }
        });
    }

}
