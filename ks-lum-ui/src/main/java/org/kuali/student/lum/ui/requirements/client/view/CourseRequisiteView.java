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
    private SimplePanel prereqRuleTextPanel = new SimplePanel();
    private SimplePanel coreqRuleTextPanel = new SimplePanel();
    private SimplePanel antireqRuleTextPanel = new SimplePanel();
    private SimplePanel enrollRuleTextPanel = new SimplePanel();  
    
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
        
        layoutBasicRuleSection(luStatementTypeKey);
        
        requirementsRpcServiceAsync.getStatementVO(courseData.get(getCourseId()).getId(), luStatementTypeKey, new AsyncCallback<StatementVO>() {
            public void onFailure(Throwable caught) {
                Window.alert(caught.getMessage());
                caught.printStackTrace();
            }
            
            public void onSuccess(final StatementVO statementVO) {
               
                if (statementVO == null) {
                    loadRuleInfo(luStatementTypeKey);
                    KSLabel preReqText = new KSLabel("No " + getRuleTypeName(luStatementTypeKey).toLowerCase() + " rules has been added.");
                    preReqText.setStyleName("KS-ReqMgr-NoRuleText");
                    SimplePanel rulesText = getRulesTextPanel(luStatementTypeKey);
                    rulesText.clear();
                    rulesText.add(preReqText);                    
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
                        loadRuleInfo(luStatementTypeKey);
                        SimplePanel rulesText = getRulesTextPanel(luStatementTypeKey);
                        rulesText.clear();
                        rulesText.add(new KSLabel(statementNaturalLanguage));                   
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
            } else if (sender.getTitle().contains("antireq")) {
                courseReqManager.setLuStatementType("kuali.luStatementType.antireqAcademicReadiness");
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
    
    private void layoutBasicRuleSection(String luStatementTypeKey) {
         
        String ruleName = getRuleTypeName(luStatementTypeKey);
        
        VerticalPanel rulesInfoPanel = getRulesInfoPanel(luStatementTypeKey);                 
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
        
        SimplePanel rulesText = getRulesTextPanel(luStatementTypeKey);
        rulesText.setWidth("400px");
        rulesText.clear();
        rulesText.add(new KSLabel("Rule is being loaded......"));
        rationalePanel2.add(rulesText);        
        
        return;
    }
        
  private void loadRuleInfo(String luStatementTypeKey) {
        
        RuleInfo rule = getRuleInfo(luStatementTypeKey);   
        VerticalPanel rulesInfoPanel = getRulesInfoPanel(luStatementTypeKey);                     
        
        //BUTTONS
        HorizontalPanel rationalePanel3 = new HorizontalPanel();
        KSLabel rationale3 = new KSLabel("");
        rationale3.setStyleName("KS-ReqMgr-Field-Labels"); 
        rationalePanel3.add(rationale3);        
        rulesInfoPanel.add(rationalePanel3);           
                                   
        //add add or manage rules button
        KSButton submit = new KSButton((rule == null ? "Add Rule" : "Manage rules"));
        submit.setTitle(luStatementTypeKey);
        submit.setStyleName("KS-Rules-Tight-Button");
        submit.addClickHandler(handler);        
        rationalePanel3.add(submit);
    }
    
    private VerticalPanel getRulesInfoPanel(String luStatementTypeKey) {
        if (luStatementTypeKey.contains("enroll")) return enrollRulePanel;
        if (luStatementTypeKey.contains("prereq")) return prereqRulePanel;
        if (luStatementTypeKey.contains("coreq")) return coreqRulePanel;
        if (luStatementTypeKey.contains("antireq")) return antireqRulePanel;   
        return new VerticalPanel();
    }
    
    private SimplePanel getRulesTextPanel(String luStatementTypeKey) {
        if (luStatementTypeKey.contains("enroll")) return enrollRuleTextPanel;
        if (luStatementTypeKey.contains("prereq")) return prereqRuleTextPanel;
        if (luStatementTypeKey.contains("coreq")) return coreqRuleTextPanel;
        if (luStatementTypeKey.contains("antireq")) return antireqRuleTextPanel;   
        return new SimplePanel();
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
