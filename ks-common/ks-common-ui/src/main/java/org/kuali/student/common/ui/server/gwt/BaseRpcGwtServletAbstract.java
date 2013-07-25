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

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import org.apache.log4j.Logger;
import org.kuali.rice.kim.api.permission.PermissionService;
import org.kuali.student.common.ui.client.service.BaseRpcService;
import org.kuali.student.common.util.security.SecurityUtils;
import org.kuali.student.r1.common.dictionary.old.dto.ObjectStructure;
import org.kuali.student.r1.common.dictionary.service.old.DictionaryService;
import org.kuali.student.r2.common.exceptions.*;
import org.kuali.student.r2.core.search.dto.SearchRequestInfo;
import org.kuali.student.r2.core.search.dto.SearchResultInfo;
import org.kuali.student.r2.core.search.service.SearchService;
import org.kuali.student.r2.common.util.ContextUtils;
import org.kuali.student.r2.core.class1.type.dto.TypeInfo;

import java.util.List;

/**
 * This abstract service delegates search & dictionary operations to the web service being
 * remoted. Extend this class for gwt servlets only if you the service being remoted has
 * dictionary and search operations
 * 
 * @author Kuali Student Team
 * 
 */
public abstract class BaseRpcGwtServletAbstract<SEI> extends RemoteServiceServlet implements BaseRpcService {
    final Logger LOG = Logger.getLogger(BaseRpcGwtServletAbstract.class);
    private static final long serialVersionUID = 1L;

    protected SEI service;
    protected PermissionService permissionService;

    public SEI getService() {
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
        return ((DictionaryService) getService()).getObjectStructure(objectTypeKey);
    }

    /**
     * @see org.kuali.student.common.dictionary.service.old.DictionaryService#getObjectTypes()
     */
    @Override
    public List<String> getObjectTypes() {
        return ((DictionaryService) getService()).getObjectTypes();
    }



    /**
     * @see org.kuali.student.common.search.service.SearchService#getSearchType(java.lang.String)
     */

    @Override
    public TypeInfo getSearchType(String searchTypeKey) {
        try {
            return ((SearchService) getService()).getSearchType(searchTypeKey, ContextUtils.getContextInfo());
        } catch (DoesNotExistException e) {
            LOG.error(e);
        } catch (InvalidParameterException e) {
            LOG.error(e);
        } catch (MissingParameterException e) {
            LOG.error(e);
        } catch (OperationFailedException e) {
            LOG.error(e);
        }
        return null;
    }

    /**
     * @see org.kuali.student.common.search.service.SearchService#getSearchTypes()
     */

    @Override
    public List<TypeInfo> getSearchTypes() {
        try {
            return ((SearchService) getService()).getSearchTypes(ContextUtils.getContextInfo());
        } catch (InvalidParameterException e) {
            LOG.error(e);
        } catch (MissingParameterException e) {
            LOG.error(e);
        } catch (OperationFailedException e) {
            LOG.error(e);
        }
        return null;
    }

    /**
     * @see org.kuali.student.common.search.service.SearchService#search(org.kuali.student.common.search.dto.SearchRequest)
     */

    @Override
    public SearchResultInfo search(SearchRequestInfo searchRequest) {
        try {
            return ((SearchService) getService()).search(searchRequest, ContextUtils.getContextInfo());
        } catch (OperationFailedException e) {
            LOG.error(e);
        } catch (InvalidParameterException e) {
            LOG.error(e);
        } catch (PermissionDeniedException e) {
            LOG.error(e);
        } catch (MissingParameterException e) {
            LOG.error(e);
        }
        return null;
    }

    protected String getCurrentUser() {
        String username = SecurityUtils.getCurrentUserId();
        //backdoorId is only for convenience
        if (username == null && this.getThreadLocalRequest().getSession().getAttribute("backdoorId") != null) {
            username = (String) this.getThreadLocalRequest().getSession().getAttribute("backdoorId");
        }
        return username;
    }
}
