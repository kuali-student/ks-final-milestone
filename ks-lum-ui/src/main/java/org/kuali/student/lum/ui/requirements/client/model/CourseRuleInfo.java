package org.kuali.student.lum.ui.requirements.client.model;

import java.io.Serializable;
import java.util.List;

import org.kuali.student.core.dto.Idable;
import org.kuali.student.lum.lu.dto.CluInfo;
import org.kuali.student.lum.lu.dto.LuStatementInfo;

public class CourseRuleInfo implements Serializable {

    private static final long serialVersionUID = 2L;
    private List<LuStatementInfo> luStatementInfoList;
     
    public LuStatementInfo getLuStatementByType(String LuStatementType) {
        for (LuStatementInfo stmtInfo : luStatementInfoList) {
            System.out.println("Found statement type: " + stmtInfo.getType());
            if (stmtInfo.getType().equals(LuStatementType)) {
                return stmtInfo;
            }
        }
        System.out.println("Did not find given Statement Type: " + LuStatementType);
        return null;
    }
    
    public List<LuStatementInfo> getLuStatementInfoList() {
        return luStatementInfoList;
    }

    public void setLuStatementInfoList(List<LuStatementInfo> luStatementInfoList) {
        this.luStatementInfoList = luStatementInfoList;
    }    
}
