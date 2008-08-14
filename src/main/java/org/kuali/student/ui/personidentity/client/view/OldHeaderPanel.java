/**
 * 
 */
package org.kuali.student.ui.personidentity.client.view;

import java.util.Date;

import org.kuali.student.commons.ui.mvc.client.ApplicationContext;
import org.kuali.student.commons.ui.mvc.client.Controller;
import org.kuali.student.commons.ui.mvc.client.EventTypeRegistry;
import org.kuali.student.commons.ui.mvc.client.MVC;
import org.kuali.student.commons.ui.mvc.client.MVCEvent;
import org.kuali.student.commons.ui.mvc.client.MVCEventListener;
import org.kuali.student.commons.ui.propertychangesupport.PropertyChangeEvent;
import org.kuali.student.commons.ui.propertychangesupport.PropertyChangeListener;
import org.kuali.student.commons.ui.propertychangesupport.PropertyChangeListenerProxy;
import org.kuali.student.commons.ui.widgets.DockLayout;
import org.kuali.student.poc.client.BaseEvents;
import org.kuali.student.poc.client.POCMain;
import org.kuali.student.poc.client.admin.AdminPanel;
import org.kuali.student.poc.client.login.LoginComposite;
import org.kuali.student.ui.personidentity.client.ModelState;

import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DeferredCommand;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author Garey
 *
 */
public class OldHeaderPanel extends Composite {
	
	public static final DateTimeFormat formatter = DateTimeFormat.getFormat("EEEE, MMM d, y");
	
	final OldHeaderPanel me = this;
	final HorizontalPanel	rootPanel			= new HorizontalPanel();
	final Image			logo				= new Image();
	final HorizontalPanel		dPanel				= new HorizontalPanel();
	final HorizontalPanel links = new HorizontalPanel();
	final Hyperlink		help				= new Hyperlink();
	final Hyperlink		admin				= new Hyperlink();
	final Hyperlink		studentSystem		= new Hyperlink();
	final Hyperlink     logout              = new Hyperlink();
	
	final FlexTable 		currSelectionPanel 	= new FlexTable();
	final SearchWidget	sWidget				= new SearchWidget();
	//Label			currSelection		= new Label("None Selected");
	//final Label			currSelection		= new Label(I18N.i18nConstant.noneSelected());
	final Label			currSelection		= new Label(ApplicationContext.getViews().get(AdminEditPanel.VIEW_NAME).getMessages().get("noneSelected"));
	
	final VerticalPanel	centerPanel			= new VerticalPanel();
	final VerticalPanel	datePanel			= new VerticalPanel();
	
	final HorizontalPanel			topPanel			= new HorizontalPanel();			
	final Label				currDate			= new Label();
	final CurrentUserPanel 	currUser			= new CurrentUserPanel();
	
	private PropertyChangeListener currSelectionListener  
	= new PropertyChangeListenerProxy(
            "currPerson",
            new PropertyChangeListener() {
                public void propertyChange(
                    PropertyChangeEvent propertyChangeEvent) {
                    //updateView( (List<GwtPersonInfo>)propertyChangeEvent.getNewValue());
                	String name = "";
                	name += ModelState.getInstance().getCurrPerson().getPreferredName().getGivenName();
                	name += " ";
                	name += ModelState.getInstance().getCurrPerson().getPreferredName().getMiddleName();
                	name += " ";
                	name += ModelState.getInstance().getCurrPerson().getPreferredName().getSurname();
                	name += " (";
                	name += ModelState.getInstance().getCurrPerson().getPersonId();
                	name += " )";
                	
                	currSelection.setText( name);
                }
            });
	
	protected ClickListener helpListener = new ClickListener(){		
		public void onClick(Widget sender) {
			PopupPanel p = new PopupPanel(true);
			//p.add(new Label("No Help For You!"));
			//p.add(new Label(I18N.i18nConstant.noHelpForYou()));
			p.add(new Label(ApplicationContext.getViews().get(AdminEditPanel.VIEW_NAME).getMessages().get("noHelpForYou")));
			
			p.addStyleName("KS-Popup-Help");
			int top = help.getAbsoluteTop() +30;
			int left = help.getAbsoluteLeft();
			p.setPopupPosition(left, top);
			
			p.show();
		}};
	
	protected ClickListener adminListener = new ClickListener() {
		public void onClick(Widget sender) {
			Controller c = MVC.findParentController(me);
			c.getEventDispatcher().fireEvent(AdminPanel.ShowAdminPanel.class);
		}
	};
	
	protected ClickListener studentSystemListener = new ClickListener() {
		public void onClick(Widget sender) {
			Controller c = MVC.findParentController(me);
			c.getEventDispatcher().fireEvent(AdminStudentTab.ShowAdminStudentTab.class);
		}
	};
	
	protected ClickListener logoutClickListener = new ClickListener() {
	    public void onClick(Widget sender) {
            Controller c = MVC.findParentController(me);
            c.getEventDispatcher().fireEvent(POCMain.RequestLogout.class);
        }  
	};
	
	
	boolean loaded = false;
	
