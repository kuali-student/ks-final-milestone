package org.kuali.student.core.enumerationmanagement.bo;

import java.util.LinkedHashMap;
import java.util.List;

import org.kuali.rice.kns.bo.PersistableBusinessObjectBase;

public class EnumerationTable extends PersistableBusinessObjectBase {

    private static final long serialVersionUID = 1L;

    Long id;
    String enumerationKey;
    String name;
    String description;
    
    List<EnumerationValue> enumerationValueList;
    List<EnumerationContext> enumerationContextList;
    
    @Override
    protected LinkedHashMap<String,Object> toStringMapper() {
        LinkedHashMap<String, Object> toStringMap = new LinkedHashMap<String, Object>();
        
        toStringMap.put("id", id);
        toStringMap.put("enumerationKey", enumerationKey);
        toStringMap.put("name", name);
        toStringMap.put("description", description);
        
        return toStringMap;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEnumerationKey() {
        return enumerationKey;
    }

    public void setEnumerationKey(String enumerationKey) {
        this.enumerationKey = enumerationKey;
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

    public List<EnumerationValue> getEnumerationValueList() {
        return enumerationValueList;
    }

    public void setEnumerationValueList(List<EnumerationValue> enumerationValueList) {
        this.enumerationValueList = enumerationValueList;
    }

    public List<EnumerationContext> getEnumerationContextList() {
        return enumerationContextList;
    }

    public void setEnumerationContextList(List<EnumerationContext> enumerationContextList) {
        this.enumerationContextList = enumerationContextList;
    }

}
