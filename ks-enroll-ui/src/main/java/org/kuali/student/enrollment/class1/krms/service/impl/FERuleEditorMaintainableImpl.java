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

//import org.apache.commons.lang.StringUtils;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.krad.maintenance.MaintenanceDocument;
import org.kuali.rice.krms.api.repository.action.ActionDefinition;
import org.kuali.rice.krms.api.repository.agenda.AgendaDefinition;
import org.kuali.rice.krms.api.repository.agenda.AgendaItemDefinition;
import org.kuali.rice.krms.api.repository.reference.ReferenceObjectBinding;
import org.kuali.rice.krms.api.repository.rule.RuleDefinition;
import org.kuali.rice.krms.dto.ActionEditor;
import org.kuali.rice.krms.dto.AgendaEditor;
import org.kuali.rice.krms.dto.AgendaTypeInfo;
import org.kuali.rice.krms.dto.RuleEditor;
import org.kuali.rice.krms.dto.RuleTypeInfo;
import org.kuali.rice.krms.service.impl.RuleEditorMaintainableImpl;
import org.kuali.student.enrollment.class1.krms.dto.FEAgendaEditor;
import org.kuali.student.enrollment.class1.krms.dto.FERuleEditor;
import org.kuali.student.enrollment.class1.krms.dto.FERuleManagementWrapper;
import org.kuali.student.enrollment.class2.courseoffering.service.decorators.PermissionServiceConstants;
import org.kuali.student.enrollment.class2.courseoffering.util.CourseOfferingResourceLoader;
import org.kuali.student.r1.common.rice.StudentIdentityConstants;
import org.kuali.student.r2.common.util.date.DateFormatters;
import org.kuali.student.r2.core.class1.type.service.TypeService;
import org.kuali.student.r2.core.constants.KSKRMSServiceConstants;
import org.kuali.student.r2.core.constants.TypeServiceConstants;
import org.kuali.student.r2.core.room.service.RoomService;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

/**
 * Overridden class to handle CO specific maintainable functionality.
 *
 * @author Kuali Student Team
 */
public class FERuleEditorMaintainableImpl extends RuleEditorMaintainableImpl {

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(FERuleEditorMaintainableImpl.class);

    private transient RoomService roomService;
    private transient TypeService typeService;

    private String usageId;
    private String actionTypeId;

    @Override
    public Object retrieveObjectForEditOrCopy(MaintenanceDocument document, Map<String, String> dataObjectKeys) {

        FERuleManagementWrapper dataObject = new FERuleManagementWrapper();

        dataObject.setNamespace(KSKRMSServiceConstants.NAMESPACE_CODE);
        dataObject.setRefDiscriminatorType(TypeServiceConstants.REF_OBJECT_URI_TYPE);

        String typeKey = dataObjectKeys.get("refObjectId");
        dataObject.setRefObjectId(typeKey);
        dataObject.setAgendas(this.getAgendasForRef(dataObject.getRefDiscriminatorType(), typeKey));

        try {
            dataObject.setType(this.getTypeService().getType(typeKey, this.createContextInfo()));
        } catch (Exception e) {
            throw new RuntimeException("Could not retrieve type for " + typeKey);
        }

        return dataObject;
    }

    @Override
    public String getViewTypeName() {
        return KSKRMSServiceConstants.AGENDA_TYPE_FINAL_EXAM;
    }

    protected List<AgendaEditor> getAgendasForRef(String discriminatorType, String refObjectId) {
        // Initialize new array lists.
        List<AgendaEditor> agendas = new ArrayList<AgendaEditor>();
        List<AgendaEditor> sortedAgendas = new ArrayList<AgendaEditor>();

        // Get the list of existing agendas
        LOG.info("Retrieving reference object binding for refobjectid: " + refObjectId);
        List<ReferenceObjectBinding> refObjectsBindings = this.getRuleManagementService().findReferenceObjectBindingsByReferenceObject(discriminatorType, refObjectId);
        for (ReferenceObjectBinding referenceObjectBinding : refObjectsBindings) {
            LOG.info("Retrieved reference object binding with id: " + referenceObjectBinding);
            agendas.add(this.getAgendaEditor(referenceObjectBinding.getKrmsObjectId()));
        }

        // Lookup existing agenda by type
        for (AgendaTypeInfo agendaTypeInfo : this.getTypeRelationships()) {
            AgendaEditor agenda = null;
            for (AgendaEditor existingAgenda : agendas) {
                if (existingAgenda.getTypeId().equals(agendaTypeInfo.getId())) {
                    agenda = existingAgenda;
                    break;
                }
            }
            if (agenda == null) {
                agenda = new FEAgendaEditor();
                agenda.setTypeId(agendaTypeInfo.getId());
            }

            agenda.setAgendaTypeInfo(agendaTypeInfo);
            agenda.setRuleEditors(this.getRulesForAgendas(agenda));
            sortedAgendas.add(agenda);
        }

        return sortedAgendas;
    }

