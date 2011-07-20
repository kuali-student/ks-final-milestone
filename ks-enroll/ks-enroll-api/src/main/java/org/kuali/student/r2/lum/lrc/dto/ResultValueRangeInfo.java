/*
 * Copyright 2007 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 1.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl1.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.student.r2.lum.lrc.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.kuali.student.r2.common.dto.HasAttributesAndMetaInfo;
import org.kuali.student.r2.lum.lrc.infc.ResultValueRange;
import org.w3c.dom.Element;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ResultValueRangeInfo", propOrder = { "id", "minValue", "maxValue", "increment",
		"effectiveDate", "expirationDate", "meta", "attributes", "_futureElements" })
public class ResultValueRangeInfo extends HasAttributesAndMetaInfo implements
		ResultValueRange, Serializable {

	private static final long serialVersionUID = 1L;

	@XmlAttribute
	private String id;
	
	@XmlElement
	private Float minValue;

	@XmlElement
	private Float maxValue;
	
	@XmlElement
	private Float increment;

	@XmlElement
	private Date effectiveDate;
	
	@XmlElement
	private Date expirationDate;

    @XmlAnyElement
    private List<Element> _futureElements;
	
	public ResultValueRangeInfo() {
	}

	public ResultValueRangeInfo(ResultValueRange resultValueRangeInfo) {
		super(resultValueRangeInfo);
		if (null != resultValueRangeInfo) {
			this.minValue = resultValueRangeInfo.getMinValue();
			this.maxValue = resultValueRangeInfo.getMaxValue();
			this.increment = resultValueRangeInfo.getIncrement();
			this.effectiveDate = new Date( resultValueRangeInfo.getEffectiveDate().getTime());
			this.expirationDate = new Date (resultValueRangeInfo.getExpirationDate( ).getTime());
		}
	}

	@Override
	public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
	public Float getMinValue() {
		return minValue;
	}

	public void setMinValue(Float minValue) {
		this.minValue = minValue;
	}

	@Override
	public Float getMaxValue() {
		return maxValue;
	}

	public void setMaxValue(Float maxValue) {
		this.maxValue = maxValue;
	}

	@Override
	public Float getIncrement() {
		return increment;
	}

	public void setIncrement(Float increment) {
		this.increment = increment;
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

    public List<Element> get_futureElements() {
        return _futureElements;
    }

    public void set_futureElements(List<Element> _futureElements) {
        this._futureElements = _futureElements;
    }	
}
