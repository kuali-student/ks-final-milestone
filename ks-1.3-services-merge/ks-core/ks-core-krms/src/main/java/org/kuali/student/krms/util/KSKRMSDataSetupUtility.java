package org.kuali.student.krms.util;

import org.kuali.rice.core.framework.resourceloader.SpringResourceLoader;
import org.kuali.rice.krad.service.BusinessObjectService;
import org.kuali.rice.krms.api.repository.context.ContextDefinition;
import org.kuali.rice.krms.api.repository.term.TermResolverDefinition;
import org.kuali.rice.krms.api.repository.term.TermSpecificationDefinition;
import org.kuali.rice.krms.api.repository.type.KrmsTypeDefinition;
import org.kuali.rice.krms.api.repository.type.KrmsTypeRepositoryService;
import org.kuali.rice.krms.impl.repository.AgendaBoService;
import org.kuali.rice.krms.impl.repository.ContextBoService;
import org.kuali.rice.krms.impl.repository.RuleBoService;
import org.kuali.rice.krms.impl.repository.TermBoService;
import org.kuali.rice.krms.impl.repository.TermSpecificationBo;
import org.kuali.student.common.util.PropertiesFilterFactoryBean;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;


/**
 * Created by IntelliJ IDEA.
 * User: NWUuser
 * Date: 2012/07/03
 * Time: 4:34 PM
 * To change this template use File | Settings | File Templates.
 */
public class KSKRMSDataSetupUtility {

    public static final String PROPERTY_namespace = "Namespace";
    public static final String PROPERTY_CONTEXT = "Context";
    public static final String PROPERTY_TERMSPEC = "TermSpec";
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

    public static void main(String [ ] args)
    {
        KSKRMSDataSetupUtility utility = new KSKRMSDataSetupUtility();
        utility.setupRequiredServices();
        utility.createKSKRMSData();
    }

    public void createKSKRMSData() {
        setupPropertyFile();
        namespace = getPropertyValue(PROPERTY_namespace);
        createKRMSContextFromPropertyFile();
        createKRMSTermSpecificationsFromPropertyFile();
    }

    private void createKRMSTermSpecificationsFromPropertyFile() {
        {
            Properties properties = getProperties(PROPERTY_TERMSPEC);
            //
            Enumeration elements = properties.elements();
            int i = 0;
            //
            while (elements.hasMoreElements()) {
                String termSpecValues =  (String) elements.nextElement();
                System.out.println(i + " - " + termSpecValues);

                String delims = ",+"; // use + to treat consecutive delims as one;
                                      // omit to treat consecutive delims separately
                String[] tokens = termSpecValues.split(delims);
                TermSpecificationDefinition termSpec = createKRMSTermSpecification(namespace,tokens[0],tokens[1],tokens[2]);
                // NOTE the term resolver must be defined as a spring bean under the name given here.
                createKRMSTermResolver(tokens[3], termSpec);
                i++;
            }
            System.out.println("Created " + i + " TermSpecification and TermResolvers for KS KRMS");
        }

    }

    public void createKRMSTermResolver(String termResolverName, TermSpecificationDefinition termSpecDefinition) {
        // KrmsType for TermResolver
        KrmsTypeDefinition krmsTermResolverTypeDefinition = getKSKRMSType(KSKRMSReplaceWithPropertyFile.KSNAMESPACE, KSKRMSReplaceWithPropertyFile.KS_TERM_RESOLVER_TYPE, "ksKRMSTermResolverTypeService");

        // TermResolver
        TermResolverDefinition termResolverDef =
                TermResolverDefinition.Builder.create(null, KSKRMSReplaceWithPropertyFile.KSNAMESPACE, termResolverName, krmsTermResolverTypeDefinition.getId(),
                        TermSpecificationDefinition.Builder.create(termSpecDefinition),
                        null,
                        null,
                        null).build();
        termResolverDef = termBoService.createTermResolver(termResolverDef);
    }

    private TermSpecificationDefinition createKRMSTermSpecification(
            String namespace, String termSpecName, String descr, String termType) {
        Map<String, String> queryArgs = new HashMap<String, String>();
        queryArgs.put("namespace", namespace);
        queryArgs.put("name", termSpecName);
        TermSpecificationBo termSpecBo = boService.findByPrimaryKey(
                TermSpecificationBo.class, queryArgs);

        // TODO Figure out how to set the Description
        TermSpecificationDefinition termSpec = null;
        if (termSpecBo == null) {

            TermSpecificationDefinition.Builder termSpecDefBuilder = TermSpecificationDefinition.Builder
                    .create(null, termSpecName, namespace, termType);
            termSpecDefBuilder.setDescription(descr);

            termSpec = termSpecDefBuilder.build();


            termSpec = termBoService.createTermSpecification(termSpec);

        } else {
            termSpec = termSpecBo.to(termSpecBo);
        }
        System.out.println("Elmien :     " + termSpec.getDescription()
                + "     " + termSpec.getName());
        return termSpec;
    }

    public void createKRMSContextFromPropertyFile() {
        getContextType();
        Properties context = getProperties(PROPERTY_CONTEXT);
        //
        Enumeration elements = context.elements();
        int i = 0;
        //
        while (elements.hasMoreElements()) {
            String contextValue =  (String) elements.nextElement();
            System.out.println(i + " - " + contextValue);
            createContext(namespace, contextValue, krmsTypeForContext);
            i++;
        }
        System.out.println("Created " + i + " context for KS KRMS");
    }

    public void createKRMSTermSpecificationFromPropertyFile() {
        getContextType();
        Properties termSpecificationProperites = getProperties(PROPERTY_TERMSPEC);
        //
        Enumeration elements = termSpecificationProperites.elements();
        int i = 0;
        //
        while (elements.hasMoreElements()) {
            String contextValue =  (String) elements.nextElement();
            System.out.println(i + " - " + contextValue);
            createContext(namespace, contextValue, krmsTypeForContext);
            i++;
        }
        System.out.println("Created " + i + " context for KS KRMS");
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

    private void getContextType() {
        
        krmsTypeForContext = getKSKRMSType(namespace, KSKRMSReplaceWithPropertyFile.KS_AGENDA_TYPE, "testAgendaTypeService");
    }

    public ContextDefinition createContext(String namespace, String name,
                                           KrmsTypeDefinition krmsContextTypeDefinition) {

        ContextDefinition contextDefinition = contextRepository.getContextByNameAndNamespace(name, namespace);
        if (contextDefinition == null) {
            ContextDefinition.Builder contextBuilder = ContextDefinition.Builder
                    .create(namespace, name);

            contextBuilder.setTypeId(krmsContextTypeDefinition.getId());
            contextDefinition = contextBuilder.build();

            contextDefinition = contextRepository.createContext(contextDefinition);

        }
        return contextDefinition;
    }


    private KrmsTypeDefinition getKSKRMSType(String namespace, String typeName, String typeServiceName) {
        KrmsTypeDefinition krmsAgendaType = krmsTypeRepository
                .getTypeByName(namespace, typeName);

        if (krmsAgendaType == null) {

            KrmsTypeDefinition.Builder krmsAgendaTypeDefinition = KrmsTypeDefinition.Builder
                    .create(typeName, namespace);
            krmsAgendaTypeDefinition.setServiceName(typeServiceName);

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

            krmsAgendaType = krmsTypeRepository
                    .createKrmsType(krmsAgendaTypeDefinition.build());
            return krmsAgendaType;
        }

        return krmsAgendaType;
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
}
