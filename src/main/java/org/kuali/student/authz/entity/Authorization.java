package org.kuali.student.authz.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Version;

import org.kuali.student.poc.common.util.UUIDHelper;


@Entity
@Table(name="AUTHZ")
public class Authorization {

    @Id
    String id;
    boolean descendTree = true;
    
    // For compatibility with Rice ORM (OJB)
    @Column(name="OBJ_ID", length=36)
    private String objectId;
    
    @Version
    @Column(name="VER_NBR")
    private int versionNumber;
    
    @ManyToOne(fetch = FetchType.LAZY)
    Principal principal;
    
    @ManyToOne(fetch = FetchType.LAZY)
    Qualifier qualifier;
    
    @ManyToOne(fetch = FetchType.LAZY)
    Role role;
    
    
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
    
    public Principal getPrincipal() {
        return principal;
    }
    
    public void setPrincipal(Principal principal) {
        this.principal = principal;
    }
    
    public Qualifier getQualifier() {
        return qualifier;
    }
    
    public void setQualifier(Qualifier qualifier) {
        this.qualifier = qualifier;
    }
    
    public Role getRole() {
        return role;
    }
    
    public void setRole(Role role) {
        this.role = role;
    }
    
    public boolean getDescendTree() {
        return descendTree;
    }
    
    public boolean isDescendTree() {
        return descendTree;
    }
    
    public void setDescendTree(boolean descendTree) {
        this.descendTree = descendTree;
    }
}
