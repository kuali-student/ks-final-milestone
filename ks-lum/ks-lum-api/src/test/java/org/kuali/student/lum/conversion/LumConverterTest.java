package org.kuali.student.lum.conversion;

import java.util.Date;

import org.junit.Assert;
import org.junit.Test;
import org.kuali.student.common.conversion.util.R1R2ConverterUtil;
import org.kuali.student.r2.lum.clu.dto.AcademicSubjectOrgInfo;
import org.kuali.student.r2.lum.clu.dto.AccreditationInfo;
import org.kuali.student.r2.lum.clu.dto.AdminOrgInfo;
import org.kuali.student.r2.lum.clu.dto.AffiliatedOrgInfo;
import org.kuali.student.r2.lum.clu.dto.CluAccountingInfo;
import org.kuali.student.r2.lum.clu.dto.CluCluRelationInfo;
import org.kuali.student.r2.lum.clu.dto.CluFeeInfo;
import org.kuali.student.r2.lum.clu.dto.CluFeeRecordInfo;
import org.kuali.student.r2.lum.clu.dto.CluIdentifierInfo;
import org.kuali.student.r2.lum.clu.dto.CluInfo;
import org.kuali.student.r2.lum.clu.dto.CluInstructorInfo;
import org.kuali.student.r2.lum.clu.dto.CluLoRelationInfo;
import org.kuali.student.r2.lum.clu.dto.CluPublicationInfo;
import org.kuali.student.r2.lum.clu.dto.CluResultInfo;
import org.kuali.student.r2.lum.clu.dto.CluSetInfo;
import org.kuali.student.r2.lum.clu.dto.CluSetTreeViewInfo;
import org.kuali.student.r2.lum.clu.dto.FieldInfo;
import org.kuali.student.r2.lum.clu.dto.LuCodeInfo;
import org.kuali.student.r2.lum.clu.dto.LuDocRelationInfo;
import org.kuali.student.r2.lum.clu.dto.MembershipQueryInfo;
import org.kuali.student.r2.lum.clu.dto.ResultOptionInfo;
import org.kuali.student.r2.lum.course.dto.ActivityInfo;
import org.kuali.student.r2.lum.course.dto.CourseCrossListingInfo;
import org.kuali.student.r2.lum.course.dto.CourseExpenditureInfo;
import org.kuali.student.r2.lum.course.dto.CourseFeeInfo;
import org.kuali.student.r2.lum.course.dto.CourseInfo;
import org.kuali.student.r2.lum.course.dto.CourseRevenueInfo;
import org.kuali.student.r2.lum.course.dto.FormatInfo;
import org.kuali.student.r2.lum.lo.dto.LoCategoryInfo;
import org.kuali.student.r2.lum.lo.dto.LoLoRelationInfo;
import org.kuali.student.r2.lum.lo.dto.LoRepositoryInfo;
import org.kuali.student.r2.lum.program.dto.CoreProgramInfo;
import org.kuali.student.r2.lum.program.dto.CredentialProgramInfo;
import org.kuali.student.r2.lum.program.dto.HonorsProgramInfo;
import org.kuali.student.r2.lum.program.dto.MajorDisciplineInfo;
import org.kuali.student.r2.lum.program.dto.MinorDisciplineInfo;
import org.kuali.student.r2.lum.program.dto.ProgramRequirementInfo;
import org.kuali.student.r2.lum.program.dto.ProgramVariationInfo;

public class LumConverterTest {

    @Test
    public void testActivityInfo() {
        org.kuali.student.r1.lum.course.dto.ActivityInfo r1 = R1TestDataUtil.getActivityInfoData();
        ActivityInfo r2 = R1R2ConverterUtil.convert(r1, ActivityInfo.class);
        Assert.assertEquals(r1.getState(), r2.getStateKey());
        Assert.assertEquals(r1.getActivityType(), r2.getTypeKey());
        Assert.assertEquals("R1-Value", r2.getAttributes().get(0).getValue());
        Assert.assertEquals(r1.getMetaInfo().getVersionInd(), r2.getMeta().getVersionInd());
        Assert.assertEquals(r1.getContactHours().getUnitType(), r2.getContactHours().getUnitTypeKey());
        Assert.assertEquals(r1.getDuration().getAtpDurationTypeKey(), r2.getDuration().getAtpDurationTypeKey());
    }

    @Test
    public void testCourseCrossListingInfo() {
        org.kuali.student.r1.lum.course.dto.CourseCrossListingInfo r1 = R1TestDataUtil.getCourseCrossListingInfoData();
        CourseCrossListingInfo r2 = R1R2ConverterUtil.convert(r1, CourseCrossListingInfo.class);
        Assert.assertEquals("R1-Value", r2.getAttributes().get(0).getValue());
        Assert.assertEquals(r1.getDepartment(), r2.getDepartment());
        Assert.assertEquals(r1.getType(), r2.getTypeKey());
    }

    @Test
    public void testCourseExpenditureInfo() {
        org.kuali.student.r1.lum.course.dto.CourseExpenditureInfo r1 = R1TestDataUtil.getCourseExpenditureInfoData();
        CourseExpenditureInfo r2 = R1R2ConverterUtil.convert(r1, CourseExpenditureInfo.class);
        Assert.assertEquals("R1-Value", r2.getAttributes().get(0).getValue());
        Assert.assertEquals(r1.getAffiliatedOrgs().get(0).getId(), r2.getAffiliatedOrgs().get(0).getId());
    }

    @Test
    public void testCourseFeeInfo() {
        org.kuali.student.r1.lum.course.dto.CourseFeeInfo r1 = R1TestDataUtil.getCourseFeeInfoData();
        CourseFeeInfo r2 = R1R2ConverterUtil.convert(r1, CourseFeeInfo.class);
        Assert.assertEquals("R1-Value", r2.getAttributes().get(0).getValue());
        Assert.assertEquals(r1.getDescr().getPlain(), r2.getDescr().getPlain());
        Assert.assertEquals(r1.getMetaInfo().getVersionInd(), r2.getMeta().getVersionInd());
        Assert.assertEquals(r1.getFeeAmounts().get(0).getId(), r2.getFeeAmounts().get(0).getId());
    }

