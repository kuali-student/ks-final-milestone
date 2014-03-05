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
package org.kuali.rice.krms.util;

import org.kuali.rice.core.api.util.tree.Node;
import org.kuali.rice.core.api.util.tree.Tree;
import org.kuali.rice.krad.uif.component.Component;
import org.kuali.rice.krad.uif.container.Group;
import org.kuali.rice.krad.uif.container.TreeGroup;
import org.kuali.rice.krad.uif.element.Message;
import org.kuali.rice.krad.uif.util.ObjectPropertyUtils;
import org.kuali.rice.krad.uif.view.View;
import org.kuali.rice.krms.dto.PropositionEditor;
import org.kuali.rice.krms.tree.node.RuleEditorTreeNode;

/**
 * @author Kuali Student Team
 */
public class EditTreeGroup extends TreeGroup {

    protected void buildTreeGroups(View view, Object model) {
        // get Tree data property
        Tree<Object, String> treeData = ObjectPropertyUtils.getPropertyValue(model, getBindingInfo().getBindingPath());

        if(treeData==null){
            return;
        }

        // build component tree that corresponds with tree data
        Tree<Group, Message> treeGroups = new Tree<Group, Message>();

        String bindingPrefix = getBindingInfo().getBindingPrefixForNested();
        Node<Group, Message> rootNode = buildTreeNode(treeData.getRootElement(),
                bindingPrefix + ".rootElement", "root");
        treeGroups.setRootElement(rootNode);

        setTreeGroups(treeGroups);
    }

    protected Node<Group, Message> buildTreeNode(Node<Object, String> nodeData, String bindingPrefix,
                                                 String parentNode) {
        Node<Group, Message> node = super.buildTreeNode(nodeData, bindingPrefix, parentNode);

        // Set the binding path on the proposition.
        if(nodeData.getData() instanceof RuleEditorTreeNode){
            PropositionEditor proposition = ((RuleEditorTreeNode) nodeData.getData()).getProposition();
            proposition.setBindingPath(bindingPrefix + ".data");
        }

        // Reset this id to a static id, can only have one proposition in edit mode at a time.
        if(node.getData().getId().startsWith("KRMS-PropositionEdit-BoxSection")){
            node.getData().setId(node.getData().getBaseId());
            for(Component component : node.getData().getItems()){
                component.setId(component.getBaseId());
            }
        }

        return node;
    }
}
