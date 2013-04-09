/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kuali.rice.krms.impl.repository.mock;

import java.util.ArrayList;
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
import org.kuali.rice.krms.api.repository.RuleManagementService;
import org.kuali.rice.krms.api.repository.context.ContextDefinition;
import org.kuali.rice.krms.api.repository.language.NaturalLanguageTemplate;
import org.kuali.rice.krms.api.repository.language.NaturalLanguageUsage;
import org.kuali.rice.krms.api.repository.proposition.PropositionDefinition;
import org.kuali.rice.krms.api.repository.proposition.PropositionType;
import org.kuali.rice.krms.api.repository.term.TermRepositoryService;
import org.kuali.rice.krms.api.repository.term.TermResolverDefinition;
import org.kuali.rice.krms.api.repository.term.TermSpecificationDefinition;
import org.kuali.rice.krms.api.repository.type.KrmsTypeDefinition;
import org.kuali.rice.krms.api.repository.type.KrmsTypeRepositoryService;
import org.kuali.student.krms.naturallanguage.util.KsKrmsConstants;

/**
 *
 * @author nwright
 */
public class RuleManagementServiceMockImplTest {

    public RuleManagementServiceMockImplTest() {
    }
    private KrmsTypeRepositoryService krmsTypeRepositoryService = null;
    private RuleManagementService ruleManagementService = null;
    private TermRepositoryService termRepositoryService = null;

    public KrmsTypeRepositoryService getKrmsTypeRepositoryService() {
        if (this.krmsTypeRepositoryService == null) {
            this.krmsTypeRepositoryService = new KrmsTypeRepositoryServiceMockImpl();
        }
        return krmsTypeRepositoryService;
    }

    public void setKrmsTypeRepositoryService(KrmsTypeRepositoryService krmsTypeRepositoryService) {
        this.krmsTypeRepositoryService = krmsTypeRepositoryService;
    }

    public RuleManagementService getRuleManagementService() {
        if (this.ruleManagementService == null) {
            this.ruleManagementService = new RuleManagementServiceMockImpl();
        }
        return ruleManagementService;
    }

    public void setRuleManagementService(RuleManagementService ruleManagementService) {
        this.ruleManagementService = ruleManagementService;
    }

    public TermRepositoryService getTermRepositoryService() {
        if (this.termRepositoryService == null) {
            this.termRepositoryService = new TermRepositoryServiceMockImpl();
        }
        return termRepositoryService;
    }

