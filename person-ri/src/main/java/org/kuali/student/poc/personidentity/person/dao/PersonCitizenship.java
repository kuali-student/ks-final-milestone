package org.kuali.student.poc.personidentity.person.dao;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "Person_Citizenship_T")
@TableGenerator(name = "idGen")
public class PersonCitizenship {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "idGen")
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "Person_ID",nullable = false)
    private Person person;

    private String countryOfCitizenship;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date effectiveStartDate;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date effectiveEndDate;   
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateTime;
    
    private String updateUserId;
    private String updateUserComment;
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Person getPerson() {
        return person;
    }
    public void setPerson(Person person) {
        this.person = person;
    }
    public String getCountryOfCitizenship() {
        return countryOfCitizenship;
    }
    public void setCountryOfCitizenship(String countryOfCitizenship) {
        this.countryOfCitizenship = countryOfCitizenship;
    }
    public Date getEffectiveStartDate() {
        return effectiveStartDate;
    }
    public void setEffectiveStartDate(Date effectiveStartDate) {
        this.effectiveStartDate = effectiveStartDate;
    }
    public Date getEffectiveEndDate() {
        return effectiveEndDate;
    }
    public void setEffectiveEndDate(Date effectiveEndDate) {
        this.effectiveEndDate = effectiveEndDate;
    }
    public Date getUpdateTime() {
        return updateTime;
    }
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
    public String getUpdateUserId() {
        return updateUserId;
    }
    public void setUpdateUserId(String updateUserId) {
        this.updateUserId = updateUserId;
    }
    public String getUpdateUserComment() {
        return updateUserComment;
    }
    public void setUpdateUserComment(String updateUserComment) {
        this.updateUserComment = updateUserComment;
    }
    
     
}
