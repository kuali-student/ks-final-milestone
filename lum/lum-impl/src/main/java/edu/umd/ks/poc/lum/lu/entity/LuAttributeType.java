package edu.umd.ks.poc.lum.lu.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.PrePersist;

import org.kuali.student.poc.common.util.UUIDHelper;

@Entity
public class LuAttributeType {
	@Id
	private String id;

	// this is an alternate key, undecided on id vs name for primary
	@Column(nullable = false, unique = true)
	private String name;

	private boolean list;
	private String dataType;
	private String displayType;
	private String scatId;
	private String groupingCd;
	@ManyToMany(mappedBy = "luAttributeTypes")
	private List<LuType> luTypes;
	private String status;
	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * AutoGenerate the Id
	 */
	@PrePersist
	public void prePersist() {
		this.id = UUIDHelper.genStringUUID(this.id);
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	// /**
	// * @return the luAttributes
	// */
	// public Set<LuAttribute> getLuAttributes() {
	// if (luAttributes == null) {
	// luAttributes = new HashSet<LuAttribute>();
	// }
	// return luAttributes;
	// }
	//
	// /**
	// * @param luAttributes
	// * the luAttributes to set
	// */
	// public void setLuAttributes(Set<LuAttribute> luAttributes) {
	// this.luAttributes = luAttributes;
	// }

	/**
	 * @return the luTypes
	 */
	public List<LuType> getLuTypes() {
		if (luTypes == null) {
			luTypes = new ArrayList<LuType>();
		}
		return luTypes;
	}

	/**
	 * @param luTypes
	 *            the luTypes to set
	 */
	public void setLuTypes(List<LuType> luTypes) {
		this.luTypes = luTypes;
	}

	/**
	 * @return the list
	 */
	public boolean isList() {
		return list;
	}

	/**
	 * @param list the list to set
	 */
	public void setList(boolean list) {
		this.list = list;
	}

	/**
	 * @return the dataType
	 */
	public String getDataType() {
		return dataType;
	}

	/**
	 * @param dataType the dataType to set
	 */
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	/**
	 * @return the displayType
	 */
	public String getDisplayType() {
		return displayType;
	}

	/**
	 * @param displayType the displayType to set
	 */
	public void setDisplayType(String displayType) {
		this.displayType = displayType;
	}

	/**
	 * @return the scatId
	 */
	public String getScatId() {
		return scatId;
	}

	/**
	 * @param scatId the scatId to set
	 */
	public void setScatId(String scatId) {
		this.scatId = scatId;
	}

	/**
	 * @return the groupingCd
	 */
	public String getGroupingCd() {
		return groupingCd;
	}

	/**
	 * @param groupingCd the groupingCd to set
	 */
	public void setGroupingCd(String groupingCd) {
		this.groupingCd = groupingCd;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		if(id!=null){
			return result;
		}
		
		result = prime * result
				+ ((dataType == null) ? 0 : dataType.hashCode());
		result = prime * result
				+ ((displayType == null) ? 0 : displayType.hashCode());
		result = prime * result
				+ ((groupingCd == null) ? 0 : groupingCd.hashCode());
		result = prime * result + (list ? 1231 : 1237);
		result = prime * result + ((luTypes == null) ? 0 : luTypes.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((scatId == null) ? 0 : scatId.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final LuAttributeType other = (LuAttributeType) obj;
		if (dataType == null) {
			if (other.dataType != null)
				return false;
		} else if (!dataType.equals(other.dataType))
			return false;
		if (displayType == null) {
			if (other.displayType != null)
				return false;
		} else if (!displayType.equals(other.displayType))
			return false;
		if (groupingCd == null) {
			if (other.groupingCd != null)
				return false;
		} else if (!groupingCd.equals(other.groupingCd))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (list != other.list)
			return false;
		if (luTypes == null) {
			if (other.luTypes != null)
				return false;
		} else if (!luTypes.equals(other.luTypes))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (scatId == null) {
			if (other.scatId != null)
				return false;
		} else if (!scatId.equals(other.scatId))
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		return true;
	}
}
