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

import org.kuali.rice.krad.maintenance.MaintenanceDocument;
import org.kuali.rice.krms.api.repository.agenda.AgendaDefinition;
import org.kuali.rice.krms.api.repository.agenda.AgendaItemDefinition;
import org.kuali.rice.krms.api.repository.reference.ReferenceObjectBinding;
import org.kuali.rice.krms.api.repository.rule.RuleDefinition;
import org.kuali.rice.krms.dto.ActionEditor;
import org.kuali.rice.krms.dto.AgendaEditor;
import org.kuali.rice.krms.dto.AgendaTypeInfo;
import org.kuali.rice.krms.dto.RuleEditor;
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

        if (agendaItem.getWhenTrue() != null) {
            rules.addAll(getRuleEditorsFromTree(agendaItem.getWhenTrue(), initProps));
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
        firstItemBuilder.setWhenTrue(null);
        firstItemBuilder.setWhenTrueId(null);
        firstItemBuilder.setVersionNumber(firstItem.getVersionNumber());
        this.getRuleManagementService().updateAgendaItem(firstItemBuilder.build());

        //Delete current agenda items to rebuild the tree.
        if (firstItem.getWhenTrue() != null) {
            this.deleteAgendaItems(firstItem.getWhenTrue());
        }

        //Delete rules
        for (RuleEditor deletedRule : agenda.getDeletedRules()) {
            this.getRuleManagementService().deleteRule(deletedRule.getId());
        }

        AgendaItemDefinition rootItem = this.getRuleManagementService().getAgendaItem(agenda.getFirstItemId());
        AgendaItemDefinition.Builder rootItemBuilder = AgendaItemDefinition.Builder.create(rootItem);
        AgendaItemDefinition.Builder itemBuilder = rootItemBuilder;
        while (rules.peek() != null) {
            itemBuilder.setRule(this.finRule(rules.poll(), namePrefix, nameSpace));
            itemBuilder.setRuleId(itemBuilder.getRule().getId());
            if (rules.peek() != null) {
                itemBuilder.setWhenTrue(AgendaItemDefinition.Builder.create(null, agenda.getId()));
                itemBuilder = itemBuilder.getWhenTrue();
            }
        }
        //Update the root item.
        AgendaItemDefinition updateItem = rootItemBuilder.build();
        this.getRuleManagementService().updateAgendaItem(updateItem);

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
        if (rule.getRuleTypeInfo() != null) {
            rule.setName(rulePrefix + rule.getRuleTypeInfo().getId() + ":1");
        }

        return RuleDefinition.Builder.create(rule);
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
