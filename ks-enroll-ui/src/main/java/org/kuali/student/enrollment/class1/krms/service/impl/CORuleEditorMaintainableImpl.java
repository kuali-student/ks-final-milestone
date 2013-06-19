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

import org.kuali.rice.core.api.criteria.PredicateFactory;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.krad.maintenance.MaintenanceDocument;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.util.KRADConstants;
import org.kuali.rice.krms.api.repository.agenda.AgendaDefinition;
import org.kuali.rice.krms.api.repository.agenda.AgendaItemDefinition;
import org.kuali.rice.krms.api.repository.agenda.AgendaTreeDefinition;
import org.kuali.rice.krms.api.repository.agenda.AgendaTreeEntryDefinitionContract;
import org.kuali.rice.krms.api.repository.agenda.AgendaTreeRuleEntry;
import org.kuali.rice.krms.api.repository.reference.ReferenceObjectBinding;
import org.kuali.rice.krms.api.repository.rule.RuleDefinition;
import org.kuali.rice.krms.dto.AgendaEditor;
import org.kuali.rice.krms.dto.PropositionEditor;
import org.kuali.rice.krms.dto.RuleEditor;
import org.kuali.rice.krms.service.impl.RuleEditorMaintainableImpl;
import org.kuali.rice.krms.tree.RuleCompareTreeBuilder;
import org.kuali.rice.krms.tree.RuleViewTreeBuilder;
import org.kuali.rice.krms.util.NaturalLanguageHelper;
import org.kuali.student.enrollment.class1.krms.dto.CORuleManagementWrapper;
import org.kuali.student.enrollment.class1.krms.dto.EnrolAgendaEditor;
import org.kuali.student.enrollment.class1.krms.dto.EnrolRuleEditor;
import org.kuali.student.enrollment.class1.krms.dto.EnrolRuleManagementWrapper;
import org.kuali.student.enrollment.class1.krms.tree.EnrolRuleViewTreeBuilder;
import org.kuali.student.enrollment.class2.courseoffering.dto.CourseOfferingContextBar;
import org.kuali.student.enrollment.class2.courseoffering.util.CourseOfferingConstants;
import org.kuali.student.enrollment.class2.courseoffering.util.CourseOfferingResourceLoader;
import org.kuali.student.enrollment.class2.courseoffering.util.ManageSocConstants;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;
import org.kuali.student.enrollment.courseofferingset.dto.SocInfo;
import org.kuali.student.enrollment.courseofferingset.service.CourseOfferingSetService;
import org.kuali.student.krms.util.KSKRMSConstants;
import org.kuali.student.r2.common.util.ContextUtils;
import org.kuali.student.r2.common.util.constants.CourseOfferingSetServiceConstants;
import org.kuali.student.r2.core.acal.dto.TermInfo;
import org.kuali.student.r2.core.acal.service.AcademicCalendarService;
import org.kuali.student.r2.core.atp.dto.AtpInfo;
import org.kuali.student.r2.core.atp.service.AtpService;
import org.kuali.student.r2.core.class1.state.service.StateService;
import org.kuali.student.r2.core.constants.AcademicCalendarServiceConstants;
import org.kuali.student.r2.core.constants.StateServiceConstants;
import org.kuali.student.r2.lum.clu.service.CluService;
import org.kuali.student.r2.lum.course.service.CourseService;
import org.kuali.student.r2.lum.util.constants.CluServiceConstants;

import javax.xml.namespace.QName;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Overridden class to handle CO specific maintainable functionality.
 *
 * @author Kuali Student Team
 */
public class CORuleEditorMaintainableImpl extends RuleEditorMaintainableImpl {

    private transient CluService cluService;
    private transient AtpService atpService;
    private transient CourseOfferingService courseOfferingService;

    private transient AcademicCalendarService acalService;
    private transient CourseOfferingSetService socService;
    private transient StateService stateService;

    private transient RuleViewTreeBuilder viewTreeBuilder;
    private transient NaturalLanguageHelper nlHelper;

