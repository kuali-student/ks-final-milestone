package org.kuali.rice.krms.tree;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.kuali.rice.krms.api.repository.NaturalLanguageTree;
import org.kuali.rice.krms.api.repository.RuleManagementService;
import org.kuali.rice.krms.api.repository.language.NaturalLanguageUsage;
import org.kuali.rice.krms.api.repository.proposition.PropositionDefinition;
import org.kuali.rice.krms.api.repository.rule.RuleDefinitionContract;
import org.kuali.rice.krms.dto.PropositionEditor;
import org.kuali.rice.krms.dto.RuleEditor;
import org.kuali.student.enrollment.class1.krms.dto.EnrolPropositionEditor;
import org.kuali.student.enrollment.class1.krms.dto.EnrolRuleEditor;
import org.kuali.student.enrollment.class2.courseoffering.service.decorators.PermissionServiceConstants;
import org.kuali.student.krms.naturallanguage.util.KsKrmsConstants;

import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Created with IntelliJ IDEA.
 * User: SW
 * Date: 2013/02/25
 * Time: 2:12 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class AbstractTreeBuilder implements TreeBuilder {

    private RuleManagementService ruleManagementService;

    protected String usageId;

    private TreeIterator nlDescriptions;

    public RuleManagementService getRuleManagementService() {
        return ruleManagementService;
    }

    public void setRuleManagementService(RuleManagementService ruleManagementService) {
        this.ruleManagementService = ruleManagementService;
    }

    protected void setNaturalLanguageTree(PropositionEditor root){
        PropositionDefinition.Builder propBuilder = PropositionDefinition.Builder.create(root);
        NaturalLanguageTree nlTree = this.getRuleManagementService().translateNaturalLanguageTreeForProposition(this.getNaturalLanguageUsageId(), propBuilder.build(), "en");
        nlDescriptions = new TreeIterator(nlTree);
    }

    protected String buildNodeLabel(RuleDefinitionContract rule, PropositionEditor prop, boolean refreshNl){
        return StringEscapeUtils.escapeHtml(this.getDescription(prop, refreshNl));
    }

    protected String getDescription(PropositionEditor proposition, boolean refreshNl) {
        if (proposition == null) {
            return StringUtils.EMPTY;
        }

        // Update description from natural language
        if (refreshNl){
            return nlDescriptions.next();
        }

        //Return the description
        if (proposition.getDescription() != null){
            return proposition.getDescription();
        }

        return StringUtils.EMPTY;
    }

    protected String getPropositionPrefix(RuleEditor rule, PropositionEditor prop){
        if (rule == null){
            return StringUtils.EMPTY;
        }

        //Add the proposition with alpha code in the map if it doesn't already exist.
        if (null == prop.getKey()) {
            prop.setKey((String) rule.getAlpha().next());
        }

        //Build the prefix.
        return "<b>" + prop.getKey() + ".</b> ";
    }

    protected String getNaturalLanguageUsageId(){
        if (usageId == null){
            NaturalLanguageUsage usage = this.getRuleManagementService().getNaturalLanguageUsageByNameAndNamespace(KsKrmsConstants.KRMS_NL_PREVIEW,
                    PermissionServiceConstants.KS_SYS_NAMESPACE);
            if (usage != null){
                usageId = usage.getId();
            }
        }
        return usageId;
    }

    /**
     * Put the natural language descriptions in a queue and retrieve them in
     * the same order as the propostion tree.
     */
    protected class TreeIterator implements Serializable, Iterator<String> {

        private Queue<String> nl;

        public TreeIterator(NaturalLanguageTree tree){
            nl = new LinkedList<String>();
            this.addToStack(tree);
        }

        private void addToStack(NaturalLanguageTree tree){
            if (tree == null){
                return;
            }

            nl.offer(tree.getNaturalLanguage());
            if (tree.getChildren() != null){
                for (NaturalLanguageTree child : tree.getChildren()){
                    addToStack(child);
                }
            }
        }

        @Override
        public boolean hasNext() {
            return !nl.isEmpty();
        }

        @Override
        public String next() {
            return nl.poll();
        }

        @Override
        public void remove() {
            //To change body of implemented methods use File | Settings | File Templates.
        }
    }
}
