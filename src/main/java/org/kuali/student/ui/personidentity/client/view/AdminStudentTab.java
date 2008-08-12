package org.kuali.student.ui.personidentity.client.view;


import org.kuali.student.commons.ui.mvc.client.ApplicationContext;
import org.kuali.student.commons.ui.mvc.client.Controller;
import org.kuali.student.commons.ui.mvc.client.MVC;
import org.kuali.student.commons.ui.mvc.client.MVCEvent;
import org.kuali.student.commons.ui.mvc.client.MVCEventListener;
import org.kuali.student.poc.client.BaseEvents.ShowView;
import org.kuali.student.poc.client.login.LoginComposite;
import org.kuali.student.registration.client.controller.RegistrationController;
import org.kuali.student.ui.personidentity.client.ModelState;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.SourcesTabEvents;
import com.google.gwt.user.client.ui.TabListener;
import com.google.gwt.user.client.ui.TabPanel;

public class AdminStudentTab extends TabPanel{
	public static abstract class ShowAdminStudentTab extends ShowView {}
	public static final ShowAdminStudentTab SHOW_ADMIN_STUDENT_TAB = GWT.create(ShowAdminStudentTab.class);
	final AdminStudentTab me = this;
	
	 PersonTab pTab = null;
	 CourseTab cTab = null;
	 RegistrationTab rTab = null;
	
	boolean loaded = false;
	
	TabListener queryBasket = new TabListener(){
		public boolean onBeforeTabSelected(SourcesTabEvents sender, int tabIndex) {
			return true;
		}
		public void onTabSelected(SourcesTabEvents sender, int tabIndex) {
			if(ModelState.getInstance().getCurrPerson() != null)
				RegistrationController.getInstance().setCurrentUserBasket(ModelState.getInstance().getCurrPerson().getPersonId());		
		}};
	
	TabListener queryCourseList = new TabListener(){
			public boolean onBeforeTabSelected(SourcesTabEvents sender, int tabIndex) {
				return true;
			}
			public void onTabSelected(SourcesTabEvents sender, int tabIndex) {
				if(ModelState.getInstance().getCurrPerson() != null)
					RegistrationController.getInstance().setCurrentUserCourses(ModelState.getInstance().getCurrPerson().getPersonId());		
			}};
	
	public AdminStudentTab(){
		
		pTab = new PersonTab();
		cTab = new CourseTab();
		rTab = new RegistrationTab();
		
		//rTab.addTabListener(queryBasket);
		//rTab.addTabListener(queryCourseList);
		
		//add(pTab, "People");
		//add(pTab, I18N.i18nConstant.peopleTab());
		add(pTab, ApplicationContext.getViews().get(AdminEditPanel.VIEW_NAME).getMessages().get("peopleTab"));
		//add(cTab, "Course");
		//add(cTab, I18N.i18nConstant.course());
		add(cTab, ApplicationContext.getViews().get(AdminEditPanel.VIEW_NAME).getMessages().get("course"));
		
		//add(rTab, "Registration");
		//add(rTab, I18N.i18nConstant.registration());
		
		//add(rTab, ApplicationContext.getViews().get(AdminEditPanel.VIEW_NAME).getMessages().get("registration"));
		//rTab.setVisible(false);
		//rTab.
		this.selectTab(0);
		

	
	}
	
	
	
	
	protected void onLoad() {
		super.onLoad();
		if (!loaded) {
			loaded = true;
			
			this.setWidth("100%");
			this.setHeight("100%");
			getPersonTab().displaySearchResultsTab(); // on first load, display the person search results tab
			
			final Controller c = MVC.findParentController(this);
			if (c != null) 
			{
				c.getEventDispatcher().addListener(PersonSearchResultPanel.PERSON_SELECTED, new MVCEventListener() 
				{
					public void onEvent(MVCEvent event, Object data) 
					{
						if(data != null)
							me.addRegistrationTab();
						else
							me.removeRegistrationTab();
					}
				});
				c.getEventDispatcher().addListener(SearchWidget.PERSON_SEARCH, new MVCEventListener() {
				    public void onEvent(MVCEvent event, Object data) {
				        // select the person tab when people are searched for
				        me.selectTab(0);
                    }
				});
			}
		}
	}
	
	public void addRegistrationTab()
	{
		if(!rTab.isAttached())
		{
			add(rTab, ApplicationContext.getViews().get(AdminEditPanel.VIEW_NAME).getMessages().get("registration"));
		}
	}
	
	public void removeRegistrationTab()
	{
		if(rTab.isAttached())
		{
			this.remove(rTab);
		}
	}

	public PersonTab getPersonTab(){
		return (PersonTab)this.getWidget(this.getWidgetIndex(pTab));		
	}
	
	public CourseTab getCourseTab(){
		return (CourseTab)this.getWidget(this.getWidgetIndex(cTab));
	}
}
