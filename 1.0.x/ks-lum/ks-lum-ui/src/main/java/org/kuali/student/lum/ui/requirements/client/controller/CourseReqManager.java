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
package org.kuali.student.lum.ui.requirements.client.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.common.ui.client.mvc.CollectionModel;
import org.kuali.student.common.ui.client.mvc.Controller;
import org.kuali.student.common.ui.client.mvc.DataModel;
import org.kuali.student.common.ui.client.mvc.ModelRequestCallback;
import org.kuali.student.common.ui.client.mvc.View;
import org.kuali.student.common.ui.client.mvc.ViewComposite;
import org.kuali.student.lum.lu.assembly.data.client.LuData;
import org.kuali.student.brms.statement.dto.StatementInfo;
import org.kuali.student.brms.statement.dto.StatementOperatorTypeKey;
import org.kuali.student.common.ui.client.configurable.mvc.FieldDescriptor;
import org.kuali.student.lum.lu.ui.course.client.configuration.course.CourseConfigurer;
import org.kuali.student.lum.ui.requirements.client.model.EditHistory;
import org.kuali.student.lum.ui.requirements.client.model.ReqComponentVO;
import org.kuali.student.lum.ui.requirements.client.model.RuleInfo;
import org.kuali.student.lum.ui.requirements.client.model.StatementVO;
import org.kuali.student.lum.ui.requirements.client.view.CourseRequisiteView;
import org.kuali.student.lum.ui.requirements.client.view.ManageRulesView;
import org.kuali.student.lum.ui.requirements.client.view.RuleComponentEditorView;
import org.kuali.student.lum.ui.requirements.client.view.RuleExpressionEditor;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class CourseReqManager extends Controller {
 
    public enum PrereqViews {
        RULES_LIST, MANAGE_RULES, RULE_COMPONENT_EDITOR, RULE_EXPRESSION_EDITOR
    }

    //controller's widgets
    private final SimplePanel mainPanel = new SimplePanel();
    private final SimplePanel viewPanel = new SimplePanel();
    private final CourseRequisiteView courseRequisiteView = new CourseRequisiteView(this);
    private final ManageRulesView manageRulesView = new ManageRulesView(this);
    private final RuleComponentEditorView ruleCompEditorView = new RuleComponentEditorView(this);
    private final RuleExpressionEditor ruleExpressionEditorView = new RuleExpressionEditor(this);
    
    //controller's data
    private CollectionModel<RuleInfo> ruleInfo;         //contains all rules belonging to this course
    private String cluId = null;						//course id
    private static int id = 0; 
    private ReqComponentVO componentToEdit;				//which component user chosen to edit	
    private List<FieldDescriptor> fieldsWithLookup = new ArrayList<FieldDescriptor>();
   
    private String selectedLuStatementType = "unknown";             //type of rule that user selected to work on (add or edit)
    private Map<String, String> cluSetsData = new HashMap<String, String>(); 
    
    public CourseReqManager(VerticalPanel displayPanel) {
        super(CourseReqManager.class.getName());       
        
        if (displayPanel == null) {        
       	 	super.initWidget(viewPanel);
       	 	viewPanel.add(mainPanel);
        } else {
        	displayPanel.add(mainPanel);
        }
        
        this.ruleInfo = null;
        loadSearchBoxData();
    }
    
    public void saveApplicationState() {
        courseRequisiteView.saveApplicationState();
    }
   
    @Override
    protected void onLoad() {
        //showDefaultView();   instead of showing default view every time user comes back, just show the screen user left off last time
    }

    // controller operations
    @Override
    @SuppressWarnings("unchecked")
    public void requestModel(Class modelType, final ModelRequestCallback callback) {
    	//retrieving all rules for given course
    	if (modelType.equals(LuData.class)) {    		

	    	super.requestModel(CourseConfigurer.CLU_PROPOSAL_MODEL,
	    			new ModelRequestCallback<DataModel>() {
	                    @Override
	                    public void onModelReady(DataModel dataModel) {
	                    	LuData luData = (LuData)dataModel.getRoot();
	                    	
	                		if (ruleInfo == null) {	                     			                    	
		                    	List<RuleInfo> rules = luData.getRuleInfos();
		                    	ruleInfo = new CollectionModel<RuleInfo>();
		                    	for (RuleInfo oneRule : rules) {
		                    		oneRule.setId(Integer.toString(id++));
		                    		ruleInfo.add(oneRule);
		                    	}
		                    	cluId = dataModel.get("course/id");
		                    	
		                    	//make sure each rule info has set course id
		                    	if (rules != null) {
			                        for (RuleInfo oneRuleInfo : ruleInfo.getValues()) {
			                            oneRuleInfo.setCluId(cluId);            		                            
			                        }		     
		                    	}	                    	
	                		} else {
	                			luData.setRuleInfos(new ArrayList(ruleInfo.getValues()));
	                		}
	                        callback.onModelReady(dataModel);
	                    }    
	
	                    @Override
	                    public void onRequestFail(Throwable cause) {
	                    	GWT.log("Failed to get LuData DataModel", cause);
	                    	Window.alert("Failed to get LuData DataModel");
	                    }
	            });    		    		
    	}
    	//retrieving a specific type of rule (selected by user for editing)
    	else if (modelType.equals(RuleInfo.class)) {            
        	CollectionModel<RuleInfo> ruleInfoGivenType = new CollectionModel<RuleInfo>();
            ruleInfoGivenType.setValue(getRuleInfo(selectedLuStatementType));                        
            callback.onModelReady(ruleInfoGivenType);            
        } else {
            super.requestModel(modelType, callback);
        }
    }
    
    private RuleInfo getRuleInfo(String luStatementTypeKey) {
        if (ruleInfo.getValues() != null && !ruleInfo.getValues().isEmpty()) {
            for (RuleInfo oneRuleInfo : ruleInfo.getValues()) {
                if (oneRuleInfo.getStatementTypeKey().equals(luStatementTypeKey)) {
                    return oneRuleInfo;
                }                
            }
        } 
        return null;
    }    

    @Override
    public void showDefaultView(final Callback<Boolean> onReadyCallback) {
        showView(PrereqViews.RULES_LIST, onReadyCallback);
    }
    
    @Override
    public void renderView(View view) {
        // in this case we know that all of our widgets are composites
        // but we could do view specific rendering, e.g. show a lightbox, etc
        mainPanel.setWidget((ViewComposite) view);
    }

    @Override
    protected void hideView(View view) {
        mainPanel.clear();
    }    

    @Override
    protected <V extends Enum<?>> View getView(V viewType) {
        switch ((PrereqViews) viewType) {
            case RULES_LIST:
	            return courseRequisiteView;
	        case MANAGE_RULES:
                return manageRulesView;
            case RULE_COMPONENT_EDITOR:    
                ruleCompEditorView.setCluSetsData(cluSetsData);                
                ruleCompEditorView.setEditedStatementVO(getRuleInfo(selectedLuStatementType).getStatementVO());
                ruleCompEditorView.setEditedReqCompVO(componentToEdit);
                ruleCompEditorView.setFieldsWithLookup(fieldsWithLookup);
                return ruleCompEditorView;
            case RULE_EXPRESSION_EDITOR:
                return ruleExpressionEditorView;
            default:
                return null;
        }
    }

	//create a blank root statementVO
    public void addNewRule(String statementType) {
        RuleInfo newPrereqInfo = new RuleInfo();
        newPrereqInfo.setCluId(cluId);
        newPrereqInfo.setId(Integer.toString(id++));
        newPrereqInfo.setEditHistory(new EditHistory());
        newPrereqInfo.setSelectedStatementType(statementType);
        newPrereqInfo.setStatementVO(newPrereqInfo.createNewStatementVO());
        ruleInfo.add(newPrereqInfo);
    }

    private void loadSearchBoxData() {        
        cluSetsData.put("CLUSET-NL-3", "CLUSET-NL-3");
        cluSetsData.put("CLUSET-NL-2", "CLUSET-NL-2");
        cluSetsData.put("CLUSET-NL-1", "CLUSET-NL-1");              
    }
    
    public void saveEditHistory(StatementVO editedStatementVO) {
    	getRuleInfo(selectedLuStatementType).getEditHistory().save(editedStatementVO);
    }
    
    public Class<? extends Enum<?>> getViewsEnum() {
        return PrereqViews.class;
    }

    @Override
    public Enum<?> getViewEnumValue(String enumValue) {
        return PrereqViews.valueOf(enumValue);
    }
    
    public void setRule(RuleInfo newRule) {
  		ruleInfo.add(newRule);
    }
    
    public String getSelectedLuStatementType() {
        return selectedLuStatementType;
    }

    public void setSelectedLuStatementType(String luStatementType) {
        this.selectedLuStatementType = luStatementType;
    } 
    
    public String getCluId() {
		return cluId;
	}

	public void setComponentToEdit(ReqComponentVO componentToEdit) {
		this.componentToEdit = componentToEdit;
	}

	public List<FieldDescriptor> getFieldsWithLookup() {
		return fieldsWithLookup;
	}

	public void setFieldsWithLookup(List<FieldDescriptor> fieldsWithLookup) {
		this.fieldsWithLookup = fieldsWithLookup;
	}		
}
