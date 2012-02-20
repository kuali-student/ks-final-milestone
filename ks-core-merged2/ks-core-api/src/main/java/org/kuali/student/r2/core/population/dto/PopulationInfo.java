/*
 * Copyright 2011 The Kuali Foundation Licensed under the
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

package org.kuali.student.r2.core.population.dto;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlType;

import org.kuali.student.r2.common.dto.KeyEntityInfo;
import org.kuali.student.r2.core.population.infc.Population;

import org.w3c.dom.Element;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PopulationInfo", propOrder = { "key", "typeKey", "stateKey", 
                "name", "descr", "meta", "attributes",
                "_futureElements" })

public class PopulationInfo extends KeyEntityInfo 
    implements Population, Serializable {

    private static final long serialVersionUID = 1L;
    
    @XmlAnyElement
    private List<Element> _futureElements;
    

    /**
     * Constructs a new PopulationInfo.
     */
    public PopulationInfo() {
    }

    /**
     * Constructs a new PopulationInfo from another Population.
     * 
     * @param population the Population to copy
     */
    public PopulationInfo(Population population) {
        super(population);
    }
}
