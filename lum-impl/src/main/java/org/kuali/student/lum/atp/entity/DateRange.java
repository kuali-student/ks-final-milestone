package org.kuali.student.lum.atp.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.kuali.student.core.entity.AttributeOwner;
import org.kuali.student.core.entity.MetaEntity;

@Entity
public class DateRange extends MetaEntity implements
		AttributeOwner<DateRangeAttribute> {
	@Id
	@Column(name = "DATERANGE_KEY")
	private String key;

	private String name;

	@Column(name = "DATERANGE_DESC")
	private String desc;

	@ManyToOne
	@JoinColumn(name = "ATP_KEY")
	private Atp atp;

	@Temporal(TemporalType.TIMESTAMP)
	private Date startDate;
	@Temporal(TemporalType.TIMESTAMP)
	private Date endDate;
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
	private List<DateRangeAttribute> attributes;
	// @Embedded
	// private Meta meta;
	@ManyToOne
	@JoinColumn(name = "DateRangeType")
	private DateRangeType type;
	private String state;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public Atp getAtp() {
		return atp;
	}

	public void setAtp(Atp atp) {
		this.atp = atp;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public List<DateRangeAttribute> getAttributes() {
		if (attributes == null) {
			attributes = new ArrayList<DateRangeAttribute>();
		}
		return attributes;
	}

	public void setAttributes(List<DateRangeAttribute> attributes) {
		this.attributes = attributes;
	}

	public DateRangeType getType() {
		return type;
	}

	public void setType(DateRangeType type) {
		this.type = type;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	// public Meta getMeta() {
	// return meta;
	// }
	//
	// public void setMeta(Meta meta) {
	// this.meta = meta;
	// }
}
