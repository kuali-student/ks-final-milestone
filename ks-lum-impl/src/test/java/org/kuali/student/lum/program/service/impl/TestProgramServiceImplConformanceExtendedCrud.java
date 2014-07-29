/*
 * Copyright 2014 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * 	http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.student.lum.program.service.impl;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.student.common.test.util.RichTextTester;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.IllegalVersionSequencingException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.util.RichTextHelper;
import org.kuali.student.r2.lum.program.dto.CoreProgramInfo;
import org.kuali.student.r2.lum.program.dto.CredentialProgramInfo;
import org.kuali.student.r2.lum.program.dto.HonorsProgramInfo;
import org.kuali.student.r2.lum.program.dto.MajorDisciplineInfo;
import org.kuali.student.r2.lum.program.dto.MinorDisciplineInfo;
import org.kuali.student.r2.lum.program.dto.ProgramRequirementInfo;
import org.kuali.student.r2.lum.program.dto.TrackInfo;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:program-map-service-test-context.xml"})
public class TestProgramServiceImplConformanceExtendedCrud extends TestProgramServiceImplConformanceBaseCrud
{

    // ========================================
    // DTO FIELD SPECIFIC METHODS
    // ========================================

    // ****************************************************
    //           CredentialProgramInfo
    // ****************************************************

    /*
        A method to set the fields for a CredentialProgram in a 'test create' section prior to calling the 'create' operation.
    */
    public void testCrudCredentialProgram_setDTOFieldsForTestCreate(CredentialProgramInfo expected)
    {
        expected.setTypeKey("typeKey01");
        expected.setStateKey("stateKey01");
        //TODO *TYPE = VersionInfo* expected.setVersion("version01");
        expected.setDescr(RichTextHelper.buildRichTextInfo("descr01", "descr01"));
        expected.setCode("code01");
        expected.setShortTitle("shortTitle01");
        expected.setLongTitle("longTitle01");
        expected.setTranscriptTitle("transcriptTitle01");
        expected.setUniversityClassification("universityClassification01");
        expected.setStartTerm("startTerm01");
        expected.setEndTerm("endTerm01");
        expected.setEndProgramEntryTerm("endProgramEntryTerm01");
        //TODO *TYPE = StringList* expected.setDivisionsContentOwner("divisionsContentOwner01");
        //TODO *TYPE = StringList* expected.setDivisionsStudentOversight("divisionsStudentOversight01");
        //TODO *TYPE = StringList* expected.setUnitsContentOwner("unitsContentOwner01");
        //TODO *TYPE = StringList* expected.setUnitsStudentOversight("unitsStudentOversight01");
        //TODO *TYPE = LoDisplayInfoList* expected.setLearningObjectives("learningObjectives01");
        //TODO *TYPE = StringList* expected.setProgramRequirements("programRequirements01");
        //TODO *TYPE = AdminOrgInfo* expected.setInstitution("institution01");
        //TODO *TYPE = StringList* expected.setResultOptions("resultOptions01");
        expected.setProgramLevel("programLevel01");
        //TODO *TYPE = StringList* expected.setCoreProgramIds("coreProgramIds01");
    }

    /*
        A method to test the fields for a CredentialProgram. This is called after:
        - creating a DTO, where actual is the DTO returned by the create operation, and expected is the dto passed in to the create operation
        - reading a DTO after creating it, and actual is the read DTO, and expected is the dto that was created
        - updating a DTO, where actual is DTO returned by the update operation, and expected is the dto that was passed in to the update operation
    */
    public void testCrudCredentialProgram_testDTOFieldsForTestCreateUpdate(CredentialProgramInfo expected, CredentialProgramInfo actual)
    {
        assertEquals (expected.getTypeKey(), actual.getTypeKey());
        assertEquals (expected.getStateKey(), actual.getStateKey());
        //TODO *TYPE = VersionInfo* assertEquals (expected.getVersion(), actual.getVersion());
        new RichTextTester().check(expected.getDescr(), actual.getDescr());
        assertEquals (expected.getCode(), actual.getCode());
        assertEquals (expected.getShortTitle(), actual.getShortTitle());
        assertEquals (expected.getLongTitle(), actual.getLongTitle());
        assertEquals (expected.getTranscriptTitle(), actual.getTranscriptTitle());
        assertEquals (expected.getUniversityClassification(), actual.getUniversityClassification());
        assertEquals (expected.getStartTerm(), actual.getStartTerm());
        assertEquals (expected.getEndTerm(), actual.getEndTerm());
        assertEquals (expected.getEndProgramEntryTerm(), actual.getEndProgramEntryTerm());
        //TODO *TYPE = StringList* assertEquals (expected.getDivisionsContentOwner(), actual.getDivisionsContentOwner());
        //TODO *TYPE = StringList* assertEquals (expected.getDivisionsStudentOversight(), actual.getDivisionsStudentOversight());
        //TODO *TYPE = StringList* assertEquals (expected.getUnitsContentOwner(), actual.getUnitsContentOwner());
        //TODO *TYPE = StringList* assertEquals (expected.getUnitsStudentOversight(), actual.getUnitsStudentOversight());
        //TODO *TYPE = LoDisplayInfoList* assertEquals (expected.getLearningObjectives(), actual.getLearningObjectives());
        //TODO *TYPE = StringList* assertEquals (expected.getProgramRequirements(), actual.getProgramRequirements());
        //TODO *TYPE = AdminOrgInfo* assertEquals (expected.getInstitution(), actual.getInstitution());
        //TODO *TYPE = StringList* assertEquals (expected.getResultOptions(), actual.getResultOptions());
        assertEquals (expected.getProgramLevel(), actual.getProgramLevel());
        //TODO *TYPE = StringList* assertEquals (expected.getCoreProgramIds(), actual.getCoreProgramIds());
    }

    /*
        A method to set the fields for a CredentialProgram in a 'test update' section prior to calling the 'update' operation.
    */
    public void testCrudCredentialProgram_setDTOFieldsForTestUpdate(CredentialProgramInfo expected)
    {
        expected.setTypeKey("typeKey_Updated");
        expected.setStateKey("stateKey_Updated");
        //TODO *TYPE = VersionInfo* expected.setVersion("version_Updated");
        expected.setDescr(RichTextHelper.buildRichTextInfo("descr_Updated", "descr_Updated"));
        expected.setCode("code_Updated");
        expected.setShortTitle("shortTitle_Updated");
        expected.setLongTitle("longTitle_Updated");
        expected.setTranscriptTitle("transcriptTitle_Updated");
        expected.setUniversityClassification("universityClassification_Updated");
        expected.setStartTerm("startTerm_Updated");
        expected.setEndTerm("endTerm_Updated");
        expected.setEndProgramEntryTerm("endProgramEntryTerm_Updated");
        //TODO *TYPE = StringList* expected.setDivisionsContentOwner("divisionsContentOwner_Updated");
        //TODO *TYPE = StringList* expected.setDivisionsStudentOversight("divisionsStudentOversight_Updated");
        //TODO *TYPE = StringList* expected.setUnitsContentOwner("unitsContentOwner_Updated");
        //TODO *TYPE = StringList* expected.setUnitsStudentOversight("unitsStudentOversight_Updated");
        //TODO *TYPE = LoDisplayInfoList* expected.setLearningObjectives("learningObjectives_Updated");
        //TODO *TYPE = StringList* expected.setProgramRequirements("programRequirements_Updated");
        //TODO *TYPE = AdminOrgInfo* expected.setInstitution("institution_Updated");
        //TODO *TYPE = StringList* expected.setResultOptions("resultOptions_Updated");
        expected.setProgramLevel("programLevel_Updated");
        //TODO *TYPE = StringList* expected.setCoreProgramIds("coreProgramIds_Updated");
    }

    /*
        A method to test the fields for a CredentialProgram after an update operation, followed by a read operation,
        where actual is the DTO returned by the read operation, and expected is the dto returned by the update operation.
    */
    public void testCrudCredentialProgram_testDTOFieldsForTestReadAfterUpdate(CredentialProgramInfo expected, CredentialProgramInfo actual)
    {
        assertEquals (expected.getId(), actual.getId());
        assertEquals (expected.getTypeKey(), actual.getTypeKey());
        assertEquals (expected.getStateKey(), actual.getStateKey());
        //TODO *TYPE = VersionInfo* assertEquals (expected.getVersion(), actual.getVersion());
        new RichTextTester().check(expected.getDescr(), actual.getDescr());
        assertEquals (expected.getCode(), actual.getCode());
        assertEquals (expected.getShortTitle(), actual.getShortTitle());
        assertEquals (expected.getLongTitle(), actual.getLongTitle());
        assertEquals (expected.getTranscriptTitle(), actual.getTranscriptTitle());
        assertEquals (expected.getUniversityClassification(), actual.getUniversityClassification());
        assertEquals (expected.getStartTerm(), actual.getStartTerm());
        assertEquals (expected.getEndTerm(), actual.getEndTerm());
        assertEquals (expected.getEndProgramEntryTerm(), actual.getEndProgramEntryTerm());
        //TODO *TYPE = StringList* assertEquals (expected.getDivisionsContentOwner(), actual.getDivisionsContentOwner());
        //TODO *TYPE = StringList* assertEquals (expected.getDivisionsStudentOversight(), actual.getDivisionsStudentOversight());
        //TODO *TYPE = StringList* assertEquals (expected.getUnitsContentOwner(), actual.getUnitsContentOwner());
        //TODO *TYPE = StringList* assertEquals (expected.getUnitsStudentOversight(), actual.getUnitsStudentOversight());
        //TODO *TYPE = LoDisplayInfoList* assertEquals (expected.getLearningObjectives(), actual.getLearningObjectives());
        //TODO *TYPE = StringList* assertEquals (expected.getProgramRequirements(), actual.getProgramRequirements());
        //TODO *TYPE = AdminOrgInfo* assertEquals (expected.getInstitution(), actual.getInstitution());
        //TODO *TYPE = StringList* assertEquals (expected.getResultOptions(), actual.getResultOptions());
        assertEquals (expected.getProgramLevel(), actual.getProgramLevel());
        //TODO *TYPE = StringList* assertEquals (expected.getCoreProgramIds(), actual.getCoreProgramIds());
    }

    /*
        A method to set the fields for a CredentialProgram in the 'test read after update' section.
        This dto is another (second) dto object being created for other tests.
    */
    public void testCrudCredentialProgram_setDTOFieldsForTestReadAfterUpdate(CredentialProgramInfo expected)
    {
        //TODO *TYPE = VersionInfo* expected.setVersion("version_Updated");
        expected.setCode("code_Updated");
        expected.setShortTitle("shortTitle_Updated");
        expected.setLongTitle("longTitle_Updated");
        expected.setTranscriptTitle("transcriptTitle_Updated");
        expected.setUniversityClassification("universityClassification_Updated");
        expected.setStartTerm("startTerm_Updated");
        expected.setEndTerm("endTerm_Updated");
        expected.setEndProgramEntryTerm("endProgramEntryTerm_Updated");
        //TODO *TYPE = StringList* expected.setDivisionsContentOwner("divisionsContentOwner_Updated");
        //TODO *TYPE = StringList* expected.setDivisionsStudentOversight("divisionsStudentOversight_Updated");
        //TODO *TYPE = StringList* expected.setUnitsContentOwner("unitsContentOwner_Updated");
        //TODO *TYPE = StringList* expected.setUnitsStudentOversight("unitsStudentOversight_Updated");
        //TODO *TYPE = LoDisplayInfoList* expected.setLearningObjectives("learningObjectives_Updated");
        //TODO *TYPE = StringList* expected.setProgramRequirements("programRequirements_Updated");
        //TODO *TYPE = AdminOrgInfo* expected.setInstitution("institution_Updated");
        //TODO *TYPE = StringList* expected.setResultOptions("resultOptions_Updated");
        expected.setProgramLevel("programLevel_Updated");
        //TODO *TYPE = StringList* expected.setCoreProgramIds("coreProgramIds_Updated");
    }


    // ****************************************************
    //           MajorDisciplineInfo
    // ****************************************************

    /*
        A method to set the fields for a MajorDiscipline in a 'test create' section prior to calling the 'create' operation.
    */
    public void testCrudMajorDiscipline_setDTOFieldsForTestCreate(MajorDisciplineInfo expected)
    {
        expected.setTypeKey("typeKey01");
        expected.setStateKey("stateKey01");
        //TODO *TYPE = VersionInfo* expected.setVersion("version01");
        expected.setDescr(RichTextHelper.buildRichTextInfo("descr01", "descr01"));
        expected.setCode("code01");
        expected.setShortTitle("shortTitle01");
        expected.setLongTitle("longTitle01");
        expected.setTranscriptTitle("transcriptTitle01");
        expected.setUniversityClassification("universityClassification01");
        expected.setStartTerm("startTerm01");
        expected.setEndTerm("endTerm01");
        expected.setEndProgramEntryTerm("endProgramEntryTerm01");
        //TODO *TYPE = StringList* expected.setDivisionsContentOwner("divisionsContentOwner01");
        //TODO *TYPE = StringList* expected.setDivisionsStudentOversight("divisionsStudentOversight01");
        //TODO *TYPE = StringList* expected.setUnitsContentOwner("unitsContentOwner01");
        //TODO *TYPE = StringList* expected.setUnitsStudentOversight("unitsStudentOversight01");
        //TODO *TYPE = LoDisplayInfoList* expected.setLearningObjectives("learningObjectives01");
        //TODO *TYPE = StringList* expected.setProgramRequirements("programRequirements01");
        expected.setReferenceURL("referenceURL01");
        //TODO *TYPE = RichTextInfo* expected.setCatalogDescr("catalogDescr01");
        //TODO *TYPE = StringList* expected.setCatalogPublicationTargets("catalogPublicationTargets01");
        expected.setIntensity("intensity01");
        expected.setCip2000Code("cip2000Code01");
        expected.setCip2010Code("cip2010Code01");
        expected.setHegisCode("hegisCode01");
        expected.setSelectiveEnrollmentCode("selectiveEnrollmentCode01");
        //TODO *TYPE = Date* expected.setEffectiveDate("effectiveDate01");
        expected.setDiplomaTitle("diplomaTitle01");
        //TODO *TYPE = StringList* expected.setCampusLocations("campusLocations01");
        //TODO *TYPE = StringList* expected.setResultOptions("resultOptions01");
        //TODO *TYPE = TimeAmountInfo* expected.setStdDuration("stdDuration01");
        //TODO *TYPE = StringList* expected.setDivisionsDeployment("divisionsDeployment01");
        //TODO *TYPE = StringList* expected.setDivisionsFinancialResources("divisionsFinancialResources01");
        //TODO *TYPE = StringList* expected.setDivisionsFinancialControl("divisionsFinancialControl01");
        //TODO *TYPE = StringList* expected.setUnitsDeployment("unitsDeployment01");
        //TODO *TYPE = StringList* expected.setUnitsFinancialResources("unitsFinancialResources01");
        //TODO *TYPE = StringList* expected.setUnitsFinancialControl("unitsFinancialControl01");
        expected.setNextReviewPeriod("nextReviewPeriod01");
        //TODO *TYPE = CluInstructorInfoList* expected.setPublishedInstructors("publishedInstructors01");
        expected.setCredentialProgramId("credentialProgramId01");
        //TODO *TYPE = AccreditationInfoList* expected.setAccreditingAgencies("accreditingAgencies01");
        //TODO *TYPE = ProgramVariationInfoList* expected.setVariations("variations01");
        //TODO *TYPE = CoreProgramInfo* expected.setOrgCoreProgram("orgCoreProgram01");
    }

    /*
        A method to test the fields for a MajorDiscipline. This is called after:
        - creating a DTO, where actual is the DTO returned by the create operation, and expected is the dto passed in to the create operation
        - reading a DTO after creating it, and actual is the read DTO, and expected is the dto that was created
        - updating a DTO, where actual is DTO returned by the update operation, and expected is the dto that was passed in to the update operation
    */
    public void testCrudMajorDiscipline_testDTOFieldsForTestCreateUpdate(MajorDisciplineInfo expected, MajorDisciplineInfo actual)
    {
        assertEquals (expected.getTypeKey(), actual.getTypeKey());
        assertEquals (expected.getStateKey(), actual.getStateKey());
        //TODO *TYPE = VersionInfo* assertEquals (expected.getVersion(), actual.getVersion());
        new RichTextTester().check(expected.getDescr(), actual.getDescr());
        assertEquals (expected.getCode(), actual.getCode());
        assertEquals (expected.getShortTitle(), actual.getShortTitle());
        assertEquals (expected.getLongTitle(), actual.getLongTitle());
        assertEquals (expected.getTranscriptTitle(), actual.getTranscriptTitle());
        assertEquals (expected.getUniversityClassification(), actual.getUniversityClassification());
        assertEquals (expected.getStartTerm(), actual.getStartTerm());
        assertEquals (expected.getEndTerm(), actual.getEndTerm());
        assertEquals (expected.getEndProgramEntryTerm(), actual.getEndProgramEntryTerm());
        //TODO *TYPE = StringList* assertEquals (expected.getDivisionsContentOwner(), actual.getDivisionsContentOwner());
        //TODO *TYPE = StringList* assertEquals (expected.getDivisionsStudentOversight(), actual.getDivisionsStudentOversight());
        //TODO *TYPE = StringList* assertEquals (expected.getUnitsContentOwner(), actual.getUnitsContentOwner());
        //TODO *TYPE = StringList* assertEquals (expected.getUnitsStudentOversight(), actual.getUnitsStudentOversight());
        //TODO *TYPE = LoDisplayInfoList* assertEquals (expected.getLearningObjectives(), actual.getLearningObjectives());
        //TODO *TYPE = StringList* assertEquals (expected.getProgramRequirements(), actual.getProgramRequirements());
        assertEquals (expected.getReferenceURL(), actual.getReferenceURL());
        //TODO *TYPE = RichTextInfo* assertEquals (expected.getCatalogDescr(), actual.getCatalogDescr());
        //TODO *TYPE = StringList* assertEquals (expected.getCatalogPublicationTargets(), actual.getCatalogPublicationTargets());
        assertEquals (expected.getIntensity(), actual.getIntensity());
        assertEquals (expected.getCip2000Code(), actual.getCip2000Code());
        assertEquals (expected.getCip2010Code(), actual.getCip2010Code());
        assertEquals (expected.getHegisCode(), actual.getHegisCode());
        assertEquals (expected.getSelectiveEnrollmentCode(), actual.getSelectiveEnrollmentCode());
        //TODO *TYPE = Date* assertEquals (expected.getEffectiveDate(), actual.getEffectiveDate());
        assertEquals (expected.getDiplomaTitle(), actual.getDiplomaTitle());
        //TODO *TYPE = StringList* assertEquals (expected.getCampusLocations(), actual.getCampusLocations());
        //TODO *TYPE = StringList* assertEquals (expected.getResultOptions(), actual.getResultOptions());
        //TODO *TYPE = TimeAmountInfo* assertEquals (expected.getStdDuration(), actual.getStdDuration());
        //TODO *TYPE = StringList* assertEquals (expected.getDivisionsDeployment(), actual.getDivisionsDeployment());
        //TODO *TYPE = StringList* assertEquals (expected.getDivisionsFinancialResources(), actual.getDivisionsFinancialResources());
        //TODO *TYPE = StringList* assertEquals (expected.getDivisionsFinancialControl(), actual.getDivisionsFinancialControl());
        //TODO *TYPE = StringList* assertEquals (expected.getUnitsDeployment(), actual.getUnitsDeployment());
        //TODO *TYPE = StringList* assertEquals (expected.getUnitsFinancialResources(), actual.getUnitsFinancialResources());
        //TODO *TYPE = StringList* assertEquals (expected.getUnitsFinancialControl(), actual.getUnitsFinancialControl());
        assertEquals (expected.getNextReviewPeriod(), actual.getNextReviewPeriod());
        //TODO *TYPE = CluInstructorInfoList* assertEquals (expected.getPublishedInstructors(), actual.getPublishedInstructors());
        assertEquals (expected.getCredentialProgramId(), actual.getCredentialProgramId());
        //TODO *TYPE = AccreditationInfoList* assertEquals (expected.getAccreditingAgencies(), actual.getAccreditingAgencies());
        //TODO *TYPE = ProgramVariationInfoList* assertEquals (expected.getVariations(), actual.getVariations());
        //TODO *TYPE = CoreProgramInfo* assertEquals (expected.getOrgCoreProgram(), actual.getOrgCoreProgram());
    }

    /*
        A method to set the fields for a MajorDiscipline in a 'test update' section prior to calling the 'update' operation.
    */
    public void testCrudMajorDiscipline_setDTOFieldsForTestUpdate(MajorDisciplineInfo expected)
    {
        expected.setTypeKey("typeKey_Updated");
        expected.setStateKey("stateKey_Updated");
        //TODO *TYPE = VersionInfo* expected.setVersion("version_Updated");
        expected.setDescr(RichTextHelper.buildRichTextInfo("descr_Updated", "descr_Updated"));
        expected.setCode("code_Updated");
        expected.setShortTitle("shortTitle_Updated");
        expected.setLongTitle("longTitle_Updated");
        expected.setTranscriptTitle("transcriptTitle_Updated");
        expected.setUniversityClassification("universityClassification_Updated");
        expected.setStartTerm("startTerm_Updated");
        expected.setEndTerm("endTerm_Updated");
        expected.setEndProgramEntryTerm("endProgramEntryTerm_Updated");
        //TODO *TYPE = StringList* expected.setDivisionsContentOwner("divisionsContentOwner_Updated");
        //TODO *TYPE = StringList* expected.setDivisionsStudentOversight("divisionsStudentOversight_Updated");
        //TODO *TYPE = StringList* expected.setUnitsContentOwner("unitsContentOwner_Updated");
        //TODO *TYPE = StringList* expected.setUnitsStudentOversight("unitsStudentOversight_Updated");
        //TODO *TYPE = LoDisplayInfoList* expected.setLearningObjectives("learningObjectives_Updated");
        //TODO *TYPE = StringList* expected.setProgramRequirements("programRequirements_Updated");
        expected.setReferenceURL("referenceURL_Updated");
        //TODO *TYPE = RichTextInfo* expected.setCatalogDescr("catalogDescr_Updated");
        //TODO *TYPE = StringList* expected.setCatalogPublicationTargets("catalogPublicationTargets_Updated");
        expected.setIntensity("intensity_Updated");
        expected.setCip2000Code("cip2000Code_Updated");
        expected.setCip2010Code("cip2010Code_Updated");
        expected.setHegisCode("hegisCode_Updated");
        expected.setSelectiveEnrollmentCode("selectiveEnrollmentCode_Updated");
        //TODO *TYPE = Date* expected.setEffectiveDate("effectiveDate_Updated");
        expected.setDiplomaTitle("diplomaTitle_Updated");
        //TODO *TYPE = StringList* expected.setCampusLocations("campusLocations_Updated");
        //TODO *TYPE = StringList* expected.setResultOptions("resultOptions_Updated");
        //TODO *TYPE = TimeAmountInfo* expected.setStdDuration("stdDuration_Updated");
        //TODO *TYPE = StringList* expected.setDivisionsDeployment("divisionsDeployment_Updated");
        //TODO *TYPE = StringList* expected.setDivisionsFinancialResources("divisionsFinancialResources_Updated");
        //TODO *TYPE = StringList* expected.setDivisionsFinancialControl("divisionsFinancialControl_Updated");
        //TODO *TYPE = StringList* expected.setUnitsDeployment("unitsDeployment_Updated");
        //TODO *TYPE = StringList* expected.setUnitsFinancialResources("unitsFinancialResources_Updated");
        //TODO *TYPE = StringList* expected.setUnitsFinancialControl("unitsFinancialControl_Updated");
        expected.setNextReviewPeriod("nextReviewPeriod_Updated");
        //TODO *TYPE = CluInstructorInfoList* expected.setPublishedInstructors("publishedInstructors_Updated");
        expected.setCredentialProgramId("credentialProgramId_Updated");
        //TODO *TYPE = AccreditationInfoList* expected.setAccreditingAgencies("accreditingAgencies_Updated");
        //TODO *TYPE = ProgramVariationInfoList* expected.setVariations("variations_Updated");
        //TODO *TYPE = CoreProgramInfo* expected.setOrgCoreProgram("orgCoreProgram_Updated");
    }

    /*
        A method to test the fields for a MajorDiscipline after an update operation, followed by a read operation,
        where actual is the DTO returned by the read operation, and expected is the dto returned by the update operation.
    */
    public void testCrudMajorDiscipline_testDTOFieldsForTestReadAfterUpdate(MajorDisciplineInfo expected, MajorDisciplineInfo actual)
    {
        assertEquals (expected.getId(), actual.getId());
        assertEquals (expected.getTypeKey(), actual.getTypeKey());
        assertEquals (expected.getStateKey(), actual.getStateKey());
        //TODO *TYPE = VersionInfo* assertEquals (expected.getVersion(), actual.getVersion());
        new RichTextTester().check(expected.getDescr(), actual.getDescr());
        assertEquals (expected.getCode(), actual.getCode());
        assertEquals (expected.getShortTitle(), actual.getShortTitle());
        assertEquals (expected.getLongTitle(), actual.getLongTitle());
        assertEquals (expected.getTranscriptTitle(), actual.getTranscriptTitle());
        assertEquals (expected.getUniversityClassification(), actual.getUniversityClassification());
        assertEquals (expected.getStartTerm(), actual.getStartTerm());
        assertEquals (expected.getEndTerm(), actual.getEndTerm());
        assertEquals (expected.getEndProgramEntryTerm(), actual.getEndProgramEntryTerm());
        //TODO *TYPE = StringList* assertEquals (expected.getDivisionsContentOwner(), actual.getDivisionsContentOwner());
        //TODO *TYPE = StringList* assertEquals (expected.getDivisionsStudentOversight(), actual.getDivisionsStudentOversight());
        //TODO *TYPE = StringList* assertEquals (expected.getUnitsContentOwner(), actual.getUnitsContentOwner());
        //TODO *TYPE = StringList* assertEquals (expected.getUnitsStudentOversight(), actual.getUnitsStudentOversight());
        //TODO *TYPE = LoDisplayInfoList* assertEquals (expected.getLearningObjectives(), actual.getLearningObjectives());
        //TODO *TYPE = StringList* assertEquals (expected.getProgramRequirements(), actual.getProgramRequirements());
        assertEquals (expected.getReferenceURL(), actual.getReferenceURL());
        //TODO *TYPE = RichTextInfo* assertEquals (expected.getCatalogDescr(), actual.getCatalogDescr());
        //TODO *TYPE = StringList* assertEquals (expected.getCatalogPublicationTargets(), actual.getCatalogPublicationTargets());
        assertEquals (expected.getIntensity(), actual.getIntensity());
        assertEquals (expected.getCip2000Code(), actual.getCip2000Code());
        assertEquals (expected.getCip2010Code(), actual.getCip2010Code());
        assertEquals (expected.getHegisCode(), actual.getHegisCode());
        assertEquals (expected.getSelectiveEnrollmentCode(), actual.getSelectiveEnrollmentCode());
        //TODO *TYPE = Date* assertEquals (expected.getEffectiveDate(), actual.getEffectiveDate());
        assertEquals (expected.getDiplomaTitle(), actual.getDiplomaTitle());
        //TODO *TYPE = StringList* assertEquals (expected.getCampusLocations(), actual.getCampusLocations());
        //TODO *TYPE = StringList* assertEquals (expected.getResultOptions(), actual.getResultOptions());
        //TODO *TYPE = TimeAmountInfo* assertEquals (expected.getStdDuration(), actual.getStdDuration());
        //TODO *TYPE = StringList* assertEquals (expected.getDivisionsDeployment(), actual.getDivisionsDeployment());
        //TODO *TYPE = StringList* assertEquals (expected.getDivisionsFinancialResources(), actual.getDivisionsFinancialResources());
        //TODO *TYPE = StringList* assertEquals (expected.getDivisionsFinancialControl(), actual.getDivisionsFinancialControl());
        //TODO *TYPE = StringList* assertEquals (expected.getUnitsDeployment(), actual.getUnitsDeployment());
        //TODO *TYPE = StringList* assertEquals (expected.getUnitsFinancialResources(), actual.getUnitsFinancialResources());
        //TODO *TYPE = StringList* assertEquals (expected.getUnitsFinancialControl(), actual.getUnitsFinancialControl());
        assertEquals (expected.getNextReviewPeriod(), actual.getNextReviewPeriod());
        //TODO *TYPE = CluInstructorInfoList* assertEquals (expected.getPublishedInstructors(), actual.getPublishedInstructors());
        assertEquals (expected.getCredentialProgramId(), actual.getCredentialProgramId());
        //TODO *TYPE = AccreditationInfoList* assertEquals (expected.getAccreditingAgencies(), actual.getAccreditingAgencies());
        //TODO *TYPE = ProgramVariationInfoList* assertEquals (expected.getVariations(), actual.getVariations());
        //TODO *TYPE = CoreProgramInfo* assertEquals (expected.getOrgCoreProgram(), actual.getOrgCoreProgram());
    }

    /*
        A method to set the fields for a MajorDiscipline in the 'test read after update' section.
        This dto is another (second) dto object being created for other tests.
    */
    public void testCrudMajorDiscipline_setDTOFieldsForTestReadAfterUpdate(MajorDisciplineInfo expected)
    {
        //TODO *TYPE = VersionInfo* expected.setVersion("version_Updated");
        expected.setCode("code_Updated");
        expected.setShortTitle("shortTitle_Updated");
        expected.setLongTitle("longTitle_Updated");
        expected.setTranscriptTitle("transcriptTitle_Updated");
        expected.setUniversityClassification("universityClassification_Updated");
        expected.setStartTerm("startTerm_Updated");
        expected.setEndTerm("endTerm_Updated");
        expected.setEndProgramEntryTerm("endProgramEntryTerm_Updated");
        //TODO *TYPE = StringList* expected.setDivisionsContentOwner("divisionsContentOwner_Updated");
        //TODO *TYPE = StringList* expected.setDivisionsStudentOversight("divisionsStudentOversight_Updated");
        //TODO *TYPE = StringList* expected.setUnitsContentOwner("unitsContentOwner_Updated");
        //TODO *TYPE = StringList* expected.setUnitsStudentOversight("unitsStudentOversight_Updated");
        //TODO *TYPE = LoDisplayInfoList* expected.setLearningObjectives("learningObjectives_Updated");
        //TODO *TYPE = StringList* expected.setProgramRequirements("programRequirements_Updated");
        expected.setReferenceURL("referenceURL_Updated");
        //TODO *TYPE = RichTextInfo* expected.setCatalogDescr("catalogDescr_Updated");
        //TODO *TYPE = StringList* expected.setCatalogPublicationTargets("catalogPublicationTargets_Updated");
        expected.setIntensity("intensity_Updated");
        expected.setCip2000Code("cip2000Code_Updated");
        expected.setCip2010Code("cip2010Code_Updated");
        expected.setHegisCode("hegisCode_Updated");
        expected.setSelectiveEnrollmentCode("selectiveEnrollmentCode_Updated");
        //TODO *TYPE = Date* expected.setEffectiveDate("effectiveDate_Updated");
        expected.setDiplomaTitle("diplomaTitle_Updated");
        //TODO *TYPE = StringList* expected.setCampusLocations("campusLocations_Updated");
        //TODO *TYPE = StringList* expected.setResultOptions("resultOptions_Updated");
        //TODO *TYPE = TimeAmountInfo* expected.setStdDuration("stdDuration_Updated");
        //TODO *TYPE = StringList* expected.setDivisionsDeployment("divisionsDeployment_Updated");
        //TODO *TYPE = StringList* expected.setDivisionsFinancialResources("divisionsFinancialResources_Updated");
        //TODO *TYPE = StringList* expected.setDivisionsFinancialControl("divisionsFinancialControl_Updated");
        //TODO *TYPE = StringList* expected.setUnitsDeployment("unitsDeployment_Updated");
        //TODO *TYPE = StringList* expected.setUnitsFinancialResources("unitsFinancialResources_Updated");
        //TODO *TYPE = StringList* expected.setUnitsFinancialControl("unitsFinancialControl_Updated");
        expected.setNextReviewPeriod("nextReviewPeriod_Updated");
        //TODO *TYPE = CluInstructorInfoList* expected.setPublishedInstructors("publishedInstructors_Updated");
        expected.setCredentialProgramId("credentialProgramId_Updated");
        //TODO *TYPE = AccreditationInfoList* expected.setAccreditingAgencies("accreditingAgencies_Updated");
        //TODO *TYPE = ProgramVariationInfoList* expected.setVariations("variations_Updated");
        //TODO *TYPE = CoreProgramInfo* expected.setOrgCoreProgram("orgCoreProgram_Updated");
    }


    // ****************************************************
    //           HonorsProgramInfo
    // ****************************************************

    /*
        A method to set the fields for a HonorsProgram in a 'test create' section prior to calling the 'create' operation.
    */
    public void testCrudHonorsProgram_setDTOFieldsForTestCreate(HonorsProgramInfo expected)
    {
        expected.setTypeKey("typeKey01");
        expected.setStateKey("stateKey01");
        expected.setCredentialProgramId("credentialProgramId01");
        //TODO *TYPE = StringList* expected.setProgramRequirements("programRequirements01");
    }

    /*
        A method to test the fields for a HonorsProgram. This is called after:
        - creating a DTO, where actual is the DTO returned by the create operation, and expected is the dto passed in to the create operation
        - reading a DTO after creating it, and actual is the read DTO, and expected is the dto that was created
        - updating a DTO, where actual is DTO returned by the update operation, and expected is the dto that was passed in to the update operation
    */
    public void testCrudHonorsProgram_testDTOFieldsForTestCreateUpdate(HonorsProgramInfo expected, HonorsProgramInfo actual)
    {
        assertEquals (expected.getTypeKey(), actual.getTypeKey());
        assertEquals (expected.getStateKey(), actual.getStateKey());
        assertEquals (expected.getCredentialProgramId(), actual.getCredentialProgramId());
        //TODO *TYPE = StringList* assertEquals (expected.getProgramRequirements(), actual.getProgramRequirements());
    }

    /*
        A method to set the fields for a HonorsProgram in a 'test update' section prior to calling the 'update' operation.
    */
    public void testCrudHonorsProgram_setDTOFieldsForTestUpdate(HonorsProgramInfo expected)
    {
        expected.setTypeKey("typeKey_Updated");
        expected.setStateKey("stateKey_Updated");
        expected.setCredentialProgramId("credentialProgramId_Updated");
        //TODO *TYPE = StringList* expected.setProgramRequirements("programRequirements_Updated");
    }

    /*
        A method to test the fields for a HonorsProgram after an update operation, followed by a read operation,
        where actual is the DTO returned by the read operation, and expected is the dto returned by the update operation.
    */
    public void testCrudHonorsProgram_testDTOFieldsForTestReadAfterUpdate(HonorsProgramInfo expected, HonorsProgramInfo actual)
    {
        assertEquals (expected.getId(), actual.getId());
        assertEquals (expected.getTypeKey(), actual.getTypeKey());
        assertEquals (expected.getStateKey(), actual.getStateKey());
        assertEquals (expected.getCredentialProgramId(), actual.getCredentialProgramId());
        //TODO *TYPE = StringList* assertEquals (expected.getProgramRequirements(), actual.getProgramRequirements());
    }

    /*
        A method to set the fields for a HonorsProgram in the 'test read after update' section.
        This dto is another (second) dto object being created for other tests.
    */
    public void testCrudHonorsProgram_setDTOFieldsForTestReadAfterUpdate(HonorsProgramInfo expected)
    {
        expected.setCredentialProgramId("credentialProgramId_Updated");
        //TODO *TYPE = StringList* expected.setProgramRequirements("programRequirements_Updated");
    }


    // ****************************************************
    //           CoreProgramInfo
    // ****************************************************

    /*
        A method to set the fields for a CoreProgram in a 'test create' section prior to calling the 'create' operation.
    */
    public void testCrudCoreProgram_setDTOFieldsForTestCreate(CoreProgramInfo expected)
    {
        expected.setTypeKey("typeKey01");
        expected.setStateKey("stateKey01");
        //TODO *TYPE = VersionInfo* expected.setVersion("version01");
        expected.setDescr(RichTextHelper.buildRichTextInfo("descr01", "descr01"));
        expected.setCode("code01");
        expected.setShortTitle("shortTitle01");
        expected.setLongTitle("longTitle01");
        expected.setTranscriptTitle("transcriptTitle01");
        expected.setUniversityClassification("universityClassification01");
        expected.setStartTerm("startTerm01");
        expected.setEndTerm("endTerm01");
        expected.setEndProgramEntryTerm("endProgramEntryTerm01");
        //TODO *TYPE = StringList* expected.setDivisionsContentOwner("divisionsContentOwner01");
        //TODO *TYPE = StringList* expected.setDivisionsStudentOversight("divisionsStudentOversight01");
        //TODO *TYPE = StringList* expected.setUnitsContentOwner("unitsContentOwner01");
        //TODO *TYPE = StringList* expected.setUnitsStudentOversight("unitsStudentOversight01");
        //TODO *TYPE = LoDisplayInfoList* expected.setLearningObjectives("learningObjectives01");
        //TODO *TYPE = StringList* expected.setProgramRequirements("programRequirements01");
        expected.setReferenceURL("referenceURL01");
        //TODO *TYPE = RichTextInfo* expected.setCatalogDescr("catalogDescr01");
        //TODO *TYPE = StringList* expected.setCatalogPublicationTargets("catalogPublicationTargets01");
    }

    /*
        A method to test the fields for a CoreProgram. This is called after:
        - creating a DTO, where actual is the DTO returned by the create operation, and expected is the dto passed in to the create operation
        - reading a DTO after creating it, and actual is the read DTO, and expected is the dto that was created
        - updating a DTO, where actual is DTO returned by the update operation, and expected is the dto that was passed in to the update operation
    */
    public void testCrudCoreProgram_testDTOFieldsForTestCreateUpdate(CoreProgramInfo expected, CoreProgramInfo actual)
    {
        assertEquals (expected.getTypeKey(), actual.getTypeKey());
        assertEquals (expected.getStateKey(), actual.getStateKey());
        //TODO *TYPE = VersionInfo* assertEquals (expected.getVersion(), actual.getVersion());
        new RichTextTester().check(expected.getDescr(), actual.getDescr());
        assertEquals (expected.getCode(), actual.getCode());
        assertEquals (expected.getShortTitle(), actual.getShortTitle());
        assertEquals (expected.getLongTitle(), actual.getLongTitle());
        assertEquals (expected.getTranscriptTitle(), actual.getTranscriptTitle());
        assertEquals (expected.getUniversityClassification(), actual.getUniversityClassification());
        assertEquals (expected.getStartTerm(), actual.getStartTerm());
        assertEquals (expected.getEndTerm(), actual.getEndTerm());
        assertEquals (expected.getEndProgramEntryTerm(), actual.getEndProgramEntryTerm());
        //TODO *TYPE = StringList* assertEquals (expected.getDivisionsContentOwner(), actual.getDivisionsContentOwner());
        //TODO *TYPE = StringList* assertEquals (expected.getDivisionsStudentOversight(), actual.getDivisionsStudentOversight());
        //TODO *TYPE = StringList* assertEquals (expected.getUnitsContentOwner(), actual.getUnitsContentOwner());
        //TODO *TYPE = StringList* assertEquals (expected.getUnitsStudentOversight(), actual.getUnitsStudentOversight());
        //TODO *TYPE = LoDisplayInfoList* assertEquals (expected.getLearningObjectives(), actual.getLearningObjectives());
        //TODO *TYPE = StringList* assertEquals (expected.getProgramRequirements(), actual.getProgramRequirements());
        assertEquals (expected.getReferenceURL(), actual.getReferenceURL());
        //TODO *TYPE = RichTextInfo* assertEquals (expected.getCatalogDescr(), actual.getCatalogDescr());
        //TODO *TYPE = StringList* assertEquals (expected.getCatalogPublicationTargets(), actual.getCatalogPublicationTargets());
    }

    /*
        A method to set the fields for a CoreProgram in a 'test update' section prior to calling the 'update' operation.
    */
    public void testCrudCoreProgram_setDTOFieldsForTestUpdate(CoreProgramInfo expected)
    {
        expected.setTypeKey("typeKey_Updated");
        expected.setStateKey("stateKey_Updated");
        //TODO *TYPE = VersionInfo* expected.setVersion("version_Updated");
        expected.setDescr(RichTextHelper.buildRichTextInfo("descr_Updated", "descr_Updated"));
        expected.setCode("code_Updated");
        expected.setShortTitle("shortTitle_Updated");
        expected.setLongTitle("longTitle_Updated");
        expected.setTranscriptTitle("transcriptTitle_Updated");
        expected.setUniversityClassification("universityClassification_Updated");
        expected.setStartTerm("startTerm_Updated");
        expected.setEndTerm("endTerm_Updated");
        expected.setEndProgramEntryTerm("endProgramEntryTerm_Updated");
        //TODO *TYPE = StringList* expected.setDivisionsContentOwner("divisionsContentOwner_Updated");
        //TODO *TYPE = StringList* expected.setDivisionsStudentOversight("divisionsStudentOversight_Updated");
        //TODO *TYPE = StringList* expected.setUnitsContentOwner("unitsContentOwner_Updated");
        //TODO *TYPE = StringList* expected.setUnitsStudentOversight("unitsStudentOversight_Updated");
        //TODO *TYPE = LoDisplayInfoList* expected.setLearningObjectives("learningObjectives_Updated");
        //TODO *TYPE = StringList* expected.setProgramRequirements("programRequirements_Updated");
        expected.setReferenceURL("referenceURL_Updated");
        //TODO *TYPE = RichTextInfo* expected.setCatalogDescr("catalogDescr_Updated");
        //TODO *TYPE = StringList* expected.setCatalogPublicationTargets("catalogPublicationTargets_Updated");
    }

    /*
        A method to test the fields for a CoreProgram after an update operation, followed by a read operation,
        where actual is the DTO returned by the read operation, and expected is the dto returned by the update operation.
    */
    public void testCrudCoreProgram_testDTOFieldsForTestReadAfterUpdate(CoreProgramInfo expected, CoreProgramInfo actual)
    {
        assertEquals (expected.getId(), actual.getId());
        assertEquals (expected.getTypeKey(), actual.getTypeKey());
        assertEquals (expected.getStateKey(), actual.getStateKey());
        //TODO *TYPE = VersionInfo* assertEquals (expected.getVersion(), actual.getVersion());
        new RichTextTester().check(expected.getDescr(), actual.getDescr());
        assertEquals (expected.getCode(), actual.getCode());
        assertEquals (expected.getShortTitle(), actual.getShortTitle());
        assertEquals (expected.getLongTitle(), actual.getLongTitle());
        assertEquals (expected.getTranscriptTitle(), actual.getTranscriptTitle());
        assertEquals (expected.getUniversityClassification(), actual.getUniversityClassification());
        assertEquals (expected.getStartTerm(), actual.getStartTerm());
        assertEquals (expected.getEndTerm(), actual.getEndTerm());
        assertEquals (expected.getEndProgramEntryTerm(), actual.getEndProgramEntryTerm());
        //TODO *TYPE = StringList* assertEquals (expected.getDivisionsContentOwner(), actual.getDivisionsContentOwner());
        //TODO *TYPE = StringList* assertEquals (expected.getDivisionsStudentOversight(), actual.getDivisionsStudentOversight());
        //TODO *TYPE = StringList* assertEquals (expected.getUnitsContentOwner(), actual.getUnitsContentOwner());
        //TODO *TYPE = StringList* assertEquals (expected.getUnitsStudentOversight(), actual.getUnitsStudentOversight());
        //TODO *TYPE = LoDisplayInfoList* assertEquals (expected.getLearningObjectives(), actual.getLearningObjectives());
        //TODO *TYPE = StringList* assertEquals (expected.getProgramRequirements(), actual.getProgramRequirements());
        assertEquals (expected.getReferenceURL(), actual.getReferenceURL());
        //TODO *TYPE = RichTextInfo* assertEquals (expected.getCatalogDescr(), actual.getCatalogDescr());
        //TODO *TYPE = StringList* assertEquals (expected.getCatalogPublicationTargets(), actual.getCatalogPublicationTargets());
    }

    /*
        A method to set the fields for a CoreProgram in the 'test read after update' section.
        This dto is another (second) dto object being created for other tests.
    */
    public void testCrudCoreProgram_setDTOFieldsForTestReadAfterUpdate(CoreProgramInfo expected)
    {
        //TODO *TYPE = VersionInfo* expected.setVersion("version_Updated");
        expected.setCode("code_Updated");
        expected.setShortTitle("shortTitle_Updated");
        expected.setLongTitle("longTitle_Updated");
        expected.setTranscriptTitle("transcriptTitle_Updated");
        expected.setUniversityClassification("universityClassification_Updated");
        expected.setStartTerm("startTerm_Updated");
        expected.setEndTerm("endTerm_Updated");
        expected.setEndProgramEntryTerm("endProgramEntryTerm_Updated");
        //TODO *TYPE = StringList* expected.setDivisionsContentOwner("divisionsContentOwner_Updated");
        //TODO *TYPE = StringList* expected.setDivisionsStudentOversight("divisionsStudentOversight_Updated");
        //TODO *TYPE = StringList* expected.setUnitsContentOwner("unitsContentOwner_Updated");
        //TODO *TYPE = StringList* expected.setUnitsStudentOversight("unitsStudentOversight_Updated");
        //TODO *TYPE = LoDisplayInfoList* expected.setLearningObjectives("learningObjectives_Updated");
        //TODO *TYPE = StringList* expected.setProgramRequirements("programRequirements_Updated");
        expected.setReferenceURL("referenceURL_Updated");
        //TODO *TYPE = RichTextInfo* expected.setCatalogDescr("catalogDescr_Updated");
        //TODO *TYPE = StringList* expected.setCatalogPublicationTargets("catalogPublicationTargets_Updated");
    }


    // ****************************************************
    //           ProgramRequirementInfo
    // ****************************************************

    /*
        A method to set the fields for a ProgramRequirement in a 'test create' section prior to calling the 'create' operation.
    */
    public void testCrudProgramRequirement_setDTOFieldsForTestCreate(ProgramRequirementInfo expected)
    {
        expected.setTypeKey("typeKey01");
        expected.setStateKey("stateKey01");
        expected.setDescr(RichTextHelper.buildRichTextInfo("descr01", "descr01"));
        expected.setShortTitle("shortTitle01");
        expected.setLongTitle("longTitle01");
        //TODO *TYPE = LoDisplayInfoList* expected.setLearningObjectives("learningObjectives01");
        //TODO *TYPE = StatementTreeViewInfo* expected.setStatement("statement01");
        //TODO *TYPE = Integer* expected.setMinCredits("minCredits01");
        //TODO *TYPE = Integer* expected.setMaxCredits("maxCredits01");
    }

    /*
        A method to test the fields for a ProgramRequirement. This is called after:
        - creating a DTO, where actual is the DTO returned by the create operation, and expected is the dto passed in to the create operation
        - reading a DTO after creating it, and actual is the read DTO, and expected is the dto that was created
        - updating a DTO, where actual is DTO returned by the update operation, and expected is the dto that was passed in to the update operation
    */
    public void testCrudProgramRequirement_testDTOFieldsForTestCreateUpdate(ProgramRequirementInfo expected, ProgramRequirementInfo actual)
    {
        assertEquals (expected.getTypeKey(), actual.getTypeKey());
        assertEquals (expected.getStateKey(), actual.getStateKey());
        new RichTextTester().check(expected.getDescr(), actual.getDescr());
        assertEquals (expected.getShortTitle(), actual.getShortTitle());
        assertEquals (expected.getLongTitle(), actual.getLongTitle());
        //TODO *TYPE = LoDisplayInfoList* assertEquals (expected.getLearningObjectives(), actual.getLearningObjectives());
        //TODO *TYPE = StatementTreeViewInfo* assertEquals (expected.getStatement(), actual.getStatement());
        //TODO *TYPE = Integer* assertEquals (expected.getMinCredits(), actual.getMinCredits());
        //TODO *TYPE = Integer* assertEquals (expected.getMaxCredits(), actual.getMaxCredits());
    }

    /*
        A method to set the fields for a ProgramRequirement in a 'test update' section prior to calling the 'update' operation.
    */
    public void testCrudProgramRequirement_setDTOFieldsForTestUpdate(ProgramRequirementInfo expected)
    {
        expected.setTypeKey("typeKey_Updated");
        expected.setStateKey("stateKey_Updated");
        expected.setDescr(RichTextHelper.buildRichTextInfo("descr_Updated", "descr_Updated"));
        expected.setShortTitle("shortTitle_Updated");
        expected.setLongTitle("longTitle_Updated");
        //TODO *TYPE = LoDisplayInfoList* expected.setLearningObjectives("learningObjectives_Updated");
        //TODO *TYPE = StatementTreeViewInfo* expected.setStatement("statement_Updated");
        //TODO *TYPE = Integer* expected.setMinCredits("minCredits_Updated");
        //TODO *TYPE = Integer* expected.setMaxCredits("maxCredits_Updated");
    }

    /*
        A method to test the fields for a ProgramRequirement after an update operation, followed by a read operation,
        where actual is the DTO returned by the read operation, and expected is the dto returned by the update operation.
    */
    public void testCrudProgramRequirement_testDTOFieldsForTestReadAfterUpdate(ProgramRequirementInfo expected, ProgramRequirementInfo actual)
    {
        assertEquals (expected.getId(), actual.getId());
        assertEquals (expected.getTypeKey(), actual.getTypeKey());
        assertEquals (expected.getStateKey(), actual.getStateKey());
        new RichTextTester().check(expected.getDescr(), actual.getDescr());
        assertEquals (expected.getShortTitle(), actual.getShortTitle());
        assertEquals (expected.getLongTitle(), actual.getLongTitle());
        //TODO *TYPE = LoDisplayInfoList* assertEquals (expected.getLearningObjectives(), actual.getLearningObjectives());
        //TODO *TYPE = StatementTreeViewInfo* assertEquals (expected.getStatement(), actual.getStatement());
        //TODO *TYPE = Integer* assertEquals (expected.getMinCredits(), actual.getMinCredits());
        //TODO *TYPE = Integer* assertEquals (expected.getMaxCredits(), actual.getMaxCredits());
    }

    /*
        A method to set the fields for a ProgramRequirement in the 'test read after update' section.
        This dto is another (second) dto object being created for other tests.
    */
    public void testCrudProgramRequirement_setDTOFieldsForTestReadAfterUpdate(ProgramRequirementInfo expected)
    {
        expected.setShortTitle("shortTitle_Updated");
        expected.setLongTitle("longTitle_Updated");
        //TODO *TYPE = LoDisplayInfoList* expected.setLearningObjectives("learningObjectives_Updated");
        //TODO *TYPE = StatementTreeViewInfo* expected.setStatement("statement_Updated");
        //TODO *TYPE = Integer* expected.setMinCredits("minCredits_Updated");
        //TODO *TYPE = Integer* expected.setMaxCredits("maxCredits_Updated");
    }


    // ****************************************************
    //           MinorDisciplineInfo
    // ****************************************************

    /*
        A method to set the fields for a MinorDiscipline in a 'test create' section prior to calling the 'create' operation.
    */
    public void testCrudMinorDiscipline_setDTOFieldsForTestCreate(MinorDisciplineInfo expected)
    {
        expected.setTypeKey("typeKey01");
        expected.setStateKey("stateKey01");
    }

    /*
        A method to test the fields for a MinorDiscipline. This is called after:
        - creating a DTO, where actual is the DTO returned by the create operation, and expected is the dto passed in to the create operation
        - reading a DTO after creating it, and actual is the read DTO, and expected is the dto that was created
        - updating a DTO, where actual is DTO returned by the update operation, and expected is the dto that was passed in to the update operation
    */
    public void testCrudMinorDiscipline_testDTOFieldsForTestCreateUpdate(MinorDisciplineInfo expected, MinorDisciplineInfo actual)
    {
        assertEquals (expected.getTypeKey(), actual.getTypeKey());
        assertEquals (expected.getStateKey(), actual.getStateKey());
    }

    /*
        A method to set the fields for a MinorDiscipline in a 'test update' section prior to calling the 'update' operation.
    */
    public void testCrudMinorDiscipline_setDTOFieldsForTestUpdate(MinorDisciplineInfo expected)
    {
        expected.setTypeKey("typeKey_Updated");
        expected.setStateKey("stateKey_Updated");
    }

    /*
        A method to test the fields for a MinorDiscipline after an update operation, followed by a read operation,
        where actual is the DTO returned by the read operation, and expected is the dto returned by the update operation.
    */
    public void testCrudMinorDiscipline_testDTOFieldsForTestReadAfterUpdate(MinorDisciplineInfo expected, MinorDisciplineInfo actual)
    {
        assertEquals (expected.getId(), actual.getId());
        assertEquals (expected.getTypeKey(), actual.getTypeKey());
        assertEquals (expected.getStateKey(), actual.getStateKey());
    }

    /*
        A method to set the fields for a MinorDiscipline in the 'test read after update' section.
        This dto is another (second) dto object being created for other tests.
    */
    public void testCrudMinorDiscipline_setDTOFieldsForTestReadAfterUpdate(MinorDisciplineInfo expected)
    {
    }


    // ****************************************************
    //           TrackInfo
    // ****************************************************

    /*
        A method to set the fields for a Track in a 'test create' section prior to calling the 'create' operation.
    */
    public void testCrudTrack_setDTOFieldsForTestCreate(TrackInfo expected)
    {
        expected.setTypeKey("typeKey01");
        expected.setStateKey("stateKey01");
    }

    /*
        A method to test the fields for a Track. This is called after:
        - creating a DTO, where actual is the DTO returned by the create operation, and expected is the dto passed in to the create operation
        - reading a DTO after creating it, and actual is the read DTO, and expected is the dto that was created
        - updating a DTO, where actual is DTO returned by the update operation, and expected is the dto that was passed in to the update operation
    */
    public void testCrudTrack_testDTOFieldsForTestCreateUpdate(TrackInfo expected, TrackInfo actual)
    {
        assertEquals (expected.getTypeKey(), actual.getTypeKey());
        assertEquals (expected.getStateKey(), actual.getStateKey());
    }

    /*
        A method to set the fields for a Track in a 'test update' section prior to calling the 'update' operation.
    */
    public void testCrudTrack_setDTOFieldsForTestUpdate(TrackInfo expected)
    {
        expected.setTypeKey("typeKey_Updated");
        expected.setStateKey("stateKey_Updated");
    }

    /*
        A method to test the fields for a Track after an update operation, followed by a read operation,
        where actual is the DTO returned by the read operation, and expected is the dto returned by the update operation.
    */
    public void testCrudTrack_testDTOFieldsForTestReadAfterUpdate(TrackInfo expected, TrackInfo actual)
    {
        assertEquals (expected.getId(), actual.getId());
        assertEquals (expected.getTypeKey(), actual.getTypeKey());
        assertEquals (expected.getStateKey(), actual.getStateKey());
    }

    /*
        A method to set the fields for a Track in the 'test read after update' section.
        This dto is another (second) dto object being created for other tests.
    */
    public void testCrudTrack_setDTOFieldsForTestReadAfterUpdate(TrackInfo expected)
    {
    }


    // ========================================
    // SERVICE OPS NOT TESTED IN BASE TEST CLASS
    // ========================================

    /* Method Name: validateCredentialProgram */
    @Test
    public void test_validateCredentialProgram()
            throws 	InvalidParameterException	,MissingParameterException	,OperationFailedException	{
    }

    /* Method Name: setCurrentCredentialProgramVersion */
    @Test
    public void test_setCurrentCredentialProgramVersion()
            throws 	DoesNotExistException	,InvalidParameterException	,MissingParameterException	,IllegalVersionSequencingException	,OperationFailedException	,PermissionDeniedException	,DataValidationErrorException	{
    }

    /* Method Name: searchForCredentialProgramIds */
    @Test
    public void test_searchForCredentialProgramIds()
            throws 	InvalidParameterException	,MissingParameterException	,OperationFailedException	,PermissionDeniedException	{
    }

    /* Method Name: searchForCredentialPrograms */
    @Test
    public void test_searchForCredentialPrograms()
            throws 	InvalidParameterException	,MissingParameterException	,OperationFailedException	,PermissionDeniedException	{
    }

    /* Method Name: getMajorDisciplineIdsByCredentialProgramType */
    @Test
    public void test_getMajorDisciplineIdsByCredentialProgramType()
            throws 	DoesNotExistException	,InvalidParameterException	,MissingParameterException	,OperationFailedException	{
    }

    /* Method Name: getProgramVariationsByMajorDiscipline */
    @Test
    public void test_getProgramVariationsByMajorDiscipline()
            throws 	DoesNotExistException	,InvalidParameterException	,MissingParameterException	,OperationFailedException	{
    }

    /* Method Name: validateMajorDiscipline */
    @Test
    public void test_validateMajorDiscipline()
            throws 	InvalidParameterException	,MissingParameterException	,OperationFailedException	,PermissionDeniedException	{
    }

    /* Method Name: searchForMajorDisciplineIds */
    @Test
    public void test_searchForMajorDisciplineIds()
            throws 	InvalidParameterException	,MissingParameterException	,OperationFailedException	,PermissionDeniedException	{
    }

    /* Method Name: searchForMajorDisciplines */
    @Test
    public void test_searchForMajorDisciplines()
            throws 	InvalidParameterException	,MissingParameterException	,OperationFailedException	,PermissionDeniedException	{
    }

    /* Method Name: getHonorProgramIdsByCredentialProgramType */
    @Test
    public void test_getHonorProgramIdsByCredentialProgramType()
            throws 	DoesNotExistException	,InvalidParameterException	,MissingParameterException	,OperationFailedException	{
    }

    /* Method Name: validateHonorsProgram */
    @Test
    public void test_validateHonorsProgram()
            throws 	InvalidParameterException	,MissingParameterException	,OperationFailedException	{
    }

    /* Method Name: searchForHonorsProgramIds */
    @Test
    public void test_searchForHonorsProgramIds()
            throws 	InvalidParameterException	,MissingParameterException	,OperationFailedException	,PermissionDeniedException	{
    }

    /* Method Name: searchForHonorsPrograms */
    @Test
    public void test_searchForHonorsPrograms()
            throws 	InvalidParameterException	,MissingParameterException	,OperationFailedException	,PermissionDeniedException	{
    }

    /* Method Name: validateCoreProgram */
    @Test
    public void test_validateCoreProgram()
            throws 	InvalidParameterException	,MissingParameterException	,OperationFailedException	{
    }

    /* Method Name: setCurrentCoreProgramVersion */
    @Test
    public void test_setCurrentCoreProgramVersion()
            throws 	DoesNotExistException	,InvalidParameterException	,MissingParameterException	,IllegalVersionSequencingException	,OperationFailedException	,PermissionDeniedException	,DataValidationErrorException	{
    }

    /* Method Name: searchForCoreProgramIds */
    @Test
    public void test_searchForCoreProgramIds()
            throws 	InvalidParameterException	,MissingParameterException	,OperationFailedException	,PermissionDeniedException	{
    }

    /* Method Name: searchForCorePrograms */
    @Test
    public void test_searchForCorePrograms()
            throws 	InvalidParameterException	,MissingParameterException	,OperationFailedException	,PermissionDeniedException	{
    }

    /* Method Name: validateProgramRequirement */
    @Test
    public void test_validateProgramRequirement()
            throws 	InvalidParameterException	,MissingParameterException	,OperationFailedException	{
    }

    /* Method Name: searchForProgramRequirementIds */
    @Test
    public void test_searchForProgramRequirementIds()
            throws 	InvalidParameterException	,MissingParameterException	,OperationFailedException	,PermissionDeniedException	{
    }

    /* Method Name: searchForProgramRequirements */
    @Test
    public void test_searchForProgramRequirements()
            throws 	InvalidParameterException	,MissingParameterException	,OperationFailedException	,PermissionDeniedException	{
    }

    /* Method Name: setCurrentMajorDisciplineVersion */
    @Test
    public void test_setCurrentMajorDisciplineVersion()
            throws 	DoesNotExistException	,InvalidParameterException	,MissingParameterException	,IllegalVersionSequencingException,OperationFailedException	,PermissionDeniedException	,DataValidationErrorException	{
    }

    /* Method Name: getMinorsByCredentialProgramType */
    @Test
    public void test_getMinorsByCredentialProgramType()
            throws 	DoesNotExistException	,InvalidParameterException	,MissingParameterException	,OperationFailedException	{
    }

    /* Method Name: validateMinorDiscipline */
    @Test
    public void test_validateMinorDiscipline()
            throws 	InvalidParameterException	,MissingParameterException	,OperationFailedException	{
    }

    /* Method Name: getVariationsByMajorDisciplineId */
    @Test
    public void test_getVariationsByMajorDisciplineId()
            throws 	DoesNotExistException	,InvalidParameterException	,MissingParameterException	,OperationFailedException	{
    }

    /* Method Name: searchForMinorDisciplineIds */
    @Test
    public void test_searchForMinorDisciplineIds()
            throws 	InvalidParameterException	,MissingParameterException	,OperationFailedException	,PermissionDeniedException	{
    }

    /* Method Name: searchForMinorDisciplines */
    @Test
    public void test_searchForMinorDisciplines()
            throws 	InvalidParameterException	,MissingParameterException	,OperationFailedException	,PermissionDeniedException	{
    }

    /* Method Name: getTracksByMinor */
    @Test
    public void test_getTracksByMinor()
            throws 	InvalidParameterException	,MissingParameterException	,OperationFailedException	,PermissionDeniedException	{
    }

    /* Method Name: validateTrack */
    @Test
    public void test_validateTrack()
            throws 	InvalidParameterException	,MissingParameterException	,OperationFailedException	,PermissionDeniedException	{
    }

    /* Method Name: searchForTrackIds */
    @Test
    public void test_searchForTrackIds()
            throws 	InvalidParameterException	,MissingParameterException	,OperationFailedException	,PermissionDeniedException	{
    }

    /* Method Name: searchForTracks */
    @Test
    public void test_searchForTracks()
            throws 	InvalidParameterException	,MissingParameterException	,OperationFailedException	,PermissionDeniedException	{
    }

}
