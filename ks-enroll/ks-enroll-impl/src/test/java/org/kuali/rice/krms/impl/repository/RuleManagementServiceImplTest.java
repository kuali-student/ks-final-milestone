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
import org.junit.Test;
import org.junit.Ignore;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.krms.api.repository.LogicalOperator;
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
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.*;
import org.kuali.student.r2.common.util.constants.LuiServiceConstants;
import org.kuali.student.r2.core.constants.KSKRMSServiceConstants;
import org.kuali.student.r2.core.process.krms.KSKRMSTestCase;
import org.kuali.student.r2.core.versionmanagement.dto.VersionDisplayInfo;
import org.kuali.student.r2.lum.clu.dto.CluInfo;
import org.kuali.student.r2.lum.clu.dto.CluSetInfo;
import org.kuali.student.r2.lum.clu.service.CluService;
import org.kuali.student.r2.lum.lu.service.impl.CluDataLoader;
import org.kuali.student.r2.lum.lu.service.impl.CluServiceMockImpl;
import org.kuali.student.r2.lum.util.constants.CluServiceConstants;
import java.util.Arrays;
import org.kuali.rice.krms.api.repository.reference.ReferenceObjectBinding;
import org.kuali.rice.krms.impl.util.KrmsRuleManagementCopyMethods;
import org.kuali.rice.krms.impl.util.KrmsRuleManagementCopyMethodsImpl;

/**
 *
 * @author nwright
 */
// TODO: KSENROLL-7265  remove ignore per larry's request that all @Ignore's have an associated jira 
@Ignore
public class RuleManagementServiceImplTest extends KSKRMSTestCase {

    private KrmsTypeRepositoryService krmsTypeRepositoryService = null;
    private RuleManagementService ruleManagementService = null;
    private TermRepositoryService termRepositoryService = null;
    private CluService cluService = null;
    private KrmsRuleManagementCopyMethods krmsRuleManagementCopyMethods = null;

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
        KrmsRuleManagementCopyMethodsImpl copyImpl = new KrmsRuleManagementCopyMethodsImpl();
//        copyImpl.setKrmsTypeRepositoryService(krmsTypeRepositoryService);
        copyImpl.setRuleManagementService(ruleManagementService);
        this.krmsRuleManagementCopyMethods = copyImpl;
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
    public void testCreateSimpleProposition() {
        this.createCheckBasicAgendaFor1OfCluSet23();
    }

