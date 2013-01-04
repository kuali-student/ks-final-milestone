package org.kuali.student.lum.program.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import junit.framework.Assert;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.student.common.conversion.util.R1R2ConverterUtil;
import org.kuali.student.common.test.util.ContextInfoTestUtility;
import org.kuali.student.r2.lum.course.service.assembler.CourseAssemblerConstants;
import org.kuali.student.r2.lum.program.service.assembler.MajorDisciplineDataGenerator;
import org.kuali.student.r2.lum.program.service.assembler.ProgramAssemblerConstants;
import org.kuali.student.r1.common.assembly.data.Metadata;
import org.kuali.student.r1.common.assembly.dictionary.MetadataServiceImpl;
import org.kuali.student.r1.core.statement.dto.ReqCompFieldTypeInfo;
import org.kuali.student.r1.core.statement.dto.ReqComponentTypeInfo;
import org.kuali.student.r1.core.statement.dto.StatementOperatorTypeKey;
import org.kuali.student.r1.core.statement.service.StatementService;
import org.kuali.student.r2.common.dto.AttributeInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.DtoConstants;
import org.kuali.student.r2.common.dto.RichTextInfo;
import org.kuali.student.r2.common.exceptions.AlreadyExistsException;
import org.kuali.student.r2.common.exceptions.CircularRelationshipException;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DependentObjectsExistException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.IllegalVersionSequencingException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.ReadOnlyException;
import org.kuali.student.r2.common.exceptions.UnsupportedActionException;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;
import org.kuali.student.r2.core.statement.dto.ReqCompFieldInfo;
import org.kuali.student.r2.core.statement.dto.ReqComponentInfo;
import org.kuali.student.r2.core.statement.dto.StatementOperator;
import org.kuali.student.r2.core.statement.dto.StatementTreeViewInfo;
import org.kuali.student.r2.lum.clu.dto.AdminOrgInfo;
import org.kuali.student.r2.lum.course.dto.LoDisplayInfo;
import org.kuali.student.r2.lum.lo.dto.LoCategoryInfo;
import org.kuali.student.r2.lum.lo.dto.LoInfo;
import org.kuali.student.r2.lum.program.dto.CoreProgramInfo;
import org.kuali.student.r2.lum.program.dto.CredentialProgramInfo;
import org.kuali.student.r2.lum.program.dto.MajorDisciplineInfo;
import org.kuali.student.r2.lum.program.dto.ProgramRequirementInfo;
import org.kuali.student.r2.lum.program.dto.ProgramVariationInfo;
import org.kuali.student.r2.lum.program.service.ProgramService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:program-test-context.xml"})
public class TestProgramServiceImpl {

    ContextInfo contextInfo = ContextInfoTestUtility.getEnglishContextInfo();

    @Autowired
    public ProgramService programService;

    @Autowired
    public StatementService statementService;

    private static final String OTHER_LO_CAT_ID = "550e8400-e29b-41d4-a716-446655440000";
    
    /**
     * A set of methods that have a dummy implementation in ProgramServiceImpl.  Method names should be removed from here once
     * they have a working implementation.
     */
    private final String[] DUMMY_SERVICE_METHODS = {"createHonorsProgram", "createMinorDiscipline",
            "deleteHonorsProgram", "deleteMinorDiscipline", "getHonorsProgram", "getMinorDiscipline",
            "getMinorsByCredentialProgramType", "updateHonorsProgram", "updateMinorDiscipline",
            "validateHonorsProgram", "validateMinorDiscipline", "getSearchCriteriaType", "getSearchCriteriaTypes",
            "getSearchResultType", "getSearchResultTypes", "getSearchType", "getSearchTypes",
            "getSearchTypesByCriteria", "getSearchTypesByResult", "search"};

    @Test
    public void testProgramServiceSetup() {
    	assertNotNull(programService);
    	assertNotNull(statementService);
    }

	@Test
    public void testGetMetaData() {
        MetadataServiceImpl metadataService = new MetadataServiceImpl(programService);
        metadataService.setUiLookupContext("classpath:lum-ui-test-lookup-context.xml");
        Metadata metadata = metadataService.getMetadata("org.kuali.student.r2.lum.program.dto.MajorDisciplineInfo");
        assertNotNull(metadata);

        Map<String, Metadata> properties = metadata.getProperties();
        assertTrue(properties.size() > 0);

        assertTrue(properties.containsKey("universityClassification"));
        metadata = properties.get("universityClassification");
        assertEquals("STRING", metadata.getDataType().name());
    }