    @Override
    public Object retrieveObjectForEditOrCopy(MaintenanceDocument document, Map<String, String> dataObjectKeys) {

        CORuleManagementWrapper dataObject = new CORuleManagementWrapper();

        dataObject.setNamespace(KSKRMSConstants.KS_SYS_NAMESPACE);
        dataObject.setRefDiscriminatorType("kuali.lui.type.course.offering");

        String coId = dataObjectKeys.get("refObjectId");
        dataObject.setRefObjectId(coId);

        dataObject.setAgendas(this.getAgendasForRef(dataObject.getRefDiscriminatorType(), coId));

        //Retrieve the Clu information
        CourseOfferingInfo courseOffering = null;
        if (coId != null) {
            try {
                courseOffering = this.getCourseOfferingService().getCourseOffering(coId, ContextUtils.createDefaultContextInfo());
            } catch (Exception e) {
                throw new RuntimeException("Could not retrieve course offering for " + coId);
            }
        }

        //Populate Clu Identification Information
        if (courseOffering != null) {
            //Get the atp code.
            StringBuilder atpCode = new StringBuilder();
            AtpInfo atp = null;
            try {
                atp = this.getAtpService().getAtp(courseOffering.getTermId(), ContextUtils.createDefaultContextInfo());
                atpCode.append(atp.getCode());
                atpCode.append(" - ");
            } catch (Exception e) {
                throw new RuntimeException("Could not retrieve atp for " + courseOffering.getTermId());
            }

            //Set the name prefix used for agenda and rule names.
            dataObject.setNamePrefix(atpCode.toString() + courseOffering.getCourseOfferingCode());

            //Set the description and atp used on the screen.
            dataObject.setCluDescription(courseOffering.getCourseOfferingCode());

            try {
                populateContextBar(dataObject, atp.getCode());
            } catch (Exception e) {
                throw new RuntimeException("Could not populate context bar.");
            }
        }

        dataObject.setCompareTree(RuleCompareTreeBuilder.initCompareTree());

        return dataObject;
    }

    @Override
    public String getViewTypeName() {
        return "kuali.krms.agenda.type.course";
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
        return new EnrolAgendaEditor(agenda);
    }

    @Override
    protected List<RuleEditor> getRuleEditorsFromTree(List<AgendaTreeEntryDefinitionContract> agendaTreeEntries, boolean initProp) {

        List<RuleEditor> rules = new ArrayList<RuleEditor>();
        for (AgendaTreeEntryDefinitionContract treeEntry : agendaTreeEntries) {
            if (treeEntry instanceof AgendaTreeRuleEntry) {
                AgendaTreeRuleEntry treeRuleEntry = (AgendaTreeRuleEntry) treeEntry;
                AgendaItemDefinition agendaItem = this.getRuleManagementService().getAgendaItem(treeEntry.getAgendaItemId());

                if (agendaItem.getRule() != null) {

                    //Build the ruleEditor
                    RuleEditor ruleEditor = new EnrolRuleEditor(agendaItem.getRule());

                    //Initialize the Proposition tree
                    if(initProp){
                        this.initPropositionEditor(ruleEditor.getPropositionEditor());
                        this.getNLHelper().setNaturalLanguageTreeForUsage(ruleEditor.getPropositionEditor(), this.getViewTreeBuilder().getNaturalLanguageUsageKey());
                        ruleEditor.setViewTree(this.getViewTreeBuilder().buildTree(ruleEditor));
                    }

                    //Add rule to list on agenda
                    rules.add(ruleEditor);
                }

                if (treeRuleEntry.getIfTrue() != null) {
                    rules.addAll(getRuleEditorsFromTree(treeRuleEntry.getIfTrue().getEntries(), initProp));
                }
            }

            // TODO: Check for sub agendas, not required for course offering.
        }

        return rules;
    }

    /**
     * Return the clu id from the canonical course that is linked to the given course offering id.
     *
     * @param refObjectId - the course offering id.
     * @return
     * @throws Exception
     */
    @Override
    public List<ReferenceObjectBinding> getParentRefOjbects(String refObjectId) {
        CourseOfferingInfo courseOffering = null;
        try {
            courseOffering = this.getCourseOfferingService().getCourseOffering(refObjectId, ContextUtils.createDefaultContextInfo());
        } catch (Exception e) {
            throw new RuntimeException("Could not retrieve course offering for " + refObjectId);
        }
        return this.getRuleManagementService().findReferenceObjectBindingsByReferenceObject("kuali.lu.type.CreditCourse", courseOffering.getCourseId());
    }

