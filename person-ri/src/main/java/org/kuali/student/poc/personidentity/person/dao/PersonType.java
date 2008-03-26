package org.kuali.student.poc.personidentity.person.dao;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
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
@Table(name = "PersonType_T")
@TableGenerator(name = "idGen")
@NamedQueries(
		{@NamedQuery( name = "PersonType.findByName",
				query = "SELECT t FROM PersonType t WHERE t.name LIKE :nameMatch")}
)
public class PersonType {

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "idGen")
	private Long id;
	
	@Column(nullable = false, unique = true)
	private String name;
	
	@ManyToMany(mappedBy="personTypes")
	protected Set<Person> people;

	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "PersonType_PersonAttrSetTyp_J",
			joinColumns = @JoinColumn(name = "PersonType_ID", referencedColumnName = "ID"),
			inverseJoinColumns = @JoinColumn(name = "PersonAttrSetTyp_ID", referencedColumnName = "ID")
	)
	protected Set<PersonAttributeSetType> personAttributeSetTypes;
	
	public PersonType() {
		id = null;
		people = null;
		personAttributeSetTypes = null;
	}
	
	public PersonType(String name) {
		super();
		this.name = name;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Set<Person> getPeople() {
		if(people == null) {
			people = new HashSet<Person>();
		}
		return people;
	}

	public void setPeople(Set<Person> people) {
		this.people = people;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<PersonAttributeSetType> getPersonAttributeSetTypes() {
		if(personAttributeSetTypes == null) {
			personAttributeSetTypes = new HashSet<PersonAttributeSetType>();
		}
		return personAttributeSetTypes;
	}

	public void setPersonAttributeSetTypes(
			Set<PersonAttributeSetType> personAttributeSetTypes) {
		this.personAttributeSetTypes = personAttributeSetTypes;
	}
}
