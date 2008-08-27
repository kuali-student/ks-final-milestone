package org.kuali.student.brms.repository.wsdl;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.namespace.QName;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.Service;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.kuali.student.rules.repository.RuleRepositoryService;
import org.kuali.student.rules.repository.drools.rule.RuleSetFactory;
import org.kuali.student.rules.repository.rule.RuleSet;
import org.kuali.student.poc.common.test.spring.AbstractServiceTest;
import org.kuali.student.poc.common.test.spring.Client;

public class RuleRepositoryServiceTest extends AbstractServiceTest {

    private static String serviceURL = "http://localhost:8080/brms-ws/services/RuleRepositoryService";
    //private static String serviceURL = "http://137.82.182.158:8080/RuleEngineRepositoryService/RuleEngineRepositoryService";
    private static String namespace = "http://student.kuali.org/wsdl/brms/RuleRepository";
    private static String serviceName = "RuleRepositoryService";
    private static String serviceInterface = RuleRepositoryService.class.getName();

    //private final static String businessRule = "A0";
    private final static String businessRule = "A0*B4+(C*D)";
    
    @Client(value="org.kuali.student.rules.repository.drools.RuleRepositoryServiceDroolsImpl", port="8181")
    private RuleRepositoryService service; 
    
	//private ServiceFactory sf = new ServiceFactory();
	
    @BeforeClass
    public static void setUpOnce() throws Exception {
    }

    @AfterClass
    public static void tearDownOnce() throws Exception {
    }

    @Before
    /*public void setUp() throws Exception {
    	service = (RuleRepositoryService) sf.getPort(
                serviceURL + "?wsdl", namespace, serviceName, serviceInterface, serviceURL);
    }*/
    
    @After
    public void tearDown() throws Exception {
    }
    
    private RuleSet createRuleSet() {
        RuleSet ruleSet = RuleSetFactory.getInstance().createRuleSet("TestName", "Test description");
        return ruleSet;
    }
    
    /*private List<org.kuali.student.rules.util.Constraint> getConstraints() {
        List<org.kuali.student.rules.util.Constraint> list = new ArrayList<org.kuali.student.rules.util.Constraint>();
        
        list.add( createConstraint( "A0: A constraintID from DB") );
        list.add( createConstraint( "B4: A constraintID from DB") );
        list.add( createConstraint( "C: A constraintID from DB") );
        list.add( createConstraint( "D: A constraintID from DB") );
        
        return list;
    }
    
    private org.kuali.student.rules.util.Constraint createConstraint( String constraintID ) {
        ConstraintStrategy cs = new MockConstraint();
        cs.setConstraintID(constraintID);
        org.kuali.student.rules.util.Constraint c = 
            new org.kuali.student.rules.util.Constraint(cs); 
        return c;
    }*/
    
    /*@Test
    public void testCreateRuleSet() throws Exception {
        RuleSet ruleSet = service.createRuleSet( createRuleSet() );
        assertNotNull( ruleSet );
        //service.removeRuleSet( uuid );
    }

    @Test
    public void testRemoveRuleSet() throws Exception {
        String uuid = service.createRuleSet( createRuleSet() );
        assertNotNull( uuid );
        service.removeRuleSet( uuid );
        assertTrue( true );
    }*/

    @Test
    public void testGetRuleSet() throws Exception {
        String uuid = null;
        try {
        	RuleSet ruleSet1 = service.createRuleSet( createRuleSet() );
            assertNotNull( ruleSet1 );
            
            RuleSet ruleSet2 = service.loadRuleSet(ruleSet1.getUUID());
            assertNotNull( ruleSet2 );
            System.out.println( "Rule Set: " + ruleSet2.toString());
        } finally {
            //service.removeRuleSet( uuid );
        }
    }

