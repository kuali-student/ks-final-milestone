package org.kuali.student.r2.lum.lrc.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.kuali.student.r2.common.dto.EntityInfo;
import org.kuali.student.r2.common.dto.KeyEntityInfo;
import org.kuali.student.r2.common.dto.MetaInfo;
import org.kuali.student.r2.common.infc.Meta;
import org.kuali.student.r2.common.infc.RichText;
import org.w3c.dom.Element;
import org.kuali.student.r2.lum.lrc.infc.ResultValue;
import org.kuali.student.r2.lum.lrc.infc.ResultValueGroup;

@XmlType(name = "ResultValueInfo", propOrder = { "id",  "typeKey",
		"stateKey", "name", "descr", "effectiveDate", "expirationDate",
		"value", "meta", "attributes", "_futureElements" })
public class ResultValueInfo extends EntityInfo implements ResultValue,
		Serializable {

	private static final long serialVersionUID = 1L;

	@XmlAttribute
	private String id;

	@XmlElement
	private String value;

	@XmlElement
	private Date effectiveDate;

	@XmlElement
	private Date expirationDate;

	@XmlAnyElement
	private List<Element> _futureElements;

	public void setId(String id) {
		this.id = id;
	}

	public ResultValueInfo() {
		super();
		id = null;
		value = null;
		effectiveDate = null;
		expirationDate = null;
	}

	public ResultValueInfo(ResultValue resultValueInfo) {
		super(resultValueInfo);
		this.value = resultValueInfo.getValue();
		this.effectiveDate = new Date( resultValueInfo.getEffectiveDate().getTime());
		this.expirationDate = new Date (resultValueInfo.getExpirationDate().getTime());
		this.id = resultValueInfo.getId();
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

	public List<Element> get_futureElements() {

		return _futureElements;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	public void setExpirationDate(Date expirationDate) {
		this.expirationDate = expirationDate;
	}

	public void set_futureElements(List<Element> _futureElements) {
		this._futureElements = _futureElements;
	}
}
