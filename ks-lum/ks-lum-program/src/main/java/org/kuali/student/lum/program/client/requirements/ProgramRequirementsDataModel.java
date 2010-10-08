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
import org.kuali.student.core.dto.RichTextInfo;
import org.kuali.student.core.dto.StatusInfo;
import org.kuali.student.core.statement.dto.StatementTreeViewInfo;
import org.kuali.student.core.statement.dto.StatementTypeInfo;
import org.kuali.student.lum.program.client.ProgramConstants;
import org.kuali.student.lum.program.client.rpc.ProgramRpcService;
import org.kuali.student.lum.program.client.rpc.ProgramRpcServiceAsync;
import org.kuali.student.lum.program.client.rpc.StatementRpcService;
import org.kuali.student.lum.program.client.rpc.StatementRpcServiceAsync;
import org.kuali.student.lum.program.dto.MajorDisciplineInfo;
import org.kuali.student.lum.program.dto.ProgramRequirementInfo;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;

public class ProgramRequirementsDataModel {

    private final ProgramRpcServiceAsync programRemoteService = GWT.create(ProgramRpcService.class);
    private StatementRpcServiceAsync statementRpcServiceAsync = GWT.create(StatementRpcService.class);
    private Controller parentController;
    private boolean isInitialized = false;    

    private static int tempProgReqInfoID = 8888;   //TODO: remove after testing

    //keeping track of rules and rule state (UPDATED, ADDED, DELETED)
    public enum requirementState {STORED, ADDED, EDITED, DELETED;}
    private LinkedHashMap<StatementTypeInfo, LinkedHashMap<ProgramRequirementInfo, requirementState>> rules = new LinkedHashMap<StatementTypeInfo, LinkedHashMap<ProgramRequirementInfo, requirementState>>();

    public ProgramRequirementsDataModel(Controller parentController) {
        this.parentController = parentController;
    }

    public Set<StatementTypeInfo> getStoredStatementTypes() {
        return rules.keySet();
    }    

    public Set<ProgramRequirementInfo> getStoredProgRequirements(StatementTypeInfo statementTypeInfo) {
        return rules.get(statementTypeInfo).keySet();
    }

    public LinkedHashMap<ProgramRequirementInfo, requirementState> getStoredProgReqsAndStates(StatementTypeInfo statementTypeInfo) {
        return rules.get(statementTypeInfo);
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

                    //TODO remove after testing
                    if (stmtInfoType.getId().equals("kuali.statement.type.program.entrance")) {
                        LinkedHashMap<ProgramRequirementInfo, requirementState> tempRulesList = new LinkedHashMap<ProgramRequirementInfo, requirementState>();
                        ProgramRequirementInfo tempProgramInfo = new ProgramRequirementInfo();
                        tempProgramInfo.setId("NEWPROGREQ" + Integer.toString(tempProgReqInfoID++));   //set unique id
                        RichTextInfo text = new RichTextInfo();
                        text.setPlain("These are classes or sequences that a student must have completed in order to register" +
                                                    " in the course. For example, students must have completed 3 classes with a specific GPA.");
                        tempProgramInfo.setDescr(text);
                        tempProgramInfo.setShortTitle("Expected Total Credits: 50 - 60");
                        tempProgramInfo.setStatement(ProgramRequirementsViewController.getTestStatement());
                        tempRulesList.put(tempProgramInfo, requirementState.ADDED);
                        rules.put(stmtInfoType, tempRulesList);
                        continue;
                    }

                    rules.put(stmtInfoType, new LinkedHashMap<ProgramRequirementInfo, requirementState>());
                }

