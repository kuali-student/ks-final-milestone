package org.kuali.student.ui.personidentity.client.view.lu;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import org.kuali.student.commons.ui.mvc.client.ApplicationContext;
import org.kuali.student.commons.ui.propertychangesupport.PropertyChangeEvent;
import org.kuali.student.commons.ui.propertychangesupport.PropertyChangeListener;
import org.kuali.student.commons.ui.propertychangesupport.PropertyChangeListenerProxy;
import org.kuali.student.registration.client.controller.RegistrationController;
import org.kuali.student.registration.client.model.GwtLuiPersonRelationDisplay;
import org.kuali.student.registration.client.model.GwtLuiPersonRelationInfo;
import org.kuali.student.registration.client.model.RegistrationModelState;
import org.kuali.student.registration.client.service.RegistrationService;
import org.kuali.student.ui.personidentity.client.ModelState;
import org.kuali.student.ui.personidentity.client.view.AdminEditPanel;
import org.kuali.student.ui.personidentity.client.view.HidablePanel;
import org.kuali.student.ui.personidentity.client.view.SearchResultElementWidget;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class EnrollmentSummaryPanel extends FlowPanel {
	
	VerticalPanel vpBasketCourses = null;
	VerticalPanel registeredCourses = null;
	
	List<GwtLuiPersonRelationDisplay> basket = null;
	List<GwtLuiPersonRelationDisplay> courses = null;
	
	Label			lblRegisteredCourses = null;
	
	Button	btnRegister = null;
	Button	btnDrop = null;
	
	
	private PropertyChangeListener currUserListener
	= new PropertyChangeListenerProxy(
            "currPerson",
            new PropertyChangeListener() {
                public void propertyChange(
                    PropertyChangeEvent propertyChangeEvent) {
                    //updateView( (List<GwtPersonInfo>)propertyChangeEvent.getNewValue());
                    if (ModelState.getInstance().getCurrPerson() == null) {
                        RegistrationController.getInstance().setCurrentUserLuiRelations(null);
                        updateBasket( null);
                        updateCourses(null);
                    } else {
                        RegistrationController.getInstance().setCurrentUserLuiRelations(ModelState.getInstance().getCurrPerson().getPersonId());
                        updateBasket( RegistrationModelState.getInstance().getCurrUserRelations());
                        updateCourses( RegistrationModelState.getInstance().getCurrUserRelations());
                    }
                }
            });

	private PropertyChangeListener currUserCoursesListener  
	= new PropertyChangeListenerProxy(
            "currUserRelations",
            new PropertyChangeListener() {
                public void propertyChange(
                    PropertyChangeEvent propertyChangeEvent) {
                    //updateView( (List<GwtPersonInfo>)propertyChangeEvent.getNewValue());
                	updateBasket( RegistrationModelState.getInstance().getCurrUserRelations());
                	updateCourses( RegistrationModelState.getInstance().getCurrUserRelations());
                }
            });
	
	
	ClickListener listRegister = new ClickListener(){
		public void onClick(Widget sender) {
			List<String> luIds = new Vector<String>();
			if(ModelState.getInstance().getCurrPerson() != null){
				
				Iterator<Widget> i = vpBasketCourses.iterator();
				while(i.hasNext()){
					SearchResultElementWidget b = (SearchResultElementWidget)i.next();
					if(b.getCBox().isChecked()){
						luIds.add(((BasketCourseWidget)b.getW()).getCrs().getLuiPersonRelationId());
					}
				}								
				
				if(luIds != null && luIds.isEmpty() == false){
					GwtLuiPersonRelationInfo pInfo = new GwtLuiPersonRelationInfo();
					pInfo.setLuiPersonRelationType(RegistrationService.REL_STUDENT);
					pInfo.setRelationState(RegistrationService.STATE_COMPLETE);
					
					RegistrationController.getInstance().updateLuiPersonRelation(luIds, 
							pInfo, btnRegister);					
					/*
					RegistrationController.getInstance().createLuiPersonRelation(
						ModelState.getInstance().getCurrPerson().getPersonId(), 
						luIds, 
						RegistrationService.STATE_COMPLETE , RegistrationService.REL_STUDENT, new GwtLuiPersonRelationInfo());
					}*/
				}
			}
		}};
		
		ClickListener dropCourse = new ClickListener(){
			public void onClick(Widget sender) {
				List<String> luIds = new Vector<String>();
				if(ModelState.getInstance().getCurrPerson() != null){
					
					Iterator<Widget> i = registeredCourses.iterator();
					while(i.hasNext()){
						SearchResultElementWidget b = (SearchResultElementWidget)i.next();
						if(b.getCBox().isChecked()){
							luIds.add(((BasketCourseWidget)b.getW()).getCrs().getLuiPersonRelationId());
						}
					}								
					
					if(luIds != null && luIds.isEmpty() == false){
						GwtLuiPersonRelationInfo pInfo = new GwtLuiPersonRelationInfo();
						pInfo.setLuiPersonRelationType(RegistrationService.REL_STUDENT);
						pInfo.setRelationState(RegistrationService.STATE_COMPLETE);
						
						RegistrationController.getInstance().deleteLuiPersonRelation(luIds);
						/*
						RegistrationController.getInstance().createLuiPersonRelation(
							ModelState.getInstance().getCurrPerson().getPersonId(), 
							luIds, 
							RegistrationService.STATE_COMPLETE , RegistrationService.REL_STUDENT, new GwtLuiPersonRelationInfo());
						}*/
					}
				}
			}};
	
	
	public EnrollmentSummaryPanel() {
		vpBasketCourses = new VerticalPanel();
		registeredCourses = new VerticalPanel();
		lblRegisteredCourses = new Label(ApplicationContext.getViews().get(AdminEditPanel.VIEW_NAME).getMessages().get("registeredCourses"));
		btnRegister = new Button(ApplicationContext.getViews().get(AdminEditPanel.VIEW_NAME).getMessages().get("registerCourses"));
		btnDrop = new Button(ApplicationContext.getViews().get(AdminEditPanel.VIEW_NAME).getMessages().get("dropCourse"));
		
		btnRegister.addClickListener(listRegister);
		btnDrop.addClickListener(dropCourse);
		ModelState.getInstance().addPropertyChangeListener(currUserListener);
						
		RegistrationModelState.getInstance().addPropertyChangeListener(currUserCoursesListener);
		//add(getLeftPanel(), DockPanel.WEST);
		//add(getCenterPanel(),DockPanel.CENTER);
        add(getLeftPanel());
        add(getCenterPanel());
		
		initStyles();
	}

    private void initStyles() {
        lblRegisteredCourses.addStyleName("KS-Label");
        btnRegister.addStyleName("KS-Button");
        btnDrop.addStyleName("KS-Button");
        

    }
	private Panel getCenterPanel() {
	
		VerticalPanel center = new VerticalPanel();
		
		center.add(lblRegisteredCourses);
		center.add(registeredCourses);
		center.add(btnDrop);
		
		center.addStyleName("enrollCenter");
		return center;
	}

	private Panel getLeftPanel() {
		HidablePanel p = new HidablePanel();
		VerticalPanel centerPanel = new VerticalPanel();
		
		centerPanel.add(vpBasketCourses);
		centerPanel.add(btnRegister);
		
		p.setTitle(ApplicationContext.getViews().get(AdminEditPanel.VIEW_NAME).getMessages().get("enrollmentCart"));
		p.setCenterWidget(centerPanel);
		
		p.addStyleName("enrollHidable");
				
		return p;
	}
	
	public void updateBasket(List<GwtLuiPersonRelationDisplay> basket){
        vpBasketCourses.clear();
        this.basket = new Vector<GwtLuiPersonRelationDisplay>();
		if(basket != null){
			for(GwtLuiPersonRelationDisplay info: basket){
				if(info.getRelationState().getState().equals(RegistrationService.STATE_BASKET.getState())){
					this.basket.add(info);
					SearchResultElementWidget sre = 
						new SearchResultElementWidget(new BasketCourseWidget(info));
					vpBasketCourses.add(sre);
				}
								
			}
		}
	}
	
	public void updateCourses(List<GwtLuiPersonRelationDisplay> courses){
        registeredCourses.clear();
        this.courses = new Vector<GwtLuiPersonRelationDisplay>();		
		if(courses != null){
			for(GwtLuiPersonRelationDisplay info: courses){
				
				if(info.getRelationState().getState().equals(RegistrationService.STATE_COMPLETE.getState())){
					this.courses.add(info);
					SearchResultElementWidget sre = 
						new SearchResultElementWidget(new BasketCourseWidget(info));
					registeredCourses.add(sre);
				}
			}
		}
	}
	
	public class BasketCourseWidget extends VerticalPanel {
		GwtLuiPersonRelationDisplay crs = null;
		Label		lblCrs = null;
		public BasketCourseWidget(GwtLuiPersonRelationDisplay crs){
			this.crs = crs;
			lblCrs = new Label(crs.getLuiDisplay().getCluDisplay().getCluShortName() + " - " + crs.getLuiDisplay().getLuiCode());
			this.add(lblCrs);
			
		}

		public GwtLuiPersonRelationDisplay getCrs(){
			return this.crs;
		}
		public void setCrs(GwtLuiPersonRelationDisplay in){
			this.crs = in;
		}
	}
	
}
