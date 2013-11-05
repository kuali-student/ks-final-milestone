/*
 * Copyright 2011 The Kuali Foundation 
 *
 * Licensed under the Educational Community License, Version 2.0 (the
 * "License"); you may not use this file except in compliance with the
 * License. You may obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */
package org.kuali.student.r2.core.population.dto;

import org.kuali.student.r2.common.dto.IdEntityInfo;
import org.kuali.student.r2.core.population.infc.Population;
//import org.w3c.dom.Element;

import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PopulationInfo", propOrder = {"id",
    "typeKey",
    "stateKey",
    "name", 
    "descr",
    "sortOrderTypeKeys",
    "variesByTime",
    "supportsGetMembers",
    "meta",
    "attributes", "_futureElements" }) 
public class PopulationInfo
        extends IdEntityInfo
        implements Population, Serializable {

    private static final long serialVersionUID = 1L;
    @XmlElement
    private List<String> sortOrderTypeKeys; // readonly
    @XmlElement
    private Boolean variesByTime; // readonly
    @XmlElement
    private Boolean supportsGetMembers; // readonly
    @XmlAnyElement
    private List<Object> _futureElements;  


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

        if (population != null) {
            if (population.getSortOrderTypeKeys() != null) {
                this.sortOrderTypeKeys = new ArrayList<String>(population.getSortOrderTypeKeys());
            }

            this.variesByTime = population.getVariesByTime();
            this.supportsGetMembers = population.getSupportsGetMembers();

        }
    }

    @Override
    public List<String> getSortOrderTypeKeys() {
        if (this.sortOrderTypeKeys == null) {
            this.sortOrderTypeKeys = new ArrayList<String>();
        }

        return this.sortOrderTypeKeys;
    }

    public void setSortOrderTypeKeys(List<String> sortOrderTypeKeys) {
        this.sortOrderTypeKeys = sortOrderTypeKeys;
    }

    @Override
    public Boolean getVariesByTime() {
        return this.variesByTime;
    }

    public void setVariesByTime(Boolean variesByTime) {
        this.variesByTime = variesByTime;
    }

    @Override
    public Boolean getSupportsGetMembers() {
        return supportsGetMembers;
    }

    public void setSupportsGetMembers(Boolean supportsGetMembers) {
        this.supportsGetMembers = supportsGetMembers;
    }
}
