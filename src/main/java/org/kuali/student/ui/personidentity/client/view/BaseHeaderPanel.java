/**
 * 
 */
package org.kuali.student.ui.personidentity.client.view;


import org.kuali.student.commons.ui.mvc.client.ApplicationContext;
import org.kuali.student.commons.ui.mvc.client.Controller;
import org.kuali.student.commons.ui.mvc.client.MVC;
import org.kuali.student.commons.ui.mvc.client.MVCEvent;
import org.kuali.student.commons.ui.mvc.client.MVCEventListener;
import org.kuali.student.commons.ui.propertychangesupport.PropertyChangeEvent;
import org.kuali.student.commons.ui.propertychangesupport.PropertyChangeListener;
import org.kuali.student.commons.ui.propertychangesupport.PropertyChangeListenerProxy;
import org.kuali.student.poc.client.BaseEvents;
import org.kuali.student.poc.client.POCMain;
import org.kuali.student.poc.client.admin.AdminPanel;
import org.kuali.student.poc.client.login.LoginComposite;
import org.kuali.student.ui.personidentity.client.ModelState;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DeferredCommand;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Composite;
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
public class BaseHeaderPanel extends Composite {
	
	public static final DateTimeFormat formatter = DateTimeFormat.getFormat("EEEE, MMM d, y");
	
	final BaseHeaderPanel me = this;
	   
    final VerticalPanel root = new VerticalPanel();
    final HorizontalPanel topRow = new HorizontalPanel();
    final HorizontalPanel topLeft = new HorizontalPanel();
    final HorizontalPanel topRight = new HorizontalPanel();
    final HorizontalPanel bottomRow = new HorizontalPanel();
    final HorizontalPanel linkPanel = new HorizontalPanel();
    
	final Hyperlink		help				= new Hyperlink();
	final Hyperlink		admin				= new Hyperlink();
	final Hyperlink		studentSystem		= new Hyperlink();
	final Hyperlink     logout              = new Hyperlink();

    final Image logo = new Image("images/logo.gif");
    final SearchWidget search = new SearchWidget();
    
//    final HorizontalPanel currSelectionPanel = new HorizontalPanel();
//    final Label currSelectionLabel = new Label(ApplicationContext.getViews().get(AdminEditPanel.VIEW_NAME).getMessages().get("currentSelection"));
//    final Label currSelection = new Label(ApplicationContext.getViews().get(AdminEditPanel.VIEW_NAME).getMessages().get("noneSelected"));
//    
//    final PropertyChangeListener currSelectionListener = new PropertyChangeListenerProxy (
//            "currPerson",
//            new PropertyChangeListener() {
//                public void propertyChange(
//                    PropertyChangeEvent propertyChangeEvent) {
//                    //updateView( (List<GwtPersonInfo>)propertyChangeEvent.getNewValue());
//                    String name = "";
//                    name += ModelState.getInstance().getCurrPerson().getPreferredName().getGivenName();
//                    name += " ";
//                    name += ModelState.getInstance().getCurrPerson().getPreferredName().getMiddleName();
//                    name += " ";
//                    name += ModelState.getInstance().getCurrPerson().getPreferredName().getSurname();
//                    name += " (";
//                    name += ModelState.getInstance().getCurrPerson().getPersonId();
//                    name += " )";
//                    
//                    currSelection.setText( name);
//                }
//            });
    