    @Test
    public void testCourseInfo() {
        org.kuali.student.r1.lum.course.dto.CourseInfo r1 = new org.kuali.student.r1.lum.course.dto.CourseInfo();
        r1.setAttributes(R1TestDataUtil.getAttributeData());
        r1.setCourseSpecificLOs(R1TestDataUtil.getLoDisplayInfoDataList());
        //TODO KSCM-567 Don't know how to convert this
        //r1.setCreditOptions(R1TestDataUtil.getResultComponentInfoDataList());
        r1.setCrossListings(R1TestDataUtil.getCourseCrossListingInfoDataList());
        r1.setDescr(R1TestDataUtil.getRichTextInfoData());
        r1.setDuration(R1TestDataUtil.getTimeAmountInfoData());
        r1.setExpenditure(R1TestDataUtil.getCourseExpenditureInfoData());
        r1.setFeeJustification(R1TestDataUtil.getRichTextInfoData());
        r1.setFees(R1TestDataUtil.getCourseFeeInfoDataList());
        r1.setFormats(R1TestDataUtil.getFormatInfoDataList());
        r1.setInstructors(R1TestDataUtil.getCluInstructorInfoDataList());
        r1.setJoints(R1TestDataUtil.getCourseJointInfoDataList());
        r1.setMetaInfo(R1TestDataUtil.getMetadataInfoData());
        r1.setOutOfClassHours(R1TestDataUtil.getAmountInfoData());
        r1.setPrimaryInstructor(R1TestDataUtil.getCluInstructorInfoData());
        r1.setRevenues(R1TestDataUtil.getCourseRevenueInfoDataList());
        r1.setVariations(R1TestDataUtil.getCourseVariationInfoDataList());
        r1.setVersionInfo(R1TestDataUtil.getVersionInfoData());
        r1.setState("R1 State");
        r1.setType("R1 Type");
        CourseInfo r2 = R1R2ConverterUtil.convert(r1, CourseInfo.class);
        Assert.assertEquals("R1-Value", r2.getAttributes().get(0).getValue());
        Assert.assertEquals(r1.getCourseSpecificLOs().get(0).getLoCategoryInfoList().get(0).getId(), r2.getCourseSpecificLOs().get(0).getLoCategoryInfoList().get(0).getId());
        Assert.assertEquals(r1.getCrossListings().get(0).getId(), r2.getCrossListings().get(0).getId());
        Assert.assertEquals(r1.getDescr().getPlain(), r2.getDescr().getPlain());
        Assert.assertEquals(r1.getDuration().getAtpDurationTypeKey(), r2.getDuration().getAtpDurationTypeKey());
        Assert.assertEquals(r1.getExpenditure().getAffiliatedOrgs().get(0).getId(), r2.getExpenditure().getAffiliatedOrgs().get(0).getId());
        Assert.assertEquals(r1.getFeeJustification().getPlain(), r2.getFeeJustification().getPlain());
        Assert.assertEquals(r1.getFees().get(0).getId(), r2.getFees().get(0).getId());
        Assert.assertEquals(r1.getFormats().get(0).getId(), r2.getFormats().get(0).getId());
        Assert.assertEquals(r1.getInstructors().get(0).getOrgId(), r2.getInstructors().get(0).getOrgId());
        Assert.assertEquals(r1.getJoints().get(0).getCourseId(), r2.getJoints().get(0).getCourseId());
        Assert.assertEquals(r1.getMetaInfo().getVersionInd(), r2.getMeta().getVersionInd());
        Assert.assertEquals(r1.getOutOfClassHours().getUnitType(), r2.getOutOfClassHours().getUnitTypeKey());
        Assert.assertEquals(r1.getPrimaryInstructor().getPersonId(), r2.getPrimaryInstructor().getPersonId());
        Assert.assertEquals(r1.getRevenues().get(0).getId(), r2.getRevenues().get(0).getId());
        Assert.assertEquals(r1.getVariations().get(0).getId(), r2.getVariations().get(0).getId());
        Assert.assertEquals(r1.getVersionInfo().getVersionIndId(), r2.getVersionInfo().getVersionIndId());
        Assert.assertEquals(r1.getState(), r2.getStateKey());
        Assert.assertEquals(r1.getType(), r2.getTypeKey());
    }

    @Test
    public void testCourseRevenueInfo() {
        org.kuali.student.r1.lum.course.dto.CourseRevenueInfo r1 = R1TestDataUtil.getCourseRevenueInfoData();
        CourseRevenueInfo r2 = R1R2ConverterUtil.convert(r1, CourseRevenueInfo.class);
        Assert.assertEquals(r1.getId(), r2.getId());
        Assert.assertEquals(r1.getFeeType(), r2.getFeeType());
        Assert.assertEquals(r1.getAffiliatedOrgs().get(0).getId(), r2.getAffiliatedOrgs().get(0).getId());
        Assert.assertEquals("R1-Value", r2.getAttributes().get(0).getValue());
        Assert.assertEquals(r1.getMetaInfo().getVersionInd(), r2.getMeta().getVersionInd());
    }

    @Test
    public void testFormatInfo() {
        org.kuali.student.r1.lum.course.dto.FormatInfo r1 = R1TestDataUtil.getFormatInfoData();
        FormatInfo r2 = R1R2ConverterUtil.convert(r1, FormatInfo.class);
        Assert.assertEquals(r1.getId(), r2.getId());
        Assert.assertEquals(r1.getState(), r2.getStateKey());
        Assert.assertEquals(r1.getType(), r2.getTypeKey());
        Assert.assertEquals(r1.getActivities().get(0).getId(), r2.getActivities().get(0).getId());
        Assert.assertEquals(r1.getDuration().getAtpDurationTypeKey(), r2.getDuration().getAtpDurationTypeKey());
        Assert.assertEquals(r1.getTermsOffered().get(0), r2.getTermsOffered().get(0));
        Assert.assertEquals("R1-Value", r2.getAttributes().get(0).getValue());
        Assert.assertEquals(r1.getMetaInfo().getVersionInd(), r2.getMeta().getVersionInd());
        Assert.assertEquals(r1.getState(), r2.getStateKey());
        Assert.assertEquals(r1.getType(), r2.getTypeKey());
    }

    @Test
    public void testLoDisplayInfo() {
        org.kuali.student.r1.lum.course.dto.LoDisplayInfo r1 = R1TestDataUtil.getLoDisplayInfoData();
        org.kuali.student.r2.lum.course.dto.LoDisplayInfo r2 = R1R2ConverterUtil.convert(r1,
                org.kuali.student.r2.lum.course.dto.LoDisplayInfo.class);
        Assert.assertEquals(r1.getParentLoRelationid(), r2.getParentLoRelationid());
        Assert.assertEquals(r1.getParentRelType(), r2.getParentRelType());
        Assert.assertEquals(r1.getLoCategoryInfoList().get(0).getId(), r2.getLoCategoryInfoList().get(0).getId());
        Assert.assertEquals(r1.getLoDisplayInfoList().get(0).getLoInfo().getId(), r2.getLoDisplayInfoList().get(0).getLoInfo().getId());
        Assert.assertEquals(r1.getLoInfo().getName(), r2.getLoInfo().getName());
    }

    @Test
    public void testLoCategoryInfo() {
        org.kuali.student.r1.lum.lo.dto.LoCategoryInfo r1 = R1TestDataUtil.getLoCategoryInfoData();
        LoCategoryInfo r2 = R1R2ConverterUtil.convert(r1, LoCategoryInfo.class);
        Assert.assertEquals(r1.getId(), r2.getId());
        Assert.assertEquals(r1.getLoRepository(), r2.getLoRepositoryKey());
        Assert.assertEquals(r1.getName(), r2.getName());
        Assert.assertEquals(r1.getState(), r2.getStateKey());
        Assert.assertEquals(r1.getType(), r2.getTypeKey());
        Assert.assertEquals(r1.getEffectiveDate(), r2.getEffectiveDate());
        Assert.assertEquals(r1.getExpirationDate(), r2.getExpirationDate());
        Assert.assertEquals("R1-Value", r2.getAttributes().get(0).getValue());
        Assert.assertEquals(r1.getDesc().getPlain(), r2.getDescr().getPlain());
        Assert.assertEquals(r1.getMetaInfo().getVersionInd(), r2.getMeta().getVersionInd());
    }

