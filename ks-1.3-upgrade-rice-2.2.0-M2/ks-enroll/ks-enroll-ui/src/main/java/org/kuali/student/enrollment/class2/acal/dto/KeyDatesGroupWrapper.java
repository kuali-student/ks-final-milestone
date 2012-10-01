package org.kuali.student.enrollment.class2.acal.dto;

import org.apache.commons.lang.StringUtils;
import org.kuali.student.r2.core.type.dto.TypeInfo;

import java.util.ArrayList;
import java.util.List;

public class KeyDatesGroupWrapper {

    private String keyDateGroupType;
    private String keyDateGroupNameUI;
    private List<KeyDateWrapper> keydates;

    private TypeInfo typeInfo;

    public KeyDatesGroupWrapper(){
        keydates = new ArrayList<KeyDateWrapper>();
    }

    public KeyDatesGroupWrapper(String keydateGroupType,String nameUI){
        this.keydates = new ArrayList<KeyDateWrapper>();
        this.keyDateGroupType = keydateGroupType;
        this.keyDateGroupNameUI = nameUI;
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

    public TypeInfo getTypeInfo() {
        return typeInfo;
    }

    public void setTypeInfo(TypeInfo typeInfo) {
        this.typeInfo = typeInfo;
    }

    public boolean isKeyDateExists(String keyDateType){
        for (KeyDateWrapper keydate : keydates) {
            if (StringUtils.equals(keyDateType,keydate.getKeyDateType())){
                return true;
            }
        }
        return false;
    }

}
