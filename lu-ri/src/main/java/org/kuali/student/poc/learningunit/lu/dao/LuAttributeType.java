package org.kuali.student.poc.learningunit.lu.dao;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.TableGenerator;

@Entity
@TableGenerator(name = "idGen")
public class LuAttributeType {
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "idGen")
	private String id;

	// this is an alternate key, undecided on id vs name for primary
	@Column(nullable = false, unique = true)
	private String name;
	
	@OneToMany(mappedBy = "luAttributeType")
	private Set<LuAttribute> luAttributes;

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
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
	 * @param name the name to set
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
	 * @param luAttributes the luAttributes to set
	 */
	public void setLuAttributes(Set<LuAttribute> luAttributes) {
		this.luAttributes = luAttributes;
	}
}
