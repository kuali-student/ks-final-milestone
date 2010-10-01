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

package org.kuali.student.common.ui.client.widgets.rules;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.kuali.student.core.dto.MetaInfo;
import org.kuali.student.core.statement.dto.*;

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
    
    public static StatementInfo clone(StatementInfo inStatementInfo) {
        StatementInfo clonedLuStatementInfo = null;
        if (inStatementInfo != null) {
            clonedLuStatementInfo = new StatementInfo();
            clonedLuStatementInfo.setName(inStatementInfo.getName());
            clonedLuStatementInfo.setDesc(inStatementInfo.getDesc());
            clonedLuStatementInfo.setOperator(inStatementInfo.getOperator());
            clonedLuStatementInfo.setStatementIds(new ArrayList<String>(inStatementInfo.getStatementIds()));
            clonedLuStatementInfo.setReqComponentIds(new ArrayList<String>(inStatementInfo.getReqComponentIds()));
            clonedLuStatementInfo.setAttributes(clone(inStatementInfo.getAttributes()));
            clonedLuStatementInfo.setMetaInfo(clone(inStatementInfo.getMetaInfo()));
            clonedLuStatementInfo.setType(inStatementInfo.getType());
            clonedLuStatementInfo.setState(inStatementInfo.getState());
            clonedLuStatementInfo.setId(inStatementInfo.getId());
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
//            clonedReqComponentInfo.setRequiredComponentType(inReqComponentInfo.getRequiredComponentType());
            clonedReqComponentInfo.setNaturalLanguageTranslation(inReqComponentInfo.getNaturalLanguageTranslation());
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

     private static ReqComponentTypeInfo clone(ReqComponentTypeInfo inReqComponentTypeInfo) {
        ReqComponentTypeInfo clonedField = null;
        if (inReqComponentTypeInfo != null) {
            clonedField = new ReqComponentTypeInfo();
            clonedField.setId(inReqComponentTypeInfo.getId());

            List<ReqCompFieldTypeInfo> clonedFieldTypeList  = null;
            if (inReqComponentTypeInfo.getReqCompFieldTypeInfos() != null) {
                clonedFieldTypeList = new ArrayList<ReqCompFieldTypeInfo>();
                for (ReqCompFieldTypeInfo  reqCompFieldTypeInfo : inReqComponentTypeInfo.getReqCompFieldTypeInfos()) {
                    ReqCompFieldTypeInfo clonedFieldType = new ReqCompFieldTypeInfo();
                    clonedFieldType.setId(reqCompFieldTypeInfo.getId());
                    //TODO clone file descriptor fields
                    clonedFieldTypeList.add(clonedFieldType);
                }
            }
            clonedField.setReqCompFieldTypeInfos(clonedFieldTypeList);
        }
        return clonedField;
    }

    public static StatementTreeViewInfo clone(StatementTreeViewInfo inStatementTreeViewInfo) {
        StatementTreeViewInfo clonedStatementTreeViewInfoInfo = null;
        if (inStatementTreeViewInfo != null) {
            clonedStatementTreeViewInfoInfo = new StatementTreeViewInfo();
            clonedStatementTreeViewInfoInfo.setName(inStatementTreeViewInfo.getName());
            clonedStatementTreeViewInfoInfo.setDesc(inStatementTreeViewInfo.getDesc());
            clonedStatementTreeViewInfoInfo.setOperator(inStatementTreeViewInfo.getOperator());
            clonedStatementTreeViewInfoInfo.setAttributes(clone(inStatementTreeViewInfo.getAttributes()));
            clonedStatementTreeViewInfoInfo.setMetaInfo(clone(inStatementTreeViewInfo.getMetaInfo()));
            clonedStatementTreeViewInfoInfo.setType(inStatementTreeViewInfo.getType());
            clonedStatementTreeViewInfoInfo.setState(inStatementTreeViewInfo.getState());
            clonedStatementTreeViewInfoInfo.setId(inStatementTreeViewInfo.getId());
//            clonedStatementTreeViewInfoInfo.setNaturalLanguageTranslation(inStatementTreeViewInfo.getNaturalLanguageTranslation());

            List<StatementTreeViewInfo> inStatements = inStatementTreeViewInfo.getStatements();
            List<ReqComponentInfo> inReqComponentInfos = inStatementTreeViewInfo.getReqComponents();

            if ((inStatements != null) && (!inStatements.isEmpty())) {
                // retrieve all statements
                List<StatementTreeViewInfo> clonedStatementList = new ArrayList<StatementTreeViewInfo>();
                for (StatementTreeViewInfo inStatement : inStatements) {
                    clonedStatementList.add(clone(inStatement)); // inside set the children of this statementTreeViewInfo
                }
                clonedStatementTreeViewInfoInfo.setStatements(clonedStatementList);
            } else if ((inReqComponentInfos != null) && (!inReqComponentInfos.isEmpty())) {
                // retrieve all req. component LEAFS
                List<ReqComponentInfo> clonedReqComponentList = new ArrayList<ReqComponentInfo>();
                for (ReqComponentInfo inReqComponent : inReqComponentInfos) {
                    clonedReqComponentList.add(ObjectClonerUtil.clone(inReqComponent));
                }
                clonedStatementTreeViewInfoInfo.setReqComponents(clonedReqComponentList);
            }
        }
        return clonedStatementTreeViewInfoInfo;
    }
}
