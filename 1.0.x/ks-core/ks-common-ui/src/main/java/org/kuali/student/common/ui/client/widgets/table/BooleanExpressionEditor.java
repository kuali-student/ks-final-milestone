/**
 * Copyright 2010 The Kuali Foundation Licensed under the
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

import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class BooleanExpressionEditor extends VerticalPanel{
    HorizontalPanel ungroupedConditionPanel = new HorizontalPanel();
    VerticalPanel treeTablePanel = new VerticalPanel();
    
    BooleanExpressionEditorModel tableModel = new BooleanExpressionEditorModel();

    BooleanExpressionEditorButtonBar buttonBar = new BooleanExpressionEditorButtonBar(tableModel);
    BooleanExpressionInputPanel booleanExpressionInput = new BooleanExpressionInputPanel(tableModel);    
    
    public BooleanExpressionEditor() {
        treeTablePanel.setPixelSize(300,300);
        //treeTablePanel.add(new Label(""));
        this.add(ungroupedConditionPanel);
        this.add(treeTablePanel);
        this.add(buttonBar);
        this.add(booleanExpressionInput);
        
        tableModel.addModelChangedEvent(new ModelChangedListener(){
            @Override
            public void modelChanged(BooleanExpressionEditorModel model) {
                refresh();
                
            }
        });
    }
    public void setNodeFromExpressionEditor(Node<Token> node){
        tableModel.setNodeFromExpressionEditor(node);
    }
    public BooleanExpressionEditorModel getModel(){
        return tableModel;
    }

    public void refresh(){
        booleanExpressionInput.addExpressionFromTableEditor(tableModel.getExpressionString());
        ungroupedConditionPanel.clear();
        treeTablePanel.clear();
        for(Widget w: tableModel.getWidgetList()){
            if(w instanceof NodeWidget){
                ungroupedConditionPanel.add(w);
            }else if(w instanceof TreeTable){
                treeTablePanel.add(w);
            }
        }
    }

}
