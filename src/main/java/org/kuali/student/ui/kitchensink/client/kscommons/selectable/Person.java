package org.kuali.student.ui.kitchensink.client.kscommons.selectable;

import org.kuali.student.core.dto.Idable;

import com.google.gwt.user.client.rpc.IsSerializable;

public class Person implements IsSerializable, Idable {
    private String id;
    private String lastName;
    private String firstName;

    public Person(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;     
    }
    
    public Person() {
        this(null,null);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
}