    /**
     * This method was overriden from the RuleEditorMaintainableImpl to create an EnrolAgendaEditor instead of
     * an AgendaEditor.
     *
     * @param agendaId
     * @return EnrolAgendaEditor.
     */
    @Override
    protected AgendaEditor getAgendaEditor(String agendaId) {
        AgendaDefinition agenda = this.getRuleManagementService().getAgenda(agendaId);
        LOG.info("Retrieved agenda for id: " + agendaId);
        return new FEAgendaEditor(agenda);
    }

    public Map<String, RuleEditor> getRulesForAgendas(AgendaEditor agenda) {

        //Get all existing rules.
        List<RuleEditor> existingRules = null;
        if (agenda.getId() != null) {
            LOG.info("Retrieving agenda item for id: " + agenda.getFirstItemId());
            AgendaItemDefinition firstItem = this.getRuleManagementService().getAgendaItem(agenda.getFirstItemId());
            LOG.info("Retrieved agenda item for id: " + agenda.getFirstItemId());
            FEAgendaEditor feAgenda = (FEAgendaEditor) agenda;
            feAgenda.setRules(getRuleEditorsFromTree(firstItem, true));

            List<RuleEditor> rules = new ArrayList<RuleEditor>();
            for (RuleTypeInfo ruleType : agenda.getAgendaTypeInfo().getRuleTypes()) {
                RuleEditor ruleEditor = null;
                    if (feAgenda.getRules() != null) {
                        for (RuleEditor rule : feAgenda.getRules()) {
                           if (rule.getTypeId().equals(ruleType.getId())){
                            ruleEditor = rule;
                            ruleEditor.setRuleTypeInfo(ruleType);
                            rules.add(ruleEditor);
                           }
                        }
                        feAgenda.setRules(rules);
                    }

            }
        }

        return null;
    }

    /**
     * Retrieves all the rules from the agenda tree and create a list of ruleeditor objects.
     * <p/>
     * Also initialize the proposition editors for each rule recursively and set natural language for the view trees.
     *
     * @param agendaItem
     * @return
     */
    @Override
    protected List<RuleEditor> getRuleEditorsFromTree(AgendaItemDefinition agendaItem, boolean initProps) {

        List<RuleEditor> rules = new ArrayList<RuleEditor>();
        if (agendaItem.getRule() != null) {

            //Build the ruleEditor
            FERuleEditor ruleEditor = new FERuleEditor(agendaItem.getRule());
            ActionEditor action = ruleEditor.getActionForType(this.getActionTypeId());
            Map<String, String> attributes = action.getAttributes();
            if (attributes.containsKey(KSKRMSServiceConstants.ACTION_PARAMETER_TYPE_RDL_DAY)) {
                ruleEditor.setDay(attributes.get(KSKRMSServiceConstants.ACTION_PARAMETER_TYPE_RDL_DAY));
            }
            if (attributes.containsKey(KSKRMSServiceConstants.ACTION_PARAMETER_TYPE_RDL_STARTTIME)) {
                Date timeForDisplay = new Date(Long.parseLong(attributes.get(KSKRMSServiceConstants.ACTION_PARAMETER_TYPE_RDL_STARTTIME)));
                String startTime = DateFormatters.HOUR_MINUTE_AM_PM_TIME_FORMATTER.format(timeForDisplay);
                ruleEditor.setStartTime(org.apache.commons.lang.StringUtils.substringBefore(startTime, " "));
                ruleEditor.setStartTimeAMPM(org.apache.commons.lang.StringUtils.substringAfter(startTime, " "));
            }
            if (attributes.containsKey(KSKRMSServiceConstants.ACTION_PARAMETER_TYPE_RDL_ENDTIME)) {
                Date timeForDisplay = new Date(Long.parseLong(attributes.get(KSKRMSServiceConstants.ACTION_PARAMETER_TYPE_RDL_ENDTIME)));
                String endTime = DateFormatters.HOUR_MINUTE_AM_PM_TIME_FORMATTER.format(timeForDisplay);
                ruleEditor.setEndTime(org.apache.commons.lang.StringUtils.substringBefore(endTime, " "));
                ruleEditor.setEndTimeAMPM(org.apache.commons.lang.StringUtils.substringAfter(endTime, " "));
            }
            if (attributes.containsKey(KSKRMSServiceConstants.ACTION_PARAMETER_TYPE_RDL_FACILITY)) {
                String buildingId = attributes.get(KSKRMSServiceConstants.ACTION_PARAMETER_TYPE_RDL_FACILITY);
                try {
                    ruleEditor.setBuilding(this.getRoomService().getBuilding(buildingId, this.createContextInfo()));
                } catch (Exception e) {
                    throw new RuntimeException("Could not retrieve building for " + buildingId);
                }
            }
            if (attributes.containsKey(KSKRMSServiceConstants.ACTION_PARAMETER_TYPE_RDL_ROOM)) {
                String roomId = attributes.get(KSKRMSServiceConstants.ACTION_PARAMETER_TYPE_RDL_ROOM);
                try {
                    ruleEditor.setRoom(this.getRoomService().getRoom(roomId, this.createContextInfo()));
                } catch (Exception e) {
                    throw new RuntimeException("Could not retrieve room for " + roomId);
                }
            }

            String description = this.getRuleManagementService().translateNaturalLanguageForObject(this.getUsageId(), "rule", ruleEditor.getId(), "en");
            int index = description.indexOf(": ");
            ruleEditor.setDescription(description.substring(index + 1));

            //Initialize the Proposition tree
            if (initProps) {
                this.initPropositionEditor(ruleEditor.getPropositionEditor());
            }

            //Add rule to list on agenda
            rules.add(ruleEditor);
        }

        if (agendaItem.getWhenFalse() != null) {

            rules.addAll(getRuleEditorsFromTree(agendaItem.getWhenFalse(), initProps));
        }

        return rules;
    }


