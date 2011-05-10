package org.kuali.student.r2.lum.classI.lrc.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.kuali.student.r2.common.dto.KeyEntityInfo;
import org.w3c.dom.Element;
import org.kuali.student.r2.lum.classI.lrc.ResultValue;

public class ResultValueInfo extends KeyEntityInfo implements ResultValue,
		Serializable {

	
	private static final long serialVersionUID = 1L;

	private String name;

	private String value;

	private String rank;

	private Date effectiveDate;

	private Date expirationDate;

	private List<Element> _futureElements;

	private String id;

	public ResultValueInfo() {

	}

	public ResultValueInfo(String name, String value, String rank,
			Date effectiveDate, Date expirationDate,
			List<Element> futureElements, String id) {
		super();
		this.name = name;
		this.value = value;
		this.rank = rank;
		this.effectiveDate = effectiveDate;
		this.expirationDate = expirationDate;
		this._futureElements = futureElements;
		this.id = id;
	}

	public static ResultValueInfo createNewResultValueInfoFromResultValueInfo(
			ResultValueInfo resultValueInfo) {
		return new ResultValueInfo(resultValueInfo.getName(),
				resultValueInfo.getValue(), resultValueInfo.getRank(),
				resultValueInfo.getEffectiveDate(),
				resultValueInfo.getEffectiveDate(),
				resultValueInfo.get_futureElements(), resultValueInfo.getId());
	}

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

		return _futureElements;
	}

}
