/**
 * Copyright 2012 The Kuali Foundation Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 *
 * Created by Paul on 2012/11/22
 */
package org.kuali.student.enrollment.class1.krms.form;

import org.kuali.rice.core.api.util.tree.Tree;
import org.kuali.rice.krms.dto.RuleEditor;
import org.kuali.student.enrollment.class1.krms.dto.EnrolRuleEditor;
import org.kuali.rice.krms.tree.node.TreeNode;
import org.kuali.student.enrollment.uif.form.KSUifForm;


/**
 * This class //TODO ...
 *
 * @author Kuali Student Team
 */
public class CompareRuleForm extends KSUifForm {


    private String ruleId;
    private RuleEditor ruleEditor = new RuleEditor();
    private  Tree<TreeNode, String> cmRule;


    public RuleEditor getRuleEditor() {
        return ruleEditor;
    }
    public String getRuleId() {
        return ruleId;
    }

    public void setRuleId(String ruleId) {
        this.ruleId = ruleId;
    }

    public Tree<TreeNode, String> getCmRule() {
        return cmRule;
    }

    public void setCmRule(Tree<TreeNode, String> cmRule) {
        this.cmRule = cmRule;
    }

}
