package org.kuali.student.lum.organization.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

import org.kuali.student.core.dto.Attribute;
import org.kuali.student.core.dto.MetaInfo;

@XmlAccessorType(XmlAccessType.FIELD)
public class OrgPositionRestrictionInfo implements Serializable {
	private static final long serialVersionUID = 1L;
	@XmlElement
	private String orgId;
	@XmlElement
	private String orgPersonRelationTypeKey;
	@XmlElement
	private String desc;
	@XmlElement
	private String title;
	@XmlElement
	private TimeAmount stdDuration;
	@XmlElement
	private Integer minNumRelations;
	@XmlElement
	private String maxNumRelations;
	@XmlElement
	private List<Attribute> attributes;
	@XmlElement
	private MetaInfo metaInfo;
	@XmlAttribute
	private String id;

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public String getOrgPersonRelationTypeKey() {
		return orgPersonRelationTypeKey;
	}

	public void setOrgPersonRelationTypeKey(String orgPersonRelationTypeKey) {
		this.orgPersonRelationTypeKey = orgPersonRelationTypeKey;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public TimeAmount getStdDuration() {
		return stdDuration;
	}

	public void setStdDuration(TimeAmount stdDuration) {
		this.stdDuration = stdDuration;
	}

	public Integer getMinNumRelations() {
		return minNumRelations;
	}

	public void setMinNumRelations(Integer minNumRelations) {
		this.minNumRelations = minNumRelations;
	}

	public String getMaxNumRelations() {
		return maxNumRelations;
	}

	public void setMaxNumRelations(String maxNumRelations) {
		this.maxNumRelations = maxNumRelations;
	}

	public List<Attribute> getAttributes() {
		if (attributes == null) {
			attributes = new ArrayList<Attribute>();
		}
		return attributes;
	}

	public void setAttributes(List<Attribute> attributes) {
		this.attributes = attributes;
	}

	public MetaInfo getMetaInfo() {
		return metaInfo;
	}

	public void setMetaInfo(MetaInfo metaInfo) {
		this.metaInfo = metaInfo;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}