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

import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.krad.maintenance.MaintenanceDocument;
import org.kuali.rice.krms.api.repository.agenda.AgendaDefinition;
import org.kuali.rice.krms.api.repository.agenda.AgendaItemDefinition;
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
import org.kuali.student.enrollment.class1.krms.dto.AORuleManagementWrapper;
import org.kuali.student.enrollment.class1.krms.dto.EnrolAgendaEditor;
import org.kuali.student.enrollment.class1.krms.dto.EnrolRuleEditor;
import org.kuali.student.enrollment.class1.krms.dto.EnrolRuleManagementWrapper;
import org.kuali.student.enrollment.class1.krms.tree.EnrolRuleViewTreeBuilder;
import org.kuali.student.enrollment.class2.courseoffering.util.CourseOfferingResourceLoader;
import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;
import org.kuali.student.krms.util.KSKRMSConstants;
import org.kuali.student.r2.common.util.ContextUtils;
import org.kuali.student.r2.common.util.constants.CourseOfferingServiceConstants;
import org.kuali.student.r2.lum.clu.service.CluService;
import org.kuali.student.r2.lum.util.constants.CluServiceConstants;

import javax.xml.namespace.QName;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Kuali Student Team
 */
public class AORuleEditorMaintainableImpl extends RuleEditorMaintainableImpl {

    private transient CluService cluService;
    private transient CourseOfferingService courseOfferingService;

    private transient RuleViewTreeBuilder viewTreeBuilder;
    private transient NaturalLanguageHelper nlHelper;

    @Override
    public Object retrieveObjectForEditOrCopy(MaintenanceDocument document, Map<String, String> dataObjectKeys) {

        AORuleManagementWrapper dataObject = new AORuleManagementWrapper();

        String aoId = dataObjectKeys.get("refObjectId");
        dataObject.setRefObjectId(aoId);

        dataObject.setNamespace(KSKRMSConstants.KS_SYS_NAMESPACE);
        dataObject.setRefDiscriminatorType("kuali.lui.type.activity.offering");

        dataObject.setAgendas(this.getAgendasForRef(dataObject.getRefDiscriminatorType(), aoId));

        //Retrieve the Reg Object information
        ActivityOfferingInfo activityOffering = null;
        if (aoId != null) {
            try {
                activityOffering = this.getCourseOfferingService().getActivityOffering(aoId, ContextUtils.createDefaultContextInfo());
            } catch (Exception e) {
                throw new RuntimeException("Could not retrieve activity offering for " + aoId);
            }
        }

        //Populate Clu Identification Information
        if (activityOffering != null) {
            StringBuilder courseNameBuilder = new StringBuilder();
            courseNameBuilder.append(activityOffering.getTermCode());
            courseNameBuilder.append(" - ");
            courseNameBuilder.append(activityOffering.getCourseOfferingCode()+activityOffering.getActivityCode());
            courseNameBuilder.append(" - ");
            courseNameBuilder.append(activityOffering.getCourseOfferingTitle());
            dataObject.setCluDescription(courseNameBuilder.toString());
        }

        dataObject.setCompareTree(RuleCompareTreeBuilder.initCompareTree());

        return dataObject;
    }

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
    protected AgendaEditor getAgendaEditor(String agendaId) {
        AgendaDefinition agenda = this.getRuleManagementService().getAgenda(agendaId);
        return new EnrolAgendaEditor(agenda);
    }

    /**
     * Retrieves all the rules from the agenda tree and create a list of ruleeditor objects.
     *
     * Also initialize the proposition editors for each rule recursively and set natural language for the view trees.
     *
     * @param agendaTreeEntries
     * @return
     */
    protected List<RuleEditor> getRuleEditorsFromTree(List<AgendaTreeEntryDefinitionContract> agendaTreeEntries, boolean initProp) {

        //Create NLHelper to populate Natural language on propositions.
        NaturalLanguageHelper nlHelper = new NaturalLanguageHelper();
        nlHelper.setRuleManagementService(this.getRuleManagementService());

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
     * Return the clu id from the canonical course that is linked to the given Activity offering id.
     *
     * @param refObjectId - the Activity offering id.
     * @return
     * @throws Exception
     */
    @Override
    public List<ReferenceObjectBinding> getParentRefOjbects(String refObjectId) {
        List<ReferenceObjectBinding> list = new ArrayList<ReferenceObjectBinding>();
      // TODO: get the Activity offering  for the given refobject and find the Parent   reference
        ActivityOfferingInfo activityOfferingInfo = null;
        try {
            activityOfferingInfo = this.getCourseOfferingService().getActivityOffering(refObjectId, ContextUtils.createDefaultContextInfo());
        } catch (Exception e) {
            throw new RuntimeException("Could not retrieve course offering for " + refObjectId);
        }
        return this.getRuleManagementService().findReferenceObjectBindingsByReferenceObject("kuali.lu.type.CreditCourse", activityOfferingInfo.getActivityId());
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


}
