package org.kuali.student.enrollment.class1.krms.tree;

import org.kuali.rice.core.api.util.tree.Node;
import org.kuali.rice.core.api.util.tree.Tree;
import org.kuali.rice.krms.api.repository.LogicalOperator;
import org.kuali.rice.krms.api.repository.proposition.PropositionType;
import org.kuali.rice.krms.dto.PropositionEditor;
import org.kuali.rice.krms.dto.RuleEditor;
import org.kuali.rice.krms.tree.AbstractTreeBuilder;
import org.kuali.rice.krms.tree.RuleViewTreeBuilder;
import org.kuali.rice.krms.tree.node.TreeNode;
import org.kuali.student.enrollment.class1.krms.dto.CluInformation;
import org.kuali.student.enrollment.class1.krms.dto.EnrolPropositionEditor;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Peggy
 * Date: 2/4/13
 * Time: 3:26 PM
 * To change this template use File | Settings | File Templates.
 */
public class CORuleViewTreeBuilder extends RuleViewTreeBuilder {

    private static final long serialVersionUID = 1L;

    public List<String> getListItems(PropositionEditor propositionEditor) {
        if (propositionEditor instanceof EnrolPropositionEditor) {
            EnrolPropositionEditor enrolProp = (EnrolPropositionEditor) propositionEditor;
            List<String> listItems = new ArrayList<String>();
            if (enrolProp.getCluSet() != null) {
                if (enrolProp.getCluSet().getClus() != null) {
                    for (CluInformation clu : enrolProp.getCluSet().getClus()) {
                        listItems.add(clu.getCode());
                    }
                }
            }
            return listItems;
        }
        return null;
    }
}
