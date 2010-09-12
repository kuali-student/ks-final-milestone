package org.kuali.student.lum.program.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
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
    @Ignore public void testCreateMajorDiscipline() {
		MajorDisciplineDataGenerator generator = new MajorDisciplineDataGenerator();
        MajorDisciplineInfo majorDisciplineInfo = null;
        try {
            assertNotNull(majorDisciplineInfo = generator.getMajorDisciplineInfoTestData());
            MajorDisciplineInfo createdMD = programService.createMajorDiscipline(majorDisciplineInfo);
            assertNotNull(createdMD);
            assertEquals(ProgramAssemblerConstants.DRAFT, createdMD.getState());
            assertEquals(ProgramAssemblerConstants.MAJOR_DISCIPLINE, createdMD.getType());
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
	@Ignore
	public void testGetProgramRequirement() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
		ProgramRequirementInfo progReqInfo = programService.getProgramRequirement("CLU-2");
		assertNotNull(progReqInfo);
	}

	@Test(expected = MissingParameterException.class)
	public void testGetProgramRequirement_nullId() throws Exception {
		programService.getProgramRequirement(null);
	}

	@Test(expected = DoesNotExistException.class)
	public void testGetProgramRequirement_badId() throws Exception {
		programService.getProgramRequirement("CLU-XXX ");
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
            major = programService.getMajorDiscipline("D4EA77DD-B492-4554-B104-863E42C5F8B7");

            assertNotNull(major);

            assertNotNull(major.getIntensity());
            assertEquals("kuali.atp.duration.full", major.getIntensity());
            assertNotNull(major.getReferenceURL());
            assertEquals("http://www.google.ca", major.getReferenceURL());
            assertEquals(1, major.getPublishedInstructors().size());

            assertEquals("INSTR-1", major.getPublishedInstructors().get(0).getPersonId());
            assertNotNull(major.getCredentialProgramId());
            assertEquals("D02DBBD3-20E2-410D-AB52-1BD6D362748B", major.getCredentialProgramId());

            assertNotNull(major.getVariations());
            assertTrue(major.getVariations().size() == 1);
            assertEquals("BS", major.getVariations().get(0).getCode());

            assertNotNull(major.getCode());
            assertEquals("ANTH", major.getCode());
            assertNotNull(major.getCip2000Code());
            assertEquals(major.getCip2000Code(), "CIP2000CODE");
            assertNotNull(major.getCip2010Code());
            assertEquals(major.getCip2010Code(), "CIP2010CODE");
            assertNotNull(major.getHegisCode());
            assertEquals(major.getHegisCode(), "HEGISCODE");
            assertNotNull(major.getUniversityClassification());
            assertEquals(major.getUniversityClassification(), "UNIVERSITYCLASSIFICATIONCODE");
            assertNotNull(major.getSelectiveEnrollmentCode());
            assertEquals(major.getSelectiveEnrollmentCode(), "SELECTIVEENROLLMENTCODE");

            assertNotNull(major.getResultOptions());
            assertTrue(major.getResultOptions().size() == 1);
            assertEquals("kuali.certificateType.degree", major.getResultOptions().get(0));

            assertNotNull(major.getStdDuration());
            assertEquals("kuali.atp.duration.Week", major.getStdDuration().getAtpDurationTypeKey());
            assertEquals(new Integer(100), major.getStdDuration().getTimeQuantity());
            assertNotNull(major.getStartTerm());
            assertEquals("kuali.atp.SU2009-2010S1", major.getStartTerm());

            //TODO end term
            //TODO end prog entry term
            assertNotNull(major.getNextReviewPeriod());
            assertEquals("kuali.atp.SU2009-2010S1", major.getNextReviewPeriod());

            assertNotNull(major.getEffectiveDate());
            //TODO effectiveDate
//            Calendar effectiveDate = GregorianCalendar.getInstance();
//            effectiveDate.set(1984, 7, 1, 0, 0, 0);
//            Date testDate = new Date(effectiveDate.getTimeInMillis());
//            assertTrue(major.getEffectiveDate().compareTo(testDate) == 0);

            assertNotNull(major.getShortTitle());
            assertEquals("Anthro", major.getShortTitle());
            assertNotNull(major.getLongTitle());
            assertEquals("Anthropology", major.getLongTitle());
            assertNotNull(major.getTranscriptTitle());
            assertEquals(major.getTranscriptTitle(), "TRANSCRIPT-TITLE");
            assertNotNull(major.getDiplomaTitle());
            assertEquals(major.getDiplomaTitle(), "DIPLOMA-TITLE");
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
            assertEquals("00F5F8C5-FFF1-4C8B-92FC-789B891E0849", major.getOrgCoreProgram().getId());
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
            assertTrue(major.getAttributes().size() ==2);
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
            assertEquals("D4EA77DD-B492-4554-B104-863E42C5F8B7", major.getId());

            /*
            MajorDisciplineInfo createdMD = programService.createMajorDiscipline(majorDisciplineInfo);
            assertNotNull(createdMD);

            // get it fresh from database
            MajorDisciplineInfo retrievedMD = programService.getMajorDiscipline(createdMD.getId());
            assertNotNull(retrievedMD);

            // confirm it has the right contents
            assertEquals("longTitle-test", retrievedMD.getLongTitle());
            assertEquals("shortTitle-test", retrievedMD.getShortTitle());
            assertEquals("programLevel-test", retrievedMD.getProgramLevel());
            assertEquals("credentialProgramId-test", retrievedMD.getCredentialProgramId());
            assertEquals(ProgramAssemblerConstants.MAJOR_DISCIPLINE, retrievedMD.getType());
            assertEquals(ProgramAssemblerConstants.DRAFT, retrievedMD.getState());

            assertEquals("plain-test", retrievedMD.getDescr().getPlain());
            assertEquals("formatted-test", retrievedMD.getDescr().getFormatted());

            assertEquals(2, retrievedMD.getVariations().size());
            ProgramVariationInfo variation = retrievedMD.getVariations().get(0);
            assertEquals(ProgramAssemblerConstants.PROGRAM_VARIATION, variation.getType());

            assertEquals(2, retrievedMD.getProgramRequirements().size());
            String programRequirements = retrievedMD.getProgramRequirements().get(0);
            assertTrue("programRequirements-test".equals(programRequirements));

            assertEquals(2, retrievedMD.getLearningObjectives().size());
            LoDisplayInfo lo = retrievedMD.getLearningObjectives().get(0);
            assertTrue("kuali.lo.type.singleUse".equals(lo.getLoInfo().getType()));
            assertTrue("loCategoryType.skillarea".equals(lo.getLoCategoryInfoList().get(0).getType()));


            assertEquals(2, retrievedMD.getAttributes().size());
            String[] attrKeys = {"attributes-1", "attributes-2"};
            for (String key : attrKeys) {
                String value = retrievedMD.getAttributes().get(key);
                assertNotNull(value);
                assertEquals(key, value);
            }
            */

          } catch (Exception e) {
        	e.printStackTrace();
            fail(e.getMessage());
        }
    }

    @Test
    @Ignore
    public void testGetVariationsByMajorDisciplineId(){
    	MajorDisciplineInfo majorDisciplineInfo = null;
        try {
			MajorDisciplineDataGenerator generator = new MajorDisciplineDataGenerator();
//			majorDisciplineInfo = generator.getMajorDisciplineInfoTestData();

			majorDisciplineInfo = programService.getMajorDiscipline("D4EA77DD-B492-4554-B104-863E42C5F8B7");
			assertNotNull(majorDisciplineInfo);

			List<ProgramVariationInfo> pvInfos = programService.getVariationsByMajorDisciplineId("D4EA77DD-B492-4554-B104-863E42C5F8B7");
			assertNotNull(pvInfos);
			assertEquals(pvInfos.size(), majorDisciplineInfo.getVariations().size());

			/*MajorDisciplineInfo createdMD = programService.createMajorDiscipline(majorDisciplineInfo);
			assertNotNull(createdMD);

			// get it fresh from database
			MajorDisciplineInfo retrievedMD = programService.getMajorDiscipline(createdMD.getId());
			assertNotNull(retrievedMD);

			// get program variations
			List<ProgramVariationInfo> pvInfos = programService.getVariationsByMajorDisciplineId(retrievedMD.getId());
			assertNotNull(pvInfos);
			assertEquals(pvInfos.size(), retrievedMD.getVariations().size());
			assertEquals(pvInfos, retrievedMD.getVariations());*/

        } catch (Exception e) {
        	e.printStackTrace();
            fail(e.getMessage());
        }
    }

    @Test
    @Ignore public void testDeleteMajorDiscipline() {
        try {
        	MajorDisciplineDataGenerator generator = new MajorDisciplineDataGenerator();
        	MajorDisciplineInfo majorDisciplineInfo = generator.getMajorDisciplineInfoTestData();
            assertNotNull(majorDisciplineInfo);
            MajorDisciplineInfo createdMD = programService.createMajorDiscipline(majorDisciplineInfo);
            assertNotNull(createdMD);
            assertEquals(ProgramAssemblerConstants.DRAFT, createdMD.getState());
            assertEquals(ProgramAssemblerConstants.MAJOR_DISCIPLINE, createdMD.getType());
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

    @Test
    @Ignore public void testUpdateMajorDiscipline() {
        try {
        	MajorDisciplineDataGenerator generator = new MajorDisciplineDataGenerator();
        	MajorDisciplineInfo majorDisciplineInfo = generator.getMajorDisciplineInfoTestData();
            assertNotNull(majorDisciplineInfo);
            MajorDisciplineInfo createdMD = programService.createMajorDiscipline(majorDisciplineInfo);
            assertNotNull(createdMD);

            // minimal sanity check
            assertEquals("longTitle-test", createdMD.getLongTitle());
            assertEquals("shortTitle-test", createdMD.getShortTitle());
            assertEquals("credentialProgramId-test", createdMD.getCredentialProgramId());
            assertEquals(ProgramAssemblerConstants.MAJOR_DISCIPLINE, createdMD.getType());
            assertEquals(ProgramAssemblerConstants.DRAFT, createdMD.getState());

            // update some fields
            createdMD.getCampusLocations().add("MAIN");
            createdMD.setLongTitle("longTitle-toolong");
            createdMD.getProgramRequirements().remove(0);

            Map<String, String> attributes = createdMD.getAttributes();
            attributes.put("testKey", "testValue");
            createdMD.setAttributes(attributes);

           //Perform the update
            MajorDisciplineInfo updatedMD = programService.updateMajorDiscipline(createdMD);

            //Verify the update
            verifyUpdate(updatedMD);

            // Now explicitly get it
            MajorDisciplineInfo retrievedMD = programService.getMajorDiscipline(createdMD.getId());
            verifyUpdate(retrievedMD);

            //TODO: add version update

        } catch (Exception e) {
        	e.printStackTrace();
            fail(e.getMessage());
        }
    }

    private void verifyUpdate(MajorDisciplineInfo updatedMD) {
    	assertNotNull(updatedMD);

        assertEquals(3, updatedMD.getAttributes().size());
        assertNotNull(updatedMD.getAttributes().get("testKey"));
        assertEquals("testValue", updatedMD.getAttributes().get("testKey"));

        assertEquals(3, updatedMD.getCampusLocations().size());
        assertEquals("MAIN", updatedMD.getCampusLocations().get(3));

        assertEquals(1, updatedMD.getProgramRequirements().size());

        assertEquals("longTitle-toolong", updatedMD.getLongTitle());
    }
}
