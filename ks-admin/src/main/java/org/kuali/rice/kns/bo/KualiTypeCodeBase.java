package org.kuali.rice.kns.bo;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Id;

import org.apache.commons.lang.StringUtils;
import org.apache.ojb.broker.PersistenceBroker;
import org.apache.ojb.broker.PersistenceBrokerException;
import org.kuali.rice.kns.service.KNSServiceLocator;
import org.kuali.rice.kns.util.GlobalVariables;
import org.kuali.student.core.AdminPropertyConstants;

public abstract class KualiTypeCodeBase extends PersistableBusinessObjectBase implements KualiTypeCode, AttributedBusinessObject {

	@Id
	@Column(name = "ID")
	private String id;

	@Column(name = "NAME")
	private String name;

	@Column(name = "DESC")
	private String description;

	@Column(name = "STATE")
	private String state;

	private String typeId;

	private String createId;

	private Date createDate;

	private String updateId;

	private Date updateDate;

	private List<BusinessObjectExtensionAttribute> attributes;

	public KualiTypeCodeBase() {
		super();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getTypeId() {
		return typeId;
	}

	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}

	public String getCreateId() {
		return createId;
	}

	public void setCreateId(String createId) {
		this.createId = createId;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getUpdateId() {
		return updateId;
	}

	public void setUpdateId(String updateId) {
		this.updateId = updateId;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	@SuppressWarnings("unchecked")
	protected LinkedHashMap toStringMapper() {
		LinkedHashMap m = new LinkedHashMap();

		m.put("id", this.id);

		return m;
	}

	@Override
	public void beforeInsert(PersistenceBroker persistenceBroker) throws PersistenceBrokerException {
		super.beforeInsert(persistenceBroker);

		if (StringUtils.isBlank(createId)) {
			this.createId = GlobalVariables.getUserSession().getPrincipalName();
		}

		if (createDate == null) {
			this.createDate = KNSServiceLocator.getDateTimeService().getCurrentSqlDate();
		}
	}

	@Override
	public void beforeUpdate(PersistenceBroker persistenceBroker) throws PersistenceBrokerException {
		super.beforeUpdate(persistenceBroker);

		this.updateId = GlobalVariables.getUserSession().getPrincipalName();
		this.updateDate = KNSServiceLocator.getDateTimeService().getCurrentSqlDate();
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
