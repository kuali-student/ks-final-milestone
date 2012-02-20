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

package org.kuali.student.r1.common.ui.server.gwt;

import java.util.List;

import org.apache.log4j.Logger;
import org.kuali.student.r1.common.dictionary.old.dto.ObjectStructure;
import org.kuali.student.r1.common.dictionary.service.old.DictionaryService;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;

import org.kuali.student.r2.common.search.service.SearchService;
import org.kuali.student.r1.common.search.dto.SearchCriteriaTypeInfo;
import org.kuali.student.r1.common.search.dto.SearchRequest;
import org.kuali.student.r1.common.search.dto.SearchResult;
import org.kuali.student.r1.common.search.dto.SearchResultTypeInfo;
import org.kuali.student.r1.common.search.dto.SearchTypeInfo;
import org.kuali.student.r1.common.ui.client.service.BaseRpcService;
import org.kuali.student.r2.common.dto.ContextInfo;

import org.kuali.student.common.util.security.SecurityUtils;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import org.kuali.rice.kim.api.permission.PermissionService;

/**
 * This abstract service delegates search & dictionary operations to the web service being remoted.
 * Extend this class for gwt servlets only if you the service being remoted has dictionary
 * and search operations 
 * 
 * @author Kuali Student Team
 *
 */
@Deprecated
public abstract class BaseRpcGwtServletAbstract<SEI> extends RemoteServiceServlet implements BaseRpcService{
	final Logger LOG = Logger.getLogger(BaseRpcGwtServletAbstract.class);
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
     * @see org.kuali.student.common.dictionary.service.old.DictionaryService#getObjectStructure(java.lang.String)
     */
    @Override
    public ObjectStructure getObjectStructure(String objectTypeKey) {
        return ((DictionaryService)getService()).getObjectStructure(objectTypeKey);        
    }

    /**
     * @see org.kuali.student.common.dictionary.service.old.DictionaryService#getObjectTypes()
     */
    @Override
    public List<String> getObjectTypes() {
        return ((DictionaryService)getService()).getObjectTypes();
    }
    
    /**
     * @see org.kuali.student.common.search.service.SearchService#getSearchCriteriaType(java.lang.String)
     */
    @Override
    public SearchCriteriaTypeInfo getSearchCriteriaType(String searchCriteriaTypeKey, ContextInfo context){
//        try {
// TODO KSCM            return ((SearchService)getService()).getSearchCriteriaType(searchCriteriaTypeKey, context);
//        } catch (DoesNotExistException e) {
//        	LOG.error(e);
//        } catch (InvalidParameterException e) {
//        	LOG.error(e);
//        } catch (MissingParameterException e) {
//        	LOG.error(e);
//        } catch (OperationFailedException e) {
//        	LOG.error(e);
//        }
        
        return null;
    }
    
    /**
     * @throws OperationFailedException 
     * @see org.kuali.student.common.search.service.SearchService#getSearchCriteriaTypes()
     */
    @Override
    public List<SearchCriteriaTypeInfo> getSearchCriteriaTypes(ContextInfo context){
  // TODO KSCM
//    	try {
//            return ((SearchService)getService()).getSearchCriteriaTypes(context);
//        } catch (OperationFailedException e) {
//        	LOG.error(e);
//        }
        return null;
    }
    
    /**
     * @see org.kuali.student.common.search.service.SearchService#getSearchResultType(java.lang.String)
     */
    @Override
    public SearchResultTypeInfo getSearchResultType(String searchResultTypeKey, ContextInfo context){
        // TODO KSCM
//    	try {
//            return ((SearchService)getService()).getSearchResultType(searchResultTypeKey, context);
//        } catch (DoesNotExistException e) {
//        	LOG.error(e);
//        } catch (InvalidParameterException e) {
//        	LOG.error(e);
//        } catch (MissingParameterException e) {
//        	LOG.error(e);
//        } catch (OperationFailedException e) {
//        	LOG.error(e);
//        }
        return null;
    }
    
    /**
     * @see org.kuali.student.common.search.service.SearchService#getSearchResultTypes()
     */

    @Override
    public List<SearchResultTypeInfo> getSearchResultTypes(ContextInfo context){
    // TODO KSCM NINA to FIX
    //        try {
//            return ((SearchService)getService()).getSearchResultTypes(context);
//        } catch (OperationFailedException e) {
//        	LOG.error(e);
//        }
        return null;
    }
    
    /**
     * @see org.kuali.student.common.search.service.SearchService#getSearchType(java.lang.String)
     */
 // TODO KSCM NINA to FIX
    @Override
    public SearchTypeInfo getSearchType(String searchTypeKey, ContextInfo context){
//        try {
//            return ((SearchService)getService()).getSearchType(searchTypeKey, context);
//        } catch (DoesNotExistException e) {
//        	LOG.error(e);
//        } catch (InvalidParameterException e) {
//        	LOG.error(e);
//        } catch (MissingParameterException e) {
//        	LOG.error(e);
//        } catch (OperationFailedException e) {
//        	LOG.error(e);
//        }
        return null;
    }
    
    /**
     * @see org.kuali.student.common.search.service.SearchService#getSearchTypes()
     */
 // TODO KSCM NINA to FIX
    @Override
    public List<SearchTypeInfo> getSearchTypes(ContextInfo context){
//        try {
//            return ((SearchService)getService()).getSearchTypes(context);
//        } catch (OperationFailedException e) {
//        	LOG.error(e);
//        }
        return null;
    }
    
    /**
     * @see org.kuali.student.common.search.service.SearchService#getSearchTypesByCriteria(java.lang.String)
     */
 // TODO KSCM NINA to FIX
    @Override
    public List<SearchTypeInfo> getSearchTypesByCriteria(String searchCriteriaTypeKey, ContextInfo context){
//        try {
//            return ((SearchService)getService()).getSearchTypes(context);
//        } catch (OperationFailedException e) {
//        	LOG.error(e);
//        }
        return null;
    }
    
    /**
     * @see org.kuali.student.common.search.service.SearchService#getSearchTypesByResult(java.lang.String)
     */
 // TODO KSCM NINA to FIX
    @Override
    public List<SearchTypeInfo> getSearchTypesByResult(String searchResultTypeKey, ContextInfo context){
//        try {
//            return ((SearchService)getService()).getSearchTypesByResult(searchResultTypeKey, context);
//        } catch (DoesNotExistException e) {
//        	LOG.error(e);
//        } catch (InvalidParameterException e) {
//        	LOG.error(e);
//        } catch (MissingParameterException e) {
//        	LOG.error(e);
//        } catch (OperationFailedException e) {
//        	LOG.error(e);
//        }
        return null;
    }
    
    /**
     * @see org.kuali.student.common.search.service.SearchService#search(org.kuali.student.common.search.dto.SearchRequest)
     */
 // TODO KSCM NINA to FIX
    @Override
    public SearchResult search(SearchRequest searchRequest, ContextInfo context) {                
//        try {
//			return ((SearchService)getService()).search(searchRequest, context);
//		} catch (MissingParameterException e) {
//			LOG.error(e);
//		} 
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
