package org.kuali.rice.kns.bo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ojb.broker.PersistenceBroker;
import org.apache.ojb.broker.PersistenceBrokerException;
import org.kuali.rice.kns.service.KNSServiceLocator;
import org.kuali.student.core.AdminPropertyConstants;

public abstract class AttributedKualiTypeInactivatableFromToBase extends KualiTypeInactivatableFromToBase implements AttributedBusinessObject {
	private List<BusinessObjectExtensionAttribute> attributes;

	public AttributedKualiTypeInactivatableFromToBase() {
		super();
	}

	public void setAttributes(List<BusinessObjectExtensionAttribute> attributes) {
		this.attributes = attributes;
	}
	
	public List<BusinessObjectExtensionAttribute> getAttributes() {
		if (attributes == null) {
			Map ownerSearch = new HashMap();
			ownerSearch.put(AdminPropertyConstants.OWNER_OBJECT_ID, this.getObjectId());

			attributes = (List<BusinessObjectExtensionAttribute>) KNSServiceLocator.getBusinessObjectService()
					.findMatching(BusinessObjectExtensionAttributeImpl.class, ownerSearch);
		}

		return attributes;
	}

	@Override
	public void afterDelete(PersistenceBroker persistenceBroker) throws PersistenceBrokerException {
		super.afterDelete(persistenceBroker);

		clearAttributes();
	}

	@Override
	public void afterInsert(PersistenceBroker persistenceBroker) throws PersistenceBrokerException {
		super.afterInsert(persistenceBroker);

		storeAttributes();
	}

	@Override
	public void afterUpdate(PersistenceBroker persistenceBroker) throws PersistenceBrokerException {
		super.afterUpdate(persistenceBroker);

		storeAttributes();
	}

	protected void storeAttributes() {
		clearAttributes();

		// update the owner id
		for (BusinessObjectExtensionAttribute attribute : attributes) {
			attribute.setOwnerObjectId(this.getObjectId());
		}

		KNSServiceLocator.getBusinessObjectService().save(attributes);
	}

	protected void clearAttributes() {
		Map ownerSearch = new HashMap();
		ownerSearch.put(AdminPropertyConstants.OWNER_OBJECT_ID, this.getObjectId());

		KNSServiceLocator.getBusinessObjectService().deleteMatching(BusinessObjectExtensionAttributeImpl.class,
				ownerSearch);
	}

}
