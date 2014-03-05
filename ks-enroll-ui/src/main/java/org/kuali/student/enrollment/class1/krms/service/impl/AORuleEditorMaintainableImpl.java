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
import org.kuali.rice.krms.api.repository.reference.ReferenceObjectBinding;
import org.kuali.rice.krms.dto.AgendaEditor;
import org.kuali.rice.krms.dto.RuleEditor;
import org.kuali.rice.krms.service.impl.RuleEditorMaintainableImpl;
import org.kuali.rice.krms.tree.RuleCompareTreeBuilder;
import org.kuali.rice.krms.tree.RuleViewTreeBuilder;
import org.kuali.rice.krms.util.NaturalLanguageHelper;
import org.kuali.student.core.krms.tree.KSRuleViewTreeBuilder;
import org.kuali.student.enrollment.class1.krms.dto.AORuleEditor;
import org.kuali.student.enrollment.class1.krms.dto.AORuleManagementWrapper;
import org.kuali.student.enrollment.class2.courseoffering.dto.CourseOfferingContextBar;
import org.kuali.student.enrollment.class2.courseoffering.util.CourseOfferingConstants;
import org.kuali.student.enrollment.class2.courseoffering.util.CourseOfferingResourceLoader;
import org.kuali.student.enrollment.class2.courseoffering.util.ManageSocConstants;
import org.kuali.student.enrollment.class2.courseofferingset.util.CourseOfferingSetUtil;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;
import org.kuali.student.enrollment.courseofferingset.dto.SocInfo;
import org.kuali.student.enrollment.courseofferingset.service.CourseOfferingSetService;
import org.kuali.student.enrollment.lui.dto.LuiInfo;
import org.kuali.student.enrollment.lui.service.LuiService;
import org.kuali.student.lum.lu.ui.krms.dto.LUAgendaEditor;
import org.kuali.student.lum.lu.ui.krms.tree.LURuleViewTreeBuilder;
import org.kuali.student.common.util.security.ContextUtils;
import org.kuali.student.r2.common.util.constants.CourseOfferingServiceConstants;
import org.kuali.student.r2.common.util.constants.CourseOfferingSetServiceConstants;
import org.kuali.student.r2.core.acal.dto.TermInfo;
import org.kuali.student.r2.core.acal.service.AcademicCalendarService;
import org.kuali.student.r2.core.atp.dto.AtpInfo;
import org.kuali.student.r2.core.atp.service.AtpService;
import org.kuali.student.r2.core.class1.state.service.StateService;
import org.kuali.student.r2.core.constants.AcademicCalendarServiceConstants;
import org.kuali.student.r2.core.constants.KSKRMSServiceConstants;
import org.kuali.student.r2.core.constants.StateServiceConstants;
import org.kuali.student.r2.lum.util.constants.CourseServiceConstants;

import javax.xml.namespace.QName;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Overridden class to handle AO specific maintainable functionality.
 *
 * @author Kuali Student Team
 */
public class AORuleEditorMaintainableImpl extends RuleEditorMaintainableImpl {

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(CORuleEditorMaintainableImpl.class);

    private transient LuiService luiService;
    private transient AtpService atpService;
    private transient CourseOfferingService courseOfferingService;

    private transient AcademicCalendarService acalService;
    private transient CourseOfferingSetService socService;
    private transient StateService stateService;

    private transient KSRuleViewTreeBuilder viewTreeBuilder;
    private transient NaturalLanguageHelper nlHelper;

