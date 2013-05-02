package org.kuali.student.enrollment.class1.krms.tree;

import org.kuali.rice.core.api.util.tree.Node;
import org.kuali.rice.core.api.util.tree.Tree;
import org.kuali.rice.krms.api.repository.agenda.AgendaTreeDefinition;
import org.kuali.rice.krms.api.repository.agenda.AgendaTreeEntryDefinitionContract;
import org.kuali.rice.krms.api.repository.agenda.AgendaTreeRuleEntry;
import org.kuali.rice.krms.api.repository.proposition.PropositionDefinitionContract;
import org.kuali.rice.krms.api.repository.reference.ReferenceObjectBinding;
import org.kuali.rice.krms.api.repository.rule.RuleDefinition;
import org.kuali.rice.krms.api.repository.rule.RuleDefinitionContract;
import org.kuali.rice.krms.dto.PropositionEditor;
import org.kuali.rice.krms.tree.RuleCompareTreeBuilder;
import org.kuali.rice.krms.tree.node.CompareTreeNode;
import org.kuali.student.enrollment.class1.krms.dto.CluInformation;
import org.kuali.student.enrollment.class1.krms.dto.EnrolPropositionEditor;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: SW
 * Date: 2013/04/03
 * Time: 11:33 AM
 * To change this template use File | Settings | File Templates.
 */
public class CORuleCompareTreeBuilder extends RuleCompareTreeBuilder {

    @Override
    public Tree<CompareTreeNode, String> buildTree(RuleDefinitionContract original, RuleDefinitionContract compare) {
        Tree<CompareTreeNode, String> compareTree = super.buildTree(original, compare);

        //Set data headers on root node.
        Node<CompareTreeNode, String> node = compareTree.getRootElement();
        if ((node.getChildren() != null) && (node.getChildren().size() > 0)) {
            Node<CompareTreeNode, String> childNode = node.getChildren().get(0);

            // Set the headers on the first root child
            if (childNode.getData() != null) {
                CompareTreeNode compareTreeNode = childNode.getData();
                compareTreeNode.setOriginal("CO Rules");
                compareTreeNode.setCompared("CLU Rules");
            }

        }

        return compareTree;
    }

    public RuleDefinition getCompareRule(String refObjectId, String typeId) {
        RuleDefinition compareRule = null;
        List<ReferenceObjectBinding> referenceObjects = this.getRuleManagementService().findReferenceObjectBindingsByReferenceObject("kuali.lu.type.CreditCourse", refObjectId);

        for (ReferenceObjectBinding referenceObject : referenceObjects) {
            AgendaTreeDefinition agendaTree = this.getRuleManagementService().getAgendaTree(referenceObject.getKrmsObjectId());
            compareRule = this.getRuleFromTree(agendaTree.getEntries(), typeId);

            if (compareRule != null) {
                return compareRule;
            }
        }

        return null;
    }

    private RuleDefinition getRuleFromTree(List<AgendaTreeEntryDefinitionContract> agendaTreeEntries, String typeId) {

        for (AgendaTreeEntryDefinitionContract treeEntry : agendaTreeEntries) {
            if (treeEntry instanceof AgendaTreeRuleEntry) {
                AgendaTreeRuleEntry treeRuleEntry = (AgendaTreeRuleEntry) treeEntry;
                RuleDefinition rule = this.getRuleManagementService().getRule(treeRuleEntry.getRuleId());
                if (rule.getTypeId().equals(typeId)) {
                    return rule;
                }

                if (treeRuleEntry.getIfTrue() != null) {
                    RuleDefinition childRule = getRuleFromTree(treeRuleEntry.getIfTrue().getEntries(), typeId);
                    if (childRule != null) {
                        return childRule;
                    }
                }
            }
        }

        return null;
    }

    @Override
    public List<String> getListItems(PropositionDefinitionContract propositionEditor) {
        if (propositionEditor instanceof EnrolPropositionEditor) {
            EnrolPropositionEditor enrolProp = (EnrolPropositionEditor) propositionEditor;
            List<String> listItems = new ArrayList<String>();
            if (enrolProp.getCluSet() != null) {
                if (enrolProp.getCluSet().getClus() != null) {
                    for (CluInformation clu : enrolProp.getCluSet().getClus()) {
                        String description = clu.getCode() + " " + clu.getTitle() + " " + clu.getCredits();
                        listItems.add(description);
                    }
                }
            }
            return listItems;
        }
        return null;
    }
}