    final ClickListener helpListener = new ClickListener(){     
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
    
    final ClickListener adminListener = new ClickListener() {
        public void onClick(Widget sender) {
            Controller c = MVC.findParentController(me);
            c.getEventDispatcher().fireEvent(AdminPanel.SHOW_ADMIN_PANEL);
            //Use [] to indicate the current selection
            admin.setText("["+ApplicationContext.getViews().get(AdminEditPanel.VIEW_NAME).getMessages().get("admin")+"]");
            studentSystem.setText(ApplicationContext.getViews().get(AdminEditPanel.VIEW_NAME).getMessages().get("studentSystem"));
            
        }
    };
    
    final ClickListener studentSystemListener = new ClickListener() {
        public void onClick(Widget sender) {
            Controller c = MVC.findParentController(me);
            c.getEventDispatcher().fireEvent(AdminStudentTab.SHOW_ADMIN_STUDENT_TAB);
            //Use [] to indicate the current selection
            studentSystem.setText("["+ApplicationContext.getViews().get(AdminEditPanel.VIEW_NAME).getMessages().get("studentSystem")+"]");
            admin.setText(ApplicationContext.getViews().get(AdminEditPanel.VIEW_NAME).getMessages().get("admin"));

        }
    };
    
    final ClickListener logoutClickListener = new ClickListener() {
        public void onClick(Widget sender) {
            Controller c = MVC.findParentController(me);
            c.getEventDispatcher().fireEvent(POCMain.REQUEST_LOGOUT);
        }  
    };
    
    boolean loaded = false;
    
    public BaseHeaderPanel() {
        super.initWidget(root);
    }

    protected void onLoad() {
        if (!loaded) {
            loaded = true;
            
            doLayout();
            initStyles();
            initWidgetListeners();
            doEventWiring();
        }
    }

    private void doEventWiring() {
//        ModelState.getInstance().addPropertyChangeListener(currSelectionListener);
        
        
        final Controller c = MVC.findParentController(this);
        if (c != null) {
            c.getEventDispatcher().addListener(LoginComposite.LOGIN_SUCCESSFUL, new MVCEventListener() {
                public void onEvent(MVCEvent event, Object data) {
                    search.setVisible(true);
                    admin.setVisible(true);
                    studentSystem.setVisible(true);
                    logout.setVisible(true);
                    final MVCEventListener me = this;
                    DeferredCommand.addCommand(new Command() {
                        public void execute() {
                            c.getEventDispatcher().removeListener(LoginComposite.LOGIN_SUCCESSFUL, me);
                        }
                    });
                    
                }
            });
            
            c.getEventDispatcher().addListener(BaseEvents.SHOW_VIEW, new MVCEventListener() {
                public void onEvent(MVCEvent event, Object data) {
                    boolean b = event.isAssignableFrom(AdminStudentTab.SHOW_ADMIN_STUDENT_TAB);
                    search.setVisible(b);
//                    currSelectionPanel.setVisible(b);
                    
                }
            });
        }
    }

    private void initWidgetListeners() {
        studentSystem.addClickListener(studentSystemListener);
        admin.addClickListener(adminListener);
        help.addClickListener(helpListener);
        logout.addClickListener(logoutClickListener);
    }
    private void initStyles() {
    	
    	
        root.addStyleName("KS-Header");
        topRow.addStyleName("KS-Header-TopRow");
        bottomRow.addStyleName("KS-Header-BarText");
        topRight.addStyleName("KS-Header-TopRight");
        
        topLeft.addStyleName("KS-Header-TopLeft");
        
		
		bottomRow.addStyleName("KS-Header-BarText");
		
		studentSystem.addStyleName("KS-Header-Links");
		admin.addStyleName("KS-Header-Links");
		help.addStyleName("KS-Header-Links");
		logout.addStyleName("KS-Header-Links");
        
		linkPanel.addStyleName("KS-Header-LinkPanel");
		
//		currSelectionPanel.addStyleName("KS-Header-CurrentSelection-Panel");
//		currSelectionLabel.addStyleName("KS-Header-CurrentSelection-Label");
//		currSelection.addStyleName("KS-Header-CurrentSelection-Text");

		search.setStyleName("KS-Header-SearchWidget");
    }

    private void doLayout() {
        root.add(topRow);
        root.add(bottomRow);
        
        topRow.add(topLeft);
        topRow.add(topRight);
        
        topLeft.add(logo);
        topRight.add(search);


        admin.setText(ApplicationContext.getViews().get(AdminEditPanel.VIEW_NAME).getMessages().get("admin"));
        //Use [] to indicate the current selection
        studentSystem.setText("["+ApplicationContext.getViews().get(AdminEditPanel.VIEW_NAME).getMessages().get("studentSystem")+"]");
        logout.setText(ApplicationContext.getViews().get(AdminEditPanel.VIEW_NAME).getMessages().get("logout"));

//        bottomRow.add(currSelectionPanel);
//        currSelectionPanel.add(currSelectionLabel);
//        currSelectionPanel.add(currSelection);
        
        bottomRow.add(linkPanel);
        linkPanel.add(studentSystem);
        linkPanel.add(admin);
        linkPanel.add(help);
        linkPanel.add(logout);

        search.setVisible(false);
        admin.setVisible(false);
        studentSystem.setVisible(false);
        logout.setVisible(false);

    }
}
