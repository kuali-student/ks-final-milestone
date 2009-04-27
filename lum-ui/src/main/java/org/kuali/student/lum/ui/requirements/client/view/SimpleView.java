package org.kuali.student.lum.ui.requirements.client.view;

import java.util.Arrays;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import org.kuali.student.common.ui.client.mvc.Controller;
import org.kuali.student.common.ui.client.mvc.Model;
import org.kuali.student.common.ui.client.mvc.ModelRequestCallback;
import org.kuali.student.common.ui.client.mvc.ViewComposite;
import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.common.ui.client.widgets.KSRadioButton;
import org.kuali.student.common.ui.client.widgets.KSRichEditor;
import org.kuali.student.common.ui.client.widgets.KSStyles;
import org.kuali.student.common.ui.client.widgets.KSTextBox;
import org.kuali.student.lum.ui.requirements.client.controller.PrereqManager.PrereqViews;
import org.kuali.student.lum.ui.requirements.client.model.PrereqInfo;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.user.client.ui.CaptionPanel;
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
    private KSRadioButton rbExistingRule = new KSRadioButton(SIMPLE_RULE_RB_GROUP);
    private KSTextBox choice1CourseList = new KSTextBox();
    private final SearchDialog searchPanelChoice1 = new SearchDialog(getController());
    private KSTextBox choice2CourseA = new KSTextBox();
    private KSTextBox choice2CourseB = new KSTextBox();
    private KSTextBox choice3NumCredits = new KSTextBox();
    private KSTextBox choice3CourseList = new KSTextBox();
    private final SearchDialog searchPanelChoice3 = new SearchDialog(getController());
    private KSLabel choice4ExistingRule = new KSLabel("existing pre-requisite");
    
    private Model<PrereqInfo> model;

    public SimpleView(Controller controller) {
        super(controller, "Simple View");
        super.initWidget(mainPanel);
        setupHandlers();
    }
    
    private void setupHandlers() {
        linkToComplexView.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
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
        rbExistingRule.addFocusHandler(new FocusHandler() {
            public void onFocus(FocusEvent event) {
                enableChoice(1, false);
                enableChoice(2, false);
                enableChoice(3, false);
                enableChoice(4, true);
            }
        });
    }
    
    public void beforeShow() {       
        if (model == null) {
            getController().requestModel(PrereqInfo.class, new ModelRequestCallback<PrereqInfo>() {
                public void onModelReady(Model<PrereqInfo> theModel) {
                    //printModel(theModel);
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
        Panel simpleView = new VerticalPanel();
        KSLabel Heading = new KSLabel("Rationale:");
        Heading.setStyleName("KS-ReqMgr-Heading");
        simpleView.add(Heading);
        Object[] temp = model.getValues().toArray();
        rationalle.setText(((PrereqInfo)temp[0]).getRationale());
        simpleView.add(rationalle);
        
        CaptionPanel capPnlPickRuleType = 
            new CaptionPanel("Student must have taken:");
        capPnlPickRuleType.setStyleName("KS-RuleEditor-SimplePanel-ReqCompTypes");        
        VerticalPanel pnlChoices = new VerticalPanel(); 
        HorizontalPanel choice1 = new HorizontalPanel(); 
        HorizontalPanel choice2 = new HorizontalPanel(); 
        HorizontalPanel choice3 = new HorizontalPanel(); 
        HorizontalPanel choice4 = new HorizontalPanel();
        choice1.setSpacing(5);
        choice2.setSpacing(5);
        choice3.setSpacing(5);
        choice4.setSpacing(5);
        // choice 1
        KSLabel lbChoice1 = new KSLabel("The following course(s):");
        lbChoice1.addStyleName("KS-Rules-Choices");
        choice1.add(rbCourseList);
        choice1.add(lbChoice1);
        choice1CourseList.setWidth("150px");
        choice1.add(choice1CourseList);
        choice1.add(searchPanelChoice1);
        searchPanelChoice1.addCourseAddHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                String origFieldValue = choice1CourseList.getText();
                String newFieldValue = 
                    combineValues(
                            origFieldValue,
                            searchPanelChoice1.getSelections());
                choice1CourseList.setText(newFieldValue);
            }
        });
        
        // choice 2
        KSLabel lbChoice2 = new KSLabel("or");
        lbChoice2.addStyleName("KS-Rules-Choices");
        choice2.add(rbCourse1OrCourse2);
        choice2CourseA.setWidth("100px");
        choice2.add(choice2CourseA);
        choice2.add(lbChoice2);
        choice2CourseB.setWidth("100px");
        choice2.add(choice2CourseB);
        // choice 3
        KSLabel lbChoice3 = new KSLabel("credits from the following course(s):");
        lbChoice3.addStyleName("KS-Rules-Choices");
        choice3.add(rbNumCreditFromCourseList);
        choice3NumCredits.setWidth("50px");
        choice3.add(choice3NumCredits);
        choice3.add(lbChoice3);
        choice3CourseList.setWidth("150px");
        choice3.add(choice3CourseList);
        choice3.add(searchPanelChoice3);
        searchPanelChoice3.addCourseAddHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                String origFieldValue = choice3CourseList.getText();
                String newFieldValue = 
                    combineValues(
                            origFieldValue,
                            searchPanelChoice3.getSelections());
                choice3CourseList.setText(newFieldValue);
            }
        });
        // choice 4
        KSLabel lbChoice4 = new KSLabel("Use an");
        lbChoice4.addStyleName("KS-Rules-Choices");
        choice4.add(rbExistingRule);
        choice4.add(lbChoice4);
//        choice4ExistingRule.addStyleName("KS-Rules-Choices-Link");
        choice4.add(choice4ExistingRule);
        
        pnlChoices.add(choice1);
        pnlChoices.add(choice2);
        pnlChoices.add(choice3);
        pnlChoices.add(choice4);
        capPnlPickRuleType.add(pnlChoices);
        
        linkToComplexView.addStyleName("KS-Rules-Link");
        simpleView.add(capPnlPickRuleType);
        simpleView.add(linkToComplexView);
        simpleView.setStyleName("Content-Margin");
        mainPanel.clear();
        mainPanel.add(simpleView);
    }
    
    private void enableChoice(int choice, boolean enabled) {
        switch (choice) {
            case 1:
                choice1CourseList.setEnabled(enabled);
                searchPanelChoice1.setEnabled(enabled);
                break;
            case 2:
                choice2CourseA.setEnabled(enabled);
                choice2CourseB.setEnabled(enabled);
                break;
            case 3:
                choice3NumCredits.setEnabled(enabled);
                choice3CourseList.setEnabled(enabled);
                searchPanelChoice3.setEnabled(enabled);
                break;
            case 4:
                if (enabled) {
                    choice4ExistingRule.addStyleName("KS-Rules-Choices-Link");
                } else {
                    choice4ExistingRule.removeStyleName("KS-Rules-Choices-Link");
                }
                break;
        }
    }
}