    public void setTermRepositoryService(TermRepositoryService termRepositoryService) {
        this.termRepositoryService = termRepositoryService;
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        KrmsConfigurationLoader loader = new KrmsConfigurationLoader();
        loader.setKrmsTypeRepositoryService(this.getKrmsTypeRepositoryService());
        loader.setRuleManagementService(this.getRuleManagementService());
        loader.setTermRepositoryService(this.getTermRepositoryService());
        loader.loadConfiguration();
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
        types = this.getKrmsTypeRepositoryService().findAllContextTypes();
        expected = new LinkedHashSet<String>();
        expected.add(KsKrmsConstants.CONTEXT_TYPE_COURSE);
        expected.add(KsKrmsConstants.CONTEXT_TYPE_PROGRAM);
        expected.add(KsKrmsConstants.CONTEXT_TYPE_COURSE_OFFERING);
        this.checkTypeNamesAnyOrder(expected, types);

        // Typically the user does not actually select the context but it is
        // hardwired into the program, so the "context" is taken from where 
        // we are within the application, i.e. are we on the course requisites screen?
        System.out.println("Please choose which context of rules that you want to work on:");
        for (KrmsTypeDefinition type : types) {
            System.out.println("     " + this.getScreenDescription(type.getId(), languageCode));
        }
        System.out.print("==> Choose: ");
        String selectedId = this.simulateUserChoosingContextTypeId();
        System.out.println(selectedId);
        KrmsTypeDefinition selectedContextType = this.getKrmsTypeRepositoryService().getTypeById(selectedId);
        String description = this.getScreenDescription(selectedContextType.getId(), languageCode);
        System.out.println("Editing Rules for Context: " + description);

        // find/create the context corresponding to this context type.
        // TODO: Confirm convention that we are creating a single context for all rules of that particular context type, right?
        ContextDefinition.Builder bldr = ContextDefinition.Builder.create(KsKrmsConstants.NAMESPACE_CODE, selectedContextType.getName());
        // TODO: find out what this typeId is really supposed to be.
        // I thought it would be the selected context type but in the existing configured data it has a value T1004?!? 
        bldr.setTypeId(selectedContextType.getId());
        bldr.setDescription(description); // set the description to be the template description of the type
        ContextDefinition context = this.getRuleManagementService().findCreateContext(bldr.build());
        assertNotNull(context);
        assertEquals(context.getName(), selectedContextType.getName());
        assertEquals(context.getNamespace(), selectedContextType.getNamespace());
        // TODO: worry that this context could have been created with a different type and/or description
        assertEquals(context.getTypeId(), selectedContextType.getId());
        assertEquals(context.getDescription(), description);

        // Get all the agenda types for this course context type
        types = this.getKrmsTypeRepositoryService().findAgendaTypesForContextType(selectedContextType.getId());
        expected = new LinkedHashSet<String>();
        expected.add(KsKrmsConstants.AGENDA_TYPE_COURSE_ENROLLMENTELIGIBILITY);
        expected.add(KsKrmsConstants.AGENDA_TYPE_COURSE_CREDITCONSTRAINTS);
        this.checkTypeNamesOrdered(expected, types);

        String title = "Please choose which type of rule you want to work on:";
        int defaultIndex = 0;
        KrmsTypeDefinition courseEligAgendaType = this.simulateUserChoosingType(title, types, languageCode, defaultIndex);

        // Get all the agenda types for the main agenda type
        // Right now we don't do this we just have rule types so this should return an empty list
        types = this.getKrmsTypeRepositoryService().findAgendaTypesForAgendaType(courseEligAgendaType.getId());
        expected = new LinkedHashSet<String>();
        this.checkTypeNamesOrdered(expected, types);

        // Get all the RULE types for the main agenda type
        types = this.getKrmsTypeRepositoryService().findRuleTypesForAgendaType(courseEligAgendaType.getId());
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
        types = this.getKrmsTypeRepositoryService().findPropositionTypesForRuleType(eligPrereqRuleType.getId());
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
        types = this.getKrmsTypeRepositoryService().findPropositionParameterTypesForPropositionType(nOfCoursesPropositionType.getId());
        expected = new LinkedHashSet<String>();
        expected.add(KsKrmsConstants.PROPOSITION_PARAMETER_TYPE_TERM_NUMBER_OF_COMPLETED_COURSES);
        expected.add(KsKrmsConstants.PROPOSITION_PARAMETER_TYPE_OPERATOR_LESS_THAN_OR_EQUAL_TO);
        expected.add(KsKrmsConstants.PROPOSITION_PARAMETER_TYPE_CONSTANT_VALUE_N);
        this.checkTypeNamesOrdered(expected, types);

        // make sure we have descriptions for all of the parameters
        this.displayScreenDescriptions(types, languageCode);
        // now check the krad implementations
        // check the term
        KrmsTypeDefinition nOfCoursesTermType = types.get(0);
        nlTemplate = this.getRuleEditUsageNaturalLanguageTemplate(nOfCoursesTermType.getId(), languageCode);
        assertNotNull(nlTemplate);
        String termName = nlTemplate.getTemplate();
        assertEquals("NumberOfCompletedCourses", termName);
        assertNull(this.getComponentId(nlTemplate));
        
        // operator
        KrmsTypeDefinition lessThanEqualOperatorType = types.get(1);
        nlTemplate = this.getRuleEditUsageNaturalLanguageTemplate(lessThanEqualOperatorType.getId(), languageCode);
        assertNotNull(nlTemplate);
        String operator = nlTemplate.getTemplate();
        assertEquals("<=", operator);
        assertNull(this.getComponentId(nlTemplate));
        
        // constant value N
        KrmsTypeDefinition constantValueN = types.get(2);
        nlTemplate = this.getRuleEditUsageNaturalLanguageTemplate(constantValueN.getId(), languageCode);
        assertNotNull(nlTemplate);
        String constantValue = nlTemplate.getTemplate();
        assertEquals("1", constantValue);
        String constantValueComponentId = this.getComponentId(nlTemplate);
        assertEquals("KRMS-NumberOfCourses-ConstantValue", constantValueComponentId);
        
        
        // Get all the term parameter types for the TERM proposition parameter type
        types = this.getKrmsTypeRepositoryService().findTermParameterTypesForTermPropositionParameterType(nOfCoursesTermType.getId());
        expected = new LinkedHashSet<String>();
        expected.add(KsKrmsConstants.TERM_PARAMETER_TYPE_COURSE_CLUSET_ID);
        this.checkTypeNamesOrdered(expected, types);
        this.displayScreenDescriptions(types, languageCode);
        KrmsTypeDefinition cluSetIdType = types.get(0);
        nlTemplate = this.getRuleEditUsageNaturalLanguageTemplate(cluSetIdType.getId(), languageCode);
        assertNotNull(nlTemplate);
        String cluSetIdComponentId = this.getComponentId(nlTemplate);
        assertEquals("KRMS-MultiCourse-Section", cluSetIdComponentId);
        String componentBuilderClass = this.getComponentBuilderClass(nlTemplate);
        assertEquals("org.kuali.student.enrollment.class1.krms.builder.MultiCourseComponentBuilder", componentBuilderClass);

//      TermSpec 
        TermSpecificationDefinition termSpec =
                this.getTermRepositoryService().getTermSpecificationByNameAndNamespace(termName,
                KsKrmsConstants.NAMESPACE_CODE);
        assertNotNull (termSpec);
        
        TermResolverDefinition termResolver =
                this.getTermRepositoryService().getTermResolverByNameAndNamespace(termName,
                KsKrmsConstants.NAMESPACE_CODE);
        assertNotNull (termResolver);
        
        

        PropositionDefinition.Builder prop = PropositionDefinition.Builder.create(null, PropositionType.SIMPLE.getCode(), null, null, null);
        prop.setId(null);
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
        usage = this.getRuleManagementService().getNaturalLanguageUsageByNameAndNamespace(name, namespace);
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

    private String simulateUserChoosingContextTypeId() {
        KrmsTypeDefinition type = this.getKrmsTypeRepositoryService().getTypeByName(KsKrmsConstants.NAMESPACE_CODE, KsKrmsConstants.CONTEXT_TYPE_COURSE);
        assertNotNull(type);
        assertEquals(KsKrmsConstants.CONTEXT_TYPE_COURSE, type.getName());
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
                this.getRuleManagementService().findNaturalLanguageTemplateByLanguageCodeTypeIdAndNluId(languageCode,
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
                this.getRuleManagementService().findNaturalLanguageTemplateByLanguageCodeTypeIdAndNluId(languageCode,
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
}
