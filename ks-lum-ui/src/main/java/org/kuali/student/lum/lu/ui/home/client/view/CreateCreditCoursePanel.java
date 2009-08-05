package org.kuali.student.lum.lu.ui.home.client.view;

import org.kuali.student.common.ui.client.mvc.Controller;
import org.kuali.student.common.ui.client.mvc.ViewComposite;
import org.kuali.student.common.ui.client.widgets.KSButton;
import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.lum.lu.ui.main.client.controller.LUMApplicationManager.LUMViews;
import org.kuali.student.lum.lu.ui.main.client.events.ChangeViewStateEvent;

import com.google.gwt.dom.client.HRElement;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class CreateCreditCoursePanel extends ViewComposite{


    private VerticalPanel mainPanel = new VerticalPanel();
    
    private KSButton viewProcess = new KSButton("View Process Overview");
    private KSButton startBlank = new KSButton("Start Blank Proposal");
    private KSButton selectTemplate = new KSButton("Select Proposal Template");
    private KSButton copyProposal = new KSButton("Copy Course Proposal");
    private KSButton copyCourse = new KSButton("Copy Existing Course");
    
    private ButtonEventHandler handler = new ButtonEventHandler();
    
    private class ButtonEventHandler implements ClickHandler{

        @Override
        public void onClick(ClickEvent event) {
            Widget sender = (Widget) event.getSource();
            
            if(sender == startBlank){
                CreateCreditCoursePanel.this.getController().fireApplicationEvent(new ChangeViewStateEvent<LUMViews>(LUMViews.CREATE_COURSE));
            }
        }
        
    }
    
    private class ButtonRow extends Composite{
        private HorizontalPanel row = new HorizontalPanel();
        private KSLabel descLabel = new KSLabel();
        
        public ButtonRow(Button theButton, String description){
            row.addStyleName("Home-Button-Row");
            row.addStyleName("Content-Left-Margin");
            descLabel.addStyleName("Home-Description-Label");
            theButton.addStyleName("Home-Standard-Button");
            descLabel.setWordWrap(true);
            
            descLabel.setText(description);
            row.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
            row.add(theButton); 
            row.add(descLabel);
            this.initWidget(row);
        }
        
        public ButtonRow(Button theButton, String description, Widget moreLink){
            this(theButton, description);
            descLabel.getElement().appendChild(moreLink.getElement());
        }
    }
    
    private class RowBreak extends Composite{
        private HorizontalPanel row = new HorizontalPanel();
        private HTML hr = new HTML("<HR />");
        public RowBreak(){
            row.addStyleName("Home-Horizontal-Break");
            row.add(hr);
            this.initWidget(row);
        }
    }
    
    public CreateCreditCoursePanel(Controller controller) {
        // TODO Bsmith - THIS CONSTRUCTOR NEEDS A JAVADOC
        super(controller, "Create Credit Course");
        Hyperlink more = new Hyperlink("More", "More");
        more.addStyleName("Home-Small-Hyperlink");
        mainPanel.add(new ButtonRow(viewProcess, "Description goes here here here here here"));
        mainPanel.add(new RowBreak());
        mainPanel.add(new ButtonRow(startBlank, "Description goes here here here here here", more));
        startBlank.addClickHandler(handler);
        mainPanel.add(new ButtonRow(selectTemplate, "Description goes here here here here here"));
        mainPanel.add(new ButtonRow(copyProposal, "Description goes here here here here here"));
        mainPanel.add(new ButtonRow(copyCourse, "Description goes here here here here here"));
        Hyperlink helpMeDecide = new Hyperlink("Help Me Decide", "HelpMe");
        helpMeDecide.addStyleName("Home-Small-Hyperlink");
        helpMeDecide.addStyleName("Content-Left-Margin");
        helpMeDecide.addClickHandler(new ClickHandler(){

            @Override
            public void onClick(ClickEvent event) {
                
                
            }
        });
        mainPanel.add(helpMeDecide);
        this.initWidget(mainPanel);
    }
}
