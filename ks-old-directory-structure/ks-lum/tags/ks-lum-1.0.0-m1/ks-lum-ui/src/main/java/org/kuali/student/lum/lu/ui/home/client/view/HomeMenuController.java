package org.kuali.student.lum.lu.ui.home.client.view;

import java.util.ArrayList;
import java.util.List;

import org.kuali.student.common.ui.client.mvc.ApplicationEvent;
import org.kuali.student.common.ui.client.mvc.Controller;
import org.kuali.student.common.ui.client.mvc.View;
import org.kuali.student.common.ui.client.mvc.ViewComposite;
import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.lum.lu.ui.main.client.events.ChangeViewStateEvent;

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

public class HomeMenuController extends Controller{
    
    public enum MenuViews {
        DEFAULT_VIEW, CREATE_COURSE_VIEW, FIND_VIEW
    }
    
    public View defaultPanel = new DefaultPanel(this);
    public View creditCoursePanel = new CreateCreditCoursePanel(this);
    public View findPanel = new FindPanel(this);
    
    public HorizontalPanel mainPanel = new HorizontalPanel();
    public VerticalPanel menuPanel = new VerticalPanel();
    public SimplePanel contentPanel = new SimplePanel();
    

    
    private EventHandler handler = new EventHandler();
    
    public static String CREATE_CREDIT_COURSE = "Create an Academic Credit Course";
    public static String CREATE_NON_CREDIT_COURSE = "Create a Non Credit Course";
    public static String CREATE_GROUP = "Create a Group";
    public static String MODIFY_COURSE = "Modify Course";
    public static String MODIFY_PROGRAM = "Modify a Program";
    public static String RETIRE_COURSE = "Retire a Course";
    public static String RETIRE_PROGRAM = "Retire a Program";
    public static String FIND = "Find Course or Proposal";
    
    private MenuItemPanel creditCourse = new MenuItemPanel(CREATE_CREDIT_COURSE);
    private MenuItemPanel nonCreditCourse = new MenuItemPanel(CREATE_NON_CREDIT_COURSE);
    private MenuItemPanel createGroup = new MenuItemPanel(CREATE_GROUP);
    private MenuItemPanel modifyCourse = new MenuItemPanel(MODIFY_COURSE);
    private MenuItemPanel modifyProgram = new MenuItemPanel(MODIFY_PROGRAM);
    private MenuItemPanel retireCourse = new MenuItemPanel(RETIRE_COURSE);
    private MenuItemPanel retireProgram = new MenuItemPanel(RETIRE_PROGRAM);
    private MenuItemPanel find = new MenuItemPanel(FIND);
    
    private List<MenuItemPanel> menuItems = new ArrayList<MenuItemPanel>();
    
    private class MenuItemPanel extends Composite{
        KSLabel itemLabel = new KSLabel();
        FocusPanel thePanel = new FocusPanel();
        boolean selected = false;
        
        public MenuItemPanel(String itemName){
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
                showView(MenuViews.CREATE_COURSE_VIEW);
            }
            else if(sender == find.getPanel()){
                find.select();
                showView(MenuViews.FIND_VIEW);
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
        
    public HomeMenuController(){
        SimplePanel simple = new SimplePanel();
        mainPanel.setStyleName("Course-Home-Main-Panel");
        menuPanel.setStyleName("Course-Home-Menu-Panel");
        contentPanel.setStyleName("Course-Home-Content-Panel");   
        setupMenuItems();
        mainPanel.add(menuPanel);
        mainPanel.add(contentPanel);
        simple.setWidget(mainPanel);
        simple.addStyleName("Course-Home-Menu-Container");
        this.initWidget(simple);
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
    
    //Controller Methods

    @Override
    protected <V extends Enum<?>> View getView(V viewType) {
        switch ((MenuViews) viewType) {
            case DEFAULT_VIEW:
                return defaultPanel;
            case CREATE_COURSE_VIEW:
                return creditCoursePanel;
            case FIND_VIEW:
                return findPanel;
            default:
                return defaultPanel;
        }
    }

    @Override
    protected void hideView(View view) {
        contentPanel.clear();
    }

    @Override
    protected void renderView(View view) {
        contentPanel.setWidget((ViewComposite) view);
    }

    @Override
    public void showDefaultView() {
    	creditCourse.select();
        showView(MenuViews.CREATE_COURSE_VIEW);
        
    }

    @Override
    public void fireApplicationEvent(ApplicationEvent event) {
        
        if ((event instanceof ChangeViewStateEvent) && (getParentController() != null)) {
            this.getParentController().fireApplicationEvent(event);
        }
        else{
            super.fireApplicationEvent(event);
        }
    }

    public Class<? extends Enum<?>> getViewsEnum() {
        return MenuViews.class;
    }
    
}
