package org.kuali.student.lum.ui.requirements.client.model;

import org.kuali.student.lum.lu.dto.ReqComponentInfo;

public class RequirementComponentVO {

    private ReqComponentInfo reqComponentInfo;
    private boolean selected = false;
    public RequirementComponentVO() {
        init();
    }
    private void init() {
        reqComponentInfo = new ReqComponentInfo();
    }
    public boolean isSelected() {
        return selected;
    }
    public void setSelected(boolean selected) {
        this.selected = selected;
    }
    public String getDesc() {
        return reqComponentInfo.getDesc();
    }
    public void setDesc(String desc) {
        this.reqComponentInfo.setDesc(desc);
    }
    public String getId() {
        return reqComponentInfo.getId();
    }
    public void setId(String id) {
        reqComponentInfo.setId(id);
    }
    
}
