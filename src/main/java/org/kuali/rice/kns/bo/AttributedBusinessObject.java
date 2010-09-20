package org.kuali.rice.kns.bo;

import java.util.List;

public interface AttributedBusinessObject {
	public void setAttributes(List<BusinessObjectExtensionAttribute> attributes);
	public List<BusinessObjectExtensionAttribute> getAttributes();
}
