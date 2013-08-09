package org.kuali.student.core.conversion;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.kuali.student.r2.common.dto.CurrencyAmountInfo;
import org.kuali.student.r2.common.dto.MetaInfo;
import org.kuali.student.r2.common.dto.RichTextInfo;
import org.kuali.student.r1.core.statement.dto.ReqCompFieldInfo;
import org.kuali.student.r1.core.statement.dto.ReqComponentInfo;
import org.kuali.student.r1.core.statement.dto.StatementOperatorTypeKey;
import org.kuali.student.r1.core.statement.dto.StatementTreeViewInfo;
import org.kuali.student.r2.core.versionmanagement.dto.VersionInfo;

public class R1TestDataUtil {

    public static RichTextInfo getRichTextInfoData() {
        RichTextInfo r1RichText = new RichTextInfo();
        r1RichText.setPlain("R1 Plain");
        r1RichText.setFormatted("R1 Formatted");
        return r1RichText;
    }

    public static Map<String, String> getAttributeData() {
        Map<String, String> r1Attributes = new HashMap<String, String>();
        r1Attributes.put("R1-Key", "R1-Value");
        return r1Attributes;
    }

    public static MetaInfo getMetadataInfoData() {
        MetaInfo r1MetaInfo = new MetaInfo();
        r1MetaInfo.setCreateId("R1 Meta Create Id");
        r1MetaInfo.setCreateTime(new Date());
        r1MetaInfo.setUpdateId("R1 Update Id");
        r1MetaInfo.setUpdateTime(new Date());
        r1MetaInfo.setVersionInd("R1 Meta Info Version Id");
        return r1MetaInfo;
    }

    public static VersionInfo getVersionData() {
        VersionInfo r1VersionInfo = new VersionInfo();
        r1VersionInfo.setVersionIndId("R1 Version Ind Id");
        r1VersionInfo.setVersionedFromId("R1 Versioned From Id");
        r1VersionInfo.setVersionComment("R1 Version Comment");
        r1VersionInfo.setSequenceNumber(Long.parseLong("1"));
        r1VersionInfo.setCurrentVersionStart(new Date());
        r1VersionInfo.setCurrentVersionEnd(new Date());
        return r1VersionInfo;
    }

    public static List<CurrencyAmountInfo> getCurrencyAmountInfoDataList() {
        List<CurrencyAmountInfo> r1List = new ArrayList<CurrencyAmountInfo>();
        CurrencyAmountInfo r1CurAmountInfo = new CurrencyAmountInfo();
        r1CurAmountInfo.setCurrencyQuantity(1);
        r1CurAmountInfo.setCurrencyTypeKey("R1 Currency Type Key");
        r1CurAmountInfo.setId("R1 Id");
        r1CurAmountInfo.setMeta(R1TestDataUtil.getMetadataInfoData());
        r1List.add(r1CurAmountInfo);
        return r1List;
    }
    
    public static StatementTreeViewInfo getStatementTreeViewInfoData() {
        StatementTreeViewInfo r1 = new StatementTreeViewInfo();
        r1.setAttributes(R1TestDataUtil.getAttributeData());
        r1.setDesc(R1TestDataUtil.getRichTextInfoData());
        r1.setId("R1 Id");
        r1.setMetaInfo(R1TestDataUtil.getMetadataInfoData());
        r1.setName("R1 Name");
        r1.setOperator(StatementOperatorTypeKey.AND);
        r1.setReqComponents(R1TestDataUtil.getReqComponentInfoDataList());
        r1.setState("R1 State");
        r1.setStatements(R1TestDataUtil.getNestedStatementTreeViewInfoDataList());
        r1.setType("R1 Type");
        return r1;
    }
    
