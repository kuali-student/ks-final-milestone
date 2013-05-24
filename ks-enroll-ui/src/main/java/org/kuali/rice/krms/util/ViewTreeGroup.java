package org.kuali.rice.krms.util;

import org.kuali.rice.core.api.util.tree.Node;
import org.kuali.rice.core.api.util.tree.Tree;
import org.kuali.rice.krad.uif.container.Group;
import org.kuali.rice.krad.uif.container.TreeGroup;
import org.kuali.rice.krad.uif.element.Message;
import org.kuali.rice.krad.uif.util.ObjectPropertyUtils;
import org.kuali.rice.krad.uif.view.View;

/**
 * Created with IntelliJ IDEA.
 * User: SW Genis
 * Date: 2013/05/23
 * Time: 11:29 AM
 * To change this template use File | Settings | File Templates.
 */
public class ViewTreeGroup extends TreeGroup {

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
                bindingPrefix + /* TODO: hack */ ".rootElement", "root");
        treeGroups.setRootElement(rootNode);

        setTreeGroups(treeGroups);
    }
}
