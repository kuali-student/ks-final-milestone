package org.kuali.student.poc.personidentity.person.dao;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

@Entity
@Table(name = "Person_Attribute_Definition_T")
@TableGenerator(name = "idGen")
public class PersonAttributeType {

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "idGen")
	private Long id;

	// this is an alternate key, undecided on id vs name for primary
	@Column(nullable = false, unique = true)
	private String name;

	private String type;

	private String displayLabel;

	private String definition;

	private boolean readOnlyFlag;

	private String validator;

	@ManyToMany(mappedBy = "personAttributeTypes")
	private Set<PersonAttributeSetType> personAttributeSetTypes;

	@OneToMany(mappedBy = "personAttributeType")
	private Set<PersonAttribute> personAttributes;

	public PersonAttributeType() {
		personAttributeSetTypes = null;
		personAttributes = null;
	}

	public PersonAttributeType(String name, String type) {
		super();
		this.name = name;
		this.type = type;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Set<PersonAttribute> getPersonAttributes() {
		if (personAttributes == null) {
			personAttributes = new HashSet<PersonAttribute>();
		}
		return personAttributes;
	}

	public void setPersonAttributes(Set<PersonAttribute> personAttributes) {
		this.personAttributes = personAttributes;
	}

	public Set<PersonAttributeSetType> getPersonAttributeSetTypes() {
		if (personAttributeSetTypes == null) {
			personAttributeSetTypes = new HashSet<PersonAttributeSetType>();
		}
		return personAttributeSetTypes;
	}

	public void setPersonAttributeSetTypes(
			Set<PersonAttributeSetType> personAttributeSetTypes) {
		this.personAttributeSetTypes = personAttributeSetTypes;
	}

	public String getDisplayLabel() {
		return displayLabel;
	}

	public void setDisplayLabel(String displayLabel) {
		this.displayLabel = displayLabel;
	}

	public String getDefinition() {
		return definition;
	}

	public void setDefinition(String definition) {
		this.definition = definition;
	}

	public boolean isReadOnlyFlag() {
		return readOnlyFlag;
	}

	public void setReadOnlyFlag(boolean readOnlyFlag) {
		this.readOnlyFlag = readOnlyFlag;
	}

	public String getValidator() {
		return validator;
	}

	public void setValidator(String validator) {
		this.validator = validator;
	}
}
