package org.kuali.student.common.ui.client.mvc.test;

import java.util.HashMap;
import java.util.Map;

import org.kuali.student.core.dto.Idable;

public class Person implements Idable {
    private String id = null;
    private String firstName = null;
    private String lastName = null;
    private final Map<String, Address> addresses = new HashMap<String, Address>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public Map<String, Address> getAddresses() {
        return addresses;
    }

}