    @Test
    public void testGetProgramRequirement() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        ProgramRequirementInfo progReqInfo = programService.getProgramRequirement("PROGREQ-1", contextInfo);
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
        assertEquals("Desc4", loInfo1.getDescr().getPlain());
        assertEquals("Edit Wiki Message Structure", loInfo1.getName());
        assertEquals("kuali.loRepository.key.singleUse", loInfo1.getLoRepositoryKey());
        assertEquals(DtoConstants.STATE_DRAFT, loInfo1.getStateKey());
        assertEquals("kuali.lo.type.singleUse", loInfo1.getTypeKey());
    }

    @Test
    @Ignore // FIXME
    public void testGetProgramRequirementNL() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        ProgramRequirementInfo progReqInfo = programService.getProgramRequirement("PROGREQ-1", contextInfo);
        assertNotNull(progReqInfo);

        checkTreeView(progReqInfo, true);
    }

	private void checkTreeView(final ProgramRequirementInfo progReqInfo, final boolean checkNaturalLanguage) {
		StatementTreeViewInfo rootTree =R1R2ConverterUtil.convert(progReqInfo.getStatement(), StatementTreeViewInfo.class);
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
        }

        // Check reqComps of sub-tree 2
        assertEquals("STMT-TV-3", subTree2.getId());
        assertEquals(2, subTree2.getReqComponents().size());
        assertEquals("REQCOMP-TV-3", subTree2.getReqComponents().get(0).getId());
        assertEquals("REQCOMP-TV-4", subTree2.getReqComponents().get(1).getId());
        if (checkNaturalLanguage) {
        	assertEquals("Student must have completed 1 of MATH 152, MATH 180", subTree2.getReqComponents().get(0).getNaturalLanguageTranslation());
        	assertEquals("Student needs a minimum GPA of 4.0 in MATH 152, MATH 180", subTree2.getReqComponents().get(1).getNaturalLanguageTranslation());
        }
	}

    @Test(expected = MissingParameterException.class)
    public void testGetProgramRequirement_nullId() throws Exception {
        programService.getProgramRequirement(null, contextInfo);
    }

    @Test(expected = DoesNotExistException.class)
    public void testGetProgramRequirement_badId() throws Exception {
        programService.getProgramRequirement("CLU-XXX ", contextInfo);
    }

    @Test
    public void testGetCoreProgram() throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException {
        CoreProgramInfo core = null;
            try {
                core = programService.getCoreProgram("d4ea77dd-b492-4554-b104-863e42c5f8b7", contextInfo);
                fail("Should have received DoesNotExistException");
            } catch (DoesNotExistException dnee) {
                String expectedExceptionMessage = "Specified CLU is not a CoreProgram";
                assertEquals("Expected DoesNotExistException has incorrect message:", expectedExceptionMessage, dnee.getMessage());
            }
            core = programService.getCoreProgram("00f5f8c5-fff1-4c8b-92fc-789b891e0849", contextInfo);

            assertNotNull(core);

            assertNotNull(core.getReferenceURL());
            assertEquals("http://www.google.ca", core.getReferenceURL());
            assertNotNull(core.getUniversityClassification());
            assertEquals(core.getUniversityClassification(), "UNIVERSITYCLASSIFICATIONCODE");
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
            assertNotNull(core.getDescr());
            assertEquals("Anthropology Major", core.getDescr().getPlain());

//            //TODO catalog descr
//            //TODO catalog pub targets

            assertNotNull(core.getLearningObjectives());
            assertTrue(core.getLearningObjectives().size() ==1);
            assertEquals("Core Program Learning objectives", core.getLearningObjectives().get(0).getLoInfo().getDescr().getPlain());

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
            assertEquals("GINGER GEM", core.getAttributeValue("COOKIES"));
            assertEquals("JAM TART", core.getAttributeValue("CAKES"));

            assertNotNull(core.getMeta());
            assertEquals("1", core.getMeta().getVersionInd());
            assertNotNull(core.getTypeKey());
            assertEquals(ProgramAssemblerConstants.CORE_PROGRAM, core.getTypeKey());
            assertNotNull(core.getStateKey());
            assertEquals(DtoConstants.STATE_ACTIVE, core.getStateKey());
            assertNotNull(core.getId());
            assertEquals("00f5f8c5-fff1-4c8b-92fc-789b891e0849", core.getId());
    }
    @Test
    public void testGetMajorDiscipline() throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException {
        MajorDisciplineInfo major = null;
//        	MajorDisciplineDataGenerator generator = new MajorDisciplineDataGenerator();
            // MajorDisciplineInfo majorDisciplineInfo = generator.getMajorDisciplineInfoTestData();
            try {
                major = programService.getMajorDiscipline("0d8c42bc-77ba-450e-ae0e-eecd76fae779", contextInfo);
                fail("Should have received DoesNotExistException");
            } catch (DoesNotExistException dnee) {
                String expectedExceptionMessage = "Specified CLU is not a Major Discipline";
                assertEquals("Expected DoesNotExistException has incorrect message:", expectedExceptionMessage, dnee.getMessage());
            }
            major = programService.getMajorDiscipline("d4ea77dd-b492-4554-b104-863e42c5f8b7", contextInfo);

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
            assertEquals("45.0202", major.getCip2000Code());
            assertNotNull(major.getCip2010Code());
            assertEquals("45.0201", major.getCip2010Code());
            assertNotNull(major.getHegisCode());
            assertEquals("220200", major.getHegisCode());
            assertNotNull(major.getUniversityClassification());
            assertEquals("UNIVERSITYCLASSIFICATIONCODE", major.getUniversityClassification());
            assertNotNull(major.getSelectiveEnrollmentCode());
            assertEquals("SELECTIVEENROLLMENTCODE", major.getSelectiveEnrollmentCode());

            assertNotNull(major.getResultOptions());
            assertTrue(major.getResultOptions().size() == 2);
            assertEquals("kuali.resultComponent.degree.ba", major.getResultOptions().get(0));
            assertEquals("kuali.resultComponent.degree.bsc", major.getResultOptions().get(1));

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

            assertNotNull(major.getCatalogDescr());
            assertEquals("This is the catalog description", major.getCatalogDescr().getPlain());
            assertNotNull(major.getCatalogPublicationTargets());
            assertEquals(1, major.getCatalogPublicationTargets().size());
            assertEquals("kuali.lu.publication.UndergradCatalog", major.getCatalogPublicationTargets().get(0));
            assertNotNull(major.getLearningObjectives());
            assertTrue(major.getLearningObjectives().size() ==1);
            assertEquals("Annihilate Wiki", major.getLearningObjectives().get(0).getLoInfo().getDescr().getPlain());
            assertNotNull(major.getCampusLocations());
            assertTrue(major.getCampusLocations().size() == 2);
            assertEquals("NO", major.getCampusLocations().get(0));
            assertEquals("SO", major.getCampusLocations().get(1));

            assertNotNull(major.getOrgCoreProgram());
            assertEquals("kuali.lu.type.CoreProgram", major.getOrgCoreProgram().getTypeKey());
            assertEquals("00f5f8c5-fff1-4c8b-92fc-789b891e0849", major.getOrgCoreProgram().getId());
            assertNotNull(major.getProgramRequirements());
            assertTrue(major.getProgramRequirements().size() == 1);
            assertEquals("REQ-200", major.getProgramRequirements().get(0));

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
            assertEquals("GINGER GEM", major.getAttributeValue("COOKIES"));
            assertEquals("JAM TART", major.getAttributeValue("CAKES"));

            assertNotNull(major.getMeta());
            assertEquals("1", major.getMeta().getVersionInd());
           //TODO createTime
//            Calendar createTime = GregorianCalendar.getInstance();
//            createTime.set(2009, 4, 7, 12, 5, 36);
//            testDate = new Date(createTime.getTimeInMillis());
//            assertTrue(major.getEffectiveDate().compareTo(testDate) == 0);

            assertNotNull(major.getTypeKey());
            assertEquals(ProgramAssemblerConstants.MAJOR_DISCIPLINE, major.getTypeKey());
            assertNotNull(major.getStateKey());
            assertEquals(DtoConstants.STATE_ACTIVE, major.getStateKey());
            assertNotNull(major.getId());
            assertEquals("d4ea77dd-b492-4554-b104-863e42c5f8b7", major.getId());


    }

    @Test
    public void testGetVariationsByMajorDisciplineId() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException{
        MajorDisciplineInfo majorDisciplineInfo = null;

            majorDisciplineInfo = programService.getMajorDiscipline("d4ea77dd-b492-4554-b104-863e42c5f8b7", contextInfo);
            assertNotNull(majorDisciplineInfo);

            List<ProgramVariationInfo> pvInfos = programService.getVariationsByMajorDisciplineId("d4ea77dd-b492-4554-b104-863e42c5f8b7", contextInfo);
            assertNotNull(pvInfos);
            assertEquals(pvInfos.size(), majorDisciplineInfo.getVariations().size());

            ProgramVariationInfo pvInfo = pvInfos.get(0);
            assertEquals("ZOOA", pvInfo.getCode());
            assertEquals("Zooarchaeology", pvInfo.getDescr().getPlain());
            assertEquals("Zooarchaeology", pvInfo.getLongTitle());
            assertEquals("ZooArch", pvInfo.getShortTitle());
            assertEquals("VAR-200", pvInfo.getId());
            assertEquals("Active", pvInfo.getStateKey());

    }

    @Test
    public void testGetBaccCredentialProgram() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException{

    	String credentialProgramId = "d02dbbd3-20e2-410d-ab52-1bd6d362748b";
    	CredentialProgramInfo credentialProgramInfo = null;
    		credentialProgramInfo = programService.getCredentialProgram(credentialProgramId, contextInfo);
            assertNotNull(credentialProgramInfo);
            assertEquals("BS", credentialProgramInfo.getCode());
            assertEquals("B.S.", credentialProgramInfo.getShortTitle());
            assertEquals("Bachelor of Science", credentialProgramInfo.getLongTitle());
            assertEquals("Bachelor of Science", credentialProgramInfo.getDescr().getPlain());
            assertEquals(DtoConstants.STATE_ACTIVE, credentialProgramInfo.getStateKey());
    		assertEquals(ProgramAssemblerConstants.BACCALAUREATE_PROGRAM, credentialProgramInfo.getCredentialProgramType());
            assertEquals("52", credentialProgramInfo.getInstitution().getOrgId());
            assertEquals(ProgramAssemblerConstants.UNDERGRAD_PROGRAM_LEVEL, credentialProgramInfo.getProgramLevel());
            assertNotNull(credentialProgramInfo.getCoreProgramIds());
            assertEquals(1, credentialProgramInfo.getCoreProgramIds().size());
            assertEquals("00f5f8c5-fff1-4c8b-92fc-789b891e0849", credentialProgramInfo.getCoreProgramIds().get(0));
    }

    @Test
    public void testCreateMajorDiscipline() throws IllegalArgumentException, SecurityException, IntrospectionException, InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchFieldException, AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
		MajorDisciplineDataGenerator mdGenerator = new MajorDisciplineDataGenerator();
        MajorDisciplineInfo major;
            assertNotNull(major = mdGenerator.getMajorDisciplineInfoTestData());

            MajorDisciplineInfo createdMD = programService.createMajorDiscipline(null, major, contextInfo);

            assertNotNull(createdMD);

            assertNotNull(createdMD.getId());

            assertNotNull(createdMD.getStateKey());
            assertEquals(DtoConstants.STATE_DRAFT, createdMD.getStateKey());

            assertNotNull(createdMD.getTypeKey());
            assertEquals(ProgramAssemblerConstants.MAJOR_DISCIPLINE, createdMD.getTypeKey());

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
            assertEquals("kuali.lu.type.Variation", createdMD.getVariations().get(0).getTypeKey());
            assertEquals("kuali.lu.type.Variation", createdMD.getVariations().get(1).getTypeKey());

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

            assertNotNull(createdMD.getCatalogDescr());
            assertEquals("plain-test", createdMD.getCatalogDescr().getPlain());
            assertEquals("formatted-test", createdMD.getCatalogDescr().getFormatted());

            assertNotNull(createdMD.getCatalogPublicationTargets());
            assertTrue(createdMD.getCatalogPublicationTargets().size() == 2);
            assertEquals("kuali.lu.publication.Catalog", createdMD.getCatalogPublicationTargets().get(0));

            assertNotNull(createdMD.getLearningObjectives());
            assertTrue(createdMD.getLearningObjectives().size() == 2);
            assertEquals("plain-test", createdMD.getLearningObjectives().get(0).getLoInfo().getDescr().getPlain());

            assertNotNull(createdMD.getCampusLocations());
            assertTrue(createdMD.getCampusLocations().size() == 2);
            assertEquals("SO", createdMD.getCampusLocations().get(0));
            assertEquals("NO", createdMD.getCampusLocations().get(1));

            assertNotNull(createdMD.getOrgCoreProgram());
            assertEquals(ProgramAssemblerConstants.CORE_PROGRAM, createdMD.getOrgCoreProgram().getTypeKey());
