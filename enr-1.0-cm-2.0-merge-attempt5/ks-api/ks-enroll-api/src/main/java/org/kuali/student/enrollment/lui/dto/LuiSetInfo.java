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
 * Created by mahtabme on 10/4/12
 */
package org.kuali.student.enrollment.lui.dto;

import org.kuali.student.enrollment.lui.infc.LuiSet;
import org.kuali.student.r2.common.dto.IdEntityInfo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This class represents a set of Luis "connected" in some manner.
 *
 * @author Kuali Student Team (ks.collab@kuali.org)
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "LuiSetInfo", propOrder = {"id", "descr", "stateKey", "typeKey", "name", "luiIds",
        "effectiveDate", "expirationDate", "meta", "attributes" })
public class LuiSetInfo extends IdEntityInfo implements Serializable, LuiSet {

    ///////////////////////////////
    // Constants
    ///////////////////////////////

    private static final long serialVersionUID = 1L;

    ///////////////////////////////
    // Data Variables
    ///////////////////////////////

    @XmlElement
    private List<String> luiIds;

    @XmlElement
    private Date effectiveDate;

    @XmlElement
    private Date expirationDate;

    ///////////////////////////////
    // Constructors
    ///////////////////////////////

    public LuiSetInfo() {

    }

    public LuiSetInfo(LuiSet luiSet) {
        super(luiSet);
        if (null != luiSet) {
            this.luiIds = new ArrayList<String>(luiSet.getLuiIds());
            this.effectiveDate = (null != luiSet.getEffectiveDate()) ? new Date(luiSet.getEffectiveDate().getTime()) : null;
            this.expirationDate = (null != luiSet.getExpirationDate()) ? new Date(luiSet.getExpirationDate().getTime()) : null;
        }
    }

    ///////////////////////////////
    // Getters and Setters
    ///////////////////////////////

    public List<String> getLuiIds() {
        if (luiIds== null) {
            luiIds= new ArrayList<String>();
        }
        return luiIds;
    }

    public void setLuiIds(List<String> luiIds) {
        this.luiIds = luiIds;
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
