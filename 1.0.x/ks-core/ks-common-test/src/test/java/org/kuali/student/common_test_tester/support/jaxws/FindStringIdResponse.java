/**
 * Copyright 2010 The Kuali Foundation Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package org.kuali.student.common_test_tester.support.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * This class was generated by the CXF 2.0.4-incubator
 * Tue Apr 15 10:46:55 EDT 2008
 * Generated source version: 2.0.4-incubator
 * 
 */

@XmlRootElement(name = "findStringIdResponse", namespace = "http://student.kuali.org/poc/wsdl/test/my")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "findStringIdResponse", namespace = "http://student.kuali.org/poc/wsdl/test/my")

public class FindStringIdResponse {

    @XmlElement(name = "return")
    private java.lang.String _return;

    public java.lang.String get_return() {
        return this._return;
    }
    
    public void set_return( java.lang.String new_return ) {
        this._return = new_return;
    }
    
}

