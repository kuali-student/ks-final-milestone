package org.kuali.student.lum.atp.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.kuali.student.core.entity.Meta;

@Entity
public class Milestone {
	@Id
	@Column(name = "MILESTONE_KEY")
	private String key;
	private String name;
	@Column(name = "MILESTONE_DESC")
	private String desc;
	@ManyToOne
	private Atp atp;
	@Temporal(TemporalType.TIMESTAMP)
	private Date milestoneDate;
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
	private List<MilestoneAttribute> attributes;
	@Embedded
	private Meta meta;
	@ManyToOne
	@JoinColumn(name="MilestoneType")
	private MilestoneType type;
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

	public Date getMilestoneDate() {
		return milestoneDate;
	}

	public void setMilestoneDate(Date milestoneDate) {
		this.milestoneDate = milestoneDate;
	}

	public List<MilestoneAttribute> getAttributes() {
		return attributes;
	}

	public void setAttributes(List<MilestoneAttribute> attributes) {
		this.attributes = attributes;
	}

	public MilestoneType getType() {
		return type;
	}

	public void setType(MilestoneType type) {
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

	public Meta getMeta() {
		return meta;
	}

	public void setMeta(Meta meta) {
		this.meta = meta;
	}

}
