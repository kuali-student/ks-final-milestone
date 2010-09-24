package org.kuali.student.core.enumerationmanagement.bo;

import java.util.LinkedHashMap;

import org.kuali.rice.kns.bo.Inactivateable;
import org.kuali.rice.kns.bo.PersistableBusinessObjectBase;

public class EnumerationContextValue extends PersistableBusinessObjectBase implements Inactivateable {
    
    private static final long serialVersionUID = 1L;
    
    private Long contextId;
    private Long enumerationValueId;
    private boolean active;
    
    private EnumerationContext enumerationContext;
    private EnumerationValue enumerationValue;
    
    
    @Override
    protected LinkedHashMap<String, Object> toStringMapper() {
        LinkedHashMap<String, Object> toStringMap = new LinkedHashMap<String, Object>();
        
        toStringMap.put("contextId", contextId);
        toStringMap.put("enumerationValueId", enumerationValueId);
        toStringMap.put("active", active);
        
        return toStringMap;
    }

    @Override
    public boolean isActive() {
        return active;
    }

    @Override
    public void setActive(boolean active) {
        this.active = active;
    }

    public Long getContextId() {
        return contextId;
    }

    public void setContextId(Long contextId) {
        this.contextId = contextId;
    }

    public Long getEnumerationValueId() {
        return enumerationValueId;
    }

    public void setEnumerationValueId(Long enumerationValueId) {
        this.enumerationValueId = enumerationValueId;
    }

    public EnumerationContext getEnumerationContext() {
        return enumerationContext;
    }

    public void setEnumerationContext(EnumerationContext enumerationContext) {
        this.enumerationContext = enumerationContext;
    }

    public EnumerationValue getEnumerationValue() {
        return enumerationValue;
    }

    public void setEnumerationValue(EnumerationValue enumerationValue) {
        this.enumerationValue = enumerationValue;
    }

}
