package org.kuali.student.core.exceptions.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * This class was generated by the CXF 2.0.4-incubator Thu Mar 20 11:20:16 EDT 2008 Generated source version: 2.0.4-incubator
 */

@XmlRootElement(name = "DependentObjectsExistException", namespace = "http://exceptions.personidentity.wsdl.poc.student.kuali.org/")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DependentObjectsExistException", namespace = "http://exceptions.personidentity.wsdl.poc.student.kuali.org/")
public class DependentObjectsExistExceptionBean {

    private java.lang.String message;

    public java.lang.String getMessage() {
        return message;
    }

    public void setMessage(java.lang.String newMessage) {
        message = newMessage;
    }

}
