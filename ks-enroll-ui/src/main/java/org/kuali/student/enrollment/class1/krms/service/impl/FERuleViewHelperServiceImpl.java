/**
 * Copyright 2005-2013 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl2.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.student.enrollment.class1.krms.service.impl;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.core.api.criteria.PredicateFactory;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.rice.core.api.exception.RiceIllegalArgumentException;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krms.api.repository.LogicalOperator;
import org.kuali.rice.krms.api.repository.proposition.PropositionType;
import org.kuali.rice.krms.dto.ActionEditor;
import org.kuali.rice.krms.dto.PropositionEditor;
import org.kuali.rice.krms.dto.RuleEditor;
import org.kuali.rice.krms.util.KRMSConstants;
import org.kuali.rice.krms.util.PropositionTreeUtil;
import org.kuali.student.common.util.ContextBuilder;
import org.kuali.student.enrollment.class1.krms.dto.FEPropositionEditor;
import org.kuali.student.enrollment.class1.krms.dto.FERuleEditor;
import org.kuali.student.enrollment.class2.courseoffering.util.ActivityOfferingConstants;
import org.kuali.student.enrollment.class2.courseoffering.util.CourseOfferingResourceLoader;
import org.kuali.student.lum.lu.ui.krms.service.impl.LURuleViewHelperServiceImpl;
import org.kuali.student.r1.common.rice.StudentIdentityConstants;
import org.kuali.student.r2.common.dto.TimeOfDayInfo;
import org.kuali.student.r2.common.util.TimeOfDayHelper;
import org.kuali.student.r2.common.util.date.DateFormatters;
import org.kuali.student.r2.core.constants.KSKRMSServiceConstants;
import org.kuali.student.r2.core.room.dto.BuildingInfo;
import org.kuali.student.r2.core.room.dto.RoomInfo;
import org.kuali.student.r2.core.room.service.RoomService;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Kuali Student Team
 */
public class FERuleViewHelperServiceImpl extends LURuleViewHelperServiceImpl {

    private RoomService roomService;

    private String rdlActionTypeId;

    /**
     * @return
     */
    @Override
    public Class<? extends PropositionEditor> getPropositionEditorClass() {
        return FEPropositionEditor.class;
    }

    @Override
    public void refreshInitTrees(RuleEditor rule) {

        if (rule == null) {
            return;
        }

        //Rebuild the trees
        rule.setEditTree(getEditTreeBuilder().buildTree(rule));
    }

    @Override
    public void refreshViewTree(RuleEditor rule) {

        if (rule == null) {
            return;
        }

        //Rebuild the trees
        rule.setDescription(this.getDescriptionForPropositionTree(rule.getPropositionEditor(), true));

    }

    public List<BuildingInfo> retrieveBuildingInfo(String buildingCode, boolean strictMatch) throws Exception {

        QueryByCriteria.Builder qbcBuilder = QueryByCriteria.Builder.create();
        if (!strictMatch) {
            buildingCode = StringUtils.upperCase(buildingCode) + "%";
        }
        qbcBuilder.setPredicates(PredicateFactory.like("buildingCode", buildingCode));

        QueryByCriteria criteria = qbcBuilder.build();

        List<BuildingInfo> b = getRoomService().searchForBuildings(criteria, createContextInfo());
        return b;
    }

    public List<RoomInfo> retrieveRoomInfo(String roomCode, String buildingCode, boolean strictMatch) throws Exception {
        List<RoomInfo> RoomInfos = new ArrayList<RoomInfo>();
        int firstBuilding = 0;
        List<BuildingInfo> buildings = getRoomService().getBuildingsByBuildingCode(buildingCode, ContextBuilder.loadContextInfo());
        if (buildings.size() > 0) {
            if (StringUtils.isBlank(roomCode)) {
                List<String> roomIds = getRoomService().getRoomIdsByBuilding(buildings.get(firstBuilding).getId(), ContextBuilder.loadContextInfo());
                return getRoomService().getRoomsByIds(roomIds, ContextBuilder.loadContextInfo());
            } else {
                return getRoomService().getRoomsByBuildingAndRoomCode(buildings.get(firstBuilding).getBuildingCode(), roomCode, ContextBuilder.loadContextInfo());
            }
        } else {
            return RoomInfos;
        }
    }

