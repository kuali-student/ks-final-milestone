package org.kuali.student.lum.ui.requirements.client;

import org.apache.log4j.Logger;
import org.kuali.student.common.ui.client.mvc.Model;
import org.kuali.student.core.dto.Idable;
import org.kuali.student.lum.ui.requirements.client.model.RuleInfo;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Widget;

public class RulesUtilities {
    
    final static Logger logger = Logger.getLogger(RulesUtilities.class);
    
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
    
    public static void clearModel(Model model) {
        for (Object data : model.getValues().toArray()) {
            if (data != null) {
                model.remove((Idable) data);
            }
        }  
    }    
    
    //returns first model object
    public static RuleInfo getReqInfoModelObject(Model<RuleInfo> model) {
        for (Object data : model.getValues().toArray()) {
            if (data != null) {
                return (RuleInfo)data;
            }
        }         
        logger.debug("empty model.....");
        return null;
    }    
}


