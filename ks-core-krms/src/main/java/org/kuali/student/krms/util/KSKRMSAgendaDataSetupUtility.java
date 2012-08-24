/**
 * Copyright 2005-2011 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl2.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.student.krms.util;

import org.kuali.rice.core.framework.resourceloader.SpringResourceLoader;
import org.kuali.rice.krad.service.KRADServiceLocator;
import org.kuali.rice.krms.api.repository.proposition.PropositionParameterType;
import org.kuali.rice.krms.api.repository.term.TermSpecificationDefinition;
import org.kuali.rice.krms.api.repository.type.KrmsTypeDefinition;
import org.kuali.rice.krms.api.repository.type.KrmsTypeRepositoryService;
import org.kuali.rice.krms.impl.repository.AgendaBoService;
import org.kuali.rice.krms.impl.repository.ContextBoService;
import org.kuali.rice.krms.impl.repository.RuleBoService;
import org.kuali.rice.krms.impl.repository.TermBoService;
import org.kuali.rice.krad.service.BusinessObjectService;
import org.kuali.rice.krms.api.repository.context.ContextDefinition;
import org.kuali.student.common.util.PropertiesFilterFactoryBean;
import org.kuali.rice.krms.api.repository.agenda.AgendaDefinition;
import org.kuali.rice.krms.api.repository.agenda.AgendaItemDefinition;
import org.kuali.rice.krms.api.repository.proposition.PropositionDefinition;
import org.kuali.rice.krms.api.repository.proposition.PropositionParameter;
import org.kuali.rice.krms.api.repository.proposition.PropositionType;
import org.kuali.rice.krms.api.repository.rule.RuleDefinition;
import org.kuali.rice.krms.api.repository.term.TermDefinition;
import org.kuali.rice.krms.api.repository.LogicalOperator;
import org.kuali.rice.krms.impl.repository.TermBo;

import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import javax.xml.namespace.QName;

public class KSKRMSAgendaDataSetupUtility {


    public static final String PROPERTY_namespace = "Namespace";
    public static final String PROPERTY_PROPOSITION1 = "Proposition1";
    public static final String PROPERTY_PROPOSITION2 = "Proposition2";
    public static final String PROPERTY_PROPOSITION3 = "Proposition3";
    public static final String PROPERTY_PROPOSITION4 = "Proposition4";
    public static final String PROPERTY_PROPOSITION5 = "Proposition5";
    public static final String PROPERTY_PROPOSITION6 = "Proposition6";
    public static final String PROPERTY_PROPOSITION7 = "Proposition7";
    public static final String PROPERTY_PROPOSITION8 = "Proposition8";
    public static final String PROPERTY_PROPOSITION9 = "Proposition9";

    KrmsTypeDefinition krmsTypeForContext;

    private ContextBoService contextRepository;
    private KrmsTypeRepositoryService krmsTypeRepository;
    private AgendaBoService agendaBoService;
    private RuleBoService ruleBoService;
    private TermBoService termBoService;
    private SpringResourceLoader krmsTestResourceLoader;
    private BusinessObjectService boService;

    PropertiesFilterFactoryBean propertyUtil;
    private String namespace;
    // Needed for agendas
    KrmsTypeDefinition krmsTypeDefinition;


    public static void main(String [ ] args)
    {
        KSKRMSAgendaDataSetupUtility utility = new KSKRMSAgendaDataSetupUtility();
        utility.setupRequiredServices();
        utility.createKSKRMSData();
    }

    public void createKSKRMSData() {
        setupPropertyFile();
        namespace = getPropertyValue(PROPERTY_namespace);
        createKRMSProposition1FromPropertyFile();
        createKRMSProposition2FromPropertyFile();
        createKRMSProposition3FromPropertyFile();
        createKRMSProposition4FromPropertyFile();
        createKRMSProposition5FromPropertyFile();
        createKRMSProposition6FromPropertyFile();
        createKRMSProposition7FromPropertyFile();
        createKRMSProposition8FromPropertyFile();
        createKRMSProposition9FromPropertyFile();

    }
    public void createKRMSProposition1FromPropertyFile() {
        Properties properties = getProperties(PROPERTY_PROPOSITION1);
        //
        Enumeration elements = properties.elements();

        ArrayList<PropositionParametersBuilder> propositionList = new ArrayList<PropositionParametersBuilder>();

        PropositionParametersBuilder proposition = null;
        int i = 0;
        //
        while (elements.hasMoreElements()) {
            propositionList = new ArrayList<PropositionParametersBuilder>();
            String termSpecValues =  (String) elements.nextElement();
            System.out.println(i + " - " + termSpecValues);

            String delims = ",+"; // use + to treat consecutive delims as one;
            // omit to treat consecutive delims separately
            String[] tokens = termSpecValues.split(delims);
            proposition = createProposition(
                    tokens[0],tokens[1],tokens[2]);
            propositionList.add(proposition);
            i++;


        }
        createAgendaAndRuleAndPropositions(KSKRMSReplaceWithPropertyFile.AGENDA1,
                contextRepository.getContextByNameAndNamespace(
                        KSKRMSReplaceWithPropertyFile.CONTEXT_STUD_ELIGIBILITY, namespace),
                null, namespace, propositionList.toArray(new PropositionParametersBuilder[propositionList.size()]));
    }

    public void createKRMSProposition2FromPropertyFile() {
        Properties properties = getProperties(PROPERTY_PROPOSITION2);
        //
        Enumeration elements = properties.elements();

        ArrayList<PropositionParametersBuilder> propositionList = new ArrayList<PropositionParametersBuilder>();

        PropositionParametersBuilder proposition = null;
        int i = 0;
        //
        while (elements.hasMoreElements()) {
            propositionList = new ArrayList<PropositionParametersBuilder>();
            String termSpecValues =  (String) elements.nextElement();
            System.out.println(i + " - " + termSpecValues);

            String delims = ",+"; // use + to treat consecutive delims as one;
            // omit to treat consecutive delims separately
            String[] tokens = termSpecValues.split(delims);
            proposition = createProposition(
                    tokens[0],tokens[1],tokens[2]);
            propositionList.add(proposition);
            i++;


        }
        createAgendaAndRuleAndPropositions(KSKRMSReplaceWithPropertyFile.AGENDA2,
                contextRepository.getContextByNameAndNamespace(
                        KSKRMSReplaceWithPropertyFile.CONTEXT_STUD_ELIGIBILITY, namespace),
                null, namespace, propositionList.toArray(new PropositionParametersBuilder[propositionList.size()]));
    }

    public void createKRMSProposition3FromPropertyFile() {
        Properties properties = getProperties(PROPERTY_PROPOSITION3);
        //
        Enumeration elements = properties.elements();

        ArrayList<PropositionParametersBuilder> propositionList = new ArrayList<PropositionParametersBuilder>();

        PropositionParametersBuilder proposition = null;
        int i = 0;
        //
        while (elements.hasMoreElements()) {
            propositionList = new ArrayList<PropositionParametersBuilder>();
            String termSpecValues =  (String) elements.nextElement();
            System.out.println(i + " - " + termSpecValues);

            String delims = ",+"; // use + to treat consecutive delims as one;
            // omit to treat consecutive delims separately
            String[] tokens = termSpecValues.split(delims);
            proposition = createProposition(
                    tokens[0],tokens[1],tokens[2]);
            propositionList.add(proposition);
            i++;
        }
        createAgendaAndRuleAndPropositions(KSKRMSReplaceWithPropertyFile.AGENDA3,
                contextRepository.getContextByNameAndNamespace(
                        KSKRMSReplaceWithPropertyFile.CONTEXT_STUD_ELIGIBILITY, namespace),
                null, namespace, propositionList.toArray(new PropositionParametersBuilder[propositionList.size()]));
    }

    public void createKRMSProposition4FromPropertyFile() {
        Properties properties = getProperties(PROPERTY_PROPOSITION4);
        //
        Enumeration elements = properties.elements();

        ArrayList<PropositionParametersBuilder> propositionList = new ArrayList<PropositionParametersBuilder>();

        PropositionParametersBuilder proposition = null;
        int i = 0;
        //
        while (elements.hasMoreElements()) {
            propositionList = new ArrayList<PropositionParametersBuilder>();
            String termSpecValues =  (String) elements.nextElement();
            System.out.println(i + " - " + termSpecValues);

            String delims = ",+"; // use + to treat consecutive delims as one;
            // omit to treat consecutive delims separately
            String[] tokens = termSpecValues.split(delims);
            proposition = createProposition(
                    tokens[0],tokens[1],tokens[2]);
            propositionList.add(proposition);
            i++;
        }
        createAgendaAndRuleAndPropositions(KSKRMSReplaceWithPropertyFile.AGENDA4,
                contextRepository.getContextByNameAndNamespace(
                        KSKRMSReplaceWithPropertyFile.CONTEXT_STUD_ELIGIBILITY, namespace),
                null, namespace, propositionList.toArray(new PropositionParametersBuilder[propositionList.size()]));
    }

    public void createKRMSProposition5FromPropertyFile() {
        Properties properties = getProperties(PROPERTY_PROPOSITION5);
        //
        Enumeration elements = properties.elements();

        ArrayList<PropositionParametersBuilder> propositionList = new ArrayList<PropositionParametersBuilder>();

        PropositionParametersBuilder proposition = null;
        int i = 0;
        //
        while (elements.hasMoreElements()) {
            propositionList = new ArrayList<PropositionParametersBuilder>();
            String termSpecValues =  (String) elements.nextElement();
            System.out.println(i + " - " + termSpecValues);

            String delims = ",+"; // use + to treat consecutive delims as one;
            // omit to treat consecutive delims separately
            String[] tokens = termSpecValues.split(delims);
            proposition = createProposition(
                    tokens[0],tokens[1],tokens[2]);
            propositionList.add(proposition);
            i++;
        }
        createAgendaAndRuleAndPropositions(KSKRMSReplaceWithPropertyFile.AGENDA5,
                contextRepository.getContextByNameAndNamespace(
                        KSKRMSReplaceWithPropertyFile.CONTEXT_STUD_ELIGIBILITY, namespace),
                null, namespace, propositionList.toArray(new PropositionParametersBuilder[propositionList.size()]));
    }

    public void createKRMSProposition6FromPropertyFile() {
        Properties properties = getProperties(PROPERTY_PROPOSITION6);
        //
        Enumeration elements = properties.elements();

        ArrayList<PropositionParametersBuilder> propositionList = new ArrayList<PropositionParametersBuilder>();

        PropositionParametersBuilder proposition = null;
        int i = 0;
        //
        while (elements.hasMoreElements()) {
            propositionList = new ArrayList<PropositionParametersBuilder>();
            String termSpecValues =  (String) elements.nextElement();
            System.out.println(i + " - " + termSpecValues);

            String delims = ",+"; // use + to treat consecutive delims as one;
            // omit to treat consecutive delims separately
            String[] tokens = termSpecValues.split(delims);
            proposition = createProposition(
                    tokens[0],tokens[1],tokens[2]);
            propositionList.add(proposition);
            i++;
        }
        createAgendaAndRuleAndPropositions(KSKRMSReplaceWithPropertyFile.AGENDA6,
                contextRepository.getContextByNameAndNamespace(
                        KSKRMSReplaceWithPropertyFile.CONTEXT_STUD_ELIGIBILITY, namespace),
                null, namespace, propositionList.toArray(new PropositionParametersBuilder[propositionList.size()]));
    }


    public void createKRMSProposition7FromPropertyFile() {
        Properties properties = getProperties(PROPERTY_PROPOSITION7);
        //
        Enumeration elements = properties.elements();

        ArrayList<PropositionParametersBuilder> propositionList = new ArrayList<PropositionParametersBuilder>();

        PropositionParametersBuilder proposition = null;
        int i = 0;
        //
        while (elements.hasMoreElements()) {
            propositionList = new ArrayList<PropositionParametersBuilder>();
            String termSpecValues =  (String) elements.nextElement();
            System.out.println(i + " - " + termSpecValues);

            String delims = ",+"; // use + to treat consecutive delims as one;
            // omit to treat consecutive delims separately
            String[] tokens = termSpecValues.split(delims);
            proposition = createProposition(
                    tokens[0],tokens[1],tokens[2]);
            propositionList.add(proposition);
            i++;
        }
        createAgendaAndRuleAndPropositions(KSKRMSReplaceWithPropertyFile.AGENDA7,
                contextRepository.getContextByNameAndNamespace(
                        KSKRMSReplaceWithPropertyFile.CONTEXT_STUD_ELIGIBILITY, namespace),
                null, namespace, propositionList.toArray(new PropositionParametersBuilder[propositionList.size()]));
    }

    public void createKRMSProposition8FromPropertyFile() {
        Properties properties = getProperties(PROPERTY_PROPOSITION8);
        //
        Enumeration elements = properties.elements();

        ArrayList<PropositionParametersBuilder> propositionList = new ArrayList<PropositionParametersBuilder>();

        PropositionParametersBuilder proposition = null;
        int i = 0;
        //
        while (elements.hasMoreElements()) {
            propositionList = new ArrayList<PropositionParametersBuilder>();
            String termSpecValues =  (String) elements.nextElement();
            System.out.println(i + " - " + termSpecValues);

            String delims = ",+"; // use + to treat consecutive delims as one;
            // omit to treat consecutive delims separately
            String[] tokens = termSpecValues.split(delims);
            proposition = createProposition(
                    tokens[0],tokens[1],tokens[2]);
            propositionList.add(proposition);
            i++;
        }

        createAgendaAndRuleAndPropositions(KSKRMSReplaceWithPropertyFile.AGENDA8,
                contextRepository.getContextByNameAndNamespace(
                        KSKRMSReplaceWithPropertyFile.CONTEXT_STUD_ELIGIBILITY, namespace),
                null, namespace,  propositionList.toArray(new PropositionParametersBuilder[propositionList.size()]));

        // Creating agenda 9 based on Made Up Data
        createAgendaAndRuleAndPropositions(KSKRMSReplaceWithPropertyFile.AGENDA9,
                contextRepository.getContextByNameAndNamespace(
                        KSKRMSReplaceWithPropertyFile.CONTEXT_STUD_ELIGIBILITY, namespace),
                null, namespace);

        // Creating agenda 10 based on Made Up Data
        createAgendaAndRuleAndPropositions(KSKRMSReplaceWithPropertyFile.AGENDA10,
                contextRepository.getContextByNameAndNamespace(
                        KSKRMSReplaceWithPropertyFile.CONTEXT_STUD_ELIGIBILITY, namespace),
                null, namespace);
    }
    public void createKRMSProposition9FromPropertyFile() {
        Properties properties = getProperties(PROPERTY_PROPOSITION9);
        //
        Enumeration elements = properties.elements();

        ArrayList<PropositionParametersBuilder> propositionList = new ArrayList<PropositionParametersBuilder>();

        PropositionParametersBuilder proposition = null;
        int i = 0;
        //
        while (elements.hasMoreElements()) {
            propositionList = new ArrayList<PropositionParametersBuilder>();
            String termSpecValues =  (String) elements.nextElement();
            System.out.println(i + " - " + termSpecValues);

            String delims = ",+"; // use + to treat consecutive delims as one;
            // omit to treat consecutive delims separately
            String[] tokens = termSpecValues.split(delims);
            proposition = createProposition(
                    tokens[0],tokens[1],tokens[2]);
            propositionList.add(proposition);
            i++;
        }
        createAgendaAndRuleAndPropositions(KSKRMSReplaceWithPropertyFile.AGENDA11,
                contextRepository.getContextByNameAndNamespace(
                        KSKRMSReplaceWithPropertyFile.CONTEXT_STUD_ELIGIBILITY, namespace),
                null, namespace, propositionList.toArray(new PropositionParametersBuilder[propositionList.size()]));
    }

    private void setupPropertyFile() {
        propertyUtil = new PropertiesFilterFactoryBean();
        propertyUtil.setPropertyFile("classpath:KSKRMSDataToLoad.properties");

    }

    private void setupRequiredServices() {
        // Setup all the services...
//        termBoService = KrmsRepositoryServiceLocator.getTermBoService();
//        agendaBoService = KrmsRepositoryServiceLocator.getAgendaBoService();
//        contextRepository = KrmsRepositoryServiceLocator.getContextBoService();
//        ruleBoService = KrmsRepositoryServiceLocator.getRuleBoService();
//        krmsTypeRepository = KrmsRepositoryServiceLocator
//                .getKrmsTypeRepositoryService();
//        functionBoService = KrmsRepositoryServiceLocator.getBean("functionRepositoryService");
    }

   /**
    * Service methods setup
    */
    public void setKrmsTypeDefinition(KrmsTypeDefinition krmsTypeDefinition) {
        this.krmsTypeForContext = krmsTypeDefinition;
    }

    public void setContextRepository(ContextBoService contextRepository) {
        this.contextRepository = contextRepository;
    }

    public void setKrmsTypeRepository(KrmsTypeRepositoryService krmsTypeRepository) {
        this.krmsTypeRepository = krmsTypeRepository;
    }

    public void setAgendaBoService(AgendaBoService agendaBoService) {
        this.agendaBoService = agendaBoService;
    }

    public void setRuleBoService(RuleBoService ruleBoService) {
        this.ruleBoService = ruleBoService;
    }

    public void setTermBoService(TermBoService termBoService) {
        this.termBoService = termBoService;
    }

    public void setBoService(BusinessObjectService boService) {
        this.boService = boService;
    }

    private void writeError(Exception e) {
        System.out.println("Error loading " + propertyUtil.getPrefix() + " from Propertyfile : " + propertyUtil.getPropertyFile() );
        System.out.println(e.getMessage());
    }
    private String getPropertyValue(String propertyPrefix) {

        Properties properties = getProperties(propertyPrefix);
        Enumeration elements = properties.elements();
        while (elements.hasMoreElements()) {
            return (String) elements.nextElement();
        }
        return null;
    }

    private Properties getProperties(String propertyPrefix) {
        propertyUtil.setPrefix(propertyPrefix);
        Properties properties = null;
        try {
            properties = (Properties) propertyUtil.getObject();
        } catch (Exception e) {
            writeError(e);
        }
        return properties;
    }

    private PropositionParametersBuilder createProposition(String termUsed,
                                                           String PropositionConstant, String Operator) {
        Map<String, PropositionParameterType> propositionsMap;
        propositionsMap = new HashMap<String, PropositionParameterType>();
        propositionsMap.put(krmsTermLookup(termUsed).getId(),
                PropositionParameterType.TERM);
        propositionsMap.put(PropositionConstant,
                PropositionParameterType.CONSTANT);
        propositionsMap.put(Operator, PropositionParameterType.OPERATOR);
        PropositionParametersBuilder proposition = buildKRMSProposition(propositionsMap);
        return proposition;
    }

    private void createAgendaAndRuleAndPropositions(String agendaName,
                                                    ContextDefinition contextDefinition, String eventName,
                                                    String nameSpace, PropositionParametersBuilder...  proposition) {
        // Create Agenda...
        AgendaDefinition agendaDef = createKRMSAgendaDefinition(agendaName,
                contextDefinition);

        // Create AgendaItems
        AgendaItemDefinition.Builder agendaItemBuilder1 = AgendaItemDefinition.Builder
                .create(null, agendaDef.getId());

        // Create Rules
        String ruleDefinitionID = createKRMSRuleDefinition(nameSpace,
                agendaName + "::Rule1", contextDefinition, LogicalOperator.OR,
                proposition).getId();

        agendaItemBuilder1.setRuleId(ruleDefinitionID);

        // Create Agenda Items on DB
        AgendaItemDefinition agendaItem1 = agendaBoService
                .createAgendaItem(agendaItemBuilder1.build());
        AgendaDefinition.Builder agendaDefBuilder1 = AgendaDefinition.Builder
                .create(agendaDef);
        // agendaDefBuilder1.setAttributes(Collections.singletonMap("Event",
        // eventName));
        agendaDefBuilder1.setFirstItemId(agendaItem1.getId());
        agendaDef = agendaDefBuilder1.build();
        // Update Agenda with the AgendaItems id...
        agendaBoService.updateAgenda(agendaDef);

    }

    private AgendaDefinition createKRMSAgendaDefinition(String agendaName,
                                                        ContextDefinition contextDefinition) {
        AgendaDefinition agendaDef = agendaBoService
                .getAgendaByNameAndContextId(agendaName,
                        contextDefinition.getId());
        if (krmsTypeDefinition == null) {
            krmsTypeDefinition = getKSKRMSType(namespace);
        }
        if (agendaDef == null) {
            agendaDef = AgendaDefinition.Builder.create(null, agendaName,
                    krmsTypeDefinition.getId(), contextDefinition.getId())
                    .build();
            agendaDef = agendaBoService.createAgenda(agendaDef);

        }
        return agendaDef;
    }

    private PropositionParametersBuilder buildKRMSProposition(
            Map<String, PropositionParameterType> propositionsMap) {
        int orderOfEntries = 0;
        PropositionParametersBuilder params1 = new PropositionParametersBuilder();
        Set<Entry<String, PropositionParameterType>> allValues = propositionsMap
                .entrySet();

        for (Entry<String, PropositionParameterType> entry : allValues) {
            System.out.println(entry.getKey() + " " + entry.getValue());
            if (entry.getValue() == PropositionParameterType.TERM
                    && orderOfEntries == 0) {
                params1.add(entry.getKey(), entry.getValue());
                orderOfEntries = 1;
            } else if (entry.getValue() == PropositionParameterType.CONSTANT
                    && orderOfEntries == 1) {
                params1.add(entry.getKey(), entry.getValue());
                orderOfEntries = 2;
            } else if (entry.getValue() == PropositionParameterType.OPERATOR
                    && orderOfEntries == 2) {
                params1.add(entry.getKey(), entry.getValue());
                orderOfEntries = 3;
            }
        }
        if (orderOfEntries == 1) {
            for (Entry<String, PropositionParameterType> entry : allValues) {
                System.out.println(entry.getKey() + " " + entry.getValue());
                if (entry.getValue() == PropositionParameterType.CONSTANT
                        && orderOfEntries == 1) {
                    params1.add(entry.getKey(), entry.getValue());
                    orderOfEntries = 2;
                } else if (entry.getValue() == PropositionParameterType.OPERATOR
                        && orderOfEntries == 2) {
                    params1.add(entry.getKey(), entry.getValue());
                    orderOfEntries = 3;
                }
            }
        }
        if (orderOfEntries == 2) {
            for (Entry<String, PropositionParameterType> entry : allValues) {
                System.out.println(entry.getKey() + " " + entry.getValue());
                if (entry.getValue() == PropositionParameterType.OPERATOR
                        && orderOfEntries == 2) {
                    params1.add(entry.getKey(), entry.getValue());
                    orderOfEntries = 3;
                }
            }
        }
        return params1;
    }

    private PropositionDefinition.Builder createKRMSPropositionDefinition(
            String propDescription, PropositionParametersBuilder params,
            RuleDefinition parentRule) {
        // Proposition for rule 2
        PropositionDefinition.Builder propositionDefBuilder1 = PropositionDefinition.Builder
                .create(null, PropositionType.SIMPLE.getCode(),
                        parentRule.getId(), null /*
												 * type code is only for custom
												 * props
												 */,
                        Collections.<PropositionParameter.Builder> emptyList());
        propositionDefBuilder1.setDescription(propDescription);

        // PropositionParams for rule 2
        List<PropositionParameter.Builder> propositionParams1 = params.build();

        // set the parent proposition so the builder will not puke
        for (PropositionParameter.Builder propositionParamBuilder : propositionParams1) {
            propositionParamBuilder.setProposition(propositionDefBuilder1);
        }

        propositionDefBuilder1.setParameters(propositionParams1);

        return propositionDefBuilder1;
    }

    private RuleDefinition createKRMSRuleDefinition(String nameSpace,
                                                    String ruleName, ContextDefinition contextDefinition,
                                                    LogicalOperator operator, PropositionParametersBuilder... pbs) {
        // Create the Rule
        RuleDefinition.Builder ruleDefBuilder = RuleDefinition.Builder.create(
                null, ruleName, nameSpace, null, null);
        ruleDefBuilder.setDescription(ruleName + "::Description");

        //
        RuleDefinition ruleDef = ruleBoService.getRuleByNameAndNamespace(
                ruleName, namespace);
        //
        if (ruleDef == null) {
            ruleDef = ruleBoService.createRule(ruleDefBuilder.build());
        }

        // Create the proposition for the rule
        PropositionDefinition.Builder parentProposition = PropositionDefinition.Builder
                .create(null, PropositionType.COMPOUND.getCode(),
                        ruleDef.getId(), null, null);
        parentProposition
                .setCompoundComponents(new ArrayList<PropositionDefinition.Builder>());

        if (operator != null) {
            parentProposition.setCompoundOpCode(operator.getCode());
        }

        ruleDefBuilder = RuleDefinition.Builder.create(ruleDef);

        //
        ArrayList<PropositionDefinition.Builder> newComponentList = new ArrayList<PropositionDefinition.Builder>();
        for (PropositionParametersBuilder params : pbs) {

            StringBuilder propositionNameBuilder = new StringBuilder(
                    "Proposition");

            propositionNameBuilder.append("::");
            for (Object[] param : params.params) {
                propositionNameBuilder.append(param[0].toString());
                propositionNameBuilder.append("--");
            }

            PropositionDefinition.Builder propositionBuilder = createKRMSPropositionDefinition(
                    propositionNameBuilder.toString(), params, ruleDef);

            if (pbs.length > 1) {
                // add it to the compound prop
                // List<org.kuali.rice.krms.api.repository.proposition.PropositionDefinition.Builder>
                // compoundComponents =
                // parentProposition.getCompoundComponents();
                newComponentList.add(propositionBuilder);
            } else {
                // if there is only one proposition to build, make it the parent
                parentProposition = propositionBuilder;
            }

        }
        if (newComponentList.size() > 0) {
            parentProposition.setCompoundComponents(newComponentList);

        }
        ruleDefBuilder.setProposition(parentProposition);
        ruleDef = ruleDefBuilder.build();
        ruleBoService.updateRule(ruleDef); // Creating the krms_prop_parm_t &
        // krms_prop_t

        // Action
        // ActionDefinition.Builder actionDefBuilder1 =
        // ActionDefinition.Builder.create(null, ruleName + "::TestAction",
        // nameSpace, createKrmsActionTypeDefinition(nameSpace).getId(),
        // ruleDef1.getId(), 1);
        // ActionDefinition actionDef1 =
        // actionBoService.createAction(actionDefBuilder1.build());

        return ruleDef;
    }

    private TermDefinition krmsTermLookup(String termName) {
        // this may be called more than once, we only want to create one though
        Map<String, String> queryArgs = new HashMap<String, String>();
        queryArgs.put("desc_txt", termName);
        TermBo termBo = getBoService()
                .findByPrimaryKey(TermBo.class, queryArgs);
        if (termBo != null) {
            return TermBo.to(termBo);
        }
        return null;
    }

    private static class PropositionParametersBuilder {

        // poor OOD but this is quick and dirty :-P
        private List<Object[]> params = new ArrayList<Object[]>();

        public PropositionParametersBuilder add(String value,
                                                PropositionParameterType type) {
            if (type == null)
                throw new IllegalArgumentException("type must not be null");
            params.add(new Object[] { value, type });
            return this;
        }

        public List<PropositionParameter.Builder> build() {
            int seqCounter = 0;

            List<PropositionParameter.Builder> results = new ArrayList<PropositionParameter.Builder>();

            for (Object[] param : params) {
                results.add(PropositionParameter.Builder.create(null, null,
                        (String) param[0],
                        ((PropositionParameterType) param[1]).getCode(),
                        seqCounter++));
            }

            return results;
        }
    }

    // methods used
    private KrmsTypeDefinition getKSKRMSType(String nameSpace) {
        KrmsTypeDefinition krmsContextTypeDefinition = krmsTypeRepository
                .getTypeByName(nameSpace, KSKRMSReplaceWithPropertyFile.KS_AGENDA_TYPE);

        if (krmsContextTypeDefinition == null) {

            KrmsTypeDefinition.Builder krmsContextTypeDefnBuilder = KrmsTypeDefinition.Builder
                    .create(KSKRMSReplaceWithPropertyFile.KS_AGENDA_TYPE, nameSpace);
            krmsContextTypeDefnBuilder.setServiceName("myKSService");

            // TODO KSKRMS not sure where the Attributes fit in and how they
            // link up
            // int contextAttrSequenceIndex = 0;
            // List<KrmsTypeAttribute.Builder> contextAttributeBuilders = new
            // ArrayList<KrmsTypeAttribute.Builder>();
            // for (KrmsAttributeDefinition attrDef :
            // ksLumAttributeDefinitions.values()) {
            // contextAttributeBuilders.add(KrmsTypeAttribute.Builder.create(null,
            // attrDef.getId(),
            // contextAttrSequenceIndex));
            // contextAttrSequenceIndex += 1;
            //
            // }
            // krmsContextTypeDefnBuilder.setAttributes(contextAttributeBuilders);

            krmsContextTypeDefinition = krmsTypeRepository
                    .createKrmsType(krmsContextTypeDefnBuilder.build());
            return krmsContextTypeDefinition;
        }

        return krmsContextTypeDefinition;
    }

    protected BusinessObjectService getBoService() {
        return KRADServiceLocator.getBusinessObjectService();
    }

}