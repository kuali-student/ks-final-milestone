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

package org.kuali.student.common.ui.server.gwt;

import java.util.List;

import org.kuali.rice.kim.service.PermissionService;
import org.kuali.student.common.ui.client.service.BaseRpcService;
import org.kuali.student.common.util.security.SecurityUtils;
import org.kuali.student.core.dictionary.dto.ObjectStructure;
import org.kuali.student.core.dictionary.service.DictionaryService;
import org.kuali.student.core.exceptions.DoesNotExistException;
import org.kuali.student.core.exceptions.InvalidParameterException;
import org.kuali.student.core.exceptions.MissingParameterException;
import org.kuali.student.core.exceptions.OperationFailedException;
import org.kuali.student.core.search.dto.SearchCriteriaTypeInfo;
import org.kuali.student.core.search.dto.SearchRequest;
import org.kuali.student.core.search.dto.SearchResult;
import org.kuali.student.core.search.dto.SearchResultTypeInfo;
import org.kuali.student.core.search.dto.SearchTypeInfo;
import org.kuali.student.core.search.service.SearchService;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * This abstract service delegates search & dictionary operations to the web service being remoted.
 * Extend this class for gwt servlets only if you the service being remoted has dictionary
 * and search operations 
 * 
 * @author Kuali Student Team
 *
 */
public abstract class BaseRpcGwtServletAbstract<SEI> extends RemoteServiceServlet implements BaseRpcService{

    private static final long serialVersionUID = 1L;
    
    protected SEI service;
    protected PermissionService permissionService;
        
    
    public SEI getService(){
        return this.service;
    }
       
    public void setService(SEI service) {
        this.service = service;
    };

    public PermissionService getPermissionService() {
    	return permissionService;
    }

	public void setPermissionService(PermissionService permissionService) {
    	this.permissionService = permissionService;
    }

	/**
     * @see org.kuali.student.core.dictionary.service.DictionaryService#getObjectStructure(java.lang.String)
     */
    @Override
    public ObjectStructure getObjectStructure(String objectTypeKey) {
        return ((DictionaryService)getService()).getObjectStructure(objectTypeKey);        
    }

    /**
     * @see org.kuali.student.core.dictionary.service.DictionaryService#getObjectTypes()
     */
    @Override
    public List<String> getObjectTypes() {
        return ((DictionaryService)getService()).getObjectTypes();
    }
    
    /**
     * @see org.kuali.student.core.dictionary.service.DictionaryService#validateObject(java.lang.String, java.lang.String, java.lang.String)
     */
    @Override
    public boolean validateObject(String objectTypeKey, String stateKey, String info) {
        return ((DictionaryService)getService()).validateObject(objectTypeKey, stateKey, info);
    }
    
    /**
     * @see org.kuali.student.core.dictionary.service.DictionaryService#validateStructureData(java.lang.String, java.lang.String, java.lang.String)
     */
    @Override
    public boolean validateStructureData(String objectTypeKey, String stateKey, String info) {
        return ((DictionaryService)getService()).validateStructureData(objectTypeKey, stateKey, info);
    }
    
    /**
     * @see org.kuali.student.core.search.service.SearchService#getSearchCriteriaType(java.lang.String)
     */
    @Override
    public SearchCriteriaTypeInfo getSearchCriteriaType(String searchCriteriaTypeKey){
        try {
            return ((SearchService)getService()).getSearchCriteriaType(searchCriteriaTypeKey);
        } catch (DoesNotExistException e) {
            e.printStackTrace();
        } catch (InvalidParameterException e) {
            e.printStackTrace();
        } catch (MissingParameterException e) {
            e.printStackTrace();
        } catch (OperationFailedException e) {
            e.printStackTrace();
        }
        
        return null;
    }
    
    /**
     * @throws OperationFailedException 
     * @see org.kuali.student.core.search.service.SearchService#getSearchCriteriaTypes()
     */
    @Override
    public List<SearchCriteriaTypeInfo> getSearchCriteriaTypes(){
        try {
            return ((SearchService)getService()).getSearchCriteriaTypes();
        } catch (OperationFailedException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    /**
     * @see org.kuali.student.core.search.service.SearchService#getSearchResultType(java.lang.String)
     */
    @Override
    public SearchResultTypeInfo getSearchResultType(String searchResultTypeKey){
        try {
            return ((SearchService)getService()).getSearchResultType(searchResultTypeKey);
        } catch (DoesNotExistException e) {
            e.printStackTrace();
        } catch (InvalidParameterException e) {
            e.printStackTrace();
        } catch (MissingParameterException e) {
            e.printStackTrace();
        } catch (OperationFailedException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    /**
     * @see org.kuali.student.core.search.service.SearchService#getSearchResultTypes()
     */
    @Override
    public List<SearchResultTypeInfo> getSearchResultTypes(){
        try {
            return ((SearchService)getService()).getSearchResultTypes();
        } catch (OperationFailedException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    /**
     * @see org.kuali.student.core.search.service.SearchService#getSearchType(java.lang.String)
     */
    @Override
    public SearchTypeInfo getSearchType(String searchTypeKey){
        try {
            return ((SearchService)getService()).getSearchType(searchTypeKey);
        } catch (DoesNotExistException e) {
            e.printStackTrace();
        } catch (InvalidParameterException e) {
            e.printStackTrace();
        } catch (MissingParameterException e) {
            e.printStackTrace();
        } catch (OperationFailedException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    /**
     * @see org.kuali.student.core.search.service.SearchService#getSearchTypes()
     */
    @Override
    public List<SearchTypeInfo> getSearchTypes(){
        try {
            return ((SearchService)getService()).getSearchTypes();
        } catch (OperationFailedException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    /**
     * @see org.kuali.student.core.search.service.SearchService#getSearchTypesByCriteria(java.lang.String)
     */
    @Override
    public List<SearchTypeInfo> getSearchTypesByCriteria(String searchCriteriaTypeKey){
        try {
            return ((SearchService)getService()).getSearchTypes();
        } catch (OperationFailedException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    /**
     * @see org.kuali.student.core.search.service.SearchService#getSearchTypesByResult(java.lang.String)
     */
    @Override
    public List<SearchTypeInfo> getSearchTypesByResult(String searchResultTypeKey){
        try {
            return ((SearchService)getService()).getSearchTypesByResult(searchResultTypeKey);
        } catch (DoesNotExistException e) {
            e.printStackTrace();
        } catch (InvalidParameterException e) {
            e.printStackTrace();
        } catch (MissingParameterException e) {
            e.printStackTrace();
        } catch (OperationFailedException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    /**
     * @see org.kuali.student.core.search.service.SearchService#search(org.kuali.student.core.search.dto.SearchRequest)
     */
    @Override
    public SearchResult search(SearchRequest searchRequest) {                
        try {
			return ((SearchService)getService()).search(searchRequest);
		} catch (MissingParameterException e) {
			e.printStackTrace();
		} 
		return null;
    }        

	protected String getCurrentUser() {
		String username = SecurityUtils.getCurrentUserId();
		//backdoorId is only for convenience
		if(username==null&&this.getThreadLocalRequest().getSession().getAttribute("backdoorId")!=null){
			username=(String)this.getThreadLocalRequest().getSession().getAttribute("backdoorId");
        }
		return username;
	}
}
