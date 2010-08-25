package org.kuali.student.lum.program.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;
import java.util.Map;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.student.core.assembly.data.Metadata;
import org.kuali.student.core.assembly.dictionary.MetadataServiceImpl;
import org.kuali.student.core.exceptions.AlreadyExistsException;
import org.kuali.student.core.exceptions.DataValidationErrorException;
import org.kuali.student.core.exceptions.DoesNotExistException;
import org.kuali.student.core.exceptions.InvalidParameterException;
import org.kuali.student.core.exceptions.MissingParameterException;
import org.kuali.student.core.exceptions.OperationFailedException;
import org.kuali.student.core.exceptions.PermissionDeniedException;
import org.kuali.student.lum.course.dto.LoDisplayInfo;
import org.kuali.student.lum.lo.dto.LoCategoryInfo;
import org.kuali.student.lum.lu.dto.AdminOrgInfo;
import org.kuali.student.lum.program.dto.CoreProgramInfo;
import org.kuali.student.lum.program.dto.CredentialProgramInfo;
import org.kuali.student.lum.program.dto.MajorDisciplineInfo;
import org.kuali.student.lum.program.dto.ProgramRequirementInfo;
import org.kuali.student.lum.program.dto.ProgramVariationInfo;
import org.kuali.student.lum.program.service.ProgramService;
import org.kuali.student.lum.program.service.assembler.MajorDisciplineDataGenerator;
import org.kuali.student.lum.program.service.assembler.ProgramAssemblerConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:program-test-context.xml"})
public class TestProgramServiceImpl {

    @Autowired
    public ProgramService programService;
    private static final String OTHER_LO_CAT_ID = "550e8400-e29b-41d4-a716-446655440000";

    @Test
    public void testProgramServiceSetup() {
    	assertNotNull(programService);
    }

	@Test
    public void testGetMetaData() {
        MetadataServiceImpl metadataService = new MetadataServiceImpl(programService);
        metadataService.setUiLookupContext("classpath:lum-ui-test-lookup-context.xml");
        Metadata metadata = metadataService.getMetadata("org.kuali.student.lum.program.dto.MajorDisciplineInfo");
        assertNotNull(metadata);

        Map<String, Metadata> properties = metadata.getProperties();
        assertTrue(properties.size() > 0);

        assertTrue(properties.containsKey("universityClassification"));
        metadata = properties.get("universityClassification");
        assertEquals("STRING", metadata.getDataType().name());
    }