    @Test
    public void testLoInfo() {
        org.kuali.student.r1.lum.lo.dto.LoInfo r1 = R1TestDataUtil.getLoInfoData();
        org.kuali.student.r2.lum.lo.dto.LoInfo r2 = R1R2ConverterUtil.convert(r1,
                org.kuali.student.r2.lum.lo.dto.LoInfo.class);
        Assert.assertEquals(r1.getId(), r2.getId());
        Assert.assertEquals(r1.getLoRepositoryKey(), r2.getLoRepositoryKey());
        Assert.assertEquals(r1.getName(), r2.getName());
        Assert.assertEquals(r1.getState(), r2.getStateKey());
        Assert.assertEquals(r1.getType(), r2.getTypeKey());
        Assert.assertEquals(r1.getEffectiveDate(), r2.getEffectiveDate());
        Assert.assertEquals(r1.getExpirationDate(), r2.getExpirationDate());
        Assert.assertEquals("R1-Value", r2.getAttributes().get(0).getValue());
        Assert.assertEquals(r1.getDesc().getPlain(), r2.getDescr().getPlain());
        Assert.assertEquals(r1.getMetaInfo().getVersionInd(), r2.getMeta().getVersionInd());
    }

    @Test
    public void testLoLoRelationInfo() {
        org.kuali.student.r1.lum.lo.dto.LoLoRelationInfo r1 = new org.kuali.student.r1.lum.lo.dto.LoLoRelationInfo();
        r1.setEffectiveDate(new Date());
        r1.setExpirationDate(new Date());
        r1.setId("R1 Id");
        r1.setLoId("R1 Id");
        r1.setRelatedLoId("R1 Id");
        r1.setState("R1 State");
        r1.setType("R1 Type");
        r1.setAttributes(R1TestDataUtil.getAttributeData());
        r1.setMetaInfo(R1TestDataUtil.getMetadataInfoData());
        LoLoRelationInfo r2 = R1R2ConverterUtil.convert(r1, LoLoRelationInfo.class);
        Assert.assertEquals(r1.getEffectiveDate(), r2.getEffectiveDate());
        Assert.assertEquals(r1.getExpirationDate(), r2.getExpirationDate());
        Assert.assertEquals(r1.getId(), r2.getId());
        Assert.assertEquals(r1.getLoId(), r2.getLoId());
        Assert.assertEquals(r1.getRelatedLoId(), r2.getRelatedLoId());
        Assert.assertEquals(r1.getState(), r2.getStateKey());
        Assert.assertEquals(r1.getType(), r2.getTypeKey());
        Assert.assertEquals("R1-Value", r2.getAttributes().get(0).getValue());
        Assert.assertEquals(r1.getMetaInfo().getVersionInd(), r2.getMeta().getVersionInd());
    }

    @Test
    public void testLoRepositoryInfo() {
        org.kuali.student.r1.lum.lo.dto.LoRepositoryInfo r1 = new org.kuali.student.r1.lum.lo.dto.LoRepositoryInfo();
        r1.setEffectiveDate(new Date());
        r1.setExpirationDate(new Date());
        // No matching R2 attribute r1.setId("R1 Id");
        r1.setName("R1 Name");
        r1.setRootLoId("R1 Id");
        r1.setAttributes(R1TestDataUtil.getAttributeData());
        r1.setDesc(R1TestDataUtil.getRichTextInfoData());
        r1.setMetaInfo(R1TestDataUtil.getMetadataInfoData());
        LoRepositoryInfo r2 = R1R2ConverterUtil.convert(r1, LoRepositoryInfo.class);
        Assert.assertEquals(r1.getEffectiveDate(), r2.getEffectiveDate());
        Assert.assertEquals(r1.getExpirationDate(), r2.getExpirationDate());
        Assert.assertEquals(r1.getName(), r2.getName());
        Assert.assertEquals(r1.getRootLoId(), r2.getRootLoId());
        Assert.assertEquals("R1-Value", r2.getAttributes().get(0).getValue());
        Assert.assertEquals(r1.getDesc().getPlain(), r2.getDescr().getPlain());
        Assert.assertEquals(r1.getMetaInfo().getVersionInd(), r2.getMeta().getVersionInd());
    }
    
    @Test
    public void testAcademicSubjectOrgInfo() {
        org.kuali.student.r1.lum.lu.dto.AcademicSubjectOrgInfo r1 = new org.kuali.student.r1.lum.lu.dto.AcademicSubjectOrgInfo();
        r1.setOrgId("R1 Org Id");
        AcademicSubjectOrgInfo r2 = R1R2ConverterUtil.convert(r1, AcademicSubjectOrgInfo.class);
        Assert.assertEquals(r1.getOrgId(), r2.getOrgId());
    }
    
    @Test
    public void testAccreditationInfo() {
        org.kuali.student.r1.lum.lu.dto.AccreditationInfo r1 = R1TestDataUtil.getAccreditationInfoData();
        AccreditationInfo r2 = R1R2ConverterUtil.convert(r1, AccreditationInfo.class);
        Assert.assertEquals(r1.getId(), r2.getId());
        Assert.assertEquals(r1.getOrgId(), r2.getOrgId());
        Assert.assertEquals("R1-Value", r2.getAttributes().get(0).getValue());
        Assert.assertEquals(r1.getEffectiveDate(), r2.getEffectiveDate());
        Assert.assertEquals(r1.getExpirationDate(), r2.getExpirationDate());
        Assert.assertEquals(r1.getMetaInfo().getVersionInd(), r2.getMeta().getVersionInd());
    }
    @Test
    public void testAdminOrgInfo(){
        org.kuali.student.r1.lum.lu.dto.AdminOrgInfo r1 = R1TestDataUtil.getAdminOrgInfoData();
        AdminOrgInfo r2 = R1R2ConverterUtil.convert(r1, AdminOrgInfo.class);
        Assert.assertEquals(r1.getId(), r2.getId());
        Assert.assertEquals(r1.getOrgId(), r2.getOrgId());
        Assert.assertEquals(r1.getType(), r2.getTypeKey());
        Assert.assertEquals("R1-Value", r2.getAttributes().get(0).getValue());
        Assert.assertEquals(r1.getMetaInfo().getVersionInd(), r2.getMeta().getVersionInd());
        Assert.assertEquals(r1.isPrimary(), r2.getIsPrimary());   
    }
    
