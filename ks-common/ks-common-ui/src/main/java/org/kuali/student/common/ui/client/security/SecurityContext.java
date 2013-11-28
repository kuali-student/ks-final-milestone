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

package org.kuali.student.common.ui.client.security;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.kuali.student.common.ui.client.application.KSAsyncCallback;
import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.common.ui.client.service.SecurityRpcService;
import org.kuali.student.common.ui.client.service.SecurityRpcServiceAsync;
import org.kuali.student.r1.common.rice.authorization.PermissionType;

import com.google.gwt.core.client.GWT;

/**
 * An interface to access the Kuali Student security context 
 * 
 * @author Kuali Student Team
 *
 */
public class SecurityContext {

    protected SecurityRpcServiceAsync securityRpcService = GWT.create(SecurityRpcService.class);
    
	private String principalName = "";
	
	private Map <String,Boolean> permissionCache = new HashMap<String, Boolean>();
     
	public void initializeSecurityContext(final Callback<Boolean> contextIntializedCallback){
	    securityRpcService.getPrincipalUsername(new KSAsyncCallback<String>(){
	        public void handleFailure(Throwable caught) {
	        	permissionCache.clear();
				throw new RuntimeException("Fatal - Unable to initialze security context");
			}
	
	        @Override
	        public void onSuccess(String userId) {
	        	permissionCache.clear();
	        	setPrincipalName(userId);
	            contextIntializedCallback.exec(true);
	        }            
	    });
	}

	/**
	 * Set principalName of the user logged in (eg. jDoe)   
	 *
	 * @param principalId
	 */
	public void setPrincipalName(String principalName) {
		this.principalName = principalName;
	}

	/**
	 * @return the userId of user logged into the system. (NOTE: When using KIM userId=principalName)
	 */
	public String getUserId() {
		return principalName;
	}
	
	/**
	 * Used to check if the permission has been loaded into the cache.
	 * 
	 * @return true if permission exists in cache
	 */
	public boolean checkPermissionCache(String permission){
		return permissionCache.containsKey(permission);
	}
	
	/**
     * Used to check if user has a screen permission. If the permission has been cached
     * in the browser, it will use the cached permission.
     * 
     * @param screenComponent
     * @return true if user has access to the screen component
     */
    public void checkScreenPermission(final String screenComponent, final Callback<Boolean> checkPermissionCallback){
        if (permissionCache.containsKey(screenComponent)){
            checkPermissionCallback.exec(permissionCache.get(screenComponent));
        } else {
            securityRpcService.hasScreenPermission(screenComponent, new KSAsyncCallback<Boolean>() {
                @Override
                public void onSuccess(Boolean result) {
                    permissionCache.put(screenComponent, result);
                    checkPermissionCallback.exec(result);
                }
            });
        }
    }
    
    /**
     * Used to check a pre-loaded or cached screen permission.  Note: This should only be called if the permission has
     * been loaded via the loadScreenPermissions method.  
     * 
     * @param screenComponent
     * @return true if user has access to the screen component
     */
    public boolean checkCachedScreenPermission(final String screenComponent){       
        if (permissionCache.containsKey(screenComponent)){
            return permissionCache.get(screenComponent);
        } else{
            throw new RuntimeException("Permission not cached " + screenComponent);
        }
    }
	
	/**
	 * Used to check if user has a permission by its permission name. If the permission has been cached
	 * in the browser, it will use the cached permission.
	 * 
	 * @param permissionName
	 * @return true if user has the permission
	 */
	public void checkPermission(final String permissionName, final Callback<Boolean> checkPermissionCallback){
        if (permissionCache.containsKey(permissionName)){
        	checkPermissionCallback.exec(permissionCache.get(permissionName));
        } else {
			securityRpcService.hasPermissionByPermissionName(permissionName, new KSAsyncCallback<Boolean>() {
	            @Override
	            public void onSuccess(Boolean result) {
	            	permissionCache.put(permissionName, result);
	            	checkPermissionCallback.exec(result);
	            }
	        });
        }
	}
	
	/**
	 * Used to check if user has at least one of the permissions listed in the set of permissions passed in
	 * 
	 * @param permissionNames list of permission names to check
	 * @return true if user has the permission
	 */
	public void checkPermission(final String[] permissionNames, final Callback<Boolean> checkPermissionCallback){
        ArrayList<String> permissionsToGet = new ArrayList<String>();
        
		//Check if any of the permission names are already in the permission cache, and return if
        //user has permission for one already in the cache.
        for (String permissionName:permissionNames){
			if (permissionCache.containsKey(permissionName)){
				if (permissionCache.get(permissionName)){
					checkPermissionCallback.exec(permissionCache.get(true));
					break;
				}
			} else {
				permissionsToGet.add(permissionName);
			}		
		}
		
        //If we have permissions we could not check in permission cache, retrieve them from the permission service
        if (!permissionsToGet.isEmpty()){
			securityRpcService.getPermissions(permissionsToGet, new KSAsyncCallback<HashMap<String,Boolean>>() {
				@Override
				public void onSuccess(HashMap<String, Boolean> result) {
					boolean hasAccess = false;
					
					for (Entry<String,Boolean> entry:result.entrySet()){
		            	permissionCache.put(entry.getKey(), entry.getValue());
		            	if (entry.getValue()){
		            		hasAccess = true;
		            	}
					}	
					
					checkPermissionCallback.exec(hasAccess);
				}
	        });
        }
	}
	
	/**
	 * Used to check if user has the permission with given permissionName without being cached.  This is useful
	 * if the permission is a derived permission and needs to be re-checked each time.
	 *  
	 * @param permissionName
	 */
	public void checkPermissionNoCache(final String permissionName, final Callback<Boolean> checkPermissionCallback){
		securityRpcService.hasScreenPermission(permissionName, new KSAsyncCallback<Boolean>() {
            @Override
            public void onSuccess(Boolean result) {
            	checkPermissionCallback.exec(result);
            }
        });		

	}
	
	/**
	 * Loads into the permission cache all the permissions user has for a permission type.
	 */
	public void loadPermissionsByPermissionType(PermissionType permissionType){
		securityRpcService.getPermissionsByType(permissionType, new KSAsyncCallback<ArrayList<String>>(){

			@Override
			public void onSuccess(ArrayList<String> result) {
				for (String permissionName:result){
					permissionCache.put(permissionName, true);
				}				
			}			
		});
	}
	
	/**
     * Loads into the permission cache all the permissions user has for a permission type.
     */
    public void loadScreenPermissions(final ArrayList<String> screenComponents, final Callback<Boolean> loadPermissionsCallback){
        securityRpcService.getScreenPermissions(screenComponents, new KSAsyncCallback<HashMap<String, Boolean>>(){

            @Override
            public void onSuccess(HashMap<String, Boolean> result) {
                for (Entry<String,Boolean> entry:result.entrySet()){
                    permissionCache.put(entry.getKey(), entry.getValue());
                }
                loadPermissionsCallback.exec(true);
            }
            
        });
    }
    
}
