package org.kuali.student.poc.learningunit.lu.dao;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.TableGenerator;
import javax.xml.bind.annotation.XmlAttribute;

@Entity
@TableGenerator(name = "idGen")
public class Atp {
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "idGen")
	private String atpId;
	private String atpName;
}
