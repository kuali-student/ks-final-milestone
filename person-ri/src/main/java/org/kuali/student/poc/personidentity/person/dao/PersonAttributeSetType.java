package org.kuali.student.poc.personidentity.person.dao;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import org.kuali.student.poc.common.util.UUIDHelper;

@Entity
@Table(name = "Person_Attribute_Set_Type_T")
@NamedQueries( { @NamedQuery(name = "PersonAttributeSetType.findByName", query = "SELECT t FROM PersonAttributeSetType t WHERE t.name LIKE :nameMatch") })
public class PersonAttributeSetType {

	@Id
	private String id;

	@Column(nullable = false, unique = true)
	private String name;

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinTable(name = "PersonAtSetTyp_PersonAtTyp_J", joinColumns = @JoinColumn(name = "PersonAtSetTyp_ID", referencedColumnName = "ID"), inverseJoinColumns = @JoinColumn(name = "PersonAtTyp_ID", referencedColumnName = "ID"))
	private Set<PersonAttributeType> personAttributeTypes;

	@ManyToMany(mappedBy = "personAttributeSetTypes")
	private Set<PersonType> personTypes;

	private Boolean required;

	private Boolean readOnlyFlag;

	public PersonAttributeSetType() {
		id = null;
		personAttributeTypes = null;
		personTypes = null;
	}

	public PersonAttributeSetType(String name, Boolean required) {
		super();
		this.name = name;
		this.required = required;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	/**
	 * AutoGenerate the Id
	 */
	@PrePersist
	public void prePersist() {
		this.id = UUIDHelper.genStringUUID();
	}

	public Set<PersonAttributeType> getPersonAttributeTypes() {
		if (personAttributeTypes == null) {
			personAttributeTypes = new HashSet<PersonAttributeType>();
		}

		return personAttributeTypes;
	}

	public void setPersonAttributeTypes(
			Set<PersonAttributeType> personAttributeType) {
		this.personAttributeTypes = personAttributeType;
	}

	public Boolean getRequired() {
		return required;
	}

	public void setRequired(Boolean required) {
		this.required = required;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<PersonType> getPersonTypes() {
		if (personTypes == null) {
			personTypes = new HashSet<PersonType>();
		}
		return personTypes;
	}

	public void setPersonTypes(Set<PersonType> personTypes) {
		this.personTypes = personTypes;
	}

	public Boolean getReadOnlyFlag() {
		return readOnlyFlag;
	}

	public void setReadOnlyFlag(Boolean readOnlyFlag) {
		this.readOnlyFlag = readOnlyFlag;
	}
}
