package org.kuali.student.lum.conversion;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.kuali.student.r1.common.search.dto.SearchParam;
import org.kuali.student.r1.core.statement.dto.ReqCompFieldInfo;
import org.kuali.student.r1.core.statement.dto.ReqComponentInfo;
import org.kuali.student.r1.core.statement.dto.StatementOperatorTypeKey;
import org.kuali.student.r1.core.statement.dto.StatementTreeViewInfo;
import org.kuali.student.r1.lum.lu.dto.AccreditationInfo;
import org.kuali.student.r1.lum.lu.dto.AdminOrgInfo;
import org.kuali.student.r1.lum.lu.dto.AffiliatedOrgInfo;
import org.kuali.student.r1.lum.lu.dto.CluAccountingInfo;
import org.kuali.student.r1.lum.lu.dto.CluCluRelationInfo;
import org.kuali.student.r1.lum.lu.dto.CluFeeInfo;
import org.kuali.student.r1.lum.lu.dto.CluFeeRecordInfo;
import org.kuali.student.r1.lum.lu.dto.CluIdentifierInfo;
import org.kuali.student.r1.lum.lu.dto.CluInfo;
import org.kuali.student.r1.lum.lu.dto.CluLoRelationInfo;
import org.kuali.student.r1.lum.lu.dto.CluPublicationInfo;
import org.kuali.student.r1.lum.lu.dto.CluResultInfo;
import org.kuali.student.r1.lum.lu.dto.CluSetInfo;
import org.kuali.student.r1.lum.lu.dto.CluSetTreeViewInfo;
import org.kuali.student.r1.lum.lu.dto.FieldInfo;
import org.kuali.student.r1.lum.lu.dto.LuCodeInfo;
import org.kuali.student.r1.lum.lu.dto.LuDocRelationInfo;
import org.kuali.student.r1.lum.lu.dto.MembershipQueryInfo;
import org.kuali.student.r1.lum.lu.dto.ResultOptionInfo;
import org.kuali.student.r2.common.dto.AmountInfo;
import org.kuali.student.r2.common.dto.AttributeInfo;
import org.kuali.student.r2.common.dto.CurrencyAmountInfo;
import org.kuali.student.r2.common.dto.MetaInfo;
import org.kuali.student.r2.common.dto.RichTextInfo;
import org.kuali.student.r2.common.dto.TimeAmountInfo;
import org.kuali.student.r2.core.versionmanagement.dto.VersionInfo;
import org.kuali.student.r2.lum.clu.dto.CluInstructorInfo;
import org.kuali.student.r2.lum.lrc.dto.ResultValuesGroupInfo;

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
    
    public static List<AttributeInfo> getAttributeListData() {
        List<AttributeInfo> r2Attributes = new ArrayList<AttributeInfo>();
        AttributeInfo attr = new AttributeInfo();
        attr.setKey("R1-Key");
        attr.setValue("R1-Value");
        r2Attributes.add(attr);
        return r2Attributes;
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

    public static VersionInfo getVersionInfoData() {
        VersionInfo r1VersionInfo = new VersionInfo();
        r1VersionInfo.setVersionIndId("R1 Version Ind Id");
        r1VersionInfo.setVersionedFromId("R1 Versioned From Id");
        r1VersionInfo.setVersionComment("R1 Version Comment");
        r1VersionInfo.setSequenceNumber(Long.parseLong("1"));
        r1VersionInfo.setCurrentVersionStart(new Date());
        r1VersionInfo.setCurrentVersionEnd(new Date());
        return r1VersionInfo;
    }

    public static List<FieldInfo> getFieldInfoDataList() {
    	List<FieldInfo> r1FieldInfoList = new ArrayList<FieldInfo>();
    	r1FieldInfoList.add(R1TestDataUtil.getFieldInfoData());
        return r1FieldInfoList;
    }
    
    public static FieldInfo getFieldInfoData() {
    	FieldInfo r1FieldInfo = new FieldInfo();
    	r1FieldInfo.setId("R1 Field Id");
    	r1FieldInfo.setValue("R1 Field Value");	
        return r1FieldInfo;
    }
    
    public static CluInfo getCluInfoData() {
    	CluInfo r1CluInfo= new CluInfo();
    	r1CluInfo.setId("R1 Field Id");
    	r1CluInfo.setAccountingInfo(R1TestDataUtil.getCluAccountingInfoData());	
    	r1CluInfo.setAccreditations(R1TestDataUtil.getAccreditationInfoDataList());
    	r1CluInfo.setAdminOrgs(R1TestDataUtil.getAdminOrgInfoDataList());
    	r1CluInfo.setAlternateIdentifiers(getCluIdentifierInfoDataList());
    	r1CluInfo.setAttributes(getAttributeData());
    	r1CluInfo.setCampusLocations(null);
    	r1CluInfo.setCanCreateLui(false);
    	r1CluInfo.setDefaultEnrollmentEstimate(0);
    	r1CluInfo.setDefaultMaximumEnrollment(0);
    	r1CluInfo.setDescr(R1TestDataUtil.getRichTextInfoData());
    	r1CluInfo.setEffectiveDate(new Date());
    	r1CluInfo.setExpectedFirstAtp("Expected FirstAtp");
    	r1CluInfo.setExpirationDate(new Date());
    	r1CluInfo.setFeeInfo(getCluFeeInfoData());
    	r1CluInfo.setHasEarlyDropDeadline(false);
    	r1CluInfo.setHazardousForDisabledStudents(false);
    	r1CluInfo.setInstructors(getCluInstructorInfoDataList());
    	r1CluInfo.setIntensity(getAmountInfoData());
    	r1CluInfo.setLastAdmitAtp("last AdmitAtp");
    	r1CluInfo.setLastAtp("lastAtp");
    	r1CluInfo.setLuCodes(getLuCodeInfoDataList());
    	r1CluInfo.setMetaInfo(getMetadataInfoData());
    	r1CluInfo.setNextReviewPeriod("next Review Period");
    	r1CluInfo.setOfferedAtpTypes(null);
    	r1CluInfo.setOfficialIdentifier(getCluIdentifierInfoData());
    	r1CluInfo.setPrimaryInstructor(getCluInstructorInfoData());
    	r1CluInfo.setReferenceURL("Reference URL");
    	r1CluInfo.setState("state");
    	r1CluInfo.setStdDuration(getTimeAmountInfoData());
    	r1CluInfo.setStudySubjectArea("Study Subject Area");
    	r1CluInfo.setType("Type");
    	r1CluInfo.setVersionInfo(getVersionInfoData());
    
        return r1CluInfo;
    }
    
    public static List<ResultValuesGroupInfo> getResultComponentInfoDataList() {
        List<ResultValuesGroupInfo> r1ResultComponentList = new ArrayList<ResultValuesGroupInfo>();
        ResultValuesGroupInfo r1ResultCompInfo = new ResultValuesGroupInfo();
        r1ResultCompInfo.setAttributes(R1TestDataUtil.getAttributeListData());
        r1ResultCompInfo.setDescr(R1TestDataUtil.getRichTextInfoData());
        r1ResultCompInfo.setEffectiveDate(new Date());
        r1ResultCompInfo.setExpirationDate(new Date());
        r1ResultCompInfo.setKey("R1 Id");
        r1ResultCompInfo.setMeta(R1TestDataUtil.getMetadataInfoData());
        r1ResultCompInfo.setName("R1 Name");
        List<String> resultValues = new ArrayList<String>();
        resultValues.add("R1 Result Value");
        r1ResultCompInfo.setResultValueKeys(resultValues);
        r1ResultCompInfo.setStateKey("R1 State");
        r1ResultCompInfo.setTypeKey("R1 Type");
        r1ResultComponentList.add(r1ResultCompInfo);
        return r1ResultComponentList;
    }

    public static TimeAmountInfo getTimeAmountInfoData() {
        TimeAmountInfo r1TimeAmountInfo = new TimeAmountInfo();
        r1TimeAmountInfo.setAtpDurationTypeKey("R1 Atp Duration Type Key");
        r1TimeAmountInfo.setTimeQuantity(1);
        return r1TimeAmountInfo;
    }

    public static AffiliatedOrgInfo getAffiliatedOrgInfoData() {
    	
        AffiliatedOrgInfo r1AffilOrgInfo = new AffiliatedOrgInfo();
        r1AffilOrgInfo.setEffectiveDate(new Date());
        r1AffilOrgInfo.setExpirationDate(new Date());
        r1AffilOrgInfo.setId("R1 Id");
        r1AffilOrgInfo.setOrgId("R1 Org Id");
        r1AffilOrgInfo.setPercentage(Long.valueOf("1"));
        return r1AffilOrgInfo; 
    }

    public static List<AffiliatedOrgInfo> getAffiliatedOrgInfoDataList() {
        List<AffiliatedOrgInfo> r1List = new ArrayList<AffiliatedOrgInfo>();
        AffiliatedOrgInfo r1AffilOrgInfo = R1TestDataUtil.getAffiliatedOrgInfoData();
        r1List.add(r1AffilOrgInfo);
        return r1List;
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

    public static AmountInfo getAmountInfoData() {
        AmountInfo r1AmountInfo = new AmountInfo();
        r1AmountInfo.setUnitQuantity("R1 Unit Quantity");
        r1AmountInfo.setUnitTypeKey("R1 Unit Type");
        return r1AmountInfo;
    }
    
    public static List<CluInstructorInfo> getCluInstructorInfoDataList() {
        List<CluInstructorInfo> r1List = new ArrayList<CluInstructorInfo>();
        r1List.add(R1TestDataUtil.getCluInstructorInfoData());
        return r1List;
    }
    
    public static CluInstructorInfo getCluInstructorInfoData() {
        CluInstructorInfo r1CluInstrInfo = new CluInstructorInfo();
        r1CluInstrInfo.setAttributes(R1TestDataUtil.getAttributeListData());
        r1CluInstrInfo.setOrgId("R1 Org Id");
        r1CluInstrInfo.setPersonId("R1 Person Id");
        r1CluInstrInfo.setPersonInfoOverride("R1 Person Info Overide");
        return r1CluInstrInfo;
    }
    
    public static List<CluLoRelationInfo> getCluLoRelationInfoDataList() {
        List<CluLoRelationInfo> r1List = new ArrayList<CluLoRelationInfo>();
        r1List.add(R1TestDataUtil.getCluLoRelationInfoData());
        return r1List;
    }
    
    public static CluLoRelationInfo getCluLoRelationInfoData() {
    	CluLoRelationInfo r1CluLoRelationInfo= new CluLoRelationInfo();
    	r1CluLoRelationInfo.setAttributes(R1TestDataUtil.getAttributeData());
    	r1CluLoRelationInfo.setCluId("Clu Id");
    	r1CluLoRelationInfo.setEffectiveDate(new Date());
    	r1CluLoRelationInfo.setExpirationDate(new Date());
    	r1CluLoRelationInfo.setId("Id");
    	r1CluLoRelationInfo.setLoId("Lo ID");
    	r1CluLoRelationInfo.setState("State");
    	r1CluLoRelationInfo.setMetaInfo(R1TestDataUtil.getMetadataInfoData());
    	r1CluLoRelationInfo.setType("Type");
    
        return r1CluLoRelationInfo;
    }
    
    public static List<CluPublicationInfo> getCluPublicationInfoDataList() {
        List<CluPublicationInfo> r1List = new ArrayList<CluPublicationInfo>();
        r1List.add(R1TestDataUtil.getCluPublicationInfoData());
        return r1List;
    }
    
    public static CluPublicationInfo getCluPublicationInfoData() {
    	CluPublicationInfo r1CluPublicationInfo= new CluPublicationInfo();
    	r1CluPublicationInfo.setAttributes(R1TestDataUtil.getAttributeData());
    	r1CluPublicationInfo.setCluId("Clu Id");
    	r1CluPublicationInfo.setEffectiveDate(new Date());
    	r1CluPublicationInfo.setExpirationDate(new Date());
    	r1CluPublicationInfo.setId("Id");
    	r1CluPublicationInfo.setStartCycle("Start Cycle");
    	r1CluPublicationInfo.setEndCycle("End Cycle");
    	r1CluPublicationInfo.setState("State");
    	r1CluPublicationInfo.setMetaInfo(R1TestDataUtil.getMetadataInfoData());
    	r1CluPublicationInfo.setType("Type");
    	r1CluPublicationInfo.setVariants(R1TestDataUtil.getFieldInfoDataList());
   
        return r1CluPublicationInfo;
    }
    
    public static List<CluResultInfo> getCluResultInfoDataList() {
        List<CluResultInfo> r1List = new ArrayList<CluResultInfo>();
        r1List.add(R1TestDataUtil.getCluResultInfoData());
        return r1List;
    }
    
    public static CluResultInfo getCluResultInfoData() {
    	CluResultInfo r1CluResultInfo = new CluResultInfo();
    	r1CluResultInfo.setCluId("Clu Id");
    	r1CluResultInfo.setEffectiveDate(new Date());
    	r1CluResultInfo.setExpirationDate(new Date());
    	r1CluResultInfo.setId("Id");
    	r1CluResultInfo.setState("State");
    	r1CluResultInfo.setMetaInfo(R1TestDataUtil.getMetadataInfoData());
    	r1CluResultInfo.setType("Type");
    	r1CluResultInfo.setDesc(R1TestDataUtil.getRichTextInfoData());
    	r1CluResultInfo.setResultOptions(null);
        return r1CluResultInfo;
    }
    
    public static List<CluSetInfo> getCluSetInfoDataList() {
        List<CluSetInfo> r1List = new ArrayList<CluSetInfo>();
        r1List.add(R1TestDataUtil.getCluSetInfoData());
        return r1List;
    }
    
    public static CluSetInfo getCluSetInfoData() {
    	CluSetInfo r1CluSetInfo = new CluSetInfo();
    	r1CluSetInfo.setCluIds(null);
    	r1CluSetInfo.setCluSetIds(null);
    	r1CluSetInfo.setEffectiveDate(new Date());
    	r1CluSetInfo.setExpirationDate(new Date());
    	r1CluSetInfo.setId("Id");
    	r1CluSetInfo.setState("State");
    	r1CluSetInfo.setAttributes(R1TestDataUtil.getAttributeData());
    	r1CluSetInfo.setMetaInfo(R1TestDataUtil.getMetadataInfoData());
    	r1CluSetInfo.setType("Type");
    	r1CluSetInfo.setDescr(R1TestDataUtil.getRichTextInfoData());
    	r1CluSetInfo.setIsReferenceable(false);
    	r1CluSetInfo.setIsReusable(false);
    	r1CluSetInfo.setName("Name");
    	r1CluSetInfo.setAdminOrg(null);
    	r1CluSetInfo.setMembershipQuery(R1TestDataUtil.getMembershipQueryInfoData());
        return r1CluSetInfo;
    }
    
    public static List<CluInfo> getCluInfoDataList() {
        List<CluInfo> r1List = new ArrayList<CluInfo>();
        r1List.add(R1TestDataUtil.getCluInfoData());
        return r1List;
    }
    
    private static List<CluSetTreeViewInfo> getNestedCluSetTreeViewInfoDataList() {
        List<CluSetTreeViewInfo> r1NestedCluSetTreeViewInfoList = new ArrayList<CluSetTreeViewInfo>();
        CluSetTreeViewInfo r1NestedCluSetTreeViewInfo = new CluSetTreeViewInfo();
        r1NestedCluSetTreeViewInfo.setClus(R1TestDataUtil.getCluInfoDataList());
        r1NestedCluSetTreeViewInfo.setCluSets(null);
        r1NestedCluSetTreeViewInfo.setEffectiveDate(new Date());
        r1NestedCluSetTreeViewInfo.setExpirationDate(new Date());
        r1NestedCluSetTreeViewInfo.setId(" Nested Id");
        r1NestedCluSetTreeViewInfo.setState("Nested State");
    	r1NestedCluSetTreeViewInfo.setAttributes(R1TestDataUtil.getAttributeData());
    	r1NestedCluSetTreeViewInfo.setMetaInfo(R1TestDataUtil.getMetadataInfoData());
    	r1NestedCluSetTreeViewInfo.setType("Nested Type");
    	r1NestedCluSetTreeViewInfo.setDescr(R1TestDataUtil.getRichTextInfoData());
    	r1NestedCluSetTreeViewInfo.setIsReferenceable(false);
    	r1NestedCluSetTreeViewInfo.setIsReusable(false);
    	r1NestedCluSetTreeViewInfo.setName("Nested Name");
    	r1NestedCluSetTreeViewInfo.setAdminOrg("Nested Admin Org");

        return r1NestedCluSetTreeViewInfoList;
    }
    public static List<CluSetTreeViewInfo> getCluSetTreeViewInfoDataList() {
        List<CluSetTreeViewInfo> r1List = new ArrayList<CluSetTreeViewInfo>();   
        r1List.add(R1TestDataUtil.getCluSetTreeViewInfoData());
        return r1List;
    }
    
    
    public static CluSetTreeViewInfo getCluSetTreeViewInfoData() {
    	CluSetTreeViewInfo r1CluSetTreeViewInfo = new CluSetTreeViewInfo();
    	r1CluSetTreeViewInfo.setClus(R1TestDataUtil.getCluInfoDataList());
    	r1CluSetTreeViewInfo.setCluSets(R1TestDataUtil.getNestedCluSetTreeViewInfoDataList());
    	r1CluSetTreeViewInfo.setEffectiveDate(new Date());
    	r1CluSetTreeViewInfo.setExpirationDate(new Date());
    	r1CluSetTreeViewInfo.setId("Id");
    	r1CluSetTreeViewInfo.setState("State");
    	r1CluSetTreeViewInfo.setAttributes(R1TestDataUtil.getAttributeData());
    	r1CluSetTreeViewInfo.setMetaInfo(R1TestDataUtil.getMetadataInfoData());
    	r1CluSetTreeViewInfo.setType("Type");
    	r1CluSetTreeViewInfo.setDescr(R1TestDataUtil.getRichTextInfoData());
    	r1CluSetTreeViewInfo.setIsReferenceable(false);
    	r1CluSetTreeViewInfo.setIsReusable(false);
    	r1CluSetTreeViewInfo.setName("Name");
    	r1CluSetTreeViewInfo.setAdminOrg("Admin Org");
        return r1CluSetTreeViewInfo;
    }
    
    public static List<LuDocRelationInfo> getLuDocRelationInfoDataList() {
        List<LuDocRelationInfo> r1List = new ArrayList<LuDocRelationInfo>();   
        r1List.add(R1TestDataUtil.getLuDocRelationInfoData());
        return r1List;
    }
    
    
    public static LuDocRelationInfo getLuDocRelationInfoData() {
    	LuDocRelationInfo r1LuDocRelationInfo = new LuDocRelationInfo();
    	r1LuDocRelationInfo.setDesc(R1TestDataUtil.getRichTextInfoData());
    	r1LuDocRelationInfo.setTitle("Title");
    	r1LuDocRelationInfo.setDocumentId("Document Id");
    	r1LuDocRelationInfo.setCluId("Clud Id");
    	r1LuDocRelationInfo.setEffectiveDate(new Date());
    	r1LuDocRelationInfo.setExpirationDate(new Date());
    	r1LuDocRelationInfo.setId("Id");
    	r1LuDocRelationInfo.setState("State");
    	r1LuDocRelationInfo.setAttributes(R1TestDataUtil.getAttributeData());
    	r1LuDocRelationInfo.setMetaInfo(R1TestDataUtil.getMetadataInfoData());
    	r1LuDocRelationInfo.setType("Type");
       return r1LuDocRelationInfo;
    }
    
    public static List<MembershipQueryInfo> getMembershipQueryInfoDataList() {
        List<MembershipQueryInfo> r1List = new ArrayList<MembershipQueryInfo>();   
        r1List.add(R1TestDataUtil.getMembershipQueryInfoData());
        return r1List;
    }
    
    
    public static MembershipQueryInfo getMembershipQueryInfoData() {
    	MembershipQueryInfo r1MembershipQueryInfo = new MembershipQueryInfo();
    	r1MembershipQueryInfo.setSearchTypeKey("searchTypeKey");
    	r1MembershipQueryInfo.setId("Id");
    	r1MembershipQueryInfo.setQueryParamValueList(R1TestDataUtil.getSearchParamDataList());
        return r1MembershipQueryInfo;
    }
    
    public static List<ResultOptionInfo> getResultOptionInfoDataList() {
        List<ResultOptionInfo> r1List = new ArrayList<ResultOptionInfo>();   
        r1List.add(R1TestDataUtil.getResultOptionInfoData());
        return r1List;
    }
    
    
    public static ResultOptionInfo getResultOptionInfoData() {
    	ResultOptionInfo r1ResultOptionInfo = new ResultOptionInfo();
    	r1ResultOptionInfo.setId("Id");
    	r1ResultOptionInfo.setState("State");
    	r1ResultOptionInfo.setResultComponentId("Result Component Id");
    	r1ResultOptionInfo.setDesc(R1TestDataUtil.getRichTextInfoData());
    	r1ResultOptionInfo.setEffectiveDate(new Date());
    	r1ResultOptionInfo.setExpirationDate(new Date());
    	r1ResultOptionInfo.setMetaInfo(R1TestDataUtil.getMetadataInfoData());
    	r1ResultOptionInfo.setResultUsageTypeKey("Result Usage TypeKey");
       return r1ResultOptionInfo;
    }
    
    public static AdminOrgInfo getAdminOrgInfoData() {
        AdminOrgInfo r1AdminOrgInfo = new AdminOrgInfo();
        r1AdminOrgInfo.setAttributes(R1TestDataUtil.getAttributeData());
        r1AdminOrgInfo.setId("R1 Id");
        r1AdminOrgInfo.setMetaInfo(R1TestDataUtil.getMetadataInfoData());
        r1AdminOrgInfo.setOrgId("R1 Org Id");
        r1AdminOrgInfo.setPrimary(false);
        r1AdminOrgInfo.setType("R1 Type");
        return r1AdminOrgInfo;
    }
    
    public static AccreditationInfo getAccreditationInfoData() {
        AccreditationInfo r1AccrInfo = new AccreditationInfo();
        r1AccrInfo.setAttributes(R1TestDataUtil.getAttributeData());
        r1AccrInfo.setEffectiveDate(new Date());
        r1AccrInfo.setExpirationDate(new Date());
        r1AccrInfo.setId("R1 Id");
        r1AccrInfo.setMetaInfo(R1TestDataUtil.getMetadataInfoData());
        r1AccrInfo.setOrgId("R1 Org Id");
        return r1AccrInfo;
    }
    
    public static List<AccreditationInfo> getAccreditationInfoDataList() {
        List<AccreditationInfo> r1AccrInfoList = new ArrayList<AccreditationInfo>();
        AccreditationInfo r1AccrInfo = R1TestDataUtil.getAccreditationInfoData();
        r1AccrInfoList.add(r1AccrInfo);
        return r1AccrInfoList;
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
        r1.setStatements(null);
        r1.setType("R1 Type");
        return r1;
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
    
    public static List<ReqCompFieldInfo> getReqCompFieldInfoDataList() {
        List<ReqCompFieldInfo> r1ReqCompFieldInfoList = new ArrayList<ReqCompFieldInfo>();
        ReqCompFieldInfo r1ReqCompFieldInfo = new ReqCompFieldInfo();
        r1ReqCompFieldInfo.setId("R1 Id");
        r1ReqCompFieldInfo.setType("R1 Type");
        r1ReqCompFieldInfo.setValue("R1 Value");
        r1ReqCompFieldInfoList.add(r1ReqCompFieldInfo);
        return r1ReqCompFieldInfoList;
    }
    
    public static CluAccountingInfo getCluAccountingInfoData() {
        CluAccountingInfo r1 = new CluAccountingInfo();
        r1.setAffiliatedOrgs(R1TestDataUtil.getAffiliatedOrgInfoDataList());
        r1.setAttributes(R1TestDataUtil.getAttributeData());
        r1.setId("R1 Id");
        return r1;
    }
    
    public static List<AdminOrgInfo> getAdminOrgInfoDataList() {
        List<AdminOrgInfo> r1List = new ArrayList<AdminOrgInfo>();
        AdminOrgInfo r1 = R1TestDataUtil.getAdminOrgInfoData();
        r1List.add(r1);
        return r1List;
    }
    
    public static CluIdentifierInfo getCluIdentifierInfoData() {
        CluIdentifierInfo r1 = new CluIdentifierInfo();
        r1.setAttributes(R1TestDataUtil.getAttributeData());
        r1.setCode("R1 Code");
        r1.setDivision("R1 Division");
        r1.setId("R1 Id");
        r1.setLevel("R1 Level");
        r1.setLongName("R1 Long Name");
        r1.setOrgId("R1 Org Id");
        r1.setShortName("R1 Short Name");
        r1.setState("R1 State");
        r1.setSuffixCode("R1 Suffix Code");
        r1.setType("R1 Type");
        r1.setVariation("R1 Variation");
        return r1;
    }
    
    public static List<CluIdentifierInfo> getCluIdentifierInfoDataList() {
        List<CluIdentifierInfo> r1List = new ArrayList<CluIdentifierInfo>();
        CluIdentifierInfo r1 = R1TestDataUtil.getCluIdentifierInfoData();
        r1List.add(r1);
        return r1List;
    }

    public static CluFeeInfo getCluFeeInfoData() {
        CluFeeInfo r1 = new CluFeeInfo();
        r1.setAttributes(R1TestDataUtil.getAttributeData());
        r1.setCluFeeRecords(R1TestDataUtil.getCluFeeRecordInfoDataList());
        r1.setDescr(R1TestDataUtil.getRichTextInfoData());
        r1.setId("R1 Id");
        r1.setMetaInfo(R1TestDataUtil.getMetadataInfoData());
        return r1;
    }
    
    public static List<CluFeeInfo> getCluFeeInfoDataList() {
    	List<CluFeeInfo> rCluFeeInfoList = new ArrayList<CluFeeInfo>();
        CluFeeInfo rCluFeeInfo = R1TestDataUtil.getCluFeeInfoData();
        rCluFeeInfoList.add(rCluFeeInfo);
        return rCluFeeInfoList;
        
    }
    
    public static CluFeeRecordInfo getCluFeeRecordInfoData() {
        List<CluFeeRecordInfo> r1List = new ArrayList<CluFeeRecordInfo>();
        CluFeeRecordInfo r1 = new CluFeeRecordInfo();
        r1.setAffiliatedOrgs(R1TestDataUtil.getAffiliatedOrgInfoDataList());
        r1.setAttributes(R1TestDataUtil.getAttributeData());
        r1.setDescr(R1TestDataUtil.getRichTextInfoData());
        r1.setFeeAmounts(R1TestDataUtil.getCurrencyAmountInfoDataList());
        r1.setFeeType("R1 Fee Type");
        r1.setId("R1 Id");
        r1.setMetaInfo(R1TestDataUtil.getMetadataInfoData());
        r1.setRateType("R1 Rate Type");
        r1List.add(r1);
        return r1;
    }
    
    public static List<CluFeeRecordInfo> getCluFeeRecordInfoDataList() {
        List<CluFeeRecordInfo> r1List = new ArrayList<CluFeeRecordInfo>();
        CluFeeRecordInfo r1 = R1TestDataUtil.getCluFeeRecordInfoData();
        r1List.add(r1);
        return r1List;
    }

    public static List<LuCodeInfo> getLuCodeInfoDataList() {
        List<LuCodeInfo> r1List = new ArrayList<LuCodeInfo>();
        r1List.add(getLuCodeInfoData());
        return r1List;
    }
    
    public static LuCodeInfo getLuCodeInfoData() {
        LuCodeInfo r1 = new LuCodeInfo();
        r1.setAttributes(R1TestDataUtil.getAttributeData());
        r1.setDescr(R1TestDataUtil.getRichTextInfoData());
        r1.setId("R1 Id");
        r1.setMetaInfo(R1TestDataUtil.getMetadataInfoData());
        r1.setType("R1 Type");
        r1.setValue("R1 Value");
        return r1;
    }
    
    public static List<CluCluRelationInfo> getCluCluRelationInfo(){
    	 List<CluCluRelationInfo> r1CluCluRelationInfoList = new ArrayList<CluCluRelationInfo>();
    	 CluCluRelationInfo r1CluCluRelationInfo = R1TestDataUtil.getCluCluRelationInfoData();
    	 r1CluCluRelationInfoList.add(r1CluCluRelationInfo);
         return r1CluCluRelationInfoList;      
 
    }
    
    public static CluCluRelationInfo getCluCluRelationInfoData() {
    	CluCluRelationInfo r1CluCluRelationInfo= new CluCluRelationInfo();
    	r1CluCluRelationInfo.setAttributes(R1TestDataUtil.getAttributeData());
    	r1CluCluRelationInfo.setEffectiveDate(new Date());
        r1CluCluRelationInfo.setExpirationDate(new Date());
        r1CluCluRelationInfo.setId("R1 Id");
        r1CluCluRelationInfo.setMetaInfo(R1TestDataUtil.getMetadataInfoData());
        r1CluCluRelationInfo.setCluId("R1 Clu Id");
        r1CluCluRelationInfo.setIsCluRelationRequired(false);
        r1CluCluRelationInfo.setRelatedCluId(" E1 Related Clu Id");
        r1CluCluRelationInfo.setState("R1 State");
        r1CluCluRelationInfo.setType("R1 Type");
        return r1CluCluRelationInfo;
    }
    
    public static List<SearchParam> getSearchParamDataList() {
        List<SearchParam> r1List = new ArrayList<SearchParam>();
        r1List.add(getSearchParamData());
        return r1List;
    }
    
    public static SearchParam getSearchParamData() {
        SearchParam r1 = new SearchParam();
        r1.setKey("R1 Key");
        double chance = Math.random();
        if (chance <= 0.5) {
            List<String> values = new ArrayList<String>();
            values.add("R1 Value #1");
            values.add("R1 Value #2");
            r1.setValue(values);
        } else {
            r1.setValue("R1 Value");
        }
        return r1;
    }   

}
