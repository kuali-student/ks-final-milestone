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
import org.apache.ojb.broker.OptimisticLockException;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.krad.maintenance.MaintenanceDocument;
import org.kuali.rice.krms.api.repository.NaturalLanguage;
import org.kuali.rice.krms.api.repository.agenda.AgendaDefinition;
import org.kuali.rice.krms.api.repository.agenda.AgendaItemDefinition;
import org.kuali.rice.krms.api.repository.context.ContextDefinition;
import org.kuali.rice.krms.api.repository.reference.ReferenceObjectBinding;
import org.kuali.rice.krms.api.repository.rule.RuleDefinition;
import org.kuali.rice.krms.dto.ActionEditor;
import org.kuali.rice.krms.dto.AgendaEditor;
import org.kuali.rice.krms.dto.AgendaTypeInfo;
import org.kuali.rice.krms.dto.RuleEditor;
import org.kuali.rice.krms.dto.RuleManagementWrapper;
import org.kuali.rice.krms.dto.RuleTypeInfo;
import org.kuali.rice.krms.service.impl.RuleEditorMaintainableImpl;
import org.kuali.rice.krms.util.AlphaIterator;
import org.kuali.student.common.collection.KSCollectionUtils;
import org.kuali.student.common.krms.exceptions.KRMSOptimisticLockingException;
import org.kuali.student.enrollment.class1.krms.dto.FEAgendaEditor;
import org.kuali.student.enrollment.class1.krms.dto.FERuleEditor;
import org.kuali.student.enrollment.class1.krms.dto.FERuleManagementWrapper;
import org.kuali.student.enrollment.class2.courseoffering.service.decorators.PermissionServiceConstants;
import org.kuali.student.enrollment.class2.courseoffering.util.CourseOfferingResourceLoader;
import org.kuali.student.enrollment.class2.examoffering.service.facade.ExamOfferingServiceFacade;
import org.kuali.student.r1.common.rice.StudentIdentityConstants;
import org.kuali.student.r2.common.dto.TimeOfDayInfo;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.util.TimeOfDayHelper;
import org.kuali.student.r2.common.util.date.DateFormatters;
import org.kuali.student.r2.core.class1.type.dto.TypeInfo;
import org.kuali.student.r2.core.class1.type.service.TypeService;
import org.kuali.student.r2.core.constants.KSKRMSServiceConstants;
import org.kuali.student.r2.core.constants.TypeServiceConstants;
import org.kuali.student.r2.core.room.service.RoomService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springmodules.orm.ojb.OjbOperationException;

import javax.xml.namespace.QName;

