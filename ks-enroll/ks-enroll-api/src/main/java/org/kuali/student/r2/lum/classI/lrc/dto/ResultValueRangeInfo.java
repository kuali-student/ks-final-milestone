package org.kuali.student.r2.lum.classI.lrc.dto;

import java.io.Serializable;
import java.util.Date;
import org.kuali.student.r2.common.dto.KeyEntityInfo;
import org.kuali.student.r2.lum.classI.lrc.ResultValueRange;

public class ResultValueRangeInfo extends KeyEntityInfo implements
		ResultValueRange, Serializable {

	private static final long serialVersionUID = 1L;

	private String resultTypeKey;

	private String id;

	private String name;

	private String minValue;

	private String maxValue;

	private float increment;

	private String scaleKey;

	private String rank;

	private Date effectiveDate;

	private Date expirationDate;
	
	@Override
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	@Override
	public String getMinValue() {
		return minValue;
	}

	public void setMinValue(String minValue) {
		this.minValue = minValue;
	}
	@Override
	public String getMaxValue() {
		return maxValue;
	}

	public void setMaxValue(String maxValue) {
		this.maxValue = maxValue;
	}
	@Override
	public float getIncrement() {
		return increment;
	}

	public void setIncrement(float increment) {
		this.increment = increment;
	}
	@Override
	public String getScaleKey() {
		return scaleKey;
	}

	public void setScaleKey(String scaleKey) {
		this.scaleKey = scaleKey;
	}
	@Override
	public String getRank() {
		return rank;
	}

	public void setRank(String rank) {
		this.rank = rank;
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

	public void setType(String type) {
		this.resultTypeKey = type;
	}
	@Override
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String getResultTypeKey() {
		return resultTypeKey;
	}

}
