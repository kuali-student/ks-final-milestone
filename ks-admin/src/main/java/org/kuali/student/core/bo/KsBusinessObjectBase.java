package org.kuali.student.core.bo;

import java.util.LinkedHashMap;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.apache.ojb.broker.PersistenceBroker;
import org.apache.ojb.broker.PersistenceBrokerException;
import org.kuali.rice.kns.bo.PersistableBusinessObjectBase;
import org.kuali.student.common.util.UUIDHelper;

@MappedSuperclass
public abstract class KsBusinessObjectBase extends PersistableBusinessObjectBase implements KsBusinessObject {

    private static final long serialVersionUID = 1L;
    
    @Id
    @Column(name = "ID")
    private String id;

    
    @Override
    public void prePersist() {
        this.id = UUIDHelper.genStringUUID(this.id);
        super.prePersist();
    }

// No longer needed, build via reflection
//    @Override
//    protected LinkedHashMap<String, Object> toStringMapper() {
//        LinkedHashMap<String, Object> toStringMap = new LinkedHashMap<String, Object>();
//        
//        toStringMap.put("id", id);
//        
//        return toStringMap;
//    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
