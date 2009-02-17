package org.kuali.student.lum.lu.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.kuali.student.core.entity.AttributeOwner;
import org.kuali.student.core.entity.MetaEntity;

@Entity
@Table(name = "KS_LUI_T")
public class Lui extends MetaEntity implements AttributeOwner<LuiAttribute> {

	@Id
	@Column(name="ID")
	private String id;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
	private List<LuiAttribute> attributes;
	
	@ManyToOne
	@JoinColumn(name="CLU_ID")
	private Clu clu;

	
	@Column(name="ATP_ID")
	private String atpId; //Foreign Service Key
	
	@Override
	public List<LuiAttribute> getAttributes() {
		if (attributes == null) {
			attributes = new ArrayList<LuiAttribute>();
		}
		return attributes;
	}

	@Override
	public void setAttributes(List<LuiAttribute> attributes) {
		this.attributes = attributes;
	}

	public Clu getClu() {
		return clu;
	}

	public void setClu(Clu clu) {
		this.clu = clu;
	}

	public String getAtpId() {
		return atpId;
	}

	public void setAtpId(String atpId) {
		this.atpId = atpId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
