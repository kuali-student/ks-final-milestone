package org.kuali.rice.kns.bo;

import java.util.List;

public interface AttributedBusinessObject extends PersistableBusinessObject{
	public void setAttributes(List<BusinessObjectExtensionAttribute> attributes);
	public List<BusinessObjectExtensionAttribute> getAttributes();
}
