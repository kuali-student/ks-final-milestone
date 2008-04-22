package org.kuali.student.poc.personidentity.person.test;

import static org.junit.Assert.assertEquals;

import org.acegisecurity.context.SecurityContextHolder;
import org.acegisecurity.providers.UsernamePasswordAuthenticationToken;
import org.junit.Test;
import org.kuali.student.poc.common.test.spring.AbstractServiceTest;
import org.kuali.student.poc.common.test.spring.Client;
import org.kuali.student.poc.common.test.spring.Dao;
import org.kuali.student.poc.common.test.spring.Daos;
import org.kuali.student.poc.common.test.spring.PersistenceFileLocation;
import org.kuali.student.poc.wsdl.personidentity.exceptions.AlreadyExistsException;
import org.kuali.student.poc.wsdl.personidentity.exceptions.DisabledIdentifierException;
import org.kuali.student.poc.wsdl.personidentity.exceptions.DoesNotExistException;
import org.kuali.student.poc.wsdl.personidentity.exceptions.InvalidParameterException;
import org.kuali.student.poc.wsdl.personidentity.exceptions.MissingParameterException;
import org.kuali.student.poc.wsdl.personidentity.exceptions.OperationFailedException;
import org.kuali.student.poc.wsdl.personidentity.exceptions.PermissionDeniedException;
import org.kuali.student.poc.wsdl.personidentity.person.PersonService;
import org.kuali.student.poc.xsd.personidentity.person.dto.PersonAttributeSetTypeInfo;
import org.kuali.student.poc.xsd.personidentity.person.dto.PersonTypeInfo;
import javax.xml.ws.soap.SOAPFaultException;


@Daos({@Dao("org.kuali.student.poc.personidentity.person.dao.PersonDAOImpl")})
@PersistenceFileLocation("classpath:META-INF/person-persistence.xml")
public class TestPersonServiceSecure extends AbstractServiceTest {

    @Client(value = "org.kuali.student.poc.personidentity.person.service.PersonServiceImpl", port = "9191", secure = true)
    public PersonService client;

    @Test
    public void testSecureCreatePersonInfoType() throws AlreadyExistsException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException, DisabledIdentifierException, Exception {

        PersonAttributeSetTypeInfo attributeSet1 = new PersonAttributeSetTypeInfo();
        attributeSet1.setName("AttrSet1");

        PersonTypeInfo personType1 = new PersonTypeInfo();
        personType1.setName("PersonType1");
        personType1.getAttributeSets().add(attributeSet1);

        // Make a call with valid username/pwd
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken("dummy", "dummy"));

        String personTypeId = client.createPersonTypeInfo(personType1);

        // Now find all the types
        PersonTypeInfo personTypeInfo = client.fetchPersonType(personTypeId);

        // Validate results
        assertEquals("PersonType1", personTypeInfo.getName());
        assertEquals(personTypeId, personTypeInfo.getId());

        // Make a call with valid username/pwd
        SecurityContextHolder.clearContext();
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken("dummy", "nodummy"));

        try {
            client.createPersonTypeInfo(personType1);
            throw new Exception("Security not processed");
        } catch (SOAPFaultException sfe) {
            assertEquals("Security processing failed.", sfe.getMessage());
        }
    }

}
