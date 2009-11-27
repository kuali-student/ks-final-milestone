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
package org.kuali.student.lum.ui.requirements.client;

import org.kuali.student.common.ui.client.mvc.CollectionModel;
import org.kuali.student.common.ui.client.mvc.Model;
import org.kuali.student.core.dto.Idable;
import org.kuali.student.lum.ui.requirements.client.model.RuleInfo;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Widget;

public class RulesUtilities {
    
    public class RowBreak extends Composite{
        private HorizontalPanel row = new HorizontalPanel();
        private HTML hr = new HTML("<HR />");
        public RowBreak(){
            row.addStyleName("Home-Horizontal-Break");
            row.add(hr);
            this.initWidget(row);
        }
        public Widget getRowBreak() {
            return this;
        }
    }
    
    public class Divider extends Composite{
        private HorizontalPanel row = new HorizontalPanel();
        private HTML hr = new HTML("<HR />");
        public Divider(){
            row.addStyleName("KS-Divider");
            row.add(hr);
            this.initWidget(row);
        }
        public Widget getRowBreak() {
            return this;
        }
    }    
    
    public static void clearModel(CollectionModel model) {
        for (Object data : model.getValues().toArray()) {
            if (data != null) {
                model.remove((Idable) data);
            }
        }  
    }    
    
    //returns first model object
    public static RuleInfo getReqInfoModelObject(CollectionModel<RuleInfo> model) {
        for (Object data : model.getValues().toArray()) {
            if (data != null) {
                return (RuleInfo)data;
            }
        }         
        System.out.println("empty model.....");
        return null;
    }    
}