    public static List<StatementTreeViewInfo> getNestedStatementTreeViewInfoDataList() {
        List<StatementTreeViewInfo> statements = new ArrayList<StatementTreeViewInfo>();
        //Sub tree 1
        StatementTreeViewInfo subTree1 = new StatementTreeViewInfo();
        subTree1.setDesc(R1TestDataUtil.getRichTextInfoData());
        subTree1.setOperator(StatementOperatorTypeKey.AND);
        subTree1.setType("kuali.statement.type.program.entrance");
        ReqComponentInfo rc1 = new ReqComponentInfo();
        rc1.setDesc(R1TestDataUtil.getRichTextInfoData());
        rc1.setType("kuali.reqComponent.type.course.courseset.completed.all");
        ReqComponentInfo rc2 = new ReqComponentInfo();
        rc2.setDesc(R1TestDataUtil.getRichTextInfoData());
        rc2.setType("kuali.reqComponent.type.course.courseset.gpa.min");
        List<ReqComponentInfo> reqCompList1 = new ArrayList<ReqComponentInfo>(3);
        reqCompList1.add(rc1);
        reqCompList1.add(rc2);
        subTree1.setReqComponents(reqCompList1);
        statements.add(subTree1);
        //Sub tree 2
        StatementTreeViewInfo subTree2 = new StatementTreeViewInfo();
        subTree1.setDesc(R1TestDataUtil.getRichTextInfoData());
        subTree1.setOperator(StatementOperatorTypeKey.AND);
        subTree1.setType("kuali.statement.type.program.entrance");
        ReqComponentInfo rc3 = new ReqComponentInfo();
        rc3.setDesc(R1TestDataUtil.getRichTextInfoData());
        rc3.setType("kuali.reqComponent.type.course.courseset.completed.nof");
        ReqComponentInfo rc4 = new ReqComponentInfo();
        rc4.setDesc(R1TestDataUtil.getRichTextInfoData());
        rc4.setType("kuali.reqComponent.type.course.permission.instructor.required");
        List<ReqComponentInfo> reqCompList2 = new ArrayList<ReqComponentInfo>(3);
        reqCompList2.add(rc3);
        reqCompList2.add(rc4);
        subTree2.setReqComponents(reqCompList2);
        statements.add(subTree2);
        return statements;
    }
    
    public static List<ReqComponentInfo> getReqComponentInfoDataList() {
        List<ReqComponentInfo> r1ReqComponentInfoList = new ArrayList<ReqComponentInfo>();
        ReqComponentInfo r1ReqComponentInfo = new ReqComponentInfo();
        r1ReqComponentInfo.setDesc(R1TestDataUtil.getRichTextInfoData());
        r1ReqComponentInfo.setEffectiveDate(new Date());
        r1ReqComponentInfo.setExpirationDate(new Date());
        r1ReqComponentInfo.setId("R1 Id");
        r1ReqComponentInfo.setMetaInfo(R1TestDataUtil.getMetadataInfoData());
        r1ReqComponentInfo.setNaturalLanguageTranslation("R1 Natural Language Translation");
        r1ReqComponentInfo.setReqCompFields(R1TestDataUtil.getReqCompFieldInfoDataList());
        r1ReqComponentInfo.setState("R1 State");
        r1ReqComponentInfo.setType("R1 Type");
        r1ReqComponentInfoList.add(r1ReqComponentInfo);
        return r1ReqComponentInfoList;
    }
    
    public static ReqComponentInfo getReqComponentInfoData() {
        ReqComponentInfo r1ReqComponentInfo = new ReqComponentInfo();
        r1ReqComponentInfo.setDesc(R1TestDataUtil.getRichTextInfoData());
        r1ReqComponentInfo.setEffectiveDate(new Date());
        r1ReqComponentInfo.setExpirationDate(new Date());
        r1ReqComponentInfo.setId("R1 Id");
        r1ReqComponentInfo.setMetaInfo(R1TestDataUtil.getMetadataInfoData());
        r1ReqComponentInfo.setNaturalLanguageTranslation("R1 Natural Language Translation");
        r1ReqComponentInfo.setReqCompFields(R1TestDataUtil.getReqCompFieldInfoDataList());
        r1ReqComponentInfo.setState("R1 State");
        r1ReqComponentInfo.setType("R1 Type");
        return r1ReqComponentInfo; 
    }
    
    public static List<ReqCompFieldInfo> getReqCompFieldInfoDataList() {
        List<ReqCompFieldInfo> r1ReqCompFieldInfoList = new ArrayList<ReqCompFieldInfo>();
        ReqCompFieldInfo r1ReqCompFieldInfo = new ReqCompFieldInfo();
        r1ReqCompFieldInfo.setId("R1 Id");
        r1ReqCompFieldInfo.setType("R1 Type");
        r1ReqCompFieldInfo.setValue("R1 Value");
        r1ReqCompFieldInfoList.add(r1ReqCompFieldInfo);
        return r1ReqCompFieldInfoList;
    }

}
