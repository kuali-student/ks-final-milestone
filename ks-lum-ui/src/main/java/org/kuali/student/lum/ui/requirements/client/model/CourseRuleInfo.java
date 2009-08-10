package org.kuali.student.lum.ui.requirements.client.model;

import java.io.Serializable;
import java.util.List;

import org.apache.log4j.Logger;
import org.kuali.student.core.dto.Idable;
import org.kuali.student.lum.lu.dto.CluInfo;
import org.kuali.student.lum.lu.dto.LuStatementInfo;

public class CourseRuleInfo implements Idable, Serializable {

    final Logger logger = Logger.getLogger(CourseRuleInfo.class);
    
    private static final long serialVersionUID = 2L;
    private String id;
    private CluInfo courseInfo;
    private List<LuStatementInfo> luStatementInfoList;
     
    public LuStatementInfo getLuStatementByType(String LuStatementType) {
        for (LuStatementInfo stmtInfo : luStatementInfoList) {
            logger.debug("Found statement type: " + stmtInfo.getType());
            if (stmtInfo.getType().equals(LuStatementType)) {
                return stmtInfo;
            }
        }
        logger.debug("Did not find given Statement Type: " + LuStatementType);
        return null;
    }
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public CluInfo getCourseInfo() {
        return courseInfo;
    }

    public void setCourseInfo(CluInfo courseInfo) {
        this.courseInfo = courseInfo;
    }

    public List<LuStatementInfo> getLuStatementInfoList() {
        return luStatementInfoList;
    }

    public void setLuStatementInfoList(List<LuStatementInfo> luStatementInfoList) {
        this.luStatementInfoList = luStatementInfoList;
    }    
}
