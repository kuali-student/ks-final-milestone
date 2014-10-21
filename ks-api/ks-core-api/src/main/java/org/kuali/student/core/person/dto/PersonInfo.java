/*
 * Copyright 2013 The Kuali Foundation Licensed under the
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
package org.kuali.student.core.person.dto;

import org.kuali.student.core.person.infc.Person;
import org.kuali.student.r2.common.dto.IdEntityInfo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PersonInfo", propOrder = {"id",
    "typeKey",
    "stateKey",
    "name",
    "descr",
    "meta",
    "attributes", "_futureElements"})
public class PersonInfo extends IdEntityInfo implements Person, Serializable {

    private static final long serialVersionUID = 1L;

    @XmlAnyElement
    private List<Object> _futureElements;

    public PersonInfo() {

    }

    public PersonInfo(Person input) {
        super(input);
    }
}
