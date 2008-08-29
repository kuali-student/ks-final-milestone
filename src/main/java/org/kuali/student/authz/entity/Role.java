package org.kuali.student.authz.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Version;

import org.kuali.student.poc.common.util.UUIDHelper;

@Entity
public class Role {

    @Id
    private String id;
    private String name;
    
    // For compatibility with Rice ORM (OJB)
    @Column(name="OBJ_ID", length=36)
    private String objectId;
    
    @Version
    @Column(name="VER_NBR")
    private int versionNumber;
    
    @OneToMany(mappedBy = "role")
    private List<Authorization> authorizations = new ArrayList<Authorization>();
    
    @ManyToMany
    private List<Permission> permissions = new ArrayList<Permission>();
    
    @ManyToOne(fetch = FetchType.LAZY)
    private QualifierType qualifierType;
    
    @ManyToOne(fetch = FetchType.LAZY)
    private QualifierHierarchy qualifierHierarchy;
    
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
    
    public List<Authorization> getAuthorizations() {
        if(authorizations == null)
            authorizations = new ArrayList<Authorization>();
        return authorizations;
    }
    
    public void setAuthorizations(List<Authorization> authorizations) {
        this.authorizations = authorizations;
    }
    
    public List<Permission> getPermissions() {
        if(permissions == null)
            permissions = new ArrayList<Permission>();
        return permissions;
    }
    
    public void setPermissions(List<Permission> permissions) {
        this.permissions = permissions;
    }

	/**
	 * @return the qualifierType
	 */
	public QualifierType getQualifierType() {
		return qualifierType;
	}

	/**
	 * @param qualifierType the qualifierType to set
	 */
	public void setQualifierType(QualifierType qualifierType) {
		this.qualifierType = qualifierType;
	}

	/**
	 * @return the qualifierHierarchy
	 */
	public QualifierHierarchy getQualifierHierarchy() {
		return qualifierHierarchy;
	}

	/**
	 * @param qualifierHierarchy the qualifierHierarchy to set
	 */
	public void setQualifierHierarchy(QualifierHierarchy qualifierHierarchy) {
		this.qualifierHierarchy = qualifierHierarchy;
	}
}