    @Test
    @Ignore
    public void testGetProgramRequirement() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        ProgramRequirementInfo progReqInfo = programService.getProgramRequirement("PROGREQ-1", null, null);
        assertNotNull(progReqInfo);
    }

    @Test(expected = MissingParameterException.class)
    public void testGetProgramRequirement_nullId() throws Exception {
        programService.getProgramRequirement(null, null, null);
    }

    @Test(expected = DoesNotExistException.class)
    public void testGetProgramRequirement_badId() throws Exception {
        programService.getProgramRequirement("CLU-XXX ", null, null);
    }

    @Test
    public void testGetCoreProgram() {
        CoreProgramInfo core = null;
        try {
            try {
                core = programService.getCoreProgram("d4ea77dd-b492-4554-b104-863e42c5f8b7");
                fail("Should have received DoesNotExistException");
            } catch (DoesNotExistException dnee) {
                String expectedExceptionMessage = "Specified CLU is not a CoreProgram";
                assertEquals("Expected DoesNotExistException has incorrect message:", expectedExceptionMessage, dnee.getMessage());
            }
            core = programService.getCoreProgram("00f5f8c5-fff1-4c8b-92fc-789b891e0849");

            assertNotNull(core);

            assertNotNull(core.getReferenceURL());
            assertEquals("http://www.google.ca", core.getReferenceURL());
//            assertNotNull(major.getUniversityClassification());
//            assertEquals(major.getUniversityClassification(), "UNIVERSITYCLASSIFICATIONCODE");
            assertNotNull(core.getStartTerm());
            assertEquals("start_term", core.getStartTerm());
            assertNotNull(core.getEndTerm());
            assertEquals("end_term", core.getEndTerm());
            assertNotNull(core.getEndProgramEntryTerm());
            assertEquals("end_admit_term", core.getEndProgramEntryTerm());
            assertNotNull(core.getCode());
            assertEquals("BS", core.getCode());
            assertNotNull(core.getShortTitle());
            assertEquals("B.S.", core.getShortTitle());
            assertNotNull(core.getLongTitle());
            assertEquals("Bachelor of Science", core.getLongTitle());
            assertNotNull(core.getTranscriptTitle());
            assertEquals(core.getTranscriptTitle(), "TRANSCRIPT-TITLE");

            //TODO: add descr in test db
//            assertNotNull(core.getDescr());
//            assertEquals("Anthropology Major", core.getDescr().getPlain());
//
//            //TODO catalog descr
//            //TODO catalog pub targets
             //TODO: add lo in test db
//            assertNotNull(major.getLearningObjectives());
//            assertTrue(major.getLearningObjectives().size() ==1);
//            assertEquals("Annihilate Wiki", major.getLearningObjectives().get(0).getLoInfo().getDesc().getPlain());

            assertNotNull(core.getDivisionsContentOwner());
            assertTrue(core.getDivisionsContentOwner().size() == 1);
            assertEquals(core.getDivisionsContentOwner().get(0).getId(), "CORE-COD");
            assertNotNull(core.getDivisionsStudentOversight());
            assertTrue(core.getDivisionsStudentOversight().size() == 1);
            assertEquals(core.getDivisionsStudentOversight().get(0).getId(), "CORE-SOD");
            assertNotNull(core.getUnitsContentOwner());
            assertTrue(core.getUnitsContentOwner().size() == 1);
            assertEquals(core.getUnitsContentOwner().get(0).getId(), "CORE-COU");
            assertNotNull(core.getUnitsStudentOversight());
            assertTrue(core.getUnitsStudentOversight().size() == 1);
            assertEquals(core.getUnitsStudentOversight().get(0).getId(), "CORE-SOU");

            assertNotNull(core.getAttributes());
            assertTrue(core.getAttributes().size() ==2);
            assertEquals("GINGER GEM", core.getAttributes().get("COOKIES"));
            assertEquals("JAM TART", core.getAttributes().get("CAKES"));

            assertNotNull(core.getMetaInfo());
            assertEquals("1", core.getMetaInfo().getVersionInd());
            assertNotNull(core.getType());
            assertEquals(ProgramAssemblerConstants.CORE_PROGRAM, core.getType());
            assertNotNull(core.getState());
            assertEquals(ProgramAssemblerConstants.ACTIVE, core.getState());
            assertNotNull(core.getId());
            assertEquals("00f5f8c5-fff1-4c8b-92fc-789b891e0849", core.getId());
          } catch (Exception e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }
    @Test
    public void testGetMajorDiscipline() {
        MajorDisciplineInfo major = null;
        try {
//        	MajorDisciplineDataGenerator generator = new MajorDisciplineDataGenerator();
            // MajorDisciplineInfo majorDisciplineInfo = generator.getMajorDisciplineInfoTestData();
            try {
                major = programService.getMajorDiscipline("0d8c42bc-77ba-450e-ae0e-eecd76fae779");
                fail("Should have received DoesNotExistException");
            } catch (DoesNotExistException dnee) {
                String expectedExceptionMessage = "Specified CLU is not a Major Discipline";
                assertEquals("Expected DoesNotExistException has incorrect message:", expectedExceptionMessage, dnee.getMessage());
            }
            major = programService.getMajorDiscipline("d4ea77dd-b492-4554-b104-863e42c5f8b7");

            assertNotNull(major);

            assertNotNull(major.getIntensity());
            assertEquals("kuali.atp.duration.full", major.getIntensity());
            assertNotNull(major.getReferenceURL());
            assertEquals("http://www.google.ca", major.getReferenceURL());
            assertEquals(1, major.getPublishedInstructors().size());

            assertEquals("INSTR-1", major.getPublishedInstructors().get(0).getPersonId());
            assertNotNull(major.getCredentialProgramId());
            assertEquals("d02dbbd3-20e2-410d-ab52-1bd6d362748b", major.getCredentialProgramId());

            assertNotNull(major.getVariations());
            assertTrue(major.getVariations().size() == 1);
            assertEquals("BS", major.getVariations().get(0).getCode());

            assertNotNull(major.getCode());
            assertEquals("ANTH", major.getCode());
            assertNotNull(major.getCip2000Code());
            assertEquals( "CIP2000CODE", major.getCip2000Code());
            assertNotNull(major.getCip2010Code());
            assertEquals("CIP2010CODE", major.getCip2010Code());
            assertNotNull(major.getHegisCode());
            assertEquals("HEGISCODE", major.getHegisCode());
            assertNotNull(major.getUniversityClassification());
            assertEquals("UNIVERSITYCLASSIFICATIONCODE", major.getUniversityClassification());
            assertNotNull(major.getSelectiveEnrollmentCode());
            assertEquals("SELECTIVEENROLLMENTCODE", major.getSelectiveEnrollmentCode());

            assertNotNull(major.getResultOptions());
            assertTrue(major.getResultOptions().size() == 1);
            assertEquals("kuali.certificateType.degree", major.getResultOptions().get(0));

            assertNotNull(major.getStdDuration());
            assertEquals("kuali.atp.duration.Week", major.getStdDuration().getAtpDurationTypeKey());
            assertEquals(new Integer(100), major.getStdDuration().getTimeQuantity());
            assertNotNull(major.getStartTerm());
            assertEquals("start_term", major.getStartTerm());
            assertNotNull(major.getEndTerm());
            assertEquals("end_term", major.getEndTerm());
            assertNotNull(major.getEndProgramEntryTerm());
            assertEquals("end_admit_term", major.getEndProgramEntryTerm());

            assertNotNull(major.getNextReviewPeriod());
            assertEquals("kuali.atp.SU2009-2010S1", major.getNextReviewPeriod());

            assertNotNull(major.getEffectiveDate());
            //TODO effectiveDate
//            Calendar effectiveDate = GregorianCalendar.getInstance();
//            effectiveDate.set(1984, 7, 1, 0, 0, 0);
//            Date testDate = new Date(effectiveDate.getTimeInMillis());
//            assertTrue(major.getEffectiveDate().compareTo(testDate) == 0);

            assertEquals(ProgramAssemblerConstants.UNDERGRAD_PROGRAM_LEVEL, major.getAttributes().get(ProgramAssemblerConstants.PROGRAM_LEVEL));
            assertNotNull(major.getShortTitle());

            assertEquals("Anthro", major.getShortTitle());
            assertNotNull(major.getLongTitle());
            assertEquals("Anthropology", major.getLongTitle());
            assertNotNull(major.getTranscriptTitle());
            assertEquals("TRANSCRIPT-TITLE", major.getTranscriptTitle());
            assertNotNull(major.getDiplomaTitle());
            assertEquals("DIPLOMA-TITLE", major.getDiplomaTitle() );
            assertNotNull(major.getDescr());
            assertEquals("Anthropology Major", major.getDescr().getPlain());

            //TODO catalog descr
            //TODO catalog pub targets

            assertNotNull(major.getLearningObjectives());
            assertTrue(major.getLearningObjectives().size() ==1);
            assertEquals("Annihilate Wiki", major.getLearningObjectives().get(0).getLoInfo().getDesc().getPlain());
            assertNotNull(major.getCampusLocations());
            assertTrue(major.getCampusLocations().size() == 2);
            assertEquals("NORTH", major.getCampusLocations().get(0));
            assertEquals("SOUTH", major.getCampusLocations().get(1));

            assertNotNull(major.getOrgCoreProgram());
            assertEquals("kuali.lu.type.CoreProgram", major.getOrgCoreProgram().getType());
            assertEquals("00f5f8c5-fff1-4c8b-92fc-789b891e0849", major.getOrgCoreProgram().getId());
            //TODO progr requirements

            assertNotNull(major.getAccreditingAgencies());
            assertTrue(major.getAccreditingAgencies().size() == 1);
            assertEquals("23", major.getAccreditingAgencies().get(0).getOrgId());
            assertNotNull(major.getDivisionsContentOwner());
            assertTrue(major.getDivisionsContentOwner().size() == 1);
            assertEquals(major.getDivisionsContentOwner().get(0).getId(), "MAJOR-1");
            assertNotNull(major.getDivisionsStudentOversight());
            assertTrue(major.getDivisionsStudentOversight().size() == 1);
            assertEquals(major.getDivisionsStudentOversight().get(0).getId(), "MAJOR-2");
            assertNotNull(major.getDivisionsDeployment());
            assertTrue(major.getDivisionsDeployment().size() == 1);
            assertEquals(major.getDivisionsDeployment().get(0).getId(), "MAJOR-3");
            assertNotNull(major.getDivisionsFinancialResources());
            assertTrue(major.getDivisionsFinancialResources().size() == 1);
            assertEquals(major.getDivisionsFinancialResources().get(0).getId(), "MAJOR-4");
            assertNotNull(major.getDivisionsFinancialControl());
            assertTrue(major.getDivisionsFinancialControl().size() == 1);
            assertEquals(major.getDivisionsFinancialControl().get(0).getId(), "MAJOR-5");

            assertNotNull(major.getUnitsContentOwner());
            assertTrue(major.getUnitsContentOwner().size() == 1);
            assertEquals(major.getUnitsContentOwner().get(0).getId(), "MAJOR-6");
            assertNotNull(major.getUnitsStudentOversight());
            assertTrue(major.getUnitsStudentOversight().size() == 1);
            assertEquals(major.getUnitsStudentOversight().get(0).getId(), "MAJOR-7");
            assertNotNull(major.getUnitsDeployment());
            assertTrue(major.getUnitsDeployment().size() == 1);
            assertEquals(major.getUnitsDeployment().get(0).getId(), "MAJOR-8");
            assertNotNull(major.getUnitsFinancialResources());
            assertTrue(major.getUnitsFinancialResources().size() == 1);
            assertEquals(major.getUnitsFinancialResources().get(0).getId(), "MAJOR-9");
            assertNotNull(major.getUnitsFinancialControl());
            assertTrue(major.getUnitsFinancialControl().size() == 2);
            assertEquals(major.getUnitsFinancialControl().get(0).getId(), "MAJOR-10");
            assertEquals(major.getUnitsFinancialControl().get(1).getId(), "MAJOR-11");
            assertNotNull(major.getAttributes());
            assertEquals(3, major.getAttributes().size());
            assertEquals("GINGER GEM", major.getAttributes().get("COOKIES"));
            assertEquals("JAM TART", major.getAttributes().get("CAKES"));

            assertNotNull(major.getMetaInfo());
            assertEquals("1", major.getMetaInfo().getVersionInd());
           //TODO createTime
//            Calendar createTime = GregorianCalendar.getInstance();
//            createTime.set(2009, 4, 7, 12, 5, 36);
//            testDate = new Date(createTime.getTimeInMillis());
//            assertTrue(major.getEffectiveDate().compareTo(testDate) == 0);

            assertNotNull(major.getType());
            assertEquals(ProgramAssemblerConstants.MAJOR_DISCIPLINE, major.getType());
            assertNotNull(major.getState());
            assertEquals(ProgramAssemblerConstants.ACTIVE, major.getState());
            assertNotNull(major.getId());
            assertEquals("d4ea77dd-b492-4554-b104-863e42c5f8b7", major.getId());


          } catch (Exception e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }

    @Test
    public void testGetVariationsByMajorDisciplineId(){
        MajorDisciplineInfo majorDisciplineInfo = null;

        try {
            majorDisciplineInfo = programService.getMajorDiscipline("d4ea77dd-b492-4554-b104-863e42c5f8b7");
            assertNotNull(majorDisciplineInfo);
            
            List<ProgramVariationInfo> pvInfos = programService.getVariationsByMajorDisciplineId("d4ea77dd-b492-4554-b104-863e42c5f8b7");
            assertNotNull(pvInfos);
            assertEquals(pvInfos.size(), majorDisciplineInfo.getVariations().size());
            
            ProgramVariationInfo pvInfo = pvInfos.get(0);
            assertEquals("BS", pvInfo.getCode());
            assertEquals("Bachelor of Science", pvInfo.getDescr().getPlain());
            assertEquals("Bachelor of Science", pvInfo.getLongTitle());
            assertEquals("B.S.", pvInfo.getShortTitle());
            assertEquals("d02dbbd3-20e2-410d-ab52-1bd6d362748b", pvInfo.getId());
            assertEquals("active", pvInfo.getState());

        } catch (Exception e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }

    
    @Test
    public void testGetBaccCredentialProgram(){

    	String credentialProgramId = "d02dbbd3-20e2-410d-ab52-1bd6d362748b";
    	CredentialProgramInfo credentialProgramInfo = null;
    	try{
    		credentialProgramInfo = programService.getCredentialProgram(credentialProgramId);
            assertNotNull(credentialProgramInfo);
            assertEquals("BS", credentialProgramInfo.getCode());
            assertEquals("B.S.", credentialProgramInfo.getShortTitle());
            assertEquals("Bachelor of Science", credentialProgramInfo.getLongTitle());
            assertEquals("Bachelor of Science", credentialProgramInfo.getDescr().getPlain());
            assertEquals(ProgramAssemblerConstants.ACTIVE, credentialProgramInfo.getState());
    		assertEquals(ProgramAssemblerConstants.BACCALAUREATE_PROGRAM, credentialProgramInfo.getCredentialProgramType());
            assertEquals("52", credentialProgramInfo.getInstitution().getOrgId());
            assertEquals(ProgramAssemblerConstants.UNDERGRAD_PROGRAM_LEVEL, credentialProgramInfo.getProgramLevel());
            assertNotNull(credentialProgramInfo.getCoreProgramIds());
            assertEquals(1, credentialProgramInfo.getCoreProgramIds().size());
            assertEquals("00f5f8c5-fff1-4c8b-92fc-789b891e0849", credentialProgramInfo.getCoreProgramIds().get(0));
	    } catch (Exception e) {
	    	e.printStackTrace();
	        fail(e.getMessage());
	    }
    }

    @Test
    public void testCreateMajorDiscipline() {
		MajorDisciplineDataGenerator mdGenerator = new MajorDisciplineDataGenerator();
        MajorDisciplineInfo major;
        try {
            assertNotNull(major = mdGenerator.getMajorDisciplineInfoTestData());

            MajorDisciplineInfo createdMD = programService.createMajorDiscipline(major);

            assertNotNull(createdMD);

            assertNotNull(createdMD.getId());

            assertNotNull(createdMD.getState());
            assertEquals(ProgramAssemblerConstants.DRAFT, createdMD.getState());
            
            assertNotNull(createdMD.getType());
            assertEquals(ProgramAssemblerConstants.MAJOR_DISCIPLINE, createdMD.getType());

            assertNotNull(createdMD.getIntensity());
            assertEquals("intensity-test", createdMD.getIntensity());
            assertNotNull(createdMD.getReferenceURL());
            assertEquals("referenceURL-test", createdMD.getReferenceURL());

            assertEquals(2, createdMD.getPublishedInstructors().size());
            assertEquals("personId-test", createdMD.getPublishedInstructors().get(0).getPersonId());

            assertNotNull(createdMD.getCredentialProgramId());
            assertEquals("00f5f8c5-fff1-4c8b-92fc-789b891e0849", createdMD.getCredentialProgramId());

            assertNotNull(createdMD.getVariations());
            assertTrue(createdMD.getVariations().size() == 2);
            assertNotNull(createdMD.getVariations().get(0).getId());
            assertNotNull(createdMD.getVariations().get(1).getId());
            assertEquals("kuali.lu.type.Variation", createdMD.getVariations().get(0).getType());
            assertEquals("kuali.lu.type.Variation", createdMD.getVariations().get(1).getType());

            assertNotNull(createdMD.getCode());
//TODO            assertEquals("ANTH", createdMD.getCode());

            assertNotNull(createdMD.getCip2000Code());
            assertEquals(createdMD.getCip2000Code(), "cip2000Code-test");
            assertNotNull(createdMD.getCip2010Code());
            assertEquals(createdMD.getCip2010Code(), "cip2010Code-test");
            assertNotNull(createdMD.getHegisCode());
            assertEquals(createdMD.getHegisCode(), "hegisCode-test");
            assertNotNull(createdMD.getUniversityClassification());
            assertEquals(createdMD.getUniversityClassification(), "universityClassification-test");
            assertNotNull(createdMD.getSelectiveEnrollmentCode());
            assertEquals(createdMD.getSelectiveEnrollmentCode(), "selectiveEnrollmentCode-test");

            assertNotNull(createdMD.getResultOptions());
            assertTrue(createdMD.getResultOptions().size() == 2);
            assertEquals("resultOptions-test", createdMD.getResultOptions().get(0));

            assertNotNull(createdMD.getStdDuration());
            assertEquals("atpDurationTypeKey-test", createdMD.getStdDuration().getAtpDurationTypeKey());
            assertEquals(new Integer(63), createdMD.getStdDuration().getTimeQuantity());

            assertNotNull(createdMD.getStartTerm());
            assertEquals("startTerm-test", createdMD.getStartTerm());
            assertNotNull(createdMD.getEndTerm());
            assertEquals("endTerm-test", createdMD.getEndTerm());
            assertNotNull(createdMD.getEndProgramEntryTerm());
            assertEquals("endProgramEntryTerm-test", createdMD.getEndProgramEntryTerm());
            assertNotNull(createdMD.getNextReviewPeriod());
            assertEquals("nextReviewPeriod-test", createdMD.getNextReviewPeriod());

            assertNotNull(createdMD.getEffectiveDate());
            //TODO effectiveDate
//            Calendar effectiveDate = GregorianCalendar.getInstance();
//            effectiveDate.set(1984, 7, 1, 0, 0, 0);
//            Date testDate = new Date(effectiveDate.getTimeInMillis());
//            assertTrue(createdMD.getEffectiveDate().compareTo(testDate) == 0);

            assertNotNull(createdMD.getShortTitle());
            assertEquals("shortTitle-test", createdMD.getShortTitle());
            assertNotNull(createdMD.getLongTitle());
            assertEquals("longTitle-test", createdMD.getLongTitle());
            assertNotNull(createdMD.getTranscriptTitle());
            assertEquals(createdMD.getTranscriptTitle(), "transcriptTitle-test");
            assertNotNull(createdMD.getDiplomaTitle());
            assertEquals(createdMD.getDiplomaTitle(), "diplomaTitle-test");
            assertNotNull(createdMD.getDescr());
            assertEquals("plain-test", createdMD.getDescr().getPlain());
            assertEquals("formatted-test", createdMD.getDescr().getFormatted());

            //TODO catalog pub targets

            assertNotNull(createdMD.getCatalogDescr());
            assertEquals("plain-test", createdMD.getCatalogDescr().getPlain());
            assertEquals("formatted-test", createdMD.getCatalogDescr().getFormatted());

            assertNotNull(createdMD.getCatalogPublicationTargets());
            assertTrue(createdMD.getCatalogPublicationTargets().size() == 2);
            assertEquals("catalogPublicationTargets-test", createdMD.getCatalogPublicationTargets().get(0));

            assertNotNull(createdMD.getLearningObjectives());
            assertTrue(createdMD.getLearningObjectives().size() == 2);
            assertEquals("plain-test", createdMD.getLearningObjectives().get(0).getLoInfo().getDesc().getPlain());

            assertNotNull(createdMD.getCampusLocations());
            assertTrue(createdMD.getCampusLocations().size() == 2);
            assertEquals("SOUTH", createdMD.getCampusLocations().get(0));
            assertEquals("NORTH", createdMD.getCampusLocations().get(1));

            assertNotNull(createdMD.getOrgCoreProgram());
            assertEquals(ProgramAssemblerConstants.CORE_PROGRAM, createdMD.getOrgCoreProgram().getType());
// TODO           assertEquals("00f5f8c5-fff1-4c8b-92fc-789b891e0849", createdMD.getOrgCoreProgram().getId());
            //TODO progr requirements

            assertNotNull(createdMD.getProgramRequirements());
            assertTrue(createdMD.getProgramRequirements().size() == 2);
            assertEquals("programRequirements-test", createdMD.getProgramRequirements().get(0));

            assertNotNull(createdMD.getAccreditingAgencies());
            assertTrue(createdMD.getAccreditingAgencies().size() == 2);
            assertEquals("orgId-test", createdMD.getAccreditingAgencies().get(0).getOrgId());

            assertNotNull(createdMD.getDivisionsContentOwner());
            assertTrue(createdMD.getDivisionsContentOwner().size() == 4);
            assertEquals(createdMD.getDivisionsContentOwner().get(0).getType(), ProgramAssemblerConstants.CONTENT_OWNER_DIVISION);
            assertNotNull(createdMD.getDivisionsStudentOversight());
            assertTrue(createdMD.getDivisionsStudentOversight().size() == 4);
            assertEquals(createdMD.getDivisionsStudentOversight().get(0).getType(), ProgramAssemblerConstants.STUDENT_OVERSIGHT_DIVISION);
            assertNotNull(createdMD.getDivisionsDeployment());
            assertTrue(createdMD.getDivisionsDeployment().size() == 4);
            assertEquals(createdMD.getDivisionsDeployment().get(0).getType(), ProgramAssemblerConstants.DEPLOYMENT_DIVISION);
            assertNotNull(createdMD.getDivisionsFinancialResources());
            assertTrue(createdMD.getDivisionsFinancialResources().size() == 4);
            assertEquals(createdMD.getDivisionsFinancialResources().get(0).getType(), ProgramAssemblerConstants.FINANCIAL_RESOURCES_DIVISION);
            assertNotNull(createdMD.getDivisionsFinancialControl());
            assertTrue(createdMD.getDivisionsFinancialControl().size() == 4);
            assertEquals(createdMD.getDivisionsFinancialControl().get(0).getType(), ProgramAssemblerConstants.FINANCIAL_CONTROL_DIVISION);

            assertNotNull(createdMD.getUnitsContentOwner());
            assertTrue(createdMD.getUnitsContentOwner().size() == 4);
            assertEquals(createdMD.getUnitsContentOwner().get(0).getType(), ProgramAssemblerConstants.CONTENT_OWNER_UNIT);
            assertNotNull(createdMD.getUnitsStudentOversight());
            assertTrue(createdMD.getUnitsStudentOversight().size() == 4);
            assertEquals(createdMD.getUnitsStudentOversight().get(1).getType(), ProgramAssemblerConstants.STUDENT_OVERSIGHT_UNIT);
            assertNotNull(createdMD.getUnitsDeployment());
            assertTrue(createdMD.getUnitsDeployment().size() == 4);
            assertEquals(createdMD.getUnitsDeployment().get(0).getType(), ProgramAssemblerConstants.DEPLOYMENT_UNIT);
            assertNotNull(createdMD.getUnitsFinancialResources());
            assertTrue(createdMD.getUnitsFinancialResources().size() == 4);
            assertEquals(createdMD.getUnitsFinancialResources().get(0).getType(), ProgramAssemblerConstants.FINANCIAL_RESOURCES_UNIT);
            assertNotNull(createdMD.getUnitsFinancialControl());
            assertTrue(createdMD.getUnitsFinancialControl().size() == 4);
            assertEquals(createdMD.getUnitsFinancialControl().get(0).getType(), ProgramAssemblerConstants.FINANCIAL_CONTROL_UNIT);

            assertNotNull(createdMD.getAttributes());
            assertTrue(createdMD.getAttributes().size() ==2);
            assertEquals("attributes-1", createdMD.getAttributes().get("attributes-1"));
            assertEquals("attributes-2", createdMD.getAttributes().get("attributes-2"));

            assertNotNull(createdMD.getMetaInfo());
            assertEquals("0", createdMD.getMetaInfo().getVersionInd());
           //TODO createTime
//            Calendar createTime = GregorianCalendar.getInstance();
//            createTime.set(2009, 4, 7, 12, 5, 36);
//            testDate = new Date(createTime.getTimeInMillis());
//            assertTrue(createdMD.getEffectiveDate().compareTo(testDate) == 0);

        } catch (Exception e) {
            fail(e.getMessage());
        }
	}

	@Test
	@Ignore
	public void testCreateProgramRequirement() throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
		ProgramRequirementInfo progReqInfo = new ProgramRequirementInfo();

		ProgramRequirementInfo createdInfo = programService.createProgramRequirement(progReqInfo);
		assertNotNull(createdInfo);
	}

    @Test
    public void testDeleteMajorDiscipline() {
        try {
        	MajorDisciplineDataGenerator generator = new MajorDisciplineDataGenerator();
        	MajorDisciplineInfo majorDisciplineInfo = generator.getMajorDisciplineInfoTestData();
            assertNotNull(majorDisciplineInfo);
            fixLoCategoryIds(majorDisciplineInfo.getLearningObjectives());
            MajorDisciplineInfo createdMD = programService.createMajorDiscipline(majorDisciplineInfo);
            assertNotNull(createdMD);
            assertEquals(ProgramAssemblerConstants.DRAFT, createdMD.getState());
            assertEquals(ProgramAssemblerConstants.MAJOR_DISCIPLINE, createdMD.getType());
            assertEquals("00f5f8c5-fff1-4c8b-92fc-789b891e0849", createdMD.getCredentialProgramId());
            
            String majorDisciplineId = createdMD.getId();
            MajorDisciplineInfo retrievedMD = programService.getMajorDiscipline(majorDisciplineId);
            assertNotNull(retrievedMD);

            programService.deleteMajorDiscipline(majorDisciplineId);
            try {
            	retrievedMD = programService.getMajorDiscipline(majorDisciplineId);
                fail("Retrieval of deleted MajorDiscipline should have thrown exception");
            } catch (DoesNotExistException e) {}
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    private void fixLoCategoryIds(List<LoDisplayInfo> loDisplayInfoList) {
        for (LoDisplayInfo parentLo : loDisplayInfoList) {
            fixLoCategoryId(parentLo.getLoCategoryInfoList());
            fixLoCategoryIds(parentLo.getLoDisplayInfoList());
        }
    }
    private void fixLoCategoryId(List<LoCategoryInfo> loCategoryInfoList) {
        loCategoryInfoList.get(1).setId(OTHER_LO_CAT_ID);
    }

    @Test
    public void testUpdateMajorDiscipline() {
        try {
        	MajorDisciplineDataGenerator generator = new MajorDisciplineDataGenerator();
        	MajorDisciplineInfo majorDisciplineInfo = generator.getMajorDisciplineInfoTestData();
            assertNotNull(majorDisciplineInfo);
//            MajorDisciplineInfo createdMD = programService.createMajorDiscipline(majorDisciplineInfo);
            MajorDisciplineInfo major = programService.getMajorDiscipline("d4ea77dd-b492-4554-b104-863e42c5f8b7");
            assertNotNull(major);

            // minimal sanity check
            assertNotNull(major.getCode());
            assertEquals("ANTH", major.getCode());
            assertNotNull(major.getType());
            assertEquals(ProgramAssemblerConstants.MAJOR_DISCIPLINE, major.getType());
            assertNotNull(major.getState());
            assertEquals(ProgramAssemblerConstants.ACTIVE, major.getState());
            assertNotNull(major.getId());
            assertEquals("d4ea77dd-b492-4554-b104-863e42c5f8b7", major.getId());
            assertNotNull(major.getShortTitle());
            assertEquals("Anthro", major.getShortTitle());
            assertNotNull(major.getLongTitle());
            assertEquals("Anthropology", major.getLongTitle());

            // update some fields
            major.getCampusLocations().add("MAIN");
            major.setLongTitle(major.getLongTitle() + "-updated");
            major.getAttributes().put("PIES", "APPLE");

            major.setCip2000Code(major.getCip2000Code() + "-updated");
            major.setDiplomaTitle(major.getDiplomaTitle() + "-updated");
            major.setTranscriptTitle(major.getTranscriptTitle() + "-updated");

            for (AdminOrgInfo orgInfo : major.getDivisionsFinancialControl()) {
                orgInfo.setOrgId(orgInfo.getOrgId()+"-updated");
            }
            for (AdminOrgInfo orgInfo : major.getUnitsDeployment()) {
                orgInfo.setOrgId(orgInfo.getOrgId()+"-updated");
            }

           //Perform the update
            MajorDisciplineInfo updatedMD = programService.updateMajorDiscipline(major);

            //Verify the update
            verifyUpdate(updatedMD);

            // Now explicitly get it
            MajorDisciplineInfo retrievedMD = programService.getMajorDiscipline(major.getId());
            verifyUpdate(retrievedMD);

            //TODO: add version update

        } catch (Exception e) {
        	e.printStackTrace();
            fail(e.getMessage());
        }
    }

    private void verifyUpdate(MajorDisciplineInfo updatedMD) {
    	assertNotNull(updatedMD);

        assertEquals(4, updatedMD.getAttributes().size());
        assertNotNull(updatedMD.getAttributes().get("PIES"));
        assertEquals("APPLE", updatedMD.getAttributes().get("PIES"));

        assertEquals(3, updatedMD.getCampusLocations().size());
        assertEquals("NORTH", updatedMD.getCampusLocations().get(0));
        assertEquals("SOUTH", updatedMD.getCampusLocations().get(1));
        assertEquals("MAIN", updatedMD.getCampusLocations().get(2));

//        assertEquals(1, updatedMD.getProgramRequirements().size());

        assertEquals("Anthropology-updated", updatedMD.getLongTitle());
        assertEquals( "CIP2000CODE-updated", updatedMD.getCip2000Code());
        assertEquals("TRANSCRIPT-TITLE-updated", updatedMD.getTranscriptTitle());
        assertEquals("DIPLOMA-TITLE-updated", updatedMD.getDiplomaTitle() );

//        for (AdminOrgInfo orgInfo : major.getDivisionsFinancialControl()) {
//            orgInfo.setOrgId(orgInfo.getOrgId()+"-updated");
//        }
//        for (AdminOrgInfo orgInfo : major.getUnitsDeployment()) {
//            orgInfo.setOrgId(orgInfo.getOrgId()+"-updated");
//        }

    }

    @Test
    @Ignore
    public void testCreateBaccCredentialProgram() {
    	CredentialProgramDataGenerator generator = new CredentialProgramDataGenerator(ProgramAssemblerConstants.BACCALAUREATE_PROGRAM);
    	CredentialProgramInfo credentialProgramInfo = null;
        try {
            assertNotNull(credentialProgramInfo = generator.getCPTestData());
            CredentialProgramInfo createdCP = programService.createCredentialProgram(credentialProgramInfo);
            assertNotNull(createdCP);
            assertEquals(ProgramAssemblerConstants.DRAFT, createdCP.getState());
            assertEquals(ProgramAssemblerConstants.BACCALAUREATE_PROGRAM, createdCP.getCredentialProgramType());
        } catch (Exception e) {
            fail(e.getMessage());
        }
	}

    @Test
    @Ignore public void testDeleteBaccCredentialProgram() {
        try {
        	CredentialProgramDataGenerator generator = new CredentialProgramDataGenerator(ProgramAssemblerConstants.BACCALAUREATE_PROGRAM);
        	CredentialProgramInfo credentialProgramInfo = generator.getCPTestData();
            assertNotNull(credentialProgramInfo);
            CredentialProgramInfo createdCP = programService.createCredentialProgram(credentialProgramInfo);
            assertNotNull(createdCP);
            assertEquals(ProgramAssemblerConstants.DRAFT, createdCP.getState());
            assertEquals(ProgramAssemblerConstants.BACCALAUREATE_PROGRAM, createdCP.getCredentialProgramType());
            String credentialProgramId = createdCP.getId();
            CredentialProgramInfo retrievedCP = programService.getCredentialProgram(credentialProgramId);
            assertNotNull(retrievedCP);

            programService.deleteMajorDiscipline(credentialProgramId);
            try {
            	retrievedCP = programService.getCredentialProgram(credentialProgramId);
                fail("Retrieval of deleted CredentialProgram should have thrown exception");
            } catch (DoesNotExistException e) {}
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    @Ignore public void testUpdateBaccCredentialProgram() {
        try {
        	CredentialProgramDataGenerator generator = new CredentialProgramDataGenerator(ProgramAssemblerConstants.BACCALAUREATE_PROGRAM);
        	CredentialProgramInfo credentialProgramInfo = generator.getCPTestData();
            assertNotNull(credentialProgramInfo);
            CredentialProgramInfo createdCP = programService.createCredentialProgram(credentialProgramInfo);
            assertNotNull(createdCP);

            // minimal sanity check
            assertEquals("longTitle-test", createdCP.getLongTitle());
            assertEquals("shortTitle-test", createdCP.getShortTitle());
            assertEquals(ProgramAssemblerConstants.BACCALAUREATE_PROGRAM, createdCP.getCredentialProgramType());
            assertEquals(ProgramAssemblerConstants.DRAFT, createdCP.getState());

            // update some fields
            createdCP.setLongTitle("longTitle-toolong");
            createdCP.getProgramRequirements().remove(0);

            Map<String, String> attributes = createdCP.getAttributes();
            attributes.put("testKey", "testValue");
            createdCP.setAttributes(attributes);

           //Perform the update
            CredentialProgramInfo updatedCP = programService.updateCredentialProgram(createdCP);

            //Verify the update
            verifyUpdate(updatedCP);

            // Now explicitly get it
            CredentialProgramInfo retrievedCP = programService.getCredentialProgram(createdCP.getId());
            verifyUpdate(retrievedCP);

            //TODO: add version update

        } catch (Exception e) {
        	e.printStackTrace();
            fail(e.getMessage());
        }
    }

    private void verifyUpdate(CredentialProgramInfo updatedCP) {
    	assertNotNull(updatedCP);

        assertEquals(3, updatedCP.getAttributes().size());
        assertNotNull(updatedCP.getAttributes().get("testKey"));
        assertEquals("testValue", updatedCP.getAttributes().get("testKey"));

        assertEquals(1, updatedCP.getProgramRequirements().size());

        assertEquals("longTitle-toolong", updatedCP.getLongTitle());
    }

    @Test
    public void testCreateCoreProgram() {
    	CoreProgramDataGenerator generator = new CoreProgramDataGenerator();
    	CoreProgramInfo coreProgramInfo = null;
        try {
            assertNotNull(coreProgramInfo = generator.getCoreProgramTestData());
            CoreProgramInfo createdCP = programService.createCoreProgram(coreProgramInfo);
            assertNotNull(createdCP);
            assertEquals(ProgramAssemblerConstants.DRAFT, createdCP.getState());
            assertEquals(ProgramAssemblerConstants.CORE_PROGRAM, createdCP.getType());
        } catch (Exception e) {
            fail(e.getMessage());
        }
	}
}
