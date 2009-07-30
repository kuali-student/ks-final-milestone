package org.kuali.student.common.ui.client.configurableui.demo;

import org.kuali.student.core.dto.Idable;

public class Student implements Idable{
    private String firstName;
    private String lastName;
    private String id;
    private Department department;
    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    @Override
    public String getId() {

        return id;
    }
    @Override
    public void setId(String id) {
      this.id = id; 
        
    }
    
    
}
