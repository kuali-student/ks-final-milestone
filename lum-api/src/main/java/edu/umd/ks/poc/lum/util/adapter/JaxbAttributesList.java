package edu.umd.ks.poc.lum.util.adapter;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class JaxbAttributesList {

	@XmlElement
	private List<JaxbAttributes> attribute;

	/**
	 * @return the attribute
	 */
	public List<JaxbAttributes> getAttribute() {
		if (this.attribute == null) {
			this.attribute = new ArrayList<JaxbAttributes>();
		}
		return attribute;
	}

}