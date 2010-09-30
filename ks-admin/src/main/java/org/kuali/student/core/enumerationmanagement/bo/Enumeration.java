package org.kuali.student.core.enumerationmanagement.bo;

import java.util.LinkedHashMap;
import java.util.List;

import org.kuali.rice.kns.bo.InactivatableFromToImpl;

public class Enumeration extends InactivatableFromToImpl {

    private static final long serialVersionUID = 1L;

    private String key;
    private String name;
    private String description;
    
    List<EnumeratedValue> enumeratedValueList;
    
    @Override
    protected LinkedHashMap<String,Object> toStringMapper() {
        LinkedHashMap<String, Object> toStringMap = new LinkedHashMap<String, Object>();
        
        toStringMap.put("key", key);
        toStringMap.put("name", name);
        toStringMap.put("description", description);
        toStringMap.put("activeFromDate", activeFromDate);
        toStringMap.put("activeToDate", activeToDate);
        
        return toStringMap;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String id) {
        this.key = id;
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
