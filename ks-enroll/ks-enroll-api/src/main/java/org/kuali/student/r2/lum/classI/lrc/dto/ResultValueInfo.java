package org.kuali.student.r2.lum.classI.lrc.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.kuali.student.r2.common.dto.KeyEntityInfo;
import org.w3c.dom.Element;
import org.kuali.student.r2.lum.classI.lrc.ResultValue;

public class ResultValueInfo extends KeyEntityInfo implements ResultValue, Serializable {

	private  String name;

	private  String value;

	private  String rank;

	private  Date effectiveDate;

	private  Date expirationDate;

	private List<Element> futureElements; 

	private  String id;

	public String getName() {
		return name;
	}

	public String getValue() {
		return value;
	}

	public String getRank() {
		return rank;
	}

	public Date getEffectiveDate() {
		return effectiveDate;
	}

	public Date getExpirationDate() {
		return expirationDate;
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public List<Element> get_futureElements() {
	
		return futureElements;
	}

}
