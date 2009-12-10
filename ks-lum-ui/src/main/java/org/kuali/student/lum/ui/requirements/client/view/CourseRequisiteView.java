/*
 * Copyright 2009 The Kuali Foundation Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 * 
 * http://www.osedu.org/licenses/ECL-2.0
 * 
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */
package org.kuali.student.lum.ui.requirements.client.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.common.ui.client.mvc.CollectionModel;
import org.kuali.student.common.ui.client.mvc.Controller;
import org.kuali.student.common.ui.client.mvc.Model;
import org.kuali.student.common.ui.client.mvc.ModelRequestCallback;
import org.kuali.student.common.ui.client.mvc.ViewComposite;
import org.kuali.student.common.ui.client.mvc.dto.ModelDTO;
import org.kuali.student.common.ui.client.mvc.dto.ModelDTOValue.ModelDTOType;
import org.kuali.student.common.ui.client.widgets.KSButton;
import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.common.ui.client.widgets.KSTextArea;
import org.kuali.student.lum.lu.ui.course.client.configuration.mvc.CluProposalModelDTO;
import org.kuali.student.lum.ui.requirements.client.RulesUtilities;
import org.kuali.student.lum.ui.requirements.client.controller.CourseReqManager;
import org.kuali.student.lum.ui.requirements.client.controller.CourseReqManager.PrereqViews;
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
    
    private final String KS_STATEMENT_TYPE_PREREQ = "kuali.luStatementType.prereqAcademicReadiness";
    private final String KS_STATEMENT_TYPE_COREQ = "kuali.luStatementType.coreqAcademicReadiness";
    private final String KS_STATEMENT_TYPE_ANTIREQ = "kuali.luStatementType.antireqAcademicReadiness";
    private final String KS_STATEMENT_TYPE_ENROLLREQ = "kuali.luStatementType.enrollAcademicReadiness";    
    
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
    private KSTextArea prereqRationaleTextArea = new KSTextArea();
    private KSTextArea coreqRationaleTextArea = new KSTextArea();
    private KSTextArea antireqRationaleTextArea = new KSTextArea();
    private KSTextArea enrollRationaleTextArea = new KSTextArea();
    private Map<String, KSButton> submitButtons = new  HashMap<String, KSButton>();
    
    private ButtonEventHandler handler = new ButtonEventHandler(); 
    
    //rule types we deal with
	List<String> applicableLuStatementTypes = new ArrayList<String>();
   		
   	//view's data   		
    private List<RuleInfo> courseRules = new ArrayList<RuleInfo>();  //contains all rules, each rule has its own RuleInfo object
    private String cluId = null;
    private static int id = 0;

    public CourseRequisiteView(Controller controller) {
        super(controller, "Course Requisites");
        super.initWidget(mainPanel);   
        
        applicableLuStatementTypes.add(KS_STATEMENT_TYPE_PREREQ);
        applicableLuStatementTypes.add(KS_STATEMENT_TYPE_COREQ);
        applicableLuStatementTypes.add(KS_STATEMENT_TYPE_ANTIREQ);
        applicableLuStatementTypes.add(KS_STATEMENT_TYPE_ENROLLREQ);
    }
    
    @Override
    public void beforeShow(final Callback<Boolean> onReadyCallback) {                 	
    	
    	getController().requestModel(CluProposalModelDTO.class,
    			new ModelRequestCallback<CollectionModel<CluProposalModelDTO>>() {
                    @Override
                    public void onModelReady(CollectionModel<CluProposalModelDTO> model) {
                    	ModelDTO cluInfoModelDTO = (ModelDTO) ((ModelDTOType) model.get().get("cluInfo")).get();
                    	courseRules = model.get().getRuleInfos();
                    	cluId = model.get().getString("cluInfo/id");
                        initializeView();
                        onReadyCallback.exec(true);
                    }    
    
                    @Override
                    public void onRequestFail(Throwable cause) {
                        Window.alert("Failed to get CluProposalModelDTO");
                        onReadyCallback.exec(false);
                    }
        });    	            
    }    
    
    public void initializeView() {       
        mainPanel.clear();
        viewPanel.clear();
        mainPanel.add(viewPanel);;        
        layoutMainPanel(viewPanel);

        for (String luStatementType : applicableLuStatementTypes) {
        	setupRuleType(luStatementType);
        } 
        
        //go to retrieve new rules only for the first time
        if ((cluId != null) && (courseRules.size() < applicableLuStatementTypes.size())) {
        	retrieveRules();
        }
    }
    
    private void setupRuleType(String luStatementTypeKey) {
    	
    	layoutBasicRuleSection(luStatementTypeKey);
    	
        //add add or manage rules button
        VerticalPanel rulesInfoPanel = getRulesInfoPanel(luStatementTypeKey);                     
        HorizontalPanel rationalePanel3 = new HorizontalPanel();
        KSLabel rationale3 = new KSLabel("");
        rationale3.setStyleName("KS-ReqMgr-Field-Labels"); 
        rationalePanel3.add(rationale3);        
        rulesInfoPanel.add(rationalePanel3);
    	
    	RuleInfo rule = getRuleInfo(luStatementTypeKey);    	
        KSButton submitButton = new KSButton(rule == null ? "Add Rule" : "Manage rule");
        submitButtons.put(luStatementTypeKey, submitButton);
        submitButton.setTitle(luStatementTypeKey);
        submitButton.setStyleName("KS-Rules-Tight-Button");
        submitButton.addClickHandler(handler);    
        rationalePanel3.add(submitButton);        
    	
        if ((rule != null) || (cluId == null) ) {
        	setRuleText(luStatementTypeKey);       	
            return;
        }     	    	
    }
            
    private void retrieveRules() {                

        requirementsRpcServiceAsync.getRules(cluId, applicableLuStatementTypes, new AsyncCallback<List<RuleInfo>>() {
            public void onFailure(Throwable caught) {
                Window.alert(caught.getMessage());
                caught.printStackTrace();
            }
            
            public void onSuccess(final List<RuleInfo> rules) {
            	
                for (String luStatementType : applicableLuStatementTypes) {
            	
                	//do not update rules that were modified locally
                	if (getRuleInfo(luStatementType) != null) {
                		continue;
                	}
                	
                	RuleInfo rule = null;
                    for (RuleInfo tmpRule : rules) {
                        if (tmpRule.getLuStatementTypeKey().equals(luStatementType)) {              	
                        	rule = tmpRule;
                        	break;
                        }                
                    }
                	
                    //no rule exists for given lu statement type so state that on rules page
		        	if (rule == null)
		        	{
			            KSLabel reqText = new KSLabel("No " + getRuleTypeName(luStatementType).toLowerCase() + " rules has been added.");
			            reqText.setStyleName("KS-ReqMgr-NoRuleText");
			            SimplePanel rulesText = getRulesTextPanel(luStatementType);
			            rulesText.clear();
			            rulesText.add(reqText); 
			            continue;
		        	}
		        	
		        	//update the model
			        setRuleInfo(rule); 
			        
			        //update the UI
			        submitButtons.get(rule.getLuStatementTypeKey()).setText("Manage rule");
			        SimplePanel rulesText = getRulesTextPanel(luStatementType);
			        rulesText.clear();
			        rulesText.add(new KSLabel(rule.getNaturalLanguage())); 
            	}
    	        
                //store this new rule model in the top most model
                getController().requestModel(CluProposalModelDTO.class, new ModelRequestCallback<CollectionModel<CluProposalModelDTO>>() {

                    @Override
                    public void onModelReady(CollectionModel<CluProposalModelDTO> model) {
                        List<RuleInfo> ruleInfos = model.get().getRuleInfos();
                        ruleInfos.retainAll(ruleInfos);
                    }

                    @Override
                    public void onRequestFail(Throwable cause) {
                        Window.alert("Failed to request for CluProposalModelDTO");
                    }
                });                                              
            } 
        });            
    }
    
    private void setRuleText(String luStatementTypeKey) {
    	String ruleText;
    	RuleInfo rule = getRuleInfo(luStatementTypeKey);    	 	
    	
    	if ((rule == null) || (rule.getNaturalLanguage() == null) || (rule.getNaturalLanguage().isEmpty())) {
    		ruleText = "No " + getRuleTypeName(luStatementTypeKey).toLowerCase() + " rules have been added.";   		    		
    	} else {
    		ruleText = rule.getNaturalLanguage();
    	}

        SimplePanel rulesText = getRulesTextPanel(luStatementTypeKey);
        rulesText.clear();
        rulesText.add(new KSLabel(ruleText));
    }
    
    private class ButtonEventHandler implements ClickHandler{

        @Override
        public void onClick(ClickEvent event) {
            Widget sender = (Widget) event.getSource();
            CourseReqManager courseReqManager = (CourseReqManager) getController();
            
            String statementType = null;
            
            if (sender.getTitle().contains("prereq")) {
            	statementType = KS_STATEMENT_TYPE_PREREQ;
            } else if (sender.getTitle().contains("coreq")) {
            	statementType = KS_STATEMENT_TYPE_COREQ;
            } else if (sender.getTitle().contains("enroll")) {
            	statementType = KS_STATEMENT_TYPE_ENROLLREQ;
            } else if (sender.getTitle().contains("antireq")) {
            	statementType = KS_STATEMENT_TYPE_ANTIREQ;
            }
            courseReqManager.setLuStatementType(statementType);
            
            RuleInfo rule = getRuleInfo(sender.getTitle());
            if ((rule == null) || (rule.getNaturalLanguage() == null) || (rule.getNaturalLanguage().isEmpty())) {                
                RuleInfo newPrereqInfo = new RuleInfo();
                newPrereqInfo.setId(Integer.toString(id++));
                newPrereqInfo.setCluId(cluId);
                newPrereqInfo.setStatementVO(null);
                newPrereqInfo.setEditHistory(new EditHistory());
                newPrereqInfo.setLuStatementTypeKey(statementType);
                courseRules.add(newPrereqInfo);               
                
                courseReqManager.resetReqCompVOModel();
                
                courseReqManager.setRuleInfoModel(courseRules);
                courseReqManager.showView(PrereqViews.CLAUSE_EDITOR, Controller.NO_OP_CALLBACK);
            } else {
                courseReqManager.setRuleInfoModel(courseRules);
                courseReqManager.showView(PrereqViews.MANAGE_RULES, Controller.NO_OP_CALLBACK);
            }
        }       
    }    
    
    private void setRuleInfo(RuleInfo ruleInfo) { 
        RuleInfo origRule = getRuleInfo(ruleInfo.getLuStatementTypeKey());
        if (origRule != null) {    	
            courseRules.remove(origRule);
        }               
        courseRules.add(ruleInfo);
    }
    
    private RuleInfo getRuleInfo(String luStatementTypeKey) { 	
        if ((courseRules != null) && !courseRules.isEmpty()) {
            for (RuleInfo ruleInfo : courseRules) {
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
        KSTextArea rantionale = getRationaleTextArea(luStatementTypeKey);
        HorizontalPanel rationalePanel = new HorizontalPanel();
        SimplePanel tempHolder = new SimplePanel();
        KSLabel rationale = new KSLabel("Rationale");
        rationale.setStyleName("KS-ReqMgr-Field-Labels");
        tempHolder.add(rationale);    
        rationalePanel.add(tempHolder);
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
        
    private void loadRuleInfo(final String luStatementTypeKey) {                                                     
        
        // TODO remove when done testing the save application state feature
//        KSButton saveApplicationState = new KSButton("Save State");
//        saveApplicationState.setTitle("Save State");
//        saveApplicationState.setStyleName("KS-Rules-Tight-Button");
//        saveApplicationState.addClickHandler(new ClickHandler() {
//            @Override
//            public void onClick(ClickEvent event) {
//                getController().requestModel(CluProposalModelDTO.class,
//                        new ModelRequestCallback<CluProposalModelDTO>() {
//                            @Override
//                            public void onModelReady(Model<CluProposalModelDTO> model) {
//                                KSTextArea rationaleTextArea = getRationaleTextArea(luStatementTypeKey);
//                                model.get().putApplicationState("rationale", rationaleTextArea.getText());
//                                Window.alert("You entered: " + model.get().getApplicationState("rationale"));
//                            }
//
//                            @Override
//                            public void onRequestFail(Throwable cause) {
//                                Window.alert("Failed to get CluProposalModelDTO");
//                            }
//                });
//            }
//        });
//        rationalePanel3.add(saveApplicationState);

    }
    
    public void saveApplicationState() {
        getController().requestModel(CluProposalModelDTO.class,
                new ModelRequestCallback<CollectionModel<CluProposalModelDTO>>() {
            @Override
            public void onModelReady(CollectionModel<CluProposalModelDTO> model) {
                model.get().putApplicationState(
                        "kuali.luStatementType.prereqAcademicReadiness.rationale", 
                        getRationaleTextArea(KS_STATEMENT_TYPE_PREREQ)
                        .getText());
                model.get().putApplicationState(
                        "kuali.luStatementType.coreqAcademicReadiness.rationale", 
                        getRationaleTextArea(KS_STATEMENT_TYPE_COREQ)
                        .getText());
                model.get().putApplicationState(
                        "kuali.luStatementType.antireqAcademicReadiness.rationale", 
                        getRationaleTextArea(KS_STATEMENT_TYPE_ANTIREQ)
                        .getText());
                model.get().putApplicationState(
                        "kuali.luStatementType.enrollAcademicReadiness.rationale", 
                        getRationaleTextArea(KS_STATEMENT_TYPE_ENROLLREQ)
                        .getText());
            }

            @Override
            public void onRequestFail(Throwable cause) {
                Window.alert("Failed to get CluProposalModelDTO");
            }
        });
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
    
    private KSTextArea getRationaleTextArea(String luStatementTypeKey) {
        if (luStatementTypeKey.contains("enroll")) return enrollRationaleTextArea;
        if (luStatementTypeKey.contains("prereq")) return prereqRationaleTextArea;
        if (luStatementTypeKey.contains("coreq")) return coreqRationaleTextArea;
        if (luStatementTypeKey.contains("antireq")) return antireqRationaleTextArea;   
        return new KSTextArea();
    }    

    private String getRuleTypeName(String luStatementTypeKey) {
        if (luStatementTypeKey.contains("enroll")) return "Enrollment Restrictions";
        if (luStatementTypeKey.contains("prereq")) return "Prerequisites";
        if (luStatementTypeKey.contains("coreq")) return "Corequisites";
        if (luStatementTypeKey.contains("antireq")) return "Antirequisites";
        return "";
    }
    }