    @Override
    public AgendaItemDefinition maintainAgendaItems(AgendaEditor agenda, String namePrefix, String nameSpace) {

        Queue<RuleEditor> rules = new LinkedList<RuleEditor>();
        FEAgendaEditor feAgendaEditor = (FEAgendaEditor) agenda;
        for (RuleEditor rule : feAgendaEditor.getRules())
            if (!rule.isDummy()) {
                rules.add(rule);
            }
        // Clear the first item and update.
        AgendaItemDefinition firstItem = this.getRuleManagementService().getAgendaItem(agenda.getFirstItemId());

        AgendaItemDefinition.Builder firstItemBuilder = AgendaItemDefinition.Builder.create(agenda.getFirstItemId(), agenda.getId());
        firstItemBuilder.setRule(null);
        firstItemBuilder.setRuleId(null);
        firstItemBuilder.setWhenFalse(null);
        firstItemBuilder.setWhenFalseId(null);
        firstItemBuilder.setVersionNumber(firstItem.getVersionNumber());
        this.getRuleManagementService().updateAgendaItem(firstItemBuilder.build());

        //Delete current agenda items to rebuild the tree.
        if (firstItem.getWhenFalse() != null) {
            this.deleteAgendaItems(firstItem.getWhenFalse());
        }

        //Delete rules
        for (RuleEditor deletedRule : agenda.getDeletedRules()) {
            this.getRuleManagementService().deleteRule(deletedRule.getId());
        }

        List<FERuleEditor> newRules = new ArrayList<FERuleEditor>();
        AgendaItemDefinition rootItem = this.getRuleManagementService().getAgendaItem(agenda.getFirstItemId());
        AgendaItemDefinition.Builder rootItemBuilder = AgendaItemDefinition.Builder.create(rootItem);
        AgendaItemDefinition.Builder itemBuilder = rootItemBuilder;
        while (rules.peek() != null) {
            RuleEditor ruleEditor = rules.poll();
            itemBuilder.setRule(this.finRule(ruleEditor, namePrefix, nameSpace));
            if(itemBuilder.getRule().getId() == null) {
                newRules.add((FERuleEditor) ruleEditor);
            }
            itemBuilder.setRuleId(itemBuilder.getRule().getId());
            if (rules.peek() != null) {
                itemBuilder.setWhenFalse(AgendaItemDefinition.Builder.create(null, agenda.getId()));
                itemBuilder = itemBuilder.getWhenFalse();
            }
        }
        //Update the root item.
        AgendaItemDefinition updateItem = rootItemBuilder.build();
        this.getRuleManagementService().updateAgendaItem(updateItem);


        //For each new rule created
        for(FERuleEditor feRuleEditor : newRules) {
            //Retrieve saved rule to get the id needed for rule actions
            RuleEditor ruleEditor = new RuleEditor(this.getRuleManagementService().getRuleByNameAndNamespace(feRuleEditor.getName(), nameSpace));
            //If rule actions empty, build actions from rule fields and add to empty actions list
            if(ruleEditor.getActions().isEmpty()) {
                ActionDefinition actionDefinition = finActions(feRuleEditor, ruleEditor.getId());
                ArrayList<ActionDefinition> actions = (ArrayList<ActionDefinition>) ruleEditor.getActions();
                actions.add(actionDefinition);
            }
            //Build new rule definition of updated rule with actions
            RuleDefinition.Builder ruleBuilder = RuleDefinition.Builder.create(ruleEditor);
            //Save updated rule
            this.getRuleManagementService().updateRule(ruleBuilder.build());
        }

        return updateItem;
    }

