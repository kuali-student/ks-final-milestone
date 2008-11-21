package org.kuali.student.lum.organization.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.Entity;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.kuali.student.core.entity.AttributeOwner;
import org.kuali.student.core.ws.binding.JaxbAttributeMapListAdapter;

@Entity
public class OrgOrgRelationType implements
		AttributeOwner<OrgOrgRelationTypeAttribute> {
	//TODO
	private String key;

	private String name;

	private String desc;
	
	private String revName;

	private String revDesc;

	private OrgHierarchy orgHierarchyKey;

	private Date effectiveDate;

	private Date expirationDate;
	
	private List<OrgOrgRelationTypeAttribute> attributes;
	
	


	@Override
	public List<OrgOrgRelationTypeAttribute> getAttributes() {
		return attributes;
	}

	@Override
	public void setAttributes(List<OrgOrgRelationTypeAttribute> attributes) {
		if (attributes == null) {
			attributes = new ArrayList<OrgOrgRelationTypeAttribute>();
		}
	}

}