    @Test
    public void testAffiliatedOrgInfo(){
    	org.kuali.student.r1.lum.lu.dto.AffiliatedOrgInfo r1 = R1TestDataUtil.getAffiliatedOrgInfoData();
    	AffiliatedOrgInfo r2 = R1R2ConverterUtil.convert(r1, AffiliatedOrgInfo.class);
    	//No matching R1 field: r2.getAttributes()
    	//No matching R1 field: r2.getMeta()
    	Assert.assertEquals(r1.getId(), r2.getId());
    	Assert.assertEquals(r1.getOrgId(), r2.getOrgId());
    	Assert.assertEquals(r1.getEffectiveDate(), r2.getEffectiveDate());
    	Assert.assertEquals(r1.getExpirationDate(), r2.getExpirationDate());
    	Assert.assertEquals(r1.getPercentage(), r2.getPercentage());
    }
    
    @Test
    public void testCluAccountingInfo(){
    	org.kuali.student.r1.lum.lu.dto.CluAccountingInfo r1 = R1TestDataUtil.getCluAccountingInfoData();
    	CluAccountingInfo r2 = R1R2ConverterUtil.convert(r1, CluAccountingInfo.class);
    	Assert.assertEquals(r1.getId(), r2.getId());
    	Assert.assertEquals(r1.getAffiliatedOrgs().get(0).getId(), r2.getAffiliatedOrgs().get(0).getId());
    	Assert.assertEquals("R1-Value", r2.getAttributes().get(0).getValue());
	
    }
    
    @Test
    public void testCluCluRelationInfo(){
    	org.kuali.student.r1.lum.lu.dto.CluCluRelationInfo r1 = R1TestDataUtil.getCluCluRelationInfoData();
    	CluCluRelationInfo r2 = R1R2ConverterUtil.convert(r1, CluCluRelationInfo.class);
    	Assert.assertEquals(r1.getCluId(), r2.getCluId());
    	Assert.assertEquals(r1.getId(), r2.getId());
    	Assert.assertEquals(r1.getRelatedCluId(), r2.getRelatedCluId());
    	Assert.assertEquals(r1.getState(), r2.getStateKey());
    	Assert.assertEquals(r1.getType(), r2.getTypeKey());
        Assert.assertEquals(r1.getExpirationDate(), r2.getExpirationDate());
        Assert.assertEquals("R1-Value", r2.getAttributes().get(0).getValue());
        Assert.assertEquals(r1.getMetaInfo().getVersionInd(), r2.getMeta().getVersionInd());
        Assert.assertEquals(r1.getIsCluRelationRequired(), r2.getIsCluRelationRequired());
    	
    }
    
    @Test
    public void testCluFeeRecordInfo(){
    	org.kuali.student.r1.lum.lu.dto.CluFeeRecordInfo r1 = R1TestDataUtil.getCluFeeRecordInfoData();
    	CluFeeRecordInfo r2 = R1R2ConverterUtil.convert(r1, CluFeeRecordInfo.class);
    	Assert.assertEquals(r1.getId(), r2.getId());
    	Assert.assertEquals(r1.getDescr().getPlain(), r2.getDescr().getPlain());
    	Assert.assertEquals("R1-Value", r2.getAttributes().get(0).getValue());
    	Assert.assertEquals(r1.getMetaInfo().getVersionInd(), r2.getMeta().getVersionInd());
    	Assert.assertEquals(r1.getFeeType(), r2.getFeeType());
    	Assert.assertEquals(r1.getRateType(), r2.getRateType());
    	Assert.assertEquals(r1.getAffiliatedOrgs().get(0).getId(), r2.getAffiliatedOrgs().get(0).getId());
    	Assert.assertEquals(r1.getFeeAmounts().get(0).getId(), r2.getFeeAmounts().get(0).getId());
    }
    
    @Test
    public void testCluInfo() {
        org.kuali.student.r1.lum.lu.dto.CluInfo r1 = new org.kuali.student.r1.lum.lu.dto.CluInfo();
        r1.setAccountingInfo(R1TestDataUtil.getCluAccountingInfoData());
        r1.setAccreditations(R1TestDataUtil.getAccreditationInfoDataList());
        r1.setAdminOrgs(R1TestDataUtil.getAdminOrgInfoDataList());
        r1.setAlternateIdentifiers(R1TestDataUtil.getCluIdentifierInfoDataList());
        r1.setAttributes(R1TestDataUtil.getAttributeData());
        r1.setCampusLocations(null);
        r1.setCanCreateLui(true);
        r1.setDefaultEnrollmentEstimate(1);
        r1.setDefaultMaximumEnrollment(1);
        r1.setDescr(R1TestDataUtil.getRichTextInfoData());
        r1.setEffectiveDate(new Date());
        r1.setEnrollable(true);
        r1.setExpectedFirstAtp("R1 Expected First Atp");
        r1.setExpirationDate(new Date());
        r1.setFeeInfo(R1TestDataUtil.getCluFeeInfoData());
        r1.setHasEarlyDropDeadline(true);
        r1.setHazardousForDisabledStudents(true);
        r1.setId("R1 Id");
        r1.setInstructors(R1TestDataUtil.getCluInstructorInfoDataList());
        r1.setIntensity(R1TestDataUtil.getAmountInfoData());
        r1.setLastAdmitAtp("R1 Last Admit Atp");
        r1.setLuCodes(R1TestDataUtil.getLuCodeInfoDataList());
        r1.setMetaInfo(R1TestDataUtil.getMetadataInfoData());
        r1.setNextReviewPeriod("R1 Next Review Period");
        r1.setOfferedAtpTypes(null);
        r1.setOfficialIdentifier(R1TestDataUtil.getCluIdentifierInfoData());
        r1.setPrimaryInstructor(R1TestDataUtil.getCluInstructorInfoData());
        r1.setReferenceURL("R1 Reference URL");
        r1.setState("R1 State");
        r1.setStdDuration(R1TestDataUtil.getTimeAmountInfoData());
        r1.setStudySubjectArea("R1 Study Subject Area");
        r1.setType("R1 Type");
        r1.setVersionInfo(R1TestDataUtil.getVersionInfoData());
        CluInfo r2 = R1R2ConverterUtil.convert(r1, CluInfo.class);
        Assert.assertEquals(r1.getAccountingInfo().getId(), r2.getAccountingInfo().getId());
        Assert.assertEquals("R1-Value", r2.getAccountingInfo().getAttributes().get(0).getValue());
        //No matching R1 property: r2.getAccountingInfo().getAffiliatedOrgs().get(0).getAttributes().get(0).getValue()
        //No matching R1 property: r2.getAccountingInfo().getDescr()
        Assert.assertEquals(r1.getAccreditations().get(0).getId(), r2.getAccreditations().get(0).getId());
        Assert.assertEquals("R1-Value", r2.getAccreditations().get(0).getAttributes().get(0).getValue());
        Assert.assertEquals(r1.getAdminOrgs().get(0).getId(), r2.getAdminOrgs().get(0).getId());
        Assert.assertEquals("R1-Value", r2.getAdminOrgs().get(0).getAttributes().get(0).getValue());
        Assert.assertEquals(r1.getAlternateIdentifiers().get(0).getId(), r2.getAlternateIdentifiers().get(0).getId());
        Assert.assertEquals("R1-Value", r2.getAlternateIdentifiers().get(0).getAttributes().get(0).getValue());
        Assert.assertEquals("R1-Value", r2.getAttributes().get(0).getValue());
        Assert.assertEquals(r1.isCanCreateLui(), r2.getCanCreateLui());
        Assert.assertEquals(r1.isEnrollable(), r2.getIsEnrollable());
        Assert.assertEquals(r1.isHasEarlyDropDeadline(), r2.getHasEarlyDropDeadline());
        Assert.assertEquals(r1.isHazardousForDisabledStudents(), r2.GetIsHazardousForDisabledStudents());
        Assert.assertEquals(r1.getDescr().getPlain(), r2.getDescr().getPlain());
        Assert.assertEquals(r1.getFeeInfo().getId(), r2.getFeeInfo().getId());
        Assert.assertEquals("R1-Value", r2.getFeeInfo().getAttributes().get(0).getValue());
        Assert.assertEquals("R1-Value", r2.getFeeInfo().getCluFeeRecords().get(0).getAttributes().get(0).getValue());
        //No matching R1 property: r2.getFeeInfo().getCluFeeRecords().get(0).getAffiliatedOrgs().get(0).getAttributes().get(0).getValue()
        Assert.assertEquals(r1.getFeeInfo().getCluFeeRecords().get(0).getFeeAmounts().get(0).getCurrencyTypeKey(), r2.getFeeInfo().getCluFeeRecords().get(0).getFeeAmounts().get(0).getCurrencyTypeKey());
        Assert.assertEquals(r1.getInstructors().get(0).getPersonId(), r2.getInstructors().get(0).getPersonId());
        Assert.assertEquals("R1-Value", r2.getInstructors().get(0).getAttributes().get(0).getValue());
        Assert.assertEquals(r1.getIntensity().getUnitType(), r2.getIntensity().getUnitTypeKey());
        Assert.assertEquals(r1.getLuCodes().get(0).getId(), r2.getLuCodes().get(0).getId());
        Assert.assertEquals("R1-Value", r2.getLuCodes().get(0).getAttributes().get(0).getValue());
        Assert.assertEquals(r1.getMetaInfo().getVersionInd(), r2.getMeta().getVersionInd());
        Assert.assertEquals(r1.getOfficialIdentifier().getId(), r2.getOfficialIdentifier().getId());
        Assert.assertEquals("R1-Value", r2.getOfficialIdentifier().getAttributes().get(0).getValue());
        Assert.assertEquals(r1.getPrimaryInstructor().getPersonId(), r2.getPrimaryInstructor().getPersonId());
        Assert.assertEquals("R1-Value", r2.getPrimaryInstructor().getAttributes().get(0).getValue());
        Assert.assertEquals(r1.getState(), r2.getStateKey());
        Assert.assertEquals(r1.getStdDuration().getTimeQuantity(), r2.getStdDuration().getTimeQuantity());
        Assert.assertEquals(r1.getType(), r2.getTypeKey());
        Assert.assertEquals(r1.getVersionInfo().getVersionIndId(), r2.getVersionInfo().getVersionIndId());
    }
    
