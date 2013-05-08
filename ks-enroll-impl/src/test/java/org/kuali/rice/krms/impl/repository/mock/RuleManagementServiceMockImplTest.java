/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kuali.rice.krms.impl.repository.mock;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Ignore;
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
import org.kuali.rice.krms.api.repository.term.TermResolverDefinition;
import org.kuali.rice.krms.api.repository.term.TermSpecificationDefinition;
import org.kuali.rice.krms.api.repository.type.KrmsTypeDefinition;
import org.kuali.rice.krms.api.repository.type.KrmsTypeRepositoryService;
import org.kuali.rice.krms.impl.repository.language.SimpleNaturalLanguageTemplater;
import org.kuali.student.krms.naturallanguage.util.KsKrmsConstants;
import org.kuali.student.r2.common.dto.ContextInfo;
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
public class RuleManagementServiceMockImplTest {

    public RuleManagementServiceMockImplTest() {
    }
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
    public void setUp() {
        contextInfo = new ContextInfo();
        contextInfo.setPrincipalId("TESTUSER");
        contextInfo.setCurrentDate(new Date());

        this.krmsTypeRepositoryService = new KrmsTypeRepositoryServiceMockImpl();
        this.termRepositoryService = new TermRepositoryServiceMockImpl();
        this.ruleManagementService = new RuleManagementServiceMockImpl();
        ((RuleManagementServiceMockImpl) this.ruleManagementService).setTemplater(new SimpleNaturalLanguageTemplater());
        ((RuleManagementServiceMockImpl) this.ruleManagementService).setTermRepositoryService(termRepositoryService);
        KrmsConfigurationLoader loader = new KrmsConfigurationLoader();
        loader.setKrmsTypeRepositoryService(this.krmsTypeRepositoryService);
        loader.setRuleManagementService(this.ruleManagementService);
        loader.setTermRepositoryService(this.termRepositoryService);
        loader.loadConfiguration();

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
    public void testStadardAuthoringUsageCase() {
        System.out.println("testStadardAuthoringUsageCase");

        List<KrmsTypeDefinition> types = null;
        Set<String> expected = null;
        String languageCode = KsKrmsConstants.LANGUAGE_CODE_ENGLISH;

        // check that we can get all the different context types
        types = this.krmsTypeRepositoryService.findAllContextTypes();
        expected = new LinkedHashSet<String>();
        expected.add(KsKrmsConstants.AGENDA_TYPE_COURSE);
        expected.add(KsKrmsConstants.AGENDA_TYPE_PROGRAM);
//        expected.add(KsKrmsConstants.CONTEXT_TYPE_COURSE_OFFERING);
        this.checkTypeNamesAnyOrder(expected, types);

        // Typically the user does not actually select the context but it is
        // hardwired into the program, so the "context" is taken from where 
        // we are within the application, i.e. are we on the course requisites screen?
        System.out.println("Please choose which context of rules that you want to work on:");
        for (KrmsTypeDefinition type : types) {
            System.out.println("     " + this.getScreenDescription(type.getId(), languageCode));
        }
        System.out.print("==> Choose: ");
        String selectedId = this.simulateUserChoosingMainTypeId();
        System.out.println(selectedId);
        KrmsTypeDefinition selectedContextType = this.krmsTypeRepositoryService.getTypeById(selectedId);
        String description = this.getScreenDescription(selectedContextType.getId(), languageCode);
        System.out.println("Editing Rules for Context: " + description);


        // Get all the agenda types for this course context type
        types = this.krmsTypeRepositoryService.findAgendaTypesForContextType(selectedContextType.getId());
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
        expected.add(KsKrmsConstants.PROPOSITION_TYPE_COURSE_TEST_SCORE_MIN);
        expected.add(KsKrmsConstants.PROPOSITION_TYPE_TEST_SCORE_BETWEEN_VALUES);
        expected.add(KsKrmsConstants.PROPOSITION_TYPE_TEST_SCORE);
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
        // for now we are not defining types for parameters
//        expected.add(KsKrmsConstants.PROPOSITION_PARAMETER_TYPE_TERM_NUMBER_OF_COMPLETED_COURSES);
//        expected.add(KsKrmsConstants.PROPOSITION_PARAMETER_TYPE_CONSTANT_VALUE_N);
//        expected.add(KsKrmsConstants.PROPOSITION_PARAMETER_TYPE_OPERATOR_LESS_THAN_OR_EQUAL_TO);
        this.checkTypeNamesOrdered(expected, types);

        String termName = "NumberOfCompletedCourses";
        String constantValue = "1";
        String operator = "<=";

//      TermSpec 
        TermSpecificationDefinition termSpec =
                this.termRepositoryService.getTermSpecificationByNameAndNamespace(termName,
                KsKrmsConstants.NAMESPACE_CODE);
        assertNotNull(termSpec);
        assertEquals (termName, termSpec.getName());

        TermResolverDefinition termResolver =
                this.termRepositoryService.getTermResolverByNameAndNamespace(termName,
                KsKrmsConstants.NAMESPACE_CODE);
        assertNotNull(termResolver);
        assertEquals (termName, termResolver.getName());

        //
        // ok now actually start creating rule
        //

//
//        // this is the course to which we are going to attach the agenda
//        // NOTE: this will be a COURSE not a CLU but I don't want to mock the course impl
//        CluInfo anchorClu = this.getClu("COURSE1");
//
//        // find/create the context corresponding to this context type.
//        // TODO: Confirm convention that we are creating a single context for all rules of that particular context type, right?
//        ContextDefinition.Builder bldr = ContextDefinition.Builder.create(KsKrmsConstants.NAMESPACE_CODE, selectedContextType.getName());
//        // TODO: find out what this typeId is really supposed to be.
//        // I thought it would be the selected context type but in the existing configured data it has a value T1004?!? 
//        bldr.setTypeId(selectedContextType.getId());
//        bldr.setDescription(description); // set the description to be the template description of the type
//        bldr.setActive(true);
//        ContextDefinition context = this.ruleManagementService.findCreateContext(bldr.build());
//        assertNotNull(context);
//        assertEquals(context.getName(), selectedContextType.getName());
//        assertEquals(context.getNamespace(), selectedContextType.getNamespace());
//        // TODO: worry that this context could have been created with a different type and/or description
//        assertEquals(context.getTypeId(), selectedContextType.getId());
//        assertEquals(context.getDescription(), description);
//
//        // create the agenda
//        String id = null; // set by service when create is called
//        String name = courseEligAgendaType.getName() + " for " + anchorClu.getOfficialIdentifier().getCode() + " " + anchorClu.getId();
//        String typeId = courseEligAgendaType.getId();
//        String contextId = context.getId();
//        AgendaDefinition.Builder agendaBldr = AgendaDefinition.Builder.create(id, name, typeId, contextId);
//        agendaBldr.setActive(false);
//        AgendaDefinition agenda = this.ruleManagementService.createAgenda(agendaBldr.build());
//
//        // create the rule
//        String ruleId = null; // sevice sets id
//        // TODO: find out if this really needs to be unique.. if not remove the actual internal Id of the course from the end 
//        name = eligPrereqRuleType.getName() + " for " + anchorClu.getOfficialIdentifier().getCode() + " " + anchorClu.getId();
//        String namespace = KsKrmsConstants.NAMESPACE_CODE;
//        typeId = eligPrereqRuleType.getId();
//        String propId = null;
//        RuleDefinition.Builder ruleBldr = RuleDefinition.Builder.create(ruleId, name, namespace, typeId, propId);
//        ruleBldr.setActive(true);
//        RuleDefinition rule = this.ruleManagementService.createRule(ruleBldr.build());
//
//        // bind the rule to the agenda via the item
//        id = null;
//        String agendaId = agenda.getId();
//        AgendaItemDefinition.Builder itemBldr = AgendaItemDefinition.Builder.create(id, agendaId);
//        itemBldr.setRuleId(ruleId);
//        AgendaItemDefinition item = this.ruleManagementService.createAgendaItem(itemBldr.build());
//        // now go back and mark the agenda with the item and make it active
//        agendaBldr = AgendaDefinition.Builder.create(agenda);
//        agendaBldr.setActive(true);
//        agendaBldr.setFirstItemId(item.getId());
//        this.ruleManagementService.updateAgenda(agendaBldr.build());
//        agenda = this.ruleManagementService.getAgenda(agenda.getId());
//
//        // create the cluset
//        CluInfo courseClu2 = this.getClu("COURSE2");
//        CluInfo courseClu3 = this.getClu("COURSE3");
//        CluInfo courseClu4 = this.getClu("COURSE4");
//        List<String> versionIndIds = this.getVersionIndIds(anchorClu, courseClu2, courseClu3);
//        CluSetInfo cluSet = createCourseSet("Courses 1, 2, & 3", versionIndIds);
//
//        propId = null; // should be null until assigned by service
//        String propTypeCode = PropositionType.SIMPLE.getCode();
//        ruleId = rule.getId();
//        typeId = nOfCoursesPropositionType.getId();
//        List<PropositionParameter.Builder> parameters = new ArrayList<PropositionParameter.Builder>();
//
//        // do the term parameter first
//        //        nOfCoursesTermType.getId();
//        KrmsTypeDefinition type = nOfCoursesTermType;
//        nlTemplate = this.getRuleEditUsageNaturalLanguageTemplate(type.getId(), languageCode);
//        id = null; // should be set by service
//        propId = null; // should also be set by the service when the create on the proposition happens
//        String value = null;
//        String parameterType = serviceName2PropositionParameterType(type.getServiceName()).getCode();
//        Integer sequenceNumber = 1;
//        PropositionParameter.Builder propParamBldr = PropositionParameter.Builder.create(id, propId, value, parameterType, sequenceNumber);
//
//        // now the parameter to the parameter! (actually the term parameter to the proposition parameter that is a term!
//        id = null; // set by service
//        TermSpecificationDefinition.Builder termSpecBldr = TermSpecificationDefinition.Builder.create(termSpec);
//        List<TermParameterDefinition.Builder> termParameters = new ArrayList<TermParameterDefinition.Builder>();
//        description = termSpec.getName() + " for " + cluSet.getId();
//        TermDefinition.Builder termBldr = TermDefinition.Builder.create(id, termSpecBldr, termParameters);
//        termBldr.setDescription(description);
//
//        id = null; //set by service
//        typeId = cluSetIdType.getId();
//        nlTemplate = this.getRuleEditUsageNaturalLanguageTemplate(typeId, languageCode);
//        name = nlTemplate.getTemplate(); // this should be CourseSetId 
//        value = cluSet.getId(); // this was created when the cluSet was built
//        TermParameterDefinition.Builder termParamBldr = TermParameterDefinition.Builder.create(id, typeId, name, value);
//        termParameters.add(termParamBldr);
//        propParamBldr.setTermValue(termBldr.build());
//        parameters.add(propParamBldr);
//
//        // do the 2nd parameter
//        type = constantValueN;
//        nlTemplate = this.getRuleEditUsageNaturalLanguageTemplate(type.getId(), languageCode);
//        id = null; // should be set by service
//        propId = null; // should also be set by the service when the create on the proposition happens
//        value = nlTemplate.getTemplate(); // this should be default of 1 but use can change to be 2 or 3 or...
//        parameterType = serviceName2PropositionParameterType(type.getServiceName()).getCode();
//        sequenceNumber = 2;
//        propParamBldr = PropositionParameter.Builder.create(id, propId, value, parameterType, sequenceNumber);
//        parameters.add(propParamBldr);
//
//        // do the 3rd parameter
//        type = lessThanEqualOperatorType;
//        nlTemplate = this.getRuleEditUsageNaturalLanguageTemplate(type.getId(), languageCode);
//        id = null; // should be set by service
//        propId = null; // should also be set by the service when the create on the proposition happens
//        value = nlTemplate.getTemplate(); // this should be default of <=..
//        parameterType = serviceName2PropositionParameterType(type.getServiceName()).getCode();
//        sequenceNumber = 3;
//        propParamBldr = PropositionParameter.Builder.create(id, propId, value, parameterType, sequenceNumber);
//        parameters.add(propParamBldr);
//
//        PropositionDefinition.Builder propBldr = PropositionDefinition.Builder.create(propId, propTypeCode, ruleId, typeId, parameters);
//
//        String enDescription = this.ruleManagementService.translateNaturalLanguageForProposition(getTypeDescriptionUsage ().getId(), propBldr.build(), languageCode);
//        System.out.println ("description=" + enDescription);
//        
////        String enPreview = this.ruleManagementService.translateNaturalLanguageForProposition(getPreviewUsage ().getId(), propBldr.build(), languageCode);
////        System.out.println (enPreview);
//     
//        
//        PropositionDefinition propositon = this.ruleManagementService.createProposition(propBldr.build());


    }

    ////
    ////
    ////
    ////
    ////
    private PropositionParameterType serviceName2PropositionParameterType(String serviceTypeName) {
        if (serviceTypeName.equals(KrmsTypeRepositoryService.TERM_PROPOSITION_PARAMETER_SERVICE_NAME)) {
            return PropositionParameterType.TERM;
        }
        if (serviceTypeName.equals(KrmsTypeRepositoryService.CONSTANT_VALUE_PROPOSITION_PARAMETER_SERVICE_NAME)) {
            return PropositionParameterType.CONSTANT;
        }
        if (serviceTypeName.equals(KrmsTypeRepositoryService.OPERATOR_PROPOSITION_PARAMETER_SERVICE_NAME)) {
            return PropositionParameterType.OPERATOR;
        }
        if (serviceTypeName.equals(KrmsTypeRepositoryService.FUNCTION_PROPOSITION_PARAMETER_SERVICE_NAME)) {
            return PropositionParameterType.FUNCTION;
        }
        throw new IllegalArgumentException(serviceTypeName);
    }

    public CluSetInfo createCourseSet(String name, List<String> versionIndIds) {
        CluSetInfo cluSetInfo = new CluSetInfo();
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

    private String simulateUserChoosingMainTypeId() {
        KrmsTypeDefinition type = this.krmsTypeRepositoryService.getTypeByName(KsKrmsConstants.NAMESPACE_CODE, KsKrmsConstants.AGENDA_TYPE_COURSE);
        assertNotNull(type);
        assertEquals(KsKrmsConstants.AGENDA_TYPE_COURSE, type.getName());
        assertEquals(KsKrmsConstants.NAMESPACE_CODE, type.getNamespace());
        return type.getId();
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

    private String getComponentId(NaturalLanguageTemplate template) {
        return template.getAttributes().get(KsKrmsConstants.ATTRIBUTE_COMPONENT_ID);
    }

    private String getComponentBuilderClass(NaturalLanguageTemplate template) {
        return template.getAttributes().get(KsKrmsConstants.ATTRIBUTE_COMPONENT_BUILDER_CLASS);
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
