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
package org.kuali.student.lum.program.client.requirements;

import java.util.*;

import org.kuali.student.common.ui.client.application.KSAsyncCallback;
import org.kuali.student.common.ui.client.mvc.*;
import org.kuali.student.core.assembly.data.Data;
import org.kuali.student.core.statement.dto.ReqCompFieldInfo;
import org.kuali.student.core.statement.dto.ReqComponentInfo;
import org.kuali.student.core.statement.dto.StatementTreeViewInfo;
import org.kuali.student.core.statement.dto.StatementTypeInfo;
import org.kuali.student.lum.program.client.ProgramConstants;
import org.kuali.student.lum.program.client.events.StoreRequirementIDsEvent;
import org.kuali.student.lum.program.client.rpc.ProgramRpcService;
import org.kuali.student.lum.program.client.rpc.ProgramRpcServiceAsync;
import org.kuali.student.lum.program.client.rpc.StatementRpcService;
import org.kuali.student.lum.program.client.rpc.StatementRpcServiceAsync;
import org.kuali.student.lum.program.dto.ProgramRequirementInfo;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.Window;

public class ProgramRequirementsDataModel {

    private final ProgramRpcServiceAsync programRemoteService = GWT.create(ProgramRpcService.class);
    private StatementRpcServiceAsync statementRpcServiceAsync = GWT.create(StatementRpcService.class);
    private Controller parentController;
    private boolean isInitialized = false;

    //keeping track of rules and rule state
    public enum requirementState {STORED, ADDED, EDITED, DELETED;}
    private Map<Integer, ProgramRequirementInfo> progReqInfos = new LinkedHashMap<Integer, ProgramRequirementInfo>();
    private Map<Integer, requirementState> progReqState = new HashMap<Integer, requirementState>();
    private List<StatementTypeInfo> stmtTypes = new ArrayList<StatementTypeInfo>();

    private static Integer progReqIDs = 111111;
    private static HandlerManager eventBus = new HandlerManager(null);    

    public ProgramRequirementsDataModel(Controller parentController) {
        this.parentController = parentController;
    }  

    //retrieve rules based on IDs stored in this program
    public void retrieveProgramRequirements(final Callback<Boolean> onReadyCallback) {
        parentController.requestModel(ProgramConstants.PROGRAM_MODEL_ID, new ModelRequestCallback() {

            @Override
            public void onRequestFail(Throwable cause) {
                Window.alert(cause.getMessage());
                GWT.log("Unable to retrieve model for program requirements view", cause);
                onReadyCallback.exec(false);
            }

            @Override
            public void onModelReady(Model model) {
                Data program = ((DataModel)model).getRoot();
                Iterator iter = ((Data)program.get("programRequirements")).iterator();
                ArrayList<String> programRequirementIds = new ArrayList<String>();                
                while (iter.hasNext()) {
                    programRequirementIds.add(((String)iter.next()));
                }

                retrieveStatementTypes(programRequirementIds, onReadyCallback);
            }
        });
    }