import java.text.ParseException;
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

    private static final Logger LOG = LoggerFactory.getLogger(FERuleEditorMaintainableImpl.class);

    private transient RoomService roomService;
    private transient TypeService typeService;
    private transient ExamOfferingServiceFacade examOfferingServiceFacade;

    private String usageId;
    private String rdlActionTypeId;

    private AlphaIterator alphaIterator = new AlphaIterator(StringUtils.EMPTY);

    @Override
    public Object retrieveObjectForEditOrCopy(MaintenanceDocument document, Map<String, String> dataObjectKeys) {

        FERuleManagementWrapper dataObject = new FERuleManagementWrapper();

        String typeKey = dataObjectKeys.get("refObjectId");
        setupDataObject(dataObject, typeKey);

        return dataObject;
    }

    public void setupDataObject(FERuleManagementWrapper dataObject, String typeKey) {
        dataObject.setNamespace(KSKRMSServiceConstants.NAMESPACE_CODE);
        dataObject.setRefDiscriminatorType(TypeServiceConstants.REF_OBJECT_URI_TYPE);

        dataObject.setRefObjectId(typeKey);
        dataObject.setAgendas(this.getAgendasForRef(dataObject.getRefDiscriminatorType(), typeKey));

        try {
            dataObject.setType(this.getTypeService().getType(typeKey, this.createContextInfo()));
        } catch (Exception e) {
            throw new RuntimeException("Could not retrieve type for " + typeKey, e);
        }

        try {
            dataObject.setLocation(this.getExamOfferingServiceFacade().isSetLocation());
        } catch (Exception e) {
            throw new RuntimeException("Could not retrieve location setting.", e);
        }

        try {
            String ownerTermType = this.getOwnerTypeForAgendas(dataObject.getAgendas());
            if (!typeKey.equals(ownerTermType)) {
                dataObject.setTermToUse(ownerTermType);
            } else {
                for (AgendaEditor agendaEditor : dataObject.getAgendas()) {
                    if (agendaEditor.getId() != null) {
                        List<ReferenceObjectBinding> bindings = this.getRuleManagementService().findReferenceObjectBindingsByKrmsObject(agendaEditor.getId());
                        for (ReferenceObjectBinding binding : bindings) {
                            if (!binding.getReferenceObjectId().equals(typeKey)) {
                                TypeInfo type = this.getTypeService().getType(binding.getReferenceObjectId(), this.createContextInfo());
                                dataObject.getLinkedTermTypes().add(type.getName());
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Could not retrieve linked term types.", e);
        }
    }

    private String getOwnerTypeForAgendas(List<AgendaEditor> agendas) {
        for (AgendaEditor agenda : agendas) {
            if (agenda.getAttributes().containsKey(KSKRMSServiceConstants.AGENDA_ATTRIBUTE_FINAL_EXAM_OWNER_TERM_TYPE)) {
                return agenda.getAttributes().get(KSKRMSServiceConstants.AGENDA_ATTRIBUTE_FINAL_EXAM_OWNER_TERM_TYPE);
            }
        }
        return null;
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
        LOG.info("Retrieving reference object binding for refobjectid: {}", refObjectId);
        List<ReferenceObjectBinding> refObjectsBindings = this.getRuleManagementService().findReferenceObjectBindingsByReferenceObject(discriminatorType, refObjectId);
        for (ReferenceObjectBinding referenceObjectBinding : refObjectsBindings) {
            LOG.info("Retrieved reference object binding with id: {}", referenceObjectBinding);
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
                Map<String, String> attributes = new HashMap<String, String>();
                attributes.put(KSKRMSServiceConstants.AGENDA_ATTRIBUTE_FINAL_EXAM_OWNER_TERM_TYPE, refObjectId);
                agenda.setAttributes(attributes);
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
        LOG.info("Retrieved agenda for id: {}", agendaId);
        return new FEAgendaEditor(agenda);
    }

    public Map<String, RuleEditor> getRulesForAgendas(AgendaEditor agenda) {

        //Get all existing rules.
        if (agenda.getId() != null) {
            AgendaItemDefinition firstItem = this.getRuleManagementService().getAgendaItem(agenda.getFirstItemId());
            FEAgendaEditor feAgenda;
            if (agenda instanceof FEAgendaEditor) {
                feAgenda = (FEAgendaEditor) agenda;

                List<String> ruleIds = new ArrayList<String>();
                List<RuleEditor> rules = getRuleEditorsFromTree(firstItem, true);
                for (RuleEditor rule : rules) {
                    ruleIds.add(rule.getId());
                }

                List<NaturalLanguage> nlList = getRuleManagementService().translateNaturalLanguageForObjects(this.getUsageId(), "rule", ruleIds, "en");

                try {
                    RuleTypeInfo ruleType = KSCollectionUtils.getRequiredZeroElement(agenda.getAgendaTypeInfo().getRuleTypes());
                    for (RuleEditor rule : rules) {
                        rule.setRuleTypeInfo(ruleType);
                        rule.setKey((String) alphaIterator.next());
                        for (NaturalLanguage nl : nlList) {
                            if (nl.getKrmsObjectId().equals(rule.getId())) {
                                String description = nl.getNaturalLanguage();
                                int index = description.indexOf(": ");
                                rule.setDescription(description.substring(index + 1));
                                break;
                            }
                        }
                    }
                    feAgenda.setRules(rules);

                } catch (OperationFailedException e) {
                    throw new RuntimeException("Unable to retrieve single ruletype from agenda.", e);
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
            ActionEditor action = ruleEditor.getActionForType(this.getRdlActionTypeId());
            Map<String, String> attributes = action.getAttributes();
            if (attributes.containsKey(KSKRMSServiceConstants.ACTION_PARAMETER_TYPE_RDL_DAY)) {
                ruleEditor.setDay(attributes.get(KSKRMSServiceConstants.ACTION_PARAMETER_TYPE_RDL_DAY));
            }
            String startTime = this.getDateValueForKey(action.getAttributes(), KSKRMSServiceConstants.ACTION_PARAMETER_TYPE_RDL_STARTTIME);
            if (startTime != null) {
                ruleEditor.setStartTime(org.apache.commons.lang.StringUtils.substringBefore(startTime, " "));
                ruleEditor.setStartTimeAMPM(org.apache.commons.lang.StringUtils.substringAfter(startTime, " "));
            }
            String endTime = this.getDateValueForKey(action.getAttributes(), KSKRMSServiceConstants.ACTION_PARAMETER_TYPE_RDL_ENDTIME);
            if (endTime != null) {
                ruleEditor.setEndTime(org.apache.commons.lang.StringUtils.substringBefore(endTime, " "));
                ruleEditor.setEndTimeAMPM(org.apache.commons.lang.StringUtils.substringAfter(endTime, " "));
            }
            if (attributes.containsKey(KSKRMSServiceConstants.ACTION_PARAMETER_TYPE_RDL_FACILITY)) {
                String buildingId = attributes.get(KSKRMSServiceConstants.ACTION_PARAMETER_TYPE_RDL_FACILITY);
                if (buildingId != null && !buildingId.isEmpty()) {
                    try {
                        ruleEditor.setBuilding(this.getRoomService().getBuilding(buildingId, this.createContextInfo()));
                    } catch (Exception e) {
                        throw new RuntimeException("Could not retrieve building for " + buildingId, e);
                    }
                }
            }
            if (attributes.containsKey(KSKRMSServiceConstants.ACTION_PARAMETER_TYPE_RDL_ROOM)) {
                String roomId = attributes.get(KSKRMSServiceConstants.ACTION_PARAMETER_TYPE_RDL_ROOM);
                if (roomId != null && !roomId.isEmpty()) {
                    try {
                        ruleEditor.setRoom(this.getRoomService().getRoom(roomId, this.createContextInfo()));
                    } catch (Exception e) {
                        throw new RuntimeException("Could not retrieve room for " + roomId, e);
                    }
                }
            }
            if (attributes.containsKey(KSKRMSServiceConstants.ACTION_PARAMETER_TYPE_RDL_TBA)) {
                ruleEditor.setTba(Boolean.parseBoolean(attributes.get(KSKRMSServiceConstants.ACTION_PARAMETER_TYPE_RDL_TBA)));
            }

            //Add rule to list on agenda
            rules.add(ruleEditor);
        }

        if (agendaItem.getWhenFalse() != null) {
            rules.addAll(getRuleEditorsFromTree(agendaItem.getWhenFalse(), initProps));
        }

        return rules;
    }

    private String getDateValueForKey(Map<String, String> attributes, String key) {

        if (attributes.containsKey(key)) {
            String value = attributes.get(key);

            if ((value == null) || (value.isEmpty())) {
                return null;
            }

            TimeOfDayInfo start = TimeOfDayHelper.setMillis(Long.parseLong(value));
            return TimeOfDayHelper.makeFormattedTimeForAOSchedules(start);
        }

        return null;
    }

    @Override
    public void saveDataObject() {
        FERuleManagementWrapper ruleWrapper = (FERuleManagementWrapper) getDataObject();
        if (ruleWrapper.getTermToUse().equals("na")) {
            super.saveDataObject();
        } else {

            try {

                // delete current agenda associated with this type.
                for (AgendaEditor agenda : ruleWrapper.getAgendas()) {
                    if (agenda.getId() == null) {
                        continue;
                    }
                    if (agenda.getAttributes().containsKey(KSKRMSServiceConstants.AGENDA_ATTRIBUTE_FINAL_EXAM_OWNER_TERM_TYPE)) {
                        String ownerTermType = agenda.getAttributes().get(KSKRMSServiceConstants.AGENDA_ATTRIBUTE_FINAL_EXAM_OWNER_TERM_TYPE);
                        if (ruleWrapper.getRefObjectId().equals(ownerTermType)) {
                            if (agenda.getFirstItemId() != null) {
                                this.getRuleManagementService().deleteAgendaItem(agenda.getFirstItemId());
                            }
                            this.getRuleManagementService().deleteAgenda(agenda.getId());
                        }
                    }
                }

                //Retrieve current ref object bindings.
                List<ReferenceObjectBinding> refObjectsBindingsForType = this.getRuleManagementService().findReferenceObjectBindingsByReferenceObject(ruleWrapper.getRefDiscriminatorType(), ruleWrapper.getRefObjectId());
                List<ReferenceObjectBinding> refObjectsBindingsForOwner = this.getRuleManagementService().findReferenceObjectBindingsByReferenceObject(ruleWrapper.getRefDiscriminatorType(), ruleWrapper.getTermToUse());
                for (ReferenceObjectBinding ownerBinding : refObjectsBindingsForOwner) {
                    ReferenceObjectBinding binding = this.getBindingForObjectId(ownerBinding.getKrmsObjectId(), refObjectsBindingsForType);
                    if (binding == null) {
                        //Create the reference object binding only on create agenda, no need to update.
                        ReferenceObjectBinding.Builder refBuilder = ReferenceObjectBinding.Builder.create("Agenda",
                                ownerBinding.getKrmsObjectId(), ruleWrapper.getNamespace(), ruleWrapper.getRefDiscriminatorType(), ruleWrapper.getRefObjectId());
                        this.getRuleManagementService().createReferenceObjectBinding(refBuilder.build());
                    }
                }

                //Delete remaining ref object binding.
                for (ReferenceObjectBinding typeBinding : refObjectsBindingsForType) {
                    this.getRuleManagementService().deleteReferenceObjectBinding(typeBinding.getId());
                }

            } catch (OjbOperationException e) {
                //OptimisticLockException
                if (e.getCause() instanceof OptimisticLockException) {
                    throw new KRMSOptimisticLockingException("Optimistic locking exception", e);
                } else {
                    throw e;
                }
            }
        }

    }

    private ReferenceObjectBinding getBindingForObjectId(String objectId, List<ReferenceObjectBinding> bindings) {
        for (ReferenceObjectBinding binding : bindings) {
            if (binding.getKrmsObjectId().equals(objectId)) {
                bindings.remove(binding);
                return binding;
            }
        }
        return null;
    }

    public AgendaItemDefinition maintainAgendaItems(AgendaEditor agenda, String namePrefix, String nameSpace) {

        Queue<RuleDefinition.Builder> rules = new LinkedList<RuleDefinition.Builder>();
        FEAgendaEditor feAgenda;
        if (agenda instanceof FEAgendaEditor) {
            feAgenda = (FEAgendaEditor) agenda;
            for (RuleEditor rule : feAgenda.getRules()) {
                if (!rule.isDummy()) {
                    rules.add(this.finRule(rule, namePrefix, nameSpace));
                }
            }

            AgendaItemDefinition.Builder rootItemBuilder = manageFirstItem(agenda);

            AgendaItemDefinition.Builder itemToDelete = null;
            AgendaItemDefinition.Builder itemBuilder = rootItemBuilder;
            while (rules.peek() != null) {
                itemBuilder.setRule(rules.poll());
                itemBuilder.setRuleId(itemBuilder.getRule().getId());
                if (rules.peek() != null) {
                    if(itemBuilder.getWhenFalse()==null){
                        itemBuilder.setWhenFalse(AgendaItemDefinition.Builder.create(null, agenda.getId()));
                    }
                    itemBuilder = itemBuilder.getWhenFalse();
                } else {
                    itemToDelete = itemBuilder.getWhenFalse();
                    itemBuilder.setWhenFalse(null);
                }
            }

            return manageAgendaItems(agenda, rootItemBuilder, itemToDelete);
        }

        return null;
    }

    @Override
    public RuleDefinition.Builder finRule(RuleEditor rule, String rulePrefix, String namespace) {
        // handle saving new parameterized terms
        if (rule.getPropositionEditor() != null) {
            this.onSubmit(rule.getPropositionEditor());
        }

        if (rule.getNamespace() == null) {
            rule.setNamespace(namespace);
        }

        //Check if someone else has not created a rule while this one was created.
        if (rule.getId() == null) {
            if (this.getRuleManagementService().getRuleByNameAndNamespace(rule.getName(), rule.getNamespace()) != null) {
                throw new KRMSOptimisticLockingException();
            }
        }

        //Setup the actions
        finActions(rule);

        return RuleDefinition.Builder.create(rule);
    }

    /**
     * Method that populates rule actions from rule parameters
     *
     * @param rule
     * @return new action definition with populated parameters
     */
    public void finActions(RuleEditor rule) {
        //nothing for now.
    }

    public String getNextRuleKey() {
        return (String) alphaIterator.next();
    }

    protected String getRdlActionTypeId() {
        if (rdlActionTypeId == null) {
            rdlActionTypeId = this.getKrmsTypeRepositoryService().getTypeByName(StudentIdentityConstants.KS_NAMESPACE_CD, KSKRMSServiceConstants.ACTION_TYPE_REQUESTED_DELIVERY_LOGISTIC).getId();
        }
        return rdlActionTypeId;
    }

    protected String getUsageId() {
        if (usageId == null) {
            usageId = this.getRuleManagementService().getNaturalLanguageUsageByNameAndNamespace(KSKRMSServiceConstants.KRMS_NL_MATRIX,
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

    public ExamOfferingServiceFacade getExamOfferingServiceFacade() {
        if (examOfferingServiceFacade == null) {
            examOfferingServiceFacade = (ExamOfferingServiceFacade) GlobalResourceLoader.getService(new QName("http://student.kuali.org/wsdl/examOfferingServiceFacade", "examOfferingServiceFacade"));
        }
        return examOfferingServiceFacade;
    }

}
