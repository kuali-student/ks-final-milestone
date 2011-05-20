package org.kuali.student.r2.lum.lrc.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.kuali.student.r2.common.dto.EntityInfo;
import org.kuali.student.r2.common.dto.KeyEntityInfo;
import org.kuali.student.r2.common.infc.Attribute;
import org.kuali.student.r2.common.infc.Meta;
import org.kuali.student.r2.common.infc.RichText;
import org.kuali.student.r2.lum.lrc.infc.ResultValueRange;

@XmlType(name = "ResultValueRangeInfo", propOrder = { "key", "typeKey",
		"stateKey", "resultTypeKey", "minValue", "maxValue", "increment",
		"meta", "effectiveDate", "expirationDate", "attributes" })
public class ResultValueRangeInfo extends EntityInfo implements
		ResultValueRange, Serializable {

	private static final long serialVersionUID = 1L;

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

}
