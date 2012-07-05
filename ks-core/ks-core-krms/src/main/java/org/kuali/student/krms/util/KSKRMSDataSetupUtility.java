package org.kuali.student.krms.util;

import org.kuali.rice.core.framework.resourceloader.SpringResourceLoader;
import org.kuali.rice.krms.api.repository.context.ContextDefinition;
import org.kuali.rice.krms.api.repository.type.KrmsTypeDefinition;
import org.kuali.rice.krms.api.repository.type.KrmsTypeRepositoryService;
import org.kuali.rice.krms.impl.repository.AgendaBoService;
import org.kuali.rice.krms.impl.repository.ContextBoService;
import org.kuali.rice.krms.impl.repository.FunctionBoServiceImpl;
import org.kuali.rice.krms.impl.repository.KrmsRepositoryServiceLocator;
import org.kuali.rice.krms.impl.repository.RuleBoService;
import org.kuali.rice.krms.impl.repository.TermBoService;

/**
 * Created by IntelliJ IDEA.
 * User: NWUuser
 * Date: 2012/07/03
 * Time: 4:34 PM
 * To change this template use File | Settings | File Templates.
 */
public class KSKRMSDataSetupUtility {

    KrmsTypeDefinition krmsTypeDefinition;

    private ContextBoService contextRepository;
    private KrmsTypeRepositoryService krmsTypeRepository;
    private AgendaBoService agendaBoService;
    private RuleBoService ruleBoService;
    private FunctionBoServiceImpl functionBoService;
    private TermBoService termBoService;
    private SpringResourceLoader krmsTestResourceLoader;

    public static void main(String [ ] args)
    {
        KSKRMSDataSetupUtility utility = new KSKRMSDataSetupUtility();
        utility.setupRequiredServices();
        utility.createAllContexts();


    }

    private void setupRequiredServices() {
        // Setup all the services...
        termBoService = KrmsRepositoryServiceLocator.getTermBoService();
        agendaBoService = KrmsRepositoryServiceLocator.getAgendaBoService();
        contextRepository = KrmsRepositoryServiceLocator.getContextBoService();
        ruleBoService = KrmsRepositoryServiceLocator.getRuleBoService();
        krmsTypeRepository = KrmsRepositoryServiceLocator
                .getKrmsTypeRepositoryService();
        functionBoService = KrmsRepositoryServiceLocator.getBean("functionRepositoryService");
    }

    public void createAllContexts() {
        String nameSpace = KSKRMSReplaceWithPropertyFile.KSNAMESPACE;
        // Create all the contexts...
        krmsTypeDefinition = getKSKRMSType(nameSpace, KSKRMSReplaceWithPropertyFile.KS_AGENDA_TYPE, "testAgendaTypeService");
        createContext(nameSpace, KSKRMSReplaceWithPropertyFile.CONTEXT_ANTI_REQUISITE,
                krmsTypeDefinition);
        createContext(nameSpace, KSKRMSReplaceWithPropertyFile.CONTEXT_CORE_REQUISITE,
                krmsTypeDefinition);
        createContext(nameSpace, KSKRMSReplaceWithPropertyFile.CONTEXT_COURSE_RESTRICTS,
                krmsTypeDefinition);
        createContext(nameSpace,
                KSKRMSReplaceWithPropertyFile.CONTEXT_RECOMMENDED_PREPARATION,
                krmsTypeDefinition);
        createContext(nameSpace, KSKRMSReplaceWithPropertyFile.CONTEXT_REPEATED_CREDITS,
                krmsTypeDefinition);
        createContext(nameSpace,
                KSKRMSReplaceWithPropertyFile.CONTEXT_STUD_ELIGIBILITY, krmsTypeDefinition);

    }

    public ContextDefinition createContext(String nameSpace, String name,
                                           KrmsTypeDefinition krmsContextTypeDefinition) {

        ContextDefinition contextDefinition = contextRepository.getContextByNameAndNamespace(name, nameSpace);
        if (contextDefinition == null) {
            ContextDefinition.Builder contextBuilder = ContextDefinition.Builder
                    .create(nameSpace, name);

            contextBuilder.setTypeId(krmsContextTypeDefinition.getId());
            contextDefinition = contextBuilder.build();

            contextDefinition = contextRepository.createContext(contextDefinition);

        }
        return contextDefinition;
    }


    private KrmsTypeDefinition getKSKRMSType(String nameSpace, String typeName, String typeServiceName) {
        KrmsTypeDefinition krmsAgendaType = krmsTypeRepository
                .getTypeByName(nameSpace, typeName);

        if (krmsAgendaType == null) {

            KrmsTypeDefinition.Builder krmsAgendaTypeDefinition = KrmsTypeDefinition.Builder
                    .create(typeName, nameSpace);
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
}
