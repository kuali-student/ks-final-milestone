package org.kuali.rice.kns.bo;

import java.util.List;

public abstract class AttributedKualiTypeInactivatableFromToBase extends KualiTypeInactivatableFromToBase implements AttributedBusinessObject {
	private List<BusinessObjectExtensionAttribute> attributes;

	public AttributedKualiTypeInactivatableFromToBase() {
		super();
	}

	public List<BusinessObjectExtensionAttribute> getAttributes() {
		return attributes;
	}

	public void setAttributes(List<BusinessObjectExtensionAttribute> attributes) {
		this.attributes = attributes;
	}

}
