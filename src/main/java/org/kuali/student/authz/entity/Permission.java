package org.kuali.student.authz.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.PrePersist;
import javax.persistence.Version;

import org.kuali.student.poc.common.util.UUIDHelper;

@Entity
public class Permission {

    @Id
    private String id;
    private String name;
    
    // For compatibility with Rice ORM (OJB)
    @Column(name="OBJ_ID", length=36)
    private String objectId;
    
    @Version
    @Column(name="VER_NBR")
    private int versionNumber;
    
    @ManyToMany(mappedBy="permissions")
    List<Role> roles = new ArrayList<Role>();
    
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
    
    public String getObjectId() {
        return objectId;
    }
    
    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }
    
    public int getVersion() {
        return versionNumber;
    }
    
    public void setVersion(int versionNumber) {
        this.versionNumber = versionNumber;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public List<Role> getRoles() {
        if(roles ==  null)
            roles = new ArrayList<Role>();
        return roles;
    }
    
    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }
}
