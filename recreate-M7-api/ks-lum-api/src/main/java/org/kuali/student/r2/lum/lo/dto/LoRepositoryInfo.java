/**
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

package org.kuali.student.r2.lum.lo.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.kuali.student.r2.common.dto.KeyEntityInfo;
import org.kuali.student.r2.lum.lo.infc.LoRepository;

@XmlType(name = "LoRepositoryInfo", propOrder = {"key", "typeKey", "stateKey", "name", "descr", "rootLoId", "effectiveDate", "expirationDate", "meta", "attributes" , "_futureElements" }) 
@XmlAccessorType(XmlAccessType.FIELD)
public class LoRepositoryInfo extends KeyEntityInfo implements LoRepository, Serializable {

    private static final long serialVersionUID = 1L;

    @XmlElement
    private String rootLoId;

    @XmlElement
    private Date effectiveDate;

    @XmlElement
    private Date expirationDate;
    
    @XmlElement
    private List<Object>_futureElements;
    
    public LoRepositoryInfo(){

    }

    public LoRepositoryInfo(LoRepository loRepository){
        super(loRepository);
        if(loRepository != null){
            this.rootLoId = loRepository.getRootLoId();
            this.effectiveDate = (null == loRepository.getEffectiveDate()) ? null : new Date(loRepository.getEffectiveDate().getTime());
            this.expirationDate = (null == loRepository.getExpirationDate()) ? null : new Date(loRepository.getExpirationDate().getTime());
        }
    }

    @Override
    public String getRootLoId() {
        return rootLoId;
    }

    public void setRootLoId(String rootLoId) {
        this.rootLoId = rootLoId;
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