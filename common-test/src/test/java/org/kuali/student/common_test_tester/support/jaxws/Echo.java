package org.kuali.student.common_test_tester.support.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "echo", namespace = "http://student.kuali.org/poc/wsdl/test/my")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "echo", namespace = "http://student.kuali.org/poc/wsdl/test/my")

public class Echo {

    @XmlElement(name = "arg0")
    private java.lang.String arg0;

    public java.lang.String getArg0() {
        return this.arg0;
    }
    
    public void setArg0( java.lang.String newArg0 ) {
        this.arg0 = newArg0;
    }
	    
}