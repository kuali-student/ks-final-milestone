package edu.umd.ks.poc.lum.lu.service.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import edu.umd.ks.poc.lum.lu.dto.LuiRelationDisplay;

/**
 * This class was generated by the CXF 2.0.4-incubator Fri Apr 18 16:30:06 EDT
 * 2008 Generated source version: 2.0.4-incubator
 * 
 */

@XmlRootElement(name = "findLuiRelationsResponse", namespace = "http://edu.umd.ks/poc/lum/lu")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "findLuiRelationsResponse", namespace = "http://edu.umd.ks/poc/lum/lu")
public class FindLuiRelationsResponse {

	@XmlElement(name = "return")
	private java.util.List<LuiRelationDisplay> _return;

	public java.util.List<LuiRelationDisplay> get_return() {
		return this._return;
	}

	public void set_return(java.util.List<LuiRelationDisplay> new_return) {
		this._return = new_return;
	}

}
