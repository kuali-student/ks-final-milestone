/*
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
package org.kuali.student.r2.core.atp.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.kuali.student.r2.common.dto.IdEntityInfo;
import org.kuali.student.r2.core.atp.infc.Atp;
import org.kuali.student.r2.common.dto.KeyEntityInfo;
//import org.w3c.dom.Element;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AtpInfo", propOrder = {
                "id", "typeKey", "stateKey", "name", "descr",
                "code", "startDate", "endDate", "adminOrgId", 
                "meta", "attributes"})//, "_futureElements" }) TODO KSCM-372: Non-GWT translatable code })

public class AtpInfo 
    extends IdEntityInfo 
    implements Atp, Serializable {

    private static final long serialVersionUID = 1L;
	
    @XmlElement
    private String code;

    @XmlElement
    private Date startDate;
	
    @XmlElement
    private Date endDate;
	
    @XmlElement
    private String adminOrgId;

//    TODO KSCM-372: Non-GWT translatable code
//    @XmlAnyElement
//    private List<Element> _futureElements;
    
    
    /**
     * Constructs a new AtpInfo.
     */
    public AtpInfo() {
    }

    /**
     * Constructs a new AtpInfo from another Atp.
     * 
     * @param atp the ATP to copy
     */
    public AtpInfo(Atp atp) {
        super(atp);

        if (atp != null) {
            this.code = atp.getCode();

            if (atp.getStartDate() != null) {
                this.startDate = new Date(atp.getStartDate().getTime());
            }

            if (atp.getEndDate() != null) {
                this.endDate = new Date(atp.getEndDate().getTime());
            }

            this.adminOrgId = atp.getAdminOrgId();
        }
    }
    
    @Override
    public String getCode() {
        return code;
    }
	
    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public Date getStartDate() {
        return startDate;
    }
	
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    @Override
    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    @Override
    public String getAdminOrgId() {
        return adminOrgId;
    }
    
    public void setAdminOrgId(String adminOrgId) {
        this.adminOrgId = adminOrgId;
    }
}
