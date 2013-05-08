/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kuali.rice.krms.impl.repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.xml.namespace.QName;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.krms.api.repository.RuleManagementService;
import org.kuali.rice.krms.api.repository.agenda.AgendaDefinition;
import org.kuali.rice.krms.api.repository.agenda.AgendaItemDefinition;
import org.kuali.rice.krms.api.repository.context.ContextDefinition;
import org.kuali.rice.krms.api.repository.language.NaturalLanguageTemplate;
import org.kuali.rice.krms.api.repository.language.NaturalLanguageUsage;
import org.kuali.rice.krms.api.repository.proposition.PropositionDefinition;
import org.kuali.rice.krms.api.repository.proposition.PropositionParameter;
import org.kuali.rice.krms.api.repository.proposition.PropositionParameterType;
import org.kuali.rice.krms.api.repository.proposition.PropositionType;
import org.kuali.rice.krms.api.repository.rule.RuleDefinition;
import org.kuali.rice.krms.api.repository.term.TermDefinition;
import org.kuali.rice.krms.api.repository.term.TermParameterDefinition;
import org.kuali.rice.krms.api.repository.term.TermRepositoryService;
import org.kuali.rice.krms.api.repository.term.TermSpecificationDefinition;
import org.kuali.rice.krms.api.repository.type.KrmsTypeDefinition;
import org.kuali.rice.krms.api.repository.type.KrmsTypeRepositoryService;
import org.kuali.rice.krms.impl.repository.mock.KrmsConfigurationLoader;
import org.kuali.student.common.util.UUIDHelper;
import org.kuali.student.krms.naturallanguage.util.KsKrmsConstants;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.core.process.krms.KSKRMSTestCase;
import org.kuali.student.r2.core.versionmanagement.dto.VersionDisplayInfo;
import org.kuali.student.r2.lum.clu.dto.CluInfo;
import org.kuali.student.r2.lum.clu.dto.CluSetInfo;
import org.kuali.student.r2.lum.clu.service.CluService;
import org.kuali.student.r2.lum.lu.service.impl.CluDataLoader;
import org.kuali.student.r2.lum.lu.service.impl.CluServiceMockImpl;
import org.kuali.student.r2.lum.util.constants.CluServiceConstants;

/**
 *
 * @author nwright
 */
@Ignore
public class RuleManagementServiceImplTest extends KSKRMSTestCase {

