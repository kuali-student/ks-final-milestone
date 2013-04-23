package org.kuali.student.enrollment.class1.krms.service.impl;

import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.krad.maintenance.MaintenanceDocument;
import org.kuali.rice.krms.api.repository.agenda.*;
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
import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingInfo;
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;
import org.kuali.student.r2.common.util.ContextUtils;
import org.kuali.student.r2.common.util.constants.CourseOfferingServiceConstants;
import org.kuali.student.r2.lum.clu.service.CluService;
import org.kuali.student.r2.lum.util.constants.CluServiceConstants;

import javax.xml.namespace.QName;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: SW
 * Date: 2013/02/04
 * Time: 9:34 AM
 * To change this template use File | Settings | File Templates.
 */
public class AORuleEditorMaintainableImpl extends RuleEditorMaintainableImpl {

    private transient CluService cluService;
    private transient CourseOfferingService courseOfferingService;

    @Override
    public Object retrieveObjectForEditOrCopy(MaintenanceDocument document, Map<String, String> dataObjectKeys) {

        AORuleManagementWrapper dataObject = new AORuleManagementWrapper();

        String aoId = dataObjectKeys.get("refObjectId");
        dataObject.setRefObjectId(aoId);

        List<AgendaEditor> agendas = new ArrayList<AgendaEditor>();
        List<ReferenceObjectBinding> refObjectsBindings = this.getRuleManagementService().findReferenceObjectBindingsByReferenceObject("kuali.lui.type.activity.offering", aoId);
        for(ReferenceObjectBinding referenceObjectBinding : refObjectsBindings){
            agendas.add(this.getAgendaEditor(referenceObjectBinding.getKrmsObjectId()));
        }

        dataObject.setAgendas(agendas);

        //Retrieve the Reg Object information
        ActivityOfferingInfo activityOffering = null;
        if (aoId != null) {
            try {
                activityOffering = this.getCourseOfferingService().getActivityOffering(aoId, ContextUtils.createDefaultContextInfo());
            } catch (Exception e) {
                //TODO: Add Exception handling.
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

    protected AgendaEditor getAgendaEditor(String agendaId) {
        AgendaDefinition agenda = this.getRuleManagementService().getAgenda(agendaId);
        AgendaEditor agendaEditor = new EnrolAgendaEditor(agenda);

        AgendaTreeDefinition agendaTree = this.getRuleManagementService().getAgendaTree(agendaId);
        agendaEditor.setRuleEditors(getRuleEditorsFromTree(agendaTree.getEntries()));

        return agendaEditor;
    }

    protected List<RuleEditor> getRuleEditorsFromTree(List<AgendaTreeEntryDefinitionContract> agendaTreeEntries) {

        //Create NLHelper to populate Natural language on propositions.
        NaturalLanguageHelper nlHelper = new NaturalLanguageHelper();
        nlHelper.setRuleManagementService(this.getRuleManagementService());

        //Create a ViewTreeBuilder to build the previews.
        RuleViewTreeBuilder viewTreeBuilder = new EnrolRuleViewTreeBuilder();
        viewTreeBuilder.setRuleManagementService(this.getRuleManagementService());

        List<RuleEditor> rules = new ArrayList<RuleEditor>();
        for (AgendaTreeEntryDefinitionContract treeEntry : agendaTreeEntries) {
            if (treeEntry instanceof AgendaTreeRuleEntry) {
                AgendaTreeRuleEntry treeRuleEntry = (AgendaTreeRuleEntry) treeEntry;
                AgendaItemDefinition agendaItem = this.getRuleManagementService().getAgendaItem(treeEntry.getAgendaItemId());

                if (agendaItem.getRuleId() != null) {

                    //Retrieve the rule
                    RuleDefinition rule = this.getRuleManagementService().getRule(treeRuleEntry.getRuleId());
                    RuleEditor ruleEditor = new EnrolRuleEditor(rule);

                    //Initialize the Proposition tree
                    PropositionEditor rootProposition = (PropositionEditor) ruleEditor.getProposition();
                    this.initPropositionEditor((PropositionEditor) ruleEditor.getProposition());
                    nlHelper.setNaturalLanguageTreeForUsage(rootProposition, viewTreeBuilder.getNaturalLanguageUsageKey());
                    ruleEditor.setViewTree(viewTreeBuilder.buildTree(ruleEditor));

                    //Add rule to list on agenda
                    rules.add(ruleEditor);
                }

                if (treeRuleEntry.getIfTrue() != null) {
                    rules.addAll(getRuleEditorsFromTree(treeRuleEntry.getIfTrue().getEntries()));
                }
            }

            // TODO: Check for sub agendas, not required for course offering.
        }

        return rules;
    }

    protected CluService getCluService() {
        if (cluService == null) {
            cluService = (CluService) GlobalResourceLoader.getService(new QName(CluServiceConstants.CLU_NAMESPACE, CluServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return cluService;
    }

    private CourseOfferingService getCourseOfferingService() {
        if (courseOfferingService == null) {
            courseOfferingService = (CourseOfferingService) GlobalResourceLoader.getService(new QName(CourseOfferingServiceConstants.NAMESPACE,
                    CourseOfferingServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return courseOfferingService;
    }
}
