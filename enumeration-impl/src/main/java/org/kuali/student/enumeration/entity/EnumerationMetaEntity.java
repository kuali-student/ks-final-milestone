package org.kuali.student.enumeration.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;

import org.kuali.student.poc.common.util.UUIDHelper;

@Entity
public class EnumerationMetaEntity implements Serializable{
    @Id
    String id;

    String enumerationKey;
    String name;
    String enumerationMetaKeyDesc;
    
    @OneToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    List<EnumeratedValueFieldEntity> enumeratedValueFieldList = new ArrayList<EnumeratedValueFieldEntity>();
    /**
     * AutoGenerate the id
     */
    @PrePersist
    public void prePersist() {
        this.id = UUIDHelper.genStringUUID();
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEnumerationKey() {
        return enumerationKey;
    }

    public void setEnumerationKey(String key) {
        this.enumerationKey = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEnumerationMetaKeyDesc() {
        return enumerationMetaKeyDesc;
    }

    public void setEnumerationMetaKeyDesc(String desc) {
        this.enumerationMetaKeyDesc = desc;
    }
    public List<EnumeratedValueFieldEntity> getEnumeratedValueFieldList() {
        return enumeratedValueFieldList;
    }
    public void setEnumeratedValueFieldList(List<EnumeratedValueFieldEntity> enumeratedValueFieldList) {
        this.enumeratedValueFieldList = enumeratedValueFieldList;
    }

}
