package org.kuali.student.enrollment.class2.acal.dto;

import java.util.ArrayList;
import java.util.List;

public class KeyDatesGroupWrapper {

    private String keyDateGroupType;
    private String keyDateGroupNameUI;
    private List<KeyDateWrapper> keydates = new ArrayList<KeyDateWrapper>();

    public KeyDatesGroupWrapper(){

    }

    public String getKeyDateGroupNameUI() {
        return keyDateGroupNameUI;
    }

    public void setKeyDateGroupNameUI(String keyDateGroupNameUI) {
        this.keyDateGroupNameUI = keyDateGroupNameUI;
    }

    public String getKeyDateGroupType() {
        return keyDateGroupType;
    }

    public void setKeyDateGroupType(String keyDateGroupType) {
        this.keyDateGroupType = keyDateGroupType;
    }

    public List<KeyDateWrapper> getKeydates() {
        return keydates;
    }

    public void setKeydates(List<KeyDateWrapper> keydates) {
        this.keydates = keydates;
    }




}