    @Test
    public void testCluFeeInfo() {
        org.kuali.student.r1.lum.lu.dto.CluFeeInfo r1 = R1TestDataUtil.getCluFeeInfoData();
        CluFeeInfo r2 = R1R2ConverterUtil.convert(r1, CluFeeInfo.class);
        Assert.assertEquals(r1.getId(), r2.getId());
        Assert.assertEquals("R1-Value", r2.getAttributes().get(0).getValue());
        Assert.assertEquals(r1.getCluFeeRecords().get(0).getId(), r2.getCluFeeRecords().get(0).getId());
        Assert.assertEquals(r1.getDescr().getPlain(), r2.getDescr().getPlain());
        Assert.assertEquals(r1.getMetaInfo().getVersionInd(), r2.getMeta().getVersionInd());
    }
    
    @Test
    public void testCluIdentifierInfo() {
        org.kuali.student.r1.lum.lu.dto.CluIdentifierInfo r1 = R1TestDataUtil.getCluIdentifierInfoData();
        CluIdentifierInfo r2 = R1R2ConverterUtil.convert(r1, CluIdentifierInfo.class);
        Assert.assertEquals(r1.getId(), r2.getId());
        Assert.assertEquals(r1.getType(), r2.getTypeKey());
        Assert.assertEquals("R1-Value", r2.getAttributes().get(0).getValue());
        Assert.assertEquals(r1.getState(), r2.getStateKey());
    }
    
    @Test
    public void testCluInstructorInfo() {
        org.kuali.student.r1.lum.lu.dto.CluInstructorInfo r1 = R1TestDataUtil.getCluInstructorInfoData();
        CluInstructorInfo r2 = R1R2ConverterUtil.convert(r1, CluInstructorInfo.class);
        Assert.assertEquals(r1.getOrgId(), r2.getOrgId());
        Assert.assertEquals(r1.getPersonId(), r2.getPersonId());
        Assert.assertEquals(r1.getPersonInfoOverride(), r2.getPersonInfoOverride());
        Assert.assertEquals("R1-Value", r2.getAttributes().get(0).getValue());
    }

    @Test 
    public void testCluLoRelationInfo(){
    	 org.kuali.student.r1.lum.lu.dto.CluLoRelationInfo r1 = R1TestDataUtil.getCluLoRelationInfoData();
    	 CluLoRelationInfo r2 = R1R2ConverterUtil.convert(r1, CluLoRelationInfo.class);
    	 Assert.assertEquals(r1.getId(), r2.getId());
         Assert.assertEquals("R1-Value", r2.getAttributes().get(0).getValue());
         Assert.assertEquals(r1.getMetaInfo().getVersionInd(), r2.getMeta().getVersionInd());
         Assert.assertEquals(r1.getType(), r2.getTypeKey());
         Assert.assertEquals(r1.getState(), r2.getStateKey());
         Assert.assertEquals(r1.getCluId(), r2.getCluId());
         Assert.assertEquals(r1.getExpirationDate(), r2.getExpirationDate());
         Assert.assertEquals(r1.getEffectiveDate(), r2.getEffectiveDate());
         Assert.assertEquals(r1.getLoId(), r2.getLoId());
    }
    
    @Test
    public void testCluPublicationInfo(){
    	org.kuali.student.r1.lum.lu.dto.CluPublicationInfo r1 = R1TestDataUtil.getCluPublicationInfoData();
    	CluPublicationInfo r2 = R1R2ConverterUtil.convert(r1, CluPublicationInfo.class);
    	 Assert.assertEquals(r1.getId(), r2.getId());
         Assert.assertEquals("R1-Value", r2.getAttributes().get(0).getValue());
         Assert.assertEquals(r1.getMetaInfo().getVersionInd(), r2.getMeta().getVersionInd());
         Assert.assertEquals(r1.getType(), r2.getTypeKey());
         Assert.assertEquals(r1.getState(), r2.getStateKey());
         Assert.assertEquals(r1.getCluId(), r2.getCluId());
         Assert.assertEquals(r1.getExpirationDate(), r2.getExpirationDate());
         Assert.assertEquals(r1.getEffectiveDate(), r2.getEffectiveDate());
         Assert.assertEquals("R1 Field Value", r2.getVariants().get(0).getValue());
         Assert.assertEquals(r1.getStartCycle(), r2.getStartCycle());
         Assert.assertEquals(r1.getEndCycle(), r2.getEndCycle());
    }
    