    private void retrieveStatementTypes(final List<String> programRequirementIds, final Callback<Boolean> onReadyCallback) {

        //retrieve available program requirement types
        statementRpcServiceAsync.getStatementTypesForStatementType("kuali.statement.type.program", new KSAsyncCallback<List<StatementTypeInfo>>() {
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
                retrieveRules(programRequirementIds, onReadyCallback);
            }
        });
    }

    private void retrieveRules(List<String> programRequirementIds, final Callback<Boolean> onReadyCallback) {

      //  programRequirementIds.add(new String("a4483f97-6ae2-4fd0-a7a4-849a70cc21a7"));        

        //true if no program requirements exist yet
        if ((programRequirementIds == null) || programRequirementIds.isEmpty()) {
            isInitialized = true;
            onReadyCallback.exec(true);
            return;
        }

        programRemoteService.getProgramRequirements(programRequirementIds, new KSAsyncCallback<List<ProgramRequirementInfo>>() {
            @Override
            public void handleFailure(Throwable caught) {
                Window.alert(caught.getMessage());
                GWT.log("getProgramRequirement failed", caught);
                onReadyCallback.exec(false);
            }

            @Override
            public void onSuccess(List<ProgramRequirementInfo> programReqInfos) {
                //update rules list with new program requirements
                for (ProgramRequirementInfo programReqInfo : programReqInfos) {

                    if (getStmtTypeInfo(programReqInfo.getStatement().getType()) == null) {
                        Window.alert("Did not find corresponding statement type for program requirement of type: " + programReqInfo.getStatement().getType());
                        GWT.log("Did not find corresponding statement type for program requirement of type: " + programReqInfo.getStatement().getType(), null);
                    }
                    
                    progReqInfos.put(progReqIDs, programReqInfo);
                    progReqState.put(progReqIDs++, requirementState.STORED);
                }

                isInitialized = true;
                onReadyCallback.exec(true);
            }
        });     
    }

    public ProgramRequirementInfo updateRules(StatementTreeViewInfo newSubRule, Integer internalProgReqID, boolean isNewRule) {

        ProgramRequirementInfo affectedRule = progReqInfos.get(internalProgReqID);

        if (affectedRule == null) {
            Window.alert("Cannot find program requirement with a statement that has id: '" + newSubRule.getId() + "'");
            GWT.log("Cannot find program requirement with a statement that has id: '" + newSubRule.getId() + "'", null);
            return null;
        }

        if (progReqState.get(internalProgReqID) == ProgramRequirementsDataModel.requirementState.STORED) {
            progReqState.put(internalProgReqID, ProgramRequirementsDataModel.requirementState.EDITED);
        }

        //if we don't have top level req. components wrapped in statement, do so before we add another statement
        StatementTreeViewInfo affectedTopTree = affectedRule.getStatement();
        if ((affectedTopTree.getReqComponents() != null) && !affectedTopTree.getReqComponents().isEmpty()) {
            StatementTreeViewInfo stmtTree = new StatementTreeViewInfo();
            stmtTree.setId(ProgramRequirementsSummaryView.generateStatementTreeId());
            stmtTree.setType( affectedRule.getStatement().getType());
            stmtTree.setReqComponents(affectedTopTree.getReqComponents());
            List<StatementTreeViewInfo> stmtList = new ArrayList<StatementTreeViewInfo>();
            stmtList.add(stmtTree);
            affectedTopTree.setStatements(stmtList);
        }

        //now update the actual rule
        List<StatementTreeViewInfo> affectedSubRules = affectedTopTree.getStatements();
        if (isNewRule) {
            affectedSubRules.add(newSubRule);
        } else {
            //update rule
            if (affectedSubRules == null || affectedSubRules.isEmpty()) {
                affectedRule.setStatement(newSubRule);
            } else { //replace the related stored subrule with a new version
                for (StatementTreeViewInfo subRule : affectedSubRules) {
                    if (subRule.getId().equals(newSubRule.getId())) {
                        int treeIx = affectedSubRules.indexOf(subRule);
                        //only update if the rule is not empty
                        if (!isEmptyRule(newSubRule)) {
                            affectedSubRules.add(treeIx, newSubRule);
                        }
                        affectedSubRules.remove(subRule);
                        break;
                    }
                }
            }
        }

        return affectedRule;
    }

    public boolean isEmptyRule(StatementTreeViewInfo tree) {
        return (tree.getStatements() == null || tree.getStatements().isEmpty() && (tree.getReqComponents() == null || tree.getReqComponents().isEmpty()));
    }  

    public void updateProgramEntities(final Callback<List<ProgramRequirementInfo>> callback) {

        final List<String> referencedProgReqIds = new ArrayList<String>();

        programRemoteService.storeProgramRequirements(progReqState, progReqInfos, new KSAsyncCallback<Map<Integer, ProgramRequirementInfo>>() {
            @Override
            public void handleFailure(Throwable caught) {
                Window.alert(caught.getMessage());
                GWT.log("storeProgramRequirements failed", caught);
            }
            @Override
            public void onSuccess(Map<Integer, ProgramRequirementInfo> storedRules) {

                for (Integer internalProgReqID : storedRules.keySet()) {
                    ProgramRequirementInfo storedRule = storedRules.get(internalProgReqID);
                    switch (progReqState.get(internalProgReqID)) {
                        case STORED:
                            //rule was not changed so continue
                            referencedProgReqIds.add(storedRule.getId());
                            break;
                        case ADDED:
                            referencedProgReqIds.add(storedRule.getId());
                            progReqInfos.put(internalProgReqID, storedRule);
                            progReqState.put(internalProgReqID, ProgramRequirementsDataModel.requirementState.STORED);
                            break;
                        case EDITED:
                            referencedProgReqIds.add(storedRule.getId());
                            progReqInfos.put(internalProgReqID, storedRule);
                            progReqState.put(internalProgReqID, ProgramRequirementsDataModel.requirementState.STORED);
                            break;
                        case DELETED:
                            progReqInfos.remove(internalProgReqID);
                            progReqState.remove(internalProgReqID);
                            break;
                        default:
                            break;
                    }
                }

                eventBus.fireEvent(new StoreRequirementIDsEvent(referencedProgReqIds));
                callback.exec(new ArrayList(storedRules.values()));  //update display widgets
            }
        });        
    }

    public static void stripStatementIds(StatementTreeViewInfo tree) {
        List<StatementTreeViewInfo> statements = tree.getStatements();
        List<ReqComponentInfo> reqComponentInfos = tree.getReqComponents();

        if (tree.getId().indexOf(ProgramRequirementsSummaryView.NEW_STMT_TREE_ID) >= 0) {
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
                if (reqComponent.getId().indexOf(ProgramRequirementsSummaryView.NEW_REQ_COMP_ID) >= 0) {
                    reqComponent.setId(null);
                }

                for (ReqCompFieldInfo field : reqComponent.getReqCompFields()) {
                    field.setId(null);
                }

                reqComponent.setState("Active");
            }
        }
    }

    public List<ProgramRequirementInfo> getProgReqInfo(String stmtTypeId) {
        List<ProgramRequirementInfo> rules = new ArrayList<ProgramRequirementInfo>();
        for(ProgramRequirementInfo progReqInfo : progReqInfos.values()) {
            if (progReqInfo.getStatement().getType().equals(stmtTypeId)) {
                rules.add(progReqInfo);
            }
        }
        return rules;
    }

    public Integer getInternalProgReqID(ProgramRequirementInfo progReqInfo) {
        for(Integer key : progReqInfos.keySet()) {
            if (progReqInfos.get(key) == progReqInfo) {
                return key;
            }
        }

        Window.alert("Problem retrieving key for program requirement: " + progReqInfo.getId());
        GWT.log("Problem retrieving key for program requirement: " + progReqInfo.getId(), null);        

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
        if (progReqState.get(internalProgReqID) == ProgramRequirementsDataModel.requirementState.ADDED) {
            //user added a rule, didn't save it and now wants to delete it
            progReqState.remove(internalProgReqID);
            progReqInfos.remove(internalProgReqID);
        }
        markRuleAsDeleted(internalProgReqID);
    }

    public void markRuleAsDeleted(Integer internalProgReqID) {
        if ((progReqState.get(internalProgReqID) == ProgramRequirementsDataModel.requirementState.STORED) ||
            (progReqState.get(internalProgReqID) == ProgramRequirementsDataModel.requirementState.EDITED)) {
            progReqState.put(internalProgReqID, ProgramRequirementsDataModel.requirementState.DELETED);
        }
    }

    public void markRuleAsEdited(Integer internalProgReqID) {
        if (progReqState.get(internalProgReqID) == ProgramRequirementsDataModel.requirementState.STORED) {
            progReqState.put(internalProgReqID, ProgramRequirementsDataModel.requirementState.EDITED);
        }
    }

    public void addRule(ProgramRequirementInfo programReqInfo) {
        progReqInfos.put(progReqIDs, programReqInfo);
        progReqState.put(progReqIDs++, requirementState.ADDED);
    }

    public void updateRule(Integer internalProgReqID, ProgramRequirementInfo programReqInfo) {
        progReqInfos.put(internalProgReqID, programReqInfo);    
    }

    public String getStmtTypeName(String stmtTypeId) {
        String name = getStmtTypeInfo(stmtTypeId).getName();
        return (name == null ? "" : name);
    }
    
    public boolean isRuleExists(String stmtTypeId) {
        boolean showNoRuleText = true;
        for(ProgramRequirementInfo ruleInfo : progReqInfos.values()) {
            if ((ruleInfo.getStatement().getType().equals(stmtTypeId)) && (progReqState.get(getInternalProgReqID(ruleInfo)) != ProgramRequirementsDataModel.requirementState.DELETED)) {
                showNoRuleText = false;
            }
        }
        return showNoRuleText;
    }

    public ProgramRequirementInfo getProgReqByInternalId(Integer internalProgReqID) {
        return progReqInfos.get(internalProgReqID);
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
