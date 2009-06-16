package org.kuali.student.common_test_tester.support.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


@XmlRootElement(name = "echoResponse", namespace = "http://student.kuali.org/poc/wsdl/test/my")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "echoResponse", namespace = "http://student.kuali.org/poc/wsdl/test/my")

public class EchoResponse {

    @XmlElement(name = "return")
    private java.lang.String _return;

    public java.lang.String get_return() {
        return this._return;
    }
    
    public void set_return( java.lang.String new_return ) {
        this._return = new_return;
    }
    
}