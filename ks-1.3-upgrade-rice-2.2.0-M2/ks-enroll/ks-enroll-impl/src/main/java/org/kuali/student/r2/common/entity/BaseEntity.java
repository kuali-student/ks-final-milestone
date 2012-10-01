package org.kuali.student.r2.common.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import org.kuali.student.common.entity.KSEntityConstants;
import org.kuali.student.common.util.UUIDHelper;

@MappedSuperclass
public class BaseEntity {

    @Id
    @Column(name = "ID")
    private String id;
    @Column(name = "OBJ_ID", length = KSEntityConstants.OBJ_ID_LENGTH)
    private String objectId;

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    @PrePersist
    public void prePersist() {
        //Auto generate the object id, and auto generate the ID if it's not set
        if (this.id == null) {
            this.id = UUIDHelper.genStringUUID(this.id);
        }
        this.objectId = UUIDHelper.genStringUUID();
        onPrePersist();
    }

    @PreUpdate
    public void preUpdate() {
        onPreUpdate();
    }

    //Override this to add additional functionality for the PrePersist Lifecycle
    protected void onPrePersist() {
    }

    //Override this to add additional functionality for the PreUpdate Lifecycle
    protected void onPreUpdate() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("BaseEntity [id=");
		builder.append(id);
		builder.append(", objectId=");
		builder.append(objectId);
		builder.append("]");
		return builder.toString();
	}
    
    
}