    @Test
    public void testCluResultInfo(){
    	org.kuali.student.r1.lum.lu.dto.CluResultInfo r1 = R1TestDataUtil.getCluResultInfoData();
    	CluResultInfo r2 = R1R2ConverterUtil.convert(r1, CluResultInfo.class);
    	Assert.assertEquals(r1.getId(), r2.getId());
        Assert.assertEquals(r1.getMetaInfo().getVersionInd(), r2.getMeta().getVersionInd());
        Assert.assertEquals(r1.getType(), r2.getTypeKey());
        Assert.assertEquals(r1.getState(), r2.getStateKey());
        Assert.assertEquals(r1.getCluId(), r2.getCluId());
        Assert.assertEquals(r1.getDesc().getPlain(), r2.getDescr().getPlain());
        Assert.assertEquals(r1.getExpirationDate(), r2.getExpirationDate());
        Assert.assertEquals(r1.getEffectiveDate(), r2.getEffectiveDate());
        Assert.assertEquals(r1.getResultOptions(), r2.getResultOptions());
    }
    
    @Test
    public void testCluSetInfo(){
    	org.kuali.student.r1.lum.lu.dto.CluSetInfo r1 = R1TestDataUtil.getCluSetInfoData();
    	CluSetInfo r2 = R1R2ConverterUtil.convert(r1, CluSetInfo.class);
    	Assert.assertEquals(r1.getId(), r2.getId());
    	Assert.assertEquals("R1-Value", r2.getAttributes().get(0).getValue());
        Assert.assertEquals(r1.getMetaInfo().getVersionInd(), r2.getMeta().getVersionInd());
        Assert.assertEquals(r1.getType(), r2.getTypeKey());
        Assert.assertEquals(r1.getState(), r2.getStateKey());
        Assert.assertEquals(r1.getDescr().getPlain(), r2.getDescr().getPlain());
        Assert.assertEquals(r1.getCluIds(), r2.getCluIds());
        Assert.assertEquals(r1.getCluSetIds(), r2.getCluSetIds());
        Assert.assertEquals(r1.getExpirationDate(), r2.getExpirationDate());
        Assert.assertEquals(r1.getEffectiveDate(), r2.getEffectiveDate());
        Assert.assertEquals(r1.getIsReferenceable(), r2.getIsReferenceable());
        Assert.assertEquals(r1.getIsReusable(), r2.getIsReusable());
        Assert.assertEquals(r1.getName(), r2.getName());
        Assert.assertEquals(r1.getMembershipQuery(), r2.getMembershipQuery());
    }
    
    @Test
    public void testCluSetTreeViewInfo(){
    	org.kuali.student.r1.lum.lu.dto.CluSetTreeViewInfo r1 = R1TestDataUtil.getCluSetTreeViewInfoData();
    	CluSetTreeViewInfo r2 = R1R2ConverterUtil.convert(r1, CluSetTreeViewInfo.class);
    	Assert.assertEquals(r1.getId(), r2.getId());
    	Assert.assertEquals("R1-Value", r2.getAttributes().get(0).getValue());
        Assert.assertEquals(r1.getMetaInfo().getVersionInd(), r2.getMeta().getVersionInd());
        Assert.assertEquals(r1.getType(), r2.getTypeKey());
        Assert.assertEquals(r1.getState(), r2.getStateKey());
        Assert.assertEquals(r1.getDescr().getPlain(), r2.getDescr().getPlain());
        Assert.assertEquals(r1.getAdminOrg(), r2.getAdminOrg());
        Assert.assertEquals(r1.getName(), r2.getName());
        Assert.assertEquals(r1.getExpirationDate(), r2.getExpirationDate());
        Assert.assertEquals(r1.getEffectiveDate(), r2.getEffectiveDate());
        Assert.assertEquals(r1.getIsReferenceable(), r2.getIsReferenceable());
        Assert.assertEquals(r1.getIsReusable(), r2.getIsReusable());
        Assert.assertEquals(r1.getCluSets(), r2.getCluSets());
        Assert.assertEquals(r1.getClus().get(0).getId(), r2.getClus().get(0).getId());

    }
    
    @Test
    public void testFieldInfo(){
    	org.kuali.student.r1.lum.lu.dto.FieldInfo r1 = R1TestDataUtil.getFieldInfoData();
    	FieldInfo r2 = R1R2ConverterUtil.convert(r1, FieldInfo.class);
    	 Assert.assertEquals(r1.getId(), r2.getId());
    	 Assert.assertEquals(r1.getValue(), r2.getValue());
    	
    }
    
    @Test
    public void testLuCodeInfo(){
    	org.kuali.student.r1.lum.lu.dto.LuCodeInfo r1 = R1TestDataUtil.getLuCodeInfoData();
    	LuCodeInfo r2 = R1R2ConverterUtil.convert(r1, LuCodeInfo.class);
    	 Assert.assertEquals(r1.getId(), r2.getId());
    	 Assert.assertEquals(r1.getValue(), r2.getValue());
    	 Assert.assertEquals(r1.getType(), r2.getTypeKey());
    	 Assert.assertEquals(r1.getDescr().getPlain(), r2.getDescr().getPlain());
    	 Assert.assertEquals(r1.getMetaInfo().getVersionInd(), r2.getMeta().getVersionInd());
    	 Assert.assertEquals("R1-Value", r2.getAttributes().get(0).getValue());
    	
    }
    
    @Test
    public void testLuDocRelationInfo(){
    	org.kuali.student.r1.lum.lu.dto.LuDocRelationInfo r1 = R1TestDataUtil.getLuDocRelationInfoData();
    	LuDocRelationInfo r2 = R1R2ConverterUtil.convert(r1, LuDocRelationInfo.class);
    	 Assert.assertEquals(r1.getId(), r2.getId());
    	 Assert.assertEquals(r1.getDocumentId(), r2.getDocumentId());
    	 Assert.assertEquals(r1.getState(), r2.getStateKey());
    	 Assert.assertEquals(r1.getTitle(), r2.getTitle());
    	 Assert.assertEquals(r1.getCluId(), r2.getCluId());
    	 Assert.assertEquals(r1.getType(), r2.getTypeKey());
    	 Assert.assertEquals(r1.getExpirationDate(), r2.getExpirationDate());
         Assert.assertEquals(r1.getEffectiveDate(), r2.getEffectiveDate());
    	 Assert.assertEquals(r1.getDesc().getPlain(), r2.getDescr().getPlain());
    	 Assert.assertEquals(r1.getMetaInfo().getVersionInd(), r2.getMeta().getVersionInd());
    	 Assert.assertEquals("R1-Value", r2.getAttributes().get(0).getValue());
   	
    }
    
