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
        
        System.out.println("HERE>...");
        
        String reqCompType = reqComponentInfo.getType();
        
        List<ReqCompFieldInfo> fields = reqComponentInfo.getReqCompField();
        
        for (ReqCompFieldInfo field : fields) {
            System.out.println("test: " + field.getValue());
        }
        
        System.out.println(typeDesc);
        return typeDesc;
        
        //return reqComponentInfo.getDesc();
    }
    
}
