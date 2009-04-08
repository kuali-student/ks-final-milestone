package org.kuali.student.lum.ui.requirements.client.model;

import java.io.Serializable;

import org.kuali.student.common.ui.client.widgets.table.Token;
import org.kuali.student.lum.lu.dto.ReqComponentInfo;

public class ReqComponentVO extends Token implements Serializable {

    private static final long serialVersionUID = 1L;
    private ReqComponentInfo reqComponentInfo;
    
    public ReqComponentVO() {}
    
    public ReqComponentVO(ReqComponentInfo reqComponentInfo) {
        this.reqComponentInfo = reqComponentInfo;
    }

    public ReqComponentInfo getReqComponentInfo() {
        return reqComponentInfo;
    }

    public void setReqComponentInfo(ReqComponentInfo reqComponentInfo) {
        this.reqComponentInfo = reqComponentInfo;
    }

    @Override
    public String toString() {
        return reqComponentInfo.getDesc();
    }
    
}
