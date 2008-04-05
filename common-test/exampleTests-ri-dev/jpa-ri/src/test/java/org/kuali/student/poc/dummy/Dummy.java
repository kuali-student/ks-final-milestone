package org.kuali.student.poc.dummy;

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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "Dummy_T")
//@TableGenerator(name = "idGen")
public class Dummy {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "dummy")
	private Set<DummyAttribute> attributes;

	public Dummy() {
		id = null;
		attributes = null;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	
	public Set<DummyAttribute> getAttributes() {
		if (attributes == null) {
			attributes = new HashSet<DummyAttribute>();
		}
		return attributes;
	}

	public void setAttributes(Set<DummyAttribute> attributes) {
		this.attributes = attributes;
	}

    
}
