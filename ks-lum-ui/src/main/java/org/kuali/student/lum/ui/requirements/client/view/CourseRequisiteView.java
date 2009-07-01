package org.kuali.student.lum.ui.requirements.client.view;

import org.kuali.student.common.ui.client.mvc.Controller;
import org.kuali.student.common.ui.client.mvc.Model;
import org.kuali.student.common.ui.client.mvc.ViewComposite;
import org.kuali.student.common.ui.client.widgets.KSButton;
import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.common.ui.client.widgets.KSTextArea;
import org.kuali.student.lum.lu.dto.CluInfo;
import org.kuali.student.lum.ui.requirements.client.RulesUtilities;
import org.kuali.student.lum.ui.requirements.client.controller.CourseReqManager;
import org.kuali.student.lum.ui.requirements.client.controller.CourseReqManager.PrereqViews;
import org.kuali.student.lum.ui.requirements.client.model.CourseRuleInfo;
import org.kuali.student.lum.ui.requirements.client.model.EditHistory;
import org.kuali.student.lum.ui.requirements.client.model.RuleInfo;
import org.kuali.student.lum.ui.requirements.client.model.StatementVO;
import org.kuali.student.lum.ui.requirements.client.service.RequirementsRpcService;
import org.kuali.student.lum.ui.requirements.client.service.RequirementsRpcServiceAsync;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class CourseRequisiteView extends ViewComposite {
    private RequirementsRpcServiceAsync requirementsRpcServiceAsync = GWT.create(RequirementsRpcService.class);
    
    //view's widgets
    private final SimplePanel mainPanel = new SimplePanel();
    private final SimplePanel viewPanel = new SimplePanel();
    private final VerticalPanel rulesPanel = new VerticalPanel();    
    private VerticalPanel prereqRulePanel = new VerticalPanel();
    private VerticalPanel coreqRulePanel = new VerticalPanel();
    private VerticalPanel antireqRulePanel = new VerticalPanel();
    private VerticalPanel enrollRulePanel = new VerticalPanel();    

    private ButtonEventHandler handler = new ButtonEventHandler(); 
    
    //view's data
    private String selectedCourseId = null;
    private static int course_id = 0;
    private Model<CourseRuleInfo> courseData = new Model<CourseRuleInfo>();    
    private final Model<RuleInfo> courseRules = new Model<RuleInfo>();
    private final CourseReqManager courseReqManager = new CourseReqManager(courseRules);
    private boolean dataInitialized = false;
    
    public CourseRequisiteView(Controller controller) {
        super(controller, "Course Requisites");
        super.initWidget(mainPanel);                   
    }
    
    public void initializeView(String courseId) {
        
        mainPanel.clear();
        viewPanel.clear();
        mainPanel.add(viewPanel);
        
        this.selectedCourseId = courseId; 

        layoutMainPanel(viewPanel);
        if ((courseId == null) || (dataInitialized)) {
            return;
        }        
        
        requirementsRpcServiceAsync.getCourseAndRulesInfo(courseId, new AsyncCallback<CourseRuleInfo>() {
                                   
            public void onFailure(Throwable caught) {
                Window.alert(caught.getMessage());
            }

            public void onSuccess(final CourseRuleInfo courseRuleInfo) { 
                
                RulesUtilities.clearModel(courseRules);
                
                courseData.add(courseRuleInfo);                                                
                
                retrieveRuleTypeData("kuali.luStatementType.prereqAcademicReadiness");
                retrieveRuleTypeData("kuali.luStatementType.coreqAcademicReadiness");                           
                retrieveRuleTypeData("kuali.luStatementType.antireqAcademicReadiness");
                retrieveRuleTypeData("kuali.luStatementType.enrollAcademicReadiness");
                dataInitialized = true;
            }
            
        });                                  
    }    
    
    private void retrieveRuleTypeData(final String luStatementTypeKey) {
        
        requirementsRpcServiceAsync.getStatementVO(courseData.get(getCourseId()).getId(), luStatementTypeKey, new AsyncCallback<StatementVO>() {
            public void onFailure(Throwable caught) {
                Window.alert(caught.getMessage());
                caught.printStackTrace();
            }
            
            public void onSuccess(final StatementVO statementVO) {
               
                if (statementVO == null) {
                    layoutOneRuleTypeSection(luStatementTypeKey);
                    return;
                }
                
                final RuleInfo ruleInfo = new RuleInfo();
                ruleInfo.setId(Integer.toString(course_id++));
                ruleInfo.setCluId(courseData.get(getCourseId()).getId());
                ruleInfo.setRationale("This is a test rationalle.");
                ruleInfo.setStatementVO(statementVO);
                ruleInfo.setLuStatementTypeKey(luStatementTypeKey);
                
                EditHistory editHistory = new EditHistory();
                editHistory.save(statementVO);
                ruleInfo.setEditHistory(editHistory);
                
                setRuleInfo(ruleInfo);                    
                                        
                requirementsRpcServiceAsync.getNaturalLanguageForStatementVO(courseData.get(getCourseId()).getId(), statementVO, "KUALI.CATALOG", new AsyncCallback<String>() {
                    public void onFailure(Throwable caught) {
                        Window.alert(caught.getMessage());
                        caught.printStackTrace();
                    }
                    
                    public void onSuccess(final String statementNaturalLanguage) {                               
                        ruleInfo.setNaturalLanguage(statementNaturalLanguage);
                        layoutOneRuleTypeSection(luStatementTypeKey);                               
                    } 
                });                                               
            } 
        });            
    }
    
    
    private class ButtonEventHandler implements ClickHandler{

        @Override
        public void onClick(ClickEvent event) {
            Widget sender = (Widget) event.getSource();
          
            RuleInfo rule = getRuleInfo(sender.getTitle());
            
            if (sender.getTitle().contains("prereq")) {
                courseReqManager.setLuStatementType("kuali.luStatementType.prereqAcademicReadiness");
            } else if (sender.getTitle().contains("coreq")) {
                courseReqManager.setLuStatementType("kuali.luStatementType.coreqAcademicReadiness");
            } else if (sender.getTitle().contains("enroll")) {
                courseReqManager.setLuStatementType("kuali.luStatementType.enrollAcademicReadiness");
            } else if (sender.getTitle().contains("anti")) {
                courseReqManager.setLuStatementType("kuali.luStatementType.antiAcademicReadiness");
            }
            
            if(rule == null) {
                System.out.println("Clearing model");
                RulesUtilities.clearModel(courseData);
                CourseRuleInfo newCourse = new CourseRuleInfo();
                newCourse.setId("CLU-NL-5"); //Integer.toString(tempCounterID++));
                CluInfo newCourseInfo = new CluInfo();
                newCourseInfo.setId("CLU-NL-5"); //Integer.toString(tempCounterID+1000));
                newCourse.setCourseInfo(newCourseInfo);
                courseData.add(newCourse);
                
                RulesUtilities.clearModel(courseRules);
                RuleInfo newPrereqInfo = new RuleInfo();
                newPrereqInfo.setCluId("CLU-NL-5"); //Integer.toString(tempCounterID-1));
                newPrereqInfo.setId("CLU-NL-5"); //Integer.toString(tempCounterID-1));
                newPrereqInfo.setStatementVO(null);
                newPrereqInfo.setEditHistory(new EditHistory());
                courseRules.add(newPrereqInfo);               
                
                courseReqManager.resetReqCompVOModel();
                
                mainPanel.clear();
                mainPanel.add(courseReqManager.getMainPanel());
                courseReqManager.showView(PrereqViews.CLAUSE_EDITOR);
            } else {
                mainPanel.clear();
                mainPanel.add(courseReqManager.getMainPanel());
                courseReqManager.showDefaultView();
            }
        }       
    }    
    
    public void setRuleInfo(RuleInfo ruleInfo) {        
        RuleInfo rule = getRuleInfo(ruleInfo.getLuStatementTypeKey());
        if (rule != null) {
            courseRules.remove(rule);
        }        
        courseRules.add(ruleInfo);
    }
    
    private RuleInfo getRuleInfo(String luStatementTypeKey) {
        if (courseRules.getValues() != null && !courseRules.getValues().isEmpty()) {
            for (RuleInfo ruleInfo : courseRules.getValues()) {
                if (ruleInfo.getLuStatementTypeKey().equals(luStatementTypeKey)) {
                    return ruleInfo;
                }                
            }
        } 
        return null;
    }
    
    private void layoutMainPanel(Panel parentPanel) {

        rulesPanel.clear();
        rulesPanel.setSpacing(5);
        
        //main header
        SimplePanel tempPanel = new SimplePanel();
        tempPanel.setStyleName("KS-Rules-FullWidth");
        KSLabel preReqHeading = new KSLabel("Rules");
        preReqHeading.setStyleName("KS-ReqMgr-Heading");
        tempPanel.add(preReqHeading);       
        rulesPanel.add(tempPanel);     

        rulesPanel.add(enrollRulePanel);  
        rulesPanel.add(prereqRulePanel); 
        rulesPanel.add(coreqRulePanel); 
        rulesPanel.add(antireqRulePanel);       
        
        parentPanel.add(rulesPanel);
        parentPanel.setStyleName("Content-Margin");
    }
    
    private void layoutOneRuleTypeSection(String luStatementTypeKey) {
        
        RuleInfo rule = getRuleInfo(luStatementTypeKey);     
        String ruleName = getRuleTypeName(luStatementTypeKey);
        
        VerticalPanel rulesInfoPanel = new VerticalPanel();
          
        if (luStatementTypeKey.contains("enroll")) rulesInfoPanel = enrollRulePanel;
        if (luStatementTypeKey.contains("prereq")) rulesInfoPanel = prereqRulePanel;
        if (luStatementTypeKey.contains("coreq")) rulesInfoPanel = coreqRulePanel;
        if (luStatementTypeKey.contains("antireq")) rulesInfoPanel = antireqRulePanel;        
        rulesInfoPanel.clear();
        
        //HEADING: prerequisite rules   
        RulesUtilities ruleUtil = new RulesUtilities();
        RulesUtilities.Divider divider = ruleUtil.new Divider();
        rulesInfoPanel.add(divider);         
        KSLabel heading = new KSLabel(ruleName);
        heading.setStyleName("KS-ReqMgr-SubHeading");
        rulesInfoPanel.add(heading);
        
        //BODY: rules RATIONALE
        HorizontalPanel rationalePanel = new HorizontalPanel();
        SimplePanel tempHolder = new SimplePanel();
        KSLabel rationale = new KSLabel("Rationale");
        rationale.setStyleName("KS-ReqMgr-Field-Labels");
       // tempHolder.setWidth("100px");
        tempHolder.add(rationale);    
        rationalePanel.add(tempHolder);
        KSTextArea rantionale = new KSTextArea();
        rationalePanel.add(rantionale);
        SimplePanel tempHolder4 = new SimplePanel();
        KSLabel note = new KSLabel("State why this course needs to have a " + ruleName.toLowerCase() + ".");
        note.setStyleName("KS-ReqMgr-Note");         
        tempHolder4.add(note);      
        rationalePanel.add(tempHolder4);
        rulesInfoPanel.add(rationalePanel);               
        
        //BODY: rules        
        HorizontalPanel rationalePanel2 = new HorizontalPanel();
        SimplePanel tempHolder2 = new SimplePanel();
        KSLabel rules = new KSLabel("Rules");
        rules.setStyleName("KS-ReqMgr-Field-Labels");
       // tempHolder2.setWidth("100px");
        tempHolder2.add(rules);           
        rationalePanel2.add(tempHolder2);   
        rulesInfoPanel.add(rationalePanel2);        
        
        //BUTTONS
        HorizontalPanel rationalePanel3 = new HorizontalPanel();
        KSLabel rationale3 = new KSLabel("");
        rationale3.setStyleName("KS-ReqMgr-Field-Labels"); 
        rationalePanel3.add(rationale3);        
        rulesInfoPanel.add(rationalePanel3);           
        
        SimplePanel rulesText = new SimplePanel();
        rulesText.setWidth("400px");
        rationalePanel2.add(rulesText);
        
        if ((selectedCourseId == null) || (selectedCourseId.isEmpty()) || (rule == null)) {
            KSLabel preReqText = new KSLabel("No " + ruleName.toLowerCase() + " rules has been added.");
            preReqText.setStyleName("KS-ReqMgr-NoRuleText");
            rulesText.add(preReqText);
            rationalePanel2.add(rulesText);
            KSButton AddRule = new KSButton("Add Rule");
            AddRule.setTitle(luStatementTypeKey);
            rationalePanel3.add(AddRule);
            AddRule.setStyleName("KS-Rules-Tight-Button");
            AddRule.addClickHandler(handler);
        } else {
            rulesText.add(new KSLabel(rule.getNaturalLanguage()));
            KSButton ManageRules = new KSButton("Manage rules");
            ManageRules.setTitle(luStatementTypeKey);            
            rationalePanel3.add(ManageRules);
            ManageRules.setStyleName("KS-Rules-Tight-Button");           
            ManageRules.addClickHandler(handler);
        }         
    }
    
    private String getRuleTypeName(String luStatementTypeKey) {
        if (luStatementTypeKey.contains("enroll")) return "Enrollment Restrictions";
        if (luStatementTypeKey.contains("prereq")) return "Prerequisites";
        if (luStatementTypeKey.contains("coreq")) return "Corequisites";
        if (luStatementTypeKey.contains("antireq")) return "Antirequisites";
        return "";
    }
    
    public void setCourseId(String courseId) {
        this.selectedCourseId = courseId;        
    }
    
    public String getCourseId() {
        return selectedCourseId;        
    }     
}
