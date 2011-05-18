package org.kuali.student.r2.lum.lrc.dto;

import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.kuali.student.r2.common.dto.KeyEntityInfo;
import org.kuali.student.r2.common.dto.MetaInfo;
import org.kuali.student.r2.common.infc.Meta;
import org.kuali.student.r2.common.infc.RichText;
import org.w3c.dom.Element;
import org.kuali.student.r2.lum.lrc.infc.ResultValue;
import org.kuali.student.r2.lum.lrc.infc.ResultValueGroup;

@XmlType(name = "ResultValueInfo", propOrder = { "id", "key", "typeKey",
		"stateKey", "name", "descr", "effectiveDate", "expirationDate",
		"value", "metaInfo", "attributes", "_futureElements" })
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

	public void setId(String id) {
		this.id = id;
	}

	public ResultValueInfo() {

	}

	private ResultValueInfo(String name, String value, String typeKey,
			String stateKey, Meta metaInfo, RichText descr, Date effectiveDate,
			Date expirationDate, String id) {
		super();
		this.value = value;
		this.effectiveDate = effectiveDate;
		this.expirationDate = expirationDate;

		this.id = id;
	}

	public static ResultValueInfo newInstance(String name, String value,
			String typeKey, String stateKey, Meta metaInfo, RichText descr,
			Date effectiveDate, Date expirationDate, String id) {

		return new ResultValueInfo(name, value, typeKey, stateKey, metaInfo,
				descr, effectiveDate, expirationDate, id);
	}

	public static ResultValueInfo createNewResultValueInfoFromResultValueInfo(
			ResultValueInfo resultValueInfo) {
		return new ResultValueInfo(resultValueInfo.getName(),
				resultValueInfo.getValue(), resultValueInfo.getTypeKey(),
				resultValueInfo.getStateKey(), resultValueInfo.getMetaInfo(),
				resultValueInfo.getDescr(), resultValueInfo.getEffectiveDate(),
				resultValueInfo.getExpirationDate(), resultValueInfo.getId());
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

}
