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

import org.kuali.rice.core.api.util.tree.Node;
import org.kuali.rice.core.api.util.tree.Tree;
import org.kuali.rice.krad.web.form.UifFormBase;
import org.kuali.rice.krms.impl.repository.PropositionBo;
import org.kuali.student.enrollment.class1.krms.dto.PropositionEditor;
import org.kuali.student.enrollment.class1.krms.dto.RuleEditorTreeNode;
import org.kuali.student.r2.lum.course.dto.CourseInfo;

import java.util.List;


/**
 * This class //TODO ...
 *
 * @author Kuali Student Team
 */
public class KrmsComponentsForm extends UifFormBase {

    private PropositionEditor proposition;

    private Tree<TreeNode, String> tree1;

    public PropositionEditor getProposition() {
        return proposition;
    }

    public void setProposition(PropositionEditor proposition) {
        this.proposition = proposition;
    }

    /**
     * @return the tree1
     */
    public Tree<TreeNode, String> getTree1() {
        if (tree1 == null){
            this.initTree();
        }
        return this.tree1;
    }

    /**
     * @param tree1 the tree1 to set
     */
    public void setTree1(Tree<TreeNode, String> tree1) {
        this.tree1 = tree1;
    }

    private void initTree(){

        tree1 = new Tree<TreeNode, String>();

        Node<TreeNode, String> item1 = createNewNode("Item 1", "Item 1");
        item1.addChild(createNewNode("SubItem A", "SubItem A"));
        item1.addChild(createNewNode("SubItem B", "SubItem B"));

        Node<TreeNode, String> item2 = createNewNode("Item 2", "Item 2");
        item2.addChild(createNewNode("SubItem A", "SubItem A"));
        Node<TreeNode, String> sub2B = createNewNode("SubItem B", "SubItem B");
        sub2B.addChild(createNewNode("Item B-1", "Item B-1"));
        sub2B.addChild(createNewNode("Item B-2", "Item B-2"));
        sub2B.addChild(createNewNode("Item B-3", "Item B-3"));
        item2.addChild(sub2B);
        item2.addChild(createNewNode("SubItem C", "SubItem C"));

        Node<TreeNode, String> root = createNewNode("Root", "Root");
        root.addChild(item1);
        root.addChild(item2);

        tree1.setRootElement(root);
    }

    private Node<TreeNode, String> createNewNode(String data, String label){
        TreeNode treeNode = new TreeNode(data);
        return new Node<TreeNode, String>(treeNode, label);
    }

}
