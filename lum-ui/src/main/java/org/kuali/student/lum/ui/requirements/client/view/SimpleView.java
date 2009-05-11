package org.kuali.student.lum.ui.requirements.client.view;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import org.kuali.student.common.ui.client.mvc.Controller;
import org.kuali.student.common.ui.client.mvc.Model;
import org.kuali.student.common.ui.client.mvc.ModelRequestCallback;
import org.kuali.student.common.ui.client.mvc.ViewComposite;
import org.kuali.student.common.ui.client.widgets.KSButton;
import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.common.ui.client.widgets.KSRadioButton;
import org.kuali.student.common.ui.client.widgets.KSRichEditor;
import org.kuali.student.common.ui.client.widgets.KSTextBox;
import org.kuali.student.lum.ui.requirements.client.controller.PrereqManager.PrereqViews;
import org.kuali.student.lum.ui.requirements.client.model.PrereqInfo;
import org.kuali.student.lum.ui.requirements.client.model.ReqComponentVO;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.user.client.ui.CaptionPanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class SimpleView extends ViewComposite {
    
    private Panel mainPanel = new SimplePanel();
    private KSRichEditor rationalle = new KSRichEditor();
    private KSLabel linkToComplexView = new KSLabel("Show me more options...");
    private static final String SIMPLE_RULE_RB_GROUP = "SIMPLE_RULE_RB_GROUP";
    private KSRadioButton rbCourseList = new KSRadioButton(SIMPLE_RULE_RB_GROUP); 
    private KSRadioButton rbCourse1OrCourse2 = new KSRadioButton(SIMPLE_RULE_RB_GROUP); 
    private KSRadioButton rbNumCreditFromCourseList = new KSRadioButton(SIMPLE_RULE_RB_GROUP);    
    private KSTextBox choice1CourseList = new KSTextBox();
    private final SearchDialog searchPanelChoice1 = new SearchDialog(getController());
    private KSTextBox choice2CourseA = new KSTextBox();
    private KSTextBox choice2CourseB = new KSTextBox();
    private final SearchDialog searchPanelChoice2a = new SearchDialog(getController());
    private final SearchDialog searchPanelChoice2b = new SearchDialog(getController());
    private KSTextBox choice3NumCredits = new KSTextBox();
    private KSTextBox choice3CourseList = new KSTextBox();
    private final SearchDialog searchPanelChoice3 = new SearchDialog(getController());
    private KSButton btnAddPrereq = new KSButton("Add Prerequisite");    
    
    private Model<PrereqInfo> model;

    public SimpleView(Controller controller) {
        super(controller, "Simple View");
        super.initWidget(mainPanel);
        setupHandlers();
    }
    
    private void setupHandlers() {
        linkToComplexView.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                getController().requestModel(ReqComponentVO.class, new ModelRequestCallback<ReqComponentVO>() {
                    public void onModelReady(Model<ReqComponentVO> theModel) {
                        if (theModel != null) {
                            List<ReqComponentVO> selectedReqComp = new ArrayList<ReqComponentVO>();
                            selectedReqComp.addAll(theModel.getValues());
                            if (selectedReqComp.size() > 0) {
                                theModel.remove(selectedReqComp.get(0).getId());        //we should have only 1 selected Req. Comp. in the model
                            }
                        }                    
                        redraw();
                    }

                    public void onRequestFail(Throwable cause) {
                        throw new RuntimeException("Unable to connect to model", cause);
                    }
                }); 
                getController().showView(PrereqViews.COMPLEX);
            }
        });
    }
    
    public void beforeShow() {       
        if (model == null) {
            getController().requestModel(PrereqInfo.class, new ModelRequestCallback<PrereqInfo>() {
                public void onModelReady(Model<PrereqInfo> theModel) {
                    model = theModel;                    
                    setupHandlers();
                    redraw();
                }

                public void onRequestFail(Throwable cause) {
                    throw new RuntimeException("Unable to connect to model", cause);
                }
            });
        }
        else
        {
            redraw();
        }
    }
   
    
    private void redraw() {
        
        VerticalPanel simpleView = new VerticalPanel();
        KSLabel heading = new KSLabel("Edit Prerequisite Rule");
        heading.setStyleName("KS-ReqMgr-Heading");
        simpleView.add(heading);

        FlexTable prereq = new FlexTable();
        prereq.setCellSpacing(25);
        
        //first show rule's rationale
        KSLabel rationalleHeading = new KSLabel("Rationalle");
        rationalleHeading.setStyleName("KS-ReqMgr-Label");        
        VerticalPanel holder = new VerticalPanel();
        holder.setHeight("100%");
        holder.add(rationalleHeading);
        holder.setCellVerticalAlignment(rationalleHeading, HasVerticalAlignment.ALIGN_TOP);
        prereq.setWidget(0, 0, holder);  

        Object[] temp = model.getValues().toArray();
        rationalle.setText(((PrereqInfo)temp[0]).getRationale());
        rationalle.setStyleName("KS-RichText-Normal");

        prereq.setWidget(0, 1, rationalle);
        
        //then show rule type
        KSLabel ruleTypeHeading = new KSLabel("Rule type");
        ruleTypeHeading.setStyleName("KS-ReqMgr-Label"); 
        VerticalPanel holder1 = new VerticalPanel();
        holder1.setHeight("100%");
        holder1.add(ruleTypeHeading);
        holder1.setCellVerticalAlignment(ruleTypeHeading, HasVerticalAlignment.ALIGN_TOP);        
        prereq.setWidget(1, 0, holder1);                            
        
        VerticalPanel ruleTypes = new VerticalPanel();
       // ruleTypes.add(setupRuleTypeChoicePanel());        
        linkToComplexView.addStyleName("KS-Rules-Link");
        ruleTypes.add(linkToComplexView);
        btnAddPrereq.addStyleName("KS-Rules-Tight-Button"); 
        ruleTypes.add(btnAddPrereq);
        prereq.setWidget(1, 1, ruleTypes);         
        
        simpleView.add(prereq);
        simpleView.setStyleName("Content-Margin");
        mainPanel.clear();
        mainPanel.add(simpleView);
    }      
}
