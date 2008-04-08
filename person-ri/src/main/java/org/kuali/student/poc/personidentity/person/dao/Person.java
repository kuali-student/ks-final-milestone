package org.kuali.student.poc.personidentity.person.dao;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "Person_T")
@TableGenerator(name = "idGen")
@NamedQueries(
        {@NamedQuery( name = "Person.findByName",
                query = "SELECT DISTINCT p FROM Person p JOIN p.personNames n WHERE n.givenName LIKE :firstName AND n.surname LIKE :lastName")}
)
public class Person {

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "idGen")
	private Long id;

	@OneToOne(cascade = CascadeType.ALL, mappedBy = "person")
	private PersonalInformation personalInformation;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "person")
	private Set<PersonName> personNames;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "person")
    private Set<PersonReferenceId> personReferenceIds;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "person")
    private Set<PersonCitizenship> personCitizenships;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "person")
	private Set<PersonAttribute> attributes;

	@ManyToMany
	@JoinTable(name = "Person_PersonType_J", joinColumns = @JoinColumn(name = "Person_ID", referencedColumnName = "ID"), inverseJoinColumns = @JoinColumn(name = "PersonType_ID", referencedColumnName = "ID"))
	protected Set<PersonType> personTypes;
  
	public Person() {
		id = null;
		attributes = null;
		personTypes = null;
		personNames= null;
	}

	//This needs to be removed
	public Person(String firstName, String lastName) {
		super();
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	
	public PersonalInformation getPersonalInformation() {
        return personalInformation;
    }

    public void setPersonalInformation(PersonalInformation personalInformation) {
        this.personalInformation = personalInformation;
    }

    public Set<PersonAttribute> getAttributes() {
		if (attributes == null) {
			attributes = new HashSet<PersonAttribute>();
		}
		return attributes;
	}

	public void setAttributes(Set<PersonAttribute> attributes) {
		this.attributes = attributes;
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

    public Set<PersonName> getPersonNames() {
        if (personNames == null) {
            personNames = new HashSet<PersonName>();
        }
        return personNames;
    }

    public void setPersonNames(Set<PersonName> personNames) {
        if (personNames == null) {
            personNames = new HashSet<PersonName>();
        }
        this.personNames = personNames;
    }

    public Set<PersonReferenceId> getPersonReferenceIds() {
        if (personReferenceIds == null) {
            personReferenceIds = new HashSet<PersonReferenceId>();
        }
        return personReferenceIds;
    }

    public void setPersonReferenceIds(Set<PersonReferenceId> personReferenceIds) {
        if (personReferenceIds == null) {
            personReferenceIds = new HashSet<PersonReferenceId>();
        }
        this.personReferenceIds = personReferenceIds;
    }

    public Set<PersonCitizenship> getPersonCitizenships() {
        if (personCitizenships == null) {
            personCitizenships = new HashSet<PersonCitizenship>();
        }
        return personCitizenships;
    }

    public void setPersonCitizenships(Set<PersonCitizenship> personCitizenships) {
        if (personCitizenships == null) {
            personCitizenships = new HashSet<PersonCitizenship>();
        }
        this.personCitizenships = personCitizenships;
    }

    
}
