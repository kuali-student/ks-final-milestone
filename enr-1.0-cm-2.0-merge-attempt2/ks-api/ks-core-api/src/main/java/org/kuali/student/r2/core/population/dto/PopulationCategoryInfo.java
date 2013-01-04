/**
 * Copyright 2012 The Kuali Foundation Licensed under the
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
 *
 * Created by Mezba Mahtab on 6/27/12
 */
package org.kuali.student.r2.core.population.dto;

import org.kuali.student.r2.common.dto.IdEntityInfo;
import org.kuali.student.r2.core.population.infc.PopulationCategory;
//import org.w3c.dom.Element;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;
import java.util.List;

/**
 * This class represents a PopulationCategory.
 *
 * @author Kuali Student Team
 */

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PopulationCategoryInfo", propOrder = { "id", "typeKey",
        "stateKey", "name", "descr", "meta", "attributes"})//, "_futureElements" }) TODO KSCM-372: Non-GWT translatable code
public class PopulationCategoryInfo
        extends IdEntityInfo
        implements PopulationCategory, Serializable {
    private static final long serialVersionUID = 1L;

    //////////////////////
    // DATA VARIABLES
    //////////////////////

//    TODO KSCM-372: Non-GWT translatable code
//    @XmlAnyElement
//    private List<Element> _futureElements;

    //////////////////////
    // CONSTRUCTORS
    //////////////////////

    /**
     * Constructs a new PopulationCategoryInfo.
     */
    public PopulationCategoryInfo() {
    }

    /**
     * Constructs a new PopulationCategoryInfo from another
     * PopulationCategory.
     *
     * @param populationCategory the PopulationCategory to copy
     */
    public PopulationCategoryInfo(PopulationCategory populationCategory) {
        super(populationCategory);
    }

    //////////////////////
    // FUNCTIONALS
    //////////////////////


    @Override
    public String toString() {
        return "PopulationCategoryInfo{" +
                "id='" + getId() + "' " +
                // "_futureElements=" + _futureElements + TODO KSCM-372: Non-GWT translatable code
                '}';
    }
}
