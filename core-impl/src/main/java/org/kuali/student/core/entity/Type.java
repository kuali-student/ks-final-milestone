package org.kuali.student.core.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.kuali.student.core.dto.Attribute;
@Entity
@Inheritance(strategy=InheritanceType.JOINED)
public class Type {
	@Id
	private String key; 
	
	private String name; 

	private String desc; 

	private String durationType; 

	private String seasonalType; 

	@Temporal(TemporalType.TIMESTAMP)
	private Date effectiveDate; 

	@Temporal(TemporalType.TIMESTAMP)
	private Date expirationDate; 

	private List<Attribute> attributes; 


}