	public OldHeaderPanel() {
		initWidget(dPanel);
	}
	/**
	 * 
	 */
	protected void onLoad() {
		if (!loaded) {
			loaded = true;
		
			HorizontalPanel hPanel1 = new HorizontalPanel();
			HorizontalPanel hPanel2 = new HorizontalPanel();
			HorizontalPanel hPanel3 = new HorizontalPanel();
			Image headerCenter = new Image();
			VerticalPanel vPanel1 = new VerticalPanel();
			
			logo.setUrl(GWT.getModuleBaseURL() +  "images/logo.gif");
			headerCenter.setUrl(GWT.getModuleBaseURL() +  "images/bg_silver.gif");
			
			hPanel1.add(logo);
			hPanel1.setStyleName("KS-Header-Logo");
			//hPanel1.setHorizontalAlignment(CENTER);
			
			hPanel2.setStyleName("KS-HeaderCenter");
			hPanel2.add(headerCenter);
			
			hPanel3.setStyleName("KS-HeaderRight");
			
			dPanel.add(hPanel1);
			dPanel.add(hPanel2);
			dPanel.add(hPanel3);
			
			currDate.setText(formatter.format(new Date()));
			currDate.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
			
			datePanel.add(currDate);
			datePanel.setStyleName("KS-DateTimeText");
			 
			rootPanel.setWidth("100%");
			//rootPanel.addStyleName("KS-Header-Panel");
			
			//Label textLabel = new Label("Current Selection");
			//final Label textLabel = new Label(I18N.i18nConstant.currentSelection());
			final Label textLabel = new Label(ApplicationContext.getViews().get(AdminEditPanel.VIEW_NAME).getMessages().get("currentSelection"));
			
			textLabel.addStyleName("KS-Header-CurrentSelection-Text");
			textLabel.setVisible(false);
			currSelection.setVisible(false);
			
			currSelectionPanel.setWidget(0,0,textLabel);
			currSelectionPanel.setWidget(0,1,currSelection);
			//temporary placeholder- need to make the currSelectionPanel a widget
			//currSelectionPanel.addStyleName("KS-Header-CurrentSelection-Panel");
			currSelectionPanel.addStyleName("KS-HeaderBarText");
			currSelection.addStyleName("KS-Header-CurrentSelection-Text");
			
			
			logo.setUrl(GWT.getModuleBaseURL() +  "images/logo.gif");
			logo.setStyleName("KS-Header-Logo");
			
			help.setText(ApplicationContext.getViews().get(AdminEditPanel.VIEW_NAME).getMessages().get("help"));
			help.addClickListener(helpListener);
			
			topPanel.add(currUser);
			topPanel.add(currDate);
			
			centerPanel.add(topPanel);
			centerPanel.add(sWidget);
			centerPanel.addStyleName("KS-Header");
			
			
			centerPanel.setWidth("100%");
			topPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
			topPanel.setWidth("100%");
			
			/*
			dPanel.add(currSelectionPanel, DockPanel.SOUTH);
			dPanel.add(logo, DockPanel.WEST);		
			dPanel.add(centerPanel, DockPanel.CENTER);	
			dPanel.add(links, DockPanel.EAST);
			dPanel.add(datePanel, DockPanel.SOUTH);
			*/
			
//			HorizontalPanel southPanel = new HorizontalPanel();
//			
//			southPanel.add(currSelectionPanel);
//			southPanel.add(datePanel);
//			dPanel.getSouth().add(southPanel);
//			dPanel.getWest().add(logo);		
//			dPanel.getCenter().add(centerPanel);	
//			dPanel.getEast().add(links);
			
			
			
			
			// TODO i18n
			admin.setText(ApplicationContext.getViews().get(AdminEditPanel.VIEW_NAME).getMessages().get("admin"));
			admin.addClickListener(adminListener);
			studentSystem.setText(ApplicationContext.getViews().get(AdminEditPanel.VIEW_NAME).getMessages().get("studentSystem"));
			studentSystem.addClickListener(studentSystemListener);
			logout.setText(ApplicationContext.getViews().get(AdminEditPanel.VIEW_NAME).getMessages().get("logout"));
			logout.addClickListener(logoutClickListener);
			logout.setVisible(false);
			
			links.addStyleName("KS-Header-Link-Panel");
			links.add(studentSystem);
			links.add(admin);
			links.add(help);
			links.add(logout);
			
			ModelState.getInstance().addPropertyChangeListener(currSelectionListener);
					
	
			sWidget.setVisible(false);
			admin.setVisible(false);
			studentSystem.setVisible(false);
			final Controller c = MVC.findParentController(this);
			if (c != null) {
				c.getEventDispatcher().addListener(LoginComposite.LoginSuccessfulEvent.class, new MVCEventListener() {
					public void onEvent(Class<? extends MVCEvent> event, Object data) {
						sWidget.setVisible(true);
						admin.setVisible(true);
						studentSystem.setVisible(true);
						logout.setVisible(true);
						final MVCEventListener me = this;
						DeferredCommand.addCommand(new Command() {
							public void execute() {
								c.getEventDispatcher().removeListener(LoginComposite.LoginSuccessfulEvent.class, me);
							}
						});
						
					}
				});
				
				c.getEventDispatcher().addListener(BaseEvents.ShowView.class, new MVCEventListener() {
					public void onEvent(Class<? extends MVCEvent> event, Object data) {
						boolean b = EventTypeRegistry.isSubClass(AdminStudentTab.ShowAdminStudentTab.class, event);
						sWidget.setVisible(b);
						textLabel.setVisible(b);
						currSelection.setVisible(b);
					}
				});
			}
		}
	}
	
	public void setSearchVisible(boolean b) {
		sWidget.setVisible(b);
	}
	public boolean isSearchVisible() {
		return sWidget.isVisible();
	}

}
