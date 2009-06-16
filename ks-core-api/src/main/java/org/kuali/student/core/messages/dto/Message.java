package org.kuali.student.core.messages.dto;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
/**
 * Example:
<message locale="en" groupName="/ks/admin/person/validate" id="isRequired">
  <value>${name} is required</value>
</message>
 */

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "message")
public class Message implements Serializable{
    private static final long serialVersionUID = 1L;
    
    @XmlAttribute(required = true)
    protected String locale;
    @XmlAttribute(required = true)
    protected String groupName;
    @XmlAttribute(required = true)
    protected String id;
    @XmlElement(name = "value", required = true)
    protected String value;
	public String getLocale() {
		return locale;
	}
	public void setLocale(String locale) {
		this.locale = locale;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}

    
}