    @Test
    public void testCopyingSimpleProposition() {
        String namespace = KSKRMSServiceConstants.NAMESPACE_CODE;
        String fromReferenceDiscriminatorType = CluServiceConstants.CREDIT_COURSE_LU_TYPE_KEY;
        String fromReferenceObjectId = "COURSE1";
        String toReferenceDiscriminatorType = LuiServiceConstants.COURSE_OFFERING_TYPE_KEY;
        String toReferenceObjectId = "COURSEOFFERING1";

        // delete any rules if there are any
        int bindingsDeleted = this.krmsRuleManagementCopyMethods.deleteReferenceObjectBindingsCascade(fromReferenceDiscriminatorType, fromReferenceObjectId);
        System.out.println("number of existing bindings deleted for " + fromReferenceObjectId + " count=" + bindingsDeleted);
        bindingsDeleted = this.krmsRuleManagementCopyMethods.deleteReferenceObjectBindingsCascade(toReferenceDiscriminatorType, toReferenceObjectId);
        System.out.println("number of existing bindings deleted for " + toReferenceObjectId + " count=" + bindingsDeleted);


        AgendaDefinition agenda = this.createCheckBasicAgendaFor1OfCluSet23();
        String krmsDiscriminatorType = KSKRMSServiceConstants.KRMS_DISCRIMINATOR_TYPE_AGENDA;
        String krmsObjectId = agenda.getId();

        ReferenceObjectBinding.Builder bindingBldr = ReferenceObjectBinding.Builder.create(krmsDiscriminatorType,
                krmsObjectId,
                namespace,
                fromReferenceDiscriminatorType,
                fromReferenceObjectId);
        ReferenceObjectBinding binding = this.ruleManagementService.createReferenceObjectBinding(bindingBldr.build());

        List<String> optionKeys = new ArrayList<String>();
        List<ReferenceObjectBinding> list = null;
        try {
            list = this.krmsRuleManagementCopyMethods.deepCopyReferenceObjectBindingsFromTo(
                    fromReferenceDiscriminatorType,
                    fromReferenceObjectId,
                    toReferenceDiscriminatorType,
                    toReferenceObjectId,
                    optionKeys);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        assertNotNull(list);
        assertEquals(1, list.size());
        ReferenceObjectBinding copy = list.get(0);
        assertEquals(toReferenceObjectId, copy.getReferenceObjectId());
        assertEquals(LuiServiceConstants.COURSE_OFFERING_TYPE_KEY, copy.getReferenceDiscriminatorType());
        checkCopy(binding, list.get(0));


        // delete any rules that were created
        bindingsDeleted = this.krmsRuleManagementCopyMethods.deleteReferenceObjectBindingsCascade(fromReferenceDiscriminatorType,
                fromReferenceObjectId);
        System.out.println("number of existing bindings deleted for " + fromReferenceObjectId + " count=" + bindingsDeleted);
        bindingsDeleted = this.krmsRuleManagementCopyMethods.deleteReferenceObjectBindingsCascade(toReferenceDiscriminatorType,
                toReferenceObjectId);
        System.out.println("number of existing bindings deleted for " + toReferenceObjectId + " count=" + bindingsDeleted);
    }

    @Test
    public void testCopyingCompoundProposition() {
        String namespace = KSKRMSServiceConstants.NAMESPACE_CODE;
        // TODO: KSENROLL-7291 convert the discriminator type to use the ref object uri instead of the lu type type
//        String fromReferenceDiscriminatorType = CourseServiceConstants.COURSE_NAMESPACE_URI;
        String fromReferenceDiscriminatorType = CluServiceConstants.CREDIT_COURSE_LU_TYPE_KEY;
        String fromReferenceObjectId = "COURSE2";
        // TODO: KSENROLL-7291 convert the discriminator type to use the ref object uri instead of the lu type type
//        String toReferenceDiscriminatorType = CourseOfferingServiceConstants.REF_OBJECT_URI_COURSE_OFFERING,
        String toReferenceDiscriminatorType = LuiServiceConstants.COURSE_OFFERING_TYPE_KEY;
        String toReferenceObjectId = "COURSEOFFERING2";

        // delete any rules if there are any
        int bindingsDeleted = this.krmsRuleManagementCopyMethods.deleteReferenceObjectBindingsCascade(fromReferenceDiscriminatorType,
                fromReferenceObjectId);
        System.out.println("number of existing bindings deleted for " + fromReferenceObjectId + " count=" + bindingsDeleted);
        bindingsDeleted = this.krmsRuleManagementCopyMethods.deleteReferenceObjectBindingsCascade(toReferenceDiscriminatorType,
                toReferenceObjectId);
        System.out.println("number of existing bindings deleted for " + toReferenceObjectId + " count=" + bindingsDeleted);


        ContextDefinition context = this.findCreateContext();
        AgendaDefinition agenda = createCheckEmptyAgenda(context);
        agenda = updateCheckAgendaFirstItemAddingEmptyRule(agenda);

        PropositionDefinition.Builder propBldr1 = this.constructFreeFormTextPropositionBulider("My first Text Value");
        CluSetInfo cluSet = this.findCreateCluSet23();
        PropositionDefinition.Builder propBldr2 = createNOfCluSetPropositionBuilder(1, cluSet);
        cluSet = this.findCreateCluSet456();
        PropositionDefinition.Builder propBldr3 = createNOfCluSetPropositionBuilder(2, cluSet);

        PropositionDefinition.Builder andPropBldr = this.makeAndCompoundProposition(propBldr2, propBldr3);
        PropositionDefinition.Builder orPropBldr = this.makeOrCompoundProposition(propBldr1, andPropBldr);
        agenda = updateCheckFirstItemSettingPropositionOnRule(agenda, orPropBldr);

        String krmsDiscriminatorType = KSKRMSServiceConstants.KRMS_DISCRIMINATOR_TYPE_AGENDA;
        String krmsObjectId = agenda.getId();

        ReferenceObjectBinding.Builder bindingBldr = ReferenceObjectBinding.Builder.create(krmsDiscriminatorType,
                krmsObjectId,
                namespace,
                fromReferenceDiscriminatorType,
                fromReferenceObjectId);
        ReferenceObjectBinding binding = this.ruleManagementService.createReferenceObjectBinding(bindingBldr.build());

        List<String> optionKeys = new ArrayList<String>();
        List<ReferenceObjectBinding> list = null;
        try {
            list = this.krmsRuleManagementCopyMethods.deepCopyReferenceObjectBindingsFromTo(
                    fromReferenceDiscriminatorType,
                    fromReferenceObjectId,
                    toReferenceDiscriminatorType,
                    toReferenceObjectId,
                    optionKeys);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        assertNotNull(list);
        assertEquals(1, list.size());
        ReferenceObjectBinding copy = list.get(0);
        assertEquals(toReferenceObjectId, copy.getReferenceObjectId());
        assertEquals(LuiServiceConstants.COURSE_OFFERING_TYPE_KEY, copy.getReferenceDiscriminatorType());
        checkCopy(binding, list.get(0));

        // delete any rules that were created
        bindingsDeleted = this.krmsRuleManagementCopyMethods.deleteReferenceObjectBindingsCascade(fromReferenceDiscriminatorType,
                fromReferenceObjectId);
        System.out.println("number of existing bindings deleted for " + fromReferenceObjectId + " count=" + bindingsDeleted);
        bindingsDeleted = this.krmsRuleManagementCopyMethods.deleteReferenceObjectBindingsCascade(toReferenceDiscriminatorType,
                toReferenceObjectId);
        System.out.println("number of existing bindings deleted for " + toReferenceObjectId + " count=" + bindingsDeleted);

    }

    private void checkCopy(ReferenceObjectBinding orig, ReferenceObjectBinding copy) {
        assertNotEquals(orig.getId(), copy.getId());
        assertEquals(orig.getKrmsDiscriminatorType(), copy.getKrmsDiscriminatorType());
        assertNotEquals(orig.getKrmsObjectId(), copy.getKrmsObjectId());
        AgendaDefinition origAgenda = this.ruleManagementService.getAgenda(orig.getKrmsObjectId());
        AgendaDefinition copyAgenda = this.ruleManagementService.getAgenda(copy.getKrmsObjectId());
        checkCopy(origAgenda, copyAgenda);
    }

    private void checkCopy(AgendaDefinition orig, AgendaDefinition copy) {
        if (orig == null && copy == null) {
            return;
        }
        assertNotEquals(orig.getId(), copy.getId());
        assertEquals(orig.getContextId(), copy.getContextId());
        // names have to be different but shouldn't they be similar?
        assertNotEquals(orig.getName(), copy.getName());
        assertEquals(orig.getTypeId(), copy.getTypeId());
        assertEquals(orig.isActive(), copy.isActive());
        assertEquals(orig.getAttributes(), copy.getAttributes());
        assertNotEquals(orig.getFirstItemId(), copy.getFirstItemId());
        AgendaItemDefinition origItem = this.ruleManagementService.getAgendaItem(orig.getFirstItemId());
        AgendaItemDefinition copyItem = this.ruleManagementService.getAgendaItem(copy.getFirstItemId());
        checkCopy(origItem, copyItem);
    }

    private void checkCopy(AgendaItemDefinition orig, AgendaItemDefinition copy) {
        if (orig == null && copy == null) {
            return;
        }
        assertNotEquals(orig.getId(), copy.getId());
        assertNotEquals(orig.getAgendaId(), copy.getAgendaId());
        checkCopy(orig.getAlwaysId(), copy.getAlwaysId());
        checkCopy(orig.getAlways(), copy.getAlways());
        checkCopy(orig.getRuleId(), copy.getRuleId());
        checkCopy(orig.getRule(), copy.getRule());
        checkCopy(orig.getSubAgendaId(), copy.getSubAgendaId());
        checkCopy(orig.getSubAgenda(), copy.getSubAgenda());
        checkCopy(orig.getWhenFalseId(), copy.getWhenFalseId());
        checkCopy(orig.getWhenFalse(), copy.getWhenFalse());
        checkCopy(orig.getWhenTrueId(), copy.getWhenTrueId());
        checkCopy(orig.getWhenTrue(), copy.getWhenTrue());
    }

    private void checkCopy(String orig, String copy) {
        if (orig == null && copy == null) {
            return;
        }
        assertNotEquals(orig, copy);
    }

    private void checkCopy(RuleDefinition orig, RuleDefinition copy) {
        if (orig == null && copy == null) {
            return;
        }
        assertNotEquals(orig.getId(), copy.getId());
        assertEquals(orig.getAttributes(), copy.getAttributes());
        assertEquals(orig.getActions(), copy.getActions());
        assertEquals(orig.getDescription(), copy.getDescription());
        // names have to be different but shouldn't they be similar?
        assertNotEquals(orig.getName(), copy.getName());
        assertEquals(orig.getNamespace(), copy.getNamespace());
        if (orig.getPropId() != null) {
            checkCopy(orig.getPropId(), copy.getPropId());
        }
        checkCopy(orig.getProposition(), copy.getProposition());
        assertEquals(orig.getTypeId(), copy.getTypeId());
        assertEquals(orig.isActive(), copy.isActive());
    }

    private void checkCopy(PropositionDefinition orig, PropositionDefinition copy) {
        if (orig == null && copy == null) {
            return;
        }
        assertNotEquals(orig.getId(), copy.getId());
        assertNotEquals(orig.getRuleId(), copy.getRuleId());
        assertEquals(orig.getTypeId(), copy.getTypeId());
        assertEquals(orig.getDescription(), copy.getDescription());
        assertEquals(orig.getPropositionTypeCode(), copy.getPropositionTypeCode());
        assertEquals(orig.getCompoundOpCode(), copy.getCompoundOpCode());
        assertEquals(orig.getCompoundSequenceNumber(), copy.getCompoundSequenceNumber());
        if (orig.getCompoundComponents() != null) {
            assertEquals(orig.getCompoundComponents().size(), copy.getCompoundComponents().size());
            for (int i = 0; i < orig.getCompoundComponents().size(); i++) {
                PropositionDefinition origCompoundComponent = orig.getCompoundComponents().get(i);
                PropositionDefinition copyCompoundComponent = copy.getCompoundComponents().get(i);
                checkCopy(origCompoundComponent, copyCompoundComponent);
            }
        }
        if (orig.getParameters() != null) {
            assertEquals(orig.getParameters().size(), copy.getParameters().size());
            for (int i = 0; i < orig.getParameters().size(); i++) {
                PropositionParameter origParam = orig.getParameters().get(i);
                PropositionParameter copyParam = copy.getParameters().get(i);
                checkCopy(origParam, copyParam);
            }
        }
    }

    private void checkCopy(PropositionParameter orig, PropositionParameter copy) {
        if (orig == null && copy == null) {
            return;
        }
        assertNotEquals(orig.getId(), copy.getId());
        assertEquals(orig.getParameterType(), copy.getParameterType());
        assertNotEquals(orig.getPropId(), copy.getPropId());
        assertEquals(orig.getSequenceNumber(), copy.getSequenceNumber());
        if (orig.getParameterType().equals(PropositionParameterType.TERM.getCode())) {
            checkCopy(orig.getTermValue(), copy.getTermValue());
        } else {
            assertEquals(orig.getValue(), copy.getValue());
            assertNull (copy.getTermValue());
        }
    }

    private void checkCopy(TermDefinition orig, TermDefinition copy) {
        if (orig == null && copy == null) {
            return;
        }
        assertNotEquals(orig.getId(), copy.getId());
        assertEquals(orig.getDescription(), copy.getDescription());
        checkCopy(orig.getSpecification(), copy.getSpecification());
        if (orig.getParameters() != null) {
            assertEquals(orig.getParameters().size(), copy.getParameters().size());
            // Might have order the lists by Name to make sure they compare properly
            for (int i = 0; i < orig.getParameters().size(); i++) {
                TermParameterDefinition origParam = orig.getParameters().get(i);
                TermParameterDefinition copyParam = copy.getParameters().get(i);
                checkCopy(origParam, copyParam);
            }
        }
    }

    private void checkCopy(TermSpecificationDefinition orig, TermSpecificationDefinition copy) {
        if (orig == null && copy == null) {
            return;
        }
        // term spec should NOT be copied so the ids should be the same!
        assertEquals(orig.getId(), copy.getId());
        assertEquals(orig.getDescription(), copy.getDescription());
        assertEquals(orig.getName(), copy.getName());
        assertEquals(orig.getNamespace(), copy.getNamespace());
        assertEquals(orig.getDescription(), copy.getDescription());
    }

    private void checkCopy(TermParameterDefinition orig, TermParameterDefinition copy) {
        if (orig == null && copy == null) {
            return;
        }
        // term spec should NOT be copied so the ids should be the same!
        assertNotEquals(orig.getId(), copy.getId());
        assertNotEquals(orig.getTermId(), copy.getTermId());
        assertEquals(orig.getName(), copy.getName());
        assertEquals(orig.getValue(), copy.getValue());
    }

    @Test
    public void testBasicCreateCompoundProposition() {
        ContextDefinition context = this.findCreateContext();
        AgendaDefinition agenda = createCheckEmptyAgenda(context);
        agenda = updateCheckAgendaFirstItemAddingEmptyRule(agenda);

        PropositionDefinition.Builder propBldr1 = this.constructFreeFormTextPropositionBulider("My first Text Value");
        CluSetInfo cluSet = this.findCreateCluSet23();
        PropositionDefinition.Builder propBldr2 = createNOfCluSetPropositionBuilder(1, cluSet);
        cluSet = this.findCreateCluSet456();
        PropositionDefinition.Builder propBldr3 = createNOfCluSetPropositionBuilder(2, cluSet);

        PropositionDefinition.Builder andPropBldr = this.makeAndCompoundProposition(propBldr2, propBldr3);
        PropositionDefinition.Builder orPropBldr = this.makeOrCompoundProposition(propBldr1, andPropBldr);
        agenda = updateCheckFirstItemSettingPropositionOnRule(agenda, orPropBldr);
    }

    @Test
    public void testChangeFromSimple2CompoundProposition() {
        ContextDefinition context = this.findCreateContext();
        AgendaDefinition agenda = createCheckEmptyAgenda(context);
        agenda = updateCheckAgendaFirstItemAddingEmptyRule(agenda);

        PropositionDefinition.Builder propBldr1 = this.constructFreeFormTextPropositionBulider("My first Text Value");
        CluSetInfo cluSet = this.findCreateCluSet23();
        PropositionDefinition.Builder propBldr2 = createNOfCluSetPropositionBuilder(1, cluSet);

        cluSet = this.findCreateCluSet456();
        PropositionDefinition.Builder propBldr3 = createNOfCluSetPropositionBuilder(2, cluSet);

        agenda = updateCheckFirstItemSettingPropositionOnRule(agenda, propBldr3);

        AgendaItemDefinition firstItem = this.ruleManagementService.getAgendaItem(agenda.getFirstItemId());
        PropositionDefinition prop = firstItem.getRule().getProposition();
        propBldr3 = PropositionDefinition.Builder.create(prop);
        PropositionDefinition.Builder andPropBldr = this.makeAndCompoundProposition(propBldr2, propBldr3);
        PropositionDefinition.Builder orPropBldr = this.makeOrCompoundProposition(propBldr1, andPropBldr);
        agenda = updateCheckFirstItemSettingPropositionOnRule(agenda, orPropBldr);
    }

    private PropositionDefinition.Builder makeAndCompoundProposition(PropositionDefinition.Builder... childPropBldrs) {
        String propId = null;
        String propTypeCode = PropositionType.COMPOUND.getCode();
        String ruleId = null;
        KrmsTypeDefinition type = this.krmsTypeRepositoryService.getTypeByName(KSKRMSServiceConstants.NAMESPACE_CODE,
                KSKRMSServiceConstants.PROPOSITION_TYPE_COMPOUND_AND);
        String typeId = type.getId();
        List<PropositionParameter.Builder> parameters = new ArrayList<PropositionParameter.Builder>();
        PropositionDefinition.Builder mainBldr = PropositionDefinition.Builder.create(propId, propTypeCode, ruleId, typeId, parameters);
        mainBldr.setCompoundOpCode(LogicalOperator.AND.getCode());
        List<PropositionDefinition.Builder> childList = Arrays.asList(childPropBldrs);
//        System.out.println ("order for AND is:");
//        for (PropositionDefinition.Builder childBldr : childList) {
//            System.out.println ("order is " + childBldr);
//        }
        mainBldr.setCompoundComponents(childList);
        return mainBldr;
    }

    private PropositionDefinition.Builder makeOrCompoundProposition(PropositionDefinition.Builder... childPropBldrs) {
        String propId = null;
        String propTypeCode = PropositionType.COMPOUND.getCode();
        String ruleId = null;
        KrmsTypeDefinition type = this.krmsTypeRepositoryService.getTypeByName(KSKRMSServiceConstants.NAMESPACE_CODE,
                KSKRMSServiceConstants.PROPOSITION_TYPE_COMPOUND_OR);
        String typeId = type.getId();
        List<PropositionParameter.Builder> parameters = new ArrayList<PropositionParameter.Builder>();
        PropositionDefinition.Builder mainBldr = PropositionDefinition.Builder.create(propId, propTypeCode, ruleId, typeId, parameters);
        mainBldr.setCompoundOpCode(LogicalOperator.OR.getCode());
        List<PropositionDefinition.Builder> childList = Arrays.asList(childPropBldrs);
//        System.out.println ("order for OR is:");
//        for (PropositionDefinition.Builder childBldr : childList) {
//            System.out.println ("order is " + childBldr);
//        }
        mainBldr.setCompoundComponents(childList);
        return mainBldr;
    }

    @Test
    public void testUpdateChangingPropositionConstantValuefrom1To2() {
        AgendaDefinition agenda = createCheckBasicAgendaFor1OfCluSet23();

        AgendaItemDefinition firstItem = this.ruleManagementService.getAgendaItem(agenda.getFirstItemId());
        PropositionDefinition prop = firstItem.getRule().getProposition();
        PropositionDefinition.Builder propBldr = PropositionDefinition.Builder.create(prop);
        propBldr = this.updateNOfCluSetProposition(propBldr, 2);
        this.updateCheckFirstItemSettingPropositionOnRule(agenda, propBldr);
    }

    @Test
    public void testUpdateCompletelyReplacingExistingPropositionCreatingOrphan() {
        AgendaDefinition agenda = createCheckBasicAgendaFor1OfCluSet23();

        PropositionDefinition.Builder propBldr = this.constructFreeFormTextPropositionBulider("My first Text Value");
        updateCheckFirstItemSettingPropositionOnRule(agenda, propBldr);
    }

    @Test
    public void testCreateMultiAgendaItems() {
        //Create agenda
        ContextDefinition context = this.findCreateContext();
        AgendaDefinition agenda = createCheckEmptyAgenda(context);
        AgendaItemDefinition firstItem = this.ruleManagementService.getAgendaItem(agenda.getFirstItemId());
        AgendaItemDefinition.Builder firstItemBldr = createMultiAgendaItems(firstItem);

        this.ruleManagementService.updateAgendaItem(firstItemBldr.build());
        AgendaItemDefinition returnItem = this.ruleManagementService.getAgendaItem(agenda.getFirstItemId());
        checkMultiAgendaItem(firstItemBldr, returnItem);

    }

    @Test
    public void testUpdateChangingTermParameters() {
        //Create agenda
        ContextDefinition context = this.findCreateContext();
        AgendaDefinition agenda = createCheckEmptyAgenda(context);
        AgendaItemDefinition firstItem = this.ruleManagementService.getAgendaItem(agenda.getFirstItemId());
        this.ruleManagementService.updateAgendaItem(createMultiAgendaItems(firstItem).build());

        firstItem = this.ruleManagementService.getAgendaItem(agenda.getFirstItemId());
        AgendaItemDefinition.Builder itemBuilder = AgendaItemDefinition.Builder.create(firstItem);
        PropositionParameter.Builder propParameter = null;
        for(PropositionParameter.Builder parameter : itemBuilder.getWhenTrue().getRule().getProposition().getParameters()){
            if(parameter.getTermValue()!=null){
                propParameter = parameter;
            }
        }

        TermDefinition.Builder term = TermDefinition.Builder.create(propParameter.getTermValue());
        for(TermParameterDefinition.Builder parameter : term.getParameters()){
            parameter.setValue("The updated free form proposition");
            break;
        }
        propParameter.setTermValue(term.build());

        this.ruleManagementService.updateAgendaItem(itemBuilder.build());
        AgendaItemDefinition returnItem = this.ruleManagementService.getAgendaItem(itemBuilder.getId());
        checkMultiAgendaItem(itemBuilder, returnItem);

    }

    private AgendaItemDefinition.Builder createMultiAgendaItems(AgendaItemDefinition firstItem){
        //Add first item details.
        PropositionDefinition.Builder firstPropBldr = this.constructFreeFormTextPropositionBulider("The first free form proposition");
        RuleDefinition.Builder firstRuleBldr = this.constructEmptyRuleBuilder();
        firstRuleBldr.setProposition(firstPropBldr);
        AgendaItemDefinition.Builder firstItemBldr = AgendaItemDefinition.Builder.create(firstItem);
        firstItemBldr.setRule(firstRuleBldr);

        //Add second item details.
        PropositionDefinition.Builder secondPropBldr = this.constructFreeFormTextPropositionBulider("The second free form proposition");
        RuleDefinition.Builder secondRuleBldr = this.constructEmptyRuleBuilder();
        secondRuleBldr.setProposition(secondPropBldr);
        AgendaItemDefinition.Builder secondItemBldr = AgendaItemDefinition.Builder.create(null, firstItem.getAgendaId());
        secondItemBldr.setRule(secondRuleBldr);
        firstItemBldr.setWhenTrue(secondItemBldr);

        return firstItemBldr;
    }

    private void checkMultiAgendaItem(AgendaItemDefinition.Builder itemBuilder, AgendaItemDefinition item){
        this.checkRule(itemBuilder.getRule(), item.getRule());
        assertNotNull(item.getWhenTrue());
        this.checkRule(itemBuilder.getWhenTrue().getRule(), item.getWhenTrue().getRule());
    }

    private AgendaDefinition createCheckBasicAgendaFor1OfCluSet23() {
        ContextDefinition context = this.findCreateContext();
        AgendaDefinition agenda = createCheckEmptyAgenda(context);
        agenda = updateCheckAgendaFirstItemAddingEmptyRule(agenda);
        CluSetInfo cluSet = this.findCreateCluSet23();
        PropositionDefinition.Builder propBldr = createNOfCluSetPropositionBuilder(1, cluSet);
        agenda = updateCheckFirstItemSettingPropositionOnRule(agenda, propBldr);
        return agenda;
    }

    private CluSetInfo findCreateCluSet23() {
        // create the cluset
        CluInfo courseClu2 = this.getClu("COURSE2");
        CluInfo courseClu3 = this.getClu("COURSE3");
        List<String> versionIndIds = this.getVersionIndIds(courseClu2, courseClu3);
        CluSetInfo cluSet = findCreateCluSet("cluSet23", "Courses 2 & 3", versionIndIds);
        return cluSet;
    }

    private CluSetInfo findCreateCluSet456() {
        // create the cluset
        CluInfo courseClu4 = this.getClu("COURSE4");
        CluInfo courseClu5 = this.getClu("COURSE5");
        CluInfo courseClu6 = this.getClu("COURSE6");
        List<String> versionIndIds = this.getVersionIndIds(courseClu4, courseClu5, courseClu6);
        CluSetInfo cluSet = findCreateCluSet("cluSet456", "Courses 4, 5 & 6", versionIndIds);
        return cluSet;
    }

    private ContextDefinition findCreateContext() {
        // find/create the context corresponding to this context type.
        ContextDefinition.Builder contextBldr = ContextDefinition.Builder.create(KSKRMSServiceConstants.NAMESPACE_CODE,
                KSKRMSServiceConstants.CONTEXT_COURSE_REQUIREMENTS);
        contextBldr.setTypeId(KSKRMSServiceConstants.CONTEXT_TYPE_DEFAULT);
        contextBldr.setDescription(KSKRMSServiceConstants.CONTEXT_COURSE_REQUIREMENTS);
        contextBldr.setActive(true);
        ContextDefinition context = this.ruleManagementService.findCreateContext(contextBldr.build());
        this.checkContext(contextBldr, context);
        return context;
    }

    private void checkContext(ContextDefinition.Builder contextBldr, ContextDefinition context) {
        assertNotNull(context);
        assertNotNull(context.getId());
        assertEquals(contextBldr.getName(), context.getName());
        assertEquals(contextBldr.getNamespace(), context.getNamespace());
        assertEquals(contextBldr.getTypeId(), context.getTypeId());
        assertEquals(contextBldr.isActive(), context.isActive());
    }

    private AgendaDefinition createCheckEmptyAgenda(ContextDefinition context) {
        // find/create the context corresponding to this context type.
        // create the agenda
        KrmsTypeDefinition agendaType = this.krmsTypeRepositoryService.getTypeByName(KSKRMSServiceConstants.NAMESPACE_CODE,
                KSKRMSServiceConstants.AGENDA_TYPE_COURSE_ENROLLMENTELIGIBILITY);
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
        this.checkAgenda(agendaBldr, agenda);
        return agenda;
    }

    private void checkAgenda(AgendaDefinition.Builder agendaBldr, AgendaDefinition agenda) {
        assertNotNull(agenda);
        assertNotNull(agenda.getId());
        assertEquals(agendaBldr.getName(), agenda.getName());
        assertEquals(agendaBldr.getContextId(), agenda.getContextId());
        assertEquals(agendaBldr.getTypeId(), agenda.getTypeId());
        assertEquals(agendaBldr.isActive(), agenda.isActive());
        if (agendaBldr.getFirstItemId() == null) {
            assertNotNull(agenda.getFirstItemId());
        } else {
            assertEquals(agendaBldr.getFirstItemId(), agenda.getFirstItemId());
        }
    }

    private AgendaDefinition updateCheckAgendaFirstItemAddingEmptyRule(AgendaDefinition agenda) {
        AgendaItemDefinition firstItem = this.ruleManagementService.getAgendaItem(agenda.getFirstItemId());
        assertNull(firstItem.getRule());
        RuleDefinition.Builder ruleBldr = null;
        ruleBldr = this.constructEmptyRuleBuilder();
        AgendaItemDefinition.Builder itemBldr = AgendaItemDefinition.Builder.create(firstItem);
        itemBldr.setRule(ruleBldr);
        this.ruleManagementService.updateAgendaItem(itemBldr.build());
        agenda = this.ruleManagementService.getAgenda(agenda.getId());
        firstItem = this.ruleManagementService.getAgendaItem(firstItem.getId());
        checkAgendaItem(agenda, itemBldr, firstItem);
        return agenda;
    }

    private void checkAgendaItem(AgendaDefinition agenda, AgendaItemDefinition.Builder itemBldr, AgendaItemDefinition item) {
        assertNotNull(item);
        assertNotNull(item.getId());
        assertNotNull(agenda.getId(), item.getAgendaId());
        if (item.getRule() == null) {
            assertNull(item.getRuleId());
        }
        if (item.getRuleId() == null) {
            assertNull(item.getRule());
        }
        if (item.getRule() != null) {
            assertEquals(item.getRuleId(), item.getRule().getId());
            checkRule(itemBldr.getRule(), item.getRule());
        }
    }

    private RuleDefinition.Builder constructEmptyRuleBuilder() {
        // make rule
        KrmsTypeDefinition ruleType = this.krmsTypeRepositoryService.getTypeByName(KSKRMSServiceConstants.NAMESPACE_CODE,
                KSKRMSServiceConstants.RULE_TYPE_COURSE_ACADEMICREADINESS_STUDENTELIGIBILITYPREREQ);
        String ruleId = null; // sevice sets id

        // name has to be unique within namespace and max 100 characters.
        // For testing use a GUID instead of anchorClu.getId () so we always get a new one for testing
        String name = UUIDHelper.genStringUUID();
//        name = anchorClu.getOfficialIdentifier().getCode() + " " + ruleType.getName() + " (" + anchorClu.getId() + ")";
        String namespace = KSKRMSServiceConstants.NAMESPACE_CODE;
        String typeId = ruleType.getId();
        String propId = null;
        RuleDefinition.Builder ruleBldr = RuleDefinition.Builder.create(ruleId, name, namespace, typeId, propId);
        ruleBldr.setActive(true);
        return ruleBldr;
    }

    private AgendaDefinition updateCheckFirstItemSettingPropositionOnRule(AgendaDefinition agenda, PropositionDefinition.Builder propBldr) {
        AgendaItemDefinition firstItem = this.ruleManagementService.getAgendaItem(agenda.getFirstItemId());
        RuleDefinition.Builder ruleBldr = RuleDefinition.Builder.create(firstItem.getRule());
        ruleBldr.setProposition(propBldr);

        AgendaItemDefinition.Builder itemBldr = AgendaItemDefinition.Builder.create(firstItem);
        itemBldr.setRule(ruleBldr);
        AgendaItemDefinition item2Update = itemBldr.build();
        this.ruleManagementService.updateAgendaItem(item2Update);
        agenda = this.ruleManagementService.getAgenda(agenda.getId());
        firstItem = this.ruleManagementService.getAgendaItem(firstItem.getId());
        this.checkAgendaItem(agenda, itemBldr, firstItem);
        return agenda;
    }

    private void checkRule(RuleDefinition.Builder ruleBldr, RuleDefinition rule) {
        assertNotNull(rule.getId());
        assertEquals(ruleBldr.getName(), rule.getName());
        assertEquals(ruleBldr.getNamespace(), rule.getNamespace());
        assertEquals(ruleBldr.getTypeId(), rule.getTypeId());
        if (ruleBldr.getProposition() == null) {
            assertNull(rule.getProposition());
        } else {
            this.checkProposition(rule.getId(), ruleBldr.getProposition(), rule.getProposition());
        }
    }

    private void checkProposition(String ruleId, PropositionDefinition.Builder propBldr, PropositionDefinition prop) {
        assertEquals(ruleId, prop.getRuleId());
//        assertEquals(propBldr.getCompoundSequenceNumber(), prop.getCompoundSequenceNumber());
        assertEquals(propBldr.getPropositionTypeCode(), prop.getPropositionTypeCode());
        assertEquals(propBldr.getTypeId(), prop.getTypeId());
        if (propBldr.getPropositionTypeCode().equals(PropositionType.SIMPLE.getCode())) {
            this.checkSimpleProposition(propBldr, prop);
        } else if (propBldr.getPropositionTypeCode().equals(PropositionType.COMPOUND.getCode())) {
            this.checkCompoundProposition(ruleId, propBldr, prop);
        } else {
            fail("unknown proposition type " + propBldr.getPropositionTypeCode());
        }
    }

    private void checkCompoundProposition(String ruleId, PropositionDefinition.Builder propBldr, PropositionDefinition prop) {
        assertEquals(PropositionType.COMPOUND.getCode(), propBldr.getPropositionTypeCode());
        assertEquals(propBldr.getPropositionTypeCode(), prop.getPropositionTypeCode());
//        assertEquals(propBldr.getDescription(), prop.getDescription());
        assertNotNull(propBldr.getCompoundOpCode());
        assertEquals(propBldr.getCompoundOpCode(), prop.getCompoundOpCode());
        // should not have parameters those are for simple propositions
        assertTrue(propBldr.getParameters().isEmpty());
        assertTrue(prop.getParameters().isEmpty());
        assertEquals(propBldr.getCompoundComponents().size(), prop.getCompoundComponents().size());

        if (propBldr.getCompoundComponents().size() < 2) {
            fail("there must be at least 2 compound components " + propBldr.getCompoundComponents().size());
        }
        for (int i = 0; i < propBldr.getCompoundComponents().size(); i++) {
            PropositionDefinition childProp = prop.getCompoundComponents().get(i);
            PropositionDefinition.Builder childPropBldr = propBldr.getCompoundComponents().get(i);
            this.checkProposition(ruleId, childPropBldr, childProp);
        }
    }

    private void checkSimpleProposition(PropositionDefinition.Builder propBldr, PropositionDefinition prop) {

        assertEquals(PropositionType.SIMPLE.getCode(), propBldr.getPropositionTypeCode());
        assertEquals(propBldr.getPropositionTypeCode(), prop.getPropositionTypeCode());
        // should not have compound stuff those are for compound propositions
        assertNull(propBldr.getCompoundOpCode());
        assertNull(prop.getCompoundOpCode());
        if (propBldr.getCompoundComponents() != null) {
            assertTrue(propBldr.getCompoundComponents().isEmpty());
            assertTrue(prop.getCompoundComponents().isEmpty());
        }
        assertEquals(propBldr.getParameters().size(),
                prop.getParameters().size());
        for (int i = 0; i < propBldr.getParameters().size(); i++) {
            PropositionParameter.Builder propParamBldr = propBldr.getParameters().get(0);
            PropositionParameter propParam = prop.getParameters().get(0);

            assertNotNull(propParam.getId());
            assertNotNull(propParamBldr.getParameterType());
            assertNotNull(propParamBldr.getSequenceNumber());
            assertEquals(propParamBldr.getSequenceNumber(),
                    propParam.getSequenceNumber());
            assertEquals(propParamBldr.getParameterType(),
                    propParam.getParameterType());
            assertEquals(prop.getId(), propParam.getPropId());
            if (propParamBldr.getParameterType().equals(PropositionParameterType.TERM.getCode())) {
                this.checkTerm(propParam.getValue(), propParamBldr.getTermValue(), propParam.getTermValue());
            } else {
                assertEquals(propParamBldr.getValue(), propParam.getValue());
                assertNull(propParam.getTermValue());
            }
        }
    }

    private void checkTerm(String value, TermDefinition termBldr, TermDefinition term) {
        assertNotNull(termBldr);
        assertNotNull(term);
        assertNotNull(term.getId());
        assertEquals(value, term.getId());
        assertNotNull(termBldr.getSpecification());
        assertNotNull(term.getSpecification());
        assertEquals(termBldr.getSpecification().getName(),
                term.getSpecification().getName());
        assertEquals(termBldr.getSpecification().getNamespace(),
                term.getSpecification().getNamespace());
        assertEquals(termBldr.getParameters().size(),
                term.getParameters().size());
        for (int i = 0; i < term.getParameters().size(); i++) {
            TermParameterDefinition termParamBldr = termBldr.getParameters().get(i);
            TermParameterDefinition termParam = term.getParameters().get(i);
            this.checkTermParam(term.getId(), termParamBldr, termParam);
        }
    }

    private void checkTermParam(String termId, TermParameterDefinition termParamBldr, TermParameterDefinition termParam) {
        assertNotNull(termParam.getId());
        assertEquals(termId, termParam.getTermId());
        assertNotNull(termParam.getName());
        assertEquals(termParamBldr.getName(), termParam.getName());
        assertEquals(termParamBldr.getValue(), termParam.getValue());
    }
    private static final String N_OF_CLU_SET_TERM_NAME = "NumberOfCompletedCourses";
    private static final String N_OF_CLU_SET_OPERATOR = "<=";

    private PropositionDefinition.Builder createNOfCluSetPropositionBuilder(int n, CluSetInfo cluSet) {

        // create all the parameters
        List<PropositionParameter.Builder> parameters = new ArrayList<PropositionParameter.Builder>();
        String constantValue = "" + n;

        // first the parameter to the parameter! (actually the term parameter to the proposition parameter that is a term!
        List<TermParameterDefinition.Builder> termParameters = new ArrayList<TermParameterDefinition.Builder>();
        String id = null; //set by service
        String termId = null;
        String name = "CourseSetId";
        String value = cluSet.getId(); // this was created when the cluSet was built
        TermParameterDefinition.Builder termParamBldr = TermParameterDefinition.Builder.create(id, termId, name, value);
        termParameters.add(termParamBldr);

        id = null; // set by service
        TermSpecificationDefinition termSpec =
                this.termRepositoryService.getTermSpecificationByNameAndNamespace(N_OF_CLU_SET_TERM_NAME,
                        KSKRMSServiceConstants.NAMESPACE_CODE);
        assertNotNull(termSpec);
        assertEquals(N_OF_CLU_SET_TERM_NAME, termSpec.getName());
        TermSpecificationDefinition.Builder termSpecBldr = TermSpecificationDefinition.Builder.create(termSpec);
        String description = termSpec.getName() + " for " + cluSet.getId();
        TermDefinition.Builder termBldr = TermDefinition.Builder.create(id, termSpecBldr, termParameters);
        termBldr.setDescription(description);


        // do the term parameter first
        id = null; // should be set by service
        String propId = null; // should also be set by the service when the create on the proposition happens
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
        value = N_OF_CLU_SET_OPERATOR;
        parameterType = PropositionParameterType.OPERATOR.getCode();
        sequenceNumber = 3;
        propParamBldr = PropositionParameter.Builder.create(id, propId, value, parameterType, sequenceNumber);
        parameters.add(propParamBldr);

        propId = null; // should be null until assigned by service
        String propTypeCode = PropositionType.SIMPLE.getCode();
        String ruleId = null;  // assigned by service
        KrmsTypeDefinition propType = this.krmsTypeRepositoryService.getTypeByName(KSKRMSServiceConstants.NAMESPACE_CODE, KSKRMSServiceConstants.PROPOSITION_TYPE_COURSE_COURSESET_COMPLETED_NOF);
        String typeId = propType.getId();
        PropositionDefinition.Builder propBldr = PropositionDefinition.Builder.create(propId, propTypeCode, ruleId, typeId, parameters);
//        propBldr.setDescription(propType.getName());
        return propBldr;
    }

    private PropositionDefinition.Builder updateNOfCluSetProposition(PropositionDefinition.Builder propBldr, int n) {
        PropositionParameter.Builder propParamBldr = propBldr.getParameters().get(1);
        propParamBldr.setValue("" + n);
        return propBldr;
    }
    private static final String FREE_FORM_TEXT_TERM_NAME = "FreeFormText";
    private static final String FREE_FORM_TEXT_TERM_PARAM_NAME = "Text";
    private static final String FREE_FORM_TEXT_CONSTANT_VALUE = "true";
    private static final String FREE_FORM_TEXT_OPERATOR = "=";

    private PropositionDefinition.Builder constructFreeFormTextPropositionBulider(String myText) {
        // create all the parameters
        List<PropositionParameter.Builder> parameters = new ArrayList<PropositionParameter.Builder>();


        // first the parameter to the parameter! (actually the term parameter to the proposition parameter that is a term!
        List<TermParameterDefinition.Builder> termParameters = new ArrayList<TermParameterDefinition.Builder>();
        String id = null; //set by service
        String termId = null;
        TermParameterDefinition.Builder termParamBldr = TermParameterDefinition.Builder.create(id, termId, FREE_FORM_TEXT_TERM_PARAM_NAME, myText);
        termParameters.add(termParamBldr);

        id = null; // set by service
        TermSpecificationDefinition termSpec =
                this.termRepositoryService.getTermSpecificationByNameAndNamespace(FREE_FORM_TEXT_TERM_NAME,
                        KSKRMSServiceConstants.NAMESPACE_CODE);
        assertNotNull(termSpec);
        assertEquals(FREE_FORM_TEXT_TERM_NAME, termSpec.getName());
        TermSpecificationDefinition.Builder termSpecBldr = TermSpecificationDefinition.Builder.create(termSpec);
        String description = termSpec.getName();
        TermDefinition.Builder termBldr = TermDefinition.Builder.create(id, termSpecBldr, termParameters);
        termBldr.setDescription(description);

        // do the term parameter first
        id = null; // should be set by service
        String propId = null; // should also be set by the service when the create on the proposition happens
        String value = null;  // set by service
        String parameterType = PropositionParameterType.TERM.getCode();
        Integer sequenceNumber = 1;
        PropositionParameter.Builder propParamBldr = PropositionParameter.Builder.create(id,
                propId, value, parameterType, sequenceNumber);
        propParamBldr.setTermValue(termBldr.build());
        parameters.add(propParamBldr);


        // do the constant value next
        id = null; // should be set by service
        propId = null; // should also be set by the service when the create on the proposition happens
        value = FREE_FORM_TEXT_CONSTANT_VALUE;
        parameterType = PropositionParameterType.CONSTANT.getCode();
        sequenceNumber = 2;
        propParamBldr = PropositionParameter.Builder.create(id, propId, value, parameterType, sequenceNumber);
        parameters.add(propParamBldr);

        // do the operator 
        id = null; // should be set by service
        propId = null; // should also be set by the service when the create on the proposition happens
        value = FREE_FORM_TEXT_OPERATOR;
        parameterType = PropositionParameterType.OPERATOR.getCode();
        sequenceNumber = 3;
        propParamBldr = PropositionParameter.Builder.create(id, propId, value, parameterType, sequenceNumber);
        parameters.add(propParamBldr);

        propId = null; // should be null until assigned by service
        String propTypeCode = PropositionType.SIMPLE.getCode();
        String ruleId = null;  // assigned by service
        KrmsTypeDefinition propType = this.krmsTypeRepositoryService.getTypeByName(KSKRMSServiceConstants.NAMESPACE_CODE, KSKRMSServiceConstants.PROPOSITION_TYPE_FREEFORM_TEXT);
        String typeId = propType.getId();
        PropositionDefinition.Builder propBldr = PropositionDefinition.Builder.create(propId, propTypeCode, ruleId, typeId, parameters);
//        propBldr.setDescription(propType.getName());
        return propBldr;
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
        return this.getNaturalLanguageUsage(KSKRMSServiceConstants.KRMS_NL_TYPE_DESCRIPTION, KSKRMSServiceConstants.NAMESPACE_CODE);
    }

    private NaturalLanguageUsage getRuleEditUsage() {
        return this.getNaturalLanguageUsage(KSKRMSServiceConstants.KRMS_NL_RULE_EDIT, KSKRMSServiceConstants.NAMESPACE_CODE);
    }

    private NaturalLanguageUsage getPreviewUsage() {
        return this.getNaturalLanguageUsage(KSKRMSServiceConstants.KRMS_NL_PREVIEW, KSKRMSServiceConstants.NAMESPACE_CODE);
    }

    private KrmsTypeDefinition simulateGettingCourseAgendaType() {
        KrmsTypeDefinition type = this.krmsTypeRepositoryService.getTypeByName(KSKRMSServiceConstants.NAMESPACE_CODE, KSKRMSServiceConstants.AGENDA_TYPE_COURSE);
        assertNotNull(type);
        assertEquals(KSKRMSServiceConstants.AGENDA_TYPE_COURSE, type.getName());
        assertEquals(KSKRMSServiceConstants.NAMESPACE_CODE, type.getNamespace());
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

    // commented out this test because it is brittle and what gets returned changes as the configuration changes
//    @Test
    public void testBasicTypeConfiguration() {

        List<KrmsTypeDefinition> types = null;
        Set<String> expected = null;
        String languageCode = KSKRMSServiceConstants.LANGUAGE_CODE_ENGLISH;
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
        expected.add(KSKRMSServiceConstants.AGENDA_TYPE_COURSE_ENROLLMENTELIGIBILITY);
        expected.add(KSKRMSServiceConstants.AGENDA_TYPE_COURSE_CREDITCONSTRAINTS);
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
        expected.add(KSKRMSServiceConstants.RULE_TYPE_COURSE_ACADEMICREADINESS_STUDENTELIGIBILITYPREREQ);
        expected.add(KSKRMSServiceConstants.RULE_TYPE_COURSE_ACADEMICREADINESS_COREQ);
        expected.add(KSKRMSServiceConstants.RULE_TYPE_COURSE_RECOMMENDEDPREPARATION);
        expected.add(KSKRMSServiceConstants.RULE_TYPE_COURSE_ACADEMICREADINESS_ANTIREQ);
        this.checkTypeNamesOrdered(expected, types);

        title = "Please choose which type of rule you want to work on:";
        defaultIndex = 0;
        KrmsTypeDefinition eligPrereqRuleType = this.simulateUserChoosingType(title, types, languageCode, defaultIndex);

        // Get all the Proposition types for the rule type
        types = this.krmsTypeRepositoryService.findPropositionTypesForRuleType(eligPrereqRuleType.getId());
        expected = new LinkedHashSet<String>();
        expected.add(KSKRMSServiceConstants.PROPOSITION_TYPE_FREEFORM_TEXT);
        expected.add(KSKRMSServiceConstants.PROPOSITION_TYPE_SUCCESS_COMPL_COURSE);
        expected.add(KSKRMSServiceConstants.PROPOSITION_TYPE_CUMULATIVE_GPA_MIN);
        expected.add(KSKRMSServiceConstants.PROPOSITION_TYPE_ADMITTED_TO_PROGRAM);
        expected.add(KSKRMSServiceConstants.PROPOSITION_TYPE_SUCCESS_CREDIT_COURSESET_COMPLETED_NOF);
        expected.add(KSKRMSServiceConstants.PROPOSITION_TYPE_SUCCESS_CREDITS_COURSESET_COMPLETED_NOF_ORG);
        expected.add(KSKRMSServiceConstants.PROPOSITION_TYPE_SUCCESS_COURSE_COURSESET_COMPLETED_ALL);
        expected.add(KSKRMSServiceConstants.PROPOSITION_TYPE_SUCCESS_COURSE_COURSESET_COMPLETED_NOF);
        expected.add(KSKRMSServiceConstants.PROPOSITION_TYPE_COURSE_COURSESET_GPA_MIN);
        expected.add(KSKRMSServiceConstants.PROPOSITION_TYPE_COURSE_COURSESET_GRADE_MIN);
        expected.add(KSKRMSServiceConstants.PROPOSITION_TYPE_COURSE_COURSESET_NOF_GRADE_MIN);
//        expected.add(KsKrmsConstants.PROPOSITION_TYPE_COURSE_COURSESET_GRADE_MAX);
        expected.add(KSKRMSServiceConstants.PROPOSITION_TYPE_ADMITTED_TO_PROGRAM_CAMPUS);
//        expected.add(KsKrmsConstants.PROPOSITION_TYPE_NOTADMITTED_TO_PROGRAM);
        expected.add(KSKRMSServiceConstants.PROPOSITION_TYPE_PERMISSION_INSTRUCTOR_REQUIRED);
        expected.add(KSKRMSServiceConstants.PROPOSITION_TYPE_PERMISSION_ADMIN_ORG);
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
//                        <property name="componentBuilderClass" value="org.kuali.student.lum.lu.ui.krms.builder.MultiCourseComponentBuilder"/>
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
    public void testTranslateNaturalLanguageForProposition() {
        ContextDefinition context = this.findCreateContext();
        AgendaDefinition agenda = createCheckEmptyAgenda(context);
        agenda = updateCheckAgendaFirstItemAddingEmptyRule(agenda);

        PropositionDefinition.Builder propBldr1 = this.constructFreeFormTextPropositionBulider("My first Text Value");
        CluSetInfo cluSet = this.findCreateCluSet23();
        PropositionDefinition.Builder propBldr2 = createNOfCluSetPropositionBuilder(1, cluSet);
        cluSet = this.findCreateCluSet456();
        PropositionDefinition.Builder propBldr3 = createNOfCluSetPropositionBuilder(2, cluSet);

        PropositionDefinition.Builder andPropBldr = this.makeAndCompoundProposition(propBldr2, propBldr3);
        PropositionDefinition.Builder orPropBldr = this.makeOrCompoundProposition(propBldr1, andPropBldr);

        AgendaItemDefinition firstItem = this.ruleManagementService.getAgendaItem(agenda.getFirstItemId());
        RuleDefinition.Builder ruleBldr = RuleDefinition.Builder.create(firstItem.getRule());
        ruleBldr.setProposition(orPropBldr);

        AgendaItemDefinition.Builder itemBldr = AgendaItemDefinition.Builder.create(firstItem);
        itemBldr.setRule(ruleBldr);
        AgendaItemDefinition item2Update = itemBldr.build();
        this.ruleManagementService.updateAgendaItem(item2Update);
        agenda = this.ruleManagementService.getAgenda(agenda.getId());
        firstItem = this.ruleManagementService.getAgendaItem(firstItem.getId());

        List<NaturalLanguageUsage> usages = this.ruleManagementService.getNaturalLanguageUsagesByNamespace(KSKRMSServiceConstants.NAMESPACE_CODE);

        System.out.println(this.ruleManagementService.translateNaturalLanguageForObject("KS-KRMS-NL-USAGE-1000", "agenda", agenda.getId(), "en"));
        System.out.println(this.ruleManagementService.translateNaturalLanguageForObject("KS-KRMS-NL-USAGE-1000", "rule", firstItem.getRule().getId(), "en"));
        System.out.println(this.ruleManagementService.translateNaturalLanguageForObject("KS-KRMS-NL-USAGE-1000", "proposition", firstItem.getRule().getProposition().getId(), "en"));

    }
}
