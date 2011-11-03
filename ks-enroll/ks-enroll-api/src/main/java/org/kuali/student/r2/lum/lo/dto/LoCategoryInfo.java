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

package org.kuali.student.r2.lum.lo.dto;

import org.kuali.student.common.dto.*;
import org.kuali.student.core.ws.binding.JaxbAttributeMapListAdapter;
import org.kuali.student.r2.common.dto.IdEntityInfo;
import org.kuali.student.r2.lum.lo.infc.LoCategory;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Detailed information about a learning objective category.
 *
 * @Author KSContractMojo
 * @Author jimt
 * @Since Tue Dec 08 10:01:27 PST 2009
 * @See <a href="https://test.kuali.org/confluence/display/KULSTU/loCategoryInfo+Structure+v1.0-rc3">LoCategoryInfo v1.0-rc3</>
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class LoCategoryInfo extends IdEntityInfo implements LoCategory, Serializable{

    private static final long serialVersionUID = 1L;

    @XmlElement
    private String loRepository;

    @XmlElement
    private Date effectiveDate;

    @XmlElement
    private Date expirationDate;

    @XmlElement
    @XmlJavaTypeAdapter(JaxbAttributeMapListAdapter.class)
    private Map<String, String> attributes;

    @XmlElement
    private MetaInfo metaInfo;

    @XmlAttribute
    private String type;

    @XmlAttribute
    private String state;

    @XmlAttribute
    private String id;


    /**
     * Unique identifier for a learning objective repository. Once set in creation, this is immutable.
     */
    public String getLoRepository() {
        return loRepository;
    }

    public void setLoRepository(String loRepository) {
        this.loRepository = loRepository;
    }

    /**
     * Date and time that this learning objective category became effective. This is a similar concept to the effective date on enumerated values. When an expiration date has been specified, this field must be less than or equal to the expiration date.
     */
    public Date getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(Date effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    /**
     * Date and time that this learning objective category expires. This is a similar concept to the expiration date on enumerated values. If specified, this should be greater than or equal to the effective date. If this field is not specified, then no expiration date has been currently defined and should automatically be considered greater than the effective date.
     */
    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }


}