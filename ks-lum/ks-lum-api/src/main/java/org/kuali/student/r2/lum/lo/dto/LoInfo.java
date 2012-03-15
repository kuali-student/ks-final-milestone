/**
 * Copyright 2010 The Kuali Foundation Licensed under the Educational Community
 * License, Version 2.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://www.osedu.org/licenses/ECL-2.0 Unless required by applicable law or
 * agreed to in writing, software distributed under the License is distributed
 * on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package org.kuali.student.r2.lum.lo.dto;

import org.kuali.student.r2.common.dto.IdEntityInfo;
import org.kuali.student.r2.lum.lo.infc.Lo;
//import org.w3c.dom.Element;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Detailed information about a learning objective
 * 
 * @author Kuali Student Team (sambitpa@kuali.org)
 */

@XmlType(name = "LoInfo", propOrder = {"id", "typeKey", "stateKey", "name", "descr", "loRepositoryKey", "effectiveDate", "expirationDate", "meta", "attributes" })//, "_futureElements" }) TODO KSCM-372: Non-GWT translatable code})
@XmlAccessorType(XmlAccessType.FIELD)
public class LoInfo extends IdEntityInfo implements Lo, Serializable {

    private static final long serialVersionUID = 1L;

    @XmlElement
    private String loRepositoryKey;

    @XmlElement
    private Date effectiveDate;

    @XmlElement
    private Date expirationDate;

//    TODO KSCM-372: Non-GWT translatable code
//    @XmlAnyElement
//    private List<Element> _futureElements;

    
    public LoInfo(){
        
    }
    
    public LoInfo(Lo lo){
        super(lo);
        if(lo!=null){
            this.loRepositoryKey= lo.getLoRepositoryKey();
            this.effectiveDate= new Date(lo.getEffectiveDate().getTime());
            this.expirationDate =  new Date(lo.getExpirationDate().getTime());
        }
    }

    @Override
    public String getLoRepositoryKey() {
        return loRepositoryKey;
    }

    public void setLoRepositoryKey(String loRepositoryKey) {
        this.loRepositoryKey = loRepositoryKey;
    }

    @Override
    public Date getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(Date effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    @Override
    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

}