    @Override
    public RuleDefinition.Builder finRule(RuleEditor rule, String rulePrefix, String namespace) {
        // handle saving new parameterized terms
        if (rule.getPropositionEditor() != null) {
            this.finPropositionEditor(rule.getPropositionEditor());
        }

        if (rule.getNamespace() == null) {
            rule.setNamespace(namespace);
        }

        if (rule.getName() == null && rule.getName().isEmpty()) {
            rule.setName(rulePrefix + rule.getRuleTypeInfo().getId() + ":na");
        }

        return RuleDefinition.Builder.create(rule);
    }

    /**
     * Method that populates rule actions from rule parameters
     *
     * @param feRuleEditor
     * @param ruleId
     * @return new action definition with populated parameters
     */
    public ActionDefinition finActions(FERuleEditor feRuleEditor, String ruleId) {

        ActionEditor actionEditor = new ActionEditor();

        //Set actionEditor required fiels from rule
        actionEditor.setRuleId(ruleId);
        actionEditor.setDescription("Day " + feRuleEditor.getDay());
        actionEditor.setNamespace(StudentIdentityConstants.KS_NAMESPACE_CD);
        actionEditor.setName("day" + feRuleEditor.getDay() + feRuleEditor.getTimePeriodToDisplay());
        actionEditor.setTypeId(getActionTypeId());
        actionEditor.setSequenceNumber(1);

        //Populate dynamic attributes
        Map<String, String> attributes = new HashMap<String, String>();
        attributes.put(KSKRMSServiceConstants.ACTION_PARAMETER_TYPE_RDL_STARTTIME, parseTime(feRuleEditor.getStartTime(), feRuleEditor.getStartTimeAMPM()));
        attributes.put(KSKRMSServiceConstants.ACTION_PARAMETER_TYPE_RDL_DAY, feRuleEditor.getDay());
        attributes.put(KSKRMSServiceConstants.ACTION_PARAMETER_TYPE_RDL_ENDTIME, parseTime(feRuleEditor.getEndTime(), feRuleEditor.getEndTimeAMPM()));
        if(feRuleEditor.getBuilding().getId() != null) {
            attributes.put(KSKRMSServiceConstants.ACTION_PARAMETER_TYPE_RDL_FACILITY, feRuleEditor.getBuilding().getId());
        }
        if(feRuleEditor.getRoom().getId() != null) {
            attributes.put(KSKRMSServiceConstants.ACTION_PARAMETER_TYPE_RDL_ROOM, feRuleEditor.getRoom().getId());
        }

        actionEditor.setAttributes(attributes);

        ActionDefinition.Builder actionBuilder = ActionDefinition.Builder.create(actionEditor);

        return actionBuilder.build();
    }

    /**
     * Parses Date into Long
     *
     * @param time
     * @param format
     * @return date in long value
     */
    protected String parseTime(String time, String format) {
        Date d = new Date();
        d.setHours(Integer.parseInt(StringUtils.substring(time, 0, 2)) + (format.equals("PM") ? 12 : 0));
        d.setMinutes(Integer.parseInt(StringUtils.substring(time, 3, 5)));
        d.setSeconds(0);

        long parsedTime = d.getTime();

        return Long.toString(parsedTime);
    }

    protected String getActionTypeId() {
        if (actionTypeId == null) {
            actionTypeId = this.getKrmsTypeRepositoryService().getTypeByName(StudentIdentityConstants.KS_NAMESPACE_CD, KSKRMSServiceConstants.ACTION_TYPE_REQUESTED_DELIVERY_LOGISTIC).getId();
        }
        return actionTypeId;
    }

    protected String getUsageId() {
        if (usageId == null) {
            usageId = this.getRuleManagementService().getNaturalLanguageUsageByNameAndNamespace(KSKRMSServiceConstants.KRMS_NL_TYPE_CATALOG,
                    PermissionServiceConstants.KS_SYS_NAMESPACE).getId();
        }
        return usageId;
    }

    public RoomService getRoomService() {
        if (roomService == null) {
            roomService = CourseOfferingResourceLoader.loadRoomService();
        }
        return roomService;
    }

    public TypeService getTypeService() {
        if (typeService == null) {
            typeService = CourseOfferingResourceLoader.loadTypeService();
        }
        return typeService;
    }

}
