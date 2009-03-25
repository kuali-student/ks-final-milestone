package org.kuali.student.ui.kitchensink.client.kscommons.sidebar;

import static org.kuali.student.ui.kitchensink.client.KitchenSinkStyleConstants.STYLE_EXAMPLE;

import org.kuali.student.common.ui.client.widgets.KSButton;
import org.kuali.student.common.ui.client.widgets.KSImage;
import org.kuali.student.common.ui.client.widgets.KSSidebar;
import org.kuali.student.common.ui.client.widgets.KSTextBox;
import org.kuali.student.common.ui.client.widgets.KSFloatPanel.FloatLocation;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

public class SidebarExample extends Composite {


    final HorizontalPanel main = new HorizontalPanel();
    KSSidebar sidebar;
    final VerticalPanel contentPanel1 = new VerticalPanel();
    final VerticalPanel contentPanel2 = new VerticalPanel();
    final VerticalPanel contentPanel3 = new VerticalPanel();

    final KSButton showRightButton ;
    final KSButton showLeftButton ;
    final KSButton removeSidebarButton ;

    final KSTextBox tb1 = new KSTextBox();
    final KSTextBox tb2 = new KSTextBox();
    final KSTextBox tb3 = new KSTextBox();


    public SidebarExample() {

        main.addStyleName(STYLE_EXAMPLE);

        showRightButton = new KSButton("Click to see Sidebar Right",
                new ClickHandler() {

            @Override
            public void onClick(ClickEvent arg0) {
                if(sidebar != null){
                    sidebar.hide();
                }
                sidebar = new KSSidebar(FloatLocation.FLOAT_RIGHT);
                populateContent();
                sidebar.show();

            }});

        showLeftButton = new KSButton("Click to see Sidebar Left",
                new ClickHandler() {

            @Override
            public void onClick(ClickEvent arg0) {
                if(sidebar != null){
                    sidebar.hide();
                }
                sidebar = new KSSidebar(FloatLocation.FLOAT_LEFT);
                populateContent();
                sidebar.show();

            }});
        
        removeSidebarButton = new KSButton("Click to remove the Sidebar", 
                new ClickHandler(){

            @Override
            public void onClick(ClickEvent arg0) {
                if(sidebar != null){
                    sidebar.hide();
                }
                
            }});

        main.add(showLeftButton);
        main.add(showRightButton);
        main.add(removeSidebarButton);
        initializeContent();
        super.initWidget(main);

    }
    
    public void initializeContent(){
        contentPanel1.add(new KSImage("images/flower1.jpg") );
        contentPanel1.add(new KSImage("images/flower2.jpg") );
        contentPanel1.add(new KSImage("images/flower3.jpg") );
        contentPanel1.add(new KSImage("images/flower1.jpg") );
        contentPanel1.add(new KSImage("images/flower2.jpg") );
        contentPanel1.add(new KSImage("images/flower3.jpg") );
        contentPanel1.add(new KSImage("images/flower1.jpg") );
        contentPanel1.add(new KSImage("images/flower2.jpg") );
        contentPanel1.add(new KSImage("images/flower3.jpg") );
        
        contentPanel2.add(new Label("About Kuali Student: " +
        		"Kuali Student is a next generation student system being " +
        		"developed over a five year period through the Community Source Community Source " +
        		"refers to the collaborative development approach of the project team building " +
        		"Kuali Student. Distinct from open-source development that solicits input from anyone, " +
        		"community-source development limits participation to a group of committed contributors. " +
        		"The Kuali Student team currently includes members and resources from all Founder " +
        		"and Partner institutions. process. It will be sustained by an international community " +
        		"of institutions and firms. Find out who’s involved, how and when it will be developed."));
        
        contentPanel3.add(new Label("Enter Data:"));
        contentPanel3.add(tb1);
        contentPanel3.add(tb2);
        contentPanel3.add(tb3);
        
        KSButton submit = new KSButton("Submit");
        submit.addClickHandler(new ClickHandler(){

            @Override
            public void onClick(ClickEvent event) {
                Window.alert("You entered: " + tb1.getText() + tb2.getText() + tb3.getText());
                
            }});
        contentPanel3.add(submit);
    }
    
    public void populateContent(){

        sidebar.addTab(contentPanel1, "Images");

        sidebar.addTab(contentPanel2, "Text");

        sidebar.addTab(contentPanel3, "Widgets");
    }

}