    @Test
    public void testMembershipQueryInfo(){
    	 org.kuali.student.r1.lum.lu.dto.MembershipQueryInfo r1 = R1TestDataUtil.getMembershipQueryInfoData();
    	 MembershipQueryInfo r2 = R1R2ConverterUtil.convert(r1, MembershipQueryInfo.class);
    	 Assert.assertEquals(r1.getId(), r2.getId());
    	 Assert.assertEquals(r1.getSearchTypeKey(), r2.getSearchTypeKey());
    	 Assert.assertEquals(r1.getQueryParamValueList(), r2.getQueryParamValues());
    }
    
    @Test
    public void testResultOptionInfo(){
    	 org.kuali.student.r1.lum.lu.dto.ResultOptionInfo r1 = R1TestDataUtil.getResultOptionInfoData();
    	 ResultOptionInfo r2 = R1R2ConverterUtil.convert(r1, ResultOptionInfo.class);
    	 Assert.assertEquals(r1.getId(), r2.getId());
    	 Assert.assertEquals(r1.getResultComponentId(), r2.getResultComponentId());
    	 Assert.assertEquals(r1.getResultUsageTypeKey(), r2.getResultUsageTypeKey());
    	 Assert.assertEquals(r1.getState(), r2.getStateKey());
    	 Assert.assertEquals(r1.getMetaInfo().getVersionInd(), r2.getMeta().getVersionInd());
    	 Assert.assertEquals(r1.getDesc().getPlain(), r2.getDescr().getPlain());
    	 Assert.assertEquals(r1.getExpirationDate(), r2.getExpirationDate());
         Assert.assertEquals(r1.getEffectiveDate(), r2.getEffectiveDate());
    }
    
    @Test
    public void testCoreProgramInfo() {
        org.kuali.student.r1.lum.program.dto.CoreProgramInfo r1 = R1TestDataUtil.getCoreProgramInfo();
        CoreProgramInfo r2 = R1R2ConverterUtil.convert(r1, CoreProgramInfo.class);
        Assert.assertEquals("R1-Value", r2.getAttributes().get(0).getValue());
        Assert.assertEquals(r1.getCatalogDescr().getPlain(), r2.getCatalogDescr().getPlain());
        Assert.assertEquals(r1.getCode(), r2.getCode());
        Assert.assertEquals(r1.getEndProgramEntryTerm(), r2.getEndProgramEntryTerm());
        Assert.assertEquals(r1.getEndTerm(), r2.getEndTerm());
        Assert.assertEquals(r1.getId(), r2.getId());
        Assert.assertEquals(r1.getLongTitle(), r2.getLongTitle());
        Assert.assertEquals(r1.getReferenceURL(), r2.getReferenceURL());
        Assert.assertEquals(r1.getShortTitle(), r2.getShortTitle());
        Assert.assertEquals(r1.getStartTerm(), r2.getStartTerm());
        Assert.assertEquals(r1.getState(), r2.getStateKey());
        Assert.assertEquals(r1.getTranscriptTitle(), r2.getTranscriptTitle());
        Assert.assertEquals(r1.getType(), r2.getTypeKey());
        Assert.assertEquals(r1.getUniversityClassification(), r2.getUniversityClassification());
        Assert.assertEquals(r1.getDivisionsStudentOversight().get(0), r2.getDivisionsStudentOversight().get(0));
        Assert.assertEquals(r1.getLearningObjectives().get(0).getLoInfo().getId(), r2.getLearningObjectives().get(0).getLoInfo().getId());
        Assert.assertEquals(r1.getProgramRequirements().get(0), r2.getProgramRequirements().get(0));
        Assert.assertEquals(r1.getUnitsContentOwner().get(0), r2.getUnitsContentOwner().get(0));
        Assert.assertEquals(r1.getUnitsStudentOversight().get(0), r2.getUnitsStudentOversight().get(0));
        Assert.assertEquals(r1.getDescr().getPlain(), r2.getDescr().getPlain());
        Assert.assertEquals(r1.getMetaInfo().getVersionInd(), r2.getMeta().getVersionInd());
        Assert.assertEquals(r1.getVersionInfo().getVersionIndId(), r2.getVersion().getVersionIndId());
    }

    @Test
    public void testCredentialProgramInfo() {
        org.kuali.student.r1.lum.program.dto.CredentialProgramInfo r1 = new org.kuali.student.r1.lum.program.dto.CredentialProgramInfo();
        r1.setState("R1 State");
        r1.setType("R1 Type");
        r1.setCredentialProgramType("R1 Credential Program Type");
        r1.setDescr(R1TestDataUtil.getRichTextInfoData());
        r1.setVersionInfo(R1TestDataUtil.getVersionInfoData());
        r1.setAttributes(R1TestDataUtil.getAttributeData());
        r1.setInstitution(R1TestDataUtil.getAdminOrgInfoData());
        r1.setLearningObjectives(R1TestDataUtil.getLoDisplayInfoDataList());
        r1.setMetaInfo(R1TestDataUtil.getMetadataInfoData());
        CredentialProgramInfo r2 = R1R2ConverterUtil.convert(r1, CredentialProgramInfo.class);
        Assert.assertEquals(r1.getState(), r2.getStateKey());
        Assert.assertEquals(r1.getType(), r2.getTypeKey());
        Assert.assertEquals("R1-Value", r2.getAttributes().get(0).getValue());
        Assert.assertEquals(r1.getCredentialProgramType(), r2.getCredentialProgramType());
        Assert.assertEquals(r1.getDescr().getPlain(), r2.getDescr().getPlain());
        Assert.assertEquals(r1.getDescr().getFormatted(), r2.getDescr().getFormatted());
        Assert.assertEquals(r1.getVersionInfo().getVersionIndId(), r2.getVersion().getVersionIndId());
        Assert.assertEquals(r1.getMetaInfo().getVersionInd(), r2.getMeta().getVersionInd());
    }

    @Test
    public void testHonorsProgramInfo() {
        org.kuali.student.r1.lum.program.dto.HonorsProgramInfo r1 = new org.kuali.student.r1.lum.program.dto.HonorsProgramInfo();
        r1.setState("R1 State");
        r1.setType("R1 Type");
        r1.setAttributes(R1TestDataUtil.getAttributeData());
        r1.setMetaInfo(R1TestDataUtil.getMetadataInfoData());
        HonorsProgramInfo r2 = R1R2ConverterUtil.convert(r1, HonorsProgramInfo.class);
        Assert.assertEquals("R1-Value", r2.getAttributes().get(0).getValue());
        Assert.assertEquals(r1.getMetaInfo().getVersionInd(), r2.getMeta().getVersionInd());
        Assert.assertEquals(r1.getState(), r2.getStateKey());
        Assert.assertEquals(r1.getType(), r2.getTypeKey());
    }

