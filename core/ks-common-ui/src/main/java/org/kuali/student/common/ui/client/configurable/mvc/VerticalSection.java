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
package org.kuali.student.common.ui.client.configurable.mvc;

import org.kuali.student.common.ui.client.mvc.Callback;

import com.google.gwt.user.client.ui.FlowPanel;

public class VerticalSection extends Section{
    
    protected final FlowPanel panel = new FlowPanel();
    
    public VerticalSection(){
        super.initWidget(panel);
        setLabelType(FieldLabelType.LABEL_TOP);
    }
        
    @Override
    public void clear() {
        super.clear();
    }
    
    @Override
    public void redraw() {
        panel.clear();
        panel.add(generateTitlePanel());
        panel.add(instructionsLabel);
        for(Section ns: sections){
            ns.redraw();
        }
        for(RowDescriptor r: rows){
            panel.add(r);
        }
    }


    @Override
    public void validate(Callback<org.kuali.student.core.validation.dto.ValidationResultInfo.ErrorLevel> callback) {
        // TODO bsmith - THIS METHOD NEEDS JAVADOCS        
    }

}