    /*@Test
    public void testGetRuleSetAndExecute() throws Exception {
        String uuid = null;
        try {
            uuid = service.createRuleSet( createRuleSet() );
            assertNotNull( uuid );
            
            BinaryRuleSetObject bin = service.getRuleSet(uuid);
            assertNotNull( bin );
            assertNotNull( bin.getObject() );
            org.drools.rule.Package pkg = (org.drools.rule.Package) DroolsUtil.getInstance().getPackage( bin.getObject() );
            assertNotNull( pkg );
    
            Propositions prop = new Propositions();
            Propositions.init(businessRule);
            Propositions.setProposition("xxx", false);
            
            //DroolsTestUtil.getInstance().executeRule(pkg, new Object[]{prop, getConstraints().toArray()});
        } finally {
            service.removeRuleSet( uuid );
        }
    }

    @Test
    public void testGetRuleSet_MultipleTimes() throws Exception {
        String uuid = service.createRuleSet( createRuleSet() );
        assertNotNull( uuid );
        
        final int INTERATIONS = 1000;
        long beginTime = System.currentTimeMillis();
        for( int i=0; i<INTERATIONS; i++ ) {
            BinaryRuleSetObject bin = service.getRuleSet(uuid);
            assertNotNull( bin );
            assertNotNull( bin.getObject() );
        }
        long endTime = System.currentTimeMillis();
        double exeTime = (double) ( endTime - beginTime ) / 1000;
        System.out.println( "Execution Time: " + exeTime );

        service.removeRuleSet( uuid );
    }
    
    private class MockConstraint extends AbstractProposition<T> {
        
        @Override
        protected void cacheAdvice(String format, Object... args) {
            String propVar = (String) args[2];
            Propositions.setFailureMessage(propVar, "Some good failure message!");
            
        }

        @Override
        public Boolean apply(String propVar) {
            return ( getConstraintID().startsWith(propVar) ? true : false );
            //return false;
        }
    }*/






    /*public class ServiceFactory {
    	
    	String	wsdlURL,
    			namespace,
    			serviceName,
    			serviceInterface,
    			endpointAddress;
    	
    	public ServiceFactory() {}
    		
    	public void setWsdlURL(String wsdlURL) {
    		this.wsdlURL = wsdlURL;
    	}

    	public void setNamespace(String namespace) {
    		this.namespace = namespace;
    	}

    	public void setServiceName(String serviceName) {
    		this.serviceName = serviceName;
    	}

    	public void setServiceInterface(String serviceInterface) {
    		this.serviceInterface = serviceInterface;
    	}

    	public void setEndpointAddress(String endpointAddress) {
    		this.endpointAddress = endpointAddress;
    	}

    	
    	public Service getService() throws Exception {
    		return getService(this.wsdlURL, this.namespace, this.serviceName);
    	}
    		
    	public Service getService(String wsdlStr, String namespace,
    			String serviceName) throws Exception {
    		URL url = null;

    		url = getLocalUrl(wsdlStr);
    		if (url == null)
    			url = new URL(wsdlStr);

    		QName sName = new QName(namespace, serviceName);
    		Service s = Service.create(url, sName);
    		
    		
    		
    		return s;
    	}
    			
    	public Object getPort() throws Exception {
    		return getPort(this.wsdlURL, this.namespace, 
    				this.serviceName, this.serviceInterface, this.endpointAddress);
    	}
    	public Object getPort(String wsdlStr, String namespace,
    			String serviceName, String serviceInterface, String endpointAddress ) throws Exception {
    		
    		Object oRet = null;
    		
    		Service s = getService(wsdlStr, namespace , serviceName);
    		
    		oRet = s.getPort(Class.forName(serviceInterface));
    		
    		if(endpointAddress != null && !"".equals(endpointAddress)){									
    			((BindingProvider)oRet).getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY,
               endpointAddress);
    		}
    		
    		return oRet;
    	}
    	
    	private URL getLocalUrl(String file) throws Exception {

    		return ServiceFactory.class.getClassLoader().getResource(file);

    	}

    }*/

}
