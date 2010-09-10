package org.kuali.student.lum.program.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.student.core.assembly.data.Metadata;
import org.kuali.student.core.assembly.dictionary.MetadataServiceImpl;
import org.kuali.student.core.dto.RichTextInfo;
import org.kuali.student.core.exceptions.AlreadyExistsException;
import org.kuali.student.core.exceptions.DataValidationErrorException;
import org.kuali.student.core.exceptions.DoesNotExistException;
import org.kuali.student.core.exceptions.InvalidParameterException;
import org.kuali.student.core.exceptions.MissingParameterException;
import org.kuali.student.core.exceptions.OperationFailedException;
import org.kuali.student.core.exceptions.PermissionDeniedException;
import org.kuali.student.core.statement.dto.ReqCompFieldInfo;
import org.kuali.student.core.statement.dto.ReqCompFieldTypeInfo;
import org.kuali.student.core.statement.dto.ReqComponentInfo;
import org.kuali.student.core.statement.dto.ReqComponentTypeInfo;
import org.kuali.student.core.statement.dto.StatementOperatorTypeKey;
import org.kuali.student.core.statement.dto.StatementTreeViewInfo;
import org.kuali.student.lum.course.dto.LoDisplayInfo;
import org.kuali.student.lum.course.service.assembler.CourseAssemblerConstants;
import org.kuali.student.lum.lo.dto.LoCategoryInfo;
import org.kuali.student.lum.lo.dto.LoInfo;
import org.kuali.student.lum.program.dto.CoreProgramInfo;
import org.kuali.student.lum.program.dto.CredentialProgramInfo;
import org.kuali.student.lum.program.dto.MajorDisciplineInfo;
import org.kuali.student.lum.program.dto.ProgramRequirementInfo;
import org.kuali.student.lum.program.dto.ProgramVariationInfo;
import org.kuali.student.lum.program.service.ProgramService;
import org.kuali.student.lum.program.service.assembler.MajorDisciplineDataGenerator;
import org.kuali.student.lum.program.service.assembler.ProgramAssemblerConstants;
import org.springframework.beans.BeanUtils;
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
    public void testGetProgramRequirement() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        ProgramRequirementInfo progReqInfo = programService.getProgramRequirement("PROGREQ-1", null, null);
        assertNotNull(progReqInfo);

        checkTreeView(progReqInfo, false);


        List<LoDisplayInfo> los = progReqInfo.getLearningObjectives();
        assertNotNull(los);
        assertEquals(1, los.size());
        LoDisplayInfo ldi1 = los.get(0);
        assertNotNull(ldi1);

        LoInfo loInfo1 = ldi1.getLoInfo();
        assertNotNull(loInfo1);
        assertEquals("81abea67-3bcc-4088-8348-e265f3670145", loInfo1.getId());
        assertEquals("Desc4", loInfo1.getDesc().getPlain());
        assertEquals("Edit Wiki Message Structure", loInfo1.getName());
        assertEquals("kuali.loRepository.key.singleUse", loInfo1.getLoRepositoryKey());
        assertEquals("draft", loInfo1.getState());
        assertEquals("kuali.lo.type.singleUse", loInfo1.getType());
    }

    @Test
    @Ignore
    public void testGetProgramRequirementNL() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        ProgramRequirementInfo progReqInfo = programService.getProgramRequirement("PROGREQ-1", "KUALI.RULEEDIT", "en");
        assertNotNull(progReqInfo);

        checkTreeView(progReqInfo, true);
    }

	private void checkTreeView(final ProgramRequirementInfo progReqInfo,  final boolean checkNaturalLanguage) {
		StatementTreeViewInfo rootTree = progReqInfo.getStatement();
        assertNotNull(rootTree);
        List<StatementTreeViewInfo> subTreeView = rootTree.getStatements();
        assertNotNull(subTreeView);
        assertEquals(2, subTreeView.size());
        StatementTreeViewInfo subTree1 = subTreeView.get(0);
        StatementTreeViewInfo subTree2 = subTreeView.get(1);

        // Check root tree
        assertNotNull(rootTree);
        assertEquals(2, subTreeView.size());
        assertNotNull(subTree1);
        assertNotNull(subTree2);

        // Check reqComps of sub-tree 1
        assertEquals("STMT-TV-2", subTree1.getId());
        assertEquals(2, subTree1.getReqComponents().size());
        assertEquals("REQCOMP-TV-1", subTree1.getReqComponents().get(0).getId());
        assertEquals("REQCOMP-TV-2", subTree1.getReqComponents().get(1).getId());
        if (checkNaturalLanguage) {
        	assertEquals("Student must have completed all of MATH 152, MATH 180", subTree1.getReqComponents().get(0).getNaturalLanguageTranslation());
        	assertEquals("Student needs a minimum GPA of 3.5 in MATH 152, MATH 180", subTree1.getReqComponents().get(1).getNaturalLanguageTranslation());
        	assertEquals("Student must have completed all of MATH 152, MATH 180 " +
        			"and Student needs a minimum GPA of 3.5 in MATH 152, MATH 180",
        			subTree1.getNaturalLanguageTranslation());
        }

        // Check reqComps of sub-tree 2
        assertEquals("STMT-TV-3", subTree2.getId());
        assertEquals(2, subTree2.getReqComponents().size());
        assertEquals("REQCOMP-TV-3", subTree2.getReqComponents().get(0).getId());
        assertEquals("REQCOMP-TV-4", subTree2.getReqComponents().get(1).getId());
        if (checkNaturalLanguage) {
        	assertEquals("Student must have completed 1 of MATH 152, MATH 180", subTree2.getReqComponents().get(0).getNaturalLanguageTranslation());
        	assertEquals("Student needs a minimum GPA of 4.0 in MATH 152, MATH 180", subTree2.getReqComponents().get(1).getNaturalLanguageTranslation());
        	assertEquals("Student must have completed 1 of MATH 152, MATH 180 " +
        			"and Student needs a minimum GPA of 4.0 in MATH 152, MATH 180",
        			subTree2.getNaturalLanguageTranslation());

        	assertEquals(
        			"(Student must have completed all of MATH 152, MATH 180 and Student needs a minimum GPA of 3.5 in MATH 152, MATH 180) " +
        			"or (Student must have completed 1 of MATH 152, MATH 180 and Student needs a minimum GPA of 4.0 in MATH 152, MATH 180)",
        			rootTree.getNaturalLanguageTranslation());
        }
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
            assertEquals(core.getDivisionsContentOwner().get(0), "48");
            assertNotNull(core.getDivisionsStudentOversight());
            assertTrue(core.getDivisionsStudentOversight().size() == 1);
            assertEquals(core.getDivisionsStudentOversight().get(0), "50");
            assertNotNull(core.getUnitsContentOwner());
            assertTrue(core.getUnitsContentOwner().size() == 1);
            assertEquals(core.getUnitsContentOwner().get(0), "49");
            assertNotNull(core.getUnitsStudentOversight());
            assertTrue(core.getUnitsStudentOversight().size() == 1);
            assertEquals(core.getUnitsStudentOversight().get(0), "51");

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
            assertTrue(major.getVariations().size() == 2);
            assertEquals("ZOOA", major.getVariations().get(0).getCode());
            assertEquals("ARCB", major.getVariations().get(1).getCode());

            assertNotNull(major.getCode());
            assertEquals("ANTH", major.getCode());
            assertNotNull(major.getCip2000Code());
            assertEquals("450202", major.getCip2000Code());
            assertNotNull(major.getCip2010Code());
            assertEquals("450201", major.getCip2010Code());
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
            assertEquals(major.getDivisionsContentOwner().get(0), "31");
            assertNotNull(major.getDivisionsStudentOversight());
            assertTrue(major.getDivisionsStudentOversight().size() == 1);
            assertEquals(major.getDivisionsStudentOversight().get(0), "32");
            assertNotNull(major.getDivisionsDeployment());
            assertTrue(major.getDivisionsDeployment().size() == 1);
            assertEquals(major.getDivisionsDeployment().get(0), "33");
            assertNotNull(major.getDivisionsFinancialResources());
            assertTrue(major.getDivisionsFinancialResources().size() == 1);
            assertEquals(major.getDivisionsFinancialResources().get(0), "34");
            assertNotNull(major.getDivisionsFinancialControl());
            assertTrue(major.getDivisionsFinancialControl().size() == 1);
            assertEquals(major.getDivisionsFinancialControl().get(0), "36");

            assertNotNull(major.getUnitsContentOwner());
            assertTrue(major.getUnitsContentOwner().size() == 1);
            assertEquals(major.getUnitsContentOwner().get(0), "41");
            assertNotNull(major.getUnitsStudentOversight());
            assertTrue(major.getUnitsStudentOversight().size() == 1);
            assertEquals(major.getUnitsStudentOversight().get(0), "42");
            assertNotNull(major.getUnitsDeployment());
            assertTrue(major.getUnitsDeployment().size() == 1);
            assertEquals(major.getUnitsDeployment().get(0), "43");
            assertNotNull(major.getUnitsFinancialResources());
            assertTrue(major.getUnitsFinancialResources().size() == 1);
            assertEquals(major.getUnitsFinancialResources().get(0), "44");
            assertNotNull(major.getUnitsFinancialControl());
            assertTrue(major.getUnitsFinancialControl().size() == 2);
            assertEquals(major.getUnitsFinancialControl().get(0), "46");
            assertEquals(major.getUnitsFinancialControl().get(1), "47");
            assertNotNull(major.getAttributes());
            assertEquals(2, major.getAttributes().size());
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
            assertEquals("ZOOA", pvInfo.getCode());
            assertEquals("Zooarchaeology", pvInfo.getDescr().getPlain());
            assertEquals("Zooarchaeology", pvInfo.getLongTitle());
            assertEquals("ZooArch", pvInfo.getShortTitle());
            assertEquals("VAR-200", pvInfo.getId());
            assertEquals("Active", pvInfo.getState());

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
            assertEquals("divisionsContentOwner-test", createdMD.getDivisionsContentOwner().get(0));

            assertNotNull(createdMD.getDivisionsStudentOversight());
            assertTrue(createdMD.getDivisionsStudentOversight().size() == 4);
            assertEquals("divisionsStudentOversight-test", createdMD.getDivisionsStudentOversight().get(0));

            assertNotNull(createdMD.getDivisionsDeployment());
            assertTrue(createdMD.getDivisionsDeployment().size() == 4);
            assertEquals("divisionsDeployment-test", createdMD.getDivisionsDeployment().get(0));

            assertNotNull(createdMD.getDivisionsFinancialResources());
            assertTrue(createdMD.getDivisionsFinancialResources().size() == 4);
            assertEquals("divisionsFinancialResources-test", createdMD.getDivisionsFinancialResources().get(0));

            assertNotNull(createdMD.getDivisionsFinancialControl());
            assertTrue(createdMD.getDivisionsFinancialControl().size() == 4);
            assertEquals("divisionsFinancialControl-test", createdMD.getDivisionsFinancialControl().get(0));

            assertNotNull(createdMD.getUnitsContentOwner());
            assertTrue(createdMD.getUnitsContentOwner().size() == 4);
            assertEquals("unitsContentOwner-test", createdMD.getUnitsContentOwner().get(0));

            assertNotNull(createdMD.getUnitsStudentOversight());
            assertTrue(createdMD.getUnitsStudentOversight().size() == 4);
            assertEquals("unitsStudentOversight-test", createdMD.getUnitsStudentOversight().get(1));

            assertNotNull(createdMD.getUnitsDeployment());
            assertTrue(createdMD.getUnitsDeployment().size() == 4);
            assertEquals("unitsDeployment-test", createdMD.getUnitsDeployment().get(0));

            assertNotNull(createdMD.getUnitsFinancialResources());
            assertTrue(createdMD.getUnitsFinancialResources().size() == 4);
            assertEquals("unitsFinancialResources-test", createdMD.getUnitsFinancialResources().get(0));

            assertNotNull(createdMD.getUnitsFinancialControl());
            assertTrue(createdMD.getUnitsFinancialControl().size() == 4);
            assertEquals("unitsFinancialControl-test", createdMD.getUnitsFinancialControl().get(0));

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

    @Test(expected = MissingParameterException.class)
    public void testCreateProgramRequirement_null() throws Exception {
    	programService.createProgramRequirement(null);
    }

    @Test
    public void testCreateProgramRequirement() throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException {
    	ProgramRequirementInfo progReq = createProgramRequirementTestData();
    	ProgramRequirementInfo createdProgReq = programService.createProgramRequirement(progReq);
    	checkProgramRequirement(progReq, createdProgReq);

    	ProgramRequirementInfo progReq2 = programService.getProgramRequirement(createdProgReq.getId(), null, null);
    	checkProgramRequirement(progReq, progReq2);
    }

	private ProgramRequirementInfo createProgramRequirementTestData() {
		ProgramRequirementInfo progReq = new ProgramRequirementInfo();
    	progReq.setShortTitle("Short Title");
    	progReq.setLongTitle("Long title");
    	progReq.setDescr(toRichText("Program Requirement"));

    	List<LoDisplayInfo> los = new ArrayList<LoDisplayInfo>(0);

		LoDisplayInfo loDisplayInfo = new LoDisplayInfo();
		LoInfo loInfo = new LoInfo();
		loInfo.setDesc(toRichText("Program Requirement LO Info"));
		loInfo.setLoRepositoryKey("lo rep key");
		loDisplayInfo.setLoInfo(loInfo);
        los.add(loDisplayInfo);
    	progReq.setLearningObjectives(los);

      	StatementTreeViewInfo statement = createStatementTree();
    	progReq.setStatement(statement);
    	progReq.setType(ProgramAssemblerConstants.PROGRAM_REQUIREMENT);
		return progReq;
	}

	private static void checkProgramRequirement(
			ProgramRequirementInfo orig, ProgramRequirementInfo created) {
		assertNotNull(orig);
		assertNotNull(created);
		assertTrue(EqualsBuilder.reflectionEquals(orig, created, new String[]{"id", "descr", "learningObjectives","statement","attributes","metaInfo"}));
    	checkLoDisplays(orig.getLearningObjectives(), created.getLearningObjectives());
		if (orig.getId() != null) {
			assertEquals(orig.getId(), created.getId());
		}

    	StatementTreeViewInfo statement2 = created.getStatement();
    	checkStatementTreeView(orig.getStatement(), created.getStatement());
    	assertNotNull(statement2);
	}

	private static void checkStatementTreeView(StatementTreeViewInfo statement,
			StatementTreeViewInfo statement2) {
		assertNotNull(statement);
		assertNotNull(statement2);
		assertTrue(EqualsBuilder.reflectionEquals(statement, statement2, new String[]{"id", "desc", "attributes", "metaInfo", "statements", "reqComponents"}));
		if (statement.getId() != null) {
			assertEquals(statement.getId(), statement2.getId());
		}
		checkRichText(statement.getDesc(), statement2.getDesc());
		checkStatementTreeViews(statement.getStatements(), statement2.getStatements());
		checkReqComponents(statement.getReqComponents(), statement2.getReqComponents());
	}

	private static void checkReqComponents(List<ReqComponentInfo> reqComponents,
			List<ReqComponentInfo> reqComponents2) {
		assertNotNull(reqComponents);
		assertNotNull(reqComponents2);
		assertEquals(reqComponents.size(), reqComponents2.size());
		for (int i = 0; i < reqComponents.size(); i++) {
			checkReqComponent(reqComponents.get(i), reqComponents2.get(i));
		}
	}

	private static void checkReqComponent(ReqComponentInfo reqComponent,
			ReqComponentInfo reqComponent2) {
		assertNotNull(reqComponent);
		assertNotNull(reqComponent2);
		assertTrue(EqualsBuilder.reflectionEquals(reqComponent, reqComponent2, new String[]{"id", "desc", "reqCompFields", "requiredComponentType", "naturalLanguageTranslation", "metaInfo"}));
		if (reqComponent.getId() != null) {
			assertEquals(reqComponent.getId(), reqComponent2.getId());
		}
		checkRichText(reqComponent.getDesc(), reqComponent2.getDesc());
		checkReqCompFields(reqComponent.getReqCompFields(), reqComponent.getReqCompFields());
		// TODO checkReqComponentType(reqComponent.getRequiredComponentType(), reqComponent2.getRequiredComponentType());
	}

	private static void checkReqComponentType(
			ReqComponentTypeInfo requiredComponentType,
			ReqComponentTypeInfo requiredComponentType2) {
		assertNotNull(requiredComponentType);
		assertNotNull(requiredComponentType2);
		checkReqCompFieldTypes(requiredComponentType.getReqCompFieldTypeInfos(), requiredComponentType2.getReqCompFieldTypeInfos());
	}

	private static void checkReqCompFieldTypes(
			List<ReqCompFieldTypeInfo> reqCompFieldTypeInfos,
			List<ReqCompFieldTypeInfo> reqCompFieldTypeInfos2) {
		assertNotNull(reqCompFieldTypeInfos);
		assertNotNull(reqCompFieldTypeInfos2);
		assertEquals(reqCompFieldTypeInfos.size(), reqCompFieldTypeInfos2.size());
		for (int i = 0; i < reqCompFieldTypeInfos.size(); i++) {
			checkReqCompFieldType(reqCompFieldTypeInfos.get(i), reqCompFieldTypeInfos2.get(i));
		}
	}

	private static void checkReqCompFieldType(
			ReqCompFieldTypeInfo reqCompFieldTypeInfo,
			ReqCompFieldTypeInfo reqCompFieldTypeInfo2) {
		assertNotNull(reqCompFieldTypeInfo);
		assertNotNull(reqCompFieldTypeInfo2);
		
	}

	private static void checkReqCompFields(List<ReqCompFieldInfo> reqCompFields,
			List<ReqCompFieldInfo> reqCompFields2) {
		assertNotNull(reqCompFields);
		assertNotNull(reqCompFields2);
		assertEquals(reqCompFields.size(), reqCompFields2.size());
		for (int i = 0; i < reqCompFields.size(); i++) {
			checkReqCompField(reqCompFields.get(i), reqCompFields2.get(i));
		}
	}

	private static void checkReqCompField(ReqCompFieldInfo reqCompField,
			ReqCompFieldInfo reqCompField2) {
		assertNotNull(reqCompField);
		assertNotNull(reqCompField2);
		assertTrue(EqualsBuilder.reflectionEquals(reqCompField,reqCompField2));
	}

	private static void checkStatementTreeViews(List<StatementTreeViewInfo> statements,
			List<StatementTreeViewInfo> statements2) {
		assertNotNull(statements);
		assertNotNull(statements2);
		assertEquals(statements.size(), statements2.size());
		for (int i = 0; i < statements.size(); i++) {
			checkStatementTreeView(statements.get(i), statements2.get(i));
		}
	}

	private static void checkLoDisplays(List<LoDisplayInfo> los,
			List<LoDisplayInfo> los2) {
		assertNotNull(los);
		assertNotNull(los2);
    	assertEquals(los.size(), los2.size());
    	for (int i = 0; i < los.size(); i++) {
    		LoDisplayInfo ldi1 = los.get(i);
    		LoDisplayInfo ldi2 = los2.get(i);
    		checkLoDisplay(ldi1, ldi2);
    	}
	}

    private static void checkLoDisplay(LoDisplayInfo ldi1, LoDisplayInfo ldi2) {
		assertNotNull(ldi1);
		assertNotNull(ldi2);
		assertTrue(EqualsBuilder.reflectionEquals(ldi1, ldi2, new String[]{"loInfo","loDisplayInfoList","loCategoryInfoList"}));

		LoInfo li1 = ldi1.getLoInfo();
		LoInfo li2 = ldi2.getLoInfo();
		checkLo(li1, li2);
		checkLoDisplayLists(ldi1.getLoDisplayInfoList(), ldi2.getLoDisplayInfoList());
		checkLoCategorys(ldi1.getLoCategoryInfoList(), ldi2.getLoCategoryInfoList());
	}

	private static void checkLoCategorys(List<LoCategoryInfo> loCategoryInfoList,
			List<LoCategoryInfo> loCategoryInfoList2) {
		assertNotNull(loCategoryInfoList);
		assertNotNull(loCategoryInfoList2);
		assertEquals(loCategoryInfoList.size(), loCategoryInfoList2.size());
		for (int i = 0; i < loCategoryInfoList.size(); i++) {
			checkLoCategory(loCategoryInfoList.get(i), loCategoryInfoList2.get(i));
		}
	}

	private static void checkLoCategory(LoCategoryInfo loCategoryInfo,
			LoCategoryInfo loCategoryInfo2) {
		assertNotNull(loCategoryInfo);
		assertNotNull(loCategoryInfo2);
		assertTrue(EqualsBuilder.reflectionEquals(loCategoryInfo, loCategoryInfo2, new String[]{"desc","attributes","metaInfo"}));
		checkRichText(loCategoryInfo.getDesc(), loCategoryInfo2.getDesc());
	}

	private static void checkLoDisplayLists(List<LoDisplayInfo> di1, List<LoDisplayInfo> di2) {
		assertNotNull(di1);
		assertNotNull(di2);
		assertEquals(di1.size(), di2.size());
		for (int i = 0; i < di1.size(); i++) {
			checkLoDisplay(di1.get(i), di2.get(i));
		}
	}

	private static void checkLo(LoInfo li1, LoInfo li2) {
		assertNotNull(li1);
		assertNotNull(li2);

		assertTrue(EqualsBuilder.reflectionEquals(li1, li2, new String[]{"desc","attributes","metaInfo"}));
		checkRichText(li1.getDesc(), li2.getDesc());
	}

	private static void checkRichText(RichTextInfo desc, RichTextInfo desc2) {
		assertNotNull(desc);
		assertNotNull(desc2);

		assertTrue(EqualsBuilder.reflectionEquals(desc, desc2));
	}

	private static StatementTreeViewInfo createStatementTree() {
        // Statement Tree
        //                --------- STMT-1:OR ---------
    	//                |                           |
        //           STMT-2:AND                  STMT-3:AND
    	//           |        |                  |        |
        //      REQCOMP-1  REQCOMP-2        REQCOMP-3  REQCOMP-4

        List<StatementTreeViewInfo> subStatements = new ArrayList<StatementTreeViewInfo>(3);
        List<ReqComponentInfo> reqCompList1 = new ArrayList<ReqComponentInfo>(3);
        List<ReqComponentInfo> reqCompList2 = new ArrayList<ReqComponentInfo>(3);

        // req components
        ReqComponentInfo rc1 = new ReqComponentInfo();
        rc1.setDesc(toRichText("REQCOMP-1"));
        rc1.setType("kuali.reqComponent.type.course.courseset.completed.all");
        ReqComponentInfo rc2 = new ReqComponentInfo();
        rc2.setDesc(toRichText("REQCOMP-2"));
        rc2.setType("kuali.reqComponent.type.course.courseset.gpa.min");
        ReqComponentInfo rc3 = new ReqComponentInfo();
        rc3.setDesc(toRichText("REQCOMP-3"));
        rc3.setType("kuali.reqComponent.type.course.courseset.completed.nof");
        ReqComponentInfo rc4 = new ReqComponentInfo();
        rc4.setDesc(toRichText("REQCOMP-4"));
        rc4.setType("kuali.reqComponent.type.course.permission.instructor.required");

        // statement tree views
        StatementTreeViewInfo statementTree = new StatementTreeViewInfo();
        statementTree.setDesc(toRichText("STMT-1"));
        statementTree.setOperator(StatementOperatorTypeKey.OR);
        statementTree.setType("kuali.statement.type.program.entrance");

        StatementTreeViewInfo subTree1 = new StatementTreeViewInfo();
        subTree1.setDesc(toRichText("STMT-2"));
        subTree1.setOperator(StatementOperatorTypeKey.AND);
        subTree1.setType("kuali.statement.type.program.entrance");

        StatementTreeViewInfo subTree2 = new StatementTreeViewInfo();
        subTree2.setDesc(toRichText("STMT-3"));
        subTree2.setOperator(StatementOperatorTypeKey.AND);
        subTree2.setType("kuali.statement.type.program.entrance");

        // construct tree with statements and req components
        reqCompList1.add(rc1);
        reqCompList1.add(rc2);
        subTree1.setReqComponents(reqCompList1);
        reqCompList2.add(rc3);
        reqCompList2.add(rc4);
        subTree2.setReqComponents(reqCompList2);
        subStatements.add(subTree1);
        subStatements.add(subTree2);
        statementTree.setStatements(subStatements);

        return statementTree;
    }

	private static RichTextInfo toRichText(String text) {
		RichTextInfo richTextInfo = new RichTextInfo();
		if (text == null) {
			return null;
		}
		richTextInfo.setPlain(text);
		richTextInfo.setFormatted("<p>" + text + "</p>");
		return richTextInfo;
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

            for (String orgInfoId : major.getDivisionsFinancialControl()) {
                orgInfoId = orgInfoId + "-updated";
            }
            for (String orgInfoId : major.getUnitsDeployment()) {
                orgInfoId = orgInfoId + "-updated";
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

        assertEquals(3, updatedMD.getAttributes().size());
        assertNotNull(updatedMD.getAttributes().get("PIES"));
        assertEquals("APPLE", updatedMD.getAttributes().get("PIES"));

        assertEquals(3, updatedMD.getCampusLocations().size());
        assertEquals("NORTH", updatedMD.getCampusLocations().get(0));
        assertEquals("SOUTH", updatedMD.getCampusLocations().get(1));
        assertEquals("MAIN", updatedMD.getCampusLocations().get(2));

//        assertEquals(1, updatedMD.getProgramRequirements().size());

        assertEquals("Anthropology-updated", updatedMD.getLongTitle());
        assertEquals("450202-updated", updatedMD.getCip2000Code());
        assertEquals("TRANSCRIPT-TITLE-updated", updatedMD.getTranscriptTitle());
        assertEquals("DIPLOMA-TITLE-updated", updatedMD.getDiplomaTitle() );
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

    @Test
    public void testUpdateVariationsByMajorDiscipline(){
        MajorDisciplineInfo majorDisciplineInfo = null;

        try {
            majorDisciplineInfo = programService.getMajorDiscipline("d4ea77dd-b492-4554-b104-863e42c5f8b7");
            assertNotNull(majorDisciplineInfo);

            List<ProgramVariationInfo> pvInfos = majorDisciplineInfo.getVariations();
            assertNotNull(pvInfos);

            // update variation fields
            ProgramVariationInfo pvInfo = pvInfos.get(0);

            pvInfo.setLongTitle(pvInfo.getLongTitle() + "-updated");
            pvInfo.setCode(pvInfo.getCode() + "-updated");
            pvInfo.setShortTitle(pvInfo.getShortTitle() + "-updated");
            RichTextInfo testDesc = pvInfo.getDescr();
            testDesc.setPlain(testDesc.getPlain() + "-updated");
            pvInfo.setDescr(testDesc);
            pvInfo.setCip2000Code( pvInfo.getCip2000Code() + "-updated");
            pvInfo.setCip2010Code(pvInfo.getCip2010Code() + "-updated");
            pvInfo.setTranscriptTitle("transcriptTitle-updated");
            pvInfo.setDiplomaTitle(pvInfo.getDiplomaTitle() + "-updated");

            List<String> campusLocations = new ArrayList<String>();
            campusLocations.add(CourseAssemblerConstants.COURSE_CAMPUS_LOCATION_CD_NORTH);
            campusLocations.add(CourseAssemblerConstants.COURSE_CAMPUS_LOCATION_CD_SOUTH);
            pvInfo.setCampusLocations(campusLocations);

            List<String> testOrgs = new ArrayList<String>();
            testOrgs.add("testOrgId");
            if(pvInfo.getDivisionsContentOwner()!= null){
            	pvInfo.getDivisionsContentOwner().clear();
                pvInfo.getDivisionsContentOwner().add("testOrgId");
            }
            else
            	pvInfo.setDivisionsContentOwner(testOrgs);

            //Perform the update
            MajorDisciplineInfo updatedMD = programService.updateMajorDiscipline(majorDisciplineInfo);
            List<ProgramVariationInfo> updatedPvInfos = updatedMD.getVariations();
            assertNotNull(updatedPvInfos);
            ProgramVariationInfo updatedPV = updatedPvInfos.get(0);

            //Verify the update
            verifyUpdate(pvInfo, updatedPV);

            // Now explicitly get it
            List<ProgramVariationInfo> retrievedPVs = programService.getVariationsByMajorDisciplineId(majorDisciplineInfo.getId());
            assertNotNull(retrievedPVs);
            verifyUpdate(pvInfo, retrievedPVs.get(0)); // see comment in verifyUpdate
        } catch (Exception e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }

    private void verifyUpdate(ProgramVariationInfo source, ProgramVariationInfo target) {
    	assertNotNull(target);

        assertEquals(source.getDescr().getPlain(), target.getDescr().getPlain());
        assertEquals(source.getLongTitle(), target.getLongTitle());
        assertEquals(source.getShortTitle(), target.getShortTitle());

        assertEquals(source.getCip2000Code(), target.getCip2000Code());
        assertEquals(source.getCip2010Code(), target.getCip2010Code());
        assertEquals(source.getTranscriptTitle(), target.getTranscriptTitle());
        assertEquals(source.getDiplomaTitle(), target.getDiplomaTitle());

        assertNotNull(target.getCampusLocations());
        for(String loc : target.getCampusLocations()){
        	assertTrue(CourseAssemblerConstants.COURSE_CAMPUS_LOCATION_CD_NORTH.equals(loc) || CourseAssemblerConstants.COURSE_CAMPUS_LOCATION_CD_SOUTH.equals(loc));
        }

        assertNotNull(target.getDivisionsContentOwner());
        // TODO: this should actually be passing; get working again after  today's change of
        // AdminOrgInfo's to those orgs ID's in Program-related DTOs
        // assertEquals("testOrgId", target.getDivisionsContentOwner().get(0));
    }

    @Test
    public void testCreateVariationsByMajorDiscipline(){
        MajorDisciplineInfo majorDisciplineInfo = null;

        try {
            majorDisciplineInfo = programService.getMajorDiscipline("d4ea77dd-b492-4554-b104-863e42c5f8b7");
            assertNotNull(majorDisciplineInfo);

            List<ProgramVariationInfo> pvInfos = majorDisciplineInfo.getVariations();
            assertNotNull(pvInfos);

            ProgramVariationInfo pvInfoS = pvInfos.get(0);
            ProgramVariationInfo pvInfoT = new ProgramVariationInfo();

            BeanUtils.copyProperties(pvInfoS, pvInfoT, new String[] { "id" });

            pvInfoT.setLongTitle(pvInfoT.getLongTitle() + "-created");
            pvInfoT.setShortTitle(pvInfoT.getShortTitle() + "-created");
            RichTextInfo testDesc = pvInfoT.getDescr();
            testDesc.setPlain(testDesc.getPlain() + "-created");
            pvInfoT.setDescr(testDesc);
            pvInfoT.setCip2000Code( pvInfoT.getCip2000Code() + "-created");
            pvInfoT.setCip2010Code(pvInfoT.getCip2010Code() + "-created");
            pvInfoT.setTranscriptTitle(pvInfoT.getTranscriptTitle() + "-created");
            pvInfoT.setDiplomaTitle(pvInfoT.getDiplomaTitle() + "-created");

            //Perform the update: adding the new variation
            pvInfos.add(pvInfoT);
            MajorDisciplineInfo updatedMD = programService.updateMajorDiscipline(majorDisciplineInfo);
            List<ProgramVariationInfo> updatedPvInfos = updatedMD.getVariations();
            assertNotNull(updatedPvInfos);
            assertEquals(3, updatedPvInfos.size());
            ProgramVariationInfo createdPV = updatedPvInfos.get(2);

            //Verify the update
            verifyUpdate(pvInfoT, createdPV);

            // Now explicitly get it
            MajorDisciplineInfo retrievedMD = programService.getMajorDiscipline(majorDisciplineInfo.getId());
            assertEquals(3, retrievedMD.getVariations().size());

            List<ProgramVariationInfo> retrievedPVs = programService.getVariationsByMajorDisciplineId(majorDisciplineInfo.getId());
            assertNotNull(retrievedPVs);
            assertEquals(3, updatedPvInfos.size());
            verifyUpdate(pvInfoT, retrievedPVs.get(2));


        } catch (Exception e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }

    @Test
    public void testDeleteVariationsByMajorDiscipline(){
        MajorDisciplineInfo majorDisciplineInfo = null;

        try {
            majorDisciplineInfo = programService.getMajorDiscipline("d4ea77dd-b492-4554-b104-863e42c5f8b7");
            assertNotNull(majorDisciplineInfo);

            List<ProgramVariationInfo> pvInfos = majorDisciplineInfo.getVariations();
            assertNotNull(pvInfos);

            //Perform the update: adding the new variation
            pvInfos.remove(1);
            MajorDisciplineInfo updatedMD = programService.updateMajorDiscipline(majorDisciplineInfo);
            List<ProgramVariationInfo> updatedPvInfos = updatedMD.getVariations();
            assertNotNull(updatedPvInfos);
            assertEquals(2, updatedPvInfos.size());

            // Now explicitly get it
            MajorDisciplineInfo retrievedMD = programService.getMajorDiscipline(majorDisciplineInfo.getId());
            assertEquals(2, retrievedMD.getVariations().size());

            List<ProgramVariationInfo> retrievedPVs = programService.getVariationsByMajorDisciplineId(majorDisciplineInfo.getId());
            assertNotNull(retrievedPVs);
            assertEquals(2, updatedPvInfos.size());
            verifyUpdate(pvInfos.get(0), retrievedPVs.get(0));

        } catch (Exception e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }

    @Test(expected=DoesNotExistException.class)
    public void testDeleteProgramRequirement() throws Exception {
    	ProgramRequirementInfo progReq = createProgramRequirementTestData();
    	ProgramRequirementInfo createdProgReq = programService.createProgramRequirement(progReq);
    	try {
			programService.deleteProgramRequirement(createdProgReq.getId());
		} catch (DoesNotExistException e) {
			fail(e.getMessage());
		}
    	programService.getProgramRequirement(createdProgReq.getId(), null, null);
    }
}
