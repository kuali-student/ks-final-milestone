/**
 * Copyright 2010 The Kuali Foundation Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package org.kuali.student.common.ui.client.application;

import java.util.HashMap;
import java.util.Map;

import org.kuali.student.common.rice.authorization.PermissionType;
import org.kuali.student.common.ui.shared.IdAttributes.IdType;

/**
 * ViewContext can be used to pass along context information when switching or initializing a view.
 * 
 * For example a display view requires the id of the object to display, the view context can be used
 * to pass along that information from a different controller or view.
 *
 */
public class ViewContext implements Comparable<ViewContext>{
	
	public static final String ID_ATR = "docId";
	public static final String ID_TYPE_ATR = "idType";
	
	
	private Map<String, String> attributes = new HashMap<String, String>();
	private String id = "";
	private IdType idType = null;
	// FIXME: change state to proper default or null
	private String state = "draft";
	private PermissionType permissionType;

	public String getId() {
		return id;
	}

	/**
	 * Set the id for this view context, this will appear as part of the address bar when used in
	 * a controller, the controller can use this id to determine what to show (determine the context)
	 */
	public void setId(String id) {
		this.id = id;
	}

	public IdType getIdType() {
		return idType;
	}

	/**
	 * Set the type of id for this view context, this will appear as part of the address bar when used in
	 * a controller
	 */
	public void setIdType(IdType idType) {
		this.idType = idType;
	}
	
	public void setIdType(String idTypeString){
		this.idType = IdType.fromString(idTypeString);
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public PermissionType getPermissionType() {
    	return permissionType;
    }

	/**
	 * Sets the type of permission needed to be at the the current view, this is interpreted by the controller
	 * if set which checks with the server to see if the user has this kind of permission.  Doesn't appear in
	 * the address bar
	 */
	public void setPermissionType(PermissionType permissionType) {
    	this.permissionType = permissionType;
    }

	@Override
	public int compareTo(ViewContext o) {
		if(o.getId().equals(id) && o.getIdType() == idType && o.getAttributes().equals(attributes)){
			return 0;
		}
		else{
			return -1;
		}
	}

    /**
     * Add an additional attribute to the view context, this will appear in the address bar like id
     * and type does
     */
    public void setAttribute(String key, String value) {
		attributes.put(key, value);
	}
	
	public String getAttribute(String key){
		return attributes.get(key);
	}
	
	public Map<String, String> getAttributes(){
		return attributes;
	}

}
