package org.kuali.student.lum.ui.requirements.client;

import org.kuali.student.common.ui.client.mvc.Model;
import org.kuali.student.core.dto.Idable;
import org.kuali.student.lum.ui.requirements.client.model.PrereqInfo;

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
    
    public static void clearModel(Model model) {
        for (Object data : model.getValues().toArray()) {
            if (data != null) {
                model.remove((Idable) data);
            }
        }  
    }    
    
    //returns first model object
    public static PrereqInfo getPrereqInfoModelObject(Model<PrereqInfo> model) {
        for (Object data : model.getValues().toArray()) {
            if (data != null) {
                return (PrereqInfo)data;
            }
        }         
        System.out.println("empty model.....");
        return null;
    }    
}


