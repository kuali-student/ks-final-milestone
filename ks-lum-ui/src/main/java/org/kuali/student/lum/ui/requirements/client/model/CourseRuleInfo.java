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
