/**
 * Copyright 2010 The Kuali Foundation Licensed under the
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

package org.kuali.student.lum.program.client.requirements;

import org.kuali.student.core.dto.MetaInfo;
import org.kuali.student.core.statement.dto.ReqCompFieldInfo;
import org.kuali.student.core.statement.dto.ReqComponentInfo;
import org.kuali.student.core.statement.dto.StatementInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ObjectClonerUtil {

    public static StatementVO clone(StatementVO inStatementVO) {
        StatementVO cloned = doClone(inStatementVO);
        return cloned;
    }
    
    private static StatementVO doClone(StatementVO inStatementVO) {
        StatementVO cloned = null;
        StatementInfo clonedLuStatementInfo = null;

        if (inStatementVO != null) {
            cloned = new StatementVO();
            clonedLuStatementInfo = clone(inStatementVO.getStatementInfo());
            cloned.setStatementInfo(clonedLuStatementInfo);
            if (inStatementVO != null) {
                for (ReqComponentVO reqComponentVO : inStatementVO.getReqComponentVOs()) {
                    cloned.addReqComponentVO(clone(reqComponentVO));
                }
            }
            if (inStatementVO != null) {
                for (StatementVO statementVO : inStatementVO.getStatementVOs()) {
                    cloned.addStatementVO(doClone(statementVO));
                }
            }
            cloned.setCheckBoxOn(inStatementVO.isCheckBoxOn());
        }
        return cloned;
    }
    
    private static StatementInfo clone(StatementInfo inLuStatementInfo) {
        StatementInfo clonedLuStatementInfo = null;
        if (inLuStatementInfo != null) {
            clonedLuStatementInfo = new StatementInfo();
            clonedLuStatementInfo.setName(inLuStatementInfo.getName());
            clonedLuStatementInfo.setDesc(inLuStatementInfo.getDesc());
            clonedLuStatementInfo.setOperator(inLuStatementInfo.getOperator());
            clonedLuStatementInfo.setStatementIds(
                    new ArrayList<String>(inLuStatementInfo.getStatementIds()));
            clonedLuStatementInfo.setReqComponentIds(
                    new ArrayList<String>(inLuStatementInfo.getReqComponentIds()));
            clonedLuStatementInfo.setAttributes(clone(inLuStatementInfo.getAttributes()));
            clonedLuStatementInfo.setMetaInfo(clone(inLuStatementInfo.getMetaInfo()));
            clonedLuStatementInfo.setType(inLuStatementInfo.getType());
            clonedLuStatementInfo.setState(inLuStatementInfo.getState());
            clonedLuStatementInfo.setId(inLuStatementInfo.getId());
        }
        return clonedLuStatementInfo;
    }
    
    private static Map<String, String> clone(Map<String, String> inAttributes) {
        Map<String, String> clonedAttributes = null;
        if (inAttributes != null) {
            clonedAttributes = new HashMap<String, String>();
            inAttributes.putAll(inAttributes);
        }
        return clonedAttributes;
    }
    
    private static MetaInfo clone(MetaInfo inMetaInfo) {
        MetaInfo clonedMetaInfo = null;
        if (inMetaInfo != null) {
            clonedMetaInfo = new MetaInfo();
            clonedMetaInfo.setVersionInd(inMetaInfo.getVersionInd());
            if (inMetaInfo.getCreateTime() != null) {
                clonedMetaInfo.setCreateTime((java.util.Date)inMetaInfo.getCreateTime().clone());
            }
            clonedMetaInfo.setCreateId(inMetaInfo.getCreateId());
            if (inMetaInfo.getUpdateTime() != null) {
                clonedMetaInfo.setUpdateTime((java.util.Date)inMetaInfo.getUpdateTime().clone());
            }
            clonedMetaInfo.setUpdateId(inMetaInfo.getUpdateId());
        }
        return clonedMetaInfo;
    }
    
    private static ReqComponentVO clone(ReqComponentVO inReqComponentVO) {
        ReqComponentVO clonedReqComponentVO = null;
        if (inReqComponentVO != null) {
            clonedReqComponentVO = new ReqComponentVO();
            clonedReqComponentVO.setId(inReqComponentVO.getId());
            clonedReqComponentVO.setReqComponentInfo(clone(inReqComponentVO.getReqComponentInfo()));
            clonedReqComponentVO.setTypeDesc(inReqComponentVO.getTypeDesc());
            clonedReqComponentVO.setDirty(inReqComponentVO.isDirty());
            clonedReqComponentVO.setCheckBoxOn(inReqComponentVO.isCheckBoxOn());
        }
        return clonedReqComponentVO;
    }
    
    public static ReqComponentInfo clone(ReqComponentInfo inReqComponentInfo) {
        ReqComponentInfo clonedReqComponentInfo = null;
        if (inReqComponentInfo != null) {
            clonedReqComponentInfo = new ReqComponentInfo();
            clonedReqComponentInfo.setDesc(inReqComponentInfo.getDesc());
            clonedReqComponentInfo.setReqCompFields(clone(inReqComponentInfo.getReqCompFields()));
            if (inReqComponentInfo.getEffectiveDate() != null) {
                clonedReqComponentInfo.setEffectiveDate((java.util.Date)inReqComponentInfo.getEffectiveDate().clone());
            }
            if (inReqComponentInfo.getExpirationDate() != null) {
                clonedReqComponentInfo.setExpirationDate(
                        (java.util.Date)inReqComponentInfo.getExpirationDate().clone());
            }
            clonedReqComponentInfo.setMetaInfo(clone(inReqComponentInfo.getMetaInfo()));
            clonedReqComponentInfo.setType(inReqComponentInfo.getType());
            clonedReqComponentInfo.setState(inReqComponentInfo.getState());
            clonedReqComponentInfo.setId(inReqComponentInfo.getId());
        }
        return clonedReqComponentInfo;
    }
    
    private static List<ReqCompFieldInfo> clone(List<ReqCompFieldInfo> inReqComponentInfos) {
        List<ReqCompFieldInfo> clonedFields = null;
        if (inReqComponentInfos != null) {
            clonedFields = new ArrayList<ReqCompFieldInfo>();
            for (ReqCompFieldInfo currField : inReqComponentInfos) {
                clonedFields.add(clone(currField));
            }
        }
        return clonedFields;
    }
    
    private static ReqCompFieldInfo clone(ReqCompFieldInfo inReqCompFieldInfo) {
        ReqCompFieldInfo clonedField = null;
        if (inReqCompFieldInfo != null) {
            clonedField = new ReqCompFieldInfo();
            clonedField.setValue(inReqCompFieldInfo.getValue());
            clonedField.setId(inReqCompFieldInfo.getId());
        }
        return clonedField;
    }
    
}
