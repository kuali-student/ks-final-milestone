package org.kuali.student.core.dictionary.poc.dto;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import org.kuali.student.core.assembly.data.CommonLookup;

@XmlAccessorType(XmlAccessType.FIELD)
public class LookupConstraint extends CommonLookup {

	private static final long serialVersionUID = 1L;
	
	//Inherit fields from CommonLookup
//	private String searchTypeId; // id of search type defined in search xml
//	private String resultReturnKey; // key of searchResultColumn to map back to
									// this field
	protected List<LookupConstraintParamMapping> lookupParams; // maps fields to
																// search
																// params?

	public List<LookupConstraintParamMapping> getLookupParams() {
		return lookupParams;
	}

	public void setLookupParams(List<LookupConstraintParamMapping> lookupParams) {
		this.lookupParams = lookupParams;
	}
}