                //now retrieve the actual rules
                retrieveRules(programRequirementIds, onReadyCallback);
            }
        });
    }

    private void retrieveRules(List<String> programRequirementIds, final Callback<Boolean> onReadyCallback) {

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

                    boolean stmtTypeFound = false;
                    for (StatementTypeInfo stmtInfo : rules.keySet()) {

                        if (stmtInfo.getId().equals(programReqInfo.getType())) {
                            stmtTypeFound = true;
                            LinkedHashMap<ProgramRequirementInfo, requirementState> tempRulesList;
                            if (rules.get(stmtInfo) != null) {
                                tempRulesList = rules.get(stmtInfo);
                            } else {
                                tempRulesList = new LinkedHashMap<ProgramRequirementInfo, requirementState>();
                            }

                            //add the rule
                            tempRulesList.put(programReqInfo, requirementState.STORED);
                            rules.put(stmtInfo, tempRulesList);
                            break;
                        }
                    }

                    if (!stmtTypeFound) {
                        Window.alert("Did not find corresponding statement type for program requirement of type: " + programReqInfo.getType());
                        GWT.log("Did not find corresponding statement type for program requirement of type: " + programReqInfo.getType(), null);
                    }
                }

                isInitialized = true;                
                onReadyCallback.exec(true);
            }
        });            
    }

    public boolean isRuleExists(StatementTypeInfo stmtTypeInfo) {
        boolean showNoRuleText = true;
        if (getStoredProgReqsAndStates(stmtTypeInfo) != null && !getStoredProgReqsAndStates(stmtTypeInfo).isEmpty()) {
            for (ProgramRequirementInfo ruleInfo : getStoredProgRequirements(stmtTypeInfo)) {
                if (getStoredProgReqsAndStates(stmtTypeInfo).get(ruleInfo) != ProgramRequirementsDataModel.requirementState.DELETED) {
                    showNoRuleText = false;
                }
            }
        }
        return showNoRuleText;
    }

    public Map<StatementTypeInfo, ProgramRequirementInfo> updateRules(StatementTreeViewInfo newTree, String originalProgramReqId, boolean isNewRule) {
        //find the affected program requirement
        LinkedHashMap<ProgramRequirementInfo, ProgramRequirementsDataModel.requirementState> reqInfo = null;
        ProgramRequirementInfo affectedProgramRequirement = null;
        StatementTypeInfo affectedStatementTypeInfo = null;
        boolean progReqFound = false;
        for (StatementTypeInfo statementTypeInfo : getStoredStatementTypes()) {
            for (ProgramRequirementInfo progReqInfo : getStoredProgRequirements(statementTypeInfo)) {
                if (isNewRule) {
                    if (!progReqInfo.getId().equals(originalProgramReqId)) {
                        continue;
                    }
                } else {
                    if (!findStatementBasedOnID(newTree.getId(), progReqInfo.getStatement())) {
                        continue;
                    }
                }

                reqInfo = getStoredProgReqsAndStates(statementTypeInfo);
                affectedProgramRequirement = progReqInfo;
                affectedStatementTypeInfo = statementTypeInfo;
                progReqFound = true;
                break;
            }

            if (progReqFound) {
                break;
            }
        }

        if (reqInfo == null) {
            Window.alert("Cannot find program requirement with a statement that has id: '" + newTree.getId() + "'");
            GWT.log("Cannot find program requirement with a statement that has id: '" + newTree.getId() + "'", null);
            return null;
        }

        if (reqInfo.get(affectedProgramRequirement) == ProgramRequirementsDataModel.requirementState.STORED) {
            reqInfo.put(affectedProgramRequirement, ProgramRequirementsDataModel.requirementState.EDITED);
        }

        //if we don't have top level req. components wrapped in statement, do so before we add another statement
        StatementTreeViewInfo affectedRule = affectedProgramRequirement.getStatement();
        if ((affectedRule.getReqComponents() != null) && !affectedRule.getReqComponents().isEmpty()) {
            StatementTreeViewInfo stmtTree = new StatementTreeViewInfo();
            stmtTree.setId(ProgramRequirementsSummaryView.generateStatementTreeId());
            stmtTree.setType(affectedStatementTypeInfo.getId());
            stmtTree.setReqComponents(affectedRule.getReqComponents());
            List<StatementTreeViewInfo> stmtList = new ArrayList<StatementTreeViewInfo>();
            stmtList.add(stmtTree);
            affectedRule.setStatements(stmtList);
        }

        List<StatementTreeViewInfo> affectedStatements = affectedRule.getStatements();
        if (isNewRule) {
            affectedStatements.add(newTree);
        } else {
            //update rule
            if (affectedStatements == null || affectedStatements.isEmpty()) {
                affectedProgramRequirement.setStatement(newTree);
            } else { //replace rule with new version
                for (StatementTreeViewInfo tree : affectedStatements) {
                    if (tree.getId().equals(newTree.getId())) {
                        int treeIx = affectedStatements.indexOf(tree);
                        //only update if the rule is not empty
                        if (!isEmptyRule(newTree)) {
                            affectedStatements.add(treeIx, newTree);
                        }
                        affectedStatements.remove(tree);
                        break;
                    }
                }
            }
        }

        Map<StatementTypeInfo, ProgramRequirementInfo> result = new HashMap<StatementTypeInfo, ProgramRequirementInfo>();
        result.put(affectedStatementTypeInfo, affectedProgramRequirement);
        return result;
    }

    public boolean isEmptyRule(StatementTreeViewInfo tree) {
        return (tree.getStatements() == null || tree.getStatements().isEmpty() && (tree.getReqComponents() == null || tree.getReqComponents().isEmpty()));
    }

    public boolean findStatementBasedOnID(String id, StatementTreeViewInfo tree) {
        boolean found = false;
        if (id.equals(tree.getId())) {
            return true;
        }
        for (StatementTreeViewInfo oneTree : tree.getStatements()) {
            if (id.equals(oneTree.getId())) {
                found = true;
                break;
            }
        }
        return found;
    }    

    public void updateModelFromLocalData(final Object dto) {
        for (final StatementTypeInfo stmtTypeInfo : getStoredStatementTypes()) {
            for (final ProgramRequirementInfo rule : getStoredProgRequirements(stmtTypeInfo)) {
                final ProgramRequirementsDataModel.requirementState ruleState = getStoredProgReqsAndStates(stmtTypeInfo).get(rule);
                switch (ruleState) {
                    case STORED:
                        //rule was not changed so continue
                        break;
                    case ADDED:
                        programRemoteService.createProgramRequirement(rule, new KSAsyncCallback<ProgramRequirementInfo>() {
                            @Override
                            public void handleFailure(Throwable caught) {
                                Window.alert(caught.getMessage());
                                GWT.log("createProgramRequirement failed", caught);
                            }
                            @Override
                            public void onSuccess(ProgramRequirementInfo programReqInfo) {
                                updateProgReqId(dto, programReqInfo.getId(), ruleState);
                                getStoredProgReqsAndStates(stmtTypeInfo).put(programReqInfo, ProgramRequirementsDataModel.requirementState.STORED);
                            }
                        });
                        break;
                    case EDITED:
                        programRemoteService.updateProgramRequirement(rule, new KSAsyncCallback<ProgramRequirementInfo>() {
                            @Override
                            public void handleFailure(Throwable caught) {
                                Window.alert(caught.getMessage());
                                GWT.log("updateProgramRequirement failed", caught);
                            }
                            @Override
                            public void onSuccess(ProgramRequirementInfo programReqInfo) {
                                updateProgReqId(dto, programReqInfo.getId(), ruleState);
                                getStoredProgReqsAndStates(stmtTypeInfo).put(programReqInfo, ProgramRequirementsDataModel.requirementState.STORED);
                            }
                        });
                        break;
                    case DELETED:
                        programRemoteService.deleteProgramRequirement(rule.getId(), new KSAsyncCallback<StatusInfo>() {
                            @Override
                            public void handleFailure(Throwable caught) {
                                Window.alert(caught.getMessage());
                                GWT.log("deleteProgramRequirement failed", caught);
                            }
                            @Override
                            public void onSuccess(StatusInfo statusInfo) {
                                updateProgReqId(dto, rule.getId(), ruleState);
                                getStoredProgRequirements(stmtTypeInfo).remove(rule);
                            }
                        });
                        break;
                    default:
                        break;
                }
            }
        }
    }

    //now update the program this requirement belongs to
    private void updateProgReqId(Object dto, String progReqId, ProgramRequirementsDataModel.requirementState op) {
        if (dto instanceof MajorDisciplineInfo) {
            MajorDisciplineInfo mdInfo = (MajorDisciplineInfo) dto;
            //mdInfo.getProgramRequirements().add(progReqId);
            updateProgramInfo(mdInfo.getProgramRequirements(), progReqId, op);
        } else {
            Window.alert("Only persistence of MajorDiscipline is currently implemented");
            GWT.log("Unable to retrieve model for program requirements view", null);
        }
    }

    private void updateProgramInfo(List<String> requirements, String id, ProgramRequirementsDataModel.requirementState op) {
        switch (op) {
            case ADDED:
                requirements.add(id);
                break;
            case DELETED:
                requirements.remove(id);
                break;
            default:
                break;
        }
    }

    public boolean isInitialized() {
        return isInitialized;
    }

    public void setInitialized(boolean initialized) {
        isInitialized = initialized;
    }

    public boolean compareStatementTrees(StatementTreeViewInfo tree1, StatementTreeViewInfo tree2) {
        boolean found = false;
        boolean noStatementsInTree1 = (tree1.getStatements() == null) || tree1.getStatements().isEmpty();
        boolean noStatementsInTree2 = (tree2.getStatements() == null) || (tree2.getStatements().isEmpty());

        if (noStatementsInTree1 && noStatementsInTree2) {
            found = tree1.getId().equals(tree2.getId());
        } else if (noStatementsInTree1) {
            found = findStatementBasedOnID(tree1.getId(), tree2);
        } else if (noStatementsInTree2) {
            found = findStatementBasedOnID(tree2.getId(), tree1);
        } else {
            for (StatementTreeViewInfo oneTree : tree1.getStatements()) {
                found = findStatementBasedOnID(oneTree.getId(), tree2);
                if (found) {
                    break;
                }
            }
        }
        return found;
    }
}
