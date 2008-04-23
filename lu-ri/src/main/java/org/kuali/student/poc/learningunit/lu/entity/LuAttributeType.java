package org.kuali.student.poc.learningunit.lu.entity;

import java.util.HashSet;
import java.util.Set;

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

	@ManyToMany(mappedBy = "luAttributeTypes")
	private Set<LuType> luTypes;

	/**
	 * AutoGenerate the Id
	 */
	@PrePersist
	public void prePersist() {
		this.id = UUIDHelper.genStringUUID();
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

//	/**
//	 * @return the luAttributes
//	 */
//	public Set<LuAttribute> getLuAttributes() {
//		if (luAttributes == null) {
//			luAttributes = new HashSet<LuAttribute>();
//		}
//		return luAttributes;
//	}
//
//	/**
//	 * @param luAttributes
//	 *            the luAttributes to set
//	 */
//	public void setLuAttributes(Set<LuAttribute> luAttributes) {
//		this.luAttributes = luAttributes;
//	}

	/**
	 * @return the luTypes
	 */
	public Set<LuType> getLuTypes() {
		if (luTypes == null) {
			luTypes = new HashSet<LuType>();
		}
		return luTypes;
	}

	/**
	 * @param luTypes
	 *            the luTypes to set
	 */
	public void setLuTypes(Set<LuType> luTypes) {
		this.luTypes = luTypes;
	}
}
