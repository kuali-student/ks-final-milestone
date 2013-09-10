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

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.core.api.util.tree.Node;
import org.kuali.rice.core.api.util.tree.Tree;
import org.kuali.rice.krad.uif.UifConstants;
import org.kuali.rice.krad.uif.container.CollectionGroup;
import org.kuali.rice.krad.uif.container.Group;
import org.kuali.rice.krad.uif.container.NodePrototype;
import org.kuali.rice.krad.uif.container.TreeGroup;
import org.kuali.rice.krad.uif.element.Message;
import org.kuali.rice.krad.uif.util.ComponentUtils;
import org.kuali.rice.krad.uif.util.ObjectPropertyUtils;
import org.kuali.rice.krad.uif.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Kuali Student Team
 */
public class ViewTreeGroup extends TreeGroup {


    /**
     * This method is overridden to add a null check.
     *
     * @param view
     * @param model
     */
    protected void buildTreeGroups(View view, Object model) {
        // get Tree data property
        Tree<Object, String> treeData = ObjectPropertyUtils.getPropertyValue(model, getBindingInfo().getBindingPath());

        if (treeData == null) {
            return;
        }

        // build component tree that corresponds with tree data
        Tree<Group, Message> treeGroups = new Tree<Group, Message>();

        String bindingPrefix = getBindingInfo().getBindingPrefixForNested();
        Node<Group, Message> rootNode = buildTreeNode(treeData.getRootElement(),
                bindingPrefix + ".rootElement", "_root");
        treeGroups.setRootElement(rootNode);

        setTreeGroups(treeGroups);
    }

    /**
     * This method is overridden to set suffix on subcollections.
     *
     * @param nodeData
     * @param bindingPrefix
     * @param parentNode
     * @return
     */
    @Override
    protected Node<Group, Message> buildTreeNode(Node<Object, String> nodeData, String bindingPrefix, String parentNode) {

        if (nodeData == null) {
            return null;
        }

        Node<Group, Message> node = new Node<Group, Message>();
        node.setNodeType(nodeData.getNodeType());

        //Note: view tree does noet use the prototype map.
        NodePrototype prototype = this.getDefaultNodePrototype();

        Message message = ComponentUtils.copy(prototype.getLabelPrototype(), parentNode);
        ComponentUtils.pushObjectToContext(message, UifConstants.ContextVariableNames.NODE, nodeData);
        message.setMessageText(nodeData.getNodeLabel());
        node.setNodeLabel(message);

        Group nodeGroup = ComponentUtils.copyComponent(prototype.getDataGroupPrototype(), bindingPrefix + ".data",
                parentNode);
        ComponentUtils.pushObjectToContext(nodeGroup, UifConstants.ContextVariableNames.NODE, nodeData);

        String nodePath = bindingPrefix + ".data";
        if (StringUtils.isNotBlank(getBindingInfo().getBindingObjectPath())) {
            nodePath = getBindingInfo().getBindingObjectPath() + "." + nodePath;
        }
        ComponentUtils.pushObjectToContext(nodeGroup, UifConstants.ContextVariableNames.NODE_PATH, nodePath);

        /*Overridden section*/
        List<CollectionGroup> components = ComponentUtils.getComponentsOfTypeShallow(nodeGroup, CollectionGroup.class);
        for (CollectionGroup fieldCollectionGroup : components) {
            fieldCollectionGroup.setSubCollectionSuffix(parentNode);
        }
        node.setData(nodeGroup);

        List<Node<Group, Message>> nodeChildren = new ArrayList<Node<Group, Message>>();

        int childIndex = 0;
        for (Node<Object, String> childDataNode : nodeData.getChildren()) {
            String nextBindingPrefix = bindingPrefix + ".children[" + childIndex + "]";
            Node<Group, Message> childNode = buildTreeNode(childDataNode, nextBindingPrefix,
                    "_node" + childIndex + parentNode); //Removed unnecessary suffix elements.

            nodeChildren.add(childNode);

            // Don't forget about me:
            ++childIndex;
        }
        node.setChildren(nodeChildren);

        return node;
    }
}
