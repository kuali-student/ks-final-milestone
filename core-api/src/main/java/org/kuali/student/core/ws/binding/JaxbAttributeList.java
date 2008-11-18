package org.kuali.student.core.ws.binding;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class JaxbAttributeList {

	@XmlElement
	private List<JaxbAttribute> attribute;

	/**
	 * @return the attribute
	 */
	public List<JaxbAttribute> getAttribute() {
		if (this.attribute == null) {
			this.attribute = new ArrayList<JaxbAttribute>();
		}
		return attribute;
	}

}