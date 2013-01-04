package org.kuali.student.core.enumerationmanagement.bo;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.kuali.student.core.bo.KsInactivatableFromToBase;

public class Enumeration extends KsInactivatableFromToBase {

    private static final long serialVersionUID = 1L;


    private String name;
    private String description;
    
    List<EnumeratedValue> enumeratedValueList;
    
    
    public Enumeration() {
        enumeratedValueList = new ArrayList<EnumeratedValue>();
    }
    
    @Override
    protected LinkedHashMap<String,Object> toStringMapper() {
        LinkedHashMap<String, Object> map = super.toStringMapper();
        
        map.put("name", name);
        map.put("description", description);
        map.put("activeFromDate", activeFromDate);
        map.put("activeToDate", activeToDate);
        
        return map;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<EnumeratedValue> getEnumeratedValueList() {
        return enumeratedValueList;
    }

    public void setEnumeratedValueList(List<EnumeratedValue> enumerationValueList) {
        this.enumeratedValueList = enumerationValueList;
    }

}
