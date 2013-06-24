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
package org.kuali.rice.krms.tree.node;

import org.kuali.rice.krms.dto.PropositionEditor;

import java.io.Serializable;

/**
 * @author Kuali Student Team
 */
public class RuleEditorTreeNode implements Serializable {

    private static final long serialVersionUID = 8038174553531544943L;

    public static final String ROOT_TYPE = "treeRoot";
    public static final String NODE_TYPE = "ruleTreeNode";

    public static final String COMPOUND_NODE_TYPE = RuleEditorTreeNode.NODE_TYPE + " compoundNode";
    public static final String COMPOUND_OP_NODE_TYPE = RuleEditorTreeNode.NODE_TYPE + " compoundOpCodeNode";

    public static final String FIRST_IN_GROUP = "firstInGroup";
    public static final String LAST_IN_GROUP = "lastInGroup";

    public static final String DISABLE_MOVE_IN = "disableMoveRight";
    public static final String DISABLE_MOVE_OUT = "disableMoveLeft";

    protected PropositionEditor proposition;

    public RuleEditorTreeNode(){}

    public RuleEditorTreeNode(PropositionEditor proposition){
        this.proposition = proposition;
    }

    public PropositionEditor getProposition() {
        return this.proposition;
    }

    public void setProposition(PropositionEditor proposition) {
        this.proposition = proposition;
    }

}
