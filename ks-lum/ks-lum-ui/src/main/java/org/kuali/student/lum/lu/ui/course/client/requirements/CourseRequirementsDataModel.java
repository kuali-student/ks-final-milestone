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
import org.kuali.student.core.assembly.data.Data;
import org.kuali.student.core.dto.StatusInfo;
import org.kuali.student.core.statement.dto.ReqCompFieldInfo;
import org.kuali.student.core.statement.dto.ReqComponentInfo;
import org.kuali.student.core.statement.dto.StatementTreeViewInfo;
import org.kuali.student.core.statement.dto.StatementTypeInfo;
import org.kuali.student.lum.lu.ui.course.client.configuration.AbstractCourseConfigurer;
import org.kuali.student.lum.lu.ui.course.client.service.CourseRpcService;
import org.kuali.student.lum.lu.ui.course.client.service.CourseRpcServiceAsync;
import org.kuali.student.lum.program.client.rpc.StatementRpcService;
import org.kuali.student.lum.program.client.rpc.StatementRpcServiceAsync;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.Window;

public class CourseRequirementsDataModel {

    private final CourseRpcServiceAsync courseRemoteService = GWT.create(CourseRpcService.class);
    private StatementRpcServiceAsync statementRpcServiceAsync = GWT.create(StatementRpcService.class);
    private Controller parentController;
    private boolean isInitialized = false;

    //keeping track of rules and rule state
    public enum requirementState {STORED, ADDED, EDITED, DELETED;}
    private Map<Integer, StatementTreeViewInfo> courseReqInfos = new LinkedHashMap<Integer, StatementTreeViewInfo>();
    private Map<Integer, requirementState> courseReqState = new HashMap<Integer, requirementState>();
    private List<StatementTypeInfo> stmtTypes = new ArrayList<StatementTypeInfo>();

    private static Integer courseReqIDs = 111111;
    private static HandlerManager eventBus = new HandlerManager(null);

    public CourseRequirementsDataModel(Controller parentController) {
        this.parentController = parentController;
    }

    //retrieve rules based on IDs associated with this course
    public void retrieveCourseRequirements(final Callback<Boolean> onReadyCallback) {
        parentController.requestModel(AbstractCourseConfigurer.CLU_PROPOSAL_MODEL, new ModelRequestCallback() {

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

    private void retrieveStatementTypes(final String courseId, final Callback<Boolean> onReadyCallback) {

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
                for (StatementTreeViewInfo foundRule : foundRules) {

                    if (getStmtTypeInfo(foundRule.getType()) == null) {
                        Window.alert("Did not find corresponding statement type for course requirement of type: " + foundRule.getType());
                        GWT.log("Did not find corresponding statement type for course requirement of type: " + foundRule.getType(), null);
                    }
                    
                    courseReqInfos.put(courseReqIDs, foundRule);
                    courseReqState.put(courseReqIDs++, requirementState.STORED);
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

    public void updateCourseRequisites(final Data dto, final Callback<StatementTreeViewInfo> callback) {

        String courseId = dto.get("id");

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

        for (final StatementTreeViewInfo rule : courseReqInfos.values()) {

            final Integer internalProgReqID = getInternalCourseReqID(rule);
            final requirementState ruleState = courseReqState.get(internalProgReqID);

            switch (ruleState) {
                case STORED:
                    //rule was not changed so continue
                    break;
                case ADDED:
                    rule.setState("Active");
                    stripStatementIds(rule);
                    courseRemoteService.createCourseStatement(courseId, rule, new KSAsyncCallback<StatementTreeViewInfo>() {
                        @Override
                        public void handleFailure(Throwable caught) {
                            Window.alert(caught.getMessage());
                            GWT.log("createCourseStatement failed", caught);
                        }
                        @Override
                            public void onSuccess(StatementTreeViewInfo updatedRule) {
                            //eventBus.fireEvent(new StoreRequirementIDsEvent(updatedRule.getId()));
                            courseReqInfos.put(internalProgReqID, updatedRule);
                            courseReqState.put(internalProgReqID, requirementState.STORED);
                            callback.exec(updatedRule);  //update display widgets
                        }
                    });
                    break;
                case EDITED:
                    rule.setState("Active");
                    stripStatementIds(rule);                    
                    courseRemoteService.updateCourseStatement(courseId, rule, new KSAsyncCallback<StatementTreeViewInfo>() {
                        @Override
                        public void handleFailure(Throwable caught) {
                            Window.alert(caught.getMessage());
                            GWT.log("updateCourseStatement failed", caught);
                        }
                        @Override
                            public void onSuccess(StatementTreeViewInfo updatedRule) {
                            courseReqInfos.put(internalProgReqID, updatedRule);
                            courseReqState.put(internalProgReqID, requirementState.STORED);
                            callback.exec(updatedRule); //update display widgets
                        }
                    });
                    break;
                case DELETED:
                    //eventBus.fireEvent(new DeleteRequirementEvent(rule.getId()));
                    courseRemoteService.deleteCourseStatement(courseId, rule, new KSAsyncCallback<StatusInfo>() {
                        @Override
                        public void handleFailure(Throwable caught) {
                            Window.alert(caught.getMessage());
                            GWT.log("deleteCourseStatement failed", caught);
                        }
                        @Override
                        public void onSuccess(StatusInfo statusInfo) {
                            courseReqInfos.remove(internalProgReqID);
                            courseReqState.remove(internalProgReqID);
                        }
                    });
                    break;
                default:
                    break;
            }
        }
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

        Window.alert("Did not find StatementTypeInfo based on type: " + stmtTypeId);
        GWT.log("Did not find StatementTypeInfo based on type: " + stmtTypeId);    

        return null;
    }

    public void deleteRule(Integer internalProgReqID) {
        if (courseReqState.get(internalProgReqID) == requirementState.ADDED) {
            //user added a rule, didn't save it and now wants to delete it
            courseReqState.remove(internalProgReqID);
            courseReqInfos.remove(internalProgReqID);
        }
        markRuleAsDeleted(internalProgReqID);
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

    public void addRule(StatementTreeViewInfo rule) {
        courseReqInfos.put(courseReqIDs, rule);
        courseReqState.put(courseReqIDs++, requirementState.ADDED);
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

    public StatementTreeViewInfo getRule(Integer internalCourseReqID) {
        return courseReqInfos.get(internalCourseReqID);
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
