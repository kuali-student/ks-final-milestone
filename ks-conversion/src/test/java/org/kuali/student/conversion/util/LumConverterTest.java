package org.kuali.student.conversion.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.kuali.student.r1.common.dto.CurrencyAmountInfo;
import org.kuali.student.r1.lum.lu.dto.AffiliatedOrgInfo;
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
        //TODO KSCM-446 Don't know how to convert this
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