// TODO           assertEquals("00f5f8c5-fff1-4c8b-92fc-789b891e0849", createdMD.getOrgCoreProgram().getId());

            assertNotNull(createdMD.getProgramRequirements());
            assertTrue(createdMD.getProgramRequirements().size() == 2);
            assertEquals("REQ-200", createdMD.getProgramRequirements().get(0));

            assertNotNull(createdMD.getAccreditingAgencies());
            assertTrue(createdMD.getAccreditingAgencies().size() == 2);
            assertEquals("orgId-test", createdMD.getAccreditingAgencies().get(0).getOrgId());

            assertNotNull(createdMD.getDivisionsContentOwner());
            assertTrue(createdMD.getDivisionsContentOwner().size() == 2);
            assertEquals("divisionsContentOwner-test", createdMD.getDivisionsContentOwner().get(0));

            assertNotNull(createdMD.getDivisionsStudentOversight());
            assertTrue(createdMD.getDivisionsStudentOversight().size() == 2);
            assertEquals("divisionsStudentOversight-test", createdMD.getDivisionsStudentOversight().get(0));

            assertNotNull(createdMD.getDivisionsDeployment());
            assertTrue(createdMD.getDivisionsDeployment().size() == 2);
            assertEquals("divisionsDeployment-test", createdMD.getDivisionsDeployment().get(0));

            assertNotNull(createdMD.getDivisionsFinancialResources());
            assertTrue(createdMD.getDivisionsFinancialResources().size() == 2);
            assertEquals("divisionsFinancialResources-test", createdMD.getDivisionsFinancialResources().get(0));

            assertNotNull(createdMD.getDivisionsFinancialControl());
            assertTrue(createdMD.getDivisionsFinancialControl().size() == 2);
            assertEquals("divisionsFinancialControl-test", createdMD.getDivisionsFinancialControl().get(0));

            assertNotNull(createdMD.getUnitsContentOwner());
            assertTrue(createdMD.getUnitsContentOwner().size() == 2);
            assertEquals("unitsContentOwner-test", createdMD.getUnitsContentOwner().get(0));

            assertNotNull(createdMD.getUnitsStudentOversight());
            assertTrue(createdMD.getUnitsStudentOversight().size() == 2);
            assertEquals("unitsStudentOversight-test", createdMD.getUnitsStudentOversight().get(0));

            assertNotNull(createdMD.getUnitsDeployment());
            assertTrue(createdMD.getUnitsDeployment().size() == 2);
            assertEquals("unitsDeployment-test", createdMD.getUnitsDeployment().get(0));

            assertNotNull(createdMD.getUnitsFinancialResources());
            assertTrue(createdMD.getUnitsFinancialResources().size() == 2);
            assertEquals("unitsFinancialResources-test", createdMD.getUnitsFinancialResources().get(0));

            assertNotNull(createdMD.getUnitsFinancialControl());
            assertTrue(createdMD.getUnitsFinancialControl().size() == 2);
            assertEquals("unitsFinancialControl-test", createdMD.getUnitsFinancialControl().get(0));

            assertNotNull(createdMD.getAttributes());
            assertTrue(createdMD.getAttributes().size() ==2);
            assertEquals("value-8", createdMD.getAttributeValue("key-7"));
            assertEquals("value-9", createdMD.getAttributeValue("key-8"));

            assertNotNull(createdMD.getMeta());
            assertEquals("0", createdMD.getMeta().getVersionInd());
           //TODO createTime
