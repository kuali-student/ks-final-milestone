package org.kuali.student.poc.personidentity.person.dao;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

@Entity
@Table(name = "Person_Attribute_Set_Type_T")
@TableGenerator(name = "idGen")
@NamedQueries(
        {@NamedQuery( name = "findByName",
                query = "SELECT t FROM PersonAttributeSetType t WHERE t.name LIKE :nameMatch")}
)
public class PersonAttributeSetType {

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "idGen")
	private Long id;

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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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