    @Override
    public Object retrieveObjectForEditOrCopy(MaintenanceDocument document, Map<String, String> dataObjectKeys) {

        AORuleManagementWrapper dataObject = new AORuleManagementWrapper();
        dataObject.setNamespace(KSKRMSServiceConstants.NAMESPACE_CODE);
        dataObject.setRefDiscriminatorType(CourseOfferingServiceConstants.REF_OBJECT_URI_ACTIVITY_OFFERING);

        String aoId = dataObjectKeys.get("refObjectId");
        try {
            //Retrieve the Reg Object information
            dataObject.setRefObjectId(aoId);
            LuiInfo aoaslui = this.getLuiService().getLui(aoId, ContextUtils.createDefaultContextInfo());

            //Set the description on the screen.
            dataObject.setAoDescription(aoaslui.getOfficialIdentifier().getCode());
        } catch (Exception e) {
            throw new RuntimeException("Could not retrieve activity offering for " + aoId);
        }

        String coId = dataObjectKeys.get("courseOfferingId");
        try {
            CourseOfferingInfo courseOffering = this.getCourseOfferingService().getCourseOffering(coId, ContextUtils.createDefaultContextInfo());

            dataObject.setCluDescription(courseOffering.getCourseOfferingCode());

            List<String> orgIds = courseOffering.getUnitsDeploymentOrgIds();
            if (orgIds != null && !orgIds.isEmpty()) {
                // managing multiple orgs
                String orgIdString = org.springframework.util.StringUtils.arrayToCommaDelimitedString(orgIds.toArray());
                if (orgIdString.length() > 0) {
                    dataObject.setAdminOrg(orgIdString);
                }
            }

            //Set the subjectArea for breadcrumb link
            dataObject.setCluSubjectCode(courseOffering.getSubjectArea());
            dataObject.setCluTermCode(courseOffering.getTermId());

            dataObject.setAgendas(this.getAgendasForRef(dataObject.getRefDiscriminatorType(), aoId, coId));
            this.setCluRules(dataObject.getAgendas(), courseOffering.getCourseId());

            try {
                //Get the atp code.
                AtpInfo atp = this.getAtpService().getAtp(courseOffering.getTermId(), ContextUtils.createDefaultContextInfo());
                //Set the term code for breadcrumb link
                dataObject.setCluTermCode(atp.getCode());
                populateContextBar(dataObject, atp.getCode());
            } catch (Exception e) {
                throw new RuntimeException("Could not populate context bar.");
            }
        } catch (Exception e) {
            throw new RuntimeException("Could not retrieve course offering for " + coId);
        }

        dataObject.setCompareTree(RuleCompareTreeBuilder.initCompareTree());

        return dataObject;
    }

    @Override
    public String getViewTypeName() {
        return KSKRMSServiceConstants.AGENDA_TYPE_COURSE;
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
        return new LUAgendaEditor(agenda);
    }

    protected RuleEditor createDummyRuleEditor(String ruleTypeId) {
        RuleEditor ruleEditor = new AORuleEditor();
        ruleEditor.setDummy(true);
        ruleEditor.setTypeId(ruleTypeId);
        return ruleEditor;
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
            RuleEditor ruleEditor = new AORuleEditor(agendaItem.getRule());

            //Initialize the Proposition tree
            if (initProps) {
                this.initPropositionEditor(ruleEditor.getPropositionEditor());
                ruleEditor.setViewTree(this.getViewTreeBuilder().buildTree(ruleEditor));
            }

            //Add rule to list on agenda
            rules.add(ruleEditor);
        }

        if (agendaItem.getWhenTrue() != null) {
            rules.addAll(getRuleEditorsFromTree(agendaItem.getWhenTrue(), initProps));
        }

