package org.kuali.student.poc.learningunit.luipersonrelation.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.TableGenerator;

import org.kuali.student.poc.learningunit.lu.entity.Lui;
import org.kuali.student.poc.personidentity.person.dao.Person;

@Entity
@TableGenerator(name = "idGen")
public class LuiPersonRelation {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "idGen")
    private String id;
    
    private Lui lui;
    private Person person;
    private LuiPersonRelationType luiPersonRelationType;
    private RelationState relationState;
    private Date effectiveStartDate;
    private Date effectiveEndDate;
}
