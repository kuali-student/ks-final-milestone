package org.kuali.student.lum.ui.requirements.client.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.kuali.student.core.dto.MetaInfo;
import org.kuali.student.core.ws.binding.JaxbAttributeMapListAdapter;
import org.kuali.student.lum.lu.dto.LuStatementInfo;
import org.kuali.student.lum.lu.dto.ReqCompFieldInfo;
import org.kuali.student.lum.lu.dto.ReqComponentInfo;
import org.kuali.student.lum.lu.typekey.StatementOperatorTypeKey;

public class ObjectClonerUtil {

    public static StatementVO clone(StatementVO inStatementVO) {
        StatementVO cloned = null;
        cloned = doClone(inStatementVO);
//        private LuStatementInfo luStatementInfo;
//        private List<ReqComponentVO> reqComponentVOs;
//        private List<StatementVO> statementVOs;
//        private boolean checkBoxOn;
        return cloned;
    }
    
    private static StatementVO doClone(StatementVO inStatementVO) {
        StatementVO cloned = new StatementVO();
        LuStatementInfo clonedLuStatementInfo = null;

        clonedLuStatementInfo = clone(
                inStatementVO.getLuStatementInfo());
        cloned.setLuStatementInfo(clonedLuStatementInfo);
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
        return cloned;
    }
    
    private static LuStatementInfo clone(LuStatementInfo inLuStatementInfo) {
        LuStatementInfo clonedLuStatementInfo = null;
        if (inLuStatementInfo != null) {
            clonedLuStatementInfo = new LuStatementInfo();
            clonedLuStatementInfo.setName(inLuStatementInfo.getName());
            clonedLuStatementInfo.setDesc(inLuStatementInfo.getDesc());
            clonedLuStatementInfo.setOperator(inLuStatementInfo.getOperator());
            clonedLuStatementInfo.setLuStatementIds(
                    new ArrayList<String>(inLuStatementInfo.getLuStatementIds()));
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
            clonedMetaInfo.setCreateTime((java.util.Date)inMetaInfo.getCreateTime().clone());
            clonedMetaInfo.setCreateId(inMetaInfo.getCreateId());
            clonedMetaInfo.setUpdateTime((java.util.Date)inMetaInfo.getUpdateTime().clone());
            clonedMetaInfo.setUpdateId(inMetaInfo.getUpdateId());
        }
        return clonedMetaInfo;
    }
    
    private static ReqComponentVO clone(ReqComponentVO inReqComponentVO) {
        ReqComponentVO clonedReqComponentVO = null;
        if (inReqComponentVO != null) {
            clonedReqComponentVO = new ReqComponentVO();
            clonedReqComponentVO.setId(inReqComponentVO.getId());
            clonedReqComponentVO.setReqComponentInfo(
                    clone(inReqComponentVO.getReqComponentInfo()));
            clonedReqComponentVO.setTypeDesc(inReqComponentVO.getTypeDesc());
            clonedReqComponentVO.setDirty(inReqComponentVO.isDirty());
            clonedReqComponentVO.setCheckBoxOn(inReqComponentVO.isCheckBoxOn());
        }
        return clonedReqComponentVO;
    }
    
    private static ReqComponentInfo clone(ReqComponentInfo inReqComponentInfo) {
        ReqComponentInfo clonedReqComponentInfo = null;
        if (inReqComponentInfo != null) {
            clonedReqComponentInfo = new ReqComponentInfo();
            clonedReqComponentInfo.setDesc(inReqComponentInfo.getDesc());
            clonedReqComponentInfo.setReqCompField(clone(inReqComponentInfo.getReqCompField()));
            clonedReqComponentInfo.setEffectiveDate(
                    (java.util.Date)inReqComponentInfo.getEffectiveDate().clone());
            clonedReqComponentInfo.setExpirationDate(
                    (java.util.Date)inReqComponentInfo.getExpirationDate().clone());
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
