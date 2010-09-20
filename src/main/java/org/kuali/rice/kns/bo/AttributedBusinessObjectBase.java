package org.kuali.rice.kns.bo;

import java.util.ArrayList;
import java.util.List;

public abstract class AttributedBusinessObjectBase extends PersistableBusinessObjectBase implements AttributedBusinessObject {
	private List<BusinessObjectExtensionAttribute> attributes;
	
	public AttributedBusinessObjectBase() {
		super();
		attributes = new ArrayList<BusinessObjectExtensionAttribute>();
	}
	
	public List<BusinessObjectExtensionAttribute> getAttributes() {
		return attributes;
	}

	public void setAttributes(List<BusinessObjectExtensionAttribute> attributes) {
		this.attributes = attributes;
	}

}
