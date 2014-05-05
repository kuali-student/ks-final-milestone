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
package org.kuali.student.lum.lu.ui.krms.tree;

import org.kuali.rice.core.api.util.tree.Node;
import org.kuali.rice.krms.dto.PropositionEditor;
import org.kuali.rice.krms.dto.RuleEditor;
import org.kuali.rice.krms.tree.node.TreeNode;
import org.kuali.student.core.krms.tree.KSRuleViewTreeBuilder;
import org.kuali.student.lum.lu.ui.krms.dto.CluGroup;
import org.kuali.student.lum.lu.ui.krms.dto.CluSetWrapper;
import org.kuali.student.lum.lu.ui.krms.dto.LUPropositionEditor;
import org.kuali.student.lum.lu.ui.krms.tree.node.LUTreeNode;

import java.util.List;

/**
 * Overridden class to add items to be displayed in the view trees that are not converted
 * by the natural language translater on the rule management service.
 *
 * @author Kuali Student Team
 */
public class LURuleViewTreeBuilder extends KSRuleViewTreeBuilder {

    private static final long serialVersionUID = 1L;

    @Override
    public TreeNode createTreeNode(RuleEditor rule, String data, PropositionEditor prop){
        LUTreeNode tNode = new LUTreeNode(data);
        tNode.setKey(prop.getKey());
        tNode.setCluGroups(this.getCluGroups(prop));
        return tNode;
    }

    public List<CluGroup> getCluGroups(PropositionEditor propositionEditor) {
        if (propositionEditor instanceof LUPropositionEditor) {
            CluSetWrapper cluSet = ((LUPropositionEditor) propositionEditor).getCourseSet();

            if (cluSet != null) {
                return cluSet.getCluGroups();
            }
        }
        return null;
    }

}
