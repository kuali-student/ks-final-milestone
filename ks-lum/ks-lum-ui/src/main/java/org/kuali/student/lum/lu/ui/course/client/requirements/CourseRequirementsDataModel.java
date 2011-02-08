/**
 * Copyright 2010 The Kuali Foundation Licensed under the
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
package org.kuali.student.lum.lu.ui.course.client.requirements;

import java.util.*;

import org.kuali.student.common.ui.client.application.KSAsyncCallback;
import org.kuali.student.common.ui.client.mvc.*;
import org.kuali.student.common.ui.client.widgets.dialog.ConfirmationDialog;
import org.kuali.student.common.ui.client.widgets.notification.KSNotification;
import org.kuali.student.common.ui.client.widgets.notification.KSNotifier;
import org.kuali.student.common.ui.client.widgets.rules.RulesUtil;
import org.kuali.student.core.statement.dto.ReqCompFieldInfo;
import org.kuali.student.core.statement.dto.ReqComponentInfo;
import org.kuali.student.core.statement.dto.StatementTreeViewInfo;
import org.kuali.student.core.statement.dto.StatementTypeInfo;
import org.kuali.student.lum.lu.ui.course.client.service.CourseRpcService;
import org.kuali.student.lum.lu.ui.course.client.service.CourseRpcServiceAsync;
import org.kuali.student.lum.program.client.rpc.StatementRpcService;
import org.kuali.student.lum.program.client.rpc.StatementRpcServiceAsync;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;

public class CourseRequirementsDataModel {

    private final CourseRpcServiceAsync courseRemoteService = GWT.create(CourseRpcService.class);
    private final StatementRpcServiceAsync statementRpcServiceAsync = GWT.create(StatementRpcService.class);
    private Controller parentController;

    //keeping track of rules and rule state
    public enum requirementState {STORED, ADDED, EDITED, DELETED;}
    private Map<Integer, StatementTreeViewInfo> courseReqInfos = new LinkedHashMap<Integer, StatementTreeViewInfo>();
    private Map<Integer, StatementTreeViewInfo> origCourseReqInfos = new LinkedHashMap<Integer, StatementTreeViewInfo>();
    private Map<Integer, requirementState> courseReqState = new HashMap<Integer, requirementState>();
    private Map<Integer, requirementState> origCourseReqState = new HashMap<Integer, requirementState>();
    private List<StatementTypeInfo> stmtTypes = new ArrayList<StatementTypeInfo>();
    private boolean isInitialized = false;
    private static Integer courseReqIDs = 111111;

    public CourseRequirementsDataModel(Controller parentController) {
        this.parentController = parentController;
    }

    //retrieve rules based on IDs associated with this course
    public void retrieveCourseRequirements(String modelId, final Callback<Boolean> onReadyCallback) {
        //initialize data
        courseReqInfos = new LinkedHashMap<Integer, StatementTreeViewInfo>();
        origCourseReqInfos = new LinkedHashMap<Integer, StatementTreeViewInfo>();
        courseReqState = new HashMap<Integer, requirementState>();
        origCourseReqState = new HashMap<Integer, requirementState>();
        stmtTypes = new ArrayList<StatementTypeInfo>();        
        isInitialized = false;

        parentController.requestModel(modelId, new ModelRequestCallback() {

            @Override
            public void onRequestFail(Throwable cause) {
                Window.alert(cause.getMessage());
                GWT.log("Unable to retrieve model for course requirements view", cause);
                onReadyCallback.exec(false);
            }

            @Override
            public void onModelReady(Model model) {
                String courseId = ((DataModel)model).getRoot().get("id");
                retrieveStatementTypes(courseId, onReadyCallback);
            }
        });    
    }

    public void retrieveStatementTypes(final String courseId, final Callback<Boolean> onReadyCallback) {

        //retrieve available course requirement types
            statementRpcServiceAsync.getStatementTypesForStatementTypeForCourse("kuali.statement.type.course", new KSAsyncCallback<List<StatementTypeInfo>>() {
                @Override
                public void handleFailure(Throwable caught) {
                    Window.alert(caught.getMessage());
                    GWT.log("getStatementTypes failed", caught);
                    onReadyCallback.exec(false);
                }

                @Override
                public void onSuccess(List<StatementTypeInfo> stmtInfoTypes) {
                    //store the statement types
                    stmtTypes.clear();
                    for (StatementTypeInfo stmtInfoType : stmtInfoTypes) {
                        stmtTypes.add(stmtInfoType);
                    }

                    //now retrieve the actual rules
                    retrieveRules(courseId, onReadyCallback);
                }
            });
    }

    private void retrieveRules(String courseId, final Callback<Boolean> onReadyCallback) {

        //true if no course exists yet
        if ((courseId == null) || courseId.isEmpty()) {
            isInitialized = true;
            onReadyCallback.exec(true);
            return;
        }

        courseRemoteService.getCourseStatements(courseId, CourseRequirementsManageView.RULEEDIT_TEMLATE,
                                                    CourseRequirementsManageView.TEMLATE_LANGUAGE, new KSAsyncCallback<List<StatementTreeViewInfo>>() {
            @Override
            public void handleFailure(Throwable caught) {
                Window.alert(caught.getMessage());
                GWT.log("getCourseStatements failed", caught);
                onReadyCallback.exec(false);
            }

            @Override
            public void onSuccess(List<StatementTreeViewInfo> foundRules) {
                //update rules list with new course requirements
                origCourseReqInfos.clear();
                origCourseReqState.clear();
                courseReqInfos.clear();
                courseReqState.clear();
            	if (foundRules != null) {
            		for (StatementTreeViewInfo foundRule : foundRules) {

            			if (getStmtTypeInfo(foundRule.getType()) == null) {
            				// Window.alert("Did not find corresponding statement type for course requirement of type: " + foundRule.getType());
            				GWT.log("Did not find corresponding statement type for course requirement of type: " + foundRule.getType(), null);
            			}

            			origCourseReqInfos.put(courseReqIDs, RulesUtil.clone(foundRule));
            			origCourseReqState.put(courseReqIDs, requirementState.STORED);
            			courseReqInfos.put(courseReqIDs, foundRule);
            			courseReqState.put(courseReqIDs++, requirementState.STORED);
            		}
            	}

                isInitialized = true;
                onReadyCallback.exec(true);
            }
        });     
    }

    public StatementTreeViewInfo updateRules(StatementTreeViewInfo newSubRule, Integer internalCourseReqID, boolean isNewRule) {

        StatementTreeViewInfo affectedRule = courseReqInfos.get(internalCourseReqID);

        if (affectedRule == null) {
            Window.alert("Cannot find course requisite with a statement that has id: '" + newSubRule.getId() + "'");
            GWT.log("Cannot find course requisite with a statement that has id: '" + newSubRule.getId() + "'", null);
            return null;
        }

        if (courseReqState.get(internalCourseReqID) == CourseRequirementsDataModel.requirementState.STORED) {
            courseReqState.put(internalCourseReqID, CourseRequirementsDataModel.requirementState.EDITED);
        }

        courseReqInfos.put(internalCourseReqID, newSubRule);

        return newSubRule;
    }

    public void updateCourseRequisites(final String courseId, final Callback<List<StatementTreeViewInfo>> callback) {

        //course proposal has to be in the database before we can save rules
        if (courseId == null) {
            final ConfirmationDialog dialog = new ConfirmationDialog("Submit Course Title", "Before saving rules please submit course proposal title");
            dialog.getConfirmButton().addClickHandler(new ClickHandler(){
                @Override
                public void onClick(ClickEvent event) {
                    dialog.hide();    
                }
            });
            dialog.show();
            return;
        }

        final List<String> referencedProgReqIds = new ArrayList<String>();

        courseRemoteService.storeCourseStatements(courseId.toString(), courseReqState, courseReqInfos, new KSAsyncCallback<Map<Integer, StatementTreeViewInfo>>() {
            @Override
            public void handleFailure(Throwable caught) {
                Window.alert(caught.getMessage());
                GWT.log("storeProgramRequirements failed", caught);
            }
            @Override
            public void onSuccess(Map<Integer, StatementTreeViewInfo> storedRules) {

                for (Integer internalProgReqID : storedRules.keySet()) {
                    StatementTreeViewInfo storedRule = storedRules.get(internalProgReqID);
                    switch (courseReqState.get(internalProgReqID)) {
                        case STORED:
                            //rule was not changed so continue
                            referencedProgReqIds.add(internalProgReqID.toString());
                            break;
                        case ADDED:
                            referencedProgReqIds.add(storedRule.getId());
                            courseReqInfos.put(internalProgReqID, storedRule);
                            origCourseReqInfos.put(internalProgReqID, RulesUtil.clone(storedRule));
                            origCourseReqState.put(internalProgReqID, requirementState.STORED);
                            courseReqState.put(internalProgReqID, requirementState.STORED);
                            break;
                        case EDITED:
                            referencedProgReqIds.add(storedRule.getId());
                            courseReqInfos.put(internalProgReqID, storedRule);
                            origCourseReqInfos.put(internalProgReqID, RulesUtil.clone(storedRule));
                            origCourseReqState.put(internalProgReqID, requirementState.STORED);
                            courseReqState.put(internalProgReqID, requirementState.STORED);
                            break;
                        case DELETED:
                            courseReqInfos.remove(internalProgReqID);
                            origCourseReqInfos.remove(internalProgReqID);
                            origCourseReqState.remove(internalProgReqID);
                            courseReqState.remove(internalProgReqID);
                            break;
                        default:
                            break;
                    }
                }

                KSNotifier.add(new KSNotification("Save Successful", false, 4000));
                //MajorManager.getEventBus().fireEvent(new StoreRequirementIDsEvent(referencedProgReqIds));
                callback.exec(new ArrayList(storedRules.values()));  //update display widgets
            }
        });        
    }

    public static void stripStatementIds(StatementTreeViewInfo tree) {
        List<StatementTreeViewInfo> statements = tree.getStatements();
        List<ReqComponentInfo> reqComponentInfos = tree.getReqComponents();

        if ((tree.getId() != null) && (tree.getId().indexOf(CourseRequirementsSummaryView.NEW_STMT_TREE_ID)) >= 0) {
            tree.setId(null);
        }
        tree.setState("Active");

        if ((statements != null) && (statements.size() > 0)) {
            // retrieve all statements
            for (StatementTreeViewInfo statement : statements) {
                stripStatementIds(statement); // inside set the children of this statementTreeViewInfo
            }
        } else if ((reqComponentInfos != null) && (reqComponentInfos.size() > 0)) {
            // retrieve all req. component LEAFS
            for (ReqComponentInfo reqComponent : reqComponentInfos) {
                if ((reqComponent.getId() != null) && (reqComponent.getId().indexOf(CourseRequirementsSummaryView.NEW_REQ_COMP_ID) >= 0)) {
                    reqComponent.setId(null);
                }

                for (ReqCompFieldInfo field : reqComponent.getReqCompFields()) {
                    field.setId(null);
                }

                reqComponent.setState("Active");
            }
        }
    }

    public List<StatementTreeViewInfo> getCourseReqInfo(String stmtTypeId) {
        List<StatementTreeViewInfo> rules = new ArrayList<StatementTreeViewInfo>();
        for(StatementTreeViewInfo rule : courseReqInfos.values()) {
            if (rule.getType().equals(stmtTypeId)) {
                rules.add(rule);
            }
        }
        return rules;
    }

    public Integer getInternalCourseReqID(StatementTreeViewInfo rule) {
        for(Integer key : courseReqInfos.keySet()) {
            if (courseReqInfos.get(key) ==  rule) {
                return key;
            }
        }

        Window.alert("Problem retrieving key for course requisite: " +  rule.getId());
        GWT.log("Problem retrieving key for course requisite: " +  rule.getId(), null);        

        return null;
    }

    public StatementTypeInfo getStmtTypeInfo(String stmtTypeId) {
        for (StatementTypeInfo stmtInfo : stmtTypes) {
            if (stmtInfo.getId().equals(stmtTypeId)) {
                return stmtInfo;
            }
        }

        try {

        Window.alert("Did not find StatementTypeInfo based on typeA: " + stmtTypeId + new Throwable().getStackTrace().toString());
        GWT.log("Did not find StatementTypeInfo based on type: " + stmtTypeId + new Throwable().getStackTrace());


        throw new Exception();

        } catch (Exception e) {
            Window.alert("Exception" + e.getStackTrace().toString());   
        }
        return null;
    }

    public void deleteRule(Integer internalProgReqID) {
        if (courseReqState.get(internalProgReqID) == requirementState.ADDED) {
            //user added a rule, didn't save it and now wants to delete it
            courseReqState.remove(internalProgReqID);
            courseReqInfos.remove(internalProgReqID);
        } else {
            markRuleAsDeleted(internalProgReqID);
        }
    }

    public void addRule(StatementTreeViewInfo rule) {
        courseReqInfos.put(courseReqIDs, rule);
        courseReqState.put(courseReqIDs++, requirementState.ADDED);
    }

    public void updateRule(Integer internalProgReqID, StatementTreeViewInfo rule) {
        courseReqInfos.put(internalProgReqID, rule);
        markRuleAsEdited(internalProgReqID);
    }    

    public void markRuleAsDeleted(Integer internalCourseReqID) {
        if ((courseReqState.get(internalCourseReqID) == requirementState.STORED) ||
            (courseReqState.get(internalCourseReqID) == requirementState.EDITED)) {
            courseReqState.put(internalCourseReqID, requirementState.DELETED);
        }
    }

    public void markRuleAsEdited(Integer internalCourseReqID) {
        if (courseReqState.get(internalCourseReqID) == requirementState.STORED) {
            courseReqState.put(internalCourseReqID, requirementState.EDITED);
        }
    }

    public String getStmtTypeName(String stmtTypeId) {
        String name = getStmtTypeInfo(stmtTypeId).getName();
        return (name == null ? "" : name);
    }
    
    public boolean isRuleExists(String stmtTypeId) {
        boolean showNoRuleText = true;
        for(StatementTreeViewInfo ruleInfo : courseReqInfos.values()) {
            if ((ruleInfo.getType().equals(stmtTypeId)) && (courseReqState.get(getInternalCourseReqID(ruleInfo)) != requirementState.DELETED)) {
                showNoRuleText = false;
            }
        }
        return showNoRuleText;
    }

    public void removeEmptyRules() {
        for(StatementTreeViewInfo rule : courseReqInfos.values()) {
            if (isEmpty(rule)) {
                Integer ruleId = getInternalCourseReqID(rule);
                courseReqInfos.remove(ruleId);
                courseReqState.remove(ruleId);
            }
        }
    }

    public boolean isDirty() {

        if (courseReqState.size() != origCourseReqState.size()) {
            return true;
        }

        for(Integer key : courseReqState.keySet()) {
            if (!courseReqState.get(key).equals(origCourseReqState.get(key))) {
                return true;
            }
        }

        return false;
    }

    public void revertRuleChanges() {
        courseReqInfos = new HashMap<Integer, StatementTreeViewInfo>();
        courseReqState = new HashMap<Integer, requirementState>();
        for(Integer key : origCourseReqInfos.keySet()) {
            courseReqInfos.put(key, RulesUtil.clone(origCourseReqInfos.get(key)));
            courseReqState.put(key, origCourseReqState.get(key));
        }
    }
    
    public StatementTreeViewInfo getRule(Integer internalCourseReqID) {
        return courseReqInfos.get(internalCourseReqID);
    }

    public static void getStatementTypes(final Callback<List<StatementTypeInfo>> onReadyCallback) {

        StatementRpcServiceAsync statementRpcServiceAsync = GWT.create(StatementRpcService.class);

        //retrieve available course requirement types
        statementRpcServiceAsync.getStatementTypesForStatementTypeForCourse("kuali.statement.type.course", new KSAsyncCallback<List<StatementTypeInfo>>() {
            @Override
            public void handleFailure(Throwable caught) {
	            Window.alert(caught.getMessage());
	            GWT.log("getStatementTypes failed", caught);
                onReadyCallback.exec(new ArrayList<StatementTypeInfo>());
            }

            @Override
            public void onSuccess(List<StatementTypeInfo> stmtInfoTypes) {
                //store the statement types
                List<StatementTypeInfo> stmtTypes = new ArrayList<StatementTypeInfo>();                
                for (StatementTypeInfo stmtInfoType : stmtInfoTypes) {
                    stmtTypes.add(stmtInfoType);
                }
                onReadyCallback.exec(stmtTypes);
            }
        });
    }

    public static boolean isEmpty(StatementTreeViewInfo rule) {
        return (((rule.getStatements() == null) || rule.getStatements().isEmpty()) && ((rule.getReqComponents() == null) || rule.getReqComponents().isEmpty()));
    }

    public boolean isInitialized() {
        return isInitialized;
    }

    public void setInitialized(boolean initialized) {
        isInitialized = initialized;
    }

    public List<StatementTypeInfo> getStmtTypes() {
        return stmtTypes;
    }
}