//            Calendar createTime = GregorianCalendar.getInstance();
//            createTime.set(2009, 4, 7, 12, 5, 36);
//            testDate = new Date(createTime.getTimeInMillis());
//            assertTrue(createdMD.getEffectiveDate().compareTo(testDate) == 0);
	}

    @Test
    public void testMajorDisciplineVersioning() throws IllegalArgumentException, SecurityException, IntrospectionException, InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchFieldException, AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException, DoesNotExistException, CircularRelationshipException, DependentObjectsExistException, UnsupportedActionException, IllegalVersionSequencingException, ReadOnlyException {
		MajorDisciplineDataGenerator mdGenerator = new MajorDisciplineDataGenerator();
        MajorDisciplineInfo mdInfo = mdGenerator.getMajorDisciplineInfoTestData();
        mdInfo.getProgramRequirements().clear();
        for(ProgramVariationInfo variation :mdInfo.getVariations()){
        	variation.getProgramRequirements().clear();
        }
        MajorDisciplineInfo createdMajor = programService.createMajorDiscipline(null, mdInfo, contextInfo);

        MajorDisciplineInfo newMajorDiscipline = programService.createNewMajorDisciplineVersion(createdMajor.getVersion().getVersionIndId(), "test make a new version", contextInfo);
        
        // Make the created the current version
        programService.setCurrentMajorDisciplineVersion(newMajorDiscipline.getId(), null, contextInfo);

		MajorDisciplineInfo	newMajor = null;
        try {
            newMajor = programService.createNewMajorDisciplineVersion(createdMajor.getVersion().getVersionIndId(), "test make a new version", contextInfo);
            assertTrue(true);
        } catch (Exception e) {
            assertTrue(false);
        }

        assertNotNull(newMajor);

    }

    @Test
    public void testCreateMajorDisciplineDeleteRule() throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, IllegalArgumentException, SecurityException, IntrospectionException, InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchFieldException {
		MajorDisciplineDataGenerator mdGenerator = new MajorDisciplineDataGenerator();
        MajorDisciplineInfo major;
            assertNotNull(major = mdGenerator.getMajorDisciplineInfoTestData());

            MajorDisciplineInfo createdMD = programService.createMajorDiscipline(null, major, contextInfo);

            ProgramRequirementInfo progReq = createProgramRequirementTestData();
        	ProgramRequirementInfo createdProgReq = programService.createProgramRequirement(null, progReq, contextInfo);
    }

    @Test(expected = MissingParameterException.class)
    public void testCreateProgramRequirement_null() throws Exception {
    	programService.createProgramRequirement(null, null, contextInfo);
    }

    @Test
    public void testCreateProgramRequirement() throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException {
    	ProgramRequirementInfo progReq = createProgramRequirementTestData();
    	ProgramRequirementInfo createdProgReq = programService.createProgramRequirement(null, progReq, contextInfo);
    	checkProgramRequirement(progReq, createdProgReq);

    	ProgramRequirementInfo progReq2 = programService.getProgramRequirement(createdProgReq.getId(), contextInfo);
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
		loInfo.setDescr(toRichText("Program Requirement LO Info"));
		loInfo.setLoRepositoryKey("lo rep key");
		loDisplayInfo.setLoInfo(loInfo);
        los.add(loDisplayInfo);
    	progReq.setLearningObjectives(los);

      	StatementTreeViewInfo statement = createStatementTree();
    	progReq.setStatement(R1R2ConverterUtil.convert(statement, org.kuali.student.r1.core.statement.dto.StatementTreeViewInfo.class) );
    	progReq.setTypeKey(ProgramAssemblerConstants.PROGRAM_REQUIREMENT);
		return progReq;
	}

	private static void checkProgramRequirement(
			ProgramRequirementInfo orig, ProgramRequirementInfo created) {
		assertNotNull(orig);
		assertNotNull(created);
		assertTrue(EqualsBuilder.reflectionEquals(orig, created, new String[]{"id", "descr", "learningObjectives","statement","attributes","meta"}));
    	checkLoDisplays(orig.getLearningObjectives(), created.getLearningObjectives());
    	if (orig.getId() == null && created.getId() == null) {
    		fail("both ProgramRequirements ids are null");
    	} else if (orig.getId() != null) {
			assertEquals(orig.getId(), created.getId());
		}

    	checkRichText(orig.getDescr(), created.getDescr());
        checkStatementTreeView(R1R2ConverterUtil.convert(orig.getStatement(), StatementTreeViewInfo.class), R1R2ConverterUtil.convert(created.getStatement(), StatementTreeViewInfo.class));
	}

	private static void checkStatementTreeView(StatementTreeViewInfo statement,
			StatementTreeViewInfo statement2) {
		assertNotNull(statement);
		assertNotNull(statement2);
		assertTrue(EqualsBuilder.reflectionEquals(statement, statement2, new String[]{"id", "descr", "attributes", "meta", "statements", "reqComponents"}));
		if (statement.getId() == null && statement2.getId() == null) {
			fail("Both StatementTreeView ids are null");
		} else if (statement.getId() != null) {
			assertEquals(statement.getId(), statement2.getId());
		}
		checkRichText(statement.getDescr(), statement2.getDescr());
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
		assertTrue(EqualsBuilder.reflectionEquals(reqComponent, reqComponent2, new String[]{"id", "descr", "reqCompFields", "requiredComponentType", "naturalLanguageTranslation", "meta"}));
		if (reqComponent.getId() == null && reqComponent2.getId() == null) {
			fail("Both ReqComponent ids are null");
		} else if (reqComponent.getId() != null) {
			assertEquals(reqComponent.getId(), reqComponent2.getId());
		}
		checkRichText(reqComponent.getDescr(), reqComponent2.getDescr());
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
		assertTrue(EqualsBuilder.reflectionEquals(loCategoryInfo, loCategoryInfo2, new String[]{"descr","attributes","meta"}));
		checkRichText(loCategoryInfo.getDescr(), loCategoryInfo2.getDescr());
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

		assertTrue(EqualsBuilder.reflectionEquals(li1, li2, new String[]{"descr","attributes","meta"}));
		checkRichText(li1.getDescr(), li2.getDescr());
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
        rc1.setDescr(toRichText("REQCOMP-1"));
        rc1.setTypeKey("kuali.reqComponent.type.course.courseset.completed.all");
        ReqComponentInfo rc2 = new ReqComponentInfo();
        rc2.setDescr(toRichText("REQCOMP-2"));
        rc2.setTypeKey("kuali.reqComponent.type.course.courseset.gpa.min");
        ReqComponentInfo rc3 = new ReqComponentInfo();
        rc3.setDescr(toRichText("REQCOMP-3"));
        rc3.setTypeKey("kuali.reqComponent.type.course.courseset.completed.nof");
        ReqComponentInfo rc4 = new ReqComponentInfo();
        rc4.setDescr(toRichText("REQCOMP-4"));
        rc4.setTypeKey("kuali.reqComponent.type.course.permission.instructor.required");

        // statement tree views
        StatementTreeViewInfo statementTree = new StatementTreeViewInfo();
        statementTree.setDescr(toRichText("STMT-1"));
        statementTree.setOperator(StatementOperator.OR);
        statementTree.setTypeKey("kuali.statement.type.program.entrance");

        StatementTreeViewInfo subTree1 = new StatementTreeViewInfo();
        subTree1.setDescr(toRichText("STMT-2"));
        subTree1.setOperator(StatementOperator.AND);
        subTree1.setTypeKey("kuali.statement.type.program.entrance");

        StatementTreeViewInfo subTree2 = new StatementTreeViewInfo();
        subTree2.setDescr(toRichText("STMT-3"));
        subTree2.setOperator(StatementOperator.AND);
        subTree2.setTypeKey("kuali.statement.type.program.entrance");

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

	@Test(expected=DoesNotExistException.class)
	public void testUpdateProgramRequirement() throws Exception {
		ProgramRequirementInfo progReq = programService.createProgramRequirement(null, createProgramRequirementTestData(), contextInfo);
        StatementTreeViewInfo treeView = R1R2ConverterUtil.convert(progReq.getStatement(), StatementTreeViewInfo.class);

        List<ReqComponentInfo> reqCompList1 = new ArrayList<ReqComponentInfo>(3);
        ReqComponentInfo rc1 = new ReqComponentInfo();
        rc1.setDescr(toRichText("REQCOMP-1"));
        rc1.setTypeKey("kuali.reqComponent.type.course.courseset.completed.all");
        ReqComponentInfo rc2 = new ReqComponentInfo();
        rc2.setDescr(toRichText("REQCOMP-2"));
        rc2.setTypeKey("kuali.reqComponent.type.course.courseset.gpa.min");
        StatementTreeViewInfo subTree1 = new StatementTreeViewInfo();
        subTree1.setDescr(toRichText("STMT-5"));
        subTree1.setOperator(StatementOperator.AND);
        subTree1.setTypeKey("kuali.statement.type.program.entrance");
        reqCompList1.add(rc1);
        reqCompList1.add(rc2);
        subTree1.setReqComponents(reqCompList1);

        StatementTreeViewInfo oldSubTree1 = treeView.getStatements().get(0);
        //treeView.getStatements().set(0, subTree1); Can't set it like this anymore, because Dozer creates a new object. The old reference is gone.
        progReq.getStatement().getStatements().set(0, R1R2ConverterUtil.convert(subTree1, org.kuali.student.r1.core.statement.dto.StatementTreeViewInfo.class));
        ProgramRequirementInfo updated = programService.updateProgramRequirement(progReq.getId(), progReq.getTypeKey(), progReq, contextInfo);
        checkProgramRequirement(progReq, updated);
        statementService.getStatement(oldSubTree1.getId());
	}

    @Test
    @Ignore 
    public void testDeleteMajorDiscipline() throws IllegalArgumentException, SecurityException, IntrospectionException, InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchFieldException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, AlreadyExistsException, DataValidationErrorException {
        	MajorDisciplineDataGenerator generator = new MajorDisciplineDataGenerator();
        	MajorDisciplineInfo majorDisciplineInfo = generator.getMajorDisciplineInfoTestData();
            assertNotNull(majorDisciplineInfo);
            fixLoCategoryIds(majorDisciplineInfo.getLearningObjectives());
            MajorDisciplineInfo createdMD = programService.createMajorDiscipline(null,majorDisciplineInfo, contextInfo);
            assertNotNull(createdMD);
            assertEquals(DtoConstants.STATE_DRAFT, createdMD.getStateKey());
            assertEquals(ProgramAssemblerConstants.MAJOR_DISCIPLINE, createdMD.getTypeKey());
            assertEquals("00f5f8c5-fff1-4c8b-92fc-789b891e0849", createdMD.getCredentialProgramId());

            String majorDisciplineId = createdMD.getId();
            MajorDisciplineInfo retrievedMD = programService.getMajorDiscipline(majorDisciplineId, contextInfo);
            assertNotNull(retrievedMD);

            programService.deleteMajorDiscipline(majorDisciplineId, contextInfo);
            try {
            	retrievedMD = programService.getMajorDiscipline(majorDisciplineId, contextInfo);
                fail("Retrieval of deleted MajorDiscipline should have thrown exception");
            } catch (DoesNotExistException e) {}
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
    public void testUpdateMajorDiscipline() throws IllegalArgumentException, SecurityException, IntrospectionException, InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchFieldException, DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, VersionMismatchException, OperationFailedException, PermissionDeniedException {
        	MajorDisciplineDataGenerator generator = new MajorDisciplineDataGenerator();
        	MajorDisciplineInfo majorDisciplineInfo = generator.getMajorDisciplineInfoTestData();
            assertNotNull(majorDisciplineInfo);
//            MajorDisciplineInfo createdMD = programService.createMajorDiscipline(majorDisciplineInfo);
            MajorDisciplineInfo major = programService.getMajorDiscipline("d4ea77dd-b492-4554-b104-863e42c5f8b7", contextInfo);
            assertNotNull(major);

            // minimal sanity check
            assertNotNull(major.getCode());
            assertEquals("ANTH", major.getCode());
            assertNotNull(major.getTypeKey());
            assertEquals(ProgramAssemblerConstants.MAJOR_DISCIPLINE, major.getTypeKey());
            assertNotNull(major.getStateKey());
            assertEquals(DtoConstants.STATE_ACTIVE, major.getStateKey());
            assertNotNull(major.getId());
            assertEquals("d4ea77dd-b492-4554-b104-863e42c5f8b7", major.getId());
            assertNotNull(major.getShortTitle());
            assertEquals("Anthro", major.getShortTitle());
            assertNotNull(major.getLongTitle());
            assertEquals("Anthropology", major.getLongTitle());

            // update some fields
            major.getCampusLocations().add("MAIN");
            major.setLongTitle(major.getLongTitle() + "-updated");
            major.getAttributes().add(new AttributeInfo("PIES", "APPLE"));

            major.setCip2000Code(major.getCip2000Code() + "-updated");
            major.setDiplomaTitle(major.getDiplomaTitle() + "-updated");
            major.setTranscriptTitle(major.getTranscriptTitle() + "-updated");
            //major.setEndProgramEntryTerm("kuali.atp.FA2008-2009");
            //major.setStartTerm("kuali.atp.FA2008-2009");

            major.getCatalogDescr().setPlain(major.getCatalogDescr().getPlain() + "-updated");
            major.getCatalogPublicationTargets().add("kuali.lu.publication.GradCatalog");

            for (String orgInfoId : major.getDivisionsFinancialControl()) {
                orgInfoId = orgInfoId + "-updated";
            }
            for (String orgInfoId : major.getUnitsDeployment()) {
                orgInfoId = orgInfoId + "-updated";
            }

            List<String> reqIds = new ArrayList<String>();
            reqIds.add("REQ-200");
            reqIds.add("REQ-300");
            major.setProgramRequirements(reqIds);

           //Perform the update
            MajorDisciplineInfo updatedMD = programService.updateMajorDiscipline(major.getId(), major, contextInfo);

            //Verify the update
            verifyUpdate(updatedMD);
            assertEquals(2, updatedMD.getProgramRequirements().size());

            // Now explicitly get it
            MajorDisciplineInfo retrievedMD = programService.getMajorDiscipline(major.getId(), contextInfo);
            verifyUpdate(retrievedMD);
            assertEquals(2, retrievedMD.getProgramRequirements().size());
            //TODO: add version update

    }

    @Test
    public void testUpdateMajorDisciplineRemoveRule() throws IllegalArgumentException, SecurityException, IntrospectionException, InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchFieldException, AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException, VersionMismatchException {
            MajorDisciplineInfo major = programService.getMajorDiscipline("d4ea77dd-b492-4554-b104-863e42c5f8b7", contextInfo);

            List<String> reqIds = new ArrayList<String>(1);
            ProgramRequirementInfo req1 = programService.createProgramRequirement(null, createProgramRequirementTestData(), contextInfo);
            reqIds.add(req1.getId());
            major.setProgramRequirements(reqIds);

           //Perform the update
            MajorDisciplineInfo updatedMD = programService.updateMajorDiscipline(major.getId(), major, contextInfo); // FIXME Updated version info isn't returned
            MajorDisciplineInfo retrievedMD = programService.getMajorDiscipline(major.getId(), contextInfo);

            // Test that we can remove the program requirements
            programService.deleteProgramRequirement(req1.getId(), contextInfo);
            retrievedMD.getProgramRequirements().clear();
            MajorDisciplineInfo updatedMD2 = programService.updateMajorDiscipline(retrievedMD.getId(), retrievedMD, contextInfo);
            assertEquals(0, updatedMD2.getProgramRequirements().size());
            retrievedMD = programService.getMajorDiscipline(major.getId(), contextInfo);
            assertEquals(0, retrievedMD.getProgramRequirements() == null ? 0 : retrievedMD.getProgramRequirements().size());
    }


    private void verifyUpdate(MajorDisciplineInfo updatedMD) {
    	assertNotNull(updatedMD);

        assertEquals(3, updatedMD.getAttributes().size());
        for (AttributeInfo attribute : updatedMD.getAttributes()){
            if ("PIES".equals(attribute.getKey())) {
                assertNotNull(attribute.getValue());
                assertEquals("APPLE", attribute.getValue());
                break;
            }
        }
        
        assertEquals(3, updatedMD.getCampusLocations().size());
        assertEquals("NO", updatedMD.getCampusLocations().get(0));
        assertEquals("SO", updatedMD.getCampusLocations().get(1));
        assertEquals("MAIN", updatedMD.getCampusLocations().get(2));

//        assertEquals(1, updatedMD.getProgramRequirements().size());

        assertNotNull(updatedMD.getCatalogDescr());
        assertEquals("This is the catalog description-updated", updatedMD.getCatalogDescr().getPlain());

        assertNotNull(updatedMD.getCatalogPublicationTargets());
        assertEquals(2, updatedMD.getCatalogPublicationTargets().size());

        assertEquals("Anthropology-updated", updatedMD.getLongTitle());
        assertEquals("45.0202-updated", updatedMD.getCip2000Code());
        assertEquals("TRANSCRIPT-TITLE-updated", updatedMD.getTranscriptTitle());
        assertEquals("DIPLOMA-TITLE-updated", updatedMD.getDiplomaTitle() );
    }

    @Test
    public void testCreateBaccCredentialProgram() throws IllegalArgumentException, SecurityException, IntrospectionException, InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchFieldException, AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
    	CredentialProgramDataGenerator generator = new CredentialProgramDataGenerator(ProgramAssemblerConstants.BACCALAUREATE_PROGRAM);
    	CredentialProgramInfo credentialProgramInfo = null;
            assertNotNull(credentialProgramInfo = generator.getCPTestData());
            List<String> coreProgramIds = new ArrayList<String>();
            coreProgramIds.add("00f5f8c5-fff1-4c8b-92fc-789b891e0849");
            credentialProgramInfo.setCoreProgramIds(coreProgramIds);
            CredentialProgramInfo createdCP = programService.createCredentialProgram(null, credentialProgramInfo, contextInfo);
            assertNotNull(createdCP);
            assertEquals(DtoConstants.STATE_DRAFT, createdCP.getStateKey());
            assertEquals(ProgramAssemblerConstants.BACCALAUREATE_PROGRAM, createdCP.getCredentialProgramType());
	}

    @Test
    public void testDeleteBaccCredentialProgram() throws DoesNotExistException, InvalidParameterException, MissingParameterException, PermissionDeniedException, OperationFailedException {
        	String credentialProgramId = "d02dbbd3-20e2-410d-ab52-1bd6d362748b";
            CredentialProgramInfo retrievedCP = programService.getCredentialProgram(credentialProgramId, contextInfo);
            assertNotNull(retrievedCP);

            try{
	            programService.deleteCredentialProgram(credentialProgramId, contextInfo);
	            try {
	            	retrievedCP = programService.getCredentialProgram(credentialProgramId, contextInfo);
	                fail("Retrieval of deleted CredentialProgram should have thrown exception");
	            } catch (DoesNotExistException e) {}
            }catch (OperationFailedException e) {}
    }

    @Test
    public void testUpdateBaccCredentialProgram() throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, VersionMismatchException, OperationFailedException, PermissionDeniedException {
        	String credentialProgramId = "d02dbbd3-20e2-410d-ab52-1bd6d362748b";
            CredentialProgramInfo credentialProgramInfo = programService.getCredentialProgram(credentialProgramId, contextInfo);
            assertNotNull(credentialProgramInfo);

            // minimal sanity check
            assertEquals("BS", credentialProgramInfo.getCode());
            assertEquals("B.S.", credentialProgramInfo.getShortTitle());
            assertEquals("Bachelor of Science", credentialProgramInfo.getLongTitle());
            assertEquals("Bachelor of Science", credentialProgramInfo.getDescr().getPlain());
            assertEquals(DtoConstants.STATE_ACTIVE, credentialProgramInfo.getStateKey());
            assertEquals("52", credentialProgramInfo.getInstitution().getOrgId());
            assertEquals(ProgramAssemblerConstants.UNDERGRAD_PROGRAM_LEVEL, credentialProgramInfo.getProgramLevel());

            // update some fields
            //credentialProgramInfo.setCode(credentialProgramInfo.getCode() + "-updated");
            //credentialProgramInfo.setShortTitle(credentialProgramInfo.getShortTitle() + "-updated");
           // credentialProgramInfo.setLongTitle(credentialProgramInfo.getLongTitle() + "-updated");
            credentialProgramInfo.setProgramLevel(ProgramAssemblerConstants.GRADUATE_PROGRAM_LEVEL);
            AdminOrgInfo institution = new AdminOrgInfo();
            institution.setOrgId("51");
            credentialProgramInfo.setInstitution(institution);

           //Perform the update
            CredentialProgramInfo updatedCP = programService.updateCredentialProgram(credentialProgramInfo.getId(), credentialProgramInfo, contextInfo);

            //Verify the update
            verifyUpdate(updatedCP);

            // Now explicitly get it
            CredentialProgramInfo retrievedCP = programService.getCredentialProgram(credentialProgramInfo.getId(),  contextInfo);
            verifyUpdate(retrievedCP);

            //TODO: add version update

    }

    private void verifyUpdate(CredentialProgramInfo updatedCP) {
    	assertNotNull(updatedCP);

        //assertEquals("BS-updated", updatedCP.getCode());
       // assertEquals("B.S.-updated", updatedCP.getShortTitle());
        //assertEquals("Bachelor of Science-updated", updatedCP.getLongTitle());
        assertEquals(ProgramAssemblerConstants.GRADUATE_PROGRAM_LEVEL, updatedCP.getProgramLevel());
        assertEquals("51", updatedCP.getInstitution().getOrgId());
    }

    @Test
    public void testCreateCoreProgram() throws IllegalArgumentException, SecurityException, IntrospectionException, InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchFieldException, AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException {
    	CoreProgramDataGenerator generator = new CoreProgramDataGenerator();
    	CoreProgramInfo coreProgramInfo = null;
            assertNotNull(coreProgramInfo = generator.getCoreProgramTestData());
            CoreProgramInfo createdCP = programService.createCoreProgram(null, coreProgramInfo ,contextInfo);
            assertNotNull(createdCP);
            assertEquals(DtoConstants.STATE_DRAFT, createdCP.getStateKey());
            assertEquals(ProgramAssemblerConstants.CORE_PROGRAM, createdCP.getTypeKey());
	}

    @Test
    public void testUpdateVariationsByMajorDiscipline() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DataValidationErrorException, VersionMismatchException{
        MajorDisciplineInfo majorDisciplineInfo = null;

        majorDisciplineInfo = programService.getMajorDiscipline("d4ea77dd-b492-4554-b104-863e42c5f8b7", contextInfo);
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
        pvInfo.setCip2000Code(pvInfo.getCip2000Code() + "-updated");
        pvInfo.setCip2010Code(pvInfo.getCip2010Code() + "-updated");
        pvInfo.setTranscriptTitle("transcriptTitle-updated");
        pvInfo.setDiplomaTitle(pvInfo.getDiplomaTitle() + "-updated");

        List<String> campusLocations = new ArrayList<String>();
        campusLocations.add(CourseAssemblerConstants.COURSE_CAMPUS_LOCATION_CD_NORTH);
        campusLocations.add(CourseAssemblerConstants.COURSE_CAMPUS_LOCATION_CD_SOUTH);
        pvInfo.setCampusLocations(campusLocations);

        List<String> testOrgs = new ArrayList<String>();
        testOrgs.add("testOrgId");
        if (pvInfo.getDivisionsContentOwner() != null) {
            pvInfo.getDivisionsContentOwner().clear();
            pvInfo.getDivisionsContentOwner().add("testOrgId");
        }
        else {
            pvInfo.setDivisionsContentOwner(testOrgs);
        }

        // Perform the update
        MajorDisciplineInfo updatedMD = programService.updateMajorDiscipline(majorDisciplineInfo.getId(), majorDisciplineInfo, contextInfo);
        List<ProgramVariationInfo> updatedPvInfos = updatedMD.getVariations();
        assertNotNull(updatedPvInfos);
            
        // Verify the update
        verifyUpdatedPVinList(pvInfo, updatedPvInfos);

        // Now explicitly get it
        List<ProgramVariationInfo> retrievedPVs = programService.getVariationsByMajorDisciplineId(majorDisciplineInfo.getId(), contextInfo);
        assertNotNull(retrievedPVs);
            
        // and verify the update
        verifyUpdatedPVinList(pvInfo, retrievedPVs);
    }

    private void verifyUpdatedPVinList(ProgramVariationInfo pvInfo, List<ProgramVariationInfo> updatedPvInfos) {
        boolean found = false;
        for (ProgramVariationInfo updatedPV : updatedPvInfos) {
            if (updatedPV.getId().equals(pvInfo.getId()) && updatedPV.getStateKey().equals("Active")) {
                verifyUpdate(pvInfo, updatedPV); // see comment in verifyUpdate
                found = true;
                break;
            }
        }
        if (!found) {
            fail("Unable to find updated ProgramVariationInfo for comparison");
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
    public void testCreateVariationsByMajorDiscipline() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, DataValidationErrorException, VersionMismatchException, PermissionDeniedException{
        MajorDisciplineInfo majorDisciplineInfo = null;

        majorDisciplineInfo = programService.getMajorDiscipline("d4ea77dd-b492-4554-b104-863e42c5f8b7", contextInfo);
        assertNotNull(majorDisciplineInfo);

        List<ProgramVariationInfo> pvInfos = majorDisciplineInfo.getVariations();
        assertNotNull(pvInfos);

        ProgramVariationInfo pvInfoS = pvInfos.get(0);
        ProgramVariationInfo pvInfoT = new ProgramVariationInfo();

        BeanUtils.copyProperties(pvInfoS, pvInfoT, new String[]{"id"});

        pvInfoT.setLongTitle(pvInfoT.getLongTitle() + "-created");
        pvInfoT.setShortTitle(pvInfoT.getShortTitle() + "-created");
        RichTextInfo testDesc = pvInfoT.getDescr();
        testDesc.setPlain(testDesc.getPlain() + "-created");
        pvInfoT.setDescr(testDesc);
        pvInfoT.setCip2000Code(pvInfoT.getCip2000Code() + "-created");
        pvInfoT.setCip2010Code(pvInfoT.getCip2010Code() + "-created");
        pvInfoT.setTranscriptTitle(pvInfoT.getTranscriptTitle() + "-created");
        pvInfoT.setDiplomaTitle(pvInfoT.getDiplomaTitle() + "-created");

        // Perform the update: adding the new variation
        pvInfos.add(pvInfoT);
        MajorDisciplineInfo updatedMD = programService.updateMajorDiscipline(majorDisciplineInfo.getId(), majorDisciplineInfo, contextInfo);
        List<ProgramVariationInfo> updatedPvInfos = updatedMD.getVariations();
        assertNotNull(updatedPvInfos);
        assertEquals(3, updatedPvInfos.size());

        // Verify the update
        verifyUpdatedPVinList(pvInfoT, updatedPvInfos);

        // Now explicitly get it
        MajorDisciplineInfo retrievedMD = programService.getMajorDiscipline(majorDisciplineInfo.getId(), contextInfo);
        assertEquals(3, retrievedMD.getVariations().size());

        List<ProgramVariationInfo> retrievedPVs = programService.getVariationsByMajorDisciplineId(majorDisciplineInfo.getId(), contextInfo);
        assertNotNull(retrievedPVs);
        assertEquals(3, updatedPvInfos.size());
            
        verifyUpdatedPVinList(pvInfoT, retrievedPVs);
    }

    @Test
    public void testDeleteVariationsByMajorDiscipline() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DataValidationErrorException, VersionMismatchException{
        MajorDisciplineInfo majorDisciplineInfo = null;

            majorDisciplineInfo = programService.getMajorDiscipline("d4ea77dd-b492-4554-b104-863e42c5f8b7", contextInfo);
            assertNotNull(majorDisciplineInfo);

            List<ProgramVariationInfo> pvInfos = majorDisciplineInfo.getVariations();
            assertNotNull(pvInfos);

            //Perform the update: remove a variation
            String var1 = pvInfos.get(1).getId();
            pvInfos.remove(1);
            MajorDisciplineInfo updatedMD = programService.updateMajorDiscipline(majorDisciplineInfo.getId(), majorDisciplineInfo, contextInfo);
            List<ProgramVariationInfo> updatedPvInfos = updatedMD.getVariations();
            assertNotNull(updatedPvInfos);
            assertEquals(2, updatedPvInfos.size());

            // Now explicitly get it
            MajorDisciplineInfo retrievedMD = programService.getMajorDiscipline(majorDisciplineInfo.getId(), contextInfo);
            assertEquals(2, retrievedMD.getVariations().size());

            List<ProgramVariationInfo> retrievedPVs = programService.getVariationsByMajorDisciplineId(majorDisciplineInfo.getId(), contextInfo);
            assertNotNull(retrievedPVs);
            for(ProgramVariationInfo pvi : retrievedPVs){
            	if(pvi.getId().equals(var1)){
            		assertEquals("Suspended", pvi.getStateKey());
            	}
            }
    }

    @Test(expected=DoesNotExistException.class)
    public void testDeleteProgramRequirement() throws Exception {
    	ProgramRequirementInfo progReq = createProgramRequirementTestData();
    	ProgramRequirementInfo createdProgReq = programService.createProgramRequirement(null, progReq,  contextInfo);
			programService.deleteProgramRequirement(createdProgReq.getId(), contextInfo);
    	programService.getProgramRequirement(createdProgReq.getId(),contextInfo);
    }

    @Test
    public void testUpdateCoreProgram() throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DataValidationErrorException, VersionMismatchException {
    	    CoreProgramInfo core = programService.getCoreProgram("00f5f8c5-fff1-4c8b-92fc-789b891e0849", contextInfo);

            // minimal sanity check
            assertNotNull(core);
            assertEquals("BS", core.getCode());
            assertNotNull(core.getShortTitle());
            assertEquals("B.S.", core.getShortTitle());
            assertNotNull(core.getLongTitle());
            assertEquals("Bachelor of Science", core.getLongTitle());
            assertNotNull(core.getDescr());
            assertEquals("Anthropology Major", core.getDescr().getPlain());
            assertEquals(ProgramAssemblerConstants.CORE_PROGRAM, core.getTypeKey());
            assertEquals(DtoConstants.STATE_ACTIVE, core.getStateKey());

            // update some fields
            core.setCode(core.getCode() + "-updated");
            core.setShortTitle(core.getShortTitle() + "-updated");
            core.setLongTitle(core.getLongTitle() + "-updated");
            core.setTranscriptTitle(core.getTranscriptTitle() + "-updated");
            core.setStateKey(DtoConstants.STATE_RETIRED);

           //Perform the update
            CoreProgramInfo updatedCP = programService.updateCoreProgram(null, null, core, contextInfo);

            //Verify the update
            verifyUpdate(updatedCP);

            // Now explicitly get it
            CoreProgramInfo retrievedCP = programService.getCoreProgram(core.getId(), contextInfo);
            verifyUpdate(retrievedCP);

            //TODO: update versioning
	}

    private void verifyUpdate(CoreProgramInfo updatedCP) {
    	assertNotNull(updatedCP);
    	assertEquals("BS-updated", updatedCP.getCode());
        assertEquals("B.S.-updated", updatedCP.getShortTitle());
        assertEquals("Bachelor of Science-updated", updatedCP.getLongTitle());
        assertEquals("TRANSCRIPT-TITLE-updated", updatedCP.getTranscriptTitle());
        assertEquals(DtoConstants.STATE_RETIRED, updatedCP.getStateKey());
    }

    @Test
    public void testDeleteCoreProgram() throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, IllegalArgumentException, SecurityException, IntrospectionException, InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchFieldException, DoesNotExistException {
        	CoreProgramDataGenerator generator = new CoreProgramDataGenerator();
        	CoreProgramInfo coreProgramInfo = generator.getCoreProgramTestData();

            assertNotNull(coreProgramInfo);
            fixLoCategoryIds(coreProgramInfo.getLearningObjectives());
            CoreProgramInfo createdCP = programService.createCoreProgram(null, coreProgramInfo,  contextInfo);
            assertNotNull(createdCP);
            assertEquals(DtoConstants.STATE_DRAFT, createdCP.getStateKey());
            assertEquals(ProgramAssemblerConstants.CORE_PROGRAM, createdCP.getTypeKey());


            String coreProgramId = createdCP.getId();
            CoreProgramInfo retrievedCP = programService.getCoreProgram(coreProgramId, contextInfo);
            assertNotNull(retrievedCP);

            try{
	            programService.deleteCoreProgram(coreProgramId, contextInfo);
	            try {
	            	retrievedCP = programService.getCoreProgram(coreProgramId, contextInfo);
	                fail("Retrieval of deleted coreProgram should have thrown exception");
	            } catch (DoesNotExistException e) {}
            }catch (OperationFailedException e) {}

    }

    @Test
    public void testCreditsProgramRequirement() throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException {
    	ProgramRequirementInfo progReq = createProgramRequirementTestData();
    	progReq.setMinCredits(3);
    	progReq.setMaxCredits(45);
    	ProgramRequirementInfo createdProgReq = programService.createProgramRequirement(null,progReq, contextInfo);
       	assertEquals("3", Integer.toString(createdProgReq.getMinCredits()));
    	assertEquals("45", Integer.toString(createdProgReq.getMaxCredits()));

    	ProgramRequirementInfo progReq2 = programService.getProgramRequirement(createdProgReq.getId(), contextInfo);
       	assertEquals("3", Integer.toString(progReq2.getMinCredits()));
    	assertEquals("45", Integer.toString(progReq2.getMaxCredits()));
    }
    
    private class ServiceMethodInvocationData {
        String methodName;
        Object[] parameters;
        Class<?>[] paramterTypes;
    }
    
    private void invokeForExpectedException(Collection<ServiceMethodInvocationData> methods, Class<? extends Exception> expectedExceptionClass) throws Exception {
        for(ServiceMethodInvocationData methodData : methods) {
            Method method = programService.getClass().getMethod(methodData.methodName, methodData.paramterTypes);
            Throwable expected = null;
            Exception unexpected = null;
            try {
                method.invoke(programService, methodData.parameters);
            }
            catch(InvocationTargetException ex) {
                if(ex.getCause() != null && ex.getCause().getClass().equals(expectedExceptionClass)) {
                    expected = ex.getCause();
                }
                else {
                    unexpected = ex;
                    unexpected.printStackTrace();
                }
            }
            catch(Exception other) {
                unexpected = other;
            }
            finally {
                assertNotNull("An exception of class: " + expectedExceptionClass.toString() + " was expected, but the method: " + methodData.methodName + " threw this exception: " + unexpected, expected);
            }
        }
    }
    
    @Test
    public void testGetVersionMethodsForInvalidParameters() throws Exception {
        String[] getVersionMethods = {"getVersionBySequenceNumber", "getVersions", "getFirstVersion", "getVersionsInDateRange", "getCurrentVersion", "getCurrentVersionOnDate"};
        
        // build an object array with the appropriate number of arguments for each version method to be called
        Object[][] getVersionParams = {new Object[4], new Object[3], new Object[3], new Object[5], new Object[3], new Object[4]};
        
        // build a class array with the parameter types for each method call
        Class<?>[][] getVersionParamTypes = {{String.class, String.class, Long.class, ContextInfo.class}, // for getVersionBySequenceNumber
                {String.class, String.class, ContextInfo.class}, // for getVersions
                {String.class, String.class, ContextInfo.class}, // for getFirstVersion
                {String.class, String.class, Date.class, Date.class, ContextInfo.class}, // for getVersionsInDateRange
                {String.class, String.class, ContextInfo.class}, // for getCurrentVersion
                {String.class, String.class, Date.class, ContextInfo.class}}; // for getCurrentVersionOnDate
        
        String badRefObjectTypeURI = "BADBADBAD";
        Collection<ServiceMethodInvocationData> methods = new ArrayList<ServiceMethodInvocationData>(getVersionMethods.length);
        for(int i = 0; i < getVersionMethods.length; i++) {
            ServiceMethodInvocationData invocationData = new ServiceMethodInvocationData();
            invocationData.methodName = getVersionMethods[i];
            
            // set the first parameter of each invocation to the invalid data
            getVersionParams[i][0] = badRefObjectTypeURI;
            
            invocationData.parameters = getVersionParams[i];
            invocationData.paramterTypes = getVersionParamTypes[i];
            
            methods.add(invocationData);
        }
        
        invokeForExpectedException(methods, InvalidParameterException.class);
    }
    
    /**
     * 
     * This method is a catch-all test for code coverage.  
     * It calls methods in ProgramServiceImpl that have contracts in the interface but are not yet implemented
     * All calls are expected to return null.  Once a method is implemented, its name should be removed from
     * the DUMMY_SERVICE_METHODS array.
     * 
     * NOTE: This method does not work for methods that are overloaded (i.e. have two declarations with the same name, but different parameters)
     * 
     * @throws Exception
     */
    @Test
    public void testCallDummyMethods() throws Exception {
        // We need to get the Method objects, but we do not know or care about the parameter types for the methods
        // so get the all methods of the service and load them into a hashtable, indexed by method name
        Method[] serviceMethods = ProgramService.class.getMethods();
        Map<String, Method> methodMap = new HashMap<String, Method>();
        
        for(Method m : serviceMethods) {
            // if a method is already loaded into the map, ignore subsequent instances with the same name
            if(!methodMap.containsKey(m.getName())) {
                methodMap.put(m.getName(), m);
            }
        }
        
        for(String s : DUMMY_SERVICE_METHODS) {
            Method dummyMethod = methodMap.get(s);
            
            if(dummyMethod == null) {
                throw new Exception("No method " + s + " defined in ProgramService");
            }
            
            // create a set of null parameters to pass to the method
            Object[] params = new Object[dummyMethod.getParameterTypes().length];
            try {
                Object returned = dummyMethod.invoke(programService, params);
                fail("The invocation of " + s + " did not throw an UnsupportedOperationException");
            } catch (InvocationTargetException e) {
                if (!(e.getTargetException() instanceof UnsupportedOperationException)) {
                    fail("The invocation of " + s + " did not throw an UnsupportedOperationException");
                }
            }
            
        }
    }
    
    @Test
    public void testCoreProgramVersioning() throws Exception {
        CoreProgramDataGenerator dataGen = new CoreProgramDataGenerator();
        CoreProgramInfo coreData = dataGen.getCoreProgramTestData();
        
        coreData.getProgramRequirements().clear();
        
        CoreProgramInfo core = programService.createCoreProgram(null, coreData, contextInfo);
        
        CoreProgramInfo newCore = programService.createNewCoreProgramVersion(core.getVersion().getVersionIndId(), "test core program versioning", contextInfo);
        
        assertNotNull(newCore);
        
        programService.setCurrentCoreProgramVersion(newCore.getId(), null,  contextInfo);
        
        // create a second version, and ensure the sequence numbers are different
        CoreProgramInfo secondVersion = null;
        
        try {
            secondVersion = programService.createNewCoreProgramVersion(core.getVersion().getVersionIndId(), "test core program second version", contextInfo);
            assertTrue(true);
        }
        catch (Exception e) {
            assertTrue(false);
        }
        
        assertNotNull(secondVersion);
        
        assertTrue(newCore.getVersion().getSequenceNumber() != secondVersion.getVersion().getSequenceNumber());
        
    }
    
}
