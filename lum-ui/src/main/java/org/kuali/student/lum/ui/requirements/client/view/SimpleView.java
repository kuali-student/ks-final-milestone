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
        rbCourseList.addFocusHandler(new FocusHandler() {
            public void onFocus(FocusEvent event) {
                enableChoice(1, true);
                enableChoice(2, false);
                enableChoice(3, false);
                enableChoice(4, false);
            }
        });
        rbCourse1OrCourse2.addFocusHandler(new FocusHandler() {
            public void onFocus(FocusEvent event) {
                enableChoice(1, false);
                enableChoice(2, true);
                enableChoice(3, false);
                enableChoice(4, false);
            }
        });
        rbNumCreditFromCourseList.addFocusHandler(new FocusHandler() {
            public void onFocus(FocusEvent event) {
                enableChoice(1, false);
                enableChoice(2, false);
                enableChoice(3, true);
                enableChoice(4, false);
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
    
    /**
     * returns a comma delimited string that represents the union of the originalValue and selectedValues
     * @param originalValue
     * @param selectedValues
     * @return
     */
    private String combineValues(String originalValue, List<String> selectedValues) {
        int fieldValueCount = 0;
        String tempOriginalValue = 
            (originalValue == null)? "" : originalValue;
        StringBuilder newFieldValue = new StringBuilder("");
        SortedSet<String> newValues = new TreeSet<String>();
        newValues.addAll(Arrays.asList(tempOriginalValue.split(", +")));
        newValues.addAll(selectedValues);
        for (String newValue : newValues) {
            if (fieldValueCount > 0 && 
                    newFieldValue.toString().trim().length() > 0) {
                newFieldValue.append(", ");
            }
            newFieldValue.append(newValue);
            fieldValueCount++;
        }
        return newFieldValue.toString();
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
        ruleTypes.add(setupRuleTypeChoicePanel());        
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
    
    private CaptionPanel setupRuleTypeChoicePanel() {
        
        CaptionPanel capPnlPickRuleType = new CaptionPanel("Student must have taken:");
       // capPnlPickRuleType.setStyleName("KS-RuleEditor-SimplePanel-ReqCompTypes");        
        VerticalPanel pnlChoices = new VerticalPanel(); 
        HorizontalPanel choice1 = new HorizontalPanel(); 
        HorizontalPanel choice2 = new HorizontalPanel(); 
        HorizontalPanel choice3 = new HorizontalPanel(); 
        choice1.setSpacing(5);
        choice2.setSpacing(5);
        choice3.setSpacing(5);       

        // choice 1
        KSLabel lbChoice1 = new KSLabel("The following course(s):");
        lbChoice1.addStyleName("KS-Rules-Choices");
        choice1.add(rbCourseList);
        choice1.add(lbChoice1);
        VerticalPanel holder = new VerticalPanel();
        choice1CourseList.setWidth("250px");
        holder.add(choice1CourseList);
        holder.add(searchPanelChoice1);
        searchPanelChoice1.addCourseAddHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                String origFieldValue = choice1CourseList.getText();
                String newFieldValue = combineValues(origFieldValue, searchPanelChoice1.getSelections());
                choice1CourseList.setText(newFieldValue);
            }
        });
        choice1.add(holder);
                
        // choice 2
        choice2.add(rbCourse1OrCourse2);
        choice2CourseA.setWidth("100px");
        VerticalPanel holder2a = new VerticalPanel();
        holder2a.add(choice2CourseA);
        holder2a.add(searchPanelChoice2a);
        searchPanelChoice2a.addCourseAddHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                String origFieldValue = choice2CourseA.getText();
                String newFieldValue = combineValues(origFieldValue, searchPanelChoice2a.getSelections());
                choice2CourseA.setText(newFieldValue);
            }
        });        
        choice2.add(holder2a);
        
        KSLabel lbChoice2 = new KSLabel("or");
        
        lbChoice2.addStyleName("KS-Rules-Choices");
        choice2.add(lbChoice2);
        choice2CourseB.setWidth("100px");        
        VerticalPanel holder2b = new VerticalPanel();
        holder2b.add(choice2CourseB);
        holder2b.add(searchPanelChoice2b);
        searchPanelChoice2b.addCourseAddHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                String origFieldValue = choice2CourseB.getText();
                String newFieldValue = combineValues(origFieldValue, searchPanelChoice2b.getSelections());
                choice2CourseB.setText(newFieldValue);
            }
        });        
        choice2.add(holder2b);               
        
        // choice 3
        KSLabel lbChoice3 = new KSLabel("credits from the following course(s):");
        lbChoice3.addStyleName("KS-Rules-Choices");
        choice3.add(rbNumCreditFromCourseList);
        choice3NumCredits.setWidth("50px");
        choice3.add(choice3NumCredits);
        choice3.add(lbChoice3);
        choice3CourseList.setWidth("150px");      
        VerticalPanel holder3 = new VerticalPanel();
        holder3.add(choice3CourseList);
        holder3.add(searchPanelChoice3);        
        searchPanelChoice3.addCourseAddHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                String origFieldValue = choice3CourseList.getText();
                String newFieldValue = combineValues(origFieldValue, searchPanelChoice3.getSelections());
                choice3CourseList.setText(newFieldValue);
            }
        });
        choice3.add(holder3);
        
        pnlChoices.add(choice1);
        pnlChoices.add(choice2);
        pnlChoices.add(choice3);
        capPnlPickRuleType.add(pnlChoices);   
        
        enableChoice(1, false);
        enableChoice(2, false);
        enableChoice(3, false);   
        
        return capPnlPickRuleType;
    }
    
    private void enableChoice(int choice, boolean enabled) {
        switch (choice) {
            case 1:
                enableTextBox(choice1CourseList, enabled);
                searchPanelChoice1.setEnabled(enabled);
                break;
            case 2:
                enableTextBox(choice2CourseA, enabled);
                searchPanelChoice2a.setEnabled(enabled);                
                enableTextBox(choice2CourseB, enabled);
                searchPanelChoice2b.setEnabled(enabled);                
                break;
            case 3:
                enableTextBox(choice3NumCredits, enabled);
                enableTextBox(choice3CourseList, enabled);
                searchPanelChoice3.setEnabled(enabled);
                break;
        }
    }
    
    private void enableTextBox(KSTextBox textField, boolean enabled) {
        if (enabled) {
//          DOM.setStyleAttribute(choice1CourseList.getElement(),
//          "backgroundColor", "#FFFFFF");
            textField.removeStyleName(
                    "KS-Rules-Choices-Disabled-TextField");
        } else {
            textField.addStyleName(
                    "KS-Rules-Choices-Disabled-TextField");
        }
        textField.setEnabled(enabled);
    }
}
