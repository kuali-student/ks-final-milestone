package org.kuali.student.poc.personidentity.person.service;

import javax.jws.WebService;

import org.springframework.transaction.annotation.Transactional;


@WebService(endpointInterface="org.kuali.student.poc.personidentity.person.PersonService",
        name="PersonServiceSecure",
        serviceName="PersonServiceSecure",
        portName="PersonServiceSecure",
        targetNamespace="http://student.kuali.org/poc/wsdl/personidentity/person")
@Transactional
public class PersonServiceSecure extends PersonServiceImpl {

}
