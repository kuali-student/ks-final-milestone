package org.kuali.student.conversion.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.kuali.student.common.versionmanagement.dto.VersionInfo;
import org.kuali.student.r1.common.dto.AmountInfo;
import org.kuali.student.r1.common.dto.CurrencyAmountInfo;
import org.kuali.student.r1.common.dto.MetaInfo;
import org.kuali.student.r1.common.dto.RichTextInfo;
import org.kuali.student.r1.common.dto.TimeAmountInfo;
import org.kuali.student.r1.lum.course.dto.ActivityInfo;
import org.kuali.student.r1.lum.course.dto.CourseCrossListingInfo;
import org.kuali.student.r1.lum.course.dto.CourseExpenditureInfo;
import org.kuali.student.r1.lum.course.dto.CourseFeeInfo;
import org.kuali.student.r1.lum.course.dto.CourseJointInfo;
import org.kuali.student.r1.lum.course.dto.CourseRevenueInfo;
import org.kuali.student.r1.lum.course.dto.CourseVariationInfo;
import org.kuali.student.r1.lum.course.dto.FormatInfo;
import org.kuali.student.r1.lum.course.dto.LoDisplayInfo;
import org.kuali.student.r1.lum.lo.dto.LoCategoryInfo;
import org.kuali.student.r1.lum.lo.dto.LoInfo;
import org.kuali.student.r1.lum.lrc.dto.ResultComponentInfo;
import org.kuali.student.r1.lum.lu.dto.AdminOrgInfo;
import org.kuali.student.r1.lum.lu.dto.AffiliatedOrgInfo;
import org.kuali.student.r1.lum.lu.dto.CluInstructorInfo;

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

    public static List<LoDisplayInfo> getLoDisplayInfoDataList() {
        List<LoDisplayInfo> r1LoDisplayInfoList = new ArrayList<LoDisplayInfo>();
        r1LoDisplayInfoList.add(R1TestDataUtil.getLoDisplayInfoData());
        return r1LoDisplayInfoList;
    }

    public static LoDisplayInfo getLoDisplayInfoData() {
        LoDisplayInfo r1LoDisplayInfo = new LoDisplayInfo();
        r1LoDisplayInfo.setLoCategoryInfoList(R1TestDataUtil.getLoCategoryInfoDataList());
        r1LoDisplayInfo.setLoDisplayInfoList(R1TestDataUtil.getNestedLoDisplayInfoDataList());
        r1LoDisplayInfo.setLoInfo(R1TestDataUtil.getLoInfoData());
        r1LoDisplayInfo.setParentLoRelationid("R1 Parent Lo Relation Id");
        r1LoDisplayInfo.setParentRelType("R1 Parent Rel Type");
        return r1LoDisplayInfo;
    }

    private static List<LoDisplayInfo> getNestedLoDisplayInfoDataList() {
        List<LoDisplayInfo> r1NestedLoDisplayInfoList = new ArrayList<LoDisplayInfo>();
        LoDisplayInfo r1NestedLoDisplayInfo = new LoDisplayInfo();
        r1NestedLoDisplayInfo.setLoCategoryInfoList(R1TestDataUtil.getLoCategoryInfoDataList());
        r1NestedLoDisplayInfo.setLoDisplayInfoList(null);
        r1NestedLoDisplayInfo.setLoInfo(R1TestDataUtil.getLoInfoData());
        r1NestedLoDisplayInfo.setParentLoRelationid("R1 Nested Parent Lo Relation Id");
        r1NestedLoDisplayInfo.setParentRelType("R1 Nested Parent Rel Type");
        r1NestedLoDisplayInfoList.add(r1NestedLoDisplayInfo);
        return r1NestedLoDisplayInfoList;
    }

    public static List<LoCategoryInfo> getLoCategoryInfoDataList() {
        List<LoCategoryInfo> r1LoCatInfoList = new ArrayList<LoCategoryInfo>();
        r1LoCatInfoList.add(R1TestDataUtil.getLoCategoryInfoData());
        return r1LoCatInfoList;
    }

    public static LoCategoryInfo getLoCategoryInfoData() {
        LoCategoryInfo r1LoCategoryInfo = new LoCategoryInfo();
        r1LoCategoryInfo.setAttributes(R1TestDataUtil.getAttributeData());
        r1LoCategoryInfo.setDesc(R1TestDataUtil.getRichTextInfoData());
        r1LoCategoryInfo.setEffectiveDate(new Date());
        r1LoCategoryInfo.setExpirationDate(new Date());
        r1LoCategoryInfo.setId("R1 Id");
        r1LoCategoryInfo.setLoRepository("R1 Repository");
        r1LoCategoryInfo.setMetaInfo(R1TestDataUtil.getMetadataInfoData());
        r1LoCategoryInfo.setName("R1 Name");
        r1LoCategoryInfo.setState("R1 State");
        r1LoCategoryInfo.setType("R1 Type");
        return r1LoCategoryInfo;
    }

    public static LoInfo getLoInfoData() {
        LoInfo r1LoInfo = new LoInfo();
        r1LoInfo.setAttributes(R1TestDataUtil.getAttributeData());
        r1LoInfo.setDesc(R1TestDataUtil.getRichTextInfoData());
        r1LoInfo.setEffectiveDate(new Date());
        r1LoInfo.setExpirationDate(new Date());
        r1LoInfo.setId("R1 Id");
        r1LoInfo.setLoRepositoryKey("R1 Repository Key");
        r1LoInfo.setMetaInfo(R1TestDataUtil.getMetadataInfoData());
        r1LoInfo.setName("R1 Name");
        r1LoInfo.setState("R1 State");
        r1LoInfo.setType("R1 Type");
        return r1LoInfo;
    }

    public static List<ResultComponentInfo> getResultComponentInfoDataList() {
        List<ResultComponentInfo> r1ResultComponentList = new ArrayList<ResultComponentInfo>();
        ResultComponentInfo r1ResultCompInfo = new ResultComponentInfo();
        r1ResultCompInfo.setAttributes(R1TestDataUtil.getAttributeData());
        r1ResultCompInfo.setDesc(R1TestDataUtil.getRichTextInfoData());
        r1ResultCompInfo.setEffectiveDate(new Date());
        r1ResultCompInfo.setExpirationDate(new Date());
        r1ResultCompInfo.setId("R1 Id");
        r1ResultCompInfo.setMetaInfo(R1TestDataUtil.getMetadataInfoData());
        r1ResultCompInfo.setName("R1 Name");
        List<String> resultValues = new ArrayList<String>();
        resultValues.add("R1 Result Value");
        r1ResultCompInfo.setResultValues(resultValues);
        r1ResultCompInfo.setState("R1 State");
        r1ResultCompInfo.setType("R1 Type");
        r1ResultComponentList.add(r1ResultCompInfo);
        return r1ResultComponentList;
    }

    public static List<CourseCrossListingInfo> getCourseCrossListingInfoDataList() {
        List<CourseCrossListingInfo> r1ResultComponentList = new ArrayList<CourseCrossListingInfo>();
        r1ResultComponentList.add(R1TestDataUtil.getCourseCrossListingInfoData());
        return r1ResultComponentList;
    }
    
    public static CourseCrossListingInfo getCourseCrossListingInfoData() {
        CourseCrossListingInfo r1CourseCrossListingInfo = new CourseCrossListingInfo();
        r1CourseCrossListingInfo.setAttributes(R1TestDataUtil.getAttributeData());
        r1CourseCrossListingInfo.setCode("R1 Code");
        r1CourseCrossListingInfo.setCourseNumberSuffix("R1 CourseNumber Suffix");
        r1CourseCrossListingInfo.setDepartment("R1 Department");
        r1CourseCrossListingInfo.setId("R1 Id");
        r1CourseCrossListingInfo.setSubjectArea("R1 Subject Area");
        r1CourseCrossListingInfo.setType("R1 Type");
        return r1CourseCrossListingInfo;
    }

    public static TimeAmountInfo getTimeAmountInfoData() {
        TimeAmountInfo r1TimeAmountInfo = new TimeAmountInfo();
        r1TimeAmountInfo.setAtpDurationTypeKey("R1 Atp Duration Type Key");
        r1TimeAmountInfo.setTimeQuantity(1);
        return r1TimeAmountInfo;
    }

    public static CourseExpenditureInfo getCourseExpenditureInfoData() {
        CourseExpenditureInfo r1CourseExpenditureInfo = new CourseExpenditureInfo();
        r1CourseExpenditureInfo.setAttributes(R1TestDataUtil.getAttributeData());
        r1CourseExpenditureInfo.setAffiliatedOrgs(R1TestDataUtil.getAffiliatedOrgInfoDataList());
        return r1CourseExpenditureInfo;
    }

    public static List<AffiliatedOrgInfo> getAffiliatedOrgInfoDataList() {
        List<AffiliatedOrgInfo> r1List = new ArrayList<AffiliatedOrgInfo>();
        AffiliatedOrgInfo r1AffilOrgInfo = new AffiliatedOrgInfo();
        r1AffilOrgInfo.setEffectiveDate(new Date());
        r1AffilOrgInfo.setExpirationDate(new Date());
        r1AffilOrgInfo.setId("R1 Id");
        r1AffilOrgInfo.setOrgId("R1 Org Id");
        r1AffilOrgInfo.setPercentage(Long.valueOf("1"));
        r1List.add(r1AffilOrgInfo);
        return r1List;
    }

    public static List<CourseFeeInfo> getCourseFeeInfoDataList() {
        List<CourseFeeInfo> r1List = new ArrayList<CourseFeeInfo>();
        r1List.add(R1TestDataUtil.getCourseFeeInfoData());
        return r1List;
    }
    
    public static CourseFeeInfo getCourseFeeInfoData() {
        CourseFeeInfo r1CourseFeeInfo = new CourseFeeInfo();
        r1CourseFeeInfo.setAttributes(R1TestDataUtil.getAttributeData());
        r1CourseFeeInfo.setDescr(R1TestDataUtil.getRichTextInfoData());
        r1CourseFeeInfo.setFeeAmounts(R1TestDataUtil.getCurrencyAmountInfoDataList());
        r1CourseFeeInfo.setFeeType("R1 Fee Type");
        r1CourseFeeInfo.setId("R1 Id");
        r1CourseFeeInfo.setMetaInfo(R1TestDataUtil.getMetadataInfoData());
        r1CourseFeeInfo.setRateType("R1 Rate Type");
        return r1CourseFeeInfo;
    }

    public static List<CurrencyAmountInfo> getCurrencyAmountInfoDataList() {
        List<CurrencyAmountInfo> r1List = new ArrayList<CurrencyAmountInfo>();
        CurrencyAmountInfo r1CurAmountInfo = new CurrencyAmountInfo();
        r1CurAmountInfo.setCurrencyQuantity(1);
        r1CurAmountInfo.setCurrencyTypeKey("R1 Currency Type Key");
        r1CurAmountInfo.setId("R1 Id");
        r1CurAmountInfo.setMetaInfo(R1TestDataUtil.getMetadataInfoData());
        r1List.add(r1CurAmountInfo);
        return r1List;
    }

    public static List<FormatInfo> getFormatInfoDataList() {
        List<FormatInfo> r1List = new ArrayList<FormatInfo>();
        r1List.add(R1TestDataUtil.getFormatInfoData());
        return r1List;
    }
    
    public static FormatInfo getFormatInfoData() {
        FormatInfo r1FormatInfo = new FormatInfo();
        r1FormatInfo.setActivities(R1TestDataUtil.getActivityInfoDataList());
        r1FormatInfo.setAttributes(R1TestDataUtil.getAttributeData());
        r1FormatInfo.setDuration(R1TestDataUtil.getTimeAmountInfoData());
        r1FormatInfo.setId("R1 Id");
        r1FormatInfo.setMetaInfo(R1TestDataUtil.getMetadataInfoData());
        r1FormatInfo.setState("R1 State");
        List<String> r1TermsOffered = new ArrayList<String>();
        r1TermsOffered.add("R1 Terms Offered");
        r1FormatInfo.setTermsOffered(r1TermsOffered);
        r1FormatInfo.setType("R1 Type");
        return r1FormatInfo;
    }

    public static List<ActivityInfo> getActivityInfoDataList() {
        List<ActivityInfo> r1List = new ArrayList<ActivityInfo>();
        r1List.add(R1TestDataUtil.getActivityInfoData());
        return r1List;
    }
    
    public static ActivityInfo getActivityInfoData() {
        ActivityInfo r1ActInfo = new ActivityInfo();
        r1ActInfo.setActivityType("R1 Activity Type");
        r1ActInfo.setAttributes(R1TestDataUtil.getAttributeData());
        r1ActInfo.setContactHours(R1TestDataUtil.getAmountInfoData());
        r1ActInfo.setDefaultEnrollmentEstimate(1);
        r1ActInfo.setDuration(R1TestDataUtil.getTimeAmountInfoData());
        r1ActInfo.setId("R1 Id");
        r1ActInfo.setMetaInfo(R1TestDataUtil.getMetadataInfoData());
        r1ActInfo.setState("R1 State");
        List<String> r1UnitContentOwnerList = new ArrayList<String>();
        r1UnitContentOwnerList.add("R1 Unit Content Owner");
        r1ActInfo.setUnitsContentOwner(r1UnitContentOwnerList);
        return r1ActInfo;
    }

    public static AmountInfo getAmountInfoData() {
        AmountInfo r1AmountInfo = new AmountInfo();
        r1AmountInfo.setUnitQuantity("R1 Unit Quantity");
        r1AmountInfo.setUnitType("R1 Unit Type");
        return r1AmountInfo;
    }
    
    public static List<CluInstructorInfo> getCluInstructorInfoDataList() {
        List<CluInstructorInfo> r1List = new ArrayList<CluInstructorInfo>();
        r1List.add(R1TestDataUtil.getCluInstructorInfoData());
        return r1List;
    }
    
    public static CluInstructorInfo getCluInstructorInfoData() {
        CluInstructorInfo r1CluInstrInfo = new CluInstructorInfo();
        r1CluInstrInfo.setAttributes(R1TestDataUtil.getAttributeData());
        r1CluInstrInfo.setOrgId("R1 Org Id");
        r1CluInstrInfo.setPersonId("R1 Person Id");
        r1CluInstrInfo.setPersonInfoOverride("R1 Person Info Overide");
        return r1CluInstrInfo;
    }
    
    public static List<CourseJointInfo> getCourseJointInfoDataList() {
        List<CourseJointInfo> r1CourseJointInfoList = new ArrayList<CourseJointInfo>();
        CourseJointInfo r1CourseJointInfo = new CourseJointInfo();
        r1CourseJointInfo.setCourseId("R1 Course Id");
        r1CourseJointInfo.setCourseNumberSuffix("R1 Course Number Suffix");
        r1CourseJointInfo.setCourseTitle("R1 Course Title");
        r1CourseJointInfo.setRelationId("R1 Relation Id");
        r1CourseJointInfo.setSubjectArea("R1 Subject Area");
        r1CourseJointInfo.setType("R1 Type");
        r1CourseJointInfoList.add(r1CourseJointInfo);
        return r1CourseJointInfoList;
    }
    
    public static List<CourseRevenueInfo> getCourseRevenueInfoDataList() {
        List<CourseRevenueInfo> r1CourseRevenueInfoList = new ArrayList<CourseRevenueInfo>();
        r1CourseRevenueInfoList.add(R1TestDataUtil.getCourseRevenueInfoData());
        return r1CourseRevenueInfoList;
    }
    
    public static CourseRevenueInfo getCourseRevenueInfoData() {
        CourseRevenueInfo r1CourseRevenueInfo = new CourseRevenueInfo();
        r1CourseRevenueInfo.setAffiliatedOrgs(R1TestDataUtil.getAffiliatedOrgInfoDataList());
        r1CourseRevenueInfo.setAttributes(R1TestDataUtil.getAttributeData());
        r1CourseRevenueInfo.setFeeType("R1 Fee Type");
        r1CourseRevenueInfo.setId("R1 Id");
        r1CourseRevenueInfo.setMetaInfo(R1TestDataUtil.getMetadataInfoData());
        return r1CourseRevenueInfo;
    }
    
    public static List<CourseVariationInfo> getCourseVariationInfoDataList() {
        List<CourseVariationInfo> r1CourseVarInfoList = new ArrayList<CourseVariationInfo>();
        CourseVariationInfo r1CourseVarInfo = new CourseVariationInfo();
        r1CourseVarInfo.setCourseNumberSuffix("R1 Course Number Suffix");
        r1CourseVarInfo.setId("R1 Id");
        r1CourseVarInfo.setSubjectArea("R1 Subject Area");
        r1CourseVarInfo.setType("R1 Type");
        r1CourseVarInfo.setVariationCode("R1 Variation Code");
        r1CourseVarInfo.setVariationTitle("R1 Variation Title");
        r1CourseVarInfoList.add(r1CourseVarInfo);
        return r1CourseVarInfoList;
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

}
