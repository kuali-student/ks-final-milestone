package org.kuali.student.krms.termresolver;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.rice.core.api.lifecycle.Lifecycle;
import org.kuali.rice.core.framework.resourceloader.SpringResourceLoader;
import org.kuali.rice.krms.api.engine.TermResolver;
import org.kuali.rice.krms.api.repository.context.ContextDefinition;
import org.kuali.rice.krms.api.repository.term.TermResolverDefinition;
import org.kuali.rice.krms.framework.type.TermResolverTypeService;
import org.kuali.rice.krms.impl.repository.ContextBoService;
import org.kuali.rice.krms.impl.repository.KrmsRepositoryServiceLocator;
import org.kuali.rice.krms.impl.repository.TermBoService;
import org.kuali.rice.krms.impl.type.KrmsTypeResolver;


import org.kuali.student.r2.common.dto.AttributeInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.RichTextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.exceptions.*;
import org.kuali.student.r2.core.hold.service.HoldService;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.persistence.EntityExistsException;
import javax.xml.namespace.QName;

import java.util.ArrayList;
import java.util.List;
import org.springframework.util.CollectionUtils;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:ks-krms-test-context.xml"})
@Ignore
public class TestKSKRMSTermResolvers {
	private KrmsTypeResolver typeResolver;
	
//	@Resource(name = "termBoService")
//	private TermBoService termBoService;
	
	@Resource(name = "contextBoService")
	public ContextBoService contextRepository;
    public ContextInfo callContext = null;
    
    @Resource(name = "ksKRMSTermResolverTypeService")
    public KSTermResolverTypeService ksKRMSTermResolverTypeService;

    @Before
    public void setUp() {
//    	this.setClearTables(false);
        callContext = new ContextInfo();
//        termBoService = KrmsRepositoryServiceLocator.getTermBoService();
//        contextRepository = KrmsRepositoryServiceLocator.getContextBoService();

    }
    

    @Test
    public void testKSKRMSTermResolverTypeServiceNotNull() {
        assertNotNull(ksKRMSTermResolverTypeService);
//        assertNotNull(contextRepository);
//        assertNotNull(termBoService);
    }

    @Test
    public void testHello() {
        System.out.println("Test Hello");
        assertNotNull(ksKRMSTermResolverTypeService);
//        TermResolverDefinition termResolverDefinition = null;
//		this.ksKRMSTermResolverTypeService.loadTermResolver(termResolverDefinition);
    }
    
//    private void getAllTermResolverDefinitions(TermResolverDefinition contextDefinition) {
//		
//		List<TermResolverDefinition> termResolverDefs = 
//				termBoService.getTermResolversByNamespace(contextDefinition.getNamespace());
//			
//			List<TermResolver<?>> termResolvers = new ArrayList<TermResolver<?>>();
//
//			if (!CollectionUtils.isEmpty(termResolverDefs)) for (TermResolverDefinition termResolverDef : termResolverDefs) {
//				if (termResolverDef != null) {
//					TermResolver<?> termResolver = translateTermResolver(termResolverDef);
//					if (termResolver != null) termResolvers.add(termResolver);
//				}
//			}
//
//	}
    
	/**
	 * This method translates a {@link TermResolverDefinition} into a {@link TermResolver}
	 * 
	 * @param termResolverDef
	 * @return
	 */
//	private TermResolver<?> translateTermResolver(TermResolverDefinition termResolverDef) {
//		if (termResolverDef == null) {
//			throw new IllegalArgumentException("termResolverDef must not be null");
//		}
//		TermResolverTypeService termResolverTypeService = 
//			typeResolver.getTermResolverTypeService(termResolverDef);
//		
//		TermResolver<?> termResolver = termResolverTypeService.loadTermResolver(termResolverDef);
//		// TODO: log warning when termResolver comes back null? or throw exception?
//		return termResolver;
//	}


	private ContextDefinition getKRMSContext(String context) {
		return contextRepository.getContextByNameAndNamespace(
				context, "KR-RULE-TEST");
	}
	//
	// KRMS
	//
//	private Lifecycle getLoadApplicationLifecycle() {
//	    if (krmsTestResourceLoader == null) {
//	        krmsTestResourceLoader = new SpringResourceLoader(new QName("KRMSTestHarnessApplicationResourceLoader"), "classpath:KRMSTestHarnessSpringBeans.xml", null);
//	        krmsTestResourceLoader.setParentSpringResourceLoader(getTestHarnessSpringResourceLoader());
//	        getTestHarnessSpringResourceLoader().addResourceLoader(krmsTestResourceLoader);
//	    }
//    	return krmsTestResourceLoader;
//	}
}

