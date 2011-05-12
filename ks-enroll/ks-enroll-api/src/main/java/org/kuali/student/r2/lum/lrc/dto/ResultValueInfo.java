package org.kuali.student.r2.lum.lrc.dto;

import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.kuali.student.r2.common.dto.KeyEntityInfo;
import org.kuali.student.r2.common.dto.MetaInfo;
import org.kuali.student.r2.common.infc.RichText;
import org.w3c.dom.Element;
import org.kuali.student.r2.lum.lrc.infc.ResultValue;
import org.kuali.student.r2.lum.lrc.infc.ResultValueGroup;

@XmlType(name = "ResultValueInfo", propOrder = { "key", "typeKey", "stateKey",
		"name", "descr", "effectiveDate", "expirationDate", "value",
		"metaInfo", "attributes", "_futureElements" })
public class ResultValueInfo extends KeyEntityInfo implements ResultValue {

	private static final long serialVersionUID = 1L;

	@XmlAttribute
	private String value;

	@XmlElement
	private Date effectiveDate;

	@XmlElement
	private Date expirationDate;

	@XmlAnyElement
	private List<Element> _futureElements;

	@XmlAttribute
	private String id;

	public ResultValueInfo() {

	}

	public ResultValueInfo(String name, String value, String rank,
			String typeKey, String stateKey, MetaInfo metaInfo, RichText descr,
			Date effectiveDate, Date expirationDate, String id) {
		// TODO once devs make objects mutable
		this.value = value;
		this.effectiveDate = effectiveDate;
		this.expirationDate = expirationDate;

		this.id = id;
	}

	public static ResultValueInfo createNewResultValueInfoFromResultValueInfo(
			ResultValueInfo resultValueInfo) {
		return new ResultValueInfo();
	}

	@Override
	public String getValue() {
		return value;
	}

	@Override
	public Date getEffectiveDate() {
		return effectiveDate;
	}

	@Override
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
