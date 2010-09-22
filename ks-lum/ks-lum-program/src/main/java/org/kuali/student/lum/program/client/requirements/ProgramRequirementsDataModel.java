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

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

import org.kuali.student.common.ui.client.application.KSAsyncCallback;
import org.kuali.student.common.ui.client.mvc.*;
import org.kuali.student.core.assembly.data.Data;
import org.kuali.student.core.dto.RichTextInfo;
import org.kuali.student.core.statement.dto.StatementTypeInfo;
import org.kuali.student.lum.program.client.ProgramConstants;
import org.kuali.student.lum.program.client.rpc.ProgramRpcService;
import org.kuali.student.lum.program.client.rpc.ProgramRpcServiceAsync;
import org.kuali.student.lum.program.client.rpc.StatementRpcService;
import org.kuali.student.lum.program.client.rpc.StatementRpcServiceAsync;
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
                Data programRequirementIdsX = program.get("programRequirements");     //TODO fix when we have actual data
                List<String> programRequirementIds = new ArrayList<String>();
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

    public boolean isInitialized() {
        return isInitialized;
    }    
}
