package org.kuali.student.poc.learningunit.lu.entity;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;

import org.kuali.student.poc.common.util.UUIDHelper;

@Entity
public class LuAttributeType {
	@Id
	private String id;

	// this is an alternate key, undecided on id vs name for primary
	@Column(nullable = false, unique = true)
	private String name;

	@OneToMany(mappedBy = "luAttributeType")
	private Set<LuAttribute> luAttributes;

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

	/**
	 * @return the luAttributes
	 */
	public Set<LuAttribute> getLuAttributes() {
		return luAttributes;
	}

	/**
	 * @param luAttributes
	 *            the luAttributes to set
	 */
	public void setLuAttributes(Set<LuAttribute> luAttributes) {
		this.luAttributes = luAttributes;
	}
}