    /**
     *
     *
     * @param form
     * @param atpCode
     * @throws Exception
     */
    public void populateContextBar(CORuleManagementWrapper form, String atpCode) throws Exception {
        String socStateKey = null;

        QueryByCriteria.Builder qbcBuilder = QueryByCriteria.Builder.create();
        qbcBuilder.setPredicates(PredicateFactory.equal("atpCode", atpCode));

        QueryByCriteria criteria = qbcBuilder.build();

        List<TermInfo> terms = getAcalService().searchForTerms(criteria, createContextInfo());

        if (terms.isEmpty()) {
            GlobalVariables.getMessageMap().putError("termCode", CourseOfferingConstants.COURSEOFFERING_MSG_ERROR_NO_TERM_IS_FOUND, atpCode);
        } else if (terms.size() > 1) {
            GlobalVariables.getMessageMap().putError("termCode", CourseOfferingConstants.COURSEOFFERING_MSG_ERROR_FOUND_MORE_THAN_ONE_TERM, atpCode);
        } else {
            //Checking soc
            List<String> socIds;
            try {
                socIds = getSocService().getSocIdsByTerm(terms.get(0).getId(), createContextInfo());
            } catch (Exception e) {
                throw convertServiceExceptionsToUI(e);
            }

            if (socIds.isEmpty()) {
                GlobalVariables.getMessageMap().putError(KRADConstants.GLOBAL_ERRORS, ManageSocConstants.MessageKeys.ERROR_SOC_NOT_EXISTS);
            } else {
                socStateKey = getSocStateKey(socIds);
            }
        }

        form.setContextBar( CourseOfferingContextBar.NEW_INSTANCE(terms.get(0), socStateKey,
                getStateService(), getAcalService(), createContextInfo()) );
    }

    private String getSocStateKey(List<String> socIds) throws Exception {
        if (socIds != null && !socIds.isEmpty()) {
            List<SocInfo> targetSocs = this.getSocService().getSocsByIds(socIds, createContextInfo());
            for (SocInfo soc : targetSocs) {
                if (soc.getTypeKey().equals(CourseOfferingSetServiceConstants.MAIN_SOC_TYPE_KEY)) {
                    return soc.getStateKey();
                }
            }
        }
        return null;
    }

    protected RuleViewTreeBuilder getViewTreeBuilder(){
        if(this.viewTreeBuilder == null){
            viewTreeBuilder = new EnrolRuleViewTreeBuilder();
            viewTreeBuilder.setRuleManagementService(this.getRuleManagementService());
        }
        return viewTreeBuilder;
    }

    protected NaturalLanguageHelper getNLHelper(){
        if(this.nlHelper == null){
            nlHelper = new NaturalLanguageHelper();
            nlHelper.setRuleManagementService(this.getRuleManagementService());
        }
        return nlHelper;
    }

    protected CluService getCluService() {
        if (cluService == null) {
            cluService = CourseOfferingResourceLoader.loadCluService();
        }
        return cluService;
    }

    private CourseOfferingService getCourseOfferingService() {
        if (courseOfferingService == null) {
            courseOfferingService = CourseOfferingResourceLoader.loadCourseOfferingService();
        }
        return courseOfferingService;
    }

    private AtpService getAtpService() {
        if (atpService == null) {
            atpService = CourseOfferingResourceLoader.loadAtpService();
        }
        return atpService;
    }

    private AcademicCalendarService getAcalService() {
        if (acalService == null) {
            acalService = (AcademicCalendarService) GlobalResourceLoader.getService(new QName(AcademicCalendarServiceConstants.NAMESPACE,
                    AcademicCalendarServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return acalService;
    }

    private CourseOfferingSetService getSocService() {
        // If it hasn't been set by Spring, then look it up by GlobalResourceLoader
        if (socService == null) {
            socService = (CourseOfferingSetService) GlobalResourceLoader.getService(new QName(CourseOfferingSetServiceConstants.NAMESPACE,
                    CourseOfferingSetServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return socService;
    }

    private StateService getStateService(){
        if (stateService == null){
            stateService = GlobalResourceLoader.getService(new QName(StateServiceConstants.NAMESPACE, StateServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return stateService;
    }
}