    private KrmsTypeRepositoryService krmsTypeRepositoryService = null;
    private RuleManagementService ruleManagementService = null;
    private TermRepositoryService termRepositoryService = null;
    private CluService cluService = null;

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }
    private ContextInfo contextInfo;

    @Before
    public void setUp() throws Exception {
        super.setUp();
        contextInfo = new ContextInfo();
        contextInfo.setPrincipalId("TESTUSER");
        contextInfo.setCurrentDate(new Date());

        this.krmsTypeRepositoryService = (KrmsTypeRepositoryService) GlobalResourceLoader.getService(QName.valueOf("krmsTypeRepositoryService"));
        this.termRepositoryService = (TermRepositoryService) GlobalResourceLoader.getService(QName.valueOf("termRepositoryService"));
        this.ruleManagementService = (RuleManagementService) GlobalResourceLoader.getService(QName.valueOf("ruleManagementService"));

        KrmsConfigurationLoader loader = new KrmsConfigurationLoader();
//        loader.setKrmsTypeRepositoryService(this.krmsTypeRepositoryService);
//        loader.setRuleManagementService(this.ruleManagementService);
//        loader.setTermRepositoryService(this.termRepositoryService);
//        loader.loadConfiguration();

        this.cluService = new CluServiceMockImpl();
        CluDataLoader cluDataLoader = new CluDataLoader();
        cluDataLoader.setCluService(cluService);
        cluDataLoader.setContextInfo(contextInfo);
        cluDataLoader.load();

    }

    @After
    public void tearDown() {
    }

    @Test
    public void testBasicTypeConfiguration() {

        List<KrmsTypeDefinition> types = null;
        Set<String> expected = null;
        String languageCode = KsKrmsConstants.LANGUAGE_CODE_ENGLISH;
        // check that we can get all the different context types
        types = this.krmsTypeRepositoryService.findAllContextTypes();
        expected = new LinkedHashSet<String>();
        // TODO: find out why all the context's were removed!
//        expected.add(KsKrmsConstants.CONTEXT_TYPE_COURSE);
//        expected.add(KsKrmsConstants.CONTEXT_TYPE_PROGRAM);
//        expected.add(KsKrmsConstants.CONTEXT_TYPE_COURSE_OFFERING);
        this.checkTypeNamesAnyOrder(expected, types);

        // Typically the user does not actually select the context but it is
        // hardwired into the program, so the "context" is taken from where 
        // we are within the application, i.e. are we on the course requisites screen?
        System.out.println("Please choose which context of rules that you want to work on:");
        for (KrmsTypeDefinition type : types) {
            System.out.println("     " + this.getScreenDescription(type.getId(), languageCode));
        }
//        System.out.print("==> Choose: ");
//        String selectedId = this.simulateUserChoosingContextTypeId();
//        System.out.println(selectedId);
//        KrmsTypeDefinition selectedContextType = this.krmsTypeRepositoryService.getTypeById(selectedId);
//        description = this.getScreenDescription(selectedContextType.getId(), languageCode);
//        System.out.println("Editing Rules for Context: " + description);


        // 
        KrmsTypeDefinition courseAgendaType = this.simulateGettingCourseAgendaType();
        assertNotNull(courseAgendaType);

        // Get all the agenda types for this course context type

        types = this.krmsTypeRepositoryService.findAgendaTypesForAgendaType(courseAgendaType.getId());
        expected = new LinkedHashSet<String>();
        expected.add(KsKrmsConstants.AGENDA_TYPE_COURSE_ENROLLMENTELIGIBILITY);
        expected.add(KsKrmsConstants.AGENDA_TYPE_COURSE_CREDITCONSTRAINTS);
        this.checkTypeNamesOrdered(expected, types);

        String title = "Please choose which type of rule you want to work on:";
        int defaultIndex = 0;
        KrmsTypeDefinition courseEligAgendaType = this.simulateUserChoosingType(title, types, languageCode, defaultIndex);

        // Get all the agenda types for the main agenda type
        // Right now we don't do this we just have rule types so this should return an empty list
        types = this.krmsTypeRepositoryService.findAgendaTypesForAgendaType(courseEligAgendaType.getId());
        expected = new LinkedHashSet<String>();
        this.checkTypeNamesOrdered(expected, types);

        // Get all the RULE types for the main agenda type
        types = this.krmsTypeRepositoryService.findRuleTypesForAgendaType(courseEligAgendaType.getId());
        expected = new LinkedHashSet<String>();
        expected.add(KsKrmsConstants.RULE_TYPE_COURSE_ACADEMICREADINESS_STUDENTELIGIBILITYPREREQ);
        expected.add(KsKrmsConstants.RULE_TYPE_COURSE_ACADEMICREADINESS_COREQ);
        expected.add(KsKrmsConstants.RULE_TYPE_COURSE_RECOMMENDEDPREPARATION);
        expected.add(KsKrmsConstants.RULE_TYPE_COURSE_ACADEMICREADINESS_ANTIREQ);
        this.checkTypeNamesOrdered(expected, types);

        title = "Please choose which type of rule you want to work on:";
        defaultIndex = 0;
        KrmsTypeDefinition eligPrereqRuleType = this.simulateUserChoosingType(title, types, languageCode, defaultIndex);

        // Get all the Proposition types for the rule type
        types = this.krmsTypeRepositoryService.findPropositionTypesForRuleType(eligPrereqRuleType.getId());
        expected = new LinkedHashSet<String>();
        expected.add(KsKrmsConstants.PROPOSITION_TYPE_FREEFORM_TEXT);
        expected.add(KsKrmsConstants.PROPOSITION_TYPE_SUCCESS_COMPL_COURSE);
        expected.add(KsKrmsConstants.PROPOSITION_TYPE_CUMULATIVE_GPA_MIN);
        expected.add(KsKrmsConstants.PROPOSITION_TYPE_ADMITTED_TO_PROGRAM);
        expected.add(KsKrmsConstants.PROPOSITION_TYPE_SUCCESS_CREDIT_COURSESET_COMPLETED_NOF);
        expected.add(KsKrmsConstants.PROPOSITION_TYPE_SUCCESS_CREDITS_COURSESET_COMPLETED_NOF_ORG);
        expected.add(KsKrmsConstants.PROPOSITION_TYPE_SUCCESS_COURSE_COURSESET_COMPLETED_ALL);
        expected.add(KsKrmsConstants.PROPOSITION_TYPE_SUCCESS_COURSE_COURSESET_COMPLETED_NOF);
        expected.add(KsKrmsConstants.PROPOSITION_TYPE_COURSE_COURSESET_GPA_MIN);
        expected.add(KsKrmsConstants.PROPOSITION_TYPE_COURSE_COURSESET_GRADE_MIN);
        expected.add(KsKrmsConstants.PROPOSITION_TYPE_COURSE_COURSESET_NOF_GRADE_MIN);
        expected.add(KsKrmsConstants.PROPOSITION_TYPE_COURSE_COURSESET_GRADE_MAX);
        expected.add(KsKrmsConstants.PROPOSITION_TYPE_ADMITTED_TO_PROGRAM_CAMPUS);
        expected.add(KsKrmsConstants.PROPOSITION_TYPE_NOTADMITTED_TO_PROGRAM);
        expected.add(KsKrmsConstants.PROPOSITION_TYPE_PERMISSION_INSTRUCTOR_REQUIRED);
        expected.add(KsKrmsConstants.PROPOSITION_TYPE_PERMISSION_ADMIN_ORG);
//        expected.add(KsKrmsConstants.PROPOSITION_TYPE_COURSE_TEST_SCORE_MIN);
//        expected.add(KsKrmsConstants.PROPOSITION_TYPE_TEST_SCORE_BETWEEN_VALUES);
//        expected.add(KsKrmsConstants.PROPOSITION_TYPE_TEST_SCORE);
        this.checkTypeNamesOrdered(expected, types);

        title = "Please choose which type of rule you want to work on:";
        defaultIndex = 7;
        KrmsTypeDefinition nOfCoursesPropositionType = this.simulateUserChoosingType(title, types, languageCode, defaultIndex);
        NaturalLanguageTemplate nlTemplate = this.getRuleEditUsageNaturalLanguageTemplate(nOfCoursesPropositionType.getId(), languageCode);
        assertNotNull(nlTemplate);
        assertEquals("#if($intValue == 1 && $courseCluSet.getCluList().size() == 1)Must have successfully completed $courseCluSet.getCluSetAsCode()#{else}Must have successfully completed a minimum of $intValue $NLHelper.getProperGrammar($intValue, \"course\") from $courseCluSet.getCluSetAsCode()#end", nlTemplate.getTemplate());

//                <entry key="kuali.krms.proposition.type.success.course.courseset.completed.nof">
//                    <bean parent="TemplateInfo-parent"
//                          p:termSpecName="NumberOfCompletedCourses" p:operator="&lt;=" p:value="n">
//                        <property name="componentId" value="KRMS-MultiCourse-Section"/>
//                        <property name="constantComponentId" value="KRMS-NumberOfCourses-ConstantValue"/>
//                        <property name="componentBuilderClass" value="org.kuali.student.enrollment.class1.krms.builder.MultiCourseComponentBuilder"/>
//                    </bean>
//                </entry>


        // Get all the parameter types for the proposition type
        types = this.krmsTypeRepositoryService.findPropositionParameterTypesForPropositionType(nOfCoursesPropositionType.getId());
        expected = new LinkedHashSet<String>();
        this.checkTypeNamesOrdered(expected, types);

//        TermResolverDefinition termResolver =
//                this.termRepositoryService.getTermResolverByNameAndNamespace(termName,
//                KsKrmsConstants.NAMESPACE_CODE);
//        assertNotNull(termResolver);
//        assertEquals (termName, termResolver.getName());
    }

    @Test
    public void testBasicCrud() {
        String description;
        String propId;
        String ruleId;
        String namespace;

        //
        // ok now actually start creating rule
        //

        // this is the course to which we are going to attach the agenda
        // NOTE: this will be a COURSE not a CLU but I don't want to mock the course impl
        CluInfo anchorClu = this.getClu("COURSE1");

        // find/create the context corresponding to this context type.
        ContextDefinition.Builder contextBldr = ContextDefinition.Builder.create(KsKrmsConstants.NAMESPACE_CODE, KsKrmsConstants.CONTEXT_COURSE_REQUIREMENTS);
        contextBldr.setTypeId(KsKrmsConstants.CONTEXT_TYPE_DEFAULT);
        contextBldr.setDescription(KsKrmsConstants.CONTEXT_COURSE_REQUIREMENTS);
        contextBldr.setActive(true);
        ContextDefinition context = this.ruleManagementService.findCreateContext(contextBldr.build());
        assertNotNull(context);
        assertEquals(context.getName(), KsKrmsConstants.CONTEXT_COURSE_REQUIREMENTS);
        assertEquals(context.getNamespace(), KsKrmsConstants.NAMESPACE_CODE);
        assertEquals(context.getTypeId(), KsKrmsConstants.CONTEXT_TYPE_DEFAULT);
        assertTrue(context.isActive());

        // create the agenda
        KrmsTypeDefinition agendaType = this.krmsTypeRepositoryService.getTypeByName(KsKrmsConstants.NAMESPACE_CODE,
                KsKrmsConstants.AGENDA_TYPE_COURSE_ENROLLMENTELIGIBILITY);
        assertNotNull(agendaType);
        String id = null; // set by service when create is called
        // For testing use a GUID instead of anchorClu.getId () so we always get a new one for testing
        String name = UUIDHelper.genStringUUID();
//        String name = agendaType.getName() + " for " + anchorClu.getOfficialIdentifier().getCode() + " (" + anchorClu.getId() + ")";
        String typeId = agendaType.getId();
        String contextId = context.getId();
        AgendaDefinition.Builder agendaBldr = AgendaDefinition.Builder.create(id, name, typeId, contextId);
        agendaBldr.setActive(false);
        AgendaDefinition agenda = this.ruleManagementService.findCreateAgenda(agendaBldr.build());
        assertNotNull(agenda);
        assertNotNull(agenda.getId());
        assertEquals(agendaBldr.getName(), agenda.getName());
        assertEquals(agendaBldr.getContextId(), agenda.getContextId());
        assertEquals(agendaBldr.getTypeId(), agenda.getTypeId());
        assertFalse(agenda.isActive());
        assertNotNull(agenda.getFirstItemId());

        // create the cluset
        CluInfo courseClu2 = this.getClu("COURSE2");
        CluInfo courseClu3 = this.getClu("COURSE3");
        CluInfo courseClu4 = this.getClu("COURSE4");
        List<String> versionIndIds = this.getVersionIndIds(anchorClu, courseClu2, courseClu3);
        CluSetInfo cluSet = findCreateCluSet("cluSet23", "Courses 2 & 3", versionIndIds);

        // create all the parameters
        List<PropositionParameter.Builder> parameters = new ArrayList<PropositionParameter.Builder>();
        String termName = "NumberOfCompletedCourses";
        String constantValue = "1";
        String operator = "<=";

        // first the parameter to the parameter! (actually the term parameter to the proposition parameter that is a term!
        List<TermParameterDefinition.Builder> termParameters = new ArrayList<TermParameterDefinition.Builder>();
        id = null; //set by service
        String termId = null;
        name = "CourseSetId";
        String value = cluSet.getId(); // this was created when the cluSet was built
        TermParameterDefinition.Builder termParamBldr = TermParameterDefinition.Builder.create(id, termId, name, value);
        termParameters.add(termParamBldr);

        id = null; // set by service
        TermSpecificationDefinition termSpec =
                this.termRepositoryService.getTermSpecificationByNameAndNamespace(termName,
                KsKrmsConstants.NAMESPACE_CODE);
        assertNotNull(termSpec);
        assertEquals(termName, termSpec.getName());
        TermSpecificationDefinition.Builder termSpecBldr = TermSpecificationDefinition.Builder.create(termSpec);
        description = termSpec.getName() + " for " + cluSet.getId();
        TermDefinition.Builder termBldr = TermDefinition.Builder.create(id, termSpecBldr, termParameters);
        termBldr.setDescription(description);


        // do the term parameter first
        id = null; // should be set by service
        propId = null; // should also be set by the service when the create on the proposition happens
        value = null;  // set by service
        String parameterType = PropositionParameterType.TERM.getCode();
        Integer sequenceNumber = 1;
        PropositionParameter.Builder propParamBldr = PropositionParameter.Builder.create(id, propId, value, parameterType, sequenceNumber);
        propParamBldr.setTermValue(termBldr.build());
        parameters.add(propParamBldr);


        // do the constant value next
        id = null; // should be set by service
        propId = null; // should also be set by the service when the create on the proposition happens
        value = constantValue;
        parameterType = PropositionParameterType.CONSTANT.getCode();
        sequenceNumber = 2;
        propParamBldr = PropositionParameter.Builder.create(id, propId, value, parameterType, sequenceNumber);
        parameters.add(propParamBldr);

        // do the operator 
        id = null; // should be set by service
        propId = null; // should also be set by the service when the create on the proposition happens
        value = operator;
        parameterType = PropositionParameterType.OPERATOR.getCode();
        sequenceNumber = 3;
        propParamBldr = PropositionParameter.Builder.create(id, propId, value, parameterType, sequenceNumber);
        parameters.add(propParamBldr);

        propId = null; // should be null until assigned by service
        String propTypeCode = PropositionType.SIMPLE.getCode();
        ruleId = null;  // assigned by service
        KrmsTypeDefinition propType = this.krmsTypeRepositoryService.getTypeByName(KsKrmsConstants.NAMESPACE_CODE, KsKrmsConstants.PROPOSITION_TYPE_COURSE_COURSESET_COMPLETED_NOF);
        typeId = propType.getId();
        PropositionDefinition.Builder propBldr = PropositionDefinition.Builder.create(propId, propTypeCode, ruleId, typeId, parameters);
        propBldr.setDescription(propType.getName());


        // make rule
        KrmsTypeDefinition ruleType = this.krmsTypeRepositoryService.getTypeByName(KsKrmsConstants.NAMESPACE_CODE,
                KsKrmsConstants.RULE_TYPE_COURSE_ACADEMICREADINESS_STUDENTELIGIBILITYPREREQ);
        ruleId = null; // sevice sets id
        // name has to be unique within namespace and max 100 characters.
        // For testing use a GUID instead of anchorClu.getId () so we always get a new one for testing
        name = UUIDHelper.genStringUUID();
//        name = anchorClu.getOfficialIdentifier().getCode() + " " + ruleType.getName() + " (" + anchorClu.getId() + ")";
        namespace = KsKrmsConstants.NAMESPACE_CODE;
        typeId = ruleType.getId();
        propId = null;
        RuleDefinition.Builder ruleBldr = RuleDefinition.Builder.create(ruleId, name, namespace, typeId, propId);
        ruleBldr.setActive(true);
        ruleBldr.setProposition(propBldr);

        RuleDefinition rule = this.ruleManagementService.getRuleByNameAndNamespace(name, namespace);
        if (rule == null) {
            rule = this.ruleManagementService.createRule(ruleBldr.build());
        }
        assertEquals(ruleBldr.getName(), rule.getName());
        assertEquals(ruleBldr.getNamespace(), rule.getNamespace());
        assertEquals(ruleBldr.getTypeId(), rule.getTypeId());
        assertNotNull(rule.getId());
        assertNotNull(rule.getProposition());
        assertNotNull(rule.getPropId());
        assertEquals(rule.getPropId(), rule.getProposition().getId());

        AgendaItemDefinition firstItem = this.ruleManagementService.getAgendaItem(agenda.getFirstItemId());
        AgendaItemDefinition.Builder itemBldr = AgendaItemDefinition.Builder.create(firstItem);
        itemBldr.setRule(RuleDefinition.Builder.create(rule));
        this.ruleManagementService.updateAgendaItem(itemBldr.build());
        firstItem = this.ruleManagementService.getAgendaItem(firstItem.getId());
        assertNotNull(firstItem);
        assertNotNull(firstItem.getId());
        assertNotNull(firstItem.getRuleId());
        assertNotNull(firstItem.getRule());
        assertNotNull(agenda.getId(), firstItem.getAgendaId());

        assertNotNull(firstItem.getRule().getPropId());
        assertNotNull(firstItem.getRule().getProposition());
        assertNotNull(firstItem.getRule().getProposition().getId());
        assertEquals(propBldr.getPropositionTypeCode(), firstItem.getRule().getProposition().getPropositionTypeCode());
        assertNull(firstItem.getRule().getProposition().getCompoundOpCode());
        assertEquals(0, firstItem.getRule().getProposition().getCompoundComponents().size());
        assertEquals(firstItem.getRuleId(), firstItem.getRule().getProposition().getRuleId());
        assertEquals(propBldr.getTypeId(), firstItem.getRule().getProposition().getTypeId());
        assertEquals(3, firstItem.getRule().getProposition().getParameters().size());

        PropositionParameter propParam = firstItem.getRule().getProposition().getParameters().get(0);
        assertNotNull(propParam.getId());
        assertEquals(new Integer(1), propParam.getSequenceNumber());
        assertEquals(PropositionParameterType.TERM.getCode(), propParam.getParameterType());
        assertEquals(firstItem.getRule().getProposition().getId(), propParam.getPropId());
        assertNotNull(propParam.getValue());
        assertNotNull(propParam.getTermValue());
        assertNotNull(propParam.getTermValue().getId());
        assertEquals(propParam.getValue(), propParam.getTermValue().getId());
        assertNotNull(propParam.getTermValue().getSpecification());
        assertEquals(termName, propParam.getTermValue().getSpecification().getName());
        assertEquals(KsKrmsConstants.NAMESPACE_CODE, propParam.getTermValue().getSpecification().getNamespace());
        assertEquals(1, propParam.getTermValue().getParameters().size());
        assertNotNull(propParam.getTermValue().getParameters().get(0).getId());
        assertEquals("CourseSetId", propParam.getTermValue().getParameters().get(0).getName());
        assertEquals(cluSet.getId(), propParam.getTermValue().getParameters().get(0).getValue());
        
        propParam = firstItem.getRule().getProposition().getParameters().get(1);
        assertNotNull(propParam.getId());
        assertEquals(new Integer(2), propParam.getSequenceNumber());
        assertEquals(PropositionParameterType.CONSTANT.getCode(), propParam.getParameterType());
        assertEquals(firstItem.getRule().getProposition().getId(), propParam.getPropId());
        assertNotNull(propParam.getValue());
        assertNull(propParam.getTermValue());
        assertEquals("1", propParam.getValue());
          
        propParam = firstItem.getRule().getProposition().getParameters().get(2);
        assertNotNull(propParam.getId());
        assertEquals(new Integer(3), propParam.getSequenceNumber());
        assertEquals(PropositionParameterType.OPERATOR.getCode(), propParam.getParameterType());
        assertEquals(firstItem.getRule().getProposition().getId(), propParam.getPropId());
        assertNotNull(propParam.getValue());
        assertNull(propParam.getTermValue());
        assertEquals("<=", propParam.getValue());
        
        // now test updating the constant count from 1 to 2 
        itemBldr = AgendaItemDefinition.Builder.create(firstItem);
        parameters = new ArrayList<PropositionParameter.Builder>();
        parameters.add(PropositionParameter.Builder.create(firstItem.getRule().getProposition().getParameters().get(0)));
        propParam = firstItem.getRule().getProposition().getParameters().get(1);
        propParamBldr = PropositionParameter.Builder.create(propParam);
        propParamBldr.setValue("2");
        parameters.add(propParamBldr);
        parameters.add(PropositionParameter.Builder.create(firstItem.getRule().getProposition().getParameters().get(2)));
        itemBldr.getRule().getProposition().setParameters(parameters);
        
        this.ruleManagementService.updateAgendaItem(itemBldr.build());
        AgendaItemDefinition orig = firstItem;
        firstItem = this.ruleManagementService.getAgendaItem(itemBldr.getId());
        assertNotNull(firstItem);
        assertEquals(orig.getId(), 
                firstItem.getId());
        assertEquals(orig.getRuleId (),
                firstItem.getRuleId());
        assertEquals(orig.getAgendaId (),
                firstItem.getAgendaId());
        assertEquals (orig.getRule().getPropId(), 
                firstItem.getRule().getPropId());
        assertEquals (orig.getRule().getProposition().getId(), 
                firstItem.getRule().getProposition().getId());
        assertEquals(orig.getRule().getProposition().getPropositionTypeCode(), 
                firstItem.getRule().getProposition().getPropositionTypeCode());
        assertEquals(orig.getRule().getProposition().getCompoundOpCode(),
                firstItem.getRule().getProposition().getCompoundOpCode());
        assertEquals(orig.getRule().getProposition().getCompoundComponents().size(),
                firstItem.getRule().getProposition().getCompoundComponents().size());
        assertEquals(orig.getRule().getProposition().getRuleId(),
                firstItem.getRule().getProposition().getRuleId());
        assertEquals(orig.getRule().getProposition().getTypeId(),
                firstItem.getRule().getProposition().getTypeId());
        assertEquals(orig.getRule().getProposition().getParameters().size(),
                firstItem.getRule().getProposition().getParameters().size());

        // check the first parameter the term
        propParam = firstItem.getRule().getProposition().getParameters().get(0);
        PropositionParameter origParam = orig.getRule().getProposition().getParameters().get(0);
        assertEquals(origParam.getId (), 
                     propParam.getId());
        assertEquals(origParam.getSequenceNumber (), 
                     propParam.getSequenceNumber());
        assertEquals(origParam.getParameterType(), 
                     propParam.getParameterType());
        assertEquals(origParam.getPropId(), 
                     propParam.getPropId());
        assertEquals (origParam.getValue(),
                      propParam.getValue());
        assertNotNull(propParam.getTermValue());
        assertEquals(origParam.getTermValue().getId(),
                     propParam.getTermValue().getId());
        assertEquals(propParam.getValue(), propParam.getTermValue().getId());
        assertNotNull(propParam.getTermValue().getSpecification());
        assertEquals(origParam.getTermValue().getSpecification().getName(),
                     propParam.getTermValue().getSpecification().getName());
        assertEquals(origParam.getTermValue().getSpecification().getNamespace(), 
                     propParam.getTermValue().getSpecification().getNamespace());
        assertEquals(origParam.getTermValue().getParameters().size(), 
                     propParam.getTermValue().getParameters().size());
        assertEquals(origParam.getTermValue().getParameters().get(0).getId(),
                     propParam.getTermValue().getParameters().get(0).getId());
        assertEquals(origParam.getTermValue().getParameters().get(0).getName(), 
                     propParam.getTermValue().getParameters().get(0).getName());
        assertEquals(origParam.getTermValue().getParameters().get(0).getValue(), 
                     propParam.getTermValue().getParameters().get(0).getValue());
        
        propParam = firstItem.getRule().getProposition().getParameters().get(1);
        origParam = orig.getRule().getProposition().getParameters().get(1);
       assertEquals(origParam.getId (), 
                     propParam.getId());
        assertEquals(origParam.getSequenceNumber (), 
                     propParam.getSequenceNumber());
        assertEquals(origParam.getParameterType(), 
                     propParam.getParameterType());
        assertEquals(origParam.getPropId(), 
                     propParam.getPropId());
//        assertEquals (origParam.getValue(),
//                      propParam.getValue());
        assertEquals("2", propParam.getValue());
        assertNull(propParam.getTermValue());
          
        propParam = firstItem.getRule().getProposition().getParameters().get(2);
        origParam = orig.getRule().getProposition().getParameters().get(2);
        assertEquals(origParam.getId (), 
                     propParam.getId());
        assertEquals(origParam.getSequenceNumber (), 
                     propParam.getSequenceNumber());
        assertEquals(origParam.getParameterType(), 
                     propParam.getParameterType());
        assertEquals(origParam.getPropId(), 
                     propParam.getPropId());
        assertEquals (origParam.getValue(),
                      propParam.getValue());
        assertNull(propParam.getTermValue());
        
        // now test swapping out and using a whole new proposition
        
        // create all the parameters
        parameters = new ArrayList<PropositionParameter.Builder>();
        termName = "FreeFormText";
        constantValue = "true";
        operator = "=";

        // first the parameter to the parameter! (actually the term parameter to the proposition parameter that is a term!
        termParameters = new ArrayList<TermParameterDefinition.Builder>();
        id = null; //set by service
        termId = null;
        name = "Text";
        value = "My Free Form Text"; 
        termParamBldr = TermParameterDefinition.Builder.create(id, termId, name, value);
        termParameters.add(termParamBldr);

        id = null; // set by service
        termSpec =
                this.termRepositoryService.getTermSpecificationByNameAndNamespace(termName,
                KsKrmsConstants.NAMESPACE_CODE);
        assertNotNull(termSpec);
        assertEquals(termName, termSpec.getName());
        termSpecBldr = TermSpecificationDefinition.Builder.create(termSpec);
        description = termSpec.getName() + " for " + cluSet.getId();
        termBldr = TermDefinition.Builder.create(id, termSpecBldr, termParameters);
        termBldr.setDescription(description);

        // do the term parameter first
        id = null; // should be set by service
        propId = null; // should also be set by the service when the create on the proposition happens
        value = null;  // set by service
        parameterType = PropositionParameterType.TERM.getCode();
        sequenceNumber = 1;
        propParamBldr = PropositionParameter.Builder.create(id, propId, value, parameterType, sequenceNumber);
        propParamBldr.setTermValue(termBldr.build());
        parameters.add(propParamBldr);


        // do the constant value next
        id = null; // should be set by service
        propId = null; // should also be set by the service when the create on the proposition happens
        value = constantValue;
        parameterType = PropositionParameterType.CONSTANT.getCode();
        sequenceNumber = 2;
        propParamBldr = PropositionParameter.Builder.create(id, propId, value, parameterType, sequenceNumber);
        parameters.add(propParamBldr);

        // do the operator 
        id = null; // should be set by service
        propId = null; // should also be set by the service when the create on the proposition happens
        value = operator;
        parameterType = PropositionParameterType.OPERATOR.getCode();
        sequenceNumber = 3;
        propParamBldr = PropositionParameter.Builder.create(id, propId, value, parameterType, sequenceNumber);
        parameters.add(propParamBldr);

        propId = null; // should be null until assigned by service
        propTypeCode = PropositionType.SIMPLE.getCode();
        ruleId = null;  // assigned by service
        propType = this.krmsTypeRepositoryService.getTypeByName(KsKrmsConstants.NAMESPACE_CODE, KsKrmsConstants.PROPOSITION_TYPE_FREEFORM_TEXT);
        typeId = propType.getId();
        propBldr = PropositionDefinition.Builder.create(propId, propTypeCode, ruleId, typeId, parameters);
        propBldr.setDescription(propType.getName());
        
        PropositionDefinition origProp = firstItem.getRule().getProposition();
        itemBldr = AgendaItemDefinition.Builder.create(firstItem);
        itemBldr.getRule().setProposition(propBldr);        
        this.ruleManagementService.updateAgendaItem(itemBldr.build());

        orig = firstItem;
        firstItem = this.ruleManagementService.getAgendaItem(itemBldr.getId());
        assertNotNull(firstItem);
        assertEquals(orig.getId(), 
                firstItem.getId());
        assertEquals(orig.getRuleId (),
                firstItem.getRuleId());
        assertEquals(orig.getAgendaId (),
                firstItem.getAgendaId());
        assertNotNull(firstItem.getRule().getPropId());
        assertNotNull(firstItem.getRule().getProposition());
        assertNotNull(firstItem.getRule().getProposition().getId());
        assertEquals(propBldr.getPropositionTypeCode(), firstItem.getRule().getProposition().getPropositionTypeCode());
        assertNull(firstItem.getRule().getProposition().getCompoundOpCode());
        assertEquals(0, firstItem.getRule().getProposition().getCompoundComponents().size());
        assertEquals(firstItem.getRuleId(), firstItem.getRule().getProposition().getRuleId());
        assertEquals(propBldr.getTypeId(), firstItem.getRule().getProposition().getTypeId());
        assertEquals(3, firstItem.getRule().getProposition().getParameters().size());

        propParam = firstItem.getRule().getProposition().getParameters().get(0);
        assertNotNull(propParam.getId());
        assertEquals(new Integer(1), propParam.getSequenceNumber());
        assertEquals(PropositionParameterType.TERM.getCode(), propParam.getParameterType());
        assertEquals(firstItem.getRule().getProposition().getId(), propParam.getPropId());
        assertNotNull(propParam.getValue());
        assertNotNull(propParam.getTermValue());
        assertNotNull(propParam.getTermValue().getId());
        assertEquals(propParam.getValue(), propParam.getTermValue().getId());
        assertNotNull(propParam.getTermValue().getSpecification());
        assertEquals(termName, propParam.getTermValue().getSpecification().getName());
        assertEquals(KsKrmsConstants.NAMESPACE_CODE, propParam.getTermValue().getSpecification().getNamespace());
        assertEquals(1, propParam.getTermValue().getParameters().size());
        assertNotNull(propParam.getTermValue().getParameters().get(0).getId());
        assertEquals("Text", propParam.getTermValue().getParameters().get(0).getName());
        assertEquals("My Free Form Text", propParam.getTermValue().getParameters().get(0).getValue());
        
        propParam = firstItem.getRule().getProposition().getParameters().get(1);
        assertNotNull(propParam.getId());
        assertEquals(new Integer(2), propParam.getSequenceNumber());
        assertEquals(PropositionParameterType.CONSTANT.getCode(), propParam.getParameterType());
        assertEquals(firstItem.getRule().getProposition().getId(), propParam.getPropId());
        assertNotNull(propParam.getValue());
        assertNull(propParam.getTermValue());
        assertEquals("true", propParam.getValue());
          
        propParam = firstItem.getRule().getProposition().getParameters().get(2);
        assertNotNull(propParam.getId());
        assertEquals(new Integer(3), propParam.getSequenceNumber());
        assertEquals(PropositionParameterType.OPERATOR.getCode(), propParam.getParameterType());
        assertEquals(firstItem.getRule().getProposition().getId(), propParam.getPropId());
        assertNotNull(propParam.getValue());
        assertNull(propParam.getTermValue());
        assertEquals("=", propParam.getValue());
        
        
    }

    private CluSetInfo findCreateCluSet(String id, String name, List<String> versionIndIds) {
        CluSetInfo cluSetInfo;
        try {
            cluSetInfo = this.cluService.getCluSet(id, contextInfo);
            return cluSetInfo;
        } catch (DoesNotExistException ex) {
           // does not exist so create
        } catch (Exception ex) {
            throw new IllegalArgumentException(ex);
        }
        cluSetInfo = new CluSetInfo();
        cluSetInfo.setId(id);
        cluSetInfo.setTypeKey(CluServiceConstants.CLUSET_TYPE_CREDIT_COURSE);
        cluSetInfo.setStateKey("Active");
        cluSetInfo.setName(name);
        cluSetInfo.setEffectiveDate(new Date());
        cluSetInfo.setIsReferenceable(Boolean.TRUE);
        cluSetInfo.setIsReusable(Boolean.FALSE);
        cluSetInfo.setCluIds(versionIndIds);
        try {
            cluSetInfo = this.cluService.createCluSet(cluSetInfo.getTypeKey(), cluSetInfo, contextInfo);
        } catch (Exception ex) {
            throw new IllegalArgumentException(ex);
        }
        return cluSetInfo;
    }

    private List<String> getVersionIndIds(CluInfo... clus) {
        List<String> list = new ArrayList<String>();
        for (CluInfo clu : clus) {
            list.add(clu.getVersion().getVersionIndId());
        }
        return list;
    }

    private CluInfo getClu(String id) {
        try {
            return this.cluService.getClu(id, contextInfo);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
    // cache
    private transient Map<String, NaturalLanguageUsage> name2NaturalLanguageUsageCache = null;

    private NaturalLanguageUsage getNaturalLanguageUsage(String name, String namespace) {
        if (name2NaturalLanguageUsageCache == null) {
            name2NaturalLanguageUsageCache = new LinkedHashMap<String, NaturalLanguageUsage>();
        }
        String key = namespace + ":" + name;
        NaturalLanguageUsage usage = name2NaturalLanguageUsageCache.get(key);
        if (usage != null) {
            return usage;
        }
        // get the usage
        usage = this.ruleManagementService.getNaturalLanguageUsageByNameAndNamespace(name, namespace);
        assertNotNull(usage);
        assertEquals(name, usage.getName());
        assertEquals(namespace, usage.getNamespace());
        name2NaturalLanguageUsageCache.put(key, usage);
        return usage;
    }

    private NaturalLanguageUsage getTypeDescriptionUsage() {
        return this.getNaturalLanguageUsage(KsKrmsConstants.KRMS_NL_TYPE_DESCRIPTION, KsKrmsConstants.NAMESPACE_CODE);
    }

    private NaturalLanguageUsage getRuleEditUsage() {
        return this.getNaturalLanguageUsage(KsKrmsConstants.KRMS_NL_RULE_EDIT, KsKrmsConstants.NAMESPACE_CODE);
    }

    private NaturalLanguageUsage getPreviewUsage() {
        return this.getNaturalLanguageUsage(KsKrmsConstants.KRMS_NL_PREVIEW, KsKrmsConstants.NAMESPACE_CODE);
    }

    private KrmsTypeDefinition simulateGettingCourseAgendaType() {
        KrmsTypeDefinition type = this.krmsTypeRepositoryService.getTypeByName(KsKrmsConstants.NAMESPACE_CODE, KsKrmsConstants.AGENDA_TYPE_COURSE);
        assertNotNull(type);
        assertEquals(KsKrmsConstants.AGENDA_TYPE_COURSE, type.getName());
        assertEquals(KsKrmsConstants.NAMESPACE_CODE, type.getNamespace());
        return type;
    }

    private KrmsTypeDefinition simulateUserChoosingType(String title, List<KrmsTypeDefinition> types, String languageCode, int defaultIndex) {
        System.out.println(title);
        this.displayScreenDescriptions(types, languageCode);
        System.out.print("==> Choose: ");
        KrmsTypeDefinition selected = types.get(defaultIndex);
        System.out.println(selected.getName());
        return selected;
    }

    private void displayScreenDescriptions(List<KrmsTypeDefinition> types, String languageCode) {
        for (KrmsTypeDefinition type : types) {
            System.out.println(this.getScreenDescription(type.getId(), languageCode));
        }
    }

    private String getScreenDescription(String typeId, String languageCode) {
        // check there is a corresponding template so we can display on the screen what kind of rule this is
        NaturalLanguageTemplate template =
                this.ruleManagementService.findNaturalLanguageTemplateByLanguageCodeTypeIdAndNluId(languageCode,
                typeId,
                this.getTypeDescriptionUsage().getId());
        if (template == null) {
            System.out.println("could not find template for " + typeId + " with language " + languageCode + " and for type description usage");
        }
        assertNotNull(template);
        assertEquals(languageCode, template.getLanguageCode());
        assertEquals(typeId, template.getTypeId());
        assertEquals(getTypeDescriptionUsage().getId(), template.getNaturalLanguageUsageId());
        return template.getTemplate();
    }

    private NaturalLanguageTemplate getRuleEditUsageNaturalLanguageTemplate(String typeId, String languageCode) {
        // check there is a corresponding template so we can display on the screen what kind of rule this is
        NaturalLanguageTemplate template =
                this.ruleManagementService.findNaturalLanguageTemplateByLanguageCodeTypeIdAndNluId(languageCode,
                typeId,
                this.getRuleEditUsage().getId());
        if (template == null) {
            System.out.println("could not find template for " + typeId + " with language " + languageCode + " and for rule edit usage");
        }
        assertNotNull(template);
        assertEquals(languageCode, template.getLanguageCode());
        assertEquals(typeId, template.getTypeId());
        assertEquals(getRuleEditUsage().getId(), template.getNaturalLanguageUsageId());
        return template;
    }

    private void checkTypeNamesAnyOrder(Set<String> expected, List<KrmsTypeDefinition> types) {
        List<String> unexpected = new ArrayList<String>();
        for (KrmsTypeDefinition type : types) {
            if (!expected.remove(type.getName())) {
                unexpected.add(type.getName());
            }
        }
        if (!expected.isEmpty() || !unexpected.isEmpty()) {
            fail(expected.size() + " types expected that were not found " + expected
                    + " and "
                    + unexpected.size() + " types were found but not expected " + unexpected);
        }
    }

    private void checkTypeNamesOrdered(Set<String> expected, List<KrmsTypeDefinition> types) {
        List<String> expectedOrdered = new ArrayList(expected);
        this.checkTypeNamesAnyOrder(expected, types);
        for (int i = 0; i < types.size(); i++) {
            if (!expectedOrdered.get(i).equals(types.get(i).getName())) {
                fail("Expected " + i + "th position to have " + expectedOrdered.get(i) + " but found " + types.get(i).getName());
            }
        }
    }

    private CluInfo getCurrentCluInfoByVersionIndId(String id, ContextInfo contextInfo) {
        VersionDisplayInfo versionDisplayInfo = null;
        try {
            versionDisplayInfo = this.cluService.getCurrentVersion(CluServiceConstants.CLU_NAMESPACE_URI, id, contextInfo);
        } catch (Exception ex) {
            throw new IllegalArgumentException("Unexpected", ex);
        }
        try {
            return this.cluService.getClu(versionDisplayInfo.getId(), contextInfo);
        } catch (Exception ex) {
            throw new IllegalArgumentException("Unexpected", ex);
        }
    }
}
