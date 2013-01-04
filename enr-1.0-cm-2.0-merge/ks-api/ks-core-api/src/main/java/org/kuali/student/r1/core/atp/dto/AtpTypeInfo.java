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

package org.kuali.student.r1.core.atp.dto;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

import org.kuali.student.r1.common.dto.HasAttributes;
import org.kuali.student.r1.common.dto.Idable;
import org.kuali.student.r1.common.dto.TypeInfo;

/**
 *Information about an academic time period type.
 */ 
@Deprecated
@XmlAccessorType(XmlAccessType.FIELD)
public class AtpTypeInfo extends TypeInfo implements Serializable, Idable, HasAttributes {

    private static final long serialVersionUID = 1L;

    @XmlElement
    private String durationType;

    @XmlElement
    private String seasonalType;

    /**
     * Unique identifier for an academic time period duration type.
     */
    public String getDurationType() {
        return durationType;
    }

    public void setDurationType(String durationType) {
        this.durationType = durationType;
    }

    /**
     * Unique identifier for an academic time period seasonal type.
     */
    public String getSeasonalType() {
        return seasonalType;
    }

    public void setSeasonalType(String seasonalType) {
        this.seasonalType = seasonalType;
    }

}
