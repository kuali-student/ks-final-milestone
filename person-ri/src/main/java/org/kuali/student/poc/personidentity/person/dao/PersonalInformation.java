package org.kuali.student.poc.personidentity.person.dao;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "Personal_Information_T")
@TableGenerator(name = "idGen")
public class PersonalInformation {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "idGen")
    private Long id;   
    
    @OneToOne
    @JoinColumn(name = "Person_ID", nullable = false)  
    private Person person;
    
    private char Gender;

    @Temporal(TemporalType.TIMESTAMP)
    private Date dateOfBirth;
    
    private String photo;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date deceasedDate;

    private boolean confidential;
   
    private String maritalStatus;
    
    private String primaryLanguageCode;
    
    private String secondaryLanguageCode;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateDate;
    
    @ManyToOne
    @JoinColumn(name = "update_person_id")
    private Person updatePerson;
    
    private String updateComment;
    
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

    public char getGender() {
        return Gender;
    }

    public void setGender(char gender) {
        Gender = gender;
    }
   
    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public boolean isConfidential() {
        return confidential;
    }

    public void setConfidential(boolean confidential) {
        this.confidential = confidential;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public Date getDeceasedDate() {
        return deceasedDate;
    }

    public void setDeceasedDate(Date deceasedDate) {
        this.deceasedDate = deceasedDate;
    }

    public String getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public String getPrimaryLanguageCode() {
        return primaryLanguageCode;
    }

    public void setPrimaryLanguageCode(String primaryLanguageCode) {
        this.primaryLanguageCode = primaryLanguageCode;
    }

    public String getSecondaryLanguageCode() {
        return secondaryLanguageCode;
    }

    public void setSecondaryLanguageCode(String secondaryLanguageCode) {
        this.secondaryLanguageCode = secondaryLanguageCode;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public Person getUpdatePerson() {
        return updatePerson;
    }

    public void setUpdatePerson(Person updatePerson) {
        this.updatePerson = updatePerson;
    }

    public String getUpdateComment() {
        return updateComment;
    }

    public void setUpdateComment(String updateComment) {
        this.updateComment = updateComment;
    }
    
    
}
