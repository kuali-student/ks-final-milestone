package org.kuali.student.lum.ui.requirements.client.model;

import java.io.Serializable;
import java.util.List;

import org.kuali.student.common.ui.client.widgets.table.Token;
import org.kuali.student.lum.lu.dto.ReqCompFieldInfo;
import org.kuali.student.lum.lu.dto.ReqComponentInfo;

public class ReqComponentVO extends Token implements Serializable {

    private static final long serialVersionUID = 1L;
    private ReqComponentInfo reqComponentInfo;
    private String typeDesc;
    
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

    public String getTypeDesc() {
        return typeDesc;
    }

    public void setTypeDesc(String typeDesc) {
        this.typeDesc = typeDesc;
    }

    @Override
    public String toString() {                
        String reqCompType = reqComponentInfo.getType();
        String reqCompDescription = typeDesc;
                
        for (ReqCompFieldInfo fieldInfo : reqComponentInfo.getReqCompField()) {
            //System.out.println("Field Info: " + fieldInfo.getId());
            reqCompDescription = reqCompDescription.replaceAll("<" + fieldInfo.getId() + ">", fieldInfo.getValue());
        }        

        return reqCompDescription;
    }
    
}
