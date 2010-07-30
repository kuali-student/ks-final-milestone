package org.kuali.student.core.assembly.data;

import java.util.Date;
import java.util.List;

import org.kuali.student.common.validator.old.ConstraintMockAddress;

public class MockPerson {
    protected String firstName;
    
    protected String lastName;
    
    protected String type;
    
    protected String state;
    
    protected String id;
    
    protected String email;
    
    protected Double gpa;
    
    protected Date dob;
    
    protected List<MockAddress> address;

    /**
     * @return the firstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * @param firstName the firstName to set
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * @return the lastName
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * @param lastName the lastName to set
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return the state
     */
    public String getState() {
        return state;
    }

    /**
     * @param state the state to set
     */
    public void setState(String state) {
        this.state = state;
    }

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the address
     */
    public List<MockAddress> getAddress() {
        return address;
    }

    /**
     * @param address the address to set
     */
    public void setAddress(List<MockAddress> address) {
        this.address = address;
    }

    /**
     * @return the gpa
     */
    public Double getGpa() {
        return gpa;
    }

    /**
     * @param gpa the gpa to set
     */
    public void setGpa(Double gpa) {
        this.gpa = gpa;
    }

    /**
     * @return the dob
     */
    public Date getDob() {
        return dob;
    }

    /**
     * @param dob the dob to set
     */
    public void setDob(Date dob) {
        this.dob = dob;
    }   

}
