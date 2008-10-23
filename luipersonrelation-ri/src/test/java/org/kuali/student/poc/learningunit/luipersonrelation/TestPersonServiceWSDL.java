package org.kuali.student.poc.learningunit.luipersonrelation;
import static org.junit.Assert.assertNotNull;

import javax.xml.namespace.QName;
import javax.xml.ws.WebServiceException;
import javax.xml.ws.soap.SOAPFaultException;

import org.junit.Test;
import org.kuali.student.poc.common.ws.beans.JaxWsClientFactory;
import org.kuali.student.poc.common.ws.beans.JaxWsClientFactoryBean;
import org.kuali.student.poc.wsdl.personidentity.person.PersonService;
import org.kuali.student.poc.xsd.personidentity.person.dto.PersonDisplay;

/**
 * Tests CXF 2.1 runtime behavior for wsdl errors. Uses PersonService but what's under test is jax-ws handling 
 * of missing, duplicate, and extra wsdl operations, elements, complexTypes, and sequences.
 * Interface errors such as missing methods and missing @WebMethod tags are not tested because they can't be detected without a service impl.
 * @author garystruthers
 * IMPORTANT: other versions of cxf and other implementations of jax-ws may behave differently.
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
	public void goodWSDL() throws Exception {
		JaxWsClientFactory client = createClient();
		client.setWsdlLocation(wsdlLocation);
			
		Object obj = client.getObject();
		assertNotNull(obj);
		PersonService personService = (PersonService)obj;
		PersonDisplay personDisplay = personService.fetchPersonDisplay("Person 666");
	}
	/*
	 * BadDuplicateOpPersonService.wsdl is the same as PersonService.wsdl 
	 * except that wsdl:operation fetchPersonDisplay is duplicated.
	 * This is invalid and is detected.
	 * PersonService is not returned by client.getObject
	 * @throws IllegalArgumentException, confirming a wsdl with duplicate operations can't be used
	 * 
	 */
	@Test(expected=IllegalArgumentException.class)
	public void duplicateOpWSDL() throws Exception {
		JaxWsClientFactory client = createClient();
		client.setWsdlLocation("classpath:BadDuplicateOpPersonService.wsdl");
			
		Object obj = client.getObject();
	}
	/*
	 * BadExtraOpPersonService.wsdl is the same as PersonService.wsdl 
	 * except that wsdl:operation fetchPersonDisplay has a copy called
	 * fetchPersonDisplay.
	 * This is valid wsdl but the operation isn't in the interface and 
	 * the error is not detected.
	 * PersonService <i>is</i> returned by client.getObject
	 * @throws SOAPFaultException, the extra operation is not detected
	 * 
	 */	
	@Test(expected=SOAPFaultException.class)
	public void extraOpWSDL() throws Exception {
		JaxWsClientFactory client = createClient();
		client.setWsdlLocation("classpath:BadExtraOpPersonService.wsdl");
			
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
	public void missingOpWSDL() throws Exception {
		JaxWsClientFactory client = createClient();
		client.setWsdlLocation("classpath:BadPersonService.wsdl"); //needs to be updated when PersonService.wsdl is
		
		Object obj = client.getObject();
	}
	/*
	 * BadDuplicateBeanPersonService.wsdl is the same as PersonService.wsdl 
	 * except that wsdl:element validatePersonInfoForPersonTypeResponse is duplicated.
	 * This is invalid wsdl but the error is not detected.
	 * PersonService <i>is</i> returned by client.getObject
	 * @throws SOAPFaultException, the invalid wsdl is not detected
	 * 
	 */	
	@Test(expected=SOAPFaultException.class)
	public void duplicateBeanWSDL() throws Exception {
		JaxWsClientFactory client = createClient();
		client.setWsdlLocation("classpath:BadDuplicateBeanPersonService.wsdl"); //needs to be updated when PersonService.wsdl is
			
		Object obj = client.getObject();
		assertNotNull(obj);
		PersonService personService = (PersonService)obj;
		PersonDisplay personDisplay = personService.fetchPersonDisplay("Person 666");
	}
	/*
	 * BadBeanPersonService.wsdl is the same as PersonService.wsdl 
	 * except that wsdl:element validatePersonInfoForPersonTypeResponse is commented out
	 * PersonService is not returned by client.getObject
	 * @throws NullPointerException, confirming a wsdl file missing an element can't be used
	 * 
	 */	
	@Test(expected=NullPointerException.class)
	public void missingBeanWSDL() throws Exception {
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
	public void missingComplexTypeWSDL() throws Exception {
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
	public void missingSequenceWSDL() throws Exception {
		JaxWsClientFactory client = createClient();
		client.setWsdlLocation("classpath:BadListTypePersonService.wsdl"); //needs to be updated when PersonService.wsdl is
					
		Object obj = client.getObject();
		assertNotNull(obj);
		PersonService personService = (PersonService)obj;
		PersonDisplay personDisplay = personService.fetchPersonDisplay("Person 666");
	}
	/*
	 * BadDuplicateTypePersonService.wsdl is the same as PersonService.wsdl 
	 * except that wsdl:complexType validatePersonInfoForPersonType is duplicated.
	 * This is invalid and is detected.
	 * PersonService is not returned by client.getObject
	 * @throws WebServiceException, confirming a wsdl with duplicate complexTypes can't be used
	 * 
	 */
	@Test(expected=WebServiceException.class)
	public void duplicateComplexTypeWSDL() throws Exception {
		JaxWsClientFactory client = createClient();
		client.setWsdlLocation("classpath:BadDuplicateTypePersonService.wsdl"); //needs to be updated when PersonService.wsdl is
					
		Object obj = client.getObject();

	}
	/*
	 * BadExtraTypePersonService.wsdl is the same as PersonService.wsdl 
	 * except that wsdl:complexType validatePersonInfoForPersonType has a copy
	 * called validatePersonInfoForPersonTypeExtra.
	 * PersonService <i>is</i> returned by client.getObject
	 * confirming a wsdl file with an extra complexType has the same test behavior as a good wsdl.
	 * @throws SOAPFaultException, 
	 * 
	 */	
	@Test(expected=SOAPFaultException.class)
	public void extraComplexTypeWSDL() throws Exception {
		JaxWsClientFactory client = createClient();
		client.setWsdlLocation("classpath:BadExtraTypePersonService.wsdl");
			
		Object obj = client.getObject();
		assertNotNull(obj);
		PersonService personService = (PersonService)obj;
		PersonDisplay personDisplay = personService.fetchPersonDisplay("Person 666");
	}
	/*
	 * BadDuplicateSequencePersonService.wsdl is the same as PersonService.wsdl 
	 * except that wsdl:sequence is duplicated within a complexType.
	 * This is invalid wsdl but the error is not detected.
	 * PersonService <i>is</i> returned by client.getObject
	 * @throws SOAPFaultException, the invalid wsdl is not detected
	 * 
	 */	
	@Test(expected=SOAPFaultException.class)
	public void duplicateSequenceWSDL() throws Exception {
		JaxWsClientFactory client = createClient();
		client.setWsdlLocation("classpath:BadDuplicateSequencePersonService.wsdl"); //needs to be updated when PersonService.wsdl is
					
		Object obj = client.getObject();
		assertNotNull(obj);
		PersonService personService = (PersonService)obj;
		PersonDisplay personDisplay = personService.fetchPersonDisplay("Person 666");
	}
}
