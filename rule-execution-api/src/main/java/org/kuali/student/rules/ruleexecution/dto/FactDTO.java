package org.kuali.student.rules.ruleexecution.dto;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class FactDTO implements java.io.Serializable {

	/** Class serial version uid */
    private static final long serialVersionUID = 1L;

	@XmlAttribute
    private String id;
    @XmlElement
	private final List<Value> factList = new ArrayList<Value>();

	public FactDTO() {}

	public FactDTO(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public void addFact(String id, String dataType, String value) {
		Value v = new Value(id, dataType, value);
		this.factList.add(v);
	}
	
	public List<Value> getValues() {
		return this.factList;
	}

	public final static class Value {
		@XmlAttribute
	    private String valueId;
		@XmlElement
		private String valueDataType;
		@XmlElement
		private String val;

		public Value() {}

		public Value(String id, String dataType, String value) {
			this.valueId = id;
			this.valueDataType = dataType;
			this.val = value;
		}

		public String getId() { return valueId; }
		public void setId(String id) { this.valueId = id; }

		public String getDataType() { return valueDataType; }
		public void setDataType(String dataType) { this.valueDataType = dataType; }

		public String getValue() { return val; }
		public void setValue(String value) { this.val = value; }

	}
}
