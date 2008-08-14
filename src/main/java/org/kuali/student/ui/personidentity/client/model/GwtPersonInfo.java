/**
 * 
 */
package org.kuali.student.ui.personidentity.client.model;

import java.io.Serializable;
import java.util.Date;

import org.kuali.student.commons.ui.mvc.client.model.ModelObject;

/**
 * @author Garey
 * 
 */
public class GwtPersonInfo extends GwtPersonCreateInfo implements Serializable, ModelObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3154076678438696716L;

	private String personId;
	
	private Date updateTime;

	private String updateUserId;

	private String updateUserComment;

	/**
	 * @return the personId
	 */
	public String getPersonId() {
		return personId;
	}

	/**
	 * @param personId
	 *            the personId to set
	 */
	public void setPersonId(String personId) {
		this.personId = personId;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getUpdateUserId() {
		return updateUserId;
	}

	public void setUpdateUserId(String updateUserId) {
		this.updateUserId = updateUserId;
	}

	public String getUpdateUserComment() {
		return updateUserComment;
	}

	public void setUpdateUserComment(String updateUserComment) {
		this.updateUserComment = updateUserComment;
	}
    
    public String getUniqueId() {       
        return this.personId;
    }
    
    public boolean equals(Object o)
    {
    	boolean isEqual = false;
    	if(o instanceof GwtPersonInfo)
    	{
    		System.out.println("is an Instance of GwtPersonInfo");
    		isEqual = this.getPersonId().equals(((GwtPersonInfo)o).getPersonId());
    	}
    	else
    	{
    		isEqual = super.equals(o);
    	}
    	return isEqual;
    }

	
}
