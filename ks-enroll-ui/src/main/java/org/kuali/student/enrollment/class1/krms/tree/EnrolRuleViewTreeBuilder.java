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
import org.kuali.student.enrollment.class1.krms.dto.CluSetInformation;
import org.kuali.student.enrollment.class1.krms.dto.EnrolPropositionEditor;
import org.kuali.student.r2.lum.clu.dto.CluSetInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Overridden class to add items to be displayed in the view trees that are not converted
 * by the natural language translater on the rule management service.
 *
 * @author Kuali Student Team
 */
public class EnrolRuleViewTreeBuilder extends RuleViewTreeBuilder {

    private static final long serialVersionUID = 1L;

    public List<Object> getListItems(PropositionEditor propositionEditor) {
        if (propositionEditor instanceof EnrolPropositionEditor) {
            CluSetInformation cluSetInfo = ((EnrolPropositionEditor) propositionEditor).getCluSet();

            if (cluSetInfo != null) {
                return cluSetInfo.getCluViewers();
            }
        }
        return null;
    }
}