    @Test
    public void testMajorDisciplineInfo() {
        org.kuali.student.r1.lum.program.dto.MajorDisciplineInfo r1 = new org.kuali.student.r1.lum.program.dto.MajorDisciplineInfo();
        r1.setAccreditingAgencies(R1TestDataUtil.getAccreditationInfoDataList());
        r1.setAttributes(R1TestDataUtil.getAttributeData());
        r1.setCatalogDescr(R1TestDataUtil.getRichTextInfoData());
        r1.setDescr(R1TestDataUtil.getRichTextInfoData());
        r1.setLearningObjectives(R1TestDataUtil.getLoDisplayInfoDataList());
        r1.setMetaInfo(R1TestDataUtil.getMetadataInfoData());
        r1.setOrgCoreProgram(R1TestDataUtil.getCoreProgramInfo());
        r1.setPublishedInstructors(R1TestDataUtil.getCluInstructorInfoDataList());
        r1.setStdDuration(R1TestDataUtil.getTimeAmountInfoData());
        r1.setVariations(R1TestDataUtil.getProgramVariationInfoDataList());
        r1.setVersionInfo(R1TestDataUtil.getVersionInfoData());
        r1.setState("R1 State");
        r1.setType("R1 Type");
        MajorDisciplineInfo r2 = R1R2ConverterUtil.convert(r1, MajorDisciplineInfo.class);
        Assert.assertEquals(r1.getAccreditingAgencies().get(0).getId(), r2.getAccreditingAgencies().get(0).getId());
        Assert.assertEquals("R1-Value", r2.getAttributes().get(0).getValue());
        Assert.assertEquals(r1.getCatalogDescr().getPlain(), r2.getCatalogDescr().getPlain());
        Assert.assertEquals(r1.getDescr().getPlain(), r2.getDescr().getPlain());
        Assert.assertEquals(r1.getLearningObjectives().get(0).getLoInfo().getId(), r2.getLearningObjectives().get(0).getLoInfo().getId());
        Assert.assertEquals(r1.getOrgCoreProgram().getId(), r2.getOrgCoreProgram().getId());
        Assert.assertEquals(r1.getPublishedInstructors().get(0).getOrgId(), r2.getPublishedInstructors().get(0).getOrgId());
        Assert.assertEquals("R1-Value", r2.getPublishedInstructors().get(0).getAttributes().get(0).getValue());
        Assert.assertEquals(r1.getStdDuration().getTimeQuantity(), r2.getStdDuration().getTimeQuantity());
        Assert.assertEquals(r1.getVariations().get(0).getId(), r2.getVariations().get(0).getId());
        Assert.assertEquals(r1.getMetaInfo().getVersionInd(), r2.getMeta().getVersionInd());
        Assert.assertEquals(r1.getVersionInfo().getVersionIndId(), r2.getVersion().getVersionIndId());
        Assert.assertEquals(r1.getState(), r2.getStateKey());
        Assert.assertEquals(r1.getType(), r2.getTypeKey());
    }

    @Test
    public void testMinorDisciplineInfo() {
        org.kuali.student.r1.lum.program.dto.MinorDisciplineInfo r1 = new org.kuali.student.r1.lum.program.dto.MinorDisciplineInfo();
        r1.setState("R1 State");
        r1.setType("R1 Type");
        r1.setAttributes(R1TestDataUtil.getAttributeData());
        r1.setMetaInfo(R1TestDataUtil.getMetadataInfoData());
        MinorDisciplineInfo r2 = R1R2ConverterUtil.convert(r1, MinorDisciplineInfo.class);
        Assert.assertEquals("R1-Value", r2.getAttributes().get(0).getValue());
        Assert.assertEquals(r1.getMetaInfo().getVersionInd(), r2.getMeta().getVersionInd());
        Assert.assertEquals(r1.getState(), r2.getStateKey());
        Assert.assertEquals(r1.getType(), r2.getTypeKey());
    }

    @Test
    public void testProgramRequirementInfo() {
        org.kuali.student.r1.lum.program.dto.ProgramRequirementInfo r1 = new org.kuali.student.r1.lum.program.dto.ProgramRequirementInfo();
        r1.setAttributes(R1TestDataUtil.getAttributeData());
        r1.setDescr(R1TestDataUtil.getRichTextInfoData());
        r1.setLearningObjectives(R1TestDataUtil.getLoDisplayInfoDataList());
        r1.setMetaInfo(R1TestDataUtil.getMetadataInfoData());
        r1.setStatement(R1TestDataUtil.getStatementTreeViewInfoData());
        r1.setState("R1 State");
        r1.setType("R1 Type");
        ProgramRequirementInfo r2 = R1R2ConverterUtil.convert(r1, ProgramRequirementInfo.class);
        Assert.assertEquals("R1-Value", r2.getAttributes().get(0).getValue());
        Assert.assertEquals(r1.getDescr().getPlain(), r2.getDescr().getPlain());
        Assert.assertEquals(r1.getLearningObjectives().get(0).getLoInfo().getId(), r2.getLearningObjectives().get(0).getLoInfo().getId());
        Assert.assertEquals(r1.getMetaInfo().getVersionInd(), r2.getMeta().getVersionInd());
        Assert.assertEquals(r1.getStatement().getId(), r2.getStatement().getId());
        Assert.assertEquals(r1.getState(), r2.getStateKey());
        Assert.assertEquals(r1.getType(), r2.getTypeKey());
    }

    @Test
    public void testProgramVariationInfo() {
        org.kuali.student.r1.lum.program.dto.ProgramVariationInfo r1 = new org.kuali.student.r1.lum.program.dto.ProgramVariationInfo();
        r1.setAttributes(R1TestDataUtil.getAttributeData());
        r1.setCatalogDescr(R1TestDataUtil.getRichTextInfoData());
        r1.setDescr(R1TestDataUtil.getRichTextInfoData());
        r1.setLearningObjectives(R1TestDataUtil.getLoDisplayInfoDataList());
        r1.setMetaInfo(R1TestDataUtil.getMetadataInfoData());
        r1.setState("R1 State");
        r1.setStdDuration(R1TestDataUtil.getTimeAmountInfoData());
        r1.setType("R1 Type");
        r1.setVersionInfo(R1TestDataUtil.getVersionInfoData());
        ProgramVariationInfo r2 = R1R2ConverterUtil.convert(r1, ProgramVariationInfo.class);
        Assert.assertEquals("R1-Value", r2.getAttributes().get(0).getValue());
        Assert.assertEquals(r1.getCatalogDescr().getPlain(), r2.getCatalogDescr().getPlain());
        Assert.assertEquals(r1.getDescr().getPlain(), r2.getDescr().getPlain());
        Assert.assertEquals(r1.getLearningObjectives().get(0).getLoInfo().getId(), r2.getLearningObjectives().get(0).getLoInfo().getId());
        Assert.assertEquals(r1.getMetaInfo().getVersionInd(), r2.getMeta().getVersionInd());
        Assert.assertEquals(r1.getState(), r2.getStateKey());
        Assert.assertEquals(r1.getStdDuration().getAtpDurationTypeKey(), r2.getStdDuration().getAtpDurationTypeKey());
        Assert.assertEquals(r1.getType(), r2.getTypeKey());
        Assert.assertEquals(r1.getVersionInfo().getVersionIndId(), r2.getVersion().getVersionIndId());
    }

}
