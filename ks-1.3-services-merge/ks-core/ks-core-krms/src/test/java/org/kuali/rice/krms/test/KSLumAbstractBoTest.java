/*
 * Copyright 2006-2011 The Kuali Foundation
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
package org.kuali.rice.krms.test;

import org.apache.bcel.generic.NEW;
import org.kuali.rice.krad.service.BusinessObjectService;
import org.kuali.rice.krad.service.KRADServiceLocator;
import org.kuali.rice.krms.api.repository.context.ContextDefinition;
import org.kuali.rice.krms.api.repository.term.TermSpecificationDefinition;
import org.kuali.rice.krms.api.repository.type.KrmsAttributeDefinition;
import org.kuali.rice.krms.api.repository.type.KrmsAttributeDefinition.Builder;
import org.kuali.rice.krms.api.repository.type.KrmsTypeAttribute;
import org.kuali.rice.krms.api.repository.type.KrmsTypeDefinition;
import org.kuali.rice.krms.api.repository.type.KrmsTypeRepositoryService;
import org.kuali.rice.krms.impl.repository.ContextAttributeBo;
import org.kuali.rice.krms.impl.repository.ContextBoService;
import org.kuali.rice.krms.impl.repository.KrmsAttributeDefinitionService;
import org.kuali.rice.krms.impl.repository.TermBoService;
import org.kuali.rice.test.BaselineTestCase.BaselineMode;
import org.kuali.rice.test.BaselineTestCase.Mode;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@BaselineMode(Mode.ROLLBACK)
public abstract class KSLumAbstractBoTest extends KRMSTestCase {
	
    private static final String ATTR_NUMBER_OF_CREDITS = "Number of Credits";
	private static final String ATTR_DEPT_ORG_NUMBER = "Dept Org Number";
	private static final String ATTR_PROPOSED_COURSE = "Proposed Course";
	private static final String ATTR_APPROVED_COURSE = "Approved Course";
	protected ContextBoService contextRepository;
    protected KrmsTypeRepositoryService krmsTypeRepository;
    protected KrmsAttributeDefinitionService krmsAttributeDefinitionService;
    protected Map<String, KrmsAttributeDefinition> ksLumAttributeDefinitions = new HashMap<String, KrmsAttributeDefinition>();
    
    private TermBoService termBoService;

    protected BusinessObjectService getBoService() {
		return KRADServiceLocator.getBusinessObjectService();
	}

    protected ContextDefinition createContextDefinition(String nameSpace, String name,
            Map<String, String> contextAttributes) {
        // Attributes for context;
        List<KrmsTypeAttribute.Builder> contextAttributeBuilders = new ArrayList<KrmsTypeAttribute.Builder>();
        int contextAttrSequenceIndex = 0;

        List<KrmsAttributeDefinition> attributeDefintions = new ArrayList<KrmsAttributeDefinition>();

        createAllLumAttributeDefinitions(nameSpace);
        
        KrmsTypeDefinition krmsContextTypeDefinition = createContextType(nameSpace);

        ContextDefinition contextDefinition = createContext(nameSpace, name,
				krmsContextTypeDefinition);

        // Context Attribute
        // TODO: do this fur eel
        for (KrmsAttributeDefinition contextTypeAttributeDefinition : attributeDefintions) {
            ContextAttributeBo contextAttribute = new ContextAttributeBo();
            contextAttribute.setAttributeDefinitionId(contextTypeAttributeDefinition.getId());
            contextAttribute.setContextId(contextDefinition.getId());
            contextAttribute.setValue(contextAttributes.get(contextTypeAttributeDefinition.getName()));
            getBoService().save(contextAttribute);
        }

        return contextDefinition;
    }

	public ContextDefinition createContext(String nameSpace, String name,
			KrmsTypeDefinition krmsContextTypeDefinition) {
		// Context
        ContextDefinition.Builder contextBuilder = ContextDefinition.Builder.create(nameSpace, name);
// TODO NINA somthing wrong with Type       contextBuilder.setTypeId(krmsContextTypeDefinition.getId());
        contextBuilder.setTypeId("T1003");
        ContextDefinition contextDefinition = contextBuilder.build();
        contextDefinition = contextRepository.createContext(contextDefinition);
		return contextDefinition;
	}

	protected KrmsTypeDefinition createContextType(String nameSpace) {
		// KrmsType for context
        KrmsTypeDefinition.Builder krmsContextTypeDefnBuilder = KrmsTypeDefinition.Builder.create("KSLumContextType-Course", nameSpace);   
        krmsContextTypeDefnBuilder.setServiceName("myKSService");
        //
        int contextAttrSequenceIndex = 0;
        List<KrmsTypeAttribute.Builder> contextAttributeBuilders = new ArrayList<KrmsTypeAttribute.Builder>();
        for (KrmsAttributeDefinition attrDef : ksLumAttributeDefinitions.values()) {
            contextAttributeBuilders.add(KrmsTypeAttribute.Builder.create(null, attrDef.getId(),
                    contextAttrSequenceIndex));
            contextAttrSequenceIndex += 1;
			
		}
        krmsContextTypeDefnBuilder.setAttributes(contextAttributeBuilders);
        //
        KrmsTypeDefinition krmsContextTypeDefinition = krmsContextTypeDefnBuilder.build();
        krmsContextTypeDefinition = krmsTypeRepository.createKrmsType(krmsContextTypeDefinition);
		return krmsContextTypeDefinition;
	}
	


	protected void createAllLumAttributeDefinitions(
			String nameSpace) {
		// Create all the attributes...
        createNumberOfCreditsAttributeDefinition(nameSpace);
        createDeptOrgNumberAttributeDefinition(nameSpace);
        createProposedCourseAttributeDefinition(nameSpace);
        createApprovedCourseAttributeDefinition(nameSpace);
	}

	private void createNumberOfCreditsAttributeDefinition(
			String nameSpace) {
		KrmsAttributeDefinition.Builder numberOfCreditsBuilder = KrmsAttributeDefinition.Builder.create(null, ATTR_NUMBER_OF_CREDITS, nameSpace);
        numberOfCreditsBuilder.setLabel(numberOfCreditsBuilder.getName());
        numberOfCreditsBuilder.setDescription(numberOfCreditsBuilder.getName());
        //
		KrmsAttributeDefinition attributeDefinition = krmsAttributeDefinitionService.createAttributeDefinition(numberOfCreditsBuilder.build());
		ksLumAttributeDefinitions.put(attributeDefinition.getName(),attributeDefinition);
	}
	
	private void createDeptOrgNumberAttributeDefinition(
			String nameSpace) {
		KrmsAttributeDefinition.Builder deptOrgNumberBuilder = KrmsAttributeDefinition.Builder.create(null, ATTR_DEPT_ORG_NUMBER, nameSpace);
        deptOrgNumberBuilder.setLabel(deptOrgNumberBuilder.getName());
        deptOrgNumberBuilder.setDescription(deptOrgNumberBuilder.getName());
        //
		KrmsAttributeDefinition attributeDefinition = krmsAttributeDefinitionService.createAttributeDefinition(deptOrgNumberBuilder.build());
		ksLumAttributeDefinitions.put(attributeDefinition.getName(),attributeDefinition);
	}
	
	private void createProposedCourseAttributeDefinition(
			String nameSpace) {
		KrmsAttributeDefinition.Builder proposedCourseBuilder = KrmsAttributeDefinition.Builder.create(null, ATTR_PROPOSED_COURSE, nameSpace);
        proposedCourseBuilder.setLabel(proposedCourseBuilder.getName());
        proposedCourseBuilder.setDescription(proposedCourseBuilder.getName());
        //
		KrmsAttributeDefinition attributeDefinition = krmsAttributeDefinitionService.createAttributeDefinition(proposedCourseBuilder.build());
		ksLumAttributeDefinitions.put(attributeDefinition.getName(),attributeDefinition);
	}

	private void createApprovedCourseAttributeDefinition(
			String nameSpace) {
		KrmsAttributeDefinition.Builder approvedCourseBuilder = KrmsAttributeDefinition.Builder.create(null, ATTR_APPROVED_COURSE, nameSpace);
        approvedCourseBuilder.setLabel(approvedCourseBuilder.getName());
        approvedCourseBuilder.setDescription(approvedCourseBuilder.getName());
        //
		KrmsAttributeDefinition attributeDefinition = krmsAttributeDefinitionService.createAttributeDefinition(approvedCourseBuilder.build());
		ksLumAttributeDefinitions.put(attributeDefinition.getName(),attributeDefinition);
	}
	


//	private KrmsAttributeDefinition createAttributeDefinition(String nameSpace,
//			String attrName) {
//		
//		KrmsAttributeDefinition.Builder contextTypeAttributeDefnBuilder = KrmsAttributeDefinition.Builder.create(null, attrName, nameSpace);
//		contextTypeAttributeDefnBuilder.setLabel(attrName + " attribute label");
//		KrmsAttributeDefinition contextTypeAttributeDefinition = krmsAttributeDefinitionService.createAttributeDefinition(contextTypeAttributeDefnBuilder.build());
//		return contextTypeAttributeDefinition;
//	}

    protected KrmsTypeDefinition createKrmsGenericTypeDefinition(String nameSpace, String serviceName,
            String attributeDefinitionLabel, String attributeDefinitionName) {
        KrmsTypeDefinition krmsGenericTypeDefinition = krmsTypeRepository.getTypeByName(nameSpace, "Event");

        if (null == krmsGenericTypeDefinition) {

            // Attribute Defn for generic type;
            KrmsAttributeDefinition.Builder genericTypeAttributeDefnBuilder = KrmsAttributeDefinition.Builder.create(null,
                    attributeDefinitionName, nameSpace);
            genericTypeAttributeDefnBuilder.setLabel(attributeDefinitionLabel);
            KrmsAttributeDefinition genericTypeAttributeDefinition1 = krmsAttributeDefinitionService.createAttributeDefinition(genericTypeAttributeDefnBuilder.build());

            // Attr for generic type;
            KrmsTypeAttribute.Builder genericTypeAttrBuilder = KrmsTypeAttribute.Builder.create(null, genericTypeAttributeDefinition1.getId(), 1);

            // Can use this generic type for KRMS bits that don't actually rely on services on the bus at this point in time
            KrmsTypeDefinition.Builder krmsGenericTypeDefnBuilder = KrmsTypeDefinition.Builder.create("KrmsTestGenericType", nameSpace);
            krmsGenericTypeDefnBuilder.setServiceName(serviceName);
            krmsGenericTypeDefnBuilder.setAttributes(Collections.singletonList(genericTypeAttrBuilder));
            krmsGenericTypeDefinition = krmsTypeRepository.createKrmsType(krmsGenericTypeDefnBuilder.build());

        }

        return krmsGenericTypeDefinition;
    }
	
}
