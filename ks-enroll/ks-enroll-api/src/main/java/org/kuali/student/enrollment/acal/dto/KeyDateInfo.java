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
package org.kuali.student.enrollment.acal.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.kuali.student.enrollment.acal.infc.KeyDate;
import org.kuali.student.r2.common.dto.KeyEntityInfo;
import org.kuali.student.r2.common.infc.Attribute;
import org.kuali.student.r2.common.infc.Meta;
import org.kuali.student.r2.common.infc.RichText;
import org.w3c.dom.Element;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "KeyDateInfo", propOrder = {"key", "typeKey", "stateKey", "name", "descr", "isAllDay", "isDateRange", "startDate", "endDate", "metaInfo", "attributes", "_futureElements"})
public class KeyDateInfo extends KeyEntityInfo implements KeyDate, Serializable {

    private static final long serialVersionUID = 1L;
    @XmlElement
    private Boolean isAllDay;
    @XmlElement
    private Boolean isDateRange;
    @XmlElement
    private Date startDate;
    @XmlElement
    private Date endDate;
    @XmlAnyElement
    private List<Element> _futureElements;


    public static KeyDateInfo newInstance() {
        return new KeyDateInfo();
    }
    
    public static KeyDateInfo getInstance(KeyDate keyDate) {
        return new KeyDateInfo(keyDate);
    }
    
    public static KeyDateInfo getInstance(String key, String name, RichText descr,
								            Boolean isAllDay, Boolean isDateRange, Date startDate, Date endDate, 
								            String typeKey, String stateKey, List<? extends Attribute> attributes, Meta meta) {
		return new KeyDateInfo(key, name, descr, isAllDay, isDateRange, startDate, endDate, typeKey, stateKey, attributes, meta);
	}

    protected KeyDateInfo() {
        isAllDay = false;
        isDateRange = false;
        startDate = null;
        endDate = null;
        _futureElements = null;
    }

    /**
     * Constructs a new KeyDateInfo from another KeyDate.
     *
     * @param keyDate the KeyDate to copy
     */
    protected KeyDateInfo(KeyDate keyDate) {
        super(keyDate);
        this.isAllDay = keyDate.isAllDay();
        this.isDateRange = keyDate.isDateRange();
        this.startDate = null != keyDate.getStartDate() ? new Date(keyDate.getStartDate().getTime()) : null;
        this.endDate = null != keyDate.getEndDate() ? new Date(keyDate.getEndDate().getTime()) : null;
        _futureElements = null;
    }

    protected KeyDateInfo(String key, String name, RichText descr,
				            Boolean isAllDay, Boolean isDateRange, Date startDate, Date endDate, 
				            String typeKey, String stateKey, List<? extends Attribute> attributes, Meta meta) {
        super(key, name, descr, typeKey, stateKey, attributes, meta);
        this.isAllDay = isAllDay;
        this.isDateRange = isDateRange;
        this.startDate = null != startDate ? new Date(startDate.getTime()) : null;
        this.endDate = null != endDate ? new Date(endDate.getTime()) : null;
        _futureElements = null;
    }
    
    @Override
    public Boolean isAllDay() {
        return isAllDay;
    }

    @Override
    public void setAllDay(Boolean isAllDay) {
        this.isAllDay = isAllDay;
    }

    @Override
    public Boolean isDateRange() {
        return isDateRange;
    }

    @Override
    public void setDateRange(Boolean isDateRange) {
        this.isDateRange = isDateRange;
    }

    @Override
    public Date getStartDate() {
        return startDate;
    }

    @Override
    public void setStartDate(Date startDate) {
        this.startDate = new Date(startDate.getTime());
    }

    @Override
    public Date getEndDate() {
        return endDate;
    }

    @Override
    public void setEndDate(Date endDate) {
        this.endDate = new Date(endDate.getTime());
    }
}