    @Override
    public void buildActions(RuleEditor ruleEditor) {
        try {
            FERuleEditor rule = (FERuleEditor) ruleEditor;
            ActionEditor action = this.getRDLAction(rule);
            Map<String, String> attributes = new HashMap<String, String>();

            if (rule.isTba()) {
                attributes.put(KSKRMSServiceConstants.ACTION_PARAMETER_TYPE_RDL_TBA, Boolean.TRUE.toString());
            } else {
                attributes.put(KSKRMSServiceConstants.ACTION_PARAMETER_TYPE_RDL_TBA, Boolean.FALSE.toString());
            }
            if (rule.getDay() != null) {
                attributes.put(KSKRMSServiceConstants.ACTION_PARAMETER_TYPE_RDL_DAY, rule.getDay());
            } else {
                attributes.put(KSKRMSServiceConstants.ACTION_PARAMETER_TYPE_RDL_DAY, StringUtils.EMPTY);
            }

            if (StringUtils.isNotEmpty(rule.getStartTime())) {
                String startTimeAMPM = new StringBuilder(rule.getStartTime()).append(" ").append(rule.getStartTimeAMPM()).toString();
                TimeOfDayInfo startTimeOfDayInfo = TimeOfDayHelper.makeTimeOfDayInfoFromTimeString(startTimeAMPM);
                String startTime = String.valueOf(TimeOfDayHelper.getMillis(startTimeOfDayInfo));
                attributes.put(KSKRMSServiceConstants.ACTION_PARAMETER_TYPE_RDL_STARTTIME, startTime);
            } else {
                attributes.put(KSKRMSServiceConstants.ACTION_PARAMETER_TYPE_RDL_STARTTIME, StringUtils.EMPTY);
            }
            if (StringUtils.isNotEmpty(rule.getEndTime())) {
                String endTimeAMPM = new StringBuilder(rule.getEndTime()).append(" ").append(rule.getEndTimeAMPM()).toString();
                TimeOfDayInfo endTimeOfDayInfo = TimeOfDayHelper.makeTimeOfDayInfoFromTimeString(endTimeAMPM);
                String endTime = String.valueOf(TimeOfDayHelper.getMillis(endTimeOfDayInfo));
                attributes.put(KSKRMSServiceConstants.ACTION_PARAMETER_TYPE_RDL_ENDTIME, endTime);
            } else {
                attributes.put(KSKRMSServiceConstants.ACTION_PARAMETER_TYPE_RDL_ENDTIME, StringUtils.EMPTY);
            }

            if (rule.getBuilding().getBuildingCode() != null && !rule.getBuilding().getBuildingCode().isEmpty()) {
                attributes.put(KSKRMSServiceConstants.ACTION_PARAMETER_TYPE_RDL_FACILITY, rule.getBuilding().getId());
            }else {
                attributes.put(KSKRMSServiceConstants.ACTION_PARAMETER_TYPE_RDL_FACILITY, StringUtils.EMPTY);
            }
            if (rule.getRoom().getRoomCode() != null && !rule.getRoom().getRoomCode().isEmpty()) {
                attributes.put(KSKRMSServiceConstants.ACTION_PARAMETER_TYPE_RDL_ROOM, rule.getRoom().getId());
            } else {
                attributes.put(KSKRMSServiceConstants.ACTION_PARAMETER_TYPE_RDL_ROOM, StringUtils.EMPTY);
            }

            action.setAttributes(attributes);
            action.setDescription(ruleEditor.getDescription());

            //Set actionEditor required fields from rule
            action.setName("day" + rule.getDay() + rule.getTimePeriodToDisplay());
            action.setName("day" + rule.getDay() + rule.getTimePeriodToDisplay());

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private ActionEditor getRDLAction(FERuleEditor rule) {

        for (ActionEditor action : rule.getActionEditors()) {
            if (action.getTypeId().equals(this.getRdlActionTypeId())) {
                return action;
            }
        }

        ActionEditor actionEditor = new ActionEditor();
        actionEditor.setRuleId(rule.getId());
        actionEditor.setNamespace(StudentIdentityConstants.KS_NAMESPACE_CD);
        actionEditor.setTypeId(getRdlActionTypeId());
        actionEditor.setSequenceNumber(1);
        rule.getActionEditors().add(actionEditor);
        return actionEditor;
    }

    protected String getRdlActionTypeId() {
        if (rdlActionTypeId == null) {
            rdlActionTypeId = this.getKrmsTypeRepositoryService().getTypeByName(StudentIdentityConstants.KS_NAMESPACE_CD, KSKRMSServiceConstants.ACTION_TYPE_REQUESTED_DELIVERY_LOGISTIC).getId();
        }
        return rdlActionTypeId;
    }

    private String getDescriptionForPropositionTree(PropositionEditor proposition, boolean isRoot) {

        if (proposition == null) {
            return StringUtils.EMPTY;
        }

        //Get the natural language for the usage key.
        this.getNaturalLanguageHelper().setNaturalLanguageForUsage(proposition, this.getNaturalLanguageUsageKey(), StudentIdentityConstants.KS_NAMESPACE_CD);

        StringBuilder naturalLanguage = new StringBuilder();
        if (proposition.getPropositionTypeCode().equals(PropositionType.SIMPLE.getCode())) {
            naturalLanguage.append(proposition.getNaturalLanguage().get(this.getNaturalLanguageUsageKey()));

        } else if (proposition.getPropositionTypeCode().equals(PropositionType.COMPOUND.getCode())) {
            //Null check because newly created compound propositions should also be translateable.
            if (proposition.getCompoundComponents() != null) {
                String operator = getCompoundSeperator(proposition, isRoot);
                for (PropositionEditor child : proposition.getCompoundEditors()) {
                    if (proposition.getCompoundComponents().indexOf(child) != 0) {
                        naturalLanguage.append(operator);
                    }
                    naturalLanguage.append(this.getDescriptionForPropositionTree(child, false));
                }
            }

        } else {
            throw new RiceIllegalArgumentException("Unknown proposition type: " + proposition.getPropositionTypeCode());
        }

        return naturalLanguage.toString();
    }

    private String getCompoundSeperator(PropositionEditor proposition, boolean isRoot) {
        String operator = getCompoundOperator(proposition);
        if (isRoot) {
            return ". " + StringUtils.capitalize(operator) + " ";
        }
        return "; " + operator + " ";
    }

    private String getCompoundOperator(PropositionEditor proposition) {
        String operator = null;
        if (LogicalOperator.AND.getCode().equalsIgnoreCase(proposition.getCompoundOpCode())) {
            operator = "and";
        } else if (LogicalOperator.OR.getCode().equalsIgnoreCase(proposition.getCompoundOpCode())) {
            operator = "or";
        }
        return operator;
    }

    @Override
    public Boolean validateRule(RuleEditor ruleEditor) {

        boolean hasError = super.validateRule(ruleEditor);
        FERuleEditor rule = (FERuleEditor) ruleEditor;

        if (rule.isTba()) {
            rule.setStartTime("");
            rule.setStartTimeAMPM("");
            rule.setEndTime("");
            rule.setEndTimeAMPM("");
        } else {

            //Return with error if the RDL fields have not been completed and TBA is unticked
            if (rule.getStartTime() == null || rule.getStartTime().isEmpty()) {
                GlobalVariables.getMessageMap().putError(PropositionTreeUtil.DOC_NEW_DATAOBJECT_PATH + ".ruleEditor.startTime", KRMSConstants.KRMS_MSG_ERROR_RDL_STARTTIME);
                hasError = true;
            }
            if (rule.getStartTimeAMPM() == null || rule.getStartTimeAMPM().isEmpty()) {
                GlobalVariables.getMessageMap().putError(PropositionTreeUtil.DOC_NEW_DATAOBJECT_PATH + ".ruleEditor.startTimeAMPM", KRMSConstants.KRMS_MSG_ERROR_RDL_STARTTIME_AMPM);
                hasError = true;
            }
            if (rule.getEndTime() == null || rule.getEndTime().isEmpty()) {
                GlobalVariables.getMessageMap().putError(PropositionTreeUtil.DOC_NEW_DATAOBJECT_PATH + ".ruleEditor.endTime", KRMSConstants.KRMS_MSG_ERROR_RDL_ENDTIME);
                hasError = true;
            }
            if (rule.getEndTimeAMPM() == null || rule.getEndTimeAMPM().isEmpty()) {
                GlobalVariables.getMessageMap().putError(PropositionTreeUtil.DOC_NEW_DATAOBJECT_PATH + ".ruleEditor.endTimeAMPM", KRMSConstants.KRMS_MSG_ERROR_RDL_ENDTIME_AMPM);
                hasError = true;
            }

            if (!hasError) {
                //Return with error message if start time is not prior to end time, but only when all other errors are resolved.
                if (compareTime(rule.getStartTime(), rule.getStartTimeAMPM(), rule.getEndTime(), rule.getEndTimeAMPM())) {
                    GlobalVariables.getMessageMap().putErrorForSectionId(KRMSConstants.KRMS_RULE_TREE_GROUP_ID, ActivityOfferingConstants.MSG_ERROR_INVALID_START_TIME);
                    hasError = true;
                }
            }
        }

        if (rule.getProposition() == null) {
            GlobalVariables.getMessageMap().putErrorForSectionId(KRMSConstants.KRMS_RULE_TREE_GROUP_ID, KRMSConstants.KRMS_MSG_ERROR_RULE_UPDATE);
            hasError = true;
        }

        try {
            if (rule.getBuilding().getBuildingCode() != null && !rule.getBuilding().getBuildingCode().isEmpty()) {
                List<BuildingInfo> buildingInfos = new ArrayList<BuildingInfo>();
                buildingInfos = retrieveBuildingInfo(rule.getBuilding().getBuildingCode(), true);
                if (buildingInfos.size() > 0) {
                    for (BuildingInfo buildingInfo : buildingInfos) {
                        if (buildingInfo.getBuildingCode().equals(rule.getBuilding().getBuildingCode())) {
                            rule.setBuilding(buildingInfo);
                            break;
                        }
                    }
                } else {
                    GlobalVariables.getMessageMap().putError(PropositionTreeUtil.DOC_NEW_DATAOBJECT_PATH + ".ruleEditor.building.buildingCode", KRMSConstants.KRMS_MSG_ERROR_RDL_FACILITY);
                    hasError = true;
                }

            } else {
                rule.setBuilding(new BuildingInfo());
                rule.setRoom(new RoomInfo());
            }
            if (rule.getRoom().getRoomCode() != null && !rule.getRoom().getRoomCode().isEmpty()) {

                List<RoomInfo> roomInfos = new ArrayList<RoomInfo>();
                roomInfos = retrieveRoomInfo(rule.getRoom().getRoomCode(), rule.getBuilding().getBuildingCode(), true);
                if (roomInfos.size() > 0) {
                    for (RoomInfo roomInfo : roomInfos) {
                        if (roomInfo.getRoomCode().equals(rule.getRoom().getRoomCode())) {
                            rule.setRoom(roomInfo);
                            break;
                        }
                    }
                } else {
                    GlobalVariables.getMessageMap().putError(PropositionTreeUtil.DOC_NEW_DATAOBJECT_PATH + ".ruleEditor.room.roomCode", KRMSConstants.KRMS_MSG_ERROR_RDL_ROOM);
                    hasError = true;
                }

            } else {
                rule.setRoom(new RoomInfo());
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return hasError;
    }

    private boolean compareTime(String startTime, String startTimeAMPM, String endTime, String endTimeAMPM) {
        String sTimeAMPM = new StringBuilder(startTime).append(" ").append(startTimeAMPM).toString();
        String eTimeAMPM = new StringBuilder(endTime).append(" ").append(endTimeAMPM).toString();

        long sTime;
        long eTime;

        try {
            sTime = parseTimeToMillis(sTimeAMPM);
            eTime = parseTimeToMillis(eTimeAMPM);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        if (eTime <= sTime) {
            return true;
        }
        return false;
    }

    protected long parseTimeToMillis(final String time) throws ParseException {
        return DateFormatters.HOUR_MINUTE_AM_PM_TIME_FORMATTER.parse(time).getTime();
    }

    public String getNaturalLanguageUsageKey() {
        return KSKRMSServiceConstants.KRMS_NL_TYPE_CATALOG;
    }

    public RoomService getRoomService() {
        if (roomService == null) {
            roomService = CourseOfferingResourceLoader.loadRoomService();
        }
        return roomService;
    }

}
