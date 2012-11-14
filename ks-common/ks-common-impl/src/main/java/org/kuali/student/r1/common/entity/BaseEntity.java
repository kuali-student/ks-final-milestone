package org.kuali.student.r1.common.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Version;

import org.kuali.student.common.util.UUIDHelper;
import org.kuali.student.r2.common.entity.PersistableEntity;

@MappedSuperclass
public abstract class BaseEntity  implements PersistableEntity<String> {
	
	@Id
	@Column(name = "ID")
	private String id;
	
	@Version
	@Column(name="VER_NBR")
	private Long versionNumber;
	
	@Column(name="OBJ_ID",length=KSEntityConstants.OBJ_ID_LENGTH)
	private String objectId;
	
	public String getObjectId() {
		return objectId;
	}

	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}

	@PrePersist
	public void prePersist(){
		//Auto generate the object id, and auto generate the ID if it's not set 
        this.id = UUIDHelper.genStringUUID(this.id);
		this.objectId = UUIDHelper.genStringUUID();
		onPrePersist();
	}
	
	@PreUpdate
	public void preUpdate(){
		onPreUpdate();
	}
	

	//Override this to add additional functionality for the PrePersist Lifecycle
	protected void onPrePersist() {
	}
	
	//Override this to add additional functionality for the PreUpdate Lifecycle
	protected void onPreUpdate() {
	}
	
	public Long getVersionNumber() {
		return versionNumber;
	}

	public void setVersionNumber(Long versionNumber) {
		this.versionNumber = versionNumber;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
