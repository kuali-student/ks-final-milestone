/*
 * Copyright 2009 The Kuali Foundation Licensed under the
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
 */
package org.kuali.student.common.ui.client.widgets.table;

import java.util.List;
public interface NodeEditor {

    public boolean isAndable(Node target, List<Node> nodeList);
    
    public boolean isOrable(Node target, List<Node> nodeList);
    
    public boolean isAddable();
    public boolean isOrable();
    public boolean isAddable(Node target, List<Node> nodeList);
    
    public boolean isRemovable(List<Node> nodeList);
    
    public boolean isTogglable(); 
    
    public void doAnd();

    public void doOr();

    public void doRemoveFromGroup();

    public void doAddToGroup();

    public void doToggle();

    public boolean canUndo();

    public boolean canRedo();
    
    public void doUndo();
    
    public void doRedo();

}
