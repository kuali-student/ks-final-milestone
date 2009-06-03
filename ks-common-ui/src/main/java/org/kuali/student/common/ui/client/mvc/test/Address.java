package org.kuali.student.common.ui.client.mvc.test;

import org.kuali.student.core.dto.Idable;

public class Address implements Idable {
    private String id = null;
    private String street = null;
    private String city = null;
    private String state = null;
    private String zip = null;

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

}
