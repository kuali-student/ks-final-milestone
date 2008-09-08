package org.kuali.student.poc.learningunit.luipersonrelation;
import javax.xml.namespace.QName;
import javax.xml.ws.WebServiceRef;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.kuali.student.poc.common.test.spring.AbstractServiceTest;
import org.kuali.student.poc.common.test.spring.Client;
import org.kuali.student.poc.common.ws.beans.JaxWsClientFactory;
import org.kuali.student.poc.common.ws.beans.JaxWsClientFactoryBean;
//import org.kuali.student.poc.personidentity.person.service.PersonService; @TODO use this when refactoring from v .03 -> .1
import org.kuali.student.poc.wsdl.personidentity.person.PersonService;
import org.kuali.student.poc.xsd.personidentity.person.dto.PersonDisplay;
import javax.xml.ws.soap.SOAPFaultException;

/**
 * Tests runtime behavior for wsdl errors. Uses PersonService but what's under test is jax-ws handling of missing wsdl elements.
 * Interface errors such as missing methods and missing @WebMethod tags are not tested because they can't be detected without a service impl.
 * @author garystruthers
 *
 */
public class TestPersonServiceWSDL {
	/**
	 * @see javax.xml.namespace.QName for namespaceURI, localPart
	 */
	private static final String namespaceURI = "http://student.kuali.org/poc/wsdl/personidentity/person";
	private static final String localPart = "PersonService";
	private static final String wsdlLocation = "classpath:wsdl/PersonService.wsdl";
	
	private static JaxWsClientFactory createClient() {
		JaxWsClientFactory client = new JaxWsClientFactoryBean();
		client.setServiceEndpointInterface(PersonService.class);
		client.setServiceName(new QName(namespaceURI,localPart));
		return client;
	}
	
	/*
	 * Calls a PersonService interface but there is no actual Service
	 * It confirms the service configuration works because client.getObject
	 * returns a PersonService
	 * @throws SOAPFaultException when fetchPersonDisplay is called, confirming
	 * the Service can't be reached
	 */
	@Test(expected=SOAPFaultException.class)
	public void checkGoodWSDL() throws Exception {
		JaxWsClientFactory client = createClient();
		client.setWsdlLocation(wsdlLocation);
			
		Object obj = client.getObject();
		assertNotNull(obj);
		PersonService personService = (PersonService)obj;
		PersonDisplay personDisplay = personService.fetchPersonDisplay("Person 666");
	}


	/*
	 * BadPersonService.wsdl is the same as PersonService.wsdl 
	 * except that wsdl:operation fetchPersonDisplay is commented out
	 * PersonService is not returned by client.getObject
	 * @throws NullPointerException, confirming a wsdl file missing an operation can't be used
	 * 
	 */
	@Test(expected=NullPointerException.class)
	public void checkMissingOpWSDL() throws Exception {
		JaxWsClientFactory client = createClient();
		client.setWsdlLocation("classpath:BadPersonService.wsdl"); //needs to be updated when PersonService.wsdl is
		
		Object obj = client.getObject();
	}
	/*
	 * BadBeanPersonService.wsdl is the same as PersonService.wsdl 
	 * except that wsdl:element validatePersonInfoForPersonTypeResponse is commented out
	 * PersonService is not returned by client.getObject
	 * @throws NullPointerException, confirming a wsdl file missing an element can't be used
	 * 
	 */	
	@Test(expected=NullPointerException.class)
	public void checkBadBeanWSDL() throws Exception {
		JaxWsClientFactory client = createClient();
		client.setWsdlLocation("classpath:BadBeanPersonService.wsdl"); //needs to be updated when PersonService.wsdl is
			
		Object obj = client.getObject();
	}

	/*
	 * BadTypePersonService.wsdl is the same as PersonService.wsdl 
	 * except that wsdl:complexType validatePersonInfoForPersonType is commented out
	 * PersonService is not returned by client.getObject
	 * @throws NullPointerException, confirming a wsdl file missing a complexType can't be used
	 * 
	 */	
	@Test(expected=NullPointerException.class)
	public void checkTypeBeanWSDL() throws Exception {
		JaxWsClientFactory client = createClient();
		client.setWsdlLocation("classpath:BadTypePersonService.wsdl"); //needs to be updated when PersonService.wsdl is
			
		Object obj = client.getObject();
	}
	/*
	 * BadListTypePersonService.wsdl is the same as PersonService.wsdl 
	 * except that wsdl:sequence is commented out
	 * PersonService <i>is</i> returned by client.getObject
	 * confirming a wsdl file missing a sequence has the same test behavior as a good wsdl.
	 * @throws SOAPFaultException, 
	 * 
	 */		
	@Test(expected=SOAPFaultException.class)
	public void checkListTypeBeanWSDL() throws Exception {
		JaxWsClientFactory client = createClient();
		client.setWsdlLocation("classpath:BadListTypePersonService.wsdl"); //needs to be updated when PersonService.wsdl is
					
		Object obj = client.getObject();
		assertNotNull(obj);
		PersonService personService = (PersonService)obj;
		PersonDisplay personDisplay = personService.fetchPersonDisplay("Person 666");
	}

}