        return rules;
    }

    /**
     * Return the clu id from the canonical course that is linked to the given Activity offering id.
     *
     * @param parentRefObjectId - the Activity offering id.
     * @return
     * @throws Exception
     */
    @Override
    public List<ReferenceObjectBinding> getParentRefOjbects(String parentRefObjectId) {
        return this.getRuleManagementService().findReferenceObjectBindingsByReferenceObject(CourseOfferingServiceConstants.REF_OBJECT_URI_COURSE_OFFERING, parentRefObjectId);
    }

    /**
     * Return the clu id from the canonical course that is linked to the given course offering id.
     *
     * @param courseId - the course offering id.
     * @return
     */
    public void setCluRules(List<AgendaEditor> agendas, String courseId) {

        List<ReferenceObjectBinding> cluRefObjects = this.getRuleManagementService().findReferenceObjectBindingsByReferenceObject(CourseServiceConstants.REF_OBJECT_URI_COURSE, courseId);
        for (ReferenceObjectBinding referenceObject : cluRefObjects) {
            AgendaDefinition cluAgenda = this.getRuleManagementService().getAgenda(referenceObject.getKrmsObjectId());
            AgendaItemDefinition firstItem = this.getRuleManagementService().getAgendaItem(cluAgenda.getFirstItemId());
            List<RuleEditor> cluRules = getRuleEditorsFromTree(firstItem, false);

            AgendaEditor aoAgenda = this.getAgendaForType(agendas, cluAgenda.getTypeId());
            for (RuleEditor cluRule : cluRules) {
                RuleEditor aoRule = getRuleForType(aoAgenda, cluRule.getTypeId());
                ((AORuleEditor) aoRule).setCluEditor(cluRule);
            }

        }

    }

    private AgendaEditor getAgendaForType(List<AgendaEditor> agendas, String typeId) {
        for (AgendaEditor agenda : agendas) {
            if (agenda.getTypeId().equals(typeId)) {
                return agenda;
            }
        }
        return null;
    }

    private RuleEditor getRuleForType(AgendaEditor agenda, String typeId) {
        for (RuleEditor rule : agenda.getRuleEditors().values()) {
            if (rule.getTypeId().equals(typeId)) {
                return rule;
            }
        }
        return null;
    }

    /**
     * @param form
     * @param atpCode
     * @throws Exception
     */
    public void populateContextBar(AORuleManagementWrapper form, String atpCode) {
        String socStateKey = null;

        QueryByCriteria.Builder qbcBuilder = QueryByCriteria.Builder.create();
        qbcBuilder.setPredicates(PredicateFactory.equal("atpCode", atpCode));

        QueryByCriteria criteria = qbcBuilder.build();

        try {
            List<TermInfo> terms = getAcalService().searchForTerms(criteria, createContextInfo());

            if (terms.isEmpty()) {
                GlobalVariables.getMessageMap().putError("termCode", CourseOfferingConstants.COURSEOFFERING_MSG_ERROR_NO_TERM_IS_FOUND, atpCode);
            } else if (terms.size() > 1) {
                GlobalVariables.getMessageMap().putError("termCode", CourseOfferingConstants.COURSEOFFERING_MSG_ERROR_FOUND_MORE_THAN_ONE_TERM, atpCode);
            } else {
                //Code Changed for JIRA-9075 - SONAR Critical issues - Use get(0) with caution - 5
                int firstTermInfo = 0;
                TermInfo term = terms.get(firstTermInfo);
                //Checking soc
                SocInfo soc = CourseOfferingSetUtil.getMainSocForTermId(term.getId(), createContextInfo());
                if (soc == null) {
                    GlobalVariables.getMessageMap().putError(KRADConstants.GLOBAL_ERRORS, ManageSocConstants.MessageKeys.ERROR_SOC_NOT_EXISTS);
                } else {
                    socStateKey = soc.getStateKey();
                }
                //Set the contextbar details.
                form.setContextBar(CourseOfferingContextBar.NEW_INSTANCE(term, socStateKey,
                        getStateService(), getAcalService(), createContextInfo()));
            }
        } catch (Exception e) {
            throw new RuntimeException("Could not populate context bar.");
        }
    }

    protected RuleViewTreeBuilder getViewTreeBuilder() {
        if (this.viewTreeBuilder == null) {
            viewTreeBuilder = new LURuleViewTreeBuilder();
            viewTreeBuilder.setNlHelper(this.getNLHelper());
        }
        return viewTreeBuilder;
    }

    protected NaturalLanguageHelper getNLHelper() {
        if (this.nlHelper == null) {
            nlHelper = new NaturalLanguageHelper();
            nlHelper.setRuleManagementService(this.getRuleManagementService());
        }
        return nlHelper;
    }

    protected LuiService getLuiService() {
        if (luiService == null) {
            luiService = CourseOfferingResourceLoader.loadLuiService();
        }
        return luiService;
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

    private StateService getStateService() {
        if (stateService == null) {
            stateService = GlobalResourceLoader.getService(new QName(StateServiceConstants.NAMESPACE, StateServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return stateService;
    }
}
