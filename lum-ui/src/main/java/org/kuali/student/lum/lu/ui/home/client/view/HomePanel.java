package org.kuali.student.lum.lu.ui.home.client.view;

import java.util.ArrayList;
import java.util.List;

import org.kuali.student.common.ui.client.widgets.KSLabel;

import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class HomePanel extends Composite{
    public HorizontalPanel mainPanel = new HorizontalPanel();
    public VerticalPanel menuPanel = new VerticalPanel();
    public SimplePanel contentPanel = new SimplePanel();
    
    public CreateCreditCoursePanel creditCoursePanel = new CreateCreditCoursePanel();
    public FindPanel findPanel = new FindPanel();
    
    private EventHandler handler = new EventHandler();
    
    public static String CREATE_CREDIT_COURSE = "Create an Academic Credit Course";
    public static String CREATE_NON_CREDIT_COURSE = "Create a Non Credit Course";
    public static String CREATE_GROUP = "Create a Group";
    public static String MODIFY_COURSE = "Modify Course";
    public static String MODIFY_PROGRAM = "Modify a Program";
    public static String RETIRE_COURSE = "Retire a Course";
    public static String RETIRE_PROGRAM = "Retire a Program";
    public static String FIND = "Find Course or Proposal";
    
    private MenuItemPanel creditCourse = new MenuItemPanel(CREATE_CREDIT_COURSE, creditCoursePanel);
    private MenuItemPanel nonCreditCourse = new MenuItemPanel(CREATE_NON_CREDIT_COURSE, null);
    private MenuItemPanel createGroup = new MenuItemPanel(CREATE_GROUP, null);
    private MenuItemPanel modifyCourse = new MenuItemPanel(MODIFY_COURSE, null);
    private MenuItemPanel modifyProgram = new MenuItemPanel(MODIFY_PROGRAM, null);
    private MenuItemPanel retireCourse = new MenuItemPanel(RETIRE_COURSE, null);
    private MenuItemPanel retireProgram = new MenuItemPanel(RETIRE_PROGRAM, null);
    private MenuItemPanel find = new MenuItemPanel(FIND, findPanel);
    
    private List<MenuItemPanel> menuItems = new ArrayList<MenuItemPanel>();
    
    private class MenuItemPanel extends Composite{
        KSLabel itemLabel = new KSLabel();
        FocusPanel thePanel = new FocusPanel();
        Widget content = null;
        boolean selected = false;
        
        public MenuItemPanel(String itemName, Widget content){
            this.content = content;
            itemLabel.setText(itemName);
            itemLabel.setWordWrap(true);
            thePanel.addStyleName("Course-Home-Menu-Item");
            itemLabel.addStyleName("Course-Home-Menu-Label");
            thePanel.addClickHandler(handler);
            thePanel.addMouseOverHandler(handler);
            thePanel.addMouseOutHandler(handler);
            thePanel.addFocusHandler(handler);
            thePanel.addBlurHandler(handler);
            thePanel.add(itemLabel);
            this.initWidget(thePanel);
        }
        
        public void deSelect(){
            selected = false;
            thePanel.removeStyleName("Course-Home-Menu-Item-Selected");
            itemLabel.removeStyleName("Course-Home-Menu-Label-Selected");
        }
        
        public void select(){
            selected = true;
            thePanel.addStyleName("Course-Home-Menu-Item-Selected");
            itemLabel.addStyleName("Course-Home-Menu-Label-Selected");
            contentPanel.setWidget(content);
        }
        
        public FocusPanel getPanel(){
            return thePanel;
        }
    }
    
    private class EventHandler implements ClickHandler, MouseOverHandler, MouseOutHandler, FocusHandler, BlurHandler{

        @Override
        public void onClick(ClickEvent event) {
            Widget sender = (Widget) event.getSource();
            
            //deselect menu items
            for(MenuItemPanel m : menuItems){
                m.deSelect();
            }

            if (sender == creditCourse.getPanel()) {
                creditCourse.select();
            }
            else if(sender == find.getPanel()){
                find.select();
            }
            
        }

        @Override
        public void onMouseOver(MouseOverEvent event) {
            Widget sender = (Widget) event.getSource();
            sender.addStyleName("Course-Home-Menu-Item-Hover");
        }

        @Override
        public void onMouseOut(MouseOutEvent event) {
            Widget sender = (Widget) event.getSource();
            sender.removeStyleName("Course-Home-Menu-Item-Hover");
        }

        @Override
        public void onFocus(FocusEvent event) {
            // no function yet
            
        }

        @Override
        public void onBlur(BlurEvent event) {
            // no function yet
            
        }
        
    }
    
    public HomePanel(){
        mainPanel.setStyleName("Course-Home-Main-Panel");
        menuPanel.setStyleName("Course-Home-Menu-Panel");
        contentPanel.setStyleName("Course-Home-Content-Panel");   
        setupMenuItems();
        mainPanel.add(menuPanel);
        mainPanel.add(contentPanel);
        this.initWidget(mainPanel);
    }
    
    private void setupMenuItems(){
        addMenuItem(creditCourse);
        addMenuItem(nonCreditCourse);
        addMenuItem(createGroup);
        addMenuItem(modifyCourse);
        addMenuItem(modifyProgram);
        addMenuItem(retireCourse);
        addMenuItem(retireProgram);
        addMenuItem(find);
    }
    
    private void addMenuItem(MenuItemPanel panel){
        menuPanel.add(panel);
        menuItems.add(panel);
    }
}
